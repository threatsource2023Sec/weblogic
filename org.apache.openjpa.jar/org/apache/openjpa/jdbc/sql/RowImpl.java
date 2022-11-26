package org.apache.openjpa.jdbc.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.JavaSQLTypes;
import org.apache.openjpa.jdbc.meta.Joinable;
import org.apache.openjpa.jdbc.meta.RelationId;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.InternalException;
import serp.util.Numbers;

public class RowImpl implements Row, Cloneable {
   public static final Object NULL = new Object();
   protected static final int VALID = 2;
   public static final int RAW = Integer.MIN_VALUE;
   protected byte flags;
   private final Column[] _cols;
   private final int _action;
   private final Object[] _vals;
   private final int[] _types;
   private String _sql;

   public RowImpl(Table table, int action) {
      this(table.getColumns(), action);
   }

   protected RowImpl(Column[] cols, int action) {
      this.flags = 0;
      this._sql = null;
      this._cols = cols;
      this._action = action;
      int len = this._cols.length;
      if (action != 1) {
         len *= 2;
      }

      this._vals = new Object[len];
      this._types = new int[len];
   }

   public Table getTable() {
      return this._cols[0].getTable();
   }

   public Column[] getColumns() {
      return this._cols;
   }

   public int getAction() {
      return this._action;
   }

   public boolean isValid() {
      return (this.flags & 2) != 0;
   }

   public void setValid(boolean valid) {
      if (valid) {
         this.flags = (byte)(this.flags | 2);
      } else {
         this.flags &= -3;
      }

   }

   public OpenJPAStateManager getPrimaryKey() {
      return null;
   }

   public Object getFailedObject() {
      return null;
   }

   public void setFailedObject(Object failed) {
      throw new InternalException();
   }

   public boolean isDependent() {
      return false;
   }

   public Object getSet(Column col) {
      return this._vals[this.getSetIndex(col)];
   }

   public Object getWhere(Column col) {
      return this._vals[this.getWhereIndex(col)];
   }

   public void setPrimaryKey(OpenJPAStateManager sm) throws SQLException {
      this.setPrimaryKey((ColumnIO)null, sm);
   }

   public void setPrimaryKey(ColumnIO io, OpenJPAStateManager sm) throws SQLException {
      this.flushPrimaryKey(sm, io, true);
   }

   public void wherePrimaryKey(OpenJPAStateManager sm) throws SQLException {
      this.flushPrimaryKey(sm, (ColumnIO)null, false);
   }

   private void flushPrimaryKey(OpenJPAStateManager sm, ColumnIO io, boolean set) throws SQLException {
      ClassMapping mapping;
      for(mapping = (ClassMapping)sm.getMetaData(); mapping.getTable() != this.getTable(); mapping = mapping.getPCSuperclassMapping()) {
      }

      Column[] cols = mapping.getPrimaryKeyColumns();
      this.flushJoinValues(sm, cols, cols, io, set);
   }

   public void setForeignKey(ForeignKey fk, OpenJPAStateManager sm) throws SQLException {
      this.setForeignKey(fk, (ColumnIO)null, sm);
   }

   public void setForeignKey(ForeignKey fk, ColumnIO io, OpenJPAStateManager sm) throws SQLException {
      this.flushForeignKey(fk, io, sm, true);
   }

   public void whereForeignKey(ForeignKey fk, OpenJPAStateManager sm) throws SQLException {
      this.flushForeignKey(fk, (ColumnIO)null, sm, false);
   }

