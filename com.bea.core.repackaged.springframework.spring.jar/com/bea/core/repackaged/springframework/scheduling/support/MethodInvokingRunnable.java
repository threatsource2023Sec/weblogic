package com.bea.core.repackaged.springframework.scheduling.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.support.ArgumentConvertingMethodInvoker;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.InvocationTargetException;

public class MethodInvokingRunnable extends ArgumentConvertingMethodInvoker implements Runnable, BeanClassLoaderAware, InitializingBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   protected Class resolveClassName(String className) throws ClassNotFoundException {
      return ClassUtils.forName(className, this.beanClassLoader);
   }

   public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
      this.prepare();
   }

   public void run() {
      try {
         this.invoke();
      } catch (InvocationTargetException var2) {
         this.logger.error(this.getInvocationFailureMessage(), var2.getTargetException());
      } catch (Throwable var3) {
         this.logger.error(this.getInvocationFailureMessage(), var3);
      }

   }

   protected String getInvocationFailureMessage() {
      return "Invocation of method '" + this.getTargetMethod() + "' on target class [" + this.getTargetClass() + "] failed";
   }
}
