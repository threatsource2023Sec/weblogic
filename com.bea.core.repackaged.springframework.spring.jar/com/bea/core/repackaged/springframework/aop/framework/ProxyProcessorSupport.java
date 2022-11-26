package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Closeable;

public class ProxyProcessorSupport extends ProxyConfig implements Ordered, BeanClassLoaderAware, AopInfrastructureBean {
   private int order = Integer.MAX_VALUE;
   @Nullable
   private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
   private boolean classLoaderConfigured = false;

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void setProxyClassLoader(@Nullable ClassLoader classLoader) {
      this.proxyClassLoader = classLoader;
      this.classLoaderConfigured = classLoader != null;
   }

   @Nullable
   protected ClassLoader getProxyClassLoader() {
      return this.proxyClassLoader;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      if (!this.classLoaderConfigured) {
         this.proxyClassLoader = classLoader;
      }

   }

   protected void evaluateProxyInterfaces(Class beanClass, ProxyFactory proxyFactory) {
      Class[] targetInterfaces = ClassUtils.getAllInterfacesForClass(beanClass, this.getProxyClassLoader());
      boolean hasReasonableProxyInterface = false;
      Class[] var5 = targetInterfaces;
      int var6 = targetInterfaces.length;

      int var7;
      Class ifc;
      for(var7 = 0; var7 < var6; ++var7) {
         ifc = var5[var7];
         if (!this.isConfigurationCallbackInterface(ifc) && !this.isInternalLanguageInterface(ifc) && ifc.getMethods().length > 0) {
            hasReasonableProxyInterface = true;
            break;
         }
      }

      if (hasReasonableProxyInterface) {
         var5 = targetInterfaces;
         var6 = targetInterfaces.length;

         for(var7 = 0; var7 < var6; ++var7) {
            ifc = var5[var7];
            proxyFactory.addInterface(ifc);
         }
      } else {
         proxyFactory.setProxyTargetClass(true);
      }

   }

   protected boolean isConfigurationCallbackInterface(Class ifc) {
      return InitializingBean.class == ifc || DisposableBean.class == ifc || Closeable.class == ifc || AutoCloseable.class == ifc || ObjectUtils.containsElement(ifc.getInterfaces(), Aware.class);
   }

   protected boolean isInternalLanguageInterface(Class ifc) {
      return ifc.getName().equals("groovy.lang.GroovyObject") || ifc.getName().endsWith(".cglib.proxy.Factory") || ifc.getName().endsWith(".bytebuddy.MockAccess");
   }
}
