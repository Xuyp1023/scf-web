package com.betterjr.modules.acceptbill;

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
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping(value = "/Scf/AcceptBill")
public class ScfAcceptBillController {

    private static final Logger logger = LoggerFactory.getLogger(ScfAcceptBillController.class);

    @Reference(interfaceClass = IScfAcceptBillService.class)
    private IScfAcceptBillService scfAcceptBillService;

    @RequestMapping(value = "/queryAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String queryAcceptBill(HttpServletRequest request, String isOnlyNormal, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webQueryAcceptBill(anMap, isOnlyNormal, flag, pageNum, pageSize);
            }
        }, "汇票信息查询失败", logger);
    }

    @RequestMapping(value = "/findAcceptBillList", method = RequestMethod.POST)
    public @ResponseBody String findAcceptBillList(String custNo, String isOnlyNormal) {
        logger.info("汇票信息查询,入参：custNo" + custNo + " isOnlyNormal=" + isOnlyNormal);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webFindAcceptBillList(custNo, isOnlyNormal);
            }
        }, "汇票信息查询失败", logger);
    }

    @RequestMapping(value = "/modifyAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String modifyAcceptBill(HttpServletRequest request, String isOnlyNormal, Long id, String fileList) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息修改,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webSaveModifyAcceptBill(anMap, id, fileList);
            }
        }, "汇票信息编辑失败", logger);
    }

}
