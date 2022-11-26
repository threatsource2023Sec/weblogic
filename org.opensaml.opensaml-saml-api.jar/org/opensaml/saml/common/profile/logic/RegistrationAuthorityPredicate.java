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

public class RegistrationAuthorityPredicate extends AbstractRegistrationInfoPredicate {
   @Nonnull
   @NonnullElements
   private final Set authorities;

   public RegistrationAuthorityPredicate(@Nonnull @NonnullElements Collection names) {
      Constraint.isNotNull(names, "Authority name collection cannot be null");
      this.authorities = new HashSet(names.size());
      Iterator var2 = names.iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         String trimmed = StringSupport.trimOrNull(name);
         if (trimmed != null) {
            this.authorities.add(trimmed);
         }
      }

   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set getAuthorities() {
      return ImmutableSet.copyOf(this.authorities);
   }

   protected boolean doApply(@Nonnull RegistrationInfo info) {
      return this.authorities.contains(info.getRegistrationAuthority());
   }
}
