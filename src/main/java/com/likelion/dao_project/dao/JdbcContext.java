package com.likelion.dao_project.dao;

import com.likelion.dao_project.statement_strategy.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement pstmt = null;
        try {
            c = dataSource.getConnection();
            pstmt = stmt.makePreparedStrategy(c);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
    }

    public void executeSql(String sql) throws SQLException {
        this.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStrategy(Connection c) throws SQLException {
                return c.prepareStatement(sql);
            }
        });
    }
}
