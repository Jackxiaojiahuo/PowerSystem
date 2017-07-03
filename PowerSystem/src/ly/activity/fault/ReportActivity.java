package ly.activity.fault;

import java.util.ArrayList;
import java.util.List;

import ly.activity.R;
import ly.data.DataManager;
import ly.net.ParseJasonDataService;
import ly.util.LogUtil;
import ly.util.ScreenUtil;
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
 * 故障抢修填报
 * 
 * @author obeya
 * 
 */
public class ReportActivity extends Activity {

	private final String TAG = "ReportActivity";
	private Button addReport;
	public static Handler mHandler;
	private List<List> trlist=null;//长度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fault_report);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ScreenUtil.hideLoading();
				Bundle bundle = msg.getData();
				String str = bundle.getString("str");
				Log.i("31>>>>>>>>>>>>>>>", str);
//				str=str.substring(str.indexOf("</tr>")+5,str.lastIndexOf("</table>")).trim();
//				String s[]=str.split("</tr>");
//				Log.i("31第一次截取>>>>>>>>>>>>>>>tr数量为:"+s.length, str);
//				for(String s1:s){
//					String ss[]=s1.split("</td>");
//					for(String s2:ss){
//						Log.i("31第一次截取>>>>>>>>>>>>>>>", s2);
//					}
//				}
				
				/**
				 * 缺陷登录
				 *  缺陷等级 线路名称 设备名称  /qxlc/qxdl/lb.jsp
				 */
				String t=str;
				t=t.substring(t.indexOf("<table"), t.indexOf("</table"));
				t=t.substring(t.indexOf("</tr>")+5);
				List<String> list=new ArrayList<String>();
				int p=t.indexOf("<tr");
				while(p!=-1){
					 if(p>0){
						 list.add(t.substring(t.indexOf("<tr"), t.indexOf("</tr>")+5));
						 t=t.substring(t.indexOf("</tr>")+5);
						 p=t.indexOf("<tr");
					 }else{
						 return;
					 }
				}
				trlist=new ArrayList<List>();
				for (String s : list) {
					List<String> tdList=new ArrayList<String>();
					for (int i= 1; i < 11; i++) {
						if(i==1){
							String ss=s.substring(s.indexOf("<td")+19,s.indexOf("</td>"));
							tdList.add(ss);
						}else if(i==2){
							String ss=s.substring(s.indexOf(">")+1,s.indexOf("</td>"));
							tdList.add(ss);
						}else if(i==3){
							String ss=s.substring(s.indexOf(".jsp?id=")+8);
							ss=ss.substring(0,ss.indexOf("'"));
							tdList.add(ss);
						}else{
							String ss=s.substring(s.indexOf("<td")+4,s.indexOf("</td>"));
							tdList.add(ss);
						}
						s=s.substring(s.indexOf("</td>")+5);
					}
					for (String s1 : tdList) {
						Log.i("left-----------",s1);
					}
					trlist.add(tdList);
				}
				initDefect();
			}
		};
		Message msg = new Message();
		msg.what = 21;// 第二个tab页面
		ParseJasonDataService.handler.sendMessage(msg);
//		ScreenUtil.showProgressDialog(ReportActivity.this);// 显示进度条
//		 new Thread(new Runnable() {
//				public void run() {
//					try {
//						Thread.sleep(500);
//						
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
	}

	// 初缺陷登录页面
	private void initDefect() {
		addReport = (Button) findViewById(R.id.addReport);
		//--------------测试数据---------------------------
//		DataManager.audit = new DataManager.Audit[2];
//		DataManager.audit[0] = new DataManager.Audit();
//		DataManager.audit[0].defectlevel = "1";
//		DataManager.audit[0].linename = "10kV许太二112线";
//		DataManager.audit[0].devicename = "架空线路";
//		DataManager.audit[1] = new DataManager.Audit();
//		DataManager.audit[1].defectlevel = "2";
//		DataManager.audit[1].linename = "10kV蔺农二114线";
//		DataManager.audit[1].devicename = "金属氧化物避雷器";
//		Log.i("数组长度", "" + DataManager.audit.length);
		//--------------------------------------------------------
		addRow(0);
		LogUtil.d(TAG, "%%%% DefectsActivity 初始化完成 %%%%");
		addReport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtil.d(TAG, "%%%% 进入 新增缺陷记录 页面 %%%%");
				// AddDefectsActivity.startAction(DefectsActivity.this);
				Intent intent = new Intent(getApplicationContext(),
						AddReport.class);
				startActivityForResult(intent, 1);// 第一次跳页面
			}
		});
	}

	// 动态添加数据
	private void addRow(final int index) {

		TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout);
		tableLayout.setStretchAllColumns(true);
		TableRow tablerow = null;
		for (int i = 0; i < trlist.size(); i++) {
			tablerow = new TableRow(ReportActivity.this);
			tablerow.setMinimumHeight(100);
			tablerow.setGravity(Gravity.CENTER);
			// if (i % 2 == 0)
			tablerow.setBackgroundColor(Color.rgb(255, 255, 255));
			// else
			// tablerow.setBackgroundColor(Color.rgb(231, 229, 220));
			/*
			 * 设置要显示的组件
			 */
			List<String> list=trlist.get(i);
			tablerow.setId(Integer.parseInt(list.get(2)));
			for (int j = 0; j < 3; j++) {
				TextView tv = new TextView(ReportActivity.this);
				switch (j) {
				case 0:
					tv.setText(list.get(4));
					break;
				case 1:
					if(list.get(5).length()>4){
						tv.setText(list.get(5).substring(0,5));
					}else{
						tv.setText(list.get(5));
					}
					break;
				case 2:
					tv.setText(list.get(6));
					break;
				}
				tv.setTextSize(17);
				tv.setTextColor(Color.BLACK);
				LogUtil.d(TAG, "%%%% 2:" + tableLayout + " %%%%");
				tablerow.addView(tv);
				tablerow.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(getApplicationContext(),
								"id:" + view.getId() + ",点击",
								Toast.LENGTH_SHORT).show();
						Intent in = null;
						in = new Intent(getApplicationContext(),
								AddReport.class);
						in.putExtra("index", index);
						startActivity(in);
					}
				});
				tablerow.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View arg0) {
						final String[] arrayFruit = new String[] { "删除", "流程提交" };
						Dialog alertDialog = new AlertDialog.Builder(
								ReportActivity.this).setTitle("请选择你的操作？")
								.setIcon(android.R.drawable.btn_dialog)
								.setItems(arrayFruit,
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// addDefects.setText("新增"+arrayFruit[which]);
												Toast.makeText(
														ReportActivity.this,
														arrayFruit[which],
														Toast.LENGTH_SHORT)
														.show();
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
			TableRow linerow = new TableRow(getApplicationContext());
			linerow.setBackgroundColor(Color.GRAY);
			linerow.setMinimumHeight(1);
			tableLayout.addView(linerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}

	}
}
