package cn.bugstack.middleware.db.router.annotation;

import java.lang.annotation.*;

/**
 * @description: 路由策略，分表标记
 * @author:  ego
 * @github: https://github.com/I-adore-you

 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouterStrategy {

    boolean splitTable() default false;

}
