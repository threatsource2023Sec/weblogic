package weblogic.jdbc.rowset;

import java.io.CharArrayReader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ThinOracleTableWriter extends OracleTableWriter {
   private List lobUpdateRows = null;
   protected boolean needs2PhaseInsert;
   private Map canonicalLOBUpdateColumns;

   public ThinOracleTableWriter(WLRowSetInternal rowSet, String tableName, BitSet columnMask) throws SQLException {
      super(rowSet, tableName, columnMask);
   }

   protected void insertRow(Connection con, CachedRow row) throws SQLException {
      this.needs2PhaseInsert = false;
      super.insertRow(con, row);
      if (this.needs2PhaseInsert) {
         this.addUpdateRow(row);
      }

   }

   private void addUpdateRow(CachedRow row) {
      if (this.lobUpdateRows == null) {
         this.lobUpdateRows = new ArrayList();
      }

      try {
         for(int i = 0; i < this.metaData.getColumnCount(); ++i) {
            if (this.isLOB(i) && this.tableName.equals(this.metaData.getQualifiedTableName(i + 1)) && (row.isUpdatedRow() && row.isModified(i + 1) || row.isInsertRow())) {
               this.lobUpdateRows.add(row);
               return;
            }
         }
      } catch (Exception var3) {
      }

   }

   protected Object insertedObject(Connection con, Object o) {
      if (o == null) {
         return null;
      } else {
         Class c = o.getClass();
         if (c == RowSetNClob.class) {
            return this.emptyNClob(con);
         } else if (c == RowSetClob.class) {
            return this.emptyClob(con);
         } else {
            return c == RowSetBlob.class ? this.emptyBlob(con) : o;
         }
      }
   }

   protected Object emptyClob(Connection con) {
      this.needs2PhaseInsert = true;

      try {
         return con.createClob();
      } catch (Exception var3) {
         throw new AssertionError(var3);
      }
   }

   protected Object emptyNClob(Connection con) {
      this.needs2PhaseInsert = true;

      try {
         return con.createNClob();
      } catch (Exception var3) {
         throw new AssertionError(var3);
      }
   }

   protected Object emptyBlob(Connection con) {
      this.needs2PhaseInsert = true;

      try {
         return con.createBlob();
      } catch (Exception var3) {
         throw new AssertionError(var3);
      }
   }

   protected BitSet getModifiedColumns(CachedRow row) {
      BitSet mods = row.getModifiedColumns();
      BitSet lobFreeMods = null;
      Object[] values = row.getColumns();

      for(int i = mods.nextSetBit(0); i >= 0; i = mods.nextSetBit(i + 1)) {
         if (values[i] instanceof RowSetBlob || values[i] instanceof RowSetClob) {
            if (lobFreeMods == null) {
               lobFreeMods = (BitSet)mods.clone();
               this.addUpdateRow(row);
            }

            lobFreeMods.clear(i);
         }
      }

      return lobFreeMods != null ? lobFreeMods : mods;
   }

   protected void updateLOBs(Connection con) throws SQLException {
      if (this.lobUpdateRows != null) {
         this.canonicalLOBUpdateColumns = new HashMap();
         boolean var6 = false;

         Iterator i;
         try {
            var6 = true;
            i = this.lobUpdateRows.iterator();

            while(i.hasNext()) {
               this.updateLOBs(con, (CachedRow)i.next());
            }

            var6 = false;
         } finally {
            if (var6) {
               Iterator i = this.canonicalLOBUpdateColumns.values().iterator();

               while(i.hasNext()) {
                  ((LOBUpdateColumns)i.next()).closeStatements();
               }

               this.canonicalLOBUpdateColumns = null;
            }
         }

         i = this.canonicalLOBUpdateColumns.values().iterator();

         while(i.hasNext()) {
            ((LOBUpdateColumns)i.next()).closeStatements();
         }

         this.canonicalLOBUpdateColumns = null;
      }
   }

   private void updateLOBs(Connection con, CachedRow row) throws SQLException {
      LOBUpdateColumns cols = this.getLOBUpdateColumns(con, row);
      ResultSet rs = null;

      for(int i = 0; i < 3; ++i) {
         try {
            rs = cols.selectForUpdate(row);
            if (!rs.next()) {
               this.throwOCE(cols.selectSql, row);
            }

            if (cols.executeUpdate(con, rs, row)) {
               return;
            }
         } finally {
            if (rs != null) {
               rs.close();
            }

         }
      }

      throw new SQLException("Failed to update row in three passes");
   }

   private LOBUpdateColumns getLOBUpdateColumns(Connection con, CachedRow row) throws SQLException {
      LOBUpdateColumns cols = this.newLOBUpdateColumns(row);
      LOBUpdateColumns canonicalCols = (LOBUpdateColumns)this.canonicalLOBUpdateColumns.get(cols);
      if (canonicalCols != null) {
         return canonicalCols;
      } else {
         cols.cacheAll(con);
         this.canonicalLOBUpdateColumns.put(cols, cols);
         return cols;
      }
   }

   protected LOBUpdateColumns newLOBUpdateColumns(CachedRow row) throws SQLException {
      return new LOBUpdateColumns(row);
   }

   class LOBUpdateColumns extends RowSetLob.UpdateHelper {
      private final BitSet lobCols = new BitSet();
      private BitSet keyCols;
      private String selectSql;
      private String updateSql;
      private PreparedStatement selectPS;
      private PreparedStatement updatePS;
      protected boolean updateAgain;

      LOBUpdateColumns(CachedRow row) throws SQLException {
         Object[] values = row.getColumns();
         BitSet modCols = row.getModifiedColumns();

         for(int i = modCols.nextSetBit(0); i >= 0; i = modCols.nextSetBit(i + 1)) {
            if (values[i] instanceof RowSetLob && ThinOracleTableWriter.this.tableName.equals(ThinOracleTableWriter.this.metaData.getQualifiedTableName(i + 1))) {
               this.lobCols.set(i);
            }
         }

      }

      public boolean equals(Object other) {
         return other instanceof LOBUpdateColumns && this.lobCols.equals(((LOBUpdateColumns)other).lobCols);
      }

      public int hashCode() {
         return this.lobCols.hashCode();
      }

      void cacheAll(Connection con) throws SQLException {
         this.keyCols = new BitSet();
         int n = ThinOracleTableWriter.this.metaData.getColumnCount();

         for(int i = 1; i <= n; ++i) {
            if (ThinOracleTableWriter.this.metaData.isPrimaryKeyColumn(i) && ThinOracleTableWriter.this.tableName.equals(ThinOracleTableWriter.this.metaData.getQualifiedTableName(i))) {
               this.keyCols.set(i - 1);
            }
         }

         this.selectSql = this.getSelectSql();
         this.updateSql = this.getUpdateSql();
         this.selectPS = con.prepareStatement(this.selectSql);
         this.updatePS = con.prepareStatement(this.updateSql);
      }

      private String getSelectSql() throws SQLException {
         if (!ThinOracleTableWriter.this.metaData.haveSetPKColumns()) {
            throw new SQLException("You must use the WLRowSetMetaData.setPrimaryKeyColumn() method to establish primary key columns before updating rows.");
         } else {
            StringBuffer buf = new StringBuffer(300);
            buf.append("SELECT ");
            String sep = "";

            for(int i = this.lobCols.nextSetBit(0); i >= 0; i = this.lobCols.nextSetBit(i + 1)) {
               buf.append(sep);
               buf.append(ThinOracleTableWriter.this.metaData.getWriteColumnName(i + 1));
               sep = ", ";
            }

            buf.append(" FROM ").append(ThinOracleTableWriter.this.tableName).append(" WHERE ");
            TableWriter.ColumnFilter filter = TableWriter.getPolicy(ThinOracleTableWriter.this.metaData.getOptimisticPolicy()).getColumnFilter(ThinOracleTableWriter.this, this.lobCols);
            boolean hasKey = false;
            sep = "";

            for(int ix = 0; ix < ThinOracleTableWriter.this.metaData.getColumnCount(); ++ix) {
               if (ThinOracleTableWriter.this.columnMask.get(ix) && !ThinOracleTableWriter.this.isLOB(ix) && filter.include(ix) && ThinOracleTableWriter.this.tableName.equals(ThinOracleTableWriter.this.metaData.getQualifiedTableName(ix + 1))) {
                  String col = ThinOracleTableWriter.this.metaData.getWriteColumnName(ix + 1);
                  if (ThinOracleTableWriter.this.metaData.isPrimaryKeyColumn(ix + 1)) {
                     buf.append(sep).append(col).append("=? ");
                     hasKey = true;
                  } else {
                     buf.append(sep).append("(").append(col).append("=? OR ");
                     buf.append("(1=? AND ").append(col).append(" IS NULL))");
                  }

                  sep = " AND ";
               }
            }

            if (!hasKey) {
               throw new SQLException("You must use the WLRowSetMetaData.setPrimaryKeyColumn() method to establish primary key columns for table " + ThinOracleTableWriter.this.tableName + " before updating rows.");
            } else {
               return buf.append(" FOR UPDATE NOWAIT").toString();
            }
         }
      }

      private String getUpdateSql() throws SQLException {
         StringBuffer buf = new StringBuffer(300);
         buf.append("UPDATE ").append(ThinOracleTableWriter.this.tableName).append(" SET ");
         String sep = "";

         int i;
         for(i = this.lobCols.nextSetBit(0); i >= 0; i = this.lobCols.nextSetBit(i + 1)) {
            buf.append(sep);
            buf.append(ThinOracleTableWriter.this.metaData.getWriteColumnName(i + 1)).append("=?");
            sep = ", ";
         }

         sep = "";
         buf.append(" WHERE ");

         for(i = this.keyCols.nextSetBit(0); i >= 0; i = this.keyCols.nextSetBit(i + 1)) {
            buf.append(sep);
            buf.append(ThinOracleTableWriter.this.metaData.getWriteColumnName(i + 1)).append("=?");
            sep = " AND ";
         }

         return buf.toString();
      }

      void closeStatements() {
         try {
            this.selectPS.close();
         } catch (SQLException var3) {
         }

         try {
            this.updatePS.close();
         } catch (SQLException var2) {
         }

      }

      ResultSet selectForUpdate(CachedRow row) throws SQLException {
         Object[] values = row.getColumns();
         ArrayList params = ThinOracleTableWriter.this.verboseSQL ? new ArrayList() : null;
         int j = 1;
         TableWriter.ColumnFilter filter = TableWriter.getPolicy(ThinOracleTableWriter.this.metaData.getOptimisticPolicy()).getColumnFilter(ThinOracleTableWriter.this, this.lobCols);

         for(int i = 0; i < ThinOracleTableWriter.this.columnCount; ++i) {
            if (ThinOracleTableWriter.this.columnMask.get(i) && !ThinOracleTableWriter.this.isLOB(i) && filter.include(i) && ThinOracleTableWriter.this.tableName.equals(ThinOracleTableWriter.this.metaData.getQualifiedTableName(i + 1))) {
               if (values[i] instanceof Integer) {
                  this.selectPS.setInt(j++, (Integer)values[i]);
               } else if (values[i] instanceof Float) {
                  this.selectPS.setFloat(j++, (Float)values[i]);
               } else if (values[i] instanceof char[]) {
                  this.selectPS.setCharacterStream(j++, new CharArrayReader((char[])((char[])values[i])), ((char[])((char[])values[i])).length);
               } else {
                  this.selectPS.setObject(j++, values[i], ThinOracleTableWriter.this.metaData.getColumnType(i + 1));
               }

               if (ThinOracleTableWriter.this.verboseSQL) {
                  params.add(values[i]);
               }

               if (!ThinOracleTableWriter.this.metaData.isPrimaryKeyColumn(i + 1)) {
                  int nullCode = values[i] == null ? 1 : 0;
                  this.selectPS.setInt(j++, nullCode);
                  if (ThinOracleTableWriter.this.verboseSQL) {
                     params.add(new Integer(nullCode));
                  }
               }
            }
         }

         if (ThinOracleTableWriter.this.verboseSQL) {
            ThinOracleTableWriter.this.printSQL(this.selectSql, params.toArray(new Object[params.size()]));
         }

         return this.selectPS.executeQuery();
      }

      boolean executeUpdate(Connection con, ResultSet rs, CachedRow row) throws SQLException {
         this.updateAgain = false;
         int j = 1;
         Object[] values = row.getColumns();
         ArrayList params = ThinOracleTableWriter.this.verboseSQL ? new ArrayList() : null;

         int i;
         for(i = this.lobCols.nextSetBit(0); i >= 0; i = this.lobCols.nextSetBit(i + 1)) {
            Object updatedLob = ((RowSetLob)values[i]).update(con, rs, j, this);
            if (updatedLob instanceof NClob) {
               this.updatePS.setNClob(j++, (NClob)updatedLob);
            } else {
               this.updatePS.setObject(j++, updatedLob, ThinOracleTableWriter.this.metaData.getColumnType(i + 1));
            }

            if (ThinOracleTableWriter.this.verboseSQL) {
               params.add(updatedLob);
            }
         }

         for(i = this.keyCols.nextSetBit(0); i >= 0; i = this.keyCols.nextSetBit(i + 1)) {
            this.updatePS.setObject(j++, values[i], ThinOracleTableWriter.this.metaData.getColumnType(i + 1));
            if (ThinOracleTableWriter.this.verboseSQL) {
               params.add(values[i]);
            }
         }

         if (ThinOracleTableWriter.this.verboseSQL) {
            ThinOracleTableWriter.this.printSQL(this.updateSql, params.toArray(new Object[params.size()]));
         }

         this.updatePS.execute();
         return !this.updateAgain;
      }

      private int setBits(BitSet s) {
         int n = 0;

         for(int i = s.nextSetBit(0); i >= 0; i = s.nextSetBit(i + 1)) {
            ++n;
         }

         return n;
      }

      protected Object update(Connection con, Blob blob, byte[] data) throws SQLException {
         if (blob != null && blob.length() <= (long)data.length) {
            blob.setBytes(1L, data);
            return blob;
         } else {
            this.updateAgain = true;
            return con.createBlob();
         }
      }

      protected Object update(Connection con, Clob clob, char[] data) throws SQLException {
         if (clob != null && clob.length() <= (long)data.length) {
            Writer writer = clob.setCharacterStream(1L);

            try {
               writer.write(data);
               writer.flush();
               return clob;
            } catch (Exception var6) {
               throw new AssertionError(var6);
            }
         } else {
            this.updateAgain = true;
            return con.createClob();
         }
      }

      protected Object update(Connection con, NClob nclob, char[] data) throws SQLException {
         if (nclob != null && nclob.length() <= (long)data.length) {
            Writer writer = nclob.setCharacterStream(1L);

            try {
               writer.write(data);
               writer.flush();
               return nclob;
            } catch (Exception var6) {
               throw new AssertionError(var6);
            }
         } else {
            this.updateAgain = true;
            return con.createNClob();
         }
      }
   }
}
