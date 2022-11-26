package weblogic.jdbc.rowset;

import java.io.Serializable;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.serial.SerialArray;
import javax.sql.rowset.serial.SerialDatalink;
import javax.sql.rowset.serial.SerialRef;
import javax.sql.rowset.spi.SyncProviderException;

/** @deprecated */
@Deprecated
public final class CachedRow extends AbstractMap implements Serializable, Map, Cloneable {
   private static final long serialVersionUID = -8966306632273347421L;
   private static final boolean VERBOSE = false;
   private static final boolean DEBUG = true;
   private CachedRowSetMetaData metaData;
   private Object[] oldColumns;
   private Object[] columns;
   private transient Object[] conflicts;
   private BitSet modifiedCols;
   private boolean isUpdatedRow;
   private boolean isInsertRow;
   private boolean isDeletedRow;
   private transient CachedRow baseRow;
   private transient Map typeMap;
   private int columnCount;
   private int status;

   public CachedRow(RowSetMetaData metaData) throws SQLException {
      this.status = 3;
      this.metaData = (CachedRowSetMetaData)metaData;
      this.columnCount = metaData.getColumnCount();
      this.columns = new Object[this.columnCount];
      this.modifiedCols = new BitSet(this.columnCount);
      this.isUpdatedRow = false;
      this.isInsertRow = false;
      this.isDeletedRow = false;
   }

   protected Object clone(CachedRowSetMetaData metaData) {
      CachedRow ret = null;

      try {
         ret = (CachedRow)super.clone();
      } catch (Throwable var4) {
         return null;
      }

      ret.metaData = metaData;
      ret.columns = new Object[this.columns.length];
      System.arraycopy(this.columns, 0, ret.columns, 0, this.columns.length);
      ret.modifiedCols = (BitSet)((BitSet)this.modifiedCols.clone());
      if (this.oldColumns != null) {
         ret.oldColumns = new Object[this.oldColumns.length];
         System.arraycopy(this.oldColumns, 0, ret.oldColumns, 0, this.oldColumns.length);
      }

      return ret;
   }

   protected CachedRow createShared(CachedRowSetMetaData metaData) throws SyncProviderException {
      CachedRow ret;
      if (this.baseRow != null) {
         ret = (CachedRow)this.baseRow.clone(metaData);
         ret.baseRow = this.baseRow;
      } else {
         ret = (CachedRow)this.clone(metaData);
         ret.baseRow = this;
      }

      if (ret.oldColumns != null) {
         ret.columns = ret.oldColumns;
      }

      ret.acceptChanges();
      return ret;
   }

   CachedRow getBaseRow() {
      return this.baseRow;
   }

   void copyFrom(CachedRow from) {
      if (from != null) {
         System.arraycopy(from.columns, 0, this.columns, 0, from.columns.length);
      }
   }

   void copyFrom(int destPos, CachedRow from) {
      if (from != null) {
         int[] columns = new int[from.getMetaData().getColumnCount()];

         for(int i = 0; i < from.getMetaData().getColumnCount(); ++i) {
            columns[i] = i + 1;
         }

         this.copyFrom(destPos, from, columns);
      }
   }

   void copyFrom(int destPos, CachedRow from, int[] fromPoses) {
      if (from != null) {
         int j = 0;

         for(int i = destPos; i < destPos + fromPoses.length; ++i) {
            this.columns[i] = from.columns[fromPoses[j++] - 1];
         }

      }
   }

   public CachedRow(ResultSet rs, RowSetMetaData md, Map typeMap) throws SQLException {
      this(md);
      this.typeMap = typeMap;

      for(int i = 0; i < this.columnCount; ++i) {
         this.columns[i] = this.retrieveData(rs, i);
      }

   }

   private Object retrieveData(ResultSet rs, int i) throws SQLException {
      Object ret = null;
      if (this.typeMap == null) {
         ret = rs.getObject(i + 1);
      } else {
         ret = rs.getObject(i + 1, this.typeMap);
      }

      if (this.metaData.getColumnType(i + 1) == 93) {
         ret = rs.getTimestamp(i + 1);
      }

      if (ret instanceof NClob) {
         ret = new RowSetNClob((NClob)ret);
      } else if (ret instanceof Clob) {
         ret = new RowSetClob((Clob)ret);
      } else if (ret instanceof Blob) {
         ret = new RowSetBlob((Blob)ret);
      } else if (ret instanceof Array && !(ret instanceof SerialArray)) {
         ret = new SerialArray((Array)ret);
      } else if (ret instanceof Ref) {
         ret = new SerialRef((Ref)ret);
      } else if (ret instanceof URL) {
         ret = new SerialDatalink((URL)ret);
      }

      return ret;
   }

