package com.betterjr.modules.enquiry;

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

@Controller
@RequestMapping(value = "/scf/Enquiry")
public class EnquiryController {
    private static final Logger logger = LoggerFactory.getLogger(EnquiryController.class);
    
    @Reference(interfaceClass = IScfEnquiryService.class)
    private IScfEnquiryService iEnquiryService;
    
    @RequestMapping(value = "/queryEnquiryList", method = RequestMethod.POST)
    public @ResponseBody String queryEnquiryList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询询价，入参:"+ map.toString());
        
        try {
            return iEnquiryService.webQueryEnquiryList(map, flag, pageNum, pageSize);
        }
        catch (RpcException btEx) {
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("queryEnquiryList service failed").toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("queryEnquiryList service failed").toJson();
        }

    }

    @RequestMapping(value = "/addEnquiry", method = RequestMethod.POST)
    public @ResponseBody String addEnquiry(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("保存询价，入参:"+ map.toString());
        
        try {
            return iEnquiryService.webAddEnquiry(map);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("addEnquiry service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/saveModifyEnquiry", method = RequestMethod.POST)
    public @ResponseBody String saveModifyEnquiry(HttpServletRequest request, Long id) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("修改询价，入参:"+ map.toString());
        
        try {
            return iEnquiryService.webSaveModifyEnquiry(map, id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("saveModifyEnquiry service failed").toJson();
        }

    }

    @RequestMapping(value = "/findEnquiryDetail", method = RequestMethod.POST)
    public @ResponseBody String findEnquiryDetail(HttpServletRequest request, Long id) {
        logger.info("查看询价详情，入参:"+ id);
        
        try {
            return iEnquiryService.webFindEnquiryDetail(id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("findEnquiryDetail service failed").toJson();
        }
    }
    
    @RequestMapping(value = "/queryOfferList", method = RequestMethod.POST)
    public @ResponseBody String queryOfferList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询报价，入参:"+ map.toString());
        
        try {
            return iEnquiryService.webQueryOfferList(map, flag, pageNum, pageSize);
        }
        catch (RpcException btEx) {
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("queryOfferList service failed").toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("queryOfferList service failed").toJson();
        }

    }

    @RequestMapping(value = "/addOffer", method = RequestMethod.POST)
    public @ResponseBody String addOffer(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("保存报价，入参:"+ map.toString());
        
        try {
            return iEnquiryService.webAddOffer(map);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("saveOffer service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/saveModifyOffer", method = RequestMethod.POST)
    public @ResponseBody String webSaveModifyOffer(HttpServletRequest request, Long id) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("修改报价，入参:"+ map.toString());
        
        try {
            return iEnquiryService.webSaveModifyOffer(map, id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("updateOffer service failed").toJson();
        }

    }

    @RequestMapping(value = "/findOfferDetail", method = RequestMethod.POST)
    public @ResponseBody String findOfferDetail(HttpServletRequest request, Long id) {
        logger.info("查看报价详情，入参:"+ id);
        
        try {
            return iEnquiryService.webFindOfferDetail(id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("findOfferDetail service failed").toJson();
        }
    }

}
