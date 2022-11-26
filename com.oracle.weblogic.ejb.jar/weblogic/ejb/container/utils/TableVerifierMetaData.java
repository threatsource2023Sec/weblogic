package weblogic.ejb.container.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.WLCMPPersistenceManager;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;
import weblogic.utils.collections.ArraySet;

public final class TableVerifierMetaData extends TableVerifier {
   public int checkTableAndColumns(WLCMPPersistenceManager pm, Connection conn, String tableName, String[] columnNames, boolean loadMaps, List variableNames, Map variable2SQLType, Map variable2nullable) {
      ResultSet rs = null;
      boolean tableCheckPassed = false;

      byte var12;
      try {
         DatabaseMetaData dbmd = conn.getMetaData();
         String actualTableName = "";
         String schemaName = null;
         int dotIndex = tableName.indexOf(".");
         if (dotIndex != -1) {
            actualTableName = tableName.substring(dotIndex + 1, tableName.length());
            schemaName = tableName.substring(0, dotIndex);
            schemaName = this.getSchemas(dbmd, schemaName);
            actualTableName = this.getStoredTableName(dbmd, actualTableName, schemaName);
         } else {
            actualTableName = this.getStoredTableName(dbmd, tableName, (String)null);
         }

         if (actualTableName == null) {
            byte var30 = 0;
            return var30;
         }

         tableCheckPassed = true;
         rs = dbmd.getColumns((String)null, schemaName, actualTableName, (String)null);
         List dbColumnNames = new ArrayList();
         List dbColumnTypes = new ArrayList();
         Map dbColumnNullable = new HashMap();

         while(rs.next()) {
            String colName = rs.getString("COLUMN_NAME");
            dbColumnNames.add(colName);
            dbColumnTypes.add(new Integer(rs.getShort("DATA_TYPE")));
            dbColumnNullable.put(colName.toUpperCase(Locale.ENGLISH), new Boolean(rs.getShort("NULLABLE") != 0));
         }

         this.newColumns = new ArraySet();
         this.removedColumns = new ArraySet();

         for(int i = 0; i < columnNames.length; ++i) {
            String colName = columnNames[i];
            if (!this.isColumnPresentInTable(dbColumnNames, colName)) {
               this.newColumns.add(colName);
            } else {
               if (loadMaps) {
                  variable2SQLType.put(variableNames.get(i), dbColumnTypes.get(i));
               }

               if (variable2nullable != null) {
                  variable2nullable.put(variableNames.get(i), dbColumnNullable.get(colName.toUpperCase(Locale.ENGLISH)));
               }
            }
         }

         this.isColumnPresentInTable(dbColumnNames, "WLS_TEMP");
         this.removedColumns.addAll(dbColumnNames);
         byte var32;
         if (this.newColumns.isEmpty() && this.removedColumns.isEmpty()) {
            var32 = 1;
            return var32;
         }

         if (!this.newColumns.isEmpty()) {
            var32 = 3;
            return var32;
         }

         if (this.createDefaultDBMSTable == null || this.createDefaultDBMSTable.equals("CreateOnly") || this.createDefaultDBMSTable.equals("Disabled")) {
            var32 = 1;
            return var32;
         }

         var32 = 3;
         return var32;
      } catch (SQLException var24) {
         Loggable l = EJBLogger.logErrorGettingTableInformationLoggable(tableName, pm.getEjbName(), var24.getMessage());
         this.wldexception = new WLDeploymentException(l.getMessage(), var24);
         byte var13;
         if (tableCheckPassed) {
            var13 = 3;
            return var13;
         }

         var13 = 0;
         return var13;
      } catch (Exception var25) {
         if (tableCheckPassed) {
            var12 = 3;
            return var12;
         }

         var12 = 0;
      } finally {
         closeQuietly(rs);
      }

      return var12;
   }

   public int isTableCreatedByContainer(WLCMPPersistenceManager pm, Connection conn, String tableName) {
      ResultSet rs = null;
      boolean doesTableExists = false;

      byte var10;
      try {
         DatabaseMetaData dbmd = conn.getMetaData();
         String actualTableName = "";
         String schemaName = null;
         int dotIndex = tableName.indexOf(".");
         if (dotIndex != -1) {
            actualTableName = tableName.substring(dotIndex + 1, tableName.length());
            schemaName = tableName.substring(0, dotIndex);
            schemaName = this.getSchemas(dbmd, schemaName);
            actualTableName = this.getStoredTableName(dbmd, actualTableName, schemaName);
         } else {
            actualTableName = this.getStoredTableName(dbmd, tableName, schemaName);
         }

         if (actualTableName != null) {
            doesTableExists = true;
            rs = dbmd.getColumns((String)null, schemaName, actualTableName, (String)null);

            String colName;
            do {
               if (!rs.next()) {
                  var10 = 5;
                  return var10;
               }

               colName = rs.getString("COLUMN_NAME");
            } while(!colName.equalsIgnoreCase("WLS_TEMP"));

            byte var11 = 4;
            return var11;
         }

         var10 = 0;
      } catch (Exception var15) {
         Loggable l = EJBLogger.logErrorGettingTableInformationLoggable(tableName, pm.getEjbName(), var15.getMessage());
         this.wldexception = new WLDeploymentException(l.getMessage(), var15);
         byte var8;
         if (doesTableExists) {
            var8 = 5;
            return var8;
         }

         var8 = 0;
         return var8;
      } finally {
         closeQuietly(rs);
      }

      return var10;
   }

   private String getStoredTableName(DatabaseMetaData dbmd, String tableName, String schemaName) {
      ResultSet rs = null;

      String var6;
      try {
         rs = dbmd.getTables((String)null, schemaName, (String)null, (String[])null);

         String storedTableName;
         while(rs.next()) {
            storedTableName = rs.getString("TABLE_NAME");
            if (tableName.equalsIgnoreCase(storedTableName)) {
               var6 = storedTableName;
               return var6;
            }
         }

         storedTableName = null;
         return storedTableName;
      } catch (SQLException var10) {
         var6 = null;
      } finally {
         closeQuietly(rs);
      }

      return var6;
   }

   private String getSchemas(DatabaseMetaData dbmd, String schemaName) {
      ResultSet rs = null;

      String var5;
      try {
         rs = dbmd.getSchemas();

         String storedSchemaName;
         do {
            if (!rs.next()) {
               storedSchemaName = schemaName;
               return storedSchemaName;
            }

            storedSchemaName = rs.getString("TABLE_SCHEM");
         } while(!schemaName.equalsIgnoreCase(storedSchemaName));

         var5 = storedSchemaName;
         return var5;
      } catch (SQLException var9) {
         var5 = schemaName;
      } finally {
         closeQuietly(rs);
      }

      return var5;
   }

   private boolean isColumnPresentInTable(List dbColumnNames, String colName) {
      ListIterator lit = dbColumnNames.listIterator();

      do {
         if (!lit.hasNext()) {
            return false;
         }
      } while(!colName.equalsIgnoreCase((String)lit.next()));

      lit.remove();
      return true;
   }
}
