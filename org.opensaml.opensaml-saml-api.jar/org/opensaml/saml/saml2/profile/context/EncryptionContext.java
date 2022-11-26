package org.opensaml.saml.saml2.profile.context;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.xmlsec.EncryptionParameters;

public class EncryptionContext extends BaseContext {
   @Nullable
   private EncryptionParameters assertionEncParams;
   @Nullable
   private EncryptionParameters idEncParams;
   @Nullable
   private EncryptionParameters attributeEncParams;

   @Nullable
   public EncryptionParameters getAssertionEncryptionParameters() {
      return this.assertionEncParams;
   }

   @Nonnull
   public EncryptionContext setAssertionEncryptionParameters(@Nullable EncryptionParameters params) {
      this.assertionEncParams = params;
      return this;
   }

   @Nullable
   public EncryptionParameters getIdentifierEncryptionParameters() {
      return this.idEncParams;
   }

   @Nonnull
   public EncryptionContext setIdentifierEncryptionParameters(@Nullable EncryptionParameters params) {
      this.idEncParams = params;
      return this;
   }

   @Nullable
   public EncryptionParameters getAttributeEncryptionParameters() {
      return this.attributeEncParams;
   }

   @Nonnull
   public EncryptionContext setAttributeEncryptionParameters(@Nullable EncryptionParameters params) {
      this.attributeEncParams = params;
      return this;
   }
}
