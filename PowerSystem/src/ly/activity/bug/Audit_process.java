package ly.activity.bug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import ly.activity.BigPhotoActivity;
import ly.activity.R;
import ly.net.ParseJasonDataService;
import ly.util.ScreenUtil;
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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Audit_process extends Activity implements OnClickListener {
	// ---------菜单项-------------------------------------------
	private Button left;// 流转过程按钮
	private Button right;// 右边按钮
	private ImageButton btn;// 关闭按钮
	// --------左边需要隐藏的表格--------------------------------------------
	private LinearLayout tables_audit;
	private LinearLayout tables_approval;
	private LinearLayout tables_handle;
	// private TableLayout tables_acceptance;
	// --------右边需要隐藏的表格---------------------------
	private LinearLayout table_audit;
	private LinearLayout table_approval;
	private LinearLayout table_handle;
	private LinearLayout table_acceptance;
	// ----------------------------------------------------
	// 左边缺陷登录界面
	private EditText defect_et1;// 线路名称
	private EditText defect_et2;// 线路杆号
	private EditText defect_fz;// 分支开关
	private EditText defect_et3;// 设备类型
	private EditText defect_et4;// 设备编号
	private EditText defect_et5;// 是否停电
	private EditText defect_et6;// 单位名称,已隐藏
	private EditText defect_et7;// 班组名称
	private EditText defect_et8;// 缺陷来源
	private EditText defect_et9;// 缺陷原因
	private EditText defect_et10;// 缺陷内容
	private EditText defect_et11;// 缺陷内容说明
	private EditText defect_et12;// 初步缺陷等级
	private EditText defect_et13;// 要求完成日期
	private EditText defect_et14;// 缺陷地点描述
	private EditText defect_et15;// 原因初步分析
	private EditText defect_et16;// 初步处理方案
	private EditText defect_et17;// 缺陷发现时间
	private EditText defect_et18;// 缺陷发现人
	private EditText defect_et19;// GPS定位
	private ImageView img_before;// 缺陷处理前照片
	// 左边专职审核界面
	private EditText audit_e1;// 缺陷等级
	private EditText audit_e2;// 是否停电
	private EditText audit_et1;// 审核意见
	private EditText audit_et2;// 审核日期
	private EditText audit_et3;// 审核人
	private EditText audit_et4;// 备注
	// 左边主任审批界面
	private EditText approval_et1;// 批准日期
	private EditText approval_et2;// 批准人
	private EditText approval_et3;// 批准意见
	// 左边缺陷处理界面
	private EditText handle_e1;// 现场处理情况
	private EditText handle_et1;// 发生材料明细
	private EditText handle_et2;// 是否遗留问题
	private EditText handle_et3;// 处理日期
	private EditText handle_et4;// 遗留问题描述
	private EditText handle_et5;// 消缺单位
	private EditText handle_et6;// 消缺负责人
	private EditText handle_et7;// 概算费用
	private EditText handle_et8;// 备注
	private Button handle_btn1;// 查看参考价格
	private ImageView img_after;// 处理后照片
	// 左边缺陷验收界面
	// private Spinner acceptance_spinner1;
	// private EditText acceptance_et1;
	// private EditText acceptance_et2;
	// private EditText acceptance_et3;
	// private EditText acceptance_et4;
	// private EditText acceptance_et5;
	// ------------------------------------------------------
	// 右边专职审核界面
	private List<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	private Spinner r_audit_spinner1;// 缺陷等级
	private Spinner r_audit_spinner2;// 是否停电
	private Spinner r_audit_spinner3;// 是否同意流程
	private EditText r_audit_et1;// 要求完成日期
	private EditText r_audit_et2;// 审核意见
	private EditText r_audit_et3;// 审核日期
	private EditText r_audit_et4;// 审核人
	private EditText r_audit_et5;// 备注
	private Button r_audit_save;// 保存按钮
	// ------------------------------------------
	private String r_audit_qxdj;// 缺陷等级*
	private String r_audit_td;// 是否停电*
	private String r_audit_yqwcrq;// 要求完成日期
	private String r_audit_shyj;// 审核意见*
	private String r_audit_shrq;// 审核日期*
	private String r_audit_shr;// 审核人*
	private String r_audit_sfty = "--	请选择--";// 是否同意流程处理*
	private String r_audit_bz;// 备注
	// 右边主任审批界面
	private Spinner r_approval_spinner1;// 是否同意流程
	private EditText r_approval_et1;// 批准日期
	private EditText r_approval_et2;// 批准人
	private EditText r_approval_et3;// 批准意见
	private Button r_approval_save;// 保存按钮
	// ------------------------------------------
	private String r_approval_pzrq = "";// (批准日期)*
	private String r_approval_pzr = "";// （批准人）*
	private String r_approval_pzyj = "";// （批准意见）*
	private String r_approval_tylc = "";// 是否同意流程处理*
	// 右边缺陷处理界面
	private EditText r_handle_et1;// 现场处理情况
	private EditText r_handle_et2;// 发生材料明细
	private EditText r_handle_et3;// 处理日期
	private EditText r_handle_et4;// 遗留问题描述
	private EditText r_handle_et5;// 消缺单位
	private EditText r_handle_et6;// 消缺负责人
	private EditText r_handle_et7;// 概算费用
	private EditText r_handle_et8;// 备注
	private EditText r_handle_et9;// GPS定位
	private ImageView r_handle_img_after;// 缺陷处理后照片
	private Spinner r_handle_spinner1;// 是否遗留问题
	private Spinner r_handle_spinner2;// 是否同意流程处理
	// private Button r_handle_btn1;//录入发生材料,已禁用
	// private Button r_handle_btn2;//查看参考价格,已禁用
	private Button r_handle_save;// 保存按钮
	private Button btn_camera;// 拍照按钮
	private Button btn_del;// 删除照片按钮
	private LinearLayout r_handle_show;// 删除照片按钮父布局
	// ------------------------------------------
	private String r_handle_xcclqk = "";// 现场处理情况*
	private String r_handle_fsclmx = "";// 发生材料明细*
	private String r_handle_sfylwt = "";// 是否遗留问题*
	private String r_handle_clrq = "";// 处理日期*
	private String r_handle_ylwtms = "";// 遗留问题描述*
	private String r_handle_xqdw = "";// 消缺单位*
	private String r_handle_xqfzr = "";// 消缺负责人*
	private String r_handle_gsfy = "";// 概算费用*
	private String r_handle_bz = "";// 备注
	private String r_handle_gpsdw = "";// GPS定位*
	// 右边缺陷验收界面
	private EditText r_acceptance_et1;// 验收情况
	private EditText r_acceptance_et2;// 验收遗留问题描述
	private EditText r_acceptance_et3;// 验收日期
	private EditText r_acceptance_et4;// 验收人
	private EditText r_acceptance_et5;// 备注
	private Spinner r_acceptance_spinner1;// 是否通过验收
	private Button r_acceptance_save;// 保存按钮
	// ------------------------------------------
	private String r_acceptance_ysqk = "";// 验收情况*
	private String r_acceptance_ysylwtms = "";// 验收遗留问题描述*
	private String r_acceptance_sftgys = "";// 是否通过验收*
	private String r_acceptance_ysrq = "";// 验收日期*
	private String r_acceptance_ysr = "";// 验收人*
	private String r_acceptance_bz = "";// 备注
	// ----------------------------------------------------

	private Calendar calendar; // 通过Calendar获取系统时间
	private int year = 2017;
	private int month = 6;
	private int day = 10;
	private int index = 0;// 页面定位
	// ----------拍照-------------------------------
	public static final String SDCARD_ROOT_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();// 路径
	public static String SAVE_PATH_IN_SDCARD = "/bidata/"; // 图片及其他数据保存文件夹
	public static String IMAGE_CAPTURE_NAME = "cameraTmp.png"; // 照片名称
	public final static int REQUEST_CODE_TAKE_PICTURE = 12;// 设置拍照操作的标志
	private Bitmap bitmap;// 拍照后图片
	// 定位都要通过LocationManager这个类实现
	private LocationManager locationManager;
	private String provider;
	// 消息传递机制
	public static Handler mHandler;
	private Intent intent;
	private String str="";
	private String str1="";
	private String str2="";
	private String str3="";
	private String str4="";
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();
		index = intent.getIntExtra("index", 0);
		id=intent.getStringExtra("id");
		setContentView(R.layout.bug_audit_processes);
		left(index);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ScreenUtil.hideLoading();
				Bundle bundle = msg.getData();
				String str = bundle.getString("str");
				if (!"连接超时".equals(str) && str == null) {
					if (!"".equals(str)) {
						if (msg.what == 121) {
							
						}else if(msg.what==122){
							
						}else if(msg.what==131){//查看专职审核
							Log.i("131>>>>>>>>>>>>>>>", str);
							String t = str;
							t = t.substring(t.indexOf("<sa>") + 4, t
									.lastIndexOf("</sa>"));
							Log.i("字符串>>>>>>>>>>>>>>>", t);
							t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
							JSONObject json = null;
							try {
								json = new JSONObject(t);
							}catch(Exception e){
								e.printStackTrace();
							}
						}else if(msg.what==132){//右边主任批准
							Log.i("132>>>>>>>>>>>>>>>", str);
							String t = str;
							t = t.substring(t.indexOf("<sa>") + 4, t
									.lastIndexOf("</sa>"));
							Log.i("字符串>>>>>>>>>>>>>>>", t);
							t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
							JSONObject json = null;
							try {
								json = new JSONObject(t);
							}catch(Exception e){
								e.printStackTrace();
							}
						}else if(msg.what==141){//查看主任批准
							Log.i("141>>>>>>>>>>>>>>>", str);
							String t = str;
							t = t.substring(t.indexOf("<sa>") + 4, t
									.lastIndexOf("</sa>"));
							Log.i("字符串>>>>>>>>>>>>>>>", t);
							t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
							JSONObject json = null;
							try {
								json = new JSONObject(t);
							}catch(Exception e){
								e.printStackTrace();
							}
						}else if(msg.what==142){//右边缺陷处理
							Log.i("142>>>>>>>>>>>>>>>", str);
							String t = str;
							t = t.substring(t.indexOf("<sa>") + 4, t
									.lastIndexOf("</sa>"));
							Log.i("字符串>>>>>>>>>>>>>>>", t);
							t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
							JSONObject json = null;
							try {
								json = new JSONObject(t);
							}catch(Exception e){
								e.printStackTrace();
							}
						}else if(msg.what==151){//左边缺陷处理
							Log.i("151>>>>>>>>>>>>>>>", str);
							String t = str;
							t = t.substring(t.indexOf("<sa>") + 4, t
									.lastIndexOf("</sa>"));
							Log.i("字符串>>>>>>>>>>>>>>>", t);
							t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
							JSONObject json = null;
							try {
								json = new JSONObject(t);
							}catch(Exception e){
								e.printStackTrace();
							}
						}else if(msg.what==152){//右边缺陷完成
							Log.i("152>>>>>>>>>>>>>>>", str);
							String t = str;
							t = t.substring(t.indexOf("<sa>") + 4, t
									.lastIndexOf("</sa>"));
							Log.i("字符串>>>>>>>>>>>>>>>", t);
							t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
							JSONObject json = null;
							try {
								json = new JSONObject(t);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				} else {
					Log.i("网络无连接>>>>>>>>>>", str);
					ScreenUtil.showMsg(Audit_process.this, "网络无连接");// 提示错误
				}
			}
		};
	}

	// 左边显示菜单
	private void left(final int index) {
		setContentView(R.layout.bug_audit_processes);
		right = (Button) findViewById(R.id.audit);
		btn = (ImageButton) findViewById(R.id.button1);
		initLeft(index);
		right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.bug_audit_process);
				right(index);
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Audit_process.this.finish();
			}
		});
	}

	// 右边显示菜单
	private void right(final int index) {
		setContentView(R.layout.bug_audit_process);
		// tr1 = (TableRow) findViewById(R.id.tr1);
		// tr1.setVisibility(View.GONE);
		left = (Button) findViewById(R.id.other);
		btn = (ImageButton) findViewById(R.id.button1);
		initRight(index);
		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.bug_audit_processes);
				left(index);
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Audit_process.this.finish();
			}
		});
	}

	// 初始化左边菜单
	private void initLeft(int index) {
		tables_audit = (LinearLayout) findViewById(R.id.tables_audit);
		tables_approval = (LinearLayout) findViewById(R.id.tables_approval);
		tables_handle = (LinearLayout) findViewById(R.id.tables_handle);
		switch (index) {
		case 1:
			tables_audit.setVisibility(View.GONE);
			tables_approval.setVisibility(View.GONE);
			tables_handle.setVisibility(View.GONE);
			initLeftDefect();
			break;
		case 2:
			tables_approval.setVisibility(View.GONE);
			tables_handle.setVisibility(View.GONE);
			right.setText("主任审批");
			initLeftAudit();
			break;
		case 3:
			tables_handle.setVisibility(View.GONE);
			right.setText("缺陷处理");
			initLeftApproval();
			break;
		case 4:
			right.setText("缺陷验收");
			initLeftHandle();
			break;
		}
		
		// audit_td=(Spinner) findViewById(R.id.audit_td);
		// 设置spinner不可用
		// audit_td.setEnabled(false);
	}

	// 初始化右边菜单
	private void initRight(int index) {
		right = (Button) findViewById(R.id.audit);
		table_audit = (LinearLayout) findViewById(R.id.table_audit);
		table_approval = (LinearLayout) findViewById(R.id.table_approval);
		table_handle = (LinearLayout) findViewById(R.id.table_handle);
		table_acceptance = (LinearLayout) findViewById(R.id.table_acceptance);
		switch (index) {
		case 1:
			table_approval.setVisibility(View.GONE);
			table_handle.setVisibility(View.GONE);
			table_acceptance.setVisibility(View.GONE);
			initRightAudit();
			break;
		case 2:
			table_audit.setVisibility(View.GONE);
			table_handle.setVisibility(View.GONE);
			table_acceptance.setVisibility(View.GONE);
			right.setText("主任审批");
			initRightApproval();
			break;
		case 3:
			table_audit.setVisibility(View.GONE);
			table_approval.setVisibility(View.GONE);
			table_acceptance.setVisibility(View.GONE);
			right.setText("缺陷处理");
			initRightHandle();
			break;
		case 4:
			table_audit.setVisibility(View.GONE);
			table_approval.setVisibility(View.GONE);
			table_handle.setVisibility(View.GONE);
			right.setText("缺陷验收");
			initRightAcceptance();
			break;
		}
	}

	// 初始化左边缺陷登录
	private void initLeftDefect() {
		defect_et1 = (EditText) findViewById(R.id.defect_e1);
		defect_et2 = (EditText) findViewById(R.id.defect_e2);
		defect_et3 = (EditText) findViewById(R.id.defect_e3);
		defect_et4 = (EditText) findViewById(R.id.defect_e4);
		defect_et5 = (EditText) findViewById(R.id.defect_e5);
		defect_et6 = (EditText) findViewById(R.id.defect_e6);
		defect_et7 = (EditText) findViewById(R.id.defect_e7);
		defect_et8 = (EditText) findViewById(R.id.defect_e8);
		defect_et9 = (EditText) findViewById(R.id.defect_e9);
		defect_et10 = (EditText) findViewById(R.id.defect_e10);
		defect_et11 = (EditText) findViewById(R.id.defect_e11);
		defect_et12 = (EditText) findViewById(R.id.defect_e12);
		defect_et13 = (EditText) findViewById(R.id.defect_e13);
		defect_et14 = (EditText) findViewById(R.id.defect_e14);
		defect_et15 = (EditText) findViewById(R.id.defect_e15);
		defect_et16 = (EditText) findViewById(R.id.defect_e16);
		defect_et17 = (EditText) findViewById(R.id.defect_e17);
		defect_et18 = (EditText) findViewById(R.id.defect_e18);
		defect_et19 = (EditText) findViewById(R.id.defect_e19);
		defect_fz = (EditText) findViewById(R.id.defect_fz);
		img_before = (ImageView) findViewById(R.id.img_before);
		//--------------为控件赋值--------------------------
		str=intent.getStringExtra("str");
		Log.i("121>>>>>>>>>>>>>>>", str);
		/*
		 * 查看缺陷信息 qxlc/zzsh/lr_qxdl.jsp?id=1303
		 */
		String t = str;
		t = t.substring(t.indexOf("<sa>") + 4, t
				.lastIndexOf("</sa>"));
		Log.i("字符串>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			JSONObject jo=json;
			Log.i("字符串1>>>>>>>>>>>>>>>", json + "");
			defect_et1.setText(jo.getString("xlmc"));
			defect_et2.setText(jo.getString("xlgh"));
			//分支开关
			String fz=jo.getString("fzkg");
			if(fz.equals("none")){
				fz="不带分支开关";
			}else{
				fz="带分支开关";
			}
			defect_fz.setText(fz);
			
			defect_et3.setText(jo.getString("sbmc"));
			defect_et4.setText(jo.getString("sbbh"));
			defect_et5.setText(jo.getString("sftd"));
			defect_et6.setText(jo.getString("dwmc"));
			defect_et7.setText(jo.getString("bzmc"));
			defect_et8.setText(jo.getString("qxly"));
			defect_et9.setText(jo.getString("qxyy"));
			StringBuffer qxnr=new StringBuffer(jo.getString("qxnr"));
			qxnr=qxnr.insert(qxnr.indexOf("。")+1, "\n");
			defect_et10.setText(qxnr.toString());
			defect_et11.setText(jo.getString("qxnrsm"));
			defect_et12.setText(jo.getString("qxdj"));
			defect_et13.setText(jo.getString("wcrq"));
			defect_et14.setText(jo.getString("qxddms"));
			defect_et15.setText(jo.getString("yycbfx"));
			defect_et16.setText(jo.getString("cbclfa"));
			defect_et17.setText(jo.getString("fxrq"));
			defect_et18.setText(jo.getString("fxr"));
			if(ParseJasonDataService.audit_img1!=null){
				img_before.setImageBitmap(ParseJasonDataService.audit_img1);
			}
//			defect_et19.setText(jo.getString("gpsdw"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 初始化左边专职审核
	private void initLeftAudit() {
		initLeftDefect();
		audit_e1 = (EditText) findViewById(R.id.audit_e1);
		audit_e2 = (EditText) findViewById(R.id.audit_e2);
		audit_et1 = (EditText) findViewById(R.id.audit_et1);
		audit_et2 = (EditText) findViewById(R.id.audit_et2);
		audit_et3 = (EditText) findViewById(R.id.audit_et3);
		audit_et4 = (EditText) findViewById(R.id.audit_et4);
		//--------------为控件赋值--------------------------
		str1=intent.getStringExtra("str1");
		String t = str1;
		t = t.substring(t.indexOf("<sa>") + 4, t
				.lastIndexOf("</sa>"));
		Log.i("字符串>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			JSONObject jo=json;
			Log.i("字符串1>>>>>>>>>>>>>>>", json + "");
			audit_e1.setText(jo.getString("qxdj"));
			audit_e2.setText(jo.getString("sh_sftd"));
			audit_et1.setText(jo.getString("shyj"));
			audit_et2.setText(jo.getString("shrq"));
			audit_et3.setText(jo.getString("shr"));
			audit_et4.setText(jo.getString("bz"));
			if(ParseJasonDataService.zrpz_img1!=null){
				img_before.setImageBitmap(ParseJasonDataService.zrpz_img1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 初始化左边主任审批
	private void initLeftApproval() {
		initLeftAudit();
		approval_et1 = (EditText) findViewById(R.id.approval_et1);
		approval_et2 = (EditText) findViewById(R.id.approval_et2);
		approval_et3 = (EditText) findViewById(R.id.approval_et3);
		//--------------为控件赋值--------------------------
		str2=intent.getStringExtra("str2");
		String t = str2;
		Log.i("字符串>>>>>>>>>>>>>>>", t);
		t = t.substring(t.indexOf("<sa>") + 4, t
				.lastIndexOf("</sa>"));
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			JSONObject jo=json;
			Log.i("字符串1>>>>>>>>>>>>>>>", json + "");
			approval_et1.setText(jo.getString("pzrq"));
			approval_et2.setText(jo.getString("pzr"));
			approval_et3.setText(jo.getString("pzyj"));
			if(ParseJasonDataService.qxcl_img1!=null){
				img_before.setImageBitmap(ParseJasonDataService.qxcl_img1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 初始化左边缺陷处理
	private void initLeftHandle() {
		initLeftApproval();
		handle_e1 = (EditText) findViewById(R.id.handle_e1);
		handle_et1 = (EditText) findViewById(R.id.handle_et1);
		handle_et2 = (EditText) findViewById(R.id.handle_et2);
		handle_et3 = (EditText) findViewById(R.id.handle_et3);
		handle_et4 = (EditText) findViewById(R.id.handle_et4);
		handle_et5 = (EditText) findViewById(R.id.handle_et5);
		handle_et6 = (EditText) findViewById(R.id.handle_et6);
		handle_et7 = (EditText) findViewById(R.id.handle_et7);
		handle_et8 = (EditText) findViewById(R.id.handle_et8);
		handle_btn1 = (Button) findViewById(R.id.handle_btn1);
		img_after = (ImageView) findViewById(R.id.img_after);
		//--------------为控件赋值--------------------------
		str3=intent.getStringExtra("str3");
		String t = str3;
		t = t.substring(t.indexOf("<sa>") + 4, t
				.lastIndexOf("</sa>"));
		Log.i("字符串>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			JSONObject jo=json;
			Log.i("字符串1>>>>>>>>>>>>>>>", json + "");
			handle_e1.setText(jo.getString("xcclqk"));
			StringBuffer info=new StringBuffer(jo.getString("fsclmx"));
			info=info.insert(info.indexOf("。")+1, "\n");
			handle_et1.setText(info.toString());
			handle_et2.setText(jo.getString("sfylwt"));
			handle_et3.setText(jo.getString("clrq"));
			handle_et4.setText(jo.getString("ylwtms"));
			handle_et5.setText(jo.getString("xqdw"));
			handle_et6.setText(jo.getString("xqfzr"));
			handle_et7.setText(jo.getString("gsfy"));
			handle_et8.setText(jo.getString("bz"));
			if(ParseJasonDataService.qxys_img1!=null){
				img_before.setImageBitmap(ParseJasonDataService.qxys_img1);
			}
			if(ParseJasonDataService.qxys_img2!=null){
				img_after.setImageBitmap(ParseJasonDataService.qxys_img2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 初始化左边缺陷验收
	// private void initLeftAcceptance() {
	// initLeftHandle();
	// }

	// -----------------------------------------------------------------------
	// 初始化右边专职审核
	private void initRightAudit() {
		r_audit_spinner1 = (Spinner) findViewById(R.id.r_audit_spinner1);
		r_audit_spinner2 = (Spinner) findViewById(R.id.r_audit_spinner2);
		r_audit_spinner3 = (Spinner) findViewById(R.id.r_audit_spinner3);
		r_audit_et1 = (EditText) findViewById(R.id.r_audit_et1);
		r_audit_et2 = (EditText) findViewById(R.id.r_audit_et2);
		r_audit_et3 = (EditText) findViewById(R.id.r_audit_et3);
		r_audit_et4 = (EditText) findViewById(R.id.r_audit_et4);
		r_audit_et5 = (EditText) findViewById(R.id.r_audit_et5);
		r_audit_save = (Button) findViewById(R.id.r_audit_save);
		//--------------为控件赋值--------------------------
		str1=intent.getStringExtra("str1");
//		// 数据
//		data_list = new ArrayList<String>();
//		data_list.add("Ⅰ");
//		data_list.add("Ⅱ");
//		data_list.add("Ⅲ");
//		data_list.add("Ⅳ");
//		// 适配器
//		arr_adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, data_list);
//		// 设置样式
//		arr_adapter
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		// 加载适配器
//		r_audit_spinner1.setAdapter(arr_adapter);
		
		// --------------获取当前系统日期-------------------------------------
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		r_audit_et3.setText(year + "-" + (month + 1) + "-" + day);
		r_audit_et1.setOnClickListener(Audit_process.this);
		r_audit_et3.setOnClickListener(Audit_process.this);
		r_audit_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				r_audit_yqwcrq = r_audit_et1.getText().toString().trim();
				r_audit_shyj = r_audit_et2.getText().toString().trim();
				r_audit_shrq = r_audit_et3.getText().toString().trim();
				r_audit_shr = r_audit_et4.getText().toString().trim();
				r_audit_sfty = "--请选择--";
				r_audit_bz = r_audit_et5.getText().toString().trim();
				if (r_audit_shyj.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写审核意见");
					return;
				}
				if (r_audit_sfty.equals("--请选择--")) {
					ScreenUtil.showMsg(Audit_process.this, "请选择是否同意流程处理");
					return;
				}
				//id
				//xm  shr r_audit_et4.getText();
			}
		});
		Log.i("122>>>>>>>>>>>>>>>", str);
		/**
		 * 专职审核
		 */
		/*
		 * 查看缺陷信息 qxlc/zzsh/lr_qxdl.jsp?id=1303
		 */
		String t = str1;
		t = t.substring(t.indexOf("<sa>") + 4, t
				.lastIndexOf("</sa>"));
		Log.i("字符串>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject json = null;
		try {
			json = new JSONObject(t);
			//缺陷等级列表
			Log.i("字符串1>>>>>>>>>>>>>>>", json.getJSONObject("sh_qxdj").getJSONArray("sh_qxdj") + "");
			JSONArray ja=json.getJSONObject("sh_qxdj").getJSONArray("sh_qxdj");
			//选中缺陷等级
			Log.i("字符串2>>>>>>>>>>>>>>>", json.getJSONObject("sh_qxdj").getString("selected") + "");
			final List<String> list = new ArrayList<String>();
			int selected=0;
			for(int i=0;i<ja.length();i++){
				if(ja.getString(i).equals(json.getJSONObject("sh_qxdj").getString("selected"))){
					selected=i;
				}
				list.add(ja.getString(i));
			}
			ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.item_spinner, R.id.text,
					list);
			r_audit_spinner1.setAdapter(spinner1Adapter);
			r_audit_spinner1.setSelection(selected,true);
			r_audit_qxdj = list.get(selected);
			r_audit_spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(
						AdapterView<?> arg0, View v,
						int pos, long arg3) {
					r_audit_qxdj=list.get(pos);
				}
				@Override
				public void onNothingSelected(
						AdapterView<?> arg0) {
					
				}
			});
			final List<String> list1 = new ArrayList<String>();
			ja=json.getJSONObject("sh_sftd").getJSONArray("sh_sftd");
			selected=0;
			for(int i=0;i<ja.length();i++){
				if(ja.getString(i).equals(json.getJSONObject("sh_sftd").getString("selected"))){
					selected=i;
				}
			}
			list1.add("是");
			list1.add("否");
			spinner1Adapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.item_spinner, R.id.text,
					list1);
			r_audit_spinner2.setAdapter(spinner1Adapter);
			r_audit_spinner2.setSelection(selected,true);
			r_audit_td = list1.get(selected);
			r_audit_spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(
						AdapterView<?> arg0, View v,
						int pos, long arg3) {
					r_audit_td=list1.get(pos);
				}
				@Override
				public void onNothingSelected(
						AdapterView<?> arg0) {
				}
			});
			r_audit_et1.setText(json.getString("wcrq"));
			r_audit_et4.setText(json.getString("sh_shr"));
			final List<String> list2 = new ArrayList<String>();
			list2.add("--请选择--");
			list2.add("同意");
			list2.add("不同意");
			spinner1Adapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.item_spinner, R.id.text,
					list2);
			r_audit_spinner3.setAdapter(spinner1Adapter);
			r_audit_spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(
						AdapterView<?> arg0, View v,
						int pos, long arg3) {
					r_audit_sfty=list2.get(pos);
				}
				@Override
				public void onNothingSelected(
						AdapterView<?> arg0) {
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 初始化右边主任审批
	private void initRightApproval() {
		r_approval_spinner1 = (Spinner) findViewById(R.id.r_approval_spinner1);
		r_approval_et1 = (EditText) findViewById(R.id.r_approval_et1);
		r_approval_et2 = (EditText) findViewById(R.id.r_approval_et2);
		r_approval_et3 = (EditText) findViewById(R.id.r_approval_et3);
		r_approval_save = (Button) findViewById(R.id.r_approval_save);
		r_approval_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				r_approval_pzrq = r_approval_et1.getText().toString().trim();
				r_approval_pzr = r_approval_et2.getText().toString().trim();
				r_approval_pzyj = r_approval_et3.getText().toString().trim();
				if (r_approval_pzrq.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写批准日期");
					return;
				}
				if (r_approval_pzr.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写批准人");
					return;
				}
				if (r_approval_pzyj.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写批准意见");
					return;
				}

			}
		});
		//--------------为控件赋值--------------------------
		str2=intent.getStringExtra("str2");
	}

	// 初始化右边缺陷处理
	private void initRightHandle() {
		r_handle_et1 = (EditText) findViewById(R.id.r_handle_et1);
		r_handle_et2 = (EditText) findViewById(R.id.r_handle_et2);
		r_handle_et3 = (EditText) findViewById(R.id.r_handle_et3);
		r_handle_et4 = (EditText) findViewById(R.id.r_handle_et4);
		r_handle_et5 = (EditText) findViewById(R.id.r_handle_et5);
		r_handle_et6 = (EditText) findViewById(R.id.r_handle_et6);
		r_handle_et7 = (EditText) findViewById(R.id.r_handle_et7);
		r_handle_et8 = (EditText) findViewById(R.id.r_handle_et8);
		r_handle_et9 = (EditText) findViewById(R.id.r_handle_et9);
		r_handle_img_after = (ImageView) findViewById(R.id.r_handle_img_after);
		r_handle_spinner1 = (Spinner) findViewById(R.id.r_handle_spinner1);
		r_handle_spinner2 = (Spinner) findViewById(R.id.r_handle_spinner2);
		// r_handle_btn1 = (Button) findViewById(R.id.r_handle_btn1);
		// r_handle_btn2 = (Button) findViewById(R.id.r_handle_btn2);
		btn_camera = (Button) findViewById(R.id.btn_camera);
		r_handle_save = (Button) findViewById(R.id.r_handle_save);
		btn_del = (Button) findViewById(R.id.btn_del);
		r_handle_show = (LinearLayout) findViewById(R.id.r_handle_show);
		btn_del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				r_handle_img_after.setVisibility(View.GONE);
				r_handle_show.setVisibility(View.GONE);
				r_handle_et9.setText("");
			}
		});
		r_handle_img_after.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Audit_process.this,
						BigPhotoActivity.class);
				i.putExtra("imgname", SAVE_PATH_IN_SDCARD);
				startActivity(i);
			}
		});
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
		r_handle_et2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						HandleContent.class);
				startActivity(i);
			}
		});
		r_handle_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				r_handle_xcclqk = r_handle_et1.getText().toString().trim();
				r_handle_fsclmx = r_handle_et2.getText().toString().trim();
				r_handle_clrq = r_handle_et3.getText().toString().trim();
				r_handle_ylwtms = r_handle_et4.getText().toString().trim();
				r_handle_xqdw = r_handle_et5.getText().toString().trim();
				r_handle_xqfzr = r_handle_et6.getText().toString().trim();
				r_handle_gsfy = r_handle_et7.getText().toString().trim();
				r_handle_bz = r_handle_et8.getText().toString().trim();
				r_handle_gpsdw = r_handle_et9.getText().toString().trim();
				if (r_handle_xcclqk.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写现场处理情况");
					return;
				}
				if (r_handle_fsclmx.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写发生材料明细");
					return;
				}
				if (r_handle_clrq.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写处理日期");
					return;
				}
				if (r_handle_ylwtms.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写遗留问题描述");
					return;
				}
				if (r_handle_gpsdw.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写GPS定位");
					return;
				}
			}
		});
		//--------------为控件赋值--------------------------
		str3=intent.getStringExtra("str3");
	}

	// 初始化右边缺陷验收
	private void initRightAcceptance() {
		r_acceptance_et1 = (EditText) findViewById(R.id.r_acceptance_et1);
		r_acceptance_et1 = (EditText) findViewById(R.id.r_acceptance_et2);
		r_acceptance_et1 = (EditText) findViewById(R.id.r_acceptance_et3);
		r_acceptance_et1 = (EditText) findViewById(R.id.r_acceptance_et4);
		r_acceptance_et1 = (EditText) findViewById(R.id.r_acceptance_et5);
		r_acceptance_spinner1 = (Spinner) findViewById(R.id.r_acceptance_spinner1);
		r_acceptance_save = (Button) findViewById(R.id.r_acceptance_save);
		r_acceptance_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				r_acceptance_ysqk = r_acceptance_et1.getText().toString()
						.trim();
				r_acceptance_ysylwtms = r_acceptance_et2.getText().toString()
						.trim();
				r_acceptance_ysrq = r_acceptance_et3.getText().toString()
						.trim();
				r_acceptance_ysr = r_acceptance_et4.getText().toString().trim();
				r_acceptance_bz = r_acceptance_et5.getText().toString().trim();
				if (r_acceptance_ysqk.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写验收情况");
					return;
				}
				if (r_acceptance_ysylwtms.equals("")) {
					ScreenUtil.showMsg(Audit_process.this, "请填写验收遗留问题描述");
					return;
				}
			}
		});
		//--------------为控件赋值--------------------------
		str4=intent.getStringExtra("str4");
	}

	@Override
	public void onClick(View v) {
		final EditText et = (EditText) v;
		new DatePickerDialog(Audit_process.this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Audit_process.this.year = year;
						month = monthOfYear;
						day = dayOfMonth;
						et.setText(year + "年" + (month + 1) + "月" + day + "日");
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if (resultCode == RESULT_OK) {
		// if (requestCode == REQUEST_CODE_TAKE_PICTURE) {// 拍照返回
		// // 存储卡可用
		// if (isHasSdcard()) {
		// getImage(SDCARD_ROOT_PATH + SAVE_PATH_IN_SDCARD
		// + IMAGE_CAPTURE_NAME);
		// } else {
		// // 存储卡不可用直接返回缩略图
		// Bundle extras = data.getExtras();
		// bitmap = (Bitmap) extras.get("data");
		// r_handle_img_after.setImageBitmap(bitmap);
		// r_handle_img_after.setVisibility(View.VISIBLE);
		// // hasShootPic = false;//此变量是在提交数据时，验证是否有图片用
		// }
		// }
		// }
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
			Toast.makeText(this, IMAGE_CAPTURE_NAME, Toast.LENGTH_LONG).show();
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
			r_handle_img_after.setImageBitmap(bitmap);// 将图片显示在ImageView里
			r_handle_img_after.setVisibility(View.VISIBLE);
			r_handle_show.setVisibility(View.VISIBLE);
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
				Toast.makeText(this, "请检查网络或GPS是否打开", Toast.LENGTH_LONG).show();
				return;
			}
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				// 获取当前位置，这里只用到了经纬度
				String string = "经度：" + CutChar(location.getLongitude())
						+ "\n纬度：" + CutChar(location.getLatitude());
				r_handle_et9.setText(string);
			}
			// 绑定定位事件，监听位置是否改变
			// 第一个参数为控制器类型
			// 第二个参数为监听位置变化的时间间隔（单位：毫秒）
			// 第三个参数为位置变化的间隔（单位：米）
			// 第四个参数为位置监听器
			locationManager.requestLocationUpdates(provider, 2000, 2,
					locationListener);

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

	/*
	 * 获得图片 图片高度 最大maxH
	 * 
	 * @param imagePath
	 */

	int maxH = 200;//  

	private void getImage(String imagePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		bitmap = BitmapFactory.decodeFile(imagePath, options); // 此时返回bm为空
		// 计算缩放比
		int be = (int) (options.outHeight / (float) maxH);
		int ys = options.outHeight % maxH;// 求余数
		float fe = ys / (float) maxH;
		if (fe >= 0.5)
			be = be + 1;
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;

		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		r_handle_img_after.setImageBitmap(bitmap);
		r_handle_img_after.setVisibility(View.VISIBLE);
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
	 * 检查存储卡是否插入
	 * 
	 * @return
	 */
	public static boolean isHasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
