package org.jboss.weld.metadata;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public class ScanningPredicate implements Predicate {
   private final Collection includes;
   private final Collection excludes;

   public ScanningPredicate(Collection includes, Collection excludes) {
      this.includes = includes;
      this.excludes = excludes;
   }

   public boolean test(Object input) {
      boolean apply = this.includes.isEmpty();
      Iterator var3 = this.includes.iterator();

      Predicate exclude;
      while(var3.hasNext()) {
         exclude = (Predicate)var3.next();
         if (exclude.test(input)) {
            apply = true;
         }
      }

      var3 = this.excludes.iterator();

      do {
         if (!var3.hasNext()) {
            return apply;
         }

         exclude = (Predicate)var3.next();
      } while(!exclude.test(input));

      return false;
   }
}
