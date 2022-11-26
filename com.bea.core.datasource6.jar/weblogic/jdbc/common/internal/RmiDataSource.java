package weblogic.jdbc.common.internal;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.transaction.SystemException;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.ConnectionInitializationCallback;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ObjectLifeCycle;
import weblogic.jdbc.rmi.internal.RmiDriverSettings;
import weblogic.jndi.CrossPartitionAware;

public final class RmiDataSource extends ParentLogger implements RemoteDataSource, DataSourceMetaData, ObjectLifeCycle, CrossPartitionAware {
   private volatile WLDataSourceImpl delegate;

   public RmiDataSource(WLDataSourceImpl delegate) {
      if (delegate == null) {
         throw new IllegalArgumentException("null delegate");
      } else {
         this.delegate = delegate;
      }
   }

   void setDelegate(WLDataSourceImpl delegate) {
      if (delegate == null) {
         throw new IllegalArgumentException("null delegate");
      } else {
         this.delegate = delegate;
      }
   }

   public String getPoolName() {
      return this.delegate.getPoolName();
   }

   public String getAppName() {
      return this.delegate.getAppName();
   }

   public String getModuleName() {
      return this.delegate.getModuleName();
   }

   public String getCompName() {
      return this.delegate.getCompName();
   }

   public void setPoolName(String name) {
      this.delegate.setPoolName(name);
   }

   public String[] getJNDINames() {
      return this.delegate.getJNDINames();
   }

   public Properties getDriverProperties() {
      return this.delegate.getDriverProperties();
   }

   public RmiDriverSettings getDriverSettings() {
      return this.delegate.getDriverSettings();
   }

   public Connection getConnection(String username, String password) throws SQLException {
      return this.delegate.getConnection(username, password);
   }

   public Connection getConnection(String username, String password, Properties labels) throws SQLException {
      return this.delegate.getConnection(username, password, labels);
   }

   public void setLogWriter(PrintWriter out) throws SQLException {
      this.delegate.setLogWriter(out);
   }

   public PrintWriter getLogWriter() throws SQLException {
      return this.delegate.getLogWriter();
   }

   public void setLoginTimeout(int seconds) throws SQLException {
      this.delegate.setLoginTimeout(seconds);
   }

   public int getLoginTimeout() throws SQLException {
      return this.delegate.getLoginTimeout();
   }

   void recoverLoggingResourceTransactions() throws SystemException {
      this.delegate.recoverLoggingResourceTransactions();
   }

   public Connection getConnection() throws SQLException {
      return this.delegate.getConnection();
   }

   public Connection getConnection(Properties labels) throws SQLException {
      return this.delegate.getConnection(labels);
   }

   public boolean isTxDataSource() {
      return this.delegate.isTxDataSource();
   }

   public void start(Object unused) throws ResourceException {
      this.delegate.start(this);
   }

   public void resume() throws ResourceException {
      this.delegate.resume();
   }

   public void suspend(boolean unused) throws ResourceException {
      this.delegate.suspend(unused);
   }

   public void forceSuspend(boolean unused) throws ResourceException {
      this.delegate.forceSuspend(unused);
   }

   public void shutdown() throws ResourceException {
      this.delegate.shutdown();
   }

   public Object unwrap(Class iface) throws SQLException {
      if (!iface.isInterface()) {
         throw new SQLException("not an interface");
      } else if (iface.isInstance(this)) {
         return iface.cast(this);
      } else {
         throw new SQLException(this + " is not an instance of " + iface);
      }
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return iface.isInstance(this);
   }

   public void registerConnectionLabelingCallback(ConnectionLabelingCallback cbk) throws SQLException {
      this.delegate.registerConnectionLabelingCallback(cbk);
   }

   public void removeConnectionLabelingCallback() throws SQLException {
      this.delegate.removeConnectionLabelingCallback();
   }

   public void registerConnectionInitializationCallback(ConnectionInitializationCallback cbk) throws SQLException {
      this.delegate.registerConnectionInitializationCallback(cbk);
   }

   public void unregisterConnectionInitializationCallback() throws SQLException {
      this.delegate.unregisterConnectionInitializationCallback();
   }

   public Connection getConnectionToInstance(String instanceName) throws SQLException {
      return this.delegate.getConnectionToInstance(instanceName);
   }

   public Connection getConnectionToInstance(Connection sameAsThisOne) throws SQLException {
      return this.delegate.getConnectionToInstance(sameAsThisOne);
   }

   public Connection createConnection(Properties additionalProperties) throws SQLException {
      return this.delegate.createConnection(additionalProperties);
   }

   public Connection createConnectionToInstance(String instance, Properties additionalProperties) throws SQLException {
      return this.delegate.createConnectionToInstance(instance, additionalProperties);
   }

   public String getPartitionName() {
      return this.delegate.getPartitionName();
   }

   public boolean isAccessAllowed() {
      return this.delegate.isAccessAllowed();
   }
}
