package com.sun.faces.facelets.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

public final class Classpath {
   private static final String[] PREFIXES_TO_EXCLUDE = new String[]{"rar:", "sar:"};
   private static final String[] EXTENSIONS_TO_EXCLUDE = new String[]{".rar", ".sar"};

   public static URL[] search(String prefix, String suffix) throws IOException {
      return search(Thread.currentThread().getContextClassLoader(), prefix, suffix, Classpath.SearchAdvice.AllMatches);
   }

   public static URL[] search(ClassLoader cl, String prefix, String suffix) throws IOException {
      return search(cl, prefix, suffix, Classpath.SearchAdvice.AllMatches);
   }

   public static URL[] search(ClassLoader cl, String prefix, String suffix, SearchAdvice advice) throws IOException {
      Enumeration[] e = new Enumeration[]{cl.getResources(prefix), cl.getResources(prefix + "MANIFEST.MF")};
      Set all = new LinkedHashSet();
      int i = 0;

      for(int s = e.length; i < s; ++i) {
         while(e[i].hasMoreElements()) {
            URL url = (URL)e[i].nextElement();
            String str = url.getPath();
            if (-1 != str.indexOf("%2520")) {
               str = url.toExternalForm();
               str = str.replace("%2520", "%20");
               url = new URL(str);
            }

            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            JarFile jarFile;
            if (conn instanceof JarURLConnection) {
               jarFile = ((JarURLConnection)conn).getJarFile();
            } else {
               jarFile = getAlternativeJarFile(url);
            }

            if (jarFile != null) {
               searchJar(cl, all, jarFile, prefix, suffix, advice);
            } else {
               boolean searchDone = searchDir(all, new File(URLDecoder.decode(url.getFile(), "UTF-8")), suffix);
               if (!searchDone) {
                  searchFromURL(all, prefix, suffix, url);
               }
            }
         }
      }

      URL[] urlArray = (URL[])((URL[])all.toArray(new URL[all.size()]));
      return urlArray;
   }

   private static boolean searchDir(Set result, File file, String suffix) throws IOException {
      if (file.exists() && file.isDirectory()) {
         File[] fc = file.listFiles();
         if (fc == null) {
            return false;
         } else {
            for(int i = 0; i < fc.length; ++i) {
               String path = fc[i].getAbsolutePath();
               if (fc[i].isDirectory()) {
                  searchDir(result, fc[i], suffix);
               } else if (path.endsWith(suffix)) {
                  result.add(fc[i].toURL());
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private static void searchFromURL(Set result, String prefix, String suffix, URL url) throws IOException {
      boolean done = false;
      InputStream is = getInputStream(url);
      if (is != null) {
         ZipInputStream zis = is instanceof ZipInputStream ? (ZipInputStream)is : new ZipInputStream(is);
         Throwable var7 = null;

         try {
            ZipEntry entry = zis.getNextEntry();

            for(done = entry != null; entry != null; entry = zis.getNextEntry()) {
               String entryName = entry.getName();
               if (entryName.endsWith(suffix)) {
                  String urlString = url.toExternalForm();
                  result.add(new URL(urlString + entryName));
               }
            }
         } catch (Throwable var19) {
            var7 = var19;
            throw var19;
         } finally {
            if (zis != null) {
               if (var7 != null) {
                  try {
                     zis.close();
                  } catch (Throwable var18) {
                     var7.addSuppressed(var18);
                  }
               } else {
                  zis.close();
               }
            }

         }
      }

      if (!done && prefix.length() > 0) {
         String urlString = url.toExternalForm() + "/";
         String[] split = prefix.split("/");
         prefix = join(split, true);
         String end = join(split, false);
         int p = urlString.lastIndexOf(end);
         if (p < 0) {
            return;
         }

         urlString = urlString.substring(0, p);
         String[] var25 = PREFIXES_TO_EXCLUDE;
         int var11 = var25.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            String cur = var25[var12];
            if (urlString.startsWith(cur)) {
               return;
            }
         }

         url = new URL(urlString);
         searchFromURL(result, prefix, suffix, url);
      }

   }

   private static String join(String[] tokens, boolean excludeLast) {
      StringBuffer join = new StringBuffer();

      for(int i = 0; i < tokens.length - (excludeLast ? 1 : 0); ++i) {
         join.append(tokens[i]).append("/");
      }

      return join.toString();
   }

   private static InputStream getInputStream(URL url) {
      try {
         return url.openStream();
      } catch (Throwable var2) {
         return null;
      }
   }

   private static JarFile getAlternativeJarFile(URL url) throws IOException {
      String urlFile = url.getFile();
      return getAlternativeJarFile(urlFile);
   }

   static JarFile getAlternativeJarFile(String urlFile) throws IOException {
      JarFile result = null;
      int bangSlash = urlFile.indexOf("!/");
      int bang = urlFile.indexOf(33);
      int separatorIndex = -1;
      if (-1 != bangSlash || -1 != bang) {
         if (bangSlash < bang) {
            separatorIndex = bangSlash;
         } else {
            separatorIndex = bang;
         }
      }

      if (separatorIndex == -1) {
         return null;
      } else {
         String jarFileUrl = urlFile.substring(0, separatorIndex);
         if (jarFileUrl.startsWith("file:")) {
            jarFileUrl = jarFileUrl.substring("file:".length());
            jarFileUrl = URLDecoder.decode(jarFileUrl, "UTF-8");
         }

         boolean foundExclusion = false;

         for(int i = 0; i < PREFIXES_TO_EXCLUDE.length; ++i) {
            if (jarFileUrl.startsWith(PREFIXES_TO_EXCLUDE[i]) || jarFileUrl.endsWith(EXTENSIONS_TO_EXCLUDE[i])) {
               foundExclusion = true;
               break;
            }
         }

         if (!foundExclusion) {
            try {
               result = new JarFile(jarFileUrl);
            } catch (ZipException var8) {
               result = null;
            }
         }

         return result;
      }
   }

   private static void searchJar(ClassLoader cl, Set result, JarFile file, String prefix, String suffix, SearchAdvice advice) throws IOException {
      Enumeration e = file.entries();

      while(e.hasMoreElements()) {
         JarEntry entry;
         try {
            entry = (JarEntry)e.nextElement();
         } catch (Throwable var10) {
            continue;
         }

         String name = entry.getName();
         if (name.startsWith(prefix) && name.endsWith(suffix)) {
            Enumeration e2 = cl.getResources(name);

            while(e2.hasMoreElements()) {
               result.add(e2.nextElement());
               if (advice == Classpath.SearchAdvice.FirstMatchOnly) {
                  return;
               }
            }
         }
      }

   }

   public static enum SearchAdvice {
      FirstMatchOnly,
      AllMatches;
   }
}
