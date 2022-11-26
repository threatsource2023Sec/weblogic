package org.jboss.weld.annotated.slim.backed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Method;
import javax.enterprise.inject.spi.AnnotatedMethod;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.serialization.MethodHolder;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class BackedAnnotatedMethod extends BackedAnnotatedCallable implements AnnotatedMethod, Serializable {
   public static AnnotatedMethod of(Method method, BackedAnnotatedType declaringType, SharedObjectCache sharedObjectCache) {
      BackedAnnotatedType downcastDeclaringType = (BackedAnnotatedType)Reflections.cast(declaringType);
      return new BackedAnnotatedMethod(method, downcastDeclaringType, sharedObjectCache);
   }

   public BackedAnnotatedMethod(Method method, BackedAnnotatedType declaringType, SharedObjectCache sharedObjectCache) {
      super(method, method.getGenericReturnType(), declaringType, sharedObjectCache);
   }

   public String toString() {
      return Formats.formatAnnotatedMethod(this);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SerializationProxy(this);
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }

   private static class SerializationProxy extends BackedAnnotatedMember.BackedAnnotatedMemberSerializationProxy {
      private static final long serialVersionUID = 8008578690970722095L;

      public SerializationProxy(BackedAnnotatedMethod method) {
         super(method.getDeclaringType(), MethodHolder.of((AnnotatedMethod)method));
      }

      private Object readResolve() throws ObjectStreamException {
         return this.resolve();
      }

      protected Iterable getCandidates() {
         return (Iterable)Reflections.cast(this.type.getMethods());
      }
   }
}
