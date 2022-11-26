package org.cryptacular.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import javax.crypto.SecretKey;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.CryptoException;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.generator.Nonce;

public abstract class AbstractCipherBean implements CipherBean {
   private KeyStore keyStore;
   private String keyAlias;
   private String keyPassword;
   private Nonce nonce;

   public AbstractCipherBean() {
   }

   public AbstractCipherBean(KeyStore keyStore, String keyAlias, String keyPassword, Nonce nonce) {
      this.setKeyStore(keyStore);
      this.setKeyAlias(keyAlias);
      this.setKeyPassword(keyPassword);
      this.setNonce(nonce);
   }

   public KeyStore getKeyStore() {
      return this.keyStore;
   }

   public void setKeyStore(KeyStore keyStore) {
      this.keyStore = keyStore;
   }

   public String getKeyAlias() {
      return this.keyAlias;
   }

   public void setKeyAlias(String keyAlias) {
      this.keyAlias = keyAlias;
   }

   public void setKeyPassword(String keyPassword) {
      this.keyPassword = keyPassword;
   }

   public Nonce getNonce() {
      return this.nonce;
   }

   public void setNonce(Nonce nonce) {
      this.nonce = nonce;
   }

   public byte[] encrypt(byte[] input) throws CryptoException {
      return this.process(new CiphertextHeader(this.nonce.generate(), this.keyAlias), true, input);
   }

   public void encrypt(InputStream input, OutputStream output) throws CryptoException, StreamException {
      CiphertextHeader header = new CiphertextHeader(this.nonce.generate(), this.keyAlias);

      try {
         output.write(header.encode());
      } catch (IOException var5) {
         throw new StreamException(var5);
      }

      this.process(header, true, input, output);
   }

   public byte[] decrypt(byte[] input) throws CryptoException, EncodingException {
      CiphertextHeader header = CiphertextHeader.decode(input);
      if (header.getKeyName() == null) {
         throw new CryptoException("Ciphertext header does not contain required key");
      } else {
         return this.process(header, false, input);
      }
   }

   public void decrypt(InputStream input, OutputStream output) throws CryptoException, EncodingException, StreamException {
      CiphertextHeader header = CiphertextHeader.decode(input);
      if (header.getKeyName() == null) {
         throw new CryptoException("Ciphertext header does not contain required key");
      } else {
         this.process(header, false, input, output);
      }
   }

   protected SecretKey lookupKey(String alias) {
      Key key;
      try {
         key = this.keyStore.getKey(alias, this.keyPassword.toCharArray());
      } catch (Exception var4) {
         throw new CryptoException("Error accessing keystore entry " + alias, var4);
      }

      if (key instanceof SecretKey) {
         return (SecretKey)key;
      } else {
         throw new CryptoException(alias + " is not a secret key");
      }
   }

   protected abstract byte[] process(CiphertextHeader var1, boolean var2, byte[] var3);

   protected abstract void process(CiphertextHeader var1, boolean var2, InputStream var3, OutputStream var4);
}
