package com.betterjr.modules.product;

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
@RequestMapping("/Scf/Product")
public class ScfProductController {

    private static final Logger logger = LoggerFactory.getLogger(ScfProductController.class);

    @Reference(interfaceClass = IScfProductService.class)
    private IScfProductService scfProductService;

    @RequestMapping(value = "/queryProduct", method = RequestMethod.POST)
    public @ResponseBody String queryProduct(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("融资产品信息查询,入参：" + anMap.toString());
        return exec(() -> scfProductService.webQueryProduct(anMap, flag, pageNum, pageSize), "融资产品信息查询失败", logger);
    }

    @RequestMapping(value = "/queryProductByCore", method = RequestMethod.POST)
    public @ResponseBody String queryProductByCore(Long coreCustNo, String flag, int pageNum, int pageSize) {
        logger.info("融资产品信息查询入参,coreCustNo= " + coreCustNo);
        return exec(() -> scfProductService.webQueryProduct(coreCustNo, flag, pageNum, pageSize), "融资产品信息查询失败", logger);
    }

    @RequestMapping(value = "/queryProductKeyAndValue", method = RequestMethod.POST)
    public @ResponseBody String queryProductKeyAndValue(Long coreCustNo, Long factorNo) {
        logger.info("融资产品下拉列表查询,入参：" + " anCoreCustNo=" + coreCustNo + ",factorNo= " + factorNo);
        return exec(() -> scfProductService.webQueryProductKeyAndValue(coreCustNo, factorNo), "融资产品信息查询失败", logger);
    }

    @RequestMapping(value = "/findProductByCode", method = RequestMethod.POST)
    public @ResponseBody String findProductByCode(Long coreCustNo, Long factorNo, String productCode) {
        logger.info("融资产品信息查询入参,coreCustNo= " + coreCustNo + ", factorNo= " + factorNo + ",productId= " + productCode);
        return exec(() -> scfProductService.webFindProductByCode(coreCustNo, factorNo, productCode), "融资产品信息查询失败",
                logger);
    }

    @RequestMapping(value = "/findProductById", method = RequestMethod.POST)
    public @ResponseBody String findProductById(Long id) {
        logger.info("融资产品信息查询入参,id= " + id);
        return exec(() -> scfProductService.webFindProductById(id), "融资产品信息查询失败", logger);
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public @ResponseBody String addProduct(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("融资产品录入,入参：" + anMap.toString());
        return exec(() -> scfProductService.webAddProduct(anMap), "融资产品录入失败", logger);
    }

    @RequestMapping(value = "/modifyProduct", method = RequestMethod.POST)
    public @ResponseBody String modifyProduct(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("融资产品修改,入参：" + anMap.toString());
        return exec(() -> scfProductService.webSaveModifyProduct(anMap, id), "融资产品修改失败", logger);
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public @ResponseBody String deleteProduct(Long id) {
        logger.info("融资产品删除,入参：" + id);
        return exec(() -> scfProductService.webSaveDeleteProduct(id), "融资产品删除失败", logger);
    }

    @RequestMapping(value = "/shelvesProduct", method = RequestMethod.POST)
    public @ResponseBody String shelvesProduct(Long id) {
        logger.info("融资产品上架,入参：" + id);
        return exec(() -> scfProductService.webSaveShelvesProduct(id), "融资产品上架失败", logger);
    }

    @RequestMapping(value = "/offlineProduct", method = RequestMethod.POST)
    public @ResponseBody String offlineProduct(Long id) {
        logger.info("融资产品下架,入参：" + id);
        return exec(() -> scfProductService.webSaveOfflineProduct(id), "融资产品下架失败", logger);
    }

}
