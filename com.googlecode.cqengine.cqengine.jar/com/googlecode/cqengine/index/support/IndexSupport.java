package com.googlecode.cqengine.index.support;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.DeduplicationOption;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.connective.ResultSetUnion;
import com.googlecode.cqengine.resultset.connective.ResultSetUnionAll;

public class IndexSupport {
   private IndexSupport() {
   }

   public static ResultSet deduplicateIfNecessary(Iterable results, Query query, Attribute attribute, QueryOptions queryOptions, final int retrievalCost) {
      boolean logicalElimination = DeduplicationOption.isLogicalElimination(queryOptions);
      boolean attributeHasAtMostOneValue = attribute instanceof SimpleAttribute || attribute instanceof SimpleNullableAttribute;
      boolean queryIsADisjointInQuery = query instanceof In && ((In)query).isDisjoint();
      return (ResultSet)(logicalElimination && !attributeHasAtMostOneValue && !queryIsADisjointInQuery ? new ResultSetUnion(results, query, queryOptions) {
         public int getRetrievalCost() {
            return retrievalCost;
         }
      } : new ResultSetUnionAll(results, query, queryOptions) {
         public int getRetrievalCost() {
            return retrievalCost;
         }
      });
   }
}
