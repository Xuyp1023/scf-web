package com.betterjr.modules.asset;

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
import com.betterjr.modules.order.IScfOrderService;
import com.betterjr.modules.order.ScfOrderController;

@Controller
@RequestMapping("/Scf/Asset")
public class AssetController {

    private static final Logger logger = LoggerFactory.getLogger(ScfOrderController.class);
    
    @Reference(interfaceClass = IScfAssetService.class)
    private IScfAssetService assetService;
    
    /**
     * 查询可以用于融资和询价的基础资产信息
     * @param custNo
     * @param dataType 关联的基础数据的类型1订单2票据3应收账款4发票5贸易合同6运输单单据类型
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryCanUseBaseData", method = RequestMethod.POST)
    public @ResponseBody String queryCanUseBaseData( Long custNo,String dataType,String flag, int pageNum, int pageSize) {
        logger.info("订单信息修改,入参：  custNo=" + custNo+"  ;dataType="+dataType);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return assetService.webQueryCanUseBaseData(custNo, dataType, pageNum, pageSize,flag);
            }
        }, "订单信息编辑失败", logger);
    } 
}
