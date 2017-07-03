package ly.activity.group;

import java.util.ArrayList;

import ly.activity.TabFirstActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 第一个Tab选项卡的Activity组
 * 用来管理每个Tab的页面
 * @author obeya
 * 
 */
public class TabFirstActivityGroup extends AbstractActivityGroup {
    
    public static TabFirstActivityGroup tabFirstGroup;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHistory = new ArrayList<View>();
        tabFirstGroup = this;
        View decorView = getLocalActivityManager().startActivity(
                "TabFirstActivity", new Intent(this, TabFirstActivity.class))
                .getDecorView();
        replaceContentView(decorView);
    }
    
}
