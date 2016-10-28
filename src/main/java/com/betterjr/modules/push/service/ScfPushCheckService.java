package com.betterjr.modules.push.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.mapper.CustDecimalJsonSerializer;
import com.betterjr.common.utils.BTAssert;
import com.betterjr.common.utils.BetterDateUtils;
import com.betterjr.common.utils.BetterStringUtils;
import com.betterjr.common.utils.Collections3;
import com.betterjr.modules.acceptbill.entity.ScfAcceptBill;
import com.betterjr.modules.acceptbill.service.ScfAcceptBillService;
import com.betterjr.modules.account.entity.CustInfo;
import com.betterjr.modules.account.entity.CustOperatorInfo;
import com.betterjr.modules.account.service.CustAccountService;
import com.betterjr.modules.account.service.CustOperatorService;
import com.betterjr.modules.agreement.entity.ScfElecAgreement;
import com.betterjr.modules.customer.ICustRelationService;
import com.betterjr.modules.customer.data.CustRelationData;
import com.betterjr.modules.loan.entity.ScfRequest;
import com.betterjr.modules.notification.INotificationSendService;
import com.betterjr.modules.notification.NotificationModel;
import com.betterjr.modules.notification.NotificationModel.Builder;
import com.betterjr.modules.param.service.ParamService;
import com.betterjr.modules.push.data.RequestTradeStatusType;
import com.betterjr.modules.push.entity.ScfSupplierPushDetail;
import com.betterjr.modules.wechat.dubboclient.CustWeChatDubboClientService;
/***
 * 推送条件检查
 * @author hubl
 *
 */
@Service
public class ScfPushCheckService {
    
    private static final Logger logger = LoggerFactory.getLogger(ScfPushCheckService.class);

    @Reference(interfaceClass=ICustRelationService.class)
    private ICustRelationService custRelationService;
    @Autowired
    private ParamService paramService;
    @Autowired
    private CustAccountService accountService;
    
    @Reference(interfaceClass = INotificationSendService.class)
    public INotificationSendService notificationSendService;
    @Autowired
    private CustOperatorService custOperatorService;
    @Autowired
    private ScfAcceptBillService acceptBillService;
    @Autowired
    private CustWeChatDubboClientService wechatClientService;
    @Autowired
    private CustAccountService custAccoService;

    /***
     * 推送数据
     * @param anCoreCustNo 核心企业客户号
     * @param anSupplierCustNo 供应商企业客户号
     */
    public List<CustRelationData> pushData(Long anCoreCustNo,Long anSupplierCustNo,ScfSupplierPushDetail supplierPushDetail){
        // 得到核心企业与保理机构的关系信息
        List<CustRelationData> custRelationDataList=custRelationService.webQueryCustRelationData(anCoreCustNo,"2");
        logger.info("relationDataList:"+custRelationDataList);
        // 得到核心企业设置的机构信息
        String agencyCustNo=paramService.queryCoreParam(anCoreCustNo.toString(),supplierPushDetail.getEndDate());
        if(BetterStringUtils.isNotBlank(agencyCustNo)){
            custRelationDataList=compareCust(custRelationDataList,agencyCustNo);
        }else{
            custRelationDataList=new ArrayList<CustRelationData>();
        }
        // 再取供应商关系客户信息
        List<CustRelationData> supplierRelationDataList=custRelationService.webQueryCustRelationData(anSupplierCustNo,"0");
        // 判断该供应商接不接收资金方消息推送，允许则可推送有设置的信息
        if(paramService.checkSupplierParam(anSupplierCustNo.toString())){
            supplierRelationDataList.addAll(custRelationDataList);
        }
        // 微信发送
        for(CustRelationData relationData:supplierRelationDataList){
            if(!billSend(anCoreCustNo,relationData.getCustNo(),supplierPushDetail)){
                supplierRelationDataList=new ArrayList<CustRelationData>();
                break;
            }
        }
        return supplierRelationDataList;
    }
    
