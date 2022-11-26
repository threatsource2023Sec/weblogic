package com.googlecode.cqengine.query.logical;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collection;
import java.util.Iterator;

public class Or extends LogicalQuery {
   private final boolean disjoint;

   public Or(Collection childQueries) {
      this(childQueries, false);
   }

   public Or(Collection childQueries, boolean disjoint) {
      super(childQueries);
      if (this.size() < 2) {
         throw new IllegalStateException("An 'Or' query cannot have fewer than 2 child queries, " + childQueries.size() + " were supplied");
      } else {
         this.disjoint = disjoint;
      }
   }

   public boolean matches(Object object, QueryOptions queryOptions) {
      Iterator var3 = super.getSimpleQueries().iterator();

      Query query;
      do {
         if (!var3.hasNext()) {
            var3 = super.getLogicalQueries().iterator();

            do {
               if (!var3.hasNext()) {
                  return false;
               }

               query = (Query)var3.next();
            } while(!query.matches(object, queryOptions));

            return true;
         }

         query = (Query)var3.next();
      } while(!query.matches(object, queryOptions));

      return true;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Or)) {
         return false;
      } else {
         Or or = (Or)o;
         if (this.disjoint != or.disjoint) {
            return false;
         } else {
            return this.childQueries.equals(or.childQueries);
         }
      }
   }

   public boolean isDisjoint() {
      return this.disjoint;
   }

   protected int calcHashCode() {
      int result = this.childQueries.hashCode();
      result = 31 * result + (this.disjoint ? 1 : 0);
      return result;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("or(");
      Iterator iterator = this.childQueries.iterator();

      while(iterator.hasNext()) {
         Query childQuery = (Query)iterator.next();
         sb.append(childQuery);
         if (iterator.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append(")");
      return sb.toString();
   }
}
