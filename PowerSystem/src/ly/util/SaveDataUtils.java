package ly.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 
 * 数据库操作通用类
 *
 */
public class SaveDataUtils {
	private Context mContext;

	public SaveDataUtils(Context context) {
		mContext = context;
	}

	public List<Map<String, Object>> readData() {
		SaveDataHelper helper = new SaveDataHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Cursor cursor = db.rawQuery("select * from save", null);
		while (cursor.moveToNext()) {
			String content = cursor.getString(1);
			int type = cursor.getInt(2);
			String comment = cursor.getString(3);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", content);
			map.put("type", type);
			map.put("comment", comment);
			list.add(map);
		}
		cursor.close();
		db.close();
		return list;
	}

	public void saveData(Map<String, Object> map) {
		SaveDataHelper helper = new SaveDataHelper(mContext);
		SQLiteDatabase db = helper.getWritableDatabase();
		String content = (String) map.get("content");
		int type = (Integer) map.get("type");
		String comment = (String) map.get("comment");
		db.execSQL("insert into save(content,type,comment) values(?,?,?)",
				new Object[] { content, type, comment });
		db.close();
	}

}
