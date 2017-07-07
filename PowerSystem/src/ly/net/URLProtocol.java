package ly.net;

/**与服务器定义的接口协议*/
public class URLProtocol {

	/**服务器URL*/
//	public static String ROOT = "http://192.168.1.102:8080/LeisureLifeServer/dealcmd";
//	public static String ROOT = "http://192.168.0.181/ts_qxgl";
//	public static String ROOT = "http://192.168.8.211/ts_qxgl";
//	public static String ROOT = "http://192.168.199.211/ts_qxgl";
//	public static String ROOT = "http://192.168.1.110/ts_qxgl";
	public static String ROOT = "http://192.168.1.103/ts_qxgl";
	
	/**登录*/
	public static final int CMD_LOGIN = 0;
	/**登录验证*/
	public static final String LOGIN = "/login/login_in.jsp";
	/**主页面top信息*/
	public static final String top= "/login/top.jsp";
	/**左菜单*/
	public static final String left="/login/cd.jsp";
	public static final String upload="/qxlc/qxdl/pic_testdb.jsp";
	//---------------------缺陷处理------------------------------------------	
	/**获取图片地址*/
	public static final String img="/qxlc/qxys/lr_fj.jsp";
	/**缺陷登录列表*/
	public static final String qxlc_qxdl_lb="/qxlc/qxdl/lb.jsp";
	/**缺陷登录列表详细信息*/
	public static final String qxlc_qxdl_lr="/qxlc/qxdl/lr.jsp";
	/**缺陷登录(路线名称)*/
	public static final String qxlc_qxdl_xlmc="/qxlc/qxdl/xlmc.jsp";
	/**缺陷登录(录入缺陷内容)前保存部分数据*/
	public static final String qxlc_qxdl_lr_testdb="/qxlc/qxdl/testdb_qx.jsp";
	/**缺陷登录(录入缺陷内容)*/
	public static final String qxlc_qxdl_lr_qx="/qxlc/qxdl/lr_qx1.jsp";
	/**缺陷登录(保存缺陷内容)*/
	public static final String qxlc_qxdl_lr_qx_testdb="/qxlc/qxdl/lr_qx_testdb.jsp";
	/**缺陷登录(删除缺陷内容)*/
	public static final String qxlc_qxdl_sc_qx="/qxlc/qxdl/sc_qx1.jsp";
	/**缺陷登录保存*/
	public static final String qxlc_qxdl_lr_db="/qxlc/qxdl/testdb.jsp";
	/**缺陷登录删除*/
	public static final String qxlc_qxdl_lr_sc="/qxlc/qxdl/sc.jsp";
	/**缺陷登录提交*/
	public static final String qxlc_qxdl_lr_tj="/qxlc/qxdl/tj.jsp";
	
	
	/**专职审核列表*/
	public static final String qxlc_zzsh_lb = "/qxlc/zzsh/lb.jsp";
	/**查看缺陷信息*/
	public static final String qxlc_zzsh_lr_qxdl="/qxlc/zzsh/lr_qxdl.jsp";
	/**专职审核处理页面*/
	public static final String qxlc_zzsh_lr="/qxlc/zzsh/lr.jsp";
	
	
	/**主任审批列表*/
	public static final String qxlc_zrpz_lb = "/qxlc/zrpz/lb.jsp";
	/**查看专职审核*/
	public static final String qxlc_zrpz_lr_zzsh="/qxlc/zrpz/lr_zzsh.jsp";
	/**主任审批处理*/
	public static final String qxlc_zrpz_lr="/qxlc/zrpz/lr.jsp";
	
	/**缺陷处理列表*/
	public static final String qxlc_qxcl_lb = "/qxlc/qxcl/lb.jsp";
	/**查看主任审批*/
	public static final String qxlc_qxcl_lr_zrpz="/qxlc/qxcl/lr_zrpz.jsp";
	/**缺陷处理页面*/
	public static final String qxlc_qxcl_lr="/qxlc/qxcl/lr.jsp";
	/**录入发生材料*/
	public static final String qxlc_qxcl_lr_wz="/qxlc/qxcl/lr_wz.jsp";
	
	/**缺陷验收列表*/
	public static final String qxlc_qxys_lb = "/qxlc/qxys/lb.jsp";
	/**查看缺陷处理*/
	public static final String qxlc_qxys_lr_qxcl="/qxlc/qxys/lr_qxcl.jsp";
	/**缺陷验收页面*/
	public static final String qxlc_qxys_lr="/qxlc/qxys/lr.jsp";
	//--------------------------------------------------------------------
	//----------------------故障抢修---------------------------------------
	/**故障抢修填报*/
	public static final String gzqxlc_gzsq_lb="/gzqxlc/gzsq/lb.jsp";
	
	//----------------------变压器维修-----------------------------------------------
	
	/**获取电影详情*/
	public static final int CMD_MOVIEDETAIL = 102;
	
	/**获取演唱会列表*/
	public static final int CMD_CONCERT = 201;
	
	/**获取演唱会详情*/
	public static final int CMD_CONCERT_DETAIL = 202;
	
	/**获取美食列表*/
	public static final int CMD_DELICACIES = 301;
	
	/**获取美食详情*/
	public static final int CMD_DELICACIES_DETAIL = 302;
	
	/**获取展览列表*/
	public static final int CMD_DISPLAY = 401;
	
	/**获取展览详情*/
	public static final int CMD_DISPLAY_DETAIL = 402;
	
	/**获取音乐会列表*/
	public static final int CMD_MUSIC = 501;
	
	/**获取音乐会详情*/
	public static final int CMD_MUSIC_DETAIL = 502;
	
	/**获取京剧列表*/
	public static final int CMD_PEKINGOPERA = 601;
	
	/**获取京剧详情*/
	public static final int CMD_PEKINGOPERA_DETAIL = 602;
	
	/**获取话剧列表*/
	public static final int CMD_PLAY = 701;
	
	/**获取话剧详情*/
	public static final int CMD_PLAY_DETAIL = 702;
	
	/**获取即将上映电影列表*/
	public static final int CMD_MOVIE_WILL = 801;
	
	/**获取即将上映电影详情*/
	public static final int CMD_MOVIE_WILL_DETAIL = 802;
}
