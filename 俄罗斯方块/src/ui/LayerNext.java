package ui;

import java.awt.Graphics;

public class LayerNext extends Layer {

	public LayerNext(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		//如果是开始状态才开始绘制下一个
		if (this.dto.isStart()) {
			this.drawImageAtCenter(Img.NEXT_ACT[this.dto.getNext()], g);
		}
	}
}
