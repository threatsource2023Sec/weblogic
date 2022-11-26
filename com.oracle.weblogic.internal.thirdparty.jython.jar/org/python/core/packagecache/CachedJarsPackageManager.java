package org.python.core.packagecache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessControlException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.python.core.Options;
import org.python.core.util.FileUtil;
import org.python.util.Generic;

public abstract class CachedJarsPackageManager extends PackageManager {
   private boolean indexModified;
   private Map jarfiles;
   private File cachedir;

   protected void message(String msg) {
   }

   protected void warning(String warn) {
   }

   protected void comment(String msg) {
   }

   protected void debug(String msg) {
   }

   protected boolean filterByName(String name, boolean pkg) {
      return name.indexOf(36) != -1;
   }

   protected boolean filterByAccess(String name, int acc) {
      return (acc & 1) != 1;
   }

   protected static String listToString(List list) {
      int n = list.size();
      StringBuilder ret = new StringBuilder();

      for(int i = 0; i < n; ++i) {
         ret.append((String)list.get(i));
         if (i < n - 1) {
            ret.append(",");
         }
      }

      return ret.toString();
   }

   private void addZipEntry(Map zipPackages, ZipEntry entry, ZipInputStream zip) throws IOException {
      String name = entry.getName();
      if (name.endsWith(".class")) {
         char sep = '/';
         int breakPoint = name.lastIndexOf(sep);
         if (breakPoint == -1) {
            breakPoint = name.lastIndexOf(92);
            sep = '\\';
         }

         String packageName;
         if (breakPoint == -1) {
            packageName = "";
         } else {
            packageName = name.substring(0, breakPoint).replace(sep, '.');
         }

         String className = name.substring(breakPoint + 1, name.length() - 6);
         if (!this.filterByName(className, false)) {
            List[] vec = (List[])zipPackages.get(packageName);
            if (vec == null) {
               vec = this.createGenericStringListArray();
               zipPackages.put(packageName, vec);
            }

            int access = checkAccess(zip);
            if (access != -1 && !this.filterByAccess(name, access)) {
               vec[0].add(className);
            } else {
               vec[1].add(className);
            }

         }
      }
   }

   private List[] createGenericStringListArray() {
      return new List[]{Generic.list(), Generic.list()};
   }

   private Map getZipPackages(InputStream jarin) throws IOException {
      Map zipPackages = Generic.map();
      ZipInputStream zip = new ZipInputStream(jarin);

      ZipEntry entry;
      while((entry = zip.getNextEntry()) != null) {
         this.addZipEntry(zipPackages, entry, zip);
         zip.closeEntry();
      }

      Map transformed = Generic.map();

      Map.Entry kv;
      String classes;
      for(Iterator var6 = zipPackages.entrySet().iterator(); var6.hasNext(); transformed.put(kv.getKey(), classes)) {
         kv = (Map.Entry)var6.next();
         List[] vec = (List[])kv.getValue();
         classes = listToString(vec[0]);
         if (vec[1].size() > 0) {
            classes = classes + '@' + listToString(vec[1]);
         }
      }

      return transformed;
   }

   public void addJarToPackages(URL jarurl) {
      this.addJarToPackages(jarurl, (File)null, false);
   }

   public void addJarToPackages(URL jarurl, boolean cache) {
      this.addJarToPackages(jarurl, (File)null, cache);
   }

   public void addJarToPackages(File jarfile) {
      this.addJarToPackages((URL)null, jarfile, false);
   }

   public void addJarToPackages(File jarfile, boolean cache) {
      this.addJarToPackages((URL)null, jarfile, cache);
   }

