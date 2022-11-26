package org.glassfish.hk2.osgiresourcelocator;

import java.net.URL;
import java.util.List;

public abstract class ResourceFinder {
   private static ResourceFinder _me;

   public static void initialize(ResourceFinder singleton) {
      if (singleton == null) {
         throw new NullPointerException("Did you intend to call reset()?");
      } else if (_me != null) {
         throw new IllegalStateException("Already initialzed with [" + _me + "]");
      } else {
         _me = singleton;
      }
   }

   public static synchronized void reset() {
      if (_me == null) {
         throw new IllegalStateException("Not yet initialized");
      } else {
         _me = null;
      }
   }

   public static URL findEntry(String path) {
      return _me == null ? null : _me.findEntry1(path);
   }

   public static List findEntries(String path) {
      return _me == null ? null : _me.findEntries1(path);
   }

   abstract URL findEntry1(String var1);

   abstract List findEntries1(String var1);
}
