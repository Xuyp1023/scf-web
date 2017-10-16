// Copyright (c) 2014-2017 Bytter. All rights reserved.
// ============================================================================
// CURRENT VERSION
// ============================================================================
// CHANGE LOG
// V2.0 : 2017年5月6日, liuwl, creation
// ============================================================================
package com.betterjr.modules.commission;

import static com.betterjr.common.web.ControllerExceptionHandler.exec;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.betterjr.common.utils.UserUtils;
import com.betterjr.common.web.AjaxObject;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.config.dubboclient.DomainAttributeDubboClientService;

/**
 * @author liuwl
 *
 */
@Controller
@RequestMapping(value = "/Scf/CommissionConfig")
public class CommissionConfigController {
    private static final Logger logger = LoggerFactory.getLogger(CommissionConfigController.class);

    @Resource
    private DomainAttributeDubboClientService domainAttributeDubboClientService;

    @RequestMapping(value = "/queryConfig", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String queryConfig(final HttpServletRequest request) {
        if (UserUtils.platformUser()) {
            final String operOrg = UserUtils.getOperatorInfo().getOperOrg();
            final BigDecimal interestRate = domainAttributeDubboClientService.findMoney(operOrg,
                    "PLAT_COMMISSION_INTEREST_RATE");
            final BigDecimal taxRate = domainAttributeDubboClientService.findMoney(operOrg, "PLAT_COMMISSION_TAX_RATE");
            final String custName = domainAttributeDubboClientService.findString(operOrg,
                    "PLAT_COMMISSION_MAKE_CUSTNAME");
            final String operator = domainAttributeDubboClientService.findString(operOrg,
                    "PLAT_COMMISSION_MAKE_OPERATOR");
            final Long confirmDays = domainAttributeDubboClientService.findNumber(operOrg,
                    "PLAT_COMMISSION_CONFIRM_DAYS");
            final String importTemplate = domainAttributeDubboClientService
                    .findString("GLOBAL_COMMISSION_IMPORT_TEMPLATE");
            final String exportTemplate = domainAttributeDubboClientService
                    .findString("GLOBAL_COMMISSION_EXPORT_TEMPLATE");
            final String dailyTemplate = domainAttributeDubboClientService
                    .findString("GLOBAL_COMMISSION_DAILY_TEMPLATE");
            final String monthlyTemplate = domainAttributeDubboClientService
                    .findString("GLOBAL_COMMISSION_MONTHLY_TEMPLATE");
            final Map<String, Object> certLicenseObj = (Map<String, Object>) domainAttributeDubboClientService
                    .findObject("GLOBAL_TIANWEI_CERT_LICENSE");

            final String certLicense = certLicenseObj != null ? (String) certLicenseObj.get("certLicense") : null;

            final Map<String, Object> param = new HashMap<>();
            param.put("interestRate", interestRate);
            param.put("taxRate", taxRate);
            param.put("custName", custName);
            param.put("operator", operator);
            param.put("confirmDays", confirmDays);
            param.put("importTemplate", importTemplate);
            param.put("exportTemplate", exportTemplate);
            param.put("dailyTemplate", dailyTemplate);
            param.put("monthlyTemplate", monthlyTemplate);
            param.put("certLicense", certLicense);
            return AjaxObject.newOk("参数查询成功！", param).toJson();
        } else {
            return AjaxObject.newError("操作失败！").toJson();
        }
    }

    @RequestMapping(value = "/findCertLicense", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String findCertLicense(final HttpServletRequest request) {
        final Map<String, Object> certLicenseObj = (Map<String, Object>) domainAttributeDubboClientService
                .findObject("GLOBAL_TIANWEI_CERT_LICENSE");

        final String certLicense = (String) certLicenseObj.get("certLicense");

        return AjaxObject.newOk("参数查询成功！", certLicense).toJson();
    }

    @RequestMapping(value = "/saveConfig", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String saveConfig(final HttpServletRequest request, final BigDecimal interestRate,
            final BigDecimal taxRate, final String custName, final String operator, final Long confirmDays,
            final String importTemplate, final String exportTemplate, final String dailyTemplate,
            final String monthlyTemplate, final String certLicense) {
        if (UserUtils.platformUser()) {
            final Map<String, Object> param = Servlets.getParametersStartingWith(request, "");
            logger.info("参数设置,入参：" + param.toString());
            final String operOrg = UserUtils.getOperatorInfo().getOperOrg();
            return exec(() -> {

                domainAttributeDubboClientService.saveMoney(operOrg, "PLAT_COMMISSION_INTEREST_RATE", interestRate);
                domainAttributeDubboClientService.saveMoney(operOrg, "PLAT_COMMISSION_TAX_RATE", taxRate);
                domainAttributeDubboClientService.saveString(operOrg, "PLAT_COMMISSION_MAKE_CUSTNAME", custName);
                domainAttributeDubboClientService.saveString(operOrg, "PLAT_COMMISSION_MAKE_OPERATOR", operator);
                domainAttributeDubboClientService.saveNumber(operOrg, "PLAT_COMMISSION_CONFIRM_DAYS", confirmDays);

                domainAttributeDubboClientService.saveString("GLOBAL_COMMISSION_IMPORT_TEMPLATE", importTemplate);
                domainAttributeDubboClientService.saveString("GLOBAL_COMMISSION_EXPORT_TEMPLATE", exportTemplate);
                domainAttributeDubboClientService.saveString("GLOBAL_COMMISSION_DAILY_TEMPLATE", dailyTemplate);
                domainAttributeDubboClientService.saveString("GLOBAL_COMMISSION_MONTHLY_TEMPLATE", monthlyTemplate);
                final Map<String, Object> certLicenseObj = new HashMap<>();
                certLicenseObj.put("certLicense", certLicense);
                domainAttributeDubboClientService.saveObject("GLOBAL_TIANWEI_CERT_LICENSE", certLicenseObj);
                return AjaxObject.newOk("参数保存成功！").toJson();
            }, "保存参数出错！", logger);
        } else {
            return AjaxObject.newError("操作失败！").toJson();
        }
    }
}
