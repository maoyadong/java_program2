package service;

public interface GameService {
	public boolean keyUp();
	public boolean keyDown();
	public boolean keyLeft();
	public boolean keyRight();
	/**
	 * 三角
	 */
	public boolean keyFunUp();
	/**
	 * 大叉
	 */
	public boolean keyFunDown();
	/**
	 * 方块
	 */
	public boolean keyFunLeft();
	/**
	 * 圆圈
	 */
	public boolean keyFunRight();
	/**
	 * 启动主线程开始游戏
	 */
	public void startGame();
	/**
	 * 主行为
	 */
	public void mainAction();
}
