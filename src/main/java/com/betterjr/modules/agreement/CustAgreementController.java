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


/***
 * 合同管理
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/CustAgree")
public class CustAgreementController {
    
    private static final Logger logger = LoggerFactory.getLogger(CustAgreementController.class);
    

    @Reference(interfaceClass=IScfCustAgreementService.class)
    private IScfCustAgreementService scfCustAgreemnetService;
    
    @RequestMapping(value = "/queryAgreement", method = RequestMethod.POST)
    public @ResponseBody String queryCustAgreement(HttpServletRequest request, String anPageNum, String anPageSize) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询客户合同, 入参:"+anMap.toString());
        try {
            return scfCustAgreemnetService.webQueryCustAgreementsByPage(anMap, Integer.parseInt(anPageNum), Integer.parseInt(anPageSize));
        } catch (RpcException btEx) {
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("service failed").toJson();
        } catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }
    
    @RequestMapping(value = "/addAgree", method = RequestMethod.POST)
    public @ResponseBody String addCustAgreement(HttpServletRequest request, String anFileList) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("新增客户合同, 入参:" + anMap.toString());
        try {
            return scfCustAgreemnetService.webAddCustAgreement(anMap, anFileList);
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("新增合同失败，请检查").toJson();
        }
    }
    
    @RequestMapping(value = "/queryCoreEnter", method = RequestMethod.POST)
    public @ResponseBody String queryCoreEnterprise(HttpServletRequest request) {
        logger.info("查询供应商核心企业");
        try {
            return scfCustAgreemnetService.webFindCoreEnters();
        }
        catch (Exception ex) {
            return AjaxObject.newError("查询核心企业失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/queryAgreeDetail", method = RequestMethod.POST)
    public @ResponseBody String queryCustAgreeDetail(Long anAgreeId) {
        logger.info("查询客户合同明细, 入参:" + anAgreeId);

        try {
            return scfCustAgreemnetService.webFindCustAgreementDetail(anAgreeId);
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("查询客户合同明细失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/updateAgree", method = RequestMethod.POST)
    public @ResponseBody String updateCustAgreement(HttpServletRequest request, Long anAgreeId, String anFileList) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("更新客户合同, 入参:" + anMap.toString() + " fileList:" + anFileList);
        try {
            return scfCustAgreemnetService.webModifyCustAgreement(anMap, anAgreeId, anFileList);
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("更新合同失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/queryAgreeAccess", method = RequestMethod.POST)
    public @ResponseBody String queryCustAgreeAccess(Long anAgreeId) {
        logger.info("查询客户合同附件, 入参:" + anAgreeId);
        try {
            return scfCustAgreemnetService.webFindCustFileItems(anAgreeId);
        }
        catch (Exception ex) {
            return AjaxObject.newError("查询客户合同附件失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/delAgreeAttach", method = RequestMethod.POST)
    public @ResponseBody String delBillAccessory(Long anId, Long anAgreeId) {
        logger.info("删除合同附件, 入参附件id:" + anId +", agreeId:" + anAgreeId);
        try {
            return scfCustAgreemnetService.webDeleteFileItem(anId, anAgreeId);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("删除合同附件失败，请检查").toJson();
        }
    }
    
    @RequestMapping(value = "/activeAgree", method = RequestMethod.POST)
    public @ResponseBody String activeCustAgreement(Long anAgreeId) {
        logger.info("启用客户合同, 入参:" + anAgreeId);
        try {
            scfCustAgreemnetService.webSaveCustAgreementStatus(anAgreeId, 1);
            return AjaxObject.newOk("启用客户合同成功").toJson();
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("启用客户合同失败，请检查").toJson();
        }
    }
    
    @RequestMapping(value = "/deleteAgree", method = RequestMethod.POST)
    public @ResponseBody String deleteCustAgreement(Long anAgreeId) {
        logger.info("删除客户合同, 入参:" + anAgreeId);
        try {
            scfCustAgreemnetService.webSaveCustAgreementStatus(anAgreeId, 2);
            return AjaxObject.newOk("删除客户合同成功").toJson();
        }
        catch (BytterTradeException btEx) {
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return AjaxObject.newError("删除客户合同失败，请检查").toJson();
        }
    }
}
