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
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;

/***
 * 合同管理
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/Scf/CustAgree")
public class CustAgreementController {

    private static final Logger logger = LoggerFactory.getLogger(CustAgreementController.class);

    @Reference(interfaceClass = IScfCustAgreementService.class)
    private IScfCustAgreementService scfCustAgreemnetService;

    @RequestMapping(value = "/queryAgreement", method = RequestMethod.POST)
    public @ResponseBody String queryCustAgreement(HttpServletRequest request, String pageNum, String pageSize) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询客户合同, 入参:" + anMap.toString());
        try {
            return scfCustAgreemnetService.webQueryCustAgreementsByPage(anMap, Integer.parseInt(pageNum),
                    Integer.parseInt(pageSize));
        }
        catch (RpcException btEx) {
            logger.error("查询合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("查询合同失败").toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }

    @RequestMapping(value = "/addAgree", method = RequestMethod.POST)
    public @ResponseBody String addCustAgreement(HttpServletRequest request, String fileList) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("新增客户合同, 入参:" + anMap.toString());
        try {
            return scfCustAgreemnetService.webAddCustAgreement(anMap, fileList);
        }
        catch (RpcException btEx) {
            logger.error("新增客户合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("新增客户合同失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("新增合同失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/queryAgreeDetail", method = RequestMethod.POST)
    public @ResponseBody String queryCustAgreeDetail(Long agreeId) {
        logger.info("查询客户合同明细, 入参:" + agreeId);

        try {
            return scfCustAgreemnetService.webFindCustAgreementDetail(agreeId);
        }
        catch (RpcException btEx) {
            logger.error("查询客户合同明细异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("查询客户合同明细失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/updateAgree", method = RequestMethod.POST)
    public @ResponseBody String updateCustAgreement(HttpServletRequest request, Long agreeId, String fileList) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("更新客户合同, 入参:" + anMap.toString() + " fileList:" + agreeId);
        try {
            return scfCustAgreemnetService.webModifyCustAgreement(anMap, agreeId, fileList);
        }
        catch (RpcException btEx) {
            logger.error("更新客户合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("更新合同失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/queryAgreeAccess", method = RequestMethod.POST)
    public @ResponseBody String queryCustAgreeAccess(Long agreeId) {
        logger.info("查询客户合同附件, 入参:" + agreeId);
        try {
            return scfCustAgreemnetService.webFindCustFileItems(agreeId);
        }
        catch (RpcException btEx) {
            logger.error("查询客户合同附件异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            logger.error("查询客户合同附件异常：" + ex.getMessage());
            return AjaxObject.newError("查询客户合同附件失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/delAgreeAttach", method = RequestMethod.POST)
    public @ResponseBody String delBillAccessory(Long id, Long agreeId) {
        logger.info("删除合同附件, 入参附件id:" + id + ", agreeId:" + agreeId);
        try {
            return scfCustAgreemnetService.webDeleteFileItem(id, agreeId);
        }
        catch (RpcException btEx) {
            logger.error("删除合同附件异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            logger.error("删除合同附件异常：" + ex.getMessage());
            return AjaxObject.newError("删除合同附件失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/activeAgree", method = RequestMethod.POST)
    public @ResponseBody String activeCustAgreement(Long agreeId) {
        logger.info("启用客户合同, 入参:" + agreeId);
        try {
            scfCustAgreemnetService.webSaveCustAgreementStatus(agreeId, 1);
            return AjaxObject.newOk("启用客户合同成功").toJson();
        }
        catch (RpcException btEx) {
            logger.error("启用客户合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            return AjaxObject.newError("启用客户合同失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/deleteAgree", method = RequestMethod.POST)
    public @ResponseBody String deleteCustAgreement(Long agreeId) {
        logger.info("删除客户合同, 入参:" + agreeId);
        try {
            scfCustAgreemnetService.webSaveCustAgreementStatus(agreeId, 2);
            return AjaxObject.newOk("删除客户合同成功").toJson();
        }
        catch (RpcException btEx) {
            logger.error("删除客户合同成功异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("删除客户合同失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/deleteContractAgree", method = RequestMethod.POST)
    public @ResponseBody String deleteContractAgree(Long agreeId) {
        logger.info("删除客户合同, 入参:" + agreeId);
        try {
            scfCustAgreemnetService.webDeleteContractAgree(agreeId);
            return AjaxObject.newOk("删除客户合同成功").toJson();
        }
        catch (RpcException btEx) {
            logger.error("删除客户合同成功异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError(btEx.getMessage()).toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("删除客户合同失败，请检查").toJson();
        }
    }
}
