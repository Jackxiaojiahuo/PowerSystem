package ly.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 菜单实体类
 * 
 * @author obeya
 * 
 */
public class Menu implements Parcelable {
    
    private int mImage;
    private String mName;
    
    public Menu() {
    }
    
    public Menu(int image, String name) {
        mImage = image;
        mName = name;
    }
    
    public int getImage() {
        return mImage;
    }
    
    public void setImage(int image) {
        mImage = image;
    }
    
    public String getName() {
        return mName;
    }
    
    public void setName(String name) {
        mName = name;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImage);
        dest.writeString(mName);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            Menu menu = new Menu();
            menu.mImage = in.readInt();
            menu.mName = in.readString();
            return menu;
        }
        
        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
    
    @Override
    public String toString() {
        return "Menu [mImage=" + mImage + ", mName=" + mName + "]";
    }
    
}
