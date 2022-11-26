package com.googlecode.cqengine.persistence.composite;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.ConnectionManager;
import com.googlecode.cqengine.index.sqlite.RequestScopeConnectionManager;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CompositePersistence implements Persistence {
   final Persistence primaryPersistence;
   final Persistence secondaryPersistence;
   final List additionalPersistences;
   final ConcurrentMap indexPersistences = new ConcurrentHashMap(1, 1.0F, 1);

   public CompositePersistence(Persistence primaryPersistence, Persistence secondaryPersistence, List additionalPersistences) {
      validatePersistenceArguments(primaryPersistence, secondaryPersistence, additionalPersistences);
      this.primaryPersistence = primaryPersistence;
      this.secondaryPersistence = secondaryPersistence;
      this.additionalPersistences = additionalPersistences;
   }

   public SimpleAttribute getPrimaryKeyAttribute() {
      return this.primaryPersistence.getPrimaryKeyAttribute();
   }

   public boolean supportsIndex(Index index) {
      Persistence persistence = this.getPersistenceForIndexOrNullWithCaching(index);
      return persistence != null;
   }

   public Persistence getPersistenceForIndex(Index index) {
      Persistence persistence = this.getPersistenceForIndexOrNullWithCaching(index);
      if (persistence == null) {
         throw new IllegalStateException("No persistence is configured for index: " + index);
      } else {
         return persistence;
      }
   }

   public ObjectStore createObjectStore() {
      return this.primaryPersistence.createObjectStore();
   }

   Persistence getPersistenceForIndexOrNullWithCaching(Index index) {
      Persistence persistence = (Persistence)this.indexPersistences.get(index);
      if (persistence == null) {
         persistence = this.getPersistenceForIndexOrNull(index);
         if (persistence != null) {
            Persistence existing = (Persistence)this.indexPersistences.putIfAbsent(index, persistence);
            if (existing != null) {
               persistence = existing;
            }
         }
      }

      return persistence;
   }

   Persistence getPersistenceForIndexOrNull(Index index) {
      if (this.primaryPersistence.supportsIndex(index)) {
         return this.primaryPersistence;
      } else if (this.secondaryPersistence.supportsIndex(index)) {
         return this.secondaryPersistence;
      } else {
         Iterator var2 = this.additionalPersistences.iterator();

         Persistence additionalPersistence;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            additionalPersistence = (Persistence)var2.next();
         } while(!additionalPersistence.supportsIndex(index));

         return additionalPersistence;
      }
   }

   public Persistence getPrimaryPersistence() {
      return this.primaryPersistence;
   }

   public Persistence getSecondaryPersistence() {
      return this.secondaryPersistence;
   }

   public List getAdditionalPersistences() {
      return this.additionalPersistences;
   }

   static void validatePersistenceArguments(Persistence primaryPersistence, Persistence secondaryPersistence, List additionalPersistences) {
      SimpleAttribute primaryKeyAttribute = validatePersistenceArgument(primaryPersistence, (SimpleAttribute)null);
      primaryKeyAttribute = validatePersistenceArgument(secondaryPersistence, primaryKeyAttribute);
      Iterator var4 = additionalPersistences.iterator();

      while(var4.hasNext()) {
         Persistence additionalPersistence = (Persistence)var4.next();
         validatePersistenceArgument(additionalPersistence, primaryKeyAttribute);
      }

   }

   static SimpleAttribute validatePersistenceArgument(Persistence persistence, SimpleAttribute primaryKeyAttribute) {
      if (persistence == null) {
         throw new NullPointerException("The Persistence argument cannot be null.");
      } else if (persistence.getPrimaryKeyAttribute() == null) {
         throw new IllegalArgumentException("All Persistence implementations in a CompositePersistence must have a primary key.");
      } else {
         if (primaryKeyAttribute == null) {
            primaryKeyAttribute = persistence.getPrimaryKeyAttribute();
         } else if (!primaryKeyAttribute.equals(persistence.getPrimaryKeyAttribute())) {
            throw new IllegalArgumentException("All Persistence implementations must be on the same primary key.");
         }

         return primaryKeyAttribute;
      }
   }

   public void openRequestScopeResources(QueryOptions queryOptions) {
      if (queryOptions.get(ConnectionManager.class) == null) {
         queryOptions.put(ConnectionManager.class, new RequestScopeConnectionManager(this));
      }

   }

   public void closeRequestScopeResources(QueryOptions queryOptions) {
      ConnectionManager connectionManager = (ConnectionManager)queryOptions.get(ConnectionManager.class);
      if (connectionManager instanceof RequestScopeConnectionManager) {
         ((RequestScopeConnectionManager)connectionManager).close();
         queryOptions.remove(ConnectionManager.class);
      }

   }

   public static CompositePersistence of(Persistence primaryPersistence, Persistence secondaryPersistence, List additionalPersistences) {
      return new CompositePersistence(primaryPersistence, secondaryPersistence, additionalPersistences);
   }

   public static CompositePersistence of(Persistence primaryPersistence, Persistence secondaryPersistence) {
      return new CompositePersistence(primaryPersistence, secondaryPersistence, Collections.emptyList());
   }
}
