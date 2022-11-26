package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.keyvalues.ECKeyValue;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class ECKeyValueResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(ECKeyValueResolver.class);

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) {
      if (element == null) {
         return null;
      } else {
         Element ecKeyElement = null;
         boolean isKeyValue = XMLUtils.elementIsInSignatureSpace(element, "KeyValue");
         if (isKeyValue) {
            ecKeyElement = XMLUtils.selectDs11Node(element.getFirstChild(), "ECKeyValue", 0);
         } else if (XMLUtils.elementIsInSignature11Space(element, "ECKeyValue")) {
            ecKeyElement = element;
         }

         if (ecKeyElement == null) {
            return null;
         } else {
            try {
               ECKeyValue ecKeyValue = new ECKeyValue(ecKeyElement, baseURI);
               return ecKeyValue.getPublicKey();
            } catch (XMLSecurityException var7) {
               LOG.debug(var7.getMessage(), var7);
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
