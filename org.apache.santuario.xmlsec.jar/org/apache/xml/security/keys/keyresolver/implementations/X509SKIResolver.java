package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.x509.XMLX509SKI;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class X509SKIResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(X509SKIResolver.class);

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      X509Certificate cert = this.engineLookupResolveX509Certificate(element, baseURI, storage);
      return cert != null ? cert.getPublicKey() : null;
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}?", element.getTagName());
      if (!XMLUtils.elementIsInSignatureSpace(element, "X509Data")) {
         LOG.debug("I can't");
         return null;
      } else {
         XMLX509SKI[] x509childObject = null;
         Element[] x509childNodes = null;
         x509childNodes = XMLUtils.selectDsNodes(element.getFirstChild(), "X509SKI");
         if (x509childNodes != null && x509childNodes.length > 0) {
            try {
               if (storage == null) {
                  Object[] exArgs = new Object[]{"X509SKI"};
                  KeyResolverException ex = new KeyResolverException("KeyResolver.needStorageResolver", exArgs);
                  LOG.debug("", ex);
                  throw ex;
               } else {
                  x509childObject = new XMLX509SKI[x509childNodes.length];

                  for(int i = 0; i < x509childNodes.length; ++i) {
                     x509childObject[i] = new XMLX509SKI(x509childNodes[i], baseURI);
                  }

                  Iterator storageIterator = storage.getIterator();

                  while(storageIterator.hasNext()) {
                     X509Certificate cert = (X509Certificate)storageIterator.next();
                     XMLX509SKI certSKI = new XMLX509SKI(element.getOwnerDocument(), cert);

                     for(int i = 0; i < x509childObject.length; ++i) {
                        if (certSKI.equals(x509childObject[i])) {
                           LOG.debug("Return PublicKey from {}", cert.getSubjectX500Principal().getName());
                           return cert;
                        }
                     }
                  }

                  return null;
               }
            } catch (XMLSecurityException var10) {
               throw new KeyResolverException(var10);
            }
         } else {
            LOG.debug("I can't");
            return null;
         }
      }
   }

   public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) {
      return null;
   }
}
