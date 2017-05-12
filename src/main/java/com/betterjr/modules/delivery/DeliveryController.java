package com.betterjr.modules.delivery;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.utils.BetterStringUtils;
import com.betterjr.common.web.ControllerExceptionHandler;
import com.betterjr.common.web.Servlets;
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;

@Controller
@RequestMapping(value = "/Scf/Delivery")
public class DeliveryController {

    
    private static final Logger logger = LoggerFactory.getLogger(DeliveryController.class);

    @Reference(interfaceClass = IDeliveryService.class)
    private IDeliveryService deliveryService;
    
    
    /**
     * 投递账单信息查询
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @param isPostCust   true   是青海移动    false  是平台
     * @return
     * 投递日期   postDate
     * 接受企业(操作企业)：  postCustNo
     * 状态   businStatus
     */
    @RequestMapping(value = "/queryDeliveryRecordList", method = RequestMethod.POST)
    public @ResponseBody String queryDeliveryRecordList(HttpServletRequest request,  String flag, int pageNum, int pageSize,boolean isPostCust) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("投递账单信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryService.webQueryFileList(anMap, flag, pageNum, pageSize,isPostCust);
            }
        }, "投递账单查询失败", logger);
    }
    
    /**
     * 查询单条投递账单信息  
     * @param refNo  凭证编号
     * @return
     */
    @RequestMapping(value = "/findDeliveryRecord", method = RequestMethod.POST)
    public @ResponseBody String findDeliveryRecord(String refNo) {
        logger.info("投递账单信息查询,入参： ref=" + refNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryService.webFindDeliveryRecord(refNo);
            }
        }, "投递账单查询失败", logger);
    }
    
    /**
     * 查询月账单信息
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryMonthlyRecordList", method = RequestMethod.POST)
    public @ResponseBody String queryMonthlyRecordList(HttpServletRequest request,  String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = replaceMapValueLine(Servlets.getParametersStartingWith(request, ""));
        logger.info("月账单信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryService.webQueryMonthlyRecordList(anMap, flag, pageNum, pageSize);
            }
        }, "月账单信息查询失败", logger);
    }
    
    /**
     * 投递账单登记
     * @param request
     * @return
     * monthList  存放月账单id,多条以,分割
     * ownCustNo   存放的是核心企业Id
     */
    @RequestMapping(value = "/saveAddDeliveryRecord", method = RequestMethod.POST)
    public @ResponseBody String saveAddDeliveryRecord(HttpServletRequest request) {
        Map paramMap = Servlets.getParametersStartingWith(request, "");
        logger.info("投递账单登记,入参：" + paramMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryService.webAddDeliveryRecord(paramMap);
            }
        }, "投递账单登记失败", logger);
    }
    
    /**
     * 投递账单明细信息移除
     * @param request
     * @return
     * statementId  投递详情明细项的id
     */
    @RequestMapping(value = "/saveDeleteStatement", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteStatement(Long statementId) {
        logger.info("投递账单修改,入参：" + statementId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryService.webSaveDeleteStatement(statementId);
            }
        }, "投递账单修改失败", logger);
    }
    
    /**
     * 删除某条对账单信息
     * @param recordId
     * @return
     */
    @RequestMapping(value = "/saveDeleteRecord", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteRecord(Long recordId) {
        logger.info("投递账单修改,入参：" + recordId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryService.webSaveDeleteRecord(recordId);
            }
        }, "投递账单修改失败", logger);
    }
    
    /**
     * 平台投递投递账单
     * @param refNo 投递主表凭证编号
     * @return
     */
    @RequestMapping(value = "/saveExpressDelivery", method = RequestMethod.POST)
    public @ResponseBody String saveExpressDelivery(String refNo,String description) {
        logger.info("投递账单投递,入参：" + refNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryService.webSaveExpressDelivery(refNo,description);
            }
        }, "投递账单投递失败", logger);
    }
    
    
    /**
     * 核心企业投递账单
     * @param refNo
     * @return
     */
    @RequestMapping(value = "/saveConfirmDelivery", method = RequestMethod.POST)
    public @ResponseBody String saveConfirmDelivery(String refNo) {
        logger.info("投递账单确认,入参：" + refNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryService.webSaveConfirmDelivery(refNo);
            }
        }, "投递账单确认失败", logger);
    }
    
    private Map<String,Object> replaceMapValueLine(Map<String,Object> anMap){
        
        Map<String, Object> params = new TreeMap<String, Object>();
        
        for(String key :anMap.keySet()) {
            
            if(anMap.get(key) !=null){
                String tmpStr =  anMap.get(key).toString();
                if (BetterStringUtils.isNotBlank(tmpStr)) {
                     params.put(key, tmpStr.replace("-", ""));
                }
            }
            
        }
        return params;
        
    }
    
}
