package org.jboss.weld.annotated.enhanced;

import javax.enterprise.inject.spi.AnnotatedParameter;

public interface EnhancedAnnotatedParameter extends EnhancedAnnotated, AnnotatedParameter {
   EnhancedAnnotatedCallable getDeclaringEnhancedCallable();

   EnhancedAnnotatedType getDeclaringType();

   AnnotatedParameter slim();
}
