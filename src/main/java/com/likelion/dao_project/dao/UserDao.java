package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.ConnectionMaker;
import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import com.likelion.dao_project.domain.User;
import com.likelion.dao_project.statement_strategy.AddStatement;
import com.likelion.dao_project.statement_strategy.DeleteAllStatement;
import com.likelion.dao_project.statement_strategy.StatementStrategy;


import java.sql.*;
import java.util.Map;


public class UserDao {

    private ConnectionMaker cm;
    public UserDao() {
        this.cm = new LocalConnectionMaker();
    }

    public UserDao(ConnectionMaker cm) {
        this.cm = cm;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException {
        PreparedStatement ps = null;
        Connection c = null;
        try {
            c = cm.makeConnection();

            DeleteAllStatement deleteAllStatement = new DeleteAllStatement();
            ps = deleteAllStatement.makePreparedStrategy(c);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {

                }
            }

            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }


        }


    }

    public void add(User user) throws SQLException {
        StatementStrategy st = new AddStatement(user);
        jdbcContextWithStatementStrategy(st);
    }

    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            // DB접속 (ex sql workbeanch실행)
            c = cm.makeConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {

            c = cm.makeConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {

                }
            }
            if (ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {

                }
            }
            if (c!=null){
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }
        }
        return count;
    }

}