package net.shibboleth.utilities.java.support.resolver;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.ClassIndexedSet;

public class CriteriaSet extends ClassIndexedSet implements Criterion {
   public CriteriaSet() {
   }

   public CriteriaSet(@Nullable Criterion... criteria) {
      if (criteria != null && criteria.length != 0) {
         Criterion[] arr$ = criteria;
         int len$ = criteria.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Criterion criterion = arr$[i$];
            if (criterion != null) {
               this.add(criterion);
            }
         }

      }
   }
}
