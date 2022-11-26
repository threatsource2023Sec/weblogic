package com.oracle.batch.connector.impl;

import com.ibm.jbatch.spi.DatabaseConfigurationBean;
import java.security.AccessController;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import weblogic.application.naming.DataSourceBinder;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;

public class WLSDatabaseConfigurationBean extends DatabaseConfigurationBean {
   public static final String _APP_NAME = "_com_oracle_weblogic_batch_connector";
   public static final String _MOD_NAME = "_com_oracle_weblogic_batch_connector_impl_";
   public static final String _COMP_NAME = "_com_oracle_weblogic_batch_connector_impl_WLSDatabaseConfigurationBean";
   public static String _batchDataSourceJndiName = "";
   public static final String _derbyDataSourceJndiName = "_com_oracle_batch_internal__derby_batch_DataSource";
   private static final DebugLogger _debugLogger = DebugLogger.getDebugLogger("BatchConnector");
   private static final WLSDatabaseConfigurationBean _instance = new WLSDatabaseConfigurationBean();
   private String schemaName;
   private DataSource derbyDataSource;
   private DataSource batchDataSource;
   private final AtomicBoolean initialized = new AtomicBoolean(false);
   private final AtomicBoolean initializedDerby = new AtomicBoolean(false);
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private WLSDatabaseConfigurationBean() {
   }

   public static WLSDatabaseConfigurationBean getInstance() {
      return _instance;
   }

   public final String getBatchDataSourceLookupName() {
      return _batchDataSourceJndiName;
   }

   public DataSource getDataSource() {
      this.init();
      return this.batchDataSource;
   }

   public String getJndiName() {
      throw new UnsupportedOperationException("DatabaseConfigurationBean.getJndiName() no longer supported");
   }

   public String getSchema() {
      this.init();
      return this.schemaName;
   }

   private DataSource getDerbyDataSource() {
      this.checkAndDefaultDerbyDataSource();
      return this.derbyDataSource;
   }

   private void init() {
      if (!this.initialized.get()) {
         synchronized(this.initialized) {
            emit("Entered WLSDatabaseConfigurationBean::init() ");

            try {
               this.schemaName = BatchConfigBeanHelper.getDomainMBean().getBatchConfig().getSchemaName();
               _batchDataSourceJndiName = BatchConfigBeanHelper.getDomainMBean().getBatchJobsDataSourceJndiName();
               if (_batchDataSourceJndiName != null && _batchDataSourceJndiName.trim().length() != 0) {
                  try {
                     this.batchDataSource = (DataSource)(new InitialContext()).lookup(_batchDataSourceJndiName);
                  } catch (NamingException var4) {
                     throw new RuntimeException("Batch failed while looking up DataSource", var4);
                  }
               } else {
                  this.batchDataSource = this.getDerbyDataSource();
                  _batchDataSourceJndiName = "_com_oracle_batch_internal__derby_batch_DataSource";
               }

               boolean isderby = this.isDerby();
               if (this.isNullOrEmpty(this.schemaName)) {
                  emit("No schema set in BatchConfig; Checking if need to default to Derby database.");
                  if (isderby) {
                     emit("Defaulting to Derby database; Since schema name is empty; Checking user name.");
                     this.schemaName = this.getUserNameFromMetadataForDerby();
                     if (this.isNullOrEmpty(this.schemaName)) {
                        this.schemaName = "APP";
                     }

                     if (this.schemaName != null) {
                        this.schemaName = this.schemaName.toUpperCase();
                     }

                     emit("Setting Schema name to: " + this.schemaName);
                  }
               } else if (isderby && this.schemaName != null) {
                  this.schemaName = this.schemaName.toUpperCase();
               }

               this.initialized.set(true);
               emit("init() Final configuration: batchDataSource: " + this.batchDataSource + "; schema = " + this.schemaName);
            } catch (Throwable var5) {
               emit("init() Got exception ", var5);
               throw new RuntimeException("init() Got exception: " + var5, var5);
            }
         }
      }

   }

   private boolean isNullOrEmpty(String str) {
      return str == null || str.trim().length() == 0;
   }

