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
 * 合同类型管理
 * @author wudy
 */
@Controller
@RequestMapping(value = "/Scf/Agreement")
public class ScfAgreementTypeController {

    private static final Logger logger = LoggerFactory.getLogger(ScfAgreementTypeController.class);

    @Reference(interfaceClass = IScfAgreementTypeService.class)
    private IScfAgreementTypeService agreementTypeService;

    /**
     * 合同类型登记
     */
    @RequestMapping(value = "/addAgreementType", method = RequestMethod.POST)
    public @ResponseBody String addAgreementType(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("合同类型登记,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(() -> agreementTypeService.webAddAgreementType(anMap), "合同类型登记出错！",
                logger);
    }

    /**
     * 登记合同类型查询
     */
    @RequestMapping(value = "/queryRegisteredAgreementType", method = RequestMethod.POST)
    public @ResponseBody String queryRegisteredAgreementType(int pageNum, int pageSize, String flag) {
        logger.info("合同类型登记查询");
        return ControllerExceptionHandler.exec(
                () -> agreementTypeService.webQueryRegisteredAgreementType(pageNum, pageSize, flag), "合同类型登记查询失败",
                logger);
    }

    /**
     * 编辑合同类型
     */
    @RequestMapping(value = "/saveModifyAgreementType", method = RequestMethod.POST)
    public @ResponseBody String saveModifyAgreementType(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("编辑合同类型,入参:" + anMap);
        return ControllerExceptionHandler.exec(() -> agreementTypeService.webSaveModifyAgreementType(anMap, id),
                "编辑合同类型错误", logger);
    }

    /**
     * 删除合同类型
     */
    @RequestMapping(value = "/saveDeleteAgreementType", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteAgreementType(Long id) {
        logger.info("删除合同类型,入参:id=" + id);
        return ControllerExceptionHandler.exec(() -> agreementTypeService.webSaveDeleteAgreementType(id), "删除合同类型错误",
                logger);
    }

    /**
     * 查询待启用合同
     */
    @RequestMapping(value = "/queryUnEnableAgreementType", method = RequestMethod.POST)
    public @ResponseBody String queryUnEnableAgreementType(int pageNum, int pageSize, String flag) {
        logger.info("查询待启用合同类型");
        return ControllerExceptionHandler.exec(
                () -> agreementTypeService.webQueryUnEnableAgreementType(pageNum, pageSize, flag), "查询待启用合同类型失败",
                logger);
    }

    /**
     * 查询已启用合同
     */
    @RequestMapping(value = "/findEnableAgreementType", method = RequestMethod.POST)
    public @ResponseBody String queryEnableAgreementType() {
        logger.info("查询待启用合同类型");
        return ControllerExceptionHandler.exec(() -> agreementTypeService.webFindEnableAgreementType(), "查询已启用合同类型失败",
                logger);
    }

    /**
     * 启用合同类型
     */
    @RequestMapping(value = "/saveEnableAgreementType", method = RequestMethod.POST)
    public @ResponseBody String saveEnableAgreementType(Long id) {
        logger.info("启用合同类型,入参:id=" + id);
        return ControllerExceptionHandler.exec(() -> agreementTypeService.webSaveEnableAgreementType(id), "启用合同类型错误",
                logger);
    }

    /**
     * 查询合同类型
     */
    @RequestMapping(value = "/queryAgreementType", method = RequestMethod.POST)
    public @ResponseBody String queryAgreementType(HttpServletRequest request, int pageNum, int pageSize, String flag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询合同类型,入参:" + anMap);
        return ControllerExceptionHandler.exec(
                () -> agreementTypeService.webQueryAgreementType(anMap, pageNum, pageSize, flag), "查询合同类型失败", logger);
    }
}
