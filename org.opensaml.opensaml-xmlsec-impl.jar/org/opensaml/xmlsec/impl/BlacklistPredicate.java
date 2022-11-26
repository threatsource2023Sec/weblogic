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

public class BlacklistPredicate implements Predicate {
   @Nonnull
   @NonnullElements
   private Collection blacklist;

   public BlacklistPredicate(@Nonnull Collection algorithms) {
      Constraint.isNotNull(algorithms, "Blacklist may not be null");
      this.blacklist = new HashSet();
      this.blacklist.addAll(Collections2.filter(algorithms, Predicates.notNull()));
   }

   public boolean apply(@Nullable String input) {
      if (input == null) {
         throw new IllegalArgumentException("Algorithm URI to evaluate may not be null");
      } else {
         return !this.blacklist.contains(input);
      }
   }
}
