package com.betterjr.modules.productconfig;

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
@RequestMapping(value = "/Scf/ProductConfig")
public class ProductConfigController {
	
    private static final Logger logger = LoggerFactory.getLogger(ProductConfigController.class);
    
    @Reference(interfaceClass = IScfProductConfigService.class)
    private IScfProductConfigService productConfigService;

    @RequestMapping(value = "/addProductConfig", method = RequestMethod.POST)
    public @ResponseBody String addProductConfig(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("保存融资产品信息,入参：" + anMap.toString());
        return exec(() -> productConfigService.webAddProductConfig(anMap), "融资产品信息保存失败", logger);
    }
    
    @RequestMapping(value = "/saveModifyProductConfig", method = RequestMethod.POST)
    public @ResponseBody String saveModifyProductConfig(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("融资产品信息查询,入参：" + anMap.toString());
        return exec(() -> productConfigService.webSaveModifyProductConfig(anMap, id), "修改融资产品信息失败", logger);
    }
    
    @RequestMapping(value = "/queryProductConfig", method = RequestMethod.POST)
    public @ResponseBody String queryProductConfig(HttpServletRequest request, int pageNum, int pageSize,int flag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询产品列表,入参：" + anMap.toString());
        return exec(() -> productConfigService.webQueryProductConfig(anMap, flag, pageNum, pageSize), "融资产品信息查询失败", logger);
    }
    
    @RequestMapping(value = "/findProductConfig", method = RequestMethod.POST)
    public @ResponseBody String findProductConfig(HttpServletRequest request, String productCode) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("融资产品信息查询,入参：" + anMap.toString());
        return exec(() -> productConfigService.webFindProductConfig(productCode), "融资产品信息查询失败", logger);
    }
    
    @RequestMapping(value = "/findAssetDictAttachRelationList", method = RequestMethod.POST)
    public @ResponseBody String findAssetDictAttachRelationList(HttpServletRequest request, String productCode) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info(" 查询资产类型与附件关系列表,入参：" + anMap.toString());
        return exec(() -> productConfigService.webFindAssetDictAttachRelationList(anMap), "查询资产类型与附件关系列表失败", logger);
    }

    @RequestMapping(value = "/addAssetDictAttachRelation", method = RequestMethod.POST)
    public @ResponseBody String addAssetDictAttachRelation(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("保存产品资产类型关联的附件类型关系" + anMap.toString());
        return exec(() -> productConfigService.webAddAssetDictAttachRelation(anMap), "保存产品资产类型关联的附件类型关系失败", logger);
    }
    
    @RequestMapping(value = "/addProductCoreRelation", method = RequestMethod.POST)
    public @ResponseBody String addProductCoreRelation(HttpServletRequest request, String productCode, String custNo) {
        logger.info("保存产品与核心企业关系" );
        return exec(() -> productConfigService.webAddProductCoreRelation(productCode, custNo), "保存产品与核心企业关系失败", logger);
    }
    
    @RequestMapping(value = "/addProductAssetDictRelation", method = RequestMethod.POST)
    public @ResponseBody String AddProductAssetDictRelation(HttpServletRequest request, String productCode, String listId) {
        //Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("保存产品与资产类型关系表" + productCode);
        return exec(() -> productConfigService.webAddProductAssetDictRelation(productCode, listId), "保存产品与资产类型关系表失败", logger);
    }
    
    @RequestMapping(value = "/findAssetDictByProduct", method = RequestMethod.POST)
    public @ResponseBody String findAssetDictByProduct(HttpServletRequest request, String productCode) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询产品的附属品关系,入参：" + anMap.toString());
        return exec(() -> productConfigService.webFindAssetDictByProduct(productCode), "查询产品的附属品关系", logger);
    }
    
    @RequestMapping(value = "/queryCoreByProduct", method = RequestMethod.POST)
    public @ResponseBody String queryCoreByProduct(HttpServletRequest request, String productCode, int pageNum, int pageSize,int flag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询资产类型列表,入参：" + anMap.toString());
        return exec(() -> productConfigService.webQueryCoreByProduct(productCode, flag, pageNum, pageSize), "查询资产类型列表失败", logger);
    }
    
    @RequestMapping(value = "/findAssetDict", method = RequestMethod.POST)
    public @ResponseBody String findAssetDict(HttpServletRequest request, Long factorNo) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询资产类型列表,入参：" + anMap.toString());
        return exec(() -> productConfigService.webFindAssetDict(factorNo), "查询资产类型列表失败", logger);
    }
    
    @RequestMapping(value = "/queryAssetDict", method = RequestMethod.POST)
    public @ResponseBody String queryAssetDict(HttpServletRequest request, Long factorNo, int pageNum, int pageSize,int flag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询资产类型列表,入参：" + anMap.toString());
        return exec(() -> productConfigService.webQueryAssetDict(factorNo, flag, pageNum, pageSize), "查询资产类型列表失败", logger);
    }


}


