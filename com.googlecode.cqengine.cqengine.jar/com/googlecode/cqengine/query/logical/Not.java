package com.googlecode.cqengine.query.logical;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collections;

public class Not extends LogicalQuery {
   private final Query negatedQuery;

   public Not(Query negatedQuery) {
      super(Collections.singleton(negatedQuery));
      this.negatedQuery = negatedQuery;
   }

   public Query getNegatedQuery() {
      return this.negatedQuery;
   }

   public boolean matches(Object object, QueryOptions queryOptions) {
      return !this.negatedQuery.matches(object, queryOptions);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Not)) {
         return false;
      } else {
         Not other = (Not)o;
         return this.negatedQuery.equals(other.negatedQuery);
      }
   }

   protected int calcHashCode() {
      return this.negatedQuery.hashCode();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("not(");
      sb.append(this.negatedQuery);
      sb.append(")");
      return sb.toString();
   }
}
