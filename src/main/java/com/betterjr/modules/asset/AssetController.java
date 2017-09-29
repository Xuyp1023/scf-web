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
     * 
     * @param custNo
     * @param dataType
     *            关联的基础数据的类型1订单2票据3应收账款4发票5贸易合同6运输单单据类型
     * @param ids
     *            存放不需要查询的id集合
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryCanUseBaseData", method = RequestMethod.POST)
    public @ResponseBody String queryCanUseBaseData(Long custNo, Long coreCustNo, String dataType, String ids, String flag, int pageNum,
            int pageSize) {
        logger.info("资产查询可用的基础资产信息,入参：  custNo=" + custNo + "  ;dataType=" + dataType);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return assetService.webQueryCanUseBaseData(custNo, coreCustNo, dataType, ids, pageNum, pageSize, flag);
            }
        }, "资产查询可用的基础资产信息失败", logger);
    }

    /**
     * 查询资产详细信息
     * 
     * @param assetId
     * @return
     */
    @RequestMapping(value = "/findAssetById", method = RequestMethod.POST)
    public @ResponseBody String findAssetById(Long assetId) {
        logger.info("资产查询,入参：  assetId=" + assetId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return assetService.webFindAssetById(assetId);
            }
        }, "资产查询信息失败", logger);
    }

    /**
     * 通过应收应付账款新增资产
     * 
     * @param assetId
     * @return
     */
    @RequestMapping(value = "/saveAddAssetByReceivable", method = RequestMethod.POST)
    public @ResponseBody String saveAddAssetByReceivable(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return assetService.webSaveAddAssetByReceivable(anMap);
            }
        }, "资产查询信息失败", logger);
    }

    /**
     * 资产驳回或作废
     * 
     * @param assetId
     * @return
     */
    @RequestMapping(value = "/saveRejectOrBreakAsset", method = RequestMethod.POST)
    public @ResponseBody String saveRejectOrBreakAsset(Long assetId) {
        logger.info("资产驳回,入参：  assetId=" + assetId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return assetService.webSaveRejectOrBreakAsset(assetId);
            }
        }, "资产驳回失败", logger);
    }

    /**
     * 资产转让
     * 
     * @param assetId
     * @return
     */
    @RequestMapping(value = "/SaveAssignmentAssetToFactory", method = RequestMethod.POST)
    public @ResponseBody String SaveAssignmentAssetToFactory(Long assetId) {
        logger.info("资产转让,入参：  assetId=" + assetId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return assetService.webSaveAssignmentAssetToFactory(assetId);
            }
        }, "资产转让失败", logger);
    }
}
