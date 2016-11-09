package com.betterjr.modules.supplychain.service;

import java.util.*;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.mapper.BeanMapper;
import com.betterjr.common.service.BaseService;
import com.betterjr.common.service.SpringContextHolder;
import com.betterjr.common.utils.BTObjectUtils;
import com.betterjr.common.utils.BetterStringUtils;
import com.betterjr.common.utils.Collections3;
import com.betterjr.common.utils.MathExtend;
import com.betterjr.common.utils.reflection.ReflectionUtils;
import com.betterjr.modules.acceptbill.service.ScfAcceptBillService;
import com.betterjr.modules.agreement.utils.SupplyChainUtil;
import com.betterjr.modules.customer.ICustRelationService;
import com.betterjr.modules.supplychain.data.CoreCustInfo;
import com.betterjr.modules.supplychain.data.ScfClientDataDetail;
import com.betterjr.modules.supplychain.data.ScfClientDataInfo;
import com.betterjr.modules.client.data.ScfClientDataParentFace;
import com.betterjr.modules.supplychain.entity.CustTempEnrollInfo;
import com.betterjr.modules.supplychain.entity.ScfSupplierBank;

@Service
public class ScfFactorClientHelperService {

    private static final Logger logger = LoggerFactory.getLogger(ScfFactorClientHelperService.class);

    @Autowired
    private ScfSupplierBankService supplierBankService;

    @Reference(interfaceClass = ICustRelationService.class)
    private ICustRelationService relationService;
    
    @Autowired
    private SupplyAccoRequestService supplyAccoService;

    private Map<String, ScfClientDataInfo> classMapService = new LinkedCaseInsensitiveMap();
 
    @Autowired
    private CustCoreCorpService coreCorpService;
    
    public Map<String, ScfClientDataInfo> getClassMapService() {

        return this.classMapService;
    }

    public void setClassMapService(Map<String, ScfClientDataInfo> anClassMapService) {

        this.classMapService = anClassMapService;
    }

    @PostConstruct
    public void initClassMapService() {
        this.classMapService.put("CoreSupplierInfo", new ScfClientDataInfo("CoreSupplierInfo", "coreEnterpriseService", "btNo", "supplier", "btNo"));
        this.classMapService.put("ScfAcceptBill",
                new ScfClientDataInfo("ScfAcceptBill", "scfAcceptBillService", "billNo", "supplierBill", "suppBankAccount"));
        this.classMapService.put("ScfBankPaymentFlow",
                new ScfClientDataInfo("ScfBankPaymentFlow", "scfBankPaymentFlowService", "btInnerId", "supplierPay", "suppBankAccount"));
        this.classMapService.put("ScfCapitalFlow",
                new ScfClientDataInfo("ScfCapitalFlow", "scfCapitalFlowService", "btInnerId", "supplierCapital", "suppBankAccount"));
        this.classMapService.put("ScfSupplierBank", new ScfClientDataInfo("ScfSupplierBank", "scfSupplierBankService", "btNo", "supplierBank", ""));
        this.classMapService.put("ScfRelation", new ScfClientDataInfo("ScfRelation", "scfRelationService", "btNo", "supplierRela", "btNo"));
        this.classMapService.put("CustTempEnrollInfo",
                new ScfClientDataInfo("CustTempEnrollInfo", "supplyAccoRequestService", "btNo", "supplierReg", "btNo"));

        this.classMapService.put("CustCoreCorpInfo",
                new ScfClientDataInfo("CustCoreCorpInfo", "CustCoreCorpService", "corpNo", "supplierReg", "corpNo"));
    }

