package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTetris;
import ui.window.JFrameConfig;
import ui.window.JFrameGame;
import ui.window.JFrameSavePoint;
import ui.window.JPanelGame;

/**
 * 接受玩家键盘控制 控制画面 控制游戏逻辑
 * 
 * @author maoyadong
 *
 */
public class GameControl {

	/**
	 * 游戏界面层
	 */
	private JPanelGame panelGame;
	/**
	 * 游戏逻辑层
	 */
	private GameService gameService;

	/**
	 * 数据访问接口A
	 */
	private Data dataA;
	/**
	 * 数据访问接口B
	 */
	private Data dataB;
	/**
	 * 游戏控制设置窗口
	 */
	private JFrameConfig frameConfig;
	/**
	 * 保存分数窗口
	 */
	private JFrameSavePoint frameSavePoint;
	/**
	 * 游戏行为控制
	 */
	private Map<Integer, Method> actionList;
	/**
	 * 游戏数据源
	 */
	private GameDto dto = null;
	/**
	 * 游戏线程
	 */
	
	private Thread gameThread = null;
	public GameControl() {
		// 创建数据源
		this.dto = new GameDto();
		// 创建游戏逻辑块(安装数据源)
		this.gameService = new GameTetris(dto);
		// 创建数据对象A
		this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
		//设置数据库记录到游戏
		this.dto.setDbRecode(dataA.loadData());
		//创建数据对象B
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		// 设置本地磁盘记录到游戏
		this.dto.setDiskRecode(dataB.loadData());
		// 创建游戏面板
		this.panelGame = new JPanelGame(this, dto);
		// 读取用户控制设置
		this.setControlConfig();
		//初始化用户配置窗口
		this.frameConfig = new JFrameConfig(this);
		//初始化保存分数窗口
		this.frameSavePoint = new JFrameSavePoint(this);
		//初始化游戏主窗口，安装游戏面板
		new JFrameGame(this.panelGame);
	}

	@SuppressWarnings({ "unchecked"})
	private void setControlConfig() {
		//创建键盘与方法名的映射
		this.actionList = new HashMap<Integer, Method>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>)ois.readObject();
			Set<Entry<Integer, String>> entrySet = cfgSet.entrySet();
			for(Entry<Integer, String> e:entrySet) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	private Data createDataObject(DataInterfaceConfig cfg) {
		try {
			// 获得类对象
			Class<?> cls = Class.forName(cfg.getClassName());
			// 创建构造器
			Constructor<?> constructor = cls.getConstructor(HashMap.class);
			// 创建对象
			return (Data) constructor.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 反射，通过字符串来创建对象
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode) {
		try {
			if(this.actionList.containsKey(keyCode)) {
				this.actionList.get(keyCode).invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.panelGame.repaint();
	}

	/**
	 * 显示玩家控制窗口
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * 子窗口关闭事件
	 */
	public void setOver() {
		this.panelGame.repaint();
		//方法重新映射一下,读新的配置
		this.setControlConfig();
	}
	
	/**
	 * 开始按钮事件
	 */
	public void start() {
		//按钮设置为不可点击
		this.panelGame.buttonSwitch(false);
		//关闭窗口
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		//游戏数据初始化
		this.gameService.startGame();
		//刷新画面
		this.panelGame.repaint();
		//创建线程对象
		this.gameThread = new MainThread();
		//启动线程
		this.gameThread.start();
		//刷新画面
		this.panelGame.repaint();
	}
	
	/**
	 * 保存分数
	 * @param name
	 */
	public void savePoint(String name) {
		Player pla = new Player(name, this.dto.getNowPoint());
		//保存记录到数据库
		this.dataA.saveData(pla);
		//保存记录到磁盘
		this.dataB.saveData(pla);
		//设置数据库记录到游戏
		this.dto.setDbRecode(dataA.loadData());
		//设置磁盘记录到游戏
		this.dto.setDiskRecode(dataB.loadData());
		//刷新画面
		this.panelGame.repaint();
	}
	/**
	 * 失败之后的处理
	 */
	public void afterLose() {
		//显示保存得分窗口
		this.frameSavePoint.showWindow(this.dto.getNowPoint());
		//是按钮可以点击
		this.panelGame.buttonSwitch(true);
	}
	private class MainThread extends Thread {
		@Override
		public void run() {
			//刷新画面
			panelGame.repaint();
			//主循环
			while(dto.isStart()) {
				try {
					//线程睡眠
					Thread.sleep(dto.getSleepTime());
					//如果暂停，那么不执行主行为
					if(dto.isPause()) {
						continue;
					}
					//主行为
					gameService.mainAction();
					//刷新画面
					panelGame.repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
		
	}
}
