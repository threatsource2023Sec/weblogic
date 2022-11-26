package com.googlecode.cqengine.resultset.order;

import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.WrappedResultSet;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import java.util.Comparator;
import java.util.Iterator;

public class MaterializedOrderedResultSet extends WrappedResultSet {
   final Comparator comparator;
   final boolean deduplicateSize;

   public MaterializedOrderedResultSet(ResultSet wrappedResultSet, Comparator comparator, boolean deduplicateSize) {
      super(wrappedResultSet);
      this.comparator = comparator;
      this.deduplicateSize = deduplicateSize;
   }

   public Iterator iterator() {
      return IteratorUtil.materializedSort(super.iterator(), this.comparator);
   }

   public int getMergeCost() {
      long mergeCost = (long)super.getMergeCost();
      return (int)Math.min(mergeCost * mergeCost, 2147483647L);
   }

   public int size() {
      return this.deduplicateSize ? IteratorUtil.countElements(this) : super.size();
   }

   public boolean isEmpty() {
      return this.wrappedResultSet.isEmpty();
   }

   public boolean isNotEmpty() {
      return this.wrappedResultSet.isNotEmpty();
   }
}
