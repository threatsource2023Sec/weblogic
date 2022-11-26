package weblogic.application.utils.annotation;

import java.util.Set;
import weblogic.application.AnnotationProcessingException;

public interface AnnotationMappings {
   Set getAnnotatedClasses(Class... var1);

   void loadAnnotatedClasses(Class[] var1) throws AnnotationProcessingException;

   void freeClassInfoFinder();
}
