package org.opensaml.xmlsec.impl;

import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.DecryptionParametersResolver;
import org.opensaml.xmlsec.criterion.DecryptionConfigurationCriterion;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicDecryptionParametersResolver extends AbstractSecurityParametersResolver implements DecryptionParametersResolver {
   private Logger log = LoggerFactory.getLogger(BasicDecryptionParametersResolver.class);

   @Nonnull
   public Iterable resolve(@Nonnull CriteriaSet criteria) throws ResolverException {
      DecryptionParameters params = this.resolveSingle(criteria);
      return params != null ? Collections.singletonList(params) : Collections.emptyList();
   }

   @Nullable
   public DecryptionParameters resolveSingle(@Nonnull CriteriaSet criteria) throws ResolverException {
      Constraint.isNotNull(criteria, "CriteriaSet was null");
      Constraint.isNotNull(criteria.get(DecryptionConfigurationCriterion.class), "Resolver requires an instance of DecryptionConfigurationCriterion");
      DecryptionParameters params = new DecryptionParameters();
      this.resolveAndPopulateWhiteAndBlacklists(params, criteria, ((DecryptionConfigurationCriterion)criteria.get(DecryptionConfigurationCriterion.class)).getConfigurations());
      params.setDataKeyInfoCredentialResolver(this.resolveDataKeyInfoCredentialResolver(criteria));
      params.setKEKKeyInfoCredentialResolver(this.resolveKEKKeyInfoCredentialResolver(criteria));
      params.setEncryptedKeyResolver(this.resolveEncryptedKeyResolver(criteria));
      this.logResult(params);
      return params;
   }

   protected void logResult(@Nonnull DecryptionParameters params) {
      if (this.log.isDebugEnabled()) {
         this.log.debug("Resolved DecryptionParameters:");
         this.log.debug("\tAlgorithm whitelist: {}", params.getWhitelistedAlgorithms());
         this.log.debug("\tAlgorithm blacklist: {}", params.getBlacklistedAlgorithms());
         this.log.debug("\tData KeyInfoCredentialResolver: {}", params.getDataKeyInfoCredentialResolver() != null ? "present" : "null");
         this.log.debug("\tKEK KeyInfoCredentialResolver: {}", params.getKEKKeyInfoCredentialResolver() != null ? "present" : "null");
         this.log.debug("\tEncryptedKeyResolver: {}", params.getEncryptedKeyResolver() != null ? "present" : "null");
      }

   }

   @Nullable
   protected EncryptedKeyResolver resolveEncryptedKeyResolver(@Nonnull CriteriaSet criteria) {
      Iterator var2 = ((DecryptionConfigurationCriterion)criteria.get(DecryptionConfigurationCriterion.class)).getConfigurations().iterator();

      DecryptionConfiguration config;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         config = (DecryptionConfiguration)var2.next();
      } while(config.getEncryptedKeyResolver() == null);

      return config.getEncryptedKeyResolver();
   }

   @Nullable
   protected KeyInfoCredentialResolver resolveKEKKeyInfoCredentialResolver(@Nonnull CriteriaSet criteria) {
      Iterator var2 = ((DecryptionConfigurationCriterion)criteria.get(DecryptionConfigurationCriterion.class)).getConfigurations().iterator();

      DecryptionConfiguration config;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         config = (DecryptionConfiguration)var2.next();
      } while(config.getKEKKeyInfoCredentialResolver() == null);

      return config.getKEKKeyInfoCredentialResolver();
   }

   @Nullable
   protected KeyInfoCredentialResolver resolveDataKeyInfoCredentialResolver(@Nonnull CriteriaSet criteria) {
      Iterator var2 = ((DecryptionConfigurationCriterion)criteria.get(DecryptionConfigurationCriterion.class)).getConfigurations().iterator();

      DecryptionConfiguration config;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         config = (DecryptionConfiguration)var2.next();
      } while(config.getDataKeyInfoCredentialResolver() == null);

      return config.getDataKeyInfoCredentialResolver();
   }
}
