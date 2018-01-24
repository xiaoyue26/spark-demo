package com.xiaoyue26.www.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.SetDataBuilder;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by xiaoyue26 on 18/1/3.
 */
public class ZookeeperIO implements Serializable{
    private CuratorFramework client;

    public void connect(String connectionString) {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        client.start();
    }

    public boolean exist(String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        if (stat != null)
            return true;
        else
            return false;
    }

    public void create(String path) throws Exception {
        if (exist(path)) {
            return;
        }

        NodeCache cache = new NodeCache(client, path, false);
        cache.start(true);
        final AtomicBoolean signal = new AtomicBoolean(false);
        NodeCacheListener listener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                signal.set(true);
            }
        };
        cache.getListenable().addListener(listener);

        client.create().creatingParentsIfNeeded().forPath(path);

        for (int i = 0; i < 100 && signal.get() == false; i++) {
            Thread.sleep(100);
        }
        cache.getListenable().removeListener(listener);
        if (signal.get() == false) {
            throw new RuntimeException("zookeeper create failed");
        }
    }

    public void setData(String path, byte[] payload) throws Exception {
        if (!exist(path)) {
            throw new RuntimeException("zookeeper setData failed: path not exist");
        }

        NodeCache cache = new NodeCache(client, path, false);
        cache.start(true);
        final AtomicBoolean signal = new AtomicBoolean(false);
        NodeCacheListener listener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                signal.set(true);
            }
        };
        cache.getListenable().addListener(listener);

        SetDataBuilder sb = client.setData();
        sb.forPath(path, payload);

        for (int i = 0; i < 10000 && signal.get() == false; i++) {
            Thread.sleep(100);
        }
        cache.getListenable().removeListener(listener);
        if (signal.get() == false) {
            throw new RuntimeException("zookeeper set failed");
        }
    }

    public void delete(String path) throws Exception {
        client.delete().guaranteed().forPath(path);
    }

    public byte[] getData(String path) throws Exception {
        return client.getData().forPath(path);
    }

    public List<String> getChildren(String path) throws Exception {
        return client.getChildren().forPath(path);
    }

    public void close() {
        client.close();
    }

    public CuratorFramework getClient() {
        return client;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }


    public static void main(String[] args) throws Exception {
        final ZookeeperIO zk = new ZookeeperIO();
        zk.connect("dx-pipe-zk1-online:2181,dx-pipe-zk2-online:2181,dx-pipe-zk3-online:2181");
        byte[] bytes = zk.getData("/consumers/test/offsets/anotherTest/0");
        System.out.println(new String(bytes));
        System.out.println("work finished");
    }
}
