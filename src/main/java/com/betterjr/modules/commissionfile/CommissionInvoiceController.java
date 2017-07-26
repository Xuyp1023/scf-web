package com.betterjr.modules.commissionfile;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.utils.BetterStringUtils;
import com.betterjr.common.web.ControllerExceptionHandler;
import com.betterjr.common.web.Servlets;
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;

@Controller
@RequestMapping(value = "/Scf/CommissionInvoice")
public class CommissionInvoiceController {
    
 private static final Logger logger = LoggerFactory.getLogger(CommissionInvoiceController.class);
    
    @Reference(interfaceClass=ICommissionInvoiceService.class)
    private ICommissionInvoiceService commissionInvoiceService;
    
    
    /**
     * 发票新增索取
     * 
     * @param anCustNo  平台Id
     * @param anCoreCustNo   核心企业Id
     * @param anMonthlyIds    月对账单Id集合
     * @param anInvoiceType    发票类型   0 普通发票  1 专用发票
     * @return
     */
    @RequestMapping(value = "/saveDemandInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveDemandInvoice(Long custNo,Long coreCustNo,String monthlyIds,String invoiceType) {

        logger.info("发票索取,入参： custNo=" + custNo+" coreCustNo="+coreCustNo+" monthlyIds="+monthlyIds+" invoiceType="+invoiceType);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return commissionInvoiceService.webSaveDemandInvoice(custNo, coreCustNo, monthlyIds, invoiceType);
            }
        }, "发票索取失败", logger);
    }
    
    
    /**
     * 索取发票之后提交发票信息   是发票正式生效
     * @param anInvoiceId
     * @param anInvoiceContent
     * @param anDescription
     * @return
     */
    @RequestMapping(value = "/saveInvoiceEffective", method = RequestMethod.POST)
    public @ResponseBody String saveInvoiceEffective(Long id,String invoiceContent,String description) {
        
        logger.info("发票确认,入参： invoiceId=" + id+" invoiceContent="+invoiceContent+" description="+description);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return commissionInvoiceService.webSaveInvoiceEffective(id, invoiceContent, description);
            }
        }, "发票确认失败", logger);
    }
    
    /**
     * 将未确认的票据作废
     * @param anInvoiceId
     * @return
     */
    @RequestMapping(value = "/saveAnnulInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveAnnulInvoice(Long invoiceId) {
        
        logger.info("发票作废,入参： invoiceId=" + invoiceId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return commissionInvoiceService.webSaveAnnulInvoice(invoiceId);
            }
        }, "发票作废失败", logger);
    }
    
    
    /**
     * 待开票的发票变成开票中的发票
     * @param anInvoiceId
     * @return
     */
    @RequestMapping(value = "/saveConfirmInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveConfirmInvoice(Long invoiceId) {
        
        logger.info("发票确认,入参： invoiceId=" + invoiceId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return commissionInvoiceService.webSaveConfirmInvoice(invoiceId);
            }
        }, "发票确认失败", logger);
    }
    
    
    /**
     * 发票维护详细信息
     * id
     * invoiceNo
     * invoiceCode
     * invoiceDate
     * description
     * drawer  出票人
     * @param anInvoice
     * @param anFileList
     * @return
     */
    @RequestMapping(value = "/saveAuditInvoice", method = RequestMethod.POST)
    public @ResponseBody String saveAuditInvoice(HttpServletRequest request,String fileList) {
        
        Map paramMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票提交,入参：" + paramMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return commissionInvoiceService.webSaveAuditInvoice(paramMap,fileList);
            }
        }, "发票提交失败", logger);
    }
    
    
    /**
     * 查询发票信息
     * businStatus  状态
     * GTEregDate   申请日期
     * LTEregDate   申请日期
     * custNo    平台id
     * coreCustNo   发票抬头企业
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize
     * @param anIsConfirm   是否是用来确认页面的查询  true  是确认页面的查询       false  发票申请页面的查询
     * @return
     */
    @RequestMapping(value = "/queryCommissionInvoice", method = RequestMethod.POST)
    public @ResponseBody String queryCommissionInvoice(HttpServletRequest request,String flag, int pageNum, int pageSize,boolean isConfirm) {
        
        Map paramMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票查询,入参：" + paramMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return commissionInvoiceService.webQueryCommissionInvoice(paramMap, flag, pageNum, pageSize, isConfirm);
            }
        }, "发票查询失败", logger);
    }
    
    
    /**
     * 查询发票详情信息
     * @param anInvoiceId
     * @return
     */
    @RequestMapping(value = "/findCommissionInvoiceById", method = RequestMethod.POST)
    public @ResponseBody String findCommissionInvoiceById(Long invoiceId) {
        
        logger.info("发票查询,入参：invoiceId=" + invoiceId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return commissionInvoiceService.webFindCommissionInvoiceById(invoiceId);
            }
        }, "发票查询失败", logger);
    }

    /**
     * 查询可以使用的月账单信息
     * coreCustNo
     * invoiceType
     * GTEbillMonth
     * LTEbillMonth 
     * 
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @param isConfirm
     * @return
     */
    @RequestMapping(value = "/queryCanUseMonthly", method = RequestMethod.POST)
    public @ResponseBody String queryCanUseMonthly (HttpServletRequest request,String flag, int pageNum, int pageSize) {
        
        Map paramMap = replaceMapValueLine(Servlets.getParametersStartingWith(request, ""));
        logger.info("发票月对账单查询,入参：" + paramMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return commissionInvoiceService.webQueryCanUseMonthly(paramMap, pageNum, pageSize);
            }
        }, "发票月对账单查询失败", logger);
    }
    
    
    private Map<String,Object> replaceMapValueLine(Map<String,Object> anMap){
        
        Map<String, Object> params = new TreeMap<String, Object>();
        
        for(String key :anMap.keySet()) {
            
            if(anMap.get(key) !=null){
                String tmpStr =  anMap.get(key).toString();
                if (BetterStringUtils.isNotBlank(tmpStr)) {
                     params.put(key, tmpStr.replace("-", ""));
                }
            }
            
        }
        return params;
        
    }
}
