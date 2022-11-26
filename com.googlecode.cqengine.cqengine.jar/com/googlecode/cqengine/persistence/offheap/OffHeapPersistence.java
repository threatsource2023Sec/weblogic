package com.googlecode.cqengine.persistence.offheap;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.ConnectionManager;
import com.googlecode.cqengine.index.sqlite.RequestScopeConnectionManager;
import com.googlecode.cqengine.index.sqlite.SQLitePersistence;
import com.googlecode.cqengine.index.sqlite.support.DBQueries;
import com.googlecode.cqengine.index.sqlite.support.DBUtils;
import com.googlecode.cqengine.index.support.indextype.OffHeapTypeIndex;
import com.googlecode.cqengine.persistence.support.PersistenceFlags;
import com.googlecode.cqengine.persistence.support.sqlite.SQLiteObjectStore;
import com.googlecode.cqengine.persistence.support.sqlite.SQLiteOffHeapIdentityIndex;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.FlagsEnabled;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.io.Closeable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

public class OffHeapPersistence implements SQLitePersistence, Closeable {
   static final AtomicInteger INSTANCE_ID_GENERATOR = new AtomicInteger();
   final SimpleAttribute primaryKeyAttribute;
   final String instanceName;
   final SQLiteDataSource sqLiteDataSource;
   static final Properties DEFAULT_PROPERTIES = new Properties();
   volatile Connection persistentConnection;
   volatile boolean closed = false;
   final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

   protected OffHeapPersistence(SimpleAttribute primaryKeyAttribute, Properties overrideProperties) {
      Properties effectiveProperties = new Properties(DEFAULT_PROPERTIES);
      effectiveProperties.putAll(overrideProperties);
      SQLiteConfig sqLiteConfig = new SQLiteConfig(effectiveProperties);
      SQLiteDataSource sqLiteDataSource = new SQLiteDataSource(sqLiteConfig);
      String instanceName = "cqengine_" + INSTANCE_ID_GENERATOR.incrementAndGet();
      sqLiteDataSource.setUrl("jdbc:sqlite:file:" + instanceName + "?mode=memory&cache=shared");
      this.primaryKeyAttribute = primaryKeyAttribute;
      this.instanceName = instanceName;
      this.sqLiteDataSource = sqLiteDataSource;
      this.persistentConnection = this.getConnectionInternal((Index)null, QueryFactory.noQueryOptions());
   }

   public SimpleAttribute getPrimaryKeyAttribute() {
      return this.primaryKeyAttribute;
   }

   public String getInstanceName() {
      return this.instanceName;
   }

   public Connection getConnection(Index index, QueryOptions queryOptions) {
      Lock connectionLock = FlagsEnabled.isFlagEnabled(queryOptions, PersistenceFlags.READ_REQUEST) ? this.readWriteLock.readLock() : this.readWriteLock.writeLock();
      connectionLock.lock();

      Connection connection;
      try {
         connection = this.getConnectionInternal(index, queryOptions);
      } catch (RuntimeException var6) {
         connectionLock.unlock();
         throw var6;
      }

      return OffHeapPersistence.LockReleasingConnection.wrap(connection, connectionLock);
   }

   protected Connection getConnectionInternal(Index index, QueryOptions queryOptions) {
      if (this.closed) {
         throw new IllegalStateException("OffHeapPersistence has been closed: " + this.toString());
      } else {
         try {
            return this.sqLiteDataSource.getConnection();
         } catch (SQLException var4) {
            throw new IllegalStateException("Failed to open SQLite connection for memory instance: " + this.instanceName, var4);
         }
      }
   }

   public boolean supportsIndex(Index index) {
      return index instanceof OffHeapTypeIndex;
   }

   public void close() {
      DBUtils.closeQuietly(this.persistentConnection);
      this.persistentConnection = null;
      this.closed = true;
   }

   public long getBytesUsed() {
      Connection connection = null;

      long var2;
      try {
         connection = this.getConnectionInternal((Index)null, QueryFactory.noQueryOptions());
         var2 = DBQueries.getDatabaseSize(connection);
      } finally {
         DBUtils.closeQuietly(connection);
      }

      return var2;
   }

   public void compact() {
      Connection connection = null;

      try {
         connection = this.getConnectionInternal((Index)null, QueryFactory.noQueryOptions());
         DBQueries.compactDatabase(connection);
      } finally {
         DBUtils.closeQuietly(connection);
      }

   }

   public void expand(long numBytes) {
      Connection connection = null;

      try {
         connection = this.getConnectionInternal((Index)null, QueryFactory.noQueryOptions());
         DBQueries.expandDatabase(connection, numBytes);
      } finally {
         DBUtils.closeQuietly(connection);
      }

   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.close();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof OffHeapPersistence)) {
         return false;
      } else {
         OffHeapPersistence that = (OffHeapPersistence)o;
         return this.primaryKeyAttribute.equals(that.primaryKeyAttribute) && this.instanceName.equals(that.instanceName);
      }
   }

   public int hashCode() {
      int result = this.primaryKeyAttribute.hashCode();
      result = 31 * result + this.instanceName.hashCode();
      return result;
   }

   public String toString() {
      return "OffHeapPersistence{primaryKeyAttribute=" + this.primaryKeyAttribute + ", instanceName='" + this.instanceName + '\'' + '}';
   }

   public SQLiteObjectStore createObjectStore() {
      return new SQLiteObjectStore(this);
   }

   public SQLiteOffHeapIdentityIndex createIdentityIndex() {
      return SQLiteOffHeapIdentityIndex.onAttribute(this.primaryKeyAttribute);
   }

   public void openRequestScopeResources(QueryOptions queryOptions) {
      if (queryOptions.get(ConnectionManager.class) == null) {
         queryOptions.put(ConnectionManager.class, new RequestScopeConnectionManager(this));
      }

   }

   public void closeRequestScopeResources(QueryOptions queryOptions) {
      ConnectionManager connectionManager = (ConnectionManager)queryOptions.get(ConnectionManager.class);
      if (connectionManager instanceof RequestScopeConnectionManager) {
         ((RequestScopeConnectionManager)connectionManager).close();
         queryOptions.remove(ConnectionManager.class);
      }

   }

   public static OffHeapPersistence onPrimaryKey(SimpleAttribute primaryKeyAttribute) {
      return onPrimaryKeyWithProperties(primaryKeyAttribute, new Properties());
   }

   public static OffHeapPersistence onPrimaryKeyWithProperties(SimpleAttribute primaryKeyAttribute, Properties overrideProperties) {
      return new OffHeapPersistence(primaryKeyAttribute, overrideProperties);
   }

   static class LockReleasingConnection implements InvocationHandler {
      final Connection targetConnection;
      final Lock lockToUnlock;
      boolean unlockedAlready = false;

      LockReleasingConnection(Connection targetConnection, Lock lockToUnlock) {
         this.targetConnection = targetConnection;
         this.lockToUnlock = lockToUnlock;
      }

      public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
         Object result = m.invoke(this.targetConnection, args);
         if (m.getName().equals("close") && !this.unlockedAlready) {
            this.lockToUnlock.unlock();
            this.unlockedAlready = true;
         }

         return result;
      }

      static Connection wrap(Connection targetConnection, Lock lockToUnlock) {
         return (Connection)Proxy.newProxyInstance(targetConnection.getClass().getClassLoader(), new Class[]{Connection.class}, new LockReleasingConnection(targetConnection, lockToUnlock));
      }
   }
}
