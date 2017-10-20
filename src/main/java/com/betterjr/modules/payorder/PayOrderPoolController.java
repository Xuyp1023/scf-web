package com.betterjr.modules.payorder;

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

/**
 * 
 * @ClassName: PayOrderPoolController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author xuyp
 * @date 2017年10月20日 下午4:04:49 
 *
 */
@Controller
@RequestMapping("/Scf/PayOrderPool")
public class PayOrderPoolController {

    private static final Logger logger = LoggerFactory.getLogger(PayOrderPoolController.class);

    @Reference(interfaceClass = IPayOrderPoolService.class)
    private IPayOrderPoolService poolService;

    /**
     * 企业查询付款池列表
     * @Title: queryPayPoolList 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param flag
     * @param @param pageNum
     * @param @param pageSize
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午4:33:42
     * GTErequestPayDate
     * LTErequestPayDate
     * factoryNo
     */
    @RequestMapping(value = "/queryPayPoolList", method = RequestMethod.POST)
    public @ResponseBody String queryPayPoolList(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询付款池记录,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return poolService.webQueryPayPoolList(anMap, flag, pageNum, pageSize);
            }
        }, "查询付款池记录失败", logger);

    }

    /**
     *  融资申请完成时新增申请记录信息
     * @Title: saveAddPayRecord 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param requestNo
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午4:31:08
     */
    @RequestMapping(value = "/saveAddPayRecord", method = RequestMethod.POST)
    public @ResponseBody String saveAddPayRecord(HttpServletRequest request, String requestNo) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("新增申请记录,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return poolService.webSaveAddPayRecord(requestNo);
            }
        }, "新增申请记录失败", logger);

    }

}
