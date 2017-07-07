package ly.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ly.net.HttpConnection;
import ly.net.URLProtocol;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText et1;
	private EditText et2;
	private HttpClient client = new DefaultHttpClient();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("yhm", "admin");
//		map.put("pas", "1234");
//		String str = HttpConnection.httpPost(URLProtocol.ROOT+URLProtocol.LOGIN, map, 0);// 获取连接返回值;
	}
	public void doC(View v){
		
	}
	public void doLogin(View v) {
		String uname = et1.getText().toString().trim();
		String pass = et2.getText().toString().trim();
		if ("".equals(uname) || "".equals(pass)) {
			Toast.makeText(this, "用户名密码错误", 1).show();
			return;
		}
		Intent intent = new Intent(MainActivity.this, WebActivity.class);
		startActivity(intent);
//		try {
//			// 16f7216y28.iask.in
//			HttpPost request = new HttpPost(
//					"http://192.168.1.102/ts_qxgl/login/login_in.jsp");
//			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//			postParameters.add(new BasicNameValuePair("yhm", uname));
//			postParameters.add(new BasicNameValuePair("pas", pass));
//			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
//					postParameters, "utf-8");
//			request.setEntity(formEntity);
//			HttpResponse response = client.execute(request);
//			if (response != null
//					&& response.getStatusLine().getStatusCode() == 200) {
//				HttpEntity entity = response.getEntity();
//				String txt = EntityUtils.toString(entity);
//				Log.i("main---------------",txt);
////				HttpPost r1 = new HttpPost(
////				"http://16f7216y28.iask.in/ts_qxgl/login/cd.jsp");
////				HttpResponse res1 = client.execute(r1);
////				if (res1 != null
////						&& res1.getStatusLine().getStatusCode() == 200) {
////					HttpEntity e = res1.getEntity();
////					String t = EntityUtils.toString(e);
////					Log.i("left-----------",t);
////				}
//				//Toast.makeText(this, txt.trim(), 1).show();
////				Intent intent = new Intent(MainActivity.this, HomeTabHost.class);
////				startActivity(intent);
//			} else {
//				Toast.makeText(this, "请求失败", 1).show();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			Toast.makeText(this, "网络异常", 1).show();
//		}
	}
}