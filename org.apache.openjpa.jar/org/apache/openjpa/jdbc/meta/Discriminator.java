package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataContext;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.InternalException;

public class Discriminator implements DiscriminatorStrategy, MetaDataContext, MetaDataModes {
   public static final Object NULL = new Object();
   private static final Localizer _loc = Localizer.forPackage(Discriminator.class);
   private final ClassMapping _mapping;
   private final DiscriminatorMappingInfo _info;
   private DiscriminatorStrategy _strategy = null;
   private int _resMode = 0;
   private Column[] _cols;
   private ColumnIO _io;
   private Index _idx;
   private boolean _subsLoaded;
   private Object _value;
   private int _javaType;

   public Discriminator(ClassMapping mapping) {
      this._cols = Schemas.EMPTY_COLUMNS;
      this._io = null;
      this._idx = null;
      this._subsLoaded = false;
      this._value = null;
      this._javaType = -1;
      this._mapping = mapping;
      this._info = this.getMappingRepository().newMappingInfo(this);
   }

   public MetaDataRepository getRepository() {
      return this._mapping.getRepository();
   }

   public MappingRepository getMappingRepository() {
      return this._mapping.getMappingRepository();
   }

   public ClassMapping getClassMapping() {
      return this._mapping;
   }

   public DiscriminatorStrategy getStrategy() {
      return this._strategy;
   }

   public void setStrategy(DiscriminatorStrategy strategy, Boolean adapt) {
      DiscriminatorStrategy orig = this._strategy;
      this._strategy = strategy;
      if (strategy != null) {
         try {
            strategy.setDiscriminator(this);
            if (adapt != null) {
               strategy.map(adapt);
            }
         } catch (RuntimeException var5) {
            this._strategy = orig;
            throw var5;
         }
      }

   }

   public Object getValue() {
      return this._value;
   }

   public void setValue(Object value) {
      this._value = value;
   }

   public DiscriminatorMappingInfo getMappingInfo() {
      return this._info;
   }

   public Column[] getColumns() {
      return this._cols;
   }

   public void setColumns(Column[] cols) {
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

   public Index getIndex() {
      return this._idx;
   }

   public void setIndex(Index idx) {
      this._idx = idx;
   }

   public void refSchemaComponents() {
      for(int i = 0; i < this._cols.length; ++i) {
         this._cols[i].ref();
      }

   }

   public void clearMapping() {
      this._strategy = null;
      this._cols = Schemas.EMPTY_COLUMNS;
      this._idx = null;
      this._value = null;
      this._info.clear();
      this.setResolve(10, false);
   }

   public void syncMappingInfo() {
      this._info.syncWith(this);
   }

   public int getResolve() {
      return this._resMode;
   }

   public void setResolve(int mode) {
      this._resMode = mode;
   }

   public void setResolve(int mode, boolean on) {
      if (mode == 0) {
         this._resMode = mode;
      } else if (on) {
         this._resMode |= mode;
      } else {
         this._resMode &= ~mode;
      }

   }

   public boolean resolve(int mode) {
      if ((this._resMode & mode) == mode) {
         return true;
      } else {
         int cur = this._resMode;
         this._resMode |= mode;
         if ((mode & 2) != 0 && (cur & 2) == 0) {
            this.resolveMapping();
         }

         if ((mode & 8) != 0 && (cur & 8) == 0) {
            this._strategy.initialize();
         }

         return false;
      }
   }

   private void resolveMapping() {
      MappingRepository repos = this.getMappingRepository();
      if (this._strategy == null) {
         repos.getStrategyInstaller().installStrategy(this);
      }

      Log log = repos.getLog();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("strategy", this, this._strategy.getAlias()));
      }

      Column[] cols = this.getColumns();
      ColumnIO io = this.getColumnIO();

      for(int i = 0; i < cols.length; ++i) {
         if (io.isInsertable(i, false)) {
            cols[i].setFlag(8, true);
         }

         if (io.isUpdatable(i, false)) {
            cols[i].setFlag(16, true);
         }
      }

   }

   public boolean getSubclassesLoaded() {
      if (!this._subsLoaded) {
         ClassMapping sup = this._mapping.getPCSuperclassMapping();
         if (sup != null && sup.getDiscriminator().getSubclassesLoaded()) {
            this._subsLoaded = true;
         }
      }

      return this._subsLoaded;
   }

   public void setSubclassesLoaded(boolean loaded) {
      this._subsLoaded = loaded;
   }

   public boolean addClassConditions(Select sel, boolean subs, Joins joins) {
      if (this._mapping.getJoinablePCSuperclassMapping() == null && this._mapping.getJoinablePCSubclassMappings().length == 0) {
         return false;
      } else if (!this.hasClassConditions(this._mapping, subs)) {
         return false;
      } else {
         ClassMapping from = this._mapping;

         for(ClassMapping sup = this._mapping.getJoinablePCSuperclassMapping(); sup != null; sup = sup.getJoinablePCSuperclassMapping()) {
            if (from.getTable() != sup.getTable()) {
               if (joins == null) {
                  joins = sel.newJoins();
               }

               joins = from.joinSuperclass(joins, false);
            }

            from = sup;
         }

         sel.where(this.getClassConditions(sel, joins, this._mapping, subs), joins);
         return true;
      }
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

   public void setDiscriminator(Discriminator owner) {
      this.assertStrategy().setDiscriminator(owner);
   }

   public boolean select(Select sel, ClassMapping mapping) {
      return this.assertStrategy().select(sel, mapping);
   }

   public void loadSubclasses(JDBCStore store) throws SQLException, ClassNotFoundException {
      this.assertStrategy().loadSubclasses(store);
   }

   public Class getClass(JDBCStore store, ClassMapping base, Result result) throws SQLException, ClassNotFoundException {
      return this.assertStrategy().getClass(store, base, result);
   }

   public boolean hasClassConditions(ClassMapping base, boolean subs) {
      return this.assertStrategy().hasClassConditions(base, subs);
   }

   public SQLBuffer getClassConditions(Select sel, Joins joins, ClassMapping base, boolean subs) {
      return this.assertStrategy().getClassConditions(sel, joins, base, subs);
   }

   private DiscriminatorStrategy assertStrategy() {
      if (this._strategy == null) {
         throw new InternalException();
      } else {
         return this._strategy;
      }
   }

   public String toString() {
      return this._mapping + "<discriminator>";
   }

   public void setJavaType(int javaType) {
      this._javaType = javaType;
   }

   public int getJavaType() {
      if (this._javaType == -1) {
         ClassMapping superMapping = this._mapping.getPCSuperclassMapping();
         if (superMapping != null && superMapping.getDiscriminator() != null) {
            this._javaType = superMapping.getDiscriminator().getJavaType();
         }
      }

      return this._javaType;
   }
}
