package com.bea.core.repackaged.springframework.aop;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ProxyMethodInvocation extends MethodInvocation {
   Object getProxy();

   MethodInvocation invocableClone();

   MethodInvocation invocableClone(Object... var1);

   void setArguments(Object... var1);

   void setUserAttribute(String var1, @Nullable Object var2);

   @Nullable
   Object getUserAttribute(String var1);
}
