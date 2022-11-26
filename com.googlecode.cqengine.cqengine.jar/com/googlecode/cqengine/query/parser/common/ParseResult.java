package com.googlecode.cqengine.query.parser.common;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;

public class ParseResult {
   final Query query;
   final QueryOptions queryOptions;

   public ParseResult(Query query, QueryOptions queryOptions) {
      this.query = query;
      this.queryOptions = queryOptions;
   }

   public Query getQuery() {
      return this.query;
   }

   public QueryOptions getQueryOptions() {
      return this.queryOptions;
   }
}
