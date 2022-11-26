package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.LifecycleException;
import java.beans.PropertyChangeEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.w3c.dom.Document;
import weblogic.utils.XXEUtils;

public class DatabaseConfigHandler {
   private Logger logger = Logger.getLogger("Lifecycle");
   private String dataSource;
   private Connection readOnlyConnection = null;
   private PreparedStatement uuidPreparedStatement = null;
   private static final Map configDataMapping = new HashMap();
   private static final Class[] parameters = new Class[1];
   private static final Class[] noParams = new Class[0];
   private String uuid = null;
   private String username;
   private String url;
   private String password;
   static final String UPDATED_ON = "UpdatedOn";
   static final String CREATED_ON = "CreatedOn";
   private static final String UPDATED_ON_COL = "Updated_On";
   private static final String CREATED_ON_COL = "Created_On";
   private static final String NAME = "Name";
   private static final String VALUE = "Value";
   private static final String PROPNAME = "PropertyName";
   private static final String PROPVALUE = "PropertyValue";
   private static final String TYPE = "Type";
   private static final String ID = "Id";

   private static void initialize() {
      if (configDataMapping.size() <= 0) {
         configDataMapping.put("Runtime", new ConfigDatabaseMetadata("LIFECYCLE_RUNTIME", "runtimes/runtime", new String[]{"Name", "Type", "Hostname", "Port"}, new String[]{"Name", "Type", "Hostname", "Port"}, new String[]{"Name"}, "LIFECYCLE_RUNTIME_PROPERTY"));
         configDataMapping.put("Partition", new ConfigDatabaseMetadata("LIFECYCLE_PARTITION", "runtimes/runtime/partition", new String[]{"Id", "Name", "Runtime.Name"}, new String[]{"Id", "Name", "Runtime_Name"}, new String[]{"Id"}, "LIFECYCLE_PARTITION_PROPERTY"));
         configDataMapping.put("Environment", new ConfigDatabaseMetadata("LIFECYCLE_ENVIRONMENT", "environments/environment", new String[]{"Name"}, new String[]{"Name"}, new String[]{"Name"}));
         configDataMapping.put("PartitionRef", new ConfigDatabaseMetadata("LIFECYCLE_PARTITION_REF", "environments/environment/partition-ref", new String[]{"Environment.Name", "Id", "RuntimeRef"}, new String[]{"Environment_Name", "Id", "Runtime_Ref"}, new String[]{"Environment_Name", "Id"}));
         configDataMapping.put("Tenant", new ConfigDatabaseMetadata("LIFECYCLE_TENANT", "tenants/tenant", new String[]{"Id", "Name", "TopLevelDir"}, new String[]{"Id", "Name", "Top_Level_Dir"}, new String[]{"Id"}));
         configDataMapping.put("Service", new ConfigDatabaseMetadata("LIFECYCLE_SERVICE", "tenants/tenant/service", new String[]{"Id", "ServiceType", "Name", "EnvironmentRef", "Tenant.Id"}, new String[]{"Id", "Service_Type", "Name", "Environment_Ref", "Tenant_Id"}, new String[]{"Id"}));
         configDataMapping.put("PDB", new ConfigDatabaseMetadata("LIFECYCLE_PDB", "tenants/tenant/service/pdb", new String[]{"Id", "Name", "PdbStatus", "Service.Id"}, new String[]{"Id", "Name", "Status", "Service_Id"}, new String[]{"Id"}));
         configDataMapping.put("Association", new ConfigDatabaseMetadata("LIFECYCLE_ASSOCIATION", "environments/associations/association", new String[]{"Partition1.Id", "Partition2.Id", "PARENT.PARENT.Name"}, new String[]{"Partition1", "Partition2", "Environment_Name"}, new String[]{"Partition1"}));
         configDataMapping.put("Resource", new ConfigDatabaseMetadata((String)null, (String)null, new String[0], (String[])null));
         configDataMapping.put("TenantResource", new ConfigDatabaseMetadata("LIFECYCLE_TENANT_RESOURCE", "tenants/resources/resource", new String[]{"Name", "Type"}, new String[]{"Name", "Type"}, new String[]{"Name"}, "LIFECYCLE_TRESOURCE_PROPERTY"));
         configDataMapping.put("ServiceResource", new ConfigDatabaseMetadata("LIFECYCLE_SERVICE_RESOURCE", "tenants/tenant/service/resources/resource", new String[]{"Name", "Type", "PARENT.PARENT.Id", "PARENT.PARENT.PARENT.Id"}, new String[]{"Name", "Type", "Service_Id", "Tenant_Id"}, new String[]{"Name", "Service_Id"}, "LIFECYCLE_SRESOURCE_PROPERTY", "#ServiceResourceProperty"));
         configDataMapping.put("#ServiceResourceProperty", new ConfigDatabaseMetadata(new String[]{"PARENT.PARENT.Id", "Name", "PARENT.PARENT.PARENT.Id", "Name", "Value"}, new String[]{"Service_Id", "Resource_Name", "Tenant_Id", "PropertyName", "PropertyValue"}, new String[]{"Service_Id", "Resource_Name", "Tenant_Id"}));
         parameters[0] = XmlHk2ConfigurationBean.class;
      }
   }

