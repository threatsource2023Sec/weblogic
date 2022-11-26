package com.sun.faces.vendor;

import com.sun.faces.spi.DiscoverableInjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import org.mortbay.jetty.annotations.AnnotationParser;
import org.mortbay.jetty.plus.annotation.InjectionCollection;
import org.mortbay.jetty.plus.annotation.LifeCycleCallbackCollection;
import org.mortbay.jetty.plus.annotation.RunAsCollection;
import org.mortbay.jetty.webapp.WebAppContext;

public class Jetty6InjectionProvider extends DiscoverableInjectionProvider {
   private InjectionCollection injections = new InjectionCollection();
   private LifeCycleCallbackCollection callbacks = new LifeCycleCallbackCollection();

   public void inject(Object managedBean) throws InjectionProviderException {
      AnnotationParser.parseAnnotations((WebAppContext)WebAppContext.getCurrentWebAppContext(), managedBean.getClass(), (RunAsCollection)null, this.injections, this.callbacks);

      try {
         this.injections.inject(managedBean);
      } catch (Exception var3) {
         throw new InjectionProviderException(var3);
      }
   }

   public void invokePreDestroy(Object managedBean) throws InjectionProviderException {
      try {
         this.callbacks.callPreDestroyCallback(managedBean);
      } catch (Exception var3) {
         throw new InjectionProviderException(var3);
      }
   }

   public void invokePostConstruct(Object managedBean) throws InjectionProviderException {
      try {
         this.callbacks.callPostConstructCallback(managedBean);
      } catch (Exception var3) {
         throw new InjectionProviderException(var3);
      }
   }
}
