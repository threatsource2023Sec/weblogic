package org.cryptacular.bean;

import java.security.KeyStore;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.adapter.BufferedBlockCipherAdapter;
import org.cryptacular.generator.Nonce;
import org.cryptacular.spec.Spec;

public class BufferedBlockCipherBean extends AbstractBlockCipherBean {
   private Spec blockCipherSpec;

   public BufferedBlockCipherBean() {
   }

   public BufferedBlockCipherBean(Spec blockCipherSpec, KeyStore keyStore, String keyAlias, String keyPassword, Nonce nonce) {
      super(keyStore, keyAlias, keyPassword, nonce);
      this.setBlockCipherSpec(blockCipherSpec);
   }

   public Spec getBlockCipherSpec() {
      return this.blockCipherSpec;
   }

   public void setBlockCipherSpec(Spec blockCipherSpec) {
      this.blockCipherSpec = blockCipherSpec;
   }

   protected BufferedBlockCipherAdapter newCipher(CiphertextHeader header, boolean mode) {
      BufferedBlockCipher cipher = (BufferedBlockCipher)this.blockCipherSpec.newInstance();
      CipherParameters params = new KeyParameter(this.lookupKey(header.getKeyName()).getEncoded());
      String algName = cipher.getUnderlyingCipher().getAlgorithmName();
      if (algName.endsWith("CBC") || algName.endsWith("OFB") || algName.endsWith("CFB")) {
         params = new ParametersWithIV((CipherParameters)params, header.getNonce());
      }

      cipher.init(mode, (CipherParameters)params);
      return new BufferedBlockCipherAdapter(cipher);
   }
}
