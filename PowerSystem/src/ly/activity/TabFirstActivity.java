package ly.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ly.activity.bug.AuditActivity;
import ly.activity.bug.DefectsActivity;
import ly.adapter.MenuAdapter;
import ly.entity.Menu;
import ly.entity.TreeNode;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 第一个Tab
 * 
 * @author obeya
 * 
 */
public class TabFirstActivity extends Activity implements OnClickListener {
    
    private final String TAG = "TabFirstActivity";
    private ListView mListView;
    public static Handler mHandler;
    private Message msg;
    public static List<Menu> tabFirstMenus = null;
	private Integer index=null;//页面标识
	private Boolean flag=false;
	private String str="";
	private Intent intent=null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_first);
		init();
    }
    private void init(){
    	 //listview显示
        mListView = (ListView) findViewById(R.id.list_view_first);
        MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(),
                R.layout.item_menu, TabFirstActivity.tabFirstMenus);
        mListView.setAdapter(menuAdapter);
        LogUtil.d(TAG, "%%%% 菜单初始化完成 %%%%");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                LogUtil.d(TAG, "%%%% 进入 "
                        + TabFirstActivity.tabFirstMenus.get(position)
                                .getName() + "页面 %%%%");
                index=returnIndex(position);
                msg = new Message();
                switch (index) {//根据标识跳转页面
				case 0:
					LogUtil.d(TAG, "%%%%index:"+index+" 进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), DefectsActivity.class);
					intent.putExtra("index", index);
					msg.what = 11;// 缺陷登录
					break;
				case 1:
					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), AuditActivity.class);
					intent.putExtra("index", index);
					msg.what = 12;// 专职页面
					break;
				case 2:
					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), DefectsActivity.class);
					intent.putExtra("index", index);
					msg.what = 13;// 主任审批
					break;
				case 3:
					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), AuditActivity.class);
					intent.putExtra("index", index);
					msg.what = 14;// 缺陷处理页面
					break;
				default:
					LogUtil.d(TAG, "%%%%index:"+index+"  进入第"+position+"个activity>>id"+id+" %%%%");
					intent=new Intent(getApplicationContext(), AuditActivity.class);
					intent.putExtra("index", index);
					msg.what = 15;// 缺陷验收专职页面
					break;
				}
    			ParseJasonDataService.handler.sendMessage(msg);
                mHandler = new Handler() {
        			@Override
        			public void handleMessage(Message msg) {
        				Bundle bundle = msg.getData();
        				str = bundle.getString("str");
        				if (!"连接超时".equals(str) && str != null) {
        					if (!"".equals(str)) {
        						intent.putExtra("str", str);
        	        			startActivity(intent);
        					}
        				} else {
        					Log.i("网络无连接>>>>>>>>>>", str);
        					ScreenUtil.showMsg(TabFirstActivity.this, "网络无连接");// 提示错误
        				}
        			}
        		};
            }
        });
    }
    //绑定页面标识
    private int returnIndex(int position){
    	if(TabFirstActivity.tabFirstMenus.get(position).getName().equals("缺陷登录")){
        	return 0;
        }else if(TabFirstActivity.tabFirstMenus.get(position).getName().equals("专职审核")){
        	return 1;
        }else if(TabFirstActivity.tabFirstMenus.get(position).getName().equals("主任批准")){
        	return 2;
        }else if(TabFirstActivity.tabFirstMenus.get(position).getName().equals("缺陷处理")){
        	return 3;
        }else{
        	return 4;
        }
    }
    @Override
    public void onClick(View view) {
        
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LogUtil.d(TAG, "%%%% 按下了back键 onBackPressed() %%%%");
    }
    
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            LogUtil.d(TAG, "%%%% TabFirstActivity 按下了back键 onKeyDown() %%%%");
//            TabFirstActivityGroup.tabFirstGroup.back();
//            return true;
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
//    }
    // @Override
    // public void onClick(View v) {
    // switch (v.getId()) {
    // case R.id.first_back:
    // TabFirstActivityGroup.tabFirstGroup.back();
    //
    // break;
    // case R.id.first_next:
    //
    // Intent intent = new Intent();
    // intent.setClass(First.this, SecondActivity.class);
    //
    // View decorView = TabFirstActivityGroup.tabFirstGroup
    // .getLocalActivityManager()
    // .startActivity("SecondActivity", intent).getDecorView();
    // TabFirstActivityGroup.tabFirstGroup.replaceContentView(decorView);
    // break;
    //
    // default:
    // break;
    // }
    // }
   
}
