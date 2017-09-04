package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

/**
 * 有两个继承类， DataBase 和 DataDisk
 * @author maoyadong
 *
 */
public abstract class LayerData extends Layer {

	/**
	 * 最大行
	 */
	private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();
	/**
	 * 起始y坐标
	 */
	private static int START_Y = 0;
	/**
	 * 间距
	 */
	private static int SPA = 0;

	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		SPA = (this.h - (IMG_RECT_H + 4) * 5 - (PADDING << 1) - Img.IMG_DB.getHeight(null)) / MAX_ROW;
		START_Y = PADDING + Img.IMG_DB.getHeight(null) + SPA;
	}

	abstract public void paint(Graphics g);

	/**
	 * 
	 * @param ImgTitle
	 *            标题图片
	 * @param players
	 *            数据源
	 * @param g
	 *            画笔
	 */
	public void showData(Image ImgTitle, List<Player> players, Graphics g) {
		g.drawImage(ImgTitle, this.x + PADDING, this.y + PADDING, null);
		int nowPoint = this.dto.getNowPoint();
		for (int i = 0; i < MAX_ROW; i++) {
			Player player = players.get(i);
			int recodePoint = player.getPoint();
			double percent = (double) nowPoint / recodePoint;
			percent = percent > 1 ? 1.0 : percent;
			String point = recodePoint == 0 ? null : String.valueOf(recodePoint);
			this.drawRect(START_Y + i * (IMG_RECT_H + 4 + SPA), player.getName(), point, percent, g);
		}
	}

}
