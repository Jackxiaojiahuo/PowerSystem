package ly.activity.fault;

import ly.activity.R;
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



public class FaultProcess extends Activity{
	// ---------菜单项-------------------------------------------
	private Button left;// 流转过程按钮
	private Button right;// 右边按钮
	private ImageButton btn;// 关闭按钮
	// --------左边需要隐藏的表格--------------------------------------------
	private LinearLayout tables_audit;
	// --------右边需要隐藏的表格---------------------------
	private LinearLayout table_audit;
	private LinearLayout table_approval;
	//-----------左边控件------------------------------------
	//--------故障抢修-----------------------
	private EditText report_e1;// 线路名称
	private EditText report_e2;//线路杆号
	private EditText report_e3;//设备类型
	private EditText report_e4;//设备编号
	private EditText report_e5;//抢修项目
	private EditText report_e6;//抢修日期
	private EditText report_e7;//抢修队伍名称
	private EditText report_e8;//故障详情及抢修情况
	private EditText report_e9;//工程量
	private EditText report_e10;//抢修发生材料明细
	private EditText report_e11;//概算价格
	private EditText report_e12;//现场负责人
	private EditText report_e13;//抢修前GPS定位
	private EditText report_e14;//抢修后GPS定位
	private Button report_btn1;//查看价格明细按钮
	private ImageView img_before;//抢修前照片
	private ImageView img_after;//抢修后照片
	//-------------专职审核----------------
	private EditText audit_et1;//审核意见
	private EditText audit_et2;//审核人
	//------------右边控件------------------------------------------
	//------------专职审核----------------------
	private Spinner r_audit_spinner1;//抢修队伍名称
	private Spinner r_audit_spinner2;//是否同意流程处理
	private EditText r_audit_et1;//现场负责人
	private EditText r_audit_et2;//审核意见
	private EditText r_audit_et3;//概算价格
	private EditText r_audit_et4;//审核人
	private Button r_audit_btn1;//审核概算价格
	private Button r_audit_save;//保存
	//-------------主任审批---------------------
	private Spinner r_approval_spinner1;//是否同意流程处理
	private EditText r_approval_et1;//审批意见
	private EditText r_approval_et2;//概算价格
	private EditText r_approval_et3;//审批人
	private Button r_approval_btn1;//审核概算价格
	private Button r_approval_save;//保存
	//-----------其他变量-----------------------
	private int index = 0;// 页面定位
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		index = intent.getIntExtra("index", 0);
		setContentView(R.layout.fault_audit_processes);
		left(index);
	}

	// 左边显示菜单
	private void left(final int index) {
		setContentView(R.layout.fault_audit_processes);
		right = (Button) findViewById(R.id.audit);
		btn = (ImageButton) findViewById(R.id.button1);
		initLeft(index);
		right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.fault_audit_process);
				right(index);
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				FaultProcess.this.finish();
			}
		});
	}

	// 右边显示菜单
	private void right(final int index) {
		setContentView(R.layout.fault_audit_process);
		left = (Button) findViewById(R.id.other);
		btn = (ImageButton) findViewById(R.id.button1);
		initRight(index);
		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.fault_audit_processes);
				left(index);
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				FaultProcess.this.finish();
			}
		});
	}
	// 初始化左边菜单
	private void initLeft(int index){
		tables_audit = (LinearLayout) findViewById(R.id.tables_audit);
		switch(index){
		case 1:
			tables_audit.setVisibility(View.GONE);
			break;
		case 2:
			right.setText("主任审批");
			break;
		}
	}
	// 初始化右边菜单
	private void initRight(int index) {
		right = (Button) findViewById(R.id.audit);
		table_audit = (LinearLayout) findViewById(R.id.table_audit);
		table_approval = (LinearLayout) findViewById(R.id.table_approval);
		switch(index){
		case 1:
			table_approval.setVisibility(View.GONE);
			break;
		case 2:
			table_audit.setVisibility(View.GONE);
			right.setText("主任审批");
			break;
		}
	}

}
