package org.opensaml.xmlsec.keyinfo.impl;

import com.google.common.base.Strings;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.InternalX500DNHandler;
import org.opensaml.security.x509.X500DNHandler;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509Support;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.X509SKI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class X509KeyInfoGeneratorFactory extends BasicKeyInfoGeneratorFactory {
   private final X509Options options = (X509Options)super.getOptions();

   @Nonnull
   public Class getCredentialType() {
      return X509Credential.class;
   }

   public boolean handles(@Nonnull Credential credential) {
      return credential instanceof X509Credential;
   }

   @Nonnull
   public KeyInfoGenerator newInstance() {
      X509Options newOptions = this.options.clone();
      return new X509KeyInfoGenerator(newOptions);
   }

   public boolean emitCRLs() {
      return this.options.emitCRLs;
   }

   public void setEmitCRLs(boolean newValue) {
      this.options.emitCRLs = newValue;
   }

   public boolean emitEntityCertificate() {
      return this.options.emitEntityCertificate;
   }

   public void setEmitEntityCertificate(boolean newValue) {
      this.options.emitEntityCertificate = newValue;
   }

   public boolean emitEntityCertificateChain() {
      return this.options.emitEntityCertificateChain;
   }

   public void setEmitEntityCertificateChain(boolean newValue) {
      this.options.emitEntityCertificateChain = newValue;
   }

   public boolean emitSubjectAltNamesAsKeyNames() {
      return this.options.emitSubjectAltNamesAsKeyNames;
   }

   public void setEmitSubjectAltNamesAsKeyNames(boolean newValue) {
      this.options.emitSubjectAltNamesAsKeyNames = newValue;
   }

   public boolean emitSubjectCNAsKeyName() {
      return this.options.emitSubjectCNAsKeyName;
   }

   public void setEmitSubjectCNAsKeyName(boolean newValue) {
      this.options.emitSubjectCNAsKeyName = newValue;
   }

   public boolean emitSubjectDNAsKeyName() {
      return this.options.emitSubjectDNAsKeyName;
   }

   public void setEmitSubjectDNAsKeyName(boolean newValue) {
      this.options.emitSubjectDNAsKeyName = newValue;
   }

   public boolean emitX509IssuerSerial() {
      return this.options.emitX509IssuerSerial;
   }

   public void setEmitX509IssuerSerial(boolean newValue) {
      this.options.emitX509IssuerSerial = newValue;
   }

   public boolean emitX509SKI() {
      return this.options.emitX509SKI;
   }

   public void setEmitX509SKI(boolean newValue) {
      this.options.emitX509SKI = newValue;
   }

   public boolean emitX509Digest() {
      return this.options.emitX509Digest;
   }

   public void setEmitX509Digest(boolean newValue) {
      this.options.emitX509Digest = newValue;
   }

   @Nonnull
   public String getX509DigestAlgorithmURI() {
      return this.options.x509DigestAlgorithmURI;
   }

   public void setX509DigestAlgorithmURI(@Nonnull String alg) {
      this.options.x509DigestAlgorithmURI = (String)Constraint.isNotNull(alg, "Algorithm cannot be null");
   }

   public boolean emitX509SubjectName() {
      return this.options.emitX509SubjectName;
   }

   public void setEmitX509SubjectName(boolean newValue) {
      this.options.emitX509SubjectName = newValue;
   }

   @Nonnull
   public Set getSubjectAltNames() {
      return this.options.subjectAltNames;
   }

   @Nonnull
   public X500DNHandler getX500DNHandler() {
      return this.options.x500DNHandler;
   }

   public void setX500DNHandler(@Nonnull X500DNHandler handler) {
      this.options.x500DNHandler = (X500DNHandler)Constraint.isNotNull(handler, "X500DNHandler cannot be null");
   }

   @Nullable
   public String getX500SubjectDNFormat() {
      return this.options.x500SubjectDNFormat;
   }

   public void setX500SubjectDNFormat(@Nullable String format) {
      this.options.x500SubjectDNFormat = format;
   }

   @Nullable
   public String getX500IssuerDNFormat() {
      return this.options.x500IssuerDNFormat;
   }

   public void setX500IssuerDNFormat(@Nullable String format) {
      this.options.x500IssuerDNFormat = format;
   }

   @Nonnull
   protected X509Options getOptions() {
      return this.options;
   }

   @Nonnull
   protected X509Options newOptions() {
      return new X509Options();
   }

   protected class X509Options extends BasicKeyInfoGeneratorFactory.BasicOptions {
      private boolean emitEntityCertificate;
      private boolean emitEntityCertificateChain;
      private boolean emitCRLs;
      private boolean emitX509SubjectName;
      private boolean emitX509IssuerSerial;
      private boolean emitX509SKI;
      private boolean emitX509Digest;
      private String x509DigestAlgorithmURI = "http://www.w3.org/2001/04/xmlenc#sha256";
      private boolean emitSubjectDNAsKeyName;
      private boolean emitSubjectCNAsKeyName;
      private boolean emitSubjectAltNamesAsKeyNames;
      private Set subjectAltNames = new LazySet();
      private X500DNHandler x500DNHandler = new InternalX500DNHandler();
      private String x500SubjectDNFormat = "RFC2253";
      private String x500IssuerDNFormat = "RFC2253";

      protected X509Options() {
         super();
      }

      protected X509Options clone() {
         X509Options clonedOptions = (X509Options)super.clone();
         clonedOptions.subjectAltNames = new LazySet();
         clonedOptions.subjectAltNames.addAll(this.subjectAltNames);
         clonedOptions.x500DNHandler = this.x500DNHandler.clone();
         return clonedOptions;
      }
   }

   public class X509KeyInfoGenerator extends BasicKeyInfoGeneratorFactory.BasicKeyInfoGenerator {
      private final Logger log = LoggerFactory.getLogger(X509KeyInfoGenerator.class);
      private X509Options options;
      private final XMLObjectBuilder keyInfoBuilder;
      private final XMLObjectBuilder x509DataBuilder;

      protected X509KeyInfoGenerator(X509Options newOptions) {
         super(newOptions);
         this.options = newOptions;
         this.keyInfoBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(KeyInfo.DEFAULT_ELEMENT_NAME);
         this.x509DataBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(X509Data.DEFAULT_ELEMENT_NAME);
      }

      @Nullable
      public KeyInfo generate(@Nullable Credential credential) throws SecurityException {
         if (credential == null) {
            this.log.warn("X509KeyInfoGenerator was passed a null credential");
            return null;
         } else if (!(credential instanceof X509Credential)) {
            this.log.warn("X509KeyInfoGenerator was passed a credential that was not an instance of X509Credential: {}", credential.getClass().getName());
            return null;
         } else {
            X509Credential x509Credential = (X509Credential)credential;
            KeyInfo keyInfo = super.generate(credential);
            if (keyInfo == null) {
               keyInfo = (KeyInfo)this.keyInfoBuilder.buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
            }

            X509Data x509Data = (X509Data)this.x509DataBuilder.buildObject(X509Data.DEFAULT_ELEMENT_NAME);
            this.processEntityCertificate(keyInfo, x509Data, x509Credential);
            this.processEntityCertificateChain(keyInfo, x509Data, x509Credential);
            this.processCRLs(keyInfo, x509Data, x509Credential);
            List x509DataChildren = x509Data.getOrderedChildren();
            if (x509DataChildren != null && x509DataChildren.size() > 0) {
               keyInfo.getX509Datas().add(x509Data);
            }

            List keyInfoChildren = keyInfo.getOrderedChildren();
            return keyInfoChildren != null && keyInfoChildren.size() > 0 ? keyInfo : null;
         }
      }

      protected void processEntityCertificate(@Nonnull KeyInfo keyInfo, @Nonnull X509Data x509Data, @Nonnull X509Credential credential) throws SecurityException {
         if (credential.getEntityCertificate() != null) {
            X509Certificate javaCert = credential.getEntityCertificate();
            this.processCertX509DataOptions(x509Data, javaCert);
            this.processCertKeyNameOptions(keyInfo, javaCert);
            if (this.options.emitEntityCertificate && !this.options.emitEntityCertificateChain) {
               try {
                  org.opensaml.xmlsec.signature.X509Certificate xmlCert = KeyInfoSupport.buildX509Certificate(javaCert);
                  x509Data.getX509Certificates().add(xmlCert);
               } catch (CertificateEncodingException var6) {
                  throw new SecurityException("Error generating X509Certificate element from credential's end-entity certificate", var6);
               }
            }

         }
      }

      protected void processCertX509DataOptions(@Nonnull X509Data x509Data, @Nonnull X509Certificate cert) throws SecurityException {
         this.processCertX509SubjectName(x509Data, cert);
         this.processCertX509IssuerSerial(x509Data, cert);
         this.processCertX509SKI(x509Data, cert);
         this.processCertX509Digest(x509Data, cert);
      }

      protected void processCertKeyNameOptions(@Nonnull KeyInfo keyInfo, @Nonnull X509Certificate cert) {
         this.processSubjectDNKeyName(keyInfo, cert);
         this.processSubjectCNKeyName(keyInfo, cert);
         this.processSubjectAltNameKeyNames(keyInfo, cert);
      }

      protected void processCertX509SubjectName(@Nonnull X509Data x509Data, @Nonnull X509Certificate cert) {
         if (this.options.emitX509SubjectName) {
            String subjectNameValue = this.getSubjectName(cert);
            if (!Strings.isNullOrEmpty(subjectNameValue)) {
               x509Data.getX509SubjectNames().add(KeyInfoSupport.buildX509SubjectName(subjectNameValue));
            }
         }

      }

      protected void processCertX509IssuerSerial(@Nonnull X509Data x509Data, @Nonnull X509Certificate cert) {
         if (this.options.emitX509IssuerSerial) {
            String issuerNameValue = this.getIssuerName(cert);
            if (!Strings.isNullOrEmpty(issuerNameValue)) {
               x509Data.getX509IssuerSerials().add(KeyInfoSupport.buildX509IssuerSerial(issuerNameValue, cert.getSerialNumber()));
            }
         }

      }

      protected void processCertX509SKI(@Nonnull X509Data x509Data, @Nonnull X509Certificate cert) {
         if (this.options.emitX509SKI) {
            X509SKI xmlSKI = KeyInfoSupport.buildX509SKI(cert);
            if (xmlSKI != null) {
               x509Data.getX509SKIs().add(xmlSKI);
            }
         }

      }

      protected void processCertX509Digest(@Nonnull X509Data x509Data, @Nonnull X509Certificate cert) throws SecurityException {
         if (this.options.emitX509Digest) {
            try {
               x509Data.getX509Digests().add(KeyInfoSupport.buildX509Digest(cert, this.options.x509DigestAlgorithmURI));
            } catch (CertificateEncodingException var4) {
               throw new SecurityException("Can't digest certificate, certificate encoding error", var4);
            } catch (NoSuchAlgorithmException var5) {
               throw new SecurityException("Can't digest certificate, unsupported digest algorithm", var5);
            }
         }

      }

      @Nullable
      protected String getSubjectName(@Nullable X509Certificate cert) {
         if (cert == null) {
            return null;
         } else {
            return !Strings.isNullOrEmpty(this.options.x500SubjectDNFormat) ? this.options.x500DNHandler.getName(cert.getSubjectX500Principal(), this.options.x500SubjectDNFormat) : this.options.x500DNHandler.getName(cert.getSubjectX500Principal());
         }
      }

      protected String getIssuerName(@Nullable X509Certificate cert) {
         if (cert == null) {
            return null;
         } else {
            return !Strings.isNullOrEmpty(this.options.x500IssuerDNFormat) ? this.options.x500DNHandler.getName(cert.getIssuerX500Principal(), this.options.x500IssuerDNFormat) : this.options.x500DNHandler.getName(cert.getIssuerX500Principal());
         }
      }

      protected void processSubjectDNKeyName(@Nonnull KeyInfo keyInfo, @Nullable X509Certificate cert) {
         if (this.options.emitSubjectDNAsKeyName) {
            String subjectNameValue = this.getSubjectName(cert);
            if (!Strings.isNullOrEmpty(subjectNameValue)) {
               KeyInfoSupport.addKeyName(keyInfo, subjectNameValue);
            }
         }

      }

      protected void processSubjectCNKeyName(@Nonnull KeyInfo keyInfo, @Nullable X509Certificate cert) {
         if (this.options.emitSubjectCNAsKeyName) {
            List cnames = X509Support.getCommonNames(cert.getSubjectX500Principal());
            if (cnames != null) {
               Iterator var4 = cnames.iterator();

               while(var4.hasNext()) {
                  String name = (String)var4.next();
                  if (!Strings.isNullOrEmpty(name)) {
                     KeyInfoSupport.addKeyName(keyInfo, name);
                  }
               }
            }
         }

      }

      protected void processSubjectAltNameKeyNames(@Nonnull KeyInfo keyInfo, @Nullable X509Certificate cert) {
         if (this.options.emitSubjectAltNamesAsKeyNames && this.options.subjectAltNames.size() > 0) {
            Integer[] nameTypes = new Integer[this.options.subjectAltNames.size()];
            this.options.subjectAltNames.toArray(nameTypes);
            List altnames = X509Support.getAltNames(cert, nameTypes);
            if (altnames != null) {
               Iterator var5 = altnames.iterator();

               while(var5.hasNext()) {
                  Object altNameValue = var5.next();
                  if (altNameValue instanceof String) {
                     KeyInfoSupport.addKeyName(keyInfo, (String)altNameValue);
                  } else if (altNameValue instanceof byte[]) {
                     this.log.warn("Certificate contained an alt name value as a DER-encoded byte[] (not supported)");
                  } else {
                     this.log.warn("Certificate contained an alt name value with an unexpected type: {}", altNameValue.getClass().getName());
                  }
               }
            }
         }

      }

      protected void processEntityCertificateChain(@Nonnull KeyInfo keyInfo, @Nonnull X509Data x509Data, @Nonnull X509Credential credential) throws SecurityException {
         if (this.options.emitEntityCertificateChain) {
            Iterator var4 = credential.getEntityCertificateChain().iterator();

            while(var4.hasNext()) {
               X509Certificate javaCert = (X509Certificate)var4.next();

               try {
                  org.opensaml.xmlsec.signature.X509Certificate xmlCert = KeyInfoSupport.buildX509Certificate(javaCert);
                  x509Data.getX509Certificates().add(xmlCert);
               } catch (CertificateEncodingException var7) {
                  throw new SecurityException("Error generating X509Certificate element from a certificate in credential's certificate chain", var7);
               }
            }
         }

      }

      protected void processCRLs(@Nonnull KeyInfo keyInfo, @Nonnull X509Data x509Data, @Nonnull X509Credential credential) throws SecurityException {
         if (this.options.emitCRLs && credential.getCRLs() != null) {
            Iterator var4 = credential.getCRLs().iterator();

            while(var4.hasNext()) {
               X509CRL javaCRL = (X509CRL)var4.next();

               try {
                  org.opensaml.xmlsec.signature.X509CRL xmlCRL = KeyInfoSupport.buildX509CRL(javaCRL);
                  x509Data.getX509CRLs().add(xmlCRL);
               } catch (CRLException var7) {
                  throw new SecurityException("Error generating X509CRL element from a CRL in credential's CRL list", var7);
               }
            }
         }

      }
   }
}
