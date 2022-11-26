package weblogic.spring.monitoring;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.spring.monitoring.utils.ApplicationContextDelegator;

public class SpringBaseRuntimeMBeanImpl extends RuntimeMBeanDelegate {
   protected static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSpringStatistics");
   protected static final long NANOS_PER_MILLI = 1000000L;
   private static final String NAME_DELIMITER = ";";
   protected Object applicationContext;
   protected Object delegate;
   protected String beanId;
   private ApplicationContextDelegator appCtxDelegator;

   public SpringBaseRuntimeMBeanImpl(Object applicationContext, String name, boolean registerNow) throws ManagementException {
      super(generateName(applicationContext, name), false);
      this.beanId = name;
      this.applicationContext = applicationContext;
   }

   public SpringBaseRuntimeMBeanImpl(Object applicationContext, String name, RuntimeMBean parent) throws ManagementException {
      super(generateName(applicationContext, name), parent);
      this.beanId = name;
      this.applicationContext = applicationContext;
   }

   public SpringBaseRuntimeMBeanImpl(Object applicationContext, Object delegate, String name, boolean registerNow) throws ManagementException {
      super(generateName(applicationContext, name), false);
      this.delegate = delegate;
      this.beanId = name;
      this.applicationContext = applicationContext;
   }

   public SpringBaseRuntimeMBeanImpl(Object applicationContext, Object delegate, String name, RuntimeMBean parent) throws ManagementException {
      super(generateName(applicationContext, name), parent);
      this.delegate = delegate;
      this.beanId = name;
      this.applicationContext = applicationContext;
   }

   public String getBeanId() {
      return this.beanId;
   }

   public Object getApplicationContext() {
      return this.applicationContext;
   }

   public Object getDelegate() {
      return this.delegate;
   }

   public String getApplicationContextDisplayName() {
      if (this.applicationContext == null) {
         return null;
      } else {
         if (this.appCtxDelegator == null) {
            this.appCtxDelegator = new ApplicationContextDelegator(this.applicationContext);
         }

         return this.appCtxDelegator.getDisplayName();
      }
   }

   private static String generateName(Object applicationContext, String originalName) {
      if (applicationContext == null) {
         return originalName;
      } else {
         ApplicationContextDelegator appCtxDelegator = new ApplicationContextDelegator(applicationContext);
         String prefix = appCtxDelegator.getDisplayName();
         if (prefix == null) {
            prefix = appCtxDelegator.getId();
         }

         return prefix.equals(originalName) ? originalName : prefix + ";" + originalName;
      }
   }
}
