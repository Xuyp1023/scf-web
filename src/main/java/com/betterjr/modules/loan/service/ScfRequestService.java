package com.betterjr.modules.loan.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.service.BaseService;
import com.betterjr.common.utils.BTAssert;
import com.betterjr.common.utils.BetterDateUtils;
import com.betterjr.common.utils.BetterStringUtils;
import com.betterjr.common.utils.Collections3;
import com.betterjr.mapper.pagehelper.Page;
import com.betterjr.modules.acceptbill.entity.ScfAcceptBill;
import com.betterjr.modules.account.service.CustAccountService;
import com.betterjr.modules.agreement.entity.CustAgreement;
import com.betterjr.modules.agreement.entity.ScfRequestCredit;
import com.betterjr.modules.agreement.entity.ScfRequestNotice;
import com.betterjr.modules.agreement.entity.ScfRequestOpinion;
import com.betterjr.modules.agreement.entity.ScfRequestProtacal;
import com.betterjr.modules.agreement.service.ScfAgreementService;
import com.betterjr.modules.customer.ICustMechBankAccountService;
import com.betterjr.modules.customer.ICustMechBaseService;
import com.betterjr.modules.customer.ICustMechLawService;
import com.betterjr.modules.customer.entity.CustMechBankAccount;
import com.betterjr.modules.customer.entity.CustMechBase;
import com.betterjr.modules.customer.entity.CustMechLaw;
import com.betterjr.modules.enquiry.entity.ScfOffer;
import com.betterjr.modules.enquiry.service.ScfOfferService;
import com.betterjr.modules.loan.dao.ScfRequestMapper;
import com.betterjr.modules.loan.entity.ScfLoan;
import com.betterjr.modules.loan.entity.ScfPayPlan;
import com.betterjr.modules.loan.entity.ScfRequest;
import com.betterjr.modules.loan.entity.ScfRequestScheme;
import com.betterjr.modules.loan.entity.ScfServiceFee;
import com.betterjr.modules.loan.helper.RequestTradeStatus;
import com.betterjr.modules.order.entity.ScfInvoice;
import com.betterjr.modules.order.entity.ScfOrder;
import com.betterjr.modules.order.helper.ScfOrderRelationType;
import com.betterjr.modules.order.service.ScfOrderService;
import com.betterjr.modules.product.entity.ScfProduct;
import com.betterjr.modules.product.service.ScfProductService;
import com.betterjr.modules.receivable.entity.ScfReceivable;
import com.betterjr.modules.workflow.data.CustFlowNodeData;

@Service
public class ScfRequestService extends BaseService<ScfRequestMapper, ScfRequest> {

    @Autowired
    private ScfPayPlanService payPlanService;
    @Autowired
    private CustAccountService custAccountService;
    @Autowired
    private ScfRequestSchemeService schemeService;
    @Autowired
    private ScfLoanService loanService;
    @Autowired
    private ScfServiceFeeService serviceFeeService;
    @Autowired
    private ScfOrderService orderService;
    @Autowired
    private ScfProductService productService;
    @Autowired
    private ScfOfferService offerService;
    
    @Reference(interfaceClass = ICustMechLawService.class)
    private ICustMechLawService mechLawService;
    @Reference(interfaceClass = ICustMechBaseService.class)
    private ICustMechBaseService mechBaseService;
    @Reference(interfaceClass = ICustMechBankAccountService.class)
    private ICustMechBankAccountService mechBankAccountService;
    @Autowired
    private ScfAgreementService agreementService;
    
    public ScfRequest saveStartRequest(ScfRequest anRequest){
        anRequest.setRequestFrom("1");
        anRequest = this.addRequest(anRequest);
        
        //从报价过来的要改变报价状态
        offerService.saveUpdateTradeStatus(anRequest.getOfferId(), "3");

        // 关联订单
        orderService.saveInfoRequestNo(anRequest.getRequestType(), anRequest.getRequestNo(), anRequest.getOrders());
        // 冻结订单
        orderService.forzenInfos(anRequest.getRequestNo(), null);
        
        return anRequest;
    }
    
    /**
     * 新增融资申请
     * 
     * @param anRequest
     * @return
     */
    public ScfRequest addRequest(ScfRequest anRequest) {
        BTAssert.notNull(anRequest, "新增融资申请失败-anRequest不能为空");
        anRequest.init();
        anRequest.setCustName(custAccountService.queryCustName(anRequest.getCustNo()));
        anRequest.setRequestDate(BetterDateUtils.getNumDate());
        this.insert(anRequest);
        return anRequest;
    }
    
