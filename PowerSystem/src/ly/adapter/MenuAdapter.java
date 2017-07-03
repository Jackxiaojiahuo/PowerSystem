package ly.adapter;

import java.util.List;

import ly.activity.R;
import ly.entity.Menu;
import ly.util.LogUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 功能菜单适配器
 * 
 * @author obeya
 * 
 */
public class MenuAdapter extends BaseAdapter {
    
    private final String TAG = "MenuAdapter";
    
    private Context context;
    private int resourceId;
    private List<Menu> menus;
    
    public MenuAdapter(Context context, int resourceId, List<Menu> menus) {
        super();
        this.context = context;
        this.resourceId = resourceId;
        this.menus = menus;
    }
    
    @Override
    public int getCount() {
        return menus.size();
    }
    
    @Override
    public Object getItem(int position) {
        return position;
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            
            viewHolder.image = (ImageView) view.findViewById(R.id.menu_image);
            viewHolder.text = (TextView) view.findViewById(R.id.menu_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        LogUtil.d(TAG, "%%%% " + menus.get(position).toString() + " %%%%");
        viewHolder.image.setImageResource(menus.get(position).getImage());
        viewHolder.text.setText(menus.get(position).getName());
        return view;
    }
    
    class ViewHolder {
        ImageView image;
        TextView text;
        
    }
    
}
