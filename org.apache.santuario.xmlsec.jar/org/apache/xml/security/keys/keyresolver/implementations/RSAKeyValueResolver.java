package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.keyvalues.RSAKeyValue;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class RSAKeyValueResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(RSAKeyValueResolver.class);

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) {
      if (element == null) {
         return null;
      } else {
         LOG.debug("Can I resolve {}", element.getTagName());
         boolean isKeyValue = XMLUtils.elementIsInSignatureSpace(element, "KeyValue");
         Element rsaKeyElement = null;
         if (isKeyValue) {
            rsaKeyElement = XMLUtils.selectDsNode(element.getFirstChild(), "RSAKeyValue", 0);
         } else if (XMLUtils.elementIsInSignatureSpace(element, "RSAKeyValue")) {
            rsaKeyElement = element;
         }

         if (rsaKeyElement == null) {
            return null;
         } else {
            try {
               RSAKeyValue rsaKeyValue = new RSAKeyValue(rsaKeyElement, baseURI);
               return rsaKeyValue.getPublicKey();
            } catch (XMLSecurityException var7) {
               LOG.debug("XMLSecurityException", var7);
               return null;
            }
         }
      }
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) {
      return null;
   }

   public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) {
      return null;
   }
}
