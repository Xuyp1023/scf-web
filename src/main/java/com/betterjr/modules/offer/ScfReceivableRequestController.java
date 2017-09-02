package com.betterjr.modules.offer;

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
import com.betterjr.common.web.Servlets;
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.modules.supplieroffer.IScfReceivableRequestService;

@Controller
@RequestMapping("/Scf/ReceivableRequest")
public class ScfReceivableRequestController {
    
    
    private static final Logger logger = LoggerFactory.getLogger(ScfSupplierOfferController.class);

    
    @Reference(interfaceClass =IScfReceivableRequestService.class )
    private IScfReceivableRequestService requestService;
    
    /**
     * 应收账款申请 新增
     * receivableId
     *      * @param request
     * @return
     */
    @RequestMapping(value = "/saveAddRequest", method = RequestMethod.POST)
    public @ResponseBody String saveAddRequest(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应付账款申请新增,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveAddRequest(anMap);
            }
        }, "应付账款申请新增失败", logger);
        
    }
    
    /**
     * 供应商提交申请，正式使申请生效
     * @param requestNo 
     * @param requestPayDate
     * @param description
     * @return
     */
    @RequestMapping(value = "/saveSubmitRequest", method = RequestMethod.POST)
    public @ResponseBody String saveSubmitRequest(String requestNo,String requestPayDate,String description) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveSubmitRequest(requestNo, requestPayDate, description);
            }
        }, "应付账款提交申请失败", logger);
        
    }
    
    /**
     * 供应商签署合同
     * @param requestNo
     * @return
     */
    @RequestMapping(value = "/saveSupplierSignAgreement", method = RequestMethod.POST)
    public @ResponseBody String saveSupplierSignAgreement(String requestNo) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveSupplierSignAgreement(requestNo);
            }
        }, "供应商签署合同失败", logger);
        
    }
    
    
    /**
     * 供应商确认申请
     * @param requestNo
     * @param requestPayDate
     * @param description
     * @return
     */
    @RequestMapping(value = "/saveSupplierFinishConfirmRequest", method = RequestMethod.POST)
    public @ResponseBody String saveSupplierFinishConfirmRequest(String requestNo,String requestPayDate,String description) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveSupplierFinishConfirmRequest(requestNo, requestPayDate, description);
            }
        }, "供应商确认申请失败", logger);
        
    }
    
    /**
     * 供应商废止申请失败
     * @param requestNo
     * @return
     */
    @RequestMapping(value = "/saveAnnulReceivableRequest", method = RequestMethod.POST)
    public @ResponseBody String saveAnnulReceivableRequest(String requestNo) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveAnnulReceivableRequest(requestNo);
            }
        }, "供应商废止申请失败", logger);
        
    }
    
    
    /**
     * 核心企业签署合同
     * @param requestNo
     * @return
     */
    @RequestMapping(value = "/saveCoreSignAgreement", method = RequestMethod.POST)
    public @ResponseBody String saveCoreSignAgreement(String requestNo) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveCoreSignAgreement(requestNo);
            }
        }, "核心企业签署合同失败", logger);
        
    }
    
    /**
     * 核心企业完成付款
     * @param requestNo
     * @param requestPayDate
     * @param description
     * @return
     */
    @RequestMapping(value = "/saveCoreFinishPayRequest", method = RequestMethod.POST)
    public @ResponseBody String saveCoreFinishPayRequest(String requestNo,String requestPayDate,String description) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveCoreFinishPayRequest(requestNo, requestPayDate, description);
            }
        }, "核心企业完成付款失败", logger);
        
    }
    
    /**
     * 查询申请信息
     * @param requestNo
     * @return
     */
    @RequestMapping(value = "/findOneByRequestNo", method = RequestMethod.POST)
    public @ResponseBody String findOneByRequestNo(String requestNo) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webFindOneByRequestNo(requestNo);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 供应商查询还有那些融资申请可以再次提交
     * custNo","coreCustNo","requestNo","GTEregDate","LTEregDate"
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryReceivableRequestWithSupplier", method = RequestMethod.POST)
    public @ResponseBody String queryReceivableRequestWithSupplier(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryReceivableRequestWithSupplier( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 供应商查询所有已经完成申请的融资
     * custNo","coreCustNo","GTEendDate","LTEendDate
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryFinishReceivableRequestWithSupplier", method = RequestMethod.POST)
    public @ResponseBody String queryFinishReceivableRequestWithSupplier(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryFinishReceivableRequestWithSupplier( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 核心企业查询还可以继续申请的融资
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryReceivableRequestWithCore", method = RequestMethod.POST)
    public @ResponseBody String queryReceivableRequestWithCore(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryReceivableRequestWithCore( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 核心企业查询已经完成的申请
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryFinishReceivableRequestWithCore", method = RequestMethod.POST)
    public @ResponseBody String queryFinishReceivableRequestWithCore(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryFinishReceivableRequestWithCore( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 模式4 融资申请
     * @param request
     * @return
     * custNo
     * coreCustNo
     * custBankName
     * custBankAccount
     * custBankAccountName
     * factoryNo
     * invoiceList
     * agreementList
     * receivableList
     * requestBalance
     * requestPayDate
     * description
     */
    @RequestMapping(value = "/saveAddRequestFour", method = RequestMethod.POST)
    public @ResponseBody String saveAddRequestFour(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应付账款申请新增,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveAddRequestFour(anMap);
            }
        }, "应付账款申请新增失败", logger);
        
    }
    
    
    /**
     * 融资申请查询
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @param isCust
     * @return
     * "custNo","factoryNo","coreCustNo","GTERegDate","LTERegDate","businStatus"
     * isCust
     */
    @RequestMapping(value = "/queryReceivableRequestFour", method = RequestMethod.POST)
    public @ResponseBody String queryReceivableRequestFour(HttpServletRequest request, String flag, int pageNum, int pageSize,boolean isCust) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryReceivableRequestFour( anMap, flag, pageNum, pageSize,isCust);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    
    /**
     * 核心企业确认融资申请
     * @param requestNo
     * @return
     */
    @RequestMapping(value = "/saveConfirmReceivableRequestFour", method = RequestMethod.POST)
    public @ResponseBody String saveConfirmReceivableRequestFour(String requestNo) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveConfirmReceivableRequestFour(requestNo);
            }
        }, "确认失败", logger);
        
    }
    
    /**
     * 核心企业确认融资申请
     * @param requestNo
     * @return
     */
    @RequestMapping(value = "/saveRejectReceivableRequestFour", method = RequestMethod.POST)
    public @ResponseBody String saveRejectReceivableRequestFour(String requestNo) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveRejectReceivableRequestFour(requestNo);
            }
        }, "确认失败", logger);
        
    }
    
    
    /**
     * 校验是否可以进行融资申请
     * @param requestNo
     * @return
     */
    @RequestMapping(value = "/checkVerifyReceivable", method = RequestMethod.POST)
    public @ResponseBody String checkVerifyReceivable(Long receivableId) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webCheckVerifyReceivable(receivableId);
            }
        }, "确认失败", logger);
        
    }
    
}
