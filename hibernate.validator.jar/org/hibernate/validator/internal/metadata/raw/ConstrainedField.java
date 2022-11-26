package org.hibernate.validator.internal.metadata.raw;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Set;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.util.StringHelper;

public class ConstrainedField extends AbstractConstrainedElement {
   private final Field field;

   public ConstrainedField(ConfigurationSource source, Field field, Set constraints, Set typeArgumentConstraints, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
      super(source, ConstrainedElement.ConstrainedElementKind.FIELD, constraints, typeArgumentConstraints, cascadingMetaDataBuilder);
      this.field = field;
   }

   public Field getField() {
      return this.field;
   }

   public String toString() {
      return "ConstrainedField [field=" + StringHelper.toShortString((Member)this.field) + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.field == null ? 0 : this.field.hashCode());
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
         ConstrainedField other = (ConstrainedField)obj;
         if (this.field == null) {
            if (other.field != null) {
               return false;
            }
         } else if (!this.field.equals(other.field)) {
            return false;
         }

         return true;
      }
   }
}
