package ly.activity.bug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.activity.R;
import ly.adapter.BugDefectContentAdapter;
import ly.entity.DefectContentModel;
import ly.net.HttpConnection;
import ly.net.ParseJasonDataService;
import ly.net.URLProtocol;
import ly.util.ScreenUtil;
import ly.util.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class DefectContent extends Activity {
	private ListView lv;
	private List<DefectContentModel> list;
	public static Map<String, String> map;
	private DefectContentModel dcm;
	private ScrollView sv;
	public static Handler mHandler;
	private Message msg = new Message();
	// ----------------------------
	private Spinner sp1;// 设备项目
	private Spinner sp2;// 缺陷内容
	private Spinner sp3;// 缺陷等级
	private Spinner sp4;// 总扣分
	private EditText et1;// 缺陷分析
	private Button btn1;// 新增缺陷
	private Button btn_save;// 缺陷内容保存
	private String sbxm = "";
	private String qxnr = "";
	private String qxdj = "";
	private String zkf = "";
	List<String> list1;
	List<String> list2;
	List<String> list3;
	List<String> list4;
	List<String> list5;
	int index;
	Intent intent;
	Intent addIntent;
	byte[] byteArray;
	String id;
	public static String sblx;
	public static String dwmc;
	public static String num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bug_defectcontent);
		intent = getIntent();
		index = intent.getIntExtra("index", 0);
		id = intent.getStringExtra("id");
		Log.i("列表id",id);
		lv = (ListView) findViewById(R.id.defectcontent_list);
		initWeight();
		map = new HashMap<String, String>();
		map.put("sblx", sblx);
		map.put("ssdw", dwmc);
		map.put("fjh", num);
		String s = HttpConnection.httpPost(URLProtocol.ROOT
				+ URLProtocol.qxlc_qxdl_lr_qx, map, 0);
		if (!"连接超时".equals(s)) {
			initsbxm(s);
		} else {
			Log.i("网络无连接>>>>>>>>>>", s);
			ScreenUtil.showMsg(DefectContent.this, "网络无连接");// 提示错误
		}

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				String re = bundle.getString("re");
				String str1 = "";
				addIntent = new Intent();
				Bundle reply = new Bundle();
				if (!"0".equals(re)) {
					String str = bundle.getString("str");
					if (!"".equals(str)) {
						if (msg.what == 1121) {
							str1 = bundle.getString("str1");
							byteArray = bundle.getByteArray("img1");// 图片信息
							addIntent.putExtra("str1", str1);// 线路
							addIntent.putExtra("img1", byteArray);// 图片
							addIntent.putExtra("index", 0);
							reply.putString("str", str);// 列表详细信息
						} 
						addIntent.putExtras(reply);
						// Log.i(">>>str",str);
						// Log.i(">>>str1",str1);
						setResult(99,addIntent);
//						startActivity(addIntent);
						DefectContent.this.finish();
					}
				}
			}
		};
	}

	private void initWeight() {
		// list=new ArrayList<DefectContentModel>();
		// for(int i=1;i<4;i++){
		// dcm=new DefectContentModel();
		// dcm.setOne("本体及引线"+i);
		// dcm.setTwo("接地引下线外观"+i);
		// dcm.setThree("V"+i);
		// dcm.setFour("15"+i);
		// dcm.setFive("无明显接地"+i);
		// list.add(dcm);
		// }
		// BugDefectContentAdapter adapter=new
		// BugDefectContentAdapter(getApplicationContext(), list);
		// lv.setAdapter(adapter);
		// -----------scrollview套listview设置--------------------
		new Utility().setListViewHeightBasedOnChildren(lv);
		sv = (ScrollView) findViewById(R.id.sv);
		sv.smoothScrollTo(0, 0);
		// ------------------------------------------
		sp1 = (Spinner) findViewById(R.id.sp1);
		sp2 = (Spinner) findViewById(R.id.sp2);
		sp3 = (Spinner) findViewById(R.id.sp3);
		sp4 = (Spinner) findViewById(R.id.sp4);
		et1 = (EditText) findViewById(R.id.et1);
		btn1 = (Button) findViewById(R.id.btn1);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if ("--请选择--".equals(sbxm)) {
					Toast.makeText(DefectContent.this, "请选择项目",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if ("--请选择--".equals(qxnr)) {
					Toast.makeText(DefectContent.this, "请选择缺陷内容",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if ("--请选择--".equals(qxdj)) {
					Toast.makeText(DefectContent.this, "请选择缺陷等级",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String s = HttpConnection.httpPost(URLProtocol.ROOT
						+ URLProtocol.qxlc_qxdl_lr_qx_testdb, DefectContent.map, 0);
				if (!"连接超时".equals(s)) {
					if (s.indexOf("缺陷新增成功") > 0) {
						map = new HashMap<String, String>();
						map.put("sblx", sblx);
						map.put("ssdw", dwmc);
						map.put("fjh", num);
						String s1 = HttpConnection.httpPost(URLProtocol.ROOT
								+ URLProtocol.qxlc_qxdl_lr_qx, map, 0);
						if (!"连接超时".equals(s1)) {
							String t = s1;
							Log.i("未截取字符串>>>>>>>>>>>>>>>", t);
							t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
							Log.i("字符串>>>>>>>>>>>>>>>", t);
							t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
							JSONObject json = null;
							try {
								json = new JSONObject(t);
								list = new ArrayList<DefectContentModel>();
								JSONArray l = json.getJSONArray("list");
								if(l.length()>0){
									Log.i(">>>>>>>>>>>>>123", l.getJSONObject(0).getString("XH"));
									for (int i = 0; i < l.length(); i++) {
										dcm = new DefectContentModel();
										dcm.setOne(l.getJSONObject(i).getString("dwmc"));
										dcm.setTwo(l.getJSONObject(i).getString("qxnr"));
										dcm.setThree(l.getJSONObject(i).getString("qxdj"));
										dcm.setFour(l.getJSONObject(i).getString("kf"));
										dcm.setFive(l.getJSONObject(i).getString("qxfx"));
										dcm.setSix(l.getJSONObject(i).getString("id"));
										dcm.setQi(l.getJSONObject(i).getString("XH"));
										list.add(dcm);
									}
									BugDefectContentAdapter adapter = new BugDefectContentAdapter(
											getApplicationContext(), list);
									lv.setAdapter(adapter);
									// -----------scrollview套listview设置--------------------
									new Utility().setListViewHeightBasedOnChildren(lv);
									sv = (ScrollView) findViewById(R.id.sv);
									sv.smoothScrollTo(0, 0);
									// ------------------------------------------
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						} else {
							Log.i("网络无连接>>>>>>>>>>", s1);
							ScreenUtil.showMsg(DefectContent.this, "网络无连接");// 提示错误
						}
					}
				} else {
					Log.i("网络无连接>>>>>>>>>>", s);
					ScreenUtil.showMsg(DefectContent.this, "网络无连接");// 提示错误
				}
			}
		});
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
					msg.what = 1121;
					Bundle bundle = new Bundle();
					bundle.putString("id", id);
					msg.setData(bundle);
					ParseJasonDataService.handler.sendMessage(msg);
			}
		});
	}

	public void initsbxm(String str) {
		String t = str;
		Log.i("缺陷内容未截取字符串>>>>>>>>>>>>>>>", t);
		t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
		Log.i("缺陷内容字符串>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			// 设备项目
			JSONArray ja = json.getJSONObject("sbxm").getJSONArray("sbxmList");
			list1 = new ArrayList<String>();
			int selected = 0;
			list1.add("--请选择--");
			for (int i = 0; i < ja.length(); i++) {
				if (ja.getString(i).equals(
						json.getJSONObject("sbxm").getString("sbxmxz"))) {
					selected = i;
				}
				list1.add(ja.getString(i));
			}
			ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.item_spinner, R.id.text,
					list1);
			sp1.setAdapter(spinner1Adapter);
			sp1.setSelection(selected, true);
			sbxm = list1.get(selected);

			//下方列表
			list = new ArrayList<DefectContentModel>();
			JSONArray l = json.getJSONArray("list");
			if(l.length()>0){
				Log.i("缺陷内容列表长度>>>>>>>>>>>>>", l.length()+"");
				for (int i = 0; i < l.length(); i++) {
					dcm = new DefectContentModel();
					dcm.setOne(l.getJSONObject(i).getString("dwmc"));
					dcm.setTwo(l.getJSONObject(i).getString("qxnr"));
					dcm.setThree(l.getJSONObject(i).getString("qxdj"));
					dcm.setFour(l.getJSONObject(i).getString("kf"));
					dcm.setFive(l.getJSONObject(i).getString("qxfx"));
					dcm.setSix(l.getJSONObject(i).getString("id"));
					dcm.setQi(l.getJSONObject(i).getString("XH"));
					list.add(dcm);
				}
				BugDefectContentAdapter adapter = new BugDefectContentAdapter(
						getApplicationContext(), list);
				lv.setAdapter(adapter);
				// -----------scrollview套listview设置--------------------
				new Utility().setListViewHeightBasedOnChildren(lv);
				sv = (ScrollView) findViewById(R.id.sv);
				sv.smoothScrollTo(0, 0);
				// ------------------------------------------
			}
			
			sp1.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View v,
						int pos, long arg3) {
					Log.w(">>>>>>>>>>>>>>>", "001");
					sbxm = list1.get(pos);
					Log.w(">>>>>>>>>>>>>>>", "002:" + sbxm);
					if (!sbxm.equals("--请选择--")) {
						msg.what = 1141;
						map.put("xm", sbxm);
						String s = HttpConnection.httpPost(URLProtocol.ROOT
								+ URLProtocol.qxlc_qxdl_lr_qx,
								DefectContent.map, 0);
						if (!"连接超时".equals(s)) {
							initqxnr(s);
						} else {
							Log.i("网络无连接>>>>>>>>>>", s);
							ScreenUtil.showMsg(DefectContent.this, "网络无连接");// 提示错误
						}
					} else {
						list2 = new ArrayList<String>();
						list2.add("--请选择--");
						ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.item_spinner,
								R.id.text, list2);
						sp1.setAdapter(spinner1Adapter);
						qxnr = "--请选择--";
						
						list3 = new ArrayList<String>();
						list3.add("--请选择--");
						spinner1Adapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.item_spinner,
								R.id.text, list3);
						sp3.setAdapter(spinner1Adapter);
						qxdj = "--请选择--";
						
						list4 = new ArrayList<String>();
						list4.add("");
						spinner1Adapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.item_spinner,
								R.id.text, list4);
						sp4.setAdapter(spinner1Adapter);
						zkf = "";
						et1.setText("");
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initqxnr(String str) {
		String t = str;
		Log.i("未截取字符串>>>>>>>>>>>>>>>", t);
		t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
		Log.i("字符串>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			// 设备项目
			JSONArray ja = json.getJSONObject("qxnr").getJSONArray("qxnrList");
			list2 = new ArrayList<String>();
			int selected = 0;
			list2.add("--请选择--");
			for (int i = 0; i < ja.length(); i++) {
				if (ja.getString(i).equals(
						json.getJSONObject("qxnr").getString("qxnrxz"))) {
					selected = i;
				}
				list2.add(ja.getString(i));
			}
			ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.item_spinner, R.id.text,
					list2);
			sp2.setAdapter(spinner1Adapter);
			sp2.setSelection(selected, true);
			qxnr = list2.get(selected);
			sp2.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View v,
						int pos, long arg3) {
					qxnr = list2.get(pos);
					if (!qxnr.equals("--请选择--")) {
						map.put("ztl", qxnr);
						String s = HttpConnection.httpPost(URLProtocol.ROOT
								+ URLProtocol.qxlc_qxdl_lr_qx,
								DefectContent.map, 0);
						if (!"连接超时".equals(s)) {
							initqxdj(s);
						} else {
							Log.i("网络无连接>>>>>>>>>>", s);
							ScreenUtil.showMsg(DefectContent.this, "网络无连接");// 提示错误
						}
					}else{
						list3 = new ArrayList<String>();
						list3.add("--请选择--");
						ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.item_spinner,
								R.id.text, list3);
						sp3.setAdapter(spinner1Adapter);
						qxdj = "--请选择--";
						
						list4 = new ArrayList<String>();
						list4.add("");
						spinner1Adapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.item_spinner,
								R.id.text, list4);
						sp4.setAdapter(spinner1Adapter);
						zkf = "";
						et1.setText("");
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initqxdj(String str) {
		String t = str;
		Log.i("未截取字符串>>>>>>>>>>>>>>>", t);
		t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
		Log.i("字符串>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			// 设备项目
			JSONArray ja = json.getJSONObject("qxdj").getJSONArray("qxdjList");
			list3 = new ArrayList<String>();
			int selected = 0;
			list3.add("--请选择--");
			for (int i = 0; i < ja.length(); i++) {
				if (ja.getString(i).equals(
						json.getJSONObject("qxdj").getString("qxdjxz"))) {
					selected = i;
				}
				list3.add(ja.getString(i));
			}
			ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.item_spinner, R.id.text,
					list3);
			sp3.setAdapter(spinner1Adapter);
			sp3.setSelection(selected, true);
			qxdj = list3.get(selected);
			sp3.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View v,
						int pos, long arg3) {
					qxdj = list3.get(pos);
					if (!qxnr.equals("--请选择--")) {
						map.put("lhcd", qxdj);
						String s = HttpConnection.httpPost(URLProtocol.ROOT
								+ URLProtocol.qxlc_qxdl_lr_qx,
								DefectContent.map, 0);
						if (!"连接超时".equals(s)) {
							initzkf(s);
						} else {
							Log.i("网络无连接>>>>>>>>>>", s);
							ScreenUtil.showMsg(DefectContent.this, "网络无连接");// 提示错误
						}
					}else{
						list4 = new ArrayList<String>();
						list4.add("");
						ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.item_spinner,
								R.id.text, list4);
						sp4.setAdapter(spinner1Adapter);
						zkf = "";
						et1.setText("");
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initzkf(String str) {
		String t = str;
		Log.i("未截取字符串>>>>>>>>>>>>>>>", t);
		t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
		Log.i("字符串总扣分>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			// 设备项目
			list4 = new ArrayList<String>();
			list4.add(json.getString("zkf"));
			ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.item_spinner, R.id.text,
					list4);
			sp4.setAdapter(spinner1Adapter);
			sp4.setSelection(0, true);
			zkf = list4.get(0);
			String qxfx = json.getString("qxfx");
			et1.setText(qxfx);
			map.put("kf", zkf);
			map.put("pdyj", qxfx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delList(String id){
		map = new HashMap<String, String>();
		map.put("sblx", sblx);
		map.put("ssdw", dwmc);
		map.put("fjh", num);
		map.put("id", id);
		String s = HttpConnection.httpPost(URLProtocol.ROOT
				+ URLProtocol.qxlc_qxdl_sc_qx, map, 0);
		if (!"连接超时".equals(s)) {
			Log.i("删除",s);
			if (s.indexOf("缺陷删除成功") > 0) {
				map = new HashMap<String, String>();
				map.put("sblx", sblx);
				map.put("ssdw", dwmc);
				map.put("fjh", num);
				String s1 = HttpConnection.httpPost(URLProtocol.ROOT
						+ URLProtocol.qxlc_qxdl_lr_qx, map, 0);
				if (!"连接超时".equals(s1)) {
					String t = s1;
					Log.i("未截取字符串>>>>>>>>>>>>>>>", t);
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					Log.i("字符串>>>>>>>>>>>>>>>", t);
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					JSONObject json = null;
					try {
						json = new JSONObject(t);
						list = new ArrayList<DefectContentModel>();
						JSONArray l = json.getJSONArray("list");
						Log.i("list内容",l.getJSONObject(l.length()-1)+"");
//						if(l.length()>0){
//							Log.i(">>>>>>>>>>>>>123", l.getJSONObject(0).getString("XH"));
//							for (int i = 0; i < l.length(); i++) {
//								dcm = new DefectContentModel();
//								dcm.setOne(l.getJSONObject(i).getString("dwmc"));
//								dcm.setTwo(l.getJSONObject(i).getString("qxnr"));
//								dcm.setThree(l.getJSONObject(i).getString("qxdj"));
//								dcm.setFour(l.getJSONObject(i).getString("kf"));
//								dcm.setFive(l.getJSONObject(i).getString("qxfx"));
//								dcm.setSix(l.getJSONObject(i).getString("id"));
//								dcm.setQi(l.getJSONObject(i).getString("XH"));
//								list.add(dcm);
//							}
//							BugDefectContentAdapter adapter = new BugDefectContentAdapter(
//									DefectContent.this, list);
//							lv.setAdapter(adapter);
//							// -----------scrollview套listview设置--------------------
//							new Utility().setListViewHeightBasedOnChildren(lv);
//							sv = (ScrollView) findViewById(R.id.sv);
//							sv.smoothScrollTo(0, 0);
//							// ------------------------------------------
//						}
					}catch(Exception e){
						e.printStackTrace();
					}
				} else {
					Log.i("网络无连接>>>>>>>>>>", s1);
					ScreenUtil.showMsg(DefectContent.this, "网络无连接");// 提示错误
				}
			}
		} else {
			Log.i("网络无连接>>>>>>>>>>", s);
			ScreenUtil.showMsg(DefectContent.this, "网络无连接");// 提示错误
		}
	}
}
