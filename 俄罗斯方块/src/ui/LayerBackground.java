package ui;

import java.awt.Graphics;

public class LayerBackground extends Layer {

	public LayerBackground(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub TODO为未完成的任务
	}

	@Override
	public void paint(Graphics g) {
		// this.x和this.y用来做相对坐标
		int bgIdx = this.dto.getNowlevel() % Img.BG_LIST.size();
		g.drawImage(Img.BG_LIST.get(bgIdx), 0, 0, 1162, 654, null);
	}

}
