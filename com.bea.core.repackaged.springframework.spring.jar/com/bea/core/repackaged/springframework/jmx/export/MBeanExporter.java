package com.bea.core.repackaged.springframework.jmx.export;

import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.scope.ScopedProxyUtils;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.aop.target.LazyInitTargetSource;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.CannotLoadBeanClassException;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.SmartInitializingSingleton;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.core.Constants;
import com.bea.core.repackaged.springframework.jmx.export.assembler.AutodetectCapableMBeanInfoAssembler;
import com.bea.core.repackaged.springframework.jmx.export.assembler.MBeanInfoAssembler;
import com.bea.core.repackaged.springframework.jmx.export.assembler.SimpleReflectiveMBeanInfoAssembler;
import com.bea.core.repackaged.springframework.jmx.export.naming.KeyNamingStrategy;
import com.bea.core.repackaged.springframework.jmx.export.naming.ObjectNamingStrategy;
import com.bea.core.repackaged.springframework.jmx.export.naming.SelfNaming;
import com.bea.core.repackaged.springframework.jmx.export.notification.ModelMBeanNotificationPublisher;
import com.bea.core.repackaged.springframework.jmx.export.notification.NotificationPublisherAware;
import com.bea.core.repackaged.springframework.jmx.support.JmxUtils;
import com.bea.core.repackaged.springframework.jmx.support.MBeanRegistrationSupport;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.management.DynamicMBean;
import javax.management.JMException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.RequiredModelMBean;

public class MBeanExporter extends MBeanRegistrationSupport implements MBeanExportOperations, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, SmartInitializingSingleton, DisposableBean {
   public static final int AUTODETECT_NONE = 0;
   public static final int AUTODETECT_MBEAN = 1;
   public static final int AUTODETECT_ASSEMBLER = 2;
   public static final int AUTODETECT_ALL = 3;
   private static final String WILDCARD = "*";
   private static final String MR_TYPE_OBJECT_REFERENCE = "ObjectReference";
   private static final String CONSTANT_PREFIX_AUTODETECT = "AUTODETECT_";
   private static final Constants constants = new Constants(MBeanExporter.class);
   @Nullable
   private Map beans;
   @Nullable
   private Integer autodetectMode;
   private boolean allowEagerInit = false;
   private MBeanInfoAssembler assembler = new SimpleReflectiveMBeanInfoAssembler();
   private ObjectNamingStrategy namingStrategy = new KeyNamingStrategy();
   private boolean ensureUniqueRuntimeObjectNames = true;
   private boolean exposeManagedResourceClassLoader = true;
   private Set excludedBeans = new HashSet();
   @Nullable
   private MBeanExporterListener[] listeners;
   @Nullable
   private NotificationListenerBean[] notificationListeners;
   private final Map registeredNotificationListeners = new LinkedHashMap();
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private ListableBeanFactory beanFactory;

   public void setBeans(Map beans) {
      this.beans = beans;
   }

   public void setAutodetect(boolean autodetect) {
      this.autodetectMode = autodetect ? 3 : 0;
   }

   public void setAutodetectMode(int autodetectMode) {
      if (!constants.getValues("AUTODETECT_").contains(autodetectMode)) {
         throw new IllegalArgumentException("Only values of autodetect constants allowed");
      } else {
         this.autodetectMode = autodetectMode;
      }
   }

   public void setAutodetectModeName(String constantName) {
      if (!constantName.startsWith("AUTODETECT_")) {
         throw new IllegalArgumentException("Only autodetect constants allowed");
      } else {
         this.autodetectMode = (Integer)constants.asNumber(constantName);
      }
   }

   public void setAllowEagerInit(boolean allowEagerInit) {
      this.allowEagerInit = allowEagerInit;
   }

   public void setAssembler(MBeanInfoAssembler assembler) {
      this.assembler = assembler;
   }

   public void setNamingStrategy(ObjectNamingStrategy namingStrategy) {
      this.namingStrategy = namingStrategy;
   }

