package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import control.GameControl;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameConfig extends JFrame {

	private GameControl gameControl;
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JButton btnUser = new JButton("应用");
	private TextCtrl[] keyText = new TextCtrl[8];
	private static final Image IMG_PSP = new ImageIcon("data/psp.jpg").getImage();
	private final static String[] METHOD_NAMES = {
			"keyUp",
			"keyLeft",
			"keyRight",
			"keyDown",
			"keyFunUp",
			"keyFunLeft",
			"keyFunRight",
			"keyFunDown"
	};
	private static final String PATH = "data/control.dat";
	//错误提示
	private JLabel errorMsg = new JLabel("");
	public JFrameConfig(GameControl gameControl) {
		//获得游戏控制器对象
		this.gameControl = gameControl;
		//标题
		this.setTitle("设置");
		//初始化按键输入框
		this.initKeyText();
		// 设置布局管理器为"边界布局"
		this.setLayout(new BorderLayout());
		// 添加主面板
		this.add(this.createMainPanel(), BorderLayout.CENTER);
		// 添加按钮面板
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		this.setSize(525, 430);
		this.setResizable(false);
		FrameUtil.setFrameCenter(this);
	}

	/**
	 * 初始化按键输入框
	 */
	@SuppressWarnings("unchecked")
	private void initKeyText() {
		int x = 20;
		int y = 50;
		int w = 50;
		int h = 20;
		for(int i=0;i<4;i++) {
			keyText[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y+=48;
		}
		x += 420;
		y = 50;
		for(int i=4;i<8;i++) {
			keyText[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y+=48;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>)ois.readObject();
			ois.close();
		
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for(Entry<Integer, String> e: entryset) {
				for(TextCtrl tc : keyText) {
					if(tc.getMethodName().equals(e.getValue())) {
						tc.setKeyCode(e.getKey());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 创建主面板
	 * 
	 * @return
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.addTab("控制设置", this.createControlPanel());
		jTabbedPane.addTab("皮肤设置", new JLabel("皮肤"));
		return jTabbedPane;
	}

	private JPanel createControlPanel() {
		JPanel jPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		jPanel.setLayout(null);
		for(int i=0;i<8;i++) {
			jPanel.add(keyText[i]);
		}
		return jPanel;
	}

	/**
	 * 创建按钮面板
	 * 
	 * @return
	 */
	private JPanel createButtonPanel() {
		// 创建按钮面板，流式布局
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		errorMsg.setForeground(Color.RED);
		jPanel.add(this.errorMsg);
		//给确定按钮添加事件监听
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(writeConfig()) {
					setVisible(false);
					gameControl.setOver();
				}
			}
		});
		jPanel.add(this.btnOk);
		this.btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
			}
		});
		jPanel.add(this.btnCancel);
		this.btnUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				writeConfig();
			}
		});
		jPanel.add(this.btnUser);

		return jPanel;
	}

	
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for(int i=0;i<this.keyText.length;i++) {
			int keyCode = this.keyText[i].getKeyCode();
			if(keyCode == 0) {
				this.errorMsg.setText("输入错误");
				return false;
			}
			keySet.put(this.keyText[i].getKeyCode(), this.keyText[i].getMethodName());
		}
		if(keySet.size() != 8) {
			this.errorMsg.setText("重复错误");
			return false;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			this.errorMsg.setText(e.getMessage());
			return false;
		} 
		this.errorMsg.setText(null);
		return true;
	}
}
