package com.googlecode.cqengine.persistence.onheap;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.support.indextype.OnHeapTypeIndex;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.support.ConcurrentOnHeapObjectStore;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.option.QueryOptions;

public class OnHeapPersistence implements Persistence {
   final SimpleAttribute primaryKeyAttribute;
   final int initialCapacity;
   final float loadFactor;
   final int concurrencyLevel;

   public OnHeapPersistence() {
      this((SimpleAttribute)null, 16, 0.75F, 16);
   }

   public OnHeapPersistence(SimpleAttribute primaryKeyAttribute) {
      this(primaryKeyAttribute, 16, 0.75F, 16);
   }

   public OnHeapPersistence(SimpleAttribute primaryKeyAttribute, int initialCapacity, float loadFactor, int concurrencyLevel) {
      this.primaryKeyAttribute = primaryKeyAttribute;
      this.initialCapacity = initialCapacity;
      this.loadFactor = loadFactor;
      this.concurrencyLevel = concurrencyLevel;
   }

   public boolean supportsIndex(Index index) {
      return index instanceof OnHeapTypeIndex;
   }

   public ObjectStore createObjectStore() {
      return new ConcurrentOnHeapObjectStore(this.initialCapacity, this.loadFactor, this.concurrencyLevel);
   }

   public void openRequestScopeResources(QueryOptions queryOptions) {
   }

   public void closeRequestScopeResources(QueryOptions queryOptions) {
   }

   public SimpleAttribute getPrimaryKeyAttribute() {
      return this.primaryKeyAttribute;
   }

   public static OnHeapPersistence onPrimaryKey(SimpleAttribute primaryKeyAttribute) {
      return new OnHeapPersistence(primaryKeyAttribute);
   }

   public static OnHeapPersistence withoutPrimaryKey() {
      return withoutPrimaryKey_Internal();
   }

   static OnHeapPersistence withoutPrimaryKey_Internal() {
      return new OnHeapPersistence();
   }
}
