package org.jboss.weld.annotated.slim.backed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import javax.enterprise.inject.spi.AnnotatedField;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.serialization.FieldHolder;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class BackedAnnotatedField extends BackedAnnotatedMember implements AnnotatedField, Serializable {
   private final Field field;

   public static AnnotatedField of(Field field, BackedAnnotatedType declaringType, SharedObjectCache sharedObjectCache) {
      BackedAnnotatedType downcastDeclaringType = (BackedAnnotatedType)Reflections.cast(declaringType);
      return new BackedAnnotatedField(field.getGenericType(), field, downcastDeclaringType, sharedObjectCache);
   }

   public BackedAnnotatedField(Type baseType, Field field, BackedAnnotatedType declaringType, SharedObjectCache sharedObjectCache) {
      super(baseType, declaringType, sharedObjectCache);
      this.field = field;
   }

   public Field getJavaMember() {
      return this.field;
   }

   public Annotation getAnnotation(Class annotationType) {
      return this.field.getAnnotation(annotationType);
   }

   protected AnnotatedElement getAnnotatedElement() {
      return this.field;
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.field.isAnnotationPresent(annotationType);
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.field == null ? 0 : this.field.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         BackedAnnotatedField other = (BackedAnnotatedField)obj;
         if (this.field == null) {
            if (other.field != null) {
               return false;
            }
         } else if (!this.field.equals(other.field)) {
            return false;
         }

         return true;
      }
   }

   public String toString() {
      return Formats.formatAnnotatedField(this);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SerializationProxy(this);
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }

   private static class SerializationProxy extends BackedAnnotatedMember.BackedAnnotatedMemberSerializationProxy {
      private static final long serialVersionUID = -8041111397369568219L;

      public SerializationProxy(BackedAnnotatedField field) {
         super(field.getDeclaringType(), new FieldHolder(field.getJavaMember()));
      }

      private Object readResolve() throws ObjectStreamException {
         return this.resolve();
      }

      protected Iterable getCandidates() {
         return (Iterable)Reflections.cast(this.type.getFields());
      }
   }
}
