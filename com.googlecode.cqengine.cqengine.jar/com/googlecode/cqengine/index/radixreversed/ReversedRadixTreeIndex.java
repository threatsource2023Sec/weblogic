package com.googlecode.cqengine.index.radixreversed;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.concurrenttrees.radix.node.NodeFactory;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.radixreversed.ConcurrentReversedRadixTree;
import com.googlecode.concurrenttrees.radixreversed.ReversedRadixTree;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.AbstractAttributeIndex;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.IndexSupport;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.DeduplicationOption;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.query.simple.StringEndsWith;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.connective.ResultSetUnion;
import com.googlecode.cqengine.resultset.connective.ResultSetUnionAll;
import com.googlecode.cqengine.resultset.stored.StoredResultSet;
import com.googlecode.cqengine.resultset.stored.StoredSetBasedResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ReversedRadixTreeIndex extends AbstractAttributeIndex implements OnHeapTypeIndex {
   private static final int INDEX_RETRIEVAL_COST = 51;
   final NodeFactory nodeFactory;
   volatile ReversedRadixTree tree;

   protected ReversedRadixTreeIndex(Attribute attribute) {
      this(attribute, new DefaultCharArrayNodeFactory());
   }

   protected ReversedRadixTreeIndex(Attribute attribute, NodeFactory nodeFactory) {
      super(attribute, new HashSet() {
         {
            this.add(Equal.class);
            this.add(In.class);
            this.add(StringEndsWith.class);
         }
      });
      this.nodeFactory = nodeFactory;
      this.tree = new ConcurrentReversedRadixTree(nodeFactory);
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
      final ReversedRadixTree tree = this.tree;
      Class queryClass = query.getClass();
      if (queryClass.equals(Equal.class)) {
         Equal equal = (Equal)query;
         return this.retrieveEqual(equal, queryOptions, tree);
      } else if (queryClass.equals(In.class)) {
         In in = (In)query;
         return this.retrieveIn(in, queryOptions, tree);
      } else if (queryClass.equals(StringEndsWith.class)) {
         final StringEndsWith stringEndsWith = (StringEndsWith)query;
         return new ResultSet() {
            public Iterator iterator() {
               Iterable resultSets = tree.getValuesForKeysEndingWith(stringEndsWith.getValue());
               ResultSet rs = ReversedRadixTreeIndex.this.unionResultSets(resultSets, query, queryOptions);
               return rs.iterator();
            }

            public boolean contains(Object object) {
               Iterable resultSets = tree.getValuesForKeysEndingWith(stringEndsWith.getValue());
               ResultSet rs = ReversedRadixTreeIndex.this.unionResultSets(resultSets, query, queryOptions);
               return rs.contains(object);
            }

            public boolean matches(Object object) {
               return query.matches(object, queryOptions);
            }

            public int size() {
               Iterable resultSets = tree.getValuesForKeysEndingWith(stringEndsWith.getValue());
               ResultSet rs = ReversedRadixTreeIndex.this.unionResultSets(resultSets, query, queryOptions);
               return rs.size();
            }

            public int getRetrievalCost() {
               return 51;
            }

            public int getMergeCost() {
               Iterable resultSets = tree.getValuesForKeysEndingWith(stringEndsWith.getValue());
               ResultSet rs = ReversedRadixTreeIndex.this.unionResultSets(resultSets, query, queryOptions);
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

   protected ResultSet retrieveIn(final In in, final QueryOptions queryOptions, final ReversedRadixTree tree) {
      Iterable results = new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               final Iterator values = in.getValues().iterator();

               protected ResultSet computeNext() {
                  return this.values.hasNext() ? ReversedRadixTreeIndex.this.retrieveEqual(new Equal(in.getAttribute(), (CharSequence)this.values.next()), queryOptions, tree) : (ResultSet)this.endOfData();
               }
            };
         }
      };
      return IndexSupport.deduplicateIfNecessary(results, in, this.getAttribute(), queryOptions, 51);
   }

   protected ResultSet retrieveEqual(final Equal equal, final QueryOptions queryOptions, final ReversedRadixTree tree) {
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
            return 51;
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

   ResultSet unionResultSets(Iterable results, Query query, QueryOptions queryOptions) {
      return (ResultSet)(DeduplicationOption.isLogicalElimination(queryOptions) && !(this.getAttribute() instanceof SimpleAttribute) && !(this.getAttribute() instanceof SimpleNullableAttribute) ? new ResultSetUnion(results, query, queryOptions) {
         public int getRetrievalCost() {
            return 51;
         }
      } : new ResultSetUnionAll(results, query, queryOptions) {
         public int getRetrievalCost() {
            return 51;
         }
      });
   }

   public StoredResultSet createValueSet() {
      return new StoredSetBasedResultSet(Collections.newSetFromMap(new ConcurrentHashMap()));
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      try {
         boolean modified = false;
         ReversedRadixTree tree = this.tree;
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
         ReversedRadixTree tree = this.tree;
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
      this.tree = new ConcurrentReversedRadixTree(new DefaultCharArrayNodeFactory());
   }

   public static ReversedRadixTreeIndex onAttribute(Attribute attribute) {
      return new ReversedRadixTreeIndex(attribute);
   }

   public static ReversedRadixTreeIndex onAttributeUsingNodeFactory(Attribute attribute, NodeFactory nodeFactory) {
      return new ReversedRadixTreeIndex(attribute, nodeFactory);
   }
}
