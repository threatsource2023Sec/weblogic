package com.oracle.weblogic.lifecycle.config.database;

import com.oracle.weblogic.lifecycle.LifecycleException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.configuration.hub.api.Change;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.configuration.hub.api.WriteableType;
import org.glassfish.hk2.configuration.hub.api.Change.ChangeCategory;
import org.jvnet.hk2.annotations.Service;

@Singleton
@Service(
   name = "DatabaseConfigPersistencePlugin"
)
public class DatabaseConfigPersistencePlugin implements LifecyclePersistencePlugin, PostConstruct {
   private Connection conn;
   private Properties tables = new Properties();
   private Map columnMappings = new HashMap();
   private Map types = new HashMap();
   private Logger logger = Logger.getLogger("LifeCycle");
   @Inject
   private Hub hub;
   private Timestamp timeStamp = null;

   public void postConstruct() {
      InputStream tableProperties = DatabaseConfigPersistencePlugin.class.getResourceAsStream("/database/table.properties");

      try {
         this.tables.load(tableProperties);
      } catch (Exception var30) {
         this.logger.log(Level.WARNING, var30.getMessage());
      } finally {
         try {
            tableProperties.close();
         } catch (Exception var26) {
            this.logger.log(Level.FINE, var26.getMessage());
         }

      }

      Iterator var2 = this.tables.stringPropertyNames().iterator();

      while(var2.hasNext()) {
         String tableName = (String)var2.next();
         String property = this.tables.getProperty(tableName);
         String[] values = property.split(";");
         this.types.put(values[0], tableName);
      }

      InputStream columnProperties = DatabaseConfigPersistencePlugin.class.getResourceAsStream("/database/columnDescriptor.properties");
      Properties props = new Properties();

      try {
         props.load(columnProperties);
      } catch (Exception var28) {
         this.logger.log(Level.WARNING, var28.getMessage());
      } finally {
         try {
            columnProperties.close();
         } catch (Exception var27) {
            this.logger.log(Level.FINE, var27.getMessage());
         }

      }

      Iterator var34 = props.stringPropertyNames().iterator();

      while(var34.hasNext()) {
         String tableName = (String)var34.next();
         String value = props.getProperty(tableName);
         this.columnMappings.put(tableName, value.split(","));
      }

   }

   private String getBeanAttributeName(String tableName, String columnName) {
      String[] attributeNames = (String[])this.columnMappings.get(tableName);
      String[] var4 = attributeNames;
      int var5 = attributeNames.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String attributeName = var4[var6];
         if (attributeName.equalsIgnoreCase(columnName)) {
            return attributeName;
         }
      }

