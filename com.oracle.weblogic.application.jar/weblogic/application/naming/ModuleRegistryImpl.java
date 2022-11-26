package weblogic.application.naming;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleRegistryImpl extends ConcurrentHashMap implements ModuleRegistry {
   private Set resolvers = new HashSet();
   private Set annotationProcessedClassNames = new HashSet();
   private Set annotatedPojoClasses;

   public void addReferenceResolver(ReferenceResolver resolver) {
      this.resolvers.add(resolver);
   }

   public Iterable getReferenceResolvers() {
      return this.resolvers;
   }

   public void addAnnotationProcessedClasses(Set classNames) {
      this.annotationProcessedClassNames.addAll(classNames);
   }

   public Set getAnnotationProcessedClasses() {
      return this.annotationProcessedClassNames;
   }

   public void setAnnotatedPojoClasses(Set classes) {
      this.annotatedPojoClasses = classes;
   }

   public Set getAnnotatedPojoClasses() {
      return this.annotatedPojoClasses;
   }

   public void clearAnnotatedPojoClasses() {
      this.annotatedPojoClasses = null;
   }
}
