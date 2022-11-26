package org.jboss.weld.annotated.slim;

import java.io.ObjectStreamException;
import java.io.Serializable;
import javax.enterprise.inject.spi.AnnotatedType;
import org.jboss.weld.Container;
import org.jboss.weld.annotated.Identified;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;

public interface SlimAnnotatedType extends AnnotatedType, Identified {
   void clear();

   public static class SerializationProxy implements Serializable {
      private static final long serialVersionUID = 6800705438537396237L;
      private final AnnotatedTypeIdentifier identifier;

      public SerializationProxy(AnnotatedTypeIdentifier identifier) {
         this.identifier = identifier;
      }

      private Object readResolve() throws ObjectStreamException {
         SlimAnnotatedType type = ((ClassTransformer)Container.instance(this.identifier).services().get(ClassTransformer.class)).getSlimAnnotatedTypeById(this.identifier);
         if (type == null) {
            type = this.tryToLoadUnknownBackedAnnotatedType();
         }

         if (type == null) {
            throw MetadataLogger.LOG.annotatedTypeDeserializationFailure(this.identifier);
         } else {
            return type;
         }
      }

      private SlimAnnotatedType tryToLoadUnknownBackedAnnotatedType() {
         if (this.identifier.getSuffix() == null && !this.identifier.isModified()) {
            BeanManagerImpl manager = Container.instance(this.identifier).getBeanManager(this.identifier.getBdaId());
            if (manager == null) {
               return null;
            } else {
               ResourceLoader resourceLoader = (ResourceLoader)manager.getServices().get(ResourceLoader.class);
               Class clazz = null;

               try {
                  clazz = resourceLoader.classForName(this.identifier.getClassName());
               } catch (ResourceLoadingException var6) {
                  MetadataLogger.LOG.catchingDebug(var6);
                  return null;
               }

               try {
                  return ((ClassTransformer)manager.getServices().get(ClassTransformer.class)).getBackedAnnotatedType(clazz, this.identifier.getBdaId());
               } catch (ResourceLoadingException var5) {
                  MetadataLogger.LOG.catchingDebug(var5);
                  return null;
               }
            }
         } else {
            return null;
         }
      }
   }
}
