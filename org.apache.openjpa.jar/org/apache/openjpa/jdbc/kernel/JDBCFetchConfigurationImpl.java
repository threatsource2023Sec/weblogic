package org.apache.openjpa.jdbc.kernel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FetchConfigurationImpl;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.rop.EagerResultList;
import org.apache.openjpa.lib.rop.ListResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.rop.SimpleResultList;
import org.apache.openjpa.lib.rop.SoftRandomAccessResultList;
import org.apache.openjpa.lib.rop.WindowResultList;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.UserException;

public class JDBCFetchConfigurationImpl extends FetchConfigurationImpl implements JDBCFetchConfiguration {
   private static final Localizer _loc = Localizer.forPackage(JDBCFetchConfigurationImpl.class);
   protected final JDBCConfigurationState _state;

   public JDBCFetchConfigurationImpl() {
      this((FetchConfigurationImpl.ConfigurationState)null, (JDBCConfigurationState)null);
   }

   protected JDBCFetchConfigurationImpl(FetchConfigurationImpl.ConfigurationState state, JDBCConfigurationState jstate) {
      super(state);
      this._state = jstate == null ? new JDBCConfigurationState() : jstate;
   }

   protected FetchConfigurationImpl newInstance(FetchConfigurationImpl.ConfigurationState state) {
      JDBCConfigurationState jstate = state == null ? null : this._state;
      return new JDBCFetchConfigurationImpl(state, jstate);
   }

   public void setContext(StoreContext ctx) {
      super.setContext(ctx);
      JDBCConfiguration conf = this.getJDBCConfiguration();
      if (conf != null) {
         this.setEagerFetchMode(conf.getEagerFetchModeConstant());
         this.setSubclassFetchMode(conf.getSubclassFetchModeConstant());
         this.setResultSetType(conf.getResultSetTypeConstant());
         this.setFetchDirection(conf.getFetchDirectionConstant());
         this.setLRSSize(conf.getLRSSizeConstant());
         this.setJoinSyntax(conf.getDBDictionaryInstance().joinSyntax);
      }
   }

   public void copy(FetchConfiguration fetch) {
      super.copy(fetch);
      JDBCFetchConfiguration jf = (JDBCFetchConfiguration)fetch;
      this.setEagerFetchMode(jf.getEagerFetchMode());
      this.setSubclassFetchMode(jf.getSubclassFetchMode());
      this.setResultSetType(jf.getResultSetType());
      this.setFetchDirection(jf.getFetchDirection());
      this.setLRSSize(jf.getLRSSize());
      this.setJoinSyntax(jf.getJoinSyntax());
      this.addJoins(jf.getJoins());
   }

   public int getEagerFetchMode() {
      return this._state.eagerMode;
   }

   public JDBCFetchConfiguration setEagerFetchMode(int mode) {
      if (mode == -99) {
         JDBCConfiguration conf = this.getJDBCConfiguration();
         if (conf != null) {
            mode = conf.getEagerFetchModeConstant();
         }
      }

      if (mode != -99) {
         this._state.eagerMode = mode;
      }

      return this;
   }

   public int getSubclassFetchMode() {
      return this._state.subclassMode;
   }

   public int getSubclassFetchMode(ClassMapping cls) {
      if (cls == null) {
         return this._state.subclassMode;
      } else {
         int mode = cls.getSubclassFetchMode();
         return mode == -99 ? this._state.subclassMode : Math.min(mode, this._state.subclassMode);
      }
   }

   public JDBCFetchConfiguration setSubclassFetchMode(int mode) {
      if (mode == -99) {
         JDBCConfiguration conf = this.getJDBCConfiguration();
         if (conf != null) {
            mode = conf.getSubclassFetchModeConstant();
         }
      }

      if (mode != -99) {
         this._state.subclassMode = mode;
      }

      return this;
   }

   public int getResultSetType() {
      return this._state.type;
   }

   public JDBCFetchConfiguration setResultSetType(int type) {
      if (type == -99) {
         JDBCConfiguration conf = this.getJDBCConfiguration();
         if (conf != null) {
            this._state.type = conf.getResultSetTypeConstant();
         }
      } else {
         this._state.type = type;
      }

      return this;
   }

   public int getFetchDirection() {
      return this._state.direction;
   }

   public JDBCFetchConfiguration setFetchDirection(int direction) {
      if (direction == -99) {
         JDBCConfiguration conf = this.getJDBCConfiguration();
         if (conf != null) {
            this._state.direction = conf.getFetchDirectionConstant();
         }
      } else {
         this._state.direction = direction;
      }

      return this;
   }

   public int getLRSSize() {
      return this._state.size;
   }

   public JDBCFetchConfiguration setLRSSize(int size) {
      if (size == -99) {
         JDBCConfiguration conf = this.getJDBCConfiguration();
         if (conf != null) {
            this._state.size = conf.getLRSSizeConstant();
         }
      } else {
         this._state.size = size;
      }

      return this;
   }

   public int getJoinSyntax() {
      return this._state.syntax;
   }