    /**
     * 产生数字证书信息时，根据客户的组织机构证书信息和银行账户更新供应链金融相关信息
     * 
     * @param anCoreCustNo
     *            核心企业编码
     * @param anBankAccount
     *            银行账户
     * @param anOperOrg
     *            企业唯一标示
     */
    public void saveCustomerOperOrg(Long anCoreCustNo, String anBankAccount, String anOperOrg) {
        // 首先保存供应商银行账户信息，建立供应商银行账户信息和操作员所在机构的关系，客户银行账户信息中保存了资金系统的客户号和银行账户，兼顾的多方的处理。
        Set<String> workBtNoList = new HashSet();
        Set<String> workBankAccountList = new HashSet();
        Set<ScfSupplierBank> tmpBankSet = supplierBankService.saveCustOperOrg(anCoreCustNo, anBankAccount, anOperOrg);
        ScfSupplierBank tmpBankAccountInfo = null;
        for (ScfSupplierBank suppBank : tmpBankSet) {
            workBtNoList.add(suppBank.getBtNo());
            workBankAccountList.add(suppBank.getBankAccount());
            if (suppBank.getBankAccount().equalsIgnoreCase(anBankAccount)) {
                tmpBankAccountInfo = suppBank;
            }
        }
        BaseService workService = null;
        Map termMap = new HashMap();
        Object obj = null;
        List<ScfClientDataParentFace> workList;
        for (ScfClientDataInfo serviceInfo : this.classMapService.values()) {
            termMap.put("coreCustNo", anCoreCustNo);
            obj = serviceInfo.findCondition(workBtNoList, workBankAccountList);
            if (obj == null) {
                continue;
            }
            termMap.put(serviceInfo.getFilterField(), obj);
            workService = (BaseService) SpringContextHolder.getBean(serviceInfo.getWorkService());
            workList = workService.selectByProperty(termMap);
            for (ScfClientDataParentFace workFace : workList) {
                if (BetterStringUtils.defShortString(workFace.getOperOrg())) {
                    workFace.setOperOrg(anOperOrg);
                    workService.updateByPrimaryKey(workFace);
                }
                else {
                    logger.warn("更新数据的OperOrg已经存在，不能更新 ：存在的OperOrg:" + workFace.getOperOrg());
                }
            }

            termMap.clear();
        }

        // 然后准备开户的默认信息，如果注册，则从注册信息中取，如果没有注册，则从供应商信息中获得

        CustTempEnrollInfo custTemp = this.supplyAccoService.findByBankAccount(tmpBankAccountInfo);

        // 表示客户不是通过注册来使用的。
        Map tmpMap = null;
        if (custTemp != null) {
            tmpMap = custTemp.converToTmpDataMap();
        }
        else if (tmpBankAccountInfo != null) {
            tmpMap = tmpBankAccountInfo.converToTmpDataMap();
        }/*
        if (tmpMap != null) {
            String data = BTObjectUtils.fastSerializeStr(tmpMap);
            this.tmpDataService.saveCustTempDataByOperOrg(data, anOperOrg);
        }*/

    }

    /**
     * 客户开户时，写入客户客户号信息
     * 
     * @param anCustNo
     *            客户开户时，平台分配的客户号
     * @param anBankAccount
     *            银行账户信息
     * @param anOperOrg
     *            客户操作员账户
     */
    public void saveCustomerCustNo(Long anCustNo, String anBankAccount, String anOperOrg) {
        List<ScfSupplierBank> suppBankList = this.supplierBankService.findCustByBankAccount(anOperOrg, anBankAccount);
        if (Collections3.isEmpty(suppBankList)) {
            logger.warn("不是供应商的银行账户信息，不能更新客户号, anBankAccount =" + anBankAccount + ", anOperOrg = " + anOperOrg);
        }
        else {
            BaseService workService = null;
            List<ScfClientDataParentFace> workList;
            for (ScfClientDataInfo serviceInfo : this.classMapService.values()) {
                workService = (BaseService) SpringContextHolder.getBean(serviceInfo.getWorkService());
                workList = workService.selectByProperty("operOrg", anOperOrg);
                for (ScfClientDataParentFace workFace : workList) {
                    if (MathExtend.smallValue(workFace.getCustNo())) {
                        workFace.setCustNo(anCustNo);
                        workService.updateByPrimaryKey(workFace);
                    }
                }
            }
        }
    }
    
    public void saveCoreCorpData(String anWorkType, ScfClientDataDetail anWorkTypeData, List anDataList, Long anCoreCustNo) {
        coreCorpService.saveCoreCorpList(anDataList, anCoreCustNo);
    }
    
