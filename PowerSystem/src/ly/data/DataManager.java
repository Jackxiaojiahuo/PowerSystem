package ly.data;

import android.graphics.drawable.Drawable;


public class DataManager {

	public static String[] cities = {"北京","上海","深圳"};
	
	/**正在上映电影实体*/
	public static class Movie{
		public int mid;
		public String name;
		public String type;
		public String time;
		public String player;
		public String image;
		public String desc;
		public String timelong;
		public Drawable icon;
		
		@Override
		public String toString() {
			return "Movie [mid=" + mid + ", name=" + name + ", type=" + type
					+ ", time=" + time + ", player=" + player + ", image="
					+ image + ", desc=" + desc + ", timelong=" + timelong
					+ ", icon=" + icon + "]";
		}
		
	}
	public static Movie[] movies;//正在上映电影
	/**专职审核*/
	public static class Audit{
		public String defectlevel;//缺陷等级
		public String linename;//线路名称
		public String devicename;//发现人
		
		@Override
		public String toString() {
			return "Audit [defectlevel="
					+ defectlevel + ", linename=" + linename + ", devicename=" + devicename+"]";
		}
		
	}
	public static Audit[] audit;//即将上映电影
	
	
	/**展览实体*/
	public static class Display{
		public int did;
		public String name;
		public String addr;
		public String time;
		public String image;
		public String call;
		public String host;
		public String desc;
		public Drawable icon;
		
		@Override
		public String toString() {
			return "Display [did=" + did + ", name=" + name + ", addr=" + addr
					+ ", time=" + time + ", image=" + image + ", call=" + call
					+ ", host=" + host + ", desc=" + desc + ", icon=" + icon
					+ "]";
		}
		
	}
	public static Display[] displays;
	
	/**美食实体*/
	public static class Delicacies{
		public int did;
		public String label;
		public String name;
		public String addr;
		public int avg;
		public String image;
		public String call;
		public String mapx;
		public String mapy;
		public Drawable icon;
	}
	public static Delicacies[] delicacies;
	
	
	/**评论实体*/
	public static class Recommend{
		public String name;
		public String time;
		public String content;
		
		@Override
		public String toString() {
			return "Recommend [name=" + name + ", time=" + time + ", content="
					+ content + "]";
		}
	}
	public static Recommend[] recoms;
	
}
