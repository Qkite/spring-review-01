package com.likelion.dao_project.statement_strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {

    PreparedStatement makePreparedStrategy(Connection c) throws SQLException;
}
