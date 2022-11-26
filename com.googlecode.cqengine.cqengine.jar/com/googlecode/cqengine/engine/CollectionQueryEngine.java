package com.googlecode.cqengine.engine;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.OrderControlAttribute;
import com.googlecode.cqengine.attribute.OrderMissingFirstAttribute;
import com.googlecode.cqengine.attribute.OrderMissingLastAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.attribute.StandingQueryAttribute;
import com.googlecode.cqengine.index.AttributeIndex;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.compound.CompoundIndex;
import com.googlecode.cqengine.index.compound.support.CompoundAttribute;
import com.googlecode.cqengine.index.compound.support.CompoundQuery;
import com.googlecode.cqengine.index.fallback.FallbackIndex;
import com.googlecode.cqengine.index.sqlite.IdentityAttributeIndex;
import com.googlecode.cqengine.index.sqlite.SQLiteIdentityIndex;
import com.googlecode.cqengine.index.sqlite.SimplifiedSQLiteIndex;
import com.googlecode.cqengine.index.standingquery.StandingQueryIndex;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.index.support.CloseableRequestResources;
import com.googlecode.cqengine.index.support.KeyValue;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsAttributeIndex;
import com.googlecode.cqengine.index.support.SortedKeyStatisticsIndex;
import com.googlecode.cqengine.index.unique.UniqueIndex;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.persistence.support.ObjectStoreResultSet;
import com.googlecode.cqengine.persistence.support.sqlite.SQLiteObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.logical.And;
import com.googlecode.cqengine.query.logical.Not;
import com.googlecode.cqengine.query.logical.Or;
import com.googlecode.cqengine.query.option.AttributeOrder;
import com.googlecode.cqengine.query.option.DeduplicationOption;
import com.googlecode.cqengine.query.option.EngineFlags;
import com.googlecode.cqengine.query.option.EngineThresholds;
import com.googlecode.cqengine.query.option.FlagsEnabled;
import com.googlecode.cqengine.query.option.OrderByOption;
import com.googlecode.cqengine.query.option.QueryLog;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.option.Thresholds;
import com.googlecode.cqengine.query.simple.Between;
import com.googlecode.cqengine.query.simple.GreaterThan;
import com.googlecode.cqengine.query.simple.LessThan;
import com.googlecode.cqengine.query.simple.SimpleQuery;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.closeable.CloseableResultSet;
import com.googlecode.cqengine.resultset.common.CostCachingResultSet;
import com.googlecode.cqengine.resultset.connective.ResultSetDifference;
import com.googlecode.cqengine.resultset.connective.ResultSetIntersection;
import com.googlecode.cqengine.resultset.connective.ResultSetUnion;
import com.googlecode.cqengine.resultset.connective.ResultSetUnionAll;
import com.googlecode.cqengine.resultset.filter.FilteringIterator;
import com.googlecode.cqengine.resultset.filter.FilteringResultSet;
import com.googlecode.cqengine.resultset.filter.MaterializedDeduplicatedIterator;
import com.googlecode.cqengine.resultset.iterator.ConcatenatingIterable;
import com.googlecode.cqengine.resultset.iterator.ConcatenatingIterator;
import com.googlecode.cqengine.resultset.iterator.IteratorUtil;
import com.googlecode.cqengine.resultset.iterator.UnmodifiableIterator;
import com.googlecode.cqengine.resultset.order.AttributeOrdersComparator;
import com.googlecode.cqengine.resultset.order.MaterializedDeduplicatedResultSet;
import com.googlecode.cqengine.resultset.order.MaterializedOrderedResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CollectionQueryEngine implements QueryEngineInternal {
   public static final String ROOT_QUERY = "ROOT_QUERY";
   private volatile Persistence persistence;
   private volatile ObjectStore objectStore;
   private final ConcurrentMap attributeIndexes = new ConcurrentHashMap();
   private final ConcurrentMap uniqueIndexes = new ConcurrentHashMap();
   private final ConcurrentMap compoundIndexes = new ConcurrentHashMap();
   private final ConcurrentMap standingQueryIndexes = new ConcurrentHashMap();
   private final FallbackIndex fallbackIndex = new FallbackIndex();
   private volatile boolean allIndexesAreMutable = true;

   public void init(final ObjectStore objectStore, final QueryOptions queryOptions) {
      this.objectStore = objectStore;
      Persistence persistenceFromQueryOptions = getPersistenceFromQueryOptions(queryOptions);
      this.persistence = persistenceFromQueryOptions;
      if (objectStore instanceof SQLiteObjectStore) {
         SQLiteObjectStore sqLiteObjectStore = (SQLiteObjectStore)objectStore;
         SQLiteIdentityIndex backingIndex = sqLiteObjectStore.getBackingIndex();
         this.addIndex(backingIndex, queryOptions);
      }

      this.forEachIndexDo(new IndexOperation() {
         public boolean perform(Index index) {
            queryOptions.put(QueryEngine.class, this);
            queryOptions.put(Persistence.class, CollectionQueryEngine.this.persistence);
            index.init(objectStore, queryOptions);
            return true;
         }
      });
   }

   public void addIndex(Index index, QueryOptions queryOptions) {
      if (index instanceof StandingQueryIndex) {
         this.allIndexesAreMutable = this.allIndexesAreMutable && index.isMutable();
         StandingQueryIndex standingQueryIndex = (StandingQueryIndex)index;
         this.addStandingQueryIndex(standingQueryIndex, standingQueryIndex.getStandingQuery(), queryOptions);
      } else if (index instanceof CompoundIndex) {
         this.allIndexesAreMutable = this.allIndexesAreMutable && index.isMutable();
         CompoundIndex compoundIndex = (CompoundIndex)index;
         CompoundAttribute compoundAttribute = compoundIndex.getAttribute();
         this.addCompoundIndex(compoundIndex, compoundAttribute, queryOptions);
      } else {
         if (!(index instanceof AttributeIndex)) {
            throw new IllegalStateException("Unexpected type of index: " + (index == null ? null : index.getClass().getName()));
         }

         this.allIndexesAreMutable = this.allIndexesAreMutable && index.isMutable();
         AttributeIndex attributeIndex = (AttributeIndex)index;
         Attribute indexedAttribute = attributeIndex.getAttribute();
         if (indexedAttribute instanceof StandingQueryAttribute) {
            StandingQueryAttribute standingQueryAttribute = (StandingQueryAttribute)indexedAttribute;
            Query standingQuery = standingQueryAttribute.getQuery();
            this.addStandingQueryIndex(index, standingQuery, queryOptions);
         } else {
            this.addAttributeIndex(attributeIndex, queryOptions);
         }
      }

   }

   void addAttributeIndex(AttributeIndex attributeIndex, QueryOptions queryOptions) {
      Attribute attribute = attributeIndex.getAttribute();
      Set indexesOnThisAttribute = (Set)this.attributeIndexes.get(attribute);
      if (indexesOnThisAttribute == null) {
         indexesOnThisAttribute = Collections.newSetFromMap(new ConcurrentHashMap());
         this.attributeIndexes.put(attribute, indexesOnThisAttribute);
      }

      if (attributeIndex instanceof SimplifiedSQLiteIndex) {
         Iterator var5 = indexesOnThisAttribute.iterator();

         while(var5.hasNext()) {
            Index existingIndex = (Index)var5.next();
            if (existingIndex instanceof IdentityAttributeIndex) {
               throw new IllegalStateException("An identity index for persistence has already been added, and no additional non-heap indexes are allowed, on attribute: " + attribute);
            }
         }
      }

      if (!indexesOnThisAttribute.add(attributeIndex)) {
         throw new IllegalStateException("An equivalent index has already been added for attribute: " + attribute);
      } else {
         if (attributeIndex instanceof UniqueIndex) {
            this.uniqueIndexes.put(attribute, attributeIndex);
         }

         queryOptions.put(QueryEngine.class, this);
         queryOptions.put(Persistence.class, this.persistence);
         attributeIndex.init(this.objectStore, queryOptions);
      }
   }

   void addStandingQueryIndex(Index standingQueryIndex, Query standingQuery, QueryOptions queryOptions) {
      Index existingIndex = (Index)this.standingQueryIndexes.putIfAbsent(standingQuery, standingQueryIndex);
      if (existingIndex != null) {
         throw new IllegalStateException("An index has already been added for standing query: " + standingQuery);
      } else {
         queryOptions.put(QueryEngine.class, this);
         queryOptions.put(Persistence.class, this.persistence);
         standingQueryIndex.init(this.objectStore, queryOptions);
      }
   }

   void addCompoundIndex(CompoundIndex compoundIndex, CompoundAttribute compoundAttribute, QueryOptions queryOptions) {
      CompoundIndex existingIndex = (CompoundIndex)this.compoundIndexes.putIfAbsent(compoundAttribute, compoundIndex);
      if (existingIndex != null) {
         throw new IllegalStateException("An index has already been added for compound attribute: " + compoundAttribute);
      } else {
         queryOptions.put(QueryEngine.class, this);
         queryOptions.put(Persistence.class, this.persistence);
         compoundIndex.init(this.objectStore, queryOptions);
      }
   }

   public Iterable getIndexes() {
      List indexes = new ArrayList();
      Iterator var2 = this.attributeIndexes.values().iterator();

      while(var2.hasNext()) {
         Set attributeIndexes = (Set)var2.next();
         indexes.addAll(attributeIndexes);
      }

      indexes.addAll(this.compoundIndexes.values());
      indexes.addAll(this.standingQueryIndexes.values());
      return indexes;
   }

   Iterable getIndexesOnAttribute(Attribute attribute) {
      Set indexesOnAttribute = (Set)this.attributeIndexes.get(attribute);
      if (indexesOnAttribute != null && !indexesOnAttribute.isEmpty()) {
         List iterables = new ArrayList(2);
         iterables.add(indexesOnAttribute);
         iterables.add(Collections.singleton(this.fallbackIndex));
         return new ConcatenatingIterable(iterables);
      } else {
         return Collections.singleton(this.fallbackIndex);
      }
   }

   ResultSet getEntireCollectionAsResultSet(final Query query, final QueryOptions queryOptions) {
      return new ObjectStoreResultSet(this.objectStore, query, queryOptions, Integer.MAX_VALUE) {
         public int getMergeCost() {
            return Integer.MAX_VALUE;
         }

         public Query getQuery() {
            return query;
         }

         public QueryOptions getQueryOptions() {
            return queryOptions;
         }
      };
   }

   ResultSet getResultSetWithLowestRetrievalCost(SimpleQuery query, QueryOptions queryOptions) {
      Index uniqueIndex = (Index)this.uniqueIndexes.get(query.getAttribute());
      if (uniqueIndex != null && uniqueIndex.supportsQuery(query, queryOptions)) {
         return uniqueIndex.retrieve(query, queryOptions);
      } else {
         Iterable indexesOnAttribute = this.getIndexesOnAttribute(query.getAttribute());
         ResultSet lowestCostResultSet = null;
         int lowestRetrievalCost = 0;
         Iterator var7 = indexesOnAttribute.iterator();

         while(true) {
            ResultSet thisIndexResultSet;
            int thisIndexRetrievalCost;
            do {
               Index index;
               do {
                  if (!var7.hasNext()) {
                     if (lowestCostResultSet == null) {
                        throw new IllegalStateException("Failed to locate an index supporting query: " + query);
                     }

                     return new CostCachingResultSet(lowestCostResultSet);
                  }

                  index = (Index)var7.next();
               } while(!index.supportsQuery(query, queryOptions));

               thisIndexResultSet = index.retrieve(query, queryOptions);
               thisIndexRetrievalCost = thisIndexResultSet.getRetrievalCost();
            } while(lowestCostResultSet != null && thisIndexRetrievalCost >= lowestRetrievalCost);

            lowestCostResultSet = thisIndexResultSet;
            lowestRetrievalCost = thisIndexRetrievalCost;
         }
      }
   }

   public ResultSet retrieve(Query query, final QueryOptions queryOptions) {
      OrderByOption orderByOption = (OrderByOption)queryOptions.get(OrderByOption.class);
      queryOptions.put("ROOT_QUERY", query);
      QueryLog queryLog = (QueryLog)queryOptions.get(QueryLog.class);
      SortedKeyStatisticsAttributeIndex indexForOrdering = null;
      if (orderByOption != null) {
         Double selectivityThreshold = Thresholds.getThreshold(queryOptions, EngineThresholds.INDEX_ORDERING_SELECTIVITY);
         if (selectivityThreshold == null) {
            selectivityThreshold = EngineThresholds.INDEX_ORDERING_SELECTIVITY.getThresholdDefault();
         }

         List allSortOrders = orderByOption.getAttributeOrders();
         if (selectivityThreshold != 0.0) {
            AttributeOrder firstOrder = (AttributeOrder)allSortOrders.iterator().next();
            Attribute firstAttribute = firstOrder.getAttribute();
            if (firstAttribute instanceof OrderControlAttribute) {
               Attribute firstAttributeDelegate = ((OrderControlAttribute)firstAttribute).getDelegateAttribute();
               firstAttribute = firstAttributeDelegate;
            }

            if (firstAttribute instanceof SimpleAttribute || this.standingQueryIndexes.get(QueryFactory.not(QueryFactory.has(firstAttribute))) != null) {
               Iterator var15 = this.getIndexesOnAttribute(firstAttribute).iterator();

               while(var15.hasNext()) {
                  Index index = (Index)var15.next();
                  if (index instanceof SortedKeyStatisticsAttributeIndex && !index.isQuantized()) {
                     indexForOrdering = (SortedKeyStatisticsAttributeIndex)index;
                     break;
                  }
               }
            }

            if (queryLog != null) {
               queryLog.log("indexForOrdering: " + (indexForOrdering == null ? null : indexForOrdering.getClass().getSimpleName()));
            }

            if (indexForOrdering != null) {
               double querySelectivity;
               if (selectivityThreshold == 1.0) {
                  querySelectivity = 0.0;
               } else if (!indexForOrdering.supportsQuery(QueryFactory.has(firstAttribute), queryOptions)) {
                  querySelectivity = 1.0;
               } else {
                  int queryCardinality = this.retrieveRecursive(query, queryOptions).getMergeCost();
                  int indexCardinality = indexForOrdering.retrieve(QueryFactory.has(firstAttribute), queryOptions).getMergeCost();
                  if (queryLog != null) {
                     queryLog.log("queryCardinality: " + queryCardinality);
                     queryLog.log("indexCardinality: " + indexCardinality);
                  }

                  if (indexCardinality == 0) {
                     querySelectivity = 1.0;
                  } else if (queryCardinality > indexCardinality) {
                     querySelectivity = 0.0;
                  } else {
                     querySelectivity = 1.0 - (double)queryCardinality / (double)indexCardinality;
                  }
               }

               if (queryLog != null) {
                  queryLog.log("querySelectivity: " + querySelectivity);
                  queryLog.log("selectivityThreshold: " + selectivityThreshold);
               }

               if (querySelectivity > selectivityThreshold) {
                  indexForOrdering = null;
               }
            }
         }
      }

      ResultSet resultSet;
      if (indexForOrdering != null) {
         resultSet = this.retrieveWithIndexOrdering(query, queryOptions, orderByOption, indexForOrdering);
         if (queryLog != null) {
            queryLog.log("orderingStrategy: index");
         }
      } else {
         resultSet = this.retrieveWithoutIndexOrdering(query, queryOptions, orderByOption);
         if (queryLog != null) {
            queryLog.log("orderingStrategy: materialize");
         }
      }

      return new CloseableResultSet(resultSet, query, queryOptions) {
         public void close() {
            super.close();
            CloseableRequestResources.closeForQueryOptions(queryOptions);
         }
      };
   }

   ResultSet retrieveWithoutIndexOrdering(Query query, QueryOptions queryOptions, OrderByOption orderByOption) {
      ResultSet resultSet = this.retrieveRecursive(query, queryOptions);
      boolean applyMaterializedDeduplication = DeduplicationOption.isMaterialize(queryOptions);
      if (orderByOption != null) {
         Comparator comparator = new AttributeOrdersComparator(orderByOption.getAttributeOrders(), queryOptions);
         resultSet = new MaterializedOrderedResultSet((ResultSet)resultSet, comparator, applyMaterializedDeduplication);
      } else if (applyMaterializedDeduplication) {
         resultSet = new MaterializedDeduplicatedResultSet((ResultSet)resultSet);
      }

      return (ResultSet)resultSet;
   }

   ResultSet retrieveWithIndexOrdering(final Query query, final QueryOptions queryOptions, OrderByOption orderByOption, final SortedKeyStatisticsIndex indexForOrdering) {
      final List allSortOrders = orderByOption.getAttributeOrders();
      final AttributeOrder primarySortOrder = (AttributeOrder)allSortOrders.get(0);
      final OrderControlAttribute orderControlAttribute = primarySortOrder.getAttribute() instanceof OrderControlAttribute ? (OrderControlAttribute)primarySortOrder.getAttribute() : null;
      final Attribute primarySortAttribute = orderControlAttribute == null ? primarySortOrder.getAttribute() : orderControlAttribute.getDelegateAttribute();
      final boolean primarySortDescending = primarySortOrder.isDescending();
      final boolean attributeCanHaveZeroValues = !(primarySortAttribute instanceof SimpleAttribute);
      final boolean attributeCanHaveMoreThanOneValue = !(primarySortAttribute instanceof SimpleAttribute) && !(primarySortAttribute instanceof SimpleNullableAttribute);
      final RangeBounds rangeBoundsFromQuery = getBoundsFromQuery(query, primarySortAttribute);
      return new ResultSet() {
         public Iterator iterator() {
            Iterator mainResults = CollectionQueryEngine.this.retrieveWithIndexOrderingMainResults(query, queryOptions, indexForOrdering, allSortOrders, rangeBoundsFromQuery, attributeCanHaveMoreThanOneValue, primarySortDescending);
            Object combinedResults;
            if (attributeCanHaveZeroValues) {
               Iterator missingResults = CollectionQueryEngine.this.retrieveWithIndexOrderingMissingResults(query, queryOptions, primarySortAttribute, allSortOrders, attributeCanHaveMoreThanOneValue);
               if (orderControlAttribute instanceof OrderMissingFirstAttribute) {
                  combinedResults = ConcatenatingIterator.concatenate(Arrays.asList(missingResults, mainResults));
               } else if (orderControlAttribute instanceof OrderMissingLastAttribute) {
                  combinedResults = ConcatenatingIterator.concatenate(Arrays.asList(mainResults, missingResults));
               } else if (primarySortOrder.isDescending()) {
                  combinedResults = ConcatenatingIterator.concatenate(Arrays.asList(mainResults, missingResults));
               } else {
                  combinedResults = ConcatenatingIterator.concatenate(Arrays.asList(missingResults, mainResults));
               }
            } else {
               combinedResults = mainResults;
            }

            if (attributeCanHaveMoreThanOneValue) {
               combinedResults = new MaterializedDeduplicatedIterator((Iterator)combinedResults);
            }

            return (Iterator)combinedResults;
         }

         public boolean contains(Object object) {
            ResultSet rs = CollectionQueryEngine.this.retrieveWithoutIndexOrdering(query, queryOptions, (OrderByOption)null);

            boolean var3;
            try {
               var3 = rs.contains(object);
            } finally {
               rs.close();
            }

            return var3;
         }

         public boolean matches(Object object) {
            return query.matches(object, queryOptions);
         }

         public Query getQuery() {
            return query;
         }

         public QueryOptions getQueryOptions() {
            return queryOptions;
         }

         public int getRetrievalCost() {
            ResultSet rs = CollectionQueryEngine.this.retrieveWithoutIndexOrdering(query, queryOptions, (OrderByOption)null);

            int var2;
            try {
               var2 = rs.getRetrievalCost();
            } finally {
               rs.close();
            }

            return var2;
         }

         public int getMergeCost() {
            ResultSet rs = CollectionQueryEngine.this.retrieveWithoutIndexOrdering(query, queryOptions, (OrderByOption)null);

            int var2;
            try {
               var2 = rs.getMergeCost();
            } finally {
               rs.close();
            }

            return var2;
         }

         public int size() {
            ResultSet rs = CollectionQueryEngine.this.retrieveWithoutIndexOrdering(query, queryOptions, (OrderByOption)null);

            int var2;
            try {
               var2 = rs.size();
            } finally {
               rs.close();
            }

            return var2;
         }

         public void close() {
         }
      };
   }

   Iterator retrieveWithIndexOrderingMainResults(Query query, QueryOptions queryOptions, SortedKeyStatisticsIndex indexForOrdering, List allSortOrders, RangeBounds rangeBoundsFromQuery, boolean attributeCanHaveMoreThanOneValue, boolean primarySortDescending) {
      CloseableRequestResources.CloseableResourceGroup closeableResourceGroup = CloseableRequestResources.forQueryOptions(queryOptions).addGroup();
      List sortOrdersForBucket = determineAdditionalSortOrdersForIndexOrdering(allSortOrders, attributeCanHaveMoreThanOneValue, indexForOrdering, queryOptions);
      final CloseableIterator keysAndValuesInRange = getKeysAndValuesInRange(indexForOrdering, rangeBoundsFromQuery, primarySortDescending, queryOptions);
      closeableResourceGroup.add(keysAndValuesInRange);
      Object sorted;
      if (sortOrdersForBucket.isEmpty()) {
         sorted = new LazyIterator() {
            protected Object computeNext() {
               return keysAndValuesInRange.hasNext() ? ((KeyValue)keysAndValuesInRange.next()).getValue() : this.endOfData();
            }
         };
      } else {
         sorted = IteratorUtil.concatenate(IteratorUtil.groupAndSort(keysAndValuesInRange, new AttributeOrdersComparator(sortOrdersForBucket, queryOptions)));
      }

      return this.filterIndexOrderingCandidateResults((Iterator)sorted, query, queryOptions);
   }

   Iterator retrieveWithIndexOrderingMissingResults(Query query, QueryOptions queryOptions, Attribute primarySortAttribute, List allSortOrders, boolean attributeCanHaveMoreThanOneValue) {
      CloseableRequestResources.CloseableResourceGroup closeableResourceGroup = CloseableRequestResources.forQueryOptions(queryOptions).addGroup();
      Not missingValuesQuery = QueryFactory.not(QueryFactory.has(primarySortAttribute));
      ResultSet missingResults = this.retrieveRecursive(missingValuesQuery, queryOptions);
      closeableResourceGroup.add(missingResults);
      Iterator missingResultsIterator = missingResults.iterator();
      missingResultsIterator = this.filterIndexOrderingCandidateResults(missingResultsIterator, query, queryOptions);
      Index indexForMissingObjects = (Index)this.standingQueryIndexes.get(missingValuesQuery);
      List sortOrdersForBucket = determineAdditionalSortOrdersForIndexOrdering(allSortOrders, attributeCanHaveMoreThanOneValue, indexForMissingObjects, queryOptions);
      if (!sortOrdersForBucket.isEmpty()) {
         Comparator comparator = new AttributeOrdersComparator(sortOrdersForBucket, queryOptions);
         missingResultsIterator = IteratorUtil.materializedSort(missingResultsIterator, comparator);
      }

      return missingResultsIterator;
   }

   Iterator filterIndexOrderingCandidateResults(Iterator sortedCandidateResults, final Query query, QueryOptions queryOptions) {
      boolean indexMergeStrategyEnabled = FlagsEnabled.isFlagEnabled(queryOptions, EngineFlags.PREFER_INDEX_MERGE_STRATEGY);
      if (indexMergeStrategyEnabled) {
         final ResultSet indexAcceleratedQueryResults = this.retrieveWithoutIndexOrdering(query, queryOptions, (OrderByOption)null);
         if (indexAcceleratedQueryResults.getRetrievalCost() != Integer.MAX_VALUE) {
            CloseableRequestResources.CloseableResourceGroup closeableResourceGroup = CloseableRequestResources.forQueryOptions(queryOptions).addGroup();
            closeableResourceGroup.add(indexAcceleratedQueryResults);
            return new FilteringIterator(sortedCandidateResults, queryOptions) {
               public boolean isValid(Object object, QueryOptions queryOptions) {
                  return indexAcceleratedQueryResults.contains(object);
               }
            };
         }

         indexAcceleratedQueryResults.close();
      }

      return new FilteringIterator(sortedCandidateResults, queryOptions) {
         public boolean isValid(Object object, QueryOptions queryOptions) {
            return query.matches(object, queryOptions);
         }
      };
   }

   static Persistence getPersistenceFromQueryOptions(QueryOptions queryOptions) {
      Persistence persistence = (Persistence)queryOptions.get(Persistence.class);
      if (persistence == null) {
         throw new IllegalStateException("A required Persistence object was not supplied in query options");
      } else {
         return persistence;
      }
   }

   static List determineAdditionalSortOrdersForIndexOrdering(List allSortOrders, boolean attributeCanHaveMoreThanOneValue, Index index, QueryOptions queryOptions) {
      return !index.isQuantized() && (!attributeCanHaveMoreThanOneValue || FlagsEnabled.isFlagEnabled(queryOptions, EngineFlags.INDEX_ORDERING_ALLOW_FAST_ORDERING_OF_MULTI_VALUED_ATTRIBUTES)) ? allSortOrders.subList(1, allSortOrders.size()) : allSortOrders;
   }

   static CloseableIterator getKeysAndValuesInRange(SortedKeyStatisticsIndex index, RangeBounds queryBounds, boolean descending, QueryOptions queryOptions) {
      return !descending ? index.getKeysAndValues(queryBounds.lowerBound, queryBounds.lowerInclusive, queryBounds.upperBound, queryBounds.upperInclusive, queryOptions).iterator() : index.getKeysAndValuesDescending(queryBounds.lowerBound, queryBounds.lowerInclusive, queryBounds.upperBound, queryBounds.upperInclusive, queryOptions).iterator();
   }

   static RangeBounds getBoundsFromQuery(Query query, Attribute attribute) {
      Comparable lowerBound = null;
      Comparable upperBound = null;
      boolean lowerInclusive = false;
      boolean upperInclusive = false;
      List candidateRangeQueries = Collections.emptyList();
      if (query instanceof SimpleQuery) {
         candidateRangeQueries = Collections.singletonList((SimpleQuery)query);
      } else if (query instanceof And) {
         And and = (And)query;
         if (and.hasSimpleQueries()) {
            candidateRangeQueries = and.getSimpleQueries();
         }
      }

      Iterator var10 = candidateRangeQueries.iterator();

      while(var10.hasNext()) {
         SimpleQuery candidate = (SimpleQuery)var10.next();
         if (attribute.equals(candidate.getAttribute())) {
            if (candidate instanceof GreaterThan) {
               GreaterThan bound = (GreaterThan)candidate;
               lowerBound = bound.getValue();
               lowerInclusive = bound.isValueInclusive();
            } else if (candidate instanceof LessThan) {
               LessThan bound = (LessThan)candidate;
               upperBound = bound.getValue();
               upperInclusive = bound.isValueInclusive();
            } else if (candidate instanceof Between) {
               Between bound = (Between)candidate;
               lowerBound = bound.getLowerValue();
               lowerInclusive = bound.isLowerInclusive();
               upperBound = bound.getUpperValue();
               upperInclusive = bound.isUpperInclusive();
            }
         }
      }

      return new RangeBounds(lowerBound, lowerInclusive, upperBound, upperInclusive);
   }

   ResultSet retrieveRecursive(Query query, final QueryOptions queryOptions) {
      final boolean indexMergeStrategyEnabled = FlagsEnabled.isFlagEnabled(queryOptions, EngineFlags.PREFER_INDEX_MERGE_STRATEGY);
      Index standingQueryIndex = (Index)this.standingQueryIndexes.get(query);
      if (standingQueryIndex != null) {
         return standingQueryIndex instanceof StandingQueryIndex ? standingQueryIndex.retrieve(query, queryOptions) : standingQueryIndex.retrieve(QueryFactory.equal(QueryFactory.forStandingQuery(query), Boolean.TRUE), queryOptions);
      } else if (query instanceof SimpleQuery) {
         return this.getResultSetWithLowestRetrievalCost((SimpleQuery)query, queryOptions);
      } else if (query instanceof And) {
         final And and = (And)query;
         if (!this.compoundIndexes.isEmpty()) {
            CompoundQuery compoundQuery = CompoundQuery.fromAndQueryIfSuitable(and);
            if (compoundQuery != null) {
               CompoundIndex compoundIndex = (CompoundIndex)this.compoundIndexes.get(compoundQuery.getCompoundAttribute());
               if (compoundIndex != null && compoundIndex.supportsQuery(compoundQuery, queryOptions)) {
                  return compoundIndex.retrieve(compoundQuery, queryOptions);
               }
            }
         }

         return new ResultSetIntersection(new Iterable() {
            public Iterator iterator() {
               return new UnmodifiableIterator() {
                  boolean needToProcessSimpleQueries = and.hasSimpleQueries();
                  Iterator logicalQueriesIterator = and.getLogicalQueries().iterator();

                  public boolean hasNext() {
                     return this.needToProcessSimpleQueries || this.logicalQueriesIterator.hasNext();
                  }

                  public ResultSet next() {
                     if (this.needToProcessSimpleQueries) {
                        this.needToProcessSimpleQueries = false;
                        return CollectionQueryEngine.this.retrieveIntersection(and.getSimpleQueries(), queryOptions, indexMergeStrategyEnabled);
                     } else {
                        return CollectionQueryEngine.this.retrieveRecursive((Query)this.logicalQueriesIterator.next(), queryOptions);
                     }
                  }
               };
            }
         }, query, queryOptions, indexMergeStrategyEnabled);
      } else if (query instanceof Or) {
         final Or or = (Or)query;
         final QueryOptions queryOptionsForOrUnion;
         if (or.isDisjoint()) {
            queryOptionsForOrUnion = new QueryOptions(queryOptions.getOptions()) {
               public Object get(Object key) {
                  return DeduplicationOption.class.equals(key) ? null : super.get(key);
               }
            };
         } else {
            queryOptionsForOrUnion = queryOptions;
         }

         Iterable resultSetsToUnion = new Iterable() {
            public Iterator iterator() {
               return new UnmodifiableIterator() {
                  boolean needToProcessSimpleQueries = or.hasSimpleQueries();
                  Iterator logicalQueriesIterator = or.getLogicalQueries().iterator();

                  public boolean hasNext() {
                     return this.needToProcessSimpleQueries || this.logicalQueriesIterator.hasNext();
                  }

                  public ResultSet next() {
                     if (this.needToProcessSimpleQueries) {
                        this.needToProcessSimpleQueries = false;
                        return CollectionQueryEngine.this.retrieveUnion(or.getSimpleQueries(), queryOptionsForOrUnion);
                     } else {
                        return CollectionQueryEngine.this.retrieveRecursive((Query)this.logicalQueriesIterator.next(), queryOptions);
                     }
                  }
               };
            }
         };
         Object union;
         if (DeduplicationOption.isLogicalElimination(queryOptionsForOrUnion)) {
            union = new ResultSetUnion(resultSetsToUnion, query, queryOptions, indexMergeStrategyEnabled);
         } else {
            union = new ResultSetUnionAll(resultSetsToUnion, query, queryOptions);
         }

         if (((ResultSet)union).getRetrievalCost() == Integer.MAX_VALUE) {
            union = new FilteringResultSet(this.getEntireCollectionAsResultSet(query, queryOptions), or, queryOptions) {
               public boolean isValid(Object object, QueryOptions queryOptions) {
                  return or.matches(object, queryOptions);
               }
            };
         }

         return (ResultSet)union;
      } else if (query instanceof Not) {
         Not not = (Not)query;
         ResultSet resultSetToNegate = this.retrieveRecursive(not.getNegatedQuery(), queryOptions);
         return new ResultSetDifference(this.getEntireCollectionAsResultSet(query, queryOptions), resultSetToNegate, query, queryOptions, indexMergeStrategyEnabled);
      } else {
         throw new IllegalStateException("Unexpected type of query object: " + getClassNameNullSafe(query));
      }
   }

   ResultSet retrieveIntersection(Collection queries, QueryOptions queryOptions, boolean indexMergeStrategyEnabled) {
      List resultSets = new ArrayList(queries.size());
      Iterator var5 = queries.iterator();

      while(var5.hasNext()) {
         SimpleQuery query = (SimpleQuery)var5.next();
         ResultSet resultSet = this.getResultSetWithLowestRetrievalCost(query, queryOptions);
         resultSets.add(resultSet);
      }

      Query query = queries.size() == 1 ? (Query)queries.iterator().next() : new And(queries);
      return new ResultSetIntersection(resultSets, (Query)query, queryOptions, indexMergeStrategyEnabled);
   }

   ResultSet retrieveUnion(final Collection queries, final QueryOptions queryOptions) {
      Iterable resultSetsToUnion = new Iterable() {
         public Iterator iterator() {
            return new UnmodifiableIterator() {
               Iterator queriesIterator = queries.iterator();

               public boolean hasNext() {
                  return this.queriesIterator.hasNext();
               }

               public ResultSet next() {
                  return CollectionQueryEngine.this.getResultSetWithLowestRetrievalCost((SimpleQuery)this.queriesIterator.next(), queryOptions);
               }
            };
         }
      };
      Query query = queries.size() == 1 ? (Query)queries.iterator().next() : new Or(queries);
      if (DeduplicationOption.isLogicalElimination(queryOptions)) {
         boolean indexMergeStrategyEnabled = FlagsEnabled.isFlagEnabled(queryOptions, EngineFlags.PREFER_INDEX_MERGE_STRATEGY);
         return new ResultSetUnion(resultSetsToUnion, (Query)query, queryOptions, indexMergeStrategyEnabled);
      } else {
         return new ResultSetUnionAll(resultSetsToUnion, (Query)query, queryOptions);
      }
   }

   public boolean addAll(final ObjectSet objectSet, final QueryOptions queryOptions) {
      this.ensureMutable();
      final FlagHolder modified = new FlagHolder();
      this.forEachIndexDo(new IndexOperation() {
         public boolean perform(Index index) {
            FlagHolder var10000 = modified;
            var10000.value |= index.addAll(objectSet, queryOptions);
            return true;
         }
      });
      return modified.value;
   }

   public boolean removeAll(final ObjectSet objectSet, final QueryOptions queryOptions) {
      this.ensureMutable();
      final FlagHolder modified = new FlagHolder();
      this.forEachIndexDo(new IndexOperation() {
         public boolean perform(Index index) {
            FlagHolder var10000 = modified;
            var10000.value |= index.removeAll(objectSet, queryOptions);
            return true;
         }
      });
      return modified.value;
   }

   public void clear(final QueryOptions queryOptions) {
      this.ensureMutable();
      this.forEachIndexDo(new IndexOperation() {
         public boolean perform(Index index) {
            index.clear(queryOptions);
            return true;
         }
      });
   }

   public boolean isMutable() {
      return this.allIndexesAreMutable;
   }

   void ensureMutable() {
      if (!this.allIndexesAreMutable) {
         throw new IllegalStateException("Cannot modify indexes, an immutable index has been added.");
      }
   }

   boolean forEachIndexDo(IndexOperation indexOperation) {
      Iterable attributeIndexes = new ConcatenatingIterable(this.attributeIndexes.values());
      Iterator var3 = attributeIndexes.iterator();

      boolean continueIterating;
      do {
         if (!var3.hasNext()) {
            Iterable compoundIndexes = this.compoundIndexes.values();
            Iterator var9 = compoundIndexes.iterator();

            boolean continueIterating;
            do {
               if (!var9.hasNext()) {
                  Iterable standingQueryIndexes = this.standingQueryIndexes.values();
                  Iterator var12 = standingQueryIndexes.iterator();

                  boolean continueIterating;
                  do {
                     if (!var12.hasNext()) {
                        return indexOperation.perform(this.fallbackIndex);
                     }

                     Index index = (Index)var12.next();
                     continueIterating = indexOperation.perform(index);
                  } while(continueIterating);

                  return false;
               }

               Index index = (Index)var9.next();
               continueIterating = indexOperation.perform(index);
            } while(continueIterating);

            return false;
         }

         Index index = (Index)var3.next();
         continueIterating = indexOperation.perform(index);
      } while(continueIterating);

      return false;
   }

   static String getClassNameNullSafe(Object object) {
      return object == null ? null : object.getClass().getName();
   }

   static class FlagHolder {
      boolean value = false;
   }

   interface IndexOperation {
      boolean perform(Index var1);
   }

   static class RangeBounds {
      final Comparable lowerBound;
      final boolean lowerInclusive;
      final Comparable upperBound;
      final Boolean upperInclusive;

      public RangeBounds(Comparable lowerBound, boolean lowerInclusive, Comparable upperBound, Boolean upperInclusive) {
         this.lowerBound = lowerBound;
         this.lowerInclusive = lowerInclusive;
         this.upperBound = upperBound;
         this.upperInclusive = upperInclusive;
      }
   }
}
