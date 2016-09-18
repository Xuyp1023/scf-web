package com.betterjr.modules.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.betterjr.common.utils.UserUtils;
import com.betterjr.modules.sys.security.ShiroUser;
import com.betterjr.modules.sys.security.WechatAuthenticationFilter;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @RequestMapping(value = "login", method = {RequestMethod.GET,RequestMethod.POST})
    public String login(final String username, final String password) {
        System.out.println("controller " + username + ":" + password);
        return "/static/error.html";
    }

    @RequestMapping(value = "logout", method = {RequestMethod.GET,RequestMethod.POST})
    public String logout() {
        return "/static/error.html";
    }

    /**
     * 管理登录
     */
    @RequestMapping(value = "/wechatOauth2", method = RequestMethod.GET)
    public String author2Login(final HttpServletRequest request, final HttpServletResponse response, final Model model) {
        final ShiroUser principal = UserUtils.getPrincipal();

        /*        logger.info("wechatOauth2 login");
        if (logger.isDebugEnabled()) {
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }
         */
        // 如果已经登录，则跳转到管理首页
        if (principal != null && !principal.isMobileLogin()) {
            final String state = request.getParameter("state");
            //            logger.info("request state is :" + state);
            return "redirect:" + WechatAuthenticationFilter.findWorkUrl(state);
        }

        return "/static/wechat/main.html";
    }
}
