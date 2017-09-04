package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataBase implements Data {

	private final String dbUrl;

	private final String dbUser;

	private final String dbPwd;

	private static String LOAD_SQL = "select * from(select user_name, point from user_point order by point DESC) as tmp limit 5";

	private static String SAVE_SQL = "insert into user_point(user_name,point,type_id) value(?,?,?)";
	

	public DataBase(HashMap<String, String> param) {
		this.dbUser = param.get("dbUser");
		this.dbPwd = param.get("dbPwd");
		this.dbUrl = param.get("dbUrl");
		try {
			Class.forName(param.get("driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<Player> loadData() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Player> players = new ArrayList<Player>();
		try {
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			statement = connection.prepareStatement(LOAD_SQL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				players.add(new Player(resultSet.getString(1), resultSet.getInt(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
				if (statement != null)
					statement.close();
				if (resultSet != null)
					resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return players;
	}

	@Override
	public void saveData(Player player) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			statement = connection.prepareStatement(SAVE_SQL);
			statement.setObject(1, player.getName());
			statement.setObject(2, player.getPoint());
			statement.setObject(3, 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
