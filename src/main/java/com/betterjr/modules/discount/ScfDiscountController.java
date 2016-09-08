package com.betterjr.modules.discount;

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
 * 贴现管理
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/Scf/Discount")
public class ScfDiscountController {

    private static final Logger logger = LoggerFactory.getLogger(ScfDiscountController.class);
    
    @Reference(interfaceClass=IScfDiscountService.class)
    private IScfDiscountService scfDiscountService;
    
    @RequestMapping(value = "/addDiscount", method = RequestMethod.POST)
    public @ResponseBody String addDiscount(HttpServletRequest request) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("添加贴现, 入参:" + anMap.toString());
        try {
            return scfDiscountService.webAddDiscount(anMap);
        }
        catch (RpcException btEx) {
            logger.error("新增贴现异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("新增贴现失败").toJson();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return AjaxObject.newError("新增贴现失败，请检查").toJson();
        }
    } 
    
    @RequestMapping(value = "/queryDiscount", method = RequestMethod.POST)
    public @ResponseBody String queryDiscount(HttpServletRequest request, String pageNum, String pageSize) {
        Map anMap = Servlets.getParametersStartingWith(request, "");
        logger.info("查询贴现, 入参:"+anMap.toString());
        try {
            return scfDiscountService.webQueryDiscount(anMap, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        } catch (RpcException btEx) {
            logger.error("查询贴现异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("查询贴现失败").toJson();
        } catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }
    

    @RequestMapping(value = "/queryDiscountById", method = RequestMethod.POST)
    public @ResponseBody String queryDiscountById(Integer discountId) {
        try {
            return scfDiscountService.webQueryDiscountById(discountId);
        } catch (RpcException btEx) {
            logger.error("根据id查询贴现异常："+btEx.getMessage());
            if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
                return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
            }
            return AjaxObject.newError("根据id查询贴现失败").toJson();
        } catch (Exception ex) {
            return AjaxObject.newError("service failed").toJson();
        }
    }
    
}
