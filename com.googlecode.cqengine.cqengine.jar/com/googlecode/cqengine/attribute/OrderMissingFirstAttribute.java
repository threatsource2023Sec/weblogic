package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Has;

public class OrderMissingFirstAttribute extends OrderControlAttribute {
   final Has hasQuery;

   public OrderMissingFirstAttribute(Attribute delegateAttribute) {
      super(delegateAttribute, "missingFirst_" + delegateAttribute.getAttributeName());
      this.hasQuery = QueryFactory.has(delegateAttribute);
   }

   public Integer getValue(Object object, QueryOptions queryOptions) {
      return this.hasQuery.matches(object, queryOptions) ? 1 : 0;
   }

   public boolean canEqual(Object other) {
      return other instanceof OrderMissingFirstAttribute;
   }
}
