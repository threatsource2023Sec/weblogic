package com.googlecode.cqengine.persistence.wrapping;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.support.CollectionWrappingObjectStore;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collection;

public class WrappingPersistence implements Persistence {
   final Collection backingCollection;
   final SimpleAttribute primaryKeyAttribute;

   public WrappingPersistence(Collection backingCollection) {
      this(backingCollection, (SimpleAttribute)null);
   }

   public WrappingPersistence(Collection backingCollection, SimpleAttribute primaryKeyAttribute) {
      this.backingCollection = backingCollection;
      this.primaryKeyAttribute = primaryKeyAttribute;
   }

   public boolean supportsIndex(Index index) {
      return index instanceof OnHeapTypeIndex;
   }

   public ObjectStore createObjectStore() {
      return new CollectionWrappingObjectStore(this.backingCollection);
   }

   public void openRequestScopeResources(QueryOptions queryOptions) {
   }

   public void closeRequestScopeResources(QueryOptions queryOptions) {
   }

   public SimpleAttribute getPrimaryKeyAttribute() {
      return this.primaryKeyAttribute;
   }

   public static WrappingPersistence aroundCollectionOnPrimaryKey(Collection collection, SimpleAttribute primaryKeyAttribute) {
      return new WrappingPersistence(collection, primaryKeyAttribute);
   }

   public static WrappingPersistence aroundCollection(Collection collection) {
      return withoutPrimaryKey_Internal(collection);
   }

   static WrappingPersistence withoutPrimaryKey_Internal(Collection collection) {
      return new WrappingPersistence(collection);
   }
}
