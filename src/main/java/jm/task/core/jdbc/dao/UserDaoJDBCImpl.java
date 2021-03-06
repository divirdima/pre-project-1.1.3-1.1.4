package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
	
	
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
    	try(Connection connection = Util.getConnection();
			Statement stat = connection.createStatement()) {
			stat.executeUpdate("CREATE TABLE IF NOT EXISTS Users("
					+ "id BIGINT NOT NULL AUTO_INCREMENT, "
					+ "name CHAR(25) NOT NULL, "
					+ "lastName CHAR(30) NOT NULL, "
					+ "age BIT(8) NOT NULL, PRIMARY KEY (id))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void dropUsersTable() {
    	try(Connection connection = Util.getConnection();
			Statement stat = connection.createStatement()) {
		stat.executeUpdate("DROP TABLE IF EXISTS Users");
		Util.getConnection().close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void saveUser(String name, String lastName, byte age) {
    	try(Connection connection = Util.getConnection();
			PreparedStatement prep = connection.prepareStatement(
				"INSERT INTO Users set name = ?, lastName = ?, age = ?")) {
			prep.setString(1, name);
			prep.setString(2, lastName);
			prep.setByte(3, age);
			
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void removeUserById(long id) {
    	try(Connection connection = Util.getConnection();
			PreparedStatement prep = connection.prepareStatement(
					"DELETE Users WHERE id = ?")) {
			prep.setLong(1, id);
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public List<User> getAllUsers() {
    	List<User> users = new ArrayList<>();
    	try(Connection connection = Util.getConnection();
			PreparedStatement prep = connection.prepareStatement("SELECT * FROM Users");
		    ResultSet result = prep.executeQuery()) {
			
			while (result.next()) {
				User tempUser = new User(
						result.getString("name"),
						result.getString("lastName"),
						result.getByte("age")
						);
				users.add(tempUser);
			}
			return users;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

    public void cleanUsersTable() {
    	try(Connection connection = Util.getConnection();
			Statement stat = connection.createStatement()) {
			stat.executeUpdate("TRUNCATE TABLE Users");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
