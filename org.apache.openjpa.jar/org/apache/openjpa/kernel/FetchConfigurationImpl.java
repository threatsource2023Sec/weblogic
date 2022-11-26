package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.rop.EagerResultList;
import org.apache.openjpa.lib.rop.ListResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.rop.SimpleResultList;
import org.apache.openjpa.lib.rop.WindowResultList;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.NoTransactionException;
import org.apache.openjpa.util.UserException;

public class FetchConfigurationImpl implements FetchConfiguration, Cloneable {
   private static final Localizer _loc = Localizer.forPackage(FetchConfigurationImpl.class);
   private final ConfigurationState _state;
   private FetchConfigurationImpl _parent;
   private String _fromField;
   private Class _fromType;
   private String _directRelationOwner;
   private boolean _load;
   private int _availableRecursion;
   private int _availableDepth;

   public FetchConfigurationImpl() {
      this((ConfigurationState)null);
   }

   protected FetchConfigurationImpl(ConfigurationState state) {
      this._load = true;
      this._state = state == null ? new ConfigurationState() : state;
      this._availableDepth = this._state.maxFetchDepth;
   }

   public StoreContext getContext() {
      return this._state.ctx;
   }

   public void setContext(StoreContext ctx) {
      if (ctx != null && this._state.ctx != null && ctx != this._state.ctx) {
         throw new InternalException();
      } else {
         this._state.ctx = ctx;
         if (ctx != null) {
            OpenJPAConfiguration conf = ctx.getConfiguration();
            this.setFetchBatchSize(conf.getFetchBatchSize());
            this.setFlushBeforeQueries(conf.getFlushBeforeQueriesConstant());
            this.setLockTimeout(conf.getLockTimeout());
            this.clearFetchGroups();
            this.addFetchGroups(Arrays.asList(conf.getFetchGroupsList()));
            this.setMaxFetchDepth(conf.getMaxFetchDepth());
         }
      }
   }

   public Object clone() {
      FetchConfigurationImpl clone = this.newInstance((ConfigurationState)null);
      clone._state.ctx = this._state.ctx;
      clone._parent = this._parent;
      clone._fromField = this._fromField;
      clone._fromType = this._fromType;
      clone._directRelationOwner = this._directRelationOwner;
      clone._load = this._load;
      clone._availableRecursion = this._availableRecursion;
      clone._availableDepth = this._availableDepth;
      clone.copy(this);
      return clone;
   }

   protected FetchConfigurationImpl newInstance(ConfigurationState state) {
      return new FetchConfigurationImpl(state);
   }

   public void copy(FetchConfiguration fetch) {
      this.setFetchBatchSize(fetch.getFetchBatchSize());
      this.setMaxFetchDepth(fetch.getMaxFetchDepth());
      this.setQueryCacheEnabled(fetch.getQueryCacheEnabled());
      this.setFlushBeforeQueries(fetch.getFlushBeforeQueries());
      this.setLockTimeout(fetch.getLockTimeout());
      this.clearFetchGroups();
      this.addFetchGroups(fetch.getFetchGroups());
      this.clearFields();
      this.addFields(fetch.getFields());
      this._state.readLockLevel = fetch.getReadLockLevel();
      this._state.writeLockLevel = fetch.getWriteLockLevel();
   }

   public int getFetchBatchSize() {
      return this._state.fetchBatchSize;
   }

   public FetchConfiguration setFetchBatchSize(int fetchBatchSize) {
      if (fetchBatchSize == -99 && this._state.ctx != null) {
         fetchBatchSize = this._state.ctx.getConfiguration().getFetchBatchSize();
      }

      if (fetchBatchSize != -99) {
         this._state.fetchBatchSize = fetchBatchSize;
      }

      return this;
   }

   public int getMaxFetchDepth() {
      return this._state.maxFetchDepth;
   }

   public FetchConfiguration setMaxFetchDepth(int depth) {
      if (depth == -99 && this._state.ctx != null) {
         depth = this._state.ctx.getConfiguration().getMaxFetchDepth();
      }

      if (depth != -99) {
         this._state.maxFetchDepth = depth;
         if (this._parent == null) {
            this._availableDepth = depth;
         }
      }

      return this;
   }

   public boolean getQueryCacheEnabled() {
      return this._state.queryCache;
   }

   public FetchConfiguration setQueryCacheEnabled(boolean cache) {
      this._state.queryCache = cache;
      return this;
   }