    /**
     * 修改融资申请
     * 
     * @param anRequest
     * @return
     */
    public ScfRequest saveModifyRequest(ScfRequest anRequest, String anRequestNo) {
        BTAssert.notNull(anRequest, "修改融资申请失败-anRequest不能为空");

        if (Collections3.isEmpty(selectByProperty("requestNo", anRequestNo))) {
            throw new IllegalArgumentException("修改融资申请失败-找不到原数据");
        }

        anRequest.initModify();
        anRequest.setRequestNo(anRequestNo);
        this.updateByPrimaryKeySelective(anRequest);
        return anRequest;
    }
    
    public ScfRequest saveApprovedInfo(Map<String, Object> anMap){
        Object requestNo = anMap.get("requestNo");
        if(null == requestNo || BetterStringUtils.isBlank(requestNo.toString())){
            throw new IllegalArgumentException("修改融资申请失败-找不到原数据");
        }
        
        Object approvedRatio = anMap.get("approvedRatio");
        Object managementRatio = anMap.get("managementRatio");
        Object approvedBalance = anMap.get("approvedBalance");
        Object approvedPeriod = anMap.get("approvedPeriod");
        Object approvedPeriodUnit = anMap.get("approvedPeriodUnit");
        ScfRequest request = this.findRequestDetail(requestNo.toString());
        if(null != approvedRatio && BetterStringUtils.isNotBlank(approvedRatio.toString())){
            request.setApprovedRatio(new BigDecimal(approvedRatio.toString()));
        }
        if(null != managementRatio && BetterStringUtils.isNotBlank(managementRatio.toString())){
            request.setManagementRatio(new BigDecimal(managementRatio.toString()));
        }
        if(null != approvedBalance && BetterStringUtils.isNotBlank(approvedBalance.toString())){
            request.setApprovedBalance(new BigDecimal(approvedBalance.toString()));
        }
        if(null != approvedPeriod && BetterStringUtils.isNotBlank(approvedPeriod.toString())){
            request.setApprovedPeriod(Integer.parseInt(approvedPeriod.toString()));
        }
        if(null != approvedPeriodUnit && BetterStringUtils.isNotBlank(approvedPeriodUnit.toString())){
            request.setApprovedPeriodUnit(Integer.parseInt(approvedPeriodUnit.toString()));
        }
        
        return this.saveModifyRequest(request, requestNo.toString());
    }
    
    /**
     * 修改融资申请
     * 
     * @param anRequest
     * @return
     */
    public ScfRequest saveAutoModifyRequest(ScfRequest anRequest, String anRequestNo) {
        BTAssert.notNull(anRequest, "修改融资申请失败-anRequest不能为空");

        if (Collections3.isEmpty(selectByProperty("requestNo", anRequestNo))) {
            throw new IllegalArgumentException("修改融资申请失败-找不到原数据");
        }

        anRequest.initModify();
        anRequest.setRequestNo(anRequestNo);
        this.updateByPrimaryKeySelective(anRequest);
        return anRequest;
    }
    
    /**
     * 查询融资申请列表
     * 
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize
     * @return
     */
    public Page<ScfRequest> queryRequestList(Map<String, Object> anMap, int anFlag, int anPageNum, int anPageSize) {
        Page<ScfRequest> page = this.selectPropertyByPage(anMap, anPageNum, anPageSize, 1 == anFlag);
        for (ScfRequest scfRequest : page) {
            fillCustName(scfRequest);
        }
        return page;
    }
    
    /**
     * 查询融资申详情
     * 
     * @param anRequestNo
     * @return
     */
    public ScfRequest findRequestDetail(String anRequestNo) {
        BTAssert.notNull(anRequestNo, " 查询融资申详情失败-requestNo不能为空");
        ScfRequest request = this.selectByPrimaryKey(anRequestNo);
        if (null == request) {
            logger.debug("没查到相关数据！");
            return new ScfRequest();
        }

        this.fillCustName(request);

        // 设置还款计划
        request.setPayPlan(payPlanService.findPayPlanByRequest(anRequestNo));
        
        // 设置票据信息
        //request.setOrder(this.fillOrderInfo(request));
        return request;
    }

