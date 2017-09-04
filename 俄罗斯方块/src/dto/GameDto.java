package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import entity.GameAct;
import util.GameFunction;

/**
 * 存储游戏数据
 * 
 * @author maoyadong
 *
 */
public class GameDto {

	public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX() + 1;
	public static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY() + 1;
	private List<Player> dbRecode;
	private List<Player> diskRecode;

	/**
	 * 线程睡眠时间
	 */
	private long sleepTime;
	/**
	 * 判断游戏是否处于开始状态
	 */
	private boolean start;
	private boolean[][] gameMap;

	/**
	 * 下落方块
	 */
	private GameAct gameAct;

	/**
	 * 下一个方块
	 */
	private int next;
	/**
	 * 等级
	 */
	private int nowlevel;

	private int nowPoint;
	/**
	 * 消行
	 */
	private int nowRemoveLine;
	/**
	 * 是否显示阴影
	 */
	public boolean ShowShadow;
	/**
	 * 暂停
	 */
	private boolean pause;

	/**
	 * 构造函数
	 * 
	 * @return
	 */
	public GameDto() {
		dtoInit();
	}

	/**
	 * 初始化函数
	 */
	public void dtoInit() {
		// TODO 硬编码
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
		this.nowlevel = 1;
		this.nowPoint = 0;
		this.nowRemoveLine = 0;
		this.pause = false;
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowlevel);
	}

	public List<Player> getDbRecode() {
		return dbRecode;
	}

	public void setDbRecode(List<Player> dbRecode) {
		this.dbRecode = this.setFillRecode(dbRecode);
	}

	public List<Player> getDiskRecode() {
		return diskRecode;
	}

	public void setDiskRecode(List<Player> diskRecode) {

		this.diskRecode = this.setFillRecode(diskRecode);
	}

	private List<Player> setFillRecode(List<Player> players) {
		if (players == null) {
			players = new ArrayList<Player>();
		}
		while (players.size() < 5) {
			players.add(new Player("No Data", 0));
		}
		Collections.sort(players);
		return players;
	}

	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getNowlevel() {
		return nowlevel;
	}

	public void setNowlevel(int nowlevel) {
		this.nowlevel = nowlevel;
		// 计算线程睡眠时间
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowlevel);
	}

	public int getNowPoint() {
		return nowPoint;
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isShowShadow() {
		return ShowShadow;
	}

	public void changeShowShadow() {
		this.ShowShadow = !this.ShowShadow;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
		this.pause = !this.pause;
	}

	public long getSleepTime() {
		return sleepTime;
	}

}
