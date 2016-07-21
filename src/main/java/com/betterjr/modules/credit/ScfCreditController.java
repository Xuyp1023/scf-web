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

}
