package kodo.persistence.jdbc;

import java.util.Map;
import java.util.Properties;
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
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;
import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactory;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;
import org.apache.openjpa.persistence.QueryResultCache;
import org.apache.openjpa.persistence.StoreCache;

public class JPAConnectionFactory extends JDBCConsolidatedConfiguration implements OpenJPAEntityManagerFactory, KodoConnectionFactory {
   private OpenJPAEntityManagerFactorySPI _factory;

   public JPAConnectionFactory() {
      this._factory = null;
      this.setSpecification("jpa");
      this.setTransactionModeManaged(true);
   }

   public JPAConnectionFactory(Properties props) {
      this();
      this.fromProperties(props);
   }

   public JPAConnectionFactory(ManagedConnectionFactory mcf, ConnectionManager cm) {
      this();
      this.fromProperties(((JDBCConfiguration)mcf).toProperties(true));
   }

   private synchronized OpenJPAEntityManagerFactorySPI getFactory() {
      if (this._factory == null) {
         ConfigurationProvider cp = new MapConfigurationProvider(this.toProperties(false));
         this._factory = (OpenJPAEntityManagerFactorySPI)JPAFacadeHelper.toEntityManagerFactory(JDBCBrokerFactory.getInstance(cp));
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
      return (Connection)this.getFactory().createEntityManager();
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
      return this.getFactory().getConfiguration();
   }

   public Properties getPropertiesAsProperties() {
      return this.getFactory().getPropertiesAsProperties();
   }

   public Map getProperties() {
      return this.getFactory().getProperties();
   }

   public Object putUserObject(Object key, Object val) {
      return this.getFactory().putUserObject(key, val);
   }

   public Object getUserObject(Object key) {
      return this.getFactory().getUserObject(key);
   }

   public StoreCache getStoreCache() {
      return this.getFactory().getStoreCache();
   }

   public StoreCache getStoreCache(String name) {
      return this.getFactory().getStoreCache(name);
   }

   public QueryResultCache getQueryResultCache() {
      return this.getFactory().getQueryResultCache();
   }

   public boolean isOpen() {
      return this.getFactory().isOpen();
   }

   public OpenJPAEntityManager createEntityManager() {
      return this.getFactory().createEntityManager();
   }

   public OpenJPAEntityManager createEntityManager(Map map) {
      return this.getFactory().createEntityManager(map);
   }

   public void addLifecycleListener(Object listener, Class... classes) {
      this.getFactory().addLifecycleListener(listener, classes);
   }

   public void removeLifecycleListener(Object listener) {
      this.getFactory().removeLifecycleListener(listener);
   }

   public void addTransactionListener(Object listener) {
      this.getFactory().addTransactionListener(listener);
   }

   public void removeTransactionListener(Object listener) {
      this.getFactory().removeTransactionListener(listener);
   }
}
