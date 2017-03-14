// Copyright (c) 2014-2017 Bytter. All rights reserved.
// ============================================================================
// CURRENT VERSION
// ============================================================================
// CHANGE LOG
// V2.0 : 2017年3月6日, liuwl, creation
// ============================================================================
package com.betterjr.modules.remote;

import java.io.UnsupportedEncodingException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.betterjr.common.utils.Encodes;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.cert.dubboclient.CustCertDubboClientService;
import com.betterjr.modules.cert.entity.CustCertInfo;
import com.betterjr.modules.remote.utils.RemoteInvokeUtils;

/**
 * @author liuwl
 * 通过此服务访问的
 */

@Controller
@RequestMapping(value = "/access")
public class AccessWebServiceController {
    protected static Logger logger = LoggerFactory.getLogger(AccessWebServiceController.class);

    @Autowired
    private CustCertDubboClientService certService;

    @RequestMapping(value = "/webservice/coreEnt", method = RequestMethod.POST)
    public @ResponseBody String jsonCoreService(final HttpServletRequest anRequest) {
        final Map<String, String> map = Servlets.getParameters(anRequest);
        if ("1".equals(map.get("based"))) {
            try {
                map.put("data", new String(Base64.decode(map.get("data")), "UTF-8"));
            }
            catch (final UnsupportedEncodingException e) {
            }
        }
        logger.info("入参:" + map.toString());
        try {
            final X509Certificate cert = Servlets.findCertificate(anRequest);
            if (cert != null) {

                final CustCertInfo certInfo= certService.checkValidityWithBase64(Encodes.encodeBase64(cert.getEncoded()));
                if (certInfo.validCertInfo()) {
                    map.put("cert", certInfo.getSerialNo());
                    return RemoteInvokeUtils.getWebServiceClient().process(map);
                } else {
                    return "内部调用失败";
                }
            } else {
                return "内部调用失败";
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return "内部调用失败";
        }
    }
}
