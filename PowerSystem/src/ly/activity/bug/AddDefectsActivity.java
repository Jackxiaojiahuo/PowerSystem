package ly.activity.bug;

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

import ly.activity.BigPhotoActivity;
import ly.activity.R;
import ly.net.HttpConnection;
import ly.net.ParseJasonDataService;
import ly.net.URLProtocol;
import ly.util.ScreenUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 新增缺陷缺陷
 * 
 * @author obeya
 * 
 */
public class AddDefectsActivity extends Activity implements OnClickListener {
	private final String TAG = "AddDefectsActivity";
	public static Map<String, String> map ;
	boolean flag = true;
	// ----------不显示,但后台需要的数据---------------------------
	private String id="0";//信息编号
	private String num;// 缺陷编号
	private String devicename;// 设备名称
	private String dwmc;// 单位名称
	// ------------接收页面数据变量----------------------
	private String linename;// 线路名称*
	private String linenum;// 线路杆号*
	private String fzkg = "是否带分支开关";// 分支开关*
	private String sblx = "--请选择--";// 设备类型*
	private String sbbh;// 设备编号
	private String td = "--请选择--";// 停电*
	private String bzmc;// 班组名称
	private String qxly = "--请选择--";// 缺陷来源*
	private String qxyy = "--请选择--";// 缺陷原因*
	private String qxnr;// 缺陷内容*
	private String qxnrsm;// 缺陷内容说明*
	private String cbqxdj;// 初步缺陷等级
	private String yqwcrq;// 要求完成日期
	private String qxddms;// 缺陷地点描述*
	private String yycbfx;// 原因初步分析*
	private String cbclfa;// 初步处理方案*
	private String qxfxsj;// 缺陷发现时间*
	private String fxr;// 缺陷发现人
	private String gpsdw;// gps定位

	// -------------------页面控件-----------------------------------------
	private EditText et1;// 缺陷编号,已禁用
	private EditText lineName; // 线路名称

	// ----------杆号改造-------------------------
	private EditText et3;// 杆号输入框1
	private EditText et4;// 杆号输入框2
	private EditText et5;// 杆号输入框3
	private EditText et6;// 杆号输入框4
	// ----------------------------------------
	private EditText etgh;// 线路杆号文本框
	private EditText et7;// 设备编号
	private EditText et8;// 单位名称,已禁用
	private EditText et9;// 班组名称
	private EditText et10;// 缺陷内容
	private EditText et11;// 缺陷内容说明
	private EditText et12;// 初步缺陷等级
	private EditText et13;// 要求完成日期
	private EditText et14;// 缺陷地点描述
	private EditText et15;// 原因初步分析
	private EditText et16;// 初步处理方案
	private EditText et17;// 缺陷发现时间
	private EditText et18;// 缺陷发现人
	private EditText et19;// GPS定位
	// private Button chooseLineBtn;// 选择线路按钮,已禁用
	// private Button defectContent;// 缺陷内容,已禁用
	private Button btn_save;// 保存按钮
	private Button btn_del;// 删除图片按钮
	private Button btn_camera;// 拍照按钮
	private Spinner spinnerDeviceType; // 设备类型下拉列表
	private Spinner spinnerDirection1; // 方位1
	private Spinner spinnerDirection2; // 方位2
	private Spinner spinnerDirection3; // 方位3
	private Spinner spinner4;// 分支开关
	private Spinner spinner6;// 是否停电
	private Spinner spinner7;// 缺陷来源
	private Spinner spinner8;// 缺陷原因
	private ImageView img_before;// 照片
	private LinearLayout ly_spinner;// 显示隐藏分支开关
	private LinearLayout show;// 删除照片
	// -------------------------------
	private Calendar calendar; // 通过Calendar获取系统时间
	private int year = 2017;
	private int month = 6;
	private int day = 10;
	// ----------拍照-------------------------------
	public static final String SDCARD_ROOT_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();// 路径
	public static String SAVE_PATH_IN_SDCARD = "/bidata/"; // 图片及其他数据保存文件夹
	public static String IMAGE_CAPTURE_NAME = "cameraTmp.png"; // 照片名称
	private Bitmap bitmap;// 拍照后图片
	// 定位都要通过LocationManager这个类实现
	private LocationManager locationManager;
	private String provider;

