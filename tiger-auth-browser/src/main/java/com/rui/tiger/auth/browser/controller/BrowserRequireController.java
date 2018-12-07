package com.rui.tiger.auth.browser.controller;

import com.rui.tiger.auth.core.properties.SecurityProperties;
import com.rui.tiger.auth.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录认证控制器
 *
 * @author CaiRui
 * @date 2018-12-5 12:44
 */
@RestController
@Slf4j
public class BrowserRequireController {

    //封装了引发跳转请求的工具类  https://blog.csdn.net/honghailiang888/article/details/53671108
    private RequestCache requestCache = new HttpSessionRequestCache();
    // spring的工具类：封装了所有跳转行为策略类
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    private static final String HTML_SUFFIX = ".html";



    /**
     * 当需要进行身份认证的时候跳转到此方法
     *
     * @param request  请求
     * @param response 响应
     * @return 将信息以JSON形式返回给前端
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("BrowserRequireController进来了 啦啦啦");
        // 从session缓存中获取引发跳转的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是：{}", redirectUrl);
            if (StringUtils.endsWithIgnoreCase(redirectUrl, HTML_SUFFIX)) {
                // 如果是HTML请求，那么就直接跳转到HTML，不再执行后面的代码
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页面");
    }


}
