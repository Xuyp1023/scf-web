package com.betterjr.modules.agreement.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;





import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.betterjr.common.utils.BetterStringUtils;
import com.betterjr.common.utils.FileUtils;
import com.betterjr.common.utils.MimeTypesHelper;
import com.betterjr.modules.document.entity.CustFileItem;

public abstract class CustFileClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(CustFileClientUtils.class);
    
    /**
     * 下载文件
     * 
     * @param response
     * @param anFileItem
     */
    public static void fileDownload(HttpServletResponse response, CustFileItem anFileItem,String basePath) {

        fileDownloadWithOpenType(response, anFileItem, null,basePath);
    }

    public static void fileDownloadWithOpenType(HttpServletResponse response, CustFileItem anFileItem, String anOpenType,String basePath) {
        OutputStream os = null;
        String msg = null;
        try {
            basePath = "/workdata";
            if (anFileItem != null) {
                File file = FileUtils.getRealFile(basePath + anFileItem.getFilePath());
                if (file != null) {
                    String openType = anOpenType;
                    if (BetterStringUtils.isBlank(openType)) {
                        if (anFileItem.isInner(MimeTypesHelper.getMimeType(anFileItem.getFileType()))) {
                            openType = "inline";
                        }
                        else {
                            openType = "attachment";
                        }
                    }
                    String fileName = anFileItem.getFileName();
                    StringBuilder sb = new StringBuilder(100);
                    sb.append(openType).append("; ").append("filename=").append(java.net.URLEncoder.encode(fileName, "UTF-8"));
                    os = response.getOutputStream();
                    response.setHeader("Content-Disposition", sb.toString());
                    response.setContentType(anFileItem.getFileType());
                    FileUtils.copyFile(file, os);
                    return;
                }
                else {
                    msg = "下载文件不存在！";
                }
            }
            else {
                msg = "没有获得下载文件的任何信息";
            }
        }
        catch (IOException e) {
            logger.error("下载文件失败，请检查；" + anFileItem, e);
            msg = "出现IO异常，请稍后再试!";
        }
        finally {
            if (msg != null) {
                response.reset();
                response.setContentType("text/html;UTF-8");
                try {
                    response.getWriter().append(msg);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            IOUtils.closeQuietly(os);
        }
    }
}