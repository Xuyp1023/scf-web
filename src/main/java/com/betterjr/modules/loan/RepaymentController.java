package com.betterjr.modules.loan;

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
@RequestMapping(value = "/Scf/Repayment")
public class RepaymentController {
    private static final Logger logger = LoggerFactory.getLogger(RepaymentController.class);

    @Reference(interfaceClass = IScfRepaymentService.class)
    private IScfRepaymentService repaymentService;
    
    @RequestMapping(value = "/saveRepayment", method = RequestMethod.POST)
    public @ResponseBody String saveRepayment(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("还款，入参:" + map);
        
        try {
            return repaymentService.webSaveRepayment(map);
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
            return repaymentService.webQueryRepaymentFee(requestNo, payType, factorNo);
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
            return repaymentService.webQuerySellerRepaymentFee(map);
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
            return repaymentService.webCalculatPayType(requestNo, payDate);
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
            return repaymentService.webQueryPayRecordList(map, flag, pageNum, pageSize);
        }
        catch (Exception ex) {
            logger.error("分页查询还款记录：", ex);
            return AjaxObject.newError("queryPayRecordList service failed").toJson();
        }
        
    }

    
    

}
