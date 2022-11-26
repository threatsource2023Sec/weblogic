package org.jboss.weld.annotated.enhanced;

import java.util.List;
import javax.enterprise.inject.spi.AnnotatedCallable;

public interface EnhancedAnnotatedCallable extends EnhancedAnnotatedMember, AnnotatedCallable {
   List getEnhancedParameters();

   List getEnhancedParameters(Class var1);

   AnnotatedCallable slim();
}
