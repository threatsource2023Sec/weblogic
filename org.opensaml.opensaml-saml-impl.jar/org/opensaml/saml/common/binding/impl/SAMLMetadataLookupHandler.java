package org.opensaml.saml.common.binding.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.AbstractSAMLEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.metadata.resolver.RoleDescriptorResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLMetadataLookupHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAMLMetadataLookupHandler.class);
   @NonnullAfterInit
   private RoleDescriptorResolver metadataResolver;
   @Nonnull
   private Class entityContextClass = SAMLPeerEntityContext.class;

   public void setEntityContextClass(@Nonnull Class clazz) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.entityContextClass = (Class)Constraint.isNotNull(clazz, "SAML entity context class may not be null");
   }

   public void setRoleDescriptorResolver(@Nonnull RoleDescriptorResolver resolver) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.metadataResolver = (RoleDescriptorResolver)Constraint.isNotNull(resolver, "RoleDescriptorResolver cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.metadataResolver == null) {
         throw new ComponentInitializationException("RoleDescriptorResolver cannot be null");
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      AbstractSAMLEntityContext entityCtx = (AbstractSAMLEntityContext)messageContext.getSubcontext(this.entityContextClass);
      SAMLProtocolContext protocolCtx = (SAMLProtocolContext)messageContext.getSubcontext(SAMLProtocolContext.class);
      if (entityCtx != null && entityCtx.getEntityId() != null && entityCtx.getRole() != null) {
         EntityIdCriterion entityIdCriterion = new EntityIdCriterion(entityCtx.getEntityId());
         EntityRoleCriterion roleCriterion = new EntityRoleCriterion(entityCtx.getRole());
         ProtocolCriterion protocolCriterion = null;
         if (protocolCtx != null && protocolCtx.getProtocol() != null) {
            protocolCriterion = new ProtocolCriterion(protocolCtx.getProtocol());
         }

         CriteriaSet criteria = new CriteriaSet(new Criterion[]{entityIdCriterion, protocolCriterion, roleCriterion});

         try {
            RoleDescriptor roleMetadata = (RoleDescriptor)this.metadataResolver.resolveSingle(criteria);
            if (roleMetadata == null) {
               if (protocolCriterion != null) {
                  this.log.info("{} No metadata returned for {} in role {} with protocol {}", new Object[]{this.getLogPrefix(), entityCtx.getEntityId(), entityCtx.getRole(), protocolCriterion.getProtocol()});
               } else {
                  this.log.info("{} No metadata returned for {} in role {}", new Object[]{this.getLogPrefix(), entityCtx.getEntityId(), entityCtx.getRole()});
               }

               return;
            }

            SAMLMetadataContext metadataCtx = new SAMLMetadataContext();
            metadataCtx.setEntityDescriptor((EntityDescriptor)roleMetadata.getParent());
            metadataCtx.setRoleDescriptor(roleMetadata);
            entityCtx.addSubcontext(metadataCtx);
            this.log.debug("{} {} added to MessageContext as child of {}", new Object[]{this.getLogPrefix(), SAMLMetadataContext.class.getName(), this.entityContextClass.getName()});
         } catch (ResolverException var10) {
            this.log.error("{} ResolverException thrown during metadata lookup", this.getLogPrefix(), var10);
         }

      } else {
         this.log.info("{} SAML entity context class '{}' missing or did not contain an entityID or role", this.getLogPrefix(), this.entityContextClass.getName());
      }
   }
}