   public CachedRowSetMetaData getMetaData() {
      return this.metaData;
   }

   public void setMetaData(CachedRowSetMetaData metaData) {
      this.metaData = metaData;
   }

   public Object[] getOldColumns() {
      return this.oldColumns;
   }

   public void clearModified() {
      this.modifiedCols.clear();
   }

   public BitSet getModifiedColumns() {
      return this.modifiedCols;
   }

   public boolean isModified(int i) throws SQLException {
      if (i > 0 && i <= this.columnCount) {
         return this.modifiedCols.get(i - 1);
      } else {
         throw new SQLException("There is no column: " + i + " in this RowSet");
      }
   }

   public void setModified(int i, boolean b) throws SQLException {
      if (i > 0 && i <= this.columnCount) {
         if (b) {
            this.modifiedCols.set(i - 1);
         } else {
            this.modifiedCols.clear(i - 1);
         }

      } else {
         throw new SQLException("There is no column: " + i + " in this RowSet");
      }
   }

   public void mergeOriginalValues(CachedRow row, BitSet modifiedCols) throws SQLException {
      this.modifiedCols = modifiedCols;
      Object[] modOrigCols = row.getColumns();
      this.oldColumns = new Object[this.columnCount];

      for(int i = 0; i < this.columnCount; ++i) {
         if (modifiedCols.get(i)) {
            this.oldColumns[i] = modOrigCols[i];
         } else {
            this.oldColumns[i] = this.columns[i];
         }
      }

   }

   public void mergeNewValues(CachedRow row, BitSet modifiedCols) throws SQLException {
      this.modifiedCols = modifiedCols;
      Object[] mergeColums = row.getColumns();
      this.oldColumns = new Object[this.columnCount];

      for(int i = 0; i < this.columnCount; ++i) {
         if (modifiedCols.get(i)) {
            this.oldColumns[i] = mergeColums[i];
         } else {
            this.oldColumns[i] = this.columns[i];
         }
      }

      System.arraycopy(mergeColums, 0, this.columns, 0, this.columnCount);
   }

   public void cancelRowUpdates() {
      if (this.isUpdatedRow) {
         this.columns = this.oldColumns;
         this.oldColumns = null;
         this.isUpdatedRow = false;
      }
   }

