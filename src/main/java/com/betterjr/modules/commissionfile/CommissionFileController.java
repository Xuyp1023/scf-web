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
@RequestMapping(value = "/Scf/Commissionfile")
public class CommissionFileController {
    
    
    private static final Logger logger = LoggerFactory.getLogger(CommissionFileController.class);

    @Reference(interfaceClass = ICommissionFileService.class)
    private ICommissionFileService fileService;

    /**
     * 佣金文件上传    
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveAddFile", method = RequestMethod.POST)
    public @ResponseBody String saveAddFile(HttpServletRequest request) {
        Map paramMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金文件登记,入参：" + paramMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webAddCommissionFile(paramMap);
            }
        }, "佣金文件登记失败", logger);
    }
    
    
    /**
     * 查询佣金文件列表
     * @param request
     * @param flag 1：查询文件总的条数
     * @param pageNum 当前页数
     * @param pageSize 每页显示的数量
     * @return
     */
    @RequestMapping(value = "/queryFileList", method = RequestMethod.POST)
    public @ResponseBody String queryFileList(HttpServletRequest request,  String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金文件信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webQueryFileList(anMap, flag, pageNum, pageSize);
            }
        }, "佣金文件查询失败", logger);
    }
    
    /**
     * 查找佣金文件导出模版
     * @return
     */
    @RequestMapping(value = "/findTemplateFile", method = RequestMethod.POST)
    public @ResponseBody String findTemplateFile() {
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webFindTemplateFile();
            }
        }, "佣金导入模版文件查询失败", logger);
    }
    
    /**
     * 删除文件
     * @param refNo  凭证编号
     * @return
     */
    @RequestMapping(value = "/saveDeleteFile", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteFile(String refNo) {
        logger.info("佣金文件删除,入参：" + refNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webSaveDeleteFile(refNo);
            }
        }, "佣金文件删除失败", logger);
    }
    
    /**
     * 佣金文件作废
     * @param fileId  文件id
     * @return
     */
    @RequestMapping(value = "/saveCannulFile", method = RequestMethod.POST)
    public @ResponseBody String saveCannulFile(Long fileId) {
        logger.info("佣金文件作废,入参：" + fileId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webSaveCannulFile(fileId);
            }
        }, "佣金文件作废失败", logger);
    }
    
    
    /**
     * 佣金文件解析
     * @param refNo
     * @return
     */
    @RequestMapping(value = "/saveResolveFile", method = RequestMethod.POST)
    public @ResponseBody String saveResolveFile(String refNo) {
        logger.info("佣金文件解析,入参：" + refNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webSaveResolveFile(refNo);
            }
        }, "佣金文件解析失败", logger);
    }

}
