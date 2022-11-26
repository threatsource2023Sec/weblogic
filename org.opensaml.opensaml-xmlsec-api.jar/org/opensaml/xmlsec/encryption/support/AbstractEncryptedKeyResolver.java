package org.opensaml.xmlsec.encryption.support;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;

public abstract class AbstractEncryptedKeyResolver implements EncryptedKeyResolver {
   private final Collection recipients;

   public AbstractEncryptedKeyResolver() {
      this.recipients = Collections.emptySet();
   }

   public AbstractEncryptedKeyResolver(@Nullable Set newRecipents) {
      this.recipients = new HashSet(StringSupport.normalizeStringCollection(newRecipents));
   }

   public AbstractEncryptedKeyResolver(@Nullable String recipient) {
      String trimmed = StringSupport.trimOrNull(recipient);
      if (trimmed != null) {
         this.recipients = Collections.singleton(trimmed);
      } else {
         this.recipients = Collections.emptySet();
      }

   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set getRecipients() {
      return ImmutableSet.copyOf(this.recipients);
   }

   protected boolean matchRecipient(@Nullable String recipient) {
      String trimmedRecipient = StringSupport.trimOrNull(recipient);
      return trimmedRecipient != null && !this.recipients.isEmpty() ? this.recipients.contains(trimmedRecipient) : true;
   }

   protected boolean matchCarriedKeyName(@Nonnull EncryptedData encryptedData, @Nonnull EncryptedKey encryptedKey) {
      Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
      Constraint.isNotNull(encryptedKey, "EncryptedKey cannot be null");
      if (encryptedKey.getCarriedKeyName() != null && !Strings.isNullOrEmpty(encryptedKey.getCarriedKeyName().getValue())) {
         if (encryptedData.getKeyInfo() != null && !encryptedData.getKeyInfo().getKeyNames().isEmpty()) {
            String keyCarriedKeyName = encryptedKey.getCarriedKeyName().getValue();
            List dataKeyNames = KeyInfoSupport.getKeyNames(encryptedData.getKeyInfo());
            return dataKeyNames.contains(keyCarriedKeyName);
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   protected boolean matchDataReference(@Nonnull EncryptedData encryptedData, @Nonnull EncryptedKey encryptedKey) {
      Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
      Constraint.isNotNull(encryptedKey, "EncryptedKey cannot be null");
      if (encryptedKey.getReferenceList() != null && !encryptedKey.getReferenceList().getDataReferences().isEmpty()) {
         if (Strings.isNullOrEmpty(encryptedData.getID())) {
            return false;
         } else {
            List drlist = encryptedKey.getReferenceList().getDataReferences();
            Iterator var4 = drlist.iterator();

            DataReference dr;
            do {
               if (!var4.hasNext()) {
                  return false;
               }

               dr = (DataReference)var4.next();
            } while(Strings.isNullOrEmpty(dr.getURI()) || !dr.getURI().startsWith("#") || dr.resolveIDFromRoot(dr.getURI().substring(1)) != encryptedData);

            return true;
         }
      } else {
         return true;
      }
   }
}
