package org.opensaml.saml.saml2.profile.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.Pair;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecryptAssertions extends AbstractDecryptAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(DecryptAssertions.class);

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      SAMLObject message = this.getSAMLObject();

      try {
         if (!(message instanceof Response)) {
            this.log.debug("{} Message was of unrecognized type {}, nothing to do", this.getLogPrefix(), message.getClass().getName());
            return;
         }

         this.processResponse(profileRequestContext, (Response)message);
      } catch (DecryptionException var4) {
         this.log.warn("{} Failure performing decryption", this.getLogPrefix(), var4);
         if (this.isErrorFatal()) {
            ActionSupport.buildEvent(profileRequestContext, "DecryptAssertionFailed");
         }
      }

   }

   @Nullable
   private Assertion processEncryptedAssertion(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull EncryptedAssertion encAssert) throws DecryptionException {
      if (!this.getDecryptionPredicate().apply(new Pair(profileRequestContext, encAssert))) {
         return null;
      } else if (this.getDecrypter() == null) {
         throw new DecryptionException("No decryption parameters, unable to decrypt EncryptedAssertion");
      } else {
         return this.getDecrypter().decrypt(encAssert);
      }
   }

   private void processResponse(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Response response) throws DecryptionException {
      Collection decrypteds = new ArrayList();
      Iterator i = response.getEncryptedAssertions().iterator();

      while(i.hasNext()) {
         this.log.debug("{} Decrypting EncryptedAssertion in Response", this.getLogPrefix());

         try {
            Assertion decrypted = this.processEncryptedAssertion(profileRequestContext, (EncryptedAssertion)i.next());
            if (decrypted != null) {
               decrypteds.add(decrypted);
               i.remove();
            }
         } catch (DecryptionException var6) {
            if (this.isErrorFatal()) {
               throw var6;
            }

            this.log.warn("{} Trapped failure decrypting EncryptedAttribute in AttributeStatement", this.getLogPrefix(), var6);
         }
      }

      response.getAssertions().addAll(decrypteds);
   }
}
