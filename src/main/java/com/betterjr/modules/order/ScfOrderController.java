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
@RequestMapping("/Scf/Order")
public class ScfOrderController {
    
    private static final Logger logger = LoggerFactory.getLogger(ScfOrderController.class);
    
    @Reference(interfaceClass = IScfOrderService.class)
    private IScfOrderService scfOrderService;
    
    @RequestMapping(value = "/modifyOrder", method = RequestMethod.POST)
    public @ResponseBody String modifyOrder(HttpServletRequest requset, Long id, String fileList, String otherFileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(requset, "");
        logger.info("订单信息修改,入参：" + anMap.toString());
        try{
            return scfOrderService.webSaveModifyOrder(anMap, id, fileList, otherFileList);
        }
        catch(Exception e) {
            logger.error("订单信息编辑失败", e);
            return AjaxObject.newError("订单信息编辑失败").toJson();
        }
    }
    
    @RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
    public @ResponseBody String queryOrder(HttpServletRequest request, String flag, int pageNum, int pageSize){
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单信息查询,入参：" + anMap.toString());
        try{
            return scfOrderService.webQueryOrder(anMap, flag, pageNum, pageSize);
        }
        catch(Exception e) {
            logger.error("订单信息查询失败", e);
            return AjaxObject.newError("订单信息查询失败").toJson();
        }
    }
}
