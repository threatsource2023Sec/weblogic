package org.jboss.weld.annotated.enhanced;

import java.util.Set;

public interface EnhancedAnnotation extends EnhancedAnnotatedType {
   Set getMembers();

   Set getMembers(Class var1);
}
