package ui;

import java.awt.Color;
import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {

	/**
	 * 分数的最大位数
	 */
	private static final int POINT_BIT = 5;

	/**
	 * 升级行数
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	/**
	 * 共有的x坐标
	 */
	private final int comX;

	/**
	 * 经验值y坐标
	 */
	private final int expY;
	/**
	 * 分数y坐标
	 */
	private final int pointY;
	/**
	 * 消行y坐标
	 */
	private final int relineY;

	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		// 初始化分数显示的x坐标
		this.comX = this.w - POINT_BIT * IMG_NUMBER_W - PADDING;
		this.pointY = PADDING;
		// 初始化消行的y坐标
		this.relineY = this.pointY + Img.IMG_POINT.getHeight(null) + PADDING;
		this.expY = this.relineY + Img.IMG_RELINE.getHeight(null) + PADDING;
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// 窗口标题(分数)
		g.drawImage(Img.IMG_POINT, this.x + PADDING, this.y + pointY, null);
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);
		// 窗口标题(消行)
		g.drawImage(Img.IMG_RELINE, this.x + PADDING, this.y + relineY, null);
		this.drawNumberLeftPad(comX, relineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
		// 绘制经验值槽
		int reLine = this.dto.getNowRemoveLine();
		drawRect(expY, "下一级", null, (double) (reLine % LEVEL_UP) / (double) (LEVEL_UP), g);
	}

	@SuppressWarnings("unused")
	@Deprecated
	private Color getNowColor(double hp, double maxHp) {
		int colorR = 0;
		int colorG = 255;
		int colorB = 0;
		double hpHalf = maxHp / 2;
		if (hp > hpHalf) {
			colorR = 255 - (int) ((hp - hpHalf) / (hpHalf) * 255);
			colorG = 255;
		} else {
			colorR = 255;
			colorG = (int) (hp / (maxHp / 2) * 255);
		}
		return new Color(colorR, colorG, colorB);
	}
}
