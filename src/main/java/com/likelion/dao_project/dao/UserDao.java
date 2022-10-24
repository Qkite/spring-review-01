package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.ConnectionMaker;
import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import com.likelion.dao_project.domain.User;
import com.likelion.dao_project.statement_strategy.AddStatement;
import com.likelion.dao_project.statement_strategy.DeleteAllStatement;
import com.likelion.dao_project.statement_strategy.StatementStrategy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;


public class UserDao {

    private DataSource dataSource;
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> rowMapper;

    public UserDao(DataSource dataSource) {

        //this.dataSource = dataSource;
        //this.jdbcContext = new JdbcContext(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                return user;
            }
        };
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        PreparedStatement ps = null;
        Connection c = null;
        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStrategy(c);
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

    public void add(User user) throws SQLException, ClassNotFoundException {
        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?);", user.getId(), user.getName(), user.getPassword());


//        jdbcContextWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStrategy(Connection c) throws SQLException {
//                PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
//                pstmt.setString(1, user.getId());
//                pstmt.setString(2, user.getName());
//                pstmt.setString(3, user.getPassword());
//                return pstmt;
//            }
//        });
    }

    public User findById(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * from users where id = ?";
//        RowMapper<User> rowMapper = new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
//                return user;
//            }
//        };

        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);

//        Connection conn = dataSource.getConnection();
//        PreparedStatement ps = conn.prepareStatement("SELECT * from users where id = ?");
//        ps.setString(1, id);
//        ResultSet rs = ps.executeQuery();
//
//        User user = null;
//        if(rs.next()){
//            user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
//        }
//
//        rs.close();
//        ps.close();
//        conn.close();
//
//        if (user == null) throw new EmptyResultDataAccessException(1);

    }



    public void deleteAll() throws SQLException {
        //this.jdbcContext.executeSql("delete from users");
        this.jdbcTemplate.update("delete from users");

    }


    public int getCount() throws SQLException, ClassNotFoundException {
        int count = this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);

//        Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        int count = 0;
//        try {
//
//            c = dataSource.getConnection();
//            ps = c.prepareStatement("select count(*) from users");
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                count = rs.getInt(1);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//
//            if(rs != null){
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if (ps!=null){
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if (c!=null){
//                try {
//                    c.close();
//                } catch (SQLException e) {
//
//                }
//            }
//        }
        return count;
    }

    public List<User> getAll(){
        String sql = "select * from users order by id";
//        RowMapper<User> rowMapper = new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
//                return user;
//            }
        return this.jdbcTemplate.query(sql, rowMapper);
        };

}


