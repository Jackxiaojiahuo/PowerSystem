package ly.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends Activity {
	private WebView webView;
	private String imgname;
	String res = "1";
//	private final static String ROOTURL = "http://192.168.1.103/ts_qxgl";
	private final static String ROOTURL = "http://192.168.0.104/ts_qxgl";
	public static final String SDCARD_ROOT_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();// 路径
	public static String SAVE_PATH_IN_SDCARD = "/bidata/"; // 图片及其他数据保存文件夹
	public static String IMAGE_CAPTURE_NAME = "cameraTmp.png"; // 照片名称
	// 定位都要通过LocationManager这个类实现
	private LocationManager locationManager;
	private String provider;
	private String longiandlati = "";
	private String bh;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		//
		webView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = webView.getSettings();
		// 设置WebView支持JavaScript
		webSettings.setJavaScriptEnabled(true);

		// String url =
		// "http://192.168.0.104/Test/public/login_in.jsp?yhm="+yhm+"&pas="+pas;
		// String url = "http://192.168.1.103/ts_qxgl/public/login.jsp";
		// String url = "http://192.168.199.213/ts_qxgl/public/login.jsp";
		String url = ROOTURL + "/public/login.jsp";
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		// 在js中调用本地java方法
		webView.addJavascriptInterface(new JsInterface(this), "GPS");

		webView.clearCache(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 关闭webview中缓存
		// 添加客户端支持
		webView.setWebChromeClient(new WebChromeClient());
	}

	/**
	 * 使点击回退按钮不会直接退出整个应用程序而是返回上一个页面
	 */
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);// 退出整个应用程序
	}

	private class JsInterface {
		private Context mContext;

		public JsInterface(Context context) {
			this.mContext = context;
		}

		// 给html提供的方法，js中可以通过：var str = window.jsObj.HtmlcallJava(); 获取到
		public String HtmlcallJava() {
			return longiandlati;
		}

		// 在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
		public void showInfoFromJs(String name) {
			if (name.indexOf(",") > 0) {
				Toast.makeText(WebActivity.this, name, Toast.LENGTH_SHORT)
						.show();
				bh = name.substring(0, name.indexOf(","));
				type = name.substring(name.indexOf(",") + 1);
				Log.i("页面传值", bh + "," + type);
				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(i, 1);
			}
			webView.clearHistory();
			// webView.clearCache(true);
		}
	}

	// 在java中调用js代码
	public void sendInfoToJs() {
		String msg = "测试";
		// 调用js中的函数：showInfoFromJava(msg)
		webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
	}

	// 位置监听器
	LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		}

		@Override
		public void onProviderEnabled(String arg0) {
		}

		@Override
		public void onProviderDisabled(String arg0) {
		}

		@Override
		public void onLocationChanged(Location arg0) {
			// 更新当前经纬度
		}
	};

	private String getLocation() {
		// 获取定位服务
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 获取当前可用的位置控制器
		List<String> list = locationManager.getProviders(true);
		if (list.contains(LocationManager.GPS_PROVIDER)) {
			// 是否为GPS位置控制器
			provider = LocationManager.GPS_PROVIDER;
		} else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
			// 是否为网络位置控制器
			provider = LocationManager.NETWORK_PROVIDER;

		} else {
			longiandlati = "请检查网络或GPS是否打开";
			Toast.makeText(WebActivity.this, "请检查网络或GPS是否打开",
					Toast.LENGTH_SHORT).show();
			return "";
		}
		try {
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				// 获取当前位置，这里只用到了经纬度
				longiandlati = "经度：" + location.getLongitude() + "纬度："
						+ location.getLatitude();
			}
			// 绑定定位事件，监听位置是否改变
			// 第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
			// 第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器
			locationManager.requestLocationUpdates(provider, 2000, 2,
					locationListener);
		} catch (SecurityException e) {
			e.printStackTrace();
			longiandlati = "定位失败";
			Toast.makeText(WebActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
		}
		return longiandlati;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {// 判断请求的编码是否为1
			if (resultCode == Activity.RESULT_OK) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				IMAGE_CAPTURE_NAME = new DateFormat().format("yyyyMMdd_hhmmss",
						Calendar.getInstance(Locale.CHINA))
						+ ".jpg";
				Toast.makeText(this, IMAGE_CAPTURE_NAME, Toast.LENGTH_LONG)
						.show();
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b = null;
				// ???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？
				File file = new File("/sdcard/fu/");
				file.mkdirs();// 创建文件夹
				SAVE_PATH_IN_SDCARD = "/sdcard/fu/" + IMAGE_CAPTURE_NAME;
				try {
					b = new FileOutputStream(SAVE_PATH_IN_SDCARD);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// 获取定位服务
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				// 获取当前可用的位置控制器
				List<String> list = locationManager.getProviders(true);
				if (list.contains(LocationManager.GPS_PROVIDER)) {
					// 是否为GPS位置控制器
					provider = LocationManager.GPS_PROVIDER;
				} else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
					// 是否为网络位置控制器
					provider = LocationManager.NETWORK_PROVIDER;

				} else {
					Toast.makeText(WebActivity.this, "请检查网络或GPS是否打开",
							Toast.LENGTH_SHORT).show();
				}
				Location location = locationManager
						.getLastKnownLocation(provider);
				if (location != null) {
					// 获取当前位置，这里只用到了经纬度
					longiandlati ="lo:"+location.getLongitude() + ",la:"
							+ location.getLatitude();
				}
				// 绑定定位事件，监听位置是否改变
				// 第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
				// 第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器
				locationManager.requestLocationUpdates(provider, 2000, 2,
						locationListener);
				Map<String, String> map = new HashMap<String, String>();
				map.put("img", SAVE_PATH_IN_SDCARD);
				map.put("type", type);
				map.put("lcmc", bh);
				map.put("gps", longiandlati);
				Log.i(">>>>>>>>>>>>>GPS",  longiandlati);
				res = httpPost(ROOTURL + "/qxdl1/pic_testdb.jsp", map, 1);
				Log.i(">>>>>>>>>>>>>", "上传结果" + res);
				File f = new File(SAVE_PATH_IN_SDCARD);// 上传成功后删除本地文件
				if (f.exists())
					f.delete();
				Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/** 从服务器获取数据 */
	public String httpPost(String urlStr, Map<String, String> map, int type) {
		HttpClient client = new DefaultHttpClient();
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
				mpEntity.addPart("type", new StringBody(map.get("type")));
				mpEntity.addPart("lcmc", new StringBody(map.get("lcmc")));
				mpEntity.addPart("gps", new StringBody(map.get("gps")));
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
		client.getConnectionManager().shutdown();
		return txt;
	}

}