   public void acceptChanges() throws SyncProviderException {
      this.oldColumns = null;
      this.isUpdatedRow = false;
      this.isInsertRow = false;
      this.isDeletedRow = false;

      for(int i = 0; i < this.modifiedCols.length(); ++i) {
         this.modifiedCols.clear(i);
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append("[CachedRow]: [" + System.identityHashCode(this) + "] columnCount: " + this.columnCount + " isUpdatedRow: " + this.isUpdatedRow + " modifiedCols: " + this.modifiedCols + " isInsertRow: " + this.isInsertRow + " isDeletedRow: " + this.isDeletedRow);
      sb.append("\nColumns:\n");

      for(int i = 0; i < this.columnCount; ++i) {
         try {
            sb.append("[" + this.metaData.getColumnName(i + 1) + " = " + this.columns[i]);
            if (this.oldColumns != null && this.isModified(i + 1)) {
               sb.append(", old value = " + this.oldColumns[i]);
            }
         } catch (SQLException var4) {
            throw new AssertionError(var4);
         }

         sb.append("] ");
      }

      sb.append("\n\n");
      return sb.toString();
   }

   public boolean isDeletedRow() {
      return this.isDeletedRow;
   }

   public void setDeletedRow(boolean b) throws SQLException {
      if (this.metaData.isReadOnly()) {
         throw new SQLException("This RowSet is Read-Only.  You must  setReadOnly(false) before attempting to delete a row.");
      } else {
         this.isDeletedRow = b;
      }
   }

   public boolean isInsertRow() {
      return this.isInsertRow;
   }

   public void setInsertRow(boolean b) {
      this.isInsertRow = b;
   }

   public boolean isUpdatedRow() {
      return this.isUpdatedRow;
   }

   public void setUpdatedRow(boolean b) {
      this.isUpdatedRow = b;
   }

   public int getColumnCount() {
      return this.columnCount;
   }

   public Object getColumn(int i) throws SQLException {
      try {
         return this.columns[i - 1];
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new SQLException("There is no column: " + i + " in this RowSet");
      }
   }

   public Object getOldColumn(int i) throws SQLException {
      try {
         return this.oldColumns[i - 1];
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new SQLException("There is no column: " + i + " in this RowSet");
      }
   }

   private void copyColumns() {
      this.oldColumns = this.columns;
      this.columns = new Object[this.columnCount];
      System.arraycopy(this.oldColumns, 0, this.columns, 0, this.columnCount);
   }

   void setOriginal(int i, Object o) {
      this.columns[i - 1] = o;
      if (this.oldColumns != null) {
         this.oldColumns[i - 1] = o;
      }

   }

   public void setColumn(int i, Object o) {
      this.columns[i - 1] = o;
   }

   private boolean isVersionColumn(int i) throws SQLException {
      return this.metaData.getOptimisticPolicy() == 6 && this.metaData.isVersionColumn(i);
   }

   public Object updateColumn(int i, Object o) throws SQLException {
      try {
         if (this.metaData.isReadOnly()) {
            throw new SQLException("This RowSet is Read-Only and cannot be updated.");
         } else if (!this.isInsertRow && this.metaData.isReadOnly(i)) {
            throw new SQLException("Column: " + this.metaData.getColumnName(i) + " is marked as read-only and cannot be updated.");
         } else if (this.isInsertRow) {
            this.columns[i - 1] = this.typeConvert(i, o);
            this.modifiedCols.set(i - 1);
            return null;
         } else {
            Object oldValue;
            if (this.isVersionColumn(i)) {
               oldValue = this.columns[i - 1];
               this.columns[i - 1] = this.typeConvert(i, o);
               if (this.isUpdatedRow) {
                  this.oldColumns[i - 1] = this.columns[i - 1];
               }

               return oldValue;
            } else {
               if (!this.isUpdatedRow) {
                  this.copyColumns();
                  this.isUpdatedRow = true;
               }

               oldValue = this.columns[i - 1];
               this.columns[i - 1] = this.typeConvert(i, o);
               this.modifiedCols.set(i - 1);
               return oldValue;
            }
         }
      } catch (ArrayIndexOutOfBoundsException var4) {
         throw new SQLException("There is no column: " + i + " in this RowSet");
      }
   }

   private Object typeConvert(int col, Object o) throws SQLException {
      if (this.metaData != null) {
         switch (this.metaData.getColumnType(col)) {
            case 2004:
               if (o instanceof byte[]) {
                  return new RowSetBlob((byte[])((byte[])o));
               }

               if (o instanceof Blob && !(o instanceof RowSetBlob)) {
                  return new RowSetBlob((Blob)o);
               }
               break;
            case 2005:
               if (o instanceof String) {
                  return new RowSetClob((String)o);
               }

               if (o instanceof char[]) {
                  return new RowSetClob((char[])((char[])o));
               }

               if (o instanceof NClob && !(o instanceof RowSetNClob)) {
                  return new RowSetNClob((NClob)o);
               }

               if (o instanceof Clob && !(o instanceof RowSetClob)) {
                  return new RowSetClob((Clob)o);
               }
               break;
            case 2011:
               if (o instanceof String) {
                  return new RowSetNClob((String)o);
               }

               if (o instanceof char[]) {
                  return new RowSetNClob((char[])((char[])o));
               }

               if (o instanceof NClob && !(o instanceof RowSetNClob)) {
                  return new RowSetNClob((NClob)o);
               }
         }
      }

      return o;
   }

   Object getConflictValue(int index) {
      if (index >= 1 && index <= this.columnCount) {
         if (this.conflicts == null) {
            if (this.status != 1 && this.status != 0) {
               throw new RuntimeException("No conflict has been detected.");
            } else {
               throw new RowNotFoundException("No conflict value available since the corresponding row has already been deleted in the datasource.");
            }
         } else {
            return this.conflicts[index - 1];
         }
      } else {
         throw new RuntimeException("Invalid column index.");
      }
   }

   void setConflictValue(int index, Object val) {
      if (index >= 1 && index <= this.columnCount) {
         if (this.conflicts == null) {
            this.conflicts = new Object[this.columns.length];
         }

         this.conflicts[index - 1] = val;
      } else {
         throw new RuntimeException("Invalid column index.");
      }
   }

   boolean setConflictValue(ResultSet rs, int[] idx) throws SQLException {
      boolean ret = false;
      if (this.conflicts == null) {
         this.conflicts = new Object[this.columns.length];
      }

      int pos = true;

      int pos;
      for(int i = 0; i < idx.length && (pos = idx[i]) != -1; ++i) {
         Object dbVal = this.retrieveData(rs, i);
         this.conflicts[pos] = dbVal;

         try {
            if (dbVal == null) {
               if (this.columns[pos] != null) {
                  ret = true;
               }
            } else if (!dbVal.equals(this.columns[pos])) {
               ret = true;
            }
         } catch (Exception var8) {
            ret = true;
         }
      }

      if (!ret) {
         this.conflicts = null;
      }

      return ret;
   }

   int getStatus() {
      return this.status;
   }

   void setStatus(int s) {
      this.status = s;
   }

   void setResolvedValue(int index, Object obj) {
      if (index >= 1 && index <= this.columnCount) {
         this.columns[index - 1] = obj;
         if (this.oldColumns != null) {
            this.oldColumns[index - 1] = this.conflicts[index - 1];
         }

      } else {
         throw new RuntimeException("Invalid column index.");
      }
   }

   public Object[] getColumns() {
      return this.columns;
   }

   public int size() {
      return this.columnCount;
   }

   public boolean isEmpty() {
      return this.columnCount == 0;
   }

   public boolean containsKey(Object key) {
      return this.get(key) != null;
   }

   public boolean containsValue(Object value) {
      for(int i = 0; i < this.columnCount; ++i) {
         if (this.columns[i] == null) {
            if (value == null) {
               return true;
            }
         } else if (this.columns[i].equals(value)) {
            return true;
         }
      }

      return false;
   }

   public Object get(Object key) {
      try {
         return this.getColumn(this.metaData.findColumn((String)key));
      } catch (SQLException var3) {
         return null;
      } catch (ClassCastException var4) {
         throw new SQLRuntimeException("Key class: " + key.getClass().getName() + " was not java.lang.String", var4);
      }
   }

   public Object put(Object key, Object value) {
      try {
         return this.updateColumn(this.metaData.findColumn((String)key), value);
      } catch (SQLException var4) {
         throw new SQLRuntimeException(var4);
      } catch (ClassCastException var5) {
         throw new SQLRuntimeException("Key class: " + key.getClass().getName() + " was not java.lang.String", var5);
      }
   }

   public Object remove(Object key) {
      return this.put(key, (Object)null);
   }

   public void clear() {
      for(int i = 0; i < this.columnCount; ++i) {
         try {
            this.put(this.metaData.getColumnName(i + 1), (Object)null);
         } catch (SQLException var3) {
            throw new AssertionError(var3);
         }
      }

   }

   public Set entrySet() {
      Set entries = new HashSet(this.columnCount);

      for(int i = 0; i < this.columnCount; ++i) {
         try {
            entries.add(new Entry(this.metaData.getColumnName(i + 1), this.columns[i]));
         } catch (SQLException var4) {
            throw new AssertionError(var4);
         }
      }

      return entries;
   }

   private static class SQLRuntimeException extends RuntimeException {
      private static final long serialVersionUID = 1444694475457733067L;

      SQLRuntimeException(Throwable cause) {
         super(cause);
      }

      SQLRuntimeException(String msg, Throwable cause) {
         super(msg, cause);
      }
   }

   private static final class Entry implements Map.Entry {
      private final Object key;
      private Object value;

      Entry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object v) {
         Object ret = this.value;
         this.value = v;
         return ret;
      }

      public boolean equals(Object e) {
         if (e == this) {
            return true;
         } else if (!(e instanceof Entry)) {
            return false;
         } else {
            Entry other = (Entry)e;
            if (this.key.equals(other.key)) {
               if (this.value == null) {
                  return other.value == null;
               } else {
                  return this.value.equals(other.value);
               }
            } else {
               return false;
            }
         }
      }

      public int hashCode() {
         return this.value == null ? this.key.hashCode() : this.key.hashCode() ^ this.value.hashCode();
      }
   }
}
