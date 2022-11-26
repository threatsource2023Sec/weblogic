package org.jboss.weld.annotated.enhanced;

import javax.enterprise.inject.spi.AnnotatedMember;

public interface EnhancedAnnotatedMember extends EnhancedAnnotated, AnnotatedMember {
   EnhancedAnnotatedType getDeclaringType();

   AnnotatedMember slim();
}
