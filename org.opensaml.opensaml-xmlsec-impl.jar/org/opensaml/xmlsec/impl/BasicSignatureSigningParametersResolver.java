package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.SignatureSigningParametersResolver;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.criterion.KeyInfoGenerationProfileCriterion;
import org.opensaml.xmlsec.criterion.SignatureSigningConfigurationCriterion;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSignatureSigningParametersResolver extends AbstractSecurityParametersResolver implements SignatureSigningParametersResolver {
   private Logger log = LoggerFactory.getLogger(BasicSignatureSigningParametersResolver.class);
   private AlgorithmRegistry algorithmRegistry = AlgorithmSupport.getGlobalAlgorithmRegistry();

   public AlgorithmRegistry getAlgorithmRegistry() {
      return this.algorithmRegistry == null ? AlgorithmSupport.getGlobalAlgorithmRegistry() : this.algorithmRegistry;
   }

   public void setAlgorithmRegistry(@Nonnull AlgorithmRegistry registry) {
      this.algorithmRegistry = (AlgorithmRegistry)Constraint.isNotNull(registry, "AlgorithmRegistry was null");
   }

   @Nonnull
   public Iterable resolve(@Nonnull CriteriaSet criteria) throws ResolverException {
      SignatureSigningParameters params = this.resolveSingle(criteria);
      return params != null ? Collections.singletonList(params) : Collections.emptyList();
   }

   @Nullable
   public SignatureSigningParameters resolveSingle(@Nonnull CriteriaSet criteria) throws ResolverException {
      Constraint.isNotNull(criteria, "CriteriaSet was null");
      Constraint.isNotNull(criteria.get(SignatureSigningConfigurationCriterion.class), "Resolver requires an instance of SignatureSigningConfigurationCriterion");
      Predicate whitelistBlacklistPredicate = this.getWhitelistBlacklistPredicate(criteria);
      SignatureSigningParameters params = new SignatureSigningParameters();
      this.resolveAndPopulateCredentialAndSignatureAlgorithm(params, criteria, whitelistBlacklistPredicate);
      params.setSignatureReferenceDigestMethod(this.resolveReferenceDigestMethod(criteria, whitelistBlacklistPredicate));
      params.setSignatureCanonicalizationAlgorithm(this.resolveCanonicalizationAlgorithm(criteria));
      if (params.getSigningCredential() != null) {
         params.setKeyInfoGenerator(this.resolveKeyInfoGenerator(criteria, params.getSigningCredential()));
         params.setSignatureHMACOutputLength(this.resolveHMACOutputLength(criteria, params.getSigningCredential(), params.getSignatureAlgorithm()));
      }

      if (this.validate(params)) {
         this.logResult(params);
         return params;
      } else {
         return null;
      }
   }

   protected void logResult(@Nonnull SignatureSigningParameters params) {
      if (this.log.isDebugEnabled()) {
         this.log.debug("Resolved SignatureSigningParameters:");
         Key signingKey = CredentialSupport.extractSigningKey(params.getSigningCredential());
         if (signingKey != null) {
            this.log.debug("\tSigning credential with key algorithm: {}", signingKey.getAlgorithm());
         } else {
            this.log.debug("\tSigning credential: null");
         }

         this.log.debug("\tSignature algorithm URI: {}", params.getSignatureAlgorithm());
         this.log.debug("\tSignature KeyInfoGenerator: {}", params.getKeyInfoGenerator() != null ? "present" : "null");
         this.log.debug("\tReference digest method algorithm URI: {}", params.getSignatureReferenceDigestMethod());
         this.log.debug("\tCanonicalization algorithm URI: {}", params.getSignatureCanonicalizationAlgorithm());
         this.log.debug("\tHMAC output length: {}", params.getSignatureHMACOutputLength());
      }

   }

   protected boolean validate(@Nonnull SignatureSigningParameters params) {
      if (params.getSigningCredential() == null) {
         this.log.warn("Validation failure: Unable to resolve signing credential");
         return false;
      } else if (params.getSignatureAlgorithm() == null) {
         this.log.warn("Validation failure: Unable to resolve signing algorithm URI");
         return false;
      } else if (params.getSignatureCanonicalizationAlgorithm() == null) {
         this.log.warn("Validation failure: Unable to resolve signing canonicalization algorithm URI");
         return false;
      } else if (params.getSignatureReferenceDigestMethod() == null) {
         this.log.warn("Validation failure: Unable to resolve reference digest algorithm URI");
         return false;
      } else {
         return true;
      }
   }

   @Nonnull
   protected Predicate getWhitelistBlacklistPredicate(@Nonnull CriteriaSet criteria) {
      return this.resolveWhitelistBlacklistPredicate(criteria, ((SignatureSigningConfigurationCriterion)criteria.get(SignatureSigningConfigurationCriterion.class)).getConfigurations());
   }

   protected void resolveAndPopulateCredentialAndSignatureAlgorithm(@Nonnull SignatureSigningParameters params, @Nonnull CriteriaSet criteria, Predicate whitelistBlacklistPredicate) {
      List credentials = this.getEffectiveSigningCredentials(criteria);
      List algorithms = this.getEffectiveSignatureAlgorithms(criteria, whitelistBlacklistPredicate);
      this.log.trace("Resolved effective signature algorithms: {}", algorithms);
      Iterator var6 = credentials.iterator();

      while(var6.hasNext()) {
         Credential credential = (Credential)var6.next();
         if (this.log.isTraceEnabled()) {
            Key key = CredentialSupport.extractSigningKey(credential);
            this.log.trace("Evaluating credential of type: {}", key != null ? key.getAlgorithm() : "n/a");
         }

         Iterator var10 = algorithms.iterator();

         while(var10.hasNext()) {
            String algorithm = (String)var10.next();
            this.log.trace("Evaluating credential against algorithm: {}", algorithm);
            if (this.credentialSupportsAlgorithm(credential, algorithm)) {
               this.log.trace("Credential passed eval against algorithm: {}", algorithm);
               params.setSigningCredential(credential);
               params.setSignatureAlgorithm(algorithm);
               return;
            }

            this.log.trace("Credential failed eval against algorithm: {}", algorithm);
         }
      }

   }

   @Nonnull
   protected Predicate getAlgorithmRuntimeSupportedPredicate() {
      return new AlgorithmRuntimeSupportedPredicate(this.getAlgorithmRegistry());
   }

   protected boolean credentialSupportsAlgorithm(@Nonnull Credential credential, @Nonnull @NotEmpty String algorithm) {
      return AlgorithmSupport.credentialSupportsAlgorithmForSigning(credential, this.getAlgorithmRegistry().get(algorithm));
   }

   @Nonnull
   protected List getEffectiveSigningCredentials(@Nonnull CriteriaSet criteria) {
      ArrayList accumulator = new ArrayList();
      Iterator var3 = ((SignatureSigningConfigurationCriterion)criteria.get(SignatureSigningConfigurationCriterion.class)).getConfigurations().iterator();

      while(var3.hasNext()) {
         SignatureSigningConfiguration config = (SignatureSigningConfiguration)var3.next();
         accumulator.addAll(config.getSigningCredentials());
      }

      return accumulator;
   }

   @Nonnull
   protected List getEffectiveSignatureAlgorithms(@Nonnull CriteriaSet criteria, @Nonnull Predicate whitelistBlacklistPredicate) {
      ArrayList accumulator = new ArrayList();
      Iterator var4 = ((SignatureSigningConfigurationCriterion)criteria.get(SignatureSigningConfigurationCriterion.class)).getConfigurations().iterator();

      while(var4.hasNext()) {
         SignatureSigningConfiguration config = (SignatureSigningConfiguration)var4.next();
         accumulator.addAll(Collections2.filter(config.getSignatureAlgorithms(), Predicates.and(this.getAlgorithmRuntimeSupportedPredicate(), whitelistBlacklistPredicate)));
      }

      return accumulator;
   }

   @Nullable
   protected String resolveReferenceDigestMethod(@Nonnull CriteriaSet criteria, @Nonnull Predicate whitelistBlacklistPredicate) {
      Iterator var3 = ((SignatureSigningConfigurationCriterion)criteria.get(SignatureSigningConfigurationCriterion.class)).getConfigurations().iterator();

      while(var3.hasNext()) {
         SignatureSigningConfiguration config = (SignatureSigningConfiguration)var3.next();
         Iterator var5 = config.getSignatureReferenceDigestMethods().iterator();

         while(var5.hasNext()) {
            String digestMethod = (String)var5.next();
            if (this.getAlgorithmRuntimeSupportedPredicate().apply(digestMethod) && whitelistBlacklistPredicate.apply(digestMethod)) {
               return digestMethod;
            }
         }
      }

      return null;
   }

   @Nullable
   protected String resolveCanonicalizationAlgorithm(@Nonnull CriteriaSet criteria) {
      Iterator var2 = ((SignatureSigningConfigurationCriterion)criteria.get(SignatureSigningConfigurationCriterion.class)).getConfigurations().iterator();

      SignatureSigningConfiguration config;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         config = (SignatureSigningConfiguration)var2.next();
      } while(config.getSignatureCanonicalizationAlgorithm() == null);

      return config.getSignatureCanonicalizationAlgorithm();
   }

   @Nullable
   protected KeyInfoGenerator resolveKeyInfoGenerator(@Nonnull CriteriaSet criteria, @Nonnull Credential signingCredential) {
      String name = null;
      if (criteria.get(KeyInfoGenerationProfileCriterion.class) != null) {
         name = ((KeyInfoGenerationProfileCriterion)criteria.get(KeyInfoGenerationProfileCriterion.class)).getName();
      }

      Iterator var4 = ((SignatureSigningConfigurationCriterion)criteria.get(SignatureSigningConfigurationCriterion.class)).getConfigurations().iterator();

      KeyInfoGenerator kig;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         SignatureSigningConfiguration config = (SignatureSigningConfiguration)var4.next();
         kig = this.lookupKeyInfoGenerator(signingCredential, config.getKeyInfoGeneratorManager(), name);
      } while(kig == null);

      return kig;
   }

   @Nullable
   protected Integer resolveHMACOutputLength(@Nonnull CriteriaSet criteria, @Nonnull Credential signingCredential, @Nonnull @NotEmpty String algorithmURI) {
      if (AlgorithmSupport.isHMAC(algorithmURI)) {
         Iterator var4 = ((SignatureSigningConfigurationCriterion)criteria.get(SignatureSigningConfigurationCriterion.class)).getConfigurations().iterator();

         while(var4.hasNext()) {
            SignatureSigningConfiguration config = (SignatureSigningConfiguration)var4.next();
            if (config.getSignatureHMACOutputLength() != null) {
               return config.getSignatureHMACOutputLength();
            }
         }
      }

      return null;
   }
}
