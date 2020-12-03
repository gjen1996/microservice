package com.glen.glengen.compiler;/**
 * @author Glen
 * @create 2020- 09-2020/9/14-11:29
 * @Description
 */

import java.sql.*;

/**
 * @author Glen
 * @create 2020/9/14 11:29
 * @Description
 */
public interface MysqlConnectionManager {
    String USER_NAME = "root";
    String PASS_WORD = "glen1996";
    String URL = "jdbc:mysql://localhost:3306/springauth?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8";

    Connection newConnection() throws SQLException;

    void closeConnection(Connection connection);

    MysqlConnectionManager X = new MysqlConnectionManager() {

        @Override
        public Connection newConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER_NAME, PASS_WORD);
        }

        @Override
        public void closeConnection(Connection connection) {
            try {
                connection.close();
            } catch (Exception ignore) {

            }
        }
    };
}



