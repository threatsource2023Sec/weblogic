package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.openjpa.event.OrphanedKeyAction;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.SuperclassDiscriminatorStrategy;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.jdbc.sql.SQLFactory;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.LockManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.QueryLanguages;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.lib.jdbc.DelegatingConnection;
import org.apache.openjpa.lib.jdbc.DelegatingPreparedStatement;
import org.apache.openjpa.lib.jdbc.DelegatingStatement;
import org.apache.openjpa.lib.rop.MergedResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.OpenJPAId;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UserException;

public class JDBCStoreManager implements StoreManager, JDBCStore {
   private static final Localizer _loc = Localizer.forPackage(JDBCStoreManager.class);
   private StoreContext _ctx = null;
   private JDBCConfiguration _conf = null;
   private DBDictionary _dict = null;
   private SQLFactory _sql = null;
   private JDBCLockManager _lm = null;
   private DataSource _ds = null;
   private RefCountConnection _conn = null;
   private boolean _active = false;
   private Set _stmnts = Collections.synchronizedSet(new HashSet());

   public StoreContext getContext() {
      return this._ctx;
   }

   public void setContext(StoreContext ctx) {
      this.setContext(ctx, (JDBCConfiguration)ctx.getConfiguration());
   }

   public void setContext(StoreContext ctx, JDBCConfiguration conf) {
      this._ctx = ctx;
      this._conf = conf;
      this._dict = this._conf.getDBDictionaryInstance();
      this._sql = this._conf.getSQLFactoryInstance();
      LockManager lm = ctx.getLockManager();
      if (lm instanceof JDBCLockManager) {
         this._lm = (JDBCLockManager)lm;
      }

      if (!ctx.isManaged() && this._conf.isConnectionFactoryModeManaged()) {
         this._ds = this._conf.getDataSource2(ctx);
      } else {
         this._ds = this._conf.getDataSource(ctx);
      }

      if (this._conf.getUpdateManagerInstance().orderDirty()) {
         ctx.setOrderDirtyObjects(true);
      }

   }

   public JDBCConfiguration getConfiguration() {
      return this._conf;
   }

   public DBDictionary getDBDictionary() {
      return this._dict;
   }

   public SQLFactory getSQLFactory() {
      return this._sql;
   }

   public JDBCLockManager getLockManager() {
      return this._lm;
   }

   public JDBCFetchConfiguration getFetchConfiguration() {
      return (JDBCFetchConfiguration)this._ctx.getFetchConfiguration();
   }

   public void beginOptimistic() {
   }

   public void rollbackOptimistic() {
   }

   public void begin() {
      this._active = true;

      try {
         if ((!this._ctx.isManaged() || !this._conf.isConnectionFactoryModeManaged()) && this._conn.getAutoCommit()) {
            this._conn.setAutoCommit(false);
         }

      } catch (SQLException var2) {
         this._active = false;
         throw SQLExceptions.getStore(var2, this._dict);
      }
   }

   public void commit() {
      try {
         if (!this._ctx.isManaged() || !this._conf.isConnectionFactoryModeManaged()) {
            this._conn.commit();
         }
      } catch (SQLException var8) {
         try {
            this._conn.rollback();
         } catch (SQLException var7) {
         }

         throw SQLExceptions.getStore(var8, this._dict);
      } finally {
         this._active = false;
      }

   }

   public void rollback() {
      if (this._active) {
         try {
            if (this._conn != null && (!this._ctx.isManaged() || !this._conf.isConnectionFactoryModeManaged())) {
               this._conn.rollback();
            }
         } catch (SQLException var5) {
            throw SQLExceptions.getStore(var5, this._dict);
         } finally {
            this._active = false;
         }

      }
   }

   public void retainConnection() {
      this.connect(false);
      this._conn.setRetain(true);
   }

   public void releaseConnection() {
      if (this._conn != null) {
         this._conn.setRetain(false);
      }

   }

   public Object getClientConnection() {
      return new ClientConnection(this.getConnection());
   }

   public Connection getConnection() {
      this.connect(true);
      return this._conn;
   }

   protected DataSource getDataSource() {
      return this._ds;
   }

   public boolean exists(OpenJPAStateManager sm, Object context) {
      ClassMapping mapping = (ClassMapping)sm.getMetaData();
      return this.exists(mapping, sm.getObjectId(), context);
   }

   private boolean exists(ClassMapping mapping, Object oid, Object context) {
      Select sel;
      for(sel = this._sql.newSelect(); mapping.getJoinablePCSuperclassMapping() != null; mapping = mapping.getJoinablePCSuperclassMapping()) {
      }

      sel.wherePrimaryKey(oid, mapping, this);

      try {
         return sel.getCount(this) != 0;
      } catch (SQLException var6) {
         throw SQLExceptions.getStore(var6, this._dict);
      }
   }

