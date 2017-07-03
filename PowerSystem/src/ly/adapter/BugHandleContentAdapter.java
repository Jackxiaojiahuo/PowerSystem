package ly.adapter;

import java.util.List;

import ly.activity.R;
import ly.entity.DefectContentModel;
import ly.entity.HandleContentModel;
import ly.util.LogUtil;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 缺陷内容适配器
 * 
 * @author obeya
 * 
 */
public class BugHandleContentAdapter extends BaseAdapter {

	private final String TAG = "BugHandleContentAdapter";

	private Context context;
	private List<HandleContentModel> list;

	public BugHandleContentAdapter(Context context,
			List<HandleContentModel> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
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
			view = LayoutInflater.from(context).inflate(
					R.layout.bug_handlecontent_item, null);
			viewHolder = new ViewHolder();

			viewHolder.text1 = (TextView) view.findViewById(R.id.tv1);
			viewHolder.text2 = (TextView) view.findViewById(R.id.tv2);
			viewHolder.text3 = (TextView) view.findViewById(R.id.tv3);
			viewHolder.text4 = (TextView) view.findViewById(R.id.tv4);
			viewHolder.text5 = (TextView) view.findViewById(R.id.tv5);
			viewHolder.text6 = (TextView) view.findViewById(R.id.tv6);
			viewHolder.text7 = (TextView) view.findViewById(R.id.tv7);
			viewHolder.btn=((Button) view.findViewById(R.id.btn1));
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		LogUtil.d(TAG, "%%%% " + list.get(position).toString() + " %%%%");
		LogUtil.d(TAG, "%%%%>> " + list.get(position).getOne() + " <<%%%%");
		viewHolder.text1.setText((position+1) + "");
		viewHolder.text2.setText(list.get(position).getOne());
		viewHolder.text3.setText(list.get(position).getTwo());
		viewHolder.text4.setText(list.get(position).getThree());
		viewHolder.text5.setText(list.get(position).getFour());
		viewHolder.text6.setText(list.get(position).getFive());
		viewHolder.text7.setText(list.get(position).getSix());
		final int index=position;
		viewHolder.btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context, "你删除了"+(index+1), 1).show();
			}
		});
		if(position%2==0) view.setBackgroundColor(Color.rgb(255, 255, 255));
        else view.setBackgroundColor(Color.rgb(231, 229, 220));
		return view;
	}

	static class ViewHolder {
		TextView text1;
		TextView text2;
		TextView text3;
		TextView text4;
		TextView text5;
		TextView text6;
		TextView text7;
		Button btn;
	}
}
