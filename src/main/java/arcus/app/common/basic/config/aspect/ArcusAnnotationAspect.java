package arcus.app.common.basic.config.aspect;

import com.jam2in.arcus.app.common.aop.ArcusCacheAspect;
import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ArcusAnnotationAspect extends ArcusCacheAspect {
    public ArcusAnnotationAspect(ArcusConfiguration arcusConfiguration) {
        super(arcusConfiguration);
    }

    @Pointcut("execution(* arcus.app.common.basic.*ItemService.findItem(..))))")
    public void findItem() {}

    @Around("findItem()")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        return super.around(joinPoint); }
}