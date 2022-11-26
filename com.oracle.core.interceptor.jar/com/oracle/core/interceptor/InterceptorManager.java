package com.oracle.core.interceptor;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.InterceptorMBean;
import weblogic.management.configuration.ScriptInterceptorMBean;

@Contract
public interface InterceptorManager {
   boolean isWhiteListingEnabled();

   void setWhiteListingEnabled(Boolean var1);

   void registerInterceptors(InterceptorMBean... var1);

   void registerScriptInterceptors(ScriptInterceptorMBean... var1);

   void deleteInterceptor(String var1);
}
