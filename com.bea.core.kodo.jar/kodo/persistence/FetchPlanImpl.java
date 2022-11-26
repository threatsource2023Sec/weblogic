package kodo.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.LockModeType;
import org.apache.openjpa.kernel.FetchConfiguration;

/** @deprecated */
public class FetchPlanImpl implements FetchPlan {
   private final org.apache.openjpa.persistence.FetchPlanImpl _delegate;

   public FetchPlanImpl(org.apache.openjpa.persistence.FetchPlanImpl del) {
      this._delegate = del;
   }

   public FetchConfiguration getDelegate() {
      return this._delegate.getDelegate();
   }

   public int getFetchBatchSize() {
      return this._delegate.getFetchBatchSize();
   }

   public int getMaxFetchDepth() {
      return this._delegate.getMaxFetchDepth();
   }

   public FetchPlan setMaxFetchDepth(int depth) {
      this._delegate.setMaxFetchDepth(depth);
      return this;
   }

   public FetchPlanImpl setFetchBatchSize(int fetchBatchSize) {
      this._delegate.setFetchBatchSize(fetchBatchSize);
      return this;
   }

   public boolean getQueryResultCacheEnabled() {
      return this._delegate.getQueryResultCacheEnabled();
   }

   public FetchPlan setQueryResultCacheEnabled(boolean cache) {
      this._delegate.setQueryResultCacheEnabled(cache);
      return this;
   }

   public boolean getQueryResultCache() {
      return this.getQueryResultCacheEnabled();
   }

   public FetchPlan setQueryResultCache(boolean cache) {
      return this.setQueryResultCacheEnabled(cache);
   }

   public Collection getFetchGroups() {
      return this._delegate.getFetchGroups();
   }

   public FetchPlan addFetchGroup(String group) {
      this._delegate.addFetchGroup(group);
      return this;
   }

   public FetchPlan addFetchGroups(String... groups) {
      this._delegate.addFetchGroups(Arrays.asList(groups));
      return this;
   }

   public FetchPlan addFetchGroups(Collection groups) {
      this._delegate.addFetchGroups(groups);
      return this;
   }

   public FetchPlan removeFetchGroup(String group) {
      this._delegate.removeFetchGroup(group);
      return this;
   }

   public FetchPlan removeFetchGroups(String... groups) {
      this._delegate.removeFetchGroups(Arrays.asList(groups));
      return this;
   }

   public FetchPlan removeFetchGroups(Collection groups) {
      this._delegate.removeFetchGroups(groups);
      return this;
   }

   public FetchPlan clearFetchGroups() {
      this._delegate.clearFetchGroups();
      return this;
   }

   public FetchPlan resetFetchGroups() {
      this._delegate.resetFetchGroups();
      return this;
   }

   public Collection getFields() {
      return this._delegate.getFields();
   }

   public boolean hasField(String field) {
      return this._delegate.hasField(field);
   }

   public boolean hasField(Class cls, String field) {
      return this._delegate.hasField(toFieldName(cls, field));
   }

   public FetchPlan addField(String field) {
      this._delegate.addField(field);
      return this;
   }

   public FetchPlan addField(Class cls, String field) {
      this._delegate.addField(toFieldName(cls, field));
      return this;
   }

   public FetchPlan addFields(String... fields) {
      this._delegate.addFields(Arrays.asList(fields));
      return this;
   }

   public FetchPlan addFields(Class cls, String... fields) {
      this._delegate.addFields(cls, Arrays.asList(fields));
      return this;
   }

   public FetchPlan addFields(Collection fields) {
      this._delegate.addFields(fields);
      return this;
   }

   public FetchPlan addFields(Class cls, Collection fields) {
      this._delegate.addFields(toFieldNames(cls, fields));
      return this;
   }

   public FetchPlan removeField(String field) {
      this._delegate.removeField(field);
      return this;
   }

   public FetchPlan removeField(Class cls, String field) {
      this._delegate.removeField(toFieldName(cls, field));
      return this;
   }

   public FetchPlan removeFields(String... fields) {
      this._delegate.removeFields(Arrays.asList(fields));
      return this;
   }

   public FetchPlan removeFields(Class cls, String... fields) {
      this._delegate.removeFields(cls, Arrays.asList(fields));
      return this;
   }

   public FetchPlan removeFields(Collection fields) {
      this._delegate.removeFields(fields);
      return this;
   }

   public FetchPlan removeFields(Class cls, Collection fields) {
      this._delegate.removeFields(toFieldNames(cls, fields));
      return this;
   }

   public FetchPlan clearFields() {
      this._delegate.clearFields();
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
      return this._delegate.getLockTimeout();
   }

   public FetchPlan setLockTimeout(int timeout) {
      this._delegate.setLockTimeout(timeout);
      return this;
   }

   public LockModeType getReadLockMode() {
      return this._delegate.getReadLockMode();
   }

   public FetchPlan setReadLockMode(LockModeType mode) {
      this._delegate.setReadLockMode(mode);
      return this;
   }

   public LockModeType getWriteLockMode() {
      return this._delegate.getWriteLockMode();
   }

   public FetchPlan setWriteLockMode(LockModeType mode) {
      this._delegate.setWriteLockMode(mode);
      return this;
   }

   public int hashCode() {
      return this._delegate.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof FetchPlanImpl) ? false : this._delegate.equals(((FetchPlanImpl)other)._delegate);
      }
   }
}
