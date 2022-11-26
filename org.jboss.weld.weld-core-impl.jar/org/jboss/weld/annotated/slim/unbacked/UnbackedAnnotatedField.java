package org.jboss.weld.annotated.slim.unbacked;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedField;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class UnbackedAnnotatedField extends UnbackedAnnotatedMember implements AnnotatedField, Serializable {
   private final Field field;

   public static AnnotatedField of(AnnotatedField originalField, UnbackedAnnotatedType declaringType, SharedObjectCache cache) {
      UnbackedAnnotatedType downcastDeclaringType = (UnbackedAnnotatedType)Reflections.cast(declaringType);
      return new UnbackedAnnotatedField(originalField.getBaseType(), originalField.getTypeClosure(), cache.getSharedSet(originalField.getAnnotations()), originalField.getJavaMember(), downcastDeclaringType);
   }

   public UnbackedAnnotatedField(Type baseType, Set typeClosure, Set annotations, Field field, UnbackedAnnotatedType declaringType) {
      super(baseType, typeClosure, annotations, declaringType);
      this.field = field;
   }

   public Field getJavaMember() {
      return this.field;
   }

   public String toString() {
      return Formats.formatAnnotatedField(this);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new UnbackedMemberIdentifier(this.getDeclaringType(), AnnotatedTypes.createFieldId(this));
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }
}
