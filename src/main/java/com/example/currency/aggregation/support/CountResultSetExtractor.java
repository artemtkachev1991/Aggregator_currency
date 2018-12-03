package com.example.currency.aggregation.support;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountResultSetExtractor implements ResultSetExtractor {
    @Override
    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
    }
}
