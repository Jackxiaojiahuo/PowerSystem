package ly.activity.fault;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ly.activity.BigPhotoActivity;
import ly.activity.R;
import ly.activity.bug.LineActivity;
import ly.util.LogUtil;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
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
 * 故障抢修填报,审核,审批
 * 
 * @author obeya
 * 
 */
public class AddReport extends Activity implements OnClickListener {

	/**
	 * 从调用本方法处 跳转到本界面
	 * 
	 * @param context
	 */

	private final String TAG = "AddReport";

	boolean flag = true;

	private EditText et1;// 编号,已禁用
	private EditText lineName; // 线路名称
	
	//---------------------------------------
	private EditText et3;//方位1
	private EditText et4;//方位2
	private EditText et5;//方位3
	private EditText et6;//方位4
	//---------------------------------------
	private EditText etgh;//线路杆号
	private EditText et7;// 设备编号
	private EditText et8;// 抢修项目
	private EditText et9;// 抢修日期
	private EditText et10;//抢修详情及抢修情况
	private EditText et11;//工程量
	private EditText et12;// 发生材料
	private EditText et13;//概算价格
	private EditText et14;//现场负责人
	private EditText et15;// 抢修前GPS
	private EditText et16;// 抢修后GPS
	private EditText et_name;//单位名称
	private EditText et_name1;//抢修队伍名称
	private Button btn_save;// 保存按钮
	private Button btn_del;// 抢修前删除图片按钮
	private Button btn_camera;// 抢修前拍照按钮
	private Button btn_del1;// 抢修后删除图片按钮
	private Button btn_camera1;// 抢修后拍照按钮
	private Spinner spinner1; // 方位1
	private Spinner spinner2; // 方位2
	private Spinner spinner3; // 方位3
	private Spinner spinner4;// 是否带分支开关
	private Spinner spinner5;// 设备类型下拉列表
	private ImageView img_before;// 照片
	private ImageView img_after;// 照片
	private LinearLayout ly_spinner;// 分支显示
	private LinearLayout show;// 抢修前删除照片
	private LinearLayout show1;// 抢修后删除照片
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
	public static String IMAGE_CAPTURE_NAME1 = "cameraTmp.png"; // 照片名称
	private Bitmap bitmap;// 拍照后图片
	// 定位都要通过LocationManager这个类实现
	private LocationManager locationManager;
	private String provider;

