package com.betterjr.modules.commissionfile;

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

@Controller
@RequestMapping(value = "/Scf/CommissionInvoiceCustInfo")
public class CommissionInvoiceCustInfoController {
    
 private static final Logger logger = LoggerFactory.getLogger(CommissionInvoiceCustInfoController.class);
    
    @Reference(interfaceClass=ICommissionInvoiceCustInfoService.class)
    private ICommissionInvoiceCustInfoService custInfoService;
    
    
    /**
     * 发票抬头新增配置
     * custNo  企业id
     * coreCustNo
     * coreBank   银行
     * coreBankAccount  银行帐号
     * coreTaxPayerNo  纳税人识别号
     * corePhone  电话
     * coreAddress  地址
     * coreInfoType  发票抬头的类型  1  企业     0个人
     * isLatest  是否默认  0 不是默认   1是默认
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveAddInvoiceCustInfo", method = RequestMethod.POST)
    public @ResponseBody String saveAddInvoiceCustInfo(HttpServletRequest request) {
        Map paramMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票抬头新增配置,入参：" + paramMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return custInfoService.webSaveAddInvoiceCustInfo(paramMap);
            }
        }, "发票抬头新增失败", logger);
    }
    
    /**
     * 发票抬头配置查询
     * custNo  平台id
     * coreCustNo  核心企业id
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryInvoiceCustInfoList", method = RequestMethod.POST)
    public @ResponseBody String queryInvoiceCustInfoList(HttpServletRequest request,  String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票抬头配置查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return custInfoService.webQueryInvoiceCustInfoList(anMap, flag, pageNum, pageSize);
            }
        }, "发票抬头失败", logger);
    }
    
    /**
     * 发票抬头查询   
     * 通过平台Id 和 核心企业id查询当前公司默认的生效的发票抬头
     * @param custNo
     * @param coreCustNo
     * @return
     */
    @RequestMapping(value = "/findEffectiveByCustNo", method = RequestMethod.POST)
    public @ResponseBody String findEffectiveByCustNo(Long custNo,Long coreCustNo) {
        
        logger.info("发票抬头查询,入参： custNo=" + custNo +" coreCustNo="+coreCustNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return custInfoService.webFindInvoiceCustInfoEffectiveByCustNo(custNo, coreCustNo);
            }
        }, "发票抬头查询失败", logger);
    }
    
    
    /**
     * 发票抬头修改
     * id
     * custNo  企业id
     * coreCustNo
     * coreBank   银行
     * coreBankAccount  银行帐号
     * coreTaxPayerNo  纳税人识别号
     * corePhone  电话
     * coreAddress  地址
     * coreInfoType  发票抬头的类型  1  企业     0个人
     * isLatest  是否默认  0 不是默认   1是默认
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveUpdateInvoiceCustInfo", method = RequestMethod.POST)
    public @ResponseBody String saveUpdateInvoiceCustInfo(HttpServletRequest request) {
        
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("发票抬头修改,入参：" + anMap.toString());
        
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return custInfoService.webSaveUpdateInvoiceCustInfo(anMap);
            }
        }, "发票抬头修改失败", logger);
    }

    /**
     * 发票抬头删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/saveDeleteCustInfoById", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteCustInfoById(Long id) {
        
        logger.info("佣金发票抬头删除,入参： id=" + id );
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return custInfoService.webSaveDeleteCustInfo(id);
            }
        }, "佣金发票抬头删除失败", logger);
    }
}
