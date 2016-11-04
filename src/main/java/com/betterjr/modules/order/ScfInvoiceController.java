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
import com.betterjr.common.web.ControllerExceptionHandler;
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping("/Scf/Invoice")
public class ScfInvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(ScfInvoiceController.class);
    
    @Reference(interfaceClass = IScfInvoiceService.class)
    private IScfInvoiceService scfInvoiceService;
    
    @RequestMapping(value = "/addInvoice", method = RequestMethod.POST)
    public @ResponseBody String addInvoice(HttpServletRequest request,String invoiceItemIds, String fileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票信息录入,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webAddInvoice(anMap, invoiceItemIds, fileList);
            }
        }, "发票信息录入失败", logger);
                
    }
    
    @RequestMapping(value = "/queryInvoice", method = RequestMethod.POST)
    public @ResponseBody String queryInvoice(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询订单发票信息,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webQueryInvoiceList(anMap, flag, pageNum, pageSize);
            }
        }, "查询订单发票信息失败", logger);

    }
    
    @RequestMapping(value = "/saveModifyInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveModifyInvoice(HttpServletRequest request,String invoiceItemIds, String fileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票信息编辑,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webSaveModifyInvoice(anMap, invoiceItemIds, fileList);
            }
        }, "发票信息录入编辑", logger);
                
    }
    
    @RequestMapping(value = "/addInvoiceItem", method = RequestMethod.POST)
    public @ResponseBody String addInvoiceItem(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票信息详情录入,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webAddInvoiceItem(anMap);
            }
        }, "发票信息详情录入失败", logger);
                
    }

    @RequestMapping(value = "/saveDeleteInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteInvoice(Long id) {
        logger.info("发票信息删除,入参:id=" + id);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webSaveDeleteInvoice(id);
            }
        }, "发票信息删除失败", logger);
        
    }
    
    @RequestMapping(value = "/queryIncompletedInvoice", method = RequestMethod.POST)
    public @ResponseBody String queryIncompletedInvoice(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票信息查询,入参" + anMap);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webQueryIncompletedInvoice(anMap, flag, pageNum, pageSize);
            }
        }, "发票信息查询失败", logger);
    }
}
