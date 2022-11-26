package com.googlecode.cqengine.index.support;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.AttributeIndex;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.persistence.support.FilteredObjectStore;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.logical.And;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.WrappedResultSet;
import java.util.Collection;
import java.util.HashSet;

public abstract class PartialIndex implements AttributeIndex {
   static final int INDEX_RETRIEVAL_COST_DELTA = -5;
   protected final Query filterQuery;
   protected final Attribute attribute;
   protected volatile AttributeIndex backingIndex;

   protected PartialIndex(Attribute attribute, Query filterQuery) {
      this.attribute = attribute;
      this.filterQuery = filterQuery;
   }

   protected AttributeIndex backingIndex() {
      if (this.backingIndex == null) {
         synchronized(this) {
            if (this.backingIndex == null) {
               this.backingIndex = this.createBackingIndex();
            }
         }
      }

      return this.backingIndex;
   }

   public Attribute getAttribute() {
      return this.backingIndex().getAttribute();
   }

   public Query getFilterQuery() {
      return this.filterQuery;
   }

   public AttributeIndex getBackingIndex() {
      return this.backingIndex;
   }

   public boolean supportsQuery(Query query, QueryOptions queryOptions) {
      Query rootQuery = (Query)queryOptions.get((Object)"ROOT_QUERY");
      return supportsQueryInternal(this.backingIndex(), this.filterQuery, rootQuery, query, queryOptions);
   }

   static boolean supportsQueryInternal(AttributeIndex backingIndex, Query filterQuery, Query rootQuery, Query branchQuery, QueryOptions queryOptions) {
      if (!backingIndex.supportsQuery(branchQuery, queryOptions)) {
         return false;
      } else if (filterQuery.equals(rootQuery)) {
         return true;
      } else if (!(rootQuery instanceof And)) {
         return false;
      } else {
         And rootAndQuery = (And)rootQuery;
         if (rootAndQuery.getChildQueries().contains(filterQuery)) {
            return true;
         } else if (!(filterQuery instanceof And)) {
            return false;
         } else {
            And filterAndQuery = (And)filterQuery;
            return rootAndQuery.getChildQueries().containsAll(filterAndQuery.getChildQueries());
         }
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof PartialIndex)) {
         return false;
      } else {
         PartialIndex that = (PartialIndex)o;
         return !this.filterQuery.equals(that.filterQuery) ? false : this.backingIndex().equals(that.backingIndex());
      }
   }

   public int hashCode() {
      int result = this.filterQuery.hashCode();
      result = 31 * result + this.backingIndex().hashCode();
      return result;
   }

   public boolean isMutable() {
      return this.backingIndex().isMutable();
   }

   public boolean isQuantized() {
      return this.backingIndex().isQuantized();
   }

   public ResultSet retrieve(Query query, QueryOptions queryOptions) {
      return new WrappedResultSet(this.backingIndex().retrieve(query, queryOptions)) {
         public int getRetrievalCost() {
            return super.getRetrievalCost() + -5;
         }
      };
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public void init(ObjectStore objectStore, QueryOptions queryOptions) {
      this.backingIndex().init(new FilteredObjectStore(objectStore, this.filterQuery), queryOptions);
   }

   public boolean addAll(ObjectSet objectSet, QueryOptions queryOptions) {
      Collection matchingSubset = this.filter(objectSet, queryOptions);
      return this.backingIndex().addAll(ObjectSet.fromCollection(matchingSubset), queryOptions);
   }

   public boolean removeAll(ObjectSet objectSet, QueryOptions queryOptions) {
      Collection matchingSubset = this.filter(objectSet, queryOptions);
      return this.backingIndex().removeAll(ObjectSet.fromCollection(matchingSubset), queryOptions);
   }

   public void clear(QueryOptions queryOptions) {
      this.backingIndex().clear(queryOptions);
   }

   protected Collection filter(ObjectSet objects, QueryOptions queryOptions) {
      CloseableIterator objectsIterator = objects.iterator();

      try {
         Collection matchingSubset = new HashSet();

         while(objectsIterator.hasNext()) {
            Object candidate = objectsIterator.next();
            if (this.filterQuery.matches(candidate, queryOptions)) {
               matchingSubset.add(candidate);
            }
         }

         HashSet var9 = matchingSubset;
         return var9;
      } finally {
         objectsIterator.close();
      }
   }

   protected abstract AttributeIndex createBackingIndex();
}
