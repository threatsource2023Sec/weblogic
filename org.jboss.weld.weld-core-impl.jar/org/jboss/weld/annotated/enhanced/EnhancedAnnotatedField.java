package org.jboss.weld.annotated.enhanced;

import javax.enterprise.inject.spi.AnnotatedField;

public interface EnhancedAnnotatedField extends EnhancedAnnotatedMember, AnnotatedField {
   String getPropertyName();

   boolean isTransient();

   AnnotatedField slim();
}
