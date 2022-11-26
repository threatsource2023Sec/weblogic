package org.opensaml.saml.common.binding.impl;

import java.util.Objects;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.common.binding.AbstractEndpointResolver;
import org.opensaml.saml.criterion.BindingCriterion;
import org.opensaml.saml.criterion.EndpointCriterion;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultEndpointResolver extends AbstractEndpointResolver {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(DefaultEndpointResolver.class);

   protected boolean doCheckEndpoint(@Nonnull CriteriaSet criteria, @Nonnull Endpoint endpoint) {
      BindingCriterion bindingCriterion = (BindingCriterion)criteria.get(BindingCriterion.class);
      if (bindingCriterion != null && !this.checkBindingCriterion(bindingCriterion, endpoint)) {
         return false;
      } else {
         EndpointCriterion epCriterion = (EndpointCriterion)criteria.get(EndpointCriterion.class);
         return epCriterion == null || this.checkEndpointCriterion(epCriterion, endpoint);
      }
   }

   private boolean checkBindingCriterion(@Nonnull BindingCriterion bindings, @Nonnull Endpoint endpoint) {
      if (endpoint.getBinding() != null && !bindings.getBindings().contains(endpoint.getBinding())) {
         this.log.debug("{} Candidate endpoint binding '{}' not permitted by input criteria", this.getLogPrefix(), endpoint.getBinding());
         return false;
      } else {
         return true;
      }
   }

   private boolean checkEndpointCriterion(@Nonnull EndpointCriterion comparison, @Nonnull Endpoint endpoint) {
      Endpoint comparisonEndpoint = comparison.getEndpoint();
      if (comparisonEndpoint == endpoint) {
         this.log.debug("{} Candidate endpoint was supplied by the criterion, skipping check", this.getLogPrefix());
         return true;
      } else if (comparisonEndpoint.getBinding() != null && !Objects.equals(comparisonEndpoint.getBinding(), endpoint.getBinding())) {
         this.log.debug("{} Candidate endpoint binding '{}' did not match '{}'", new Object[]{this.getLogPrefix(), endpoint.getBinding(), comparisonEndpoint.getBinding()});
         return false;
      } else if (comparisonEndpoint.getLocation() != null && !Objects.equals(comparisonEndpoint.getLocation(), endpoint.getLocation()) && !Objects.equals(comparisonEndpoint.getLocation(), endpoint.getResponseLocation())) {
         this.log.debug("{} Neither candidate endpoint location '{}' nor response location '{}' matched '{}' ", new Object[]{this.getLogPrefix(), endpoint.getLocation(), endpoint.getResponseLocation(), comparisonEndpoint.getLocation()});
         return false;
      } else {
         if (comparisonEndpoint instanceof IndexedEndpoint && ((IndexedEndpoint)comparisonEndpoint).getIndex() != null) {
            if (!(endpoint instanceof IndexedEndpoint)) {
               this.log.debug("{} Candidate endpoint was not indexed, so did not match", this.getLogPrefix());
               return false;
            }

            if (!Objects.equals(((IndexedEndpoint)comparisonEndpoint).getIndex(), ((IndexedEndpoint)endpoint).getIndex())) {
               this.log.debug("{} Candidate endpoint index {} did not match {}", new Object[]{this.getLogPrefix(), ((IndexedEndpoint)endpoint).getIndex(), ((IndexedEndpoint)comparisonEndpoint).getIndex()});
               return false;
            }
         }

         return true;
      }
   }
}
