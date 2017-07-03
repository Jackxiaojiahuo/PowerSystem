package ly.util;

import java.util.ArrayList;
import java.util.List;

import ly.activity.R;
import ly.activity.group.TabFirstActivityGroup;
import ly.activity.group.TabSecondActivityGroup;
import ly.activity.group.TabThreeActivityGroup;
import ly.entity.Menu;

/**
 * 常量工具类 <li>设置Tab选项卡的图标;设置Tab选项卡的文字;可添加Tab界面 <li>设置Menu菜单的内容
 * 
 * @author obeya
 * 
 */
public class Constant {
    
    /**
     * 设置菜单内容
     * 
     * @author obeya
     */
    public static final class TabFirstMenuValue {
        
        /**
         * Tab1菜单
         */
        public static List<Menu> tabFirstMenus = null;
        
        static {
            tabFirstMenus = new ArrayList<Menu>();
            Menu tabFirstMenu1 = new Menu(R.drawable.item_menu, "缺陷登录");
            tabFirstMenus.add(tabFirstMenu1);
            Menu tabFirstMenu2 = new Menu(R.drawable.item_menu, "专职审核");
            tabFirstMenus.add(tabFirstMenu2);
            Menu tabFirstMenu3 = new Menu(R.drawable.item_menu, "主任批准");
            tabFirstMenus.add(tabFirstMenu3);
            Menu tabFirstMenu4 = new Menu(R.drawable.item_menu, "缺陷处理");
            tabFirstMenus.add(tabFirstMenu4);
            Menu tabFirstMenu5 = new Menu(R.drawable.item_menu, "缺陷验收");
            tabFirstMenus.add(tabFirstMenu5);
        }
        /**
         * Tab2菜单
         */
        public static List<Menu> tabSecondMenus = null;
        
        static {
        	tabSecondMenus = new ArrayList<Menu>();
        	Menu tabFirstMenu1 = new Menu(R.drawable.item_menu, "故障抢修填报");
        	tabSecondMenus.add(tabFirstMenu1);
        	Menu tabFirstMenu2 = new Menu(R.drawable.item_menu, "故障抢修专职审核");
        	tabSecondMenus.add(tabFirstMenu2);
        	Menu tabFirstMenu3 = new Menu(R.drawable.item_menu, "故障抢修主任审批");
        	tabSecondMenus.add(tabFirstMenu3);
//        	Menu tabFirstMenu5 = new Menu(R.drawable.item_menu, "领导阅办");
//        	tabSecondMenus.add(tabFirstMenu5);
        }
        /**
         * Tab3菜单
         */
        public static List<Menu> tabThirdMenus = null;
        
        static {
        	tabThirdMenus = new ArrayList<Menu>();
        	Menu tabFirstMenu1 = new Menu(R.drawable.item_menu, "变压器维修申报");
        	tabThirdMenus.add(tabFirstMenu1);
        	Menu tabFirstMenu2 = new Menu(R.drawable.item_menu, "变压器维修专职审核");
        	tabThirdMenus.add(tabFirstMenu2);
        	Menu tabFirstMenu3 = new Menu(R.drawable.item_menu, "变压器维修主任审批");
        	tabThirdMenus.add(tabFirstMenu3);
        }
    }
    
    /**
     * tab选项卡
     * 
     * @author obeya
     * 
     */
    public static final class TabValue {
        /**
         * Tab选项卡的图标
         */
        public static int mImageViewArray[] = { R.drawable.tab_icon1,
                R.drawable.tab_icon2, R.drawable.tab_icon3 };
        
        /**
         * Tab选项卡的文字
         * 1.缺陷流程管理
         * 2.故障抢修流程管理
         * 3.变压器维修流程管理
         */
        public static String mTextViewArray[] = { "缺陷流程管理", "故障抢修流程管理",
                "变压器维修流程管理" };
        
        /**
         * 每一个Tab界面,设置一个Activity组界面
         */
        public static Class<?> mTabClassArray[] = {
                TabFirstActivityGroup.class, TabSecondActivityGroup.class,
                TabThreeActivityGroup.class };
        
    }
}
