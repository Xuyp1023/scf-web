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
@RequestMapping(value = "/Scf/DeliveryStatement")
public class DeliveryStatementController {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryStatementController.class);

    @Reference(interfaceClass = IDeliveryStatementService.class)
    private IDeliveryStatementService deliveryStatementService;
    
    /**
     * 青海移动对账单详情列表查询
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     * billMonth  对账月份
     * 对账企业   ownCustNo
     * expressStatus   状态
     */
    @RequestMapping(value = "/queryDeliveryStatementList", method = RequestMethod.POST)
    public @ResponseBody String queryDeliveryStatementList(HttpServletRequest request,  String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = replaceMapValueLine(Servlets.getParametersStartingWith(request, ""));
        logger.info("投递账单详情信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return deliveryStatementService.webQueryStatementList(anMap, flag, pageNum, pageSize);
            }
        }, "投递账单详情查询失败", logger);
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
