package com.betterjr.modules.businType;

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
import com.betterjr.modules.order.ScfOrderController;

@Controller
@RequestMapping("/Scf/BusinType")
public class BusinTypeController {

    private static final Logger logger = LoggerFactory.getLogger(ScfOrderController.class);

    @Reference(interfaceClass = IScfBusinTypeService.class)
    private IScfBusinTypeService businTypeService;

    @RequestMapping(value = "/queryEffectiveBusinType", method = RequestMethod.POST)
    public @ResponseBody String queryEffectiveBusinType(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询业务类型,入参= " + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return businTypeService.webQueryBusinType(anMap);
            }
        }, "查询业务类型失败", logger);
    }

}
