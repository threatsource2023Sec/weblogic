package org.opensaml.saml.saml2.profile.impl;

import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.Pair;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;
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
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecryptNameIDs extends AbstractDecryptAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(DecryptNameIDs.class);

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      SAMLObject message = this.getSAMLObject();

      try {
         if (message instanceof AuthnRequest) {
            this.processSubject(profileRequestContext, ((AuthnRequest)message).getSubject());
         } else if (message instanceof SubjectQuery) {
            this.processSubject(profileRequestContext, ((SubjectQuery)message).getSubject());
         } else if (message instanceof Response) {
            Iterator var3 = ((Response)message).getAssertions().iterator();

            while(var3.hasNext()) {
               Assertion a = (Assertion)var3.next();
               this.processAssertion(profileRequestContext, a);
            }
         } else if (message instanceof LogoutRequest) {
            this.processLogoutRequest(profileRequestContext, (LogoutRequest)message);
         } else if (message instanceof ManageNameIDRequest) {
            this.processManageNameIDRequest(profileRequestContext, (ManageNameIDRequest)message);
         } else if (message instanceof NameIDMappingRequest) {
            this.processNameIDMappingRequest(profileRequestContext, (NameIDMappingRequest)message);
         } else if (message instanceof NameIDMappingResponse) {
            this.processNameIDMappingResponse(profileRequestContext, (NameIDMappingResponse)message);
         } else {
            if (!(message instanceof Assertion)) {
               this.log.debug("{} Message was of unrecognized type {}, nothing to do", this.getLogPrefix(), message.getClass().getName());
               return;
            }

            this.processAssertion(profileRequestContext, (Assertion)message);
         }
      } catch (DecryptionException var5) {
         this.log.warn("{} Failure performing decryption", this.getLogPrefix(), var5);
         if (this.isErrorFatal()) {
            ActionSupport.buildEvent(profileRequestContext, "DecryptNameIDFailed");
         }
      }

   }

   @Nullable
   private NameID processEncryptedID(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull EncryptedID encID) throws DecryptionException {
      if (!this.getDecryptionPredicate().apply(new Pair(profileRequestContext, encID))) {
         return null;
      } else if (this.getDecrypter() == null) {
         throw new DecryptionException("No decryption parameters, unable to decrypt EncryptedID");
      } else {
         SAMLObject object = this.getDecrypter().decrypt(encID);
         if (object instanceof NameID) {
            return (NameID)object;
         } else {
            throw new DecryptionException("Decrypted EncryptedID was not a NameID, was a " + object.getElementQName().toString());
         }
      }
   }

   @Nullable
   private NewID processNewEncryptedID(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull NewEncryptedID encID) throws DecryptionException {
      if (!this.getDecryptionPredicate().apply(new Pair(profileRequestContext, encID))) {
         return null;
      } else if (this.getDecrypter() == null) {
         throw new DecryptionException("No decryption parameters, unable to decrypt NewEncryptedID");
      } else {
         return this.getDecrypter().decrypt(encID);
      }
   }

   private void processSubject(@Nonnull ProfileRequestContext profileRequestContext, @Nullable Subject subject) throws DecryptionException {
      if (subject != null) {
         if (subject.getEncryptedID() != null) {
            this.log.debug("{} Decrypting EncryptedID in Subject", this.getLogPrefix());

            try {
               NameID decrypted = this.processEncryptedID(profileRequestContext, subject.getEncryptedID());
               if (decrypted != null) {
                  subject.setNameID(decrypted);
                  subject.setEncryptedID((EncryptedID)null);
               }
            } catch (DecryptionException var7) {
               if (this.isErrorFatal()) {
                  throw var7;
               }

               this.log.warn("{} Trapped failure decrypting EncryptedID in Subject", this.getLogPrefix(), var7);
            }
         }

         Iterator var8 = subject.getSubjectConfirmations().iterator();

         while(true) {
            SubjectConfirmation sc;
            do {
               if (!var8.hasNext()) {
                  return;
               }

               sc = (SubjectConfirmation)var8.next();
            } while(sc.getEncryptedID() == null);

            this.log.debug("{} Decrypting EncryptedID in SubjectConfirmation", this.getLogPrefix());

            try {
               NameID decrypted = this.processEncryptedID(profileRequestContext, subject.getEncryptedID());
               if (decrypted != null) {
                  sc.setNameID(decrypted);
                  sc.setEncryptedID((EncryptedID)null);
               }
            } catch (DecryptionException var6) {
               if (this.isErrorFatal()) {
                  throw var6;
               }

               this.log.warn("{} Trapped failure decrypting EncryptedID in SubjectConfirmation", this.getLogPrefix(), var6);
            }
         }
      }
   }

   private void processLogoutRequest(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull LogoutRequest request) throws DecryptionException {
      if (request.getEncryptedID() != null) {
         this.log.debug("{} Decrypting EncryptedID in LogoutRequest", this.getLogPrefix());
         NameID decrypted = this.processEncryptedID(profileRequestContext, request.getEncryptedID());
         if (decrypted != null) {
            request.setNameID(decrypted);
            request.setEncryptedID((EncryptedID)null);
         }
      }

   }

   private void processManageNameIDRequest(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull ManageNameIDRequest request) throws DecryptionException {
      if (request.getEncryptedID() != null) {
         this.log.debug("{} Decrypting EncryptedID in ManageNameIDRequest", this.getLogPrefix());
         NameID decrypted = this.processEncryptedID(profileRequestContext, request.getEncryptedID());
         if (decrypted != null) {
            request.setNameID(decrypted);
            request.setEncryptedID((EncryptedID)null);
         }
      }

      if (request.getNewEncryptedID() != null) {
         this.log.debug("{} Decrypting NewEncryptedID in ManageNameIDRequest", this.getLogPrefix());
         NewID decrypted = this.processNewEncryptedID(profileRequestContext, request.getNewEncryptedID());
         if (decrypted != null) {
            request.setNewID(decrypted);
            request.setNewEncryptedID((NewEncryptedID)null);
         }
      }

   }

   private void processNameIDMappingRequest(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull NameIDMappingRequest request) throws DecryptionException {
      if (request.getEncryptedID() != null) {
         this.log.debug("{} Decrypting EncryptedID in NameIDMappingRequest", this.getLogPrefix());
         NameID decrypted = this.processEncryptedID(profileRequestContext, request.getEncryptedID());
         if (decrypted != null) {
            request.setNameID(decrypted);
            request.setEncryptedID((EncryptedID)null);
         }
      }

   }

   private void processNameIDMappingResponse(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull NameIDMappingResponse response) throws DecryptionException {
      if (response.getEncryptedID() != null) {
         this.log.debug("{} Decrypting EncryptedID in NameIDMappingRequest", this.getLogPrefix());
         NameID decrypted = this.processEncryptedID(profileRequestContext, response.getEncryptedID());
         if (decrypted != null) {
            response.setNameID(decrypted);
            response.setEncryptedID((EncryptedID)null);
         }
      }

   }

   private void processAssertion(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Assertion assertion) throws DecryptionException {
      try {
         this.processSubject(profileRequestContext, assertion.getSubject());
      } catch (DecryptionException var9) {
         if (this.isErrorFatal()) {
            throw var9;
         }

         this.log.warn("{} Trapped failure decrypting EncryptedIDs in Subject", this.getLogPrefix(), var9);
      }

      if (assertion.getConditions() != null) {
         Iterator var3 = assertion.getConditions().getConditions().iterator();

         label55:
         while(true) {
            Condition c;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               c = (Condition)var3.next();
            } while(!(c instanceof DelegationRestrictionType));

            Iterator var5 = ((DelegationRestrictionType)c).getDelegates().iterator();

            while(true) {
               Delegate d;
               do {
                  if (!var5.hasNext()) {
                     continue label55;
                  }

                  d = (Delegate)var5.next();
               } while(d.getEncryptedID() == null);

               this.log.debug("{} Decrypting EncryptedID in Delegate", this.getLogPrefix());

               try {
                  NameID decrypted = this.processEncryptedID(profileRequestContext, d.getEncryptedID());
                  if (decrypted != null) {
                     d.setNameID(decrypted);
                     d.setEncryptedID((EncryptedID)null);
                  }
               } catch (DecryptionException var8) {
                  if (this.isErrorFatal()) {
                     throw var8;
                  }

                  this.log.warn("{} Trapped failure decrypting EncryptedID in Delegate", this.getLogPrefix(), var8);
               }
            }
         }
      }
   }
}
