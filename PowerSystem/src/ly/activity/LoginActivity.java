package ly.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ly.entity.Menu;
import ly.net.ParseJasonDataService;
import ly.net.URLProtocol;
import ly.util.LogUtil;
import ly.util.ScreenUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/** 登录界面 */
public class LoginActivity extends Activity {

	private EditText et1;
	private EditText et2;
	public static Handler mHandler;
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Intent tent = new Intent(this, ParseJasonDataService.class);
//		startService(tent);
		setContentView(R.layout.main);
		// Intent tent = new Intent(this, ParseJasonDataService.class);
		// startService(tent);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
	}

	public void doLogin(View v) {
		// 测试用
		// EditText url_txt = (EditText) findViewById(R.id.url);
		// URLProtocol.ROOT = url_txt.getText().toString();
		// 测试用完
		final String name = et1.getText().toString().trim();
		final String pw = et2.getText().toString().trim();
		intent = new Intent(LoginActivity.this,
				HomeTabHost.class);//请求地址
		if ("".equals(name) || "".equals(pw)) {// 用户名或密码为空
			ScreenUtil.showMsg(LoginActivity.this,
					getString(R.string.input_null));
		} else {
			mHandler = new Handler() {// 返回请求
				@Override
				public void handleMessage(Message msg) {
//					ScreenUtil.hideLoading();
					if(msg.what==1){//登录验证返回数据
						Bundle bundle = msg.getData();
						String re = bundle.getString("re");
						if (!"0".equals(re)) {
							String str = bundle.getString("str");
							if (str.indexOf("top.jsp") > 0) {// 
								Log.i("Bundle数据>>>>>>>>>>", str);
								ScreenUtil.showMsg(LoginActivity.this,
										getString(R.string.login_success));// 提示登录成功
								//-----------------------------
								//ScreenUtil.showProgressDialog(LoginActivity.this);// 显示进度条
								//--------------------获取用户信息-----------------------------------
								String str1 = bundle.getString("str1");
								Log.i("11111>>>>>>>>>>>>>>>", str1);
								String t = str1;
								t=t.substring(t.indexOf("<sa>")+4,t.lastIndexOf("</sa>"));
								Log.i("字符串>>>>>>>>>>>>>>>", t);
								t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
								JSONObject json=null;
								try {
									json = new JSONObject(t);
									String r=json.getString("角色");
									String d=json.getString("部门");
									String n=json.getString("姓名");
									//----------------------需要发送的参数--------------------
									intent.putExtra("r", r);
									intent.putExtra("d", d);
									intent.putExtra("n", n);
									Log.i("新截取-----------", r + "," + d + "," + n);
								}catch(Exception e){
									e.printStackTrace();
								}
								
								//------------------------------------获取菜单列表-----------------
								String str2 = bundle.getString("str2");
								Log.i("2222>>>>>>>>>>>>>>>",str2);
								TabFirstActivity.tabFirstMenus=new ArrayList<Menu>();
								if(str2.indexOf("缺陷登录")>0){
									Menu tabFirstMenu1 = new Menu(R.drawable.item_menu, "缺陷登录");
									TabFirstActivity.tabFirstMenus.add(tabFirstMenu1);
								}
								if(str2.indexOf("专职审核")>0){
									Menu tabFirstMenu2 = new Menu(R.drawable.item_menu, "专职审核");
									TabFirstActivity.tabFirstMenus.add(tabFirstMenu2);
								}
								if(str2.indexOf("主任批准")>0){
									Menu tabFirstMenu3 = new Menu(R.drawable.item_menu, "主任批准");
									TabFirstActivity.tabFirstMenus.add(tabFirstMenu3);
								}
								if(str2.indexOf("缺陷处理")>0){
									 Menu tabFirstMenu4 = new Menu(R.drawable.item_menu, "缺陷处理");
									 TabFirstActivity. tabFirstMenus.add(tabFirstMenu4);
								}
								if(str2.indexOf("缺陷验收")>0){
									 Menu tabFirstMenu5 = new Menu(R.drawable.item_menu, "缺陷验收");
									 TabFirstActivity.tabFirstMenus.add(tabFirstMenu5);
								}
								LogUtil.d("1", "%%%% TabHost主页初始化完成 %%%%");
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								LoginActivity.this.finish();
							} else {
								Log.i("用户名或密码错误>>>>>>>>>>", str);
								ScreenUtil.showMsg(LoginActivity.this,
										getString(R.string.input_error));// 提示用户名或密码错误
							}
						}else {
							Log.i("网络无连接>>>>>>>>>>", re);
							ScreenUtil.showMsg(LoginActivity.this,
									"网络无连接");// 提示错误
						}
					}
				}
			};
//			ScreenUtil.showProgressDialog(LoginActivity.this);// 显示进度条
//			new Thread(new Runnable() {//开启线程去访问用户信息以及菜单列表
//				public void run() {
//					try {
//						Thread.sleep(1000);
//						Message msg = new Message();
//						msg.what = 1;
//						Bundle bundle = new Bundle();
//						bundle.putString("name", name);
//						bundle.putString("pw", pw);
//						msg.setData(bundle);
//						ParseJasonDataService.handler.sendMessage(msg);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
			//------------------------
			Message msg = new Message();
			msg.what = 1;
			Bundle bundle = new Bundle();
			bundle.putString("name", name);
			bundle.putString("pw", pw);
			msg.setData(bundle);
			ParseJasonDataService.handler.sendMessage(msg);
		}
	}
}