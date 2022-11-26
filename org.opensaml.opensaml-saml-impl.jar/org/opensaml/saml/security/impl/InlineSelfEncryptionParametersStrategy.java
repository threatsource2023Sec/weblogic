package org.opensaml.saml.security.impl;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.EncryptionParametersResolver;
import org.opensaml.xmlsec.SecurityConfigurationSupport;
import org.opensaml.xmlsec.criterion.EncryptionConfigurationCriterion;
import org.opensaml.xmlsec.impl.BasicEncryptionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InlineSelfEncryptionParametersStrategy implements Function {
   private Logger log;
   @Nonnull
   private CredentialResolver credentialResolver;
   @Nonnull
   private EncryptionParametersResolver encParamsresolver;
   @Nullable
   private Function configurationLookupStrategy;

   public InlineSelfEncryptionParametersStrategy(@Nonnull CredentialResolver credResolver, @Nonnull EncryptionParametersResolver paramsResolver) {
      this(credResolver, paramsResolver, (Function)null);
   }

   public InlineSelfEncryptionParametersStrategy(@Nonnull CredentialResolver credResolver, @Nonnull EncryptionParametersResolver paramsResolver, @Nullable Function configStrategy) {
      this.log = LoggerFactory.getLogger(InlineSelfEncryptionParametersStrategy.class);
      this.credentialResolver = (CredentialResolver)Constraint.isNotNull(credResolver, "CredentialResolver was null");
      this.encParamsresolver = (EncryptionParametersResolver)Constraint.isNotNull(paramsResolver, "EncryptionParametersResolver was null");
      this.configurationLookupStrategy = configStrategy;
   }

   @Nullable
   public List apply(@Nullable Pair input) {
      if (input != null && input.getFirst() != null) {
         List credentials = this.resolveCredentials((ProfileRequestContext)input.getFirst());
         if (credentials.isEmpty()) {
            this.log.debug("No self-encryption credentials were resolved, skipping further processing");
            return Collections.emptyList();
         } else {
            this.log.debug("Resolved {} self-encryption credentials", credentials.size());
            List baseConfigs = this.resolveBaseConfigurations((ProfileRequestContext)input.getFirst());
            this.log.debug("Resolved {} base EncryptionConfigurations", baseConfigs.size());
            ArrayList encParams = new ArrayList();
            Iterator var5 = credentials.iterator();

            while(var5.hasNext()) {
               Credential cred = (Credential)var5.next();
               BasicEncryptionConfiguration selfConfig = new BasicEncryptionConfiguration();
               selfConfig.setKeyTransportEncryptionCredentials(Collections.singletonList(cred));
               if (input.getSecond() != null && ((EncryptionParameters)input.getSecond()).getDataEncryptionAlgorithm() != null) {
                  selfConfig.setDataEncryptionAlgorithms(Collections.singletonList(((EncryptionParameters)input.getSecond()).getDataEncryptionAlgorithm()));
               }

               ArrayList configs = new ArrayList();
               configs.add(selfConfig);
               configs.addAll(baseConfigs);

               try {
                  Iterables.addAll(encParams, this.encParamsresolver.resolve(new CriteriaSet(new Criterion[]{new EncryptionConfigurationCriterion(configs)})));
               } catch (ResolverException var10) {
                  this.log.error("Error resolving self-encryption parameters for Credential '{}', params from other Credentials may still succeed", cred, var10);
               }
            }

            this.log.debug("Resolved {} self-encryption EncryptionParameters", encParams.size());
            return encParams;
         }
      } else {
         this.log.debug("Input Pair or ProfileRequestContext was null, skipping");
         return Collections.emptyList();
      }
   }

   @Nonnull
   protected List resolveCredentials(@Nonnull ProfileRequestContext profileRequestContext) {
      try {
         ArrayList credentials = new ArrayList();
         Iterables.addAll(credentials, this.credentialResolver.resolve(new CriteriaSet(new Criterion[]{new UsageCriterion(UsageType.ENCRYPTION)})));
         return credentials;
      } catch (ResolverException var3) {
         this.log.error("Error resolving IdP encryption credentials", var3);
         return Collections.emptyList();
      }
   }

   @Nonnull
   protected List resolveBaseConfigurations(@Nonnull ProfileRequestContext profileRequestContext) {
      List baseConfigs = null;
      if (this.configurationLookupStrategy != null) {
         this.log.debug("Self-encryption EncryptionConfiguration lookup strategy was non-null");
         baseConfigs = (List)this.configurationLookupStrategy.apply(profileRequestContext);
      } else {
         this.log.debug("Self-encryption EncryptionConfiguration lookup strategy was null");
      }

      if (baseConfigs != null) {
         return baseConfigs;
      } else {
         this.log.debug("No self-encryption EncryptionConfigurations were resolved, returning global configuration");
         return Collections.singletonList(SecurityConfigurationSupport.getGlobalEncryptionConfiguration());
      }
   }
}
