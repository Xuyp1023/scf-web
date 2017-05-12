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
 * 日报表控制层
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/Scf/CommissionDailyStatement")
public class CommissionDailyStatementController {

    private static final Logger logger=LoggerFactory.getLogger(CommissionDailyStatementController.class); 
    
    @Reference(interfaceClass=ICommissionDailyStatementService.class)
    private ICommissionDailyStatementService dailyStatementService;
    
    @RequestMapping(value = "/queryDailyStatement", method = RequestMethod.POST)
    public @ResponseBody String queryDailyStatement(HttpServletRequest request,int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询日报表数据,入参：" + anMap.toString());
        return exec(() -> dailyStatementService.webQueryDailyStatement(anMap, pageNum, pageSize), "分页查询日报表数据", logger);
    }
    
    @RequestMapping(value = "/findDailyStatementCount", method = RequestMethod.POST)
    public @ResponseBody String findDailyStatementCount(String billMonth,Long custNo) {
        logger.info("查询统计日账单的累计月总数,入参：month:" + billMonth+",custNo:"+custNo);
        return exec(() -> dailyStatementService.webFindDailyStatementCount(billMonth, custNo), "查询统计日账单的累计月总数", logger);
    }
    
    @RequestMapping(value = "/findDailyStatementInfoByMonth", method = RequestMethod.POST)
    public @ResponseBody String findDailyStatementInfoByMonth(String billMonth,Long custNo) {
        logger.info("根据对账月份查询日账单列表,入参：month:" + billMonth+",custNo:"+custNo);
        return exec(() -> dailyStatementService.webFindDailyStatementInfoByMonth(billMonth, custNo), "根据对账月份查询日账单列表", logger);
    }
    
    @RequestMapping(value = "/findDailyStatementBasicsInfo", method = RequestMethod.POST)
    public @ResponseBody String findDailyStatementBasicsInfo(HttpServletRequest request) { 
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询日账单的列表基础信息,入参：" + anMap.toString());
        return exec(() -> dailyStatementService.webFindDailyStatementBasicsInfo(anMap), "查询日账单的列表基础信息", logger);
    }
    
    @RequestMapping(value = "/updateDailyStatement", method = RequestMethod.POST)
    public @ResponseBody String updateDailyStatement(Long dailyStatementId,String businStatus) { 
        logger.info("更新日报表状态,入参：dailyStatementId:" + dailyStatementId+",businStatus:"+businStatus);
        return exec(() -> dailyStatementService.webUpdateDailyStatement(dailyStatementId, businStatus), "更新日报表状态", logger);
    }
    
    @RequestMapping(value = "/delDailyStatement", method = RequestMethod.POST)
    public @ResponseBody String delDailyStatement(Long dailyStatementId) { 
        logger.info("删除日报表,入参：dailyStatementId:" + dailyStatementId);
        return exec(() -> dailyStatementService.webDelDailyStatement(dailyStatementId), "删除日报表", logger);
    }
    
    @RequestMapping(value = "/findPayResultCount", method = RequestMethod.POST)
    public @ResponseBody String findPayResultCount(Long custNo,String payDate) { 
        String anPayDate=payDate.replace("-", "");
        logger.info("查询支付结果统计,入参：custNo:" + custNo+",payDate:"+anPayDate);
        return exec(() -> dailyStatementService.webFindPayResultCount(anPayDate, custNo), "查询支付结果统计", logger);
    }
    
    @RequestMapping(value = "/queryPayResultRecord", method = RequestMethod.POST)
    public @ResponseBody String queryPayResultRecord(Long custNo,String payDate,int flag,int pageNum, int pageSize) { 
        String anPayDate=payDate.replace("-", "");
        logger.info("分页查询支付结果,入参：custNo:" + custNo+",payDate:"+anPayDate);
        return exec(() -> dailyStatementService.webQueryPayResultRecord(custNo, anPayDate, flag, pageNum, pageSize), "分页查询支付结果", logger);
    }
    
    @RequestMapping(value = "/findPayResultInfo", method = RequestMethod.POST)
    public @ResponseBody String findPayResultInfo(String payDate,Long custNo) {
        String anPayDate=payDate.replace("-", "");
        logger.info("查询支付信息,入参：payDate:" + anPayDate+",custNo:"+custNo);
        return exec(() -> dailyStatementService.webFindPayResultInfo(anPayDate, custNo), "查询支付信息", logger);
    }
    
    @RequestMapping(value = "/addDailyStatement", method = RequestMethod.POST)
    public @ResponseBody String addDailyStatement(String dailyRefNo,String payDate,Long custNo) { 
        String anPayDate=payDate.replace("-", "");
        logger.info("添加日报表,入参dailyRefNo:"+dailyRefNo+",payDate:" + anPayDate+",custNo:"+custNo);
        return exec(() -> dailyStatementService.webSaveDailyStatement(dailyRefNo, anPayDate, custNo), "添加日报表", logger);
    }
    
    @RequestMapping(value = "/findDailyStatementById", method = RequestMethod.POST)
    public @ResponseBody String findDailyStatementById(Long dailyStatementId) { 
        logger.info("查询日账单详情,入参dailyStatementId:"+dailyStatementId);
        return exec(() -> dailyStatementService.webFindDailyStatementById(dailyStatementId), "查询日账单详情", logger);
    }
    
    @RequestMapping(value = "/queryDailyStatementRecordByDailyId", method = RequestMethod.POST)
    public @ResponseBody String queryDailyStatementRecordByDailyId(Long dailyStatementId,String flag,int pageNum, int pageSize) { 
        logger.info("分页查询日账单记录列表,入参dailyStatementId:"+dailyStatementId);
        return exec(() -> dailyStatementService.webQueryDailyStatementRecordById(dailyStatementId, pageNum, pageSize, flag), "查询日账单记录列表", logger);
    }
    
    @RequestMapping(value = "/findDailyStatementByPayDate", method = RequestMethod.POST)
    public @ResponseBody String findDailyStatementByPayDate(String payDate,Long custNo) {
        String anPayDate=payDate.replace("-", "");
        logger.info("根据对账日期和对账企业查询日账单,入参payDate:"+anPayDate+",custNo:"+custNo);
        return exec(() -> dailyStatementService.webFindDailyStatementByPayDate(payDate,custNo), "根据对账日期和对账企业查询日账单", logger);
    }
}
