package com.betterjr.modules.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.web.AjaxObject;

@Controller
@RequestMapping("/Scf/Product")
public class ScfProductController {

    private static final Logger logger = LoggerFactory.getLogger(ScfProductController.class);

    @Reference(interfaceClass = IScfProductService.class)
    private IScfProductService scfProductService;

    @RequestMapping(value = "/queryProductKeyAndValue", method = RequestMethod.POST)
    public @ResponseBody String queryProductKeyAndValue(Long coreCustNo, Long factorNo) {
        logger.info("融资产品下拉列表查询,入参：" + " anCoreCustNo " + coreCustNo + ",factorNo " + factorNo);
        try {

            return scfProductService.webQueryProductKeyAndValue(coreCustNo, factorNo);
        }
        catch (Exception e) {
            logger.error("融资产品下拉列表查询失败", e);
            return AjaxObject.newError("融资产品下拉列表查询失败").toJson();
        }
    }

}
