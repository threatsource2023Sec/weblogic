package org.hibernate.validator.cdi.internal.interceptor;

import java.lang.reflect.Method;
import javax.enterprise.inject.spi.AnnotatedMethod;

public class ValidationEnabledAnnotatedMethod extends ValidationEnabledAnnotatedCallable implements AnnotatedMethod {
   public ValidationEnabledAnnotatedMethod(AnnotatedMethod method) {
      super(method);
   }

   public Method getJavaMember() {
      return (Method)this.getWrappedCallable().getJavaMember();
   }
}
