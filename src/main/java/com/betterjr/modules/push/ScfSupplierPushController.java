package com.betterjr.modules.push;

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

/***
 * 推送管理
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/Scf/push")
public class ScfSupplierPushController {

    public static final Logger logger = LoggerFactory.getLogger(ScfSupplierPushController.class);

    @Reference(interfaceClass = IScfSupplierPushService.class)
    private IScfSupplierPushService scfSupplierPushService;

    @RequestMapping(value = "/addPush", method = RequestMethod.POST)
    public @ResponseBody String addPush(Long billId) {
        logger.info("推送, 入参:" + billId);
        try {
            return scfSupplierPushService.webAddPushSupplier(billId);
        }
        catch (RpcException btEx) {
            logger.error("推送异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("推送失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("推送失败，请检查").toJson();
        }
    }

    @RequestMapping(value = "/pushSign", method = RequestMethod.POST)
    public @ResponseBody String pushSign(String appNo) {
        logger.info("推送, 入参:" + appNo);
        try {
            scfSupplierPushService.webAddPushSign(appNo);
            return "OK";
        }
        catch (RpcException btEx) {
            logger.error("推送异常：" + btEx.getMessage());
            if (btEx.getCause() != null && btEx.getCause() instanceof BytterException) {
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("推送失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("推送失败，请检查").toJson();
        }
    }

}
