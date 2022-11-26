package com.oracle.batch.connector.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.management.configuration.DomainMBean;

public class BatchConfigBeanHelper {
   private AtomicBoolean initialized = new AtomicBoolean(false);
   private static ThreadLocal _adminCallerPrefix = new ThreadLocal();
   private static DomainMBean _domainMBean;

   public static void setDomainMBean(DomainMBean _domainMBean) {
      BatchConfigBeanHelper._domainMBean = _domainMBean;
   }

   public static DomainMBean getDomainMBean() {
      return _domainMBean;
   }

   public static String getAdminCallerPrefix() {
      return (String)_adminCallerPrefix.get();
   }

   public static void markCallerAdminPrefix(String prefix) {
      _adminCallerPrefix.set(prefix);
   }

   public static void resetCallerAnAdmin() {
      _adminCallerPrefix.set((Object)null);
   }
}
