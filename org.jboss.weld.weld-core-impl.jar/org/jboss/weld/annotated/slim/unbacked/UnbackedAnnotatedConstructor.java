package org.jboss.weld.annotated.slim.unbacked;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.reflection.Formats;

@SuppressFBWarnings(
   value = {"SE_BAD_FIELD", "SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class UnbackedAnnotatedConstructor extends UnbackedAnnotatedMember implements AnnotatedConstructor, Serializable {
   private final Constructor constructor;
   private final List parameters;

   public static AnnotatedConstructor of(AnnotatedConstructor originalConstructor, UnbackedAnnotatedType declaringType, SharedObjectCache cache) {
      return new UnbackedAnnotatedConstructor(originalConstructor.getBaseType(), originalConstructor.getTypeClosure(), originalConstructor.getAnnotations(), declaringType, originalConstructor.getParameters(), originalConstructor.getJavaMember(), cache);
   }

   public UnbackedAnnotatedConstructor(Type baseType, Set typeClosure, Set annotations, UnbackedAnnotatedType declaringType, List originalParameters, Constructor constructor, SharedObjectCache cache) {
      super(baseType, typeClosure, cache.getSharedSet(annotations), declaringType);
      this.constructor = constructor;
      List parameters = new ArrayList(originalParameters.size());
      Iterator var9 = originalParameters.iterator();

      while(var9.hasNext()) {
         AnnotatedParameter originalParameter = (AnnotatedParameter)var9.next();
         parameters.add(new UnbackedAnnotatedParameter(originalParameter.getBaseType(), originalParameter.getTypeClosure(), cache.getSharedSet(originalParameter.getAnnotations()), originalParameter.getPosition(), this));
      }

      this.parameters = ImmutableList.copyOf((Collection)parameters);
   }

   public Constructor getJavaMember() {
      return this.constructor;
   }

   public List getParameters() {
      return this.parameters;
   }

   public String toString() {
      return Formats.formatAnnotatedConstructor(this);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new UnbackedMemberIdentifier(this.getDeclaringType(), AnnotatedTypes.createConstructorId(this.constructor, this.getAnnotations(), this.getParameters()));
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }
}
