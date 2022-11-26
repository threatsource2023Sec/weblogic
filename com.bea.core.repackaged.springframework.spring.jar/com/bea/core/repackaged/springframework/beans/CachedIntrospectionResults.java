package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.SpringProperties;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.io.support.SpringFactoriesLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class CachedIntrospectionResults {
   public static final String IGNORE_BEANINFO_PROPERTY_NAME = "spring.beaninfo.ignore";
   private static final boolean shouldIntrospectorIgnoreBeaninfoClasses = SpringProperties.getFlag("spring.beaninfo.ignore");
   private static List beanInfoFactories = SpringFactoriesLoader.loadFactories(BeanInfoFactory.class, CachedIntrospectionResults.class.getClassLoader());
   private static final Log logger = LogFactory.getLog(CachedIntrospectionResults.class);
   static final Set acceptedClassLoaders = Collections.newSetFromMap(new ConcurrentHashMap(16));
   static final ConcurrentMap strongClassCache = new ConcurrentHashMap(64);
   static final ConcurrentMap softClassCache = new ConcurrentReferenceHashMap(64);
   private final BeanInfo beanInfo;
   private final Map propertyDescriptorCache;
   private final ConcurrentMap typeDescriptorCache;

   public static void acceptClassLoader(@Nullable ClassLoader classLoader) {
      if (classLoader != null) {
         acceptedClassLoaders.add(classLoader);
      }

   }

   public static void clearClassLoader(@Nullable ClassLoader classLoader) {
      acceptedClassLoaders.removeIf((registeredLoader) -> {
         return isUnderneathClassLoader(registeredLoader, classLoader);
      });
      strongClassCache.keySet().removeIf((beanClass) -> {
         return isUnderneathClassLoader(beanClass.getClassLoader(), classLoader);
      });
      softClassCache.keySet().removeIf((beanClass) -> {
         return isUnderneathClassLoader(beanClass.getClassLoader(), classLoader);
      });
   }

   static CachedIntrospectionResults forClass(Class beanClass) throws BeansException {
      CachedIntrospectionResults results = (CachedIntrospectionResults)strongClassCache.get(beanClass);
      if (results != null) {
         return results;
      } else {
         results = (CachedIntrospectionResults)softClassCache.get(beanClass);
         if (results != null) {
            return results;
         } else {
            results = new CachedIntrospectionResults(beanClass);
            ConcurrentMap classCacheToUse;
            if (!ClassUtils.isCacheSafe(beanClass, CachedIntrospectionResults.class.getClassLoader()) && !isClassLoaderAccepted(beanClass.getClassLoader())) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Not strongly caching class [" + beanClass.getName() + "] because it is not cache-safe");
               }

               classCacheToUse = softClassCache;
            } else {
               classCacheToUse = strongClassCache;
            }

            CachedIntrospectionResults existing = (CachedIntrospectionResults)classCacheToUse.putIfAbsent(beanClass, results);
            return existing != null ? existing : results;
         }
      }
   }

   private static boolean isClassLoaderAccepted(ClassLoader classLoader) {
      Iterator var1 = acceptedClassLoaders.iterator();

      ClassLoader acceptedLoader;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         acceptedLoader = (ClassLoader)var1.next();
      } while(!isUnderneathClassLoader(classLoader, acceptedLoader));

      return true;
   }

   private static boolean isUnderneathClassLoader(@Nullable ClassLoader candidate, @Nullable ClassLoader parent) {
      if (candidate == parent) {
         return true;
      } else if (candidate == null) {
         return false;
      } else {
         ClassLoader classLoaderToCheck = candidate;

         do {
            if (classLoaderToCheck == null) {
               return false;
            }

            classLoaderToCheck = classLoaderToCheck.getParent();
         } while(classLoaderToCheck != parent);

         return true;
      }
   }

   private static BeanInfo getBeanInfo(Class beanClass) throws IntrospectionException {
      Iterator var1 = beanInfoFactories.iterator();

      BeanInfo beanInfo;
      do {
         if (!var1.hasNext()) {
            return shouldIntrospectorIgnoreBeaninfoClasses ? Introspector.getBeanInfo(beanClass, 3) : Introspector.getBeanInfo(beanClass);
         }

         BeanInfoFactory beanInfoFactory = (BeanInfoFactory)var1.next();
         beanInfo = beanInfoFactory.getBeanInfo(beanClass);
      } while(beanInfo == null);

      return beanInfo;
   }

   private CachedIntrospectionResults(Class beanClass) throws BeansException {
      try {
         if (logger.isTraceEnabled()) {
            logger.trace("Getting BeanInfo for class [" + beanClass.getName() + "]");
         }

         this.beanInfo = getBeanInfo(beanClass);
         if (logger.isTraceEnabled()) {
            logger.trace("Caching PropertyDescriptors for class [" + beanClass.getName() + "]");
         }

         this.propertyDescriptorCache = new LinkedHashMap();
         PropertyDescriptor[] pds = this.beanInfo.getPropertyDescriptors();
         PropertyDescriptor[] var3 = pds;
         int var4 = pds.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PropertyDescriptor pd = var3[var5];
            if (Class.class != beanClass || !"classLoader".equals(pd.getName()) && !"protectionDomain".equals(pd.getName())) {
               if (logger.isTraceEnabled()) {
                  logger.trace("Found bean property '" + pd.getName() + "'" + (pd.getPropertyType() != null ? " of type [" + pd.getPropertyType().getName() + "]" : "") + (pd.getPropertyEditorClass() != null ? "; editor [" + pd.getPropertyEditorClass().getName() + "]" : ""));
               }

               pd = this.buildGenericTypeAwarePropertyDescriptor(beanClass, pd);
               this.propertyDescriptorCache.put(pd.getName(), pd);
            }
         }

         for(Class currClass = beanClass; currClass != null && currClass != Object.class; currClass = currClass.getSuperclass()) {
            this.introspectInterfaces(beanClass, currClass);
         }

         this.typeDescriptorCache = new ConcurrentReferenceHashMap();
      } catch (IntrospectionException var7) {
         throw new FatalBeanException("Failed to obtain BeanInfo for class [" + beanClass.getName() + "]", var7);
      }
   }

   private void introspectInterfaces(Class beanClass, Class currClass) throws IntrospectionException {
      Class[] var3 = currClass.getInterfaces();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class ifc = var3[var5];
         if (!ClassUtils.isJavaLanguageInterface(ifc)) {
            PropertyDescriptor[] var7 = getBeanInfo(ifc).getPropertyDescriptors();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               PropertyDescriptor pd = var7[var9];
               PropertyDescriptor existingPd = (PropertyDescriptor)this.propertyDescriptorCache.get(pd.getName());
               if (existingPd == null || existingPd.getReadMethod() == null && pd.getReadMethod() != null) {
                  pd = this.buildGenericTypeAwarePropertyDescriptor(beanClass, pd);
                  this.propertyDescriptorCache.put(pd.getName(), pd);
               }
            }

            this.introspectInterfaces(ifc, ifc);
         }
      }

   }

   BeanInfo getBeanInfo() {
      return this.beanInfo;
   }

   Class getBeanClass() {
      return this.beanInfo.getBeanDescriptor().getBeanClass();
   }

   @Nullable
   PropertyDescriptor getPropertyDescriptor(String name) {
      PropertyDescriptor pd = (PropertyDescriptor)this.propertyDescriptorCache.get(name);
      if (pd == null && StringUtils.hasLength(name)) {
         pd = (PropertyDescriptor)this.propertyDescriptorCache.get(StringUtils.uncapitalize(name));
         if (pd == null) {
            pd = (PropertyDescriptor)this.propertyDescriptorCache.get(StringUtils.capitalize(name));
         }
      }

      return pd != null && !(pd instanceof GenericTypeAwarePropertyDescriptor) ? this.buildGenericTypeAwarePropertyDescriptor(this.getBeanClass(), pd) : pd;
   }

   PropertyDescriptor[] getPropertyDescriptors() {
      PropertyDescriptor[] pds = new PropertyDescriptor[this.propertyDescriptorCache.size()];
      int i = 0;

      for(Iterator var3 = this.propertyDescriptorCache.values().iterator(); var3.hasNext(); ++i) {
         PropertyDescriptor pd = (PropertyDescriptor)var3.next();
         pds[i] = pd instanceof GenericTypeAwarePropertyDescriptor ? pd : this.buildGenericTypeAwarePropertyDescriptor(this.getBeanClass(), pd);
      }

      return pds;
   }

   private PropertyDescriptor buildGenericTypeAwarePropertyDescriptor(Class beanClass, PropertyDescriptor pd) {
      try {
         return new GenericTypeAwarePropertyDescriptor(beanClass, pd.getName(), pd.getReadMethod(), pd.getWriteMethod(), pd.getPropertyEditorClass());
      } catch (IntrospectionException var4) {
         throw new FatalBeanException("Failed to re-introspect class [" + beanClass.getName() + "]", var4);
      }
   }

   TypeDescriptor addTypeDescriptor(PropertyDescriptor pd, TypeDescriptor td) {
      TypeDescriptor existing = (TypeDescriptor)this.typeDescriptorCache.putIfAbsent(pd, td);
      return existing != null ? existing : td;
   }

   @Nullable
   TypeDescriptor getTypeDescriptor(PropertyDescriptor pd) {
      return (TypeDescriptor)this.typeDescriptorCache.get(pd);
   }
}
