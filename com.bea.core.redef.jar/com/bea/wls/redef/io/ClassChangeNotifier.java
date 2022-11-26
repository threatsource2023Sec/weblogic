package com.bea.wls.redef.io;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;

public class ClassChangeNotifier {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("ClassChangeNotifier");
   private ClassFinder finder;
   private URI[] roots;
   private final ConcurrentHashMap sourceMap = new ConcurrentHashMap();

   public void setFinder(ClassFinder finder) {
      this.finder = finder;
   }

   private File[] getDirs(String classPath) {
      String[] paths = classPath.split(File.pathSeparator);
      List l = new ArrayList();
      String[] var4 = paths;
      int var5 = paths.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String path = var4[var6];
         File f = new File(path);
         if (f.isDirectory()) {
            l.add(f);
         }
      }

      return (File[])l.toArray(new File[l.size()]);
   }

   public void updateCache(String className, Source s) {
      try {
         if (this.isRelative(s)) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Adding '" + className + "' to Map");
            }

            this.sourceMap.put(new SourceKey(className, s), s.lastModified());
         } else if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Source " + s + " is not relative to roots");
         }
      } catch (URISyntaxException var4) {
         var4.printStackTrace();
      }

   }

   private void updateCache(SourceKey key) {
      this.sourceMap.put(key, key.source.lastModified());
   }

   public Map scanForUpdates() {
      return this.scanForUpdates((Set)null);
   }

   public Map scanForUpdates(Set candidates) {
      Map sources = Collections.emptyMap();
      Iterator var3 = this.sourceMap.keySet().iterator();

      while(true) {
         SourceKey s;
         do {
            if (!var3.hasNext()) {
               return (Map)sources;
            }

            s = (SourceKey)var3.next();
         } while(candidates != null && !candidates.contains(s.className));

         Long lastMod = (Long)this.sourceMap.get(s);
         Long currentLastMod = s.source.lastModified();
         if (!lastMod.equals(currentLastMod)) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Source '" + s + " modified. currentLastMod: " + currentLastMod + " oldLastMod " + lastMod);
            }

            if (((Map)sources).isEmpty()) {
               sources = new TreeMap();
            }

            ((Map)sources).put(s.className, s.source);
            this.updateCache(s);
         }
      }
   }

   public void close() {
   }

   private URI[] createRoots(File[] dirs) {
      List uris = Collections.emptyList();
      File[] var3 = dirs;
      int var4 = dirs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File dir = var3[var5];
         if (!dir.isDirectory()) {
            throw new IllegalArgumentException(" Given File " + dir + " is not a directory");
         }

         if (!dir.exists()) {
            throw new IllegalArgumentException(" Given dir " + dir + " does not exist. ");
         }

         if (((List)uris).isEmpty()) {
            uris = new ArrayList();
         }

         ((List)uris).add(dir.toURI());
      }

      return (URI[])((List)uris).toArray(new URI[((List)uris).size()]);
   }

   private boolean isRelative(Source s) throws URISyntaxException {
      String u;
      if (this.roots == null) {
         u = this.finder.getClassPath();
         DEBUG.debug("Using classPath: " + u);
         this.roots = this.createRoots(this.getDirs(u));
      }

      u = null;
      URL url = s.getURL();

      URI u;
      try {
         u = url.toURI();
      } catch (URISyntaxException var12) {
         String scheme = url.getProtocol();
         if (!"file".equals(scheme)) {
            return false;
         }

         String path = url.getPath();
         String query = url.getQuery();
         String fragment = url.getRef();
         String authority = url.getAuthority();

         try {
            u = new URI(scheme, authority, path, query, fragment);
         } catch (URISyntaxException var11) {
            throw var12;
         }
      }

      if (u == null) {
         return false;
      } else {
         URI[] var4 = this.roots;
         int var14 = var4.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            URI root = var4[var15];
            URI rel = root.relativize(u);
            if (!rel.equals(u)) {
               return true;
            }
         }

         return false;
      }
   }

   static class SourceKey {
      final String className;
      final Source source;
      final int code;

      public SourceKey(String name, Source s) {
         this.className = name;
         this.source = s;
         this.code = 7 * this.className.hashCode() ^ 13 * this.source.hashCode();
      }

      public int hashCode() {
         return this.code;
      }
   }
}
