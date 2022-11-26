package weblogic.jdbc.rowset;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.RowSetInternal;
import javax.sql.RowSetWriter;
import weblogic.utils.Debug;

public final class CachedRowSetJDBCWriter implements RowSetWriter, Serializable {
   private static final long serialVersionUID = 2948281081383306001L;
   private static final String VERBOSE_JDBC_WRITER = "weblogic.jdbc.rowset.CachedRowSetJDBCWriter.verbose";
   private static final boolean VERBOSE = Boolean.getBoolean("weblogic.jdbc.rowset.CachedRowSetJDBCWriter.verbose");
   private static final String ORACLE_THIN_DRIVER_NAME = "Oracle JDBC driver";
   private static final String WL_OCI_DRIVER_NAME = "Weblogic, Inc. Java-OCI JDBC Driver (weblogicoci39)";

   public boolean writeData(RowSetInternal caller) throws SQLException {
      if (VERBOSE) {
         Debug.say("** BEGIN writeData");
      }

      WLRowSetInternal rowSet = (WLRowSetInternal)caller;
      CachedRowSetMetaData metaData = (CachedRowSetMetaData)rowSet.getMetaData();
      if (metaData.getOptimisticPolicy() == 3 && !metaData.hasSelectedColumn()) {
         throw new SQLException("There is no column selected to verify.");
      } else {
         List tableWriterList = this.buildTableWriters(rowSet);
         Connection con = rowSet.getConnection();
         Iterator it = tableWriterList.iterator();
         if (!it.hasNext()) {
            label49: {
               Iterator rows = rowSet.getCachedRows().iterator();

               CachedRow row;
               do {
                  if (!rows.hasNext()) {
                     break label49;
                  }

                  row = (CachedRow)rows.next();
               } while(!row.isInsertRow() && !row.isDeletedRow() && !row.isUpdatedRow());

               throw new NullUpdateException();
            }
         }

         while(it.hasNext()) {
            TableWriter tw = (TableWriter)it.next();
            tw.issueSQL(con);
         }

         return true;
      }
   }

   private String getTableName(WLRowSetMetaData metaData, int i, boolean bSupportsSchemas, boolean bSupportsCatalogs, boolean bCatalogAtStart, String sCatalogSeparator) throws SQLException {
      String tableName = metaData.getTableName(i + 1);
      String catalogName;
      if (tableName != null && !"".equals(tableName)) {
         if (bSupportsSchemas) {
            catalogName = metaData.getSchemaName(i + 1);
            if (catalogName != null && !"".equals(catalogName)) {
               tableName = catalogName + "." + tableName;
            }
         }

         if (bSupportsCatalogs) {
            catalogName = metaData.getCatalogName(i + 1);
            if (bCatalogAtStart) {
               if (catalogName != null && !"".equals(catalogName)) {
                  tableName = catalogName + sCatalogSeparator + tableName;
               }
            } else if (catalogName != null && !"".equals(catalogName)) {
               tableName = tableName + sCatalogSeparator + catalogName;
            }
         }

         return tableName;
      } else {
         catalogName = metaData.getWriteTableName();
         if (catalogName == null) {
            throw new SQLException("Unable to determine the table name for column: '" + metaData.getColumnName(i + 1) + "'.  Please ensure that you've called WLRowSetMetaData.setTableName  to set a table name for this column.");
         } else {
            return metaData.isPrimaryKeyColumn(i + 1) ? catalogName : null;
         }
      }
   }

   private List buildTableWriters(WLRowSetInternal rowSet) throws SQLException {
      WLRowSetMetaData metaData = (WLRowSetMetaData)rowSet.getMetaData();
      String writeTableName = metaData.getWriteTableName();
      DatabaseMetaData dbmd = rowSet.getConnection().getMetaData();
      boolean bSupportsSchemas = dbmd.supportsSchemasInDataManipulation();
      boolean bSupportsCatalogs = dbmd.supportsCatalogsInDataManipulation();
      boolean bCatalogAtStart = dbmd.isCatalogAtStart();
      String sCatalogSeparator = dbmd.getCatalogSeparator();
      Map tableMap = new HashMap();

      for(int i = 0; i < metaData.getColumnCount(); ++i) {
         String tableName = this.getTableName(metaData, i, bSupportsSchemas, bSupportsCatalogs, bCatalogAtStart, sCatalogSeparator);
         if (writeTableName == null || this.qualifiedTableEqual(metaData, i, dbmd, writeTableName)) {
            BitSet bs = (BitSet)tableMap.get(tableName);
            if (bs == null) {
               bs = new BitSet();
               tableMap.put(tableName, bs);
            }

            bs.set(i);
         }
      }

      Iterator it = tableMap.keySet().iterator();
      ArrayList tableWriterList = new ArrayList();

      while(it.hasNext()) {
         String table = (String)it.next();
         tableWriterList.add(this.createTableWriter(dbmd, rowSet, table, (BitSet)tableMap.get(table)));
      }

      return tableWriterList;
   }

   private TableWriter createTableWriter(DatabaseMetaData dbmd, WLRowSetInternal rowSet, String tableName, BitSet columnMask) throws SQLException {
      if ("oracle".equalsIgnoreCase(dbmd.getDatabaseProductName())) {
         if ("Weblogic, Inc. Java-OCI JDBC Driver (weblogicoci39)".equals(dbmd.getDriverName())) {
            return new OciTableWriter(rowSet, tableName, columnMask);
         } else {
            return (TableWriter)("Oracle JDBC driver".equals(dbmd.getDriverName()) ? new ThinOracleTableWriter(rowSet, tableName, columnMask) : new OracleTableWriter(rowSet, tableName, columnMask));
         }
      } else {
         return (TableWriter)(dbmd.getDriverName().startsWith("IBM DB2 JDBC") ? new OracleTableWriter(rowSet, tableName, columnMask) : new TableWriter(rowSet, tableName, columnMask));
      }
   }

   private boolean qualifiedTableEqual(WLRowSetMetaData rowsetMetaData, int columnIndex, DatabaseMetaData databaseMetaData, String writeTableName) throws SQLException {
      String columnTable = rowsetMetaData.getTableName(columnIndex + 1);
      String columnSchema = rowsetMetaData.getSchemaName(columnIndex + 1);
      String columnCatalog = rowsetMetaData.getCatalogName(columnIndex + 1);
      TableNameParser parser = new TableNameParser(writeTableName, new DatabaseMetaDataHolder(databaseMetaData));
      String[] parts = parser.parse();
      return parser.identifierEqual(columnTable, parts[2]) && parser.identifierEqual(columnSchema, parts[1]) && parser.identifierEqual(columnCatalog, parts[0]);
   }
}
