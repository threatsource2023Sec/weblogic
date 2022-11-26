package org.opensaml.saml.metadata.resolver.filter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityRoleFilter implements MetadataFilter {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(EntityRoleFilter.class);
   @Nonnull
   @NonnullElements
   private List roleWhiteList = new ArrayList();
   private boolean removeRolelessEntityDescriptors;
   private boolean removeEmptyEntitiesDescriptors;
   @Nonnull
   private final QName extRoleDescriptor;

   public EntityRoleFilter(@Nullable List keptRoles) {
      if (keptRoles != null) {
         this.roleWhiteList.addAll(keptRoles);
      }

      this.roleWhiteList = Collections.unmodifiableList(this.roleWhiteList);
      this.removeRolelessEntityDescriptors = true;
      this.removeEmptyEntitiesDescriptors = true;
      this.extRoleDescriptor = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RoleDescriptor");
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getRoleWhiteList() {
      return this.roleWhiteList;
   }

   public boolean getRemoveRolelessEntityDescriptors() {
      return this.removeRolelessEntityDescriptors;
   }

   public void setRemoveRolelessEntityDescriptors(boolean remove) {
      this.removeRolelessEntityDescriptors = remove;
   }

   public boolean getRemoveEmptyEntitiesDescriptors() {
      return this.removeEmptyEntitiesDescriptors;
   }

   public void setRemoveEmptyEntitiesDescriptors(boolean remove) {
      this.removeEmptyEntitiesDescriptors = remove;
   }

   @Nullable
   public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
      if (metadata == null) {
         return null;
      } else {
         if (metadata instanceof EntitiesDescriptor) {
            this.filterEntitiesDescriptor((EntitiesDescriptor)metadata);
         } else {
            this.filterEntityDescriptor((EntityDescriptor)metadata);
         }

         return metadata;
      }
   }

   protected void filterEntitiesDescriptor(@Nonnull EntitiesDescriptor descriptor) throws FilterException {
      List entityDescriptors = descriptor.getEntityDescriptors();
      if (entityDescriptors != null && !entityDescriptors.isEmpty()) {
         List emptyEntityDescriptors = new ArrayList();
         Iterator entityDescriptorsItr = entityDescriptors.iterator();

         label63:
         while(true) {
            EntityDescriptor entityDescriptor;
            List entityRoles;
            do {
               do {
                  if (!entityDescriptorsItr.hasNext()) {
                     entityDescriptors.removeAll(emptyEntityDescriptors);
                     break label63;
                  }

                  entityDescriptor = (EntityDescriptor)entityDescriptorsItr.next();
                  this.filterEntityDescriptor(entityDescriptor);
               } while(!this.getRemoveRolelessEntityDescriptors());

               entityRoles = entityDescriptor.getRoleDescriptors();
            } while(entityRoles != null && !entityRoles.isEmpty());

            this.log.trace("Filtering out entity descriptor {} from entity group {}", entityDescriptor.getEntityID(), descriptor.getName());
            emptyEntityDescriptors.add(entityDescriptor);
         }
      }

      List entitiesDescriptors = descriptor.getEntitiesDescriptors();
      if (entitiesDescriptors != null && !entitiesDescriptors.isEmpty()) {
         List emptyEntitiesDescriptors = new ArrayList();
         Iterator entitiesDescriptorsItr = entitiesDescriptors.iterator();

         while(true) {
            EntitiesDescriptor entitiesDescriptor;
            do {
               do {
                  do {
                     if (!entitiesDescriptorsItr.hasNext()) {
                        entitiesDescriptors.removeAll(emptyEntitiesDescriptors);
                        return;
                     }

                     entitiesDescriptor = (EntitiesDescriptor)entitiesDescriptorsItr.next();
                     this.filterEntitiesDescriptor(entitiesDescriptor);
                  } while(!this.getRemoveEmptyEntitiesDescriptors());
               } while(entitiesDescriptor.getEntityDescriptors() != null && !entitiesDescriptor.getEntityDescriptors().isEmpty());
            } while(entitiesDescriptor.getEntitiesDescriptors() != null && !entitiesDescriptor.getEntitiesDescriptors().isEmpty());

            this.log.trace("Filtering out entity descriptor {} from entity group {}", entitiesDescriptor.getName(), descriptor.getName());
            emptyEntitiesDescriptors.add(entitiesDescriptor);
         }
      }
   }

   protected void filterEntityDescriptor(@Nonnull EntityDescriptor descriptor) throws FilterException {
      List roles = descriptor.getRoleDescriptors();
      if (roles != null && !roles.isEmpty()) {
         Iterator rolesItr = roles.iterator();

         while(rolesItr.hasNext()) {
            QName roleName = this.getRoleName((RoleDescriptor)rolesItr.next());
            if (!this.roleWhiteList.contains(roleName)) {
               this.log.trace("Filtering out role {} from entity {}", roleName, descriptor.getEntityID());
               rolesItr.remove();
            }
         }
      }

   }

   protected QName getRoleName(@Nonnull RoleDescriptor role) throws FilterException {
      QName roleName = role.getElementQName();
      if (this.extRoleDescriptor.equals(roleName)) {
         roleName = role.getSchemaType();
         if (roleName == null) {
            throw new FilterException("Role descriptor element was " + this.extRoleDescriptor + " but did not contain a schema type.  This is illegal.");
         }
      }

      return roleName;
   }
}
