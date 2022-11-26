package com.googlecode.cqengine.index.support;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import com.googlecode.cqengine.resultset.stored.StoredResultSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractMapBasedAttributeIndex extends AbstractAttributeIndex {
   protected final Factory indexMapFactory;
   protected final Factory valueSetFactory;
   protected final ConcurrentMap indexMap;

   protected AbstractMapBasedAttributeIndex(Factory indexMapFactory, Factory valueSetFactory, Attribute attribute, Set supportedQueries) {
      super(attribute, supportedQueries);
      this.indexMapFactory = indexMapFactory;
      this.valueSetFactory = valueSetFactory;
      this.indexMap = (ConcurrentMap)indexMapFactory.create();
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      try {
         boolean modified = false;
         ConcurrentMap indexMap = this.indexMap;
         CloseableIterator var5 = objectSet.iterator();

         label58:
         while(true) {
            if (var5.hasNext()) {
               Object object = var5.next();
               Iterable attributeValues = this.getAttribute().getValues(object, queryOptions);
               Iterator var8 = attributeValues.iterator();

               while(true) {
                  if (!var8.hasNext()) {
                     continue label58;
                  }

                  Object attributeValue = var8.next();
                  attributeValue = this.getQuantizedValue(attributeValue);
                  StoredResultSet valueSet = (StoredResultSet)indexMap.get(attributeValue);
                  if (valueSet == null) {
                     valueSet = (StoredResultSet)this.valueSetFactory.create();
                     StoredResultSet existingValueSet = (StoredResultSet)indexMap.putIfAbsent(attributeValue, valueSet);
                     if (existingValueSet != null) {
                        valueSet = existingValueSet;
                     }
                  }

                  modified |= valueSet.add(object);
               }
            }

            boolean var15 = modified;
            return var15;
         }
      } finally {
         objectSet.close();
      }
   }

   public boolean removeAll(ObjectSet objectSet, QueryOptions queryOptions) {
      try {
         boolean modified = false;
         ConcurrentMap indexMap = this.indexMap;
         CloseableIterator var5 = objectSet.iterator();

         label55:
         while(true) {
            if (var5.hasNext()) {
               Object object = var5.next();
               Iterable attributeValues = this.getAttribute().getValues(object, queryOptions);
               Iterator var8 = attributeValues.iterator();

               while(true) {
                  if (!var8.hasNext()) {
                     continue label55;
                  }

                  Object attributeValue = var8.next();
                  attributeValue = this.getQuantizedValue(attributeValue);
                  StoredResultSet valueSet = (StoredResultSet)indexMap.get(attributeValue);
                  if (valueSet != null) {
                     modified |= valueSet.remove(object);
                     if (valueSet.isEmpty()) {
                        indexMap.remove(attributeValue);
                     }
                  }
               }
            }

            boolean var14 = modified;
            return var14;
         }
      } finally {
         objectSet.close();
      }
   }

   public void init(ObjectStore objectStore, QueryOptions queryOptions) {
      this.addAll(ObjectSet.fromObjectStore(objectStore, queryOptions), queryOptions);
   }

   public void clear(QueryOptions queryOptions) {
      this.indexMap.clear();
   }

   protected CloseableIterable getDistinctKeys() {
      return wrapNonCloseable(this.indexMap.keySet());
   }

   protected CloseableIterable getKeysAndValues() {
      return wrapNonCloseable(IteratorUtil.flatten(this.indexMap));
   }

   protected static CloseableIterable wrapNonCloseable(final Iterable iterable) {
      return new CloseableIterable() {
         public CloseableIterator iterator() {
            return new CloseableIterator() {
               final Iterator iterator = iterable.iterator();

               public void close() {
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }

               public boolean hasNext() {
                  return this.iterator.hasNext();
               }

               public Object next() {
                  return this.iterator.next();
               }
            };
         }
      };
   }

   protected Integer getCountForKey(Object key) {
      StoredResultSet objectsForKey = (StoredResultSet)this.indexMap.get(key);
      return objectsForKey == null ? 0 : objectsForKey.size();
   }

   protected Integer getCountOfDistinctKeys(QueryOptions queryOptions) {
      return this.indexMap.keySet().size();
   }

   public CloseableIterable getStatisticsForDistinctKeys(QueryOptions queryOptions) {
      final Iterator distinctKeysIterator = this.indexMap.keySet().iterator();
      return wrapNonCloseable(new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               protected KeyStatistics computeNext() {
                  if (distinctKeysIterator.hasNext()) {
                     Object key = distinctKeysIterator.next();
                     return new KeyStatistics(key, AbstractMapBasedAttributeIndex.this.getCountForKey(key));
                  } else {
                     return (KeyStatistics)this.endOfData();
                  }
               }
            };
         }
      });
   }

   public boolean isQuantized() {
      return false;
   }

   protected Object getQuantizedValue(Object attributeValue) {
      return attributeValue;
   }
}
