package org.jboss.weld.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.Any.Literal;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.util.collections.ImmutableSet;

public final class EventMetadataImpl implements EventMetadata {
   private final Type type;
   private final InjectionPoint injectionPoint;
   private final Set qualifiers;
   private final Annotation[] qualifierArray;

   public EventMetadataImpl(Type type, InjectionPoint injectionPoint, Set qualifiers) {
      this(type, injectionPoint, qualifiers, (Annotation[])null);
   }

   public EventMetadataImpl(Type type, InjectionPoint injectionPoint, Annotation[] qualifiers) {
      this(type, injectionPoint, (Set)null, qualifiers);
   }

   private EventMetadataImpl(Type type, InjectionPoint injectionPoint, Set qualifiers, Annotation[] qualifierArray) {
      this.type = type;
      this.injectionPoint = injectionPoint;
      this.qualifiers = qualifiers;
      this.qualifierArray = qualifierArray;
   }

   public Set getQualifiers() {
      ImmutableSet.Builder builder = ImmutableSet.builder();
      builder.add(Literal.INSTANCE);
      if (this.qualifiers != null) {
         return builder.addAll((Iterable)this.qualifiers).build();
      } else if (this.qualifierArray != null) {
         return builder.addAll((Object[])this.qualifierArray).build();
      } else {
         throw new IllegalStateException();
      }
   }

   public InjectionPoint getInjectionPoint() {
      return this.injectionPoint;
   }

   public Type getType() {
      return this.type;
   }

   public String toString() {
      return "EventMetadataImpl [type=" + this.type + ", qualifiers=" + this.qualifiers + ", injectionPoint=" + this.injectionPoint + "]";
   }
}
