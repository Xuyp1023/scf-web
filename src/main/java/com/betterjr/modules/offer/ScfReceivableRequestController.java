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
    
    /**
     * 模式2 新增申请
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveAddRequestTwo", method = RequestMethod.POST)
    public @ResponseBody String saveAddRequestTwo(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应付账款申请新增,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveAddRequestTwo(anMap);
            }
        }, "应付账款申请新增失败", logger);
        
    }
    
    
    /**
     * 模式2 核心企业确认申请行为
     * @param anRequestNo
     * @param anRequestPayDate
     * @param anDescription
     * @return
     */
    @RequestMapping(value = "/saveCoreConfrimPayRequest", method = RequestMethod.POST)
    public @ResponseBody String saveCoreConfrimPayRequest(String requestNo,String requestPayDate,String description) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveCoreConfrimPayRequest(requestNo, requestPayDate, description);
            }
        }, "确认失败", logger);
        
    }
    
    /**
     * 模式2 第六步结算中心签署合同
     * @param anRequestNo
     * @return
     */
    @RequestMapping(value = "/saveFactorySignAgreement", method = RequestMethod.POST)
    public @ResponseBody String saveFactorySignAgreement(String requestNo) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveFactorySignAgreement(requestNo);
            }
        }, "签署合同失败", logger);
        
    }
    
    
    /**
     * 模式2 结算中心确认付款并完成申请流程
     * @param anRequestNo
     * @param anRequestPayDate
     * @param anDescription
     * @return
     */
    @RequestMapping(value = "/saveFactoryConfrimPayRequest", method = RequestMethod.POST)
    public @ResponseBody String saveFactoryConfrimPayRequest(String requestNo,String requestPayDate,String description) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveFactoryConfrimPayRequest(requestNo, requestPayDate, description);
            }
        }, "确认付款失败", logger);
        
    }
    
    
    /**
     * 模式2 供应商查询还有那些融资申请可以再次提交
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize  
     * @return
     */
    @RequestMapping(value = "/queryReceivableRequestTwoWithSupplier", method = RequestMethod.POST)
    public @ResponseBody String queryReceivableRequestTwoWithSupplier(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryReceivableRequestTwoWithSupplier( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 模式 二供应商查询已经完结的融资信息
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize  
     * @return
     */
    @RequestMapping(value = "/queryTwoFinishReceivableRequestWithSupplier", method = RequestMethod.POST)
    public @ResponseBody String queryTwoFinishReceivableRequestWithSupplier(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryTwoFinishReceivableRequestWithSupplier( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    
    /**
     * 模式二核心企业查询可以提交的融资信息
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize  
     * @return
     */
    @RequestMapping(value = "/queryTwoReceivableRequestWithCore", method = RequestMethod.POST)
    public @ResponseBody String queryTwoReceivableRequestWithCore(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryTwoReceivableRequestWithCore( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 模式二 核心企业查询已经融资结束的所有的申请信息
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize  
     * @return
     */
    @RequestMapping(value = "/queryTwoFinishReceivableRequestWithCore", method = RequestMethod.POST)
    public @ResponseBody String queryTwoFinishReceivableRequestWithCore(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryTwoFinishReceivableRequestWithCore( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 模式 2保理公司查询可以申请的申请
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize  
     * @return
     */
    @RequestMapping(value = "/queryTwoReceivableRequestWithFactory", method = RequestMethod.POST)
    public @ResponseBody String queryTwoReceivableRequestWithFactory(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryTwoReceivableRequestWithFactory( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 模式2  保理公司查询已经融资结束的所有的申请信息
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize  
     * @return
     */
    @RequestMapping(value = "/queryTwoFinishReceivableRequestWithFactory", method = RequestMethod.POST)
    public @ResponseBody String queryTwoFinishReceivableRequestWithFactory(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webQueryTwoFinishReceivableRequestWithFactory( anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    @RequestMapping(value = "/saveSubmitRequestTwo", method = RequestMethod.POST)
    public @ResponseBody String saveSubmitRequestTwo(String requestNo,String requestPayDate,String description,Long factoryNo) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return requestService.webSaveSubmitRequestTwo(requestNo, requestPayDate, description,factoryNo);
            }
        }, "应付账款提交申请失败", logger);
        
    }
    
}
