package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    private static final String CLEAN_TABLE_USERS = "TRUNCATE TABLE users";
    private static final String REQUESTING_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ";
    private static final String INSERT_USERS = "INSERT INTO users (name, lastName, age) VALUE (?,?,?)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            " id INTEGER PRIMARY KEY NOT NULL" +
            " AUTO_INCREMENT," +
            " name VARCHAR(40), " +
            "lastName VARCHAR(40), " +
            "age INTEGER(3))";


    Connection conn = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;


    public UserDaoJDBCImpl() {}

    public void dropUsersTable() {
        try {
            conn = Util.getMySQLConnection();
            statement = conn.createStatement();
            statement.executeUpdate(DROP_TABLE);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            conn = Util.getMySQLConnection();
            statement = conn.createStatement();
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try {
            conn = Util.getMySQLConnection();
            preparedStatement = conn.prepareStatement(INSERT_USERS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            conn = Util.getMySQLConnection();
            statement = conn.createStatement();
            statement.executeUpdate(DELETE_BY_ID + id);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        User user;
        ResultSet resultSet;

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(REQUESTING_ALL_USERS);
            while (resultSet.next()) {
                user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            conn = Util.getMySQLConnection();
            statement = conn.createStatement();
            statement.executeUpdate(CLEAN_TABLE_USERS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
