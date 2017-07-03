package ly.activity.group;

import java.util.ArrayList;

import ly.activity.TabThreeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 第三个Tab选项卡的Activity组
 * 用来管理每个Tab的页面
 * 
 * @author obeya
 * 
 */
public class TabThreeActivityGroup extends AbstractActivityGroup {
    
    public static TabThreeActivityGroup tabThreeGroup;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.viewHistory = new ArrayList<View>();
        
        View decorView = getLocalActivityManager().startActivity(
                "TabThreeActivity",
                new Intent(this, TabThreeActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
        
        viewHistory.add(decorView);  // 添加到activities列表中
        setContentView(decorView);
    }
    
}
