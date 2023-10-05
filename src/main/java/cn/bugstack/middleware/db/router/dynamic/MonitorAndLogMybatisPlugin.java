package cn.bugstack.middleware.db.router.dynamic;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.regex.Pattern;

/**
 * @description: sql 执行过程中的   **监控** 和 日志记录
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MonitorAndLogMybatisPlugin implements Interceptor {

    private Pattern pattern = Pattern.compile("(from|into|update)[\\s]{1,}(\\w{1,})", Pattern.CASE_INSENSITIVE);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取StatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

        /*
         TODO : 是否打印sql 信息, 便于debug, 把该功能设置成一个 开关  enable: true / false, 从配置文件中读取就ok
         TODO : 慢sql 打印, slowSQL: 也可以 以配置的方式读取这个 设置的时间, 超过这个时间, 就是慢 sql ,然后 打印出来, log
         TODO : 其他的功能
         TODO : 现在的问题是, 怎么从当前类中,获取到 yml 中的配置信息
         */

        // 获取SQL
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();




        return invocation.proceed();
    }

}
