package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Request;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SubjectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLSubjectNameIdentifierContext extends BaseContext {
   @Nullable
   private Logger log = LoggerFactory.getLogger(SAMLSubjectNameIdentifierContext.class);
   @Nullable
   private SAMLObject nameID;

   @Nullable
   public SAMLObject getSubjectNameIdentifier() {
      if (this.nameID == null) {
         this.nameID = this.resolveNameIdentifier();
      }

      return this.nameID;
   }

   @Nullable
   public NameIdentifier getSAML1SubjectNameIdentifier() {
      SAMLObject samlObject = this.getSubjectNameIdentifier();
      return samlObject instanceof NameIdentifier ? (NameIdentifier)samlObject : null;
   }

   @Nullable
   public NameID getSAML2SubjectNameID() {
      SAMLObject samlObject = this.getSubjectNameIdentifier();
      return samlObject instanceof NameID ? (NameID)samlObject : null;
   }

   public void setSubjectNameIdentifier(@Nullable SAMLObject newNameID) {
      if (newNameID != null && !(newNameID instanceof NameIdentifier) && !(newNameID instanceof NameID)) {
         throw new IllegalArgumentException("Argument was not a valid SAML 1 or 2 name identifier type or null: " + newNameID.getClass().getName());
      } else {
         this.nameID = newNameID;
      }
   }

   @Nullable
   protected SAMLObject resolveNameIdentifier() {
      SAMLObject samlMessage = this.resolveSAMLMessage();
      if (samlMessage == null) {
         this.log.debug("SAML message could not be dynamically resolved from parent context");
         return null;
      } else if (samlMessage instanceof SubjectQuery) {
         SubjectQuery query = (SubjectQuery)samlMessage;
         return query.getSubject() != null ? query.getSubject().getNameID() : null;
      } else if (samlMessage instanceof AuthnRequest) {
         AuthnRequest request = (AuthnRequest)samlMessage;
         return request.getSubject() != null ? request.getSubject().getNameID() : null;
      } else if (samlMessage instanceof Request && ((Request)samlMessage).getSubjectQuery() != null) {
         org.opensaml.saml.saml1.core.SubjectQuery query = ((Request)samlMessage).getSubjectQuery();
         return query.getSubject() != null ? query.getSubject().getNameIdentifier() : null;
      } else {
         if (samlMessage instanceof LogoutRequest) {
            this.log.debug("Ignoring LogoutRequest, Subject does not require processing");
         } else {
            this.log.debug("Message in resolved parent message context was not a supported instance of SAMLObject: {}", samlMessage.getClass().getName());
         }

         return null;
      }
   }

   @Nullable
   protected SAMLObject resolveSAMLMessage() {
      if (this.getParent() instanceof MessageContext) {
         MessageContext parent = (MessageContext)this.getParent();
         if (parent.getMessage() instanceof SAMLObject) {
            return (SAMLObject)parent.getMessage();
         }
      }

      return null;
   }
}
