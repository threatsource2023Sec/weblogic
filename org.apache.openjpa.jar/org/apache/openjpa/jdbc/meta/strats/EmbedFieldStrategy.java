package org.apache.openjpa.jdbc.meta.strats;

import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.JavaSQLTypes;
import org.apache.openjpa.jdbc.meta.RelationId;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.UserException;

public class EmbedFieldStrategy extends AbstractFieldStrategy {
   private static final int INSERT = 0;
   private static final int UPDATE = 1;
   private static final int DELETE = 2;
   private static final Localizer _loc = Localizer.forPackage(EmbedFieldStrategy.class);
   private boolean _synthetic = false;

   public void map(boolean adapt) {
      if (this.field.getEmbeddedMetaData() == null) {
         throw new MetaDataException(_loc.get("not-embed", (Object)this.field));
      } else {
         this.assertNotMappedBy();
         this.field.mapJoin(adapt, false);
         this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
         this.field.getElementMapping().getValueInfo().assertNoSchemaComponents(this.field.getElement(), !adapt);
         ValueMappingInfo vinfo = this.field.getValueInfo();
         vinfo.assertNoJoin(this.field, true);
         vinfo.assertNoForeignKey(this.field, !adapt);
         vinfo.assertNoUnique(this.field, !adapt);
         vinfo.assertNoIndex(this.field, !adapt);
         ValueMapping var10000 = this.field.getValueMapping();
         FieldMapping var10001 = this.field;
         FieldMapping var10002 = this.field;
         var10000.resolve(1 | 2);
         Column col = vinfo.getNullIndicatorColumn(this.field, this.field.getName(), this.field.getTable(), adapt);
         if (col != null) {
            this.field.setColumns(new Column[]{col});
            this.field.setColumnIO(vinfo.getColumnIO());
         }

         this.field.mapPrimaryKey(adapt);
      }
   }

   public void initialize() {
      Column[] cols = this.field.getColumns();
      if (cols.length != 1) {
         this._synthetic = false;
      } else {
         Column col = cols[0];
         boolean found = false;
         FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();

         for(int i = 0; !found && i < fields.length; ++i) {
            cols = fields[i].getColumns();

            for(int j = 0; j < cols.length; ++j) {
               if (cols[j].equals(col)) {
                  found = true;
               }
            }
         }

         this._synthetic = !found;
      }

   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = this.field.getRow(sm, store, rm, 1);
      if (row != null) {
         OpenJPAStateManager em = store.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
         this.insert(sm, em, store, rm, row);
      }
   }

   public void insert(OpenJPAStateManager owner, OpenJPAStateManager sm, JDBCStore store, RowManager rm, Row row) throws SQLException {
      OpenJPAStateManager em = sm;
      if (sm == null) {
         em = new NullEmbeddedStateManager(owner, this.field);
      }

      RowManager rm = new EmbeddedRowManager(rm, row);
      FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fields.length; ++i) {
         if (!Boolean.TRUE.equals(fields[i].isCustomInsert((OpenJPAStateManager)em, store)) && !fields[i].isPrimaryKey()) {
            fields[i].insert((OpenJPAStateManager)em, store, rm);
         }
      }

      if (this.field.getColumnIO().isInsertable(0, true)) {
         this.setNullIndicatorColumn(sm, row);
      }

   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      OpenJPAStateManager em = store.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
      boolean newVal = em == null || em.getPCState() == PCState.ECOPY;
      Row row = null;
      if (newVal && this.field.getJoinForeignKey() != null && this.field.isJoinOuter()) {
         Row del = rm.getRow(this.field.getTable(), 2, sm, true);
         del.whereForeignKey(this.field.getJoinForeignKey(), sm);
         this.delete(sm, (OpenJPAStateManager)null, store, rm, del);
         row = rm.getRow(this.field.getTable(), 1, sm, em != null);
      } else {
         row = rm.getRow(this.field.getTable(), 0, sm, true);
      }

