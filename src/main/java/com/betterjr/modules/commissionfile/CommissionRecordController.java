package com.betterjr.modules.commissionfile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

@Controller
@RequestMapping(value = "/Scf/Commissionrecord")
public class CommissionRecordController {

    private static final Logger logger = LoggerFactory.getLogger(CommissionRecordController.class);

    @Reference(interfaceClass = ICommissionRecordService.class)
    private ICommissionRecordService recordService;

    /**
     * 佣金记录查询
     * 
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryRecordList", method = RequestMethod.POST)
    public @ResponseBody String queryRecordList(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金记录信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return recordService.webQueryRecordList(anMap, flag, pageNum, pageSize);
            }
        }, "佣金记录查询失败", logger);
    }

    /**
     * 佣金记录审核全部查询所有记录
     * 
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryCanAuditRecordList", method = RequestMethod.POST)
    public @ResponseBody String queryCanAuditRecordList(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金记录信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return recordService.webQueryCanAuditRecordList(anMap, flag, pageNum, pageSize);
            }
        }, "佣金记录查询失败", logger);
    }

    @RequestMapping(value = "/saveAuditRecordList", method = RequestMethod.POST)
    public @ResponseBody String saveAuditRecordList(Long custNo, final String importDate) {

        logger.info("佣金记录审核,入参：custNo:" + custNo + "   :importDate=" + importDate);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return recordService.webSaveAuditRecordList(custNo, importDate.replaceAll("-", ""));
            }
        }, "佣金记录审核失败", logger);
    }

}
