package com.betterjr.modules.order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping("/Scf/Invoice")
public class ScfInvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(ScfInvoiceController.class);
    
    @Reference(interfaceClass = IScfInvoiceService.class)
    private IScfInvoiceService scfInvoiceService;
    
    @RequestMapping(value = "/addInvoice", method = RequestMethod.POST)
    public @ResponseBody String addInvoice(HttpServletRequest request, String fileList) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票信息录入,入参:" + anMap.toString());
        try{
            return scfInvoiceService.webAddInvoice(anMap, fileList);
        }
        catch(Exception e) {
            logger.error("发票信息录入失败", e);
            return AjaxObject.newError("发票信息录入失败").toJson();
        }
                
    }
    
    @RequestMapping(value = "/queryInvoice", method = RequestMethod.POST)
    public @ResponseBody String queryInvoice(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询订单发票信息,入参:" + anMap.toString());
        try{
            return scfInvoiceService.webQueryInvoiceList(anMap, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("查询订单发票信息失败", e);
            return AjaxObject.newError("查询订单发票信息失败").toJson();
        }
    }
    
}
