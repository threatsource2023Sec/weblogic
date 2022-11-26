package org.jboss.weld.annotated.slim.unbacked;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_NO_SUITABLE_CONSTRUCTOR", "SE_NO_SERIALVERSIONID"},
   justification = "False positive from FindBugs - serialization is handled by SerializationProxy."
)
public class UnbackedAnnotatedType extends UnbackedAnnotated implements SlimAnnotatedType, Serializable {
   private final Class javaClass;
   private final Set constructors;
   private final Set methods;
   private final Set fields;
   private final AnnotatedTypeIdentifier identifier;

   public static UnbackedAnnotatedType additionalAnnotatedType(String contextId, AnnotatedType source, String bdaId, String suffix, SharedObjectCache cache) {
      return new UnbackedAnnotatedType(source, AnnotatedTypeIdentifier.of(contextId, bdaId, source.getJavaClass().getName(), suffix, false), cache);
   }

   public static UnbackedAnnotatedType modifiedAnnotatedType(SlimAnnotatedType originalType, AnnotatedType source, SharedObjectCache cache) {
      AnnotatedTypeIdentifier identifier = AnnotatedTypeIdentifier.forModifiedAnnotatedType((AnnotatedTypeIdentifier)originalType.getIdentifier());
      return new UnbackedAnnotatedType(source, identifier, cache);
   }

   private UnbackedAnnotatedType(AnnotatedType source, AnnotatedTypeIdentifier identifier, SharedObjectCache cache) {
      super(source.getBaseType(), source.getTypeClosure(), source.getAnnotations());
      this.javaClass = source.getJavaClass();
      ImmutableSet.Builder constructors = ImmutableSet.builder();
      Iterator var5 = source.getConstructors().iterator();

      while(var5.hasNext()) {
         AnnotatedConstructor constructor = (AnnotatedConstructor)var5.next();
         constructors.add(UnbackedAnnotatedConstructor.of(constructor, this, cache));
      }

      this.constructors = constructors.build();
      ImmutableSet.Builder methods = ImmutableSet.builder();
      Iterator var10 = source.getMethods().iterator();

      while(var10.hasNext()) {
         AnnotatedMethod originalMethod = (AnnotatedMethod)var10.next();
         methods.add(UnbackedAnnotatedMethod.of(originalMethod, this, cache));
      }

      this.methods = methods.build();
      ImmutableSet.Builder fields = ImmutableSet.builder();
      Iterator var12 = source.getFields().iterator();

      while(var12.hasNext()) {
         AnnotatedField originalField = (AnnotatedField)var12.next();
         fields.add(UnbackedAnnotatedField.of(originalField, this, cache));
      }

      this.fields = fields.build();
      this.identifier = identifier;
   }

   public Class getJavaClass() {
      return this.javaClass;
   }

   public Set getConstructors() {
      return this.constructors;
   }

   public Set getMethods() {
      return this.methods;
   }

   public Set getFields() {
      return this.fields;
   }

   public String toString() {
      return Formats.formatAnnotatedType(this);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SlimAnnotatedType.SerializationProxy(this.getIdentifier());
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }

   public void clear() {
   }

   public int hashCode() {
      return this.identifier.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof UnbackedAnnotatedType) {
         UnbackedAnnotatedType that = (UnbackedAnnotatedType)Reflections.cast(obj);
         return Objects.equals(this.identifier, that.identifier);
      } else {
         return false;
      }
   }

   public AnnotatedTypeIdentifier getIdentifier() {
      return this.identifier;
   }
}
