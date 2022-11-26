package com.googlecode.cqengine.index.radix;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.RadixTree;
import com.googlecode.concurrenttrees.radix.node.NodeFactory;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.AbstractAttributeIndex;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.IndexSupport;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.query.simple.StringStartsWith;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.stored.StoredResultSet;
import com.googlecode.cqengine.resultset.stored.StoredSetBasedResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class RadixTreeIndex extends AbstractAttributeIndex implements OnHeapTypeIndex {
   private static final int INDEX_RETRIEVAL_COST = 50;
   final NodeFactory nodeFactory;
   volatile RadixTree tree;

   protected RadixTreeIndex(Attribute attribute) {
      this(attribute, new DefaultCharArrayNodeFactory());
   }

   protected RadixTreeIndex(Attribute attribute, NodeFactory nodeFactory) {
      super(attribute, new HashSet() {
         {
            this.add(Equal.class);
            this.add(In.class);
            this.add(StringStartsWith.class);
         }
      });
      this.nodeFactory = nodeFactory;
      this.tree = new ConcurrentRadixTree(nodeFactory);
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

   public ResultSet retrieve(final Query query, final QueryOptions queryOptions) {
      final RadixTree tree = this.tree;
      Class queryClass = query.getClass();
      if (queryClass.equals(Equal.class)) {
         Equal equal = (Equal)query;
         return this.retrieveEqual(equal, queryOptions, tree);
      } else if (queryClass.equals(In.class)) {
         In in = (In)query;
         return this.retrieveIn(in, queryOptions, tree);
      } else if (queryClass.equals(StringStartsWith.class)) {
         final StringStartsWith stringStartsWith = (StringStartsWith)query;
         return new ResultSet() {
            public Iterator iterator() {
               Iterable resultSets = tree.getValuesForKeysStartingWith(stringStartsWith.getValue());
               ResultSet rs = IndexSupport.deduplicateIfNecessary(resultSets, query, RadixTreeIndex.this.getAttribute(), queryOptions, 50);
               return rs.iterator();
            }

            public boolean contains(Object object) {
               Iterable resultSets = tree.getValuesForKeysStartingWith(stringStartsWith.getValue());
               ResultSet rs = IndexSupport.deduplicateIfNecessary(resultSets, query, RadixTreeIndex.this.getAttribute(), queryOptions, 50);
               return rs.contains(object);
            }

            public boolean matches(Object object) {
               return query.matches(object, queryOptions);
            }

            public int size() {
               Iterable resultSets = tree.getValuesForKeysStartingWith(stringStartsWith.getValue());
               ResultSet rs = IndexSupport.deduplicateIfNecessary(resultSets, query, RadixTreeIndex.this.getAttribute(), queryOptions, 50);
               return rs.size();
            }

            public int getRetrievalCost() {
               return 50;
            }

            public int getMergeCost() {
               Iterable resultSets = tree.getValuesForKeysStartingWith(stringStartsWith.getValue());
               ResultSet rs = IndexSupport.deduplicateIfNecessary(resultSets, query, RadixTreeIndex.this.getAttribute(), queryOptions, 50);
               return rs.getMergeCost();
            }

            public void close() {
            }

            public Query getQuery() {
               return query;
            }

            public QueryOptions getQueryOptions() {
               return queryOptions;
            }
         };
      } else {
         throw new IllegalArgumentException("Unsupported query: " + query);
      }
   }

   protected ResultSet retrieveIn(final In in, final QueryOptions queryOptions, final RadixTree tree) {
      Iterable results = new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               final Iterator values = in.getValues().iterator();

               protected ResultSet computeNext() {
                  return this.values.hasNext() ? RadixTreeIndex.this.retrieveEqual(new Equal(in.getAttribute(), (CharSequence)this.values.next()), queryOptions, tree) : (ResultSet)this.endOfData();
               }
            };
         }
      };
      return IndexSupport.deduplicateIfNecessary(results, in, this.getAttribute(), queryOptions, 50);
   }

   protected ResultSet retrieveEqual(final Equal equal, final QueryOptions queryOptions, final RadixTree tree) {
      return new ResultSet() {
         public Iterator iterator() {
            ResultSet rs = (ResultSet)tree.getValueForExactKey((CharSequence)equal.getValue());
            return rs == null ? Collections.emptySet().iterator() : rs.iterator();
         }

         public boolean contains(Object object) {
            ResultSet rs = (ResultSet)tree.getValueForExactKey((CharSequence)equal.getValue());
            return rs != null && rs.contains(object);
         }

         public boolean matches(Object object) {
            return equal.matches(object, queryOptions);
         }

         public int size() {
            ResultSet rs = (ResultSet)tree.getValueForExactKey((CharSequence)equal.getValue());
            return rs == null ? 0 : rs.size();
         }

         public int getRetrievalCost() {
            return 50;
         }

         public int getMergeCost() {
            ResultSet rs = (ResultSet)tree.getValueForExactKey((CharSequence)equal.getValue());
            return rs == null ? 0 : rs.size();
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

   public StoredResultSet createValueSet() {
      return new StoredSetBasedResultSet(Collections.newSetFromMap(new ConcurrentHashMap()));
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      try {
         boolean modified = false;
         RadixTree tree = this.tree;
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

                  CharSequence attributeValue = (CharSequence)var8.next();
                  StoredResultSet valueSet = (StoredResultSet)tree.getValueForExactKey(attributeValue);
                  if (valueSet == null) {
                     valueSet = this.createValueSet();
                     StoredResultSet existingValueSet = (StoredResultSet)tree.putIfAbsent(attributeValue, valueSet);
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
         RadixTree tree = this.tree;
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

                  CharSequence attributeValue = (CharSequence)var8.next();
                  StoredResultSet valueSet = (StoredResultSet)tree.getValueForExactKey(attributeValue);
                  if (valueSet != null) {
                     modified |= valueSet.remove(object);
                     if (valueSet.isEmpty()) {
                        tree.remove(attributeValue);
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
      this.tree = new ConcurrentRadixTree(new DefaultCharArrayNodeFactory());
   }

   public static RadixTreeIndex onAttribute(Attribute attribute) {
      return new RadixTreeIndex(attribute);
   }

   public static RadixTreeIndex onAttributeUsingNodeFactory(Attribute attribute, NodeFactory nodeFactory) {
      return new RadixTreeIndex(attribute, nodeFactory);
   }
}
