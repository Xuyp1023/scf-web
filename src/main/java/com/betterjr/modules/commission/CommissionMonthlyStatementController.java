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

/***
 * 月报表控制层
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/Scf/CommissionMonthlyStatement")
public class CommissionMonthlyStatementController {

    private static final Logger logger = LoggerFactory.getLogger(CommissionMonthlyStatementController.class);

    @Reference(interfaceClass = ICommissionMonthlyStatementService.class)
    private ICommissionMonthlyStatementService commissionMonthlyStatementService;

    @RequestMapping(value = "/queryMonthlyStatement", method = RequestMethod.POST)
    public @ResponseBody String queryMonthlyStatement(HttpServletRequest request, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询月报表数据,入参：" + anMap.toString());
        return exec(() -> commissionMonthlyStatementService.webQueryMonthlyStatement(anMap, pageNum, pageSize),
                "分页查询日报表数据", logger);
    }

    @RequestMapping(value = "/saveComissionMonthlyStatement", method = RequestMethod.POST)
    public @ResponseBody String saveComissionMonthlyStatement(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("保存月账单信息,入参：" + anMap.toString());
        return exec(() -> commissionMonthlyStatementService.webSaveComissionMonthlyStatement(anMap), "保存月账单信息", logger);
    }

    @RequestMapping(value = "/findMonthlyStatementById", method = RequestMethod.POST)
    public @ResponseBody String findMonthlyStatementById(Long monthlyId) {
        logger.info("查询详情,入参：" + monthlyId);
        return exec(() -> commissionMonthlyStatementService.webFindMonthlyStatementById(monthlyId), "查询详情", logger);
    }

    @RequestMapping(value = "/saveMonthlyStatement", method = RequestMethod.POST)
    public @ResponseBody String saveMonthlyStatement(Long monthlyId, String businStatus) {
        logger.info("更新状态,入参：" + "monthlyId:" + monthlyId + ",businStatus:" + businStatus);
        return exec(() -> commissionMonthlyStatementService.webSaveMonthlyStatement(monthlyId, businStatus), "更新状态",
                logger);
    }

    @RequestMapping(value = "/delMonthlyStatement", method = RequestMethod.POST)
    public @ResponseBody String delMonthlyStatement(Long monthlyId) {
        logger.info("删除,入参：" + "monthlyId:" + monthlyId);
        return exec(() -> commissionMonthlyStatementService.webDelMonthlyStatement(monthlyId), "删除", logger);
    }
}
