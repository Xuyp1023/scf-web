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
 * @ClassName: PayOrderPoolRecordController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author xuyp
 * @date 2017年10月20日 下午4:05:01 
 *
 */
@Controller
@RequestMapping("/Scf/PayOrderPoolRecord")
public class PayOrderPoolRecordController {

    private static final Logger logger = LoggerFactory.getLogger(PayOrderPoolRecordController.class);

    @Reference(interfaceClass = IPayOrderPoolRecordService.class)
    private IPayOrderPoolRecordService recordService;

    /**
     * 查询记录列表
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
     * @date 2017年10月18日 下午4:58:13
     * requestPayDate
     * businStatus
     * factoryNo
     */
    @RequestMapping(value = "/queryRecordPage", method = RequestMethod.POST)
    public @ResponseBody String queryRecordPage(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询记录列表,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return recordService.webQueryRecordPage(anMap, flag, pageNum, pageSize);
            }
        }, "查询记录列表失败", logger);

    }

    /**
     * 根据付款时间，企业，状态查询记录信息
     * @Title: queryRecordList 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午5:00:31
     *  requestPayDate","businStatus","factoryNo
     */
    @RequestMapping(value = "/queryRecordList", method = RequestMethod.POST)
    public @ResponseBody String queryRecordList(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询记录列表,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return recordService.webQueryRecordList(anMap);
            }
        }, "查询记录列表失败", logger);

    }

    /**
     * 根据对应的文件id查询所有的付款进入信息
     * @Title: queryRecordListByFileId 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param sourceFileid  下载付款文件id
     * @param @param businStatus
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午5:06:46
     */
    @RequestMapping(value = "/queryRecordListByFileIdAndBusinStatus", method = RequestMethod.POST)
    public @ResponseBody String queryRecordListByFileIdAndBusinStatus(HttpServletRequest request, Long sourceFileid,
            String businStatus) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询记录列表,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return recordService.webQueryRecordListByFileId(sourceFileid, businStatus);
            }
        }, "查询记录列表失败", logger);

    }

    /**
     * 查询所有生效的付款记录信息
     * @Title: queryRecordListByFileId 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param sourceFileid  下载付款文件id
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午5:08:58
     */
    @RequestMapping(value = "/queryRecordListByFileId", method = RequestMethod.POST)
    public @ResponseBody String queryRecordListByFileId(HttpServletRequest request, Long sourceFileid) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询记录列表,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return recordService.webQueryRecordListByFileId(sourceFileid);
            }
        }, "查询记录列表失败", logger);

    }
}
