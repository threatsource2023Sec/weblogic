package org.opensaml.saml.common.binding.security.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.ConstraintViolationException;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.AbstractAuthenticatableSAMLEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.security.messaging.impl.BaseClientCertAuthSecurityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLMDClientCertAuthSecurityHandler extends BaseClientCertAuthSecurityHandler {
   private Logger log = LoggerFactory.getLogger(SAMLMDClientCertAuthSecurityHandler.class);
   private Class entityContextClass = SAMLPeerEntityContext.class;

   @Nonnull
   public Class getEntityContextClass() {
      return this.entityContextClass;
   }

   public void setEntityContextClass(@Nonnull Class clazz) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.entityContextClass = (Class)Constraint.isNotNull(clazz, "The SAML entity context class may not be null");
   }

   @Nonnull
   protected CriteriaSet buildCriteriaSet(@Nullable String entityID, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      CriteriaSet criteriaSet = super.buildCriteriaSet(entityID, messageContext);

      try {
         this.log.trace("Attempting to build criteria based on contents of entity contxt class of type: {}", this.entityContextClass.getName());
         AbstractAuthenticatableSAMLEntityContext entityContext = (AbstractAuthenticatableSAMLEntityContext)messageContext.getSubcontext(this.entityContextClass);
         Constraint.isNotNull(entityContext, "Required authenticatable SAML entity context was not present in message context: " + this.entityContextClass.getName());
         Constraint.isNotNull(entityContext.getRole(), "SAML entity role was null");
         criteriaSet.add(new EntityRoleCriterion(entityContext.getRole()));
         SAMLProtocolContext protocolContext = (SAMLProtocolContext)messageContext.getSubcontext(SAMLProtocolContext.class);
         Constraint.isNotNull(protocolContext, "SAMLProtocolContext was null");
         Constraint.isNotNull(protocolContext.getProtocol(), "SAML protocol was null");
         criteriaSet.add(new ProtocolCriterion(protocolContext.getProtocol()));
         return criteriaSet;
      } catch (ConstraintViolationException var6) {
         throw new MessageHandlerException(var6);
      }
   }

   @Nullable
   protected String getCertificatePresenterEntityID(@Nonnull MessageContext messageContext) {
      AbstractAuthenticatableSAMLEntityContext entityContext = (AbstractAuthenticatableSAMLEntityContext)messageContext.getSubcontext(this.entityContextClass);
      if (entityContext != null) {
         this.log.trace("Found authenticatable entityID '{}' from context: {}", entityContext.getEntityId(), entityContext.getClass().getName());
         return entityContext.getEntityId();
      } else {
         this.log.trace("Authenticatable entityID context was not present: {}", entityContext.getClass().getName());
         return null;
      }
   }

   protected void setAuthenticatedCertificatePresenterEntityID(@Nonnull MessageContext messageContext, @Nullable String entityID) {
      this.log.trace("Storing authenticatable entityID '{}' in context: {}", entityID, this.entityContextClass);
      ((AbstractAuthenticatableSAMLEntityContext)messageContext.getSubcontext(this.entityContextClass, true)).setEntityId(entityID);
   }

   protected void setAuthenticatedState(@Nonnull MessageContext messageContext, boolean authenticated) {
      this.log.trace("Storing authenticated entity state '{}' in context: {}", authenticated, this.entityContextClass);
      ((AbstractAuthenticatableSAMLEntityContext)messageContext.getSubcontext(this.entityContextClass, true)).setAuthenticated(authenticated);
   }
}
