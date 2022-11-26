package org.opensaml.saml.metadata;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class EntityGroupName {
   @Nonnull
   @NotEmpty
   private String name;

   public EntityGroupName(@Nonnull @NotEmpty String newName) {
      this.name = (String)Constraint.isNotNull(StringSupport.trimOrNull(newName), "Entity group name may not be null or empty");
   }

   @Nonnull
   @NotEmpty
   public String getName() {
      return this.name;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return obj instanceof EntityGroupName ? this.name.equals(((EntityGroupName)obj).getName()) : false;
      }
   }
}