   private boolean isDerby() {
      try {
         try {
            Connection conn = this.batchDataSource.getConnection();
            Throwable var2 = null;

            boolean var3;
            try {
               emit("Got connection; Checking if it is Derby");
               var3 = conn.getMetaData().getDatabaseProductName().toLowerCase().indexOf("derby") > 0;
            } catch (Throwable var14) {
               var2 = var14;
               throw var14;
            } finally {
               if (conn != null) {
                  if (var2 != null) {
                     try {
                        conn.close();
                     } catch (Throwable var13) {
                        var2.addSuppressed(var13);
                     }
                  } else {
                     conn.close();
                  }
               }

            }

            return var3;
         } catch (SQLException var16) {
            emit("While attempting to determine if isDerby(): Got exception", var16);
            return false;
         }
      } catch (Exception var17) {
         emit("While attempting to determine if isDerby(): Got exception", var17);
         throw new RuntimeException("WLSDatabaseConfigurationBean:: While attempting to determine if isDerby(): Got exception", var17);
      }
   }

   private String getUserNameFromMetadataForDerby() {
      try {
         Connection conn = this.batchDataSource.getConnection();
         Throwable var2 = null;

         String var4;
         try {
            DatabaseMetaData dbmd = conn.getMetaData();
            var4 = dbmd.getUserName();
         } catch (Throwable var14) {
            var2 = var14;
            throw var14;
         } finally {
            if (conn != null) {
               if (var2 != null) {
                  try {
                     conn.close();
                  } catch (Throwable var13) {
                     var2.addSuppressed(var13);
                  }
               } else {
                  conn.close();
               }
            }

         }

         return var4;
      } catch (SQLException var16) {
         emit("getDefaultSchemaNameForDerby: Got exception", var16);
         return null;
      }
   }

   private static void emit(String str) {
      if (_debugLogger.isDebugEnabled()) {
         _debugLogger.debug(" [** WLSDatabaseConfigurationBean **]: " + str);
      }

   }

   private static void emit(String str, Throwable th) {
      if (_debugLogger.isDebugEnabled()) {
         _debugLogger.debug(" [** WLSDatabaseConfigurationBean **]: " + str, th);
      }

   }

   private void checkAndDefaultDerbyDataSource() {
      if (!this.initializedDerby.get()) {
         synchronized(this.initializedDerby) {
            if (!this.initializedDerby.get()) {
               SecurityManager.pushSubject(kernelId, kernelId);

               try {
                  emit("Attempting to create default DerbyDataSource");
                  DataSourceBean derbyDataSourceBean = this.createGenericDataSourceBean();
                  this.derbyDataSource = DataSourceBinder.createDataSource(derbyDataSourceBean, "_com_oracle_weblogic_batch_connector", "_com_oracle_weblogic_batch_connector_impl_", "_com_oracle_weblogic_batch_connector_impl_WLSDatabaseConfigurationBean");
                  this.initializedDerby.set(true);
                  emit("Successfully created default DerbyDataSource");
               } catch (Throwable var8) {
                  emit("createDefaultDerbyDataSource() Got exception", var8);
                  throw new RuntimeException("** WLSDatabaseConfigurationBean::createDefaultDerbyDataSource: Got exception", var8);
               } finally {
                  SecurityManager.popSubject(kernelId);
               }
            }
         }
      }

   }

   private DataSourceBean createGenericDataSourceBean() throws Exception {
      Descriptor d1 = (new DescriptorManager()).createDescriptorRoot(DataSourceBean.class);
      DataSourceBean bean = (DataSourceBean)d1.getRootBean();
      bean.setName("_com_oracle_batch_internal__derby_batch_DataSource");
      bean.setClassName("org.apache.derby.jdbc.ClientDataSource");
      bean.setPortNumber(1527);
      bean.setServerName("localhost");
      bean.setDatabaseName("batchdb; create=true");
      bean.setInitialPoolSize(0);
      bean.setMaxPoolSize(15);
      bean.setMinPoolSize(0);
      bean.setMaxStatements(0);
      bean.setTransactional(false);
      bean.setMaxIdleTime(300);
      bean.setUser("APP");
      bean.setPassword("derby");
      JavaEEPropertyBean prop = bean.createProperty();
      prop.setName("weblogic.DatasourceType");
      prop.setValue("GENERIC");
      return bean;
   }
}
