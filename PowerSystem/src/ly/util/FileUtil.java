package ly.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;
/**
 * 
 * 文件操作通用类
 *
 */
public class FileUtil {

	public static String separator = File.separator;
	public static String IMAGEDIR = getRootDir() + separator + "leisurlife"+separator+"image";
	
	/**获取SDCard根目录*/
	public static File getRootDir(){
		if(SDCardExist()){
			return Environment.getExternalStorageDirectory();
		}else{
			return null;
		}
	}
	
	/**判断sdcard是否存在*/
	public static boolean SDCardExist(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	/**创建文件夹*/
	public static boolean createFile(String name){
		if(SDCardExist()){
			File file = new File(name);
			if(!file.exists()){
				return file.mkdirs();
			}else if(file.exists()){
				return true;
			}
		}
		return false;
	}
	
	/**保存图片到图片文件夹*/
	public static boolean saveFile(String name ,byte[] data) throws Exception{
		if(SDCardExist() && name != null && data != null && data.length > 0){
			ByteArrayInputStream inStream = new ByteArrayInputStream(data);
			File file = new File(IMAGEDIR,name+".life");
			FileOutputStream outStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = inStream.read(buffer)) > -1){
				outStream.write(buffer, 0, len);
			}
			inStream.close();
			outStream.close();		
			return true;
		}else{
			return false;
		}
	}
	
	/**判断图片是否存在*/
	public static boolean imageExist(String name){
		File file = new File(IMAGEDIR+separator+name+".life");
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}
}
