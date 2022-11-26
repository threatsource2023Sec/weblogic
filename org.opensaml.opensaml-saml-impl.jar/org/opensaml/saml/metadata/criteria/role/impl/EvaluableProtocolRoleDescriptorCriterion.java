package org.opensaml.saml.metadata.criteria.role.impl;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.metadata.criteria.role.EvaluableRoleDescriptorCriterion;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public class EvaluableProtocolRoleDescriptorCriterion implements EvaluableRoleDescriptorCriterion {
   @Nonnull
   @NotEmpty
   private String protocol;

   public EvaluableProtocolRoleDescriptorCriterion(@Nonnull ProtocolCriterion criterion) {
      Constraint.isNotNull(criterion, "ProtocolCriterion was null");
      this.protocol = (String)Constraint.isNotNull(criterion.getProtocol(), "Criterion protocol was null");
   }

   public EvaluableProtocolRoleDescriptorCriterion(@Nonnull String roleProtocol) {
      this.protocol = (String)Constraint.isNotNull(StringSupport.trimOrNull(roleProtocol), "Entity Role protocol was null or empty");
   }

   public boolean apply(RoleDescriptor input) {
      return input == null ? false : input.isSupportedProtocol(this.protocol);
   }

   public int hashCode() {
      return this.protocol.hashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof EvaluableProtocolRoleDescriptorCriterion ? Objects.equals(this.protocol, ((EvaluableProtocolRoleDescriptorCriterion)other).protocol) : false;
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("protocol", this.protocol).toString();
   }
}
