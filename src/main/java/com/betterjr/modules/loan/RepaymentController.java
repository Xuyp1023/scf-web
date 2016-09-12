package com.betterjr.modules.loan;

import java.math.BigDecimal;
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
import com.betterjr.common.web.ControllerExceptionHandler;
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping(value = "/Scf/Repayment")
public class RepaymentController {
    private static final Logger logger = LoggerFactory.getLogger(RepaymentController.class);

    @Reference(interfaceClass = IScfRepaymentService.class)
    private IScfRepaymentService scfRepaymentService;
    
    @RequestMapping(value = "/saveRepayment", method = RequestMethod.POST)
    public @ResponseBody String saveRepayment(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("还款，入参:" + map);
        
        try {
            return scfRepaymentService.webSaveRepayment(map);
        }
        catch (Exception ex) {
            logger.error("还款：", ex);
            return AjaxObject.newError("repayment service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/queryRepaymentFee", method = RequestMethod.POST)
    public @ResponseBody String queryRepaymentFee(HttpServletRequest request, String requestNo, String payType, String factorNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("查询还款费用，入参:" + map);
        
        try {
            return scfRepaymentService.webQueryRepaymentFee(requestNo, payType, factorNo);
        }
        catch (Exception ex) {
            logger.error("查询还款费用：", ex);
            return AjaxObject.newError("queryRepaymentFee service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/querySellerRepaymentFee", method = RequestMethod.POST)
    public @ResponseBody String querySellerRepaymentFee(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("经销商还款，入参:" + map);
        
        try {
            return scfRepaymentService.webQuerySellerRepaymentFee(map);
        }
        catch (Exception ex) {
            logger.error("经销商还款：", ex);
            return AjaxObject.newError("querySellerRepaymentFee service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/calculatPayType", method = RequestMethod.POST)
    public @ResponseBody String calculatPayType(HttpServletRequest request, String requestNo, String payDate) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("计算还款方式，入参:" + map);
        
        try {
            return scfRepaymentService.webCalculatPayType(requestNo, payDate);
        }
        catch (Exception ex) {
            logger.error("计算还款方式：", ex);
            return AjaxObject.newError("calculatPayType service failed").toJson();
        }
        
    }
    
    @RequestMapping(value = "/queryPayRecordList", method = RequestMethod.POST)
    public @ResponseBody String queryPayRecordList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询还款记录，入参:" + map);
        
        try {
            return scfRepaymentService.webQueryPayRecordList(map, flag, pageNum, pageSize);
        }
        catch (Exception ex) {
            logger.error("分页查询还款记录：", ex);
            return AjaxObject.newError("queryPayRecordList service failed").toJson();
        }
        
    }

    
    @RequestMapping(value = "/queryExemptList", method = RequestMethod.POST)
    public @ResponseBody String queryExemptList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webQueryExemptList(map, flag, pageNum, pageSize);
            }
        }, "分页查询豁免记录列表", logger);
    }
    