   private void addJarToPackages(URL jarurl, File jarfile, boolean cache) {
      try {
         boolean caching = this.jarfiles != null;
         URLConnection jarconn = null;
         boolean localfile = true;
         if (jarfile == null) {
            jarconn = jarurl.openConnection();
            if (jarconn.getURL().getProtocol().equals("file")) {
               String jarfilename = jarurl.getFile();
               jarfilename = jarfilename.replace('/', File.separatorChar);
               jarfile = new File(jarfilename);
            } else {
               localfile = false;
            }
         }

         if (localfile && !jarfile.exists()) {
            return;
         }

         Map zipPackages = null;
         long mtime = 0L;
         String jarcanon = null;
         JarXEntry entry = null;
         boolean brandNew = false;
         if (caching) {
            if (localfile) {
               mtime = jarfile.lastModified();
               jarcanon = jarfile.getCanonicalPath();
            } else {
               mtime = jarconn.getLastModified();
               jarcanon = jarurl.toString();
            }

            entry = (JarXEntry)this.jarfiles.get(jarcanon);
            if ((entry == null || !(new File(entry.cachefile)).exists()) && cache) {
               this.comment("processing new jar, '" + jarcanon + "'");
               String jarname;
               if (localfile) {
                  jarname = jarfile.getName();
               } else {
                  jarname = jarurl.getFile();
                  int slash = jarname.lastIndexOf(47);
                  if (slash != -1) {
                     jarname = jarname.substring(slash + 1);
                  }
               }

               jarname = jarname.substring(0, jarname.length() - 4);
               entry = new JarXEntry(jarname);
               this.jarfiles.put(jarcanon, entry);
               brandNew = true;
            }

            if (mtime != 0L && entry != null && entry.mtime == mtime) {
               zipPackages = this.readCacheFile(entry, jarcanon);
            }
         }

         if (zipPackages == null) {
            caching = caching && cache;
            if (caching) {
               this.indexModified = true;
               if (entry.mtime != 0L) {
                  this.comment("processing modified jar, '" + jarcanon + "'");
               }

               entry.mtime = mtime;
            }

            InputStream jarin = null;

            try {
               if (jarconn == null) {
                  jarin = new BufferedInputStream(new FileInputStream(jarfile));
               } else {
                  jarin = jarconn.getInputStream();
               }

               zipPackages = this.getZipPackages((InputStream)jarin);
            } finally {
               if (jarin != null) {
                  ((InputStream)jarin).close();
               }

            }

            if (caching) {
               this.writeCacheFile(entry, jarcanon, zipPackages, brandNew);
            }
         }

         this.addPackages(zipPackages, jarcanon);
      } catch (IOException var19) {
         this.warning("skipping bad jar, '" + (jarfile != null ? jarfile.toString() : jarurl.toString()) + "'");
      }

   }

   protected void addPackages(Map zipPackages, String jarfile) {
      String pkg;
      String classes;
      for(Iterator var3 = zipPackages.entrySet().iterator(); var3.hasNext(); this.makeJavaPackage(pkg, classes, jarfile)) {
         Map.Entry entry = (Map.Entry)var3.next();
         pkg = (String)entry.getKey();
         classes = (String)entry.getValue();
         int idx = classes.indexOf(64);
         if (idx >= 0 && Options.respectJavaAccessibility) {
            classes = classes.substring(0, idx);
         }
      }

   }

   private Map readCacheFile(JarXEntry entry, String jarcanon) {
      String cachefile = entry.cachefile;
      long mtime = entry.mtime;
      this.debug("reading cache, '" + jarcanon + "'");
      DataInputStream istream = null;

      Object var8;
      try {
         istream = this.inOpenCacheFile(cachefile);
         String old_jarcanon = istream.readUTF();
         long old_mtime = istream.readLong();
         Map packs;
         if (old_jarcanon.equals(jarcanon) && old_mtime == mtime) {
            packs = Generic.map();

            try {
               while(true) {
                  String packageName = istream.readUTF();
                  String classes = istream.readUTF();
                  if (packs.containsKey(packageName)) {
                     classes = (String)packs.get(packageName) + classes;
                  }

                  packs.put(packageName, classes);
               }
            } catch (EOFException var23) {
               Map var11 = packs;
               return var11;
            }
         }

         this.comment("invalid cache file: " + cachefile + ", " + jarcanon + ":" + old_jarcanon + ", " + mtime + ":" + old_mtime);
         this.deleteCacheFile(cachefile);
         packs = null;
         return packs;
      } catch (IOException var24) {
         var8 = null;
      } finally {
         if (istream != null) {
            try {
               istream.close();
            } catch (IOException var22) {
            }
         }

      }

      return (Map)var8;
   }

