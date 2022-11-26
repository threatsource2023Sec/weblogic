package com.googlecode.cqengine.query.logical;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collection;
import java.util.Iterator;

public class And extends LogicalQuery {
   public And(Collection childQueries) {
      super(childQueries);
      if (this.size() < 2) {
         throw new IllegalStateException("An 'And' query cannot have fewer than 2 child queries, " + childQueries.size() + " were supplied");
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
                  return true;
               }

               query = (Query)var3.next();
            } while(query.matches(object, queryOptions));

            return false;
         }

         query = (Query)var3.next();
      } while(query.matches(object, queryOptions));

      return false;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof And)) {
         return false;
      } else {
         And and = (And)o;
         return this.childQueries.equals(and.childQueries);
      }
   }

   protected int calcHashCode() {
      return this.childQueries.hashCode();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("and(");
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
