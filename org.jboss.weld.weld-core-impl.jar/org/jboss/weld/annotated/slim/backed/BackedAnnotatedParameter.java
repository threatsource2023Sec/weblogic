package org.jboss.weld.annotated.slim.backed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.ReflectionCache;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class BackedAnnotatedParameter extends BackedAnnotated implements AnnotatedParameter, Serializable {
   private final Parameter parameter;
   private final int position;
   private final BackedAnnotatedCallable declaringCallable;

   public static List forExecutable(Executable executable, BackedAnnotatedCallable declaringCallable, SharedObjectCache cache) {
      Parameter[] parameters = executable.getParameters();
      if (parameters.length == 0) {
         return Collections.emptyList();
      } else {
         ImmutableList.Builder builder = ImmutableList.builder();

         for(int i = 0; i < parameters.length; ++i) {
            builder.add(of(parameters[i], i, declaringCallable, cache));
         }

         return builder.build();
      }
   }

   public static AnnotatedParameter of(Parameter parameter, int position, BackedAnnotatedCallable declaringCallable, SharedObjectCache sharedObjectCache) {
      return new BackedAnnotatedParameter(parameter, position, declaringCallable, sharedObjectCache);
   }

   private BackedAnnotatedParameter(Parameter parameter, int position, BackedAnnotatedCallable declaringCallable, SharedObjectCache sharedObjectCache) {
      super(parameter.getParameterizedType(), sharedObjectCache);
      this.parameter = parameter;
      this.position = position;
      this.declaringCallable = declaringCallable;
   }

   public int getPosition() {
      return this.position;
   }

   public Parameter getJavaParameter() {
      return this.parameter;
   }

   public BackedAnnotatedCallable getDeclaringCallable() {
      return this.declaringCallable;
   }

   public Annotation getAnnotation(Class annotationType) {
      Iterator var2 = this.getAnnotations().iterator();

      Annotation annotation;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         annotation = (Annotation)var2.next();
      } while(!annotation.annotationType().equals(annotationType));

      return (Annotation)Reflections.cast(annotation);
   }

   public Set getAnnotations() {
      return this.getReflectionCache().getAnnotations(this.parameter);
   }

   protected AnnotatedElement getAnnotatedElement() {
      return null;
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.getAnnotation(annotationType) != null;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.parameter == null ? 0 : this.parameter.hashCode());
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
         BackedAnnotatedParameter other = (BackedAnnotatedParameter)obj;
         if (this.parameter == null) {
            if (other.parameter != null) {
               return false;
            }
         } else if (!this.parameter.equals(other.parameter)) {
            return false;
         }

         return true;
      }
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

   protected ReflectionCache getReflectionCache() {
      return this.getDeclaringCallable().getDeclaringType().getReflectionCache();
   }

   private static class SerializationProxy implements Serializable {
      private static final long serialVersionUID = 8784172191880064479L;
      private final AnnotatedCallable callable;
      private final int position;

      public SerializationProxy(BackedAnnotatedParameter parameter) {
         this.callable = parameter.getDeclaringCallable();
         this.position = parameter.getPosition();
      }

      private Object readResolve() throws ObjectStreamException {
         return this.callable.getParameters().get(this.position);
      }
   }
}
