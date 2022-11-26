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
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.EncryptedAttribute;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecryptAttributes extends AbstractDecryptAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(DecryptAttributes.class);

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      SAMLObject message = this.getSAMLObject();

      try {
         if (message instanceof Response) {
            Iterator var3 = ((Response)message).getAssertions().iterator();

            while(var3.hasNext()) {
               Assertion a = (Assertion)var3.next();
               this.processAssertion(profileRequestContext, a);
            }
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
            ActionSupport.buildEvent(profileRequestContext, "DecryptAttributeFailed");
         }
      }

   }

   @Nullable
   private Attribute processEncryptedAttribute(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull EncryptedAttribute encAttr) throws DecryptionException {
      if (!this.getDecryptionPredicate().apply(new Pair(profileRequestContext, encAttr))) {
         return null;
      } else if (this.getDecrypter() == null) {
         throw new DecryptionException("No decryption parameters, unable to decrypt EncryptedAttribute");
      } else {
         return this.getDecrypter().decrypt(encAttr);
      }
   }

   private void processAssertion(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Assertion assertion) throws DecryptionException {
      Collection decrypteds = new ArrayList();
      Iterator var4 = assertion.getAttributeStatements().iterator();

      while(var4.hasNext()) {
         AttributeStatement s = (AttributeStatement)var4.next();
         Iterator i = s.getEncryptedAttributes().iterator();

         while(i.hasNext()) {
            this.log.debug("{} Decrypting EncryptedAttribute in AttributeStatement", this.getLogPrefix());

            try {
               Attribute decrypted = this.processEncryptedAttribute(profileRequestContext, (EncryptedAttribute)i.next());
               if (decrypted != null) {
                  decrypteds.add(decrypted);
                  i.remove();
               }
            } catch (DecryptionException var8) {
               if (this.isErrorFatal()) {
                  throw var8;
               }

               this.log.warn("{} Trapped failure decrypting EncryptedAttribute in AttributeStatement", this.getLogPrefix(), var8);
            }
         }

         s.getAttributes().addAll(decrypteds);
      }

   }
}
