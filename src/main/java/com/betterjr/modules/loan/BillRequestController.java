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
import com.betterjr.common.utils.Collections3;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.ControllerExceptionHandler;
import com.betterjr.common.web.Servlets;
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;

@Controller
@RequestMapping(value = "/Scf/BillRequest")
public class BillRequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    
    @Reference(interfaceClass = IScfWechatRequestService.class)
    private IScfWechatRequestService billRequestService;
    
    @RequestMapping(value = "/queryBillRequestList", method = RequestMethod.POST)
    public @ResponseBody String queryRequestList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        String[] queryTerm = new String[] { "custNo", "coreCustNo", "factorNo", "GTEactualDate", "LTEactualDate" };
        Map<String, Object> qyMap = Collections3.filterMap(map, queryTerm);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return billRequestService.webQueryRequestList(qyMap, flag, pageNum, pageSize);
            }
        }, "查询融资申请列表", logger);
    }
    
    @RequestMapping(value = "/addBillRequest", method = RequestMethod.POST)
    public @ResponseBody String addBillRequest(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("添加票据融资申请，入参:" + map.toString());
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return billRequestService.webAddRequest(map);
            }
        }, "加票据融资申请", logger);
    }
    
    @RequestMapping(value = "/requestByOffer", method = RequestMethod.POST)
    public @ResponseBody String requestByOffer(HttpServletRequest request, String offerId) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("根据报价信息进行融资申请，入参:" + map.toString());

        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return billRequestService.webRequestByOffer(offerId);
            }
        }, "根据报价信息进行融资申请", logger);
    }

    @RequestMapping(value = "/findRequestByBill", method = RequestMethod.POST)
    public @ResponseBody String findRequestByBill(HttpServletRequest request, String billId) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("根据票据id查询融资申请，入参:" + map.toString());

        try {
            return billRequestService.webFindRequestByBill(billId);
        }
        catch (Exception ex) {
            logger.error("根据票据id查询融资申请:", ex);
            return AjaxObject.newError("findRequestByBill service failed").toJson();
        }

    }
    
    @RequestMapping(value = "/findRequestByNo", method = RequestMethod.POST)
    public @ResponseBody String findRequestByNo(HttpServletRequest request, String requestNo) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("根据申请编号查询融资申请，入参:" + map.toString());

        try {
            return billRequestService.webFindRequestByNo(requestNo);
        }
        catch (Exception ex) {
            logger.error("查询票据融资申请:", ex);
            return AjaxObject.newError("findRequestByNo service failed").toJson();
        }

    }
}
