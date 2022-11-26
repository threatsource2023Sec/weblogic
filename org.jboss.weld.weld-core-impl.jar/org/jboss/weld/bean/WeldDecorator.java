package org.jboss.weld.bean;

import java.lang.reflect.Method;
import javax.enterprise.inject.spi.Decorator;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.runtime.InvokableAnnotatedMethod;

public interface WeldDecorator extends Decorator {
   EnhancedAnnotatedType getEnhancedAnnotated();

   InvokableAnnotatedMethod getDecoratorMethod(Method var1);
}
