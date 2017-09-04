package ui.window;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame {

	public JFrameGame(JPanelGame panelGame) {
		FrameConfig frameConfig = GameConfig.getFrameConfig();
		// 设置标题
		this.setTitle(frameConfig.getTitle());
		// 设置默认关闭属性
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小
		this.setSize(frameConfig.getWidth(), frameConfig.getHeight());
		// 不允许用户改变窗口大小
		this.setResizable(false);
		FrameUtil.setFrameCenter(this);
		this.setContentPane(panelGame);
		this.setVisible(true);
	}
}
