package ly.activity;

import java.io.File;

import ly.net.ParseJasonDataService;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

public class BigPhotoActivity extends Activity {
	private ImageView bigphoto;
	private String imgname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigphoto);
		bigphoto=(ImageView) findViewById(R.id.bigphoto);
		Intent i=getIntent();
		if(i.getIntExtra("pos", 0)==1){
			bigphoto.setImageBitmap(ParseJasonDataService.qxdl_img1);
		}else{
			bigphoto.setImageBitmap(ParseJasonDataService.qxdl_img1);
		}
//		read();
	}
	private void read() {
		try {
			//如果手机插入了SD卡,并且应用程序具有访问SD的权限
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				 File file = new File(imgname);  
		            if (file.exists()) {  
		                Bitmap bm = BitmapFactory.decodeFile(imgname);  
		                // 将图片显示到ImageView中  
		                bigphoto.setImageBitmap(bm);  
		            }  
			}else{
				Toast.makeText(getApplicationContext(), "未插入SD卡或没有SD操作权限!",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
