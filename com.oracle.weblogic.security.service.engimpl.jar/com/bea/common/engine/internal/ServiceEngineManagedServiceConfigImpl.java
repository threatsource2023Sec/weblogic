package com.bea.common.engine.internal;

import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import java.util.HashSet;
import java.util.Set;

public class ServiceEngineManagedServiceConfigImpl extends BaseServiceConfigImpl implements ServiceEngineManagedServiceConfig {
   private String className = null;
   private String classLoaderName = null;
   private Object config = null;
   private Set dependencies = null;

   ServiceEngineManagedServiceConfigImpl(String serviceName, String className, boolean exposedToEnvironment, String serviceLoggingName) throws IllegalArgumentException {
      super(serviceName, exposedToEnvironment, serviceLoggingName);
      if (className == null) {
         throw new IllegalArgumentException();
      } else {
         this.className = className;
      }
   }

   public synchronized void setClassLoader(String classLoaderName) {
      this.failIfConfigLockedDown();
      this.classLoaderName = classLoaderName;
   }

   public synchronized void addDependency(String dependentServiceName) {
      if (dependentServiceName == null) {
         throw new IllegalArgumentException();
      } else {
         this.failIfConfigLockedDown();
         if (this.dependencies == null) {
            this.dependencies = new HashSet();
         }

         this.dependencies.add(dependentServiceName);
      }
   }

   public synchronized void setConfig(Object config) {
      this.failIfConfigLockedDown();
      this.config = config;
   }

   synchronized String getClassName() {
      return this.className;
   }

   synchronized String getClassLoaderName() {
      return this.classLoaderName;
   }

   synchronized String[] getDependentServiceNames() {
      return this.dependencies == null ? null : (String[])((String[])this.dependencies.toArray(new String[this.dependencies.size()]));
   }

   synchronized Object getConfig() {
      return this.config;
   }
}