   public JDBCFetchConfiguration setJoinSyntax(int syntax) {
      if (syntax == -99) {
         JDBCConfiguration conf = this.getJDBCConfiguration();
         if (conf != null) {
            this._state.syntax = conf.getDBDictionaryInstance().joinSyntax;
         }
      } else {
         this._state.syntax = syntax;
      }

      return this;
   }

   public ResultList newResultList(ResultObjectProvider rop) {
      if (rop instanceof ListResultObjectProvider) {
         return new SimpleResultList(rop);
      } else if (rop instanceof PagingResultObjectProvider) {
         return new WindowResultList(rop, ((PagingResultObjectProvider)rop).getPageSize());
      } else if (this.getFetchBatchSize() < 0) {
         return new EagerResultList(rop);
      } else if (this._state.type != 1003 && this._state.direction != 1000 && rop.supportsRandomAccess()) {
         return (ResultList)(this._state.direction == 1002 ? new SoftRandomAccessResultList(rop) : new SimpleResultList(rop));
      } else {
         return this.getFetchBatchSize() > 0 && this.getFetchBatchSize() <= 50 ? new WindowResultList(rop, this.getFetchBatchSize()) : new WindowResultList(rop, 50);
      }
   }

   public Set getJoins() {
      return this._state.joins == null ? Collections.EMPTY_SET : this._state.joins;
   }

   public boolean hasJoin(String field) {
      return this._state.joins != null && this._state.joins.contains(field);
   }

   public JDBCFetchConfiguration addJoin(String join) {
      if (StringUtils.isEmpty(join)) {
         throw new UserException(_loc.get("null-join"));
      } else {
         this.lock();

         try {
            if (this._state.joins == null) {
               this._state.joins = new HashSet();
            }

            this._state.joins.add(join);
         } finally {
            this.unlock();
         }

         return this;
      }
   }

   public JDBCFetchConfiguration addJoins(Collection joins) {
      if (joins != null && !joins.isEmpty()) {
         Iterator itr = joins.iterator();

         while(itr.hasNext()) {
            this.addJoin((String)itr.next());
         }

         return this;
      } else {
         return this;
      }
   }

   public JDBCFetchConfiguration removeJoin(String field) {
      this.lock();

      try {
         if (this._state.joins != null) {
            this._state.joins.remove(field);
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public JDBCFetchConfiguration removeJoins(Collection joins) {
      this.lock();

      try {
         if (this._state.joins != null) {
            this._state.joins.removeAll(joins);
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public JDBCFetchConfiguration clearJoins() {
      this.lock();

      try {
         if (this._state.joins != null) {
            this._state.joins.clear();
         }
      } finally {
         this.unlock();
      }

      return this;
   }

   public int getIsolation() {
      return this._state.isolationLevel;
   }

   public JDBCFetchConfiguration setIsolation(int level) {
      if (level != -1 && level != -99 && level != 0 && level != 1 && level != 2 && level != 4 && level != 8) {
         throw new IllegalArgumentException(_loc.get("bad-level", (Object)(new Integer(level))).getMessage());
      } else {
         if (level == -99) {
            this._state.isolationLevel = -1;
         } else {
            this._state.isolationLevel = level;
         }

         return this;
      }
   }

   public JDBCFetchConfiguration traverseJDBC(FieldMetaData fm) {
      return (JDBCFetchConfiguration)this.traverse(fm);
   }

   private JDBCConfiguration getJDBCConfiguration() {
      StoreContext ctx = this.getContext();
      if (ctx == null) {
         return null;
      } else {
         OpenJPAConfiguration conf = ctx.getConfiguration();
         return !(conf instanceof JDBCConfiguration) ? null : (JDBCConfiguration)conf;
      }
   }

   public Set getFetchInnerJoins() {
      return this._state.fetchInnerJoins == null ? Collections.EMPTY_SET : this._state.fetchInnerJoins;
   }

   public boolean hasFetchInnerJoin(String field) {
      return this._state.fetchInnerJoins != null && this._state.fetchInnerJoins.contains(field);
   }

   public JDBCFetchConfiguration addFetchInnerJoin(String join) {
      if (StringUtils.isEmpty(join)) {
         throw new UserException(_loc.get("null-join"));
      } else {
         this.lock();

         try {
            if (this._state.fetchInnerJoins == null) {
               this._state.fetchInnerJoins = new HashSet();
            }

            this._state.fetchInnerJoins.add(join);
         } finally {
            this.unlock();
         }

         return this;
      }
   }

   public JDBCFetchConfiguration addFetchInnerJoins(Collection joins) {
      if (joins != null && !joins.isEmpty()) {
         Iterator itr = joins.iterator();

         while(itr.hasNext()) {
            this.addFetchInnerJoin((String)itr.next());
         }

         return this;
      } else {
         return this;
      }
   }

   protected static class JDBCConfigurationState implements Serializable {
      public int eagerMode = 0;
      public int subclassMode = 0;
      public int type = 0;
      public int direction = 0;
      public int size = 0;
      public int syntax = 0;
      public Set joins = null;
      public Set fetchInnerJoins = null;
      public int isolationLevel = -1;
   }
}
