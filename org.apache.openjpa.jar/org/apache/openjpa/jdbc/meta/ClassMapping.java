package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.strats.NoneClassStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.OpenJPAId;

public class ClassMapping extends ClassMetaData implements ClassStrategy {
   public static final ClassMapping[] EMPTY_MAPPINGS = new ClassMapping[0];
   private static final Localizer _loc = Localizer.forPackage(ClassMapping.class);
   private final ClassMappingInfo _info;
   private final Discriminator _discrim;
   private final Version _version;
   private ClassStrategy _strategy = null;
   private Table _table = null;
   private ColumnIO _io = null;
   private Column[] _cols;
   private ForeignKey _fk;
   private int _subclassMode;
   private ClassMapping[] _joinSubMaps;
   private ClassMapping[] _assignMaps;
   private final Map _joinables;

   protected ClassMapping(Class type, MappingRepository repos) {
      super(type, repos);
      this._cols = Schemas.EMPTY_COLUMNS;
      this._fk = null;
      this._subclassMode = Integer.MAX_VALUE;
      this._joinSubMaps = null;
      this._assignMaps = null;
      this._joinables = Collections.synchronizedMap(new HashMap());
      this._discrim = repos.newDiscriminator(this);
      this._version = repos.newVersion(this);
      this._info = repos.newMappingInfo(this);
   }

   protected ClassMapping(ValueMetaData vmd) {
      super(vmd);
      this._cols = Schemas.EMPTY_COLUMNS;
      this._fk = null;
      this._subclassMode = Integer.MAX_VALUE;
      this._joinSubMaps = null;
      this._assignMaps = null;
      this._joinables = Collections.synchronizedMap(new HashMap());
      this._discrim = this.getMappingRepository().newDiscriminator(this);
      this._version = this.getMappingRepository().newVersion(this);
      this._info = this.getMappingRepository().newMappingInfo(this);
   }

   public Discriminator getDiscriminator() {
      return this._discrim;
   }

   public Version getVersion() {
      return this._version;
   }

   public Object getObjectId(JDBCStore store, Result res, ForeignKey fk, boolean subs, Joins joins) throws SQLException {
      ValueMapping embed = this.getEmbeddingMapping();
      return embed != null ? embed.getFieldMapping().getDefiningMapping().getObjectId(store, res, fk, subs, joins) : this.getObjectId(this, store, res, fk, subs, joins);
   }

   private Object getObjectId(ClassMapping cls, JDBCStore store, Result res, ForeignKey fk, boolean subs, Joins joins) throws SQLException {
      if (!this.isPrimaryKeyObjectId(true)) {
         return this.getPCSuperclassMapping().getObjectId(cls, store, res, fk, subs, joins);
      } else if (this.getIdentityType() == 0) {
         throw new InternalException();
      } else {
         Column[] pks = this.getPrimaryKeyColumns();
         if (this.getIdentityType() == 1) {
            Column col = fk == null ? pks[0] : fk.getColumn(pks[0]);
            long id = res.getLong(col, joins);
            return id == 0L && res.wasNull() ? null : store.newDataStoreId(id, cls, subs);
         } else {
            Object[] vals = new Object[this.getPrimaryKeyFields().length];
            boolean canReadDiscriminator = true;

            for(int i = 0; i < pks.length; ++i) {
               Joinable join = this.assertJoinable(pks[i]);
               FieldMapping fm = this.getFieldMapping(join.getFieldIndex());
               int pkIdx = fm.getPrimaryKeyIndex();
               canReadDiscriminator &= this.isSelfReference(fk, join.getColumns());
               if (vals[pkIdx] == null) {
                  res.startDataRequest(fm);
                  vals[pkIdx] = join.getPrimaryKeyValue(res, join.getColumns(), fk, store, joins);
                  res.endDataRequest();
                  if (vals[pkIdx] == null) {
                     return null;
                  }
               }
            }

            ClassMapping dcls = cls;
            if (subs && canReadDiscriminator) {
               res.startDataRequest(cls.getDiscriminator());

               try {
                  Class dtype = cls.getDiscriminator().getClass(store, cls, res);
                  if (dtype != cls.getDescribedType()) {
                     dcls = cls.getMappingRepository().getMapping(dtype, store.getContext().getClassLoader(), true);
                  }
               } catch (Exception var15) {
               }

               res.endDataRequest();
            }

            Object oid = ApplicationIds.fromPKValues(vals, dcls);
            if (oid instanceof OpenJPAId) {
               ((OpenJPAId)oid).setManagedInstanceType(dcls.getDescribedType(), subs);
            }

            return oid;
         }
      }
   }

