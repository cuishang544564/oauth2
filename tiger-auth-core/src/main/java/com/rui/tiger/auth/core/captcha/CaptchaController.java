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

    public static final String CAPTCHA_SESSION_KEY = "captcha_session_key";
    private static final String FORMAT_NAME = "JPEG";

    @Autowired
    private CaptchaGenerate imageCaptchaGenerate;
    @Autowired
    private CaptchaGenerate smsCaptchaGenerate;
    @Autowired
    private SmsCaptchaSend smsCaptchaSend;

    @Autowired
    private CaptchaCreateService captchaCreateService;

    //spring session 工具类
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

 /*   *//**
     * 获取图片验证码
     *
     * @param request
     * @param response
     * @throws IOException
     *//*
    @GetMapping("/captcha/image")
    public void createKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.接口生成验证码
        ImageCaptchaVo imageCaptcha = (ImageCaptchaVo) imageCaptchaGenerate.generate();
        //2.保存到session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), CAPTCHA_SESSION_KEY, imageCaptcha);
        //3.写到响应流中
        response.setHeader("Cache-Control", "no-store, no-cache");// 没有缓存
        response.setContentType("image/jpeg");
        ImageIO.write(imageCaptcha.getImage(), FORMAT_NAME, response.getOutputStream());
    }*/
   /* *//**
     * 获取图片验证码
     *
     * @param request
     * @param response
     * @throws IOException
     *//*
    @GetMapping("/captcha/sms")
    public void createSms(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        log.info("获取短信验证码");
        //1.获取短信验证码
        CaptchaVo captchaVo = smsCaptchaGenerate.generate();
        //2.保存到session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), CAPTCHA_SESSION_KEY + "sms", captchaVo);
        //3.发送
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        smsCaptchaSend.sendSms(mobile, captchaVo.getCode());
    }
*/

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
