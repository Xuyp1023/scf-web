package com.betterjr.modules.loan;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.betterjr.common.exception.BytterException;
import com.betterjr.common.utils.Collections3;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping(value = "/Scf/Request")
public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @Reference(interfaceClass = IScfRequestService.class)
    private IScfRequestService scfRequestService;

    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    public @ResponseBody String addRequest(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("添加融资申请，入参:" + map.toString());

        try {
            return scfRequestService.webAddRequest(map);
        }
        catch (Exception ex) {
            logger.error("添加融资申请:", ex);
            return AjaxObject.newError(ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/saveModifyRequest", method = RequestMethod.POST)
    public @ResponseBody String saveModifyRequest(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("修改融资申请，入参:" + map.toString());

        try {
            return scfRequestService.webSaveModifyRequest(map, requestNo);
        }
        catch (Exception ex) {
            logger.error("修改融资申请:", ex);
            return AjaxObject.newError("saveModifyRequest service failed").toJson();
        }

    }

    @RequestMapping(value = "/queryRequestList", method = RequestMethod.POST)
    public @ResponseBody String queryRequestList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        String[] queryTerm = new String[] { "custNo", "coreCustNo", "factorNo", "GTEactualDate", "LTEactualDate" };
        map = Collections3.filterMap(map, queryTerm);
        logger.info("分页查询融资申请，入参:" + map.toString());

        try {
            return scfRequestService.webQueryRequestList(map, flag, pageNum, pageSize);
        }
        catch (RpcException btEx) {
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("queryRequestList service failed").toJson();
        }
        catch (Exception ex) {
            logger.error("分页查询融资申请，", ex);
            return AjaxObject.newError("queryRequestList service failed").toJson();
        }

    }

    @RequestMapping(value = "/findRequestDetail", method = RequestMethod.POST)
    public @ResponseBody String findRequestByNo(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询融资申请详情，入参:" + map.toString());

        try {
            return scfRequestService.webFindRequestDetail(map, requestNo);
        }
        catch (Exception ex) {
            logger.error("查询融资申请详情，", ex);
            return AjaxObject.newError("findRequestDetail service failed").toJson();
        }

    }

    @RequestMapping(value = "/offerScheme", method = RequestMethod.POST)
    public @ResponseBody String offerScheme(HttpServletRequest request, String approvalResult, String returnNode, String description) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("保存融资方案，入参:" + map.toString());

        try {
            return scfRequestService.webOfferScheme(map, approvalResult, returnNode, description);
        }
        catch (Exception ex) {
            logger.error("保存融资方案，", ex);
            return AjaxObject.newError("offerScheme service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/saveModifyScheme", method = RequestMethod.POST)
    public @ResponseBody String saveModifyScheme(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("修改融资方案，入参:" + map);

        try {
            return scfRequestService.webSaveModifyScheme(map);
        }
        catch (Exception ex) {
            logger.error("修改融资方案：", ex);
            return AjaxObject.newError("saveModifyScheme service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/querySchemeList", method = RequestMethod.POST)
    public @ResponseBody String querySchemeList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询融资方案申请，入参:" + map);

        try {
            return scfRequestService.webQuerySchemeList(map, flag, pageNum, pageSize);
        }
        catch (RpcException btEx) {
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("querySchemeList service failed:" + btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            logger.error("分页查询融资方案申请：", ex);
            return AjaxObject.newError("querySchemeList service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/findSchemeDetail", method = RequestMethod.POST)
    public @ResponseBody String findSchemeDetail(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询融资方案详情，入参:" + map);

        try {
            return scfRequestService.webFindSchemeDetail(requestNo);
        }
        catch (Exception ex) {
            logger.error("查询融资方案详情：", ex);
            return AjaxObject.newError("findSchemeDetail service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/confirmScheme", method = RequestMethod.POST)
    public @ResponseBody String findApprovedDetail(HttpServletRequest request, String requestNo, String approvalResult, String smsCode) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("确认融资方案，入参:" + map);

        try {
            return scfRequestService.webConfirmScheme(requestNo, approvalResult, smsCode);
        }
        catch (Exception ex) {
            logger.error("确认融资方案：", ex);
            return AjaxObject.newError("confirmScheme service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/requestTradingBackgrand", method = RequestMethod.POST)
    public @ResponseBody String requestTradingBackgrand(HttpServletRequest request, String requestNo, String approvalResult, String returnNode,
            String description, String smsCode) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("发起融资背景确认，入参:" + map);

        try {
            return scfRequestService.webRequestTradingBackgrand(requestNo, approvalResult, returnNode, description, smsCode);
        }
        catch (Exception ex) {
            logger.error("发起融资背景确认：", ex);
            return AjaxObject.newError("requestTradingBackgrand service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/confirmTradingBackgrand", method = RequestMethod.POST)
    public @ResponseBody String confirmTradingBackgrand(HttpServletRequest request, String requestNo, String approvalResult, String smsCode) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("确认融资贸易背景，入参:" + map);

        try {
            return scfRequestService.webConfirmTradingBackgrand(requestNo, approvalResult, smsCode);
        }
        catch (Exception ex) {
            logger.error("确认融资贸易背景：", ex);
            return AjaxObject.newError("confirmTradingBackgrand service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/approveRequest", method = RequestMethod.POST)
    public @ResponseBody String approveRequest(HttpServletRequest request, String requestNo, String approvalResult, String returnNode,
            String description) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("审批，入参:" + map);

        try {
            return scfRequestService.webApproveRequest(requestNo, approvalResult, returnNode, description);
        }
        catch (Exception ex) {
            logger.error("审批：", ex);
            return AjaxObject.newError("approveRequest service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/confirmLoan", method = RequestMethod.POST)
    public @ResponseBody String confirmLoan(HttpServletRequest request, String approvalResult, String returnNode, String description) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("确认放款，入参:" + map);

        try {
            return scfRequestService.webConfirmLoan(map, approvalResult, returnNode, description);
        }
        catch (Exception ex) {
            logger.error("确认放款：", ex);
            return AjaxObject.newError("confirmLoan service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/calculatServiceFee", method = RequestMethod.POST)
    public @ResponseBody String calculatServiceFee(HttpServletRequest request, String requestNo, BigDecimal loanBalance) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("计算手续费，入参:" + map);

        try {
            return scfRequestService.webGetServiecFee(requestNo, loanBalance);
        }
        catch (Exception ex) {
            logger.error("计算手续费：", ex);
            return AjaxObject.newError("calculatServiceFee service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/calculatEndDate", method = RequestMethod.POST)
    public @ResponseBody String calculatEndDate(HttpServletRequest request, String requestNo, String loanDate) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("计算结束日期，入参:" + map);

        try {
            return scfRequestService.webGetEndDate(requestNo, loanDate);
        }
        catch (Exception ex) {
            logger.error("计算结束日期：", ex);
            return AjaxObject.newError("calculatEndDate service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/calculatInsterest", method = RequestMethod.POST)
    public @ResponseBody String calculatInsterest(HttpServletRequest request, String requestNo, BigDecimal loanBalance, String loanDate, String endDate) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("计算利息，入参:" + map);

        try {
            return scfRequestService.webGetInsterest(requestNo, loanBalance, loanDate, endDate);
        }
        catch (Exception ex) {
            logger.error("计算利息：", ex);
            return AjaxObject.newError("calculatInsterest service failed:" + ex.getMessage()).toJson();
        }

    }

    @RequestMapping(value = "/queryPendingRequest", method = RequestMethod.POST)
    public @ResponseBody String queryPendingRequest(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        try {
            return scfRequestService.webQueryPendingRequest(anMap, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("查询待批融资失败", e);
            return AjaxObject.newError("查询待批融资失败:" + e.getMessage()).toJson();
        }
    }

    @RequestMapping(value = "/queryRepaymentRequest", method = RequestMethod.POST)
    public @ResponseBody String queryRepaymentRequest(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        try {
            return scfRequestService.webQueryRepaymentRequest(anMap, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("查询还款融资失败", e);
            return AjaxObject.newError("查询还款融资失败:" + e.getMessage()).toJson();
        }
    }

    @RequestMapping(value = "/queryCompletedRequest", method = RequestMethod.POST)
    public @ResponseBody String queryCompletedRequest(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        try {
            return scfRequestService.webQueryCompletedRequest(anMap, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("查询历史融资失败", e);
            return AjaxObject.newError("查询历史融资失败:" + e.getMessage()).toJson();
        }
    }

    @RequestMapping(value = "/queryCoreEnterpriseRequest", method = RequestMethod.POST)
    public @ResponseBody String queryCoreEnterpriseRequest(HttpServletRequest request, String requestType, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        try {
            return scfRequestService.webQueryCoreEnterpriseRequest(anMap, requestType, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("查询融资失败", e);
            return AjaxObject.newError("查询融资失败").toJson();
        }
    }

    @RequestMapping(value = "/queryWorkTask", method = RequestMethod.POST)
    public @ResponseBody String queryWorkTask(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        try {
            return scfRequestService.webQueryWorkTask(map, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("用户任务列表查询失败", e);
            return AjaxObject.newError("用户任务列表查询失败:" + e.getMessage()).toJson();
        }
    }
    
    @RequestMapping(value = "/queryTradeStatus", method = RequestMethod.POST)
    public @ResponseBody String queryTradeStatus(HttpServletRequest request) {
        try {
            return scfRequestService.webQueryTradeStatus();
        }
        catch (Exception e) {
            logger.error("状态列表查询失败", e);
            return AjaxObject.newError("状态列表").toJson();
        }
    }
    
    @RequestMapping(value = "/findPayPlan", method = RequestMethod.POST)
    public @ResponseBody String findPayPlan(HttpServletRequest request, Long id) {
        try {
            return scfRequestService.webFindPayPlan(id);
        }
        catch (Exception e) {
            logger.error("还款计划详情查询失败", e);
            return AjaxObject.newError("还款计划详情:" + e.getMessage()).toJson();
        }
    }
    
    /**
     * 
     */
    @RequestMapping(value = "/querySupplierRequestByCore", method = RequestMethod.POST)
    public @ResponseBody String querySupplierRequestByCore(HttpServletRequest request,String businStatus, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        try {
            return scfRequestService.webQuerySupplierRequestByCore(anMap, businStatus, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("供应商融资查询失败", e);
            return AjaxObject.newError("供应商融资查询失败:" + e.getMessage()).toJson();
        }
    }
    
    @RequestMapping(value = "/querySellerRequestByCore", method = RequestMethod.POST)
    public @ResponseBody String querySellerRequestByCore(HttpServletRequest request,String businStatus, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        try {
            return scfRequestService.webQuerySellerRequestByCore(anMap, businStatus, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("经销商融资查询失败", e);
            return AjaxObject.newError("经销商融资融资查询成功:" + e.getMessage()).toJson();
        }
    }
    
}
