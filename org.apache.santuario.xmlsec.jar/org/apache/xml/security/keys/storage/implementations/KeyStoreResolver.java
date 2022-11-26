package org.apache.xml.security.keys.storage.implementations;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.xml.security.keys.storage.StorageResolverException;
import org.apache.xml.security.keys.storage.StorageResolverSpi;

public class KeyStoreResolver extends StorageResolverSpi {
   private KeyStore keyStore;

   public KeyStoreResolver(KeyStore keyStore) throws StorageResolverException {
      this.keyStore = keyStore;

      try {
         keyStore.aliases();
      } catch (KeyStoreException var3) {
         throw new StorageResolverException(var3);
      }
   }

   public Iterator getIterator() {
      return new KeyStoreIterator(this.keyStore);
   }

   static class KeyStoreIterator implements Iterator {
      KeyStore keyStore = null;
      Enumeration aliases = null;
      Certificate nextCert = null;

      public KeyStoreIterator(KeyStore keyStore) {
         try {
            this.keyStore = keyStore;
            this.aliases = this.keyStore.aliases();
         } catch (KeyStoreException var3) {
            this.aliases = new Enumeration() {
               public boolean hasMoreElements() {
                  return false;
               }

               public String nextElement() {
                  return null;
               }
            };
         }

      }

      public boolean hasNext() {
         if (this.nextCert == null) {
            this.nextCert = this.findNextCert();
         }

         return this.nextCert != null;
      }

      public Certificate next() {
         if (this.nextCert == null) {
            this.nextCert = this.findNextCert();
            if (this.nextCert == null) {
               throw new NoSuchElementException();
            }
         }

         Certificate ret = this.nextCert;
         this.nextCert = null;
         return ret;
      }

      public void remove() {
         throw new UnsupportedOperationException("Can't remove keys from KeyStore");
      }

      private Certificate findNextCert() {
         while(true) {
            if (this.aliases.hasMoreElements()) {
               String alias = (String)this.aliases.nextElement();

               try {
                  Certificate cert = this.keyStore.getCertificate(alias);
                  if (cert == null) {
                     continue;
                  }

                  return cert;
               } catch (KeyStoreException var3) {
                  return null;
               }
            }

            return null;
         }
      }
   }
}
