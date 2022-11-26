package org.cryptacular.bean;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.cryptacular.CryptoException;

public class KeyStoreBasedKeyFactoryBean implements FactoryBean {
   private KeyStore keyStore;
   private String alias;
   private String password;

   public KeyStoreBasedKeyFactoryBean() {
   }

   public KeyStoreBasedKeyFactoryBean(KeyStore keyStore, String alias, String password) {
      this.setKeyStore(keyStore);
      this.setAlias(alias);
      this.setPassword(password);
   }

   public KeyStore getKeyStore() {
      return this.keyStore;
   }

   public void setKeyStore(KeyStore keyStore) {
      this.keyStore = keyStore;
   }

   public String getAlias() {
      return this.alias;
   }

   public void setAlias(String alias) {
      this.alias = alias;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Key newInstance() {
      try {
         Key key = this.keyStore.getKey(this.alias, this.password.toCharArray());
         return key;
      } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException var3) {
         throw new CryptoException("Error accessing keystore entry " + this.alias, var3);
      }
   }
}
