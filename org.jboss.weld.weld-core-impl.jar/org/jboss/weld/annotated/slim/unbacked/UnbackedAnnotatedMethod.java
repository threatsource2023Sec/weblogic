package org.jboss.weld.annotated.slim.unbacked;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class UnbackedAnnotatedMethod extends UnbackedAnnotatedMember implements AnnotatedMethod, Serializable {
   private final Method method;
   private final List parameters;

   public static AnnotatedMethod of(AnnotatedMethod originalMethod, UnbackedAnnotatedType declaringType, SharedObjectCache cache) {
      UnbackedAnnotatedType downcastDeclaringType = (UnbackedAnnotatedType)Reflections.cast(declaringType);
      return new UnbackedAnnotatedMethod(originalMethod.getBaseType(), originalMethod.getTypeClosure(), originalMethod.getAnnotations(), downcastDeclaringType, originalMethod.getParameters(), originalMethod.getJavaMember(), cache);
   }

   public UnbackedAnnotatedMethod(Type baseType, Set typeClosure, Set annotations, UnbackedAnnotatedType declaringType, List originalParameters, Method method, SharedObjectCache cache) {
      super(baseType, typeClosure, cache.getSharedSet(annotations), declaringType);
      this.method = method;
      List parameters = new ArrayList(originalParameters.size());
      Iterator var9 = originalParameters.iterator();

      while(var9.hasNext()) {
         AnnotatedParameter originalParameter = (AnnotatedParameter)var9.next();
         parameters.add(new UnbackedAnnotatedParameter(originalParameter.getBaseType(), originalParameter.getTypeClosure(), cache.getSharedSet(originalParameter.getAnnotations()), originalParameter.getPosition(), this));
      }

      this.parameters = ImmutableList.copyOf((Collection)parameters);
   }

   public Method getJavaMember() {
      return this.method;
   }

   public List getParameters() {
      return this.parameters;
   }

   public String toString() {
      return Formats.formatAnnotatedMethod(this);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new UnbackedMemberIdentifier(this.getDeclaringType(), AnnotatedTypes.createMethodId(this.method, this.getAnnotations(), this.getParameters()));
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }
}
