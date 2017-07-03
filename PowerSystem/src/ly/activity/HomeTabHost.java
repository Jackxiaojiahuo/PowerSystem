package ly.activity;

import ly.net.ParseJasonDataService;
import ly.util.LogUtil;
import ly.util.ScreenUtil;
import ly.util.Constant.TabValue;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

/**
 * TabHost主页
 * 
 * @author obeya
 * 
 */
// @SuppressLint("InflateParams")
// @SuppressWarnings("deprecation")
public class HomeTabHost extends android.app.TabActivity {

	private final String TAG = "HomeTabHost";
	public static Handler mHandler;
	private TabHost mTabHost;
	private LayoutInflater mInflater;
	private TextView role;
	private TextView name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_tabhost);
		LogUtil.d(TAG, "%%%% TabHost主页准备初始化 %%%%");
		role = (TextView) findViewById(R.id.role);
		name = (TextView) findViewById(R.id.username);
		init();
	}

	private void init() {
		Intent in=getIntent();
		String d=in.getStringExtra("d");
		String r=in.getStringExtra("r");
		String n=in.getStringExtra("n");
		role.setText("部门:" + d + "\n角色:" + r);
		name.setText(n);
		initView(); // 初始化主页
	}

	/**
	 * 初始化视图组件
	 */
	private void initView() {
		mTabHost = getTabHost(); // 获取TabHost对象
		mInflater = LayoutInflater.from(this); // 实例化布局对象

		int count = TabValue.mTabClassArray.length; // 得到tabhost的tab个数
		LogUtil.d(TAG, "%%%% tabhost的tab个数: " + count + " %%%%");

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(TabValue.mTextViewArray[i])
					.setIndicator(getTabItemView(i)).setContent(
							getTabItemIntent(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(
					R.drawable.tab_background_selector);
		}
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = mInflater.inflate(R.layout.item_tab, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.image_icon);
		if (imageView != null) {
			imageView.setImageResource(TabValue.mImageViewArray[index]);
		}
		TextView textView = (TextView) view.findViewById(R.id.text_name);
		textView.setText(TabValue.mTextViewArray[index]);
		return view;
	}

	/**
	 * 给Tab选项卡设置内容（每个内容是一个Activity）
	 */
	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this, TabValue.mTabClassArray[index]);
		return intent;
	}
}
