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
import com.betterjr.common.config.ParamNames;
import com.betterjr.common.exception.BytterException;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.document.entity.CustFileItem;
import com.betterjr.modules.document.utils.CustFileClientUtils;

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
        
        CustFileClientUtils.fileDownload(response, fileItem,scfElecAgreementService.webFindParamPath(ParamNames.OPENACCO_FILE_DOWNLOAD_PATH));
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
    
    /***
     * 根据请求单号和合同类型获取验证码
     * @param appNo
     * @param custType
     * @return
     */
    @RequestMapping(value = "/findValidCodeByRequestNo", method = RequestMethod.POST)
    public @ResponseBody String findValidCodeByRequestNo(String requestNo, String agreeType) {
        logger.info("获取签署合同的验证码，requestNo:" + requestNo + " agreeType:" + agreeType);
        try {
            return scfElecAgreementService.webFindValidCodeByRequestNo(requestNo, agreeType);
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
    
    /****
     * 根据申请单号发送验证签署合同验证码
     * @param appNo
     * @param custType
     * @param vCode
     * @return
     */
    @RequestMapping(value = "/sendValidCodeByRequestNo", method = RequestMethod.POST)
    public @ResponseBody String sendValidCodeByRequestNo(String requestNo, String agreeType, String vCode) {
        logger.info("发送并验证签署合同的验证码，requestNo:" + requestNo + " agreeType:" + agreeType + " vCode:" + vCode);
        try {
            return scfElecAgreementService.webSendValidCodeByRequestNo(requestNo, agreeType, vCode);
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
    
    @RequestMapping(value = "/findAgreePageByRequestNo", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String findAgreePageByRequestNo(String requestNo, String agreeType) {
        logger.info("生成电子合同的静态页面，requestNo：" + requestNo+"，agreeType:"+agreeType);
        try {
            return scfElecAgreementService.webFindElecAgreePageByRequestNo(requestNo, agreeType);
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

    /***
     * 添加保理合同
     * @param request 请求对象
     * @param fileList 附件列表
     * @return
     */
    @RequestMapping(value = "/addFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String addFactorAgreement(HttpServletRequest request, String fileList) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("新增保理合同, 入参:" + anMap.toString());
        try {
            return scfElecAgreementService.webAddFactorAgreement(anMap, fileList);
        }
        catch (RpcException btEx) {
            logger.error("新增保理合同异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("新增保理合同失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("新增保理合同失败，请检查").toJson();
        }
    }
    
    /***
     * 修改保理合同
     * @param request 请求对象
     * @param fileList 附件列表
     * @return
     */
    @RequestMapping(value = "/updateFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String updateFactorAgreement(HttpServletRequest request,String appNo, String fileList) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("修改保理合同, 入参:" + anMap.toString());
        try {
            return scfElecAgreementService.webUpdateFactorAgreement(anMap,appNo, fileList);
        }
        catch (RpcException btEx) {
            logger.error("修改保理合同异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("修改保理合同失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("修改保理合同失败，请检查").toJson();
        }
    }
    
    /***
     * 查询保理合同
     */
    @RequestMapping(value = "/queryFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String queryFactorAgree(HttpServletRequest request, int pageNum, int pageSize) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询保理合同, 入参:" + anMap.toString()+"，pageNum："+pageNum+"，pageSize:"+pageSize);
        try {
            return scfElecAgreementService.webQueryFactorAgreement(anMap, pageNum,pageSize);
        }
        catch (RpcException btEx) {
            logger.error("查询保理合同异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("查询保理合同失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("查询保理合同失败，请检查").toJson();
        }
    }
    
    /***
     * 查询保理合同关联列表
     */
    @RequestMapping(value = "/findFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String findFactorAgree(Long custNo,Long factorNo,String agreeType) {
        logger.info("查询保理合同关联列表, 入参:custNo:" + custNo+"，factorNo："+factorNo+",agreeType:"+agreeType);
        try {
            return scfElecAgreementService.webFindFactorAgreement(custNo,factorNo,agreeType);
        }
        catch (RpcException btEx) {
            logger.error("查询保理合同关联列表异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("查询保理合同关联列表失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("查询保理合同关联列表失败，请检查").toJson();
        }
    }
    
    /***
     * 作废/启用保理合同
     */
    @RequestMapping(value = "/cancelFactorAgree", method = RequestMethod.POST)
    public @ResponseBody String cancelFactorAgree(String appNo,String signStatus) {
        logger.info("作废/启用保理合同, 入参:appNo:" + appNo+"，status:"+signStatus);
        try {
            return scfElecAgreementService.updateFactorAgree(appNo,signStatus);
        }
        catch (RpcException btEx) {
            logger.error("作废/启用合同异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("作废/启用合同失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("作废/启用合同失败，请检查").toJson();
        }
    }
}