      return columnName;
   }

   private void add(Change change) throws Exception {
      String key = change.getInstanceKey();
      Instance instanceValue = change.getInstanceValue();
      Map data = (Map)instanceValue.getBean();
      Type type = change.getChangeType();
      String typeName = type.getName();
      String tableName = this.getTableName(typeName);
      StringBuilder columnNames = new StringBuilder();
      StringBuilder columnValues = new StringBuilder();
      this.getDataFromInstanceValue(tableName, key, data);
      Iterator var10 = data.keySet().iterator();

      String columnName;
      while(var10.hasNext()) {
         columnName = (String)var10.next();
         if (!columnName.equalsIgnoreCase("properties")) {
            Object columnData = data.get(columnName);
            if (columnData instanceof String) {
               this.addColumnName(columnName, columnNames);
               this.addColumnValue(columnName, data, columnValues);
            }
         }
      }

      Statement stmt = this.conn.createStatement();
      columnName = "INSERT INTO " + tableName + "(" + columnNames + ") VALUES (" + columnValues + ")";
      this.logger.log(Level.FINE, columnName);
      stmt.executeUpdate(columnName);
      Properties props = this.getProperties(data);
      if (props != null) {
         String lastKey = this.getLastKey(key, tableName);
         String propsTable = this.getPropsTableName(tableName);
         String propStatement = " INSERT INTO " + propsTable + " (ID, NAME, VALUE) VALUES (?,?,?)";
         PreparedStatement preparedStatement = this.conn.prepareStatement(propStatement);
         preparedStatement.setString(1, lastKey);
         Iterator var17 = props.stringPropertyNames().iterator();

         while(var17.hasNext()) {
            String propKey = (String)var17.next();
            preparedStatement.setString(2, propKey);
            preparedStatement.setString(3, props.getProperty(propKey));
            preparedStatement.execute();
         }
      }

   }

   private String getPropsTableName(String tableName) {
      return tableName + "_PROPERTY";
   }

   private String getLastKey(String key, String tableName) throws LifecycleException {
      String[] keys = key.split(ConfigUtil.getSeparator());
      if (keys.length < 2) {
         throw new LifecycleException("Cannot add to properties table for " + tableName + ".  Data key does not have enough values : " + key);
      } else {
         return keys[keys.length - 1];
      }
   }

   private void addColumnName(String columnName, StringBuilder columnNames) {
      if (columnNames.length() > 0) {
         columnNames.append(",");
      }

      columnNames.append(columnName);
   }

   private void addColumnValue(String columnName, Map data, StringBuilder columnValues) {
      if (columnValues.length() > 0) {
         columnValues.append(",");
      }

      String value = (String)data.get(columnName);
      columnValues.append("'").append(value).append("'");
   }

   private void update(Change change) throws Exception {
   }

   private String getDeleteClause(Map data) {
      StringBuilder clause = new StringBuilder();
      boolean added = false;
      Iterator var4 = data.keySet().iterator();

      while(var4.hasNext()) {
         String columnName = (String)var4.next();
         if (!columnName.equalsIgnoreCase("properties") && !columnName.equalsIgnoreCase("createdOn") && !columnName.equalsIgnoreCase("updatedOn")) {
            if (added) {
               clause.append(" AND ");
            } else {
               added = true;
            }

            clause.append(columnName).append("=").append("'").append(data.get(columnName)).append("'");
         }
      }

      return clause.toString();
   }

   private void delete(Change change) throws Exception {
      String key = change.getInstanceKey();
      Type type = change.getChangeType();
      String typeName = type.getName();
      String tableName = this.getTableName(typeName);
      Instance instanceValue = change.getInstanceValue();
      Map data = (Map)instanceValue.getBean();
      Properties props = this.getProperties(data);
      if (props != null && props.size() > 0) {
         String lastKey = this.getLastKey(key, tableName);
         String propsTable = this.getPropsTableName(tableName);
         String propStatement = " DELETE " + propsTable + " WHERE ID=? AND NAME=?";
         PreparedStatement preparedStatement = this.conn.prepareStatement(propStatement);
         preparedStatement.setString(1, lastKey);
         Iterator var13 = props.stringPropertyNames().iterator();

         while(var13.hasNext()) {
            String propKey = (String)var13.next();
            preparedStatement.setString(2, propKey);
            preparedStatement.execute();
         }
      }

      this.delete(tableName, this.getDeleteClause(data));
   }

   private void delete(String tableName, String whereClause) throws Exception {
      String statement = " DELETE " + tableName + " WHERE " + whereClause;
      Statement stmt = this.conn.prepareStatement(statement);
      this.logger.log(Level.FINE, statement);
      stmt.executeUpdate(statement);
   }

   public void persist(List changes) throws Exception {
      this.getConnection();

      try {
         boolean timeStampUpdated = this.updateTimeStamp();
         if (!timeStampUpdated) {
            this.conn.rollback();
            ConfigUtil.refreshHK2Cache(this.hub);
            return;
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         this.conn.rollback();
         ConfigUtil.refreshHK2Cache(this.hub);
      }

      try {
         Iterator var6 = changes.iterator();

         while(true) {
            while(var6.hasNext()) {
               Change change = (Change)var6.next();
               if (change.getChangeCategory() != ChangeCategory.ADD_INSTANCE && change.getChangeCategory() != ChangeCategory.ADD_TYPE) {
                  if (change.getChangeCategory() == ChangeCategory.MODIFY_INSTANCE) {
                     this.update(change);
                  } else if (change.getChangeCategory() == ChangeCategory.REMOVE_INSTANCE) {
                     this.delete(change);
                  }
               } else {
                  this.add(change);
               }
            }

            this.conn.commit();
            break;
         }
      } catch (Exception var5) {
         this.conn.rollback();
         ConfigUtil.refreshHK2Cache(this.hub);
         throw var5;
      }

      this.getTimeStamp();
   }

   public void load() throws Exception {
      this.getConnection();
      WriteableBeanDatabase wbd = this.hub.getWriteableDatabaseCopy();
      Iterator var2 = this.tables.stringPropertyNames().iterator();

      while(var2.hasNext()) {
         String table = (String)var2.next();
         String type = this.getType(table);
         WriteableType wt = wbd.findOrAddWriteableType(type);
         String sqlQuery = "SELECT * from " + table;
         PreparedStatement stmt = this.conn.prepareStatement(sqlQuery);
         this.logger.log(Level.FINE, sqlQuery);
         ResultSet rs = stmt.executeQuery();
         ResultSetMetaData meta = rs.getMetaData();

         while(rs.next()) {
            HashMap data = new HashMap();
            int columnCount = rs.getMetaData().getColumnCount();

            for(int j = 0; j < columnCount; ++j) {
               String attributeName = this.getBeanAttributeName(table, meta.getColumnName(j + 1));
               data.put(attributeName, rs.getString(j + 1));
            }

            wt.addInstance(this.getInstanceValue(table, data), data);
         }
      }

      this.getTimeStamp();
      wbd.commit();
   }

   private void getTimeStamp() throws Exception {
      PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT LAST_UPDATE FROM LIFECYCLE_TIMESTAMP WHERE TYPE='DATA'");
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
         this.timeStamp = rs.getTimestamp(1);
      }

   }

   private int getStartCount(String s, char c) {
      int i;
      for(i = 0; i < s.length() && s.charAt(i) == c; ++i) {
      }

      return i;
   }

   private void getDataFromInstanceValue(String table, String instanceValue, Map map) {
      String type = this.tables.getProperty(table);
      String[] typeValues = type.split(";");
      String[] instanceValues = instanceValue.split(ConfigUtil.getSeparator());
      if (typeValues.length > 1) {
         String[] ids = typeValues[1].split(ConfigUtil.getSeparator());

         for(int i = 0; i < ids.length; ++i) {
            int count = this.getStartCount(ids[i], '$');
            if (count > 1) {
               map.put(ids[i].substring(count), instanceValues[instanceValues.length - count]);
            }
         }
      }

   }

   private String getInstanceValue(String table, Map data) {
      String type = this.tables.getProperty(table);
      String[] values = type.split(";");
      StringBuffer id = new StringBuffer();
      if (values.length > 1) {
         String[] ids = values[1].split(ConfigUtil.getSeparator());

         for(int i = 0; i < ids.length; ++i) {
            if (i > 0) {
               id.append(ConfigUtil.getSeparatorChar());
            }

            if (ids[i].startsWith("$")) {
               String key = ids[i].replaceAll("\\$", "");
               String dataValue = (String)data.get(key);
               if (dataValue != null) {
                  id.append(dataValue);
               }
            } else {
               id.append(ids[i]);
            }
         }
      }

      return id.toString();
   }

   private String getType(String table) {
      String val = (String)this.tables.get(table);
      String[] values = val.split(";");
      return values[0];
   }

   private String getTableName(String type) {
      return (String)this.types.get(type);
   }

   private Properties getProperties(Map data) {
      Object props = data.get("properties");
      return props instanceof Properties ? (Properties)props : null;
   }

   private boolean updateTimeStamp() throws Exception {
      String insertStatement = "INSERT INTO LIFECYCLE_TIMESTAMP VALUES('DATA', ?)";
      int numInserted = 0;

      try {
         PreparedStatement preparedStatement = this.conn.prepareStatement(insertStatement);
         preparedStatement.setTimestamp(1, this.timeStamp);
         numInserted = preparedStatement.executeUpdate();
      } catch (Exception var6) {
      }

      if (numInserted > 0) {
         return true;
      } else {
         String updateStatement = "UPDATE LIFECYCLE_TIMESTAMP SET LAST_UPDATE = CURRENT_TIMESTAMP WHERE LAST_UPDATE <= ? AND TYPE='DATA'";
         PreparedStatement preparedStatement = this.conn.prepareStatement(updateStatement);
         preparedStatement.setTimestamp(1, this.timeStamp);
         int numUpdated = preparedStatement.executeUpdate();
         return numUpdated == 1;
      }
   }

   private void getConnection() throws Exception {
      if (this.conn == null) {
         this.conn = DriverManager.getConnection((String)null, (String)null, (String)null);
         this.conn.setAutoCommit(false);
         this.conn.setTransactionIsolation(8);
      }

   }
}
