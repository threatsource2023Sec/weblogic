package org.apache.openjpa.kernel;

import java.util.Collection;
import java.util.Set;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingFetchConfiguration implements FetchConfiguration {
   private final FetchConfiguration _fetch;
   private final DelegatingFetchConfiguration _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingFetchConfiguration(FetchConfiguration fetch) {
      this(fetch, (RuntimeExceptionTranslator)null);
   }

   public DelegatingFetchConfiguration(FetchConfiguration fetch, RuntimeExceptionTranslator trans) {
      this._fetch = fetch;
      if (fetch instanceof DelegatingFetchConfiguration) {
         this._del = (DelegatingFetchConfiguration)fetch;
      } else {
         this._del = null;
      }

      this._trans = trans;
   }

   public FetchConfiguration getDelegate() {
      return this._fetch;
   }

   public FetchConfiguration getInnermostDelegate() {
      return this._del == null ? this._fetch : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingFetchConfiguration) {
            other = ((DelegatingFetchConfiguration)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public StoreContext getContext() {
      try {
         return this._fetch.getContext();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setContext(StoreContext ctx) {
      try {
         this._fetch.setContext(ctx);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getFetchBatchSize() {
      try {
         return this._fetch.getFetchBatchSize();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setFetchBatchSize(int fetchBatchSize) {
      try {
         this._fetch.setFetchBatchSize(fetchBatchSize);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getMaxFetchDepth() {
      try {
         return this._fetch.getMaxFetchDepth();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setMaxFetchDepth(int depth) {
      try {
         this._fetch.setMaxFetchDepth(depth);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Set getRootInstances() {
      try {
         return this._fetch.getRootInstances();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setRootInstances(Collection roots) {
      try {
         this._fetch.setRootInstances(roots);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Set getRootClasses() {
      try {
         return this._fetch.getRootClasses();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setRootClasses(Collection roots) {
      try {
         this._fetch.setRootClasses(roots);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getQueryCacheEnabled() {
      try {
         return this._fetch.getQueryCacheEnabled();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setQueryCacheEnabled(boolean cache) {
      try {
         this._fetch.setQueryCacheEnabled(cache);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getFlushBeforeQueries() {
      try {
         return this._fetch.getFlushBeforeQueries();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setFlushBeforeQueries(int flush) {
      try {
         this._fetch.setFlushBeforeQueries(flush);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Set getFetchGroups() {
      try {
         return this._fetch.getFetchGroups();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean hasFetchGroup(String group) {
      try {
         return this._fetch.hasFetchGroup(group);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration addFetchGroup(String group) {
      try {
         this._fetch.addFetchGroup(group);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration addFetchGroups(Collection groups) {
      try {
         this._fetch.addFetchGroups(groups);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration removeFetchGroup(String group) {
      try {
         this._fetch.removeFetchGroup(group);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration removeFetchGroups(Collection groups) {
      try {
         this._fetch.removeFetchGroups(groups);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration clearFetchGroups() {
      try {
         this._fetch.clearFetchGroups();
         return this;
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration resetFetchGroups() {
      try {
         this._fetch.resetFetchGroups();
         return this;
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Set getFields() {
      try {
         return this._fetch.getFields();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean hasField(String field) {
      try {
         return this._fetch.hasField(field);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration addField(String field) {
      try {
         this._fetch.addField(field);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration addFields(Collection fields) {
      try {
         this._fetch.addFields(fields);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration removeField(String field) {
      try {
         this._fetch.removeField(field);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration removeFields(Collection fields) {
      try {
         this._fetch.removeFields(fields);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public FetchConfiguration clearFields() {
      try {
         this._fetch.clearFields();
         return this;
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public int getLockTimeout() {
      try {
         return this._fetch.getLockTimeout();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setLockTimeout(int timeout) {
      try {
         this._fetch.setLockTimeout(timeout);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getReadLockLevel() {
      try {
         return this._fetch.getReadLockLevel();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setReadLockLevel(int level) {
      try {
         this._fetch.setReadLockLevel(level);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getWriteLockLevel() {
      try {
         return this._fetch.getWriteLockLevel();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration setWriteLockLevel(int level) {
      try {
         this._fetch.setWriteLockLevel(level);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public ResultList newResultList(ResultObjectProvider rop) {
      try {
         return this._fetch.newResultList(rop);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void copy(FetchConfiguration fetch) {
      try {
         this._fetch.copy(fetch);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object clone() {
      try {
         return this._fetch.clone();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setHint(String name, Object value) {
      try {
         this._fetch.setHint(name, value);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object getHint(String name) {
      try {
         return this._fetch.getHint(name);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int requiresFetch(FieldMetaData fmd) {
      try {
         return this._fetch.requiresFetch(fmd);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean requiresLoad() {
      try {
         return this._fetch.requiresLoad();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration traverse(FieldMetaData fmd) {
      try {
         return this._fetch.traverse(fmd);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void lock() {
      try {
         this._fetch.lock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void unlock() {
      try {
         this._fetch.unlock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }
}
