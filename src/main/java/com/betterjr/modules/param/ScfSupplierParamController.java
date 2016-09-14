package com.betterjr.modules.param;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.betterjr.common.web.Servlets;

/***
 * 基础参数设置
 * @author hubl
 *
 */
@Controller
@RequestMapping(value="/Scf/Param")
public class ScfSupplierParamController {

    private final static Logger logger=LoggerFactory.getLogger(ScfSupplierParamController.class);
    
    @Reference(interfaceClass=IScfSupplierParamService.class)
    private IScfSupplierParamService scfSupplierParamService;
    
    @RequestMapping(value="/saveParam",method=RequestMethod.POST)
    public @ResponseBody String saveParam(HttpServletRequest request){
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info(" 入参:"+anMap.toString());
        try {
            return scfSupplierParamService.webSaveSupplierParam(anMap);
        } catch (RpcException btEx) {
            logger.error("参数设置异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("参数设置失败").toJson();
        } catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }
    

    @RequestMapping(value="/saveCoreParam",method=RequestMethod.POST)
    public @ResponseBody String saveCoreParam(HttpServletRequest request){
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("入参:"+anMap.toString());
        try {
            return scfSupplierParamService.webSaveCoreParam(anMap);
        } catch (RpcException btEx) {
            logger.error("参数设置异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("参数设置失败").toJson();
        } catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }
    

    @RequestMapping(value="/querySupplerParam",method=RequestMethod.POST)
    public @ResponseBody String querySupplerParam(String custNo){
        logger.info("入参:"+custNo);
        try {
            return scfSupplierParamService.webQuerySupplierParam(custNo);
        } catch (RpcException btEx) {
            logger.error("参数查询异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("参数查询失败").toJson();
        } catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }
    


    @RequestMapping(value="/queryCoreParam",method=RequestMethod.POST)
    public @ResponseBody String queryCoreParam(String custNo){
        logger.info("入参:"+custNo);
        try {
            return scfSupplierParamService.webQueryCoreParam(custNo);
        } catch (RpcException btEx) {
            logger.error("参数查询异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("参数查询失败").toJson();
        } catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }
    
}
