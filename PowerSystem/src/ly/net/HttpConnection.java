package ly.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import ly.util.FileUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * 网络交互类
 * 
 */
public class HttpConnection {
	private static HttpClient client = new DefaultHttpClient();

	/** 从服务器获取数据 */
	public static String httpPost(String urlStr, Map<String, String> map,
			int type) {
		String txt = "";
		try {
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					8000);
			HttpPost request = new HttpPost(urlStr);
			System.out.println(">>>>>>>" + urlStr);
			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
			if (type == 0) {// 纯数据上传
				if (map != null) {
					for (String key : map.keySet()) {
						String value = map.get(key);
						System.out.println(key + "-----------------" + value);
						list.add(new BasicNameValuePair(key, map.get(key)));
					}
					UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
							list, "gb2312");
					request.setEntity(formEntity);
				}
			} else {// 图片上传
				File file = new File(map.get("img"));
				MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
				FileBody fileBody = new FileBody(file);
				mpEntity.addPart("file", fileBody);
				mpEntity.addPart("type",  new StringBody(map.get("type")));
				mpEntity.addPart("lcmc",  new StringBody(map.get("lcmc")));
				request.setEntity(mpEntity);
			}
			HttpResponse response = client.execute(request);
			if (response != null
					&& response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				txt = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			txt = "连接超时";
		}
		return txt;

		// URL url = null;
		// HttpURLConnection connection = null;
		// try {
		// System.out.println(">>>>>>>" + urlStr);
		// url = new URL(urlStr);
		// connection = (HttpURLConnection) url.openConnection();
		// connection.setRequestMethod("POST");
		// connection.setReadTimeout(5000);
		// connection.setConnectTimeout(5000);
		// connection.setDoOutput(true);
		// connection.setDoInput(true);
		// connection.setUseCaches(false);
		// connection.setRequestProperty("charset", "utf-8");
		// // 获取输出流
		// DataOutputStream os = new DataOutputStream(connection
		// .getOutputStream());
		// os.write(param.getQueryStr().getBytes());
		// System.out.println("参数:" + param.getQueryStr());
		// os.flush();
		// os.close();
		// if (connection.getResponseCode() == 200) {
		// byte[] data = readData(connection.getInputStream());
		// String result = new String(data, "GBK");
		// System.out.println("result<<<<<<<" + result);
		// return result;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (connection != null) {
		// connection.disconnect();
		// }
		// }
		// return null;
	}

	/** 读取输入流 */
	public static byte[] readData(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		inStream.close();
		outStream.close();
		return data;
	}

	/** 获取网路图片 */
	public static byte[] getImg(String urlStr) {
		try {
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					8000);
			HttpPost request = new HttpPost(urlStr);
			System.out.println("URL>>>>>>>" + urlStr);
			HttpResponse response = client.execute(request);
			if (response != null
					&& response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				byte[] byteArray = EntityUtils.toByteArray(entity);
				return byteArray;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 获取网路图片 */
	public static InputStream getHttpStream(String strUrl, String image) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("请求uri失败");
			}
			InputStream inStream = conn.getInputStream();
			byte[] data = readData(inStream);
			FileUtil.saveFile(image, data);// 获取图片后保存图片到SDCard

			inStream.close();

			return new ByteArrayInputStream(data);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;

	}
}
