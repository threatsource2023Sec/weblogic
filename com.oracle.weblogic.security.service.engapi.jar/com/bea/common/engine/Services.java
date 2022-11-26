package com.bea.common.engine;

public interface Services {
   Object getService(String var1) throws ServiceInitializationException, ServiceNotFoundException;

   String getServiceLoggingName(String var1) throws ServiceNotFoundException;

   Object getServiceManagementObject(String var1) throws ServiceInitializationException, ServiceNotFoundException;
}
