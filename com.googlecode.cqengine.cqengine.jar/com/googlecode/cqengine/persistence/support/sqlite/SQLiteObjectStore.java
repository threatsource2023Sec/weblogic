package com.googlecode.cqengine.persistence.support.sqlite;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.sqlite.SQLiteIdentityIndex;
import com.googlecode.cqengine.index.sqlite.SQLitePersistence;
import com.googlecode.cqengine.index.support.CloseableIterator;
import com.googlecode.cqengine.persistence.support.ObjectSet;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.iterator.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SQLiteObjectStore implements ObjectStore {
   final SQLitePersistence persistence;
   final SQLiteIdentityIndex backingIndex;
   final SimpleAttribute primaryKeyAttribute;
   final Class objectType;

   public SQLiteObjectStore(SQLitePersistence persistence) {
      this.persistence = persistence;
      this.objectType = persistence.getPrimaryKeyAttribute().getObjectType();
      this.primaryKeyAttribute = persistence.getPrimaryKeyAttribute();
      this.backingIndex = persistence.createIdentityIndex();
   }

   public void init(QueryOptions queryOptions) {
      this.backingIndex.init(this, queryOptions);
   }

   public SQLitePersistence getPersistence() {
      return this.persistence;
   }

   public SQLiteIdentityIndex getBackingIndex() {
      return this.backingIndex;
   }

   public int size(QueryOptions queryOptions) {
      return this.backingIndex.retrieve(QueryFactory.has(this.primaryKeyAttribute), queryOptions).size();
   }

   public boolean contains(Object o, QueryOptions queryOptions) {
      Comparable objectId = (Comparable)this.primaryKeyAttribute.getValue(o, queryOptions);
      return this.backingIndex.retrieve(QueryFactory.equal(this.primaryKeyAttribute, objectId), queryOptions).size() > 0;
   }

   public CloseableIterator iterator(QueryOptions queryOptions) {
      final ResultSet rs = this.backingIndex.retrieve(QueryFactory.has(this.primaryKeyAttribute), queryOptions);
      final Iterator i = rs.iterator();

      class CloseableIteratorImpl extends UnmodifiableIterator implements CloseableIterator {
         public boolean hasNext() {
            return i.hasNext();
         }

         public Object next() {
            return i.next();
         }

         public void close() {
            rs.close();
         }
      }

      return new CloseableIteratorImpl();
   }

   public boolean isEmpty(QueryOptions queryOptions) {
      return this.size(queryOptions) == 0;
   }

   public boolean add(Object object, QueryOptions queryOptions) {
      return this.backingIndex.addAll(ObjectSet.fromCollection(Collections.singleton(object)), queryOptions);
   }

   public boolean remove(Object o, QueryOptions queryOptions) {
      return this.backingIndex.removeAll(ObjectSet.fromCollection(Collections.singleton(o)), queryOptions);
   }

   public boolean containsAll(Collection c, QueryOptions queryOptions) {
      Iterator var3 = c.iterator();

      Object o;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         o = var3.next();
      } while(this.contains(o, queryOptions));

      return false;
   }

   public boolean addAll(Collection c, QueryOptions queryOptions) {
      return this.backingIndex.addAll(ObjectSet.fromCollection(c), queryOptions);
   }

   public boolean retainAll(Collection c, QueryOptions queryOptions) {
      Collection objectsToRemove = new ArrayList();
      ResultSet allObjects = this.backingIndex.retrieve(QueryFactory.has(this.primaryKeyAttribute), queryOptions);

      try {
         Iterator var5 = allObjects.iterator();

         while(var5.hasNext()) {
            Object object = var5.next();
            if (!c.contains(object)) {
               objectsToRemove.add(object);
            }
         }
      } finally {
         allObjects.close();
      }

      return this.backingIndex.removeAll(ObjectSet.fromCollection(objectsToRemove), queryOptions);
   }

   public boolean removeAll(Collection c, QueryOptions queryOptions) {
      return this.backingIndex.removeAll(ObjectSet.fromCollection(c), queryOptions);
   }

   public void clear(QueryOptions queryOptions) {
      this.backingIndex.clear(queryOptions);
   }
}
