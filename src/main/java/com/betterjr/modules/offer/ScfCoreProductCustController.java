package com.betterjr.modules.offer;

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
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.modules.supplieroffer.IScfCoreProductCustService;

@Controller
@RequestMapping("/Scf/CoreProductCust")
public class ScfCoreProductCustController {

    private static final Logger logger = LoggerFactory.getLogger(ScfSupplierOfferController.class);
    
    @Reference(interfaceClass =IScfCoreProductCustService.class)
    private IScfCoreProductCustService productService;
    
    
    /**
     * 核心企业查询可用分配给供应商的保理产品
     * @param anCoreCustNo
     * @return
     */
    @RequestMapping(value = "/queryProductConfigByCore", method = RequestMethod.POST)
    public @ResponseBody String queryProductConfigByCore(HttpServletRequest request,Long custNo,Long coreCustNo) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("产品查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return productService.webQueryProductConfigByCore(custNo,coreCustNo);
            }
        }, "产品查询失败", logger);
    }
    
    
    /**
     * 更新融资产品列表
     * @param anCustNo
     * @param anCoreCustNo
     * @param anProductCodes
     * @return
     */
    @RequestMapping(value = "/saveAddAndUpdateProduct", method = RequestMethod.POST)
    public @ResponseBody String saveAddAndUpdateProduct(HttpServletRequest request,Long custNo,Long coreCustNo,String productCodes) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("产品修改,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return productService.webSaveAddAndUpdateProduct(custNo,coreCustNo,productCodes);
            }
        }, "产品修改失败", logger);
    }
    
    /**
     * 查询核心企业分配给供应商的保理产品
     * @param request
     * @param custNo
     * @param coreCustNo
     * @return
     */
    @RequestMapping(value = "/queryCanUseProduct", method = RequestMethod.POST)
    public @ResponseBody String queryCanUseProduct(HttpServletRequest request,Long custNo,Long coreCustNo) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("产品查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return productService.webQueryCanUseProduct(custNo, coreCustNo);
            }
        }, "产品查询失败", logger);
    }
    
}
