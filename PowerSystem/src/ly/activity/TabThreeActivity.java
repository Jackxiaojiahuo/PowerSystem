package ly.activity;

import java.util.ArrayList;
import java.util.List;

import ly.activity.transformer.TransApprove;
import ly.activity.transformer.TransAudit;
import ly.activity.transformer.Transformer;
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
 * 第三个Tab
 * @author obeya
 *
 */
public class TabThreeActivity extends Activity {
	private final String TAG = "TabThreeActivity";
    private ListView mListView;
    public static Handler mHandler;
    public static List<Menu> tabThirdMenus = null;
	private Integer index=null;//页面标识
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_three);
        mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				String str = bundle.getString("str");
				Log.i("3333>>>>>>>>>>>>>>>",str);
				tabThirdMenus=new ArrayList<Menu>();
				if(str.indexOf("变压器维修申报")>0){
					Menu tabFirstMenu1 = new Menu(R.drawable.item_menu, "变压器维修申报");
		        	tabThirdMenus.add(tabFirstMenu1);
				}
				if(str.indexOf("变压器维修专职审核")>0){
					Menu tabFirstMenu2 = new Menu(R.drawable.item_menu, "变压器维修专职审核");
		        	tabThirdMenus.add(tabFirstMenu2);
				}
				if(str.indexOf("变压器维修主任审批")>0){
					Menu tabFirstMenu3 = new Menu(R.drawable.item_menu, "变压器维修主任审批");
		        	tabThirdMenus.add(tabFirstMenu3);
				}
				Log.i("leftssss-----------","");
				init();
			}
        };
        Message msg = new Message();
		msg.what = 3;// 第三个tab页面
		ParseJasonDataService.handler.sendMessage(msg);
//		ScreenUtil.showProgressDialog(getApplicationContext());// 显示进度条
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
        mListView = (ListView) findViewById(R.id.list_view_three);
        
        MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(),
                R.layout.item_menu, TabThreeActivity.tabThirdMenus);
        mListView.setAdapter(menuAdapter);
        LogUtil.d(TAG, "%%%% 菜单初始化完成 %%%%");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                LogUtil.d(TAG, "%%%% 进入 "
                        + TabThreeActivity.tabThirdMenus.get(position)
                                .getName() + "页面 %%%%");
                Intent intent=null;
                index=returnIndex(position);
                switch (index) {//根据标识跳转页面
				case 0:
					LogUtil.d(TAG, "%%%%index:"+index+" 进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), Transformer.class);
					intent.putExtra("index", index);
					break;
				case 1:
					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), TransAudit.class);
					intent.putExtra("index", index);
					break;
				case 2:
					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), TransApprove.class);
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
    	if(TabThreeActivity.tabThirdMenus.get(position).getName().equals("变压器维修申报")){
        	return 0;
        }else if(TabThreeActivity.tabThirdMenus.get(position).getName().equals("变压器维修专职审核")){
        	return 1;
        }else if(TabThreeActivity.tabThirdMenus.get(position).getName().equals("变压器维修主任审批")){
        	return 2;
        }else{
        	return 0;
        }
    }
}
