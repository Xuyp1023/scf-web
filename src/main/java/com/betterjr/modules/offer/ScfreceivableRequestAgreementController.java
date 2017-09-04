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
import com.betterjr.modules.supplieroffer.IScfReceivableRequestAgreementService;

@Controller
@RequestMapping("/Scf/ReceivableRequestAgreement")
public class ScfreceivableRequestAgreementController {
    
    private static final Logger logger = LoggerFactory.getLogger(ScfSupplierOfferController.class);

    
    @Reference(interfaceClass=IScfReceivableRequestAgreementService.class)
    private IScfReceivableRequestAgreementService agreementService;
    
    /**
     * 供应商查询合同管理列表
     * custNo","coreCustNo","agreementType
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryAgreementWithSupplier", method = RequestMethod.POST)
    public @ResponseBody String queryAgreementWithSupplier(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return agreementService.webQueryAgreementWithSupplier(anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    /**
     * 核心企业查询合同管理
     * custNo","coreCustNo
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryAgreementWithCore", method = RequestMethod.POST)
    public @ResponseBody String queryAgreementWithCore(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询申请信息,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return agreementService.webQueryAgreementWithCore(anMap, flag, pageNum, pageSize);
            }
        }, "查询申请信息失败", logger);
        
    }
    
    @RequestMapping(value = "/queryDictFactory", method = RequestMethod.POST)
    public @ResponseBody String queryDictFactory(HttpServletRequest request) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return agreementService.webQueryDictFactory();
            }
        }, "查询失败", logger);
        
    }

}
