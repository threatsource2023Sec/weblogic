package org.opensaml.saml.common.binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.saml.criterion.EndpointCriterion;
import org.opensaml.saml.criterion.RoleDescriptorCriterion;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEndpointResolver extends AbstractIdentifiedInitializableComponent implements EndpointResolver {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(AbstractEndpointResolver.class);

   public AbstractEndpointResolver() {
      super.setId(this.getClass().getName());
   }

   @Nonnull
   @NonnullElements
   public Iterable resolve(@Nullable CriteriaSet criteria) throws ResolverException {
      this.validateCriteria(criteria);
      if (this.canUseRequestedEndpoint(criteria)) {
         Endpoint endpoint = ((EndpointCriterion)criteria.get(EndpointCriterion.class)).getEndpoint();
         if (this.doCheckEndpoint(criteria, endpoint)) {
            return Collections.singletonList(endpoint);
         } else {
            this.log.debug("{} Requested endpoint was rejected by extended validation process", this.getLogPrefix());
            return Collections.emptyList();
         }
      } else {
         List candidates = this.getCandidatesFromMetadata(criteria);
         Iterator i = candidates.iterator();

         while(i.hasNext()) {
            if (!this.doCheckEndpoint(criteria, (Endpoint)i.next())) {
               i.remove();
            }
         }

         this.log.debug("{} {} endpoints remain after filtering process", this.getLogPrefix(), candidates.size());
         return candidates;
      }
   }

   @Nullable
   public Endpoint resolveSingle(@Nullable CriteriaSet criteria) throws ResolverException {
      this.validateCriteria(criteria);
      if (this.canUseRequestedEndpoint(criteria)) {
         Endpoint endpoint = ((EndpointCriterion)criteria.get(EndpointCriterion.class)).getEndpoint();
         if (this.doCheckEndpoint(criteria, endpoint)) {
            return endpoint;
         } else {
            this.log.debug("{} Requested endpoint was rejected by extended validation process", this.getLogPrefix());
            return null;
         }
      } else {
         Iterator var2 = this.getCandidatesFromMetadata(criteria).iterator();

         Endpoint candidate;
         do {
            if (!var2.hasNext()) {
               this.log.debug("{} No candidate endpoints met criteria", this.getLogPrefix());
               return null;
            }

            candidate = (Endpoint)var2.next();
         } while(!this.doCheckEndpoint(criteria, candidate));

         return candidate;
      }
   }

   protected boolean doCheckEndpoint(@Nonnull CriteriaSet criteria, @Nonnull Endpoint endpoint) {
      return true;
   }

   private void validateCriteria(@Nullable CriteriaSet criteria) throws ResolverException {
      if (criteria == null) {
         throw new ResolverException("CriteriaSet cannot be null");
      } else {
         EndpointCriterion epCriterion = (EndpointCriterion)criteria.get(EndpointCriterion.class);
         if (epCriterion == null) {
            throw new ResolverException("EndpointCriterion not supplied");
         }
      }
   }

   private boolean canUseRequestedEndpoint(@Nonnull CriteriaSet criteria) {
      EndpointCriterion epc = (EndpointCriterion)criteria.get(EndpointCriterion.class);
      if (epc.isTrusted()) {
         Endpoint requestedEndpoint = epc.getEndpoint();
         if (requestedEndpoint.getBinding() != null && (requestedEndpoint.getLocation() != null || requestedEndpoint.getResponseLocation() != null)) {
            return true;
         }
      }

      return false;
   }

   @Nonnull
   @NonnullElements
   private List getCandidatesFromMetadata(@Nonnull CriteriaSet criteria) {
      RoleDescriptorCriterion role = (RoleDescriptorCriterion)criteria.get(RoleDescriptorCriterion.class);
      if (role == null) {
         this.log.debug("{} No metadata supplied, no candidate endpoints to return", this.getLogPrefix());
         return new ArrayList();
      } else {
         EndpointCriterion epCriterion = (EndpointCriterion)criteria.get(EndpointCriterion.class);
         QName endpointType = epCriterion.getEndpoint().getSchemaType();
         if (endpointType == null) {
            endpointType = epCriterion.getEndpoint().getElementQName();
         }

         List endpoints = role.getRole().getEndpoints(endpointType);
         if (endpoints.isEmpty()) {
            this.log.debug("{} No endpoints in metadata of type {}", this.getLogPrefix(), endpointType);
         } else {
            this.log.debug("{} Returning {} candidate endpoints of type {}", new Object[]{this.getLogPrefix(), endpoints.size(), endpointType});
         }

         return this.sortCandidates(endpoints);
      }
   }

   @Nonnull
   @NonnullElements
   private List sortCandidates(@Nonnull @NonnullElements List candidates) {
      Endpoint hardDefault = null;
      Endpoint softDefault = null;
      LinkedList toReturn = new LinkedList();
      Iterator var5 = candidates.iterator();

      while(true) {
         while(true) {
            while(var5.hasNext()) {
               Endpoint endpoint = (Endpoint)var5.next();
               if (hardDefault == null && endpoint instanceof IndexedEndpoint) {
                  Boolean flag = ((IndexedEndpoint)endpoint).isDefault();
                  if (flag != null) {
                     if (flag) {
                        hardDefault = endpoint;
                        if (softDefault != null) {
                           toReturn.addFirst(softDefault);
                           softDefault = null;
                        }
                     } else {
                        toReturn.addLast(endpoint);
                     }
                  } else if (hardDefault == null && softDefault == null) {
                     softDefault = endpoint;
                  } else {
                     toReturn.addLast(endpoint);
                  }
               } else {
                  toReturn.addLast(endpoint);
               }
            }

            if (hardDefault != null) {
               toReturn.addFirst(hardDefault);
            } else if (softDefault != null) {
               toReturn.addFirst(softDefault);
            }

            return toReturn;
         }
      }
   }

   @Nonnull
   protected String getLogPrefix() {
      return "Endpoint Resolver " + this.getId() + ":";
   }
}
