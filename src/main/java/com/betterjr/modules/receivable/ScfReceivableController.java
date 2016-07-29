package com.betterjr.modules.receivable;

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
import com.betterjr.modules.receivable.IScfReceivableService;

@Controller
@RequestMapping("/Scf/Receivable")
public class ScfReceivableController {

    private static final Logger logger = LoggerFactory.getLogger(ScfReceivableController.class);
    
    @Reference(interfaceClass = IScfReceivableService.class)
    private IScfReceivableService scfReceivableService;
    
    @RequestMapping(value = "/modifyReceivable", method = RequestMethod.POST)
    public @ResponseBody String modifyReceivable(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款编辑,入参:" + anMap.toString());
        try{
            return scfReceivableService.webSaveModifyReceivable(anMap, id);
        }
        catch(Exception e) {
            logger.error("应收账款编辑失败", e);
            return AjaxObject.newError("应收账款编辑失败").toJson();
        }
    }
    
    @RequestMapping(value = "/queryReceivable", method = RequestMethod.POST)
    public @ResponseBody String queryReceivable(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款查询,入参:" + anMap.toString());
        try {
            return scfReceivableService.webQueryReceivable(anMap, flag, pageNum, pageSize);
        }
        catch (Exception e) {
            logger.error("应收账款查询失败", e);
            return AjaxObject.newError("应收账款查询失败").toJson();
        }
    }
        
    
}
