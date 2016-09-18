package com.betterjr.modules.agreement;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.betterjr.common.exception.BytterException;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.agreement.utils.CustFileClientUtils;
import com.betterjr.modules.document.entity.CustFileItem;

/****
 * 电子合同管理
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/Scf/ElecAgree")
public class ScfElecAgreementController {
    private static final Logger logger = LoggerFactory.getLogger(ScfElecAgreementController.class);
    
    @Reference(interfaceClass=IScfElecAgreementService.class)
    private IScfElecAgreementService scfElecAgreementService;
    
    @RequestMapping(value = "/queryElecAgreement", method = RequestMethod.POST)
    public @ResponseBody String queryElecAgreement(HttpServletRequest request, int pageNum, int pageSize) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询电子合同, 入参:"+anMap.toString());
        try {
            return scfElecAgreementService.webQueryElecAgreementByPage(anMap, pageNum, pageSize);
        } catch (RpcException btEx) {
            logger.error("分页查询电子合同异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("分页查询电子合同失败").toJson();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("分页查询电子合同失败").toJson();
        }
    }
    
    @RequestMapping(value = "/cancelElecAgreement", method = RequestMethod.POST)
    public @ResponseBody String cancelElecAgreePage(String appNo,String describe) {
        logger.info("取消电子合同的流水号：" + appNo);
        try {
            return scfElecAgreementService.webCancelElecAgreement(appNo,describe);
        }
        catch (RpcException btEx) {
            logger.error("取消电子合同异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("取消电子合同失败，请检查").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("取消电子合同的流水号失败，请检查").toJson();
        }
    }
    
    @RequestMapping(value = "/findAgreePage", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String findElecAgreePage(String appNo) {
        logger.info("生成电子合同的静态页面，流水号：" + appNo);
        try {
            return scfElecAgreementService.webFindElecAgreePage(appNo);
        }
        catch (RpcException btEx) {
            logger.error("生成电子合同的静态页面异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("生成静态页面失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("生成通知书的静态页面失败，请检查").toJson();
        }
    }
    
    @RequestMapping(value = "/findValidCode", method = RequestMethod.POST)
    public @ResponseBody String findValidCode(String appNo, String custType) {
        logger.info("获取签署合同的验证码，流水号:" + appNo + " custType:" + custType);
        try {
            return scfElecAgreementService.webFindValidCode(appNo,custType);
        }
        catch (RpcException btEx) {
            logger.error("获取签署合同的验证码异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("获取签署合同的验证码失败").toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("获取签署合同的验证码失败，请检查").toJson();
        }
    }
    
    @RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
    public @ResponseBody String sendValidCode(String appNo, String custType, String vCode) {
        logger.info("发送并验证签署合同的验证码，流水号:" + appNo + " custType:" + custType + " vCode:" + vCode);
        try {
            return scfElecAgreementService.webSendValidCode(appNo,custType,vCode);
        }
        catch (RpcException btEx) {
            logger.error("验证签署合同的验证码异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("验证签署合同的验证码失败").toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("发送并验证签署合同的验证码失败，请检查").toJson();
        }
    }
    
    
    @RequestMapping(value = "/transNotice", method = RequestMethod.POST)
    public @ResponseBody String transNotice(HttpServletRequest request) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("transNotice 入参：" + anMap);
        try {
            boolean bool= scfElecAgreementService.webTransNotice(anMap);
            logger.info("bool-TransNotice:"+bool);
            return "OK";
        }
        catch (RpcException btEx) {
            logger.error("转让通知书异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("失败").toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("转让书通知失败").toJson();
        }
    }
    
    @RequestMapping(value = "/transOpinion", method = RequestMethod.POST)
    public @ResponseBody String transOpinion(HttpServletRequest request) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("transOpinion 入参：" + anMap);
        try {
            boolean bool= scfElecAgreementService.webTransOpinion(anMap);
            logger.info("bool-TransOpinion:"+bool);
            return "OK";
        }
        catch (RpcException btEx) {
            logger.error("买方确认书通知异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("失败").toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("买方确认书通知失败").toJson();
        }
    }
    

    @RequestMapping(value = "/transProtacal", method = RequestMethod.POST)
    public @ResponseBody String transProtacal(HttpServletRequest request) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("transProtacal 入参：" + anMap);
        try {
            boolean bool= scfElecAgreementService.webTransProtacal(anMap);
            logger.info("bool-transProtacal:"+bool);
            return "OK";
        }
        catch (RpcException btEx) {
            logger.error("三方协议异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("失败").toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("三方协议失败").toJson();
        }
    }
    
    @RequestMapping(value = "/findElecAgreeByRequestNo", method = RequestMethod.POST)
    public @ResponseBody String findElecAgreeByRequestNo(String requestNo,String signType) {
        logger.info("入参： 申请单号:" + requestNo+"，类型："+signType);
        try {
           return scfElecAgreementService.webFindElecAgreeByOrderNo(requestNo, signType);
        }
        catch (RpcException btEx) {
            logger.error("查询电子合同异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return AjaxObject.newError("查询电子合同异常").toJson();
        }
    }
    
    @RequestMapping(value = "/downloadAgreePDF", method = { RequestMethod.GET, RequestMethod.POST })
    public void downloadElecAgreePDF(HttpServletResponse response, String appNo) {
        logger.info("下载电子合同的PDF格式文件，流水号：" + appNo);
        CustFileItem fileItem = scfElecAgreementService.webFindPdfFileInfo(appNo);
        CustFileClientUtils.fileDownload(response, fileItem,null);
    }
    
    @RequestMapping(value = "/addOtherFile", method = RequestMethod.POST)
    public @ResponseBody String addOtherFile(HttpServletRequest request) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("addOtherFile 入参：" + anMap);
        try {
           return scfElecAgreementService.webAddOtherFile(anMap);
        }
        catch (RpcException btEx) {
            logger.error("添加其它资料异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("添加其它资料失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return AjaxObject.newError("添加其它资料异常：").toJson();
        }
    } 
    
    @RequestMapping(value = "/queryOtherFile", method = RequestMethod.POST)
    public @ResponseBody String queryOtherFile(String requestNo) {
        logger.info("queryOtherFile 入参：" + requestNo);
        try {
           return scfElecAgreementService.webQueryOtherFile(requestNo);
        }
        catch (RpcException btEx) {
            logger.error("requestNo查询其它资料异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("查询出现异常").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return AjaxObject.newError("requestNo查询其它资料异常：").toJson();
        }
    } 
    

    @RequestMapping(value = "/delOtherFile", method = RequestMethod.POST)
    public @ResponseBody String delOtherFile(Long otherId) {
        logger.info("delOtherFile 入参：" + otherId);
        try {
           return scfElecAgreementService.webDelOtherFile(otherId);
        }
        catch (RpcException btEx) {
            logger.error("删除资料异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("资料删除出现异常").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return AjaxObject.newError("删除资料异常：").toJson();
        }
    } 
}
