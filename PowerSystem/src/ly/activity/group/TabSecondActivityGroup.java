package ly.activity.group;

import java.util.ArrayList;

import ly.activity.TabSecondActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 第二个Tab选项卡的Activity组
 * 用来管理每个Tab的页面
 * 
 * @author obeya
 * 
 */
public class TabSecondActivityGroup extends AbstractActivityGroup {
    
    public static TabSecondActivityGroup tabSecondGroup;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.viewHistory = new ArrayList<View>();
        
        View decorView = getLocalActivityManager().startActivity(
                "TabSecondActivity",
                new Intent(this, TabSecondActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
        
        viewHistory.add(decorView);  // 添加到activities列表中
        setContentView(decorView);
    }
    
}
