package org.opensaml.saml.common.profile.logic;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationPolicy;

public class RegistrationPolicyPredicate extends AbstractRegistrationInfoPredicate {
   @Nonnull
   @NonnullElements
   private final Set policySet;

   public RegistrationPolicyPredicate(@Nonnull @NonnullElements Collection policies) {
      Constraint.isNotNull(policies, "Authority name collection cannot be null");
      this.policySet = new HashSet(policies.size());
      Iterator var2 = policies.iterator();

      while(var2.hasNext()) {
         String policy = (String)var2.next();
         String trimmed = StringSupport.trimOrNull(policy);
         if (trimmed != null) {
            policies.add(trimmed);
         }
      }

   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set getPolicies() {
      return ImmutableSet.copyOf(this.policySet);
   }

   protected boolean doApply(@Nonnull RegistrationInfo info) {
      Iterator var2 = info.getRegistrationPolicies().iterator();

      RegistrationPolicy policy;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         policy = (RegistrationPolicy)var2.next();
      } while(!this.policySet.contains(policy.getValue()));

      return true;
   }
}
