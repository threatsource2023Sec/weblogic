package weblogic.management.provider.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.internal.mbean.BeanInfoImpl;
import weblogic.management.provider.BeanInfoAccess;
import weblogic.management.provider.BeanInfoKey;
import weblogic.management.provider.BeanInfoRegistration;
import weblogic.utils.cmm.MemoryPressureListener;
import weblogic.utils.cmm.MemoryPressureService;
import weblogic.utils.cmm.Scrubber;
import weblogic.utils.codegen.ImplementationFactory;
import weblogic.utils.codegen.RoleInfoImplementationFactory;

public class BeanInfoAccessImpl implements BeanInfoAccess, BeanInfoRegistration, Scrubber {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private Map beanInfos = new Hashtable(256);
   private Map beanInfoFactoryToLoader = Collections.synchronizedMap(new HashMap(10));
   private BeanInfoFactoryList beanInfoFactories = new BeanInfoFactoryList();
   private BeanInterfaceClasses beanInterfaceClasses = new BeanInterfaceClasses();
   private static long FiveMinutes = 300000L;
   private Map disabledProperties = new HashMap();
   private Map beanPropertyMapByBeanInfo = Collections.synchronizedMap(new HashMap());

   public void disableProperty(String mbeanInterfaceName, String... propertyName) {
      Set disabled = (Set)this.disabledProperties.get(mbeanInterfaceName);
      if (disabled == null) {
         disabled = new HashSet();
         this.disabledProperties.put(mbeanInterfaceName, disabled);
      }

      String[] var4 = propertyName;
      int var5 = propertyName.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String p = var4[var6];
         ((Set)disabled).add(p);
      }

   }

   public void enableProperty(String mbeanInterfaceName, String... propertyName) {
      Set disabled = (Set)this.disabledProperties.get(mbeanInterfaceName);
      if (disabled != null) {
         String[] var4 = propertyName;
         int var5 = propertyName.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String p = var4[var6];
            disabled.remove(p);
         }

         if (disabled.size() == 0) {
            this.disabledProperties.remove(mbeanInterfaceName);
         }

      }
   }

   public void setupMemoryListeners(ServiceLocator locator) {
      ServiceLocatorUtilities.addOneConstant(locator, this, (String)null, new Type[]{Scrubber.class});
      MemoryPressureListener ml = new MemoryPressureListener() {
         public void handleCMMLevel(int newLevel) {
            if (newLevel > 2) {
               BeanInfoAccessImpl.this.releaseAllBeanInfos();
            }

         }
      };

      try {
         MemoryPressureService memoryPressureService = (MemoryPressureService)locator.getService(MemoryPressureService.class, new Annotation[0]);
         memoryPressureService.registerListener(this.getClass().getName(), ml);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("registered memory listeners for BeanInfoAccessImpl.");
         }

      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public void registerBeanInfoFactoryClass(String className, ClassLoader ldr) {
      if (ldr == null) {
         ldr = this.getClass().getClassLoader();
      }

      this.beanInfoFactoryToLoader.put(className, ldr);
      if (this.beanInfoFactories.loadedFactories()) {
         this.beanInfoFactories.reloadFactories();
      }

   }

   public void discoverBeanInfoFactories(ClassLoader classLoader) {
      try {
         Enumeration urls = classLoader.getResources("META-INF/beaninfofactory.txt");

         while(urls.hasMoreElements()) {
            URL next = (URL)urls.nextElement();
            BufferedReader reader = null;

            try {
               reader = new BufferedReader(new InputStreamReader(next.openStream()));

               String factoryClassName;
               while((factoryClassName = reader.readLine()) != null) {
                  factoryClassName = factoryClassName.trim();
                  if (factoryClassName.length() != 0) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Discovered factory class name " + factoryClassName);
                     }

                     this.registerBeanInfoFactoryClass(factoryClassName, classLoader);
                  }
               }
            } catch (IOException var10) {
               throw var10;
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }
         }

      } catch (IOException var12) {
         throw new AssertionError("Unable to acquire BeanInfoFactories");
      }
   }

   public BeanInfoKey getBeanInfoKeyForInstance(Object instance, boolean readOnly, String version) {
      String beanInfoClassName = this.getBeanInfoClassName(instance.getClass());
      Class beanInfoClass = this.getBeanInfoClass(instance);
      return new BeanInfoKeyImpl(beanInfoClassName, readOnly, version, beanInfoClass);
   }

   public BeanInfo getBeanInfoForInstance(Object instance, boolean readOnly, String version) {
      BeanInfoKey key = this.getBeanInfoKeyForInstance(instance, readOnly, version);
      return this.getBeanInfo(key);
   }

   private Class getBeanInfoClass(Object instance) {
      String beanInfoClassName = this.getBeanInfoClassName(instance.getClass());
      Class instanceClass = instance.getClass();
      Class beanInfoClass = null;
      ClassNotFoundException save = null;

      while(beanInfoClass == null && instanceClass != null) {
         try {
            ClassLoader cl = instanceClass.getClassLoader();
            if (cl == null) {
               break;
            }

            beanInfoClass = cl.loadClass(beanInfoClassName);
         } catch (ClassNotFoundException var7) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Could not load beaninfo class " + beanInfoClassName + ", ignoring class not found exception", var7);
            }

            if (save == null) {
               save = var7;
            }

            instanceClass = instanceClass.getSuperclass();
            beanInfoClassName = this.getBeanInfoClassName(instanceClass);
         }
      }

      if (beanInfoClass == null) {
         if (save != null) {
            throw new RuntimeException("Unable to acquire bean info for " + instance, save);
         } else {
            throw new RuntimeException("Unable to acquire bean info for " + instance);
         }
      } else {
         return beanInfoClass;
      }
   }

   private String getBeanInfoClassName(Class clazz) {
      return this.getBeanInfoClassName(clazz.getName());
   }

   private String getBeanInfoClassName(String className) {
      return className + "BeanInfo";
   }

   public Class getClassForName(String beanInterface) throws ClassNotFoundException {
      Class result = null;

      try {
         result = Class.forName(beanInterface);
         return result;
      } catch (ClassNotFoundException var8) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Could not get class for name (simple option) for " + beanInterface + " ignoring class not found error");
         }

         Iterator it = this.beanInfoFactories.getFactories().iterator();

         ImplementationFactory factory;
         String beanInfoClassName;
         do {
            if (!it.hasNext()) {
               throw new ClassNotFoundException(beanInterface);
            }

            factory = (ImplementationFactory)it.next();
            beanInfoClassName = factory.getImplementationClassName(beanInterface);
         } while(beanInfoClassName == null);

         try {
            return factory.getClass().getClassLoader().loadClass(beanInterface);
         } catch (ClassNotFoundException var7) {
            throw new Error(var7);
         }
      }
   }

   public boolean hasBeanInfo(Class interfaceClass) {
      return this.beanInterfaceClasses.contains(interfaceClass);
   }

   public Class getInterfaceForInstance(Object instance) {
      Class beanInfoClass = this.getBeanInfoClass(instance);

      try {
         Field field = beanInfoClass.getField("INTERFACE_CLASS");
         return (Class)field.get((Object)null);
      } catch (NoSuchFieldException var4) {
         return null;
      } catch (IllegalAccessException var5) {
         throw new RuntimeException(var5);
      }
   }

   public Class loadClass(String className) throws ClassNotFoundException {
      Iterator it = this.beanInfoFactories.getFactories().iterator();

      while(it.hasNext()) {
         ImplementationFactory factory = (ImplementationFactory)it.next();
         ClassLoader loader = factory.getClass().getClassLoader();

         try {
            Class result = loader.loadClass(className);
            if (result != null) {
               return result;
            }
         } catch (ClassNotFoundException var6) {
         }
      }

      throw new ClassNotFoundException(className);
   }

   public BeanInfoKey getBeanInfoKeyForInterface(String beanInterface, boolean readOnly, String version) {
      Class beanInfoClass = null;

      try {
         beanInfoClass = this.getBeanInfoClass(beanInterface);
         if (beanInfoClass == null) {
            return null;
         }
      } catch (ClassNotFoundException var6) {
         return null;
      }

      return new BeanInfoKeyImpl(beanInterface, readOnly, version, beanInfoClass);
   }

   public BeanInfo getBeanInfoForInterface(String beanInterface, boolean readOnly, String version) {
      BeanInfoKey key = this.getBeanInfoKeyForInterface(beanInterface, readOnly, version);
      return key == null ? null : this.getBeanInfo(key);
   }

   public String[] getSubtypes(String baseInterface) {
      if (baseInterface == null) {
         return this.beanInterfaceClasses.getAllInterfaceNames();
      } else {
         Set resultSet = new TreeSet();
         Class interfaceClass = null;

         try {
            interfaceClass = this.getClassForName(baseInterface);
         } catch (ClassNotFoundException var6) {
            IllegalArgumentException iae = new IllegalArgumentException("Specified interface is not valid");
            iae.initCause(var6);
            throw iae;
         }

         Iterator iterator = this.beanInterfaceClasses.getClasses().iterator();

         while(iterator.hasNext()) {
            Class aClass = (Class)iterator.next();
            if (interfaceClass.isAssignableFrom(aClass)) {
               resultSet.add(aClass.getName());
            }
         }

         return (String[])((String[])resultSet.toArray(new String[resultSet.size()]));
      }
   }

   public String[] getBeanInfoFactoryNames() {
      ArrayList resultNames = new ArrayList();
      Iterator it = this.beanInfoFactories.getFactories().iterator();

      while(it.hasNext()) {
         ImplementationFactory factory = (ImplementationFactory)it.next();
         resultNames.add(factory.getClass().getName());
      }

      return (String[])((String[])resultNames.toArray(new String[resultNames.size()]));
   }

   public String[] getInterfacesWithRoleInfo(String factoryName) {
      if (factoryName == null) {
         return null;
      } else {
         Iterator it = this.beanInfoFactories.getFactories().iterator();

         ImplementationFactory factory;
         do {
            if (!it.hasNext()) {
               return null;
            }

            factory = (ImplementationFactory)it.next();
         } while(!factoryName.equals(factory.getClass().getName()) || !(factory instanceof RoleInfoImplementationFactory));

         RoleInfoImplementationFactory roleFactory = (RoleInfoImplementationFactory)factory;
         return roleFactory.getInterfacesWithRoleInfo();
      }
   }

   public String getRoleInfoImplementationFactoryTimestamp(String factoryName) {
      if (factoryName == null) {
         return null;
      } else {
         Iterator it = this.beanInfoFactories.getFactories().iterator();

         ImplementationFactory factory;
         do {
            if (!it.hasNext()) {
               return null;
            }

            factory = (ImplementationFactory)it.next();
         } while(!factoryName.equals(factory.getClass().getName()) || !(factory instanceof RoleInfoImplementationFactory));

         RoleInfoImplementationFactory roleFactory = (RoleInfoImplementationFactory)factory;
         return roleFactory.getRoleInfoImplementationFactoryTimestamp();
      }
   }

   public BeanInfo getBeanInfoForDescriptorBean(DescriptorBean bean) {
      return this.getBeanInfoForInstance(bean, false, (String)null);
   }

   public PropertyDescriptor getPropertyDescriptor(BeanInfo beanInfo, String propertyName) {
      PropertyDescriptor desc = null;
      Map propertyMap = (Map)this.beanPropertyMapByBeanInfo.get(beanInfo);
      if (propertyMap == null) {
         propertyMap = Collections.synchronizedMap(new HashMap());
         PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();

         for(int i = 0; props != null && i < props.length; ++i) {
            propertyMap.put(props[i].getName(), props[i]);
         }

         this.beanPropertyMapByBeanInfo.put(beanInfo, propertyMap);
      }

      if (propertyMap != null) {
         desc = (PropertyDescriptor)propertyMap.get(propertyName);
      }

      return desc;
   }

   public BeanInfo getBeanInfo(BeanInfoKey key) {
      BeanInfo result = (BeanInfo)this.beanInfos.get(key);
      if (result == null) {
         result = this.buildBeanInfo(key);
      }

      if (result instanceof BeanInfoImpl) {
         BeanInfoImpl info = (BeanInfoImpl)result;
         info.setLastAccess(System.currentTimeMillis());
      }

      return result;
   }

   private synchronized BeanInfo buildBeanInfo(BeanInfoKey key) {
      BeanInfo result = (BeanInfo)this.beanInfos.get(key);
      if (result != null) {
         return result;
      } else {
         try {
            Class beanInfoClass = key.getBeanInfoClass();
            if (beanInfoClass == null) {
               beanInfoClass = this.loadClass(key.getBeanInfoClassName());
            }

            Constructor constructor = beanInfoClass.getDeclaredConstructor(BeanInfoHelper.CONSTRUCTORPARAMS);
            result = (BeanInfo)constructor.newInstance(new Boolean(key.isReadOnly()), key.getVersion());
            Set disabled = (Set)this.disabledProperties.get(result.getBeanDescriptor().getValue("interfaceclassname"));
            if (disabled != null && !disabled.isEmpty() && result instanceof BeanInfoImpl) {
               ((BeanInfoImpl)result).disableProperties((String[])disabled.toArray(new String[disabled.size()]));
            }
         } catch (ClassNotFoundException var6) {
            throw new Error(var6);
         } catch (IllegalAccessException var7) {
            throw new Error(var7);
         } catch (NoSuchMethodException var8) {
            throw new Error(var8);
         } catch (InvocationTargetException var9) {
            throw new Error(var9);
         } catch (InstantiationException var10) {
            throw new Error(var10);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Added beaninfo size: " + this.beanInfos.size() + " key: " + key);
         }

         this.beanInfos.put(key, result);
         return result;
      }
   }

   private Class getBeanInfoClass(String beanInterface) throws ClassNotFoundException {
      Iterator it = this.beanInfoFactories.getFactories().iterator();

      ImplementationFactory factory;
      String beanInfoClassName;
      do {
         if (!it.hasNext()) {
            return null;
         }

         factory = (ImplementationFactory)it.next();
         beanInfoClassName = factory.getImplementationClassName(beanInterface);
      } while(beanInfoClassName == null);

      return factory.getClass().getClassLoader().loadClass(beanInfoClassName);
   }

   private synchronized void releaseInactiveBeanInfos() {
      int s = this.beanInfos.size();
      Set keys = this.beanInfos.keySet();
      ArrayList toBeRemovedKeys = new ArrayList();
      long fiveMinAgo = System.currentTimeMillis() - FiveMinutes;
      Iterator var6 = keys.iterator();

      BeanInfoKey key;
      while(var6.hasNext()) {
         key = (BeanInfoKey)var6.next();
         BeanInfo info = (BeanInfo)this.beanInfos.get(key);
         if (info instanceof BeanInfoImpl && ((BeanInfoImpl)info).getLastAccess() <= fiveMinAgo) {
            toBeRemovedKeys.add(key);
         }
      }

      var6 = toBeRemovedKeys.iterator();

      while(var6.hasNext()) {
         key = (BeanInfoKey)var6.next();
         this.beanInfos.remove(key);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("release inactive beaninfos ... " + s + " to " + this.beanInfos.size());
      }

   }

   private synchronized void releaseAllBeanInfos() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("release all beaninfos ... " + this.beanInfos.size());
      }

      this.beanInfos.clear();
   }

   public void scrubADubDub() {
      this.releaseInactiveBeanInfos();
   }

   private class BeanInterfaceClasses {
      private Set classes = null;
      private String[] allInterfaceNames = null;
      private boolean initialized = false;

      public boolean contains(Class clazz) {
         this.initialize();
         return this.classes.contains(clazz);
      }

      public final Set getClasses() {
         this.initialize();
         return this.classes;
      }

      public final String[] getAllInterfaceNames() {
         this.initialize();
         return this.allInterfaceNames;
      }

      BeanInterfaceClasses() {
      }

      private void initialize() {
         if (!this.initialized) {
            this.load();
         }

      }

      private synchronized void load() {
         if (!this.initialized) {
            List factories = BeanInfoAccessImpl.this.beanInfoFactories.getFactories();
            this.classes = new HashSet(256);
            Set allClassNames = new TreeSet();
            Iterator it = factories.iterator();

            while(it.hasNext()) {
               ImplementationFactory factory = (ImplementationFactory)it.next();
               String[] typeNames = factory.getInterfaces();

               for(int i = 0; i < typeNames.length; ++i) {
                  String typeName = typeNames[i];

                  try {
                     Class clazz = factory.getClass().getClassLoader().loadClass(typeName);
                     this.classes.add(clazz);
                     allClassNames.add(typeName);
                  } catch (ClassNotFoundException var9) {
                     throw new Error(var9);
                  }
               }
            }

            this.allInterfaceNames = (String[])((String[])allClassNames.toArray(new String[allClassNames.size()]));
            this.initialized = true;
         }

      }
   }

   private class BeanInfoFactoryList {
      private List beanInfoFactories = null;
      private boolean initialized = false;

      BeanInfoFactoryList() {
      }

      List getFactories() {
         if (!this.initialized) {
            this.loadBeanInfoFactories();
         }

         return this.beanInfoFactories;
      }

      boolean loadedFactories() {
         return this.initialized;
      }

      void reloadFactories() {
         this.initialized = false;
      }

      private synchronized void loadBeanInfoFactories() {
         if (!this.initialized) {
            this.beanInfoFactories = Collections.synchronizedList(new ArrayList(10));
            Iterator factoryIterator = BeanInfoAccessImpl.this.beanInfoFactoryToLoader.entrySet().iterator();

            while(factoryIterator.hasNext()) {
               Map.Entry entry = (Map.Entry)factoryIterator.next();
               this.loadBeanInfoFactory((String)entry.getKey(), (ClassLoader)entry.getValue());
            }

            this.initialized = true;
         }

      }

      private void loadBeanInfoFactory(String factoryClassName, ClassLoader ldr) {
         try {
            Class factoryClass = Class.forName(factoryClassName, true, ldr);
            Method method = factoryClass.getMethod("getInstance", (Class[])null);
            ImplementationFactory result = (ImplementationFactory)method.invoke((Object)null, (Object[])null);
            this.beanInfoFactories.add(result);
         } catch (NoSuchMethodException var6) {
            throw new Error(var6);
         } catch (IllegalAccessException var7) {
            throw new Error(var7);
         } catch (InvocationTargetException var8) {
            throw new Error(var8);
         } catch (ClassNotFoundException var9) {
            if (BeanInfoAccessImpl.debugLogger.isDebugEnabled()) {
               BeanInfoAccessImpl.debugLogger.debug("Could not create bean info factory for " + factoryClassName + ",  ignoring class not found error");
            }
         }

      }
   }

   private class BeanInfoKeyImpl implements BeanInfoKey {
      protected boolean readOnly;
      protected String version;
      protected String beanInfoClassName;
      protected Class beanInfoClass;

      BeanInfoKeyImpl(String beanInfoClassName, boolean readOnly, String version, Class beanInfoClass) {
         this.beanInfoClassName = beanInfoClassName;
         this.readOnly = readOnly;
         this.version = version;
         this.beanInfoClass = beanInfoClass;
      }

      public String getBeanInfoClassName() {
         return this.beanInfoClassName;
      }

      public Class getBeanInfoClass() {
         return this.beanInfoClass;
      }

      public ClassLoader getBeanInfoClassLoader() {
         return this.beanInfoClass.getClassLoader();
      }

      public boolean isReadOnly() {
         return this.readOnly;
      }

      public String getVersion() {
         return this.version;
      }

      public int compareTo(Object otherObject) {
         BeanInfoKeyImpl other = (BeanInfoKeyImpl)otherObject;
         if (other == null) {
            return 1;
         } else {
            int result = this.beanInfoClassName.compareTo(other.beanInfoClassName);
            if (result != 0) {
               return result;
            } else {
               result = this.compareVersionStrings(this.version, other.version);
               if (result != 0) {
                  return result;
               } else {
                  result = this.readOnly && !other.readOnly ? -1 : (!this.readOnly && other.readOnly ? 1 : 0);
                  if (result != 0) {
                     return result;
                  } else if (this.getBeanInfoClassLoader() == other.getBeanInfoClassLoader()) {
                     return 0;
                  } else if (this.getBeanInfoClassLoader() == null && other.getBeanInfoClassLoader() != null) {
                     return -1;
                  } else {
                     return this.getBeanInfoClassLoader() != null && other.getBeanInfoClassLoader() == null ? 1 : this.getBeanInfoClassLoader().hashCode() - other.getBeanInfoClassLoader().hashCode();
                  }
               }
            }
         }
      }

      public boolean equals(Object otherObject) {
         return this.compareTo(otherObject) == 0;
      }

      int compareVersionStrings(String one, String two) {
         if (one == null && two == null) {
            return 0;
         } else if (one == null && two != null) {
            return -1;
         } else {
            return one != null && two == null ? 1 : one.compareTo(two);
         }
      }

      public int hashCode() {
         return this.beanInfoClassName.hashCode();
      }

      public String toString() {
         return "BeanInfoKeyImpl: name=" + this.beanInfoClassName + ",version=" + this.version + ",readonly=" + this.readOnly + ",classloader=" + this.getBeanInfoClassLoader();
      }
   }
}
