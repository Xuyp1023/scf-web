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
@RequestMapping("/Scf/OrderRelation")
public class ScfOrderRelationController {

    private static final Logger logger = LoggerFactory.getLogger(ScfOrderRelationController.class);

    @Reference(interfaceClass = IScfOrderRelationService.class)
    private IScfOrderRelationService scfOrderRelationService;

    @RequestMapping(value = "/addOrderRelation", method = RequestMethod.POST)
    public @ResponseBody String addOrderRelation(HttpServletRequest request, String infoIdList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单关联关系添加,入参:" + anMap);
        try {
            return scfOrderRelationService.webAddOrderRelation(anMap, infoIdList);
        }
        catch (Exception e) {
            logger.error("订单关联关系添加失败", e);
            return AjaxObject.newError("订单关联关系添加失败").toJson();
        }
    }

    @RequestMapping(value = "/deleteOrderRelation", method = RequestMethod.POST)
    public @ResponseBody String deleteOrderRelation(Long id) {
        logger.info("订单关联关系删除,入参:" + id);
        try {
            return scfOrderRelationService.webSaveDeleteOrderRelation(id);
        }
        catch (Exception e) {
            logger.error("订单关联关系删除失败");
            return AjaxObject.newError("订单关联关系删除失败").toJson();
        }
    }
}
