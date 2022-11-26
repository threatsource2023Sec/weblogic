package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.MoreObjects;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public class RoleMetadataIndex implements MetadataIndex {
   @Nullable
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set generateKeys(@Nonnull CriteriaSet criteriaSet) {
      Constraint.isNotNull(criteriaSet, "CriteriaSet was null");
      EntityRoleCriterion roleCrit = (EntityRoleCriterion)criteriaSet.get(EntityRoleCriterion.class);
      if (roleCrit != null) {
         HashSet result = new HashSet();
         result.add(new RoleMetadataIndexKey(roleCrit.getRole()));
         return result;
      } else {
         return null;
      }
   }

   @Nullable
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set generateKeys(@Nonnull EntityDescriptor descriptor) {
      Constraint.isNotNull(descriptor, "EntityDescriptor was null");
      HashSet result = new HashSet();
      Iterator var3 = descriptor.getRoleDescriptors().iterator();

      while(var3.hasNext()) {
         RoleDescriptor role = (RoleDescriptor)var3.next();
         QName type = role.getSchemaType();
         if (type != null) {
            result.add(new RoleMetadataIndexKey(type));
         } else {
            result.add(new RoleMetadataIndexKey(role.getElementQName()));
         }
      }

      return result;
   }

   protected static class RoleMetadataIndexKey implements MetadataIndexKey {
      @Nonnull
      private final QName role;

      public RoleMetadataIndexKey(@Nonnull QName samlRole) {
         this.role = (QName)Constraint.isNotNull(samlRole, "SAML role cannot be null");
      }

      @Nonnull
      public QName getRole() {
         return this.role;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("role", this.role).toString();
      }

      public int hashCode() {
         return this.role.hashCode();
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else {
            return obj instanceof RoleMetadataIndexKey ? this.role.equals(((RoleMetadataIndexKey)obj).role) : false;
         }
      }
   }
}
