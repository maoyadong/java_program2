package config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GameConfig {

	private static FrameConfig FRAME_CONFIG = null;
	private static DataConfig DATA_CONFIG = null;
	private static SystemConfig SYSTEM_CONFIG = null;
	
	static {
		try {
			//创建XML读取器
			SAXReader reader = new SAXReader();
			//读取XML文件
			Document doc = reader.read("data/cfg.xml");
			//获得XML文件的根节点
			Element game = doc.getRootElement();
			FRAME_CONFIG = new FrameConfig(game.element("frame"));
			SYSTEM_CONFIG = new SystemConfig(game.element("system"));
			DATA_CONFIG = new DataConfig(game.element("data"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 构造器私有化
	 */
	private GameConfig() {
		
	}
	/**
	 * 获得窗口配置
	 * @return
	 */
	public static FrameConfig getFrameConfig() {
		return FRAME_CONFIG;
	}
	/**
	 * 获得系统配置
	 * @return
	 */
	public static SystemConfig getSystemConfig() {
		return SYSTEM_CONFIG;
	}
	/**
	 * 获得数据访问配置
	 * @return
	 */
	public static DataConfig getDataConfig() {
		return DATA_CONFIG;
	}
}