    /***
     * 比较设置需要推送的机构信息
     * @param custRelationDataList
     * @param agencyCustNo
     * @return
     */
    public List<CustRelationData> compareCust(List<CustRelationData> custRelationDataList,String agencyCustNo){
        List<CustRelationData> relationList=new ArrayList<CustRelationData>();
        String[] agencyArr=agencyCustNo.split(",");
        for(CustRelationData relationData:custRelationDataList){
            if(Arrays.asList(agencyArr).contains(relationData.getRelateCustno())){
                relationList.add(relationData);
            }
        }
        return relationList;
    }
    
    /****
     * 微信票据消息发送
     * @param sendCustNo 发送客户
     * @param targetCustNo 接收客户
     */
    public boolean billSend(Long sendCustNo,Long targetCustNo,ScfSupplierPushDetail supplierPushDetail){
        boolean bool=false;
        final CustInfo sendCustomer = accountService.findCustInfo(sendCustNo);
        supplierPushDetail.setCustName(sendCustomer.getCustName());
        final CustOperatorInfo sendOperator = Collections3.getFirst(custOperatorService.queryOperatorInfoByCustNo(sendCustNo));
        final CustOperatorInfo targetOperator = Collections3.getFirst(custOperatorService.queryOperatorInfoByCustNo(targetCustNo));
        if(sendOperator!=null && targetOperator!=null){
            supplierPushDetail.setTragetCustName(accountService.queryCustName(targetCustNo));
            supplierPushDetail.setAcceptor(findBillAcceptor(Long.parseLong(supplierPushDetail.getOrderId())).getAcceptor());
            supplierPushDetail.setBillNo(findBillAcceptor(Long.parseLong(supplierPushDetail.getOrderId())).getBillNo());
            final Builder builder = NotificationModel.newBuilder("商业汇票开立通知", sendCustomer, sendOperator);
            builder.addParam("appId", wechatClientService.getAppId());
            builder.addParam("wechatUrl", wechatClientService.getWechatUrl());
            builder.addParam("tragetCustName", supplierPushDetail.getTragetCustName());
            builder.addParam("custName", supplierPushDetail.getCustName());
            builder.addParam("acceptor", supplierPushDetail.getAcceptor());
            builder.addParam("billNo", supplierPushDetail.getBillNo());
            builder.addParam("disMoney", supplierPushDetail.getDisMoney());
            builder.addParam("disDate", supplierPushDetail.getDisDate());
            builder.addReceiver(targetCustNo, targetOperator.getId());  // 接收人
            notificationSendService.sendNotification(builder.build());
            bool=true;
        }
        return bool;
    }
    
