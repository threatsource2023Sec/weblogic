package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.aop.framework.AopConfigException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;

public class SimpleAspectInstanceFactory implements AspectInstanceFactory {
   private final Class aspectClass;

   public SimpleAspectInstanceFactory(Class aspectClass) {
      Assert.notNull(aspectClass, (String)"Aspect class must not be null");
      this.aspectClass = aspectClass;
   }

   public final Class getAspectClass() {
      return this.aspectClass;
   }

   public final Object getAspectInstance() {
      try {
         return ReflectionUtils.accessibleConstructor(this.aspectClass).newInstance();
      } catch (NoSuchMethodException var2) {
         throw new AopConfigException("No default constructor on aspect class: " + this.aspectClass.getName(), var2);
      } catch (InstantiationException var3) {
         throw new AopConfigException("Unable to instantiate aspect class: " + this.aspectClass.getName(), var3);
      } catch (IllegalAccessException var4) {
         throw new AopConfigException("Could not access aspect constructor: " + this.aspectClass.getName(), var4);
      } catch (InvocationTargetException var5) {
         throw new AopConfigException("Failed to invoke aspect constructor: " + this.aspectClass.getName(), var5.getTargetException());
      }
   }

   @Nullable
   public ClassLoader getAspectClassLoader() {
      return this.aspectClass.getClassLoader();
   }

   public int getOrder() {
      return this.getOrderForAspectClass(this.aspectClass);
   }

   protected int getOrderForAspectClass(Class aspectClass) {
      return Integer.MAX_VALUE;
   }
}
