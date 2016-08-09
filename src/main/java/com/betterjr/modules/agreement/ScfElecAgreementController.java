package com.betterjr.modules.agreement;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.betterjr.common.exception.BytterException;
import com.betterjr.common.exception.BytterTradeException;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;

/****
 * 电子合同管理
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/ElecAgree")
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
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("service failed").toJson();
        } catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }
    
    @RequestMapping(value = "/cancelElecAgreement", method = RequestMethod.POST)
    public @ResponseBody String cancelElecAgreePage(String appNo) {
        logger.info("取消电子合同的流水号：" + appNo);
        try {
            return scfElecAgreementService.webCancelElecAgreement(appNo);
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("取消电子合同的流水号失败，请检查").toJson();
        }
    }
    
    @RequestMapping(value = "/findAgreePage", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String findElecAgreePage(String appNo) {
        logger.info("生成电子合同的静态页面，流水号：" + appNo);
        try {
            return scfElecAgreementService.webFindElecAgreePage(appNo);
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("生成通知书的静态页面失败，请检查").toJson();
        }
    }
    
    @RequestMapping(value = "/findValidCode", method = RequestMethod.POST)
    public @ResponseBody String findValidCode(String appNo, String custType) {
        logger.info("获取签署合同的验证码，流水号:" + appNo + " custType:" + custType);
        try {
            return scfElecAgreementService.webFindValidCode(appNo,custType);
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
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
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("发送并验证签署合同的验证码失败，请检查").toJson();
        }
    }
    
    
    @RequestMapping(value = "/transNotice", method = RequestMethod.POST)
    public @ResponseBody String transNotice(HttpServletRequest request) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("申请单号:" + anMap);
        try {
            boolean bool= scfElecAgreementService.webTransNotice(anMap);
            logger.info("bool-TransNotice:"+bool);
            return "OK";
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("转让书通知失败").toJson();
        }
    }
    
    @RequestMapping(value = "/transOpinion", method = RequestMethod.POST)
    public @ResponseBody String transOpinion(HttpServletRequest request) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("申请单号:" + anMap);
        try {
            boolean bool= scfElecAgreementService.webTransOpinion(anMap);
            logger.info("bool-TransOpinion:"+bool);
            return "OK";
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("买方确认书通知失败").toJson();
        }
    }
}
