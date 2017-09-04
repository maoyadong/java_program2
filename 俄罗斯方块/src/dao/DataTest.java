package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Player;

/**
 * 实现接口
 * @author maoyadong
 *
 */
public class DataTest implements Data{

	@Override
	public List<Player> loadData() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("冒亚东", 100));
		players.add(new Player("冒亚东", 1000));
		players.add(new Player("冒亚东", 2000));
		players.add(new Player("冒亚东", 3000));
		players.add(new Player("冒亚东", 4000));
		return players;
	}

	@Override
	public void saveData(Player players) {
		System.out.println();
	}

}