    private void fillCustName(ScfRequest request) {
        // 设置相关名称
        request.setCoreCustName(custAccountService.queryCustName(request.getCoreCustNo()));
        request.setFactorName(custAccountService.queryCustName(request.getFactorNo()));
        ScfProduct product = productService.findProductById(request.getProductId());
        if(null != product){
            request.setProductName(product.getProductName());  
        }
    }

    /**
     * 资方-出具融资方案（生成应收账款转让通知书）
     * 
     * @param anMap
     * @return
     */
    public ScfRequestScheme saveOfferScheme(ScfRequestScheme anScheme) {
        ScfRequest request = this.selectByPrimaryKey(anScheme.getRequestNo());
        // 1,订单，2:票据;3:应收款;4:经销商
        if (BetterStringUtils.equals("4", request.getRequestType()) == false) {
            // 发送出-应收帐款转让通知书
            ScfRequestNotice noticeRequest = this.getNotice(request);
            agreementService.transNotice(noticeRequest);

            // 添加转让明细（因为在转让申请时就添加了 转让明细，如果核心企业不同意，那明细需要删除，但目前没有做删除这步）
            agreementService.transCredit(this.getCreditList(request));
        }
        
        return schemeService.addScheme(anScheme);
    }

    /**
     * 融方-确认融资方案（确认融资金额，期限，利率，）
     * 
     * @param  anRequestNo
     * @param  anAduitStatus
     * @return
     */
    public ScfRequestScheme saveConfirmScheme(String anRequestNo, String anAduitStatus) {
        ScfRequestScheme scheme = schemeService.findSchemeDetail2(anRequestNo);
        BTAssert.notNull(scheme);
        
        // 修改申请表中的信息
        ScfRequest request = this.selectByPrimaryKey(anRequestNo);
        request.setApprovedPeriod(scheme.getApprovedPeriod());
        request.setApprovedPeriodUnit(scheme.getApprovedPeriodUnit());
        request.setManagementRatio(scheme.getApprovedManagementRatio());
        request.setServicefeeRatio(scheme.getServicefeeRatio());
        request.setApprovedBalance(scheme.getApprovedBalance());
        request.setConfirmBalance(scheme.getApprovedBalance());
        this.saveModifyRequest(request, anRequestNo);
        
        // 修改融资企业确认状态
        scheme.setCustAduit(anAduitStatus);
        return schemeService.saveModifyScheme(scheme);
    }

    /**
     * 资方-发起贸易背景确认（生成应收账款转确认意见书）
     * @param anMap
     * @return
     */
    public ScfRequest saveRequestTradingBackgrand(String anRequestNo) {
        ScfRequestScheme scheme = schemeService.findSchemeDetail2(anRequestNo);
        ScfRequest request = this.selectByPrimaryKey(anRequestNo);
        scheme.setCoreCustAduit("0");
        schemeService.saveModifyScheme(scheme);
        
        // 1,订单，2:票据;3:应收款;4:经销商
        if (BetterStringUtils.equals("4", request.getRequestType()) == true) {
            //签署-三方协议
            agreementService.transProtacal(this.getProtacal(request));
        }
        else {
            // 签署-应收账款转让意见确认书
            agreementService.transOpinion(this.getOption(request));
        }
        return request;
    }
    
    /**
     * 核心企业
     * 
     * @param anMap
     * @return
     */
    public ScfRequest confirmTradingBackgrand(String anRequestNo, String anAduitStatus) {
        ScfRequestScheme scheme = schemeService.findSchemeDetail2(anRequestNo);
        BTAssert.notNull(scheme);

        // 修改核心企业确认状态
        scheme.setCoreCustAduit("1");
        schemeService.saveModifyScheme(scheme);
        
        return findRequestDetail(anRequestNo);
    }

