package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;
/**
 * 绘制图片边框   这个类不能实例化，只能靠子类实现
 */
public abstract class Layer {

	protected static final int PADDING;
	/*
	 * 边框宽度
	 */
	protected static final int BORDER;
	
	
	// 数字切片的宽度
	protected static final int IMG_NUMBER_W = Img.IMG_NUMBER.getWidth(null) / 10;
	protected static final int IMG_NUMBER_H = Img.IMG_NUMBER.getHeight(null);
	
	/**
	 * 矩形值槽的高度
	 */
	protected static final int IMG_RECT_H = Img.IMG_RECT.getHeight(null);
	/**
	 * 矩形值槽的宽度
	 */
	protected static final int IMG_RECT_W = Img.IMG_RECT.getWidth(null);
	/**
	 * 经验值槽的宽度
	 */
	private final int rectW;
	/**
	 * 默认字体
	 */
	private static final Font DEF_FONT = new Font("黑体", Font.BOLD, 24);
	
	static {
		//获得游戏配置
		FrameConfig frameConfig = GameConfig.getFrameConfig();
		PADDING = frameConfig.getPadding();
		BORDER = frameConfig.getBorder();
	}
	
	private static int WINDOW_W = Img.WINDOW_IMG.getWidth(null);
	private static int WINDOW_H = Img.WINDOW_IMG.getHeight(null);
	/*
	 * 窗口左上角横坐标
	 */
	protected int x;
	/*
	 * 窗口左上角纵坐标
	 */
	protected int y;
	/*
	 * 窗口宽度
	 */
	protected int w;
	/*
	 * 窗口高度
	 */
	protected int h;

	/*
	 * 构造方法
	 */
	protected GameDto dto = null;
	public Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		rectW = this.w - (PADDING << 1);
	}
	
	/*
	 * 绘制窗口
	 */
	protected void createWindow(Graphics g) {
		// 左上
		g.drawImage(Img.WINDOW_IMG, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
		// 中上
		g.drawImage(Img.WINDOW_IMG, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER, 0, WINDOW_W - BORDER, BORDER, null);
		// 右上
		g.drawImage(Img.WINDOW_IMG, x + w - BORDER, y, x + w, y + BORDER, WINDOW_W - BORDER, 0, WINDOW_W, BORDER, null);
		// 左中
		g.drawImage(Img.WINDOW_IMG, x, y + BORDER, x + BORDER, y + h - BORDER, 0, BORDER, BORDER, WINDOW_H - BORDER, null);
		// 中
		g.drawImage(Img.WINDOW_IMG, x + BORDER, y + BORDER, x + w - BORDER, y + h - BORDER, BORDER, BORDER, WINDOW_W - BORDER, WINDOW_H - BORDER, null);
		// 右中
		g.drawImage(Img.WINDOW_IMG, x + w - BORDER, y + BORDER, x + w, y + h - BORDER, WINDOW_W - BORDER, BORDER, WINDOW_W, WINDOW_H - BORDER, null);
		// 左下
		g.drawImage(Img.WINDOW_IMG, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_H - BORDER, BORDER, WINDOW_H, null);
		// 中下
		g.drawImage(Img.WINDOW_IMG, x + BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_H - BORDER, WINDOW_W - BORDER, WINDOW_H, null);
		// 右下
		g.drawImage(Img.WINDOW_IMG, x + w - BORDER, y + h - BORDER, x + w, y + h, WINDOW_W - BORDER, WINDOW_H - BORDER, WINDOW_W, WINDOW_H, null);
			
	}

	//靠子类实现
	abstract public void paint(Graphics g);

	/**
	 * 绘制经验槽值
	 * 
	 * @param g
	 */
	protected void drawRect(int expY, String title, String number, double percent, Graphics g) {
		// 初始化
		int rect_x = this.x + PADDING;
		int rect_y = this.y + expY;
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rectW, IMG_RECT_H + 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.rectW - 2, IMG_RECT_H + 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.rectW - 4, IMG_RECT_H);
		int w = (int) (percent * (this.rectW - 4));
		int subIdx = (int) (percent * IMG_RECT_W) - 1;
		g.drawImage(Img.IMG_RECT, rect_x + 2, rect_y + 2, rect_x + 2 + w, rect_y + IMG_RECT_H + 2, subIdx, 0, subIdx + 1,
				IMG_RECT_H, null);
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_x + 3, rect_y +24);
		if(number != null) {
			g.drawString(String.valueOf(number), rect_x + 200, rect_y +24);
		}
	}
	
	/**
	 * 正中绘图
	 */
	protected void drawImageAtCenter(Image img, Graphics g) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		int imgX = this.x + (this.w - imgW >> 1);
		int imgY = this.y + (this.h - imgH >> 1);
		g.drawImage(img, imgX, imgY, null);
	}
	
	/**
	 * 显示数字
	 * 
	 * @param x
	 *            左上角坐标
	 * @param y
	 *            左上角坐标
	 * @param num
	 *            要显示的数字
	 * @param g
	 *            画笔对象
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		String strNum = Integer.toString(num);
		for (int i = 0; i < maxBit; i++) {
			if (maxBit - i <= strNum.length()) {
				// 获得数字在字符串中的下标
				int idx = i - maxBit + strNum.length();
				int bit = strNum.charAt(idx) - '0';
				g.drawImage(Img.IMG_NUMBER, this.x + x + IMG_NUMBER_W * i, this.y + y, this.x + x + IMG_NUMBER_W * (i + 1),
						this.y + y + IMG_NUMBER_H, bit * IMG_NUMBER_W, 0, (bit + 1) * IMG_NUMBER_W, IMG_NUMBER_H, null);
			} 
		}
	}
	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	
}
