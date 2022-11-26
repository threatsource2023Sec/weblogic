package org.opensaml.xmlsec.encryption.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleKeyInfoReferenceEncryptedKeyResolver extends AbstractEncryptedKeyResolver {
   @Nonnull
   private final Logger log;
   private int depthLimit;

   public SimpleKeyInfoReferenceEncryptedKeyResolver() {
      this((Set)null);
   }

   public SimpleKeyInfoReferenceEncryptedKeyResolver(@Nullable Set recipients) {
      super(recipients);
      this.log = LoggerFactory.getLogger(SimpleKeyInfoReferenceEncryptedKeyResolver.class);
      this.depthLimit = 5;
   }

   public SimpleKeyInfoReferenceEncryptedKeyResolver(@Nullable String recipient) {
      this(Collections.singleton(recipient));
   }

   public int getDepthLimit() {
      return this.depthLimit;
   }

   public void setDepthLimit(int limit) {
      this.depthLimit = Math.max(1, limit);
   }

   @Nonnull
   public Iterable resolve(@Nonnull EncryptedData encryptedData) {
      Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
      return this.resolveKeyInfo(encryptedData.getKeyInfo(), this.depthLimit);
   }

   @Nonnull
   protected Iterable resolveKeyInfo(@Nullable KeyInfo keyInfo, int limit) {
      List resolvedEncKeys = new ArrayList();
      if (keyInfo == null) {
         return resolvedEncKeys;
      } else {
         Iterator var4;
         if (limit < this.depthLimit) {
            var4 = keyInfo.getEncryptedKeys().iterator();

            while(var4.hasNext()) {
               EncryptedKey encKey = (EncryptedKey)var4.next();
               if (this.matchRecipient(encKey.getRecipient())) {
                  resolvedEncKeys.add(encKey);
               }
            }
         }

         if (limit == 0) {
            this.log.info("Reached depth limit for KeyInfoReferences");
         } else {
            var4 = keyInfo.getKeyInfoReferences().iterator();

            while(var4.hasNext()) {
               KeyInfoReference ref = (KeyInfoReference)var4.next();
               Iterator var6 = this.resolveKeyInfo(this.dereferenceURI(ref), limit - 1).iterator();

               while(var6.hasNext()) {
                  EncryptedKey encKey = (EncryptedKey)var6.next();
                  resolvedEncKeys.add(encKey);
               }
            }
         }

         return resolvedEncKeys;
      }
   }

   @Nullable
   protected KeyInfo dereferenceURI(@Nonnull KeyInfoReference ref) {
      String uri = ref.getURI();
      if (uri != null && !uri.isEmpty() && uri.startsWith("#")) {
         XMLObject target = ref.resolveIDFromRoot(uri.substring(1));
         if (target == null) {
            this.log.warn("EncryptedKey KeyInfoReference URI could not be dereferenced");
            return null;
         } else if (!(target instanceof KeyInfo)) {
            this.log.warn("The product of dereferencing the EncryptedKey KeyInfoReference was not a KeyInfo");
            return null;
         } else {
            return (KeyInfo)target;
         }
      } else {
         this.log.warn("EncryptedKey KeyInfoReference did not contain a same-document URI reference, cannot process");
         return null;
      }
   }
}