   public void setEnsureUniqueRuntimeObjectNames(boolean ensureUniqueRuntimeObjectNames) {
      this.ensureUniqueRuntimeObjectNames = ensureUniqueRuntimeObjectNames;
   }

   public void setExposeManagedResourceClassLoader(boolean exposeManagedResourceClassLoader) {
      this.exposeManagedResourceClassLoader = exposeManagedResourceClassLoader;
   }

   public void setExcludedBeans(String... excludedBeans) {
      this.excludedBeans.clear();
      Collections.addAll(this.excludedBeans, excludedBeans);
   }

   public void addExcludedBean(String excludedBean) {
      Assert.notNull(excludedBean, (String)"ExcludedBean must not be null");
      this.excludedBeans.add(excludedBean);
   }

   public void setListeners(MBeanExporterListener... listeners) {
      this.listeners = listeners;
   }

   public void setNotificationListeners(NotificationListenerBean... notificationListeners) {
      this.notificationListeners = notificationListeners;
   }

   public void setNotificationListenerMappings(Map listeners) {
      Assert.notNull(listeners, (String)"'listeners' must not be null");
      List notificationListeners = new ArrayList(listeners.size());
      listeners.forEach((key, listener) -> {
         NotificationListenerBean bean = new NotificationListenerBean(listener);
         if (key != null && !"*".equals(key)) {
            bean.setMappedObjectName(key);
         }

         notificationListeners.add(bean);
      });
      this.notificationListeners = (NotificationListenerBean[])notificationListeners.toArray(new NotificationListenerBean[0]);
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (beanFactory instanceof ListableBeanFactory) {
         this.beanFactory = (ListableBeanFactory)beanFactory;
      } else {
         this.logger.debug("MBeanExporter not running in a ListableBeanFactory: autodetection of MBeans not available.");
      }

   }

   public void afterPropertiesSet() {
      if (this.server == null) {
         this.server = JmxUtils.locateMBeanServer();
      }

   }

   public void afterSingletonsInstantiated() {
      try {
         this.logger.debug("Registering beans for JMX exposure on startup");
         this.registerBeans();
         this.registerNotificationListeners();
      } catch (RuntimeException var2) {
         this.unregisterNotificationListeners();
         this.unregisterBeans();
         throw var2;
      }
   }

   public void destroy() {
      this.logger.debug("Unregistering JMX-exposed beans on shutdown");
      this.unregisterNotificationListeners();
      this.unregisterBeans();
   }

   public ObjectName registerManagedResource(Object managedResource) throws MBeanExportException {
      Assert.notNull(managedResource, "Managed resource must not be null");

      ObjectName objectName;
      try {
         objectName = this.getObjectName(managedResource, (String)null);
         if (this.ensureUniqueRuntimeObjectNames) {
            objectName = JmxUtils.appendIdentityToObjectName(objectName, managedResource);
         }
      } catch (Throwable var4) {
         throw new MBeanExportException("Unable to generate ObjectName for MBean [" + managedResource + "]", var4);
      }

      this.registerManagedResource(managedResource, objectName);
      return objectName;
   }

   public void registerManagedResource(Object managedResource, ObjectName objectName) throws MBeanExportException {
      Assert.notNull(managedResource, "Managed resource must not be null");
      Assert.notNull(objectName, (String)"ObjectName must not be null");

      try {
         if (this.isMBean(managedResource.getClass())) {
            this.doRegister(managedResource, objectName);
         } else {
            ModelMBean mbean = this.createAndConfigureMBean(managedResource, managedResource.getClass().getName());
            this.doRegister(mbean, objectName);
            this.injectNotificationPublisherIfNecessary(managedResource, mbean, objectName);
         }

      } catch (JMException var4) {
         throw new UnableToRegisterMBeanException("Unable to register MBean [" + managedResource + "] with object name [" + objectName + "]", var4);
      }
   }

