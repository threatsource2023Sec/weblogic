package org.jboss.weld.annotated.enhanced;

import javax.enterprise.inject.spi.AnnotatedConstructor;

public interface EnhancedAnnotatedConstructor extends EnhancedAnnotatedCallable, AnnotatedConstructor {
   ConstructorSignature getSignature();

   AnnotatedConstructor slim();
}
