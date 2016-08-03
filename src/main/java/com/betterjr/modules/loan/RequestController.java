package com.betterjr.modules.loan;

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
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.loan.IScfRequestService;

@Controller
@RequestMapping(value = "/Scf/Request")
public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    
    @Reference(interfaceClass = IScfRequestService.class)
    private IScfRequestService scfRequestService;
    
    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    public @ResponseBody String addRequest(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("添加融资申请，入参:"+ map.toString());
        
        try {
            return scfRequestService.webAddRequest(map);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("addRequest service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/saveModifyRequest", method = RequestMethod.POST)
    public @ResponseBody String saveModifyRequest(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("修改融资申请，入参:"+ map.toString());
        
        try {
            return scfRequestService.webSaveModifyRequest(map, requestNo);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("saveModifyRequest service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/queryRequestList", method = RequestMethod.POST)
    public @ResponseBody String queryRequestList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询融资申请，入参:"+ map.toString());
        
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
            ex.printStackTrace();
            return AjaxObject.newError("queryRequestList service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/findRequestDetail", method = RequestMethod.POST)
    public @ResponseBody String findRequestByNo(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询融资申请详情，入参:"+ map.toString());
        
        try {
            return scfRequestService.webFindRequestDetail(map, requestNo);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("findRequestDetail service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/offerScheme", method = RequestMethod.POST)
    public @ResponseBody String offerScheme(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询融资申请详情，入参:"+ map.toString());
        
        try {
            return scfRequestService.webOfferScheme(map);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("offerScheme service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/saveModifyApproved", method = RequestMethod.POST)
    public @ResponseBody String saveModifyApproved(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询融资申请详情，入参:"+ map);
        
        try {
            return scfRequestService.webSaveModifyApproved(map);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("saveModifyApproved service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/queryApprovedList", method = RequestMethod.POST)
    public @ResponseBody String queryApprovedList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询融资申请，入参:"+ map);
        
        try {
            return scfRequestService.webQueryApprovedList(map, flag, pageNum, pageSize);
        }
        catch (RpcException btEx) {
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("queryApprovedList service failed").toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("queryApprovedList service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/findApprovedDetail", method = RequestMethod.POST)
    public @ResponseBody String findApprovedDetail(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询融资申请详情，入参:"+ map);
        
        try {
            return scfRequestService.webFindApprovedDetail(requestNo);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("findApprovedDetail service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/confirmScheme", method = RequestMethod.POST)
    public @ResponseBody String findApprovedDetail(HttpServletRequest request, String requestNo, String businStatus) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("确认融资方案，入参:"+ map);
        
        try {
            return scfRequestService.webConfirmScheme(requestNo, businStatus);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("findApprovedDetail service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/requestTradingBackgrand", method = RequestMethod.POST)
    public @ResponseBody String requestTradingBackgrand(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("发起融资背景确认，入参:"+ map);
        
        try {
            return scfRequestService.webRequestTradingBackgrand(requestNo);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("requestTradingBackgrand service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/confirmTradingBackgrand", method = RequestMethod.POST)
    public @ResponseBody String confirmTradingBackgrand(HttpServletRequest request, String requestNo, String businStatus) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("确认融资贸易背景，入参:"+ map);
        
        try {
            return scfRequestService.webConfirmTradingBackgrand(requestNo, businStatus);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("confirmTradingBackgrand service failed").toJson();
        }
        
    }

   
}
