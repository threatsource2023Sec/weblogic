package net.shibboleth.utilities.java.support.httpclient;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.primitive.TimerSupport;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClientBuilder;
import org.apache.http.impl.client.cache.FileResourceFactory;
import org.apache.http.impl.client.cache.ManagedHttpCacheStorage;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCachingHttpClientBuilder extends HttpClientBuilder {
   private File cacheDir;
   private int maxCacheEntries;
   private long maxCacheEntrySize;
   @Duration
   private long maintentanceTaskInterval;
   private ManagedHttpCacheStorage managedStorage;

   public FileCachingHttpClientBuilder() {
      this(CachingHttpClientBuilder.create());
   }

   public FileCachingHttpClientBuilder(@Nonnull CachingHttpClientBuilder builder) {
      super(builder);
      this.cacheDir = new File(System.getProperty("java.io.tmpdir") + File.separator + "wwwcache");
      this.maxCacheEntries = 100;
      this.maxCacheEntrySize = 10485760L;
      this.maintentanceTaskInterval = 1800000L;
   }

   public File getCacheDirectory() {
      return this.cacheDir;
   }

   public void setCacheDirectory(@Nonnull @NotEmpty String directoryPath) {
      String trimmedPath = (String)Constraint.isNotNull(StringSupport.trimOrNull(directoryPath), "Cache directory path can not be null or empty");
      this.cacheDir = new File(trimmedPath);
   }

   public void setCacheDirectory(@Nonnull File directory) {
      this.cacheDir = (File)Constraint.isNotNull(directory, "Cache directory can not be null");
   }

   public int getMaxCacheEntries() {
      return this.maxCacheEntries;
   }

   public void setMaxCacheEntries(int maxEntries) {
      this.maxCacheEntries = (int)Constraint.isGreaterThan(0L, (long)maxEntries, "Maximum number of cache entries must be greater than 0");
   }

   public long getMaxCacheEntrySize() {
      return this.maxCacheEntrySize;
   }

   public void setMaxCacheEntrySize(long size) {
      this.maxCacheEntrySize = (long)((int)Constraint.isGreaterThan(0L, size, "Maximum cache entry size must be greater than 0"));
   }

   public long getMaintentanceTaskInterval() {
      return this.maintentanceTaskInterval;
   }

   public void setMaintentanceTaskInterval(long value) {
      this.maintentanceTaskInterval = Constraint.isGreaterThan(0L, value, "Maintenance task interval must be greater than 0");
   }

   protected void decorateApacheBuilder() throws Exception {
      super.decorateApacheBuilder();
      if (!this.cacheDir.exists() && !this.cacheDir.mkdirs()) {
         throw new IOException("Unable to create cache directory " + this.cacheDir.getAbsolutePath());
      } else if (!this.cacheDir.canRead()) {
         throw new IOException("Cache directory '" + this.cacheDir.getAbsolutePath() + "' is not readable");
      } else if (!this.cacheDir.canWrite()) {
         throw new IOException("Cache directory '" + this.cacheDir.getAbsolutePath() + "' is not writable");
      } else {
         CachingHttpClientBuilder cachingBuilder = (CachingHttpClientBuilder)this.getApacheBuilder();
         CacheConfig.Builder cacheConfigBuilder = CacheConfig.custom();
         cacheConfigBuilder.setMaxCacheEntries(this.maxCacheEntries);
         cacheConfigBuilder.setMaxObjectSize(this.maxCacheEntrySize);
         cacheConfigBuilder.setHeuristicCachingEnabled(false);
         cacheConfigBuilder.setSharedCache(false);
         CacheConfig cacheConfig = cacheConfigBuilder.build();
         cachingBuilder.setCacheConfig(cacheConfig);
         cachingBuilder.setResourceFactory(new FileResourceFactory(this.cacheDir));
         this.managedStorage = new ManagedHttpCacheStorage(cacheConfig);
         cachingBuilder.setHttpCacheStorage(this.managedStorage);
      }
   }

   public synchronized HttpClient buildClient() throws Exception {
      CloseableHttpClient client = (CloseableHttpClient)super.buildClient();
      ManagedHttpCacheStorage tempStorage = this.managedStorage;
      this.managedStorage = null;
      return new StorageManagingHttpClient(client, tempStorage, this.getMaintentanceTaskInterval());
   }

   public static class StorageMaintenanceTask extends TimerTask {
      private Logger log = LoggerFactory.getLogger(StorageMaintenanceTask.class);
      private ManagedHttpCacheStorage storage;

      public StorageMaintenanceTask(@Nonnull ManagedHttpCacheStorage managedStorage) {
         this.storage = (ManagedHttpCacheStorage)Constraint.isNotNull(managedStorage, "ManagedHttpCacheStorage was null");
      }

      public void run() {
         try {
            this.log.debug("Executing ManagedHttpCacheStorage cleanResources()");
            this.storage.cleanResources();
         } catch (Throwable var2) {
            this.log.warn("Error invoking ManagedHttpCacheStorage cleanResources()", var2);
         }

      }
   }

   public static class StorageManagingHttpClient extends CloseableHttpClient implements InitializableComponent, DestructableComponent {
      private Logger log = LoggerFactory.getLogger(StorageManagingHttpClient.class);
      private CloseableHttpClient httpClient;
      private ManagedHttpCacheStorage storage;
      private long maintenanceTaskInterval;
      private boolean initialized;
      private boolean destroyed;
      private Timer timer;
      private TimerTask maintenanceTask;

      public StorageManagingHttpClient(@Nonnull CloseableHttpClient wrappedClient, @Nonnull ManagedHttpCacheStorage managedStorage, long taskInterval) {
         this.httpClient = (CloseableHttpClient)Constraint.isNotNull(wrappedClient, "HttpClient was null");
         this.storage = (ManagedHttpCacheStorage)Constraint.isNotNull(managedStorage, "ManagedHttpCacheStorage was null");
         this.maintenanceTaskInterval = taskInterval;
      }

      protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
         ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
         ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
         return this.httpClient.execute(target, request, context);
      }

      /** @deprecated */
      @Deprecated
      public HttpParams getParams() {
         ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
         ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
         return this.httpClient.getParams();
      }

      /** @deprecated */
      @Deprecated
      public ClientConnectionManager getConnectionManager() {
         ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
         ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
         return this.httpClient.getConnectionManager();
      }

      public void close() throws IOException {
         ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
         ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
         this.httpClient.close();
      }

      public boolean isInitialized() {
         return this.initialized;
      }

      public boolean isDestroyed() {
         return this.destroyed;
      }

      public void initialize() throws ComponentInitializationException {
         this.timer = new Timer(TimerSupport.getTimerName(this), true);
         this.maintenanceTask = new StorageMaintenanceTask(this.storage);
         this.timer.schedule(this.maintenanceTask, this.maintenanceTaskInterval, this.maintenanceTaskInterval);
         this.initialized = true;
      }

      public void destroy() {
         this.maintenanceTask.cancel();
         this.timer.cancel();
         this.maintenanceTask = null;
         this.timer = null;

         try {
            this.log.debug("Executing ManagedHttpCacheStorage shutdown()");
            this.storage.shutdown();
         } catch (Throwable var2) {
            this.log.warn("Error invoking ManagedHttpCacheStorage shutdown()", var2);
         }

         this.storage = null;
         this.httpClient = null;
         this.destroyed = true;
      }
   }
}
