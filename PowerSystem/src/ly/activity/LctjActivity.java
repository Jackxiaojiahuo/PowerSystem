package ly.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.activity.bug.DefectsActivity;
import ly.net.HttpConnection;
import ly.net.URLProtocol;
import ly.util.ScreenUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class LctjActivity extends Activity {
	private Spinner sp;
	private Button save;
	Intent i;
	String select;
	String str;
	String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bug_lctj);
		sp=(Spinner)findViewById(R.id.sp);
		save=(Button)findViewById(R.id.save);
		i=getIntent();
		id=i.getStringExtra("id");
		Map<String,String> map=new HashMap<String, String>();
		map.put("id", id);
		str=HttpConnection.httpPost(URLProtocol.ROOT
				+ URLProtocol.qxlc_qxdl_lr_tj,map,0);
		if(!"连接超时".equals(str)){
			String t = str;
			t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
			Log.i("字符串ID>>>>>>>>>>>>>>>", t);
			t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
			JSONObject json = null;
			try {
				json = new JSONObject(t);
				JSONArray ja = json.getJSONArray("list");
				final List<String> list=new ArrayList<String>();
				for(int i=0;i<ja.length();i++){
					list.add(ja.getString(i));
				}
				ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_spinner,
						R.id.text, list);
				sp.setAdapter(spinner1Adapter);
				select=list.get(0);
				sp.setSelection(0, true);
				sp.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						select=list.get(pos);
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
			}catch(Exception e){
				e.printStackTrace();
			}
		 } else {
				Log.i("网络无连接>>>>>>>>>>", str);
				ScreenUtil.showMsg(LctjActivity.this, "网络无连接");// 提示错误
		}
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Map<String,String> map=new HashMap<String, String>();
				map.put("id", id);
				map.put("lctjr", select);
				map.put("tjbs", "1");
				str=HttpConnection.httpPost(URLProtocol.ROOT
						+ URLProtocol.qxlc_qxdl_lr_tj,map,0);
				if(!"连接超时".equals(str)){
					back();
				 } else {
						Log.i("网络无连接>>>>>>>>>>", str);
						ScreenUtil.showMsg(LctjActivity.this, "网络无连接");// 提示错误
				}
			}
		});
	}
	public void back(){
			Intent in=new Intent(LctjActivity.this,DefectsActivity.class);
		 in.putExtra("index", 0);
		 String info = HttpConnection.httpPost(URLProtocol.ROOT
					+ URLProtocol.qxlc_qxdl_lb,null,0);// 获取连接返回值;
		 if(!"连接超时".equals(info)){
			 in.putExtra("str", info);
			 startActivity(in);
			 LctjActivity.this.finish();
		 } else {
				Log.i("网络无连接>>>>>>>>>>", info);
				ScreenUtil.showMsg(LctjActivity.this, "网络无连接");// 提示错误
		}
	}
}
