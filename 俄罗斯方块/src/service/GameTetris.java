package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService {

	/**
	 * 生成随机数并初始化
	 */
	private Random random = new Random();
	/**
	 * 方块种类数
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() - 1;
	/**
	 * 升级行数
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	/**
	 * 连续消行分数
	 */
	private static final Map<Integer, Integer> PLUS_POINT = GameConfig.getSystemConfig().getPlusPoint();

	private GameDto dto;

	public GameTetris(GameDto dto) {
		this.dto = dto;
	}

	public boolean keyUp() {
		if (this.dto.isPause()) {
			return true;
		}
		// TODO 旋转 独占dto 其他程序不能访问
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}

	public boolean keyDown() {
		if (this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			// 获取地图对象
			boolean map[][] = this.dto.getGameMap();
			Point[] act = this.dto.getGameAct().getActPoints();
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// 判断消行，并计算经验值
			int plusExp = this.plusExp();
			// 如果发生消行
			if (plusExp > 0) {
				// 增加经验值
				this.plusPoint(plusExp);
			}
			// 创建下一个方块
			this.dto.getGameAct().init(this.dto.getNext());
			// 随机生成下一个方块
			this.dto.setNext(random.nextInt(MAX_TYPE));
			// 检查是否失败
			if (this.isLose()) {
				// 结束游戏
				this.dto.setStart(false);
			}
			return true;
		}
	}

	/**
	 * 检查是否失败
	 */
	private boolean isLose() {
		// 获得现在的活动方块
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		// 获得现在的游戏地图
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if (map[actPoints[i].x][actPoints[i].y]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 加分升级操作
	 * 
	 * @param exp
	 */
	private void plusPoint(int plusExp) {
		int level = this.dto.getNowlevel();
		int rmLine = this.dto.getNowRemoveLine();
		int point = this.dto.getNowPoint();
		if (rmLine % LEVEL_UP + plusExp >= LEVEL_UP) {
			this.dto.setNowlevel(++level);
		}
		this.dto.setNowRemoveLine(rmLine + plusExp);
		this.dto.setNowPoint(point + PLUS_POINT.get(plusExp));

	}

	public boolean keyLeft() {
		if (this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
		return true;
	}

	public boolean keyRight() {
		if (this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * 消行操作
	 */
	private int plusExp() {
		// 获得游戏地图
		boolean[][] map = this.dto.getGameMap();
		int exp = 0;
		// 扫描游戏地图，查看是否有可消行
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			// 判断是否可消行
			if (isCanRemoveLine(y, map)) {
				// 如果可消行，那就消
				this.removeLine(y, map);
				// 增加经验值
				exp++;
			}

		}
		return exp;
	}

	/**
	 * 消行处理
	 * 
	 * @param y
	 * @param map
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y - 1];
			}
			map[x][0] = false;
		}
	}

	/**
	 * 判断某一行是否可消行
	 * 
	 * @param y
	 * @return
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		// 对一行的单元格进行扫描
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if (!map[x][y]) {
				// 如果有一个方格为false，则直接跳到下一行
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean keyFunUp() {
		// 作弊键
		this.plusPoint(4);
		int point = this.dto.getNowPoint();
		int rmLine = this.dto.getNowRemoveLine();
		int lv = this.dto.getNowlevel();
		point += 10;
		rmLine += 1;
		if (rmLine % 20 == 0) {
			lv += 1;
		}
		this.dto.setNowPoint(point);
		this.dto.setNowlevel(lv);
		this.dto.setNowRemoveLine(rmLine);
		return true;
	}

	@Override
	public boolean keyFunDown() {
		if (this.dto.isPause()) {
			return true;
		}
		// 瞬间下落
		while (!this.keyDown());
		return true;
	}

	@Override
	public boolean keyFunLeft() {
		// 阴影开关
		this.dto.changeShowShadow();
		return true;
	}

	@Override
	public boolean keyFunRight() {
		if (this.dto.isStart()) {
			this.dto.changePause();
		}
		return true;
	}

	public void startGame() {
		// 随机生成下一方块
		this.dto.setNext(random.nextInt(MAX_TYPE));
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		this.dto.setStart(true);
		//dto初始化
		this.dto.dtoInit();
	}

	@Override
	public void mainAction() {
		this.keyDown();
	}
}
