package com.sun.faces.spi;

import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;

public abstract class AnnotationProvider {
   protected ServletContext servletContext;
   protected AnnotationProvider wrappedAnnotationProvider;

   public AnnotationProvider(ServletContext servletContext) {
      this.initialize(servletContext, (AnnotationProvider)null);
   }

   public AnnotationProvider() {
   }

   final void initialize(ServletContext servletContext, AnnotationProvider wrappedAnnotationProvider) {
      if (this.servletContext == null) {
         this.servletContext = servletContext;
      }

      if (this.wrappedAnnotationProvider == null) {
         this.wrappedAnnotationProvider = wrappedAnnotationProvider;
      }

   }

   public abstract Map getAnnotatedClasses(Set var1);
}
