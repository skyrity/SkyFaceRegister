package com.skyrity.utils;

import com.skyrity.bean.AccessToken;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Date;

public class wxCoreUtil {
	private static Logger log = LoggerFactory.getLogger(wxCoreUtil.class);
	// 微信应用服务器URL，需要根据不同用户服务器修改
	public final static String HOME_RUL = "https://www.skyrity.cn/SkyRegisterFace"; // 人脸注册
	public final static String HOME_WEB_RUL = "https://www.skyrity.cn/SkyRegisterFace"; // 人脸注册内部网站管理
	// ============APPID，需要根据微信公众号提供的信息修改==================
	public static final String APPID = "wx6f2981ca1f186b4c"; // 人脸注册小程序
	// ============APPSecret,需要根据微信公众号提供的信息修改===============
	public static final String APPSECRET = "95356594e115151fa6a31773dcd51459";// 人脸注册小程序

	// 小程序登录中通过code获取token令牌
	public static final String ACCESS_TOKEN_JSCODE_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

	// 模板消息获取Access_token
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 发送模板消息
	public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";

	/**
	 * 获取微信小程序 session_key 和 openid
	 * 
	 * @author macai
	 * @param code
	 *            调用微信登陆返回的Code
	 * @return
	 */
	public static JSONObject getSessionKeyOropenid(String code) {
		// 微信端登录code值
		String wxCode = code;

		String requestUrl = ACCESS_TOKEN_JSCODE_URL.replace("APPID", APPID)
				.replace("SECRET", APPSECRET).replace("JSCODE", wxCode); // 请求地址
		// https://api.weixin.qq.com/sns/jscode2session
		// 发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session
		// 接口获取openid用户唯一标识
		JSONObject jsonObject = doGetStr(requestUrl);
		return jsonObject;
	}

	/**
	 * 解密用户敏感数据获取用户信息
	 * 
	 * @author macai
	 * @param sessionKey
	 *            数据进行加密签名的密钥
	 * @param encryptedData
	 *            包括敏感数据在内的完整用户信息的加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @return
	 * @throws Base64DecodingException
	 */
	public static JSONObject getUserInfo(String sessionKey,
			String encryptedData, String iv) throws Base64DecodingException {
		// 被加密的数据
		byte[] dataByte = Base64.decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.decode(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.decode(iv);

		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base
						+ (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters
					.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取AccessTokenOAuth,直接通过微信获得
	 */
	public static AccessToken getAccessToken() {

		AccessToken token = null;
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace(
				"APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject != null && jsonObject.has("access_token")) {
			token = new AccessToken();
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));

			token.setExpiresDate(new Date(System.currentTimeMillis()
					+ token.getExpiresIn() * 1000));
			// mAccessToken = token;

			// writeIni("AccessToken", token.getToken());
			// writeIni("ExpiresIn", "" + token.getExpiresIn());
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// writeIni("ExpiresDate_OAuth",
			// "" + sdf.format(token.getExpiresDate()));

			log.info("AccessToken=" + token.toString());
		} else {
			if (jsonObject.has("errcode")) {
				log.error(jsonObject.toString());
			}
		}
		return token;
	}


	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static JSONObject doGetStr(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		// CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse;
		try {
			httpResponse = client.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.error(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
		}

		return jsonObject;
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param outStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static JSONObject doPostStr(String url, String outStr)
			throws UnsupportedEncodingException {
		DefaultHttpClient client = new DefaultHttpClient();
		// CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		if (outStr != null && !outStr.equals("")) {
			httpost.setEntity(new StringEntity(outStr, "UTF-8"));

		}
		HttpResponse response;
		try {
			response = client.execute(httpost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return jsonObject;
	}

}
