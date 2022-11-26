package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SecretKeyResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(SecretKeyResolver.class);
   private KeyStore keyStore;
   private char[] password;

   public SecretKeyResolver(KeyStore keyStore, char[] password) {
      this.keyStore = keyStore;
      this.password = password;
   }

   public boolean engineCanResolve(Element element, String baseURI, StorageResolver storage) {
      return XMLUtils.elementIsInSignatureSpace(element, "KeyName");
   }

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return null;
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return null;
   }

   public SecretKey engineResolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}?", element.getTagName());
      if (XMLUtils.elementIsInSignatureSpace(element, "KeyName")) {
         String keyName = element.getFirstChild().getNodeValue();

         try {
            Key key = this.keyStore.getKey(keyName, this.password);
            if (key instanceof SecretKey) {
               return (SecretKey)key;
            }
         } catch (Exception var6) {
            LOG.debug("Cannot recover the key", var6);
         }
      }

      LOG.debug("I can't");
      return null;
   }

   public PrivateKey engineLookupAndResolvePrivateKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return null;
   }
}