    /**
     * 保存客户端上传来的数据
     * 
     * @param anDataList
     *            数据列表
     * @param anCoreCustNo
     *            核心企业客户号
     */
    public void saveCustomerData(String anWorkType, ScfClientDataDetail anWorkTypeData, List<ScfClientDataParentFace> anDataList, Long anCoreCustNo) {
        // 空就不处理了.
        if (Collections3.isEmpty(anDataList)) {

            return;
        }
        String myClass = Collections3.getFirst(anDataList).getClass().getSimpleName();
        ScfClientDataInfo serviceInfo = classMapService.get(myClass);
        BaseService workService = (BaseService) SpringContextHolder.getBean(serviceInfo.getWorkService());
        logger.info("saveCustomerData work Service is " + workService);
        ScfSupplierBank suppBank;
        List<ScfClientDataParentFace> resultData = new ArrayList();
        for (ScfClientDataParentFace clientData : anDataList) {
            clientData.setCoreCustNo(anCoreCustNo);
            clientData.fillDefaultValue();
            // 表示基础账户信息，可以通过供应商和核心企业关系来取数据
            if (BetterStringUtils.isNotBlank(clientData.getBtNo()) || BetterStringUtils.isNotBlank(clientData.getBankAccount())) {
                if (BetterStringUtils.isNotBlank(clientData.getBtNo())) {
                    suppBank = this.supplierBankService.findCustNoByBtCustNo(anCoreCustNo, clientData.getBtNo());
                } // 表示可以通过银行信息获得客户编号
                else {
                    suppBank = this.supplierBankService.findScfBankByBankAccount(anCoreCustNo, clientData.getBankAccount());
                }
                if (suppBank != null) {
                    clientData.setCustNo(suppBank.getCustNo());
                    clientData.setOperOrg(suppBank.getOperOrg());
                }//通过从注册的表中获取已经开户的客户信息                
                else{
                    String tmpAccoName = anWorkTypeData.getBankAccName();
                    String tmpAcco = anWorkTypeData.getBankAcc();                    
                    CustTempEnrollInfo tempEnroll = supplyAccoService.findUnRegisterAccount(tmpAccoName, tmpAcco, false);
                    if (tempEnroll == null){
                       tmpAcco = clientData.getBankAccount();
                       tmpAccoName = clientData.findBankAccountName();
                    }
                    tempEnroll = supplyAccoService.findUnRegisterAccount(tmpAccoName, tmpAcco, false);
                    if (tempEnroll != null){
                        clientData.setCustNo(tempEnroll.getCustNo());
                        clientData.setOperOrg(tempEnroll.getOperOrg());
                    }
                }
            } // 其它情况不能处理，不属于处理的范围
            else {
                logger.error("find Has Data not dispather, " + clientData);
                continue;
            }
            resultData.add(clientData);
            // logger.info("work Client Data is " + clientData);
        }

        saveUploadSupplierData(workService, resultData, serviceInfo, anCoreCustNo);
        if (workService instanceof CoreEnterpriseService) {
            saveUploadSupplierRelation(resultData, anCoreCustNo);

            supplyAccoService.saveUnRegisterAccount(resultData, anCoreCustNo);
        }
    }

    private void saveUploadSupplierRelation(List anDataList, Long anCoreCustNo){
        String coreCustName = SupplyChainUtil.findCoreNameByCustNo(anCoreCustNo);
        for (Object data: anDataList){
           Map workValue = BeanMapper.map(data, HashMap.class);
           relationService.saveAndCheckCust(workValue, coreCustName , anCoreCustNo);
        }
    }
    
    /**
     * 保存核心企业上传上来的供应商银行账户账户数据；处理逻辑，该表主要由核心企业的数据同步，因此；<BR>
     * 每次处理都是删除后追加，先简单处理，以后实现归档的操作。即删除前作归档操作。 <BR>
     * 删除的逻辑是根据核心企业号和数据表中的唯一主键来删除。
     * 
     * @param anList
     *            数据列表
     * @return 处理成功返回true， 处理失败返回false
     */
    protected boolean saveUploadSupplierData(BaseService anService, List anList, ScfClientDataInfo anServiceInfo, Long anCoreCustNo) {
        // 没数据，直接返回
        if (Collections3.isEmpty(anList)) {

            return true;
        }

        if (anService instanceof ScfAcceptBillService) {
            ScfAcceptBillService accetBillService = (ScfAcceptBillService) anService;
            return accetBillService.saveUploadSupplierData(anList, anCoreCustNo);
        }
        else {
            Set<String> distinctKey = ReflectionUtils.listToKeySet(anList, anServiceInfo.getKeyField());
            Map<String, Object> termMap = new HashMap<String, Object>();
            for (String btNo : distinctKey) {
                termMap.put("coreCustNo", anCoreCustNo);
                termMap.put(anServiceInfo.getKeyField(), btNo);
                anService.deleteByExample(termMap);
                termMap.clear();
            }

            for (Object scfObj : anList) {

                anService.insert(scfObj);
            }
            return true;
        }
    }
    
    /**
     * 检查是否需要会写获取核心企业的上、下游客户信息
     * @param anMap 开户时提供的参数
     */
    public void checkTempAcco(Map anMap){
        logger.info("checkTempAcco request value :"+anMap);
        supplyAccoService.addSupplyAccountFromOpenAcco(anMap);
    }
}