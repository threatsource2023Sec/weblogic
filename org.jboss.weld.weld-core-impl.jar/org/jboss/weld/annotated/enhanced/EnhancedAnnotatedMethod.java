package org.jboss.weld.annotated.enhanced;

import java.lang.reflect.Method;
import java.util.List;
import javax.enterprise.inject.spi.AnnotatedMethod;

public interface EnhancedAnnotatedMethod extends EnhancedAnnotatedCallable, AnnotatedMethod {
   Class[] getParameterTypesAsArray();

   String getPropertyName();

   boolean isEquivalent(Method var1);

   MethodSignature getSignature();

   List getEnhancedParameters(Class var1);

   AnnotatedMethod slim();
}
