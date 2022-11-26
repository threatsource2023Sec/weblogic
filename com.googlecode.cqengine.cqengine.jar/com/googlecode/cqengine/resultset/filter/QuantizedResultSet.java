package com.googlecode.cqengine.resultset.filter;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;

public class QuantizedResultSet extends FilteringResultSet {
   final Query query;

   public QuantizedResultSet(ResultSet wrappedResultSet, Query query, QueryOptions queryOptions) {
      super(wrappedResultSet, query, queryOptions);
      this.query = query;
   }

   public boolean isValid(Object object, QueryOptions queryOptions) {
      return this.query.matches(object, queryOptions);
   }

   public Query getQuery() {
      return this.query;
   }
}
