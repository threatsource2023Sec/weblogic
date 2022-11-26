package org.opensaml.security.criteria;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.security.credential.UsageType;

public final class UsageCriterion implements Criterion {
   private UsageType credUsage;

   public UsageCriterion(@Nullable UsageType usage) {
      this.setUsage(usage);
   }

   @Nonnull
   public UsageType getUsage() {
      return this.credUsage;
   }

   public void setUsage(@Nullable UsageType usage) {
      if (usage != null) {
         this.credUsage = usage;
      } else {
         this.credUsage = UsageType.UNSPECIFIED;
      }

   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("UsageCriterion [credUsage=");
      builder.append(this.credUsage);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.credUsage.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof UsageCriterion ? this.credUsage.equals(((UsageCriterion)obj).credUsage) : false;
      }
   }
}
