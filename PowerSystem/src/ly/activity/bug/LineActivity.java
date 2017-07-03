package ly.activity.bug;

import java.util.ArrayList;
import java.util.Iterator;

import ly.activity.R;
import ly.adapter.TreeViewAdapter;
import ly.entity.TreeNode;
import ly.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LineActivity extends Activity implements OnItemClickListener {
	private ArrayList<TreeNode> allNodes;
	private ArrayList<TreeNode> topNodes;
	private TreeViewAdapter treeViewAdapter;
	private Intent intent;
	private String line;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bug_line);
		intent = getIntent();
		line = intent.getStringExtra("line");
		
		// tree显示
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		init();

		ListView treeview = (ListView) findViewById(R.id.line_list);
		treeViewAdapter = new TreeViewAdapter(topNodes, allNodes, inflater);
		// TreeViewItemClickListener treeViewItemClickListener = new
		// TreeViewItemClickListener(treeViewAdapter);
		treeview.setAdapter(treeViewAdapter);
		treeview.setOnItemClickListener(LineActivity.this);
	}

	private void init() {
		topNodes = new ArrayList<TreeNode>();
		allNodes = new ArrayList<TreeNode>();
		// 添加节点 -- 节点名称，节点level，节点id，父节点id，是否有子节点，是否展开

		// 添加最外层节点
		TreeNode node1 = new TreeNode("铜山供电公司-配件-线路(点击展开)", TreeNode.TOP_LEVEL,
				0, TreeNode.NO_PARENT, true, true);
		topNodes.add(node1);
		allNodes.add(node1);
		
		String t = line;
		t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
		Log.i("字符串>>>>>>>>>>>>>>>", t);
		t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		JSONObject jo = null;
		try {
			jo = new JSONObject(t);
			Log.i("线路名称>>>>>>>>>>>>>>>", jo.keys() + "");
			Iterator<String> sIterator = jo.keys();  
			int i=1;
			while(sIterator.hasNext()){  
			    // 获得key
				i++;
			    String key = sIterator.next();  
				TreeNode node=new TreeNode(key, TreeNode.TOP_LEVEL + 2, i, 0, true, false);
				topNodes.add(node);
				allNodes.add(node);
			    // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可  
			    JSONArray ja = jo.getJSONArray(key);  
			    for(int j=0;j<ja.length();j++){
			    	TreeNode node2=new TreeNode(ja.getString(j), TreeNode.TOP_LEVEL + 4, j+1, i, false, false);
			    	allNodes.add(node2);
			    }
			}  
		} catch (JSONException e) {
			e.printStackTrace();
		}

		

//		// 添加第二层节点
//		TreeNode node2 = new TreeNode("大庙变", TreeNode.TOP_LEVEL + 1, 1, node1
//				.getId(), true, false);
//		// 添加第二层节点
//		TreeNode node3 = new TreeNode("大许变", TreeNode.TOP_LEVEL + 1, 2, node1
//				.getId(), true, false);
//		// 添加第二层节点
//		TreeNode node4 = new TreeNode("房村变", TreeNode.TOP_LEVEL + 1, 4, node1
//				.getId(), true, false);
//		// 添加第二层节点
//		TreeNode node5 = new TreeNode("付庄变", TreeNode.TOP_LEVEL + 1, 5, node1
//				.getId(), true, false);
//		// 添加第二层节点
//		TreeNode node26 = new TreeNode("高营变", TreeNode.TOP_LEVEL + 1, 26, node1
//				.getId(), true, false);
//		// 添加第二层节点
//		TreeNode node27 = new TreeNode("汉王变", TreeNode.TOP_LEVEL + 1, 27, node1
//				.getId(), true, false);
//
//		// 添加第三层节点
//		TreeNode node6 = new TreeNode("10kV大阎115线1", TreeNode.TOP_LEVEL + 2, 6,
//				node2.getId(), false, false);
//		// 添加第三层节点
//		TreeNode node7 = new TreeNode("10kV大阎115线2", TreeNode.TOP_LEVEL + 2, 7,
//				node2.getId(), false, false);
//		// 添加第三层节点
//		TreeNode node8 = new TreeNode("10kV大阎115线", TreeNode.TOP_LEVEL + 2, 8,
//				node2.getId(), false, false);
//		// 添加第三层节点
//		TreeNode node9 = new TreeNode("10kV大马114线", TreeNode.TOP_LEVEL + 2, 9,
//				node2.getId(), false, false);
//		// 添加第三层节点
//		TreeNode node10 = new TreeNode("10kV大农118线", TreeNode.TOP_LEVEL + 2,
//				10, node2.getId(), false, false);
//		// 添加第三层节点
//		TreeNode node11 = new TreeNode("10KV许太一115线", TreeNode.TOP_LEVEL + 2,
//				11, node3.getId(), false, false);
//		// 添加第三层节点
//		TreeNode node12 = new TreeNode("10kV许太二112线", TreeNode.TOP_LEVEL + 2,
//				12, node3.getId(), false, false);
//		TreeNode node13 = new TreeNode("10kV徐庄114线", TreeNode.TOP_LEVEL + 2,
//				13, node3.getId(), false, false);
//		TreeNode node14 = new TreeNode("10kV铁南117线", TreeNode.TOP_LEVEL + 2,
//				14, node3.getId(), false, false);
//		TreeNode node15 = new TreeNode("10kV铁北111线", TreeNode.TOP_LEVEL + 2,
//				15, node3.getId(), false, false);
//		TreeNode node16 = new TreeNode("10kV机关113线", TreeNode.TOP_LEVEL + 2,
//				16, node3.getId(), false, false);
//		TreeNode node17 = new TreeNode("10kV房尚112线", TreeNode.TOP_LEVEL + 2,
//				17, node4.getId(), false, false);
//		TreeNode node18 = new TreeNode("10kV房刘113线", TreeNode.TOP_LEVEL + 2,
//				18, node4.getId(), false, false);
//		TreeNode node19 = new TreeNode("10kV房李111线", TreeNode.TOP_LEVEL + 2,
//				19, node4.getId(), false, false);
//		TreeNode node20 = new TreeNode("10kV房郭117线", TreeNode.TOP_LEVEL + 2,
//				20, node4.getId(), false, false);
//		TreeNode node21 = new TreeNode("10kV房豆115线", TreeNode.TOP_LEVEL + 2,
//				21, node4.getId(), false, false);
//		TreeNode node22 = new TreeNode("10kV付园二115线", TreeNode.TOP_LEVEL + 2,
//				22, node5.getId(), false, false);
//		TreeNode node23 = new TreeNode("10kV付园116线", TreeNode.TOP_LEVEL + 2,
//				23, node5.getId(), false, false);
//		TreeNode node24 = new TreeNode("10kV付马119线", TreeNode.TOP_LEVEL + 2,
//				24, node5.getId(), false, false);
//		TreeNode node25 = new TreeNode("10kV工业117线", TreeNode.TOP_LEVEL + 2,
//				25, node5.getId(), false, false);
//
//		// 添加初始树元素
//
//		topNodes.add(node1);
//		topNodes.add(node2);
//		topNodes.add(node3);
//		topNodes.add(node4);
//		topNodes.add(node5);
//		topNodes.add(node26);
//		topNodes.add(node27);
//
//		// topNodes.add(node8);
//		// topNodes.add(node9);
//		// topNodes.add(node10);
//		// topNodes.add(node11);
//		// topNodes.add(node12);
//		// topNodes.add(node13);
//		// topNodes.add(node14);
//		// topNodes.add(node15);
//		// topNodes.add(node16);
//		// topNodes.add(node17);
//		// topNodes.add(node18);
//		// topNodes.add(node19);
//		// topNodes.add(node20);
//		// topNodes.add(node21);
//		// topNodes.add(node22);
//		// topNodes.add(node23);
//		// topNodes.add(node24);
//		// topNodes.add(node25);
//
//		// 创建数据源
//		allNodes.add(node1);
//		allNodes.add(node2);
//		allNodes.add(node3);
//		allNodes.add(node4);
//		allNodes.add(node5);
//		allNodes.add(node6);
//		allNodes.add(node7);
//		allNodes.add(node8);
//		allNodes.add(node9);
//		allNodes.add(node10);
//		allNodes.add(node11);
//		allNodes.add(node12);
//		allNodes.add(node13);
//		allNodes.add(node14);
//		allNodes.add(node15);
//		allNodes.add(node16);
//		allNodes.add(node17);
//		allNodes.add(node18);
//		allNodes.add(node19);
//		allNodes.add(node20);
//		allNodes.add(node21);
//		allNodes.add(node22);
//		allNodes.add(node23);
//		allNodes.add(node24);
//		allNodes.add(node25);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {
		// 点击的item代表的元素
		TreeNode treeNode = (TreeNode) treeViewAdapter.getItem(position);
		// 树中顶层的元素
		ArrayList<TreeNode> topNodes = treeViewAdapter.getTopNodes();
		// 元素的数据源
		ArrayList<TreeNode> allNodes = treeViewAdapter.getAllNodes();

		// 点击没有子项的item直接返回
		if (!treeNode.isHasChildren()) {
			// 回传数据
			Intent backIntent = new Intent();
			// Intent backIntent = new
			// Intent(getApplicationContext(),AddDefectsActivity.class);
			Bundle reply = new Bundle();
			reply.putString("lineName", treeNode.getContentText());// 线路名称
			reply.putInt("lineId", treeNode.getId());// 线路id
			backIntent.putExtras(reply);
			setResult(RESULT_CANCELED, backIntent);
			// startActivity(backIntent);
			LogUtil.d("ChooseLineActivity", "%%%% 回传值 %%%%>>"
					+ treeNode.getContentText());
			finish();
		}

		if (treeNode.isExpanded()) {// 节点展开状态
			treeNode.setExpanded(false);// 设置节点不展开
			// 删除节点内部对应子节点数据，包括子节点的子节点...
			ArrayList<TreeNode> elementsToDel = new ArrayList<TreeNode>();
			for (int i = position + 1; i < topNodes.size(); i++) {
				if (treeNode.getLevel() >= topNodes.get(i).getLevel())
					break;
				elementsToDel.add(topNodes.get(i));
			}
			topNodes.removeAll(elementsToDel);
			treeViewAdapter.notifyDataSetChanged();
		} else {// 节点未展开状态
		// System.out.println(">>>>>>>>>>>未展开节点");
		// TreeNode t=null;
		// for(TreeNode tn:topNodes){
		// if(tn.getParendId()!=-1&&tn.isExpanded()==true&&tn.getId()!=treeNode.getId()){
		// tn.setExpanded(false);
		// t=tn;
		// System.out.println(">>>>>>>>size"+topNodes.size());
		// }
		// }
		// //删除节点内部对应子节点数据，包括子节点的子节点...
		// if(t!=null){
		// ArrayList<TreeNode> elementsToDel = new ArrayList<TreeNode>();
		// for (int i = 0; i < topNodes.size(); i++) {
		// System.out.println("tn等级:"+t.getLevel()+",topNodes等级:"+topNodes.get(i).getLevel());
		// if (t.getLevel() < topNodes.get(i).getLevel())
		// elementsToDel.add(topNodes.get(i));
		// }
		// System.out.println(elementsToDel.size()+"<<<<<<<<<<<<<<<<<<<");
		// topNodes.removeAll(elementsToDel);
		// }
		// System.out.println(">>>>>>>>>>>>>>>>>展开节点");

			treeNode.setExpanded(true);// 设置节点展开
			// 从数据源中提取子节点数据添加进树，注意这里只是添加了下一级子节点，为了简化逻辑
			int i = 1;// 注意这里的计数器放在for外面才能保证计数有效
			for (TreeNode e : allNodes) {
				if (e.getParendId() == treeNode.getId()) {
					e.setExpanded(false);
					topNodes.add(position + i, e);
					i++;
				}
			}
			treeViewAdapter.notifyDataSetChanged();
		}
	}
}
