package org.opensaml.saml.security.impl;

import com.google.common.base.Predicate;
import java.security.Key;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.crypto.KeySupport;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.KeyTransportAlgorithmPredicate;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.encryption.MGF;
import org.opensaml.xmlsec.encryption.OAEPparams;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.impl.BasicEncryptionParametersResolver;
import org.opensaml.xmlsec.signature.DigestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLMetadataEncryptionParametersResolver extends BasicEncryptionParametersResolver {
   private Logger log = LoggerFactory.getLogger(SAMLMetadataEncryptionParametersResolver.class);
   private MetadataCredentialResolver credentialResolver;
   private boolean mergeMetadataRSAOAEPParametersWithConfig;

   public SAMLMetadataEncryptionParametersResolver(@Nonnull @ParameterName(name = "resolver") MetadataCredentialResolver resolver) {
      this.credentialResolver = (MetadataCredentialResolver)Constraint.isNotNull(resolver, "MetadataCredentialResoler may not be null");
   }

   public boolean isMergeMetadataRSAOAEPParametersWithConfig() {
      return this.mergeMetadataRSAOAEPParametersWithConfig;
   }

   public void setMergeMetadataRSAOAEPParametersWithConfig(boolean flag) {
      this.mergeMetadataRSAOAEPParametersWithConfig = flag;
   }

   @Nonnull
   protected MetadataCredentialResolver getMetadataCredentialResolver() {
      return this.credentialResolver;
   }

   protected void resolveAndPopulateCredentialsAndAlgorithms(@Nonnull EncryptionParameters params, @Nonnull CriteriaSet criteria, @Nonnull Predicate whitelistBlacklistPredicate) {
      CriteriaSet mdCredResolverCriteria = new CriteriaSet();
      mdCredResolverCriteria.addAll(criteria);
      mdCredResolverCriteria.add(new UsageCriterion(UsageType.ENCRYPTION), true);

      try {
         Iterator var5 = this.getMetadataCredentialResolver().resolve(mdCredResolverCriteria).iterator();

         while(var5.hasNext()) {
            Credential keyTransportCredential = (Credential)var5.next();
            if (this.log.isTraceEnabled()) {
               Key key = CredentialSupport.extractEncryptionKey(keyTransportCredential);
               this.log.trace("Evaluating key transport encryption credential from SAML metadata of type: {}", key != null ? key.getAlgorithm() : "n/a");
            }

            SAMLMDCredentialContext metadataCredContext = (SAMLMDCredentialContext)keyTransportCredential.getCredentialContextSet().get(SAMLMDCredentialContext.class);
            Pair dataEncryptionAlgorithmAndMethod = this.resolveDataEncryptionAlgorithm(criteria, whitelistBlacklistPredicate, metadataCredContext);
            Pair keyTransportAlgorithmAndMethod = this.resolveKeyTransportAlgorithm(keyTransportCredential, criteria, whitelistBlacklistPredicate, (String)dataEncryptionAlgorithmAndMethod.getFirst(), metadataCredContext);
            if (keyTransportAlgorithmAndMethod.getFirst() != null) {
               params.setKeyTransportEncryptionCredential(keyTransportCredential);
               params.setKeyTransportEncryptionAlgorithm((String)keyTransportAlgorithmAndMethod.getFirst());
               params.setDataEncryptionAlgorithm((String)dataEncryptionAlgorithmAndMethod.getFirst());
               this.resolveAndPopulateRSAOAEPParams(params, criteria, whitelistBlacklistPredicate, (EncryptionMethod)keyTransportAlgorithmAndMethod.getSecond());
               this.processDataEncryptionCredentialAutoGeneration(params);
               return;
            }

            this.log.debug("Unable to resolve key transport algorithm for credential with key type '{}', considering other credentials", CredentialSupport.extractEncryptionKey(keyTransportCredential).getAlgorithm());
         }
      } catch (ResolverException var10) {
         this.log.warn("Problem resolving credentials from metadata, falling back to local configuration", var10);
      }

      this.log.debug("Could not resolve encryption parameters based on SAML metadata, falling back to locally configured credentials and algorithms");
      super.resolveAndPopulateCredentialsAndAlgorithms(params, criteria, whitelistBlacklistPredicate);
   }

   protected void resolveAndPopulateRSAOAEPParams(@Nonnull EncryptionParameters params, @Nonnull CriteriaSet criteria, @Nonnull Predicate whitelistBlacklistPredicate, @Nullable EncryptionMethod encryptionMethod) {
      if (AlgorithmSupport.isRSAOAEP(params.getKeyTransportEncryptionAlgorithm())) {
         if (encryptionMethod == null) {
            super.resolveAndPopulateRSAOAEPParams(params, criteria, whitelistBlacklistPredicate);
         } else {
            if (params.getRSAOAEPParameters() == null) {
               params.setRSAOAEPParameters(new RSAOAEPParameters());
            }

            this.populateRSAOAEPParamsFromEncryptionMethod(params.getRSAOAEPParameters(), encryptionMethod, whitelistBlacklistPredicate);
            if (!params.getRSAOAEPParameters().isComplete()) {
               if (params.getRSAOAEPParameters().isEmpty()) {
                  super.resolveAndPopulateRSAOAEPParams(params, criteria, whitelistBlacklistPredicate);
               } else if (this.isMergeMetadataRSAOAEPParametersWithConfig()) {
                  super.resolveAndPopulateRSAOAEPParams(params, criteria, whitelistBlacklistPredicate);
               }

            }
         }
      }
   }

   protected void populateRSAOAEPParamsFromEncryptionMethod(@Nonnull RSAOAEPParameters params, @Nonnull EncryptionMethod encryptionMethod, @Nonnull Predicate whitelistBlacklistPredicate) {
      Predicate algoSupportPredicate = this.getAlgorithmRuntimeSupportedPredicate();
      List digestMethods = encryptionMethod.getUnknownXMLObjects(DigestMethod.DEFAULT_ELEMENT_NAME);
      String value;
      if (digestMethods.size() > 0) {
         DigestMethod digestMethod = (DigestMethod)digestMethods.get(0);
         value = StringSupport.trimOrNull(digestMethod.getAlgorithm());
         if (value != null && whitelistBlacklistPredicate.apply(value) && algoSupportPredicate.apply(value)) {
            params.setDigestMethod(value);
         }
      }

      if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionMethod.getAlgorithm())) {
         List mgfs = encryptionMethod.getUnknownXMLObjects(MGF.DEFAULT_ELEMENT_NAME);
         if (mgfs.size() > 0) {
            MGF mgf = (MGF)mgfs.get(0);
            String mgfAlgorithm = StringSupport.trimOrNull(mgf.getAlgorithm());
            if (mgfAlgorithm != null && whitelistBlacklistPredicate.apply(mgfAlgorithm)) {
               params.setMaskGenerationFunction(mgfAlgorithm);
            }
         }
      }

      OAEPparams oaepParams = encryptionMethod.getOAEPparams();
      if (oaepParams != null) {
         value = StringSupport.trimOrNull(oaepParams.getValue());
         if (value != null) {
            params.setOAEPparams(value);
         }
      }

   }

   @Nonnull
   protected Pair resolveKeyTransportAlgorithm(@Nonnull Credential keyTransportCredential, @Nonnull CriteriaSet criteria, @Nonnull Predicate whitelistBlacklistPredicate, @Nullable String dataEncryptionAlgorithm, @Nullable SAMLMDCredentialContext metadataCredContext) {
      if (metadataCredContext != null) {
         KeyTransportAlgorithmPredicate keyTransportPredicate = this.resolveKeyTransportAlgorithmPredicate(criteria);
         Iterator var7 = metadataCredContext.getEncryptionMethods().iterator();

         while(var7.hasNext()) {
            EncryptionMethod encryptionMethod = (EncryptionMethod)var7.next();
            String algorithm = encryptionMethod.getAlgorithm();
            this.log.trace("Evaluating SAML metadata EncryptionMethod algorithm for key transport: {}", algorithm);
            if (this.isKeyTransportAlgorithm(algorithm) && whitelistBlacklistPredicate.apply(algorithm) && this.getAlgorithmRuntimeSupportedPredicate().apply(algorithm) && this.credentialSupportsEncryptionMethod(keyTransportCredential, encryptionMethod) && this.evaluateEncryptionMethodChildren(encryptionMethod, criteria, whitelistBlacklistPredicate)) {
               boolean accepted = true;
               if (keyTransportPredicate != null) {
                  accepted = keyTransportPredicate.apply(new KeyTransportAlgorithmPredicate.SelectionInput(algorithm, dataEncryptionAlgorithm, keyTransportCredential));
               }

               if (accepted) {
                  this.log.debug("Resolved key transport algorithm URI from SAML metadata EncryptionMethod: {}", algorithm);
                  return new Pair(algorithm, encryptionMethod);
               }
            }
         }
      }

      this.log.debug("Could not resolve key transport algorithm based on SAML metadata, falling back to locally configured algorithms");
      return new Pair(super.resolveKeyTransportAlgorithm(keyTransportCredential, criteria, whitelistBlacklistPredicate, dataEncryptionAlgorithm), (Object)null);
   }

   @Nonnull
   protected Pair resolveDataEncryptionAlgorithm(@Nonnull CriteriaSet criteria, @Nonnull Predicate whitelistBlacklistPredicate, @Nullable SAMLMDCredentialContext metadataCredContext) {
      if (metadataCredContext != null) {
         Iterator var4 = metadataCredContext.getEncryptionMethods().iterator();

         while(var4.hasNext()) {
            EncryptionMethod encryptionMethod = (EncryptionMethod)var4.next();
            String algorithm = encryptionMethod.getAlgorithm();
            this.log.trace("Evaluating SAML metadata EncryptionMethod algorithm for data encryption: {}", algorithm);
            if (this.isDataEncryptionAlgorithm(algorithm) && whitelistBlacklistPredicate.apply(algorithm) && this.getAlgorithmRuntimeSupportedPredicate().apply(algorithm) && this.evaluateEncryptionMethodChildren(encryptionMethod, criteria, whitelistBlacklistPredicate)) {
               this.log.debug("Resolved data encryption algorithm URI from SAML metadata EncryptionMethod: {}", algorithm);
               return new Pair(algorithm, encryptionMethod);
            }
         }
      }

      this.log.debug("Could not resolve data encryption algorithm based on SAML metadata, falling back to locally configured algorithms");
      return new Pair(super.resolveDataEncryptionAlgorithm((Credential)null, criteria, whitelistBlacklistPredicate), (Object)null);
   }

   protected boolean evaluateEncryptionMethodChildren(@Nonnull EncryptionMethod encryptionMethod, @Nonnull CriteriaSet criteria, @Nonnull Predicate whitelistBlacklistPredicate) {
      switch (encryptionMethod.getAlgorithm()) {
         case "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p":
         case "http://www.w3.org/2009/xmlenc11#rsa-oaep":
            return this.evaluateRSAOAEPChildren(encryptionMethod, criteria, whitelistBlacklistPredicate);
         default:
            return true;
      }
   }

   protected boolean evaluateRSAOAEPChildren(@Nonnull EncryptionMethod encryptionMethod, @Nonnull CriteriaSet criteria, @Nonnull Predicate whitelistBlacklistPredicate) {
      Predicate algoSupportPredicate = this.getAlgorithmRuntimeSupportedPredicate();
      List digestMethods = encryptionMethod.getUnknownXMLObjects(DigestMethod.DEFAULT_ELEMENT_NAME);
      if (digestMethods.size() > 0) {
         DigestMethod digestMethod = (DigestMethod)digestMethods.get(0);
         String digestAlgorithm = StringSupport.trimOrNull(digestMethod.getAlgorithm());
         if (digestAlgorithm != null && (!whitelistBlacklistPredicate.apply(digestAlgorithm) || !algoSupportPredicate.apply(digestAlgorithm))) {
            this.log.debug("Rejecting RSA OAEP EncryptionMethod due to unsupported or disallowed DigestMethod: {}", digestAlgorithm);
            return false;
         }
      }

      if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionMethod.getAlgorithm())) {
         List mgfs = encryptionMethod.getUnknownXMLObjects(MGF.DEFAULT_ELEMENT_NAME);
         if (mgfs.size() > 0) {
            MGF mgf = (MGF)mgfs.get(0);
            String mgfAlgorithm = StringSupport.trimOrNull(mgf.getAlgorithm());
            if (mgfAlgorithm != null && !whitelistBlacklistPredicate.apply(mgfAlgorithm)) {
               this.log.debug("Rejecting RSA OAEP EncryptionMethod due to disallowed MGF: {}", mgfAlgorithm);
               return false;
            }
         }
      }

      return true;
   }

   protected boolean credentialSupportsEncryptionMethod(@Nonnull Credential credential, @Nonnull @NotEmpty EncryptionMethod encryptionMethod) {
      if (!this.credentialSupportsAlgorithm(credential, encryptionMethod.getAlgorithm())) {
         return false;
      } else {
         if (encryptionMethod.getKeySize() != null && encryptionMethod.getKeySize().getValue() != null) {
            Key encryptionKey = CredentialSupport.extractEncryptionKey(credential);
            if (encryptionKey == null) {
               this.log.warn("Could not extract encryption key from credential. Failing evaluation");
               return false;
            }

            Integer keyLength = KeySupport.getKeyLength(encryptionKey);
            if (keyLength == null) {
               this.log.warn("Could not determine key length of candidate encryption credential. Failing evaluation");
               return false;
            }

            if (!keyLength.equals(encryptionMethod.getKeySize().getValue())) {
               return false;
            }
         }

         return true;
      }
   }
}