	// 消息传递机制
	public static Handler mHandler;
	private int index;// 页面定位标识
	private Intent addintent = null;
	private String str = "";
	private String str1 = "";
	private String str2 = "";
	private String str3 = "";
	private String str4 = "";
	private Intent in;
	private byte[] img1;
	private Message msg = new Message();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bug_defects_add);
		addintent = getIntent();
		index = addintent.getIntExtra("index", 0);
		Log.i("121>>>>>>>>>>>>>>>", index + "");
		initWeight();
		initContent();
		// initDeviceTypes(); // 初始化设备类型
		if (index == 0) {
			id=addintent.getStringExtra("id");
			str = addintent.getStringExtra("str");
			str1 = addintent.getStringExtra("str1");
			img1 = addintent.getByteArrayExtra("img1");
			if (img1 != null) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(img1, 0,
						img1.length);
				img_before.setImageBitmap(bitmap);
				img_before.setVisibility(View.VISIBLE);
				ParseJasonDataService.qxdl_img1=bitmap;
				show.setVisibility(View.VISIBLE);
			}

			Log.i("121>>>>>>>>>>>>>>>", str);
			/*
			 * 查看缺陷信息 qxlc/zzsh/lr_qxdl.jsp?id=1303
			 */
			String t = str;
			t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
			Log.i("字符串>>>>>>>>>>>>>>>", t);
			t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
			JSONObject json = null;
			try {
				json = new JSONObject(t);
				num = json.getString("qxbh");
				lineName.setText(json.getString("xlmc"));
				linenum = json.getString("fsbmc");
				etgh.setText(linenum);
				// 分支开关
				String fz = json.getString("fzkg");
				if (fz.equals("none")) {
					fz = "不带分支开关";
					spinner4.setSelection(2, true);
				} else {
					fz = "带分支开关";
					spinner4.setSelection(1, true);
				}
				fzkg = fz;
				// 设备类型
				JSONArray ja = json.getJSONObject("sblx").getJSONArray(
						"sblxList");
				final List<String> list = new ArrayList<String>();
				int selected = 0;
				for (int i = 0; i < ja.length(); i++) {
					if (ja.getString(i).equals(
							json.getJSONObject("sblx").getString("sblxxz"))) {
						selected = i;
					}
					list.add(ja.getString(i));
				}
				ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_spinner,
						R.id.text, list);
				spinnerDeviceType.setAdapter(spinner1Adapter);
				spinnerDeviceType.setSelection(selected, true);
				sblx = list.get(selected);
				spinnerDeviceType
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View v, int pos, long arg3) {
								sblx = list.get(pos);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {

							}
						});
				// 设备编号
				et7.setText(json.getString("sbbh"));
				// 是否停电
				JSONArray jt = json.getJSONObject("sh_sftd").getJSONArray(
				"sftdList");
				final List<String> lt = new ArrayList<String>();
				lt.add("--请选择--");
				selected = 0;
				for (int i = 0; i < jt.length(); i++) {
					if (jt.getString(i).equals(
							json.getJSONObject("sh_sftd").getString("sftdxz"))) {
						selected = i + 1;
					}
					lt.add(jt.getString(i));
				}
				spinner1Adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_spinner,
						R.id.text, lt);
				spinner6.setAdapter(spinner1Adapter);