    /**
     * 资方-确认放款（收取放款手续费,计算利息，生成还款计划）
     * 
     * @param anMap
     * @return
     */
    public ScfRequest saveConfirmLoan(ScfLoan anLoan) {
        ScfRequest request = this.selectByPrimaryKey(anLoan.getRequestNo());
        BTAssert.notNull(request, "确认放款失败-找不到融资申请单");
        
         /*
        ScfRequestScheme scheme = schemeService.findSchemeDetail2(request.getRequestNo());
        BTAssert.notNull(scheme, "确认放款失败-找不到融资方案");*/

        // ---修申请表---------------------------------------------
        request.setActualDate(anLoan.getLoanDate());
        request.setEndDate(anLoan.getEndDate());
        request.setConfirmBalance(anLoan.getLoanBalance());
        this.updateByPrimaryKey(request);

        // ---保存还款计划------------------------------------------
        ScfPayPlan plan = createPayPlan(anLoan, request);

        // ---保存手续费----------------------------------------------
        BigDecimal servicefeeBalance = new BigDecimal(0);
        if (null != anLoan.getServicefeeBalance() || null != request.getServicefeeRatio()) {
            servicefeeBalance = saveServiceFee(anLoan, request);
        }

        // ---保存放款记录------------------------------------------
        anLoan.setInterestBalance(plan.getShouldInterestBalance());
        anLoan.setManagementBalance(plan.getShouldManagementBalance());
        anLoan.setCustNo(request.getCustNo());
        anLoan.setServicefeeBalance(servicefeeBalance);
        anLoan.setFactorNo(request.getFactorNo());
        loanService.addLoan(anLoan);

        return request;
    }

    private BigDecimal saveServiceFee(ScfLoan anLoan, ScfRequest anRequest) {
        ScfServiceFee serviceFee = new ScfServiceFee();
        serviceFee.setCustNo(anRequest.getCustNo());
        serviceFee.setFactorNo(anRequest.getFactorNo());
        serviceFee.setRequestNo(anLoan.getRequestNo());
        serviceFee.setPayDate(anLoan.getLoanDate());

        BigDecimal servicefeeBalance;
        if (null == anLoan.getServicefeeBalance()) {
            // 计算手续费(按千分之收)
            servicefeeBalance = anRequest.getApprovedBalance().multiply(anRequest.getServicefeeRatio()).divide(new BigDecimal(1000));
        }
        else {
            servicefeeBalance = anLoan.getServicefeeBalance();
        }
        serviceFee.setBalance(servicefeeBalance);
        serviceFeeService.addServiceFee(serviceFee);
        return servicefeeBalance;
    }

    private ScfPayPlan createPayPlan(ScfLoan anLoan, ScfRequest request) {
        ScfPayPlan plan = new ScfPayPlan();
        plan.setTerm(1);
        plan.setCustNo(request.getCustNo());
        plan.setCoreCustNo(request.getCoreCustNo());
        plan.setFactorNo(request.getFactorNo());
        plan.setRequestNo(anLoan.getRequestNo());
        plan.setStartDate(anLoan.getLoanDate());
        plan.setPlanDate(anLoan.getEndDate());
        plan.setRatio(request.getApprovedRatio());
        plan.setManagementRatio(request.getManagementRatio());
        
        //计算应还
        plan.setShouldPrincipalBalance(anLoan.getLoanBalance());
        if (null == anLoan.getInterestBalance()) {
            plan.setShouldInterestBalance(payPlanService.getFee(anLoan.getRequestNo(), request.getApprovedBalance(), 3));
        }
        else {
            plan.setShouldInterestBalance(anLoan.getInterestBalance());
        }
        
        if(null == anLoan.getManagementBalance()){
            plan.setShouldManagementBalance(payPlanService.getFee(anLoan.getRequestNo(), request.getApprovedBalance(), 1));
        }else{
            plan.setShouldManagementBalance(anLoan.getManagementBalance());
        }
        
        //未还
        plan.setSurplusPrincipalBalance(plan.getShouldPrincipalBalance());
        plan.setSurplusInterestBalance(plan.getShouldInterestBalance());
        plan.setSurplusManagementBalance(plan.getShouldManagementBalance());
        
        //计算应还未还总额
        BigDecimal totalBalance = plan.getShouldPrincipalBalance().add(plan.getShouldInterestBalance()).add(plan.getShouldManagementBalance());
        plan.setShouldTotalBalance(totalBalance);
        plan.setSurplusTotalBalance(totalBalance);
        payPlanService.addPayPlan(plan);
        return plan;
    }
    
    public List<ScfRequest> queryTodoList(){
        
        return null;
    }
    
