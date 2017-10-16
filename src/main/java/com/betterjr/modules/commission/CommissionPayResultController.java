// Copyright (c) 2014-2017 Bytter. All rights reserved.
// ============================================================================
// CURRENT VERSION
// ============================================================================
// CHANGE LOG
// V2.0 : 2017年5月4日, liuwl, creation
// ============================================================================
package com.betterjr.modules.commission;

import static com.betterjr.common.web.ControllerExceptionHandler.exec;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.web.Servlets;

/**
 * @author liuwl
 *
 */
@Controller
@RequestMapping(value = "/Scf/CommissionPayResult")
public class CommissionPayResultController {
    private static final Logger logger = LoggerFactory.getLogger(CommissionPayResultController.class);

    @Reference(interfaceClass = ICommissionPayResultService.class)
    private ICommissionPayResultService commissionPayResultService;

    @RequestMapping(value = "/createPayResult", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String createPayResult(final HttpServletRequest request, final String importDate,
            final String payDate, final Long custNo) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("创建日对账单出错,入参：" + param.toString());
        return exec(() -> commissionPayResultService.webCreatePayResult(param), "创建日对账单出错！", logger);
    }

    @RequestMapping(value = "/findCommissionRecord", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String findCommissionRecord(final HttpServletRequest request, final String refNo) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("查询佣金详情,入参：" + param.toString());
        return exec(() -> commissionPayResultService.webFindCommissionRecord(refNo), "统计日对账单出错！", logger);
    }

    @RequestMapping(value = "/countPayResultRecord", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String countPayResultRecord(final HttpServletRequest request, final Long payResultId) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("统计日对账单出错,入参：" + param.toString());
        return exec(() -> commissionPayResultService.webCountPayResultRecord(payResultId), "统计日对账单出错！", logger);
    }

    @RequestMapping(value = "/queryUncheckCommissionRecord", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String queryUncheckCommissionRecord(final HttpServletRequest request, final int flag,
            final int pageNum, final int pageSize) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("查询已导入数据,入参：" + param.toString());
        return exec(() -> commissionPayResultService.webQueryUncheckCommissionRecord(param, flag, pageNum, pageSize),
                "查询已导入数据出错！", logger);
    }

    @RequestMapping(value = "/findCountCommissionRecord", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String findCountCommissionRecord(final HttpServletRequest request) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("统计已导入数据,入参：" + param.toString());
        return exec(() -> commissionPayResultService.webFindCountCommissionRecord(param), "统计已导入数据出错！", logger);
    }

    @RequestMapping(value = "/queryNormalPayResultList", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String queryNormalPayResultList(final HttpServletRequest request, final int flag,
            final int pageNum, final int pageSize) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("查询未确认的日对账单,入参：" + param.toString());
        return exec(() -> commissionPayResultService.webQueryNormalPayResultList(param, flag, pageNum, pageSize),
                "查询未确认的日对账单出错！", logger);
    }

    @RequestMapping(value = "/queryConfirmPayResultList", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String queryConfirmPayResultList(final HttpServletRequest request, final int flag,
            final int pageNum, final int pageSize) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("查询已确认的日对账单,入参：" + param.toString());

        return exec(() -> commissionPayResultService.webQueryConfirmPayResultList(param, flag, pageNum, pageSize),
                "查询已确认的日对账单出错！", logger);
    }

    @RequestMapping(value = "/queryAuditPayResultList", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String queryAuditPayResultList(final HttpServletRequest request, final Long custNo,
            final String payDate, final int flag, final int pageNum, final int pageSize) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("查询已审核的日对账单,入参：" + param.toString());
        return exec(() -> commissionPayResultService.webQueryAuditPayResultList(param, flag, pageNum, pageSize),
                "查询已审核的日对账单出错！", logger);
    }

    @RequestMapping(value = "/queryAllPayResultRecords", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String queryAllPayResultRecords(final HttpServletRequest request, final Long payResultId,
            final int flag, final int pageNum, final int pageSize) {
        return exec(() -> commissionPayResultService.webQueryAllPayResultRecords(payResultId, flag, pageNum, pageSize),
                "查询全部的日对记录出错！", logger);
    }

    @RequestMapping(value = "/queryUncheckPayResultRecords", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String queryUncheckPayResultRecords(final HttpServletRequest request, final Long payResultId,
            final int flag, final int pageNum, final int pageSize) {
        final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");

        logger.info("查询未处理的日对账单,入参：" + param.toString());
        return exec(() -> commissionPayResultService.webQueryUncheckPayResultRecords(param, payResultId, flag, pageNum,
                pageSize), "查询未处理的日对记录出错！", logger);
    }

    @RequestMapping(value = "/querySuccessPayResultRecords", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String querySuccessPayResultRecords(final HttpServletRequest request, final Long payResultId,
            final int flag, final int pageNum, final int pageSize) {
        return exec(
                () -> commissionPayResultService.webQuerySuccessPayResultRecords(payResultId, flag, pageNum, pageSize),
                "查询成功支付的日对记录出错！", logger);
    }

    @RequestMapping(value = "/queryFailurePayResultRecords", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String queryFailurePayResultRecords(final HttpServletRequest request, final Long payResultId,
            final int flag, final int pageNum, final int pageSize) {
        return exec(
                () -> commissionPayResultService.webQueryFailurePayResultRecords(payResultId, flag, pageNum, pageSize),
                "查询成功支付的日对记录出错！", logger);
    }

    @RequestMapping(value = "/confirmSuccessPayResultRecords", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String confirmSuccessPayResultRecords(final HttpServletRequest request, final Long payResultId,
            final String payResultRecords) {
        return exec(() -> commissionPayResultService.webConfirmSuccessPayResultRecords(payResultId, payResultRecords),
                "设置支付成功的日对记录出错！", logger);
    }

    @RequestMapping(value = "/confirmFailurePayResultRecords", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String confirmFailurePayResultRecords(final HttpServletRequest request, final Long payResultId,
            final String payResultRecords) {
        return exec(() -> commissionPayResultService.webConfirmFailurePayResultRecords(payResultId, payResultRecords),
                "设置支付失败的日对账记录出错！", logger);
    }

    @RequestMapping(value = "/successToFailurePayResultRecord", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String successToFailurePayResultRecord(final HttpServletRequest request,
            final Long payResultId, final Long payResultRecordId) {
        return exec(() -> commissionPayResultService.webSuccessToFailurePayResultRecord(payResultId, payResultRecordId),
                "设置支付失败的日对账记录出错！", logger);
    }

    @RequestMapping(value = "/failureToSuccessPayResultRecord", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String failureToSuccessPayResultRecord(final HttpServletRequest request,
            final Long payResultId, final Long payResultRecordId) {
        return exec(() -> commissionPayResultService.webFailureToSuccessPayResultRecord(payResultId, payResultRecordId),
                "设置支付成功的日对账记录出错！", logger);
    }

    @RequestMapping(value = "/confirmPayResult", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String confirmPayResult(final HttpServletRequest request, final Long payResultId) {
        return exec(() -> commissionPayResultService.webConfirmPayResult(payResultId), "确认日对账单出错！", logger);
    }

    @RequestMapping(value = "/auditPayResult", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String auditPayResult(final HttpServletRequest request, final Long payResultId) {
        return exec(() -> commissionPayResultService.webAuditPayResult(payResultId), "确认日对账单出错！", logger);
    }
}
