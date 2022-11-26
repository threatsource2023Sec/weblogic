package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.X509Data;
import org.apache.xml.security.keys.content.x509.XMLX509IssuerSerial;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.signature.XMLSignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class X509IssuerSerialResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(X509IssuerSerialResolver.class);

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      X509Certificate cert = this.engineLookupResolveX509Certificate(element, baseURI, storage);
      return cert != null ? cert.getPublicKey() : null;
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}?", element.getTagName());
      X509Data x509data = null;

      try {
         x509data = new X509Data(element, baseURI);
      } catch (XMLSignatureException var11) {
         LOG.debug("I can't");
         return null;
      } catch (XMLSecurityException var12) {
         LOG.debug("I can't");
         return null;
      }

      if (!x509data.containsIssuerSerial()) {
         return null;
      } else {
         try {
            if (storage == null) {
               Object[] exArgs = new Object[]{"X509IssuerSerial"};
               KeyResolverException ex = new KeyResolverException("KeyResolver.needStorageResolver", exArgs);
               LOG.debug("", ex);
               throw ex;
            } else {
               int noOfISS = x509data.lengthIssuerSerial();
               Iterator storageIterator = storage.getIterator();

               while(storageIterator.hasNext()) {
                  X509Certificate cert = (X509Certificate)storageIterator.next();
                  XMLX509IssuerSerial certSerial = new XMLX509IssuerSerial(element.getOwnerDocument(), cert);
                  LOG.debug("Found Certificate Issuer: {}", certSerial.getIssuerName());
                  LOG.debug("Found Certificate Serial: {}", certSerial.getSerialNumber().toString());

                  for(int i = 0; i < noOfISS; ++i) {
                     XMLX509IssuerSerial xmliss = x509data.itemIssuerSerial(i);
                     LOG.debug("Found Element Issuer:     {}", xmliss.getIssuerName());
                     LOG.debug("Found Element Serial:     {}", xmliss.getSerialNumber().toString());
                     if (certSerial.equals(xmliss)) {
                        LOG.debug("match !!! ");
                        return cert;
                     }

                     LOG.debug("no match...");
                  }
               }

               return null;
            }
         } catch (XMLSecurityException var13) {
            LOG.debug("XMLSecurityException", var13);
            throw new KeyResolverException(var13);
         }
      }
   }

   public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) {
      return null;
   }
}
