package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.keyvalues.DSAKeyValue;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class DSAKeyValueResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(DSAKeyValueResolver.class);

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) {
      if (element == null) {
         return null;
      } else {
         Element dsaKeyElement = null;
         boolean isKeyValue = XMLUtils.elementIsInSignatureSpace(element, "KeyValue");
         if (isKeyValue) {
            dsaKeyElement = XMLUtils.selectDsNode(element.getFirstChild(), "DSAKeyValue", 0);
         } else if (XMLUtils.elementIsInSignatureSpace(element, "DSAKeyValue")) {
            dsaKeyElement = element;
         }

         if (dsaKeyElement == null) {
            return null;
         } else {
            try {
               DSAKeyValue dsaKeyValue = new DSAKeyValue(dsaKeyElement, baseURI);
               PublicKey pk = dsaKeyValue.getPublicKey();
               return pk;
            } catch (XMLSecurityException var8) {
               LOG.debug(var8.getMessage(), var8);
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
