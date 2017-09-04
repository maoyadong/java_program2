package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class LayerAbout extends Layer {

	
	public LayerAbout(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	//TODO
	public void paint(Graphics g) {
		this.createWindow(g);
		g.setColor(Color.ORANGE);
		g.setFont(new Font("楷体", Font.PLAIN, 32));
		g.drawString("2017-8-2  冒亚东", this.x+24, this.y+60);
	}
}
