package org.opensaml.xmlsec.criterion;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.xmlsec.SignatureSigningConfiguration;

public class SignatureSigningConfigurationCriterion implements Criterion {
   @Nonnull
   @NonnullElements
   private List configs;

   public SignatureSigningConfigurationCriterion(@Nonnull @NonnullElements @NotEmpty List configurations) {
      Constraint.isNotNull(configurations, "List of configurations cannot be null");
      this.configs = new ArrayList(Collections2.filter(configurations, Predicates.notNull()));
      Constraint.isGreaterThanOrEqual(1L, (long)this.configs.size(), "At least one configuration is required");
   }

   public SignatureSigningConfigurationCriterion(@Nonnull @NonnullElements @NotEmpty SignatureSigningConfiguration... configurations) {
      Constraint.isNotNull(configurations, "List of configurations cannot be null");
      this.configs = new ArrayList(Collections2.filter(Arrays.asList(configurations), Predicates.notNull()));
      Constraint.isGreaterThanOrEqual(1L, (long)this.configs.size(), "At least one configuration is required");
   }

   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   @NotEmpty
   public List getConfigurations() {
      return ImmutableList.copyOf(this.configs);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("SignatureSigningConfigurationCriterion [configs=");
      builder.append(this.configs);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.configs.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof SignatureSigningConfigurationCriterion ? this.configs.equals(((SignatureSigningConfigurationCriterion)obj).getConfigurations()) : false;
      }
   }
}
