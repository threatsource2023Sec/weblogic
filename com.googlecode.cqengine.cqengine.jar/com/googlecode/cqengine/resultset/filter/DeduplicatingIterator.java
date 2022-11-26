package com.googlecode.cqengine.resultset.filter;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DeduplicatingIterator extends FilteringIterator {
   private final Attribute uniqueAttribute;
   private final Set attributeValuesProcessed = new HashSet();

   public DeduplicatingIterator(Attribute uniqueAttribute, QueryOptions queryOptions, Iterator wrappedIterator) {
      super(wrappedIterator, queryOptions);
      this.uniqueAttribute = uniqueAttribute;
   }

   public boolean isValid(Object object, QueryOptions queryOptions) {
      boolean modified = false;

      Object value;
      for(Iterator var4 = this.uniqueAttribute.getValues(object, queryOptions).iterator(); var4.hasNext(); modified = this.attributeValuesProcessed.add(value) || modified) {
         value = var4.next();
      }

      return modified;
   }
}
