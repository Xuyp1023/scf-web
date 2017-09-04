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
import com.betterjr.modules.supplieroffer.IScfSupplierOfferService;


@Controller
@RequestMapping("/Scf/SupplierOffer")
public class ScfSupplierOfferController {
    
    private static final Logger logger = LoggerFactory.getLogger(ScfSupplierOfferController.class);

    
    @Reference(interfaceClass = IScfSupplierOfferService.class)
    private IScfSupplierOfferService offerService;
    
    /**
     * 供应商利率新增
     * custNo
     * coreCustNo
     * coreCustRate 利率
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveAddOffer", method = RequestMethod.POST)
    public @ResponseBody String saveAddOffer(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("供应商利率新增,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return offerService.webSaveAddOffer(anMap);
            }
        }, "供应商利率新增失败", logger);
    }
    
    /**
     * 供应商利率修改
     * @param custNo
     * @param coreCustNo
     * @param coreCustRate
     * @return
     */
    @RequestMapping(value = "/saveUpdateOffer", method = RequestMethod.POST)
    public @ResponseBody String saveUpdateOffer(Long custNo,Long coreCustNo,double coreCustRate) {
        logger.info("供应商利率修改,入参： custNo=" + custNo +"  coreCustNo="+coreCustNo +"  coreCustRate="+coreCustRate);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return offerService.webSaveUpdateOffer(custNo, coreCustNo, coreCustRate);
            }
        }, "供应商利率修改失败", logger);
    }
    
    
    /**
     * 供应商利率查询
     * coreCustNo
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryOfferList", method = RequestMethod.POST)
    public @ResponseBody String queryOfferList(HttpServletRequest request, String flag, int pageNum, int pageSize){
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("供应商利率查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return offerService.webQueryOfferPage(anMap, flag, pageNum, pageSize);
            }
        }, "供应商利率查询失败", logger);
    }
    
    @RequestMapping(value = "/queryAllCust", method = RequestMethod.POST)
    public @ResponseBody String queryAllCust(Long coreCustNo) {
        logger.info("供应商查询,入参 coreCustNo="+coreCustNo );
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return offerService.webQueryAllCust(coreCustNo);
            }
        }, "供应商查询失败", logger);
    }
    
    /**
     * 通过供应商和核心企业查询利率
     * @param coreCustNo
     * @param custNo
     * @return
     */
    @RequestMapping(value = "/findOffer", method = RequestMethod.POST)
    public @ResponseBody String findOffer(Long coreCustNo,Long custNo) {
        logger.info("供应商利率查询,入参 coreCustNo="+coreCustNo +"custNo="+custNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return offerService.webFindOffer(custNo, coreCustNo);
            }
        }, "供应商利率查询失败", logger);
    }
    
    
    @RequestMapping(value = "/queryAllFactoryByCustNo", method = RequestMethod.POST)
    public @ResponseBody String queryAllFactoryByCustNo(Long custNo){
        
        logger.info("供应商利率查询,入参：  custNo=" + custNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return offerService.webQueryAllFactoryByCustNo(custNo);
            }
        }, "查询失败", logger);
    }
    
}
