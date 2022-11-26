package com.sun.faces.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;

public final class ExpiringConcurrentCache extends ConcurrentCache {
   private final ExpiryChecker _checker;
   private final ConcurrentMap _cache = new ConcurrentHashMap();
   private static final Logger _LOGGER;

   public ExpiringConcurrentCache(ConcurrentCache.Factory f, ExpiryChecker checker) {
      super(f);
      this._checker = checker;
   }

   public Object get(final Object key) throws ExecutionException {
      while(true) {
         boolean newlyCached = false;
         Future f = (Future)this._cache.get(key);
         if (f == null) {
            Callable callable = new Callable() {
               public Object call() throws Exception {
                  return ExpiringConcurrentCache.this.getFactory().newInstance(key);
               }
            };
            FutureTask ft = new FutureTask(callable);
            f = (Future)this._cache.putIfAbsent(key, ft);
            if (f == null) {
               f = ft;
               ft.run();
               newlyCached = true;
            }
         }

         try {
            Object obj = ((Future)f).get();
            if (newlyCached || !this._getExpiryChecker().isExpired(key, obj)) {
               return obj;
            }

            this._cache.remove(key, f);
         } catch (CancellationException var6) {
            if (_LOGGER.isLoggable(Level.SEVERE)) {
               _LOGGER.log(Level.SEVERE, var6.toString(), var6);
            }

            this._cache.remove(key, f);
         } catch (ExecutionException var7) {
            this._cache.remove(key, f);
            throw var7;
         } catch (InterruptedException var8) {
            throw new FacesException(var8);
         }
      }
   }

   public boolean containsKey(Object key) {
      Future f = (Future)this._cache.get(key);
      if (f != null && f.isDone() && !f.isCancelled()) {
         try {
            Object obj = f.get(0L, TimeUnit.MILLISECONDS);
            if (!this._getExpiryChecker().isExpired(key, obj)) {
               return true;
            }

            this._cache.remove(key, f);
         } catch (ExecutionException | TimeoutException var4) {
         } catch (CancellationException var5) {
            if (_LOGGER.isLoggable(Level.SEVERE)) {
               _LOGGER.log(Level.SEVERE, var5.toString(), var5);
            }
         } catch (InterruptedException var6) {
            throw new FacesException(var6);
         }
      }

      return false;
   }

   private ExpiryChecker _getExpiryChecker() {
      return this._checker;
   }

   static {
      _LOGGER = FacesLogger.UTIL.getLogger();
   }

   public interface ExpiryChecker {
      boolean isExpired(Object var1, Object var2);
   }
}
