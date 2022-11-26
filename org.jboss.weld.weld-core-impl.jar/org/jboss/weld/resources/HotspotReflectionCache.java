package org.jboss.weld.resources;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.metadata.TypeStore;

public class HotspotReflectionCache extends DefaultReflectionCache {
   private final Class annotationTypeLock;

   public HotspotReflectionCache(TypeStore store) {
      super(store);

      try {
         this.annotationTypeLock = Class.forName("sun.reflect.annotation.AnnotationType");
      } catch (ClassNotFoundException var3) {
         throw new WeldException(var3);
      }
   }

   protected Annotation[] internalGetAnnotations(AnnotatedElement element) {
      if (element instanceof Class) {
         Class clazz = (Class)element;
         if (clazz.isAnnotation()) {
            synchronized(this.annotationTypeLock) {
               return element.getAnnotations();
            }
         }
      }

      return element.getAnnotations();
   }
}
