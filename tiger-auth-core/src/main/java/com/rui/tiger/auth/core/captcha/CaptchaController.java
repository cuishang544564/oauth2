package com.rui.tiger.auth.core.captcha;

import com.rui.tiger.auth.core.captcha.sms.SmsCaptchaSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码控制器
 *
 * @author CaiRui
 * @date 2018-12-10 12:13
 */
@RestController
@Slf4j
public class CaptchaController {
    @Autowired
    private CaptchaCreateService captchaCreateService;
    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/captcha/{type}")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        log.info("获取验证码开始");
        captchaCreateService.createCaptcha(new ServletWebRequest(request, response), type);
        log.info("获取验证码结束");
    }
}
