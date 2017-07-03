package ly.activity.bug;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ly.activity.LoginActivity;
import ly.activity.R;
import ly.data.DataManager;
import ly.net.ParseJasonDataService;
import ly.util.ScreenUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AuditActivity extends Activity {

	private final String TAG = "AuditActivity";
	public static Handler mHandler;
	private Message msg;
	private List<List> trlist = null;// 长度
	private int index=0;
	private String str="";
	private String str1="";
	private String str2="";
	private String str3="";
	private String str4="";
	private Intent in=null;
	/**
	 * 从调用本方法处 跳转到本界面
	 * 
	 * @param context
	 */
	// public static void startAction(Context context) {
	// Intent intent = new Intent();
	// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// intent.setClass(context, AuditActivity.class); // 设置要跳转到的activity
	/*
	 * 进行页面跳转时使用
	 */
	// View decorView = TabFirstActivityGroup.tabFirstGroup
	// .getLocalActivityManager()
	// .startActivity("AuditActivity", intent).getDecorView();
	// TabFirstActivityGroup.tabFirstGroup.replaceContentView(decorView);
	// }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		index=intent.getIntExtra("index", 1);
		str=intent.getStringExtra("str");
		if (index == 1) {
			setContentView(R.layout.bug_audit);
//			msg.what = 12;// 缺陷登录专职页面
			Log.i("31>>>>>>>>>>>>>>>", str);
			/**
			 * 专职审核/qxlc/zzsh/lb.jsp
			 */
			String t = str;
			t = t.substring(t.indexOf("<sa>") + 4, t
					.lastIndexOf("</sa>"));
			Log.i("字符串>>>>>>>>>>>>>>>", t);
			t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
			JSONObject json = null;
			try {
				json = new JSONObject(t);
				JSONArray jrray = json.getJSONArray("list");
				int len = jrray.length();
				JSONObject jo = null;
				Log.i("字符串1>>>>>>>>>>>>>>>", jrray + "");
				trlist = new ArrayList<List>();
				for (int i = 0; i < len; i++) {
					jo = jrray.getJSONObject(i);
					List<String> tdList = new ArrayList<String>();
					// index
					// 0:线路名称
					// 1:杆号
					// 2:设备名称
					// 3:编号
					tdList.add(jo.getString("xlmc"));
					tdList.add(jo.getString("xlgh"));
					tdList.add(jo.getString("sbmc"));
					tdList.add(jo.getString("id"));
					trlist.add(tdList);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			initAudit();
		} else if (index == 3) {
			setContentView(R.layout.bug_handle);
//			msg.what = 14;// 缺陷处理页面
			Log.i("缺陷处理>>>>>>>>>>>>>>>", str);
			/**
			 * 缺陷处理/qxlc/zzsh/lb.jsp
			 */
			String t = str;
			t = t.substring(t.indexOf("<sa>") + 4, t
					.lastIndexOf("</sa>"));
			Log.i("字符串>>>>>>>>>>>>>>>", t);
			t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
			JSONObject json = null;
			try {
				json = new JSONObject(t);
				JSONArray jrray = json.getJSONArray("list");
				int len = jrray.length();
				JSONObject jo = null;
				Log.i("字符串1>>>>>>>>>>>>>>>", jrray + "");
				trlist = new ArrayList<List>();
				for (int i = 0; i < len; i++) {
					jo = jrray.getJSONObject(i);
					List<String> tdList = new ArrayList<String>();
					// index
					// 0:线路名称
					// 1:杆号
					// 2:设备名称
					// 3:编号
					tdList.add(jo.getString("xlmc"));
					tdList.add(jo.getString("xlgh"));
					tdList.add(jo.getString("sbmc"));
					tdList.add(jo.getString("id"));
					trlist.add(tdList);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			initHandle();
		} else if (index == 4) {
			setContentView(R.layout.bug_acceptance);
//			msg.what = 15;// 缺陷验收专职页面
			Log.i("缺陷验收>>>>>>>>>>>>>>>", str);
			/**
			 * 缺陷验收/qxlc/zzsh/lb.jsp
			 */
			String t = str;
			t = t.substring(t.indexOf("<sa>") + 4, t
					.lastIndexOf("</sa>"));
			Log.i("字符串>>>>>>>>>>>>>>>", t);
			t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
			JSONObject json = null;
			try {
				json = new JSONObject(t);
				JSONArray jrray = json.getJSONArray("list");
				int len = jrray.length();
				JSONObject jo = null;
				Log.i("字符串1>>>>>>>>>>>>>>>", jrray + "");
				trlist = new ArrayList<List>();
				for (int i = 0; i < len; i++) {
					jo = jrray.getJSONObject(i);
					List<String> tdList = new ArrayList<String>();
					// index
					// 0:线路名称
					// 1:杆号
					// 2:设备名称
					// 3:编号
					tdList.add(jo.getString("xlmc"));
					tdList.add(jo.getString("xlgh"));
					tdList.add(jo.getString("sbmc"));
					tdList.add(jo.getString("id"));
					trlist.add(tdList);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			initAcceptance();
		}
	}

	// 初始化专职界面
	private void initAudit() {
		// ------------测试数据------------------------------
		// DataManager.audit=new DataManager.Audit[2];
		// DataManager.audit[0]=new DataManager.Audit();
		// DataManager.audit[0].defectlevel="1";
		// DataManager.audit[0].linename="10kV乡矿121线";
		// DataManager.audit[0].devicename="架空线路";
		// DataManager.audit[1]=new DataManager.Audit();
		// DataManager.audit[1].defectlevel="2";
		// DataManager.audit[1].linename="10kV蔺农二114线";
		// DataManager.audit[1].devicename="金属氧化物避雷器";
		// Log.i("数组长度",""+DataManager.audit.length);
		// ------------------------------------------------------
		addTablerow(1);
	}

	// 初始化缺陷处理界面
	private void initHandle() {
//		DataManager.audit = new DataManager.Audit[2];
//		DataManager.audit[0] = new DataManager.Audit();
//		DataManager.audit[0].defectlevel = "1";
//		DataManager.audit[0].linename = "10kV乡矿121线";
//		DataManager.audit[0].devicename = "架空线路";
//		DataManager.audit[1] = new DataManager.Audit();
//		DataManager.audit[1].defectlevel = "2";
//		DataManager.audit[1].linename = "10kV蔺农二114线";
//		DataManager.audit[1].devicename = "金属氧化物避雷器";
//		Log.i("数组长度", "" + DataManager.audit.length);
		addTablerow(3);
	}

	// 初始化缺陷验收界面
	private void initAcceptance() {
//		DataManager.audit = new DataManager.Audit[2];
//		DataManager.audit[0] = new DataManager.Audit();
//		DataManager.audit[0].defectlevel = "1";
//		DataManager.audit[0].linename = "10kV乡矿121线";
//		DataManager.audit[0].devicename = "架空线路";
//		DataManager.audit[1] = new DataManager.Audit();
//		DataManager.audit[1].defectlevel = "2";
//		DataManager.audit[1].linename = "10kV蔺农二114线";
//		DataManager.audit[1].devicename = "金属氧化物避雷器";
//		Log.i("数组长度", "" + DataManager.audit.length);
		addTablerow(4);
	}

	// @Override
	// public boolean dispatchKeyEvent(KeyEvent event) {
	// if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
	// if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount()
	// == 0) {
	// TabFirstActivityGroup.tabFirstGroup.back(); // 返回上一Activity
	// LogUtil.d(TAG, "%%%% 返回上一Activity  dispatchKeyEvent() %%%%");
	// }
	// return true;
	// }
	// return super.dispatchKeyEvent(event);
	// }
	public void addTablerow(final int index) {
		TableLayout table = (TableLayout) findViewById(R.id.tablelayout);
		table.setStretchAllColumns(true);
		TableRow tablerow = null;
		for (int i = 0; i < trlist.size(); i++) {
			tablerow = new TableRow(AuditActivity.this);
			tablerow.setId(i);
			tablerow.setMinimumHeight(100);
			tablerow.setGravity(Gravity.CENTER);
			// if(i%2==0)
			tablerow.setBackgroundColor(Color.rgb(255, 255, 255));
			// else tablerow.setBackgroundColor(Color.rgb(231, 229, 220));
			List<String> list = trlist.get(i);
			tablerow.setId(Integer.parseInt(list.get(3)));
			for (int j = 0; j < 3; j++) {
				TextView tv = new TextView(AuditActivity.this);
				switch (j) {
				case 0:
					tv.setText(list.get(0));
					break;
				case 1:
					if (list.get(1).length() > 4) {
						tv.setText(list.get(1).substring(0, 5));
					} else {
						tv.setText(list.get(1));
					}
					break;
				case 2:
					tv.setText(list.get(2));
					break;
				}
				tv.setTextSize(17);
				tv.setTextColor(Color.BLACK);
				tablerow.addView(tv);
				tablerow.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(getApplicationContext(),
								"id:" + view.getId() + ",点击",
								Toast.LENGTH_SHORT).show();
						in = new Intent(getApplicationContext(),
								Audit_process.class);
						msg = new Message();
						if(index==1)
							msg.what = 121;// 缺陷登录专职页面
						else if(index==3)
							msg.what = 141;// 缺陷处理页面
						else if(index==4)
							msg.what = 151;// 缺陷验收专职页面
						in.putExtra("index", index);
						in.putExtra("id", view.getId() + "");
						Bundle bundle = new Bundle();
						bundle.putString("id", view.getId() + "");
						msg.setData(bundle);
						ParseJasonDataService.handler.sendMessage(msg);
						mHandler = new Handler() {
							@Override
							public void handleMessage(Message msg) {
								Bundle bundle = msg.getData();
								String re = bundle.getString("re");
								if (!"0".equals(re)) {
									str = bundle.getString("str");
									if (!"".equals(str)) {
										if (msg.what == 121) {//专职审核
											str1 = bundle.getString("str1");
											in.putExtra("str", str);
											in.putExtra("str1", str1);
										} else if (msg.what == 141) {//缺陷处理
											str1 = bundle.getString("str1");
											str2 = bundle.getString("str2");
											str3 = bundle.getString("str3");
											in.putExtra("str", str);
											in.putExtra("str1", str1);
											in.putExtra("str2", str2);
											in.putExtra("str3", str3);
										} else if (msg.what == 151) {//缺陷验收
											str1 = bundle.getString("str1");
											str2 = bundle.getString("str2");
											str3 = bundle.getString("str3");
											str4 = bundle.getString("str4");
											in.putExtra("str", str);
											in.putExtra("str1", str1);
											in.putExtra("str2", str2);
											in.putExtra("str3", str3);
											in.putExtra("str4", str4);
										}
										startActivity(in);
									}
								} else {
									Log.i("网络无连接>>>>>>>>>>", str);
									ScreenUtil.showMsg(AuditActivity.this, "网络无连接");// 提示错误
								}
							}
						};
					}
				});
			}
			table.addView(tablerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			TableRow linerow = new TableRow(getApplicationContext());
			linerow.setBackgroundColor(Color.GRAY);
			linerow.setMinimumHeight(1);
			table.addView(linerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			// View view=new View(AuditActivity.this);
			// view.setBackgroundColor(Color.BLACK);
			// table.addView(view,new TableLayout.LayoutParams(
			// LayoutParams.FILL_PARENT, 1));
		}
	}

}
