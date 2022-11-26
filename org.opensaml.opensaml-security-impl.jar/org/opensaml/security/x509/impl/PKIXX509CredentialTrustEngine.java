package org.opensaml.security.x509.impl;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.x509.PKIXTrustEngine;
import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.PKIXValidationInformation;
import org.opensaml.security.x509.PKIXValidationInformationResolver;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PKIXX509CredentialTrustEngine implements PKIXTrustEngine {
   private final Logger log = LoggerFactory.getLogger(PKIXX509CredentialTrustEngine.class);
   private final PKIXValidationInformationResolver pkixResolver;
   private final PKIXTrustEvaluator pkixTrustEvaluator;
   private final X509CredentialNameEvaluator credNameEvaluator;

   public PKIXX509CredentialTrustEngine(@Nonnull PKIXValidationInformationResolver resolver) {
      this.pkixResolver = (PKIXValidationInformationResolver)Constraint.isNotNull(resolver, "PKIX trust information resolver cannot be null");
      this.pkixTrustEvaluator = new CertPathPKIXTrustEvaluator();
      this.credNameEvaluator = new BasicX509CredentialNameEvaluator();
   }

   public PKIXX509CredentialTrustEngine(@Nonnull PKIXValidationInformationResolver resolver, @Nonnull PKIXTrustEvaluator pkixEvaluator, @Nullable X509CredentialNameEvaluator nameEvaluator) {
      this.pkixResolver = (PKIXValidationInformationResolver)Constraint.isNotNull(resolver, "PKIX trust information resolver cannot be null");
      this.pkixTrustEvaluator = (PKIXTrustEvaluator)Constraint.isNotNull(pkixEvaluator, "PKIX trust evaluator may not be null");
      this.credNameEvaluator = nameEvaluator;
   }

   @Nonnull
   public PKIXValidationInformationResolver getPKIXResolver() {
      return this.pkixResolver;
   }

   @Nonnull
   public PKIXTrustEvaluator getPKIXTrustEvaluator() {
      return this.pkixTrustEvaluator;
   }

   @Nullable
   public X509CredentialNameEvaluator getX509CredentialNameEvaluator() {
      return this.credNameEvaluator;
   }

   public boolean validate(@Nonnull X509Credential untrustedCredential, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
      this.log.debug("Attempting PKIX validation of untrusted credential");
      if (untrustedCredential == null) {
         this.log.error("X.509 credential was null, unable to perform validation");
         return false;
      } else if (untrustedCredential.getEntityCertificate() == null) {
         this.log.error("Untrusted X.509 credential's entity certificate was null, unable to perform validation");
         return false;
      } else {
         Set trustedNames = null;
         if (this.getPKIXResolver().supportsTrustedNameResolution()) {
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

         try {
            return this.validate(untrustedCredential, trustedNames, this.pkixResolver.resolve(trustBasisCriteria));
         } catch (ResolverException var5) {
            throw new SecurityException("Error resolving trusted credentials", var5);
         }
      }
   }

   protected boolean validate(@Nonnull X509Credential untrustedX509Credential, @Nullable Set trustedNames, @Nonnull Iterable validationInfoSet) throws SecurityException {
      this.log.debug("Beginning PKIX validation using trusted validation information");
      if (!this.checkNames(trustedNames, untrustedX509Credential)) {
         this.log.debug("Evaluation of credential against trusted names failed. Aborting PKIX validation");
         return false;
      } else {
         Iterator var4 = validationInfoSet.iterator();

         while(var4.hasNext()) {
            PKIXValidationInformation validationInfo = (PKIXValidationInformation)var4.next();

            try {
               if (this.getPKIXTrustEvaluator().validate(validationInfo, untrustedX509Credential)) {
                  this.log.debug("Credential trust established via PKIX validation");
                  return true;
               }
            } catch (SecurityException var7) {
               this.log.debug("Error performing PKIX validation on untrusted credential", var7);
            }
         }

         this.log.debug("Trust of untrusted credential could not be established via PKIX validation");
         return false;
      }
   }

   protected boolean checkNames(@Nullable Set trustedNames, @Nonnull X509Credential untrustedCredential) throws SecurityException {
      if (this.getX509CredentialNameEvaluator() == null) {
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
