package org.opensaml.xmlsec.signature.support.impl;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.x509.PKIXTrustEngine;
import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.PKIXValidationInformation;
import org.opensaml.security.x509.PKIXValidationInformationResolver;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.impl.BasicX509CredentialNameEvaluator;
import org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator;
import org.opensaml.security.x509.impl.X509CredentialNameEvaluator;
import org.opensaml.xmlsec.crypto.XMLSigningUtil;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PKIXSignatureTrustEngine extends BaseSignatureTrustEngine implements PKIXTrustEngine {
   private final Logger log = LoggerFactory.getLogger(PKIXSignatureTrustEngine.class);
   private final PKIXValidationInformationResolver pkixResolver;
   private final PKIXTrustEvaluator pkixTrustEvaluator;
   private final X509CredentialNameEvaluator credNameEvaluator;

   public PKIXSignatureTrustEngine(@Nonnull @ParameterName(name = "resolver") PKIXValidationInformationResolver resolver, @Nonnull @ParameterName(name = "keyInfoResolver") KeyInfoCredentialResolver keyInfoResolver) {
      super(keyInfoResolver);
      this.pkixResolver = (PKIXValidationInformationResolver)Constraint.isNotNull(resolver, "PKIX trust information resolver cannot be null");
      this.pkixTrustEvaluator = new CertPathPKIXTrustEvaluator();
      this.credNameEvaluator = new BasicX509CredentialNameEvaluator();
   }

   public PKIXSignatureTrustEngine(@Nonnull @ParameterName(name = "resolver") PKIXValidationInformationResolver resolver, @Nonnull @ParameterName(name = "keyInfoResolver") KeyInfoCredentialResolver keyInfoResolver, @Nonnull @ParameterName(name = "pkixEvaluator") PKIXTrustEvaluator pkixEvaluator, @Nullable @ParameterName(name = "nameEvaluator") X509CredentialNameEvaluator nameEvaluator) {
      super(keyInfoResolver);
      this.pkixResolver = (PKIXValidationInformationResolver)Constraint.isNotNull(resolver, "PKIX trust information resolver cannot be null");
      this.pkixTrustEvaluator = (PKIXTrustEvaluator)Constraint.isNotNull(pkixEvaluator, "PKIX trust evaluator cannot be null");
      this.credNameEvaluator = nameEvaluator;
   }

   @Nonnull
   public PKIXTrustEvaluator getPKIXTrustEvaluator() {
      return this.pkixTrustEvaluator;
   }

   @Nullable
   public X509CredentialNameEvaluator getX509CredentialNameEvaluator() {
      return this.credNameEvaluator;
   }

   @Nonnull
   public PKIXValidationInformationResolver getPKIXResolver() {
      return this.pkixResolver;
   }

   protected boolean doValidate(@Nonnull Signature signature, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
      Pair validationPair = this.resolveValidationInfo(trustBasisCriteria);
      if (this.validate(signature, validationPair)) {
         return true;
      } else {
         this.log.debug("PKIX validation of signature failed, unable to resolve valid and trusted signing key");
         return false;
      }
   }

   protected boolean doValidate(@Nonnull byte[] signature, @Nonnull byte[] content, @Nonnull String algorithmURI, @Nullable CriteriaSet trustBasisCriteria, @Nullable Credential candidateCredential) throws SecurityException {
      if (candidateCredential != null && CredentialSupport.extractVerificationKey(candidateCredential) != null) {
         Pair validationPair = this.resolveValidationInfo(trustBasisCriteria);

         try {
            if (XMLSigningUtil.verifyWithURI(candidateCredential, algorithmURI, signature, content)) {
               this.log.debug("Successfully verified raw signature using supplied candidate credential");
               this.log.debug("Attempting to establish trust of supplied candidate credential");
               if (this.evaluateTrust(candidateCredential, validationPair)) {
                  this.log.debug("Successfully established trust of supplied candidate credential");
                  return true;
               }

               this.log.debug("Failed to establish trust of supplied candidate credential");
            } else {
               this.log.debug("Cryptographic verification of raw signature failed with candidate credential");
            }
         } catch (SecurityException var8) {
         }

         this.log.debug("PKIX validation of raw signature failed, unable to establish trust of supplied verification credential");
         return false;
      } else {
         this.log.debug("Candidate credential was either not supplied or did not contain verification key");
         this.log.debug("PKIX trust engine requires supplied key, skipping PKIX trust evaluation");
         return false;
      }
   }

   protected boolean evaluateTrust(@Nonnull Credential untrustedCredential, @Nullable Pair validationPair) throws SecurityException {
      if (!(untrustedCredential instanceof X509Credential)) {
         this.log.debug("Can not evaluate trust of non-X509Credential");
         return false;
      } else {
         X509Credential untrustedX509Credential = (X509Credential)untrustedCredential;
         Set trustedNames = (Set)validationPair.getFirst();
         Iterable validationInfoSet = (Iterable)validationPair.getSecond();
         if (validationInfoSet == null) {
            this.log.debug("PKIX validation information not available. Aborting PKIX validation");
            return false;
         } else if (!this.checkNames(trustedNames, untrustedX509Credential)) {
            this.log.debug("Evaluation of credential against trusted names failed. Aborting PKIX validation");
            return false;
         } else {
            Iterator var6 = validationInfoSet.iterator();

            while(var6.hasNext()) {
               PKIXValidationInformation validationInfo = (PKIXValidationInformation)var6.next();

               try {
                  if (this.pkixTrustEvaluator.validate(validationInfo, untrustedX509Credential)) {
                     this.log.debug("Signature trust established via PKIX validation of signing credential");
                     return true;
                  }
               } catch (SecurityException var9) {
                  this.log.debug("Error performing PKIX validation on untrusted credential", var9);
               }
            }

            this.log.debug("Signature trust could not be established via PKIX validation of signing credential");
            return false;
         }
      }
   }

   @Nonnull
   protected Pair resolveValidationInfo(@Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
      Set trustedNames = null;
      if (this.pkixResolver.supportsTrustedNameResolution()) {
         try {
            trustedNames = this.pkixResolver.resolveTrustedNames(trustBasisCriteria);
         } catch (UnsupportedOperationException var6) {
            throw new SecurityException("Error resolving trusted names", var6);
         } catch (ResolverException var7) {
            throw new SecurityException("Error resolving trusted names", var7);
         }
      } else {
         this.log.debug("PKIX resolver does not support resolution of trusted names, skipping name checking");
      }

      Iterable validationInfoSet;
      try {
         validationInfoSet = this.pkixResolver.resolve(trustBasisCriteria);
      } catch (ResolverException var5) {
         throw new SecurityException("Error resolving trusted PKIX validation information", var5);
      }

      return new Pair(trustedNames, validationInfoSet);
   }

   protected boolean checkNames(@Nullable Set trustedNames, @Nonnull X509Credential untrustedCredential) throws SecurityException {
      if (this.credNameEvaluator == null) {
         this.log.debug("No credential name evaluator was available, skipping trusted name evaluation");
         return true;
      } else if (trustedNames == null) {
         this.log.debug("Trusted names was null, signalling PKIX resolver does not support trusted names resolution, skipping trusted name evaluation");
         return true;
      } else {
         return this.credNameEvaluator.evaluate(untrustedCredential, trustedNames);
      }
   }
}
