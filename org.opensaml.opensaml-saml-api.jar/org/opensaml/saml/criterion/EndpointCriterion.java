package org.opensaml.saml.criterion;

import java.util.Objects;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.saml.saml2.metadata.Endpoint;

public final class EndpointCriterion implements Criterion {
   private final boolean trusted;
   @Nonnull
   private final Endpoint endpoint;

   public EndpointCriterion(@Nonnull Endpoint ep) {
      this(ep, false);
   }

   public EndpointCriterion(@Nonnull Endpoint ep, boolean trust) {
      this.endpoint = (Endpoint)Constraint.isNotNull(ep, "Endpoint cannot be null");
      this.trusted = trust;
   }

   @Nonnull
   public Endpoint getEndpoint() {
      return this.endpoint;
   }

   public boolean isTrusted() {
      return this.trusted;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EndpointCriterion [type=").append(this.endpoint.getElementQName());
      if (this.endpoint.getBinding() != null) {
         builder.append(", Binding=").append(this.endpoint.getBinding());
      }

      if (this.endpoint.getLocation() != null) {
         builder.append(", Location=").append(this.endpoint.getLocation());
      }

      if (this.endpoint.getResponseLocation() != null) {
         builder.append(", ResponseLocation=").append(this.endpoint.getResponseLocation());
      }

      builder.append(", trusted=").append(this.trusted).append(']');
      return builder.toString();
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (obj instanceof EndpointCriterion) {
         Endpoint endpoint2 = ((EndpointCriterion)obj).getEndpoint();
         if (!Objects.equals(this.endpoint.getElementQName(), endpoint2.getElementQName())) {
            return false;
         } else if (!Objects.equals(this.endpoint.getBinding(), endpoint2.getBinding())) {
            return false;
         } else if (!Objects.equals(this.endpoint.getLocation(), endpoint2.getLocation())) {
            return false;
         } else {
            return Objects.equals(this.endpoint.getResponseLocation(), endpoint2.getResponseLocation());
         }
      } else {
         return false;
      }
   }
}
