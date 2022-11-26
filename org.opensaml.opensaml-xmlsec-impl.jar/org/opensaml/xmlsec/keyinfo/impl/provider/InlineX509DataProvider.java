package org.opensaml.xmlsec.keyinfo.impl.provider;

import com.google.common.base.Strings;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.CredentialContext;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.InternalX500DNHandler;
import org.opensaml.security.x509.X500DNHandler;
import org.opensaml.security.x509.X509Support;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.X509Digest;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SKI;
import org.opensaml.xmlsec.signature.X509SubjectName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InlineX509DataProvider extends AbstractKeyInfoProvider {
   private final Logger log = LoggerFactory.getLogger(InlineX509DataProvider.class);
   private X500DNHandler x500DNHandler = new InternalX500DNHandler();

   @Nonnull
   public X500DNHandler getX500DNHandler() {
      return this.x500DNHandler;
   }

   public void setX500DNHandler(@Nonnull X500DNHandler handler) {
      this.x500DNHandler = (X500DNHandler)Constraint.isNotNull(handler, "X500DNHandler cannot be null");
   }

   public boolean handles(@Nonnull XMLObject keyInfoChild) {
      return keyInfoChild instanceof X509Data;
   }

   @Nullable
   public Collection process(@Nonnull KeyInfoCredentialResolver resolver, @Nonnull XMLObject keyInfoChild, @Nullable CriteriaSet criteriaSet, @Nonnull KeyInfoResolutionContext kiContext) throws SecurityException {
      if (!this.handles(keyInfoChild)) {
         return null;
      } else {
         X509Data x509Data = (X509Data)keyInfoChild;
         this.log.debug("Attempting to extract credential from an X509Data");
         List certs = this.extractCertificates(x509Data);
         if (certs.isEmpty()) {
            this.log.info("The X509Data contained no X509Certificate elements, skipping credential extraction");
            return null;
         } else {
            List crls = this.extractCRLs(x509Data);
            PublicKey resolvedPublicKey = null;
            if (kiContext.getKey() != null && kiContext.getKey() instanceof PublicKey) {
               resolvedPublicKey = (PublicKey)kiContext.getKey();
            }

            X509Certificate entityCert = this.findEntityCert(certs, x509Data, resolvedPublicKey);
            if (entityCert == null) {
               this.log.warn("The end-entity cert could not be identified, skipping credential extraction");
               return null;
            } else {
               BasicX509Credential cred = new BasicX509Credential(entityCert);
               cred.setCRLs(crls);
               cred.setEntityCertificateChain(certs);
               cred.getKeyNames().addAll(kiContext.getKeyNames());
               CredentialContext credContext = this.buildCredentialContext(kiContext);
               if (credContext != null) {
                  cred.getCredentialContextSet().add(credContext);
               }

               LazySet credentialSet = new LazySet();
               credentialSet.add(cred);
               return credentialSet;
            }
         }
      }
   }

   @Nonnull
   private List extractCRLs(@Nonnull X509Data x509Data) throws SecurityException {
      List crls = null;

      try {
         crls = KeyInfoSupport.getCRLs(x509Data);
      } catch (CRLException var4) {
         this.log.error("Error extracting CRLs from X509Data", var4);
         throw new SecurityException("Error extracting CRLs from X509Data", var4);
      }

      this.log.debug("Found {} X509CRLs", crls.size());
      return crls;
   }

   @Nonnull
   private List extractCertificates(@Nonnull X509Data x509Data) throws SecurityException {
      List certs = null;

      try {
         certs = KeyInfoSupport.getCertificates(x509Data);
      } catch (CertificateException var4) {
         this.log.error("Error extracting certificates from X509Data", var4);
         throw new SecurityException("Error extracting certificates from X509Data", var4);
      }

      this.log.debug("Found {} X509Certificates", certs.size());
      return certs;
   }

   @Nullable
   protected X509Certificate findEntityCert(@Nullable List certs, @Nonnull X509Data x509Data, @Nullable PublicKey resolvedKey) {
      if (certs != null && !certs.isEmpty()) {
         if (certs.size() == 1) {
            this.log.debug("Single certificate was present, treating as end-entity certificate");
            return (X509Certificate)certs.get(0);
         } else {
            X509Certificate cert = null;
            cert = this.findCertFromKey(certs, resolvedKey);
            if (cert != null) {
               this.log.debug("End-entity certificate resolved by matching previously resolved public key");
               return cert;
            } else {
               cert = this.findCertFromSubjectNames(certs, x509Data.getX509SubjectNames());
               if (cert != null) {
                  this.log.debug("End-entity certificate resolved by matching X509SubjectName");
                  return cert;
               } else {
                  cert = this.findCertFromIssuerSerials(certs, x509Data.getX509IssuerSerials());
                  if (cert != null) {
                     this.log.debug("End-entity certificate resolved by matching X509IssuerSerial");
                     return cert;
                  } else {
                     cert = this.findCertFromSubjectKeyIdentifier(certs, x509Data.getX509SKIs());
                     if (cert != null) {
                        this.log.debug("End-entity certificate resolved by matching X509SKI");
                        return cert;
                     } else {
                        cert = this.findCertFromDigest(certs, x509Data.getX509Digests());
                        if (cert != null) {
                           this.log.debug("End-entity certificate resolved by matching X509Digest");
                           return cert;
                        } else {
                           this.log.debug("Treating the first certificate in the X509Data as the end-entity certificate");
                           return (X509Certificate)certs.get(0);
                        }
                     }
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   @Nullable
   protected X509Certificate findCertFromKey(@Nonnull List certs, @Nullable PublicKey key) {
      if (key != null) {
         Iterator var3 = certs.iterator();

         while(var3.hasNext()) {
            X509Certificate cert = (X509Certificate)var3.next();
            if (cert.getPublicKey().equals(key)) {
               return cert;
            }
         }
      }

      return null;
   }

   @Nullable
   protected X509Certificate findCertFromSubjectNames(@Nonnull List certs, @Nonnull List names) {
      Iterator var3 = names.iterator();

      while(true) {
         X509SubjectName subjectName;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            subjectName = (X509SubjectName)var3.next();
         } while(Strings.isNullOrEmpty(subjectName.getValue()));

         X500Principal subjectX500Principal = null;

         try {
            subjectX500Principal = this.x500DNHandler.parse(subjectName.getValue());
         } catch (IllegalArgumentException var8) {
            this.log.warn("X500 subject name '{}' could not be parsed by configured X500DNHandler '{}'", subjectName.getValue(), this.x500DNHandler.getClass().getName());
            return null;
         }

         Iterator var6 = certs.iterator();

         while(var6.hasNext()) {
            X509Certificate cert = (X509Certificate)var6.next();
            if (cert.getSubjectX500Principal().equals(subjectX500Principal)) {
               return cert;
            }
         }
      }
   }

   @Nullable
   protected X509Certificate findCertFromIssuerSerials(@Nonnull List certs, @Nonnull List serials) {
      Iterator var3 = serials.iterator();

      while(true) {
         String issuerNameValue;
         BigInteger serialNumber;
         do {
            X509IssuerSerial issuerSerial;
            do {
               do {
                  if (!var3.hasNext()) {
                     return null;
                  }

                  issuerSerial = (X509IssuerSerial)var3.next();
               } while(issuerSerial.getX509IssuerName() == null);
            } while(issuerSerial.getX509SerialNumber() == null);

            issuerNameValue = issuerSerial.getX509IssuerName().getValue();
            serialNumber = issuerSerial.getX509SerialNumber().getValue();
         } while(Strings.isNullOrEmpty(issuerNameValue));

         X500Principal issuerX500Principal = null;

         try {
            issuerX500Principal = this.x500DNHandler.parse(issuerNameValue);
         } catch (IllegalArgumentException var10) {
            this.log.warn("X500 issuer name '{}' could not be parsed by configured X500DNHandler '{}'", issuerNameValue, this.x500DNHandler.getClass().getName());
            return null;
         }

         Iterator var8 = certs.iterator();

         while(var8.hasNext()) {
            X509Certificate cert = (X509Certificate)var8.next();
            if (cert.getIssuerX500Principal().equals(issuerX500Principal) && cert.getSerialNumber().equals(serialNumber)) {
               return cert;
            }
         }
      }
   }

   @Nullable
   protected X509Certificate findCertFromSubjectKeyIdentifier(@Nonnull List certs, @Nonnull List skis) {
      Iterator var3 = skis.iterator();

      while(true) {
         X509SKI ski;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            ski = (X509SKI)var3.next();
         } while(Strings.isNullOrEmpty(ski.getValue()));

         byte[] xmlValue = Base64Support.decode(ski.getValue());
         Iterator var6 = certs.iterator();

         while(var6.hasNext()) {
            X509Certificate cert = (X509Certificate)var6.next();
            byte[] certValue = X509Support.getSubjectKeyIdentifier(cert);
            if (certValue != null && Arrays.equals(xmlValue, certValue)) {
               return cert;
            }
         }
      }
   }

   @Nullable
   protected X509Certificate findCertFromDigest(@Nonnull List certs, @Nonnull List digests) {
      Iterator var3 = digests.iterator();

      while(true) {
         while(true) {
            X509Digest digest;
            do {
               do {
                  if (!var3.hasNext()) {
                     return null;
                  }

                  digest = (X509Digest)var3.next();
               } while(Strings.isNullOrEmpty(digest.getValue()));
            } while(Strings.isNullOrEmpty(digest.getAlgorithm()));

            String alg = AlgorithmSupport.getAlgorithmID(digest.getAlgorithm());
            if (alg == null) {
               this.log.warn("Algorithm {} not supported", digest.getAlgorithm());
            } else {
               byte[] xmlValue = Base64Support.decode(digest.getValue());
               Iterator var7 = certs.iterator();

               while(var7.hasNext()) {
                  X509Certificate cert = (X509Certificate)var7.next();

                  try {
                     byte[] certValue = X509Support.getX509Digest(cert, alg);
                     if (certValue != null && Arrays.equals(xmlValue, certValue)) {
                        return cert;
                     }
                  } catch (SecurityException var10) {
                  }
               }
            }
         }
      }
   }
}
