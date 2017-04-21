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
import com.betterjr.modules.vo.InvoiceVO;

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
    
    /**
     * 发票新增
     * @param request
     * @param fileList
     * @param confirmFlag
     * @return
     */
    @RequestMapping(value = "/addInvoiceDO", method = RequestMethod.POST)
    public @ResponseBody String addInvoiceDO(HttpServletRequest request, String fileList, boolean confirmFlag, InvoiceVO invoice) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票信息新增,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webAddInvoiceDO(anMap, fileList,confirmFlag,invoice.getInvoiceItemList());
            }
        }, "发票信息新增失败", logger);
    }
    
    /**
     * 编辑保存发票
     * @param request
     * @param fileList
     * @param confirmFlag
     * @return
     */
    @RequestMapping(value = "/modifyInvoiceDO", method = RequestMethod.POST)
    public @ResponseBody String modifyInvoiceDO(HttpServletRequest request, String fileList,boolean confirmFlag,InvoiceVO invoice) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票信息修改,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webSaveModifyInvoiceDO(anMap,fileList, confirmFlag,invoice.getInvoiceItemList());
            }
        }, "发票信息编辑失败", logger);
    }
    
    @RequestMapping(value = "/saveAnnulInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveAnnulInvoice(String refNo,String version) {
        logger.info("发票信息作废,入参：refNo=" + refNo+"  version:"+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webSaveAnnulInvoice(refNo,version);
            }
        }, "发票信息废止失败", logger);
    }
    
    @RequestMapping(value = "/saveAuditInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveAuditInvoice(String refNo,String version) {
        logger.info("发票审核,入参：refNo=" + refNo +" : version="+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webSaveAuditInvoiceByRefNoVersion(refNo, version);
            }
        }, "发票审核成功！", logger);
    }
    
    /**
     * 
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @param isAudit  true 审核条件的发票查询列表            false  新增界面的发票查询列表
     * @return
     */
    @RequestMapping(value = "/queryIneffectiveInvoice", method = RequestMethod.POST)
    public @ResponseBody String queryIneffectiveInvoice(HttpServletRequest request, String flag, int pageNum, int pageSize,boolean isAudit) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票未生效信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webQueryIneffectiveInvoice(anMap, flag, pageNum, pageSize,isAudit);
            }
        }, "发票信息查询失败", logger);
    }
    
    
    /**
     * 
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @param isCust  true  供应商查询已经生效的发票                      false  核心企业查询已经生效的发票
     * @return
     */
    @RequestMapping(value = "/queryEffectiveInvoice", method = RequestMethod.POST)
    public @ResponseBody String queryEffectiveInvoice(HttpServletRequest request,  String flag, int pageNum, int pageSize,boolean isCust) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票已经生效信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webQueryEffectiveInvoice(anMap, flag, pageNum, pageSize,isCust);
            }
        }, "发票信息查询失败", logger);
    }
    
    /**
     * 查询发票的详情
     * @param refNo
     * @param version
     * @return
     */
    @RequestMapping(value = "/findInvoiceDO", method = RequestMethod.POST)
    public @ResponseBody String findInvoiceDO(String refNo,String version) {
        
        logger.info("发票详细信息查询,入参： refNo:" + refNo+"  version: " +version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webFindInvoiceDO(refNo,version);
            }
        }, "发票信息查询失败", logger);
    }
    
    /**
     * 对已经生效的票据进行查询,可以进行废止操作
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryRecycleInvoiceDO", method = RequestMethod.POST)
    public @ResponseBody String queryRecycleInvoiceDO(HttpServletRequest request,  String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票回收信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webQueryRecycleInvoice(anMap, flag, pageNum, pageSize);
            }
        }, "发票回收信息查询失败", logger);
    }
    
    @RequestMapping(value = "/saveAnnulEffectiveInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveAnnulEffectiveInvoice(String refNo,String version) {
        logger.info("发票信息作废,入参：refNo=" + refNo+"  version:"+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfInvoiceService.webSaveAnnulEffectiveInvoice(refNo,version);
            }
        }, "发票信息废止失败", logger);
    }
}
