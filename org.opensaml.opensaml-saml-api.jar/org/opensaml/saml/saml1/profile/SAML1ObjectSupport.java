package org.opensaml.saml.saml1.profile;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAML1ObjectSupport {
   private SAML1ObjectSupport() {
   }

   public static boolean areNameIdentifierFormatsEquivalent(@Nullable String format1, @Nullable String format2) {
      return Objects.equals(format1 != null ? format1 : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified", format2 != null ? format2 : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
   }

   public static boolean areNameIdentifiersEquivalent(@Nonnull NameIdentifier name1, @Nonnull NameIdentifier name2) {
      return areNameIdentifierFormatsEquivalent(name1.getFormat(), name2.getFormat()) && Objects.equals(name1.getValue(), name2.getValue()) && Objects.equals(name1.getNameQualifier(), name2.getNameQualifier());
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(SAML1ObjectSupport.class);
   }
}
