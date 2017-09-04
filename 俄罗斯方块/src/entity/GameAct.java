package entity;

import java.awt.Point;
import java.util.List;

import config.GameConfig;

/**
 * 放置4个小块，，经典俄罗斯方块
 * 
 * @author maoyadong
 *
 */
public class GameAct {
	/**
	 * 方块数组
	 */
	private Point[] actPoints;

	/**
	 * 方块编号
	 */
	private int typeCode;
	private static final int MIN_X = GameConfig.getSystemConfig().getMinX();
	private static final int MAX_X = GameConfig.getSystemConfig().getMaxX();
	private static final int MIN_Y = GameConfig.getSystemConfig().getMinY();
	private static final int MAX_Y = GameConfig.getSystemConfig().getMaxY();

	/**
	 * 获得各个小方块的位置数组
	 */
	private static final List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
	private static final List<Boolean> TYPE_ROUND = GameConfig.getSystemConfig().getTypeRound();
	public GameAct(int typeCode) {
		this.init(typeCode);
	}

	//初始化
	public void init(int typeCode) {
		this.typeCode = typeCode;
		Point[] points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[points.length];
		for(int i=0;i<actPoints.length;i++) {
			actPoints[i] = new Point(points[i].x, points[i].y);
		}
	}
	public Point[] getActPoints() {
		return actPoints;
	}

	/**
	 * 方块移动
	 * 
	 * @param moveX
	 *            X轴偏移量
	 * @param moveY
	 *            Y轴偏移量
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap) {
		// 移动处理
		for (int i = 0; i < actPoints.length; i++) {
			int newX = actPoints[i].x + moveX;
			int newY = actPoints[i].y + moveY;
			if (this.isOverZone(newX, newY, gameMap)) {
				return false;
			}
		}
		for (int i = 0; i < actPoints.length; i++) {
			actPoints[i].x += moveX;
			actPoints[i].y += moveY;
		}
		return true;
	}

	/**
	 * 旋转 笛卡尔坐标系 顺时针 A.x = 0.y + 0.x -B.y A.y = 0.y - 0.x +B.x
	 * typeCode 种类的下标值
	 */
	public void round(boolean[][] gameMap) {
		//正方形方块不需要旋转
		if(!TYPE_ROUND.get(this.typeCode)) {
			return;
		}
		for (int i = 1; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			if (this.isOverZone(newX, newY, gameMap)) {
				return;
			}
		}
		for (int i = 1; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			actPoints[i].x = newX;
			actPoints[i].y = newY;
		}
	}

	/**
	 * 判断是否超出边界
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isOverZone(int x, int y, boolean[][] gameMap) {
		return x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y || gameMap[x][y];
	}

	
	/**
	 * 获得方块类型编号
	 */
	public int getTypeCode() {
		return typeCode;
	}
	
}
