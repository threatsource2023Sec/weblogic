package com.oracle.pitchfork.inject;

import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.oracle.pitchfork.JeeLogger;
import com.oracle.pitchfork.interfaces.inject.InjectionInfo;
import com.oracle.pitchfork.util.reflect.ReflectionUtils;
import java.lang.reflect.Method;

public class MethodInjection extends Injection {
   public MethodInjection(Method m, InjectionInfo ri, boolean isOptional) {
      super(m, ri, isOptional);
   }

   public void apply(Object instance, Object value) {
      try {
         ReflectionUtils.makeAccessible(this.getMember());
         this.getMember().invoke(instance, value);
      } catch (Exception var4) {
         throw new BeanCreationException(JeeLogger.logMethodInjectionFailure(this, instance, value), var4);
      }
   }

   protected Class getMemberType() {
      return this.getMember().getParameterTypes()[0];
   }

   public Method getMember() {
      return (Method)super.getMember();
   }
}
