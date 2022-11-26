package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml.saml2.core.NameIDMappingResponse;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectQuery;
import org.opensaml.saml.saml2.profile.context.EncryptionContext;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EncryptNameIDs extends AbstractEncryptAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(EncryptNameIDs.class);
   @Nonnull
   private Function messageLookupStrategy = Functions.compose(new MessageLookup(SAMLObject.class), new OutboundMessageContextLookup());
   @Nonnull
   @NonnullElements
   private Set excludedFormats = Collections.singleton("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
   @Nullable
   private SAMLObject message;

   public void setMessageLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.messageLookupStrategy = (Function)Constraint.isNotNull(strategy, "Message lookup strategy cannot be null");
   }

   public void setExcludedFormats(@Nonnull @NonnullElements Collection formats) {
      this.excludedFormats = new HashSet(StringSupport.normalizeStringCollection(formats));
   }

   @Nullable
   protected EncryptionParameters getApplicableParameters(@Nullable EncryptionContext ctx) {
      return ctx != null ? ctx.getIdentifierEncryptionParameters() : null;
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.message = (SAMLObject)this.messageLookupStrategy.apply(profileRequestContext);
      if (this.message != null && this.message instanceof ArtifactResponse) {
         this.message = ((ArtifactResponse)this.message).getMessage();
      }

      if (this.message == null) {
         this.log.debug("{} Message was not present, nothing to do", this.getLogPrefix());
         return false;
      } else {
         return super.doPreExecute(profileRequestContext);
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      try {
         if (this.message instanceof AuthnRequest) {
            this.processSubject(((AuthnRequest)this.message).getSubject());
         } else if (this.message instanceof SubjectQuery) {
            this.processSubject(((SubjectQuery)this.message).getSubject());
         } else if (this.message instanceof Response) {
            Iterator var2 = ((Response)this.message).getAssertions().iterator();

            while(var2.hasNext()) {
               Assertion a = (Assertion)var2.next();
               this.processAssertion(a);
            }
         } else if (this.message instanceof LogoutRequest) {
            this.processLogoutRequest((LogoutRequest)this.message);
         } else if (this.message instanceof ManageNameIDRequest) {
            this.processManageNameIDRequest((ManageNameIDRequest)this.message);
         } else if (this.message instanceof NameIDMappingRequest) {
            this.processNameIDMappingRequest((NameIDMappingRequest)this.message);
         } else if (this.message instanceof NameIDMappingResponse) {
            this.processNameIDMappingResponse((NameIDMappingResponse)this.message);
         } else {
            if (!(this.message instanceof Assertion)) {
               this.log.debug("{} Message was of unrecognized type {}, nothing to do", this.getLogPrefix(), this.message.getClass().getName());
               return;
            }

            this.processAssertion((Assertion)this.message);
         }
      } catch (EncryptionException var4) {
         this.log.warn("{} Error encrypting NameID", this.getLogPrefix(), var4);
         ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
      }

   }

   private boolean shouldEncrypt(@Nullable NameID name) {
      if (name != null) {
         String format = name.getFormat();
         if (format == null) {
            format = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
         }

         if (!this.excludedFormats.contains(format)) {
            if (this.log.isDebugEnabled()) {
               try {
                  Element dom = XMLObjectSupport.marshall(name);
                  this.log.debug("{} NameID before encryption:\n{}", this.getLogPrefix(), SerializeSupport.prettyPrintXML(dom));
               } catch (MarshallingException var4) {
                  this.log.error("{} Unable to marshall NameID for logging purposes", this.getLogPrefix(), var4);
               }
            }

            return true;
         }
      }

      return false;
   }

   private void processSubject(@Nullable Subject subject) throws EncryptionException {
      if (subject != null) {
         if (this.shouldEncrypt(subject.getNameID())) {
            this.log.debug("{} Encrypt NameID in Subject", this.getLogPrefix());
            EncryptedID encrypted = this.getEncrypter().encrypt(subject.getNameID());
            subject.setEncryptedID(encrypted);
            subject.setNameID((NameID)null);
         }

         Iterator var5 = subject.getSubjectConfirmations().iterator();

         while(var5.hasNext()) {
            SubjectConfirmation sc = (SubjectConfirmation)var5.next();
            if (this.shouldEncrypt(sc.getNameID())) {
               this.log.debug("{} Encrypt NameID in SubjectConfirmation", this.getLogPrefix());
               EncryptedID encrypted = this.getEncrypter().encrypt(sc.getNameID());
               sc.setEncryptedID(encrypted);
               sc.setNameID((NameID)null);
            }
         }
      }

   }

   private void processLogoutRequest(@Nonnull LogoutRequest request) throws EncryptionException {
      if (this.shouldEncrypt(request.getNameID())) {
         this.log.debug("{} Encrypting NameID in LogoutRequest", this.getLogPrefix());
         EncryptedID encrypted = this.getEncrypter().encrypt(request.getNameID());
         request.setEncryptedID(encrypted);
         request.setNameID((NameID)null);
      }

   }

   private void processManageNameIDRequest(@Nonnull ManageNameIDRequest request) throws EncryptionException {
      if (this.shouldEncrypt(request.getNameID())) {
         this.log.debug("{} Encrypting NameID in ManageNameIDRequest", this.getLogPrefix());
         EncryptedID encrypted = this.getEncrypter().encrypt(request.getNameID());
         request.setEncryptedID(encrypted);
         request.setNameID((NameID)null);
      }

      if (request.getNewID() != null) {
         this.log.debug("{} Encrypting NewID in ManageNameIDRequest", this.getLogPrefix());
         NewEncryptedID encrypted = this.getEncrypter().encrypt(request.getNewID());
         request.setNewEncryptedID(encrypted);
         request.setNewID((NewID)null);
      }

   }

   private void processNameIDMappingRequest(@Nonnull NameIDMappingRequest request) throws EncryptionException {
      if (this.shouldEncrypt(request.getNameID())) {
         this.log.debug("{} Encrypting NameID in NameIDMappingRequest", this.getLogPrefix());
         EncryptedID encrypted = this.getEncrypter().encrypt(request.getNameID());
         request.setEncryptedID(encrypted);
         request.setNameID((NameID)null);
      }

   }

   private void processNameIDMappingResponse(@Nonnull NameIDMappingResponse response) throws EncryptionException {
      if (this.shouldEncrypt(response.getNameID())) {
         this.log.debug("{} Encrypting NameID in NameIDMappingResponse", this.getLogPrefix());
         EncryptedID encrypted = this.getEncrypter().encrypt(response.getNameID());
         response.setEncryptedID(encrypted);
         response.setNameID((NameID)null);
      }

   }

   private void processAssertion(@Nonnull Assertion assertion) throws EncryptionException {
      this.processSubject(assertion.getSubject());
      if (assertion.getConditions() != null) {
         Iterator var2 = assertion.getConditions().getConditions().iterator();

         while(true) {
            Condition c;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               c = (Condition)var2.next();
            } while(!(c instanceof DelegationRestrictionType));

            Iterator var4 = ((DelegationRestrictionType)c).getDelegates().iterator();

            while(var4.hasNext()) {
               Delegate d = (Delegate)var4.next();
               if (this.shouldEncrypt(d.getNameID())) {
                  this.log.debug("{} Encrypting NameID in Delegate", this.getLogPrefix());
                  EncryptedID encrypted = this.getEncrypter().encrypt(d.getNameID());
                  d.setEncryptedID(encrypted);
                  d.setNameID((NameID)null);
               }
            }
         }
      }
   }
}
