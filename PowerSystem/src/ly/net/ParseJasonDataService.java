package ly.net;

import java.util.HashMap;
import java.util.Map;

import ly.activity.LoginActivity;
import ly.activity.TabFirstActivity;
import ly.activity.TabSecondActivity;
import ly.activity.TabThreeActivity;
import ly.activity.bug.AddDefectsActivity;
import ly.activity.bug.AuditActivity;
import ly.activity.bug.DefectContent;
import ly.activity.bug.DefectsActivity;
import ly.activity.fault.ReportActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * 
 * 后台服务
 * 
 */
public class ParseJasonDataService extends Service {

	public static Handler handler;
	public static final int CODE_OK = 0;
	public static final int COLLECTION = 500;
	public static final String HTTP_CMD = "cmd";// 功能模块
	public static final String HTTP_CODE = "code"; // 是否成功
	private String str = "";
	private String str1 = "";
	private String str2 = "";
	private String str3 = "";
	private String str4 = "";
	public static Bitmap qxdl_img1;
	public static Bitmap audit_img1;
	public static Bitmap zrpz_img1;
	public static Bitmap qxcl_img1;
	public static Bitmap qxys_img1;
	public static Bitmap qxys_img2;
	public static Map<String,String> param;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onStart(Intent intent, int startId) {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				// 登录功能
				case 1: {
					Message m = new Message();
					Bundle result = new Bundle();
					m.what = msg.what;
					String re="1";
					Bundle bundle = msg.getData();
					String name = bundle.getString("name");
					String pw = bundle.getString("pw");
					Map<String, String> map = new HashMap<String, String>();
					map.put("yhm", name);
					map.put("pas", pw);
					str = getStr(map,URLProtocol.LOGIN,0);// 获取连接返回值;
					if("连接超时".equals(str)){
						re="0";
						result.putString("re",re);
						LoginActivity.mHandler.sendMessage(m);
						break;
					}
					result.putString("str", str);
					String str1 = getStr(null, URLProtocol.top,0);// 获取连接返回值;
					if("连接超时".equals(str1)){
						re="0";
						result.putString("re",re);
						LoginActivity.mHandler.sendMessage(m);
						break;
					}
					result.putString("str1", str1);
					Map<String, String> map1 = new HashMap<String, String>();
					map.put("cdbm", "001");
					String str2 = getStr(map1,URLProtocol.left,0);// 获取连接返回值;
					if("连接超时".equals(str2)){
						re="0";
						result.putString("re",re);
						LoginActivity.mHandler.sendMessage(m);
						break;
					}
					result.putString("str2", str2);
					m.setData(result);
					LoginActivity.mHandler.sendMessage(m);
				}
					break;
				case 11:// 缺陷登录
				{
					str = getStr(null,URLProtocol.qxlc_qxdl_lb,0);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					TabFirstActivity.mHandler.sendMessage(m);
				}
					break;
				case 111:// 缺陷登录上传图片
				{
					Bundle bundle = msg.getData();
					String img = bundle.getString("img");
					Map<String, String> map = new HashMap<String, String>();
					map.put("img", img);
					str = HttpConnection.httpPost(URLProtocol.ROOT
							+ URLProtocol.upload, map, 1);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Log.i("上传图片>>>>>>>>", "成功");
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					// DefectsActivity.mHandler.sendMessage(m);
				}
					break;
				case 112:// 缺陷登录点击列表详细
				{
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					String re="1";
					Bundle bundle = msg.getData();
					String id = bundle.getString("id");
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					str = getStr(map,URLProtocol.qxlc_qxdl_lr,0);// 获取连接返回值;
					if("连接超时".equals(str)){
						re="0";
						result.putString("re",re);
						DefectsActivity.mHandler.sendMessage(m);
						break;
					}
					str1=getStr(map,URLProtocol.qxlc_qxdl_xlmc,0);
					if("连接超时".equals(str1)){
						re="0";
						result.putString("re",re);
						DefectsActivity.mHandler.sendMessage(m);
						break;
					}
					//----获取图片-----------
					String s="";
					String t = str;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					JSONObject json = null;
					try {
						json = new JSONObject(t);
						s = json.getString("qxbh");
					}catch(Exception e){
						e.printStackTrace();
					}
					map = new HashMap<String, String>();
					map.put("xqxmmc", s);
					s=getStr(map,URLProtocol.img,0);
					if("连接超时".equals(s)){
						re="0";
						result.putString("re",re);
						DefectsActivity.mHandler.sendMessage(m);
						break;
					}
					t = s;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					try {
						json = new JSONObject(t);
						s = json.getString("qxclqtp");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(!"".equals(s)){
						s=s.substring(s.indexOf("/upload"));
						Log.i("img-------------------------------------->",s);
						byte[] byteArray=HttpConnection.getImg(URLProtocol.ROOT+s);
						result.putByteArray("img1", byteArray);
					}
					//----------------------------
					result.putString("str", str);
					result.putString("str1", str1);
					m.setData(result);
					DefectsActivity.mHandler.sendMessage(m);
				}
					break;
				case 1121:// 缺陷登录点击列表详细
				{
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					String re="1";
					Bundle bundle = msg.getData();
					String id = bundle.getString("id");
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					str = getStr(map,URLProtocol.qxlc_qxdl_lr,0);// 获取连接返回值;
					if("连接超时".equals(str)){
						re="0";
						result.putString("re",re);
						DefectContent.mHandler.sendMessage(m);
						break;
					}
					str1=getStr(map,URLProtocol.qxlc_qxdl_xlmc,0);
					if("连接超时".equals(str1)){
						re="0";
						result.putString("re",re);
						DefectContent.mHandler.sendMessage(m);
						break;
					}
					//----获取图片-----------
					String s="";
					String t = str;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					JSONObject json = null;
					try {
						json = new JSONObject(t);
						s = json.getString("qxbh");
					}catch(Exception e){
						e.printStackTrace();
					}
					map = new HashMap<String, String>();
					map.put("xqxmmc", s);
					s=getStr(map,URLProtocol.img,0);
					if("连接超时".equals(s)){
						re="0";
						result.putString("re",re);
						DefectContent.mHandler.sendMessage(m);
						break;
					}
					t = s;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					try {
						json = new JSONObject(t);
						s = json.getString("qxclqtp");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(!"".equals(s)){
						s=s.substring(s.indexOf("/upload"));
						Log.i("img-------------------------------------->",s);
						byte[] byteArray=HttpConnection.getImg(URLProtocol.ROOT+s);
						result.putByteArray("img1", byteArray);
					}
					//----------------------------
					result.putString("str", str);
					result.putString("str1", str1);
					m.setData(result);
					DefectContent.mHandler.sendMessage(m);
				}
				break;
				case 113:// 点击添加按钮获取数据
				{
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					String re="1";
					str = getStr(null,URLProtocol.qxlc_qxdl_lr,0);
					if("连接超时".equals(str)){
						re="0";
						result.putString("re",re);
						DefectsActivity.mHandler.sendMessage(m);
						break;
					}
					str1=getStr(null,URLProtocol.qxlc_qxdl_xlmc,0);
					result.putString("str", str);
					result.putString("str1", str1);
					m.setData(result);
					DefectsActivity.mHandler.sendMessage(m);
				}
				break;
//				case 1131:// 点击添加按钮获取数据
//				{
//					Message m = new Message();
//					m.what = msg.what;
//					Bundle result = new Bundle();
//					String re="1";
//					str = getStr(null,URLProtocol.qxlc_qxdl_lr,0);
//					if("连接超时".equals(str)){
//						re="0";
//						result.putString("re",re);
//						DefectContent.mHandler.sendMessage(m);
//						break;
//					}
//					str1=getStr(null,URLProtocol.qxlc_qxdl_xlmc,0);
//					result.putString("str", str);
//					result.putString("str1", str1);
//					m.setData(result);
//					DefectContent.mHandler.sendMessage(m);
//				}
//				break;
//				case 114:// 获取缺陷内容
//				{
//					str = getStr(DefectContent.map,URLProtocol.qxlc_qxdl_lr_qx,0);
//					Message m = new Message();
//					m.what = msg.what;
//					Bundle result = new Bundle();
//					result.putString("str", str);
//					m.setData(result);
//					DefectContent.mHandler.sendMessage(m);
//				}
//				break;
//				case 1141:// 获取缺陷内容
//				{
//					str = getStr(DefectContent.map,URLProtocol.qxlc_qxdl_lr_qx,0);
//					Message m = new Message();
//					m.what = msg.what;
//					Bundle result = new Bundle();
//					result.putString("str", str);
//					m.setData(result);
//					DefectContent.mHandler.sendMessage(m);
//				}
//				break;
				case 118:// 录入缺陷内容前保存数据
				{
					str = getStr(AddDefectsActivity.map,URLProtocol.qxlc_qxdl_lr_testdb,0);
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					AddDefectsActivity.mHandler.sendMessage(m);
				}
				break;
				case 119:// 保存数据
				{
					str=getStr(null,URLProtocol.qxlc_qxdl_xlmc,0);
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					AddDefectsActivity.mHandler.sendMessage(m);
				}
				break;
				case 12:// 专职审核
				{
					str = getStr(null,URLProtocol.qxlc_zzsh_lb,0);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					TabFirstActivity.mHandler.sendMessage(m);
				}
					break;
				case 121:// 专职审核点击列表详细
				{
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					String re="1";
					Bundle bundle = msg.getData();
					String id = bundle.getString("id");
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					//专职审核查看缺陷登录信息
					str = getStr(map,URLProtocol.qxlc_zzsh_lr_qxdl,0);// 获取连接返回值;
					if("连接超时".equals(str)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//专制审核右边页面数据
					str1=getStr(map,URLProtocol.qxlc_zzsh_lr,0);
					if("连接超时".equals(str1)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//----获取图片-----------
					String s="";
					String t = str;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					JSONObject json = null;
					try {
						json = new JSONObject(t);
						s=json.getString("qxbh");
					}catch(Exception e){
						e.printStackTrace();
					}
					map = new HashMap<String, String>();
					map.put("xqxmmc", s);
					s=getStr(map,URLProtocol.img,0);
					if("连接超时".equals(s)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					
					t = s;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					try {
						json = new JSONObject(t);
						s = json.getString("qxclqtp");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(!"".equals(s)){
						s=s.substring(s.indexOf("/upload"));
						Log.i("img-------------------------------------->",s);
						byte[] byteArray=HttpConnection.getImg(URLProtocol.ROOT+s);
						Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
						audit_img1=bitmap;
					}
					//----------------------------
					result.putString("str", str);
					result.putString("str1", str1);
					m.setData(result);
					AuditActivity.mHandler.sendMessage(m);
				}
					break;
				case 13:// 主任审批
				{
					str = getStr(null,URLProtocol.qxlc_zrpz_lb,0);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					TabFirstActivity.mHandler.sendMessage(m);
				}
					break;
				case 131:// 主任批准查看专职审核信息
				{
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					String re="1";
					Bundle bundle = msg.getData();
					String id = bundle.getString("id");
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					//专职审核查看缺陷登录信息
					str = getStr(map,URLProtocol.qxlc_zzsh_lr_qxdl,0);// 获取连接返回值;
					if("连接超时".equals(str)){
						re="0";
						result.putString("re",re);
						DefectsActivity.mHandler.sendMessage(m);
						break;
					}
					//专制审核左边页面数据
					str1=getStr(map,URLProtocol.qxlc_zrpz_lr_zzsh,0);
					if("连接超时".equals(str1)){
						re="0";
						result.putString("re",re);
						DefectsActivity.mHandler.sendMessage(m);
						break;
					}
					//主任审批右边信息
					str2=getStr(map,URLProtocol.qxlc_zrpz_lr,0);
					if("连接超时".equals(str2)){
						re="0";
						result.putString("re",re);
						DefectsActivity.mHandler.sendMessage(m);
						break;
					}
					//----获取图片-----------
					String s="";
					String t = str;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					JSONObject json = null;
					try {
						json = new JSONObject(t);
						s=json.getString("qxbh");
					}catch(Exception e){
						e.printStackTrace();
					}
					map = new HashMap<String, String>();
					map.put("xqxmmc", s);
					s=getStr(map,URLProtocol.img,0);
					if("连接超时".equals(s)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					
					t = s;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					try {
						json = new JSONObject(t);
						s = json.getString("qxclqtp");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(!"".equals(s)){
						s=s.substring(s.indexOf("/upload"));
						Log.i("img-------------------------------------->",s);
						byte[] byteArray=HttpConnection.getImg(URLProtocol.ROOT+s);
						Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
						zrpz_img1=bitmap;
					}
					//----------------------------
					result.putString("str", str);
					result.putString("str1", str1);
					result.putString("str2", str2);
					m.setData(result);
					DefectsActivity.mHandler.sendMessage(m);
				}
					break;
				case 14:// 缺陷处理
				{
					str = getStr(null,URLProtocol.qxlc_qxcl_lb,0);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					TabFirstActivity.mHandler.sendMessage(m);
				}
					break;
				case 141:// 缺陷处理查看主任批准信息
				{
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					String re="1";
					Bundle bundle = msg.getData();
					String id = bundle.getString("id");
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					//专职审核查看缺陷登录信息
					str = getStr(map,URLProtocol.qxlc_zzsh_lr_qxdl,0);// 获取连接返回值;
					if("连接超时".equals(str)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//专制审核左边页面数据
					str1=getStr(map,URLProtocol.qxlc_zrpz_lr_zzsh,0);
					if("连接超时".equals(str1)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//主任审批左边信息
					str2=getStr(map,URLProtocol.qxlc_qxcl_lr_zrpz,0);
					if("连接超时".equals(str2)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//缺陷处理右边信息
					str3=getStr(map,URLProtocol.qxlc_qxcl_lr,0);
					if("连接超时".equals(str3)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//----获取图片-----------
					String s="";
					String t = str;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					JSONObject json = null;
					try {
						json = new JSONObject(t);
						s=json.getString("qxbh");
					}catch(Exception e){
						e.printStackTrace();
					}
					map = new HashMap<String, String>();
					map.put("xqxmmc", s);
					s=getStr(map,URLProtocol.img,0);
					if("连接超时".equals(s)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					
					t = s;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					try {
						json = new JSONObject(t);
						s = json.getString("qxclqtp");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(!"".equals(s)){
						s=s.substring(s.indexOf("/upload"));
						Log.i("img-------------------------------------->",s);
						byte[] byteArray=HttpConnection.getImg(URLProtocol.ROOT+s);
						Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
						qxcl_img1=bitmap;
					}
					//----------------------------
					result.putString("str", str);
					result.putString("str1", str1);
					result.putString("str2", str2);
					result.putString("str3", str3);
					m.setData(result);
					AuditActivity.mHandler.sendMessage(m);
				}
					break;
				case 15:// 缺陷验收
				{
					str = getStr(null,URLProtocol.qxlc_qxys_lb,0);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					TabFirstActivity.mHandler.sendMessage(m);
				}
					break;
				case 151:// 缺陷验收查看缺陷处理信息
				{
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					String re="1";
					Bundle bundle = msg.getData();
					String id = bundle.getString("id");
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					//专职审核查看缺陷登录信息
					str = getStr(map,URLProtocol.qxlc_zzsh_lr_qxdl,0);// 获取连接返回值;
					if("连接超时".equals(str)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//专制审核左边页面数据
					str1=getStr(map,URLProtocol.qxlc_zrpz_lr_zzsh,0);
					if("连接超时".equals(str1)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//主任审批左边信息
					str2=getStr(map,URLProtocol.qxlc_qxcl_lr_zrpz,0);
					if("连接超时".equals(str2)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//缺陷处理左边信息
					str3=getStr(map,URLProtocol.qxlc_qxys_lr_qxcl,0);
					if("连接超时".equals(str3)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//缺陷验收右边信息
					str4=getStr(map,URLProtocol.qxlc_qxys_lr,0);
					if("连接超时".equals(str4)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					//----获取图片-----------
					String s="";
					String t = str;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					JSONObject json = null;
					try {
						json = new JSONObject(t);
						s=json.getString("qxbh");
					}catch(Exception e){
						e.printStackTrace();
					}
					map = new HashMap<String, String>();
					map.put("xqxmmc", s);
					s=getStr(map,URLProtocol.img,0);
					if("连接超时".equals(s)){
						re="0";
						result.putString("re",re);
						AuditActivity.mHandler.sendMessage(m);
						break;
					}
					
					t = s;
					t = t.substring(t.indexOf("<sa>") + 4, t.lastIndexOf("</sa>"));
					t = t.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					String s1="";
					try {
						json = new JSONObject(t);
						s = json.getString("qxclqtp");
						s1 = json.getString("qxclhtp");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(!"".equals(s)){
						s=s.substring(s.indexOf("/upload"));
						Log.i("img-------------------------------------->",s);
						byte[] byteArray=HttpConnection.getImg(URLProtocol.ROOT+s);
						Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
						qxys_img1=bitmap;
					}
					if(!"".equals(s1)){
						s1=s1.substring(s1.indexOf("/upload"));
						Log.i("img-------------------------------------->",s1);
						byte[] byteArray=HttpConnection.getImg(URLProtocol.ROOT+s1);
						Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
						qxys_img2=bitmap;
					}
					//----------------------------
					result.putString("str", str);
					result.putString("str1", str1);
					result.putString("str2", str2);
					result.putString("str3", str3);
					result.putString("str4", str4);
					m.setData(result);
					AuditActivity.mHandler.sendMessage(m);
				}
					break;
					//----------------------------------------------------
				case 2: {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cdbm", "002");
					str = HttpConnection.httpPost(URLProtocol.ROOT
							+ URLProtocol.left, map, 0);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					TabSecondActivity.mHandler.sendMessage(m);
				}
					break;
				case 21: {
					str = HttpConnection.httpPost(URLProtocol.ROOT
							+ URLProtocol.gzqxlc_gzsq_lb, null, 0);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					ReportActivity.mHandler.sendMessage(m);
				}
					break;
				case 3: {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cdbm", "003");
					str = HttpConnection.httpPost(URLProtocol.ROOT
							+ URLProtocol.left, map, 0);// 获取连接返回值;
					Message m = new Message();
					m.what = msg.what;
					Bundle result = new Bundle();
					result.putString("str", str);
					m.setData(result);
					TabThreeActivity.mHandler.sendMessage(m);
				}
					break;

				case 4: {

				}
					break;
				// // 看电影
				// case URLProtocol.CMD_MOVIE: {
				// URLParam param = new URLParam(null);
				// Bundle bundle = doCmd(URLProtocol.CMD_MOVIE, param, null, 0);
				// Message m = new Message();
				// m.what = URLProtocol.CMD_MOVIE;
				// m.setData(bundle);
				// HomeActivity.mHandler.sendMessage(m);
				// }
				// break;
				}
			}
		};
	}

	public String getStr(Map<String, String> map, String url,int type) {
		return HttpConnection.httpPost(URLProtocol.ROOT
				+ url, map, type);
	}
	// public Bundle doCmd(int cmd, String cmd_name, URLParam p, Bundle input,
	// int position) {
	// Bundle result = new Bundle();
	// result.putInt(HTTP_CMD, cmd);
	// if (input != null) {
	// result.putAll(input);
	// }
	// URLParam param = new URLParam(p);//附加参数
	// String jsonStr = HttpConnection
	// .httpPost(URLProtocol.ROOT + cmd_name, param);//获取连接返回值
	// JSONObject json = null;
	// if (jsonStr == null) {//未连接服务器
	// result.putInt(HTTP_CODE, -2);
	// throw new Exception();
	// }
	// jsonStr = jsonStr.replaceAll("(\r\n|\r|\n|\n\r)", " ");
	// json = new JSONObject(jsonStr);
	// int code = json.getInt("code");
	// result.putInt(HTTP_CODE, code);
	// int returnCmd = json.getInt("cmd");
	// if (returnCmd != cmd) {
	// System.out.println("Data Error!!!");
	// }
	// result.putInt("cmd", cmd);
	// // System.out.println("code: " + code + " cmd: " + cmd);
	// // 向服务器发送请求成功
	// if (code == 0) {
	// switch (cmd) {
	// case URLProtocol.CMD_LOGIN:// 登录
	// {
	// int id = json.getInt("id");
	// String name = json.getString("name");
	// String role = json.getString("role");
	// String dept = json.getString("dept");
	// result.putInt("id", id);
	// result.putString("name", name);
	// result.putString("role", role);
	// result.putString("dept", dept);
	// }
	// break;
	// // case URLProtocol.CMD_MOVIE:// 看电影
	// // {
	// // JSONArray jrray = json.getJSONArray("list");
	// // int len = jrray.length();
	// // JSONObject jo;
	// // DataManager.movies = new DataManager.Movie[len];
	// // for (int i = 0; i < len; ++i) {
	// // jo = jrray.getJSONObject(i);
	// // DataManager.movies[i] = new DataManager.Movie();
	// // DataManager.movies[i].mid = jo.getInt("mid");
	// // DataManager.movies[i].name = jo.getString("name");
	// // DataManager.movies[i].type = jo.getString("type");
	// // DataManager.movies[i].time = jo.getString("time");
	// // DataManager.movies[i].player = jo.getString("player");
	// // DataManager.movies[i].image = jo.getString("image");
	// // Drawable img = null;
	// //
	// // if (FileUtil.imageExist(DataManager.movies[i].image)) {
	// // String path = FileUtil.IMAGEDIR
	// // + FileUtil.separator
	// // + DataManager.movies[i].image + ".life";
	// // img = BitmapDrawable.createFromPath(path);
	// // } else {
	// // try {
	// // img = HttpConnection
	// // .getNetimage(DataManager.movies[i].image);
	// //
	// // } catch (Exception e) {
	// // img = null;
	// // }
	// // }
	// // DataManager.movies[i].icon = img;
	// // }
	// // URLParam pam = new URLParam(p);
	// // doCmd(URLProtocol.CMD_MOVIE_WILL, pam, null, 0);
	// // }
	// // break;
	// // case URLProtocol.CMD_GET_REC:// 获取评论
	// // {
	// // JSONArray jrray = json.getJSONArray("list");
	// // int len = jrray.length();
	// // JSONObject jo;
	// // DataManager.recoms = new DataManager.Recommend[len];
	// // for (int i = 0; i < len; i++) {
	// // jo = jrray.getJSONObject(i);
	// // DataManager.recoms[i] = new DataManager.Recommend();
	// // DataManager.recoms[i].content = jo.getString("content");
	// // DataManager.recoms[i].time = jo.getString("time");
	// // DataManager.recoms[i].name = jo.getString("name");
	// // }
	// // }
	// // break;
	// }
	// }
	// return result;
	// }
}
