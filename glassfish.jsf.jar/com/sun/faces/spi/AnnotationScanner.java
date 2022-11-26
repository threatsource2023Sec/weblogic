package com.sun.faces.spi;

import java.util.Collection;
import java.util.Map;
import javax.servlet.ServletContext;

public interface AnnotationScanner {
   Map getAnnotatedClassesInCurrentModule(ServletContext var1) throws InjectionProviderException;

   public interface ScannedAnnotation {
      Collection getDefiningURIs();

      String getFullyQualifiedClassName();
   }
}
