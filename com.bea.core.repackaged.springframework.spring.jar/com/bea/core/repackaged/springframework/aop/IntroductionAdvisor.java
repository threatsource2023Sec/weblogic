package com.bea.core.repackaged.springframework.aop;

public interface IntroductionAdvisor extends Advisor, IntroductionInfo {
   ClassFilter getClassFilter();

   void validateInterfaces() throws IllegalArgumentException;
}
