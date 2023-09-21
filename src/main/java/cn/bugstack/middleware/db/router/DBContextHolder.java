package cn.bugstack.middleware.db.router;

/**
 * @description: 数据源上下文
 * @author: 小傅哥，微信：fustack
 * @date: 2021/9/22
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class DBContextHolder {

    private static final ThreadLocal<String> dbKey = new ThreadLocal<String>();
    private static final ThreadLocal<String> tbKey = new ThreadLocal<String>();

    // 再添加一个 判断 是否 读写 分离的 标志

    private static final ThreadLocal<Boolean> readWritre = new ThreadLocal<>();



    public static void setDBKey(String dbKeyIdx){
        dbKey.set(dbKeyIdx);
    }

    public static String getDBKey(){
        return dbKey.get();
    }

    public static void setTBKey(String tbKeyIdx){
        tbKey.set(tbKeyIdx);
    }

    public static String getTBKey(){
        return tbKey.get();
    }

    public static void clearDBKey(){
        dbKey.remove();
    }


    //增加 可以 手动 设置 读写 分离？  感觉 没有必要 ，好像也有必要？

    public static boolean getReadWrite(){
        return readWritre.get();
    }
    // 不适用 读写 分离的注解 就是 走 主ku ，使用了 读写 分离的注解就是走 从库
    public static void setReadWritre(boolean flag){
        readWritre.set(flag);
    }


    public static void clearTBKey(){
        tbKey.remove();
    }

    // 清楚读写 分离的标志
    public static void clearReadWrite(){
        readWritre.remove();
    }

}
