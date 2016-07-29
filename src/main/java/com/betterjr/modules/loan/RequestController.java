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
    
    @RequestMapping(value = "/queryRequestList", method = RequestMethod.POST)
    public @ResponseBody String queryRequestList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("通知单查询，入参:"+ map.toString());
        
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

    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    public @ResponseBody String addRequest(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("添加发货通知单，入参:"+ map.toString());
        
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
        logger.info("修改发货通知单，入参:"+ map.toString());
        
        try {
            return scfRequestService.webSaveModifyRequest(map, requestNo);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("saveModifyRequest service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/findRequestDetail", method = RequestMethod.POST)
    public @ResponseBody String findRequestByNo(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("修改发货通知单，入参:"+ map.toString());
        
        try {
            return scfRequestService.webFindRequestDetail(map, requestNo);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("saveModifyRequest service failed").toJson();
        }
        
    }

   
}
