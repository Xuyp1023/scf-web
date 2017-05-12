package com.betterjr.modules.commissionfile;

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

@Controller
@RequestMapping(value = "/Scf/Commissionfiledown")
public class CommissionFileDownController {
    
    private static final Logger logger = LoggerFactory.getLogger(CommissionFileController.class);
    
    @Reference(interfaceClass = ICommissionFileDownService.class)
    private ICommissionFileDownService fileService;
    
    /**
     * 查询佣金文件下载
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryFileList", method = RequestMethod.POST)
    public @ResponseBody String queryFileList(HttpServletRequest request,  String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金文件信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webQueryFileDownList(anMap, flag, pageNum, pageSize);
            }
        }, "佣金文件查询失败", logger);
    }

}
