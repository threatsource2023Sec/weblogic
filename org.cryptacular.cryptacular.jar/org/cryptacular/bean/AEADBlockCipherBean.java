package org.cryptacular.bean;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.adapter.AEADBlockCipherAdapter;
import org.cryptacular.generator.Nonce;
import org.cryptacular.spec.Spec;

public class AEADBlockCipherBean extends AbstractBlockCipherBean {
   public static final int MAC_SIZE_BITS = 128;
   private Spec blockCipherSpec;

   public AEADBlockCipherBean() {
   }

   public AEADBlockCipherBean(Spec blockCipherSpec, KeyStore keyStore, String keyAlias, String keyPassword, Nonce nonce) {
      super(keyStore, keyAlias, keyPassword, nonce);
      this.setBlockCipherSpec(blockCipherSpec);
   }

   public Spec getBlockCipherSpec() {
      return this.blockCipherSpec;
   }

   public void setBlockCipherSpec(Spec blockCipherSpec) {
      this.blockCipherSpec = blockCipherSpec;
   }

   public void encrypt(InputStream input, OutputStream output) {
      if (this.blockCipherSpec.toString().endsWith("CCM")) {
         throw new UnsupportedOperationException("CCM mode ciphers do not support chunked encryption.");
      } else {
         super.encrypt(input, output);
      }
   }

   public void decrypt(InputStream input, OutputStream output) {
      if (this.blockCipherSpec.toString().endsWith("CCM")) {
         throw new UnsupportedOperationException("CCM mode ciphers do not support chunked decryption.");
      } else {
         super.decrypt(input, output);
      }
   }

   protected AEADBlockCipherAdapter newCipher(CiphertextHeader header, boolean mode) {
      AEADBlockCipher cipher = (AEADBlockCipher)this.blockCipherSpec.newInstance();
      SecretKey key = this.lookupKey(header.getKeyName());
      AEADParameters params = new AEADParameters(new KeyParameter(key.getEncoded()), 128, header.getNonce(), header.encode());
      cipher.init(mode, params);
      return new AEADBlockCipherAdapter(cipher);
   }
}