   public static Map getConfigDataMapping() {
      return configDataMapping;
   }

   public DatabaseConfigHandler(String dataSource) {
      Objects.requireNonNull(dataSource);
      this.dataSource = dataSource;
      initialize();
   }

   public DatabaseConfigHandler(String url, String username, String password) {
      Objects.requireNonNull(url);
      Objects.requireNonNull(username);
      Objects.requireNonNull(password);
      this.username = username;
      this.url = url;
      this.password = password;
      initialize();
   }

   public DatabaseConfigHandler(LifecycleConfig lc) {
      Objects.requireNonNull(lc);
      this.url = this.getPropertyValue(lc, "url");
      this.username = this.getPropertyValue(lc, "username");
      this.password = this.getPropertyValue(lc, "password");
      initialize();
   }

   private String getPropertyValue(PropertyBagBean bag, String propName) {
      Objects.requireNonNull(bag);
      Objects.requireNonNull(propName);
      PropertyBean prop = bag.getProperty(propName);
      return prop == null ? null : prop.getValue();
   }

   public XMLStreamReader load() throws SQLException, NamingException, XPathExpressionException, LifecycleException, ParserConfigurationException, TransformerException, XMLStreamException {
      this.readOnlyConnection = this.getConnection(this.readOnlyConnection, true);
      DatabaseConfigReader configReader = new DatabaseConfigReader();
      Document doc = configReader.load(this.readOnlyConnection);
      this.uuid = this.getUUIDFromTable();
      DOMSource source = new DOMSource(doc);
      Transformer xformer = XXEUtils.createTransformerFactoryInstance().newTransformer();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      StreamResult out = new StreamResult(outputStream);
      xformer.transform(source, out);
      return XXEUtils.createXMLInputFactoryInstance().createXMLStreamReader(new ByteArrayInputStream(outputStream.toByteArray()));
   }

   private String getUpdateString(String key, Object value) {
      String s = value == null ? "null" : value.toString();
      return key + " = '" + s + "'";
   }

   private boolean isEqual(Object obj1, Object obj2) {
      if (obj1 == null && obj2 == null) {
         return true;
      } else {
         return obj1 == null ? false : obj1.equals(obj2);
      }
   }

   private String getClassName(String name) {
      Objects.requireNonNull(name);
      String[] names = name.split("\\.");
      return names[names.length - 1];
   }

   private ConfigDatabaseMetadata getResourceMetaData(Resource resource) {
      Objects.requireNonNull(resource);
      if (!(resource instanceof XmlHk2ConfigurationBean)) {
         return null;
      } else {
         XmlHk2ConfigurationBean hk2Bean = (XmlHk2ConfigurationBean)resource;
         XmlHk2ConfigurationBean p = hk2Bean._getParent();
         if (p != null) {
            p = p._getParent();
         }

         if (p != null) {
            if (p instanceof Tenants) {
               return (ConfigDatabaseMetadata)configDataMapping.get("TenantResource");
            }

            if (p instanceof Service) {
               return (ConfigDatabaseMetadata)configDataMapping.get("ServiceResource");
            }
         }

         return null;
      }
   }

   private String getUUIDFromTable() throws SQLException, NamingException {
      ResultSet rs = this.getUUIDPreparedStatement().executeQuery();
      Throwable var2 = null;

      String var3;
      try {
         if (rs.next()) {
            var3 = rs.getString(1);
            return var3;
         }

         var3 = null;
      } catch (Throwable var13) {
         var2 = var13;
         throw var13;
      } finally {
         if (rs != null) {
            if (var2 != null) {
               try {
                  rs.close();
               } catch (Throwable var12) {
                  var2.addSuppressed(var12);
               }
            } else {
               rs.close();
            }
         }

      }

      return var3;
   }

