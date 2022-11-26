package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EncryptedKeyResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(RSAKeyValueResolver.class);
   private Key kek;
   private String algorithm;
   private List internalKeyResolvers;

   public EncryptedKeyResolver(String algorithm) {
      this.kek = null;
      this.algorithm = algorithm;
   }

   public EncryptedKeyResolver(String algorithm, Key kek) {
      this.algorithm = algorithm;
      this.kek = kek;
   }

   public void registerInternalKeyResolver(KeyResolverSpi realKeyResolver) {
      if (this.internalKeyResolvers == null) {
         this.internalKeyResolvers = new ArrayList();
      }

      this.internalKeyResolvers.add(realKeyResolver);
   }

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) {
      return null;
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) {
      return null;
   }

   public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) {
      if (element == null) {
         return null;
      } else {
         LOG.debug("EncryptedKeyResolver - Can I resolve {}", element.getTagName());
         SecretKey key = null;
         boolean isEncryptedKey = XMLUtils.elementIsInEncryptionSpace(element, "EncryptedKey");
         if (isEncryptedKey) {
            LOG.debug("Passed an Encrypted Key");

            try {
               XMLCipher cipher = XMLCipher.getInstance();
               cipher.init(4, this.kek);
               if (this.internalKeyResolvers != null) {
                  int size = this.internalKeyResolvers.size();

                  for(int i = 0; i < size; ++i) {
                     cipher.registerInternalKeyResolver((KeyResolverSpi)this.internalKeyResolvers.get(i));
                  }
               }

               EncryptedKey ek = cipher.loadEncryptedKey(element);
               key = (SecretKey)cipher.decryptKey(ek, this.algorithm);
            } catch (XMLEncryptionException var9) {
               LOG.debug(var9.getMessage(), var9);
            }
         }

         return key;
      }
   }
}
