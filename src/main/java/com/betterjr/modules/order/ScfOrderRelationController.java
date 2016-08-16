package com.betterjr.modules.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.web.ControllerExceptionHandler;
import com.betterjr.common.web.ControllerExceptionHandler.ExceptionHandler;

@Controller
@RequestMapping("/Scf/OrderRelation")
public class ScfOrderRelationController {

    private static final Logger logger = LoggerFactory.getLogger(ScfOrderRelationController.class);

    @Reference(interfaceClass = IScfOrderRelationService.class)
    private IScfOrderRelationService scfOrderRelationService;

    @RequestMapping(value = "/addOrderRelation", method = RequestMethod.POST)
    public @ResponseBody String addOrderRelation(String enterType, Long enterId, String infoType, String infoIdList) {
        logger.info("订单关联关系添加,入参:enterType=" + enterType + " enterId=" + enterId + " infoType=" + infoType + " infoIds=" + infoIdList );
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderRelationService.webAddOrderRelation(enterType, enterId, infoType, infoIdList);
            }
        }, "订单关联关系添加失败", logger);

    }

    @RequestMapping(value = "/deleteOrderRelation", method = RequestMethod.POST)
    public @ResponseBody String deleteOrderRelation(String enterType, Long enterId, String infoType, Long infoId) {
        logger.info("订单关联关系删除,入参:enterType=" + enterType + " enterId=" + enterId + " infoType=" + infoType + " infoId=" + infoId);
        return ControllerExceptionHandler.exec(new ExceptionHandler() {
            public String handle() {
                return scfOrderRelationService.webSaveDeleteOrderRelation(enterType, enterId, infoType, infoId);
            }
        }, "订单关联关系删除失败", logger);
    }
}
