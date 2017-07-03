package ly.activity;

import java.util.ArrayList;
import java.util.List;

import ly.activity.fault.FaultApprove;
import ly.activity.fault.FaultAudit;
import ly.activity.fault.ReportActivity;
import ly.adapter.MenuAdapter;
import ly.entity.Menu;
import ly.net.ParseJasonDataService;
import ly.util.LogUtil;
import ly.util.ScreenUtil;
import ly.util.Constant.TabFirstMenuValue;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 第二个Tab
 * @author obeya
 *
 */
public class TabSecondActivity extends Activity {
	private final String TAG = "TabSecondActivity";
    private ListView mListView;
    public static Handler mHandler;
    public static List<Menu> tabSecondMenus = null;
	
	private Integer index=null;//页面标识
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_second);
        mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				String str = bundle.getString("str");
				Log.i("2222>>>>>>>>>>>>>>>",str);
				tabSecondMenus=new ArrayList<Menu>();
				if(str.indexOf("故障抢修填报")>0){
		        	Menu tabFirstMenu1 = new Menu(R.drawable.item_menu, "故障抢修填报");
		        	tabSecondMenus.add(tabFirstMenu1);
				}
				if(str.indexOf("故障抢修专职审核")>0){
					Menu tabFirstMenu2 = new Menu(R.drawable.item_menu, "故障抢修专职审核");
		        	tabSecondMenus.add(tabFirstMenu2);
				}
				if(str.indexOf("故障抢修主任审批")>0){
					Menu tabFirstMenu3 = new Menu(R.drawable.item_menu, "故障抢修主任审批");
		        	tabSecondMenus.add(tabFirstMenu3);
				}
				Log.i("leftssss-----------","");
				init();
			}
		};
		Message msg = new Message();
		msg.what = 2;// 第二个tab页面
		ParseJasonDataService.handler.sendMessage(msg);
//		ScreenUtil.showProgressDialog(TabSecondActivity.this);// 显示进度条
//		 new Thread(new Runnable() {
//				public void run() {
//					try {
//						Thread.sleep(2000);
//						
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
    }
    private void init(){
    	  //listview显示
        mListView = (ListView) findViewById(R.id.list_view_two);
        
        MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(),
                R.layout.item_menu, TabSecondActivity.tabSecondMenus);
        mListView.setAdapter(menuAdapter);
        LogUtil.d(TAG, "%%%% 菜单初始化完成 %%%%");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                LogUtil.d(TAG, "%%%% 进入 "
                        + TabSecondActivity.tabSecondMenus.get(position)
                                .getName() + "页面 %%%%");
                Intent intent=null;
                index=returnIndex(position);
                switch (index) {//根据标识跳转页面
				case 0:
					LogUtil.d(TAG, "%%%%index:"+index+" 进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), ReportActivity.class);
					intent.putExtra("index", index);
					break;
				case 1:
					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), FaultAudit.class);
					intent.putExtra("index", index);
					break;
				case 2:
					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), FaultApprove.class);
					intent.putExtra("index", index);
					break;
//				case 3:
//					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
//					intent=new Intent(getApplicationContext(), AuditActivity.class);
//					intent.putExtra("index", index);
//					break;
//				default:
//					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
//					intent=new Intent(getApplicationContext(), AuditActivity.class);
//					intent.putExtra("index", index);
//					break;
				}
                startActivity(intent);
            }
        });
    }
  //绑定页面标识
    private int returnIndex(int position){
    	if(TabSecondActivity.tabSecondMenus.get(position).getName().equals("故障抢修申报")){
        	return 0;
        }else if(TabSecondActivity.tabSecondMenus.get(position).getName().equals("故障抢修专职审核")){
        	return 1;
        }else if(TabSecondActivity.tabSecondMenus.get(position).getName().equals("故障抢修主任审批")){
        	return 2;
//        }else if(TabSecondActivity.tabSecondMenus.get(position).getName().equals("领导阅办")){
//        	return 3;
        }else{
        	return 0;
        }
    }
}
