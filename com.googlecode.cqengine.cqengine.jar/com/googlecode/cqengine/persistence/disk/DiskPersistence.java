package com.googlecode.cqengine.persistence.disk;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.ConnectionManager;
import com.googlecode.cqengine.index.sqlite.RequestScopeConnectionManager;
import com.googlecode.cqengine.index.sqlite.SQLitePersistence;
import com.googlecode.cqengine.index.sqlite.support.DBQueries;
import com.googlecode.cqengine.index.sqlite.support.DBUtils;
import com.googlecode.cqengine.index.support.indextype.DiskTypeIndex;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.persistence.support.sqlite.SQLiteDiskIdentityIndex;
import com.googlecode.cqengine.persistence.support.sqlite.SQLiteObjectStore;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.io.Closeable;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

public class DiskPersistence implements SQLitePersistence, Closeable {
   final SimpleAttribute primaryKeyAttribute;
   final File file;
   final SQLiteDataSource sqLiteDataSource;
   static final Properties DEFAULT_PROPERTIES = new Properties();
   volatile Connection persistentConnection;
   volatile boolean closed = false;

   protected DiskPersistence(SimpleAttribute primaryKeyAttribute, File file, Properties overrideProperties) {
      Properties effectiveProperties = new Properties();
      effectiveProperties.putAll(DEFAULT_PROPERTIES);
      effectiveProperties.putAll(overrideProperties);
      SQLiteConfig sqLiteConfig = new SQLiteConfig(effectiveProperties);
      SQLiteDataSource sqLiteDataSource = new SQLiteDataSource(sqLiteConfig);
      sqLiteDataSource.setUrl("jdbc:sqlite:file:" + file);
      this.primaryKeyAttribute = primaryKeyAttribute;
      this.file = file.getAbsoluteFile();
      this.sqLiteDataSource = sqLiteDataSource;
      boolean openPersistentConnection = "true".equals(effectiveProperties.getProperty("persistent_connection"));
      boolean useSharedCache = "true".equals(effectiveProperties.getProperty("shared_cache"));
      if (useSharedCache) {
         sqLiteDataSource.setUrl("jdbc:sqlite:file:" + file + "?cache=shared");
      } else {
         sqLiteDataSource.setUrl("jdbc:sqlite:file:" + file);
      }

      if (useSharedCache || openPersistentConnection) {
         this.persistentConnection = this.getConnection((Index)null, QueryFactory.noQueryOptions());
      }

   }

   public SimpleAttribute getPrimaryKeyAttribute() {
      return this.primaryKeyAttribute;
   }

   public File getFile() {
      return this.file;
   }

   public Connection getConnection(Index index, QueryOptions queryOptions) {
      if (this.closed) {
         throw new IllegalStateException("DiskPersistence has been closed: " + this.toString());
      } else {
         try {
            return this.sqLiteDataSource.getConnection();
         } catch (SQLException var4) {
            throw new IllegalStateException("Failed to open SQLite connection for file: " + this.file, var4);
         }
      }
   }

   public boolean supportsIndex(Index index) {
      return index instanceof DiskTypeIndex;
   }

   public void close() {
      DBUtils.closeQuietly(this.persistentConnection);
      this.persistentConnection = null;
      this.closed = true;
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.close();
   }

   public long getBytesUsed() {
      Connection connection = null;

      long var2;
      try {
         connection = this.getConnection((Index)null, QueryFactory.noQueryOptions());
         var2 = DBQueries.getDatabaseSize(connection);
      } finally {
         DBUtils.closeQuietly(connection);
      }

      return var2;
   }

   public void compact() {
      Connection connection = null;

      try {
         connection = this.getConnection((Index)null, QueryFactory.noQueryOptions());
         DBQueries.compactDatabase(connection);
      } finally {
         DBUtils.closeQuietly(connection);
      }

   }

   public void expand(long numBytes) {
      Connection connection = null;

      try {
         connection = this.getConnection((Index)null, QueryFactory.noQueryOptions());
         DBQueries.expandDatabase(connection, numBytes);
      } finally {
         DBUtils.closeQuietly(connection);
      }

   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DiskPersistence)) {
         return false;
      } else {
         DiskPersistence that = (DiskPersistence)o;
         return this.primaryKeyAttribute.equals(that.primaryKeyAttribute) && this.file.equals(that.file);
      }
   }

   public int hashCode() {
      int result = this.primaryKeyAttribute.hashCode();
      result = 31 * result + this.file.hashCode();
      return result;
   }

   public String toString() {
      return "DiskPersistence{primaryKeyAttribute=" + this.primaryKeyAttribute + ", file=" + this.file + '}';
   }

   public ObjectStore createObjectStore() {
      return new SQLiteObjectStore(this);
   }

   public SQLiteDiskIdentityIndex createIdentityIndex() {
      return SQLiteDiskIdentityIndex.onAttribute(this.primaryKeyAttribute);
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

   public static File createTempFile() {
      try {
         File tempFile = File.createTempFile("cqengine_", ".db");
         return tempFile;
      } catch (Exception var2) {
         throw new IllegalStateException("Failed to create temp file for CQEngine disk persistence", var2);
      }
   }

   public static DiskPersistence onPrimaryKey(SimpleAttribute primaryKeyAttribute) {
      return onPrimaryKeyInFile(primaryKeyAttribute, createTempFile());
   }

   public static DiskPersistence onPrimaryKeyInFile(SimpleAttribute primaryKeyAttribute, File file) {
      return onPrimaryKeyInFileWithProperties(primaryKeyAttribute, file, new Properties());
   }

   public static DiskPersistence onPrimaryKeyInFileWithProperties(SimpleAttribute primaryKeyAttribute, File file, Properties overrideProperties) {
      return new DiskPersistence(primaryKeyAttribute, file, overrideProperties);
   }

   static {
      DEFAULT_PROPERTIES.setProperty("busy_timeout", String.valueOf(Integer.MAX_VALUE));
      DEFAULT_PROPERTIES.setProperty("journal_mode", "WAL");
      DEFAULT_PROPERTIES.setProperty("synchronous", "NORMAL");
      DEFAULT_PROPERTIES.setProperty("shared_cache", "false");
      DEFAULT_PROPERTIES.setProperty("persistent_connection", "false");
   }
}
