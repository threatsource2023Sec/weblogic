package org.hibernate.validator.internal.metadata.raw;

import java.util.Collections;
import java.util.Set;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;

public class ConstrainedType extends AbstractConstrainedElement {
   private final Class beanClass;

   public ConstrainedType(ConfigurationSource source, Class beanClass, Set constraints) {
      super(source, ConstrainedElement.ConstrainedElementKind.TYPE, constraints, Collections.emptySet(), CascadingMetaDataBuilder.nonCascading());
      this.beanClass = beanClass;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.beanClass == null ? 0 : this.beanClass.hashCode());
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
         ConstrainedType other = (ConstrainedType)obj;
         if (this.beanClass == null) {
            if (other.beanClass != null) {
               return false;
            }
         } else if (!this.beanClass.equals(other.beanClass)) {
            return false;
         }

         return true;
      }
   }
}
