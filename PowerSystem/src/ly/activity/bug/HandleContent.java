package ly.activity.bug;

import java.util.ArrayList;
import java.util.List;

import ly.activity.R;
import ly.adapter.BugHandleContentAdapter;
import ly.entity.HandleContentModel;
import ly.util.Utility;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;

public class HandleContent extends Activity {
	private ListView lv;
	private List<HandleContentModel> list;
	private HandleContentModel hcm;
	private ScrollView sv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bug_handlecontent);
		lv=(ListView) findViewById(R.id.list);
		list=new ArrayList<HandleContentModel>();
		for(int i=1;i<4;i++){
			hcm=new HandleContentModel();
			hcm.setOne("400kVA变压器"+i);
			hcm.setTwo("1kVA电缆终端"+i);
			hcm.setThree("4*240,户外"+i);
			hcm.setFour("500219");
			hcm.setFive("1kV电缆终端,4×240,户外终端,热缩,铜"+i);
			hcm.setSix("1(套)");
			list.add(hcm);
		}
		BugHandleContentAdapter adapter=new BugHandleContentAdapter(getApplicationContext(), list);
		lv.setAdapter(adapter);
		new Utility().setListViewHeightBasedOnChildren(lv);
		sv = (ScrollView) findViewById(R.id.sv);
		sv.smoothScrollTo(0, 0);
	}
}
