package weblogic.ejb.container.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb.container.interfaces.WLCMPPersistenceManager;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;
import weblogic.utils.collections.ArraySet;

public final class TableVerifierSqlQuery extends TableVerifier {
   private boolean fireSql(WLCMPPersistenceManager pm, Connection conn, String tableName, String[] columnNamesToCheck, boolean loadMaps, List variableNames, Map variable2SQLType, Map variable2nullable, String[] ddColumnNames) {
      StringBuilder sqlBuffer = new StringBuilder(200);
      sqlBuffer.append("SELECT ");
      String colNames = this.strArrayToCommaList(columnNamesToCheck);
      sqlBuffer.append(colNames);
      sqlBuffer.append(" FROM ").append(tableName);
      if (pm instanceof RDBMSPersistenceManager) {
         int databaseType = ((RDBMSPersistenceManager)pm).getDatabaseType();
         if (databaseType == 4) {
            sqlBuffer.append(" FETCH FIRST 1 ROWS ONLY");
         } else {
            sqlBuffer.append(" WHERE 1 = 0");
         }
      } else {
         sqlBuffer.append(" WHERE 1 = 0");
      }

      String query = sqlBuffer.toString();
      if (debugLogger.isDebugEnabled()) {
         debug("Using query: " + query);
      }

      Statement stmt = null;
      ResultSet rs = null;

      boolean var15;
      try {
         boolean var17;
         try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (loadMaps) {
               ResultSetMetaData metaData = rs.getMetaData();
               Iterator iterator = variableNames.iterator();

               for(int iResultType = 1; iResultType <= metaData.getColumnCount(); ++iResultType) {
                  assert iterator.hasNext() : "Have no variable names but a result column.";

                  String dbColName;
                  if (colNames.trim().equals("*")) {
                     dbColName = metaData.getColumnName(iResultType);
                     if (!this.isDbColumnInDeploymentDescriptor(ddColumnNames, dbColName) && !"WLS_TEMP".equalsIgnoreCase(dbColName)) {
                        this.removedColumns.add(dbColName);
                     }
                  } else {
                     dbColName = (String)iterator.next();
                     variable2SQLType.put(dbColName, new Integer(metaData.getColumnType(iResultType)));
                     variable2nullable.put(dbColName, new Boolean(metaData.isNullable(iResultType) != 0));
                  }
               }

               var17 = true;
               return var17;
            }

            var15 = true;
         } catch (Exception var22) {
            Loggable l = EJBLogger.logErrorGettingTableInformationLoggable(tableName, pm.getEjbName(), var22.getMessage());
            this.wldexception = new WLDeploymentException(l.getMessage(), var22);
            var17 = false;
            return var17;
         }
      } finally {
         closeQuietly(rs);
         closeQuietly(stmt);
      }

      return var15;
   }

   private boolean isDbColumnInDeploymentDescriptor(String[] ddColumnNames, String dbCol) {
      String[] var3 = ddColumnNames;
      int var4 = ddColumnNames.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String ddColumnName = var3[var5];
         if (dbCol.equalsIgnoreCase(ddColumnName)) {
            return true;
         }
      }

      return false;
   }

   public int checkTableAndColumns(WLCMPPersistenceManager pm, Connection conn, String tableName, String[] columnNames, boolean loadMaps, List variableNames, Map variable2SQLType, Map variable2nullable) {
      this.newColumns = new ArraySet();
      this.removedColumns = new ArraySet();
      if (this.fireSql(pm, conn, tableName, columnNames, loadMaps, variableNames, variable2SQLType, variable2nullable, columnNames)) {
         if (this.createDefaultDBMSTable != null && !this.createDefaultDBMSTable.equals("CreateOnly") && !this.createDefaultDBMSTable.equals("Disabled")) {
            if (this.fireSql(pm, conn, tableName, new String[]{"*"}, true, variableNames, variable2SQLType, variable2nullable, columnNames)) {
               this.removeTempColumnFromTheSet(this.removedColumns, "WLS_TEMP");
               return this.removedColumns.isEmpty() ? 1 : 3;
            } else {
               return 3;
            }
         } else {
            return 1;
         }
      } else if (!this.fireSql(pm, conn, tableName, new String[]{"*"}, true, variableNames, variable2SQLType, variable2nullable, columnNames)) {
         return 0;
      } else {
         for(int i = 0; i < columnNames.length; ++i) {
            if (!this.fireSql(pm, conn, tableName, new String[]{columnNames[i]}, false, (List)null, (Map)null, (Map)null, columnNames)) {
               this.newColumns.add(columnNames[i]);
            }
         }

         if (!this.newColumns.isEmpty()) {
            return 3;
         } else {
            throw new AssertionError("The table verification failed for an unknown reason.  Please try redeploying the bean");
         }
      }
   }

   public int isTableCreatedByContainer(WLCMPPersistenceManager pm, Connection conn, String tableName) {
      StringBuilder sb = new StringBuilder("SELECT * FROM ");
      sb.append(tableName);
      if (pm instanceof RDBMSPersistenceManager) {
         int databaseType = ((RDBMSPersistenceManager)pm).getDatabaseType();
         if (databaseType == 4) {
            sb.append(" FETCH FIRST 1 ROWS ONLY");
         } else {
            sb.append(" WHERE 1 = 0");
         }
      } else {
         sb.append(" WHERE 1 = 0");
      }

      Statement stmt = null;

      byte var7;
      label116: {
         try {
            stmt = conn.createStatement();
            stmt.executeQuery(sb.toString());
            break label116;
         } catch (Exception var20) {
            var7 = 0;
         } finally {
            closeQuietly(stmt);
         }

         return var7;
      }

      try {
         stmt = conn.createStatement();
         stmt.executeQuery("SELECT WLS_TEMP FROM " + tableName);
         byte var6 = 4;
         return var6;
      } catch (Exception var18) {
         var7 = 5;
      } finally {
         closeQuietly(stmt);
      }

      return var7;
   }

   private boolean removeTempColumnFromTheSet(Set removedColumnNames, String colName) {
      Iterator it = removedColumnNames.iterator();

      do {
         if (!it.hasNext()) {
            return false;
         }
      } while(!colName.equalsIgnoreCase((String)it.next()));

      it.remove();
      return true;
   }

   private static void debug(String s) {
      debugLogger.debug("[TableVerifierSqlQuery] " + s);
   }
}
