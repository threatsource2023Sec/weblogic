package org.hibernate.validator.cdi.internal.interceptor;

import java.lang.reflect.Constructor;
import javax.enterprise.inject.spi.AnnotatedConstructor;

public class ValidationEnabledAnnotatedConstructor extends ValidationEnabledAnnotatedCallable implements AnnotatedConstructor {
   public ValidationEnabledAnnotatedConstructor(AnnotatedConstructor constructor) {
      super(constructor);
   }

   public Constructor getJavaMember() {
      return (Constructor)this.getWrappedCallable().getJavaMember();
   }
}
