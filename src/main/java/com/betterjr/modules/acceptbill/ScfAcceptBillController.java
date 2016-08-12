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
import com.betterjr.common.web.AjaxObject;
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
        try {

            return scfAcceptBillService.webQueryAcceptBill(anMap, isOnlyNormal, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("汇票信息查询失败", e);
            return AjaxObject.newError("汇票信息查询失败").toJson();
        }
    }

    @RequestMapping(value = "/modifyAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String modifyAcceptBill(HttpServletRequest request,String isOnlyNormal, Long id, String fileList) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息修改,入参：" + anMap.toString());
        try {

            return scfAcceptBillService.webSaveModifyAcceptBill(anMap, id, fileList);
        }
        catch (Exception e) {
            logger.error("汇票信息编辑失败", e);
            return AjaxObject.newError("汇票信息修改失败").toJson();
        }
    }

}
