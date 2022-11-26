package com.googlecode.cqengine.index.unique;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.AbstractAttributeIndex;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.Factory;
import com.googlecode.cqengine.index.support.IndexSupport;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.iterator.UnmodifiableIterator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UniqueIndex extends AbstractAttributeIndex implements OnHeapTypeIndex {
   protected static final int INDEX_RETRIEVAL_COST = 25;
   protected final Factory indexMapFactory;
   protected final ConcurrentMap indexMap;

   protected UniqueIndex(Factory indexMapFactory, Attribute attribute) {
      super(attribute, new HashSet() {
         {
            this.add(Equal.class);
            this.add(In.class);
         }
      });
      this.indexMapFactory = indexMapFactory;
      this.indexMap = (ConcurrentMap)indexMapFactory.create();
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      Class queryClass = query.getClass();
      return queryClass.equals(Equal.class) || queryClass.equals(In.class);
   }

   public boolean isMutable() {
      return true;
   }

   public boolean isQuantized() {
      return false;
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      Class queryClass = query.getClass();
      if (queryClass.equals(Equal.class)) {
         ConcurrentMap indexMap = this.indexMap;
         Equal equal = (Equal)query;
         return this.retrieveEqual(equal, queryOptions, indexMap);
      } else if (queryClass.equals(In.class)) {
         In in = (In)query;
         return this.retrieveIn(in, queryOptions, this.indexMap);
      } else {
         throw new IllegalArgumentException("Unsupported query: " + query);
      }
   }

   protected ResultSet retrieveIn(final In in, final QueryOptions queryOptions, final ConcurrentMap indexMap) {
      Iterable results = new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               final Iterator values = in.getValues().iterator();

               protected ResultSet computeNext() {
                  return this.values.hasNext() ? UniqueIndex.this.retrieveEqual(new Equal(in.getAttribute(), this.values.next()), queryOptions, indexMap) : (ResultSet)this.endOfData();
               }
            };
         }
      };
      return IndexSupport.deduplicateIfNecessary(results, in, this.getAttribute(), queryOptions, 25);
   }

   protected ResultSet retrieveEqual(final Equal equal, final QueryOptions queryOptions, ConcurrentMap indexMap) {
      final Object obj = indexMap.get(equal.getValue());
      return new ResultSet() {
         public Iterator iterator() {
            return new UnmodifiableIterator() {
               boolean hasNext = obj != null;

               public boolean hasNext() {
                  return this.hasNext;
               }

               public Object next() {
                  this.hasNext = false;
                  return obj;
               }
            };
         }

         public boolean contains(Object object) {
            return object != null && obj != null && object.equals(obj);
         }

         public boolean matches(Object object) {
            return equal.matches(object, queryOptions);
         }

         public int size() {
            return obj == null ? 0 : 1;
         }

         public int getRetrievalCost() {
            return 25;
         }

         public int getMergeCost() {
            return obj == null ? 0 : 1;
         }

         public void close() {
         }

         public Query getQuery() {
            return equal;
         }

         public QueryOptions getQueryOptions() {
            return queryOptions;
         }
      };
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
                  Object existingValue = indexMap.put(attributeValue, object);
                  if (existingValue != null && !existingValue.equals(object)) {
                     throw new UniqueConstraintViolatedException("The application has attempted to add a duplicate object to the UniqueIndex on attribute '" + this.attribute.getAttributeName() + "', potentially causing inconsistencies between indexes. UniqueIndex should not be used with attributes which do not uniquely identify objects. Problematic attribute value: '" + attributeValue + "', problematic duplicate object: " + object);
                  }

                  modified = true;
               }
            }

            boolean var14 = modified;
            return var14;
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

         label56:
         while(true) {
            if (var5.hasNext()) {
               Object object = var5.next();
               Iterable attributeValues = this.getAttribute().getValues(object, queryOptions);
               Iterator var8 = attributeValues.iterator();

               while(true) {
                  if (!var8.hasNext()) {
                     continue label56;
                  }

                  Object attributeValue = var8.next();
                  modified |= indexMap.remove(attributeValue) != null;
               }
            }

            boolean var13 = modified;
            return var13;
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

   public static UniqueIndex onAttribute(Attribute attribute) {
      return onAttribute(new DefaultIndexMapFactory(), attribute);
   }

   public static UniqueIndex onAttribute(Factory indexMapFactory, Attribute attribute) {
      return new UniqueIndex(indexMapFactory, attribute);
   }

   public static class DefaultIndexMapFactory implements Factory {
      public ConcurrentMap create() {
         return new ConcurrentHashMap();
      }
   }

   public static class UniqueConstraintViolatedException extends RuntimeException {
      public UniqueConstraintViolatedException(String message) {
         super(message);
      }
   }
}
