package com.oracle.pitchfork.inject;

import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.oracle.pitchfork.JeeLogger;
import com.oracle.pitchfork.interfaces.inject.InjectionInfo;
import com.oracle.pitchfork.util.reflect.ReflectionUtils;
import java.lang.reflect.Field;

public class FieldInjection extends Injection {
   public FieldInjection(Field f, InjectionInfo ri, boolean isOptional) {
      super(f, ri, isOptional);
   }

   public void apply(Object instance, Object value) {
      try {
         ReflectionUtils.makeAccessible(this.getMember());
         this.getMember().set(instance, value);
      } catch (Exception var4) {
         throw new BeanCreationException(JeeLogger.logFieldInjectionFailure(this, value), var4);
      }
   }

   protected Class getMemberType() {
      return this.getMember().getType();
   }

   public Field getMember() {
      return (Field)super.getMember();
   }
}
