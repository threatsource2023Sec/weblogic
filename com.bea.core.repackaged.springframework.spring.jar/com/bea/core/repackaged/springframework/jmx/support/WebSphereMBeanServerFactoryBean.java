package com.bea.core.repackaged.springframework.jmx.support;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jmx.MBeanServerNotFoundException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.management.MBeanServer;

public class WebSphereMBeanServerFactoryBean implements FactoryBean, InitializingBean {
   private static final String ADMIN_SERVICE_FACTORY_CLASS = "com.ibm.websphere.management.AdminServiceFactory";
   private static final String GET_MBEAN_FACTORY_METHOD = "getMBeanFactory";
   private static final String GET_MBEAN_SERVER_METHOD = "getMBeanServer";
   @Nullable
   private MBeanServer mbeanServer;

   public void afterPropertiesSet() throws MBeanServerNotFoundException {
      try {
         Class adminServiceClass = this.getClass().getClassLoader().loadClass("com.ibm.websphere.management.AdminServiceFactory");
         Method getMBeanFactoryMethod = adminServiceClass.getMethod("getMBeanFactory");
         Object mbeanFactory = getMBeanFactoryMethod.invoke((Object)null);
         Method getMBeanServerMethod = mbeanFactory.getClass().getMethod("getMBeanServer");
         this.mbeanServer = (MBeanServer)getMBeanServerMethod.invoke(mbeanFactory);
      } catch (ClassNotFoundException var5) {
         throw new MBeanServerNotFoundException("Could not find WebSphere's AdminServiceFactory class", var5);
      } catch (InvocationTargetException var6) {
         throw new MBeanServerNotFoundException("WebSphere's AdminServiceFactory.getMBeanFactory/getMBeanServer method failed", var6.getTargetException());
      } catch (Exception var7) {
         throw new MBeanServerNotFoundException("Could not access WebSphere's AdminServiceFactory.getMBeanFactory/getMBeanServer method", var7);
      }
   }

   @Nullable
   public MBeanServer getObject() {
      return this.mbeanServer;
   }

   public Class getObjectType() {
      return this.mbeanServer != null ? this.mbeanServer.getClass() : MBeanServer.class;
   }

   public boolean isSingleton() {
      return true;
   }
}
