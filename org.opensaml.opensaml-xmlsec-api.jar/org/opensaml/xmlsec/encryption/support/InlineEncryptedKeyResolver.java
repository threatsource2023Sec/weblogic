package org.opensaml.xmlsec.encryption.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;

public class InlineEncryptedKeyResolver extends AbstractEncryptedKeyResolver {
   public InlineEncryptedKeyResolver() {
   }

   public InlineEncryptedKeyResolver(@Nullable Set recipients) {
      super(recipients);
   }

   public InlineEncryptedKeyResolver(@Nullable String recipient) {
      this(Collections.singleton(recipient));
   }

   @Nonnull
   public Iterable resolve(@Nonnull EncryptedData encryptedData) {
      Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
      List resolvedEncKeys = new ArrayList();
      if (encryptedData.getKeyInfo() == null) {
         return resolvedEncKeys;
      } else {
         Iterator var3 = encryptedData.getKeyInfo().getEncryptedKeys().iterator();

         while(var3.hasNext()) {
            EncryptedKey encKey = (EncryptedKey)var3.next();
            if (this.matchRecipient(encKey.getRecipient())) {
               resolvedEncKeys.add(encKey);
            }
         }

         return resolvedEncKeys;
      }
   }
}
