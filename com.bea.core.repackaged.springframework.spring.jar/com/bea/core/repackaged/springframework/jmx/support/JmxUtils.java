package com.bea.core.repackaged.springframework.jmx.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.jmx.MBeanServerNotFoundException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyDescriptor;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import javax.management.DynamicMBean;
import javax.management.JMX;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public abstract class JmxUtils {
   public static final String IDENTITY_OBJECT_NAME_KEY = "identity";
   private static final String MBEAN_SUFFIX = "MBean";
   private static final Log logger = LogFactory.getLog(JmxUtils.class);

   public static MBeanServer locateMBeanServer() throws MBeanServerNotFoundException {
      return locateMBeanServer((String)null);
   }

   public static MBeanServer locateMBeanServer(@Nullable String agentId) throws MBeanServerNotFoundException {
      MBeanServer server = null;
      if (!"".equals(agentId)) {
         List servers = MBeanServerFactory.findMBeanServer(agentId);
         if (!CollectionUtils.isEmpty((Collection)servers)) {
            if (servers.size() > 1 && logger.isInfoEnabled()) {
               logger.info("Found more than one MBeanServer instance" + (agentId != null ? " with agent id [" + agentId + "]" : "") + ". Returning first from list.");
            }

            server = (MBeanServer)servers.get(0);
         }
      }

      if (server == null && !StringUtils.hasLength(agentId)) {
         try {
            server = ManagementFactory.getPlatformMBeanServer();
         } catch (SecurityException var3) {
            throw new MBeanServerNotFoundException("No specific MBeanServer found, and not allowed to obtain the Java platform MBeanServer", var3);
         }
      }

      if (server == null) {
         throw new MBeanServerNotFoundException("Unable to locate an MBeanServer instance" + (agentId != null ? " with agent id [" + agentId + "]" : ""));
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Found MBeanServer: " + server);
         }

         return server;
      }
   }

   @Nullable
   public static Class[] parameterInfoToTypes(@Nullable MBeanParameterInfo[] paramInfo) throws ClassNotFoundException {
      return parameterInfoToTypes(paramInfo, ClassUtils.getDefaultClassLoader());
   }

   @Nullable
   public static Class[] parameterInfoToTypes(@Nullable MBeanParameterInfo[] paramInfo, @Nullable ClassLoader classLoader) throws ClassNotFoundException {
      Class[] types = null;
      if (paramInfo != null && paramInfo.length > 0) {
         types = new Class[paramInfo.length];

         for(int x = 0; x < paramInfo.length; ++x) {
            types[x] = ClassUtils.forName(paramInfo[x].getType(), classLoader);
         }
      }

      return types;
   }

   public static String[] getMethodSignature(Method method) {
      Class[] types = method.getParameterTypes();
      String[] signature = new String[types.length];

      for(int x = 0; x < types.length; ++x) {
         signature[x] = types[x].getName();
      }

      return signature;
   }

   public static String getAttributeName(PropertyDescriptor property, boolean useStrictCasing) {
      return useStrictCasing ? StringUtils.capitalize(property.getName()) : property.getName();
   }

   public static ObjectName appendIdentityToObjectName(ObjectName objectName, Object managedResource) throws MalformedObjectNameException {
      Hashtable keyProperties = objectName.getKeyPropertyList();
      keyProperties.put("identity", ObjectUtils.getIdentityHexString(managedResource));
      return ObjectNameManager.getInstance(objectName.getDomain(), keyProperties);
   }

   public static Class getClassToExpose(Object managedBean) {
      return ClassUtils.getUserClass(managedBean);
   }

   public static Class getClassToExpose(Class clazz) {
      return ClassUtils.getUserClass(clazz);
   }

   public static boolean isMBean(@Nullable Class clazz) {
      return clazz != null && (DynamicMBean.class.isAssignableFrom(clazz) || getMBeanInterface(clazz) != null || getMXBeanInterface(clazz) != null);
   }

   @Nullable
   public static Class getMBeanInterface(@Nullable Class clazz) {
      if (clazz != null && clazz.getSuperclass() != null) {
         String mbeanInterfaceName = clazz.getName() + "MBean";
         Class[] implementedInterfaces = clazz.getInterfaces();
         Class[] var3 = implementedInterfaces;
         int var4 = implementedInterfaces.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class iface = var3[var5];
            if (iface.getName().equals(mbeanInterfaceName)) {
               return iface;
            }
         }

         return getMBeanInterface(clazz.getSuperclass());
      } else {
         return null;
      }
   }

   @Nullable
   public static Class getMXBeanInterface(@Nullable Class clazz) {
      if (clazz != null && clazz.getSuperclass() != null) {
         Class[] implementedInterfaces = clazz.getInterfaces();
         Class[] var2 = implementedInterfaces;
         int var3 = implementedInterfaces.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class iface = var2[var4];
            if (JMX.isMXBeanInterface(iface)) {
               return iface;
            }
         }

         return getMXBeanInterface(clazz.getSuperclass());
      } else {
         return null;
      }
   }
}
