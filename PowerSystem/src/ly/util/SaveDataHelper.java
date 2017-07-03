package ly.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
 * 数据库操作帮助类
 *
 */
public class SaveDataHelper extends SQLiteOpenHelper {

	private final static String NAME = "save";
	private final static int VERSION = 1;

	public SaveDataHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS save (saveid integer primary key autoincrement,content varchar(20),type integer,comment text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
