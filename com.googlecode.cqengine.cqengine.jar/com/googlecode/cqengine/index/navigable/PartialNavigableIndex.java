package com.googlecode.cqengine.index.navigable;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.Factory;
import com.googlecode.cqengine.index.support.PartialSortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.query.Query;

public class PartialNavigableIndex extends PartialSortedKeyStatisticsAttributeIndex implements OnHeapTypeIndex {
   final Factory indexMapFactory;
   final Factory valueSetFactory;

   protected PartialNavigableIndex(Factory indexMapFactory, Factory valueSetFactory, Attribute attribute, Query filterQuery) {
      super(attribute, filterQuery);
      this.indexMapFactory = indexMapFactory;
      this.valueSetFactory = valueSetFactory;
   }

   protected SortedKeyStatisticsAttributeIndex createBackingIndex() {
      return new NavigableIndex(this.indexMapFactory, this.valueSetFactory, this.attribute) {
         public Index getEffectiveIndex() {
            return PartialNavigableIndex.this.getEffectiveIndex();
         }
      };
   }

   public static PartialNavigableIndex onAttributeWithFilterQuery(Attribute attribute, Query filterQuery) {
      return onAttributeWithFilterQuery(new NavigableIndex.DefaultIndexMapFactory(), new NavigableIndex.DefaultValueSetFactory(), attribute, filterQuery);
   }

   public static PartialNavigableIndex onAttributeWithFilterQuery(Factory indexMapFactory, Factory valueSetFactory, Attribute attribute, Query filterQuery) {
      return new PartialNavigableIndex(indexMapFactory, valueSetFactory, attribute, filterQuery);
   }
}
