package weblogic.apache.org.apache.log.output.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.sql.DataSource;
import weblogic.apache.org.apache.log.LogEvent;

public class NormalizedJDBCTarget extends DefaultJDBCTarget {
   private HashMap m_categoryIDs = new HashMap();
   private HashMap m_priorityIDs = new HashMap();

   public NormalizedJDBCTarget(DataSource dataSource, String table, ColumnInfo[] columns) {
      super(dataSource, table, columns);
   }

   protected void specifyColumn(PreparedStatement statement, int index, LogEvent event) throws SQLException {
      ColumnInfo info = this.getColumn(index);
      int id = false;
      String tableName = null;
      int id;
      switch (info.getType()) {
         case 2:
            tableName = this.getTable() + "_" + "category" + "_SET";
            id = this.getID(tableName, this.m_categoryIDs, event.getCategory());
            statement.setInt(index + 1, id);
            break;
         case 8:
            tableName = this.getTable() + "_" + "priority" + "_SET";
            id = this.getID(tableName, this.m_priorityIDs, event.getPriority().getName());
            statement.setInt(index + 1, id);
            break;
         default:
            super.specifyColumn(statement, index, event);
      }

   }

   protected synchronized int getID(String tableName, HashMap idMap, String instance) throws SQLException {
      Integer id = (Integer)idMap.get(instance);
      if (null != id) {
         return id;
      } else {
         Statement statement = null;
         ResultSet resultSet = null;

         int var12;
         try {
            statement = this.getConnection().createStatement();
            String querySql = "SELECT ID FROM " + tableName + " WHERE NAME='" + instance + "'";
            resultSet = statement.executeQuery(querySql);
            int max;
            if (resultSet.next()) {
               Integer newID = new Integer(resultSet.getInt(1));
               idMap.put(instance, newID);
               max = newID;
               return max;
            }

            resultSet.close();
            String maxQuerySql = "SELECT MAX(ID) FROM " + tableName;
            resultSet = statement.executeQuery(maxQuerySql);
            max = 0;
            if (resultSet.next()) {
               max = resultSet.getInt(1);
            }

            resultSet.close();
            int newID = max + 1;
            String insertSQL = "INSERT INTO " + tableName + " (ID, NAME) VALUES ( " + newID + ", '" + instance + "')";
            statement.executeUpdate(insertSQL);
            idMap.put(instance, new Integer(newID));
            var12 = newID;
         } finally {
            if (null != resultSet) {
               try {
                  resultSet.close();
               } catch (Exception var24) {
               }
            }

            if (null != statement) {
               try {
                  statement.close();
               } catch (Exception var23) {
               }
            }

         }

         return var12;
      }
   }
}