    /****
     * 发送签约提醒
     * @param elecAgreement
     * @return
     */
    public boolean signSend(ScfElecAgreement elecAgreement,ScfSupplierPushDetail anSupplierPushDetail){
        boolean bool=false;
        try {
            final CustInfo sendCustomer = accountService.findCustInfo(Long.parseLong(elecAgreement.getFactorNo()));
            final CustOperatorInfo sendOperator = Collections3.getFirst(custOperatorService.queryOperatorInfoByCustNo(Long.parseLong(elecAgreement.getFactorNo())));
            CustOperatorInfo targetOperator=null;
            CustInfo targetCustomer=null;
            if(BetterStringUtils.equalsIgnoreCase(elecAgreement.getAgreeType(), "0")){
                targetCustomer = accountService.findCustInfo(elecAgreement.getSupplierNo());
                targetOperator = Collections3.getFirst(custOperatorService.queryOperatorInfoByCustNo(elecAgreement.getSupplierNo()));
            }else if(BetterStringUtils.equalsIgnoreCase(elecAgreement.getAgreeType(), "1")){
                targetCustomer = accountService.findCustInfo(elecAgreement.getBuyerNo());
                targetOperator = Collections3.getFirst(custOperatorService.queryOperatorInfoByCustNo(elecAgreement.getBuyerNo()));
            }
            if(sendOperator!=null && targetOperator!=null){
                anSupplierPushDetail.setSendNo(elecAgreement.getFactorNo());
                anSupplierPushDetail.setReceiveNo(targetCustomer.getCustNo()+"");
                anSupplierPushDetail.setBeginDate(elecAgreement.getRegDate());
                anSupplierPushDetail.setEndDate(BetterDateUtils.addStrDays(elecAgreement.getRegDate(),3));
                
                final Builder builder = NotificationModel.newBuilder("签约提醒", sendCustomer, sendOperator);
                builder.addParam("appId", wechatClientService.getAppId());
                builder.addParam("wechatUrl", wechatClientService.getWechatUrl());
                builder.addParam("appNo", elecAgreement.getAppNo());
                builder.addParam("supplierName", targetCustomer.getName());
                builder.addParam("factorName", sendCustomer.getCustName());
                builder.addParam("regDate", getDisDate(elecAgreement.getRegDate()));
                builder.addParam("agreeName", elecAgreement.getAgreeName());
                builder.addParam("endDate", getDisDate(BetterDateUtils.addStrDays(elecAgreement.getRegDate(),3)));
                
                builder.addReceiver(elecAgreement.getSupplierNo(), targetOperator.getId());  // 接收人
                notificationSendService.sendNotification(builder.build());
                bool=true;
            }
        }
        catch (Exception e) {
            logger.error("发送签约提醒异常："+e.getMessage());
        }
        return bool;
    }
    
    /****
     * 融资状态推送
     * @return
     */
    public boolean orderStatusSend(ScfRequest anRequest){
        boolean bool=false;
        final CustInfo sendCustomer = accountService.findCustInfo(anRequest.getFactorNo());
        final CustOperatorInfo sendOperator = Collections3.getFirst(custOperatorService.queryOperatorInfoByCustNo(anRequest.getFactorNo()));
        final CustInfo targetCustomer = accountService.findCustInfo(anRequest.getCustNo());
        final CustOperatorInfo targetOperator = Collections3.getFirst(custOperatorService.queryOperatorInfoByCustNo(anRequest.getCustNo()));
        if(sendOperator!=null && targetOperator!=null){
            final Builder builder = NotificationModel.newBuilder("融资进度提醒", sendCustomer, sendOperator);
            builder.addParam("appId", wechatClientService.getAppId());
            builder.addParam("wechatUrl", wechatClientService.getWechatUrl());
            builder.addParam("requestNo", anRequest.getRequestNo());
            builder.addParam("supplierName", targetCustomer.getName());
            builder.addParam("factorName", sendCustomer.getCustName());
            builder.addParam("requestDate", getDisDate(anRequest.getRegDate()));
            builder.addParam("balance", getDisMoney(anRequest.getBalance()));
            builder.addParam("tradeStatus", RequestTradeStatusType.checking(anRequest.getTradeStatus()).getTitle());
            builder.addReceiver(anRequest.getCustNo(), targetOperator.getId());  // 接收人
            notificationSendService.sendNotification(builder.build());
            bool=true;
        }
        return bool;
    }
    
    /***
     * 查询票据承兑人
     * @param billId
     * @return
     */
    public ScfAcceptBill findBillAcceptor(Long billId){
        Map<String, Object> anMap=new HashMap<String, Object>();
        anMap.put("id", billId);
        ScfAcceptBill acceptBill=Collections3.getFirst(acceptBillService.findAcceptBill(anMap));
        BTAssert.notNull(acceptBill,"未找到票据信息");
        return acceptBill;
    }
    public String getDisDate(final String date) {
        return BetterDateUtils.formatDate(BetterDateUtils.parseDate(date), "yyyy年MM月dd日");
    }
    public String getDisMoney(final BigDecimal money) {
        return "￥"+CustDecimalJsonSerializer.format(money);
    }
}
