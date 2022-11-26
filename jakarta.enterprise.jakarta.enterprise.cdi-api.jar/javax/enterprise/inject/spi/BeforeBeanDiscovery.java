package javax.enterprise.inject.spi;

import java.lang.annotation.Annotation;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;

public interface BeforeBeanDiscovery {
   void addQualifier(Class var1);

   void addQualifier(AnnotatedType var1);

   void addScope(Class var1, boolean var2, boolean var3);

   void addStereotype(Class var1, Annotation... var2);

   void addInterceptorBinding(AnnotatedType var1);

   void addInterceptorBinding(Class var1, Annotation... var2);

   void addAnnotatedType(AnnotatedType var1);

   void addAnnotatedType(AnnotatedType var1, String var2);

   AnnotatedTypeConfigurator addAnnotatedType(Class var1, String var2);

   AnnotatedTypeConfigurator configureQualifier(Class var1);

   AnnotatedTypeConfigurator configureInterceptorBinding(Class var1);
}
