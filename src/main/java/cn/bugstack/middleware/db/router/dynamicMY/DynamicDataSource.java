package cn.bugstack.middleware.db.router.dynamicMY;

import cn.bugstack.middleware.db.router.DBRouterConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;


public class DynamicDataSource extends AbstractRoutingDataSource {

    private static int MOD;  // 进行 取模用的

    /*
     需要 把这个 给注入进来
     */
    @Autowired
    private DBRouterConfig dbRouterConfig;


    /*
     这里实现的是 数据源 的一个切换
     如果想把这里 给 理解透彻的话， 估计需要
     深入的理解一下 mybatis， jdbc  这个东西， 否则 你对这个理解
     只停留在 表面！
     */
    @Override
    protected Object determineCurrentLookupKey() { // 如果 涉及到 读写 分离 ， 那下面的代码 放到 这里 就不太正确了， 应该 放到 抽象父类里面， 这里先放在 这里吧
        /*
         添加 读写分离的 功能 再这里 进行 判断一下 ， 如果 设置 了 读写分离的 标志
         这里 直接 让走 slave 库 ， 这里 定义一个  全局 成员变量  MOD
         */

        String masterName = "db" + DBContextHolder.getDBKey(); // 这里得到的是  masterName
        List<DataSource> dataSources = super.targetSlaveDataSource.get(masterName); // 这里 是根据 masterName 得到其从库

        // 这会不会存在 线程安全问题， 多个线程  同时 ，来去调用这个方法 , 感觉是存在 线程安全问题的
        int index = MOD % dataSources.size();

        DataSource dataSource = dataSources.get(index); //  这得到的 就是  所选择的  slave 库
        MOD++;
        return masterName;
    }

    /*
    -
        父类的属性 声明的是 private
        根本访问不到， 所以 实现该功能的话，就不能依赖 AbstractRoutingDataSource 这个东西了
        必须 像 **baomidou** 那样直接写一个 。

        -
        这里 还需要注意的一个点是， 这里 的 数据源的 配置 可能会发生变化
        所以  DBRouterConfig 这里面 关于 库 和 表的 数量 信息 都要 发生变化

     */
    public boolean addDatasource(Object datdSourceName,DataSource dataSource){
        // 可以借鉴 baomidou 的做法 ，  这个是可以实现的！
        try {

            super.targetDataSources.put(datdSourceName,dataSource);
            // 下面的代码 ，在并发的情况下， 存在线程安全问题， 可以 用 synchronized 代码块， 上面的 有 concurrentHashMap 保证线程安全问题
            // 也可以直接 将这两段 代码 放到同一个 代码， 然后 直接 就用 HashMap 就行了
            dbRouterConfig.setDbCount(dbRouterConfig.getDbCount() + 1);
//            dbRouterConfig.setTbCount(dbRouterConfig.getTbCount() + 1); // 一个 库中 分表的数量尽量统一 ， 这里 就不用设置 这个了

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
