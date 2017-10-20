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
 * @ClassName: PayOrderFileController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author xuyp
 * @date 2017年10月20日 下午4:04:32 
 *
 */
@Controller
@RequestMapping("/Scf/PayOrderFile")
public class PayOrderFileController {

    private static final Logger logger = LoggerFactory.getLogger(PayOrderFileController.class);

    @Reference(interfaceClass = IPayOrderFileService.class)
    private IPayOrderFileService fileService;

    /**
     * 新增下载付款文件
     * @Title: saveAddFile 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param flag  0 不需要插入到数据库  1 需要生成文件并且插入到数据库
     * @param @param requestPayDate
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午4:42:01
     */
    @RequestMapping(value = "/saveAddFile", method = RequestMethod.POST)
    public @ResponseBody String saveAddFile(HttpServletRequest request, String flag, String requestPayDate) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("新增下载付款文件,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return fileService.webSaveAddFile(requestPayDate.replaceAll("-", ""), flag);
            }
        }, "新增下载付款文件失败", logger);

    }

    /**
     * 查询文件记录
     * @Title: queryFileList 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param businStatus
     * @param @param requestPayDate
     * @param @param infoType  付款类型   0生成付款文件 1上传的付款结果文件
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午4:44:21
     */
    @RequestMapping(value = "/queryFileList", method = RequestMethod.POST)
    public @ResponseBody String queryFileList(HttpServletRequest request, String businStatus, String requestPayDate,
            String infoType) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询文件记录,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return fileService.webQueryFileList(requestPayDate.replaceAll("-", ""), businStatus, infoType);
            }
        }, "查询文件记录失败", logger);

    }

    @RequestMapping(value = "/queryFileListByMap", method = RequestMethod.POST)
    public @ResponseBody String queryFileListByMap(HttpServletRequest request) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询文件记录,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return fileService.webQueryFileListByMap(anMap);
            }
        }, "查询文件记录失败", logger);

    }

    /**
     * 查询文件记录
     * @Title: queryFilePage 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param flag
     * @param @param pageNum
     * @param @param pageSize
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月19日 下午1:34:29
     * GTErequestPayDate  LTErequestPayDate   businStatus  infoType
     */
    @RequestMapping(value = "/queryFilePage", method = RequestMethod.POST)
    public @ResponseBody String queryFilePage(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询文件记录,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return fileService.webQueryFilePage(anMap, flag, pageNum, pageSize);
            }
        }, "查询文件记录失败", logger);

    }

    /**
     * 上传付款结果解析
     * @Title: saveResolveFile 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param fileItemId  上传结果文件id
     * @param @param sourceFileId  下载的付款文件id
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午4:49:14
     */
    @RequestMapping(value = "/saveResolveFile", method = RequestMethod.POST)
    public @ResponseBody String saveResolveFile(HttpServletRequest request, Long fileItemId, Long sourceFileId) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("上传付款结果解析,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return fileService.webSaveResolveFile(fileItemId, sourceFileId);
            }
        }, "解析失败", logger);

    }

    /**
     * 查询付款详情
     * @Title: findFileDetailByPrimaryKey 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param id  下载付款文件id
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午4:51:18
     */
    @RequestMapping(value = "/findFileDetailByPrimaryKey", method = RequestMethod.POST)
    public @ResponseBody String findFileDetailByPrimaryKey(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询付款详情,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return fileService.webFindFileDetailByPrimaryKey(id);
            }
        }, "查询付款详情失败", logger);

    }

    /**
     * 审核文件
     * @Title: saveAuditFileByPrimaryKey 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param id 下载付款文件id
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午4:52:56
     */
    @RequestMapping(value = "/saveAuditFileByPrimaryKey", method = RequestMethod.POST)
    public @ResponseBody String saveAuditFileByPrimaryKey(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("审核文件,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return fileService.webSaveAuditFileByPrimaryKey(id);
            }
        }, "审核文件失败", logger);

    }

    /**
     * 删除文件
     * @Title: saveDeleteFileByPrimaryKey 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param id
     * @param @return 参数说明 
     * @return String 返回类型 
     * @throws 
     * @author xuyp
     * @date 2017年10月18日 下午4:53:58
     */
    @RequestMapping(value = "/saveDeleteFileByPrimaryKey", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteFileByPrimaryKey(HttpServletRequest request, Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("删除文件,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return fileService.webSaveDeleteFileByPrimaryKey(id);
            }
        }, "删除文件失败", logger);

    }
}