      if (row != null) {
         if (row.getAction() == 1) {
            this.insert(sm, em, store, rm, row);
         } else {
            this.update(sm, em, store, rm, row);
         }

      }
   }

   public void update(OpenJPAStateManager owner, OpenJPAStateManager sm, JDBCStore store, RowManager rm, Row row) throws SQLException {
      OpenJPAStateManager em = sm;
      if (sm == null) {
         em = new NullEmbeddedStateManager(owner, this.field);
      }

      RowManager rm = new EmbeddedRowManager(rm, row);
      FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fields.length; ++i) {
         if (((OpenJPAStateManager)em).getDirty().get(i) && !((OpenJPAStateManager)em).getFlushed().get(i) && !Boolean.TRUE.equals(fields[i].isCustomUpdate((OpenJPAStateManager)em, store))) {
            fields[i].update((OpenJPAStateManager)em, store, rm);
         }
      }

      if (this.field.getColumnIO().isUpdatable(0, true)) {
         this.setNullIndicatorColumn(sm, row);
      }

   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      OpenJPAStateManager em = null;
      if (sm.getLoaded().get(this.field.getIndex())) {
         em = store.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
      }

      Row row = this.field.getRow(sm, store, rm, 2);
      this.delete(sm, em, store, rm, row);
   }

   public void delete(OpenJPAStateManager owner, OpenJPAStateManager sm, JDBCStore store, RowManager rm, Row row) throws SQLException {
      RowManager rm = new EmbeddedRowManager(rm, row);
      if (sm == null) {
         sm = new NullEmbeddedStateManager(owner, this.field);
      }

      FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fields.length; ++i) {
         if (!Boolean.TRUE.equals(fields[i].isCustomDelete((OpenJPAStateManager)sm, store))) {
            fields[i].delete((OpenJPAStateManager)sm, store, rm);
         }
      }

   }

   private void setNullIndicatorColumn(OpenJPAStateManager sm, Row row) throws SQLException {
      if (this._synthetic) {
         Column col = this.field.getColumns()[0];
         Object val = ((EmbeddedClassStrategy)this.field.getEmbeddedMapping().getStrategy()).getNullIndicatorValue(sm);
         if (val == null) {
            row.setNull(col, true);
         } else {
            row.setObject(col, val);
         }

      }
   }

   public Boolean isCustomInsert(OpenJPAStateManager sm, JDBCStore store) {
      OpenJPAStateManager em = sm.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
      Boolean custom = this.isCustom(0, sm, em, store);
      return Boolean.TRUE.equals(custom) && this._synthetic ? null : custom;
   }

   public Boolean isCustomUpdate(OpenJPAStateManager sm, JDBCStore store) {
      OpenJPAStateManager em = sm.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
      Boolean custom = this.isCustom(1, sm, em, store);
      return Boolean.TRUE.equals(custom) && this._synthetic ? null : custom;
   }

   public Boolean isCustomDelete(OpenJPAStateManager sm, JDBCStore store) {
      OpenJPAStateManager em = sm.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
      return this.isCustom(2, sm, em, store);
   }

   private Boolean isCustom(int action, OpenJPAStateManager owner, OpenJPAStateManager sm, JDBCStore store) {
      if (sm == null) {
         sm = new NullEmbeddedStateManager(owner, this.field);
      }

      boolean hasCustom = false;
      boolean hasStd = false;
      FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();
      Boolean custom = null;

      for(int i = 0; i < fields.length; ++i) {
         switch (action) {
            case 0:
               custom = fields[i].isCustomInsert((OpenJPAStateManager)sm, store);
               break;
            case 1:
               custom = fields[i].isCustomUpdate((OpenJPAStateManager)sm, store);
               break;
            case 2:
               custom = fields[i].isCustomDelete((OpenJPAStateManager)sm, store);
         }

         if (!Boolean.FALSE.equals(custom)) {
            hasCustom = true;
         }

         if (!Boolean.TRUE.equals(custom)) {
            hasStd = true;
         }
      }

      if (hasCustom && hasStd) {
         return null;
      } else {
         return hasCustom ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   public void customInsert(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      OpenJPAStateManager em = store.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
      if (em == null) {
         em = new NullEmbeddedStateManager(sm, this.field);
      }

      FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fields.length; ++i) {
         if (!Boolean.FALSE.equals(fields[i].isCustomInsert((OpenJPAStateManager)em, store))) {
            fields[i].customInsert((OpenJPAStateManager)em, store);
         }
      }

   }

   public void customUpdate(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      OpenJPAStateManager em = store.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
      if (em == null) {
         em = new NullEmbeddedStateManager(sm, this.field);
      }

      FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fields.length; ++i) {
         if (((OpenJPAStateManager)em).getDirty().get(i) && !((OpenJPAStateManager)em).getFlushed().get(i) && !Boolean.FALSE.equals(fields[i].isCustomUpdate((OpenJPAStateManager)em, store))) {
            fields[i].customUpdate((OpenJPAStateManager)em, store);
         }
      }

   }

   public void customDelete(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      OpenJPAStateManager em = store.getContext().getStateManager(sm.fetchObject(this.field.getIndex()));
      if (em == null) {
         em = new NullEmbeddedStateManager(sm, this.field);
      }

      FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();

      for(int i = 0; i < fields.length; ++i) {
         if (!Boolean.FALSE.equals(fields[i].isCustomDelete((OpenJPAStateManager)em, store))) {
            fields[i].customDelete((OpenJPAStateManager)em, store);
         }
      }

   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      return type == 3 && sel.isSelected(this.field.getTable()) ? 1 : 0;
   }

   public int select(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      Joins joins = this.field.join(sel);
      sel.select(this.field.getColumns(), joins);
      eagerMode = Math.min(eagerMode, 1);
      sel.select(this.field.getEmbeddedMapping(), 4, store, fetch, eagerMode, joins);
      return 1;
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      Boolean isNull = this.indicatesNull(res);
      if (isNull != null) {
         if (Boolean.TRUE.equals(isNull)) {
            sm.storeObject(this.field.getIndex(), (Object)null);
         } else {
            StoreContext ctx = store.getContext();
            OpenJPAStateManager em = ctx.embed((Object)null, (Object)null, sm, this.field);
            sm.storeObject(this.field.getIndex(), em.getManagedInstance());
            FieldMapping[] fields = this.field.getEmbeddedMapping().getFieldMappings();
            boolean needsLoad = false;

            for(int i = 0; i < fields.length; ++i) {
               Object eres = res.getEager(fields[i]);
               res.startDataRequest(fields[i]);

               try {
                  if (eres == res) {
                     fields[i].loadEagerJoin(em, store, fetch, res);
                  } else if (eres != null) {
                     Object processed = fields[i].loadEagerParallel(em, store, fetch, eres);
                     if (processed != eres) {
                        res.putEager(fields[i], processed);
                     }
                  } else {
                     fields[i].load(em, store, fetch, res);
                  }

                  needsLoad = needsLoad || !em.getLoaded().get(i) && fetch.requiresFetch(fields[i]) == 1;
               } finally {
                  res.endDataRequest();
               }
            }

            if (needsLoad && fetch.requiresFetch(this.field.getFieldMetaData()) == 1) {
               em.load(fetch);
            }

         }
      }
   }

   private Boolean indicatesNull(Result res) throws SQLException {
      Column[] cols = this.field.getColumns();
      if (cols.length != 1) {
         return Boolean.FALSE;
      } else if (!res.contains(cols[0])) {
         return null;
      } else {
         Object obj = res.getObject(cols[0], -1, (Object)null);
         return ((EmbeddedClassStrategy)this.field.getEmbeddedMapping().getStrategy()).indicatesNull(obj) ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      ClassMapping type = this.field.getTypeMapping();
      return type.toDataStoreValue(val, type.getPrimaryKeyColumns(), store);
   }

   public void appendIsNull(SQLBuffer sql, Select sel, Joins joins) {
      this.appendTestNull(sql, sel, joins, true);
   }

   public void appendIsNotNull(SQLBuffer sql, Select sel, Joins joins) {
      this.appendTestNull(sql, sel, joins, false);
   }

   private void appendTestNull(SQLBuffer sql, Select sel, Joins joins, boolean isNull) {
      Column[] cols = this.field.getColumns();
      if (cols.length != 1) {
         if (isNull) {
            sql.append("1 <> 1");
         } else {
            sql.append("1 = 1");
         }

      } else {
         Object val;
         if (cols[0].isNotNull()) {
            if (!this._synthetic && cols[0].getDefaultString() != null) {
               val = cols[0].getDefault();
            } else {
               val = JavaSQLTypes.getEmptyValue(cols[0].getJavaType());
            }
         } else {
            val = null;
         }

         joins = this.join(joins, false);
         sql.append(sel.getColumnAlias(cols[0], joins));
         if (isNull && val == null) {
            sql.append(" IS ");
         } else if (isNull) {
            sql.append(" = ");
         } else if (val == null) {
            sql.append(" IS NOT ");
         } else {
            sql.append(" <> ");
         }

         sql.appendValue(val, cols[0]);
      }
   }

   public Joins join(Joins joins, boolean forceOuter) {
      return this.field.join(joins, forceOuter, false);
   }

   public Object loadProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      throw new UserException(_loc.get("cant-project-owned", (Object)this.field));
   }

   private static class EmbeddedRow implements Row {
      private final Row _row;

      public EmbeddedRow(Row delegate) {
         this._row = delegate;
      }

      public Row getDelegate() {
         return this._row;
      }

      public boolean isValid() {
         return this._row.isValid();
      }

      public void setValid(boolean valid) {
         this._row.setValid(valid);
      }

      public void setPrimaryKey(OpenJPAStateManager sm) throws SQLException {
         this._row.setPrimaryKey(this.getOwner(sm));
      }

      public void setPrimaryKey(ColumnIO io, OpenJPAStateManager sm) throws SQLException {
         this._row.setPrimaryKey(io, this.getOwner(sm));
      }

      public void wherePrimaryKey(OpenJPAStateManager sm) throws SQLException {
         this._row.wherePrimaryKey(this.getOwner(sm));
      }

      public void setForeignKey(ForeignKey fk, OpenJPAStateManager sm) throws SQLException {
         this._row.setForeignKey(fk, this.getOwner(sm));
      }

      public void setForeignKey(ForeignKey fk, ColumnIO io, OpenJPAStateManager sm) throws SQLException {
         this._row.setForeignKey(fk, io, this.getOwner(sm));
      }

      public void whereForeignKey(ForeignKey fk, OpenJPAStateManager sm) throws SQLException {
         this._row.whereForeignKey(fk, this.getOwner(sm));
      }

      public void setRelationId(Column col, OpenJPAStateManager sm, RelationId rel) throws SQLException {
         this._row.setRelationId(col, this.getOwner(sm), rel);
      }

      private OpenJPAStateManager getOwner(OpenJPAStateManager sm) {
         while(sm != null && sm.getOwner() != null) {
            sm = sm.getOwner();
         }

         return sm;
      }

      public Table getTable() {
         return this._row.getTable();
      }

      public int getAction() {
         return this._row.getAction();
      }

      public Object getFailedObject() {
         return this._row.getFailedObject();
      }

      public void setFailedObject(Object failed) {
         this._row.setFailedObject(failed);
      }

      public OpenJPAStateManager getPrimaryKey() {
         return this._row.getPrimaryKey();
      }

      public void setArray(Column col, Array val) throws SQLException {
         this._row.setArray(col, val);
      }

      public void setAsciiStream(Column col, InputStream val, int length) throws SQLException {
         this._row.setAsciiStream(col, val, length);
      }

      public void setBigDecimal(Column col, BigDecimal val) throws SQLException {
         this._row.setBigDecimal(col, val);
      }

      public void setBigInteger(Column col, BigInteger val) throws SQLException {
         this._row.setBigInteger(col, val);
      }

      public void setBinaryStream(Column col, InputStream val, int length) throws SQLException {
         this._row.setBinaryStream(col, val, length);
      }

      public void setBlob(Column col, Blob val) throws SQLException {
         this._row.setBlob(col, val);
      }

      public void setBoolean(Column col, boolean val) throws SQLException {
         this._row.setBoolean(col, val);
      }

      public void setByte(Column col, byte val) throws SQLException {
         this._row.setByte(col, val);
      }

      public void setBytes(Column col, byte[] val) throws SQLException {
         this._row.setBytes(col, val);
      }

      public void setCalendar(Column col, Calendar val) throws SQLException {
         this._row.setCalendar(col, val);
      }

      public void setChar(Column col, char val) throws SQLException {
         this._row.setChar(col, val);
      }

      public void setCharacterStream(Column col, Reader val, int length) throws SQLException {
         this._row.setCharacterStream(col, val, length);
      }

      public void setClob(Column col, Clob val) throws SQLException {
         this._row.setClob(col, val);
      }

      public void setDate(Column col, Date val) throws SQLException {
         this._row.setDate(col, val);
      }

      public void setDate(Column col, java.sql.Date val, Calendar cal) throws SQLException {
         this._row.setDate(col, val, cal);
      }

      public void setDouble(Column col, double val) throws SQLException {
         this._row.setDouble(col, val);
      }

      public void setFloat(Column col, float val) throws SQLException {
         this._row.setFloat(col, val);
      }

      public void setInt(Column col, int val) throws SQLException {
         this._row.setInt(col, val);
      }

      public void setLong(Column col, long val) throws SQLException {
         this._row.setLong(col, val);
      }

      public void setLocale(Column col, Locale val) throws SQLException {
         this._row.setLocale(col, val);
      }

      public void setNull(Column col) throws SQLException {
         this._row.setNull(col);
      }

      public void setNull(Column col, boolean overrideDefault) throws SQLException {
         this._row.setNull(col, overrideDefault);
      }

      public void setNumber(Column col, Number val) throws SQLException {
         this._row.setNumber(col, val);
      }

      public void setObject(Column col, Object val) throws SQLException {
         this._row.setObject(col, val);
      }

      public void setRaw(Column col, String val) throws SQLException {
         this._row.setRaw(col, val);
      }

      public void setShort(Column col, short val) throws SQLException {
         this._row.setShort(col, val);
      }

      public void setString(Column col, String val) throws SQLException {
         this._row.setString(col, val);
      }

      public void setTime(Column col, Time val, Calendar cal) throws SQLException {
         this._row.setTime(col, val, cal);
      }

      public void setTimestamp(Column col, Timestamp val, Calendar cal) throws SQLException {
         this._row.setTimestamp(col, val, cal);
      }

      public void whereArray(Column col, Array val) throws SQLException {
         this._row.whereArray(col, val);
      }

      public void whereAsciiStream(Column col, InputStream val, int length) throws SQLException {
         this._row.whereAsciiStream(col, val, length);
      }

      public void whereBigDecimal(Column col, BigDecimal val) throws SQLException {
         this._row.whereBigDecimal(col, val);
      }

      public void whereBigInteger(Column col, BigInteger val) throws SQLException {
         this._row.whereBigInteger(col, val);
      }

      public void whereBinaryStream(Column col, InputStream val, int length) throws SQLException {
         this._row.whereBinaryStream(col, val, length);
      }

      public void whereBlob(Column col, Blob val) throws SQLException {
         this._row.whereBlob(col, val);
      }

      public void whereBoolean(Column col, boolean val) throws SQLException {
         this._row.whereBoolean(col, val);
      }

      public void whereByte(Column col, byte val) throws SQLException {
         this._row.whereByte(col, val);
      }

      public void whereBytes(Column col, byte[] val) throws SQLException {
         this._row.whereBytes(col, val);
      }

      public void whereCalendar(Column col, Calendar val) throws SQLException {
         this._row.whereCalendar(col, val);
      }

      public void whereChar(Column col, char val) throws SQLException {
         this._row.whereChar(col, val);
      }

      public void whereCharacterStream(Column col, Reader val, int length) throws SQLException {
         this._row.whereCharacterStream(col, val, length);
      }

      public void whereClob(Column col, Clob val) throws SQLException {
         this._row.whereClob(col, val);
      }

      public void whereDate(Column col, Date val) throws SQLException {
         this._row.whereDate(col, val);
      }

      public void whereDate(Column col, java.sql.Date val, Calendar cal) throws SQLException {
         this._row.whereDate(col, val, cal);
      }

      public void whereDouble(Column col, double val) throws SQLException {
         this._row.whereDouble(col, val);
      }

      public void whereFloat(Column col, float val) throws SQLException {
         this._row.whereFloat(col, val);
      }

      public void whereInt(Column col, int val) throws SQLException {
         this._row.whereInt(col, val);
      }

      public void whereLong(Column col, long val) throws SQLException {
         this._row.whereLong(col, val);
      }

      public void whereLocale(Column col, Locale val) throws SQLException {
         this._row.whereLocale(col, val);
      }

      public void whereNull(Column col) throws SQLException {
         this._row.whereNull(col);
      }

      public void whereNumber(Column col, Number val) throws SQLException {
         this._row.whereNumber(col, val);
      }

      public void whereObject(Column col, Object val) throws SQLException {
         this._row.whereObject(col, val);
      }

      public void whereRaw(Column col, String val) throws SQLException {
         this._row.whereRaw(col, val);
      }

      public void whereShort(Column col, short val) throws SQLException {
         this._row.whereShort(col, val);
      }

      public void whereString(Column col, String val) throws SQLException {
         this._row.whereString(col, val);
      }

      public void whereTime(Column col, Time val, Calendar cal) throws SQLException {
         this._row.whereTime(col, val, cal);
      }

      public void whereTimestamp(Column col, Timestamp val, Calendar cal) throws SQLException {
         this._row.whereTimestamp(col, val, cal);
      }
   }

   private static class EmbeddedRowManager implements RowManager {
      private final RowManager _rm;
      private final Row _row;

      public EmbeddedRowManager(RowManager delegate, Row row) {
         this._rm = delegate;
         this._row = row == null ? null : new EmbeddedRow(row);
      }

      public boolean hasAutoAssignConstraints() {
         return false;
      }

      public Collection getInserts() {
         throw new InternalException();
      }

      public Collection getUpdates() {
         throw new InternalException();
      }

      public Collection getDeletes() {
         throw new InternalException();
      }

      public Collection getSecondaryUpdates() {
         throw new InternalException();
      }

      public Collection getSecondaryDeletes() {
         throw new InternalException();
      }

      public Collection getAllRowUpdates() {
         throw new InternalException();
      }

      public Collection getAllRowDeletes() {
         throw new InternalException();
      }

      public Row getRow(Table table, int action, OpenJPAStateManager sm, boolean create) {
         while(sm != null && sm.getOwner() != null) {
            sm = sm.getOwner();
         }

         if (this._row != null && table == this._row.getTable() && action == this._row.getAction()) {
            return this._row;
         } else {
            return new EmbeddedRow(this._rm.getRow(table, action, sm, create));
         }
      }

      public Row getSecondaryRow(Table table, int action) {
         return new EmbeddedRow(this._rm.getSecondaryRow(table, action));
      }

      public void flushSecondaryRow(Row row) throws SQLException {
         this._rm.flushSecondaryRow(((EmbeddedRow)row).getDelegate());
      }

      public Row getAllRows(Table table, int action) {
         return new EmbeddedRow(this._rm.getAllRows(table, action));
      }

      public void flushAllRows(Row row) throws SQLException {
         this._rm.flushAllRows(((EmbeddedRow)row).getDelegate());
      }
   }

   private static class NullEmbeddedStateManager implements OpenJPAStateManager {
      private static final BitSet EMPTY_BITSET = new BitSet();
      private final OpenJPAStateManager _owner;
      private final ValueMetaData _vmd;
      private BitSet _full = null;

      public NullEmbeddedStateManager(OpenJPAStateManager owner, ValueMetaData vmd) {
         this._owner = owner;
         this._vmd = vmd;
      }

      public void initialize(Class forType, PCState state) {
         throw new InternalException();
      }

      public void load(FetchConfiguration fetch) {
         throw new InternalException();
      }

      public boolean assignObjectId(boolean flush) {
         throw new InternalException();
      }

      public Object getManagedInstance() {
         return null;
      }

      public PersistenceCapable getPersistenceCapable() {
         return null;
      }

      public ClassMetaData getMetaData() {
         return this._vmd.getEmbeddedMetaData();
      }

      public OpenJPAStateManager getOwner() {
         return this._owner;
      }

      public int getOwnerIndex() {
         return this._vmd.getFieldMetaData().getIndex();
      }

      public boolean isEmbedded() {
         return true;
      }

      public boolean isTransactional() {
         return true;
      }

      public boolean isPersistent() {
         return true;
      }

      public boolean isNew() {
         return this._owner.isNew();
      }

      public boolean isDeleted() {
         return this._owner.isDeleted();
      }

      public boolean isDetached() {
         return this._owner.isDetached();
      }

      public boolean isVersionUpdateRequired() {
         return this._owner.isVersionUpdateRequired();
      }

      public boolean isVersionCheckRequired() {
         return this._owner.isVersionCheckRequired();
      }

      public boolean isDirty() {
         return true;
      }

      public boolean isFlushed() {
         return this._owner.isFlushed();
      }

      public boolean isFlushedDirty() {
         return this.isFlushed();
      }

      public boolean isProvisional() {
         return this._owner.isProvisional();
      }

      public BitSet getLoaded() {
         if (this._full == null) {
            FieldMetaData[] fmds = this._vmd.getEmbeddedMetaData().getFields();
            this._full = new BitSet(fmds.length);

            for(int i = 0; i < fmds.length; ++i) {
               this._full.set(i);
            }
         }

         return this._full;
      }

      public BitSet getDirty() {
         if (this._full == null) {
            FieldMetaData[] fmds = this._vmd.getEmbeddedMetaData().getFields();
            this._full = new BitSet(fmds.length);

            for(int i = 0; i < fmds.length; ++i) {
               this._full.set(i);
            }
         }

         return this._full;
      }

      public BitSet getFlushed() {
         return EMPTY_BITSET;
      }

      public BitSet getUnloaded(FetchConfiguration fetch) {
         throw new InternalException();
      }

      public Object newProxy(int field) {
         throw new InternalException();
      }

      public Object newFieldProxy(int field) {
         throw new InternalException();
      }

      public boolean isDefaultValue(int field) {
         return true;
      }

      public StoreContext getContext() {
         return this._owner.getContext();
      }

      public Object getId() {
         return this._owner.getId();
      }

      public Object getObjectId() {
         return this._owner.getObjectId();
      }

      public void setObjectId(Object oid) {
         throw new InternalException();
      }

      public Object getLock() {
         return null;
      }

      public void setLock(Object lock) {
         throw new InternalException();
      }

      public Object getVersion() {
         return null;
      }

      public void setVersion(Object version) {
         throw new InternalException();
      }

      public void setNextVersion(Object version) {
         throw new InternalException();
      }

      public PCState getPCState() {
         return this._owner.isDeleted() ? PCState.EDELETED : PCState.ECOPY;
      }

      public Object getImplData() {
         return null;
      }

      public Object setImplData(Object data, boolean cacheable) {
         throw new InternalException();
      }

      public boolean isImplDataCacheable() {
         return false;
      }

      public Object getImplData(int field) {
         return null;
      }

      public Object setImplData(int field, Object data) {
         throw new InternalException();
      }

      public boolean isImplDataCacheable(int field) {
         return false;
      }

      public Object getIntermediate(int field) {
         return null;
      }

      public void setIntermediate(int field, Object value) {
         throw new InternalException();
      }

      public boolean fetchBooleanField(int field) {
         return false;
      }

      public byte fetchByteField(int field) {
         return 0;
      }

      public char fetchCharField(int field) {
         return '\u0000';
      }

      public double fetchDoubleField(int field) {
         return 0.0;
      }

      public float fetchFloatField(int field) {
         return 0.0F;
      }

      public int fetchIntField(int field) {
         return 0;
      }

      public long fetchLongField(int field) {
         return 0L;
      }

      public Object fetchObjectField(int field) {
         return null;
      }

      public short fetchShortField(int field) {
         return 0;
      }

      public String fetchStringField(int field) {
         return null;
      }

      public Object fetchField(int field, boolean transitions) {
         return null;
      }

      public void storeBooleanField(int field, boolean externalVal) {
         throw new InternalException();
      }

      public void storeByteField(int field, byte externalVal) {
         throw new InternalException();
      }

      public void storeCharField(int field, char externalVal) {
         throw new InternalException();
      }

      public void storeDoubleField(int field, double externalVal) {
         throw new InternalException();
      }

      public void storeFloatField(int field, float externalVal) {
         throw new InternalException();
      }

      public void storeIntField(int field, int externalVal) {
         throw new InternalException();
      }

      public void storeLongField(int field, long externalVal) {
         throw new InternalException();
      }

      public void storeObjectField(int field, Object externalVal) {
         throw new InternalException();
      }

      public void storeShortField(int field, short externalVal) {
         throw new InternalException();
      }

      public void storeStringField(int field, String externalVal) {
         throw new InternalException();
      }

      public void storeField(int field, Object value) {
         throw new InternalException();
      }

      public boolean fetchBoolean(int field) {
         return false;
      }

      public byte fetchByte(int field) {
         return 0;
      }

      public char fetchChar(int field) {
         return '\u0000';
      }

      public double fetchDouble(int field) {
         return 0.0;
      }

      public float fetchFloat(int field) {
         return 0.0F;
      }

      public int fetchInt(int field) {
         return 0;
      }

      public long fetchLong(int field) {
         return 0L;
      }

      public Object fetchObject(int field) {
         return null;
      }

      public short fetchShort(int field) {
         return 0;
      }

      public String fetchString(int field) {
         return null;
      }

      public Object fetch(int field) {
         return null;
      }

      public void storeBoolean(int field, boolean externalVal) {
         throw new InternalException();
      }

      public void storeByte(int field, byte externalVal) {
         throw new InternalException();
      }

      public void storeChar(int field, char externalVal) {
         throw new InternalException();
      }

      public void storeDouble(int field, double externalVal) {
         throw new InternalException();
      }

      public void storeFloat(int field, float externalVal) {
         throw new InternalException();
      }

      public void storeInt(int field, int externalVal) {
         throw new InternalException();
      }

      public void storeLong(int field, long externalVal) {
         throw new InternalException();
      }

      public void storeObject(int field, Object externalVal) {
         throw new InternalException();
      }

      public void storeShort(int field, short externalVal) {
         throw new InternalException();
      }

      public void storeString(int field, String externalVal) {
         throw new InternalException();
      }

      public void store(int field, Object value) {
         throw new InternalException();
      }

      public Object fetchInitialField(int field) {
         throw new InternalException();
      }

      public void dirty(int field) {
         throw new InternalException();
      }

      public void removed(int field, Object removed, boolean key) {
         throw new InternalException();
      }

      public boolean beforeRefresh(boolean refreshAll) {
         throw new InternalException();
      }

      public void setRemote(int field, Object value) {
         throw new InternalException();
      }

      public Object getPCPrimaryKey(Object oid, int field) {
         throw new InternalException();
      }

      public StateManager replaceStateManager(StateManager sm) {
         throw new InternalException();
      }

      public Object getGenericContext() {
         return this.getContext();
      }

      public void dirty(String field) {
         throw new InternalException();
      }

      public Object fetchObjectId() {
         return this.getObjectId();
      }

      public void accessingField(int field) {
      }

      public boolean serializing() {
         throw new InternalException();
      }

      public boolean writeDetached(ObjectOutput out) {
         throw new InternalException();
      }

      public void proxyDetachedDeserialized(int idx) {
         throw new InternalException();
      }

      public void settingBooleanField(PersistenceCapable pc, int field, boolean val1, boolean val2, int set) {
         throw new InternalException();
      }

      public void settingCharField(PersistenceCapable pc, int field, char val1, char val2, int set) {
         throw new InternalException();
      }

      public void settingByteField(PersistenceCapable pc, int field, byte val1, byte val2, int set) {
         throw new InternalException();
      }

      public void settingShortField(PersistenceCapable pc, int field, short val1, short val2, int set) {
         throw new InternalException();
      }

      public void settingIntField(PersistenceCapable pc, int field, int val1, int val2, int set) {
         throw new InternalException();
      }

      public void settingLongField(PersistenceCapable pc, int field, long val1, long val2, int set) {
         throw new InternalException();
      }

      public void settingFloatField(PersistenceCapable pc, int field, float val1, float val2, int set) {
         throw new InternalException();
      }

      public void settingDoubleField(PersistenceCapable pc, int field, double val1, double val2, int set) {
         throw new InternalException();
      }

      public void settingStringField(PersistenceCapable pc, int field, String val1, String val2, int set) {
         throw new InternalException();
      }

      public void settingObjectField(PersistenceCapable pc, int field, Object val1, Object val2, int set) {
         throw new InternalException();
      }

      public void providedBooleanField(PersistenceCapable pc, int field, boolean val) {
         throw new InternalException();
      }

      public void providedCharField(PersistenceCapable pc, int field, char val) {
         throw new InternalException();
      }

      public void providedByteField(PersistenceCapable pc, int field, byte val) {
         throw new InternalException();
      }

      public void providedShortField(PersistenceCapable pc, int field, short val) {
         throw new InternalException();
      }

      public void providedIntField(PersistenceCapable pc, int field, int val) {
         throw new InternalException();
      }

      public void providedLongField(PersistenceCapable pc, int field, long val) {
         throw new InternalException();
      }

      public void providedFloatField(PersistenceCapable pc, int field, float val) {
         throw new InternalException();
      }

      public void providedDoubleField(PersistenceCapable pc, int field, double val) {
         throw new InternalException();
      }

      public void providedStringField(PersistenceCapable pc, int field, String val) {
         throw new InternalException();
      }

      public void providedObjectField(PersistenceCapable pc, int field, Object val) {
         throw new InternalException();
      }

      public boolean replaceBooleanField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public char replaceCharField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public byte replaceByteField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public short replaceShortField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public int replaceIntField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public long replaceLongField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public float replaceFloatField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public double replaceDoubleField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public String replaceStringField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }

      public Object replaceObjectField(PersistenceCapable pc, int field) {
         throw new InternalException();
      }
   }
}
