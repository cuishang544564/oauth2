package com.rui.tiger.auth.core.social;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社交账号绑定视图
 * @author CaiRui
 * @Date 2019-01-20 10:01
 */
@Component("connect/status")
public class TigerConnectionStatusView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");
        //key :社交供应商id  value：是否绑定
        Map<String,Boolean>  bindResult=new HashMap<>();
        for(String providerId:connections.keySet()){
            bindResult.put(providerId, CollectionUtils.isNotEmpty(connections.get(providerId)));
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(bindResult));//返回json
    }
}
