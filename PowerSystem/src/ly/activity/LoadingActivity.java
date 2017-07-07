package ly.activity;

import ly.net.ParseJasonDataService;
import ly.util.FileUtil;
import ly.util.ScreenUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
/**
 * 
 * 加载界面
 *
 */
public class LoadingActivity extends Activity {

	private Intent intent;
	public static Handler mHandler;
//	private String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		ScreenUtil.showProgressDialog(this);
		Intent tent = new Intent(this, ParseJasonDataService.class);
		startService(tent);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i("lei", " LoadingActivity onPause");
		//ScreenUtil._progressDialog.dismiss();
		///ScreenUtil.hideLoading();
		super.onPause();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		setContentView(R.layout.loading_layout);
		// SDCard上创建程序文件夹
		if (FileUtil.createFile(FileUtil.IMAGEDIR)) {// 创建文件成功
					//正常只有run里面的代码，此线程是我后加的，不然loading页面不显示，延时5秒，才能看到到界面
					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							intent = new Intent(LoadingActivity.this,
									WebActivity.class);
							startActivity(intent);
							Log.i("lei", "非自动登录，跳转到登录界面 LoadingActivity.this.finish()");
//							ScreenUtil._progressDialog.dismiss();
							//ScreenUtil.hideLoading();
							LoadingActivity.this.finish();
						}
					}).start();
		} else {
			ScreenUtil.showMsg(LoadingActivity.this,
					getString(R.string.creat_file_fail));
			LoadingActivity.this.finish();
		}
	}
	
}
