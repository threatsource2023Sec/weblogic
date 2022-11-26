package org.opensaml.saml.saml2.encryption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.support.AbstractEncryptedKeyResolver;

public class EncryptedElementTypeEncryptedKeyResolver extends AbstractEncryptedKeyResolver {
   public EncryptedElementTypeEncryptedKeyResolver() {
   }

   public EncryptedElementTypeEncryptedKeyResolver(@Nullable Set recipients) {
      super(recipients);
   }

   public EncryptedElementTypeEncryptedKeyResolver(@Nullable String recipient) {
      this(Collections.singleton(recipient));
   }

   public Iterable resolve(EncryptedData encryptedData) {
      List resolvedEncKeys = new ArrayList();
      if (!(encryptedData.getParent() instanceof EncryptedElementType)) {
         return resolvedEncKeys;
      } else {
         EncryptedElementType encElementType = (EncryptedElementType)encryptedData.getParent();
         Iterator var4 = encElementType.getEncryptedKeys().iterator();

         while(var4.hasNext()) {
            EncryptedKey encKey = (EncryptedKey)var4.next();
            if (this.matchRecipient(encKey.getRecipient())) {
               resolvedEncKeys.add(encKey);
            }
         }

         return resolvedEncKeys;
      }
   }
}
