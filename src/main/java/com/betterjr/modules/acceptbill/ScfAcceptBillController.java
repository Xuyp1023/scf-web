package com.betterjr.modules.acceptbill;

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
@RequestMapping(value = "/Scf/AcceptBill")
public class ScfAcceptBillController {

    private static final Logger logger = LoggerFactory.getLogger(ScfAcceptBillController.class);

    @Reference(interfaceClass = IScfAcceptBillService.class)
    private IScfAcceptBillService scfAcceptBillService;

    @RequestMapping(value = "/queryAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String queryAcceptBill(HttpServletRequest request, String isOnlyNormal, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webQueryAcceptBill(anMap, isOnlyNormal, flag, pageNum, pageSize);
            }
        }, "汇票信息查询失败", logger);
    }
    
    /**
     * 查询登入页面的数据来源和核准页面的数据来源
     * @param request
     * @param isOnlyNormal
     * @param flag
     * @param pageNum
     * @param pageSize
     * @param isAudit true 用来查询核准页面的数据来源   false 用来查询登入数据的数据来源
     * @return
     */
    @RequestMapping(value = "/queryIneffectiveBill", method = RequestMethod.POST)
    public @ResponseBody String queryIneffectiveBill(HttpServletRequest request, String isOnlyNormal, String flag, int pageNum, int pageSize,boolean isAudit) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票未生效信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webQueryIneffectiveAcceptBill(anMap, isOnlyNormal, flag, pageNum, pageSize,isAudit);
            }
        }, "汇票信息查询失败", logger);
    }
    
    /**
     * 查询核心企业/供应商已经生效的票据次信息
     * @param request
     * @param isOnlyNormal
     * @param flag
     * @param pageNum
     * @param pageSize
     * @param isCust true 是核心企业的票据池信息  false 是供应商的票据池信息
     * @return
     */
    @RequestMapping(value = "/queryEffectiveBill", method = RequestMethod.POST)
    public @ResponseBody String queryEffectiveBill(HttpServletRequest request, String isOnlyNormal, String flag, int pageNum, int pageSize,boolean isCust) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票已经生效信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webQueryEffectiveAcceptBill(anMap, isOnlyNormal, flag, pageNum, pageSize,isCust);
            }
        }, "汇票信息查询失败", logger);
    }
    
    @RequestMapping(value = "/queryCanAnnulBill", method = RequestMethod.POST)
    public @ResponseBody String queryCanAnnulBill(HttpServletRequest request, String isOnlyNormal, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票已经审核然后想废止的信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webQueryCanAnnulAcceptBill(anMap, isOnlyNormal, flag, pageNum, pageSize);
            }
        }, "汇票信息查询失败", logger);
    }
    
    @RequestMapping(value = "/findAcceptBillByCustNo", method = RequestMethod.POST)
    public @ResponseBody String queryAcceptBill(HttpServletRequest request, String custNo) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webFindAcceptBillListByCustNo(custNo);
            }
        }, "汇票信息查询失败", logger);
    }

    @RequestMapping(value = "/findAcceptBillList", method = RequestMethod.POST)
    public @ResponseBody String findAcceptBillList(HttpServletRequest request, String isOnlyNormal) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息查询,入参：" + anMap + " isOnlyNormal=" + isOnlyNormal);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webFindAcceptBillList(anMap, isOnlyNormal);
            }
        }, "汇票信息查询失败", logger);
    }

    @RequestMapping(value = "/modifyAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String modifyAcceptBill(HttpServletRequest request, String fileList, boolean anConfirmFlag) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息修改,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webSaveModifyAcceptBillDO(anMap, fileList, anConfirmFlag);
            }
        }, "汇票信息编辑失败", logger);
    }
    
    @RequestMapping(value = "/addAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String addAcceptBill(HttpServletRequest request, String fileList,boolean confirmFlag) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票信息登记,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webAddAcceptBillDO(anMap, fileList, confirmFlag);
            }
        }, "汇票信息登记失败", logger);
    }
    
    @RequestMapping(value = "/saveAduitAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String saveAduitAcceptBill(Long id) {
        logger.info("汇票信息审核,入参：id=" + id);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webSaveAduitAcceptBill(id);
            }
        }, "汇票信息审核失败", logger);
    }
    
    /**
     * 登入状态的废止
     * @param refNo
     * @param version
     * @return
     */
    @RequestMapping(value = "/saveAnnulAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String saveAnnulAcceptBill(String refNo,String version) {
        logger.info("汇票信息作废,入参：refNo=" + refNo+"  version:"+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webSaveAnnulAcceptBill(refNo,version);
            }
        }, "汇票信息废止失败", logger);
    }
    
    /**
     * 生效单据的废止
     * @param refNo
     * @param version
     * @return
     */
    @RequestMapping(value = "/saveAnnulEffectiveAcceptBill", method = RequestMethod.POST)
    public @ResponseBody String saveAnnulEffectiveAcceptBill(String refNo,String version) {
        logger.info("汇票信息作废,入参：refNo=" + refNo+"  version:"+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webSaveCoreCustAnnulBill(refNo,version);
            }
        }, "汇票信息废止失败", logger);
    }

    /**
     * 查找单条票据的详情
     * @param anRefNo
     * @param anVersion
     * @return
     */
    @RequestMapping(value = "/findBillDO", method = RequestMethod.POST)
    public @ResponseBody String findBillDO(String refNo,String version) {
        logger.info("汇票查询详情,入参：refNo=" + refNo +" : version="+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webFindAcceptBillDOByRefNoVersion(refNo, version);
            }
        }, "汇票查询详情成功", logger);
    }
    
    @RequestMapping(value = "/saveAuditBillDO", method = RequestMethod.POST)
    public @ResponseBody String saveAuditBillDO(String refNo,String version) {
        logger.info("汇票审核,入参：refNo=" + refNo +" : version="+version);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webSaveAuditBillDOByRefNoVersion(refNo, version);
            }
        }, "汇票审核成功！", logger);
    }
    
    @RequestMapping(value = "/findAllFile", method = RequestMethod.POST)
    public @ResponseBody String findAllFile(Long id) {
        logger.info("汇票所有附件查询,入参：id=" + id);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webFindAllFile(id);
            }
        }, "汇票所有附件查询", logger);
    }
    
    @RequestMapping(value = "/findAcceptBillDetailsById", method = RequestMethod.POST)
    public @ResponseBody String findAcceptBillDetailsById(Long id) {
        logger.info("汇票详情,入参：id=" + id);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webFindAcceptBillDetailsById(id);
            }
        }, "汇票详情", logger);
    }
    
    @RequestMapping(value = "queryFinancedByFactor", method = RequestMethod.POST)
    public @ResponseBody String queryFinancedByFactor(HttpServletRequest request, Long factorNo) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("汇票所有附件查询,入参：factorNo" + factorNo + anMap);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfAcceptBillService.webQueryFinancedByFactor(anMap, factorNo);
            }
        }, "保理公司查询已融资汇票", logger);
    }
}
