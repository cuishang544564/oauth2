package com.rui.tiger.auth.core.properties;

/**
 * 图片验证码配置类,根据情况添加具体配置项
 *
 * @author CaiRui
 * @date 2018-12-11 9:02
 * @see com.google.code.kaptcha.Constants
 */
public class ImageCaptchaProperties {

	/**
	 * 验证码长度
	 */
	private int size = 4;

	/**
	 * 宽度
	 */
	private int width;
	/**
	 * 高度
	 */
	private int height;
	/**
	 * 失效秒数
	 */
	private int expireAfterSecondes = 3 * 60;//默认三分钟
	/**
	 * 验证码拦截的路径 多个路径以,(逗号)进行分割
	 */
	private String interceptImageUrl;



	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getExpireAfterSecondes() {
		return expireAfterSecondes;
	}

	public void setExpireAfterSecondes(int expireAfterSecondes) {
		this.expireAfterSecondes = expireAfterSecondes;
	}

	public String getInterceptImageUrl() {
		return interceptImageUrl;
	}

	public void setInterceptImageUrl(String interceptImageUrl) {
		this.interceptImageUrl = interceptImageUrl;
	}

}
