package config;

import org.dom4j.Element;

public class DataConfig {

	private final int maxRow;
	private final DataInterfaceConfig dataA;
	private final DataInterfaceConfig dataB;
	public DataConfig(Element data) {
		maxRow = Integer.parseInt(data.attributeValue("maxRow"));
		dataA = new DataInterfaceConfig(data.element("dataA"));
		dataB = new DataInterfaceConfig(data.element("dataB"));
	}
	public int getMaxRow() {
		return maxRow;
	}
	public DataInterfaceConfig getDataA() {
		return dataA;
	}
	public DataInterfaceConfig getDataB() {
		return dataB;
	}
}