   public int getFlushBeforeQueries() {
      return this._state.flushQuery;
   }

   public FetchConfiguration setFlushBeforeQueries(int flush) {
      if (flush == -99 && this._state.ctx != null) {
         this._state.flushQuery = this._state.ctx.getConfiguration().getFlushBeforeQueriesConstant();
      } else if (flush != -99) {
         this._state.flushQuery = flush;
      }

      return this;
   }

   public Set getFetchGroups() {
      return this._state.fetchGroups == null ? Collections.EMPTY_SET : this._state.fetchGroups;
   }

   public boolean hasFetchGroup(String group) {
      return this._state.fetchGroups != null && (this._state.fetchGroups.contains(group) || this._state.fetchGroups.contains("all"));
   }

   public FetchConfiguration addFetchGroup(String name) {
      if (StringUtils.isEmpty(name)) {
         throw new UserException(_loc.get("null-fg"));
      } else {
         this.lock();

         try {
            if (this._state.fetchGroups == null) {
               this._state.fetchGroups = new HashSet();
            }

            this._state.fetchGroups.add(name);
         } finally {
            this.unlock();
         }

         return this;
      }
   }

   public FetchConfiguration addFetchGroups(Collection groups) {
      if (groups != null && !groups.isEmpty()) {
         Iterator itr = groups.iterator();

         while(itr.hasNext()) {
            this.addFetchGroup((String)itr.next());
         }

         return this;
      } else {
         return this;
      }
   }

