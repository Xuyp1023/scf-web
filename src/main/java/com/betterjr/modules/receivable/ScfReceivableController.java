package com.betterjr.modules.receivable;

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
@RequestMapping("/Scf/Receivable")
public class ScfReceivableController {

    private static final Logger logger =    LoggerFactory.getLogger(ScfReceivableController.class);
    
    @Reference(interfaceClass = IScfReceivableService.class)
    private IScfReceivableService scfReceivableService;
    
    @RequestMapping(value = "/modifyReceivable", method = RequestMethod.POST)
    public @ResponseBody String modifyReceivable(HttpServletRequest request, Long id, String fileList, String otherFileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款编辑,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webSaveModifyReceivable(anMap, id, fileList, otherFileList);
            }
        }, "应收账款编辑失败", logger);
    }
    
    @RequestMapping(value = "/queryReceivable", method = RequestMethod.POST)
    public @ResponseBody String queryReceivable(HttpServletRequest request, String isOnlyNormal, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款查询,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webQueryReceivable(anMap, isOnlyNormal, flag, pageNum, pageSize);
            }
        }, "应收账款查询失败", logger);
    }
    
    @RequestMapping(value = "/findReceivableList", method = RequestMethod.POST)
    public @ResponseBody String findReceivableList(HttpServletRequest request, String isOnlyNormal) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款查询,入参:" + anMap + " isOnlyNormal" + isOnlyNormal);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webFindReceivableList(anMap, isOnlyNormal);
            }
        }, "应收账款查询失败", logger);
    }
    
    @RequestMapping(value = "/findReceivableDetailsById", method = RequestMethod.POST)
    public @ResponseBody String findReceivableDetailsById(Long id) {
        logger.info("应收账款详情查询,入参:id=" + id);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webFindReceivableDetailsById(id);
            }
        }, "应收账款详情查询失败", logger);
    }
    
    @RequestMapping(value = "/addReceivable", method = RequestMethod.POST)
    public @ResponseBody String addReceivable(HttpServletRequest request, String fileList, String otherFileList) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款新增,入参:" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webAddReceivable(anMap, fileList, otherFileList);
            }
        }, "应收账款新增失败", logger);
    }
    
    @RequestMapping(value = "/saveAduitReceivable", method = RequestMethod.POST)
    public @ResponseBody String saveAduitReceivable(Long id) {
        logger.info("应收账款审核,入参：id=" + id);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webSaveAduitReceivable(id);
            }
        }, "应收账款审核失败", logger);
    }
    
    /**
     * 新增应收账款
     * @param request
     * @param fileList  文件上传批次号
     * @param confirmFlag true 新增并确认   false  新增
     * @return
     */
    @RequestMapping(value = "/saveAddReceivableDO", method = RequestMethod.POST)
    public @ResponseBody String addReceivableDO(HttpServletRequest request, String fileList, boolean confirmFlag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("订单信息新增,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webAddReceivableDO(anMap, fileList,confirmFlag);
            }
        }, "订单信息新增失败", logger);
    }
    
    /**
     * 应收账款信息编辑
     * @param request
     * @param fileList 上传批次号
     * @param confirmFlag 编辑并确认
     * @return
     */
    @RequestMapping(value = "/saveModifyReceivableDO", method = RequestMethod.POST)
    public @ResponseBody String modifyReceivableDO(HttpServletRequest request, String fileList,boolean confirmFlag) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款信息修改,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webSaveModifyReceivableDO(anMap,fileList, confirmFlag);
            }
        }, "应收账款信息编辑失败", logger);
    }
    
    /**
     * 对应收账款进行废止操作
     * @param refNo
     * @param version
     * @return
     */
    @RequestMapping(value = "/saveAnnulReceivable", method = RequestMethod.POST)
    public @ResponseBody String saveAnnulReceivable(String refNo,String version) {
        logger.info("应收账款信息作废,入参：refNo=" + refNo+"  version:"+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webSaveAnnulReceivable(refNo,version);
            }
        }, "应收账款信息废止失败", logger);
    }
    
    /**
     * 对应收账款单据进行审核操作
     * @param refNo
     * @param version
     * @return
     */
    @RequestMapping(value = "/saveAuditReceivable", method = RequestMethod.POST)
    public @ResponseBody String saveAuditOrder(String refNo,String version) {
        logger.info("应收账款审核,入参：refNo=" + refNo +" : version="+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webSaveAuditReceivableByRefNoVersion(refNo, version);
            }
        }, "应收账款审核成功！", logger);
    }
    
    /**
     * 查询樱花艘账款详情信息
     * @param refNo
     * @param version
     * @return
     */
    @RequestMapping(value = "/findReceivable", method = RequestMethod.POST)
    public @ResponseBody String findReceivable(String refNo,String version) {
        
        logger.info("应收账款查询详情,入参：refNo=" + refNo +" : version="+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webFindReceivableByRefNoVersion(refNo, version);
            }
        }, "应收账款查询详情成功！", logger);
    }
    
    /**
     * 查询未生效的应收账款信息，登入数据来源和审核界面数据来源
     * @param request
     * @param flag 是否需要查询分页
     * @param pageNum
     * @param pageSize
     * @param isAudit 审核的标记
     * @return
     */
    @RequestMapping(value = "/queryIneffectiveReceivable", method = RequestMethod.POST)
    public @ResponseBody String queryIneffectiveReceivable(HttpServletRequest request, String flag, int pageNum, int pageSize,boolean isAudit) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款未生效信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webQueryIneffectiveReceivable(anMap, flag, pageNum, pageSize,isAudit);
            }
        }, "应收账款信息查询失败", logger);
    }
    
    
    @RequestMapping(value = "/queryEffectiveReceivable", method = RequestMethod.POST)
    public @ResponseBody String queryEffectiveReceivable(HttpServletRequest request, String flag, int pageNum, int pageSize,boolean isCust) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("应收账款已经生效信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfReceivableService.webQueryEffectiveReceivable(anMap, flag, pageNum, pageSize,isCust);
            }
        }, "应收账款信息查询失败", logger);
    }
}
