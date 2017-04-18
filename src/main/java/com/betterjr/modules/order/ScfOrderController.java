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
    public @ResponseBody String modifyOrder(HttpServletRequest request, String fileList,boolean confirmFlag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单信息修改,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webSaveModifyOrderDO(anMap,fileList, confirmFlag);
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
                return scfOrderService.webQueryOrderDO(anMap, isOnlyNormal, flag, pageNum, pageSize);
            }
        }, "订单信息查询失败", logger);
    }
    
    /**
     * 订单无分页查询
     * @param isOnlyNormal 是否过滤，仅查询正常未融资数据 1：未融资 0：查询所有
     * @return
     */
    @RequestMapping(value = "/findOrderList", method = RequestMethod.POST)
    public @ResponseBody String findOrderList(HttpServletRequest request,String isOnlyNormal){
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单信息查询,入参：" + anMap + " isOnlyNormal=" + isOnlyNormal);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webFindOrderList(anMap, isOnlyNormal);
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
    
    /**
     * 新增订单
     */
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public @ResponseBody String addOrder(HttpServletRequest request, String fileList, boolean confirmFlag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单信息新增,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webAddOrderDO(anMap, fileList,confirmFlag);
            }
        }, "订单信息新增失败", logger);
    }
    
    /**
     * 检查订单下发票所关联订单是否勾选完成
     */
    @RequestMapping(value = "/checkCompleteInvoice", method = RequestMethod.POST)
    public @ResponseBody String checkCompleteInvoice(String requestType, String idList) {
        logger.info("检查关联发票关系,入参：requestType=" + requestType + "idList=" + idList);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webCheckCompleteInvoice(requestType, idList);
            }
        }, "检查关联发票关系失败", logger);
    }
    
    /**
     * 查询融资申请下面所有附件
     */
    @RequestMapping(value = "/findRequestBaseInfoFileList", method = RequestMethod.POST)
    public @ResponseBody String findRequestBaseInfoFileList(String requestNo) {
        logger.info("查询所有附件,入参：requestNo=" + requestNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webFindRequestBaseInfoFileList(requestNo);
            }
        }, "查询所有附件失败", logger);
    }
    
    /**
     * 根据Id查看订单详情
     */
    @RequestMapping(value = "/findOrderDetailsById", method = RequestMethod.POST)
    public @ResponseBody String findOrderDetailsById(Long id) {
        logger.info("查询订单详情,入参：id=" + id);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webFindOrderDetailsById(id);
            }
        }, "查询查询订单详情", logger);
    }
    
    /**
     * 检查业务所需信息是否完成--贸易合同、发票
     * 1:订单，2:票据;3:应收款;
     */
    @RequestMapping(value = "/checkInfoCompleted", method = RequestMethod.POST)
    public @ResponseBody String checkInfoCompleted(String idList, String requestType) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webCheckInfoCompleted(idList, requestType);
            }
        }, "查询查询订单详情", logger);
    }
    
    @RequestMapping(value = "/checkAgreementStatus", method = RequestMethod.POST)
    public @ResponseBody String checkAgreementStatus(Long acceptBillId) {
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webCheckAgreementStatus(acceptBillId);
            }
        }, "合同状态检查成功", logger);
    }
    
    /**
     * 查询核心企业编号
     */
    @RequestMapping(value = "/findCoreCustNo", method = RequestMethod.POST)
    public @ResponseBody String findRequestBaseInfoFileList(String orderId, String orderType) {
        logger.info("查询核心企业编号,入参：orderId=" + orderId + "orderType:" +orderType);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webFindCoreCustNo(orderId, orderType);
            }
        }, "查询核心企业编号失败", logger);
    }
    
    /**
     * 查询废止状态的所有订单
     * @param request
     * @param isOnlyNormal
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryCanAnnulOrder", method = RequestMethod.POST)
    public @ResponseBody String queryCanAnnulOrder(HttpServletRequest request, String isOnlyNormal, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单已经审核然后想废止的信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webQueryCanAnnulOrder(anMap, isOnlyNormal, flag, pageNum, pageSize);
            }
        }, "订单信息查询失败", logger);
    }
    
    /**
     * 废止当前的单据
     * @param refNo
     * @param version
     * @return
     */
    @RequestMapping(value = "/saveAnnulOrder", method = RequestMethod.POST)
    public @ResponseBody String saveAnnulOrder(String refNo,String version) {
        logger.info("订单信息作废,入参：refNo=" + refNo+"  version:"+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webSaveAnnulOrder(refNo,version);
            }
        }, "订单信息废止失败", logger);
    }
    
    @RequestMapping(value = "/saveAuditOrder", method = RequestMethod.POST)
    public @ResponseBody String saveAuditOrder(String refNo,String version) {
        logger.info("订单审核,入参：refNo=" + refNo +" : version="+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderService.webSaveAuditOrderByRefNoVersion(refNo, version);
            }
        }, "订单审核成功！", logger);
    }
    
    
}
