package org.opensaml.xmlsec.signature.support.impl;

import com.google.common.base.Strings;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.KeyAlgorithmCriterion;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.trust.TrustedCredentialTrustEngine;
import org.opensaml.security.trust.impl.ExplicitKeyTrustEvaluator;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.crypto.XMLSigningUtil;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplicitKeySignatureTrustEngine extends BaseSignatureTrustEngine implements TrustedCredentialTrustEngine {
   private final Logger log = LoggerFactory.getLogger(ExplicitKeySignatureTrustEngine.class);
   private final CredentialResolver credentialResolver;
   private final ExplicitKeyTrustEvaluator keyTrust;

   public ExplicitKeySignatureTrustEngine(@Nonnull @ParameterName(name = "resolver") CredentialResolver resolver, @Nonnull @ParameterName(name = "keyInfoResolver") KeyInfoCredentialResolver keyInfoResolver) {
      super(keyInfoResolver);
      this.credentialResolver = (CredentialResolver)Constraint.isNotNull(resolver, "Credential resolver cannot be null");
      this.keyTrust = new ExplicitKeyTrustEvaluator();
   }

   @Nonnull
   public CredentialResolver getCredentialResolver() {
      return this.credentialResolver;
   }

   protected boolean doValidate(@Nonnull Signature signature, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
      CriteriaSet criteriaSet = new CriteriaSet();
      criteriaSet.addAll(trustBasisCriteria);
      if (!criteriaSet.contains(UsageCriterion.class)) {
         criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
      }

      String jcaAlgorithm = AlgorithmSupport.getKeyAlgorithm(signature.getSignatureAlgorithm());
      if (!Strings.isNullOrEmpty(jcaAlgorithm)) {
         criteriaSet.add(new KeyAlgorithmCriterion(jcaAlgorithm), true);
      }

      Iterable trustedCredentials;
      try {
         trustedCredentials = this.getCredentialResolver().resolve(criteriaSet);
      } catch (ResolverException var8) {
         throw new SecurityException("Error resolving trusted credentials", var8);
      }

      if (this.validate(signature, trustedCredentials)) {
         return true;
      } else {
         this.log.debug("Attempting to verify signature using trusted credentials");
         Iterator var6 = trustedCredentials.iterator();

         Credential trustedCredential;
         do {
            if (!var6.hasNext()) {
               this.log.debug("Failed to verify signature using either KeyInfo-derived or directly trusted credentials");
               return false;
            }

            trustedCredential = (Credential)var6.next();
         } while(!this.verifySignature(signature, trustedCredential));

         this.log.debug("Successfully verified signature using resolved trusted credential");
         return true;
      }
   }

   protected boolean doValidate(@Nonnull byte[] signature, @Nonnull byte[] content, @Nonnull String algorithmURI, @Nullable CriteriaSet trustBasisCriteria, @Nullable Credential candidateCredential) throws SecurityException {
      CriteriaSet criteriaSet = new CriteriaSet();
      criteriaSet.addAll(trustBasisCriteria);
      if (!criteriaSet.contains(UsageCriterion.class)) {
         criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
      }

      String jcaAlgorithm = AlgorithmSupport.getKeyAlgorithm(algorithmURI);
      if (!Strings.isNullOrEmpty(jcaAlgorithm)) {
         criteriaSet.add(new KeyAlgorithmCriterion(jcaAlgorithm), true);
      }

      Iterable trustedCredentials;
      try {
         trustedCredentials = this.getCredentialResolver().resolve(criteriaSet);
      } catch (ResolverException var14) {
         throw new SecurityException("Error resolving trusted credentials", var14);
      }

      if (candidateCredential != null) {
         this.log.debug("Attempting to verify raw signature using supplied candidate credential");

         try {
            if (XMLSigningUtil.verifyWithURI(candidateCredential, algorithmURI, signature, content)) {
               this.log.debug("Successfully verified signature using supplied candidate credential");
               this.log.debug("Attempting to establish trust of supplied candidate credential");
               if (this.evaluateTrust(candidateCredential, trustedCredentials)) {
                  this.log.debug("Successfully established trust of supplied candidate credential");
                  return true;
               }

               this.log.debug("Failed to establish trust of supplied candidate credential");
            }
         } catch (SecurityException var13) {
            this.log.debug("Saw fatal error attempting to verify raw signature with supplied candidate credential", var13);
         }
      }

      this.log.debug("Attempting to verify signature using trusted credentials");
      Iterator var9 = trustedCredentials.iterator();

      while(var9.hasNext()) {
         Credential trustedCredential = (Credential)var9.next();

         try {
            if (XMLSigningUtil.verifyWithURI(trustedCredential, algorithmURI, signature, content)) {
               this.log.debug("Successfully verified signature using resolved trusted credential");
               return true;
            }
         } catch (SecurityException var12) {
            this.log.debug("Saw fatal error attempting to verify raw signature with trusted credential", var12);
         }
      }

      this.log.debug("Failed to verify signature using either supplied candidate credential or directly trusted credentials");
      return false;
   }

   protected boolean evaluateTrust(@Nonnull Credential untrustedCredential, @Nullable Iterable trustedCredentials) throws SecurityException {
      return this.keyTrust.validate(untrustedCredential, trustedCredentials);
   }
}
