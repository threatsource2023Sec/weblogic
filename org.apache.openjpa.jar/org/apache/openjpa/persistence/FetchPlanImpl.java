package org.apache.openjpa.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.LockModeType;
import org.apache.openjpa.kernel.DelegatingFetchConfiguration;
import org.apache.openjpa.kernel.FetchConfiguration;

public class FetchPlanImpl implements FetchPlan {
   private final DelegatingFetchConfiguration _fetch;

   public FetchPlanImpl(FetchConfiguration fetch) {
      this._fetch = this.newDelegatingFetchConfiguration(fetch);
   }

   protected DelegatingFetchConfiguration newDelegatingFetchConfiguration(FetchConfiguration fetch) {
      return new DelegatingFetchConfiguration(fetch, PersistenceExceptions.TRANSLATOR);
   }

   public FetchConfiguration getDelegate() {
      return this._fetch.getDelegate();
   }

   public int getMaxFetchDepth() {
      return this._fetch.getMaxFetchDepth();
   }

   public FetchPlan setMaxFetchDepth(int depth) {
      this._fetch.setMaxFetchDepth(depth);
      return this;
   }

   public int getFetchBatchSize() {
      return this._fetch.getFetchBatchSize();
   }

   public FetchPlan setFetchBatchSize(int fetchBatchSize) {
      this._fetch.setFetchBatchSize(fetchBatchSize);
      return this;
   }

   public boolean getQueryResultCacheEnabled() {
      return this._fetch.getQueryCacheEnabled();
   }

   public FetchPlan setQueryResultCacheEnabled(boolean cache) {
      this._fetch.setQueryCacheEnabled(cache);
      return this;
   }

   public boolean getQueryResultCache() {
      return this.getQueryResultCacheEnabled();
   }

   public FetchPlan setQueryResultCache(boolean cache) {
      return this.setQueryResultCacheEnabled(cache);
   }

   public Collection getFetchGroups() {
      return this._fetch.getFetchGroups();
   }

   public FetchPlan addFetchGroup(String group) {
      this._fetch.addFetchGroup(group);
      return this;
   }

   public FetchPlan addFetchGroups(String... groups) {
      return this.addFetchGroups((Collection)Arrays.asList(groups));
   }

   public FetchPlan addFetchGroups(Collection groups) {
      this._fetch.addFetchGroups(groups);
      return this;
   }

   public FetchPlan removeFetchGroup(String group) {
      this._fetch.removeFetchGroup(group);
      return this;
   }

   public FetchPlan removeFetchGroups(String... groups) {
      return this.removeFetchGroups((Collection)Arrays.asList(groups));
   }

   public FetchPlan removeFetchGroups(Collection groups) {
      this._fetch.removeFetchGroups(groups);
      return this;
   }

   public FetchPlan clearFetchGroups() {
      this._fetch.clearFetchGroups();
      return this;
   }

   public FetchPlan resetFetchGroups() {
      this._fetch.resetFetchGroups();
      return this;
   }

   public Collection getFields() {
      return this._fetch.getFields();
   }

   public boolean hasField(String field) {
      return this._fetch.hasField(field);
   }

   public boolean hasField(Class cls, String field) {
      return this.hasField(toFieldName(cls, field));
   }

   public FetchPlan addField(String field) {
      this._fetch.addField(field);
      return this;
   }

   public FetchPlan addField(Class cls, String field) {
      return this.addField(toFieldName(cls, field));
   }

   public FetchPlan addFields(String... fields) {
      return this.addFields((Collection)Arrays.asList(fields));
   }

   public FetchPlan addFields(Class cls, String... fields) {
      return this.addFields(cls, (Collection)Arrays.asList(fields));
   }

   public FetchPlan addFields(Collection fields) {
      this._fetch.addFields(fields);
      return this;
   }

   public FetchPlan addFields(Class cls, Collection fields) {
      return this.addFields(toFieldNames(cls, fields));
   }

   public FetchPlan removeField(String field) {
      this._fetch.removeField(field);
      return this;
   }

   public FetchPlan removeField(Class cls, String field) {
      return this.removeField(toFieldName(cls, field));
   }

   public FetchPlan removeFields(String... fields) {
      return this.removeFields((Collection)Arrays.asList(fields));
   }

   public FetchPlan removeFields(Class cls, String... fields) {
      return this.removeFields(cls, (Collection)Arrays.asList(fields));
   }

   public FetchPlan removeFields(Collection fields) {
      this._fetch.removeFields(fields);
      return this;
   }

   public FetchPlan removeFields(Class cls, Collection fields) {
      return this.removeFields(toFieldNames(cls, fields));
   }

   public FetchPlan clearFields() {
      this._fetch.clearFields();
      return this;
   }

   private static String toFieldName(Class cls, String field) {
      return cls.getName() + "." + field;
   }

   private static Collection toFieldNames(Class cls, Collection fields) {
      if (fields.isEmpty()) {
         return fields;
      } else {
         Collection names = new ArrayList(fields);
         Iterator itr = fields.iterator();

         while(itr.hasNext()) {
            names.add(toFieldName(cls, (String)itr.next()));
         }

         return names;
      }
   }

   public int getLockTimeout() {
      return this._fetch.getLockTimeout();
   }

   public FetchPlan setLockTimeout(int timeout) {
      this._fetch.setLockTimeout(timeout);
      return this;
   }

   public LockModeType getReadLockMode() {
      return EntityManagerImpl.fromLockLevel(this._fetch.getReadLockLevel());
   }

   public FetchPlan setReadLockMode(LockModeType mode) {
      this._fetch.setReadLockLevel(EntityManagerImpl.toLockLevel(mode));
      return this;
   }

   public LockModeType getWriteLockMode() {
      return EntityManagerImpl.fromLockLevel(this._fetch.getWriteLockLevel());
   }

   public FetchPlan setWriteLockMode(LockModeType mode) {
      this._fetch.setWriteLockLevel(EntityManagerImpl.toLockLevel(mode));
      return this;
   }

   public int hashCode() {
      return this._fetch.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof FetchPlanImpl) ? false : this._fetch.equals(((FetchPlanImpl)other)._fetch);
      }
   }
}
