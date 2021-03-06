package com.betterjr.modules.agreement;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.betterjr.common.exception.BytterException;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.document.entity.CustFileItem;
import com.betterjr.modules.document.service.DataStoreService;
import com.betterjr.modules.document.utils.FileWebClientUtils;

/****
 * 电子合同管理
 * 
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/Scf/ElecAgree")
public class ScfElecAgreementController {
    private static final Logger logger = LoggerFactory.getLogger(ScfElecAgreementController.class);

    @Reference(interfaceClass = IScfElecAgreementService.class)
    private IScfElecAgreementService scfElecAgreementService;

    @Autowired
    private DataStoreService dtaStoreService;

    @RequestMapping(value = "/queryElecAgreement", method = RequestMethod.POST)
    public @ResponseBody String queryElecAgreement(final HttpServletRequest request, final int pageNum,
            final int pageSize) {
        final Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询电子合同, 入参:" + anMap.toString());
        try {
            return scfElecAgreementService.webQueryElecAgreementByPage(anMap, pageNum, pageSize);
        }
        catch (final RpcException btEx) {
            logger.error("分页查询电子合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("分页查询电子合同失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("分页查询电子合同失败").toJson();
        }
    }

    @RequestMapping(value = "/cancelElecAgreement", method = RequestMethod.POST)
    public @ResponseBody String cancelElecAgreePage(final String appNo, final String describe) {
        logger.info("取消电子合同的流水号：" + appNo);
        try {
            return scfElecAgreementService.webCancelElecAgreement(appNo, describe);
        }
        catch (final RpcException btEx) {
            logger.error("取消电子合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("取消电子合同失败，请检查").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("取消电子合同的流水号失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/findAgreePage", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String findElecAgreePage(final String appNo) {
        logger.info("生成电子合同的静态页面，流水号：" + appNo);
        try {
            return scfElecAgreementService.webFindElecAgreePage(appNo);
        }
        catch (final RpcException btEx) {
            logger.error("生成电子合同的静态页面异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("生成静态页面失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("生成通知书的静态页面失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/findValidCode", method = RequestMethod.POST)
    public @ResponseBody String findValidCode(final String appNo, final String custType) {
        logger.info("获取签署合同的验证码，流水号:" + appNo + " custType:" + custType);
        try {
            return scfElecAgreementService.webFindValidCode(appNo, custType);
        }
        catch (final RpcException btEx) {
            logger.error("获取签署合同的验证码异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("获取签署合同的验证码失败").toJson();
        }
        catch (final Exception ex) {
            return AjaxObject.newError("获取签署合同的验证码失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
    public @ResponseBody String sendValidCode(final String appNo, final String custType, final String vCode) {
        logger.info("发送并验证签署合同的验证码，流水号:" + appNo + " custType:" + custType + " vCode:" + vCode);
        try {
            return scfElecAgreementService.webSendValidCode(appNo, custType, vCode);
        }
        catch (final RpcException btEx) {
            logger.error("验证签署合同的验证码异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("验证签署合同的验证码失败").toJson();
        }
        catch (final Exception ex) {
            return AjaxObject.newError("发送并验证签署合同的验证码失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/findElecAgreeByRequestNo", method = RequestMethod.POST)
    public @ResponseBody String findElecAgreeByRequestNo(final String requestNo, final String signType) {
        logger.info("入参： 申请单号:" + requestNo + "，类型：" + signType);
        try {
            return scfElecAgreementService.webFindElecAgreeByOrderNo(requestNo, signType);
        }
        catch (final RpcException btEx) {
            logger.error("查询电子合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("查询电子合同异常").toJson();
        }
    }

    @RequestMapping(value = "/downloadAgreePDF", method = { RequestMethod.GET, RequestMethod.POST })
    public void downloadElecAgreePDF(final HttpServletResponse response, final String appNo) {
        logger.info("下载电子合同的PDF格式文件，流水号：" + appNo);
        final CustFileItem fileItem = scfElecAgreementService.webFindPdfFileInfo(appNo);

        FileWebClientUtils.fileDownload(dtaStoreService, response, fileItem);
    }

    @RequestMapping(value = "/addOtherFile", method = RequestMethod.POST)
    public @ResponseBody String addOtherFile(final HttpServletRequest request) {
        final Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("addOtherFile 入参：" + anMap);
        try {
            return scfElecAgreementService.webAddOtherFile(anMap);
        }
        catch (final RpcException btEx) {
            logger.error("添加其它资料异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("添加其它资料失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("添加其它资料异常：").toJson();
        }
    }

    @RequestMapping(value = "/queryOtherFile", method = RequestMethod.POST)
    public @ResponseBody String queryOtherFile(final String requestNo) {
        logger.info("queryOtherFile 入参：" + requestNo);
        try {
            return scfElecAgreementService.webQueryOtherFile(requestNo);
        }
        catch (final RpcException btEx) {
            logger.error("requestNo查询其它资料异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("查询出现异常").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("requestNo查询其它资料异常：").toJson();
        }
    }

    @RequestMapping(value = "/delOtherFile", method = RequestMethod.POST)
    public @ResponseBody String delOtherFile(final Long otherId) {
        logger.info("delOtherFile 入参：" + otherId);
        try {
            return scfElecAgreementService.webDelOtherFile(otherId);
        }
        catch (final RpcException btEx) {
            logger.error("删除资料异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("资料删除出现异常").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("删除资料异常：").toJson();
        }
    }

    /***
     * 根据请求单号和合同类型获取验证码
     * 
     * @param appNo
     * @param custType
     * @return
     */
    @RequestMapping(value = "/findValidCodeByRequestNo", method = RequestMethod.POST)
    public @ResponseBody String findValidCodeByRequestNo(final String requestNo, final String agreeType) {
        logger.info("获取签署合同的验证码，requestNo:" + requestNo + " agreeType:" + agreeType);
        try {
            return scfElecAgreementService.webFindValidCodeByRequestNo(requestNo, agreeType);
        }
        catch (final RpcException btEx) {
            logger.error("获取签署合同的验证码异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("获取签署合同的验证码失败").toJson();
        }
        catch (final Exception ex) {
            return AjaxObject.newError("获取签署合同的验证码失败，请检查").toJson();
        }
    }

    /****
     * 根据申请单号发送验证签署合同验证码
     * 
     * @param appNo
     * @param custType
     * @param vCode
     * @return
     */
    @RequestMapping(value = "/sendValidCodeByRequestNo", method = RequestMethod.POST)
    public @ResponseBody String sendValidCodeByRequestNo(final String requestNo, final String agreeType,
            final String vCode) {
        logger.info("发送并验证签署合同的验证码，requestNo:" + requestNo + " agreeType:" + agreeType + " vCode:" + vCode);
        try {
            return scfElecAgreementService.webSendValidCodeByRequestNo(requestNo, agreeType, vCode);
        }
        catch (final RpcException btEx) {
            logger.error("验证签署合同的验证码异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("验证签署合同的验证码失败").toJson();
        }
        catch (final Exception ex) {
            return AjaxObject.newError("发送并验证签署合同的验证码失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/findAgreePageByRequestNo", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String findAgreePageByRequestNo(final String requestNo, final String agreeType) {
        logger.info("生成电子合同的静态页面，requestNo：" + requestNo + "，agreeType:" + agreeType);
        try {
            return scfElecAgreementService.webFindElecAgreePageByRequestNo(requestNo, agreeType);
        }
        catch (final RpcException btEx) {
            logger.error("生成电子合同的静态页面异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("生成静态页面失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("生成通知书的静态页面失败，请检查").toJson();
        }
    }

    /***
     * 添加保理合同
     * 
     * @param request
     *            请求对象
     * @param fileList
     *            附件列表
     * @return
     */
    @RequestMapping(value = "/addFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String addFactorAgreement(final HttpServletRequest request, final String fileList) {
        final Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("新增保理合同, 入参:" + anMap.toString());
        try {
            return scfElecAgreementService.webAddFactorAgreement(anMap, fileList);
        }
        catch (final RpcException btEx) {
            logger.error("新增保理合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("新增保理合同失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("新增保理合同失败，请检查").toJson();
        }
    }

    /***
     * 修改保理合同
     * 
     * @param request
     *            请求对象
     * @param fileList
     *            附件列表
     * @return
     */
    @RequestMapping(value = "/updateFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String updateFactorAgreement(final HttpServletRequest request, final String appNo,
            final String fileList) {
        final Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("修改保理合同, 入参:" + anMap.toString());
        try {
            return scfElecAgreementService.webUpdateFactorAgreement(anMap, appNo, fileList);
        }
        catch (final RpcException btEx) {
            logger.error("修改保理合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("修改保理合同失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("修改保理合同失败，请检查").toJson();
        }
    }

    /***
     * 查询保理合同
     */
    @RequestMapping(value = "/queryFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String queryFactorAgree(final HttpServletRequest request, final int pageNum,
            final int pageSize) {
        final Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询保理合同, 入参:" + anMap.toString() + "，pageNum：" + pageNum + "，pageSize:" + pageSize);
        try {
            return scfElecAgreementService.webQueryFactorAgreement(anMap, pageNum, pageSize);
        }
        catch (final RpcException btEx) {
            logger.error("查询保理合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("查询保理合同失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("查询保理合同失败，请检查").toJson();
        }
    }

    /***
     * 查询保理合同关联列表
     */
    @RequestMapping(value = "/findFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String findFactorAgree(final Long custNo, final Long factorNo, final String agreeType) {
        logger.info("查询保理合同关联列表, 入参:custNo:" + custNo + "，factorNo：" + factorNo + ",agreeType:" + agreeType);
        try {
            return scfElecAgreementService.webFindFactorAgreement(custNo, factorNo, agreeType);
        }
        catch (final RpcException btEx) {
            logger.error("查询保理合同关联列表异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("查询保理合同关联列表失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("查询保理合同关联列表失败，请检查").toJson();
        }
    }

    /***
     * 作废/启用保理合同
     */
    @RequestMapping(value = "/cancelFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String cancelFactorAgree(final String appNo, final String signStatus) {
        logger.info("作废/启用保理合同, 入参:appNo:" + appNo + "，status:" + signStatus);
        try {
            return scfElecAgreementService.updateFactorAgree(appNo, signStatus);
        }
        catch (final RpcException btEx) {
            logger.error("作废/启用合同异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("作废/启用合同失败").toJson();
        }
        catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("作废/启用合同失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/downloadAgreeImage", method = { RequestMethod.GET, RequestMethod.POST })
    public void downloadElecAgreeImage(final HttpServletResponse response, final String appNo, final Long batchNo,
            final Long id) {

        final CustFileItem fileItem = scfElecAgreementService.webFindSignedImage(appNo, batchNo, id);

        FileWebClientUtils.fileDownload(dtaStoreService, response, fileItem);
    }

}