   public void clearForeignKey(ForeignKey fk) throws SQLException {
      this._sql = null;
      Column[] cols = fk.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         this._vals[this.getSetIndex(cols[i])] = null;
      }

   }

   private void flushForeignKey(ForeignKey fk, ColumnIO io, OpenJPAStateManager sm, boolean set) throws SQLException {
      this.flushJoinValues(sm, fk.getPrimaryKeyColumns(), fk.getColumns(), io, set);
      if (sm != null) {
         Column[] cols = fk.getConstantColumns();
         int len = fk.getColumns().length;

         for(int i = 0; i < cols.length; ++i) {
            Object obj = fk.getConstant(cols[i]);
            int type = cols[i].getJavaType();
            if (set && this.canSet(io, i + len, obj == null)) {
               this.setObject(cols[i], obj, type, false);
            } else if (!set) {
               this.whereObject(cols[i], obj, type);
            }
         }
      }

   }

   private void flushJoinValues(OpenJPAStateManager to, Column[] toCols, Column[] fromCols, ColumnIO io, boolean set) throws SQLException {
      if (to == null) {
         for(int i = 0; i < fromCols.length; ++i) {
            if (set && this.canSet(io, i, true)) {
               this.setNull(fromCols[i]);
            } else if (!set) {
               this.whereNull(fromCols[i]);
            }
         }

      } else if (!set || this.canSetAny(io, fromCols.length, false)) {
         ClassMapping toMapping = (ClassMapping)to.getMetaData();

         for(int i = 0; i < toCols.length; ++i) {
            if (!set || (this._action != 1 || !fromCols[i].isAutoAssigned()) && this.canSet(io, i, false)) {
               Joinable join = toMapping.assertJoinable(toCols[i]);
               Object val = join.getJoinValue(to, toCols[i], (JDBCStore)to.getContext().getStoreManager().getInnermostDelegate());
               if (set && val == null) {
                  if (this.canSet(io, i, true)) {
                     this.setNull(fromCols[i]);
                  }
               } else if (set && val instanceof Raw) {
                  this.setRaw(fromCols[i], val.toString());
               } else if (set) {
                  this.setObject(fromCols[i], val, toCols[i].getJavaType(), false);
               } else if (val == null) {
                  this.whereNull(fromCols[i]);
               } else if (val instanceof Raw) {
                  this.whereRaw(fromCols[i], val.toString());
               } else {
                  this.whereObject(fromCols[i], val, toCols[i].getJavaType());
               }
            }
         }

      }
   }

   protected boolean canSetAny(ColumnIO io, int i, boolean nullValue) {
      if (io == null) {
         return true;
      } else if (this._action == 1) {
         return io.isAnyInsertable(i, nullValue);
      } else {
         return this._action == 0 ? io.isAnyUpdatable(i, nullValue) : true;
      }
   }

   protected boolean canSet(ColumnIO io, int i, boolean nullValue) {
      if (io == null) {
         return true;
      } else if (this._action == 1) {
         return io.isInsertable(i, nullValue);
      } else {
         return this._action == 0 ? io.isUpdatable(i, nullValue) : true;
      }
   }

   public void setRelationId(Column col, OpenJPAStateManager sm, RelationId rel) throws SQLException {
      this.setObject(col, rel.toRelationDataStoreValue(sm, col), col.getJavaType(), false);
   }

   public void clearRelationId(Column col) throws SQLException {
      this._sql = null;
      this._vals[this.getSetIndex(col)] = null;
   }

   public void setArray(Column col, Array val) throws SQLException {
      this.setObject(col, val, 11, false);
   }

   public void setAsciiStream(Column col, InputStream val, int length) throws SQLException {
      this.setObject(col, val == null ? null : new Sized(val, length), 1001, false);
   }

   public void setBigDecimal(Column col, BigDecimal val) throws SQLException {
      this.setObject(col, val, 24, false);
   }

   public void setBigInteger(Column col, BigInteger val) throws SQLException {
      this.setObject(col, val, 25, false);
   }

   public void setBinaryStream(Column col, InputStream val, int length) throws SQLException {
      this.setObject(col, val == null ? null : new Sized(val, length), 1002, false);
   }

   public void setBlob(Column col, Blob val) throws SQLException {
      this.setObject(col, val, 1003, false);
   }

   public void setBoolean(Column col, boolean val) throws SQLException {
      this.setObject(col, val ? Boolean.TRUE : Boolean.FALSE, 0, false);
   }

   public void setByte(Column col, byte val) throws SQLException {
      this.setObject(col, new Byte(val), 1, false);
   }

   public void setBytes(Column col, byte[] val) throws SQLException {
      this.setObject(col, val, 1004, false);
   }

   public void setCalendar(Column col, Calendar val) throws SQLException {
      this.setObject(col, val, 28, false);
   }

   public void setChar(Column col, char val) throws SQLException {
      this.setObject(col, new Character(val), 2, false);
   }

   public void setCharacterStream(Column col, Reader val, int length) throws SQLException {
      this.setObject(col, val == null ? null : new Sized(val, length), 1005, false);
   }

   public void setClob(Column col, Clob val) throws SQLException {
      this.setObject(col, val, 1006, false);
   }

   public void setDate(Column col, Date val) throws SQLException {
      this.setObject(col, val, 14, false);
   }

   public void setDate(Column col, java.sql.Date val, Calendar cal) throws SQLException {
      Object obj;
      if (val != null && cal != null) {
         obj = new Calendard(val, cal);
      } else {
         obj = val;
      }

      this.setObject(col, obj, 1007, false);
   }

   public void setDouble(Column col, double val) throws SQLException {
      this.setObject(col, new Double(val), 3, false);
   }

   public void setFloat(Column col, float val) throws SQLException {
      this.setObject(col, new Float(val), 4, false);
   }

   public void setInt(Column col, int val) throws SQLException {
      this.setObject(col, Numbers.valueOf(val), 5, false);
   }

   public void setLong(Column col, long val) throws SQLException {
      this.setObject(col, Numbers.valueOf(val), 6, false);
   }

   public void setLocale(Column col, Locale val) throws SQLException {
      this.setObject(col, val, 26, false);
   }

   public void setNull(Column col) throws SQLException {
      this.setNull(col, false);
   }

   public void setNull(Column col, boolean overrideDefault) throws SQLException {
      this.setObject(col, (Object)null, col.getJavaType(), overrideDefault);
   }

   public void setNumber(Column col, Number val) throws SQLException {
      this.setObject(col, val, 10, false);
   }

   public void setRaw(Column col, String val) throws SQLException {
      this.setObject(col, val, Integer.MIN_VALUE, false);
   }

   public void setShort(Column col, short val) throws SQLException {
      this.setObject(col, new Short(val), 7, false);
   }

   public void setString(Column col, String val) throws SQLException {
      this.setObject(col, val, 9, false);
   }

   public void setTime(Column col, Time val, Calendar cal) throws SQLException {
      Object obj;
      if (val != null && cal != null) {
         obj = new Calendard(val, cal);
      } else {
         obj = val;
      }

      this.setObject(col, obj, 1010, false);
   }

   public void setTimestamp(Column col, Timestamp val, Calendar cal) throws SQLException {
      Object obj;
      if (val != null && cal != null) {
         obj = new Calendard(val, cal);
      } else {
         obj = val;
      }

      this.setObject(col, obj, 1011, false);
   }

   public void setObject(Column col, Object val) throws SQLException {
      if (val instanceof Raw) {
         this.setObject(col, val, Integer.MIN_VALUE, false);
      } else {
         this.setObject(col, val, col.getJavaType(), false);
      }

   }

   public void whereArray(Column col, Array val) throws SQLException {
      this.whereObject(col, val, 1000);
   }

   public void whereAsciiStream(Column col, InputStream val, int length) throws SQLException {
      this.whereObject(col, val == null ? null : new Sized(val, length), 1001);
   }

   public void whereBigDecimal(Column col, BigDecimal val) throws SQLException {
      this.whereObject(col, val, 24);
   }

   public void whereBigInteger(Column col, BigInteger val) throws SQLException {
      this.whereObject(col, val, 25);
   }

   public void whereBinaryStream(Column col, InputStream val, int length) throws SQLException {
      this.whereObject(col, val == null ? null : new Sized(val, length), 1002);
   }

   public void whereBlob(Column col, Blob val) throws SQLException {
      this.whereObject(col, val, 1003);
   }

   public void whereBoolean(Column col, boolean val) throws SQLException {
      this.whereObject(col, val ? Boolean.TRUE : Boolean.FALSE, 0);
   }

   public void whereByte(Column col, byte val) throws SQLException {
      this.whereObject(col, new Byte(val), 1);
   }

   public void whereBytes(Column col, byte[] val) throws SQLException {
      this.whereObject(col, val, 1004);
   }

   public void whereCalendar(Column col, Calendar val) throws SQLException {
      this.whereObject(col, val, 28);
   }

   public void whereChar(Column col, char val) throws SQLException {
      this.whereObject(col, new Character(val), 2);
   }

   public void whereCharacterStream(Column col, Reader val, int length) throws SQLException {
      this.whereObject(col, val == null ? null : new Sized(val, length), 1005);
   }

   public void whereClob(Column col, Clob val) throws SQLException {
      this.whereObject(col, val, 1006);
   }

   public void whereDate(Column col, Date val) throws SQLException {
      this.whereObject(col, val, 14);
   }

   public void whereDate(Column col, java.sql.Date val, Calendar cal) throws SQLException {
      Object obj;
      if (val != null && cal != null) {
         obj = new Calendard(val, cal);
      } else {
         obj = val;
      }

      this.whereObject(col, obj, 1007);
   }

   public void whereDouble(Column col, double val) throws SQLException {
      this.whereObject(col, new Double(val), 3);
   }

   public void whereFloat(Column col, float val) throws SQLException {
      this.whereObject(col, new Float(val), 4);
   }

   public void whereInt(Column col, int val) throws SQLException {
      this.whereObject(col, Numbers.valueOf(val), 5);
   }

   public void whereLong(Column col, long val) throws SQLException {
      this.whereObject(col, Numbers.valueOf(val), 6);
   }

   public void whereLocale(Column col, Locale val) throws SQLException {
      this.whereObject(col, val, 26);
   }

   public void whereNull(Column col) throws SQLException {
      this.whereObject(col, (Object)null, col.getJavaType());
   }

   public void whereNumber(Column col, Number val) throws SQLException {
      this.whereObject(col, val, 10);
   }

   public void whereRaw(Column col, String val) throws SQLException {
      this.whereObject(col, val, Integer.MIN_VALUE);
   }

   public void whereShort(Column col, short val) throws SQLException {
      this.whereObject(col, new Short(val), 7);
   }

   public void whereString(Column col, String val) throws SQLException {
      this.whereObject(col, val, 9);
   }

   public void whereTime(Column col, Time val, Calendar cal) throws SQLException {
      Object obj;
      if (val != null && cal != null) {
         obj = new Calendard(val, cal);
      } else {
         obj = val;
      }

      this.whereObject(col, obj, 1010);
   }

   public void whereTimestamp(Column col, Timestamp val, Calendar cal) throws SQLException {
      Object obj;
      if (val != null && cal != null) {
         obj = new Calendard(val, cal);
      } else {
         obj = val;
      }

      this.whereObject(col, obj, 1011);
   }

   public void whereObject(Column col, Object val) throws SQLException {
      if (val instanceof Raw) {
         this.whereObject(col, val, Integer.MIN_VALUE);
      } else {
         this.whereObject(col, val, col.getJavaType());
      }

   }

   protected void setObject(Column col, Object val, int metaType, boolean overrideDefault) throws SQLException {
      if (this._action == 1) {
         if (col.isAutoAssigned()) {
            return;
         }

         if (!overrideDefault && val == null && col.getDefaultString() != null) {
            return;
         }
      }

      if (val == null && col.isNotNull()) {
         val = JavaSQLTypes.getEmptyValue(col.getJavaType());
      }

      this.flush(col, val, metaType, true);
   }

   protected void whereObject(Column col, Object val, int metaType) throws SQLException {
      this.flush(col, val, metaType, false);
   }

   private void flush(Column col, Object val, int metaType, boolean set) {
      int idx = set ? this.getSetIndex(col) : this.getWhereIndex(col);
      this._types[idx] = metaType;
      if (val == null) {
         this._vals[idx] = NULL;
      } else {
         this._vals[idx] = val;
      }

      if (set || this._action == 2) {
         this.setValid(true);
      }

   }

   public String getSQL(DBDictionary dict) {
      if (!this.isValid()) {
         return "";
      } else {
         if (this._sql == null) {
            this._sql = this.generateSQL(dict);
         }

         return this._sql;
      }
   }

   protected String generateSQL(DBDictionary dict) {
      switch (this.getAction()) {
         case 0:
            return this.getUpdateSQL(dict);
         case 1:
            return this.getInsertSQL(dict);
         default:
            return this.getDeleteSQL(dict);
      }
   }

   private String getUpdateSQL(DBDictionary dict) {
      StringBuffer buf = new StringBuffer();
      buf.append("UPDATE ").append(dict.getFullName(this.getTable(), false)).append(" SET ");
      boolean hasVal = false;

      for(int i = 0; i < this._cols.length; ++i) {
         if (this._vals[i] != null) {
            if (hasVal) {
               buf.append(", ");
            }

            buf.append(this._cols[i]);
            if (this._types[i] == Integer.MIN_VALUE) {
               buf.append(" = ").append(this._vals[i]);
            } else {
               buf.append(" = ?");
            }

            hasVal = true;
         }
      }

      this.appendWhere(buf, dict);
      return buf.toString();
   }

   private String getInsertSQL(DBDictionary dict) {
      StringBuffer buf = new StringBuffer();
      StringBuffer vals = new StringBuffer();
      buf.append("INSERT INTO ").append(dict.getFullName(this.getTable(), false)).append(" (");
      boolean hasVal = false;

      for(int i = 0; i < this._cols.length; ++i) {
         if (this._vals[i] != null) {
            if (hasVal) {
               buf.append(", ");
               vals.append(", ");
            }

            buf.append(this._cols[i]);
            if (this._types[i] == Integer.MIN_VALUE) {
               vals.append(this._vals[i]);
            } else {
               vals.append("?");
            }

            hasVal = true;
         }
      }

      buf.append(") VALUES (").append(vals.toString()).append(")");
      return buf.toString();
   }

   private String getDeleteSQL(DBDictionary dict) {
      StringBuffer buf = new StringBuffer();
      buf.append("DELETE FROM ").append(dict.getFullName(this.getTable(), false));
      this.appendWhere(buf, dict);
      return buf.toString();
   }

   private void appendWhere(StringBuffer buf, DBDictionary dict) {
      boolean hasWhere = false;

      for(int i = 0; i < this._cols.length; ++i) {
         if (this._vals[this.getWhereIndex(this._cols[i])] != null) {
            if (!hasWhere) {
               buf.append(" WHERE ");
            } else {
               buf.append(" AND ");
            }

            if (this._cols[i].getVersionStrategy() != null) {
               buf.append(dict.getVersionColumn(this._cols[i], this._cols[i].getTableName())).append(" = ?");
            } else if (this._vals[this.getWhereIndex(this._cols[i])] == NULL) {
               buf.append(this._cols[i]).append(" IS NULL");
            } else if (this._types[i] == Integer.MIN_VALUE) {
               buf.append(this._cols[i]).append(" = ").append(this._vals[i]);
            } else {
               buf.append(this._cols[i]).append(" = ?");
            }

            hasWhere = true;
         }
      }

   }

   public int getParameterCount() {
      return this._vals.length;
   }

   public void flush(PreparedStatement stmnt, DBDictionary dict, JDBCStore store) throws SQLException {
      this.flush(stmnt, 1, dict, store);
   }

   public void flush(PreparedStatement stmnt, int idx, DBDictionary dict, JDBCStore store) throws SQLException {
      int i = this.getAction() == 2 ? this._cols.length : 0;

      for(int half = this._vals.length / 2; i < this._vals.length; ++i) {
         if (this._vals[i] != null && (this._vals[i] != NULL || this.getAction() == 1 || i < half)) {
            Column col;
            if (i < this._cols.length) {
               col = this._cols[i];
            } else {
               col = this._cols[i - this._cols.length];
            }

            Object val = this._vals[i];
            if (val == NULL) {
               val = null;
            }

            if (val == null || this._types[i] != Integer.MIN_VALUE) {
               dict.setTyped(stmnt, idx, val, col, this._types[i], store);
               ++idx;
            }
         }
      }

   }

   private int getSetIndex(Column col) {
      return col.getIndex();
   }

   private int getWhereIndex(Column col) {
      return col.getIndex() + this._cols.length;
   }

   public Object clone() {
      RowImpl clone = this.newInstance(this.getColumns(), this.getAction());
      this.copyInto(clone, false);
      return clone;
   }

   protected RowImpl newInstance(Column[] cols, int action) {
      return new RowImpl(cols, action);
   }

   public void copyInto(RowImpl row, boolean whereOnly) {
      int action = this.getAction();
      int rowAction = row.getAction();
      int start;
      int len;
      if (whereOnly) {
         if (action != 1 && rowAction != 1) {
            start = len = this._vals.length / 2;
         } else {
            len = 0;
            start = 0;
         }
      } else {
         start = 0;
         if (rowAction == 1 && action != 1) {
            len = this._vals.length / 2;
         } else {
            len = this._vals.length;
         }
      }

      System.arraycopy(this._vals, start, row._vals, start, len);
      System.arraycopy(this._types, start, row._types, start, len);
      if (this.isValid()) {
         row.setValid(true);
      }

   }

   public Object[] getVals() {
      return this._vals;
   }

   public int[] getTypes() {
      return this._types;
   }
}
