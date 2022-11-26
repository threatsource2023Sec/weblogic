package org.opensaml.saml.criterion;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public class StartsWithLocationCriterion implements Criterion {
   private Boolean matchStartsWith;

   public StartsWithLocationCriterion() {
      this.matchStartsWith = Boolean.TRUE;
   }

   public StartsWithLocationCriterion(boolean value) {
      this.matchStartsWith = value;
   }

   public boolean isMatchStartsWith() {
      return this.matchStartsWith;
   }

   public int hashCode() {
      return this.matchStartsWith.hashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof StartsWithLocationCriterion ? Objects.equals(this.matchStartsWith, ((StartsWithLocationCriterion)other).matchStartsWith) : false;
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).addValue(this.matchStartsWith).toString();
   }
}
