package org.opensaml.saml.saml2.profile;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.saml.saml2.core.NameID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAML2ObjectSupport {
   private SAML2ObjectSupport() {
   }

   public static boolean areNameIDFormatsEquivalent(@Nullable String format1, @Nullable String format2) {
      return Objects.equals(format1 != null ? format1 : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified", format2 != null ? format2 : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
   }

   public static boolean areNameIDsEquivalent(@Nonnull NameID name1, @Nonnull NameID name2) {
      return areNameIDFormatsEquivalent(name1.getFormat(), name2.getFormat()) && Objects.equals(name1.getValue(), name2.getValue()) && Objects.equals(name1.getNameQualifier(), name2.getNameQualifier()) && Objects.equals(name1.getSPNameQualifier(), name2.getSPNameQualifier());
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(SAML2ObjectSupport.class);
   }
}
