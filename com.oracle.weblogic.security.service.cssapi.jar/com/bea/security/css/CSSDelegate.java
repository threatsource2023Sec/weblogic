package com.bea.security.css;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.service.IdentityService;

public interface CSSDelegate {
   Object getService(String var1) throws ServiceInitializationException, ServiceNotFoundException;

   String getServiceLoggingName(String var1) throws ServiceNotFoundException;

   Object getServiceManagementObject(String var1) throws ServiceInitializationException, ServiceNotFoundException;

   void shutdown();

   void initialize(CSSConfig var1, ClassLoader var2, IdentityService var3, LoggerService var4) throws CSSConfigurationException;
}
