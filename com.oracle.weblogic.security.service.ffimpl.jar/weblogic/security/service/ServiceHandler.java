package weblogic.security.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.shared.LoggerWrapper;

abstract class ServiceHandler implements InvocationHandler {
   protected LoggerWrapper debugLogger;
   protected SecurityServiceManagerDelegate cssm;
   protected ConcurrentHashMap serviceRealmMap = null;

   protected ServiceHandler(boolean isMapRequired, SecurityServiceManagerDelegate cssm, LoggerWrapper debugLogger) {
      this.cssm = cssm;
      this.debugLogger = debugLogger;
      if (isMapRequired) {
         this.serviceRealmMap = new ConcurrentHashMap();
      }

   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String realmName = this.getRealmName();
      Object delegate = this.getServiceObject(realmName, proxy, method, args);

      try {
         return method.invoke(delegate, args);
      } catch (InvocationTargetException var7) {
         throw var7.getCause();
      }
   }

   protected String getPartitionName() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (cic != null && !cic.isGlobalRuntime()) {
         String result = cic.getPartitionName();
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("getPartitionName: found " + result);
         }

         return result;
      } else {
         return null;
      }
   }

   protected String getRealmFromPartition(String partitionName) {
      return this.cssm.getRealmName(partitionName);
   }

   protected String getDefaultRealmName() {
      return this.cssm.getAdministrativeRealmName();
   }

   protected Object getCachedService(String realmName) {
      return this.serviceRealmMap.get(realmName);
   }

   protected Object putCachedService(String realmName, Object service) {
      return this.serviceRealmMap.putIfAbsent(realmName, service);
   }

   synchronized void flush() {
      this.serviceRealmMap.clear();
   }

   protected abstract String getRealmName();

   protected abstract Object getServiceObject(String var1, Object var2, Method var3, Object[] var4);

   static class CSSWiredServiceHandler extends WiredServiceHandler {
      private String serviceName;

      CSSWiredServiceHandler(String realmName, String serviceName, SecurityServiceManagerDelegate cssm, LoggerWrapper debugLogger) {
         super(realmName, ServiceType.SECURITY_SERVICES, cssm, debugLogger);
         this.serviceName = serviceName;
      }

      protected Object getServiceObject(String realmName, Object proxy, Method method, Object[] args) {
         Object delegate = this.service;
         if (delegate == null) {
            synchronized(this) {
               delegate = this.cssm.getCSSServiceInternal(realmName, this.serviceName);
               this.service = delegate;
            }

            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("getServiceObject: added wiring for " + realmName + " to " + delegate.toString());
            }
         }

         return delegate;
      }
   }

   static class WiredServiceHandler extends ServiceHandler {
      private SecurityService.ServiceType type;
      private String realmName;
      protected volatile Object service = null;

      WiredServiceHandler(String realmName, SecurityService.ServiceType type, SecurityServiceManagerDelegate cssm, LoggerWrapper debugLogger) {
         super(false, cssm, debugLogger);
         this.type = type;
         this.realmName = realmName;
      }

      protected Object getServiceObject(String realmName, Object proxy, Method method, Object[] args) {
         Object delegate = this.service;
         if (delegate == null) {
            synchronized(this) {
               delegate = this.cssm.getSecurityServiceInternal(realmName, this.type);
               this.service = delegate;
            }

            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("getServiceObject: added wiring for " + realmName + " to " + delegate.toString());
            }
         }

         return delegate;
      }

      protected String getRealmName() {
         return this.realmName;
      }

      synchronized void flush() {
         this.service = null;
      }
   }

   static class CSSServiceHandler extends SecurityServiceHandler {
      private String serviceName;

      CSSServiceHandler(String serviceName, SecurityServiceManagerDelegate cssm, LoggerWrapper debugLogger) {
         super(ServiceType.SECURITY_SERVICES, cssm, debugLogger);
         this.serviceName = serviceName;
      }

      protected Object getServiceObject(String realmName, Object proxy, Method method, Object[] args) {
         Object delegate = this.getCachedService(realmName);
         if (delegate == null) {
            synchronized(this) {
               delegate = this.cssm.getCSSServiceInternal(realmName, this.serviceName);
               this.putCachedService(realmName, delegate);
            }

            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("putCachedService: added mapping for " + realmName + " to " + delegate.toString());
            }
         }

         return delegate;
      }
   }

   static class SecurityServiceHandler extends ServiceHandler {
      private SecurityService.ServiceType type;

      SecurityServiceHandler(SecurityService.ServiceType type, SecurityServiceManagerDelegate cssm, LoggerWrapper debugLogger) {
         super(true, cssm, debugLogger);
         this.type = type;
      }

      protected Object getServiceObject(String realmName, Object proxy, Method method, Object[] args) {
         Object delegate = this.getCachedService(realmName);
         if (delegate == null) {
            synchronized(this) {
               delegate = this.cssm.getSecurityServiceInternal(realmName, this.type);
               this.putCachedService(realmName, delegate);
            }

            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("putCachedService: added mapping for " + realmName + " to " + delegate.toString());
            }
         }

         return delegate;
      }

      protected String getRealmName() {
         String realmName = this.getDefaultRealmName();
         String name = this.getRealmFromPartition(this.getPartitionName());
         if (name != null) {
            realmName = name;
         }

         return realmName;
      }
   }
}
