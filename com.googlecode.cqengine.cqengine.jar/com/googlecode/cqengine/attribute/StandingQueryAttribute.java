package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collections;

public class StandingQueryAttribute extends MultiValueAttribute {
   final Query standingQuery;

   public StandingQueryAttribute(Query standingQuery) {
      super(Object.class, Boolean.class, "<StandingQueryAttribute: " + standingQuery.toString() + ">");
      this.standingQuery = standingQuery;
   }

   public Class getObjectType() {
      throw new UnsupportedOperationException("Unsupported use of StandingQueryAttribute");
   }

   public Query getQuery() {
      return this.standingQuery;
   }

   public Iterable getValues(Object object, QueryOptions queryOptions) {
      return this.standingQuery.matches(object, queryOptions) ? Collections.singleton(Boolean.TRUE) : Collections.emptySet();
   }
}
