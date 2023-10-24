package arcus.app.common.basic.timelog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class TimeLogAspect {

  //@Trace 어노테이션이 존재하면, 해당 aspect가 걸림
  @Pointcut("@annotation(arcus.app.common.basic.timelog.Trace)")
  public void tracePointCut() {}

  @Around("tracePointCut()")
  public Object doTrace(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object[] args = joinPoint.getArgs();
    Object result = joinPoint.proceed();
    long end = System.currentTimeMillis();
    log.info("[Trace] {}, [args] {}", joinPoint.getSignature(), args);
    log.info("[Total Time] = {} ms ", end - start);
    return result;
  }
}