   private PreparedStatement getUUIDPreparedStatement() throws SQLException, NamingException {
      if (this.uuidPreparedStatement == null) {
         this.readOnlyConnection = this.getConnection(this.readOnlyConnection, true);
         this.uuidPreparedStatement = this.readOnlyConnection.prepareStatement("SELECT UUID FROM LIFECYCLE_VERSION WHERE TYPE='DATA'");
      }

      return this.uuidPreparedStatement;
   }

   private String getUUIDFromTable(Connection connection) throws SQLException {
      Objects.requireNonNull(connection);
      PreparedStatement s = connection.prepareStatement("SELECT UUID FROM LIFECYCLE_VERSION WHERE TYPE='DATA'");
      Throwable var3 = null;

      Object var6;
      try {
         ResultSet rs = s.executeQuery();
         Throwable var5 = null;

         try {
            if (!rs.next()) {
               var6 = null;
               return (String)var6;
            }

            var6 = rs.getString(1);
         } catch (Throwable var32) {
            var6 = var32;
            var5 = var32;
            throw var32;
         } finally {
            if (rs != null) {
               if (var5 != null) {
                  try {
                     rs.close();
                  } catch (Throwable var31) {
                     var5.addSuppressed(var31);
                  }
               } else {
                  rs.close();
               }
            }

         }
      } catch (Throwable var34) {
         var3 = var34;
         throw var34;
      } finally {
         if (s != null) {
            if (var3 != null) {
               try {
                  s.close();
               } catch (Throwable var30) {
                  var3.addSuppressed(var30);
               }
            } else {
               s.close();
            }
         }

      }

      return (String)var6;
   }

   public boolean isDataUptodate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, NamingException {
      String s = this.getUUIDFromTable();
      if (s == null) {
         return true;
      } else {
         return this.uuid == null ? false : s.equals(this.uuid);
      }
   }

   private void setValues(PreparedStatement ps, XmlHk2ConfigurationBean bean, String[] columnNames) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
      Objects.requireNonNull(ps);
      Objects.requireNonNull(bean);
      Objects.requireNonNull(columnNames);

