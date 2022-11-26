package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataContext;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.InternalException;

public class Version implements VersionStrategy, MetaDataContext, MetaDataModes {
   private static final Localizer _loc = Localizer.forPackage(Version.class);
   private final ClassMapping _mapping;
   private final VersionMappingInfo _info;
   private VersionStrategy _strategy = null;
   private int _resMode = 0;
   private Column[] _cols;
   private ColumnIO _io;
   private Index _idx;

   public Version(ClassMapping mapping) {
      this._cols = Schemas.EMPTY_COLUMNS;
      this._io = null;
      this._idx = null;
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

   public VersionStrategy getStrategy() {
      return this._strategy;
   }

   public void setStrategy(VersionStrategy strategy, Boolean adapt) {
      VersionStrategy orig = this._strategy;
      this._strategy = strategy;
      if (strategy != null) {
         try {
            strategy.setVersion(this);
            if (adapt != null) {
               strategy.map(adapt);
            }
         } catch (RuntimeException var5) {
            this._strategy = orig;
            throw var5;
         }
      }

   }

   public VersionMappingInfo getMappingInfo() {
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
      this._info.clear();
      this.setResolve(10, false);
   }

   public void syncMappingInfo() {
      this._info.syncWith(this);
      FieldMapping fm = this._mapping.getVersionFieldMapping();
      if (fm != null) {
         if (this._info.getStrategy() != null && this._info.getStrategy().equals(this.getMappingRepository().defaultStrategy(this, fm).getAlias())) {
            this._info.setStrategy((String)null);
         }

         fm.getMappingInfo().clear();
         fm.getValueInfo().clear();
         fm.getKeyMapping().getValueInfo().clear();
         fm.getElementMapping().getValueInfo().clear();
         fm.getValueInfo().copy(this._info);
         this._info.clear();
      }

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
      FieldMapping fm = this._mapping.getVersionFieldMapping();
      if (fm != null) {
         this._info.copy(fm.getValueInfo());
      }

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

   public void setVersion(Version owner) {
      this.assertStrategy().setVersion(owner);
   }

   public boolean select(Select sel, ClassMapping mapping) {
      return this.assertStrategy().select(sel, mapping);
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, Result res) throws SQLException {
      this.assertStrategy().load(sm, store, res);
   }

   public void afterLoad(OpenJPAStateManager sm, JDBCStore store) {
      this.assertStrategy().afterLoad(sm, store);
   }

   public boolean checkVersion(OpenJPAStateManager sm, JDBCStore store, boolean updateVersion) throws SQLException {
      return this.assertStrategy().checkVersion(sm, store, updateVersion);
   }

   public int compareVersion(Object v1, Object v2) {
      return this.assertStrategy().compareVersion(v1, v2);
   }

   private VersionStrategy assertStrategy() {
      if (this._strategy == null) {
         throw new InternalException();
      } else {
         return this._strategy;
      }
   }

   public String toString() {
      return this._mapping + "<version>";
   }

   public Map getBulkUpdateValues() {
      return this._strategy.getBulkUpdateValues();
   }
}
