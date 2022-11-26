package org.opensaml.core.criterion;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public class SatisfyAnyCriterion implements Criterion {
   private Boolean satisfyAny;

   public SatisfyAnyCriterion() {
      this.satisfyAny = Boolean.TRUE;
   }

   public SatisfyAnyCriterion(boolean value) {
      this.satisfyAny = value;
   }

   public boolean isSatisfyAny() {
      return this.satisfyAny;
   }

   public int hashCode() {
      return this.satisfyAny.hashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof SatisfyAnyCriterion ? Objects.equals(this.satisfyAny, ((SatisfyAnyCriterion)other).satisfyAny) : false;
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).addValue(this.satisfyAny).toString();
   }
}
