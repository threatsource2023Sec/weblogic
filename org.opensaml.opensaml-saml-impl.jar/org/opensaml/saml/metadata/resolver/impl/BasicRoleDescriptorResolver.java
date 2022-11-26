package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.RoleDescriptorResolver;
import org.opensaml.saml.saml2.common.SAML2Support;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @deprecated */
@Deprecated
public class BasicRoleDescriptorResolver extends AbstractIdentifiedInitializableComponent implements RoleDescriptorResolver {
   private Logger log = LoggerFactory.getLogger(BasicRoleDescriptorResolver.class);
   private boolean requireValidMetadata;
   private MetadataResolver entityDescriptorResolver;

   public BasicRoleDescriptorResolver(@Nonnull MetadataResolver mdResolver) {
      this.entityDescriptorResolver = (MetadataResolver)Constraint.isNotNull(mdResolver, "Resolver for EntityDescriptors may not be null");
      this.setId(UUID.randomUUID().toString());
   }

   public boolean isRequireValidMetadata() {
      return this.requireValidMetadata;
   }

   public void setRequireValidMetadata(boolean require) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.requireValidMetadata = require;
   }

   @Nullable
   public RoleDescriptor resolveSingle(CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      Iterable iterable = this.resolve(criteria);
      if (iterable != null) {
         Iterator iterator = iterable.iterator();
         if (iterator != null && iterator.hasNext()) {
            return (RoleDescriptor)iterator.next();
         }
      }

      return null;
   }

   @Nonnull
   public Iterable resolve(CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      EntityIdCriterion entityIdCriterion = (EntityIdCriterion)criteria.get(EntityIdCriterion.class);
      EntityRoleCriterion entityRoleCriterion = (EntityRoleCriterion)criteria.get(EntityRoleCriterion.class);
      ProtocolCriterion protocolCriterion = (ProtocolCriterion)criteria.get(ProtocolCriterion.class);
      if (entityIdCriterion != null && !Strings.isNullOrEmpty(entityIdCriterion.getEntityId())) {
         if (entityRoleCriterion != null && entityRoleCriterion.getRole() != null) {
            if (protocolCriterion != null) {
               RoleDescriptor role = this.getRole(entityIdCriterion.getEntityId(), entityRoleCriterion.getRole(), protocolCriterion.getProtocol());
               return role != null ? Collections.singletonList(role) : Collections.emptyList();
            } else {
               return this.getRole(entityIdCriterion.getEntityId(), entityRoleCriterion.getRole());
            }
         } else {
            throw new ResolverException("Entity role was not supplied in criteria set");
         }
      } else {
         throw new ResolverException("Entity Id was not supplied in criteria set");
      }
   }

   @Nonnull
   @NonnullElements
   protected List getRole(@Nullable String entityID, @Nullable QName roleName) throws ResolverException {
      if (Strings.isNullOrEmpty(entityID)) {
         this.log.debug("EntityDescriptor entityID was null or empty, skipping search for roles");
         return Collections.emptyList();
      } else if (roleName == null) {
         this.log.debug("Role descriptor name was null, skipping search for roles");
         return Collections.emptyList();
      } else {
         List roleDescriptors = this.doGetRole(entityID, roleName);
         if (roleDescriptors != null && !roleDescriptors.isEmpty()) {
            Iterator roleDescItr = roleDescriptors.iterator();

            while(roleDescItr.hasNext()) {
               if (!this.isValid((XMLObject)roleDescItr.next())) {
                  this.log.debug("Metadata document contained a role of type {} for entity {}, but it was invalid", roleName, entityID);
                  roleDescItr.remove();
               }
            }

            if (roleDescriptors.isEmpty()) {
               this.log.debug("Entity descriptor {} did not contain any valid {} roles", entityID, roleName);
            }

            return roleDescriptors;
         } else {
            this.log.debug("Entity descriptor {} did not contain any {} roles", entityID, roleName);
            return Collections.emptyList();
         }
      }
   }

   @Nonnull
   @NonnullElements
   protected List doGetRole(@Nullable String entityID, @Nullable QName roleName) throws ResolverException {
      EntityDescriptor entity = this.doGetEntityDescriptor(entityID);
      if (entity == null) {
         this.log.debug("Metadata document did not contain a descriptor for entity {}", entityID);
         return Collections.emptyList();
      } else {
         List descriptors = entity.getRoleDescriptors(roleName);
         return (List)(descriptors != null && !descriptors.isEmpty() ? new ArrayList(descriptors) : Collections.emptyList());
      }
   }

   protected EntityDescriptor doGetEntityDescriptor(String entityID) throws ResolverException {
      return (EntityDescriptor)this.entityDescriptorResolver.resolveSingle(new CriteriaSet(new Criterion[]{new EntityIdCriterion(entityID)}));
   }

   @Nullable
   protected RoleDescriptor getRole(@Nullable String entityID, @Nullable QName roleName, @Nullable String supportedProtocol) throws ResolverException {
      if (Strings.isNullOrEmpty(entityID)) {
         this.log.debug("EntityDescriptor entityID was null or empty, skipping search for role");
         return null;
      } else if (roleName == null) {
         this.log.debug("Role descriptor name was null, skipping search for role");
         return null;
      } else if (Strings.isNullOrEmpty(supportedProtocol)) {
         this.log.debug("Supported protocol was null, skipping search for role.");
         return null;
      } else {
         RoleDescriptor role = this.doGetRole(entityID, roleName, supportedProtocol);
         if (role == null) {
            this.log.debug("Metadata document does not contain a role of type {} supporting protocol {} for entity {}", new Object[]{roleName, supportedProtocol, entityID});
            return null;
         } else if (!this.isValid(role)) {
            this.log.debug("Metadata document contained a role of type {} supporting protocol {} for entity {}, but it was not longer valid", new Object[]{roleName, supportedProtocol, entityID});
            return null;
         } else {
            return role;
         }
      }
   }

   protected RoleDescriptor doGetRole(String entityID, QName roleName, String supportedProtocol) throws ResolverException {
      List roles = this.doGetRole(entityID, roleName);
      if (roles != null && !roles.isEmpty()) {
         Iterator rolesItr = roles.iterator();
         RoleDescriptor role = null;

         do {
            if (!rolesItr.hasNext()) {
               return null;
            }

            role = (RoleDescriptor)rolesItr.next();
         } while(role == null || !role.isSupportedProtocol(supportedProtocol));

         return role;
      } else {
         this.log.debug("Metadata document did not contain any role descriptors of type {} for entity {}", roleName, entityID);
         return null;
      }
   }

   protected boolean isValid(XMLObject descriptor) {
      if (descriptor == null) {
         return false;
      } else {
         return !this.isRequireValidMetadata() ? true : SAML2Support.isValid(descriptor);
      }
   }
}