   boolean isSelfReference(ForeignKey fk, Column[] cols) {
      if (fk == null) {
         return true;
      } else {
         Column[] arr$ = cols;
         int len$ = cols.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Column col = arr$[i$];
            if (fk.getColumn(col) != col) {
               return false;
            }
         }

         return true;
      }
   }

   public Object toDataStoreValue(Object obj, Column[] cols, JDBCStore store) {
      Object ret = cols.length == 1 ? null : new Object[cols.length];
      OpenJPAStateManager sm;
      if (ImplHelper.isManageable(obj)) {
         sm = (OpenJPAStateManager)ImplHelper.toPersistenceCapable(obj, this.getRepository().getConfiguration()).pcGetStateManager();
      } else {
         sm = store.getContext().getStateManager(obj);
      }

      if (sm == null) {
         return ret;
      } else {
         for(int i = 0; i < cols.length; ++i) {
            Object val = this.assertJoinable(cols[i]).getJoinValue(sm, cols[i], store);
            if (cols.length == 1) {
               ret = val;
            } else {
               ((Object[])((Object[])ret))[i] = val;
            }
         }

         return ret;
      }
   }

   public Joinable assertJoinable(Column col) {
      Joinable join = this.getJoinable(col);
      if (join == null) {
         throw new MetaDataException(_loc.get("no-joinable", (Object)col.getFullName()));
      } else {
         return join;
      }
   }

   public Joinable getJoinable(Column col) {
      Joinable join;
      if (this.getEmbeddingMetaData() != null) {
         join = this.getEmbeddingMapping().getFieldMapping().getDefiningMapping().getJoinable(col);
         if (join != null) {
            return join;
         }
      }

      ClassMapping sup = this.getJoinablePCSuperclassMapping();
      if (sup != null) {
         join = sup.getJoinable(col);
         if (join != null) {
            return join;
         }
      }

      return (Joinable)this._joinables.get(col);
   }

   public void setJoinable(Column col, Joinable joinable) {
      Joinable join = (Joinable)this._joinables.get(col);
      if (join == null || join.getFieldIndex() != -1 && this.getField(join.getFieldIndex()).getPrimaryKeyIndex() == -1) {
         this._joinables.put(col, joinable);
      }

   }

   public Boolean isForeignKeyObjectId(ForeignKey fk) {
      if (this.getIdentityType() != 0 && this.isPrimaryKeyObjectId(false)) {
         Column[] cols = fk.getPrimaryKeyColumns();
         if (this.getIdentityType() == 1) {
            return cols.length == 1 && cols[0] == this.getPrimaryKeyColumns()[0] ? Boolean.TRUE : Boolean.FALSE;
         } else {
            for(int i = 0; i < cols.length; ++i) {
               Joinable join = this.assertJoinable(cols[i]);
               if (join.getFieldIndex() != -1 && this.getField(join.getFieldIndex()).getPrimaryKeyIndex() == -1) {
                  return Boolean.FALSE;
               }
            }

            if (this.isPrimaryKeyObjectId(true) && cols.length == this.getPrimaryKeyColumns().length) {
               return Boolean.TRUE;
            } else {
               return null;
            }
         }
      } else {
         return Boolean.FALSE;
      }
   }

   public ClassMappingInfo getMappingInfo() {
      return this._info;
   }

   public ClassStrategy getStrategy() {
      return this._strategy;
   }

   public void setStrategy(ClassStrategy strategy, Boolean adapt) {
      ClassStrategy orig = this._strategy;
      this._strategy = strategy;
      if (strategy != null) {
         try {
            strategy.setClassMapping(this);
            if (adapt != null) {
               strategy.map(adapt);
            }
         } catch (RuntimeException var5) {
            this._strategy = orig;
            throw var5;
         }
      }

   }

   public Table getTable() {
      return this._table;
   }

   public void setTable(Table table) {
      this._table = table;
   }

   public Column[] getPrimaryKeyColumns() {
      if (this._cols.length == 0 && this.getIdentityType() == 2 && this.isMapped()) {
         FieldMapping[] pks = this.getPrimaryKeyFieldMappings();
         Collection cols = new ArrayList(pks.length);

         for(int i = 0; i < pks.length; ++i) {
            Column[] fieldCols = pks[i].getColumns();

            for(int j = 0; j < fieldCols.length; ++j) {
               cols.add(fieldCols[j]);
            }
         }

         this._cols = (Column[])((Column[])cols.toArray(new Column[cols.size()]));
      }

      return this._cols;
   }

   public void setPrimaryKeyColumns(Column[] cols) {
      if (cols == null) {
         cols = Schemas.EMPTY_COLUMNS;
      }

      this._cols = cols;
   }

   public ColumnIO getColumnIO() {
      return this._io == null ? ColumnIO.UNRESTRICTED : this._io;
   }

   public void setColumnIO(ColumnIO io) {
      this._io = io;
   }

   public ForeignKey getJoinForeignKey() {
      return this._fk;
   }

   public void setJoinForeignKey(ForeignKey fk) {
      this._fk = fk;
   }

   public void refSchemaComponents() {
      int i;
      if (this.getEmbeddingMetaData() == null) {
         if (this._table != null && this._table.getPrimaryKey() != null) {
            this._table.getPrimaryKey().ref();
         }

         if (this._fk != null) {
            this._fk.ref();
         }

         Column[] pks = this.getPrimaryKeyColumns();

         for(i = 0; i < pks.length; ++i) {
            pks[i].ref();
         }
      } else {
         FieldMapping[] fields = this.getFieldMappings();

         for(i = 0; i < fields.length; ++i) {
            fields[i].refSchemaComponents();
         }
      }

   }

   public void clearMapping() {
      this._strategy = null;
      this._cols = Schemas.EMPTY_COLUMNS;
      this._fk = null;
      this._table = null;
      this._info.clear();
      this.setResolve(10, false);
   }

   public void syncMappingInfo() {
      if (this.getEmbeddingMetaData() == null) {
         this._info.syncWith(this);
      } else {
         this._info.clear();
         FieldMapping[] fields = this.getFieldMappings();

         for(int i = 0; i < fields.length; ++i) {
            fields[i].syncMappingInfo();
         }
      }

   }

   protected void setDescribedType(Class type) {
      super.setDescribedType(type);
      if (this._info != null) {
         this._info.setClassName(type.getName());
      }

   }

   public int getSubclassFetchMode() {
      if (this._subclassMode == Integer.MAX_VALUE) {
         if (this.getPCSuperclass() != null) {
            this._subclassMode = this.getPCSuperclassMapping().getSubclassFetchMode();
         } else {
            this._subclassMode = -99;
         }
      }

      return this._subclassMode;
   }

   public void setSubclassFetchMode(int mode) {
      this._subclassMode = mode;
   }

   public MappingRepository getMappingRepository() {
      return (MappingRepository)this.getRepository();
   }

   public ValueMapping getEmbeddingMapping() {
      return (ValueMapping)this.getEmbeddingMetaData();
   }

   public boolean isMapped() {
      if (!super.isMapped()) {
         return false;
      } else if (this._strategy != null) {
         return this._strategy != NoneClassStrategy.getInstance();
      } else {
         return !"none".equals(this._info.getStrategy());
      }
   }

   public ClassMapping getPCSuperclassMapping() {
      return (ClassMapping)this.getPCSuperclassMetaData();
   }

   public ClassMapping getMappedPCSuperclassMapping() {
      return (ClassMapping)this.getMappedPCSuperclassMetaData();
   }

   public ClassMapping getJoinablePCSuperclassMapping() {
      ClassMapping sup = this.getMappedPCSuperclassMapping();
      if (sup == null) {
         return null;
      } else {
         return this._fk == null && this._table != null && !this._table.equals(sup.getTable()) ? null : sup;
      }
   }

   public ClassMapping[] getPCSubclassMappings() {
      return (ClassMapping[])((ClassMapping[])this.getPCSubclassMetaDatas());
   }

   public ClassMapping[] getMappedPCSubclassMappings() {
      return (ClassMapping[])((ClassMapping[])this.getMappedPCSubclassMetaDatas());
   }

   public ClassMapping[] getJoinablePCSubclassMappings() {
      ClassMapping[] subs = this.getMappedPCSubclassMappings();
      if (this._joinSubMaps == null) {
         if (subs.length == 0) {
            this._joinSubMaps = subs;
         } else {
            List joinable = new ArrayList(subs.length);

            for(int i = 0; i < subs.length; ++i) {
               if (this.isSubJoinable(subs[i])) {
                  joinable.add(subs[i]);
               }
            }

            this._joinSubMaps = (ClassMapping[])((ClassMapping[])joinable.toArray(new ClassMapping[joinable.size()]));
         }
      }

      return this._joinSubMaps;
   }

   private boolean isSubJoinable(ClassMapping sub) {
      if (sub == null) {
         return false;
      } else {
         return sub == this ? true : this.isSubJoinable(sub.getJoinablePCSuperclassMapping());
      }
   }

   public ClassMapping[] getIndependentAssignableMappings() {
      ClassMapping[] subs = this.getMappedPCSubclassMappings();
      if (this._assignMaps == null) {
         if (subs.length == 0) {
            if (this.isMapped()) {
               this._assignMaps = new ClassMapping[]{this};
            } else {
               this._assignMaps = subs;
            }
         } else {
            int size = (int)((double)subs.length * 1.33 + 2.0);
            Set independent = new LinkedHashSet(size);
            if (this.isMapped()) {
               independent.add(this);
            }

            independent.addAll(Arrays.asList(subs));
            List clear = null;
            Iterator itr = independent.iterator();

            while(itr.hasNext()) {
               ClassMapping map = (ClassMapping)itr.next();
               ClassMapping sup = map.getJoinablePCSuperclassMapping();
               if (sup != null && independent.contains(sup)) {
                  if (clear == null) {
                     clear = new ArrayList(independent.size() - 1);
                  }

                  clear.add(map);
               }
            }

            if (clear != null) {
               independent.removeAll(clear);
            }

            this._assignMaps = (ClassMapping[])((ClassMapping[])independent.toArray(new ClassMapping[independent.size()]));
         }
      }

      return this._assignMaps;
   }

   public FieldMapping[] getFieldMappings() {
      return (FieldMapping[])((FieldMapping[])this.getFields());
   }

   public FieldMapping[] getDeclaredFieldMappings() {
      return (FieldMapping[])((FieldMapping[])this.getDeclaredFields());
   }

   public FieldMapping[] getPrimaryKeyFieldMappings() {
      return (FieldMapping[])((FieldMapping[])this.getPrimaryKeyFields());
   }

   public FieldMapping getVersionFieldMapping() {
      return (FieldMapping)this.getVersionField();
   }

   public FieldMapping[] getDefaultFetchGroupFieldMappings() {
      return (FieldMapping[])((FieldMapping[])this.getDefaultFetchGroupFields());
   }

   public FieldMapping[] getDefinedFieldMappings() {
      return (FieldMapping[])((FieldMapping[])this.getDefinedFields());
   }

   public FieldMapping[] getFieldMappingsInListingOrder() {
      return (FieldMapping[])((FieldMapping[])this.getFieldsInListingOrder());
   }

   public FieldMapping[] getDefinedFieldMappingsInListingOrder() {
      return (FieldMapping[])((FieldMapping[])this.getDefinedFieldsInListingOrder());
   }

   public FieldMapping getFieldMapping(int index) {
      return (FieldMapping)this.getField(index);
   }

   public FieldMapping getDeclaredFieldMapping(int index) {
      return (FieldMapping)this.getDeclaredField(index);
   }

   public FieldMapping getFieldMapping(String name) {
      return (FieldMapping)this.getField(name);
   }

   public FieldMapping getDeclaredFieldMapping(String name) {
      return (FieldMapping)this.getDeclaredField(name);
   }

   public FieldMapping[] getDeclaredUnmanagedFieldMappings() {
      return (FieldMapping[])((FieldMapping[])this.getDeclaredUnmanagedFields());
   }

   public FieldMapping addDeclaredFieldMapping(String name, Class type) {
      return (FieldMapping)this.addDeclaredField(name, type);
   }

   protected void resolveMapping(boolean runtime) {
      super.resolveMapping(runtime);
      MappingRepository repos = this.getMappingRepository();
      if (this._strategy == null) {
         repos.getStrategyInstaller().installStrategy(this);
      }

      Log log = this.getRepository().getLog();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("strategy", this, this._strategy.getAlias()));
      }

      this.defineSuperclassFields(this.getJoinablePCSuperclassMapping() == null);
      this.resolveNonRelationMappings();
      FieldMapping[] fms = this.getFieldMappings();

      int i;
      for(i = 0; i < fms.length; ++i) {
         if (fms[i].getDefiningMetaData() == this) {
            fms[i].resolve(2);
         }
      }

      fms = this.getDeclaredUnmanagedFieldMappings();

      for(i = 0; i < fms.length; ++i) {
         fms[i].resolve(2);
      }

      if (this._cols != null) {
         ColumnIO io = this.getColumnIO();

         for(int i = 0; i < this._cols.length; ++i) {
            if (io.isInsertable(i, false)) {
               this._cols[i].setFlag(8, true);
            }

            if (io.isUpdatable(i, false)) {
               this._cols[i].setFlag(16, true);
            }
         }
      }

      this._info.getUniques(this, true);
   }

   void resolveNonRelationMappings() {
      FieldMapping[] fms = this.getPrimaryKeyFieldMappings();

      int i;
      for(i = 0; i < fms.length; ++i) {
         fms[i].resolve(2);
      }

      fms = this.getFieldMappings();

      for(i = 0; i < fms.length; ++i) {
         if (fms[i].getDefiningMetaData() == this && !fms[i].isTypePC() && !fms[i].getKey().isTypePC() && !fms[i].getElement().isTypePC()) {
            fms[i].resolve(2);
         }
      }

      this._discrim.resolve(2);
      this._version.resolve(2);
   }

   protected void initializeMapping() {
      super.initializeMapping();
      FieldMapping[] fields = this.getDefinedFieldMappings();

      for(int i = 0; i < fields.length; ++i) {
         fields[i].resolve(8);
      }

      this._discrim.resolve(8);
      this._version.resolve(8);
      this._strategy.initialize();
   }

   protected void clearDefinedFieldCache() {
      super.clearDefinedFieldCache();
   }

   protected void clearSubclassCache() {
      super.clearSubclassCache();
      this._joinSubMaps = null;
      this._assignMaps = null;
   }

   public void copy(ClassMetaData cls) {
      super.copy(cls);
      if (this._subclassMode == Integer.MAX_VALUE) {
         this._subclassMode = ((ClassMapping)cls).getSubclassFetchMode();
      }

   }

   protected boolean validateDataStoreExtensionPrefix(String prefix) {
      return "jdbc-".equals(prefix);
   }

   public String getAlias() {
      return this.assertStrategy().getAlias();
   }

   public void map(boolean adapt) {
      this.assertStrategy().map(adapt);
   }

   public void initialize() {
      this.assertStrategy().initialize();
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.assertStrategy().insert(sm, store, rm);
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.assertStrategy().update(sm, store, rm);
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.assertStrategy().delete(sm, store, rm);
   }

   public Boolean isCustomInsert(OpenJPAStateManager sm, JDBCStore store) {
      return this.assertStrategy().isCustomInsert(sm, store);
   }

   public Boolean isCustomUpdate(OpenJPAStateManager sm, JDBCStore store) {
      return this.assertStrategy().isCustomUpdate(sm, store);
   }

   public Boolean isCustomDelete(OpenJPAStateManager sm, JDBCStore store) {
      return this.assertStrategy().isCustomDelete(sm, store);
   }

   public void customInsert(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      this.assertStrategy().customInsert(sm, store);
   }

   public void customUpdate(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      this.assertStrategy().customUpdate(sm, store);
   }

   public void customDelete(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      this.assertStrategy().customDelete(sm, store);
   }

   public void setClassMapping(ClassMapping owner) {
      this.assertStrategy().setClassMapping(owner);
   }

   public boolean isPrimaryKeyObjectId(boolean hasAll) {
      return this.assertStrategy().isPrimaryKeyObjectId(hasAll);
   }

   public Joins joinSuperclass(Joins joins, boolean toThis) {
      return this.assertStrategy().joinSuperclass(joins, toThis);
   }

   public boolean supportsEagerSelect(Select sel, OpenJPAStateManager sm, JDBCStore store, ClassMapping base, JDBCFetchConfiguration fetch) {
      return this.assertStrategy().supportsEagerSelect(sel, sm, store, base, fetch);
   }

   public ResultObjectProvider customLoad(JDBCStore store, boolean subclasses, JDBCFetchConfiguration fetch, long startIdx, long endIdx) throws SQLException {
      return this.assertStrategy().customLoad(store, subclasses, fetch, startIdx, endIdx);
   }

   public boolean customLoad(OpenJPAStateManager sm, JDBCStore store, PCState state, JDBCFetchConfiguration fetch) throws SQLException, ClassNotFoundException {
      return this.assertStrategy().customLoad(sm, store, state, fetch);
   }

   public boolean customLoad(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result result) throws SQLException {
      return this.assertStrategy().customLoad(sm, store, fetch, result);
   }

   private ClassStrategy assertStrategy() {
      if (this._strategy == null) {
         throw new InternalException();
      } else {
         return this._strategy;
      }
   }
}
