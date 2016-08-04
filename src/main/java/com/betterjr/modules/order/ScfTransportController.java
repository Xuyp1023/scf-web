package com.betterjr.modules.order;

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
@RequestMapping("/Scf/Transport")
public class ScfTransportController {

    private static final Logger logger = LoggerFactory.getLogger(ScfTransportController.class);

    @Reference(interfaceClass = IScfTransportService.class)
    private IScfTransportService scfTransportService;

    @RequestMapping(value = "/addTransport", method = RequestMethod.POST)
    public @ResponseBody String addTransport(HttpServletRequest request, String fileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单运输单据录入,入参:" + anMap.toString());
        try {
            return scfTransportService.webAddTransport(anMap, fileList);
        }
        catch (Exception e) {
            logger.error("订单运输信息录入失败", e);
            return AjaxObject.newError("订单运输信息录入失败").toJson();
        }
    }

    @RequestMapping(value = "/queryTransport", method = RequestMethod.POST)
    public @ResponseBody String queryTransport(HttpServletRequest request, String flag, int pageNum, int pageSize)  {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询订单运输单据,入参:" + anMap.toString());
        try{
            return scfTransportService.webQueryTransportList(anMap,flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("查询订单运输单据失败",e);
            return AjaxObject.newError("查询订单运输单据失败").toJson();
        }
    }
}
