package org.hibernate.validator.internal.metadata.raw;

import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ConstrainedParameter extends AbstractConstrainedElement {
   private final Executable executable;
   private final Type type;
   private final int index;

   public ConstrainedParameter(ConfigurationSource source, Executable executable, Type type, int index) {
      this(source, executable, type, index, Collections.emptySet(), Collections.emptySet(), CascadingMetaDataBuilder.nonCascading());
   }

   public ConstrainedParameter(ConfigurationSource source, Executable executable, Type type, int index, Set constraints, Set typeArgumentConstraints, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
      super(source, ConstrainedElement.ConstrainedElementKind.PARAMETER, constraints, typeArgumentConstraints, cascadingMetaDataBuilder);
      this.executable = executable;
      this.type = type;
      this.index = index;
   }

   public Type getType() {
      return this.type;
   }

   public Executable getExecutable() {
      return this.executable;
   }

   public int getIndex() {
      return this.index;
   }

   public ConstrainedParameter merge(ConstrainedParameter other) {
      ConfigurationSource mergedSource = ConfigurationSource.max(this.source, other.source);
      Set mergedConstraints = CollectionHelper.newHashSet((Collection)this.constraints);
      mergedConstraints.addAll(other.constraints);
      Set mergedTypeArgumentConstraints = new HashSet(this.typeArgumentConstraints);
      mergedTypeArgumentConstraints.addAll(other.typeArgumentConstraints);
      CascadingMetaDataBuilder mergedCascadingMetaData = this.cascadingMetaDataBuilder.merge(other.cascadingMetaDataBuilder);
      return new ConstrainedParameter(mergedSource, this.executable, this.type, this.index, mergedConstraints, mergedTypeArgumentConstraints, mergedCascadingMetaData);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.getConstraints().iterator();

      while(var2.hasNext()) {
         MetaConstraint oneConstraint = (MetaConstraint)var2.next();
         sb.append(oneConstraint.getDescriptor().getAnnotation().annotationType().getSimpleName());
         sb.append(", ");
      }

      String constraintsAsString = sb.length() > 0 ? sb.substring(0, sb.length() - 2) : sb.toString();
      return "ParameterMetaData [executable=" + this.executable + ", index=" + this.index + "], constraints=[" + constraintsAsString + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + this.index;
      result = 31 * result + (this.executable == null ? 0 : this.executable.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ConstrainedParameter other = (ConstrainedParameter)obj;
         if (this.index != other.index) {
            return false;
         } else {
            if (this.executable == null) {
               if (other.executable != null) {
                  return false;
               }
            } else if (!this.executable.equals(other.executable)) {
               return false;
            }

            return true;
         }
      }
   }
}
