package com.betterjr.modules.template;

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
import com.betterjr.common.web.ControllerExceptionHandler;
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.contract.IScfContractTemplateService;

@Controller
@RequestMapping(value = "/Scf/Template")
public class ScfContractTemplateController {

	private static final Logger logger = LoggerFactory.getLogger(ScfContractTemplateController.class);

	@Reference(interfaceClass = IScfContractTemplateService.class)
	private IScfContractTemplateService templateService;

	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveTemplate(HttpServletRequest request) {
		Map<String, Object> param = Servlets.getParametersStartingWith(request, "");
		logger.info("合同模板保存:" + param);
		
		return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webSaveTemplate(param);
            }
        }, "合同模板保存失败", logger);
		
	}
	
	@RequestMapping(value = "/saveModifyTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveModifyTemplate(HttpServletRequest request, Long id) {
		Map<String, Object> param = Servlets.getParametersStartingWith(request, "");
		logger.info("合同模板修改:" + param);
		
		return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webSaveModifyTemplate(param, id);
            }
        }, "合同模板修改失败", logger);
		
		
	}
	
	@RequestMapping(value = "/findTemplateByType", method = RequestMethod.POST)
	public @ResponseBody String findTemplateByType(HttpServletRequest request, Long factorNo, String type) {
		Map<String, Object> param = Servlets.getParametersStartingWith(request, "");
		logger.info("合同模板查询:" + param);
		try {
			return templateService.webFindTemplateByType(factorNo, type);
		} catch (Exception ex) {
			logger.error("合同模板查询失败：", ex);
			return AjaxObject.newError("合同模板查询失败" + ex.getMessage()).toJson();
		}
	}
	
	@RequestMapping(value = "/findTemplate", method = RequestMethod.POST)
	public @ResponseBody String findTemplate(HttpServletRequest request, Long id) {
		logger.info("查询合同模板:" + id);
		try {
			return templateService.webFindTemplate(id);
		} catch (Exception ex) {
			logger.error("合同模板查询失败：", ex);
			return AjaxObject.newError("合同模板查询失败" + ex.getMessage()).toJson();
		}
	}
	
	@RequestMapping(value = "/queryTemplate", method = RequestMethod.POST)
	public @ResponseBody String queryTemplate(HttpServletRequest request, int flag, int pageNum, int pageSize) {
		Map<String, Object> param = Servlets.getParametersStartingWith(request, "");
		logger.info("查询合同模板列表:" + param);
		return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webQueryTemplate(param, flag, pageNum, pageSize);
            }
        }, "合同模板列表查询失败", logger);
		
	}
}
