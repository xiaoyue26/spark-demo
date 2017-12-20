package com.xiaoyue26.www.storage;

import com.xiaoyue26.www.data.TestEntity;

import java.util.List;

/**
 * Created by xiaoyue26 on 17/12/20.
 */
public interface ITestDAO {

    void addTestEntity(TestEntity t) ;
    List<TestEntity> findAll();
}
