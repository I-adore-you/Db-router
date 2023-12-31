package cn.bugstack.middleware.db.router;

import cn.bugstack.middleware.db.router.annotation.DBReadWrite;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author aodre , QQ : 480029069
 * @date 2023/8/16 15:23
 */
@Aspect
public class DBReadWriteAOP {




    @Pointcut("@annotation(cn.bugstack.middleware.db.router.annotation.DBReadWrite)")
    public void aopPoint() {
    }

    /*
      这里理解一下, 这个aop 只会拦截有 DBReadWrite , 所以DBReadWrite 不用去判 null 了
      只有用了该注解,才会走这样的逻辑
     */
    @Around("aopPoint() && @annotation(dbReadWrite)")
    public Object doReadWrite(ProceedingJoinPoint joinPoint, DBReadWrite dbReadWrite){

        // 直接 把 设置的阿 放到  指定的  threadLocal 中 就行了
        DBContextHolder.setReadWritre(dbReadWrite.ReadWrite());
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            // 执行完的 时候，把 这个 给清理掉
            DBContextHolder.clearReadWrite();
        }
        return null;
    }

}