//				if (json.getJSONObject("sh_sftd").getString("sftdxz").equals(
//						"是")) {
//					selected = 1;
//					td = "是";
//				} else if (json.getJSONObject("sh_sftd").getString("sftdxz")
//						.equals("否")) {
//					selected = 2;
//					td = "否";
//				}
				spinner6.setSelection(selected, true);
				td=lt.get(selected);
				spinner6
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0,
							View v, int pos, long arg3) {
						td = lt.get(pos);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
				// 单位名称
				dwmc = json.getString("dwmc");
				// 班组名称
				bzmc = json.getString("bzmc");
				et9.setText(bzmc);
				// 缺陷来源
				JSONArray jb = json.getJSONObject("qxly").getJSONArray(
						"qxlyList");
				final List<String> list1 = new ArrayList<String>();
				list1.add("--请选择--");
				selected = 0;
				for (int i = 0; i < jb.length(); i++) {
					if (jb.getString(i).equals(
							json.getJSONObject("qxly").getString("qxlyxz"))) {
						selected = i + 1;
					}
					list1.add(jb.getString(i));
				}
				// Toast.makeText(getApplicationContext(),
				// jb.getString(selected)+":缺陷来源", Toast.LENGTH_SHORT).show();
				spinner1Adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_spinner,
						R.id.text, list1);
				spinner7.setAdapter(spinner1Adapter);
				spinner7.setSelection(selected, true);
				qxly = list1.get(selected);
				spinner7
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View v, int pos, long arg3) {
								qxly = list1.get(pos);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});
				// 缺陷原因
				JSONArray jc = json.getJSONObject("qxyy").getJSONArray(
						"qxyyList");
				final List<String> list2 = new ArrayList<String>();
				selected = 0;
				list2.add("--请选择--");
				for (int i = 0; i < jc.length(); i++) {
					if (jc.getString(i).equals(
							json.getJSONObject("qxyy").getString("qxyyxz"))) {
						selected = i + 1;
					}
					list2.add(jc.getString(i));
				}
				// Toast.makeText(getApplicationContext(),
				// jc.getString(selected)+":缺陷原因", Toast.LENGTH_SHORT).show();
				spinner1Adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_spinner,
						R.id.text, list2);
				spinner8.setAdapter(spinner1Adapter);
				spinner8.setSelection(selected, true);
				qxyy = list2.get(selected);
				spinner8
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View v, int pos, long arg3) {
								qxyy = list2.get(pos);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});
				// 缺陷内容
				qxnr = json.getString("qxnr");
				if (null != qxnr || !"".equals(qxnr)) {
					StringBuffer qxnrStr = new StringBuffer(qxnr);
					qxnrStr = qxnrStr.insert(qxnr.indexOf("。") + 1, "\n");
					qxnr = qxnrStr.toString();
				}
				et10.setText(qxnr);
				// 缺陷内容说明
				qxnrsm = json.getString("qxnrsm");
				et11.setText(qxnrsm);
				// 初步缺陷等级
				cbqxdj = json.getString("qxdj");
				et12.setText(cbqxdj);
				// 要求完成日期
				yqwcrq = json.getString("wcrq");
				et13.setText(yqwcrq);
				// 缺陷地点描述
				qxddms = json.getString("qxddms");
				et14.setText(qxddms);
				// 原因初步分析
				yycbfx = json.getString("yycbfx");
				et15.setText(yycbfx);
				// 初步处理方案
				cbclfa = json.getString("cbclfa");
				et16.setText(cbclfa);
				// 缺陷发现时间
				qxfxsj = json.getString("fxrq");
				et17.setText(qxfxsj);
				// 缺陷发现人
				fxr = json.getString("fxr");
				et18.setText(fxr);
				// 缺陷处理前照片
				// img_before
				// 缺陷处理前GPS
				String g=json.getString("gps1");
				if(!"".equals(g)&&g!=null){
					StringBuffer gStr = new StringBuffer(g);
					gStr = gStr.insert(g.indexOf("纬度") , "\n");
					g = gStr.toString();
					et19.setText(g);
					gpsdw=g;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(index == 10) {
			str = addintent.getStringExtra("str");
			str1 = addintent.getStringExtra("str1");
			Log.i("121>>>>>>>>>>>>>>>", str);
			/*
			 * 查看缺陷信息 qxlc/zzsh/lr_qxdl.jsp?id=1303
			 */
			String t = str;
			t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
			Log.i("字符串>>>>>>>>>>>>>>>", t);
			t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
			JSONObject json = null;
			try {
				json = new JSONObject(t);
				// 设备类型
				JSONArray ja = json.getJSONObject("sblx").getJSONArray(
						"sblxList");
				final List<String> list = new ArrayList<String>();
				int selected = 0;
				for (int i = 0; i < ja.length(); i++) {
					if (ja.getString(i).equals(
							json.getJSONObject("sblx").getString("sblxxz"))) {
						selected = i;
					}
					list.add(ja.getString(i));
				}
				ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_spinner,
						R.id.text, list);
				spinnerDeviceType.setAdapter(spinner1Adapter);
				spinnerDeviceType.setSelection(selected, true);
				sblx = list.get(selected);
				spinnerDeviceType
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View v, int pos, long arg3) {
								sblx = list.get(pos);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});
				// 单位名称
				dwmc = json.getString("dwmc");
				// 缺陷来源
				JSONArray jb = json.getJSONObject("qxly").getJSONArray(
						"qxlyList");
				final List<String> list1 = new ArrayList<String>();
				list1.add("--请选择--");
				selected = 0;
				for (int i = 0; i < jb.length(); i++) {
					if (jb.getString(i).equals(
							json.getJSONObject("qxly").getString("qxlyxz"))) {
						selected = i + 1;
					}
					list1.add(jb.getString(i));
				}
				// Toast.makeText(getApplicationContext(),
				// jb.getString(selected)+":缺陷来源", Toast.LENGTH_SHORT).show();
				spinner1Adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_spinner,
						R.id.text, list1);
				spinner7.setAdapter(spinner1Adapter);
				spinner7.setSelection(selected, true);
				qxly = list1.get(selected);
				spinner7
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View v, int pos, long arg3) {
								qxly = list1.get(pos);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});
				// 缺陷原因
				JSONArray jc = json.getJSONObject("qxyy").getJSONArray(
						"qxyyList");
				final List<String> list2 = new ArrayList<String>();
				selected = 0;
				list2.add("--请选择--");
				for (int i = 0; i < jc.length(); i++) {
					if (jc.getString(i).equals(
							json.getJSONObject("qxyy").getString("qxyyxz"))) {
						selected = i + 1;
					}
					list2.add(jc.getString(i));
				}
				// Toast.makeText(getApplicationContext(),
				// jc.getString(selected)+":缺陷原因", Toast.LENGTH_SHORT).show();
				spinner1Adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_spinner,
						R.id.text, list2);
				spinner8.setAdapter(spinner1Adapter);
				spinner8.setSelection(selected, true);
				qxyy = list2.get(selected);
				spinner8
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View v, int pos, long arg3) {
								qxyy = list2.get(pos);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(index == 11){
			
		}

		/*
		 * 如果本页面用到Spinner 需使用以下方式设置View
		 */
		// View contentView =
		// LayoutInflater.from(this.getParent()).inflate(R.layout.menu_add_defects,
		// null);
		//---------准备下一页面数据----------------
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				String str = bundle.getString("str");
				if (!"连接超时".equals(str)) {
					if (!"".equals(str)) {
						if(msg.what==118){
							String t = str;
							t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
							Log.i("字符串ID>>>>>>>>>>>>>>>", t);
							///qxlc/qxdl/lr_qx.jsp?sblx=柱上真空开关&ssdw=运维检修部&fjh=20170628019
							Intent intent=new Intent(getApplicationContext(),DefectContent.class);
							intent.putExtra("index", index);
							num=t;
							String y=str;
							y = y.substring(y.indexOf("<as>") + 4, y.lastIndexOf("</as>"));
							intent.putExtra("id", y);
							DefectContent.dwmc=dwmc;
							DefectContent.sblx=sblx;
							DefectContent.num=num;
							startActivity(intent);
						}
					}
				}
			}
		};
		//----------------------------------------
	}

	// 初始化控件
	private void initWeight() {
		// et1 = (EditText) findViewById(R.id.et1);
		lineName = (EditText) findViewById(R.id.et2);
		et3 = (EditText) findViewById(R.id.et3);
		et4 = (EditText) findViewById(R.id.et4);
		et5 = (EditText) findViewById(R.id.et5);
		et6 = (EditText) findViewById(R.id.et6);
		et7 = (EditText) findViewById(R.id.et7);
		etgh = (EditText) findViewById(R.id.etgh);
		et9 = (EditText) findViewById(R.id.et9);
		et10 = (EditText) findViewById(R.id.et10);
		et11 = (EditText) findViewById(R.id.et11);
		et12 = (EditText) findViewById(R.id.et12);
		et13 = (EditText) findViewById(R.id.et13);
		et14 = (EditText) findViewById(R.id.et14);
		et15 = (EditText) findViewById(R.id.et15);
		et16 = (EditText) findViewById(R.id.et16);
		et17 = (EditText) findViewById(R.id.et17);
		et18 = (EditText) findViewById(R.id.et18);
		et19 = (EditText) findViewById(R.id.et19);
		// chooseLineBtn = (Button) findViewById(R.id.btn1);
		// defectContent = (Button) findViewById(R.id.btn2);
		btn_del = (Button) findViewById(R.id.btn_del);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_camera = (Button) findViewById(R.id.btn_camera);
		spinnerDirection1 = (Spinner) findViewById(R.id.spinner1);
		spinnerDirection2 = (Spinner) findViewById(R.id.spinner2);
		spinnerDirection3 = (Spinner) findViewById(R.id.spinner3);
		spinner4 = (Spinner) findViewById(R.id.spinner4);
		spinnerDeviceType = (Spinner) findViewById(R.id.spinner5);
		spinner6 = (Spinner) findViewById(R.id.spinner6);
		spinner7 = (Spinner) findViewById(R.id.spinner7);
		spinner8 = (Spinner) findViewById(R.id.spinner8);
		img_before = (ImageView) findViewById(R.id.img_before);
		show = (LinearLayout) findViewById(R.id.show);
		ly_spinner = (LinearLayout) findViewById(R.id.ly_spinner);
		btn_camera.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(i, 1);
				// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// // 存储卡可用 将照片存储在 sdcard
				// if (isHasSdcard()) {
				// intent
				// .putExtra(MediaStore.EXTRA_OUTPUT, Uri
				// .fromFile(new File(SDCARD_ROOT_PATH
				// + SAVE_PATH_IN_SDCARD,
				// IMAGE_CAPTURE_NAME)));
				// }
				// startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
				// Audit_process.requestPermissions($.getActivity(),
				// new
				// String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
				// 1);
				// Intent intent = new Intent(getBaseContext(),
				// ActivityCapture.class);
				// startActivityForResult(intent, CameraRequestCode);
			}
		});
		btn_del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				img_before.setVisibility(View.GONE);
				show.setVisibility(View.GONE);
				File f = new File(SAVE_PATH_IN_SDCARD);
				if (f.exists())
					f.delete();
				et19.setText("");
			}
		});
		img_before.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						BigPhotoActivity.class);
				i.putExtra("pos", 1);
				startActivity(i);
			}
		});
		lineName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra("line", str1);
				intent.setClass(getApplicationContext(), LineActivity.class);
				startActivityForResult(intent, 1); // 激活目标Activity,
				// 通过父Activity启动其他Activity
				// AddDefectsActivity.this.getParent().startActivityForResult(intent,
				// 1); // 激活目标Activity,
				// startActivity(intent); // 并回传值
			}
		});
		/**
		 * 添加保存按钮事件
		 */
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// .getText().toString().trim();
				linename = lineName.getText().toString().trim();
				linenum = etgh.getText().toString().trim();
				sbbh = et7.getText().toString().trim();
				bzmc = et9.getText().toString().trim();
				qxnr = et10.getText().toString().trim();
				qxnrsm = et11.getText().toString().trim();
				cbqxdj = et12.getText().toString().trim();
				yqwcrq = et13.getText().toString().trim();
				qxddms = et14.getText().toString().trim();
				yycbfx = et15.getText().toString().trim();
				cbclfa = et16.getText().toString().trim();
				qxfxsj = et17.getText().toString().trim();
				gpsdw = et19.getText().toString().trim();
				if (linename.equals("")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择线路名称");
					return;
				}
				if (linenum.equals("")) {
					etgh.setFocusable(true);
					ScreenUtil.showMsg(AddDefectsActivity.this, "请填写线路杆号");
					return;
				}
				if (fzkg.equals("是否带分支开关")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择分支开关");
					return;
				}
				if (sblx.equals("--请选择--")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择设备类型");
					return;
				}
				if (td.equals("--请选择--")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择是否停电");
					return;
				}
				if (qxly.equals("--请选择--")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择缺陷来源");
					return;
				}
				if (qxyy.equals("--请选择--")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择缺陷原因");
					return;
				}
				if (qxnr.equals("")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请填写缺陷内容");
					return;
				}
				if (qxnrsm.equals("")) {
					et11.setFocusable(true);
					ScreenUtil.showMsg(AddDefectsActivity.this, "请填写缺陷内容说明");
					return;
				}
				if (qxddms.equals("")) {
					et14.setFocusable(true);
					ScreenUtil.showMsg(AddDefectsActivity.this, "请填写缺陷地点描述");
					return;
				}
				if (yycbfx.equals("")) {
					et15.setFocusable(true);
					ScreenUtil.showMsg(AddDefectsActivity.this, "请填写原因初步分析");
					return;
				}
				if (cbclfa.equals("")) {
					et16.setFocusable(true);
					ScreenUtil.showMsg(AddDefectsActivity.this, "请填写初步处理方案");
					return;
				}
				if (gpsdw.equals("")) {
					ScreenUtil
							.showMsg(AddDefectsActivity.this, "GPS定位失败,请重新拍照");
					return;
				}
				saveData();
				str=HttpConnection.httpPost(URLProtocol.ROOT
						+ URLProtocol.qxlc_qxdl_lr_db, map, 0);
				if (!"连接超时".equals(str)) {
					Log.i("保存",str);
					if (str.indexOf("保存成功") > 0) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("img", SAVE_PATH_IN_SDCARD);
						map.put("type", "1");
						map.put("lcmc", num);
						str = HttpConnection.httpPost(URLProtocol.ROOT
								+ URLProtocol.upload, map, 1);// 获取连接返回值;
						Log.i("上传成功",str);
						if(str.indexOf("上传成功")>0){
							 File f=new File(SAVE_PATH_IN_SDCARD);//上传成功后删除本地文件
							 if(f.exists()) f.delete();
							 Intent in=new Intent(AddDefectsActivity.this,DefectsActivity.class);
							 in.putExtra("index", 0);
							 String info = HttpConnection.httpPost(URLProtocol.ROOT
										+ URLProtocol.qxlc_qxdl_lb,null,0);// 获取连接返回值;
							 if(!"连接超时".equals(info)){
								 in.putExtra("str", info);
								 startActivity(in);
								 AddDefectsActivity.this.finish();
							 } else {
									Log.i("网络无连接>>>>>>>>>>", str);
									ScreenUtil.showMsg(AddDefectsActivity.this, "网络无连接");// 提示错误
							}
						}
					}
				} else {
					Log.i("网络无连接>>>>>>>>>>", str);
					ScreenUtil.showMsg(AddDefectsActivity.this, "网络无连接");// 提示错误
				}
				// 图片上传
				// {
				// Message msg = new Message();
				// msg.what=111;
				// Bundle data =new Bundle();
				// data.putString("img", SAVE_PATH_IN_SDCARD);
				// msg.setData(data);
				// ParseJasonDataService.handler.sendMessage(msg);
				// }
				// File f=new File(SAVE_PATH_IN_SDCARD);//上传成功后删除本地文件
				// if(f.exists())
				// f.delete();

			}
		});
	}

	/**
	 * 初始化设备类型
	 */
	// private void initDeviceTypes() {
	// // 测试数据 -> 需要重数据库查询
	// final List<String> deviceTypes = new ArrayList<String>();
	// deviceTypes.add("--请选择--");
	// deviceTypes.add("架空线路");
	// deviceTypes.add("柱上真空开关");
	// deviceTypes.add("柱上SF6开关");
	// deviceTypes.add("柱上隔离开关");
	// deviceTypes.add("跌落式熔断器");
	// deviceTypes.add("金属氧化物避雷器");
	// deviceTypes.add("电容器");
	// deviceTypes.add("高压计量箱");
	// deviceTypes.add("配电变压器");
	// deviceTypes.add("开关柜");
	// deviceTypes.add("电缆线路");
	// deviceTypes.add("电缆分支箱");
	// deviceTypes.add("构筑物及外壳");
	// LogUtil.d(TAG, "%%%% deviceTypes个数:" + deviceTypes.size() + " %%%%");
	//
	// /*
	// * 下拉框选项实体，在Spinner中显示选项的时候， 默认会显示每个项（Object）的 toString() 之后的内容， 因此重写
	// * toString() 方法，让它输出 文本 。
	// */
	//
	// /*
	// * 设置Spinner的适配器
	// */
	// ArrayAdapter<String> spinnerDeviceTypeDdapter = new ArrayAdapter<String>(
	// getApplicationContext(), R.layout.item_spinner, R.id.text,
	// deviceTypes);
	// spinnerDeviceType.setAdapter(spinnerDeviceTypeDdapter);
	// // spinnerDeviceType.setPrompt("选择设备类型");
	// /*
	// * Spinner监听
	// */
	//
	// spinnerDeviceType
	// .setOnItemSelectedListener(new OnItemSelectedListener() {
	// @Override
	// public void onItemSelected(AdapterView<?> parent,
	// View view, int position, long id) {
	// sblx=deviceTypes.get(position);
	// Toast.makeText(getApplicationContext(),
	// "选中设备:" + deviceTypes.get(position),
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> arg0) {
	//
	// }
	// });
	// }

	private void initContent() {
		// -------------------------
		// ----------方位------------------
		// 是否显示分支开关
		et4.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (!et4.getText().toString().trim().equals("")) {
					Toast.makeText(
							getApplicationContext(),
							(!et4.getText().toString().trim().equals("")) + ":"
									+ et4.getText().toString().trim(),
							Toast.LENGTH_SHORT).show();
					ly_spinner.setVisibility(View.VISIBLE);
				} else {
					ly_spinner.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
//		final String[] directions = getResources().getStringArray(
//				R.array.direction);
//		ArrayAdapter spinnerDirection1Adapter = ArrayAdapter
//				.createFromResource(getApplicationContext(), R.array.direction,
//						android.R.layout.simple_spinner_item);
//		spinnerDirection1Adapter
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerDirection1.setAdapter(spinnerDirection1Adapter);
//		ArrayAdapter spinnerDirection2Adapter = ArrayAdapter
//				.createFromResource(getApplicationContext(), R.array.direction,
//						android.R.layout.simple_spinner_item);
//		spinnerDirection2Adapter
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerDirection2.setAdapter(spinnerDirection2Adapter);
//		ArrayAdapter spinnerDirection3Adapter = ArrayAdapter
//				.createFromResource(getApplicationContext(), R.array.direction,
//						android.R.layout.simple_spinner_item);
//		spinnerDirection3Adapter
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerDirection3.setAdapter(spinnerDirection3Adapter);

		// ------------其他spinner--------------------
		final List<String> list4 = new ArrayList<String>();
		list4.add("是否带分支开关");
		list4.add("带分支开关");
		list4.add("不带分支开关");
		ArrayAdapter<String> spinner4Adapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.item_spinner, R.id.text,
				list4);
		spinner4.setAdapter(spinner4Adapter);
		spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				fzkg=list4.get(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});

		List<String> list6 = new ArrayList<String>();
		list6.add("--请选择--");
		list6.add("是");
		list6.add("否");
		ArrayAdapter<String> spinner6Adapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.item_spinner, R.id.text,
				list6);
		spinner6.setAdapter(spinner6Adapter);
		spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				td=list4.get(pos);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		List<String> list7 = new ArrayList<String>();
		list7.add("--请选择--");
		list7.add("抢修");
		list7.add("工程");
		list7.add("其他");
		ArrayAdapter<String> spinner7Adapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.item_spinner, R.id.text,
				list7);
		spinner7.setAdapter(spinner7Adapter);

		List<String> list8 = new ArrayList<String>();
		list8.add("--请选择--");
		list8.add("设备质量");
		list8.add("外力破坏");
		list8.add("施工工艺");
		list8.add("运行不当");
		list8.add("设备老化");
		ArrayAdapter<String> spinner8Adapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.item_spinner, R.id.text,
				list8);
		spinner8.setAdapter(spinner8Adapter);
		// -------------------------
		// --------------获取当前系统日期-------------------------------------
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		et17.setText(year + "-" + (month + 1) + "-" + day);
		et13.setOnClickListener(AddDefectsActivity.this);
		et17.setOnClickListener(AddDefectsActivity.this);
		// --------按钮---------------
		et10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				in = new Intent(getApplicationContext(), DefectContent.class);
				linename = lineName.getText().toString().trim();
				linenum = etgh.getText().toString().trim();
				sbbh = et7.getText().toString().trim();
				if (linename.equals("")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择线路名称");
					return;
				}
				if (linenum.equals("")) {
					etgh.setFocusable(true);
					ScreenUtil.showMsg(AddDefectsActivity.this, "请填写线路杆号");
					return;
				}
				if (fzkg.equals("是否带分支开关")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择分支开关");
					return;
				}
				if (sblx.equals("--请选择--")) {
					ScreenUtil.showMsg(AddDefectsActivity.this, "请选择设备类型");
					return;
				}
				Message msg = new Message();
				msg.what = 118;
				saveData();
				ParseJasonDataService.handler.sendMessage(msg);
			}
		});

	}
	public void saveData(){
		map= new HashMap<String, String>();
		map.put("id", id);
		map.put("dwmc", dwmc);
		map.put("bzmc", bzmc);
		map.put("qxhb", num);
		map.put("sbmc", sblx);
		map.put("sbbh", sbbh);
		map.put("xlmc", linename);
		map.put("fsbmc", linenum);
		map.put("qxly", qxly);
		map.put("qxyy", qxyy);
		map.put("qxnr", qxnr);
		map.put("qxnrsm", qxnrsm);
		map.put("qxdj", cbqxdj);
		map.put("wcrq", yqwcrq);
		map.put("qxddms", sblx);
		map.put("yycbfx", yycbfx);
		map.put("cbclfa", cbclfa);
		map.put("fxrq", qxfxsj);
		map.put("fxr", fxr);
		map.put("fzkg", fzkg);
		map.put("sh_sftd", td);
		map.put("gps1", gpsdw);
	}
	// 回显数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {// 判断请求的编码是否为1
			if (resultCode == RESULT_CANCELED) {// 判断结果编码是否为取消
				if (data != null) {// 如果选择了线路
					Bundle reply = data.getExtras();// 取出回传的Bundle
					String linename = reply.getString("lineName");// 取出Bundle中线路名称
					int line_id = reply.getInt("lineId");// 取出线路id
					lineName.setText(linename);
				}
			} else if (resultCode == Activity.RESULT_OK) {
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
				bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b = null;
				// ???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？
				File file = new File("/sdcard/myImage/");
				file.mkdirs();// 创建文件夹
				SAVE_PATH_IN_SDCARD = "/sdcard/myImage/" + IMAGE_CAPTURE_NAME;
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
				ParseJasonDataService.qxdl_img1=bitmap;
				img_before.setImageBitmap(bitmap);// 将图片显示在ImageView里
				img_before.setVisibility(View.VISIBLE);
				show.setVisibility(View.VISIBLE);
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
					Toast.makeText(this, "请检查网络或GPS是否打开", Toast.LENGTH_LONG)
							.show();
					return;
				}
				Location location = locationManager
						.getLastKnownLocation(provider);
				if (location != null) {
					// 获取当前位置，这里只用到了经纬度
					String string = "经度：" + CutChar(location.getLongitude())
							+ "\n纬度：" + CutChar(location.getLatitude());
					et19.setText(string);
				}
				// 绑定定位事件，监听位置是否改变
				// 第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
				// 第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器
				locationManager.requestLocationUpdates(provider, 2000, 2,
						locationListener);

			}
		}
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

	// 关闭时解除监听器
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
		}
	}
	/**
	 * 时间点击事件
	 */
	@Override
	public void onClick(View v) {
		final EditText et = (EditText) v;
		new DatePickerDialog(AddDefectsActivity.this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						AddDefectsActivity.this.year = year;
						month = monthOfYear;
						day = dayOfMonth;
						et.setText(year + "-" + (month + 1) + "-" + day);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();
	}

	/**
	 * 截取经纬度
	 * 
	 * @param loc
	 * @return
	 */
	private String CutChar(double loc) {
		String str = loc + "";
		str = str.substring(str.indexOf("."), 5);
		return str;
	}
	/**
	 * 监听返回按键
	 */
	// @Override
	// public boolean dispatchKeyEvent(KeyEvent event) {
	// if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	// if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount()
	// == 0) {
	// TabFirstActivityGroup.tabFirstGroup.back(); // 返回上一Activity
	// }
	// return true;
	// }
	// return super.dispatchKeyEvent(event);
	// }

	// @Override
	// public void onBackPressed() {
	// super.onBackPressed(); /* 如果你不打算做其他事情了，就调这个，会执行系统的默认动作 */
	// return; /* 不写return，就会自动执行系统默认的back动作 */
	// }

}