   public FetchConfiguration removeFetchGroup(String group) {
      this.lock();

      try {
         if (this._state.fetchGroups != null) {
            this._state.fetchGroups.remove(group);
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public FetchConfiguration removeFetchGroups(Collection groups) {
      this.lock();

      try {
         if (this._state.fetchGroups != null) {
            this._state.fetchGroups.removeAll(groups);
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public FetchConfiguration clearFetchGroups() {
      this.lock();

      try {
         if (this._state.fetchGroups != null) {
            this._state.fetchGroups.clear();
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public FetchConfiguration resetFetchGroups() {
      this.clearFetchGroups();
      if (this._state.ctx != null) {
         this.addFetchGroups(Arrays.asList(this._state.ctx.getConfiguration().getFetchGroupsList()));
      }

      return this;
   }

   public Set getFields() {
      return this._state.fields == null ? Collections.EMPTY_SET : this._state.fields;
   }

   public boolean hasField(String field) {
      return this._state.fields != null && this._state.fields.contains(field);
   }

   public FetchConfiguration addField(String field) {
      if (StringUtils.isEmpty(field)) {
         throw new UserException(_loc.get("null-field"));
      } else {
         this.lock();

         try {
            if (this._state.fields == null) {
               this._state.fields = new HashSet();
            }

            this._state.fields.add(field);
         } finally {
            this.unlock();
         }

         return this;
      }
   }

   public FetchConfiguration addFields(Collection fields) {
      if (fields != null && !fields.isEmpty()) {
         this.lock();

         try {
            if (this._state.fields == null) {
               this._state.fields = new HashSet();
            }

            this._state.fields.addAll(fields);
         } finally {
            this.unlock();
         }

         return this;
      } else {
         return this;
      }
   }

   public FetchConfiguration removeField(String field) {
      this.lock();

      try {
         if (this._state.fields != null) {
            this._state.fields.remove(field);
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public FetchConfiguration removeFields(Collection fields) {
      this.lock();

      try {
         if (this._state.fields != null) {
            this._state.fields.removeAll(fields);
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public FetchConfiguration clearFields() {
      this.lock();

      try {
         if (this._state.fields != null) {
            this._state.fields.clear();
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public int getLockTimeout() {
      return this._state.lockTimeout;
   }

   public FetchConfiguration setLockTimeout(int timeout) {
      if (timeout == -99 && this._state.ctx != null) {
         this._state.lockTimeout = this._state.ctx.getConfiguration().getLockTimeout();
      } else if (timeout != -99) {
         this._state.lockTimeout = timeout;
      }

      return this;
   }

   public int getReadLockLevel() {
      return this._state.readLockLevel;
   }

   public FetchConfiguration setReadLockLevel(int level) {
      if (this._state.ctx == null) {
         return this;
      } else {
         this.lock();

         try {
            this.assertActiveTransaction();
            if (level == -99) {
               this._state.readLockLevel = this._state.ctx.getConfiguration().getReadLockLevelConstant();
            } else {
               this._state.readLockLevel = level;
            }
         } finally {
            this.unlock();
         }

         return this;
      }
   }

   public int getWriteLockLevel() {
      return this._state.writeLockLevel;
   }

   public FetchConfiguration setWriteLockLevel(int level) {
      if (this._state.ctx == null) {
         return this;
      } else {
         this.lock();

         try {
            this.assertActiveTransaction();
            if (level == -99) {
               this._state.writeLockLevel = this._state.ctx.getConfiguration().getWriteLockLevelConstant();
            } else {
               this._state.writeLockLevel = level;
            }
         } finally {
            this.unlock();
         }

         return this;
      }
   }

   public ResultList newResultList(ResultObjectProvider rop) {
      if (rop instanceof ListResultObjectProvider) {
         return new SimpleResultList(rop);
      } else if (this._state.fetchBatchSize < 0) {
         return new EagerResultList(rop);
      } else {
         return (ResultList)(rop.supportsRandomAccess() ? new SimpleResultList(rop) : new WindowResultList(rop));
      }
   }

   private void assertActiveTransaction() {
      if (this._state.ctx != null && !this._state.ctx.isActive()) {
         throw new NoTransactionException(_loc.get("not-active"));
      }
   }

   public void setHint(String name, Object value) {
      this.lock();

      try {
         if (this._state.hints == null) {
            this._state.hints = new HashMap();
         }

         this._state.hints.put(name, value);
      } finally {
         this.unlock();
      }

   }

   public Object getHint(String name) {
      return this._state.hints == null ? null : this._state.hints.get(name);
   }

   public Set getRootClasses() {
      return this._state.rootClasses == null ? Collections.EMPTY_SET : this._state.rootClasses;
   }

   public FetchConfiguration setRootClasses(Collection classes) {
      this.lock();

      try {
         if (this._state.rootClasses != null) {
            this._state.rootClasses.clear();
         }

         if (classes != null && !classes.isEmpty()) {
            if (this._state.rootClasses == null) {
               this._state.rootClasses = new HashSet(classes);
            } else {
               this._state.rootClasses.addAll(classes);
            }
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public Set getRootInstances() {
      return this._state.rootInstances == null ? Collections.EMPTY_SET : this._state.rootInstances;
   }

   public FetchConfiguration setRootInstances(Collection instances) {
      this.lock();

      try {
         if (this._state.rootInstances != null) {
            this._state.rootInstances.clear();
         }

         if (instances != null && !instances.isEmpty()) {
            if (this._state.rootInstances == null) {
               this._state.rootInstances = new HashSet(instances);
            } else {
               this._state.rootInstances.addAll(instances);
            }
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public void lock() {
      if (this._state.ctx != null) {
         this._state.ctx.lock();
      }

   }

   public void unlock() {
      if (this._state.ctx != null) {
         this._state.ctx.unlock();
      }

   }

   public int requiresFetch(FieldMetaData fm) {
      if (!this.includes(fm)) {
         return 0;
      } else {
         Class type = getRelationType(fm);
         if (type == null) {
            return 1;
         } else if (this._availableDepth == 0) {
            return 0;
         } else if (this._parent == null) {
            return 1;
         } else {
            int rdepth = this.getAvailableRecursionDepth(fm, type, false);
            if (rdepth != -1 && rdepth <= 0) {
               return 0;
            } else {
               return StringUtils.equals(this._directRelationOwner, fm.getFullName()) ? 2 : 1;
            }
         }
      }
   }

   public boolean requiresLoad() {
      return this._load;
   }

   public FetchConfiguration traverse(FieldMetaData fm) {
      Class type = getRelationType(fm);
      if (type == null) {
         return this;
      } else {
         FetchConfigurationImpl clone = this.newInstance(this._state);
         clone._parent = this;
         clone._availableDepth = reduce(this._availableDepth);
         clone._fromField = fm.getFullName(false);
         clone._fromType = type;
         clone._availableRecursion = this.getAvailableRecursionDepth(fm, type, true);
         if (StringUtils.equals(this._directRelationOwner, fm.getFullName())) {
            clone._load = false;
         } else {
            clone._load = this._load;
         }

         FieldMetaData owner = fm.getMappedByMetaData();
         if (owner != null && owner.getTypeCode() == 15) {
            clone._directRelationOwner = owner.getFullName();
         }

         return clone;
      }
   }

   private boolean includes(FieldMetaData fmd) {
      if ((!fmd.isInDefaultFetchGroup() || !this.hasFetchGroup("default")) && !this.hasFetchGroup("all") && !this.hasField(fmd.getFullName(false))) {
         String[] fgs = fmd.getCustomFetchGroups();

         for(int i = 0; i < fgs.length; ++i) {
            if (this.hasFetchGroup(fgs[i])) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private int getAvailableRecursionDepth(FieldMetaData fm, Class type, boolean traverse) {
      int avail = Integer.MIN_VALUE;

      for(FetchConfigurationImpl f = this; f != null; f = f._parent) {
         if (ImplHelper.isAssignable(f._fromType, type)) {
            avail = f._availableRecursion;
            if (traverse) {
               avail = reduce(avail);
            }
            break;
         }
      }

      if (avail == 0) {
         return 0;
      } else {
         ClassMetaData meta = fm.getDefiningMetaData();
         int max = Integer.MIN_VALUE;
         if (fm.isInDefaultFetchGroup()) {
            max = meta.getFetchGroup("default").getRecursionDepth(fm);
         }

         String[] groups = fm.getCustomFetchGroups();

         for(int i = 0; max != -1 && i < groups.length; ++i) {
            if (this.hasFetchGroup(groups[i])) {
               int cur = meta.getFetchGroup(groups[i]).getRecursionDepth(fm);
               if (cur == -1 || cur > max) {
                  max = cur;
               }
            }
         }

         if (traverse && max != Integer.MIN_VALUE && ImplHelper.isAssignable(meta.getDescribedType(), type)) {
            max = reduce(max);
         }

         if (avail == Integer.MIN_VALUE && max == Integer.MIN_VALUE) {
            int def = 1;
            return traverse && ImplHelper.isAssignable(meta.getDescribedType(), type) ? def - 1 : def;
         } else if (avail != Integer.MIN_VALUE && avail != -1) {
            return max != Integer.MIN_VALUE && max != -1 ? Math.min(max, avail) : avail;
         } else {
            return max;
         }
      }
   }

   private static Class getRelationType(FieldMetaData fm) {
      if (fm.isDeclaredTypePC()) {
         return fm.getDeclaredType();
      } else if (fm.getElement().isDeclaredTypePC()) {
         return fm.getElement().getDeclaredType();
      } else {
         return fm.getKey().isDeclaredTypePC() ? fm.getKey().getDeclaredType() : null;
      }
   }

   private static int reduce(int d) {
      if (d == 0) {
         return 0;
      } else {
         if (d != -1) {
            --d;
         }

         return d;
      }
   }

   FetchConfiguration getParent() {
      return this._parent;
   }

   boolean isRoot() {
      return this._parent == null;
   }

   FetchConfiguration getRoot() {
      return (FetchConfiguration)(this.isRoot() ? this : this._parent.getRoot());
   }

   int getAvailableFetchDepth() {
      return this._availableDepth;
   }

   int getAvailableRecursionDepth() {
      return this._availableRecursion;
   }

   String getTraversedFromField() {
      return this._fromField;
   }

   Class getTraversedFromType() {
      return this._fromType;
   }

   List getPath() {
      return this.isRoot() ? Collections.EMPTY_LIST : this.trackPath(new ArrayList());
   }

   List trackPath(List path) {
      if (this._parent != null) {
         this._parent.trackPath(path);
      }

      path.add(this);
      return path;
   }

   public String toString() {
      return "FetchConfiguration@" + System.identityHashCode(this) + " (" + this._availableDepth + ")" + this.getPathString();
   }

   private String getPathString() {
      List path = this.getPath();
      if (path.isEmpty()) {
         return "";
      } else {
         StringBuffer buf = (new StringBuffer()).append(": ");
         Iterator itr = path.iterator();

         while(itr.hasNext()) {
            buf.append(((FetchConfigurationImpl)itr.next()).getTraversedFromField());
            if (itr.hasNext()) {
               buf.append("->");
            }
         }

         return buf.toString();
      }
   }

   protected static class ConfigurationState implements Serializable {
      public transient StoreContext ctx = null;
      public int fetchBatchSize = 0;
      public int maxFetchDepth = 1;
      public boolean queryCache = true;
      public int flushQuery = 0;
      public int lockTimeout = -1;
      public int readLockLevel = 0;
      public int writeLockLevel = 0;
      public Set fetchGroups = null;
      public Set fields = null;
      public Set rootClasses;
      public Set rootInstances;
      public Map hints = null;
   }
}
