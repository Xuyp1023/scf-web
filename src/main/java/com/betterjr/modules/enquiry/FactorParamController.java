package com.betterjr.modules.enquiry;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.betterjr.common.exception.BytterException;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.param.IScfFactorParamService;

@Controller
@RequestMapping(value = "/Scf/FactorParam")
public class FactorParamController {
    private static final Logger logger = LoggerFactory.getLogger(FactorParamController.class);

    @Reference(interfaceClass = IScfFactorParamService.class)
    private IScfFactorParamService scfFactorParamService;

    @RequestMapping(value = "/saveFactorParam", method = RequestMethod.POST)
    public @ResponseBody String saveFactorParam(HttpServletRequest request) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("保理公司参数保存，入参:" + map.toString());

        try {
            return scfFactorParamService.webSaveFactorParam(map);
        }
        catch (RpcException btEx) {
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("saveFactorParam service failed").toJson();
        }
        catch (Exception ex) {
            logger.error("保理公司参数保存:", ex);
            return AjaxObject.newError("saveFactorParam service failed").toJson();
        }

    }

    @RequestMapping(value = "/loadFactorParam", method = RequestMethod.POST)
    public @ResponseBody String loadFactorParam(HttpServletRequest request, String custNo) {
        logger.info("理公司参数查询，入参:" + custNo);

        try {
            return scfFactorParamService.webLoadFactorParam(custNo);
        }
        catch (Exception ex) {
            logger.error("理公司参数查询:", ex);
            return AjaxObject.newError("loadFactorParam service failed").toJson();
        }
    }

}
