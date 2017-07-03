package ly.activity.fault;

import ly.activity.R;
import ly.data.DataManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
public class FaultAudit extends Activity {
	private int index;//页面标识
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fault_audit);
		index=getIntent().getIntExtra("index", 1);
		init();
	}

	// 初始化专制审核页面
	private void init() {
		DataManager.audit = new DataManager.Audit[2];
		DataManager.audit[0] = new DataManager.Audit();
		DataManager.audit[0].defectlevel = "1";
		DataManager.audit[0].linename = "10KV高园122线";
		DataManager.audit[0].devicename = "架空线路";
		DataManager.audit[1] = new DataManager.Audit();
		DataManager.audit[1].defectlevel = "2";
		DataManager.audit[1].linename = "10kV蔺农二114线";
		DataManager.audit[1].devicename = "金属氧化物避雷器";
		Log.i("数组长度", "" + DataManager.audit.length);
		addRow(index);
	}

	// 动态添加数据
	private void addRow(final int index) {

		TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout);
		tableLayout.setStretchAllColumns(true);
		TableRow tablerow = null;
		for (int i = 0; i < 2; i++) {
			tablerow = new TableRow(getApplicationContext());
			tablerow.setId(i);
			tablerow.setMinimumHeight(100);
			tablerow.setGravity(Gravity.CENTER);
			// if (i % 2 == 0)
			tablerow.setBackgroundColor(Color.rgb(255, 255, 255));
			// else
			// tablerow.setBackgroundColor(Color.rgb(231, 229, 220));
			/*
			 * 设置要显示的组件
			 */
			for (int j = 0; j < 3; j++) {
				TextView tv = new TextView(getApplicationContext());
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
				tablerow.addView(tv);
				tablerow.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(getApplicationContext(),
								"id:" + view.getId() + ",点击",
								Toast.LENGTH_SHORT).show();
						Intent in = null;
						in = new Intent(getApplicationContext(),
								FaultProcess.class);
						in.putExtra("index", index);
						startActivity(in);
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
