package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.util.ReferenceHashSet;

public class InformixDictionary extends DBDictionary {
   public boolean lockModeEnabled = false;
   public int lockWaitSeconds = 30;
   public boolean swapSchemaAndCatalog = true;
   private final Collection _seenConnections = new ReferenceHashSet(2);

   public InformixDictionary() {
      this.platform = "Informix";
      this.validationSQL = "SELECT FIRST 1 CURRENT TIMESTAMP FROM informix.systables";
      this.supportsAutoAssign = true;
      this.autoAssignTypeName = "serial";
      this.lastGeneratedKeyQuery = "SELECT FIRST 1 DBINFO('sqlca.sqlerrd1') FROM informix.systables";
      this.supportsDeferredConstraints = false;
      this.constraintNameMode = "after";
      this.maxTableNameLength = 18;
      this.maxColumnNameLength = 18;
      this.maxIndexNameLength = 18;
      this.maxConstraintNameLength = 18;
      this.useGetStringForClobs = true;
      this.longVarcharTypeName = "TEXT";
      this.clobTypeName = "TEXT";
      this.smallintTypeName = "INT8";
      this.tinyintTypeName = "INT8";
      this.floatTypeName = "FLOAT";
      this.bitTypeName = "BOOLEAN";
      this.blobTypeName = "BYTE";
      this.doubleTypeName = "NUMERIC(32,20)";
      this.dateTypeName = "DATE";
      this.timeTypeName = "DATETIME HOUR TO SECOND";
      this.timestampTypeName = "DATETIME YEAR TO SECOND";
      this.doubleTypeName = "NUMERIC(32,20)";
      this.floatTypeName = "REAL";
      this.bigintTypeName = "NUMERIC(32,0)";
      this.doubleTypeName = "DOUBLE PRECISION";
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("BYTE", "DOUBLE PRECISION", "INTERVAL", "SMALLFLOAT", "TEXT", "INT8"));
      this.supportsQueryTimeout = false;
      this.supportsMultipleNontransactionalResultSets = false;
      this.supportsLockingWithDistinctClause = false;
      this.supportsLockingWithMultipleTables = false;
      this.supportsLockingWithOrderClause = false;
      this.supportsSchemaForGetColumns = false;
      this.supportsSchemaForGetTables = false;
      this.allowsAliasInBulkClause = false;
      this.supportsSubselect = false;
   }

   public void connectedConfiguration(Connection conn) throws SQLException {
      super.connectedConfiguration(conn);
      if (this.driverVendor == null) {
         DatabaseMetaData meta = conn.getMetaData();
         if ("Informix".equalsIgnoreCase(meta.getDriverName())) {
            this.driverVendor = "datadirect";
         } else {
            this.driverVendor = "other";
         }
      }

   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String columnName, Connection conn) throws SQLException {
      Column[] cols = super.getColumns(meta, catalog, schemaName, tableName, columnName, conn);

      for(int i = 0; cols != null && i < cols.length; ++i) {
         if (cols[i].getType() == -1) {
            cols[i].setType(2005);
         }
      }

      return cols;
   }

   public Column newColumn(ResultSet colMeta) throws SQLException {
      Column col = super.newColumn(colMeta);
      if (this.swapSchemaAndCatalog) {
         col.setSchemaName(colMeta.getString("TABLE_CAT"));
      }

      return col;
   }

   public PrimaryKey newPrimaryKey(ResultSet pkMeta) throws SQLException {
      PrimaryKey pk = super.newPrimaryKey(pkMeta);
      if (this.swapSchemaAndCatalog) {
         pk.setSchemaName(pkMeta.getString("TABLE_CAT"));
      }

      return pk;
   }

   public Index newIndex(ResultSet idxMeta) throws SQLException {
      Index idx = super.newIndex(idxMeta);
      if (this.swapSchemaAndCatalog) {
         idx.setSchemaName(idxMeta.getString("TABLE_CAT"));
      }

      return idx;
   }

   public void setBoolean(PreparedStatement stmnt, int idx, boolean val, Column col) throws SQLException {
      stmnt.setBoolean(idx, val);
   }

   public String[] getCreateTableSQL(Table table) {
      String[] create = super.getCreateTableSQL(table);
      create[0] = create[0] + " LOCK MODE ROW";
      return create;
   }

   public String[] getAddPrimaryKeySQL(PrimaryKey pk) {
      String pksql = this.getPrimaryKeyConstraintSQL(pk);
      return pksql == null ? new String[0] : new String[]{"ALTER TABLE " + this.getFullName(pk.getTable(), false) + " ADD CONSTRAINT " + pksql};
   }

   public String[] getAddForeignKeySQL(ForeignKey fk) {
      String fksql = this.getForeignKeyConstraintSQL(fk);
      return fksql == null ? new String[0] : new String[]{"ALTER TABLE " + this.getFullName(fk.getTable(), false) + " ADD CONSTRAINT " + fksql};
   }

   public boolean supportsRandomAccessResultSet(Select sel, boolean forUpdate) {
      return !forUpdate && !sel.isLob() && super.supportsRandomAccessResultSet(sel, forUpdate);
   }

   public Connection decorate(Connection conn) throws SQLException {
      conn = super.decorate(conn);
      if (this.lockModeEnabled && this._seenConnections.add(conn)) {
         String sql = "SET LOCK MODE TO WAIT";
         if (this.lockWaitSeconds > 0) {
            sql = sql + " " + this.lockWaitSeconds;
         }

         Statement stmnt = null;

         try {
            stmnt = conn.createStatement();
            stmnt.executeUpdate(sql);
         } catch (SQLException var14) {
            throw SQLExceptions.getStore(var14, (DBDictionary)this);
         } finally {
            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var12) {
               }
            }

         }
      }

      if ("datadirect".equalsIgnoreCase(this.driverVendor)) {
         try {
            conn.rollback();
         } catch (SQLException var13) {
         }
      }

      return conn;
   }
}
