package org.apache.taglibs.standard.lang.jstl;

import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessControlException;
import java.util.HashMap;
import java.util.Map;

public class BeanInfoManager {
   Class mBeanClass;
   BeanInfo mBeanInfo;
   Map mPropertyByName;
   Map mIndexedPropertyByName;
   Map mEventSetByName;
   boolean mInitialized;
   static Map mBeanInfoManagerByClass = new HashMap();

   public Class getBeanClass() {
      return this.mBeanClass;
   }

   BeanInfoManager(Class pBeanClass) {
      this.mBeanClass = pBeanClass;
   }

   public static BeanInfoManager getBeanInfoManager(Class pClass) {
      BeanInfoManager ret = (BeanInfoManager)mBeanInfoManagerByClass.get(pClass);
      if (ret == null) {
         ret = createBeanInfoManager(pClass);
      }

      return ret;
   }

   static synchronized BeanInfoManager createBeanInfoManager(Class pClass) {
      BeanInfoManager ret = (BeanInfoManager)mBeanInfoManagerByClass.get(pClass);
      if (ret == null) {
         ret = new BeanInfoManager(pClass);
         mBeanInfoManagerByClass.put(pClass, ret);
      }

      return ret;
   }

   public static BeanInfoProperty getBeanInfoProperty(Class pClass, String pPropertyName, Logger pLogger) throws ELException {
      return getBeanInfoManager(pClass).getProperty(pPropertyName, pLogger);
   }

   public static BeanInfoIndexedProperty getBeanInfoIndexedProperty(Class pClass, String pIndexedPropertyName, Logger pLogger) throws ELException {
      return getBeanInfoManager(pClass).getIndexedProperty(pIndexedPropertyName, pLogger);
   }

   void checkInitialized(Logger pLogger) throws ELException {
      if (!this.mInitialized) {
         synchronized(this) {
            if (!this.mInitialized) {
               this.initialize(pLogger);
               this.mInitialized = true;
            }
         }
      }

   }

   void initialize(Logger pLogger) throws ELException {
      try {
         this.mBeanInfo = Introspector.getBeanInfo(this.mBeanClass);
         this.mPropertyByName = new HashMap();
         this.mIndexedPropertyByName = new HashMap();
         PropertyDescriptor[] pds = this.mBeanInfo.getPropertyDescriptors();

         for(int i = 0; pds != null && i < pds.length; ++i) {
            PropertyDescriptor pd = pds[i];
            Method writeMethod;
            if (pd instanceof IndexedPropertyDescriptor) {
               IndexedPropertyDescriptor ipd = (IndexedPropertyDescriptor)pd;
               writeMethod = getPublicMethod(ipd.getIndexedReadMethod());
               Method writeMethod = getPublicMethod(ipd.getIndexedWriteMethod());
               BeanInfoIndexedProperty property = new BeanInfoIndexedProperty(writeMethod, writeMethod, ipd);
               this.mIndexedPropertyByName.put(ipd.getName(), property);
            }

            Method readMethod = getPublicMethod(pd.getReadMethod());
            writeMethod = getPublicMethod(pd.getWriteMethod());
            BeanInfoProperty property = new BeanInfoProperty(readMethod, writeMethod, pd);
            this.mPropertyByName.put(pd.getName(), property);
         }

         this.mEventSetByName = new HashMap();
         EventSetDescriptor[] esds = this.mBeanInfo.getEventSetDescriptors();

         for(int i = 0; esds != null && i < esds.length; ++i) {
            EventSetDescriptor esd = esds[i];
            this.mEventSetByName.put(esd.getName(), esd);
         }
      } catch (IntrospectionException var9) {
         if (pLogger.isLoggingWarning()) {
            pLogger.logWarning(Constants.EXCEPTION_GETTING_BEANINFO, (Throwable)var9, this.mBeanClass.getName());
         }
      }

   }

   BeanInfo getBeanInfo(Logger pLogger) throws ELException {
      this.checkInitialized(pLogger);
      return this.mBeanInfo;
   }

   public BeanInfoProperty getProperty(String pPropertyName, Logger pLogger) throws ELException {
      this.checkInitialized(pLogger);
      return (BeanInfoProperty)this.mPropertyByName.get(pPropertyName);
   }

   public BeanInfoIndexedProperty getIndexedProperty(String pIndexedPropertyName, Logger pLogger) throws ELException {
      this.checkInitialized(pLogger);
      return (BeanInfoIndexedProperty)this.mIndexedPropertyByName.get(pIndexedPropertyName);
   }

   public EventSetDescriptor getEventSet(String pEventSetName, Logger pLogger) throws ELException {
      this.checkInitialized(pLogger);
      return (EventSetDescriptor)this.mEventSetByName.get(pEventSetName);
   }

   static Method getPublicMethod(Method pMethod) {
      if (pMethod == null) {
         return null;
      } else {
         Class cl = pMethod.getDeclaringClass();
         if (Modifier.isPublic(cl.getModifiers())) {
            return pMethod;
         } else {
            Method ret = getPublicMethod(cl, pMethod);
            return ret != null ? ret : pMethod;
         }
      }
   }

   static Method getPublicMethod(Class pClass, Method pMethod) {
      if (Modifier.isPublic(pClass.getModifiers())) {
         try {
            Method m;
            try {
               m = pClass.getDeclaredMethod(pMethod.getName(), pMethod.getParameterTypes());
            } catch (AccessControlException var5) {
               m = pClass.getMethod(pMethod.getName(), pMethod.getParameterTypes());
            }

            if (Modifier.isPublic(m.getModifiers())) {
               return m;
            }
         } catch (NoSuchMethodException var6) {
         }
      }

      Class[] interfaces = pClass.getInterfaces();
      if (interfaces != null) {
         for(int i = 0; i < interfaces.length; ++i) {
            Method m = getPublicMethod(interfaces[i], pMethod);
            if (m != null) {
               return m;
            }
         }
      }

      Class superclass = pClass.getSuperclass();
      if (superclass != null) {
         Method m = getPublicMethod(superclass, pMethod);
         if (m != null) {
            return m;
         }
      }

      return null;
   }
}
