package com.betterjr.modules.loan;

import static com.betterjr.common.web.ControllerExceptionHandler.exec;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping(value = "/Scf/RequestTemp")
public class RequestTempController {
	private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
	
	@Reference(interfaceClass = IScfRequestTempService.class)
	private IScfRequestTempService scfRequestTempService;

	@RequestMapping(value = "/addRequestTemp", method = RequestMethod.POST)
	public @ResponseBody String addRequestTemp(HttpServletRequest request) {
		Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
		logger.info("添加融资申请，入参:" + map.toString());
		return exec(() -> scfRequestTempService.webAddRequestTemp(map), "保存融资申请失败", logger);
	}

	@RequestMapping(value = "/saveModifyRequestTemp", method = RequestMethod.POST)
	public @ResponseBody String saveModifyRequestTemp(HttpServletRequest request, String requestNo) {
		Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
		logger.info("修改融资申请，入参:" + map.toString());
		return exec(() -> scfRequestTempService.webSaveModifyRequestTemp(map, requestNo), "修改融资申请失败", logger);
	}
	
	@RequestMapping(value = "/queryRequestTempList", method = RequestMethod.POST)
	public @ResponseBody String queryRequestTempList(HttpServletRequest request, int flag, int pageNum, int pageSize) {
		Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
		logger.info("分页查询融资申请，入参:" + map.toString());
		return exec(() -> scfRequestTempService.webQueryRequestTempList(map, flag, pageNum, pageSize), "查询融资信息失败", logger);
	}
}
