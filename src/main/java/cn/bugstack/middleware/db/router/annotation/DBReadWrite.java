package cn.bugstack.middleware.db.router.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD}) // 其实 控制 可以 只加在 方法上面就可以了！
public @interface DBReadWrite {


    boolean ReadWrite() default false;
}