	private int index;// 页面定位标识

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fault_report_add);
		Intent intent = getIntent();
		index = intent.getIntExtra("index", 0);
		if (index == 0) {

		} else {

		}

		/*
		 * 如果本页面用到Spinner 需使用以下方式设置View
		 */
		// View contentView =
		// LayoutInflater.from(this.getParent()).inflate(R.layout.menu_add_defects,
		// null);

		initWeight();

		lineName.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), LineActivity.class);
				startActivityForResult(intent, 1); // 激活目标Activity,
				// 通过父Activity启动其他Activity
				// AddDefectsActivity.this.getParent().startActivityForResult(intent,
				// 1); // 激活目标Activity,
				// startActivity(intent); // 并回传值
			}
		});
		initContent();
		initDeviceTypes(); // 初始化设备类型

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
		et8 = (EditText) findViewById(R.id.et8);
		et9 = (EditText) findViewById(R.id.et9);
		etgh=(EditText) findViewById(R.id.etgh);
		et10 = (EditText) findViewById(R.id.et10);
		et11 = (EditText) findViewById(R.id.et11);
		et12 = (EditText) findViewById(R.id.et12);
		et13 = (EditText) findViewById(R.id.et13);
		et14 = (EditText) findViewById(R.id.et14);
		et15 = (EditText) findViewById(R.id.et15);
		et16 = (EditText) findViewById(R.id.et16);
		et_name = (EditText) findViewById(R.id.et_name);
		et_name1 = (EditText) findViewById(R.id.et_name1);
		btn_del = (Button) findViewById(R.id.btn_del);
		btn_camera = (Button) findViewById(R.id.btn_camera);
		btn_del1 = (Button) findViewById(R.id.btn_del1);
		btn_camera1 = (Button) findViewById(R.id.btn_camera1);
		btn_save = (Button) findViewById(R.id.btn_save);
		spinner1 = (Spinner) findViewById(R.id.spinner1); 
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		spinner4 = (Spinner) findViewById(R.id.spinner4);
		spinner5 = (Spinner) findViewById(R.id.spinner5);// 设备类型
		img_before = (ImageView) findViewById(R.id.img_before);
		img_after = (ImageView) findViewById(R.id.img_after);
		show = (LinearLayout) findViewById(R.id.show);
		show1 = (LinearLayout) findViewById(R.id.show1);
		ly_spinner = (LinearLayout) findViewById(R.id.ly_spinner);
		// 调用拍照
		btn_camera.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(i, 1);
			}
		});
		btn_camera1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(i, 2);
			}
		});
		// 删除照片
		btn_del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				img_before.setVisibility(View.GONE);
				show.setVisibility(View.GONE);
				et15.setText("");
			}
		});
		// 删除照片
		btn_del1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				img_before.setVisibility(View.GONE);
				show.setVisibility(View.GONE);
				et16.setText("");
			}
		});
		// 放大照片
		img_before.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						BigPhotoActivity.class);
				i.putExtra("imgname", SAVE_PATH_IN_SDCARD);
				startActivity(i);
			}
		});
		// 放大照片
		img_after.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						BigPhotoActivity.class);
				i.putExtra("imgname", SAVE_PATH_IN_SDCARD);
				startActivity(i);
			}
		});
	}

	/**
	 * 初始化设备类型
	 */
	private void initDeviceTypes() {
		// 测试数据 -> 需要重数据库查询
		final List<String> deviceTypes = new ArrayList<String>();
		deviceTypes.add("--请选择--");
		deviceTypes.add("架空线路");
		deviceTypes.add("柱上真空开关");
		deviceTypes.add("柱上SF6开关");
		deviceTypes.add("柱上隔离开关");
		deviceTypes.add("跌落式熔断器");
		deviceTypes.add("金属氧化物避雷器");
		deviceTypes.add("电容器");
		deviceTypes.add("高压计量箱");
		deviceTypes.add("配电变压器");
		deviceTypes.add("开关柜");
		deviceTypes.add("电缆线路");
		deviceTypes.add("电缆分支箱");
		deviceTypes.add("构筑物及外壳");
		LogUtil.d(TAG, "%%%% deviceTypes个数:" + deviceTypes.size() + " %%%%");

		/*
		 * 下拉框选项实体，在Spinner中显示选项的时候， 默认会显示每个项（Object）的 toString() 之后的内容， 因此重写
		 * toString() 方法，让它输出 文本 。
		 */

		/*
		 * 设置Spinner的适配器
		 */
		ArrayAdapter<String> spinnerDeviceTypeDdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.item_spinner, R.id.text,
				deviceTypes);
		spinner5.setAdapter(spinnerDeviceTypeDdapter);
		// spinnerDeviceType.setPrompt("选择设备类型");
		/*
		 * Spinner监听
		 */

		spinner5.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast
						.makeText(getApplicationContext(),
								"选中设备:" + deviceTypes.get(position),
								Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void initContent() {
		// -------------------------
		
		//6-23已弃用
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
		// --------------获取当前系统日期-------------------------------------
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		et9.setText(year + "年" + (month + 1) + "月" + day + "日");
		et9.setOnClickListener(AddReport.this);
		// --------发生材料录入---------------
		et12.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Intent in=new
				// Intent(getApplicationContext(),DefectContent.class);
				// startActivity(in);
			}
		});

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
				File file = new File("/sdcard/myImage/"); // 放在内存卡上
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
					String string = "纬度：" + location.getLatitude() + "\n经度："
							+ location.getLongitude();
					et15.setText(string);
				}
				// 绑定定位事件，监听位置是否改变
				// 第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
				// 第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器
				locationManager.requestLocationUpdates(provider, 2000, 2,
						locationListener);

			}
		} else if (requestCode == 2) {
			if (resultCode == Activity.RESULT_OK) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				IMAGE_CAPTURE_NAME1 = new DateFormat().format(
						"yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))
						+ ".jpg";
				Toast.makeText(this, IMAGE_CAPTURE_NAME, Toast.LENGTH_LONG)
						.show();
				Bundle bundle = data.getExtras();
				bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b = null;
				File file = new File("/sdcard/myImage/"); // 放在内存卡上
				file.mkdirs();// 创建文件夹
				SAVE_PATH_IN_SDCARD = "/sdcard/myImage/" + IMAGE_CAPTURE_NAME1;
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
				img_after.setImageBitmap(bitmap);// 将图片显示在ImageView里
				img_after.setVisibility(View.VISIBLE);
				show1.setVisibility(View.VISIBLE);
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
					String string = "纬度：" + location.getLatitude() + "\n经度："
							+ location.getLongitude();
					et16.setText(string);
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
		super.onDestroy();
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
		}
	}

	// 时间点击事件
	@Override
	public void onClick(View v) {
		final EditText et = (EditText) v;
		new DatePickerDialog(AddReport.this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						AddReport.this.year = year;
						month = monthOfYear;
						day = dayOfMonth;
						et.setText(year + "年" + (month + 1) + "月" + day + "日");
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();
	}

}