    /**
     * 查询待批融资   <=150
     * 出具保理方案 - 110
     * 融资方确认方案 - 120
     * 发起融资背景确认 - 130
     * 核心企业确认背景 - 140
     * 放款确认 - 150
     * 完成融资 - 160
     * 放款完成 - 170
     */
    public Page<ScfRequest> queryPendingRequest(Map<String, Object> anMap, String anFlag, int anPageNum, int anPageSize) {
        // 放款前的状态
        anMap.put("LTEtradeStatus", "150");
        Page<ScfRequest> page = this.selectPropertyByPage(anMap, anPageNum, anPageSize, "1".equals(anFlag));
        for (ScfRequest request : page) {
            fillCustName(request);
            // 设置还款计划
            request.setPayPlan(payPlanService.findPayPlanByRequest(request.getRequestNo()));
        }
        return page;
    }

    /**
     * 查询还款融资      >150  <190
     * 出具保理方案 - 110
     * 融资方确认方案 - 120
     * 发起融资背景确认 - 130
     * 核心企业确认背景 - 140
     * 放款确认 - 150
     * 完成融资 - 160
     * 放款完成 - 170
     */
    public Page<ScfRequest> queryRepaymentRequest(Map<String, Object> anMap, String anFlag, int anPageNum, int anPageSize) {
        // 放款后结束前的状态
        anMap.put("GTtradeStatus", "150");
        anMap.put("LTtradeStatus", "190");
        Page<ScfRequest> page = this.selectPropertyByPage(anMap, anPageNum, anPageSize, "1".equals(anFlag));
        for (ScfRequest request : page) {
            fillCustName(request);
            // 设置还款计划
            request.setPayPlan(payPlanService.findPayPlanByRequest(request.getRequestNo()));
        }
        return page;
    }

    /**
     * 查询历史融资  >=190
     * 出具保理方案 - 110
     * 融资方确认方案 - 120
     * 发起融资背景确认 - 130
     * 核心企业确认背景 - 140
     * 放款确认 - 150
     * 完成融资 - 160
     * 放款完成 - 170
     */
    public Page<ScfRequest> queryCompletedRequest(Map<String, Object> anMap, String anFlag, int anPageNum, int anPageSize) {
        // 放款后结束前的状态
        anMap.put("GTEtradeStatus", "190");
        Page<ScfRequest> page = this.selectPropertyByPage(anMap, anPageNum, anPageSize, "1".equals(anFlag));
        for (ScfRequest scfRequest : page) {
            fillCustName(scfRequest);
            // 设置还款计划
            scfRequest.setPayPlan(payPlanService.findPayPlanByRequest(scfRequest.getRequestNo()));
        }
        return page;
    }

    /**
     * 核心企业融资查询
     * 出具保理方案 - 110
     * 融资方确认方案 - 120
     * 发起融资背景确认 - 130
     * 核心企业确认背景 - 140
     * 放款确认 - 150
     * 完成融资 - 160
     * 放款完成 - 170
     */
    public Page<ScfRequest> queryCoreEnterpriseRequest(Map<String, Object> anMap, String anRequestType, String anFlag, int anPageNum, int anPageSize) {
        switch (anRequestType) {
        case "1":
            // 未还款
            return this.queryPendingRequest(anMap, anFlag, anPageNum, anPageSize);
        case "2":
            // 还款中
            return this.queryRepaymentRequest(anMap, anFlag, anPageNum, anPageSize);
        case "3":
            // 已还款
            return this.queryCompletedRequest(anMap, anFlag, anPageNum, anPageSize);
        default:
            return (Page<ScfRequest>) Collections3.union(queryPendingRequest(anMap, anFlag, anPageNum, anPageSize), Collections3.union(queryRepaymentRequest(anMap, anFlag, anPageNum, anPageSize),queryCompletedRequest(anMap, anFlag, anPageNum, anPageSize)));
        }
    }
    
    public ScfRequest approveRequest(Map<String, Object> anMap) {
        return null;
    }

