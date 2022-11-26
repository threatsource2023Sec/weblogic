package weblogic.spring.monitoring;

import java.util.concurrent.ConcurrentHashMap;

public class SpringRuntimeStatisticsHolder {
   private static ConcurrentHashMap globalSpringApplicationContextRuntimeMBeans = new ConcurrentHashMap();
   private static ConcurrentHashMap globalSpringTransactionManagerRuntimeMBeans = new ConcurrentHashMap();

   public static SpringApplicationContextRuntimeMBeanImpl getGlobalSpringApplicationContextRuntimeMBeanImpl(Object bean) {
      return bean == null ? null : (SpringApplicationContextRuntimeMBeanImpl)globalSpringApplicationContextRuntimeMBeans.get(bean);
   }

   public static SpringTransactionManagerRuntimeMBeanImpl getGlobalSpringTransactionManagerRuntimeMBeanImpl(Object bean) {
      return bean == null ? null : (SpringTransactionManagerRuntimeMBeanImpl)globalSpringTransactionManagerRuntimeMBeans.get(bean);
   }

   public static void putGlobalSpringApplicationContextRuntimeMBeanImpl(Object key, SpringApplicationContextRuntimeMBeanImpl mBean) {
      globalSpringApplicationContextRuntimeMBeans.put(key, mBean);
   }

   public static void putGlobalSpringTransactionManagerRuntimeMBeanImpl(Object key, SpringTransactionManagerRuntimeMBeanImpl mBean) {
      globalSpringTransactionManagerRuntimeMBeans.put(key, mBean);
   }

   public static SpringApplicationContextRuntimeMBeanImpl removeGlobalSpringApplicationContextRuntimeMBeanImpl(Object bean) {
      return bean == null ? null : (SpringApplicationContextRuntimeMBeanImpl)globalSpringApplicationContextRuntimeMBeans.remove(bean);
   }

   public static SpringTransactionManagerRuntimeMBeanImpl removeGlobalSpringTransactionManagerRuntimeMBeanImpl(Object bean) {
      return bean == null ? null : (SpringTransactionManagerRuntimeMBeanImpl)globalSpringTransactionManagerRuntimeMBeans.remove(bean);
   }

   public static ConcurrentHashMap getGlobalSpringTransactionManagerRuntimeMBeanMap() {
      return globalSpringTransactionManagerRuntimeMBeans;
   }
}
