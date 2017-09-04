package config;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public class SystemConfig {

	private final int minX;
	private final int maxX;
	private final int minY;
	private final int maxY;
	private final int levelUp;
	private final List<Point[]> typeConfig;
	private final List<Boolean> typeRound;
	
	private final Map<Integer, Integer> plusPoint;
	@SuppressWarnings("unchecked")
	public SystemConfig(Element system) {
		this.minX = Integer.parseInt(system.attributeValue("minX"));
		this.maxX = Integer.parseInt(system.attributeValue("maxX"));
		this.minY = Integer.parseInt(system.attributeValue("minY"));
		this.maxY = Integer.parseInt(system.attributeValue("maxY"));
		this.levelUp = Integer.parseInt(system.attributeValue("levelUp"));
		List<Element> rects = system.elements("rect");
		typeConfig = new ArrayList<Point[]>(rects.size());
		typeRound = new ArrayList<Boolean>();
		for (Element rect: rects) {
			this.typeRound.add(Boolean.parseBoolean(rect.attributeValue("round")));
			//获得坐标对象
			List<Element> pointConfig = rect.elements("Point");
			Point[] points = new Point[pointConfig.size()];
			for (int i = 0; i < points.length; i++) {
				int x = Integer.parseInt(pointConfig.get(i).attributeValue("x"));
				int y = Integer.parseInt(pointConfig.get(i).attributeValue("y"));
				points[i] = new Point(x, y);
			}
			typeConfig.add(points);
		}
		
		this.plusPoint = new HashMap<Integer, Integer>();
		List<Element> plusPointCfg = system.elements("plusPoint");
		for(Element cfg:plusPointCfg) {
			int rm = Integer.parseInt(cfg.attributeValue("rm"));
			int point = Integer.parseInt(cfg.attributeValue("point"));
			plusPoint.put(rm, point);
		}
	}

	public List<Boolean> getTypeRound() {
		return typeRound;
	}

	public int getLevelUp() {
		return levelUp;
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public List<Point[]> getTypeConfig() {
		return typeConfig;
	}

	public Map<Integer, Integer> getPlusPoint() {
		return plusPoint;
	}
}
