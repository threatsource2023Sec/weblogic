package kodo.jdo.jdbc;

import java.util.Map;
import java.util.Properties;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.datastore.DataStoreCache;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.RecordFactory;
import javax.resource.cci.ResourceAdapterMetaData;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;
import kodo.ee.KodoConnectionFactory;
import kodo.ee.KodoConnectionFactoryMetaData;
import kodo.ee.KodoObjectFactory;
import kodo.jdbc.conf.JDBCConsolidatedConfiguration;
import kodo.jdo.JDOProperties;
import kodo.jdo.KodoDataStoreCache;
import kodo.jdo.KodoJDOHelper;
import kodo.jdo.KodoPersistenceManager;
import kodo.jdo.KodoPersistenceManagerFactory;
import kodo.jdo.QueryResultCache;
import kodo.remote.RemoteTransferListener;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;

public class JDOConnectionFactory extends JDBCConsolidatedConfiguration implements KodoPersistenceManagerFactory, KodoConnectionFactory {
   private KodoPersistenceManagerFactory _factory;

   public static PersistenceManagerFactory getPersistenceManagerFactory(Properties props) {
      return new JDOConnectionFactory(props);
   }

   public static PersistenceManagerFactory getPersistenceManagerFactory(Map map) {
      Properties props = new Properties();
      props.putAll(props);
      return new JDOConnectionFactory(props);
   }

   public JDOConnectionFactory() {
      this(true);
   }

   public JDOConnectionFactory(boolean defaults) {
      super(defaults);
      this._factory = null;
      this.setSpecification("jdo");
      this.setTransactionModeManaged(true);
   }

   public JDOConnectionFactory(Properties props) {
      this();
      this.fromProperties(JDOProperties.toKodoProperties(props));
   }

   public JDOConnectionFactory(ManagedConnectionFactory mcf, ConnectionManager cm) {
      this();
      this.fromProperties(((JDBCConfiguration)mcf).toProperties(true));
   }

   private synchronized KodoPersistenceManagerFactory getFactory() {
      if (this._factory == null) {
         Map props = this.toProperties(false);
         ConfigurationProvider cp = new MapConfigurationProvider(props);
         this._factory = KodoJDOHelper.toPersistenceManagerFactory(JDBCBrokerFactory.getInstance(cp));
         this.setReadOnly(2);
      }

      return this._factory;
   }

   public RecordFactory getRecordFactory() throws NotSupportedException {
      throw new NotSupportedException("getRecordFactory");
   }

   public ResourceAdapterMetaData getMetaData() {
      return new KodoConnectionFactoryMetaData();
   }

   public Connection getConnection() throws ResourceException {
      return this.getConnection((ConnectionSpec)null);
   }

   public Connection getConnection(ConnectionSpec spec) throws ResourceException {
      return (Connection)this.getFactory().getPersistenceManager();
   }

   public void setReference(Reference ref) {
   }

   public Reference getReference() {
      String className = this.getClass().getName();
      String factoryName = KodoObjectFactory.class.getName();
      String factoryURL = null;
      Reference ref = new Reference(className, factoryName, (String)factoryURL);
      String id = String.valueOf(this.hashCode());
      ref.add(new StringRefAddr("kodo.name", id));
      KodoObjectFactory.ref(id, this);
      return ref;
   }

   public OpenJPAConfiguration getConfiguration() {
      return this;
   }

   public Properties getProperties() {
      return this.getFactory().getProperties();
   }

   public Object getUserObject(Object key) {
      return this.getFactory().getUserObject(key);
   }

   public Object putUserObject(Object key, Object val) {
      return this.getFactory().putUserObject(key, val);
   }

   public DataStoreCache getDataStoreCache() {
      return this.getFactory().getDataStoreCache();
   }

   public KodoDataStoreCache getDataStoreCache(String cacheName) {
      return this.getFactory().getDataStoreCache(cacheName);
   }

   public QueryResultCache getQueryResultCache() {
      return this.getFactory().getQueryResultCache();
   }

   public PersistenceManager getPersistenceManager() {
      return this.getFactory().getPersistenceManager();
   }

   public PersistenceManager getPersistenceManager(String user, String pass) {
      return this.getFactory().getPersistenceManager(user, pass);
   }

   public KodoPersistenceManager getPersistenceManager(boolean managed, int connRetainMode) {
      return this.getFactory().getPersistenceManager(managed, connRetainMode);
   }

   public KodoPersistenceManager getPersistenceManager(String user, String pass, boolean managed, int connRetainMode) {
      return this.getFactory().getPersistenceManager(user, pass, managed, connRetainMode);
   }

   public boolean startPersistenceServer() {
      return this.getFactory().startPersistenceServer();
   }

   public boolean joinPersistenceServer() {
      return this.getFactory().joinPersistenceServer();
   }

   public boolean stopPersistenceServer() {
      return this.getFactory().stopPersistenceServer();
   }

   public boolean isPersistenceServerRunning() {
      return this.getFactory().isPersistenceServerRunning();
   }

   public void addInstanceLifecycleListener(InstanceLifecycleListener listener, Class[] classes) {
      this.getFactory().addInstanceLifecycleListener(listener, classes);
   }

   public void removeInstanceLifecycleListener(InstanceLifecycleListener listener) {
      this.getFactory().removeInstanceLifecycleListener(listener);
   }

   public void addTransferListener(RemoteTransferListener listener) {
      this.getFactory().addTransferListener(listener);
   }

   public void removeTransferListener(RemoteTransferListener listener) {
      this.getFactory().removeTransferListener(listener);
   }

   protected void preClose() {
      this.getFactory().close();
   }

   public boolean isClosed() {
      return this.getFactory().isClosed();
   }

   public boolean getRetainValues() {
      return this.getFactory().getRetainValues();
   }

   public void setRetainValues(boolean val) {
      this.getFactory().setRetainValues(val);
   }

   public boolean getRestoreValues() {
      return this.getFactory().getRestoreValues();
   }

   public void setRestoreValues(boolean val) {
      this.getFactory().setRestoreValues(val);
   }

   public boolean getIgnoreCache() {
      return this.getFactory().getIgnoreCache();
   }

   public void setIgnoreCache(boolean val) {
      this.getFactory().setIgnoreCache(val);
   }

   public boolean getDetachAllOnCommit() {
      return this.getFactory().getDetachAllOnCommit();
   }

   public void setDetachAllOnCommit(boolean detach) {
      this.getFactory().setDetachAllOnCommit(detach);
   }
}
