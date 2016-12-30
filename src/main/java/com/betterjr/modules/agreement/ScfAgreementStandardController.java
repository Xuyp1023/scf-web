package com.betterjr.modules.agreement;

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
import com.betterjr.common.web.Servlets;

/**
 * 标准合同管理
 * 
 * @author wudy
 *
 */
@Controller
@RequestMapping(value = "/Scf/Agreement")
public class ScfAgreementStandardController {

    private static final Logger logger = LoggerFactory.getLogger(ScfAgreementStandardController.class);

    @Reference(interfaceClass = IScfAgreementStandardService.class)
    private IScfAgreementStandardService agreementStandardService;

    /**
     * 标准合同登记
     */
    @RequestMapping(value = "/addAgreementStandard", method = RequestMethod.POST)
    public @ResponseBody String addAgreementStandard(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("标准合同登记,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(() -> agreementStandardService.webAddAgreementStandard(anMap), "标准合同登记失败！", logger);
    }

    /**
     * 登记标准合同查询
     */
    @RequestMapping(value = "/queryRegisteredAgreementStandard", method = RequestMethod.POST)
    public @ResponseBody String queryRegisteredAgreementStandard(int pageNum, int pageSize, String flag) {
        logger.info("登记标准合同查询");
        return ControllerExceptionHandler.exec(() -> agreementStandardService.webQueryRegisteredAgreementStandard(pageNum, pageSize, flag),
                "登记标准合同查询失败！", logger);
    }

    /**
     * 标准合同编辑
     */
    @RequestMapping(value = "/saveModifyAgreementStandard", method = RequestMethod.POST)
    public @ResponseBody String saveModifyAgreementStandard(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("标准合同编辑,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(() -> agreementStandardService.webSaveModifyAgreementStandard(anMap, id), "标准合同编辑失败！", logger);
    }

    /**
     * 标准合同删除
     */
    @RequestMapping(value = "/saveDeleteAgreementStandard", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteAgreementStandard(Long id) {
        logger.info("标准合同删除,入参:" + id);
        return ControllerExceptionHandler.exec(() -> agreementStandardService.webSaveDeleteAgreementStandard(id), "标准合同删除失败！", logger);
    }

    /**
     * 审核标准合同查询
     */
    @RequestMapping(value = "/queryAgreementStandardByStatus", method = RequestMethod.POST)
    public @ResponseBody String queryAgreementStandardByStatus(String businStatus,int pageNum, int pageSize, String flag) {
        logger.info("审核标准合同查询");
        return ControllerExceptionHandler.exec(() -> agreementStandardService.webQueryAgreementStandardByStatus(businStatus,pageNum, pageSize, flag), "审核标准合同查询失败！", logger);
    }

    /**
     * 标准合同启用
     */
    @RequestMapping(value = "/saveEnableAgreementStandard", method = RequestMethod.POST)
    public @ResponseBody String saveEnableAgreementStandard(Long id) {
        logger.info("标准合同启用,入参:id=" + id);
        return ControllerExceptionHandler.exec(() -> agreementStandardService.webSaveEnableAgreementStandard(id), "标准合同启用失败！", logger);
    }

    /**
     * 标准合同停用
     */
    @RequestMapping(value = "/saveDisableAgreementStandard", method = RequestMethod.POST)
    public @ResponseBody String saveDisableAgreementStandard(Long id) {
        logger.info("标准合同停用,入参:id=" + id);
        return ControllerExceptionHandler.exec(() -> agreementStandardService.webSaveDisableAgreementStandard(id), "标准合同停用失败！", logger);
    }

    /**
     * 标准合同查询
     */
    @RequestMapping(value = "/queryAgreementStandard", method = RequestMethod.POST)
    public @ResponseBody String queryAgreementStandard(HttpServletRequest request, int pageNum, int pageSize, String flag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("标准合同查询,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(() -> agreementStandardService.webQueryAgreementStandard(anMap, pageNum, pageSize, flag), "标准合同查询失败！", logger);
    }
}
