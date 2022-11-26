package org.opensaml.security.x509.tls.impl;

import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.tls.CertificateNameOptions;
import org.opensaml.security.x509.tls.ClientTLSValidationConfiguration;
import org.opensaml.security.x509.tls.ClientTLSValidationConfigurationCriterion;
import org.opensaml.security.x509.tls.ClientTLSValidationParameters;
import org.opensaml.security.x509.tls.ClientTLSValidationParametersResolver;

public class BasicClientTLSValidationParametersResolver implements ClientTLSValidationParametersResolver {
   @Nonnull
   @NonnullElements
   public Iterable resolve(CriteriaSet criteria) throws ResolverException {
      ClientTLSValidationParameters params = this.resolveSingle(criteria);
      return params != null ? Collections.singletonList(params) : Collections.emptyList();
   }

   @Nonnull
   public ClientTLSValidationParameters resolveSingle(CriteriaSet criteria) throws ResolverException {
      Constraint.isNotNull(criteria, "CriteriaSet was null");
      Constraint.isNotNull(criteria.get(ClientTLSValidationConfigurationCriterion.class), "Resolver requires an instance of ClientTLSValidationConfigurationCriterion");
      ClientTLSValidationParameters params = new ClientTLSValidationParameters();
      params.setX509TrustEngine(this.resolveTrustEngine(criteria));
      params.setCertificateNameOptions(this.resolveNameOptions(criteria));
      return params;
   }

   @Nullable
   protected TrustEngine resolveTrustEngine(@Nonnull CriteriaSet criteria) {
      Iterator var2 = ((ClientTLSValidationConfigurationCriterion)criteria.get(ClientTLSValidationConfigurationCriterion.class)).getConfigurations().iterator();

      ClientTLSValidationConfiguration config;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         config = (ClientTLSValidationConfiguration)var2.next();
      } while(config.getX509TrustEngine() == null);

      return config.getX509TrustEngine();
   }

   @Nullable
   protected CertificateNameOptions resolveNameOptions(@Nonnull CriteriaSet criteria) {
      Iterator var2 = ((ClientTLSValidationConfigurationCriterion)criteria.get(ClientTLSValidationConfigurationCriterion.class)).getConfigurations().iterator();

      ClientTLSValidationConfiguration config;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         config = (ClientTLSValidationConfiguration)var2.next();
      } while(config.getCertificateNameOptions() == null);

      return config.getCertificateNameOptions();
   }
}