    public List<ScfRequestCredit> getCreditList(ScfRequest anRequest){
        List<CustAgreement> agreementList = (List)orderService.findRelationInfo(anRequest.getRequestNo(), ScfOrderRelationType.AGGREMENT);
        CustAgreement agreement = Collections3.getFirst(agreementList);
        BTAssert.notNull(agreement, "发起融资背景确认失败：没有找到贸易合同！");
        
        String type = anRequest.getRequestType();
        List<ScfRequestCredit> creditList = new ArrayList<ScfRequestCredit>();
        
        if(BetterStringUtils.equals("1", type) || BetterStringUtils.equals("4", type)){
            List<ScfOrder> list = (List)orderService.findInfoListByRequest(anRequest.getRequestNo(), "1");
            for (ScfOrder order : list) {
                creditList = setInvoice(anRequest, order.getInvoiceList(), order.getBalance(), order.getOrderNo(),agreement);
            }
        }else if(BetterStringUtils.equals("2", type)){
            List<ScfReceivable> list = (List)orderService.findInfoListByRequest(anRequest.getRequestNo(), "2");
            for (ScfReceivable receivable : list) {
                creditList = setInvoice(anRequest, receivable.getInvoiceList(), receivable.getBalance(), receivable.getReceivableNo() ,agreement);
            }
        }else if(BetterStringUtils.equals("3", type)){
            List<ScfAcceptBill> list = (List)orderService.findInfoListByRequest(anRequest.getRequestNo(), "3");
            for (ScfAcceptBill bill : list) {
                creditList = setInvoice(anRequest, bill.getInvoiceList(), bill.getBalance(), bill.getBtBillNo(),agreement);
            }
        }
        return creditList;
    }
    
    /**
     * 设置转主明细相关信息
     * @param request
     * @param anInvoiceList
     * @param anBalance
     * @param anObjectNo
     * @param anAgreement
     * @return
     */
    private List<ScfRequestCredit> setInvoice(ScfRequest request, List<ScfInvoice> anInvoiceList, BigDecimal anBalance, String anObjectNo, CustAgreement anAgreement){
        List<ScfRequestCredit> creditList = new ArrayList<ScfRequestCredit>();
        for (ScfInvoice invoice : anInvoiceList) {
            ScfRequestCredit credit = new ScfRequestCredit();
            credit.setTransNo(anObjectNo);
            credit.setRequestNo(request.getRequestNo());
            credit.setBalance(anBalance);
            credit.setInvoiceNo(invoice.getInvoiceNo());
            credit.setInvoiceBalance(invoice.getBalance());
            credit.setEndDate(invoice.getInvoiceDate());
            creditList.add(credit);
        }
        return creditList;
    }
    
    /**
     * 设置转让通知书相关信息
     * @param anRequestNo
     * @param anRequest
     * @return
     */
    public ScfRequestNotice getNotice(ScfRequest anRequest) {
        //TODO 合同名称、 银行账号， 保理公司详细地址
        CustMechBankAccount bankAccount = mechBankAccountService.findDefaultBankAccount(anRequest.getFactorNo());
        
        String noticeNo = BetterDateUtils.getDate("yyyyMMdd") + anRequest.getRequestNo();
        ScfRequestNotice noticeRequest = new ScfRequestNotice();
        noticeRequest.setRequestNo(anRequest.getRequestNo());
        noticeRequest.setAgreeName(anRequest.getCustName() + "应收账款转让申请书");
        noticeRequest.setNoticeNo(noticeNo);
        noticeRequest.setBuyer(anRequest.getCoreCustName());
        noticeRequest.setFactorRequestNo(noticeNo);
        noticeRequest.setBankAccount(bankAccount.getBankAcco());
        CustMechBase custBase = mechBaseService.findBaseInfo(anRequest.getCustNo());
        noticeRequest.setFactorAddr(custBase.getAddress());
        noticeRequest.setFactorPost(custBase.getZipCode());
        
        CustMechLaw custMechLaw = mechLawService.findLawInfo(anRequest.getCustNo());
        noticeRequest.setFactorLinkMan(custMechLaw.getName());
        return noticeRequest;
    }

