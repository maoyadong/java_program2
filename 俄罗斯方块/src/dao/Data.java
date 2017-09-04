package dao;

import java.util.List;

import dto.Player;

/**
 * 数据持久层接口
 * @author maoyadong
 *
 */
public interface Data {
	/**
	 * 获得数据
	 */
	public List<Player> loadData();
	/**
	 * 存储数据
	 */
	public void saveData(Player player);
}
