package com.sun.faces.facelets.impl;

import com.sun.faces.util.ConcurrentCache;
import com.sun.faces.util.ExpiringConcurrentCache;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.view.facelets.FaceletCache;

final class DefaultFaceletCache extends FaceletCache {
   private static final Logger LOGGER;
   private final ConcurrentCache _faceletCache;
   private final ConcurrentCache _metadataFaceletCache;

   DefaultFaceletCache(final long refreshPeriod) {
      final boolean checkExpiry = refreshPeriod > 0L;
      ConcurrentCache.Factory faceletFactory = new ConcurrentCache.Factory() {
         public Record newInstance(URL key) throws IOException {
            long lastModified = checkExpiry ? Util.getLastModified(key) : 0L;
            return new Record(System.currentTimeMillis(), lastModified, (DefaultFacelet)DefaultFaceletCache.this.getMemberFactory().newInstance(key), refreshPeriod);
         }
      };
      ConcurrentCache.Factory metadataFaceletFactory = new ConcurrentCache.Factory() {
         public Record newInstance(URL key) throws IOException {
            long lastModified = checkExpiry ? Util.getLastModified(key) : 0L;
            return new Record(System.currentTimeMillis(), lastModified, (DefaultFacelet)DefaultFaceletCache.this.getMetadataMemberFactory().newInstance(key), refreshPeriod);
         }
      };
      if (refreshPeriod == 0L) {
         this._faceletCache = new NoCache(faceletFactory);
         this._metadataFaceletCache = new NoCache(metadataFaceletFactory);
      } else {
         ExpiringConcurrentCache.ExpiryChecker checker = refreshPeriod > 0L ? new ExpiryChecker() : new NeverExpired();
         this._faceletCache = new ExpiringConcurrentCache(faceletFactory, (ExpiringConcurrentCache.ExpiryChecker)checker);
         this._metadataFaceletCache = new ExpiringConcurrentCache(metadataFaceletFactory, (ExpiringConcurrentCache.ExpiryChecker)checker);
      }

   }

   public DefaultFacelet getFacelet(URL url) throws IOException {
      Util.notNull("url", url);
      DefaultFacelet f = null;

      try {
         f = ((Record)this._faceletCache.get(url)).getFacelet();
      } catch (ExecutionException var4) {
         this._unwrapIOException(var4);
      }

      return f;
   }

   public boolean isFaceletCached(URL url) {
      Util.notNull("url", url);
      return this._faceletCache.containsKey(url);
   }

   public DefaultFacelet getViewMetadataFacelet(URL url) throws IOException {
      Util.notNull("url", url);
      DefaultFacelet f = null;

      try {
         f = ((Record)this._metadataFaceletCache.get(url)).getFacelet();
      } catch (ExecutionException var4) {
         this._unwrapIOException(var4);
      }

      return f;
   }

   public boolean isViewMetadataFaceletCached(URL url) {
      Util.notNull("url", url);
      return this._metadataFaceletCache.containsKey(url);
   }

   private void _unwrapIOException(ExecutionException e) throws IOException {
      Throwable t = e.getCause();
      if (t instanceof IOException) {
         throw (IOException)t;
      } else if (t.getCause() instanceof IOException) {
         throw (IOException)t.getCause();
      } else if (t instanceof RuntimeException) {
         throw (RuntimeException)t;
      } else {
         throw new FacesException(t);
      }
   }

   static {
      LOGGER = FacesLogger.FACELETS_FACTORY.getLogger();
   }

   private static class NoCache extends ConcurrentCache {
      public NoCache(ConcurrentCache.Factory f) {
         super(f);
      }

      public Record get(URL key) throws ExecutionException {
         try {
            return (Record)this.getFactory().newInstance(key);
         } catch (Exception var3) {
            throw new ExecutionException(var3);
         }
      }

      public boolean containsKey(URL key) {
         return false;
      }
   }

   private static class NeverExpired implements ExpiringConcurrentCache.ExpiryChecker {
      private NeverExpired() {
      }

      public boolean isExpired(URL key, Record value) {
         return false;
      }

      // $FF: synthetic method
      NeverExpired(Object x0) {
         this();
      }
   }

   private static class ExpiryChecker implements ExpiringConcurrentCache.ExpiryChecker {
      private ExpiryChecker() {
      }

      public boolean isExpired(URL url, Record record) {
         if (System.currentTimeMillis() > record.getNextRefreshTime()) {
            record.getAndUpdateNextRefreshTime();
            long lastModified = Util.getLastModified(url);
            return lastModified > record.getLastModified();
         } else {
            return false;
         }
      }

      // $FF: synthetic method
      ExpiryChecker(Object x0) {
         this();
      }
   }

   private static class Record {
      private final long _lastModified;
      private final long _refreshInterval;
      private final long _creationTime;
      private final AtomicLong _nextRefreshTime;
      private final DefaultFacelet _facelet;

      Record(long creationTime, long lastModified, DefaultFacelet facelet, long refreshInterval) {
         this._facelet = facelet;
         this._creationTime = creationTime;
         this._lastModified = lastModified;
         this._refreshInterval = refreshInterval;
         this._nextRefreshTime = this._refreshInterval > 0L ? new AtomicLong(creationTime + refreshInterval) : null;
      }

      DefaultFacelet getFacelet() {
         return this._facelet;
      }

      long getLastModified() {
         return this._lastModified;
      }

      long getNextRefreshTime() {
         return this._refreshInterval > 0L ? this._nextRefreshTime.get() : 0L;
      }

      long getAndUpdateNextRefreshTime() {
         return this._refreshInterval > 0L ? this._nextRefreshTime.getAndSet(System.currentTimeMillis() + this._refreshInterval) : 0L;
      }
   }
}
