package com.googlecode.cqengine.resultset.order;

import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.WrappedResultSet;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Iterator;

public class MaterializedDeduplicatedResultSet extends WrappedResultSet {
   public MaterializedDeduplicatedResultSet(ResultSet wrappedResultSet) {
      super(wrappedResultSet);
   }

   public Iterator iterator() {
      return IteratorUtil.materializedDeuplicate(super.iterator());
   }

   public int size() {
      return IteratorUtil.countElements(this);
   }

   public boolean isEmpty() {
      return this.wrappedResultSet.isEmpty();
   }

   public boolean isNotEmpty() {
      return this.wrappedResultSet.isNotEmpty();
   }
}
