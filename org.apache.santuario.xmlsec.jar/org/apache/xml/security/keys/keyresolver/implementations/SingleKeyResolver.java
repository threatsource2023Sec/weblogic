package org.apache.xml.security.keys.keyresolver.implementations;

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

public class SingleKeyResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(SingleKeyResolver.class);
   private String keyName;
   private PublicKey publicKey;
   private PrivateKey privateKey;
   private SecretKey secretKey;

   public SingleKeyResolver(String keyName, PublicKey publicKey) {
      this.keyName = keyName;
      this.publicKey = publicKey;
   }

   public SingleKeyResolver(String keyName, PrivateKey privateKey) {
      this.keyName = keyName;
      this.privateKey = privateKey;
   }

   public SingleKeyResolver(String keyName, SecretKey secretKey) {
      this.keyName = keyName;
      this.secretKey = secretKey;
   }

   public boolean engineCanResolve(Element element, String baseURI, StorageResolver storage) {
      return XMLUtils.elementIsInSignatureSpace(element, "KeyName");
   }

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}?", element.getTagName());
      if (this.publicKey != null && XMLUtils.elementIsInSignatureSpace(element, "KeyName")) {
         String name = element.getFirstChild().getNodeValue();
         if (this.keyName.equals(name)) {
            return this.publicKey;
         }
      }

      LOG.debug("I can't");
      return null;
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return null;
   }

   public SecretKey engineResolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}?", element.getTagName());
      if (this.secretKey != null && XMLUtils.elementIsInSignatureSpace(element, "KeyName")) {
         String name = element.getFirstChild().getNodeValue();
         if (this.keyName.equals(name)) {
            return this.secretKey;
         }
      }

      LOG.debug("I can't");
      return null;
   }

   public PrivateKey engineLookupAndResolvePrivateKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}?", element.getTagName());
      if (this.privateKey != null && XMLUtils.elementIsInSignatureSpace(element, "KeyName")) {
         String name = element.getFirstChild().getNodeValue();
         if (this.keyName.equals(name)) {
            return this.privateKey;
         }
      }

      LOG.debug("I can't");
      return null;
   }
}
