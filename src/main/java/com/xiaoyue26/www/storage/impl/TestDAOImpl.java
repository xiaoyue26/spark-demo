package com.xiaoyue26.www.storage.impl;

import com.xiaoyue26.www.data.TestEntity;
import com.xiaoyue26.www.storage.ITestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by xiaoyue26 on 17/12/20.
 */
@Repository
public class TestDAOImpl extends JdbcDaoSupport implements ITestDAO {

    @Autowired
    public void setDs(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public void addTestEntity(TestEntity t) {
        String sql = "replace into test values(?,?,?)";
        this.getJdbcTemplate().update(sql, t.getDt(), t.getCol1(),t.getCol2());
    }

    @Override
    public List<TestEntity> findAll() {
        String sql = "select * from test";
        return this.getJdbcTemplate().query(sql, TEST_ROW_MAPPER);
    }

    private static final RowMapper<TestEntity> TEST_ROW_MAPPER = (rs, rownum) -> {
        TestEntity t=new TestEntity();
        t.setDt(rs.getString("dt"));
        t.setCol1(rs.getInt("col1"));
        t.setCol2(rs.getString("col2"));
        return t;
    };
}
