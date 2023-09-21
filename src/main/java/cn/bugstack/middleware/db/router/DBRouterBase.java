package cn.bugstack.middleware.db.router;

/**
 * @description: 数据源基础配置
 * @author:  ego

 * @github: https://github.com/I-adore-you

 */
public class DBRouterBase {

    private String tbIdx;

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }

}
