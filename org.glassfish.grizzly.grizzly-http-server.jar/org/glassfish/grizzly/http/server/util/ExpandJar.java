package org.glassfish.grizzly.http.server.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExpandJar {
   public static String expand(URL jar) throws IOException {
      return expand(jar, System.getProperty("java.io.tmpdir"));
   }

   public static String expand(URL jar, String workFolder) throws IOException {
      String pathname = jar.toString().replace('\\', '/');
      if (pathname.endsWith("!/")) {
         pathname = pathname.substring(0, pathname.length() - 2);
      }

      int period = pathname.lastIndexOf(46);
      if (period >= pathname.length() - 4) {
         pathname = pathname.substring(0, period);
      }

      int slash = pathname.lastIndexOf(47);
      if (slash >= 0) {
         pathname = pathname.substring(slash + 1);
      }

      return expand(jar, pathname, workFolder);
   }

   public static String expand(URL jar, String pathname, String dirname) throws IOException {
      File appBase = new File(dirname);
      File docBase = new File(appBase, pathname);
      if (!docBase.mkdir()) {
         throw new IllegalStateException(String.format("Unable to create directory: %s", docBase.getAbsolutePath()));
      } else {
         JarURLConnection juc = (JarURLConnection)jar.openConnection();
         juc.setUseCaches(false);
         JarFile jarFile = null;
         InputStream input = null;

         try {
            jarFile = juc.getJarFile();
            Enumeration jarEntries = jarFile.entries();

            while(jarEntries.hasMoreElements()) {
               JarEntry jarEntry = (JarEntry)jarEntries.nextElement();
               String name = jarEntry.getName();
               int last = name.lastIndexOf(47);
               if (last >= 0) {
                  File parent = new File(docBase, name.substring(0, last));
                  if (!parent.mkdirs()) {
                     throw new IllegalStateException(String.format("Unable to create directory: %s", parent.getAbsolutePath()));
                  }
               }

               if (!name.endsWith("/")) {
                  input = jarFile.getInputStream(jarEntry);
                  expand(input, docBase, name);
                  input.close();
                  input = null;
               }
            }
         } catch (IOException var23) {
            deleteDir(docBase);
            throw var23;
         } finally {
            if (input != null) {
               try {
                  input.close();
               } catch (Throwable var22) {
               }

               input = null;
            }

            if (jarFile != null) {
               try {
                  jarFile.close();
               } catch (Throwable var21) {
               }

               jarFile = null;
            }

         }

         return docBase.getAbsolutePath();
      }
   }

   public static boolean copy(File src, File dest) {
      boolean result = true;
      String[] files;
      if (src.isDirectory()) {
         files = src.list();
         result = dest.mkdir();
      } else {
         files = new String[]{""};
      }

      if (files == null) {
         files = new String[0];
      }

      for(int i = 0; i < files.length && result; ++i) {
         File fileSrc = new File(src, files[i]);
         File fileDest = new File(dest, files[i]);
         if (fileSrc.isDirectory()) {
            result = copy(fileSrc, fileDest);
         } else {
            FileChannel ic = null;
            FileChannel oc = null;

            try {
               ic = (new FileInputStream(fileSrc)).getChannel();
               oc = (new FileOutputStream(fileDest)).getChannel();
               ic.transferTo(0L, ic.size(), oc);
            } catch (IOException var22) {
               result = false;
            } finally {
               if (ic != null) {
                  try {
                     ic.close();
                  } catch (IOException var21) {
                  }
               }

               if (oc != null) {
                  try {
                     oc.close();
                  } catch (IOException var20) {
                  }
               }

            }
         }
      }

      return result;
   }

   public static boolean delete(File dir) {
      return dir.isDirectory() ? deleteDir(dir) : dir.delete();
   }

   public static boolean deleteDir(File dir) {
      String[] files = dir.list();
      if (files == null) {
         files = new String[0];
      }

      for(int i = 0; i < files.length; ++i) {
         File file = new File(dir, files[i]);
         if (file.isDirectory()) {
            deleteDir(file);
         } else if (!file.delete()) {
            throw new IllegalStateException(String.format("Unable to delete file: %s", file.getAbsolutePath()));
         }
      }

      return dir.delete();
   }

   protected static void expand(InputStream input, File docBase, String name) throws IOException {
      File file = new File(docBase, name);
      BufferedOutputStream output = null;

      try {
         output = new BufferedOutputStream(new FileOutputStream(file));
         byte[] buffer = new byte[2048];

         while(true) {
            int n = input.read(buffer);
            if (n <= 0) {
               return;
            }

            output.write(buffer, 0, n);
         }
      } finally {
         if (output != null) {
            try {
               output.close();
            } catch (IOException var12) {
            }
         }

      }
   }
}
