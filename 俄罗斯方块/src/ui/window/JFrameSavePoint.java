package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GameControl;
import util.FrameUtil;


public class JFrameSavePoint extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8638221818665077256L;
	private JLabel lbPoint = null;
	private JButton btnOk = null;
	private JTextField txName = null;
	private JLabel errMsg;
	/**
	 * 获得控制器对象
	 */
	private GameControl gameControl = null;

	public JFrameSavePoint(GameControl gameControl) {
		this.gameControl = gameControl;
		this.setTitle("保存记录");
		this.setSize(256, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.creatCom();
		this.createAction();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 显示窗口
	 */
	public void showWindow(int point) {
		this.lbPoint.setText("您的得分: " + point);
		this.setVisible(true);
	}

	/**
	 * 创建事件监听
	 */
	private void createAction() {
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txName.getText();
				if (name.length() > 16 || name == null || "".equals(name)) {
					errMsg.setText("请输入十六位以下的名字");
				} else {
					setVisible(false);
					gameControl.savePoint(name);
				}
			}
		});

	}

	/**
	 * 初始化控件
	 */
	private void creatCom() {
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.lbPoint = new JLabel();

		north.add(this.lbPoint);

		// 创建信息错误控件
		this.errMsg = new JLabel();
		this.errMsg.setForeground(Color.RED);
		north.add(this.errMsg);
		this.add(north, BorderLayout.NORTH);

		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName = new JTextField(10);
		txName.setRequestFocusEnabled(true);
		center.add(new JLabel("您的名字:"));
		center.add(this.txName);
		this.add(center, BorderLayout.CENTER);

		// 创建南部面板（流式布局）
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// 创建确定按钮
		this.btnOk = new JButton("OK");
		// 按钮添加到南部面板
		south.add(btnOk);
		// 南部面板添加到主面板
		this.add(south, BorderLayout.SOUTH);
	}
}
