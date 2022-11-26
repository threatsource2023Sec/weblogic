package weblogic.management.utils;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.ForeignJNDIProviderMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSBridgeDestinationMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.configuration.ManagedExecutorServiceMBean;
import weblogic.management.configuration.ManagedScheduledExecutorServiceMBean;
import weblogic.management.configuration.ManagedThreadFactoryMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.provider.ManagementService;

public class ResourceUtil {
   private static final List beanClasses = new ArrayList(Arrays.asList(LibraryMBean.class, AppDeploymentMBean.class, CoherenceClusterSystemResourceMBean.class, FileStoreMBean.class, JDBCStoreMBean.class, JDBCSystemResourceMBean.class, JMSBridgeDestinationMBean.class, JMSServerMBean.class, JMSSystemResourceMBean.class, MailSessionMBean.class, ManagedExecutorServiceMBean.class, ManagedScheduledExecutorServiceMBean.class, ManagedThreadFactoryMBean.class, MessagingBridgeMBean.class, PathServiceMBean.class, SAFAgentMBean.class, WLDFSystemResourceMBean.class, OsgiFrameworkMBean.class, ForeignJNDIProviderMBean.class));
   private static final Collection resourcesToSkip = new HashSet() {
      {
         this.add("InternalAppDeployments");
      }
   };
   private static Map ownerToResourceGetters = new HashMap();

   private static synchronized Map gettersForOwner(Class ownerClass) {
      Map result = (Map)ownerToResourceGetters.get(ownerClass);
      if (result == null) {
         result = prepareGettersForOwner(ownerClass);
         ownerToResourceGetters.put(ownerClass, result);
      }

      return result;
   }

   private static Map prepareGettersForOwner(Class ownerClass) {
      BeanInfo ownerBeanInfo = ManagementService.getBeanInfoAccess().getBeanInfoForInterface(ownerClass.getName(), true, (String)null);
      Map result = new HashMap();
      Iterator var3 = beanClasses.iterator();

      while(var3.hasNext()) {
         Class c = (Class)var3.next();
         PropertyDescriptor[] var5 = ownerBeanInfo.getPropertyDescriptors();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PropertyDescriptor pd = var5[var7];
            if (!resourcesToSkip.contains(pd.getName())) {
               Class propertyType = pd.getPropertyType();
               if (propertyType.isArray()) {
                  Class componentType = propertyType.getComponentType();
                  if (beanClasses.contains(componentType)) {
                     result.put(componentType, pd.getReadMethod());
                  }
               }
            }
         }
      }

      return result;
   }

   public static List getResourceClasses() {
      return Collections.unmodifiableList(beanClasses);
   }

   public static Class beanToClass(ConfigurationMBean bean) {
      Iterator var1 = beanClasses.iterator();

      Class beanClass;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         beanClass = (Class)var1.next();
      } while(!beanClass.isAssignableFrom(bean.getClass()));

      return beanClass;
   }

   public static String beanClassToAttributeName(Class beanClass) {
      String className = beanClass.getSimpleName();
      if (className == null) {
         return null;
      } else {
         int suffixPos = className.indexOf("MBean");
         return className.substring(0, suffixPos);
      }
   }

   public static Collection getResources(DomainMBean domain) throws InvocationTargetException, IllegalAccessException {
      List result = new ArrayList();
      Iterator var2 = gettersForOwner(DomainMBean.class).values().iterator();

      while(true) {
         Object resultArray;
         do {
            if (!var2.hasNext()) {
               return result;
            }

            Method getter = (Method)var2.next();
            resultArray = getter.invoke(domain);
         } while(!resultArray.getClass().isArray());

         Object[] var5 = (Object[])((Object[])resultArray);
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Object candidateResource = var5[var7];
            if (candidateResource instanceof ConfigurationMBean) {
               result.add((ConfigurationMBean)candidateResource);
            }
         }
      }
   }
}
