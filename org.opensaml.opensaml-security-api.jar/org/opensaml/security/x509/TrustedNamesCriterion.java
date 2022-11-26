package org.opensaml.security.x509;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public class TrustedNamesCriterion implements Criterion {
   private Set trustedNames;

   public TrustedNamesCriterion(@Nonnull Set names) {
      this.setTrustedNames(names);
   }

   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   public Set getTrustedNames() {
      return ImmutableSet.copyOf(this.trustedNames);
   }

   public void setTrustedNames(@Nullable Set names) {
      if (names == null) {
         this.trustedNames = Collections.emptySet();
      } else {
         this.trustedNames = new HashSet(StringSupport.normalizeStringCollection(names));
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("TrustedNamesCriterion [names=");
      builder.append(this.trustedNames);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.trustedNames.hashCode();
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (obj instanceof TrustedNamesCriterion) {
         TrustedNamesCriterion other = (TrustedNamesCriterion)obj;
         return this.trustedNames.equals(other.trustedNames);
      } else {
         return false;
      }
   }
}
