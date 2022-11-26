package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.TargetClassAware;
import com.bea.core.repackaged.springframework.aop.TargetSource;

public interface Advised extends TargetClassAware {
   boolean isFrozen();

   boolean isProxyTargetClass();

   Class[] getProxiedInterfaces();

   boolean isInterfaceProxied(Class var1);

   void setTargetSource(TargetSource var1);

   TargetSource getTargetSource();

   void setExposeProxy(boolean var1);

   boolean isExposeProxy();

   void setPreFiltered(boolean var1);

   boolean isPreFiltered();

   Advisor[] getAdvisors();

   void addAdvisor(Advisor var1) throws AopConfigException;

   void addAdvisor(int var1, Advisor var2) throws AopConfigException;

   boolean removeAdvisor(Advisor var1);

   void removeAdvisor(int var1) throws AopConfigException;

   int indexOf(Advisor var1);

   boolean replaceAdvisor(Advisor var1, Advisor var2) throws AopConfigException;

   void addAdvice(Advice var1) throws AopConfigException;

   void addAdvice(int var1, Advice var2) throws AopConfigException;

   boolean removeAdvice(Advice var1);

   int indexOf(Advice var1);

   String toProxyConfigString();
}
