package util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import config.GameConfig;

public class FrameUtil {
	
	/**
	 * 窗口居中
	 * @param jframe
	 */
	public static void setFrameCenter(JFrame jframe) {
		// 设置窗口位置 居中
		Toolkit toolkit = Toolkit.getDefaultToolkit();// 工厂方法
		// 获得显示器的大小
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - jframe.getWidth())>>1; // 获得显示器的宽度
		int y = (screen.width - jframe.getHeight()>>1) - GameConfig.getFrameConfig().getWindowUp(); // 获得显示器的高度
		jframe.setLocation(x, y);
	}
}
