//package ly.activity.bug;
//
//import ly.activity.R;
//import ly.activity.R.array;
//import ly.activity.R.id;
//import ly.activity.R.layout;
//import ly.util.LogUtil;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//public class ChooseLineActivity extends Activity {
//	
//	private Spinner spinnerTown;
//	private Spinner spinnerLine;
//	private Button btnOk;
//	private Button btnCancel;
//	private String select="1";
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.choose_line);
//		
//		spinnerTown = (Spinner) findViewById(R.id.spinnerTown);
//		spinnerLine = (Spinner) findViewById(R.id.spinnerLine);
//		btnOk = (Button) findViewById(R.id.btnOk);
//		btnCancel = (Button) findViewById(R.id.btnCancel);
//		
//		final String[] directions = getResources().getStringArray(R.array.direction);
//		ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.direction,
//				android.R.layout.simple_spinner_item);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerTown.setAdapter(adapter);
//		
//		spinnerTown.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//				select=directions[position];
//				Toast.makeText(getApplicationContext(), "选中数据:"+directions[position], Toast.LENGTH_SHORT).show();
//			}
//			
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				
//			}
//		});
//		
//		btnOk.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View view) {
//				// 回传数据
//				Intent backIntent = new Intent();
//				//Intent backIntent = new Intent(getApplicationContext(),AddDefectsActivity.class);
//				Bundle reply = new Bundle();
//				reply.putString("lineName", select);
//				backIntent.putExtras(reply);
//				setResult(RESULT_CANCELED, backIntent);
//				//startActivity(backIntent);
//				LogUtil.d("ChooseLineActivity", "%%%% 回传值 %%%%>>"+select);
//				finish();
//			}
//		});
//		
//		btnCancel.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View view) {
//				finish();
//			}
//		});
//	}
//	
//	public void init() {
//		
//	}
//	
//	// public static void startAction(Context context) {
//	// Intent intent = new Intent(context, ChooseLineActivity.class);
//	// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	// context.startActivity(intent);
//	// }
//}
