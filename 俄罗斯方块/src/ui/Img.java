package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {
	private Img() {
		
	}
	/**
	 * 等级标题图片
	 */
	public static Image IMG_LV = new ImageIcon("Image/string/level.png").getImage();
	/**
	 * 分数标题图片
	 */
	public static Image IMG_POINT = new ImageIcon("Image/string/point.png").getImage();
	/**
	 * 消行标题图片
	 */
	public static Image IMG_RELINE = new ImageIcon("Image/string/rmline.png").getImage();
	/**
	 * 方块颜色图片 8种
	 */
	public static Image Act = new ImageIcon("Image/game/rect.png").getImage();
	
	/**
	 * 数字图片 260 36
	 */
	public static final Image IMG_NUMBER = new ImageIcon("Image/string/num.png").getImage();
	/**
	 * 值槽图片
	 */
	public static Image IMG_RECT = new ImageIcon("Image/window/rect.png").getImage();
	/**
	 * 数据库图片
	 */
	public static Image IMG_DB = new ImageIcon("Image/string/db.png").getImage();
	/**
	 * 本地数据库
	 */
	public static Image Img_DISK = new ImageIcon("Image/string/disk.png").getImage();
	
	/**
	 * 边框图片
	 */
	public static Image WINDOW_IMG = new ImageIcon("Image/Window/Window3.png").getImage();
	/**
	 * 开始按钮
	 */
	public static ImageIcon BIN_START = new ImageIcon("Image/string/start.png");
	/**
	 * 设置按钮
	 */
	public static ImageIcon BIN_CONFIG = new ImageIcon("Image/string/config.png");
	/**
	 * 下一个方块
	 */
	public static Image[] NEXT_ACT;
	/**
	 * 暂停图片
	 */
	public static Image PAUSE = new ImageIcon("Image/string/pause.png").getImage();
	/**
	 * 背景图片
	 */
	public static List<Image> BG_LIST;
	static {
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("Image/game/" + i + ".png").getImage();
		}
		//背景图片数组
		File dir = new File("Image/background");
		BG_LIST = new ArrayList<Image>();
		File[] files = dir.listFiles();
		for(File file:files) {
			if(file.isDirectory()) {
				continue;
			}
			BG_LIST.add(new ImageIcon(file.getPath()).getImage());
		}
	}
	
}
