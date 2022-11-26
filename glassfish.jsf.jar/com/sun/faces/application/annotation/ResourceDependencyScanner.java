package com.sun.faces.application.annotation;

import com.sun.faces.util.Util;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;

class ResourceDependencyScanner implements Scanner {
   public Class getAnnotation() {
      return ResourceDependency.class;
   }

   public RuntimeAnnotationHandler scan(Class clazz) {
      Util.notNull("clazz", clazz);
      ResourceDependencyHandler handler = null;
      ResourceDependency dep = (ResourceDependency)clazz.getAnnotation(ResourceDependency.class);
      if (dep != null) {
         handler = new ResourceDependencyHandler(new ResourceDependency[]{dep});
      } else {
         ResourceDependencies deps = (ResourceDependencies)clazz.getAnnotation(ResourceDependencies.class);
         if (deps != null) {
            handler = new ResourceDependencyHandler(deps.value());
         }
      }

      return handler;
   }
}
