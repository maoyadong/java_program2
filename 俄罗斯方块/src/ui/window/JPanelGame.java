package ui.window;

/**
 * 核心代码
 */
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.FrameConfig;
import config.GameConfig;
import config.LayerCongfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;
import ui.Img;
import ui.Layer;

@SuppressWarnings("serial")
public class JPanelGame extends JPanel {

	// 按钮宽
	private static final int BIN_SIZE_W = GameConfig.getFrameConfig().getButtonConfig().getButtonW();
	private static final int BIN_SIZE_H = GameConfig.getFrameConfig().getButtonConfig().getButtonH();
	// 开始按钮
	private JButton btnStart;
	private JButton btnConfig;
	private List<Layer> layers = null;
	private GameControl gameControl = null;
	public JPanelGame(GameControl gameControl,GameDto dto) {
		//连接游戏控制器
		this.gameControl = gameControl;
		// 设置布局管理器为自由布局
		this.setLayout(null);
		initComponent();
		//初始化组件
		initLayer(dto);
		//安装键盘监听器
		this.addKeyListener(new PlayerControl(gameControl));
	}

	/**
	 * 初始化组件
	 */
	private void initComponent() {
		// 初始化开始按钮
		this.btnStart = new JButton(Img.BIN_START);
		//给开始按钮添加事件监听
		this.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
			}
		});
		// 设置开始按钮位置
		this.btnStart.setBounds(GameConfig.getFrameConfig().getButtonConfig().getStartX(),
				GameConfig.getFrameConfig().getButtonConfig().getStartY(), BIN_SIZE_W, BIN_SIZE_H);
		this.add(btnStart);
		// 初始化设置按钮
		this.btnConfig = new JButton(Img.BIN_CONFIG);
		this.btnConfig.setBounds(GameConfig.getFrameConfig().getButtonConfig().getUserConfigX(),
				GameConfig.getFrameConfig().getButtonConfig().getUserConfigY(), BIN_SIZE_W, BIN_SIZE_H);
		this.btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameControl.showUserConfig();
			}
		});
		this.add(btnConfig);
	}

	/**
	 * 层初始化
	 */
	private void initLayer(GameDto dto) {
		try {
			// 获得游戏配置
			FrameConfig frameConfig = GameConfig.getFrameConfig();
			// 获得层配置
			List<LayerCongfig> layersCfg = frameConfig.getLayerCongfig();
			layers = new ArrayList<Layer>(layersCfg.size());
			for (LayerCongfig layerCfg : layersCfg) {
				Class<?> cls = Class.forName(layerCfg.getClassName());
				Constructor<?> constructor = cls.getConstructor(int.class, int.class, int.class, int.class);
				// 调用构造函数创建对象
				Layer layer = (Layer) constructor.newInstance(layerCfg.getX(), layerCfg.getY(), layerCfg.getW(),
						layerCfg.getH());
				// 设置游戏数据对象
				layer.setDto(dto);
				layers.add(layer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		// 调用一下基类方法
		super.paintComponent(g);
		// 循环刷新游戏画面
		for (int i = 0; i < layers.size(); i++) {
			// 刷新层窗口
			layers.get(i).paint(g);
		}
		// 返回焦点
		this.requestFocus();
	}

	/**
	 * 
	 * @param gameControl
	 */
	public void buttonSwitch(boolean onOff) {
		this.btnStart.setEnabled(onOff);
		this.btnConfig.setEnabled(onOff);
	}
}
