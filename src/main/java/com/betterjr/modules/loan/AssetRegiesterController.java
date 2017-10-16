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
@RequestMapping(value = "/Scf/AssetRegiester")
public class AssetRegiesterController {

    private static final Logger logger = LoggerFactory.getLogger(AssetRegiesterController.class);

    @Reference(interfaceClass = IscfAssetRegisterService.class)
    private IscfAssetRegisterService assetRegisterService;

    @RequestMapping(value = "/addAssetCheck", method = RequestMethod.POST)
    public @ResponseBody String addAssetCheck(HttpServletRequest request, String fileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("保存资产检查信息,入参：" + anMap.toString());
        return exec(() -> assetRegisterService.webAddAssetCheck(anMap, fileList), "保存资产中登网检查信息失败", logger);
    }

    @RequestMapping(value = "/findAssetCheck", method = RequestMethod.POST)
    public @ResponseBody String findAssetCheck(String requestNo) {
        logger.info("查询资产检查信息,入参：" + requestNo);
        return exec(() -> assetRegisterService.webFindAssetCheck(requestNo), "查询资产中登网检查信息失败", logger);
    }

    @RequestMapping(value = "/addAssetRegiester", method = RequestMethod.POST)
    public @ResponseBody String addAssetRegiester(HttpServletRequest request, String fileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("保存资产登记信息,入参：" + anMap.toString());
        return exec(() -> assetRegisterService.webAddAssetRegister(anMap, fileList), "保存资产中登网信息失败", logger);
    }

    @RequestMapping(value = "/findAssetRegiester", method = RequestMethod.POST)
    public @ResponseBody String findAssetRegiester(String requestNo) {
        logger.info("查询资产登记信息,入参：" + requestNo);
        return exec(() -> assetRegisterService.webFindAssetRegister(requestNo), "查询资产中登网信息失败", logger);
    }
}
