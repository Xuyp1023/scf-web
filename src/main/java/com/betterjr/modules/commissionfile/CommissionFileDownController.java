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
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping(value = "/Scf/Commissionfiledown")
public class CommissionFileDownController {
    
    private static final Logger logger = LoggerFactory.getLogger(CommissionFileController.class);
    
    @Reference(interfaceClass = ICommissionFileDownService.class)
    private ICommissionFileDownService fileService;
    
    /*@Reference(interfaceClass = IContractCorpAccountService.class)
    private IContractCorpAccountService custFileDubboService;*/
    
    /**
     * 查询佣金文件下载
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     * custNo
     * GTEimportDate
     * LTEimportDate
     */
    @RequestMapping(value = "/queryFileList", method = RequestMethod.POST)
    public @ResponseBody String queryFileList(HttpServletRequest request,  String flag, int pageNum, int pageSize) {
        
       /* @SuppressWarnings("unused")
        String webFindCorpInfo = custFileDubboService.webFindCorpInfo(123l);*/
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金文件信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webQueryFileDownList(anMap, flag, pageNum, pageSize);
            }
        }, "佣金文件查询失败", logger);
    }
    
    
    /**
     * 查询佣金数据审核文件集合
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * custNo
     * GTEimportDate
     * LTEimportDate
     * confirmStatus
     * @return
     */
    @RequestMapping(value = "/queryCanAuditFileList", method = RequestMethod.POST)
    public @ResponseBody String queryCanAuditFileList(HttpServletRequest request,  String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金文件信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webQueryCanAuditFileList(anMap, flag, pageNum, pageSize);
            }
        }, "佣金文件查询失败", logger);
    }
    
    /**
     * 通过导出文件id查询当前文件下面的所有佣金记录分页信息
     * @param fileId
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     * fileId
     */
    @RequestMapping(value = "/queryFileRecordByFileId", method = RequestMethod.POST)
    public @ResponseBody String queryFileRecordByFileId(Long fileId,  String flag, int pageNum, int pageSize) {
        
        logger.info("佣金文件才查询明细信息,入参： fileId=" + fileId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webQueryFileRecordByFileId(fileId, flag, pageNum, pageSize);
            }
        }, "佣金文件查询失败", logger);
    }
    
    
    /**
     * 审核导出文件
     * @param request
     * @return
     * confirmMessage
     * confirmStatus
     * id
     */
    @RequestMapping(value = "/saveAuditFileDownById", method = RequestMethod.POST)
    public @ResponseBody String saveAuditFileDownById(HttpServletRequest request ) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金文件信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return fileService.webSaveAuditFileDownById(anMap );
            }
        }, "佣金导出文件审核失败", logger);
    }

}