   public boolean syncVersion(OpenJPAStateManager sm, Object context) {
      ClassMapping mapping = (ClassMapping)sm.getMetaData();

      try {
         return mapping.getVersion().checkVersion(sm, this, true);
      } catch (SQLException var5) {
         throw SQLExceptions.getStore(var5, this._dict);
      }
   }

   public int compareVersion(OpenJPAStateManager state, Object v1, Object v2) {
      ClassMapping mapping = (ClassMapping)state.getMetaData();
      return mapping.getVersion().compareVersion(v1, v2);
   }

   public boolean initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, Object context) {
      ConnectionInfo info = (ConnectionInfo)context;

      try {
         return this.initializeState(sm, state, (JDBCFetchConfiguration)fetch, info);
      } catch (ClassNotFoundException var7) {
         throw new UserException(var7);
      } catch (SQLException var8) {
         throw SQLExceptions.getStore(var8, this._dict);
      }
   }

   protected boolean initializeState(OpenJPAStateManager sm, PCState state, JDBCFetchConfiguration fetch, ConnectionInfo info) throws ClassNotFoundException, SQLException {
      Object oid = sm.getObjectId();
      ClassMapping mapping = (ClassMapping)sm.getMetaData();
      Result res = null;

      try {
         boolean var21;
         if (info != null && info.result != null) {
            res = info.result;
            info.sm = sm;
            if (info.mapping == null) {
               info.mapping = mapping;
            }

            mapping = info.mapping;
         } else if (oid instanceof OpenJPAId && !((OpenJPAId)oid).hasSubclasses()) {
            Boolean custom = this.customLoad(sm, mapping, state, fetch);
            if (custom != null) {
               var21 = custom;
               return var21;
            }

            res = this.getInitializeStateResult(sm, mapping, fetch, 4);
            if (res == null && !this.selectPrimaryKey(sm, mapping, fetch)) {
               var21 = false;
               return var21;
            }

            if (this.isEmptyResult(res)) {
               var21 = false;
               return var21;
            }
         } else {
            ClassMapping[] mappings = mapping.getIndependentAssignableMappings();
            if (mappings.length == 1) {
               mapping = mappings[0];
               Boolean custom = this.customLoad(sm, mapping, state, fetch);
               boolean var10;
               if (custom != null) {
                  var10 = custom;
                  return var10;
               }

               res = this.getInitializeStateResult(sm, mapping, fetch, 3);
               if (res == null && !this.selectPrimaryKey(sm, mapping, fetch)) {
                  var10 = false;
                  return var10;
               }
            } else {
               res = this.getInitializeStateUnionResult(sm, mapping, mappings, fetch);
            }

            if (this.isEmptyResult(res)) {
               var21 = false;
               return var21;
            }
         }

         Class type;
         if ((type = this.getType(res, mapping)) == null) {
            if (res.getBaseMapping() != null) {
               mapping = res.getBaseMapping();
            }

            res.startDataRequest(mapping.getDiscriminator());

            try {
               type = mapping.getDiscriminator().getClass(this, mapping, res);
            } finally {
               res.endDataRequest();
            }
         }

         sm.initialize(type, state);
         if (info != null && info.result != null) {
            FieldMapping mappedByFieldMapping = info.result.getMappedByFieldMapping();
            Object mappedByObject = info.result.getMappedByValue();
            if (mappedByFieldMapping != null && mappedByObject != null) {
               this.setMappedBy(sm, mappedByFieldMapping, mappedByObject);
            }
         }

         if (res != null) {
            mapping = (ClassMapping)sm.getMetaData();
            this.load(mapping, sm, fetch, res);
            this.getVersion(mapping, sm, res);
         }

         var21 = true;
         return var21;
      } finally {
         if (res != null && (info == null || res != info.result)) {
            res.close();
         }

      }
   }

   protected void setMappedBy(OpenJPAStateManager sm, FieldMapping mappedByFieldMapping, Object mappedByObject) {
      ClassMapping mapping = (ClassMapping)sm.getMetaData();
      FieldMapping[] fms = mapping.getDeclaredFieldMappings();

      for(int i = 0; i < fms.length; ++i) {
         if (fms[i] == mappedByFieldMapping) {
            sm.storeObject(fms[i].getIndex(), mappedByObject);
            return;
         }
      }

   }

   protected void getVersion(ClassMapping mapping, OpenJPAStateManager sm, Result res) throws SQLException {
      mapping.getVersion().afterLoad(sm, this);
   }

   protected boolean isEmptyResult(Result res) throws SQLException {
      return res != null && !res.next();
   }

   protected Class getType(Result res, ClassMapping mapping) {
      return res == null ? mapping.getDescribedType() : null;
   }

   private Boolean customLoad(OpenJPAStateManager sm, ClassMapping mapping, PCState state, JDBCFetchConfiguration fetch) throws ClassNotFoundException, SQLException {
      if (!mapping.customLoad(sm, this, (PCState)state, (JDBCFetchConfiguration)fetch)) {
         return null;
      } else if (sm.getManagedInstance() != null) {
         mapping.getVersion().afterLoad(sm, this);
         return Boolean.TRUE;
      } else {
         return Boolean.FALSE;
      }
   }

   private Result getInitializeStateResult(OpenJPAStateManager sm, ClassMapping mapping, JDBCFetchConfiguration fetch, int subs) throws SQLException {
      Select sel = this._sql.newSelect();
      if (!this.select(sel, mapping, subs, sm, (BitSet)null, fetch, 1, true, false)) {
         return null;
      } else {
         sel.wherePrimaryKey(sm.getObjectId(), mapping, this);
         sel.setExpectedResultCount(1, false);
         return sel.execute(this, fetch);
      }
   }

   private Result getInitializeStateUnionResult(final OpenJPAStateManager sm, ClassMapping mapping, final ClassMapping[] mappings, final JDBCFetchConfiguration fetch) throws SQLException {
      final int eager = Math.min(fetch.getEagerFetchMode(), 1);
      Union union = this._sql.newUnion(mappings.length);
      union.setExpectedResultCount(1, false);
      if (fetch.getSubclassFetchMode(mapping) != 1) {
         union.abortUnion();
      }

      union.select(new Union.Selector() {
         public void select(Select sel, int i) {
            sel.select(mappings[i], 3, JDBCStoreManager.this, fetch, eager);
            sel.wherePrimaryKey(sm.getObjectId(), mappings[i], JDBCStoreManager.this);
         }
      });
      return union.execute(this, fetch);
   }

   private boolean selectPrimaryKey(OpenJPAStateManager sm, ClassMapping mapping, JDBCFetchConfiguration fetch) throws SQLException {
      ClassMapping base;
      for(base = mapping; base.getJoinablePCSuperclassMapping() != null; base = base.getJoinablePCSuperclassMapping()) {
      }

      Select sel = this._sql.newSelect();
      sel.select(base.getPrimaryKeyColumns());
      sel.wherePrimaryKey(sm.getObjectId(), base, this);
      Result exists = sel.execute(this, fetch);

      boolean var7;
      try {
         if (this.isEmptyResult(exists)) {
            var7 = false;
            return var7;
         }

         if (this._active && this._lm != null && exists.isLocking()) {
            this._lm.loadedForUpdate(sm);
         }

         var7 = true;
      } finally {
         exists.close();
      }

      return var7;
   }

   public boolean load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, int lockLevel, Object context) {
      JDBCFetchConfiguration jfetch = (JDBCFetchConfiguration)fetch;
      ConnectionInfo info = (ConnectionInfo)context;
      Result res = null;
      if (info != null) {
         if (info.sm != sm) {
            res = info.result;
         }

         info.sm = null;
      }

      try {
         ClassMapping mapping = (ClassMapping)sm.getMetaData();
         if (res != null) {
            this.load(mapping, sm, jfetch, res);
            this.removeLoadedFields(sm, fields);
         }

         if (sm.getLoaded().length() == 0 && mapping.customLoad(sm, this, (PCState)null, (JDBCFetchConfiguration)jfetch)) {
            this.removeLoadedFields(sm, fields);
         }

         Select sel = this._sql.newSelect();
         if (this.select(sel, mapping, 4, sm, fields, jfetch, 1, true, false)) {
            sel.wherePrimaryKey(sm.getObjectId(), mapping, this);
            res = sel.execute(this, jfetch, lockLevel);

            try {
               if (this.isEmptyResult(res)) {
                  boolean var11 = false;
                  return var11;
               }

               this.load(mapping, sm, jfetch, res);
            } finally {
               res.close();
            }
         }

         FieldMapping[] fms = mapping.getFieldMappings();

         for(int i = 0; i < fms.length; ++i) {
            if (fields.get(i) && !sm.getLoaded().get(i)) {
               fms[i].load(sm, this, jfetch.traverseJDBC(fms[i]));
            }
         }

         mapping.getVersion().afterLoad(sm, this);
         return true;
      } catch (ClassNotFoundException var17) {
         throw new StoreException(var17);
      } catch (SQLException var18) {
         throw SQLExceptions.getStore(var18, this._dict);
      }
   }

   private void removeLoadedFields(OpenJPAStateManager sm, BitSet fields) {
      int i = 0;

      for(int len = fields.length(); i < len; ++i) {
         if (fields.get(i) && sm.getLoaded().get(i)) {
            fields.clear(i);
         }
      }

   }

   public Collection loadAll(Collection sms, PCState state, int load, FetchConfiguration fetch, Object context) {
      return ImplHelper.loadAll(sms, this, state, load, fetch, context);
   }

   public void beforeStateChange(OpenJPAStateManager sm, PCState fromState, PCState toState) {
   }

   public Collection flush(Collection sms) {
      return this._conf.getUpdateManagerInstance().flush(sms, this);
   }

   public boolean cancelAll() {
      ArrayList stmnts;
      synchronized(this._stmnts) {
         if (this._stmnts.isEmpty()) {
            return false;
         }

         stmnts = new ArrayList(this._stmnts);
      }

      try {
         Iterator itr = stmnts.iterator();

         while(itr.hasNext()) {
            ((Statement)itr.next()).cancel();
         }

         return true;
      } catch (SQLException var4) {
         throw SQLExceptions.getStore(var4, this._dict);
      }
   }

   public boolean assignObjectId(OpenJPAStateManager sm, boolean preFlush) {
      ClassMetaData meta = sm.getMetaData();
      if (meta.getIdentityType() == 2) {
         return ApplicationIds.assign(sm, this, preFlush);
      } else {
         Object val = ImplHelper.generateIdentityValue(this._ctx, meta, 6);
         if (val == null && meta.getIdentityStrategy() != 1) {
            return false;
         } else {
            if (val == null) {
               val = this.getDataStoreIdSequence(meta).next(this._ctx, meta);
            }

            sm.setObjectId(this.newDataStoreId(val, meta));
            return true;
         }
      }
   }

   public boolean assignField(OpenJPAStateManager sm, int field, boolean preFlush) {
      FieldMetaData fmd = sm.getMetaData().getField(field);
      Object val = ImplHelper.generateFieldValue(this._ctx, fmd);
      if (val == null) {
         return false;
      } else {
         sm.store(field, val);
         return true;
      }
   }

   public Class getManagedType(Object oid) {
      return oid instanceof Id ? ((Id)oid).getType() : null;
   }

   public Class getDataStoreIdType(ClassMetaData meta) {
      return Id.class;
   }

   public Object copyDataStoreId(Object oid, ClassMetaData meta) {
      Id id = (Id)oid;
      return new Id(meta.getDescribedType(), id.getId(), id.hasSubclasses());
   }

   public Object newDataStoreId(Object val, ClassMetaData meta) {
      return Id.newInstance(meta.getDescribedType(), val);
   }

   public Id newDataStoreId(long id, ClassMapping mapping, boolean subs) {
      return new Id(mapping.getDescribedType(), id, subs);
   }

   public ResultObjectProvider executeExtent(ClassMetaData meta, final boolean subclasses, FetchConfiguration fetch) {
      ClassMapping mapping = (ClassMapping)meta;
      final ClassMapping[] mappings;
      if (subclasses) {
         mappings = mapping.getIndependentAssignableMappings();
      } else {
         mappings = new ClassMapping[]{mapping};
      }

      ResultObjectProvider[] rops = null;
      final JDBCFetchConfiguration jfetch = (JDBCFetchConfiguration)fetch;
      if (jfetch.getSubclassFetchMode(mapping) != 1) {
         rops = new ResultObjectProvider[mappings.length];
      }

      try {
         ResultObjectProvider rop;
         int i;
         for(i = 0; i < mappings.length; ++i) {
            rop = mappings[i].customLoad(this, subclasses, jfetch, 0L, Long.MAX_VALUE);
            if (rop != null) {
               if (rops == null) {
                  rops = new ResultObjectProvider[mappings.length];
               }

               rops[i] = rop;
            }
         }

         rop = null;
         if (rops != null) {
            for(i = 0; i < mappings.length; ++i) {
               if (rops[i] == null) {
                  Select sel = this._sql.newSelect();
                  sel.setLRS(true);
                  BitSet paged = this.selectExtent(sel, mappings[i], jfetch, subclasses);
                  if (paged == null) {
                     rops[i] = new InstanceResultObjectProvider(sel, mappings[i], this, jfetch);
                  } else {
                     rops[i] = new PagingResultObjectProvider(sel, mappings[i], this, jfetch, paged, Long.MAX_VALUE);
                  }
               }
            }

            if (rops.length == 1) {
               return rops[0];
            } else {
               return new MergedResultObjectProvider(rops);
            }
         } else {
            Union union = this._sql.newUnion(mappings.length);
            union.setLRS(true);
            final BitSet[] paged = new BitSet[mappings.length];
            union.select(new Union.Selector() {
               public void select(Select sel, int idx) {
                  paged[idx] = JDBCStoreManager.this.selectExtent(sel, mappings[idx], jfetch, subclasses);
               }
            });

            for(int i = 0; i < paged.length; ++i) {
               if (paged[i] != null) {
                  return new PagingResultObjectProvider(union, mappings, this, jfetch, paged, Long.MAX_VALUE);
               }
            }

            return new InstanceResultObjectProvider(union, mappings[0], this, jfetch);
         }
      } catch (SQLException var12) {
         throw SQLExceptions.getStore(var12, this._dict);
      }
   }

   private BitSet selectExtent(Select sel, ClassMapping mapping, JDBCFetchConfiguration fetch, boolean subclasses) {
      int subs = subclasses ? 1 : 2;
      BitSet paged = PagingResultObjectProvider.getPagedFields(sel, mapping, this, fetch, 2, Long.MAX_VALUE);
      if (paged == null) {
         sel.selectIdentifier(mapping, subs, this, fetch, 2);
      } else {
         sel.selectIdentifier(mapping, subs, this, fetch, 1);
      }

      return paged;
   }

   public StoreQuery newQuery(String language) {
      ExpressionParser ep = QueryLanguages.parserForLanguage(language);
      if (ep != null) {
         return new JDBCStoreQuery(this, ep);
      } else {
         return "openjpa.SQL".equals(language) ? new SQLStoreQuery(this) : null;
      }
   }

   public FetchConfiguration newFetchConfiguration() {
      return new JDBCFetchConfigurationImpl();
   }

   public Seq getDataStoreIdSequence(ClassMetaData meta) {
      return meta.getIdentityStrategy() != 1 && meta.getIdentityStrategy() != 0 ? null : this._conf.getSequenceInstance();
   }

   public Seq getValueSequence(FieldMetaData fmd) {
      return null;
   }

   public void close() {
      if (this._conn != null) {
         this._conn.free();
      }

   }

   private void connect(boolean ref) {
      this._ctx.lock();

      try {
         if (this._conn == null) {
            this._conn = this.connectInternal();
         }

         if (ref) {
            this._conn.ref();
         }
      } catch (SQLException var6) {
         throw SQLExceptions.getStore(var6, this._dict);
      } finally {
         this._ctx.unlock();
      }

   }

   protected RefCountConnection connectInternal() throws SQLException {
      return new RefCountConnection(this._ds.getConnection());
   }

   public Object find(Object oid, ValueMapping vm, JDBCFetchConfiguration fetch) {
      if (oid == null) {
         return null;
      } else {
         Object pc = this._ctx.find(oid, fetch, (BitSet)null, (Object)null, 0);
         if (pc == null && vm != null) {
            OrphanedKeyAction action = this._conf.getOrphanedKeyActionInstance();
            pc = action.orphan(oid, (OpenJPAStateManager)null, vm);
         }

         return pc;
      }
   }

   public Object load(ClassMapping mapping, JDBCFetchConfiguration fetch, BitSet exclude, Result result) throws SQLException {
      if (!mapping.isMapped()) {
         throw new InvalidStateException(_loc.get("virtual-mapping", (Object)mapping));
      } else {
         ClassMapping base;
         for(base = mapping; base.getJoinablePCSuperclassMapping() != null; base = base.getJoinablePCSuperclassMapping()) {
         }

         Object oid = base.getObjectId(this, result, (ForeignKey)null, true, (Joins)null);
         if (oid == null) {
            return null;
         } else {
            ConnectionInfo info = new ConnectionInfo();
            info.result = result;
            info.mapping = mapping;
            return this._ctx.find(oid, fetch, exclude, info, 0);
         }
      }
   }

   private void load(ClassMapping mapping, OpenJPAStateManager sm, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      FieldMapping eagerToMany = this.load(mapping, sm, fetch, res, (FieldMapping)null);
      if (eagerToMany != null) {
         eagerToMany.loadEagerJoin(sm, this, fetch.traverseJDBC(eagerToMany), res);
      }

      if (this._active && this._lm != null && res.isLocking()) {
         this._lm.loadedForUpdate(sm);
      }

   }

   private FieldMapping load(ClassMapping mapping, OpenJPAStateManager sm, JDBCFetchConfiguration fetch, Result res, FieldMapping eagerToMany) throws SQLException {
      if (mapping.customLoad(sm, this, (JDBCFetchConfiguration)fetch, (Result)res)) {
         return eagerToMany;
      } else {
         ClassMapping parent = mapping.getJoinablePCSuperclassMapping();
         if (parent != null) {
            eagerToMany = this.load(parent, sm, fetch, res, eagerToMany);
         } else if (sm.getVersion() == null) {
            mapping.getVersion().load(sm, this, res);
         }

         FieldMapping[] fms = mapping.getDefinedFieldMappings();

         for(int i = 0; i < fms.length; ++i) {
            if (!fms[i].isPrimaryKey() && !sm.getLoaded().get(fms[i].getIndex())) {
               Object eres = res.getEager(fms[i]);
               res.startDataRequest(fms[i]);

               try {
                  if (eres == res) {
                     if (eagerToMany == null && fms[i].isEagerSelectToMany()) {
                        eagerToMany = fms[i];
                     } else {
                        fms[i].loadEagerJoin(sm, this, fetch.traverseJDBC(fms[i]), res);
                     }
                  } else if (eres != null) {
                     Object processed = fms[i].loadEagerParallel(sm, this, fetch.traverseJDBC(fms[i]), eres);
                     if (processed != eres) {
                        res.putEager(fms[i], processed);
                     }
                  } else {
                     fms[i].load(sm, this, fetch.traverseJDBC(fms[i]), res);
                  }
               } finally {
                  res.endDataRequest();
               }
            }
         }

         return eagerToMany;
      }
   }

   public boolean select(Select sel, ClassMapping mapping, int subs, OpenJPAStateManager sm, BitSet fields, JDBCFetchConfiguration fetch, int eager, boolean ident, boolean outer) {
      boolean joinedSupers = false;
      if (this.needClassCondition(mapping, subs, sm)) {
         joinedSupers = this.getJoinedSupers(sel, mapping, subs, outer);
      }

      eager = Math.min(eager, fetch.getEagerFetchMode());
      FieldMapping eagerToMany = this.createEagerSelects(sel, mapping, sm, fields, fetch, eager);
      int seld = this.selectBaseMappings(sel, mapping, mapping, sm, fields, fetch, eager, eagerToMany, ident, joinedSupers);
      if (eagerToMany != null) {
         eagerToMany.selectEagerJoin(sel, sm, this, fetch.traverseJDBC(eagerToMany), eager);
      }

      if (subs == 1 || subs == 3) {
         this.selectSubclassMappings(sel, mapping, sm, fetch);
      }

      if (sm != null) {
         sel.setDistinct(false);
      }

      return seld > 0;
   }

   private boolean getJoinedSupers(Select sel, ClassMapping mapping, int subs, boolean outer) {
      this.loadSubclasses(mapping);
      Joins joins = outer ? sel.newOuterJoins() : null;
      return mapping.getDiscriminator().addClassConditions(sel, subs == 1, joins);
   }

   private boolean needClassCondition(ClassMapping mapping, int subs, OpenJPAStateManager sm) {
      boolean retVal = false;
      if (sm == null || sm.getPCState() == PCState.TRANSIENT) {
         if (subs != 1 && subs != 2) {
            if (mapping.getDiscriminator() != null && SuperclassDiscriminatorStrategy.class.isInstance(mapping.getDiscriminator().getStrategy()) && mapping.getMappingRepository().getConfiguration().getCompatibilityInstance().getSuperclassDiscriminatorStrategyByDefault()) {
               retVal = true;
            }
         } else {
            retVal = true;
         }
      }

      return retVal;
   }

   private FieldMapping createEagerSelects(Select sel, ClassMapping mapping, OpenJPAStateManager sm, BitSet fields, JDBCFetchConfiguration fetch, int eager) {
      if (mapping != null && eager != 0) {
         FieldMapping eagerToMany = this.createEagerSelects(sel, mapping.getJoinablePCSuperclassMapping(), sm, fields, fetch, eager);
         FieldMapping[] fms = mapping.getDefinedFieldMappings();
         boolean inEagerJoin = sel.hasEagerJoin(false);

         for(int i = 0; i < fms.length; ++i) {
            int mode = fms[i].getEagerFetchMode();
            if (mode != 0 && requiresSelect(fms[i], sm, fields, fetch)) {
               int var10000 = fms[i].getNullValue();
               FieldMapping var10001 = fms[i];
               int jtype = var10000 == 2 ? 0 : 1;
               if (mode == 2 || fms[i].isEagerSelectToMany() || fms[i].supportsSelect(sel, jtype, sm, this, fetch) <= 0 || sel.eagerClone(fms[i], jtype, false, 1) == null) {
                  boolean hasJoin = fetch.hasJoin(fms[i].getFullName(false));
                  if ((hasJoin || mode == 1 || mode == -99 && sm != null) && fms[i].isEagerSelectToMany() && !inEagerJoin && !sel.hasEagerJoin(true) && (!sel.getAutoDistinct() || !sel.isLRS() && sel.getStartIndex() == 0L && sel.getEndIndex() == Long.MAX_VALUE) && fms[i].supportsSelect(sel, jtype, sm, this, fetch) > 0) {
                     if (sel.eagerClone(fms[i], jtype, true, 1) == null) {
                        continue;
                     }

                     eagerToMany = fms[i];
                  }

                  int sels;
                  if (eager == 2 && (sels = fms[i].supportsSelect(sel, 2, sm, this, fetch)) != 0) {
                     sel.eagerClone(fms[i], 2, fms[i].isEagerSelectToMany(), sels);
                  }
               }
            }
         }

         return eagerToMany;
      } else {
         return null;
      }
   }

   private static boolean requiresSelect(FieldMapping fm, OpenJPAStateManager sm, BitSet fields, JDBCFetchConfiguration fetch) {
      if (fields != null) {
         return fields.get(fm.getIndex());
      } else if (sm != null && sm.getPCState() != PCState.TRANSIENT && sm.getLoaded().get(fm.getIndex())) {
         return false;
      } else {
         return fetch.requiresFetch(fm) == 1;
      }
   }

   private int selectBaseMappings(Select sel, ClassMapping mapping, ClassMapping orig, OpenJPAStateManager sm, BitSet fields, JDBCFetchConfiguration fetch, int eager, FieldMapping eagerToMany, boolean ident, boolean joined) {
      ClassMapping parent = mapping.getJoinablePCSuperclassMapping();
      if (parent == null && !mapping.isMapped()) {
         throw new InvalidStateException(_loc.get("virtual-mapping", (Object)mapping.getDescribedType()));
      } else {
         int seld = -1;
         int pseld = -1;
         if (parent == null) {
            if (sm == null) {
               if (ident) {
                  sel.selectIdentifier(mapping.getPrimaryKeyColumns());
               } else {
                  sel.select(mapping.getPrimaryKeyColumns());
               }

               seld = 1;
            }

            if ((sm == null || sm.getPCState() == PCState.TRANSIENT && (!(sm.getObjectId() instanceof OpenJPAId) || ((OpenJPAId)sm.getObjectId()).hasSubclasses())) && mapping.getDiscriminator().select(sel, orig)) {
               seld = 1;
            }

            if ((sm == null || sm.getVersion() == null) && mapping.getVersion().select(sel, orig)) {
               seld = 1;
            }
         } else {
            pseld = this.selectBaseMappings(sel, parent, orig, sm, fields, fetch, eager, eagerToMany, ident, joined);
         }

         FieldMapping[] fms = mapping.getDefinedFieldMappings();

         for(int i = 0; i < fms.length; ++i) {
            if (fms[i] != eagerToMany) {
               SelectExecutor esel = sel.getEager(fms[i]);
               if (esel != null) {
                  if (esel == sel) {
                     fms[i].selectEagerJoin(sel, sm, this, fetch.traverseJDBC(fms[i]), eager);
                  } else {
                     fms[i].selectEagerParallel(esel, sm, this, fetch.traverseJDBC(fms[i]), eager);
                  }

                  seld = Math.max(0, seld);
               } else {
                  int fseld;
                  if (requiresSelect(fms[i], sm, fields, fetch)) {
                     fseld = fms[i].select(sel, sm, this, fetch.traverseJDBC(fms[i]), eager);
                     seld = Math.max(fseld, seld);
                  } else if (this.optSelect(fms[i], sel, sm, fetch)) {
                     fseld = fms[i].select(sel, sm, this, fetch.traverseJDBC(fms[i]), 0);
                     if (fseld >= 0 && seld < 0) {
                        seld = 0;
                     }
                  }
               }
            }
         }

         if (eagerToMany != null && pseld < 0 && !joined && parent != null) {
            FieldMapping[] pfms = parent.getDefinedFieldMappings();

            for(int i = 0; i < pfms.length; ++i) {
               if (pfms[i] == eagerToMany) {
                  pseld = 0;
                  break;
               }
            }
         }

         if (!joined && pseld >= 0 && parent.getTable() != mapping.getTable()) {
            sel.where(mapping.joinSuperclass(sel.newJoins(), false));
         }

         return Math.max(pseld, seld);
      }
   }

   private boolean optSelect(FieldMapping fm, Select sel, OpenJPAStateManager sm, JDBCFetchConfiguration fetch) {
      return !fm.isInDefaultFetchGroup() && !fm.isDefaultFetchGroupExplicit() && (sm == null || sm.getPCState() == PCState.TRANSIENT || !sm.getLoaded().get(fm.getIndex())) && fm.supportsSelect(sel, 4, sm, this, fetch) > 0;
   }

   private void selectSubclassMappings(Select sel, ClassMapping mapping, OpenJPAStateManager sm, JDBCFetchConfiguration fetch) {
      this.loadSubclasses(mapping);
      ClassMapping[] subMappings = mapping.getJoinablePCSubclassMappings();
      if (subMappings.length != 0) {
         boolean canJoin = this._dict.joinSyntax != 1 && fetch.getSubclassFetchMode(mapping) != 0;

         for(int i = 0; i < subMappings.length; ++i) {
            if (subMappings[i].supportsEagerSelect(sel, sm, this, mapping, fetch)) {
               boolean joined = !canJoin;
               FieldMapping[] fms = subMappings[i].getDefinedFieldMappings();

               for(int j = 0; j < fms.length; ++j) {
                  if (fetch.requiresFetch(fms[j]) == 1 || (fms[j].isInDefaultFetchGroup() || !fms[j].isDefaultFetchGroupExplicit()) && fms[j].supportsSelect(sel, 4, sm, this, fetch) > 0) {
                     if (!joined) {
                        joined = true;
                        sel.where(joinSubclass(sel, mapping, subMappings[i], (Joins)null));
                     }

                     if (fms[j].supportsSelect(sel, 3, sm, this, fetch) > 0) {
                        fms[j].select(sel, (OpenJPAStateManager)null, this, fetch.traverseJDBC(fms[j]), 0);
                     }
                  }
               }
            }
         }

      }
   }

   private static Joins joinSubclass(Select sel, ClassMapping base, ClassMapping sub, Joins joins) {
      if (sub != base && sub.getTable() != base.getTable() && !sel.isSelected(sub.getTable())) {
         ClassMapping sup = sub.getJoinablePCSuperclassMapping();
         joins = joinSubclass(sel, base, sup, joins);
         if (joins == null) {
            joins = sel.newJoins();
         }

         return sub.joinSuperclass(joins, true);
      } else {
         return null;
      }
   }

   public void loadSubclasses(ClassMapping mapping) {
      Discriminator dsc = mapping.getDiscriminator();
      if (!dsc.getSubclassesLoaded()) {
         if (mapping.getRepository().getPersistentTypeNames(false, this._ctx.getClassLoader()) != null) {
            dsc.setSubclassesLoaded(true);
         } else {
            try {
               dsc.loadSubclasses(this);
            } catch (ClassNotFoundException var4) {
               throw new StoreException(var4);
            } catch (SQLException var5) {
               throw SQLExceptions.getStore(var5, this._dict);
            }
         }
      }
   }

   private void beforeExecuteStatement(Statement stmnt) {
      this._stmnts.add(stmnt);
   }

   private void afterExecuteStatement(Statement stmnt) {
      this._stmnts.remove(stmnt);
   }

   private class CancelPreparedStatement extends DelegatingPreparedStatement {
      public CancelPreparedStatement(PreparedStatement stmnt, Connection conn) {
         super(stmnt, conn);
      }

      public int executeUpdate() throws SQLException {
         JDBCStoreManager.this.beforeExecuteStatement(this);

         int var1;
         try {
            var1 = super.executeUpdate();
         } finally {
            JDBCStoreManager.this.afterExecuteStatement(this);
         }

         return var1;
      }

      protected ResultSet executeQuery(boolean wrap) throws SQLException {
         JDBCStoreManager.this.beforeExecuteStatement(this);

         ResultSet var2;
         try {
            var2 = super.executeQuery(wrap);
         } finally {
            JDBCStoreManager.this.afterExecuteStatement(this);
         }

         return var2;
      }

      public int[] executeBatch() throws SQLException {
         JDBCStoreManager.this.beforeExecuteStatement(this);

         int[] var1;
         try {
            var1 = super.executeBatch();
         } finally {
            JDBCStoreManager.this.afterExecuteStatement(this);
         }

         return var1;
      }
   }

   private class CancelStatement extends DelegatingStatement {
      public CancelStatement(Statement stmnt, Connection conn) {
         super(stmnt, conn);
      }

      public int executeUpdate(String sql) throws SQLException {
         JDBCStoreManager.this.beforeExecuteStatement(this);

         int var2;
         try {
            var2 = super.executeUpdate(sql);
         } finally {
            JDBCStoreManager.this.afterExecuteStatement(this);
         }

         return var2;
      }

      protected ResultSet executeQuery(String sql, boolean wrap) throws SQLException {
         JDBCStoreManager.this.beforeExecuteStatement(this);

         ResultSet var3;
         try {
            var3 = super.executeQuery(sql, wrap);
         } finally {
            JDBCStoreManager.this.afterExecuteStatement(this);
         }

         return var3;
      }
   }

   protected class RefCountConnection extends DelegatingConnection {
      private boolean _retain = false;
      private int _refs = 0;
      private boolean _freed = false;

      public RefCountConnection(Connection conn) {
         super(conn);
      }

      public boolean getRetain() {
         return this._retain;
      }

      public void setRetain(boolean retain) {
         if (this._retain && !retain && this._refs <= 0) {
            this.free();
         }

         this._retain = retain;
      }

      public void ref() {
         ++this._refs;
      }

      public void close() throws SQLException {
         JDBCStoreManager.this._ctx.lock();

         try {
            --this._refs;
            if (this._refs <= 0 && !this._retain) {
               this.free();
            }
         } finally {
            JDBCStoreManager.this._ctx.unlock();
         }

      }

      public void free() {
         if (!this._freed) {
            try {
               this.getDelegate().close();
            } catch (SQLException var2) {
            }

            this._freed = true;
            JDBCStoreManager.this._conn = null;
         }
      }

      protected Statement createStatement(boolean wrap) throws SQLException {
         return JDBCStoreManager.this.new CancelStatement(super.createStatement(false), this);
      }

      protected Statement createStatement(int rsType, int rsConcur, boolean wrap) throws SQLException {
         return JDBCStoreManager.this.new CancelStatement(super.createStatement(rsType, rsConcur, false), this);
      }

      protected PreparedStatement prepareStatement(String sql, boolean wrap) throws SQLException {
         return JDBCStoreManager.this.new CancelPreparedStatement(super.prepareStatement(sql, false), this);
      }

      protected PreparedStatement prepareStatement(String sql, int rsType, int rsConcur, boolean wrap) throws SQLException {
         return JDBCStoreManager.this.new CancelPreparedStatement(super.prepareStatement(sql, rsType, rsConcur, false), this);
      }
   }

   private static class ClientConnection extends DelegatingConnection {
      private boolean _closed = false;

      public ClientConnection(Connection conn) {
         super(conn);
      }

      public void close() throws SQLException {
         this._closed = true;
         super.close();
      }

      protected void finalize() throws SQLException {
         if (!this._closed) {
            this.close();
         }

      }
   }
}