      for(int i = 0; i < columnNames.length; ++i) {
         String attrName = columnNames[i];
         Object value;
         if (attrName.indexOf(46) > 0) {
            String[] names = attrName.split("\\.");
            if (names[0].equals("PARENT")) {
               value = this.getParentValue(bean, names);
            } else {
               Object parent = this.getValue(bean, names[0]);
               value = this.getValue(parent, names[1]);
            }
         } else {
            value = this.getValue(bean, attrName);
         }

         if (value != null) {
            if (LifecycleConfigUtil.isDebugEnabled()) {
               LifecycleConfigUtil.debug(i + 1 + " : " + value.toString());
            }

            ps.setString(i + 1, value.toString());
         } else {
            ps.setString(i + 1, (String)null);
         }
      }

   }

   private Object getParentValue(XmlHk2ConfigurationBean bean, String[] names) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Objects.requireNonNull(bean);
      Objects.requireNonNull(names);
      if (!names[0].equals("PARENT")) {
         return null;
      } else {
         XmlHk2ConfigurationBean parent = bean;

         for(int i = 0; i < names.length - 1; ++i) {
            parent = parent._getParent();
         }

         return this.getValue(parent, names[names.length - 1]);
      }
   }

   private Object getValue(Object bean, String getterMethod) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Objects.requireNonNull(bean);
      Objects.requireNonNull(getterMethod);

      try {
         Method method = bean.getClass().getMethod("get" + getterMethod, noParams);
         return method.invoke(bean, (Object[])noParams);
      } catch (NoSuchMethodException var4) {
         this.logger.log(Level.INFO, bean.toString());
         throw var4;
      }
   }

   private StringBuilder getWhereClause(String[] keys) {
      Objects.requireNonNull(keys);
      StringBuilder buf = new StringBuilder(" WHERE ");

      for(int i = 0; i < keys.length; ++i) {
         if (i > 0) {
            buf.append(" AND ");
         }

         String key = keys[i];
         key = key.replaceAll("\\.", "_");
         buf.append(key);
         buf.append(" = ? ");
      }

      return buf;
   }

   private Connection getConnection(Connection conn, boolean readOnly) throws SQLException, NamingException {
      if (conn == null || conn.isClosed()) {
         if (this.dataSource != null) {
            if (LifecycleConfigUtil.isDebugEnabled()) {
               LifecycleConfigUtil.debug("Getting connection from datasource " + this.dataSource);
            }

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup(this.dataSource);
            conn = ds.getConnection();
         } else if (this.url != null) {
            if (LifecycleConfigUtil.isDebugEnabled()) {
               LifecycleConfigUtil.debug("Getting connection for URL " + this.url);
            }

            conn = DriverManager.getConnection(this.url, this.username, this.password);
         }

         if (conn != null) {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(2);
            DatabaseMetaData metaData = conn.getMetaData();
            if (metaData.supportsResultSetHoldability(2)) {
               conn.setHoldability(2);
            }

            if (readOnly) {
               conn.setReadOnly(true);
            }
         }
      }

      return conn;
   }

   private Connection getConnection(Connection conn) throws SQLException, NamingException {
      return this.getConnection(conn, false);
   }

   public void handleConfigChange(List changes) throws Exception {
      Objects.requireNonNull(changes);
      ConfigChangeHandler configChangeHandler = new ConfigChangeHandler(changes);

      try {
         Iterator var3 = changes.iterator();

         while(var3.hasNext()) {
            PropertyChangeEvent change = (PropertyChangeEvent)var3.next();
            configChangeHandler.translatePropertyChangeEvent(change);
         }

         configChangeHandler.commit();
      } catch (RuntimeException var6) {
         try {
            configChangeHandler.rollback();
         } catch (Exception var5) {
            this.logger.log(Level.WARNING, var5.getMessage());
         }

         throw var6;
      }
   }

   private static enum CHANGE_TYPE {
      ADD,
      REMOVE,
      CHANGE;
   }

   private class ConfigChangeHandler {
      private List events;
      private Connection connection;
      boolean updateInitialized = false;

      ConfigChangeHandler(List events) {
         this.events = events;
      }

      private boolean initializeUpdate() throws SQLException, NamingException, LifecycleException {
         if (this.updateInitialized) {
            return false;
         } else {
            this.connection = DatabaseConfigHandler.this.getConnection(this.connection);
            boolean updated = this.updateUUID();
            if (!updated) {
               try {
                  this.rollback();
               } catch (Exception var3) {
                  DatabaseConfigHandler.this.logger.log(Level.WARNING, var3.getMessage());
               }

               throw new LifecycleException("Time stamp out of synch. Data cannot be updated.");
            } else {
               this.updateInitialized = true;
               return true;
            }
         }
      }

      void commit() throws SQLException {
         if (this.connection != null) {
            try {
               DatabaseConfigHandler.this.uuid = DatabaseConfigHandler.this.getUUIDFromTable(this.connection);
               this.connection.commit();
            } finally {
               this.connection.close();
            }
         }

      }

      void rollback() throws SQLException {
         if (this.connection != null) {
            try {
               this.connection.rollback();
            } finally {
               this.connection.close();
            }
         }

      }

      public void translatePropertyChangeEvent(PropertyChangeEvent event) {
         Object oldV = event.getOldValue();
         Object newV = event.getNewValue();
         if (oldV != null || newV != null) {
            CHANGE_TYPE type;
            Class changedType;
            Object changedInstance;
            if (oldV == null) {
               type = DatabaseConfigHandler.CHANGE_TYPE.ADD;
               changedType = newV.getClass();
               changedInstance = newV;
            } else if (newV == null) {
               type = DatabaseConfigHandler.CHANGE_TYPE.REMOVE;
               changedType = oldV.getClass();
               changedInstance = oldV;
            } else {
               type = DatabaseConfigHandler.CHANGE_TYPE.CHANGE;
               changedType = newV.getClass();
               changedInstance = newV;
            }

            this.changed(type, changedType, changedInstance);
         }
      }

      public void changed(CHANGE_TYPE type, Class changedType, Object changedInstance) {
         String changedClass = DatabaseConfigHandler.this.getClassName(changedType.getName());
         ConfigDatabaseMetadata metaData = (ConfigDatabaseMetadata)DatabaseConfigHandler.configDataMapping.get(changedClass);
         if (LifecycleConfigUtil.isDebugEnabled()) {
            LifecycleConfigUtil.debug(type.toString() + " : " + changedType.getName() + " : " + changedInstance);
         }

         if (metaData != null) {
            if (changedInstance instanceof Resource) {
               metaData = DatabaseConfigHandler.this.getResourceMetaData((Resource)changedInstance);
               if (metaData == null) {
                  throw new RuntimeException("Incorrect type : " + changedInstance);
               }
            }

            try {
               switch (type) {
                  case ADD:
                     this.add((XmlHk2ConfigurationBean)changedInstance, metaData);
                     break;
                  case REMOVE:
                     this.remove((XmlHk2ConfigurationBean)changedInstance, metaData);
                     break;
                  case CHANGE:
                     if (changedInstance instanceof PDB) {
                        this.addPDB((PDB)changedInstance, metaData);
                     } else {
                        this.update((XmlHk2ConfigurationBean)changedInstance, metaData);
                     }
                     break;
                  default:
                     throw new RuntimeException("Unknown type: " + type);
               }

            } catch (Exception var7) {
               throw new RuntimeException(var7);
            }
         }
      }

      private String[] getPropertyKeys(ConfigDatabaseMetadata metadata) {
         String[] keys = null;
         if (metadata.getPropertyDescriptor() != null) {
            ConfigDatabaseMetadata propertyMetaData = (ConfigDatabaseMetadata)DatabaseConfigHandler.configDataMapping.get(metadata.getPropertyDescriptor());
            keys = propertyMetaData.getKeys();
         } else if (metadata.getPropertyTable() != null) {
            keys = metadata.getKeys();
         }

         return keys;
      }

      private List getProperties(ConfigDatabaseMetadata metadata, XmlHk2ConfigurationBean bean) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, NamingException, LifecycleException {
         Objects.requireNonNull(metadata);
         Objects.requireNonNull(bean);
         if (metadata.getPropertyDescriptor() == null && metadata.getPropertyTable() == null) {
            return null;
         } else {
            Object properties = DatabaseConfigHandler.this.getValue(bean, "Property");
            if (properties != null && properties instanceof List) {
               return ((List)properties).size() == 0 ? null : (List)properties;
            } else {
               return null;
            }
         }
      }

      private void add(XmlHk2ConfigurationBean bean, ConfigDatabaseMetadata metadata) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, NamingException, LifecycleException {
         Objects.requireNonNull(metadata);
         Objects.requireNonNull(bean);
         StringBuilder statement = (new StringBuilder("INSERT INTO ")).append(metadata.getTableName()).append("(").append(metadata.getColumnsList()).append(") VALUES(");
         int numCols = metadata.getColumns().length;

         for(int i = 0; i < numCols; ++i) {
            if (i > 0) {
               statement.append(",");
            }

            statement.append("?");
         }

         statement.append(")");
         this.initializeUpdate();
         if (LifecycleConfigUtil.isDebugEnabled()) {
            LifecycleConfigUtil.debug(statement.toString());
         }

         PreparedStatement ps = this.connection.prepareStatement(statement.toString());
         Throwable var6 = null;

         try {
            DatabaseConfigHandler.this.setValues(ps, bean, metadata.getAttributes());
            this.executeUpdate(ps);
         } catch (Throwable var34) {
            var6 = var34;
            throw var34;
         } finally {
            if (ps != null) {
               if (var6 != null) {
                  try {
                     ps.close();
                  } catch (Throwable var32) {
                     var6.addSuppressed(var32);
                  }
               } else {
                  ps.close();
               }
            }

         }

         List properties = this.getProperties(metadata, bean);
         if (properties != null) {
            ConfigDatabaseMetadata propMetadata = metadata;
            if (LifecycleConfigUtil.isDebugEnabled()) {
               LifecycleConfigUtil.debug("propertyDescriptor = " + metadata.getPropertyDescriptor());
            }

            if (metadata.getPropertyDescriptor() != null) {
               propMetadata = (ConfigDatabaseMetadata)DatabaseConfigHandler.configDataMapping.get(metadata.getPropertyDescriptor());
            }

            String[] keys = propMetadata.getKeys();
            if (keys != null) {
               String propertyTableName = metadata.getPropertyTable();
               StringBuilder propInsertStatement = (new StringBuilder("INSERT INTO ")).append(propertyTableName).append("(");
               StringBuilder propValues = new StringBuilder(" VALUES(");

               for(int ix = 0; ix < keys.length; ++ix) {
                  propInsertStatement.append(keys[ix]).append(",");
                  propValues.append("?,");
               }

               propInsertStatement.append("PropertyName").append(",").append("PropertyValue").append(")").append(propValues).append("?,?)");
               if (LifecycleConfigUtil.isDebugEnabled()) {
                  LifecycleConfigUtil.debug(propInsertStatement.toString());
               }

               PreparedStatement propStatement = this.connection.prepareStatement(propInsertStatement.toString());
               Throwable var12 = null;

               try {
                  Iterator var13 = properties.iterator();

                  while(var13.hasNext()) {
                     PropertyBean prop = (PropertyBean)var13.next();
                     if (LifecycleConfigUtil.isDebugEnabled()) {
                        this.print("keys", keys);
                        this.print("attrForCols:", propMetadata.getAttributesForColumns(keys));
                     }

                     DatabaseConfigHandler.this.setValues(propStatement, bean, propMetadata.getAttributesForColumns(keys));
                     propStatement.setString(keys.length + 1, prop.getName());
                     propStatement.setString(keys.length + 2, prop.getValue());
                     this.executeUpdate(propStatement);
                  }
               } catch (Throwable var36) {
                  var12 = var36;
                  throw var36;
               } finally {
                  if (propStatement != null) {
                     if (var12 != null) {
                        try {
                           propStatement.close();
                        } catch (Throwable var33) {
                           var12.addSuppressed(var33);
                        }
                     } else {
                        propStatement.close();
                     }
                  }

               }

            }
         }
      }

      private void print(String key, String[] s) {
         LifecycleConfigUtil.debug("STRINGS FOR " + key);

         for(int i = 0; i < s.length; ++i) {
            LifecycleConfigUtil.debug(s[i] + " ");
         }

         LifecycleConfigUtil.debug("STRINGS FOR " + key + " DONE..");
      }

      private void addPDB(PDB bean, ConfigDatabaseMetadata metadata) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, NamingException, LifecycleException {
         if (!this.exists((XmlHk2ConfigurationBean)bean, metadata)) {
            this.add((XmlHk2ConfigurationBean)bean, metadata);
         }

      }

      private void remove(XmlHk2ConfigurationBean bean, ConfigDatabaseMetadata metadata) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, NamingException, LifecycleException {
         Objects.requireNonNull(bean);
         Objects.requireNonNull(metadata);
         if (LifecycleConfigUtil.isDebugEnabled()) {
            LifecycleConfigUtil.debug("Deleting properties for " + metadata.getTableName() + " ; " + metadata.getPropertyTable());
         }

         ConfigDatabaseMetadata propMetadata = metadata;
         if (metadata.getPropertyDescriptor() != null) {
            propMetadata = (ConfigDatabaseMetadata)DatabaseConfigHandler.configDataMapping.get(metadata.getPropertyDescriptor());
         }

         String[] keys = propMetadata.getKeys();
         if (keys != null) {
            String propertyTable = metadata.getPropertyTable();
            if (propertyTable != null) {
               StringBuilder propSQL = (new StringBuilder("  DELETE FROM ")).append(propertyTable).append(DatabaseConfigHandler.this.getWhereClause(keys));
               if (LifecycleConfigUtil.isDebugEnabled()) {
                  LifecycleConfigUtil.debug(this.getString(keys));
                  LifecycleConfigUtil.debug(propSQL.toString());
               }

               this.initializeUpdate();
               PreparedStatement propStatement = this.connection.prepareStatement(propSQL.toString());
               Throwable var8 = null;

               try {
                  DatabaseConfigHandler.this.setValues(propStatement, bean, propMetadata.getAttributesForColumns(keys));
                  this.executeUpdate(propStatement);
               } catch (Throwable var31) {
                  var8 = var31;
                  throw var31;
               } finally {
                  if (propStatement != null) {
                     if (var8 != null) {
                        try {
                           propStatement.close();
                        } catch (Throwable var30) {
                           var8.addSuppressed(var30);
                        }
                     } else {
                        propStatement.close();
                     }
                  }

               }
            }
         }

         StringBuilder statement = (new StringBuilder("DELETE FROM ")).append(metadata.getTableName()).append(DatabaseConfigHandler.this.getWhereClause(metadata.getKeys()));
         this.initializeUpdate();
         PreparedStatement ps = this.connection.prepareStatement(statement.toString());
         Throwable var37 = null;

         try {
            if (LifecycleConfigUtil.isDebugEnabled()) {
               LifecycleConfigUtil.debug(statement.toString());
               LifecycleConfigUtil.debug(this.getString(metadata.getAttributesForKeys()));
            }

            DatabaseConfigHandler.this.setValues(ps, bean, metadata.getAttributesForKeys());
            this.executeUpdate(ps);
         } catch (Throwable var33) {
            var37 = var33;
            throw var33;
         } finally {
            if (ps != null) {
               if (var37 != null) {
                  try {
                     ps.close();
                  } catch (Throwable var29) {
                     var37.addSuppressed(var29);
                  }
               } else {
                  ps.close();
               }
            }

         }

      }

      private Map update(XmlHk2ConfigurationBean bean, ConfigDatabaseMetadata metadata) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, NamingException, LifecycleException {
         Objects.requireNonNull(bean);
         Objects.requireNonNull(metadata);
         StringBuilder statement = (new StringBuilder("SELECT * FROM ")).append(metadata.getTableName()).append(DatabaseConfigHandler.this.getWhereClause(metadata.getKeys()));
         this.connection = DatabaseConfigHandler.this.getConnection(this.connection);
         Map updateProps = new HashMap();
         PreparedStatement psx = this.connection.prepareStatement(statement.toString());
         Throwable var6 = null;

         Throwable var8;
         try {
            DatabaseConfigHandler.this.setValues(psx, bean, metadata.getAttributesForKeys());
            ResultSet rs = psx.executeQuery();
            var8 = null;

            try {
               ResultSetMetaData rsmd = rs.getMetaData();
               if (rs.next()) {
                  for(int i = 0; i < rsmd.getColumnCount(); ++i) {
                     String colName = rsmd.getColumnName(i + 1);
                     String attrName = metadata.getAttributeForColumn(colName);
                     if (attrName != null && attrName.indexOf(46) < 0 && !metadata.isKey(attrName) && !metadata.ignoreForUpdate(attrName)) {
                        Object obj = DatabaseConfigHandler.this.getValue(bean, attrName);
                        if (!DatabaseConfigHandler.this.isEqual(rs.getString(i + 1), obj)) {
                           updateProps.put(colName, obj);
                        }
                     }
                  }
               }
            } catch (Throwable var58) {
               var8 = var58;
               throw var58;
            } finally {
               if (rs != null) {
                  if (var8 != null) {
                     try {
                        rs.close();
                     } catch (Throwable var55) {
                        var8.addSuppressed(var55);
                     }
                  } else {
                     rs.close();
                  }
               }

            }
         } catch (Throwable var60) {
            var6 = var60;
            throw var60;
         } finally {
            if (psx != null) {
               if (var6 != null) {
                  try {
                     psx.close();
                  } catch (Throwable var54) {
                     var6.addSuppressed(var54);
                  }
               } else {
                  psx.close();
               }
            }

         }

         if (updateProps.size() > 0) {
            StringBuilder updateStatement = (new StringBuilder("UPDATE ")).append(metadata.getTableName()).append(" SET ");
            boolean added = false;

            for(Iterator var64 = updateProps.keySet().iterator(); var64.hasNext(); added = true) {
               String key = (String)var64.next();
               if (added) {
                  updateStatement.append(",");
               }

               updateStatement.append(DatabaseConfigHandler.this.getUpdateString(key, updateProps.get(key)));
            }

            updateStatement.append(DatabaseConfigHandler.this.getWhereClause(metadata.getKeys()));
            this.initializeUpdate();
            PreparedStatement ps = this.connection.prepareStatement(updateStatement.toString());
            var8 = null;

            try {
               DatabaseConfigHandler.this.setValues(ps, bean, metadata.getAttributesForKeys());
               this.executeUpdate(ps);
            } catch (Throwable var56) {
               var8 = var56;
               throw var56;
            } finally {
               if (ps != null) {
                  if (var8 != null) {
                     try {
                        ps.close();
                     } catch (Throwable var53) {
                        var8.addSuppressed(var53);
                     }
                  } else {
                     ps.close();
                  }
               }

            }
         }

         return updateProps;
      }

      private boolean exists(XmlHk2ConfigurationBean bean, ConfigDatabaseMetadata metadata) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, NamingException {
         Objects.requireNonNull(bean);
         Objects.requireNonNull(metadata);
         StringBuilder statement = (new StringBuilder("SELECT * FROM ")).append(metadata.getTableName()).append(DatabaseConfigHandler.this.getWhereClause(metadata.getKeys()));
         this.connection = DatabaseConfigHandler.this.getConnection(this.connection);
         PreparedStatement ps = this.connection.prepareStatement(statement.toString());
         Throwable var5 = null;

         Throwable var8;
         try {
            DatabaseConfigHandler.this.setValues(ps, bean, metadata.getAttributesForKeys());
            ResultSet rs = ps.executeQuery();
            Throwable var7 = null;

            try {
               var8 = rs.next();
            } catch (Throwable var31) {
               var8 = var31;
               var7 = var31;
               throw var31;
            } finally {
               if (rs != null) {
                  if (var7 != null) {
                     try {
                        rs.close();
                     } catch (Throwable var30) {
                        var7.addSuppressed(var30);
                     }
                  } else {
                     rs.close();
                  }
               }

            }
         } catch (Throwable var33) {
            var5 = var33;
            throw var33;
         } finally {
            if (ps != null) {
               if (var5 != null) {
                  try {
                     ps.close();
                  } catch (Throwable var29) {
                     var5.addSuppressed(var29);
                  }
               } else {
                  ps.close();
               }
            }

         }

         return (boolean)var8;
      }

      private int executeUpdate(PreparedStatement ps) throws SQLException {
         return ps.executeUpdate();
      }

      private String addUUID() throws SQLException {
         String newUUID = UUID.randomUUID().toString();
         String insertStatement = "INSERT INTO LIFECYCLE_VERSION VALUES('DATA', ?, CURRENT_TIMESTAMP)";
         if (LifecycleConfigUtil.isDebugEnabled()) {
            LifecycleConfigUtil.debug("Adding UUID : " + insertStatement + " uuid = " + newUUID);
         }

         PreparedStatement preparedStatement = this.connection.prepareStatement(insertStatement);
         Throwable var4 = null;

         try {
            preparedStatement.setString(1, newUUID);
            preparedStatement.executeUpdate();
         } catch (Throwable var13) {
            var4 = var13;
            throw var13;
         } finally {
            if (preparedStatement != null) {
               if (var4 != null) {
                  try {
                     preparedStatement.close();
                  } catch (Throwable var12) {
                     var4.addSuppressed(var12);
                  }
               } else {
                  preparedStatement.close();
               }
            }

         }

         return newUUID;
      }

      private boolean updateUUID() throws SQLException, NamingException {
         String newUUID;
         if (DatabaseConfigHandler.this.uuid == null) {
            newUUID = DatabaseConfigHandler.this.getUUIDFromTable(this.connection);
            if (newUUID != null) {
               return false;
            } else {
               DatabaseConfigHandler.this.uuid = this.addUUID();
               if (LifecycleConfigUtil.isDebugEnabled()) {
                  LifecycleConfigUtil.debug("added uuid : " + DatabaseConfigHandler.this.uuid);
               }

               return true;
            }
         } else {
            newUUID = UUID.randomUUID().toString();
            String updateStatement = "UPDATE LIFECYCLE_VERSION SET UUID = ?, UPDATED_ON = CURRENT_TIMESTAMP  WHERE UUID = ? AND TYPE='DATA'";
            PreparedStatement preparedStatement = this.connection.prepareStatement(updateStatement);
            Throwable var4 = null;

            boolean var6;
            try {
               preparedStatement.setString(1, newUUID);
               preparedStatement.setString(2, DatabaseConfigHandler.this.uuid);
               if (LifecycleConfigUtil.isDebugEnabled()) {
                  LifecycleConfigUtil.debug(preparedStatement.toString());
               }

               int numUpdated = preparedStatement.executeUpdate();
               var6 = numUpdated == 1;
            } catch (Throwable var15) {
               var4 = var15;
               throw var15;
            } finally {
               if (preparedStatement != null) {
                  if (var4 != null) {
                     try {
                        preparedStatement.close();
                     } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                     }
                  } else {
                     preparedStatement.close();
                  }
               }

            }

            return var6;
         }
      }

      private String getString(String[] strings) {
         StringBuilder sb = new StringBuilder();
         String[] var3 = strings;
         int var4 = strings.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            sb.append(s);
         }

         return sb.toString();
      }
   }
}
