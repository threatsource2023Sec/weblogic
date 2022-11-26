package org.jboss.weld.util;

import java.lang.reflect.Method;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.resources.spi.ResourceLoader;

public class AnnotationApiAbstraction extends ApiAbstraction implements Service {
   public final Class PRIORITY_ANNOTATION_CLASS = this.annotationTypeForName("javax.annotation.Priority");
   private final Method PRIORITY_VALUE;

   public AnnotationApiAbstraction(ResourceLoader resourceLoader) {
      super(resourceLoader);
      Method method = null;
      if (!this.PRIORITY_ANNOTATION_CLASS.equals(ApiAbstraction.DummyAnnotation.class)) {
         try {
            method = this.PRIORITY_ANNOTATION_CLASS.getMethod("value");
         } catch (Throwable var4) {
            throw new WeldException(var4);
         }
      }

      this.PRIORITY_VALUE = method;
   }

   public Integer getPriority(Object annotationInstance) {
      if (this.PRIORITY_VALUE == null) {
         return null;
      } else {
         try {
            return (Integer)this.PRIORITY_VALUE.invoke(annotationInstance);
         } catch (Throwable var3) {
            return null;
         }
      }
   }

   public void cleanup() {
   }
}
