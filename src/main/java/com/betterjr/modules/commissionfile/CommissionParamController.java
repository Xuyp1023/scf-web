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
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;
import com.betterjr.common.web.Servlets;

@Controller
@RequestMapping(value = "/Scf/CommissionParam")
public class CommissionParamController {

    private static final Logger logger = LoggerFactory.getLogger(CommissionParamController.class);

    @Reference(interfaceClass = ICommissionParamService.class)
    private ICommissionParamService paramService;

    /**
     * 佣金发票参数配置
     * @param request
     * @return
     * custNo   平台id
     * coreCustNo  核心企业Id
     * taxRate      税率
     * interestRate   年利率
     */
    @RequestMapping(value = "/saveAddParam", method = RequestMethod.POST)
    public @ResponseBody String saveAddFile(HttpServletRequest request) {
        Map paramMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金发票参数配置,入参：" + paramMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return paramService.webSaveAddParam(paramMap);
            }
        }, "佣金发票参数配置失败", logger);
    }

    /**
     * 佣金发票参数配置查询
     * @param request
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     * 
     * custNo   平台id
     * coreCustNo  核心企业Id
     */
    @RequestMapping(value = "/queryParamList", method = RequestMethod.POST)
    public @ResponseBody String queryParamList(HttpServletRequest request, String flag, int pageNum, int pageSize) {
        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金发票参数配置查询,入参：" + anMap.toString());
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return paramService.webQueryParamList(anMap, flag, pageNum, pageSize);
            }
        }, "佣金发票参数配置失败", logger);
    }

    /**
     * 佣金发票参数配置查询
     * @param custNo  平台id
     * @param coreCustNo  核心企业Id
     * @return
     */
    @RequestMapping(value = "/findParamByCustNo", method = RequestMethod.POST)
    public @ResponseBody String findParamByCustNo(Long custNo, Long coreCustNo) {

        logger.info("佣金发票参数配置查询,入参： custNo=" + custNo + " coreCustNo=" + coreCustNo);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return paramService.webFindParamByCustNo(custNo, coreCustNo);
            }
        }, "佣金发票参数配置查询失败", logger);
    }

    /**
     * 佣金参数删除
     * @param paramId
     * @return
     */
    @RequestMapping(value = "/saveDeleteParamById", method = RequestMethod.POST)
    public @ResponseBody String saveDeleteParamById(Long paramId) {

        logger.info("佣金发票参数删除,入参： paramId=" + paramId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return paramService.webSaveDeleteParam(paramId);
            }
        }, "佣金发票参数配置删除失败", logger);
    }

    /**
     * 
     * 佣金发票参数配置修改
     * 
     * @param request
     * @return
     * 
     * id  主键
     * taxRate      税率
     * interestRate   年利率
     * 
     */
    @RequestMapping(value = "/saveUpdateParam", method = RequestMethod.POST)
    public @ResponseBody String saveUpdateParam(HttpServletRequest request) {

        Map<String, Object> anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("佣金发票参数配置修改,入参：" + anMap.toString());

        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            @Override
            public String handle() {
                return paramService.webSaveUpdateParam(anMap);
            }
        }, "佣金发票参数配置修改失败", logger);
    }

}
