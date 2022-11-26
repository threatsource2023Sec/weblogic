package org.opensaml.xmlsec.encryption.support;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.EncryptionParameters;

public class KeyEncryptionParameters extends DataEncryptionParameters {
   private String recipient;
   private RSAOAEPParameters rsaOAEPParameters;

   public KeyEncryptionParameters() {
      this.setAlgorithm((String)null);
   }

   public KeyEncryptionParameters(EncryptionParameters params, String recipientId) {
      this();
      Constraint.isNotNull(params, "EncryptionParameters instance was null");
      this.setEncryptionCredential(params.getKeyTransportEncryptionCredential());
      this.setAlgorithm(params.getKeyTransportEncryptionAlgorithm());
      this.setKeyInfoGenerator(params.getKeyTransportKeyInfoGenerator());
      this.setRecipient(recipientId);
      this.setRSAOAEPParameters(params.getRSAOAEPParameters());
   }

   @Nullable
   public String getRecipient() {
      return this.recipient;
   }

   public void setRecipient(@Nullable String newRecipient) {
      this.recipient = newRecipient;
   }

   @Nullable
   public RSAOAEPParameters getRSAOAEPParameters() {
      return this.rsaOAEPParameters;
   }

   public void setRSAOAEPParameters(@Nullable RSAOAEPParameters params) {
      this.rsaOAEPParameters = params;
   }
}
