package com.kenai.jffi;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public final class Library {
   private static final Map cache = new ConcurrentHashMap();
   private static final Object lock = new Object();
   private static final ThreadLocal lastError = new ThreadLocal();
   public static final int LAZY = 1;
   public static final int NOW = 2;
   public static final int LOCAL = 4;
   public static final int GLOBAL = 8;
   private final long handle;
   private final String name;
   private final Foreign foreign;
   private volatile int disposed;
   private static final AtomicIntegerFieldUpdater UPDATER = AtomicIntegerFieldUpdater.newUpdater(Library.class, "disposed");

   private static long dlopen(Foreign foreign, String name, int flags) {
      try {
         return Foreign.dlopen(name, flags);
      } catch (UnsatisfiedLinkError var4) {
         lastError.set(var4.getMessage());
         return 0L;
      }
   }

   public static final Library getDefault() {
      return Library.DefaultLibrary.INSTANCE;
   }

   public static final Library getCachedInstance(String name, int flags) {
      if (name == null) {
         return getDefault();
      } else {
         WeakReference ref = (WeakReference)cache.get(name);
         Library lib = ref != null ? (Library)ref.get() : null;
         if (lib != null) {
            return lib;
         } else {
            lib = openLibrary(name, flags);
            if (lib == null) {
               return null;
            } else {
               cache.put(name, new WeakReference(lib));
               return lib;
            }
         }
      }
   }

   public static final Library openLibrary(String name, int flags) {
      if (flags == 0) {
         flags = 5;
      }

      Foreign foreign = Foreign.getInstance();
      long address = dlopen(foreign, name, flags);
      return address != 0L ? new Library(foreign, name, address) : null;
   }

   private Library(Foreign foreign, String name, long address) {
      this.foreign = foreign;
      this.name = name;
      this.handle = address;
   }

   public final long getSymbolAddress(String name) {
      try {
         Foreign var10000 = this.foreign;
         return Foreign.dlsym(this.handle, name);
      } catch (UnsatisfiedLinkError var3) {
         Foreign var10001 = this.foreign;
         lastError.set(Foreign.dlerror());
         return 0L;
      }
   }

   public static final String getLastError() {
      String error = (String)lastError.get();
      return error != null ? error : "unknown";
   }

   protected void finalize() throws Throwable {
      try {
         int disposed = UPDATER.getAndSet(this, 1);
         if (disposed == 0 && this.handle != 0L) {
            Foreign var10000 = this.foreign;
            Foreign.dlclose(this.handle);
         }
      } finally {
         super.finalize();
      }

   }

   private static final class DefaultLibrary {
      private static final Library INSTANCE = Library.openLibrary((String)null, 9);
   }
}
