package kodo.jdo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.jdo.FetchPlan;
import javax.jdo.JDOUserException;
import org.apache.openjpa.kernel.DelegatingFetchConfiguration;
import org.apache.openjpa.kernel.DetachState;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.lib.util.Localizer;

public class FetchPlanImpl implements KodoFetchPlan, DetachState {
   private static final Class[] EMPTY_CLASSES = new Class[0];
   private static final Localizer _loc = Localizer.forPackage(FetchPlanImpl.class);
   private final DelegatingFetchConfiguration _fetch;

   public FetchPlanImpl(FetchConfiguration fetch) {
      this._fetch = this.newDelegatingFetchConfiguration(fetch);
   }

   protected DelegatingFetchConfiguration newDelegatingFetchConfiguration(FetchConfiguration fetch) {
      return new DelegatingFetchConfiguration(fetch, JDOExceptions.TRANSLATOR);
   }

   public FetchConfiguration getDelegate() {
      return this._fetch.getDelegate();
   }

   public FetchPlan addGroup(String group) {
      this._fetch.addFetchGroup(group);
      return this;
   }

   public FetchPlan removeGroup(String group) {
      this._fetch.removeFetchGroup(group);
      return this;
   }

   public FetchPlan clearGroups() {
      this._fetch.clearFetchGroups();
      return this;
   }

   public Set getGroups() {
      return this._fetch.getFetchGroups();
   }

   public FetchPlan setGroups(Collection groups) {
      this._fetch.clearFetchGroups();
      this._fetch.addFetchGroups(groups);
      return this;
   }

   public FetchPlan setGroups(String[] groups) {
      return this.setGroups((Collection)Arrays.asList(groups));
   }

   public FetchPlan setGroup(String group) {
      this._fetch.clearFetchGroups();
      return this.addGroup(group);
   }

   public int getFetchSize() {
      return this._fetch.getFetchBatchSize();
   }

   public FetchPlan setFetchSize(int size) {
      this._fetch.setFetchBatchSize(size);
      return this;
   }

   public int getMaxFetchDepth() {
      return this._fetch.getMaxFetchDepth();
   }

   public FetchPlan setMaxFetchDepth(int depth) {
      if (depth == 0) {
         throw new JDOUserException(_loc.get("invalid-max-depth", new Integer(depth)).getMessage());
      } else {
         this._fetch.setMaxFetchDepth(depth);
         return this;
      }
   }

   public int getDetachmentOptions() {
      int state = this._fetch.getContext().getDetachState();
      if (state == 0) {
         return 3;
      } else {
         return state == 2 ? 9 : 1;
      }
   }

   public FetchPlan setDetachmentOptions(int opts) {
      if ((opts & 8) != 0) {
         this._fetch.getContext().setDetachState(2);
      } else if (opts == 3) {
         this._fetch.getContext().setDetachState(0);
      } else {
         if (opts != 2 && opts != 0) {
            throw new UnsupportedOptionException();
         }

         this._fetch.getContext().setDetachState(1);
      }

      return this;
   }

   public Class[] getDetachmentRootClasses() {
      Collection result = this._fetch.getRootClasses();
      return result != null && !result.isEmpty() ? (Class[])((Class[])result.toArray(new Class[result.size()])) : EMPTY_CLASSES;
   }

   public FetchPlan setDetachmentRootClasses(Class[] cls) {
      if (cls != null && cls.length != 0) {
         this._fetch.setRootClasses(Arrays.asList(cls));
      }

      return this;
   }

   public Collection getDetachmentRoots() {
      return this._fetch.getRootInstances();
   }

   public FetchPlan setDetachmentRoots(Collection roots) {
      if (roots != null && !roots.isEmpty()) {
         this._fetch.setRootInstances(roots);
      }

      return this;
   }

   public boolean getQueryResultCache() {
      return this._fetch.getQueryCacheEnabled();
   }

   public KodoFetchPlan setQueryResultCache(boolean cache) {
      this._fetch.setQueryCacheEnabled(cache);
      return this;
   }

   public int getFlushBeforeQueries() {
      return this._fetch.getFlushBeforeQueries();
   }

   public KodoFetchPlan setFlushBeforeQueries(int flush) {
      this._fetch.setFlushBeforeQueries(flush);
      return this;
   }

   public KodoFetchPlan resetGroups() {
      this._fetch.resetFetchGroups();
      return this;
   }

   public Set getFields() {
      return this._fetch.getFields();
   }

   public boolean hasField(Class cls, String field) {
      return this._fetch.hasField(toFieldName(cls, field));
   }

   public KodoFetchPlan addField(String field) {
      this._fetch.addField(field);
      return this;
   }

   public KodoFetchPlan addField(Class cls, String field) {
      return this.addField(toFieldName(cls, field));
   }

   public KodoFetchPlan setFields(String[] fields) {
      return this.setFields((Collection)Arrays.asList(fields));
   }

   public KodoFetchPlan setFields(Class cls, String[] fields) {
      return this.setFields(cls, (Collection)Arrays.asList(fields));
   }

   public KodoFetchPlan setFields(Collection fields) {
      this._fetch.clearFields();
      this._fetch.addFields(fields);
      return this;
   }

   public KodoFetchPlan setFields(Class cls, Collection fields) {
      return this.setFields(toFieldNames(cls, fields));
   }

   public KodoFetchPlan removeField(String field) {
      this._fetch.removeField(field);
      return this;
   }

   public KodoFetchPlan removeField(Class cls, String field) {
      return this.removeField(toFieldName(cls, field));
   }

   protected static String toFieldName(Class cls, String field) {
      return cls.getName() + "." + field;
   }

   protected static Collection toFieldNames(Class cls, Collection fields) {
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

   public KodoFetchPlan clearFields() {
      this._fetch.clearFields();
      return this;
   }

   public int getLockTimeout() {
      return this._fetch.getLockTimeout();
   }

   public KodoFetchPlan setLockTimeout(int timeout) {
      this._fetch.setLockTimeout(timeout);
      return this;
   }

   public int getReadLockLevel() {
      return this._fetch.getReadLockLevel();
   }

   public KodoFetchPlan setReadLockLevel(int level) {
      this._fetch.setReadLockLevel(level);
      return this;
   }

   public int getWriteLockLevel() {
      return this._fetch.getWriteLockLevel();
   }

   public KodoFetchPlan setWriteLockLevel(int level) {
      this._fetch.setWriteLockLevel(level);
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
