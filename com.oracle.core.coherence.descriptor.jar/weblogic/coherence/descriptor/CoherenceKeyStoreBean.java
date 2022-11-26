package weblogic.coherence.descriptor;

import java.io.File;
import java.security.KeyStore;

public class CoherenceKeyStoreBean {
   private File keyStoreFile;
   private String keyStoreType;
   private char[] passPhrase;
   private char[] identityPassPhrase;
   private KeyStore keystore;

   public File getKeyStoreFile() {
      return this.keyStoreFile;
   }

   public void setKeyStoreFile(File keyStoreFile) {
      this.keyStoreFile = keyStoreFile;
   }

   public String getKeyStoreType() {
      return this.keyStoreType;
   }

   public void setKeyStoreType(String keyStoreType) {
      this.keyStoreType = keyStoreType;
   }

   public char[] getPassPhrase() {
      return this.passPhrase;
   }

   public void setPassPhrase(char[] passPhrase) {
      this.passPhrase = passPhrase;
   }

   public char[] getIdentityPassPhrase() {
      return this.identityPassPhrase;
   }

   public void setIdentityPassPhrase(char[] identityPassPhrase) {
      this.identityPassPhrase = identityPassPhrase;
   }

   public KeyStore getKeyStore() {
      return this.keystore;
   }

   public void setKeyStore(KeyStore keystore) {
      this.keystore = keystore;
   }
}
