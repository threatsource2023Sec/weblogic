package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Enumeration;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.X509Data;
import org.apache.xml.security.keys.content.x509.XMLX509Certificate;
import org.apache.xml.security.keys.content.x509.XMLX509IssuerSerial;
import org.apache.xml.security.keys.content.x509.XMLX509SKI;
import org.apache.xml.security.keys.content.x509.XMLX509SubjectName;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class PrivateKeyResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(PrivateKeyResolver.class);
   private KeyStore keyStore;
   private char[] password;

   public PrivateKeyResolver(KeyStore keyStore, char[] password) {
      this.keyStore = keyStore;
      this.password = password;
   }

   public boolean engineCanResolve(Element element, String baseURI, StorageResolver storage) {
      return XMLUtils.elementIsInSignatureSpace(element, "X509Data") || XMLUtils.elementIsInSignatureSpace(element, "KeyName");
   }

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return null;
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return null;
   }

   public SecretKey engineResolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return null;
   }

   public PrivateKey engineLookupAndResolvePrivateKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}?", element.getTagName());
      if (XMLUtils.elementIsInSignatureSpace(element, "X509Data")) {
         PrivateKey privKey = this.resolveX509Data(element, baseURI);
         if (privKey != null) {
            return privKey;
         }
      } else if (XMLUtils.elementIsInSignatureSpace(element, "KeyName")) {
         LOG.debug("Can I resolve KeyName?");
         String keyName = element.getFirstChild().getNodeValue();

         try {
            Key key = this.keyStore.getKey(keyName, this.password);
            if (key instanceof PrivateKey) {
               return (PrivateKey)key;
            }
         } catch (Exception var6) {
            LOG.debug("Cannot recover the key", var6);
         }
      }

      LOG.debug("I can't");
      return null;
   }

   private PrivateKey resolveX509Data(Element element, String baseURI) {
      LOG.debug("Can I resolve X509Data?");

      try {
         X509Data x509Data = new X509Data(element, baseURI);
         int len = x509Data.lengthSKI();

         int i;
         PrivateKey privKey;
         for(i = 0; i < len; ++i) {
            XMLX509SKI x509SKI = x509Data.itemSKI(i);
            privKey = this.resolveX509SKI(x509SKI);
            if (privKey != null) {
               return privKey;
            }
         }

         len = x509Data.lengthIssuerSerial();

         for(i = 0; i < len; ++i) {
            XMLX509IssuerSerial x509Serial = x509Data.itemIssuerSerial(i);
            privKey = this.resolveX509IssuerSerial(x509Serial);
            if (privKey != null) {
               return privKey;
            }
         }

         len = x509Data.lengthSubjectName();

         for(i = 0; i < len; ++i) {
            XMLX509SubjectName x509SubjectName = x509Data.itemSubjectName(i);
            privKey = this.resolveX509SubjectName(x509SubjectName);
            if (privKey != null) {
               return privKey;
            }
         }

         len = x509Data.lengthCertificate();

         for(i = 0; i < len; ++i) {
            XMLX509Certificate x509Cert = x509Data.itemCertificate(i);
            privKey = this.resolveX509Certificate(x509Cert);
            if (privKey != null) {
               return privKey;
            }
         }
      } catch (XMLSecurityException var8) {
         LOG.debug("XMLSecurityException", var8);
      } catch (KeyStoreException var9) {
         LOG.debug("KeyStoreException", var9);
      }

      return null;
   }

   private PrivateKey resolveX509SKI(XMLX509SKI x509SKI) throws XMLSecurityException, KeyStoreException {
      LOG.debug("Can I resolve X509SKI?");
      Enumeration aliases = this.keyStore.aliases();

      while(aliases.hasMoreElements()) {
         String alias = (String)aliases.nextElement();
         if (this.keyStore.isKeyEntry(alias)) {
            Certificate cert = this.keyStore.getCertificate(alias);
            if (cert instanceof X509Certificate) {
               XMLX509SKI certSKI = new XMLX509SKI(x509SKI.getDocument(), (X509Certificate)cert);
               if (certSKI.equals(x509SKI)) {
                  LOG.debug("match !!! ");

                  try {
                     Key key = this.keyStore.getKey(alias, this.password);
                     if (key instanceof PrivateKey) {
                        return (PrivateKey)key;
                     }
                  } catch (Exception var7) {
                     LOG.debug("Cannot recover the key", var7);
                  }
               }
            }
         }
      }

      return null;
   }

   private PrivateKey resolveX509IssuerSerial(XMLX509IssuerSerial x509Serial) throws KeyStoreException {
      LOG.debug("Can I resolve X509IssuerSerial?");
      Enumeration aliases = this.keyStore.aliases();

      while(aliases.hasMoreElements()) {
         String alias = (String)aliases.nextElement();
         if (this.keyStore.isKeyEntry(alias)) {
            Certificate cert = this.keyStore.getCertificate(alias);
            if (cert instanceof X509Certificate) {
               XMLX509IssuerSerial certSerial = new XMLX509IssuerSerial(x509Serial.getDocument(), (X509Certificate)cert);
               if (certSerial.equals(x509Serial)) {
                  LOG.debug("match !!! ");

                  try {
                     Key key = this.keyStore.getKey(alias, this.password);
                     if (key instanceof PrivateKey) {
                        return (PrivateKey)key;
                     }
                  } catch (Exception var7) {
                     LOG.debug("Cannot recover the key", var7);
                  }
               }
            }
         }
      }

      return null;
   }

   private PrivateKey resolveX509SubjectName(XMLX509SubjectName x509SubjectName) throws KeyStoreException {
      LOG.debug("Can I resolve X509SubjectName?");
      Enumeration aliases = this.keyStore.aliases();

      while(aliases.hasMoreElements()) {
         String alias = (String)aliases.nextElement();
         if (this.keyStore.isKeyEntry(alias)) {
            Certificate cert = this.keyStore.getCertificate(alias);
            if (cert instanceof X509Certificate) {
               XMLX509SubjectName certSN = new XMLX509SubjectName(x509SubjectName.getDocument(), (X509Certificate)cert);
               if (certSN.equals(x509SubjectName)) {
                  LOG.debug("match !!! ");

                  try {
                     Key key = this.keyStore.getKey(alias, this.password);
                     if (key instanceof PrivateKey) {
                        return (PrivateKey)key;
                     }
                  } catch (Exception var7) {
                     LOG.debug("Cannot recover the key", var7);
                  }
               }
            }
         }
      }

      return null;
   }

   private PrivateKey resolveX509Certificate(XMLX509Certificate x509Cert) throws XMLSecurityException, KeyStoreException {
      LOG.debug("Can I resolve X509Certificate?");
      byte[] x509CertBytes = x509Cert.getCertificateBytes();
      Enumeration aliases = this.keyStore.aliases();

      while(aliases.hasMoreElements()) {
         String alias = (String)aliases.nextElement();
         if (this.keyStore.isKeyEntry(alias)) {
            Certificate cert = this.keyStore.getCertificate(alias);
            if (cert instanceof X509Certificate) {
               byte[] certBytes = null;

               try {
                  certBytes = cert.getEncoded();
               } catch (CertificateEncodingException var9) {
                  LOG.debug("Cannot recover the key", var9);
               }

               if (certBytes != null && Arrays.equals(certBytes, x509CertBytes)) {
                  LOG.debug("match !!! ");

                  try {
                     Key key = this.keyStore.getKey(alias, this.password);
                     if (key instanceof PrivateKey) {
                        return (PrivateKey)key;
                     }
                  } catch (Exception var8) {
                     LOG.debug("Cannot recover the key", var8);
                  }
               }
            }
         }
      }

      return null;
   }
}
