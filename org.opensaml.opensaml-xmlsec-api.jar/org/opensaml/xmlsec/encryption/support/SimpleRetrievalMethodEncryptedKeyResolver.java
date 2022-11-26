package org.opensaml.xmlsec.encryption.support;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRetrievalMethodEncryptedKeyResolver extends AbstractEncryptedKeyResolver {
   private final Logger log;

   public SimpleRetrievalMethodEncryptedKeyResolver() {
      this.log = LoggerFactory.getLogger(SimpleRetrievalMethodEncryptedKeyResolver.class);
   }

   public SimpleRetrievalMethodEncryptedKeyResolver(@Nullable Set recipients) {
      super(recipients);
      this.log = LoggerFactory.getLogger(SimpleRetrievalMethodEncryptedKeyResolver.class);
   }

   public SimpleRetrievalMethodEncryptedKeyResolver(@Nullable String recipient) {
      this(Collections.singleton(recipient));
   }

   @Nonnull
   public Iterable resolve(@Nonnull EncryptedData encryptedData) {
      Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
      List resolvedEncKeys = new ArrayList();
      if (encryptedData.getKeyInfo() == null) {
         return resolvedEncKeys;
      } else {
         Iterator var3 = encryptedData.getKeyInfo().getRetrievalMethods().iterator();

         while(var3.hasNext()) {
            RetrievalMethod rm = (RetrievalMethod)var3.next();
            if (Objects.equals(rm.getType(), "http://www.w3.org/2001/04/xmlenc#EncryptedKey")) {
               if (rm.getTransforms() != null) {
                  this.log.warn("EncryptedKey RetrievalMethod has transforms, cannot process");
               } else {
                  EncryptedKey encKey = this.dereferenceURI(rm);
                  if (encKey != null && this.matchRecipient(encKey.getRecipient())) {
                     resolvedEncKeys.add(encKey);
                  }
               }
            }
         }

         return resolvedEncKeys;
      }
   }

   @Nullable
   protected EncryptedKey dereferenceURI(@Nonnull RetrievalMethod rm) {
      String uri = rm.getURI();
      if (!Strings.isNullOrEmpty(uri) && uri.startsWith("#")) {
         XMLObject target = rm.resolveIDFromRoot(uri.substring(1));
         if (target == null) {
            this.log.warn("EncryptedKey RetrievalMethod URI could not be dereferenced");
            return null;
         } else if (!(target instanceof EncryptedKey)) {
            this.log.warn("The product of dereferencing the EncryptedKey RetrievalMethod was not an EncryptedKey");
            return null;
         } else {
            return (EncryptedKey)target;
         }
      } else {
         this.log.warn("EncryptedKey RetrievalMethod did not contain a same-document URI reference, cannot process");
         return null;
      }
   }
}
