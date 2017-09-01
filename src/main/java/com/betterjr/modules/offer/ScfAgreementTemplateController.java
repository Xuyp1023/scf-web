package com.betterjr.modules.offer;

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
import com.betterjr.modules.supplieroffer.IScfAgreementTemplateService;

@Controller
@RequestMapping("/Scf/AgreementTemplate")
public class ScfAgreementTemplateController {
    
    private static final Logger logger = LoggerFactory.getLogger(ScfSupplierOfferController.class);

    @Reference(interfaceClass = IScfAgreementTemplateService.class)
    private IScfAgreementTemplateService templateService;
    
    /**
     * 合同模版新增
     * 
     * @param request
     * coreCustNo 所属企业
     * templateFileId  文件id 
     * 
     * @param isOperator 平台为true
     * @return
     */
    @RequestMapping(value = "/saveAddTemplate", method = RequestMethod.POST)
    public @ResponseBody String saveAddTemplate(HttpServletRequest request,boolean isOperator) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("合同模版新增,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webSaveAddTemplate(anMap, isOperator);
            }
        }, "合同模版新增失败", logger);
    }
    
    /**
     * 合同模版删除
     * @param request
     * @param id  id为模版主键
     * @return
     */
    @RequestMapping(value = "/saveDeleteTemplate", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteTemplate(HttpServletRequest request,Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("合同模版删除,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webSaveDeleteTemplate(id);
            }
        }, "合同模版删除失败", logger);
    }
    
    /**
     * 合同模版激活
     * @param request
     * @param id  id为模版主键
     * @return
     */
    @RequestMapping(value = "/saveActiveTemplate", method = RequestMethod.POST)
    public @ResponseBody String saveActiveTemplate(HttpServletRequest request,Long id) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("合同模版激活,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webSaveActiveTemplate(id);
            }
        }, "合同模版激活失败", logger);
    }
    
    /**
     * 
     * 合同模版的查找，通过核心企业查找不同状态下的合同模版信息
     * @param coreCustNo  核心企业编号
     * @param businStatus   状态  模版的状态 0 已删除 1 刚上传模版  2 平台已经制作ftl  3已经激活
     * @return
     */
    @RequestMapping(value = "/findTemplateWithStatus", method = RequestMethod.POST)
    public @ResponseBody String findTemplateWithStatus(Long coreCustNo,String businStatus) {
        logger.info("合同模版查找,入参： coreCustNo=" + coreCustNo+"  businStatus= "+businStatus);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webFindTemplateWithStatus(coreCustNo, businStatus);
            }
        }, "合同模版查找失败", logger);
    }
    
    /**
     * 合同模版上传ftl格式的模版文件
     * @param id  合同模版的主键
     * @param fileId   上传文件的id
     * @return
     */
    @RequestMapping(value = "/saveUploadFtlAgreement", method = RequestMethod.POST)
    public @ResponseBody String saveUploadFtlAgreement(Long id,Long fileId) {
        logger.info("合同模版上传Ftl模版,入参： id=" + id+"  fileId= "+fileId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webSaveUploadFtlAgreement(id, fileId);
            }
        }, "合同模版上传Ftl模版失败", logger);
    }
    
    /**
     * 合同模版删除Ftl模版信息
     * @param id 合同模版的主键
     * @return
     */
    @RequestMapping(value = "/saveDeleteFtlAgreement", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteFtlAgreement(Long id) {
        logger.info("合同模版删除Ftl模版,入参： id=" + id);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webSaveDeleteFtlAgreement(id);
            }
        }, "合同模版删除Ftl模版失败", logger);
    }
    
    /**
     * 查找当前公司可用的合同模版信息
     * @param coreCustNo
     * @return
     */
    @RequestMapping(value = "/findTemplate", method = RequestMethod.POST)
    public @ResponseBody String findTemplate(Long coreCustNo) {
        logger.info("合同模版查找,入参： coreCustNo=" + coreCustNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webFindTemplate(coreCustNo);
            }
        }, "合同模版查找失败", logger);
    }
    
    
    /**
     * 合同模版查询
     * @param request
     * coreCustName  核心企业名称
     * 
     * coreCustNo  核心企业编号
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryTemplatePage", method = RequestMethod.POST)
    public @ResponseBody String queryTemplatePage(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("合同模版查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return templateService.webQueryTemplatePage(anMap, flag, pageNum, pageSize);
            }
        }, "合同模版查询失败", logger);
    }
    
}
