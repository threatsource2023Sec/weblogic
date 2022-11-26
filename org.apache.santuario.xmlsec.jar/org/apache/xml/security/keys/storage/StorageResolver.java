package org.apache.xml.security.keys.storage;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.xml.security.keys.storage.implementations.KeyStoreResolver;
import org.apache.xml.security.keys.storage.implementations.SingleCertificateResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageResolver {
   private static final Logger LOG = LoggerFactory.getLogger(StorageResolver.class);
   private List storageResolvers;

   public StorageResolver() {
   }

   public StorageResolver(StorageResolverSpi resolver) {
      this.add(resolver);
   }

   public void add(StorageResolverSpi resolver) {
      if (this.storageResolvers == null) {
         this.storageResolvers = new ArrayList();
      }

      this.storageResolvers.add(resolver);
   }

   public StorageResolver(KeyStore keyStore) {
      this.add(keyStore);
   }

   public void add(KeyStore keyStore) {
      try {
         this.add((StorageResolverSpi)(new KeyStoreResolver(keyStore)));
      } catch (StorageResolverException var3) {
         LOG.error("Could not add KeyStore because of: ", var3);
      }

   }

   public StorageResolver(X509Certificate x509certificate) {
      this.add(x509certificate);
   }

   public void add(X509Certificate x509certificate) {
      this.add((StorageResolverSpi)(new SingleCertificateResolver(x509certificate)));
   }

   public Iterator getIterator() {
      return new StorageResolverIterator(this.storageResolvers.iterator());
   }

   static class StorageResolverIterator implements Iterator {
      Iterator resolvers = null;
      Iterator currentResolver = null;

      public StorageResolverIterator(Iterator resolvers) {
         this.resolvers = resolvers;
         this.currentResolver = this.findNextResolver();
      }

      public boolean hasNext() {
         if (this.currentResolver == null) {
            return false;
         } else if (this.currentResolver.hasNext()) {
            return true;
         } else {
            this.currentResolver = this.findNextResolver();
            return this.currentResolver != null;
         }
      }

      public Certificate next() {
         if (this.hasNext()) {
            return (Certificate)this.currentResolver.next();
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("Can't remove keys from KeyStore");
      }

      private Iterator findNextResolver() {
         while(true) {
            if (this.resolvers.hasNext()) {
               StorageResolverSpi resolverSpi = (StorageResolverSpi)this.resolvers.next();
               Iterator iter = resolverSpi.getIterator();
               if (!iter.hasNext()) {
                  continue;
               }

               return iter;
            }

            return null;
         }
      }
   }
}
