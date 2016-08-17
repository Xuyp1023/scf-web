package com.betterjr.modules.order;

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
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping("/Scf/Order")
public class ScfOrderController {
    
    private static final Logger logger = LoggerFactory.getLogger(ScfOrderController.class);
    
    @Reference(interfaceClass = IScfOrderService.class)
    private IScfOrderService scfOrderService;
    
    @RequestMapping(value = "/modifyOrder", method = RequestMethod.POST)
    public @ResponseBody String modifyOrder(HttpServletRequest request, Long id, String fileList, String otherFileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单信息修改,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webSaveModifyOrder(anMap, id, fileList, otherFileList);
            }
        }, "订单信息编辑失败", logger);
    }
    
    /**
     * 订单分页查询
     * @param isOnlyNormal 是否过滤，仅查询正常未融资数据 1：未融资 0：查询所有
     * @return
     */
    @RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
    public @ResponseBody String queryOrder(HttpServletRequest request,String isOnlyNormal, String flag, int pageNum, int pageSize){
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webQueryOrder(anMap, isOnlyNormal, flag, pageNum, pageSize);
            }
        }, "订单信息查询失败", logger);
    }
    
    /**
     * 订单无分页查询
     * @param isOnlyNormal 是否过滤，仅查询正常未融资数据 1：未融资 0：查询所有
     * @return
     */
    @RequestMapping(value = "/findOrderList", method = RequestMethod.POST)
    public @ResponseBody String findOrderList(String custNo,String isOnlyNormal){
        logger.info("订单信息查询,入参：custNo=" + custNo + " isOnlyNormal=" + isOnlyNormal);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webFindOrderList(custNo, isOnlyNormal);
            }
        }, "订单信息查询失败", logger);
    }
    
    /**
     * 通过融资申请信息，订单无分页查询
     * @param anRequestNo   融资申请编号
     * @param anRequestType  1：订单，2:票据;3:应收款;4:经销商
     */
    @RequestMapping(value = "/findInfoListByRequest", method = RequestMethod.POST)
    public @ResponseBody String findInfoListByRequest(String requestNo,String requestType){
        logger.info("融资资料信息查询,入参：requestNo=" + requestNo + " requestType=" + requestType);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webFindInfoListByRequest(requestNo, requestType);
            }
        }, "融资资料信息查询失败", logger);
    }
}
