package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.HashSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class WhitelistPredicate implements Predicate {
   @Nonnull
   @NonnullElements
   private Collection whitelist;

   public WhitelistPredicate(@Nonnull Collection algorithms) {
      Constraint.isNotNull(algorithms, "Whitelist may not be null");
      this.whitelist = new HashSet();
      this.whitelist.addAll(Collections2.filter(algorithms, Predicates.notNull()));
   }

   public boolean apply(@Nullable String input) {
      if (input == null) {
         throw new IllegalArgumentException("Algorithm URI to evaluate may not be null");
      } else {
         return this.whitelist.isEmpty() ? true : this.whitelist.contains(input);
      }
   }
}