   public void unregisterManagedResource(ObjectName objectName) {
      Assert.notNull(objectName, (String)"ObjectName must not be null");
      this.doUnregister(objectName);
   }

   protected void registerBeans() {
      if (this.beans == null) {
         this.beans = new HashMap();
         if (this.autodetectMode == null) {
            this.autodetectMode = 3;
         }
      }

      int mode = this.autodetectMode != null ? this.autodetectMode : 0;
      if (mode != 0) {
         if (this.beanFactory == null) {
            throw new MBeanExportException("Cannot autodetect MBeans if not running in a BeanFactory");
         }

         if (mode == 1 || mode == 3) {
            this.logger.debug("Autodetecting user-defined JMX MBeans");
            this.autodetect(this.beans, (beanClass, beanName) -> {
               return this.isMBean(beanClass);
            });
         }

         if ((mode == 2 || mode == 3) && this.assembler instanceof AutodetectCapableMBeanInfoAssembler) {
            AutodetectCapableMBeanInfoAssembler var10002 = (AutodetectCapableMBeanInfoAssembler)this.assembler;
            this.autodetect(this.beans, var10002::includeBean);
         }
      }

      if (!this.beans.isEmpty()) {
         this.beans.forEach((beanName, instance) -> {
            this.registerBeanNameOrInstance(instance, beanName);
         });
      }

   }

   protected boolean isBeanDefinitionLazyInit(ListableBeanFactory beanFactory, String beanName) {
      return beanFactory instanceof ConfigurableListableBeanFactory && beanFactory.containsBeanDefinition(beanName) && ((ConfigurableListableBeanFactory)beanFactory).getBeanDefinition(beanName).isLazyInit();
   }

   protected ObjectName registerBeanNameOrInstance(Object mapValue, String beanKey) throws MBeanExportException {
      try {
         if (mapValue instanceof String) {
            if (this.beanFactory == null) {
               throw new MBeanExportException("Cannot resolve bean names if not running in a BeanFactory");
            } else {
               String beanName = (String)mapValue;
               if (this.isBeanDefinitionLazyInit(this.beanFactory, beanName)) {
                  ObjectName objectName = this.registerLazyInit(beanName, beanKey);
                  this.replaceNotificationListenerBeanNameKeysIfNecessary(beanName, objectName);
                  return objectName;
               } else {
                  Object bean = this.beanFactory.getBean(beanName);
                  ObjectName objectName = this.registerBeanInstance(bean, beanKey);
                  this.replaceNotificationListenerBeanNameKeysIfNecessary(beanName, objectName);
                  return objectName;
               }
            }
         } else {
            if (this.beanFactory != null) {
               Map beansOfSameType = this.beanFactory.getBeansOfType(mapValue.getClass(), false, this.allowEagerInit);
               Iterator var4 = beansOfSameType.entrySet().iterator();

               while(var4.hasNext()) {
                  Map.Entry entry = (Map.Entry)var4.next();
                  if (entry.getValue() == mapValue) {
                     String beanName = (String)entry.getKey();
                     ObjectName objectName = this.registerBeanInstance(mapValue, beanKey);
                     this.replaceNotificationListenerBeanNameKeysIfNecessary(beanName, objectName);
                     return objectName;
                  }
               }
            }

            return this.registerBeanInstance(mapValue, beanKey);
         }
      } catch (Throwable var8) {
         throw new UnableToRegisterMBeanException("Unable to register MBean [" + mapValue + "] with key '" + beanKey + "'", var8);
      }
   }

