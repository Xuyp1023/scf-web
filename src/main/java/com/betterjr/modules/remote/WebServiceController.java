package com.betterjr.modules.remote;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.web.Servlets;
import com.betterjr.modules.document.ICustFileService;
import com.betterjr.modules.document.data.DownloadFileInfo;
import com.betterjr.modules.document.utils.CustFileClientUtils;
import com.betterjr.modules.document.utils.DownloadFileService;
import com.betterjr.modules.remote.utils.RemoteInvokeUtils;

@Controller
@RequestMapping(value = "/forAgency")
public class WebServiceController {
    
    protected static Logger logger = LoggerFactory.getLogger(WebServiceController.class);
    
    @Reference(interfaceClass=ICustFileService.class)
    private ICustFileService fileItemService;
    
    @RequestMapping(value = "/webservice/json", method = RequestMethod.POST)
    public @ResponseBody String jsonService(HttpServletRequest anRequest) {
        Map<String, String> map = Servlets.getParameters(anRequest);
        logger.info("入参:" + map.toString());
        try{
            return RemoteInvokeUtils.getWebServiceClient().process(map);
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            return "内部调用失败";
        }
    }
    
    @RequestMapping(value = "/webservice/coreEnt", method = RequestMethod.POST)
    public @ResponseBody String jsonCoreService(HttpServletRequest anRequest){
        Map<String, String> map = Servlets.getParameters(anRequest);
        if ("1".equals(map.get("based"))) {
            try {
                map.put("data", new String(Base64.decode(map.get("data")), "UTF-8"));
            }
            catch (final UnsupportedEncodingException e) {
            }
        }
        logger.info("入参:" + map.toString());
        try{
            return RemoteInvokeUtils.getWebServiceClient().process(map);
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            return "内部调用失败";
        }
    }
    
    @RequestMapping(value = "/webservice/fileDownload")
    public void fileDownload(String fileToken, int dataType, HttpServletResponse response) throws UnsupportedEncodingException {
        DownloadFileInfo fileInfo = DownloadFileService.exactDownloadFile(fileToken);
        String msg = "";
        boolean isText = true;
        if (fileInfo != null) {
            logger.debug("file download:fileToken=" + fileToken + "dataType=" + dataType + ";file=" + fileInfo.toString());
            if (dataType == 0) {
                String basePath = this.fileItemService.findFileBasePath();
                CustFileClientUtils.fileDownloadWithOpenType(response, fileInfo, "attachment", basePath);
                isText = false;
            }
            else {
                try {
                    msg = RemoteInvokeUtils.getWebServiceClient().signFile(fileInfo.getPartnerCode(),fileToken);
                }
                catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    msg = "内部调用失败";
                }
            }
        }
        else {
            msg = "file not find or file invalid";
        }
        if (isText) {
            response.reset();
            response.setContentType("text/html;UTF-8");
            try {
                response.getWriter().append(msg);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}