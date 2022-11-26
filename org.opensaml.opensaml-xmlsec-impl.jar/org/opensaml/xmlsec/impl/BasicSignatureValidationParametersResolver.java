package org.opensaml.xmlsec.impl;

import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.xmlsec.SignatureValidationConfiguration;
import org.opensaml.xmlsec.SignatureValidationParameters;
import org.opensaml.xmlsec.SignatureValidationParametersResolver;
import org.opensaml.xmlsec.criterion.SignatureValidationConfigurationCriterion;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSignatureValidationParametersResolver extends AbstractSecurityParametersResolver implements SignatureValidationParametersResolver {
   private Logger log = LoggerFactory.getLogger(BasicSignatureValidationParametersResolver.class);

   @Nonnull
   public Iterable resolve(@Nonnull CriteriaSet criteria) throws ResolverException {
      SignatureValidationParameters params = this.resolveSingle(criteria);
      return params != null ? Collections.singletonList(params) : Collections.emptyList();
   }

   @Nullable
   public SignatureValidationParameters resolveSingle(@Nonnull CriteriaSet criteria) throws ResolverException {
      Constraint.isNotNull(criteria, "CriteriaSet was null");
      Constraint.isNotNull(criteria.get(SignatureValidationConfigurationCriterion.class), "Resolver requires an instance of SignatureValidationConfigurationCriterion");
      SignatureValidationParameters params = new SignatureValidationParameters();
      this.resolveAndPopulateWhiteAndBlacklists(params, criteria, ((SignatureValidationConfigurationCriterion)criteria.get(SignatureValidationConfigurationCriterion.class)).getConfigurations());
      params.setSignatureTrustEngine(this.resolveSignatureTrustEngine(criteria));
      this.logResult(params);
      return params;
   }

   protected void logResult(@Nonnull SignatureValidationParameters params) {
      if (this.log.isDebugEnabled()) {
         this.log.debug("Resolved SignatureValidationParameters:");
         this.log.debug("\tAlgorithm whitelist: {}", params.getWhitelistedAlgorithms());
         this.log.debug("\tAlgorithm blacklist: {}", params.getBlacklistedAlgorithms());
         this.log.debug("\tSignatureTrustEngine: {}", params.getSignatureTrustEngine() != null ? "present" : "null");
      }

   }

   @Nullable
   protected SignatureTrustEngine resolveSignatureTrustEngine(@Nonnull CriteriaSet criteria) {
      Iterator var2 = ((SignatureValidationConfigurationCriterion)criteria.get(SignatureValidationConfigurationCriterion.class)).getConfigurations().iterator();

      SignatureValidationConfiguration config;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         config = (SignatureValidationConfiguration)var2.next();
      } while(config.getSignatureTrustEngine() == null);

      return config.getSignatureTrustEngine();
   }
}
