package kodo.runtime;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kodo.jdo.UserException;
import org.apache.openjpa.kernel.LockLevels;
import org.apache.openjpa.kernel.QueryFlushModes;

/** @deprecated */
public class FetchConfiguration implements Serializable, Cloneable, LockLevels, QueryFlushModes {
   public static final int DEFAULT = -99;
   public static final String FETCH_GROUP_DEFAULT = "default";
   public static final String FETCH_GROUP_ALL = "all";
   public static final int EAGER_NONE = 0;
   public static final int EAGER_JOIN = 1;
   public static final int EAGER_PARALLEL = 2;
   public static final int EAGER_FETCH_NONE = 0;
   public static final int EAGER_FETCH_SINGLE = 1;
   public static final int EAGER_FETCH_MULTIPLE = 2;
   public static final int QUERY_FLUSH_TRUE = 0;
   public static final int QUERY_FLUSH_FALSE = 1;
   public static final int QUERY_FLUSH_WITH_CONNECTION = 2;
   private static Method _getEagerFetchMode = null;
   private static Method _setEagerFetchMode = null;
   private static Method _getSubclassFetchMode = null;
   private static Method _setSubclassFetchMode = null;
   private transient KodoPersistenceManager _pm = null;
   private final org.apache.openjpa.kernel.FetchConfiguration _fetch;

   public FetchConfiguration(KodoPersistenceManager pm, org.apache.openjpa.kernel.FetchConfiguration fetch) {
      this._pm = pm;
      this._fetch = fetch;
   }

   public KodoPersistenceManager getPersistenceManager() {
      return this._pm;
   }

   public int getEagerFetchMode() {
      if (_getEagerFetchMode != null && this._fetch.getClass().getName().startsWith("org.apache.openjpa.jdbc")) {
         try {
            return ((Number)_getEagerFetchMode.invoke(this._fetch, (Object[])null)).intValue();
         } catch (RuntimeException var2) {
            throw var2;
         } catch (Exception var3) {
            throw new UserException(var3.getMessage(), new Throwable[]{var3}, (Object)null);
         }
      } else {
         return 0;
      }
   }

   public void setEagerFetchMode(int mode) {
      if (_setEagerFetchMode != null && this._fetch.getClass().getName().startsWith("org.apache.openjpa.jdbc")) {
         try {
            _setEagerFetchMode.invoke(this._fetch, new Integer(mode));
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw new UserException(var4.getMessage(), new Throwable[]{var4}, (Object)null);
         }
      }
   }

   public int getSubclassFetchMode() {
      if (_getSubclassFetchMode != null && this._fetch.getClass().getName().startsWith("org.apache.openjpa.jdbc")) {
         try {
            return ((Number)_getSubclassFetchMode.invoke(this._fetch, (Object[])null)).intValue();
         } catch (RuntimeException var2) {
            throw var2;
         } catch (Exception var3) {
            throw new UserException(var3.getMessage(), new Throwable[]{var3}, (Object)null);
         }
      } else {
         return 0;
      }
   }

   public void setSubclassFetchMode(int mode) {
      if (_setSubclassFetchMode != null && this._fetch.getClass().getName().startsWith("org.apache.openjpa.jdbc")) {
         try {
            _setSubclassFetchMode.invoke(this._fetch, new Integer(mode));
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw new UserException(var4.getMessage(), new Throwable[]{var4}, (Object)null);
         }
      }
   }

   public int getFetchBatchSize() {
      return this._fetch.getFetchBatchSize();
   }

   public void setFetchBatchSize(int fetchBatchSize) {
      this._fetch.setFetchBatchSize(fetchBatchSize);
   }

   public boolean isQueryCacheEnabled() {
      return this._fetch.getQueryCacheEnabled();
   }

   public void setQueryCacheEnabled(boolean cache) {
      this._fetch.setQueryCacheEnabled(cache);
   }

   public int getFlushBeforeQueries() {
      return this._fetch.getFlushBeforeQueries();
   }

   public void setFlushBeforeQueries(int flush) {
      this._fetch.setFlushBeforeQueries(flush);
   }

   public Set getFetchGroups() {
      return this._fetch.getFetchGroups();
   }

   public boolean hasFetchGroup(String group) {
      this._fetch.hasFetchGroup(group);
      return true;
   }

