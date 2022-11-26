package org.jboss.weld.annotated.slim.unbacked;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.reflection.Formats;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class UnbackedAnnotatedParameter extends UnbackedAnnotated implements AnnotatedParameter, Serializable {
   private final int position;
   private final AnnotatedCallable declaringCallable;

   public UnbackedAnnotatedParameter(Type baseType, Set typeClosure, Set annotations, int position, AnnotatedCallable declaringCallable) {
      super(baseType, typeClosure, annotations);
      this.position = position;
      this.declaringCallable = declaringCallable;
   }

   public int getPosition() {
      return this.position;
   }

   public AnnotatedCallable getDeclaringCallable() {
      return this.declaringCallable;
   }

   public String toString() {
      return Formats.formatAnnotatedParameter(this);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SerializationProxy(this);
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }

   private static class SerializationProxy implements Serializable {
      private static final long serialVersionUID = 8979519845687646272L;
      private final AnnotatedCallable callable;
      private final int position;

      public SerializationProxy(UnbackedAnnotatedParameter parameter) {
         this.callable = parameter.getDeclaringCallable();
         this.position = parameter.getPosition();
      }

      private Object readResolve() throws ObjectStreamException {
         return this.callable.getParameters().get(this.position);
      }
   }
}
