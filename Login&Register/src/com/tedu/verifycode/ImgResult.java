package com.tedu.verifycode;
/**
 * 验证码类
 */
public class ImgResult {
	private String code;
	private String img;
	
	public ImgResult() {}

	public ImgResult(String code, String img) {
		super();
		this.code = code;
		this.img = img;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
}
