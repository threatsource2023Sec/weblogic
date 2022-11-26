package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Has;

public class OrderMissingLastAttribute extends OrderControlAttribute {
   final Has hasQuery;

   public OrderMissingLastAttribute(Attribute delegateAttribute) {
      super(delegateAttribute, "missingLast_" + delegateAttribute.getAttributeName());
      this.hasQuery = QueryFactory.has(delegateAttribute);
   }

   public Integer getValue(Object object, QueryOptions queryOptions) {
      return this.hasQuery.matches(object, queryOptions) ? 0 : 1;
   }

   public boolean canEqual(Object other) {
      return other instanceof OrderMissingLastAttribute;
   }
}