   private void writeCacheFile(JarXEntry entry, String jarcanon, Map zipPackages, boolean brandNew) {
      DataOutputStream ostream = null;

      try {
         ostream = this.outCreateCacheFile(entry, brandNew);
         ostream.writeUTF(jarcanon);
         ostream.writeLong(entry.mtime);
         this.comment("rewriting cachefile for '" + jarcanon + "'");
         Iterator var6 = zipPackages.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry kv = (Map.Entry)var6.next();
            String classes = (String)kv.getValue();
            String[] var9 = splitString(classes, 65535);
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String part = var9[var11];
               ostream.writeUTF((String)kv.getKey());
               ostream.writeUTF(part);
            }
         }
      } catch (IOException var21) {
         this.warning("can't write cache file for '" + jarcanon + "'");
      } finally {
         if (ostream != null) {
            try {
               ostream.close();
            } catch (IOException var20) {
            }
         }

      }

   }

   protected static String[] splitString(String str, int maxLength) {
      if (str == null) {
         return null;
      } else {
         int len = str.length();
         if (len <= maxLength) {
            return new String[]{str};
         } else {
            int chunkCount = (int)Math.ceil((double)((float)len / (float)maxLength));
            String[] chunks = new String[chunkCount];

            for(int i = 0; i < chunkCount; ++i) {
               chunks[i] = str.substring(i * maxLength, Math.min(i * maxLength + maxLength, len));
            }

            return chunks;
         }
      }
   }

   protected void initCache() {
      this.indexModified = false;
      this.jarfiles = Generic.map();
      DataInputStream istream = null;

      try {
         istream = this.inOpenIndex();
         if (istream != null) {
            try {
               while(true) {
                  String jarcanon = istream.readUTF();
                  String cachefile = istream.readUTF();
                  long mtime = istream.readLong();
                  this.jarfiles.put(jarcanon, new JarXEntry(cachefile, mtime));
               }
            } catch (EOFException var16) {
               return;
            }
         }
      } catch (IOException var17) {
         this.warning("invalid index file");
         return;
      } finally {
         if (istream != null) {
            try {
               istream.close();
            } catch (IOException var15) {
            }
         }

      }

   }

   public void saveCache() {
      if (this.jarfiles != null && this.indexModified) {
         this.indexModified = false;
         this.comment("writing modified index file");
         DataOutputStream ostream = null;

         try {
            ostream = this.outOpenIndex();
            Iterator var2 = this.jarfiles.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               String jarcanon = (String)entry.getKey();
               JarXEntry xentry = (JarXEntry)entry.getValue();
               ostream.writeUTF(jarcanon);
               ostream.writeUTF(xentry.cachefile);
               ostream.writeLong(xentry.mtime);
            }
         } catch (IOException var14) {
            this.warning("can't write index file");
         } finally {
            if (ostream != null) {
               try {
                  ostream.close();
               } catch (IOException var13) {
               }
            }

         }

      }
   }

   protected DataInputStream inOpenIndex() throws IOException {
      File indexFile = new File(this.cachedir, "packages.idx");
      if (!indexFile.exists()) {
         return null;
      } else {
         DataInputStream istream = new DataInputStream(new BufferedInputStream(new FileInputStream(indexFile)));
         return istream;
      }
   }

   protected DataOutputStream outOpenIndex() throws IOException {
      File indexFile = new File(this.cachedir, "packages.idx");
      FileOutputStream fos = new FileOutputStream(indexFile);
      FileUtil.setOwnerOnlyPermissions(indexFile);
      return new DataOutputStream(new BufferedOutputStream(fos));
   }

   protected DataInputStream inOpenCacheFile(String cachefile) throws IOException {
      return new DataInputStream(new BufferedInputStream(new FileInputStream(cachefile)));
   }

   protected void deleteCacheFile(String cachefile) {
      (new File(cachefile)).delete();
   }

   protected DataOutputStream outCreateCacheFile(JarXEntry entry, boolean create) throws IOException {
      File cachefile = null;
      if (create) {
         int index = 1;
         String suffix = "";
         String jarname = entry.cachefile;

         while(true) {
            cachefile = new File(this.cachedir, jarname + suffix + ".pkc");
            if (!cachefile.exists()) {
               entry.cachefile = cachefile.getCanonicalPath();
               break;
            }

            suffix = "$" + index;
            ++index;
         }
      } else {
         cachefile = new File(entry.cachefile);
      }

      FileOutputStream fos = new FileOutputStream(cachefile);
      FileUtil.setOwnerOnlyPermissions(cachefile);
      return new DataOutputStream(new BufferedOutputStream(fos));
   }

   protected boolean useCacheDir(File aCachedir1) {
      if (aCachedir1 == null) {
         return false;
      } else {
         try {
            if (!aCachedir1.isDirectory() && !FileUtil.makeDirs(aCachedir1)) {
               this.warning("can't create package cache dir, '" + aCachedir1 + "'");
               return false;
            }
         } catch (AccessControlException var3) {
            this.warning("The java security manager isn't allowing access to the package cache dir, '" + aCachedir1 + "'");
            return false;
         }

         this.cachedir = aCachedir1;
         return true;
      }
   }

   public static class JarXEntry {
      public String cachefile;
      public long mtime;

      public JarXEntry(String cachefile) {
         this.cachefile = cachefile;
      }

      public JarXEntry(String cachefile, long mtime) {
         this.cachefile = cachefile;
         this.mtime = mtime;
      }
   }
}
