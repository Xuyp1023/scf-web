package com.betterjr.modules.credit;

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
@RequestMapping(value = "/Scf/Credit")
public class ScfCreditController {

    private static final Logger logger = LoggerFactory.getLogger(ScfCreditController.class);

    @Reference(interfaceClass = IScfCreditService.class)
    private IScfCreditService scfCreditService;

    @RequestMapping(value = "/queryFactorCredit", method = RequestMethod.POST)
    public @ResponseBody String queryFactorCredit(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("授信额度信息查询,入参：" + anMap.toString());
        try {

            return scfCreditService.webQueryFactorCredit(anMap, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("授信额度信息查询失败", e);
            return AjaxObject.newError("授信额度信息查询失败").toJson();
        }
    }

    @RequestMapping(value = "/queryCreditDetail", method = RequestMethod.POST)
    public @ResponseBody String queryCreditDetail(HttpServletRequest request, Long creditId, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("授信额度变动信息查询,入参：" + anMap.toString());
        try {

            return scfCreditService.webQueryCreditDetail(anMap, creditId, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("授信额度变动信息查询", e);
            return AjaxObject.newError("授信额度变动信息查询").toJson();
        }
    }

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    public @ResponseBody String addCredit(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("授信额度录入,入参：" + anMap.toString());
        try {

            return scfCreditService.webAddCredit(anMap);
        }
        catch (Exception e) {
            logger.error("授信额度录入失败", e);
            return AjaxObject.newError("授信额度录入失败").toJson();
        }
    }

    @RequestMapping(value = "/modifyCredit", method = RequestMethod.POST)
    public @ResponseBody String modifyCredit(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("授信记录修改,入参：" + anMap.toString());
        try {

            return scfCreditService.webModifyCredit(anMap, id);
        }
        catch (Exception e) {
            logger.error("授信记录修改失败", e);
            return AjaxObject.newError("授信记录修改失败").toJson();
        }
    }

    @RequestMapping(value = "/activateCredit", method = RequestMethod.POST)
    public @ResponseBody String activateCredit(Long id) {
        logger.info("授信额度激活,入参： " + id);
        try {

            return scfCreditService.webActivateCredit(id);
        }
        catch (Exception e) {
            logger.error("授信额度激活失败", e);
            return AjaxObject.newError("授信额度激活失败").toJson();
        }
    }

    @RequestMapping(value = "/terminatCredit", method = RequestMethod.POST)
    public @ResponseBody String terminatCredit(Long id) {
        logger.info("授信终止,入参： " + id);
        try {

            return scfCreditService.webTerminatCredit(id);
        }
        catch (Exception e) {
            logger.error("授信终止失败", e);
            return AjaxObject.newError("授信终止失败").toJson();
        }
    }

}
