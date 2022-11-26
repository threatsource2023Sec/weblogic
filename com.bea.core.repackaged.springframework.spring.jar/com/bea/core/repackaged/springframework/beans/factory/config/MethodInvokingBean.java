package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.support.ArgumentConvertingMethodInvoker;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.InvocationTargetException;

public class MethodInvokingBean extends ArgumentConvertingMethodInvoker implements BeanClassLoaderAware, BeanFactoryAware, InitializingBean {
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private ConfigurableBeanFactory beanFactory;

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   protected Class resolveClassName(String className) throws ClassNotFoundException {
      return ClassUtils.forName(className, this.beanClassLoader);
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (beanFactory instanceof ConfigurableBeanFactory) {
         this.beanFactory = (ConfigurableBeanFactory)beanFactory;
      }

   }

   protected TypeConverter getDefaultTypeConverter() {
      return this.beanFactory != null ? this.beanFactory.getTypeConverter() : super.getDefaultTypeConverter();
   }

   public void afterPropertiesSet() throws Exception {
      this.prepare();
      this.invokeWithTargetException();
   }

   @Nullable
   protected Object invokeWithTargetException() throws Exception {
      try {
         return this.invoke();
      } catch (InvocationTargetException var2) {
         if (var2.getTargetException() instanceof Exception) {
            throw (Exception)var2.getTargetException();
         } else if (var2.getTargetException() instanceof Error) {
            throw (Error)var2.getTargetException();
         } else {
            throw var2;
         }
      }
   }
}
