package com.googlecode.cqengine.index.support;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.AttributeIndex;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collections;
import java.util.Set;

public abstract class AbstractAttributeIndex implements AttributeIndex {
   protected final Set supportedQueries;
   protected final Attribute attribute;

   protected AbstractAttributeIndex(Attribute attribute, Set supportedQueries) {
      this.attribute = attribute;
      this.supportedQueries = Collections.unmodifiableSet(supportedQueries);
   }

   public Attribute getAttribute() {
      return this.attribute;
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      return this.supportedQueries.contains(query.getClass());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         AbstractAttributeIndex that = (AbstractAttributeIndex)o;
         return this.attribute.equals(that.attribute);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.getClass().hashCode();
      result = 31 * result + this.attribute.hashCode();
      return result;
   }
}
