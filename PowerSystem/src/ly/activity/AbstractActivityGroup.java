package ly.activity;

import android.app.ActivityGroup;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;

import java.util.List;

/**
 * 利用ActivityGroup来管理activity,
 * 在每次跳转时将此view添加到一个list中
 * 当返回时就remove掉一个view
 */
@SuppressWarnings("deprecation")
public abstract class AbstractActivityGroup extends ActivityGroup {
    
    public List<View> viewHistory;
    
    public void replaceContentView(View view) {
        viewHistory.add(view);
        setContentView(view);
    }
    
    public void back() {
        if (!viewHistory.isEmpty()) {
            viewHistory.remove(viewHistory.get(viewHistory.size() - 1));
            if (viewHistory.isEmpty()) {
                finish();
                return;
            }
            setContentView(viewHistory.get(viewHistory.size() - 1));
        } else {
            finish();
        }
    }
    
    @Override
    public void onBackPressed() {
        back();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.DONUT
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
