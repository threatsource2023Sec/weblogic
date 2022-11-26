package com.bea.core.repackaged.springframework.jmx.export.assembler;

import java.lang.reflect.Method;

public class SimpleReflectiveMBeanInfoAssembler extends AbstractConfigurableMBeanInfoAssembler {
   protected boolean includeReadAttribute(Method method, String beanKey) {
      return true;
   }

   protected boolean includeWriteAttribute(Method method, String beanKey) {
      return true;
   }

   protected boolean includeOperation(Method method, String beanKey) {
      return true;
   }
}