    @RequestMapping(value = "/addExempt", method = RequestMethod.POST)
    public @ResponseBody String addExemptList(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            
            public String handle() {
                return scfRepaymentService.webAddExempt(map);
            }
        }, "新增豁免", logger);
        
    }
    
    @RequestMapping(value = "/queryPressMoneyList", method = RequestMethod.POST)
    public @ResponseBody String queryPressMoneyList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webQueryPressMoneyList(map, flag, pageNum, pageSize);
            }
        }, "分页查催收免记录列表", logger);
    }
    
    @RequestMapping(value = "/addPressMoney", method = RequestMethod.POST)
    public @ResponseBody String addPressMoney(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            
            public String handle() {
                return scfRepaymentService.webAddPressMoney(map);
            }
        }, "新增催收", logger);
        
    }
    
    @RequestMapping(value = "/calculatExtensionEndDate", method = RequestMethod.POST)
    public @ResponseBody String calculatEndDate(HttpServletRequest request, String startDate, Integer period, Integer periodUnit) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webCalculatExtensionEndDate(startDate, period, periodUnit);
            }
        }, "获取展期结束日期", logger);
        
    }
    
    @RequestMapping(value = "/calculatExtensionFee", method = RequestMethod.POST)
    public @ResponseBody String calculatFee(HttpServletRequest request, BigDecimal ratio, BigDecimal managementRatio, BigDecimal extensionBalance) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webCalculatExtensionFee(ratio, managementRatio, extensionBalance);
            }
        }, "计算展期后的利息", logger);
        
    }
    
    @RequestMapping(value = "/calculatLoanBalance", method = RequestMethod.POST)
    public @ResponseBody String calculatLoanBalance(HttpServletRequest request, String requestNo, String startDate) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webCalculatLoanBalance(requestNo, startDate);
            }
        }, "计算展期前贷款余额", logger);
        
    }
    
    @RequestMapping(value = "/addExtension", method = RequestMethod.POST)
    public @ResponseBody String addExtension(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("保存展期,参数:"+map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webAddExtension(map);
            }
        }, "保存展期数据", logger);
        
    }
    
    @RequestMapping(value = "/payAssigned", method = RequestMethod.POST)
    public @ResponseBody String payAssigned(HttpServletRequest request, String requestNo, String startDate, BigDecimal payBalance) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("查询展期时还款分陪,参数:"+map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webPayAssigned(requestNo, startDate, payBalance);
            }
        }, "查询展期时还款分陪数据", logger);
        
    }
    
    @RequestMapping(value = "/queryExtensionList", method = RequestMethod.POST)
    public @ResponseBody String extensionList(HttpServletRequest request, String requestNo, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("分页查询展期列表,参数:"+ map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webQueryExtensionList(map, requestNo, flag, pageNum, pageSize);
            }
        }, "分页查询展期列表", logger);
        
    }
    
    @RequestMapping(value = "/findExtensionList", method = RequestMethod.POST)
    public @ResponseBody String findExtensionList(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("无分页查询展期列表,参数:"+ map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webFindExtensionList(map);
            }
        }, "分页查询展期列表", logger);
        
    }
    
    @RequestMapping(value = "/findExemptList", method = RequestMethod.POST)
    public @ResponseBody String findExemptList(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("无分页查询豁免列表,参数:"+ map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webFindExemptList(map);
            }
        }, "无分页查询豁免列表", logger);
        
    }
    
    @RequestMapping(value = "/findPayRecordList", method = RequestMethod.POST)
    public @ResponseBody String findPayRecordList(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("无分页查还款记录列表,参数:"+ map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webFindPayRecordList(map);
            }
        }, "无分页查还款记录列表", logger);
        
    }
    
    @RequestMapping(value = "/findPlanList", method = RequestMethod.POST)
    public @ResponseBody String findPlanList(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("无分页查还款计划列表,参数:"+ map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webFindPlanList(map);
            }
        }, "无分页查还款计划列表", logger);
        
    }
    
    @RequestMapping(value = "/findPresMoneyist", method = RequestMethod.POST)
    public @ResponseBody String findPresMoneyist(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("无分页查催收列表,参数:"+ map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webFindPresMoneyist(map);
            }
        }, "无分页查催收列表", logger);
        
    }
    
    @RequestMapping(value = "/saveModifyPressMoney", method = RequestMethod.POST)
    public @ResponseBody String saveModifyPressMoney(HttpServletRequest request, Long id) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("修改催收,参数:"+ map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webSaveModifyPayPlan(map, id);
            }
        }, "修改催收", logger);
        
    }
    
    @RequestMapping(value = "/saveDelPressMoney", method = RequestMethod.POST)
    public @ResponseBody String saveDelPressMoney(HttpServletRequest request, Long id) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.debug("修改催收,参数:"+ map);
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfRepaymentService.webSaveDelPressMoney(map, id);
            }
        }, "修改催收", logger);
        
    }
    
   
}
