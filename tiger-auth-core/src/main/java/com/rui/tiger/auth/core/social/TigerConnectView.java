package com.rui.tiger.auth.core.social;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 社交账号绑定成功返回界面 要适配多个社交账号 放在配置中管理bean
 * getViewPath() + providerId + "Connected";// connect/wechat/Connected
 * @author CaiRui
 * @Date 2019-01-20 11:19
 */
public class TigerConnectView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (model.get("connections") == null) {
            response.getWriter().write("<h3>解绑成功</h3>");
        } else {
            response.getWriter().write("<h3>绑定成功</h3>");
        }
    }
}
