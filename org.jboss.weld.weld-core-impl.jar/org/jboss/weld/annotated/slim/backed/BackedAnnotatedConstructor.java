package org.jboss.weld.annotated.slim.backed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.serialization.ConstructorHolder;
import org.jboss.weld.util.reflection.Formats;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class BackedAnnotatedConstructor extends BackedAnnotatedCallable implements AnnotatedConstructor, Serializable {
   public static AnnotatedConstructor of(Constructor constructor, BackedAnnotatedType declaringType, SharedObjectCache sharedObjectCache) {
      return new BackedAnnotatedConstructor(constructor, declaringType, sharedObjectCache);
   }

   public BackedAnnotatedConstructor(Constructor constructor, BackedAnnotatedType declaringType, SharedObjectCache sharedObjectCache) {
      super(constructor, constructor.getDeclaringClass(), declaringType, sharedObjectCache);
   }

   protected List initParameters(Constructor member, SharedObjectCache sharedObjectCache) {
      int length = member.getParameterTypes().length;
      return length == member.getGenericParameterTypes().length && length == member.getParameterAnnotations().length ? BackedAnnotatedParameter.forExecutable(member, this, sharedObjectCache) : Collections.emptyList();
   }

   public String toString() {
      return Formats.formatAnnotatedConstructor(this);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SerializationProxy(this);
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }

   private static class SerializationProxy extends BackedAnnotatedMember.BackedAnnotatedMemberSerializationProxy {
      private static final long serialVersionUID = -2726172060851333254L;

      public SerializationProxy(BackedAnnotatedConstructor constructor) {
         super(constructor.getDeclaringType(), new ConstructorHolder((Constructor)constructor.getJavaMember()));
      }

      private Object readResolve() throws ObjectStreamException {
         return this.resolve();
      }

      protected Iterable getCandidates() {
         return this.type.getConstructors();
      }
   }
}
