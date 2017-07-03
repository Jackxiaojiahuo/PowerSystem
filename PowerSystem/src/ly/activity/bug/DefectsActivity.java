package ly.activity.bug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.activity.LctjActivity;
import ly.activity.R;
import ly.net.HttpConnection;
import ly.net.ParseJasonDataService;
import ly.net.URLProtocol;
import ly.util.LogUtil;
import ly.util.ScreenUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

/**
 * 缺陷登录
 * 
 * @author obeya
 * 
 */
public class DefectsActivity extends Activity {

	private final String TAG = "DefectsActivity";
	public static Handler mHandler;
	private List<List> trlist = null;// 长度
	private Button addDefects;// 添加按钮
	private int index=0;
	private String str="";
	private Boolean flag=false;
	private String str1="";
	private String str2="";
	private String str3="";
	private String str4="";
	private byte[] byteArray;
	private byte[] byteArray1;
	private Intent addIntent = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		index=intent.getIntExtra("index", 0);
		str=intent.getStringExtra("str");
		if ( index== 0) {
			setContentView(R.layout.bug_defects);
//			msg.what = 11;// 缺陷登录
			Log.i("11>>>>>>>>>>>>>>>", str);
			/**
			 * 缺陷登录 缺陷等级 线路名称 设备名称 /qxlc/qxdl/lb.jsp
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
					// 3:缺陷编号
					// 4:编号
					tdList.add(jo.getString("xlmc"));
					tdList.add(jo.getString("xlgh"));
					tdList.add(jo.getString("sbmc"));
					tdList.add(jo.getString("qxbh"));
					tdList.add(jo.getString("id"));
					trlist.add(tdList);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			initDefect();
		} else if (index == 2) {
			setContentView(R.layout.bug_approve);
//			msg.what = 13;// 主任审批
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
			initAppro();
		}
		//---------准备下一页面数据----------------
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				String re = bundle.getString("re");
				if (!"0".equals(re)) {
					String str = bundle.getString("str");
					if (!"".equals(str)) {
						if (msg.what == 112) {
							str1 = bundle.getString("str1");
							byteArray=bundle.getByteArray("img1");//图片信息
							addIntent.putExtra("str", str);//列表详细信息
							addIntent.putExtra("str1", str1);//线路
							addIntent.putExtra("img1", byteArray);//图片
						} else if (msg.what == 131) {// 主任审批
							str1 = bundle.getString("str1");
							str2 = bundle.getString("str2");
							addIntent.putExtra("str", str);
							addIntent.putExtra("str1", str1);
							addIntent.putExtra("str2", str2);
						}else if(msg.what==113){//添加按钮进去的
							str1 = bundle.getString("str1");
							addIntent.putExtra("str", str);
							addIntent.putExtra("str1", str1);
						}
//						Log.i(">>>str",str);
//						Log.i(">>>str1",str1);
						startActivity(addIntent);
					}
				} else {
					Log.i("网络无连接>>>>>>>>>>", str);
					ScreenUtil.showMsg(DefectsActivity.this, "网络无连接");// 提示错误
				}
			}
		};
	}

	// 初缺陷登录页面
	private void initDefect() {
		// ---------测试数据--------------------------
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
		addDefects = (Button) findViewById(R.id.addDefects);
		addRow(0);
		LogUtil.d(TAG, "%%%% DefectsActivity 初始化完成 %%%%");
		addDefects.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtil.d(TAG, "%%%% 进入 新增缺陷记录 页面 %%%%");
				// AddDefectsActivity.startAction(DefectsActivity.this);
				addIntent = new Intent(getApplicationContext(),
						AddDefectsActivity.class);
				addIntent.putExtra("index", 10);
				Message msg = new Message();
				msg.what=113;
				ParseJasonDataService.handler.sendMessage(msg);
			}
		});
	}

	// 初始化主任页面
	private void initAppro() {
//		DataManager.audit = new DataManager.Audit[2];
//		DataManager.audit[0] = new DataManager.Audit();
//		DataManager.audit[0].defectlevel = "II";
//		DataManager.audit[0].linename = "10kV乡矿121线";
//		DataManager.audit[0].devicename = "架空线路";
//		DataManager.audit[1] = new DataManager.Audit();
//		DataManager.audit[1].defectlevel = "I";
//		DataManager.audit[1].linename = "10kV蔺农二114线";
//		DataManager.audit[1].devicename = "金属氧化物避雷器";
//		Log.i("数组长度", "" + DataManager.audit.length);
		addRow(2);
	}

	private void addRow(final int index) {// 初始化缺陷登录
		// LayoutParams layoutParams = new LayoutParams();
		// layoutParams.topMargin = 10; // 边距大小
		// layoutParams.bottomMargin = 10; // 边距大小

		TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout);
		tableLayout.setStretchAllColumns(true);
		TableRow tablerow = null;
		for (int i = 0; i < trlist.size(); i++) {
			tablerow = new TableRow(DefectsActivity.this);
			tablerow.setMinimumHeight(100);
			tablerow.setGravity(Gravity.CENTER);
			// if (i % 2 == 0)
			tablerow.setBackgroundColor(Color.rgb(255, 255, 255));
			// else
			// tablerow.setBackgroundColor(Color.rgb(231, 229, 220));
			/*
			 * 设置要显示的组件
			 */
			List<String> list = trlist.get(i);
			if(index==0)
				tablerow.setId(Integer.parseInt(list.get(4)));
			else
				tablerow.setId(Integer.parseInt(list.get(3)));
			for (int j = 0; j < 3; j++) {
				TextView tv = new TextView(DefectsActivity.this);
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
				// lineName.setLayoutParams(layoutParams); // 设置序号的右外边距
				// Button detailBtn = new Button(getApplicationContext());
				// Button deleteBtn = new Button(getApplicationContext());
				// Button commitBtn = new Button(getApplicationContext());
				//
				// lineName.setTextColor(this.getResources().getColor(R.color.black));
				// // 设置字体颜色
				// lineName.setTextSize(17);
				// lineName.setLayoutParams(layoutParams);
				// rodNo.setTextColor(this.getResources().getColor(R.color.black));
				// // 设置字体颜色
				// rodNo.setTextSize(17);
				// rodNo.setLayoutParams(layoutParams);
				// deviceName.setTextColor(this.getResources().getColor(R.color.black));//
				// 设置字体颜色
				// deviceName.setTextSize(17);
				// deviceName.setLayoutParams(layoutParams);

				// 测试数据
				// lineName.setText(i + "10kV徐庄1" + (i + 1) + "4线");
				// detailBtn.setText("查看");
				// deleteBtn.setText("删除");
				// commitBtn.setText("提交");

				// tableRow.addView(lineName);
				LogUtil.d(TAG, "%%%% 2:" + tableLayout + " %%%%");
				tablerow.addView(tv);
				tablerow.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(getApplicationContext(),
								"id:" + view.getId() + ",点击",
								Toast.LENGTH_SHORT).show();
						Message msg = new Message();
						if(index==0){
							addIntent = new Intent(getApplicationContext(),
									AddDefectsActivity.class);
							addIntent.putExtra("id", view.getId() + "");
							msg.what=112;
						}else{
							addIntent = new Intent(getApplicationContext(),
									Audit_process.class);
							msg.what=131;
						}
						addIntent.putExtra("index", index);
						Bundle bundle = new Bundle();
						bundle.putString("id", view.getId() + "");
						msg.setData(bundle);
						ParseJasonDataService.handler.sendMessage(msg);
					}
				});
				tablerow.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View view) {
						final String[] arrayFruit = new String[] { "删除", "流程提交" };
						final String id=view.getId()+"";
						final View v=view;
						Dialog alertDialog = new AlertDialog.Builder(
								DefectsActivity.this).setTitle("请选择你的操作？")
								.setIcon(android.R.drawable.btn_dialog)
								.setItems(arrayFruit,
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												if(which==0){
													Map<String,String> map = new HashMap<String, String>();
													map.put("id", id);
													String re= HttpConnection.httpPost(URLProtocol.ROOT
																+ URLProtocol.qxlc_qxdl_lr_sc, map, 0);
													if (!"连接超时".equals(re)) {
														v.setVisibility(View.GONE);
													} else {
														Log.i("网络无连接>>>>>>>>>>", re);
														ScreenUtil.showMsg(DefectsActivity.this, "网络无连接");// 提示错误
													}
												}else{
													Map<String, String> map = new HashMap<String, String>();
													map.put("id", id);
													str = HttpConnection.httpPost(URLProtocol.ROOT+URLProtocol.qxlc_qxdl_lr,map,0);// 获取连接返回值;
													if(!"连接超时".equals(str)){
														String s="";
														String t = str;
														t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
														t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
														JSONObject json = null;
														try {
															json = new JSONObject(t);
															s = json.getString("qxbh");
														}catch(Exception e){
															e.printStackTrace();
														}
														map = new HashMap<String, String>();
														map.put("xqxmmc", s);
														s=HttpConnection.httpPost(URLProtocol.ROOT+URLProtocol.img,map,0);
														if(!"连接超时".equals(s)){
															t = s;
															t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
															t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
															try {
																json = new JSONObject(t);
																s = json.getString("qxclqtp");
															}catch(Exception e){
																e.printStackTrace();
															}
															if(!"".equals(s)){
																s=s.substring(s.indexOf("/upload"));
																Log.i("img-------------------------------------->",s);
																byte[] byteArray=HttpConnection.getImg(URLProtocol.ROOT+s);
																if(byteArray!=null){
																	Intent i=new Intent(DefectsActivity.this,LctjActivity.class);
																	i.putExtra("id", id);
																	startActivity(i);
																	DefectsActivity.this.finish();
																}else{
																	Toast.makeText(
																			DefectsActivity.this,
																			"请将信息填写完整",
																			Toast.LENGTH_SHORT)
																			.show();
																}
															}else{
																Toast.makeText(
																		DefectsActivity.this,
																		"请将信息填写完整",
																		Toast.LENGTH_SHORT)
																		.show();
															}
														}else{
															ScreenUtil.showMsg(DefectsActivity.this, "网络无连接");// 提示错误
														}
													}else {
														ScreenUtil.showMsg(DefectsActivity.this, "网络无连接");// 提示错误
													}
													
												}
											}
										}).setNegativeButton("取消",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {

											}
										}).create();
						alertDialog.show();
						return true;
					}
				});
			}
			tableLayout.addView(tablerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			TableRow linerow = new TableRow(DefectsActivity.this);
			linerow.setBackgroundColor(Color.GRAY);
			linerow.setMinimumHeight(1);
			tableLayout.addView(linerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}

	}
}