    /**
     * TODO 设置三方协议相关信息
     * @return
     */
    public ScfRequestProtacal getProtacal(ScfRequest anRequest) {
        //负责人默认为法人
        String firstJob = "法人";
        
        ScfRequestProtacal protacal = new ScfRequestProtacal();
        CustMechLaw factorLow = mechLawService.findLawInfo(anRequest.getFactorNo());
        CustMechLaw buyLow = mechLawService.findLawInfo(anRequest.getCoreCustNo());
        CustMechLaw sellerLow = mechLawService.findLawInfo(anRequest.getCustNo());
        
        CustMechBase factorBase = mechBaseService.findBaseInfo(anRequest.getFactorNo());
        CustMechBase buyBase = mechBaseService.findBaseInfo(anRequest.getCoreCustNo());
        CustMechBase sellerBase = mechBaseService.findBaseInfo(anRequest.getCustNo());
        protacal.setFirstAddress(factorBase.getAddress());
        protacal.setFirstFax(factorBase.getFax());
        protacal.setFirstName(anRequest.getFactorName());
        protacal.setFirstJob(firstJob);
        protacal.setFirstPhone(factorBase.getMobile());
        protacal.setFirstLegal(factorLow.getCustName());
        
        protacal.setSecondAddress(buyBase.getAddress());
        protacal.setSecondFax(buyBase.getFax());
        protacal.setSecondName(anRequest.getCoreCustName());
        protacal.setSecondJob(firstJob);
        protacal.setSecondPhone(buyBase.getMobile());
        protacal.setSecondLegal(buyLow.getCustName());
        
        protacal.setThreeAddress(sellerBase.getAddress());
        protacal.setThreeFax(sellerBase.getFax());
        protacal.setThreeName(anRequest.getCustName());
        protacal.setThreeJob(firstJob);
        protacal.setThreePhone(sellerBase.getMobile());
        protacal.setThreeLegal(sellerLow.getCustName());
        return protacal;
    }

    /**
     * 设置转让意见确认书相关信息
     * @param anRequest
     * @return
     */
    public ScfRequestOpinion getOption(ScfRequest anRequest) {
        String noticeNo = BetterDateUtils.getNumDate() + anRequest.getRequestNo();
        ScfRequestOpinion opinion = new ScfRequestOpinion();
        opinion.setRequestNo(anRequest.getRequestNo());
        opinion.setFactorRequestNo(noticeNo);
        opinion.setConfirmNo(noticeNo);
        opinion.setSupplier(anRequest.getCustName());
        return opinion;
    }

    public void getDefaultNode(List<CustFlowNodeData> list) {
        CustFlowNodeData node = new CustFlowNodeData();
        node = new CustFlowNodeData();
        node.setNodeCustomName(RequestTradeStatus.OVERDUE.getName());
        node.setSysNodeName(RequestTradeStatus.OVERDUE.getName());
        node.setSysNodeId(new Long(RequestTradeStatus.OVERDUE.getCode()));
        node.setId(new Long(RequestTradeStatus.OVERDUE.getCode()));
        list.add(node);
        
        node = new CustFlowNodeData();
        node.setNodeCustomName(RequestTradeStatus.EXTENSION.getName());
        node.setSysNodeName(RequestTradeStatus.EXTENSION.getName());
        node.setSysNodeId(new Long(RequestTradeStatus.EXTENSION.getCode()));
        node.setId(new Long(RequestTradeStatus.EXTENSION.getCode()));
        list.add(node);
        
        node = new CustFlowNodeData();
        node.setNodeCustomName(RequestTradeStatus.PAYFINSH.getName());
        node.setSysNodeName(RequestTradeStatus.PAYFINSH.getName());
        node.setSysNodeId(new Long(RequestTradeStatus.PAYFINSH.getCode()));
        node.setId(new Long(RequestTradeStatus.PAYFINSH.getCode()));
        list.add(node);
    }
    
    /**
     * 填充订单信息
     * @param anScfRequest
     */
    public Object fillOrderInfo(ScfRequest anScfRequest) {
        //经销商融资不用设置
        if(BetterStringUtils.equals("4", anScfRequest.getRequestType())){
            return null;
        }
        
        if(BetterStringUtils.equals("1", anScfRequest.getRequestType())){
            List<ScfOrder> orderList = (List)orderService.findInfoListByRequest(anScfRequest.getRequestNo(), anScfRequest.getRequestType());
            return Collections3.getFirst(orderList);
        }
        else if(BetterStringUtils.equals("2", anScfRequest.getRequestType())){
            List<ScfAcceptBill> orderList = (List)orderService.findInfoListByRequest(anScfRequest.getRequestNo(), anScfRequest.getRequestType());
            return Collections3.getFirst(orderList);
        }else{
            List<ScfReceivable> orderList = (List)orderService.findInfoListByRequest(anScfRequest.getRequestNo(), anScfRequest.getRequestType());
            return Collections3.getFirst(orderList);
        }
       
    }

    /**
     * 查询融资列表，无分页
     */
    public List<ScfRequest> findRequestList(Map<String, Object> anMap) {
        List<ScfRequest> requestList = this.selectByProperty(anMap);
        for(ScfRequest request: requestList) {
            fillCustName(request);
        }
        return requestList;
    }
}
