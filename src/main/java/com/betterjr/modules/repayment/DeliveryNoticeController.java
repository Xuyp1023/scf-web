package com.betterjr.modules.repayment;

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
import com.betterjr.modules.repayment.IScfDeliveryNoticeService;

@Controller
@RequestMapping(value = "/Scf/Delivery")
public class DeliveryNoticeController {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryNoticeController.class);
    
    @Reference(interfaceClass = IScfDeliveryNoticeService.class)
    private IScfDeliveryNoticeService scfDeliveryNoticeService;
    
    @RequestMapping(value = "/queryDeliveryNoticeList", method = RequestMethod.POST)
    public @ResponseBody String queryDeliveryNoticeList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("通知单查询，入参:"+ map.toString());
        
        try {
            return scfDeliveryNoticeService.webQueryDeliveryNoticeList(map, flag, pageNum, pageSize);
        }
        catch (RpcException btEx) {
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("webQueryDeliveryNoticeList service failed").toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("webQueryDeliveryNoticeList service failed").toJson();
        }

    }

    @RequestMapping(value = "/addDeliveryNotice", method = RequestMethod.POST)
    public @ResponseBody String addDeliveryNotice(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("添加发货通知单，入参:"+ map.toString());
        
        try {
            return scfDeliveryNoticeService.webAddDeliveryNotice(map);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("addDeliveryNotice service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/saveModifyEnquiry", method = RequestMethod.POST)
    public @ResponseBody String saveModifyEnquiry(HttpServletRequest request, Long id) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("修改发货通知单，入参:"+ map.toString());
        
        try {
            return scfDeliveryNoticeService.webSaveModifyDeliveryNotice(map, id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("webSaveModifyDeliveryNotice service failed").toJson();
        }

    }

   
}
