package org.cryptacular.bean;

import java.security.PrivateKey;
import org.cryptacular.EncodingException;
import org.cryptacular.util.ByteUtil;
import org.cryptacular.util.KeyPairUtil;
import org.cryptacular.util.PemUtil;

public class PemBasedPrivateKeyFactoryBean implements FactoryBean {
   private String encodedKey;

   public PemBasedPrivateKeyFactoryBean() {
   }

   public PemBasedPrivateKeyFactoryBean(String pemEncodedKey) {
      this.setEncodedKey(pemEncodedKey);
   }

   public String getEncodedKey() {
      return this.encodedKey;
   }

   public void setEncodedKey(String pemEncodedKey) {
      if (!PemUtil.isPem(ByteUtil.toBytes(pemEncodedKey))) {
         throw new IllegalArgumentException("Data is not PEM encoded.");
      } else {
         this.encodedKey = pemEncodedKey;
      }
   }

   public PrivateKey newInstance() throws EncodingException {
      return KeyPairUtil.decodePrivateKey(PemUtil.decode(this.encodedKey));
   }
}
