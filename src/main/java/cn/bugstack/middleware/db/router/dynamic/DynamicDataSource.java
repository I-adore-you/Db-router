package cn.bugstack.middleware.db.router.dynamic;

import cn.bugstack.middleware.db.router.DBContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @description: 动态数据源获取，每当切换数据源，都要从这个里面进行获取
 * @author: 小傅哥，微信：fustack
 * @date: 2021/9/22
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /*
     这里实现的是 数据源 的一个切换
     如果想把这里 给 理解透彻的话， 估计需要
     深入的理解一下 mybatis， jdbc  这个东西， 否则 你对这个理解
     只停留在 表面！
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return "db" + DBContextHolder.getDBKey();
    }

}