   public boolean addFetchGroup(String group) {
      this._fetch.addFetchGroup(group);
      return true;
   }

   public boolean addFetchGroups(String[] groups) {
      return this.addFetchGroups((Collection)Arrays.asList(groups));
   }

   public boolean addFetchGroups(Collection groups) {
      this._fetch.addFetchGroups(groups);
      return true;
   }

   public boolean removeFetchGroup(String group) {
      this._fetch.removeFetchGroup(group);
      return true;
   }

   public boolean removeFetchGroups(String[] groups) {
      return this.removeFetchGroups((Collection)Arrays.asList(groups));
   }

   public boolean removeFetchGroups(Collection groups) {
      this._fetch.removeFetchGroups(groups);
      return true;
   }

   public void clearFetchGroups() {
      this._fetch.clearFetchGroups();
   }

   public void resetFetchGroups() {
      this._fetch.resetFetchGroups();
   }

   public Set getFields() {
      return this._fetch.getFields();
   }

   public boolean hasField(String field) {
      return this._fetch.hasField(field);
   }

   public boolean hasField(Class cls, String field) {
      return this.hasField(cls.getName() + "." + field);
   }

   public boolean addField(String field) {
      this._fetch.addField(field);
      return true;
   }

   public boolean addField(Class cls, String field) {
      return this.addField(cls.getName() + "." + field);
   }

   public boolean addFields(String[] fields) {
      return this.addFields((Collection)Arrays.asList(fields));
   }

   public boolean addFields(Class cls, String[] fields) {
      return this.addFields(cls, (Collection)Arrays.asList(fields));
   }

   public boolean addFields(Collection fields) {
      this._fetch.addFields(fields);
      return true;
   }

   public boolean addFields(Class cls, Collection fields) {
      String[] fullNames = new String[fields.size()];
      int i = 0;

      for(Iterator itr = fields.iterator(); itr.hasNext(); fullNames[i++] = cls.getName() + "." + itr.next()) {
      }

      return this.addFields(fullNames);
   }

   public boolean removeField(String field) {
      this._fetch.removeField(field);
      return true;
   }

   public boolean removeField(Class cls, String field) {
      return this.removeField(cls.getName() + "." + field);
   }

   public boolean removeFields(String[] fields) {
      return this.removeFields((Collection)Arrays.asList(fields));
   }

   public boolean removeFields(Class cls, String[] fields) {
      return this.removeFields(cls, (Collection)Arrays.asList(fields));
   }

   public boolean removeFields(Collection fields) {
      this._fetch.removeFields(fields);
      return true;
   }

   public boolean removeFields(Class cls, Collection fields) {
      String[] fullNames = new String[fields.size()];
      int i = 0;

      for(Iterator itr = fields.iterator(); itr.hasNext(); fullNames[i++] = cls.getName() + "." + itr.next()) {
      }

      return this.removeFields((Collection)Arrays.asList(fullNames));
   }

   public void clearFields() {
      this._fetch.clearFields();
   }

   public int getLockTimeout() {
      return this._fetch.getLockTimeout();
   }

   public void setLockTimeout(int timeout) {
      this._fetch.setLockTimeout(timeout);
   }

   public int getReadLockLevel() {
      return this._fetch.getReadLockLevel();
   }

   public void setReadLockLevel(int level) {
      this._fetch.setReadLockLevel(level);
   }

   public int getWriteLockLevel() {
      return this._fetch.getWriteLockLevel();
   }

   public void setWriteLockLevel(int level) {
      this._fetch.setWriteLockLevel(level);
   }

   public Object clone() {
      return new FetchConfiguration(this._pm, (org.apache.openjpa.kernel.FetchConfiguration)this._fetch.clone());
   }

   static {
      try {
         Class c = Class.forName("kodo.jdbc.kernel.JDBCFetchConfiguration");
         _getEagerFetchMode = c.getDeclaredMethod("getEagerFetchMode", (Class[])null);
         _setEagerFetchMode = c.getDeclaredMethod("setEagerFetchMode", Integer.TYPE);
         _getSubclassFetchMode = c.getDeclaredMethod("getSubclassFetchMode", (Class[])null);
         _setSubclassFetchMode = c.getDeclaredMethod("setSubclassFetchMode", Integer.TYPE);
      } catch (Throwable var1) {
      }

   }
}
