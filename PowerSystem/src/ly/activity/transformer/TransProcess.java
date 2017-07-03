package ly.activity.transformer;

import ly.activity.R;
import ly.activity.bug.Audit_process;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;



public class TransProcess extends Activity{
	// ---------菜单项-------------------------------------------
	private Button left;// 流转过程按钮
	private Button right;// 右边按钮
	private ImageButton btn;// 关闭按钮
	// --------左边需要隐藏的表格--------------------------------------------
	private LinearLayout tables_audit;
	private LinearLayout tables_approval;
	// private TableLayout tables_acceptance;
	// --------右边需要隐藏的表格---------------------------
	private LinearLayout table_audit;
	private LinearLayout table_approval;
	//------------左边页面---------------------------------
	//------------变压器抢修---------------------------------
	private EditText et1;//变压器维修属性
	private EditText et2;//变压器维修工单,已隐藏
	private EditText et3;//更换/迁移日期
	private EditText et4;//单位名称
	private EditText et5;//队伍名称
	private EditText et6;//原变压器类型
	private EditText et7;//原变压器名称
	private EditText et8;//原变压器厂家
	private EditText et9;//原变压器品牌
	private EditText et10;//原变压器容量
	private EditText et11;//原变压器序列号
	private EditText et12;//新变压器类型
	private EditText et13;//新变压器名称
	private EditText et14;//新变压器厂家
	private EditText et15;//新变压器品牌
	private EditText et16;//新变压器容量
	private EditText et17;//新变压器序列号
	private EditText et18;//工程量
	private EditText et19;//更换/迁移材料明细
	private EditText et20;//概算价格
	private EditText et21;//现场负责人
	private EditText et22;// 抢修前GPS
	private EditText et23;// 抢修后GPS
	private EditText et24;// 备注
	private Button btn1;//查看价格明细
	private ImageView img_before;// 实施前照片
	private ImageView img_after;// 实施后照片
	private LinearLayout ly_replace;//更换显示项
	//------------专职审核---------------------------------
	private EditText audit_et1;//审核意见
	private EditText audit_et2;//审核人
	
	//------------右边页面---------------------------------
	//------------专职审核---------------------------------
	private Spinner r_audit_spinner1;//抢修队伍名称
	private Spinner r_audit_spinner2;//是否同意流程处理
	private EditText r_audit_et1;//现场负责人
	private EditText r_audit_et2;//审核意见
	private EditText r_audit_et3;//概算价格
	private EditText r_audit_et4;//审核人
	private Button r_audit_btn1;//审核概算价格
	private Button r_audit_save;//保存
	//------------主任审批---------------------------------
	private Spinner r_approval_spinner1;//是否同意流程处理
	private EditText r_approval_et1;//审批意见
	private EditText r_approval_et2;//概算价格
	private EditText r_approval_et3;//审批人
	private Button r_approval_btn1;//审核概算价格
	private Button r_approval_save;//保存
	
	
	private int index = 0;// 页面定位
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		index = intent.getIntExtra("index", 0);
		setContentView(R.layout.trans_audit_processes);
		left(index);
	}

	// 左边显示菜单
	private void left(final int index) {
		setContentView(R.layout.trans_audit_processes);
		right = (Button) findViewById(R.id.audit);
		btn = (ImageButton) findViewById(R.id.button1);
		initLeft(index);
		right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.trans_audit_process);
				right(index);
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				TransProcess.this.finish();
			}
		});
	}

	// 右边显示菜单
	private void right(final int index) {
		setContentView(R.layout.trans_audit_process);
		// tr1 = (TableRow) findViewById(R.id.tr1);
		// tr1.setVisibility(View.GONE);
		left = (Button) findViewById(R.id.other);
		btn = (ImageButton) findViewById(R.id.button1);
		initRight(index);
		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.trans_audit_processes);
				left(index);
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				TransProcess.this.finish();
			}
		});
	}
	// 初始化左边菜单
	private void initLeft(int index) {
		tables_audit = (LinearLayout) findViewById(R.id.tables_audit);
		tables_approval = (LinearLayout) findViewById(R.id.tables_approval);
		ly_replace=(LinearLayout)findViewById(R.id.ly_replace);
		switch (index) {
		case 1:
			tables_audit.setVisibility(View.GONE);
			break;
		case 2:
			tables_approval.setVisibility(View.GONE);
			right.setText("主任审批");
			break;
		}
	}

	// 初始化右边菜单
	private void initRight(int index) {
		right = (Button) findViewById(R.id.audit);
		table_audit = (LinearLayout) findViewById(R.id.table_audit);
		table_approval = (LinearLayout) findViewById(R.id.table_approval);
		switch (index) {
		case 1:
			table_approval.setVisibility(View.GONE);
			break;
		case 2:
			table_audit.setVisibility(View.GONE);
			right.setText("主任审批");
			break;
		}
	}
	//初始化左边抢修内容
	private void initLeftReport(){
		String str_et1=et1.getText().toString();
		if("更换".equals(str_et1)){
			ly_replace.setVisibility(View.VISIBLE);
		}else{
			ly_replace.setVisibility(View.GONE);
		}
	}
	//初始化左边专职审核内容
	private void initLeftAudit(){
		
	}
	//初始化右边专职审核内容
	private void initRightAudit(){
		
	}
	//初始化右边主任审批内容
	private void initRightApprove(){
		
	}

}
