package ly.activity.transformer;

import ly.activity.R;
import ly.activity.R.id;
import ly.activity.R.layout;
import ly.activity.bug.DefectsActivity;
import ly.data.DataManager;
import ly.util.LogUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
public class Transformer extends Activity {

	private final String TAG = "Transformer";
	private Button addReport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		if(intent.getIntExtra("index", 0)==0){
			setContentView(R.layout.trans_report);
			initDefect();
		}
	}
	//初缺陷登录页面
	private void initDefect(){
		addReport = (Button) findViewById(R.id.addReport);
		DataManager.audit=new DataManager.Audit[2];
		DataManager.audit[0]=new DataManager.Audit();
		DataManager.audit[0].defectlevel="1";
		DataManager.audit[0].linename="更换";
//		DataManager.audit[0].devicename="架空线路";
		DataManager.audit[1]=new DataManager.Audit();
		DataManager.audit[1].defectlevel="2";
		DataManager.audit[1].linename="迁移";
//		DataManager.audit[1].devicename="金属氧化物避雷器";
		Log.i("数组长度",""+DataManager.audit.length);
		addRow(0);
		LogUtil.d(TAG, "%%%% Transformer 初始化完成 %%%%");
		addReport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtil.d(TAG, "%%%% 进入 新增变压器维修 页面 %%%%");
				//AddDefectsActivity.startAction(DefectsActivity.this);
				Intent intent=new Intent(getApplicationContext(),AddTranReport.class);
				startActivityForResult(intent, 1);//第一次跳页面
			}
		});
	}
	
	//动态添加数据
	private void addRow(final int index) {

		TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout);
		tableLayout.setStretchAllColumns(true); 
		TableRow tablerow=null;
		for (int i = 0; i < 2; i++) {
			tablerow = new TableRow(Transformer.this);
			tablerow.setId(i);
			tablerow.setMinimumHeight(100);
			tablerow.setGravity(Gravity.CENTER);
//			if (i % 2 == 0)
				tablerow.setBackgroundColor(Color.rgb(255, 255, 255));
//			else
//				tablerow.setBackgroundColor(Color.rgb(231, 229, 220));
			/*
			 * 设置要显示的组件
			 */
			for (int j = 0; j < 2; j++) {
				TextView tv = new TextView(Transformer.this);
				switch (j) {
				case 0:
					tv.setText(DataManager.audit[i].defectlevel);
					break;
				case 1:
					tv.setText(DataManager.audit[i].linename);
					break;
				case 2:
					tv.setText(DataManager.audit[i].devicename);
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
						Intent in=null;
						if(index==0){//缺陷登录跳转到添加并绑定数据
							in = new Intent(getApplicationContext(),
									AddTranReport.class);
						}else{//主任审批跳转到流程处理
//							in = new Intent(getApplicationContext(),
//									Audit_process.class);
						}
						in.putExtra("index", index);
						startActivity(in);
					}
				});
				   tablerow.setOnLongClickListener(new OnLongClickListener() {
						
						@Override
						public boolean onLongClick(View arg0) {
							final String[] arrayFruit = new String[] { "删除", "流程提交" };
							Dialog alertDialog = new AlertDialog.Builder(Transformer.this).
						    setTitle("请选择你的操作？").
						    setIcon(android.R.drawable.btn_dialog)
						    .setItems(arrayFruit, new DialogInterface.OnClickListener() {
						     @Override
						     public void onClick(DialogInterface dialog, int which) {
						    	 //addDefects.setText("新增"+arrayFruit[which]);
						    	 Toast.makeText(Transformer.this, arrayFruit[which], Toast.LENGTH_SHORT).show();
						     }
						    }).
						    setNegativeButton("取消", new DialogInterface.OnClickListener() {
						     @Override
						     public void onClick(DialogInterface dialog, int which) {
						    	 	
						     }
						    }).
						    create();
						  alertDialog.show();
							return true;
						}
					});
			}
			tableLayout.addView(tablerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			TableRow linerow=new TableRow(getApplicationContext());
			linerow.setBackgroundColor(Color.GRAY);
			linerow.setMinimumHeight(1);
			tableLayout.addView(linerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}

	}
}
