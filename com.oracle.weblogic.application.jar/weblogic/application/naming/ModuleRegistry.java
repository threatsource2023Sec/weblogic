package weblogic.application.naming;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public interface ModuleRegistry extends ConcurrentMap {
   void addReferenceResolver(ReferenceResolver var1);

   Iterable getReferenceResolvers();

   void addAnnotationProcessedClasses(Set var1);

   Set getAnnotationProcessedClasses();

   Set getAnnotatedPojoClasses();

   void clearAnnotatedPojoClasses();
}
