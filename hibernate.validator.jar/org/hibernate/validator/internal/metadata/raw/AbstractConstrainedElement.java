package org.hibernate.validator.internal.metadata.raw;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.util.CollectionHelper;

public abstract class AbstractConstrainedElement implements ConstrainedElement {
   private final ConstrainedElement.ConstrainedElementKind kind;
   protected final ConfigurationSource source;
   protected final Set constraints;
   protected final CascadingMetaDataBuilder cascadingMetaDataBuilder;
   protected final Set typeArgumentConstraints;

   public AbstractConstrainedElement(ConfigurationSource source, ConstrainedElement.ConstrainedElementKind kind, Set constraints, Set typeArgumentConstraints, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
      this.kind = kind;
      this.source = source;
      this.constraints = constraints != null ? CollectionHelper.toImmutableSet(constraints) : Collections.emptySet();
      this.typeArgumentConstraints = typeArgumentConstraints != null ? CollectionHelper.toImmutableSet(typeArgumentConstraints) : Collections.emptySet();
      this.cascadingMetaDataBuilder = cascadingMetaDataBuilder;
   }

   public ConstrainedElement.ConstrainedElementKind getKind() {
      return this.kind;
   }

   public Iterator iterator() {
      return this.constraints.iterator();
   }

   public Set getConstraints() {
      return this.constraints;
   }

   public Set getTypeArgumentConstraints() {
      return this.typeArgumentConstraints;
   }

   public CascadingMetaDataBuilder getCascadingMetaDataBuilder() {
      return this.cascadingMetaDataBuilder;
   }

   public boolean isConstrained() {
      return this.cascadingMetaDataBuilder.isMarkedForCascadingOnAnnotatedObjectOrContainerElements() || this.cascadingMetaDataBuilder.hasGroupConversionsOnAnnotatedObjectOrContainerElements() || !this.constraints.isEmpty() || !this.typeArgumentConstraints.isEmpty();
   }

   public ConfigurationSource getSource() {
      return this.source;
   }

   public String toString() {
      return "AbstractConstrainedElement [kind=" + this.kind + ", source=" + this.source + ", constraints=" + this.constraints + ", cascadingMetaDataBuilder=" + this.cascadingMetaDataBuilder + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.source == null ? 0 : this.source.hashCode());
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
         AbstractConstrainedElement other = (AbstractConstrainedElement)obj;
         return this.source == other.source;
      }
   }
}