   private void replaceNotificationListenerBeanNameKeysIfNecessary(String beanName, ObjectName objectName) {
      if (this.notificationListeners != null) {
         NotificationListenerBean[] var3 = this.notificationListeners;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            NotificationListenerBean notificationListener = var3[var5];
            notificationListener.replaceObjectName(beanName, objectName);
         }
      }

   }

   private ObjectName registerBeanInstance(Object bean, String beanKey) throws JMException {
      ObjectName objectName = this.getObjectName(bean, beanKey);
      Object mbeanToExpose = null;
      if (this.isMBean(bean.getClass())) {
         mbeanToExpose = bean;
      } else {
         DynamicMBean adaptedBean = this.adaptMBeanIfPossible(bean);
         if (adaptedBean != null) {
            mbeanToExpose = adaptedBean;
         }
      }

      if (mbeanToExpose != null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Located MBean '" + beanKey + "': registering with JMX server as MBean [" + objectName + "]");
         }

         this.doRegister(mbeanToExpose, objectName);
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Located managed bean '" + beanKey + "': registering with JMX server as MBean [" + objectName + "]");
         }

         ModelMBean mbean = this.createAndConfigureMBean(bean, beanKey);
         this.doRegister(mbean, objectName);
         this.injectNotificationPublisherIfNecessary(bean, mbean, objectName);
      }

      return objectName;
   }

   private ObjectName registerLazyInit(String beanName, String beanKey) throws JMException {
      Assert.state(this.beanFactory != null, "No BeanFactory set");
      ProxyFactory proxyFactory = new ProxyFactory();
      proxyFactory.setProxyTargetClass(true);
      proxyFactory.setFrozen(true);
      Object proxy;
      ObjectName objectName;
      if (this.isMBean(this.beanFactory.getType(beanName))) {
         LazyInitTargetSource targetSource = new LazyInitTargetSource();
         targetSource.setTargetBeanName(beanName);
         targetSource.setBeanFactory(this.beanFactory);
         proxyFactory.setTargetSource(targetSource);
         proxy = proxyFactory.getProxy(this.beanClassLoader);
         objectName = this.getObjectName(proxy, beanKey);
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Located MBean '" + beanKey + "': registering with JMX server as lazy-init MBean [" + objectName + "]");
         }

         this.doRegister(proxy, objectName);
         return objectName;
      } else {
         NotificationPublisherAwareLazyTargetSource targetSource = new NotificationPublisherAwareLazyTargetSource();
         targetSource.setTargetBeanName(beanName);
         targetSource.setBeanFactory(this.beanFactory);
         proxyFactory.setTargetSource(targetSource);
         proxy = proxyFactory.getProxy(this.beanClassLoader);
         objectName = this.getObjectName(proxy, beanKey);
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Located simple bean '" + beanKey + "': registering with JMX server as lazy-init MBean [" + objectName + "]");
         }

         ModelMBean mbean = this.createAndConfigureMBean(proxy, beanKey);
         targetSource.setModelMBean(mbean);
         targetSource.setObjectName(objectName);
         this.doRegister(mbean, objectName);
         return objectName;
      }
   }

   protected ObjectName getObjectName(Object bean, @Nullable String beanKey) throws MalformedObjectNameException {
      return bean instanceof SelfNaming ? ((SelfNaming)bean).getObjectName() : this.namingStrategy.getObjectName(bean, beanKey);
   }

   protected boolean isMBean(@Nullable Class beanClass) {
      return JmxUtils.isMBean(beanClass);
   }

   @Nullable
   protected DynamicMBean adaptMBeanIfPossible(Object bean) throws JMException {
      Class targetClass = AopUtils.getTargetClass(bean);
      if (targetClass != bean.getClass()) {
         Class ifc = JmxUtils.getMXBeanInterface(targetClass);
         if (ifc != null) {
            if (!ifc.isInstance(bean)) {
               throw new NotCompliantMBeanException("Managed bean [" + bean + "] has a target class with an MXBean interface but does not expose it in the proxy");
            }

            return new StandardMBean(bean, ifc, true);
         }

         ifc = JmxUtils.getMBeanInterface(targetClass);
         if (ifc != null) {
            if (!ifc.isInstance(bean)) {
               throw new NotCompliantMBeanException("Managed bean [" + bean + "] has a target class with an MBean interface but does not expose it in the proxy");
            }

            return new StandardMBean(bean, ifc);
         }
      }

      return null;
   }

   protected ModelMBean createAndConfigureMBean(Object managedResource, String beanKey) throws MBeanExportException {
      try {
         ModelMBean mbean = this.createModelMBean();
         mbean.setModelMBeanInfo(this.getMBeanInfo(managedResource, beanKey));
         mbean.setManagedResource(managedResource, "ObjectReference");
         return mbean;
      } catch (Throwable var4) {
         throw new MBeanExportException("Could not create ModelMBean for managed resource [" + managedResource + "] with key '" + beanKey + "'", var4);
      }
   }

   protected ModelMBean createModelMBean() throws MBeanException {
      return (ModelMBean)(this.exposeManagedResourceClassLoader ? new SpringModelMBean() : new RequiredModelMBean());
   }

   private ModelMBeanInfo getMBeanInfo(Object managedBean, String beanKey) throws JMException {
      ModelMBeanInfo info = this.assembler.getMBeanInfo(managedBean, beanKey);
      if (this.logger.isInfoEnabled() && ObjectUtils.isEmpty((Object[])info.getAttributes()) && ObjectUtils.isEmpty((Object[])info.getOperations())) {
         this.logger.info("Bean with key '" + beanKey + "' has been registered as an MBean but has no exposed attributes or operations");
      }

      return info;
   }

   private void autodetect(Map beans, AutodetectCallback callback) {
      Assert.state(this.beanFactory != null, "No BeanFactory set");
      Set beanNames = new LinkedHashSet(this.beanFactory.getBeanDefinitionCount());
      Collections.addAll(beanNames, this.beanFactory.getBeanDefinitionNames());
      if (this.beanFactory instanceof ConfigurableBeanFactory) {
         Collections.addAll(beanNames, ((ConfigurableBeanFactory)this.beanFactory).getSingletonNames());
      }

      Iterator var4 = beanNames.iterator();

      while(true) {
         String beanName;
         do {
            do {
               if (!var4.hasNext()) {
                  return;
               }

               beanName = (String)var4.next();
            } while(this.isExcluded(beanName));
         } while(this.isBeanDefinitionAbstract(this.beanFactory, beanName));

         try {
            Class beanClass = this.beanFactory.getType(beanName);
            if (beanClass != null && callback.include(beanClass, beanName)) {
               boolean lazyInit = this.isBeanDefinitionLazyInit(this.beanFactory, beanName);
               Object beanInstance = null;
               if (!lazyInit) {
                  beanInstance = this.beanFactory.getBean(beanName);
                  if (!beanClass.isInstance(beanInstance)) {
                     continue;
                  }
               }

               if (!ScopedProxyUtils.isScopedTarget(beanName) && !beans.containsValue(beanName) && (beanInstance == null || !CollectionUtils.containsInstance(beans.values(), beanInstance))) {
                  beans.put(beanName, beanInstance != null ? beanInstance : beanName);
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("Bean with name '" + beanName + "' has been autodetected for JMX exposure");
                  }
               } else if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Bean with name '" + beanName + "' is already registered for JMX exposure");
               }
            }
         } catch (CannotLoadBeanClassException var9) {
            if (this.allowEagerInit) {
               throw var9;
            }
         }
      }
   }

   private boolean isExcluded(String beanName) {
      return this.excludedBeans.contains(beanName) || beanName.startsWith("&") && this.excludedBeans.contains(beanName.substring("&".length()));
   }

   private boolean isBeanDefinitionAbstract(ListableBeanFactory beanFactory, String beanName) {
      return beanFactory instanceof ConfigurableListableBeanFactory && beanFactory.containsBeanDefinition(beanName) && ((ConfigurableListableBeanFactory)beanFactory).getBeanDefinition(beanName).isAbstract();
   }

   private void injectNotificationPublisherIfNecessary(Object managedResource, @Nullable ModelMBean modelMBean, @Nullable ObjectName objectName) {
      if (managedResource instanceof NotificationPublisherAware && modelMBean != null && objectName != null) {
         ((NotificationPublisherAware)managedResource).setNotificationPublisher(new ModelMBeanNotificationPublisher(modelMBean, objectName, managedResource));
      }

   }

   private void registerNotificationListeners() throws MBeanExportException {
      if (this.notificationListeners != null) {
         Assert.state(this.server != null, "No MBeanServer available");
         NotificationListenerBean[] var1 = this.notificationListeners;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            NotificationListenerBean bean = var1[var3];

            try {
               ObjectName[] mappedObjectNames = bean.getResolvedObjectNames();
               if (mappedObjectNames == null) {
                  mappedObjectNames = this.getRegisteredObjectNames();
               }

               if (this.registeredNotificationListeners.put(bean, mappedObjectNames) == null) {
                  ObjectName[] var6 = mappedObjectNames;
                  int var7 = mappedObjectNames.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     ObjectName mappedObjectName = var6[var8];
                     this.server.addNotificationListener(mappedObjectName, bean.getNotificationListener(), bean.getNotificationFilter(), bean.getHandback());
                  }
               }
            } catch (Throwable var10) {
               throw new MBeanExportException("Unable to register NotificationListener", var10);
            }
         }
      }

   }

   private void unregisterNotificationListeners() {
      if (this.server != null) {
         this.registeredNotificationListeners.forEach((bean, mappedObjectNames) -> {
            ObjectName[] var3 = mappedObjectNames;
            int var4 = mappedObjectNames.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ObjectName mappedObjectName = var3[var5];

               try {
                  this.server.removeNotificationListener(mappedObjectName, bean.getNotificationListener(), bean.getNotificationFilter(), bean.getHandback());
               } catch (Throwable var8) {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("Unable to unregister NotificationListener", var8);
                  }
               }
            }

         });
      }

      this.registeredNotificationListeners.clear();
   }

   protected void onRegister(ObjectName objectName) {
      this.notifyListenersOfRegistration(objectName);
   }

   protected void onUnregister(ObjectName objectName) {
      this.notifyListenersOfUnregistration(objectName);
   }

   private void notifyListenersOfRegistration(ObjectName objectName) {
      if (this.listeners != null) {
         MBeanExporterListener[] var2 = this.listeners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MBeanExporterListener listener = var2[var4];
            listener.mbeanRegistered(objectName);
         }
      }

   }

   private void notifyListenersOfUnregistration(ObjectName objectName) {
      if (this.listeners != null) {
         MBeanExporterListener[] var2 = this.listeners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MBeanExporterListener listener = var2[var4];
            listener.mbeanUnregistered(objectName);
         }
      }

   }

   private class NotificationPublisherAwareLazyTargetSource extends LazyInitTargetSource {
      @Nullable
      private ModelMBean modelMBean;
      @Nullable
      private ObjectName objectName;

      private NotificationPublisherAwareLazyTargetSource() {
      }

      public void setModelMBean(ModelMBean modelMBean) {
         this.modelMBean = modelMBean;
      }

      public void setObjectName(ObjectName objectName) {
         this.objectName = objectName;
      }

      @Nullable
      public Object getTarget() {
         try {
            return super.getTarget();
         } catch (RuntimeException var2) {
            if (this.logger.isInfoEnabled()) {
               this.logger.info("Failed to retrieve target for JMX-exposed bean [" + this.objectName + "]: " + var2);
            }

            throw var2;
         }
      }

      protected void postProcessTargetObject(Object targetObject) {
         MBeanExporter.this.injectNotificationPublisherIfNecessary(targetObject, this.modelMBean, this.objectName);
      }

      // $FF: synthetic method
      NotificationPublisherAwareLazyTargetSource(Object x1) {
         this();
      }
   }

   @FunctionalInterface
   private interface AutodetectCallback {
      boolean include(Class var1, String var2);
   }
}
