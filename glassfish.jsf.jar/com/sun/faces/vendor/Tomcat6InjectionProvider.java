package com.sun.faces.vendor;

import com.sun.faces.spi.DiscoverableInjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import javax.servlet.ServletContext;
import org.apache.AnnotationProcessor;

public class Tomcat6InjectionProvider extends DiscoverableInjectionProvider {
   private ServletContext servletContext;

   public Tomcat6InjectionProvider(ServletContext servletContext) {
      this.servletContext = servletContext;
   }

   public void inject(Object managedBean) throws InjectionProviderException {
      try {
         this.getProcessor().processAnnotations(managedBean);
      } catch (Exception var3) {
         throw new InjectionProviderException(var3);
      }
   }

   public void invokePreDestroy(Object managedBean) throws InjectionProviderException {
      try {
         this.getProcessor().preDestroy(managedBean);
      } catch (Exception var3) {
         throw new InjectionProviderException(var3);
      }
   }

   public void invokePostConstruct(Object managedBean) throws InjectionProviderException {
      try {
         this.getProcessor().postConstruct(managedBean);
      } catch (Exception var3) {
         throw new InjectionProviderException(var3);
      }
   }

   private AnnotationProcessor getProcessor() {
      return (AnnotationProcessor)this.servletContext.getAttribute(AnnotationProcessor.class.getName());
   }
}
