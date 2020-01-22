package edu.bit.board.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

//어노테이션을 이용한 AOP

@Aspect
public class LogAopAnnotation {
	
	@Pointcut("within(edu.bit.board.service.*)")
	private void publicM() {
		
	}
	
	@Around("publicM()")
	public Object loggerAop(ProceedingJoinPoint joinPoint) throws Throwable { //객체 안에 이 ProceedingJoinPoint 함수를 집어넣음 (aop:around 사용)
		
		String signatureStr = joinPoint.getSignature().toShortString(); //getStudentInfo(), getWorkerInfo() 함수를 끌고 옴.
		System.out.println(signatureStr + " is start");
		
		long st = System.currentTimeMillis();
		
		try {
			Object obj = joinPoint.proceed(); //실질적으로 핵심기능(getStudentInfo(), getWorkerInfo()) 함수가 실행하는 곳. 이 사이사이에 공통기능을 실행시키겠다.
			return obj;
		} finally {
			
			long et = System.currentTimeMillis();
			
			System.out.println(signatureStr + " is finished");
			System.out.println(signatureStr + " 걸리는 시간 : " + (et - st));
		}
	}
}
