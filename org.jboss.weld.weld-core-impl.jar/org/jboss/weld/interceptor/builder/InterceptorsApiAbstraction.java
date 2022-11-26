package org.jboss.weld.interceptor.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.enterprise.inject.spi.Annotated;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.ApiAbstraction;

public class InterceptorsApiAbstraction extends ApiAbstraction implements Service {
   private final Class INTERCEPTORS_ANNOTATION_CLASS = this.annotationTypeForName("javax.interceptor.Interceptors");
   private final Method interceptorsValueMethod;
   private final Class EXCLUDE_CLASS_INTERCEPTORS_ANNOTATION_CLASS = this.annotationTypeForName("javax.interceptor.ExcludeClassInterceptors");

   public InterceptorsApiAbstraction(ResourceLoader resourceLoader) {
      super(resourceLoader);
      if (ApiAbstraction.DummyAnnotation.class.isAssignableFrom(this.INTERCEPTORS_ANNOTATION_CLASS)) {
         this.interceptorsValueMethod = null;
      } else {
         try {
            this.interceptorsValueMethod = this.INTERCEPTORS_ANNOTATION_CLASS.getMethod("value");
         } catch (Exception var3) {
            throw UtilLogger.LOG.annotationValuesInaccessible(var3);
         }
      }

   }

   public Class getInterceptorsAnnotationClass() {
      return this.INTERCEPTORS_ANNOTATION_CLASS;
   }

   public Class getExcludeClassInterceptorsAnnotationClass() {
      return this.EXCLUDE_CLASS_INTERCEPTORS_ANNOTATION_CLASS;
   }

   public Class[] extractInterceptorClasses(Annotated annotated) {
      Annotation annotation = annotated.getAnnotation(this.INTERCEPTORS_ANNOTATION_CLASS);
      if (annotation != null) {
         try {
            return (Class[])((Class[])this.interceptorsValueMethod.invoke(annotation));
         } catch (Exception var4) {
            throw UtilLogger.LOG.annotationValuesInaccessible(var4);
         }
      } else {
         return null;
      }
   }

   public void cleanup() {
   }
}
