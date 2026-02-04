package org.uche.t0ken.api.gdax;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.jboss.logging.Logger;


public class GdaxUtil {

	public static ZoneId zone = ZoneOffset.UTC;
	
	private static Logger logger = Logger.getLogger(GdaxUtil.class);
	
	
	public static String getTsString(Instant timestamp) {
		StringBuffer buf = new StringBuffer(32);
		buf = buf.append(timestamp.getEpochSecond());
		/*return buf.toString();*/
		int nanos = timestamp.getNano();
		if (nanos != 0) {
			buf = buf.append(".").append(nanos);
			String res = buf.toString();
			res = res.replaceFirst("0+$", "");
			return res;
		} else {
			return buf.toString();
		}
		
		
	}
	
	
	
	
	
	private static String encode(String key, String data) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		byte[] decodedKey = Base64.getDecoder().decode(key);
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(decodedKey, "HmacSHA256");
		sha256_HMAC.init(secret_key);
		return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
		 
	}
	public static String getBase64Sign(String privateKey, Instant timestamp, String method, String requestPath, String bodyJson) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException  {
		
		int bufLen = requestPath.length() + 40;
		if (bodyJson != null) bufLen+=bodyJson.length();
		
		StringBuffer buf = new StringBuffer(bufLen);
		buf = buf.append(getTsString(timestamp)).append(method.toUpperCase()).append(requestPath);
			
		if (bodyJson != null) {
			buf.append(bodyJson);
		}
		//logger.warn("getBase64Sign: buf: " + buf.toString() + " pk: " + privateKey);
		return encode(privateKey, buf.toString());
	}
	
	public static Subscribe signSubscribe(Subscribe subscribe, String base64SecretKey, String apiKey, String passphrase) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		/**
		 * private signMessage(msg: any): any {
	        const headers: AuthHeaders = this.coinbaseProAPI.getSignature('GET', '/users/self/verify', '');
	        msg.signature = headers['CB-ACCESS-SIGN'];
	        msg.key = headers['CB-ACCESS-KEY'];
	        msg.timestamp = headers['CB-ACCESS-TIMESTAMP'];
	        msg.passphrase = headers['CB-ACCESS-PASSPHRASE'];
	        return msg;
	    }
		 */
		Instant now = Instant.now();
		String method = "GET";
		String requestPath="/users/self/verify";
		String sign = GdaxUtil.getBase64Sign(base64SecretKey, now, method, requestPath, null);
		subscribe.setKey(apiKey);
		subscribe.setPassphrase(passphrase);
		subscribe.setTimestamp(GdaxUtil.getTsString(now));
		subscribe.setSignature(sign);
		
		return subscribe;
		
	}


}
