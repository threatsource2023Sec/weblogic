package org.python.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import org.python.core.Py;
import org.python.core.PyFile;

public class FileUtil {
   public static PyFile wrap(InputStream is, String mode, int bufsize) {
      return new PyFile(is, "<Java InputStream '" + is + "' as file>", mode, bufsize, true);
   }

   public static PyFile wrap(InputStream is, String mode) {
      return wrap((InputStream)is, mode, -1);
   }

   public static PyFile wrap(InputStream is, int bufsize) {
      return wrap(is, "r", bufsize);
   }

   public static PyFile wrap(InputStream is) {
      return wrap((InputStream)is, -1);
   }

   public static PyFile wrap(OutputStream os, String mode, int bufsize) {
      return new PyFile(os, mode, bufsize);
   }

   public static PyFile wrap(OutputStream os, String mode) {
      return wrap((OutputStream)os, mode, -1);
   }

   public static PyFile wrap(OutputStream os, int bufsize) {
      return wrap(os, "w", bufsize);
   }

   public static PyFile wrap(OutputStream os) {
      return wrap((OutputStream)os, -1);
   }

   public static byte[] readBytes(InputStream in) throws IOException {
      int bufsize = true;
      byte[] buf = new byte[8192];
      ByteArrayOutputStream out = new ByteArrayOutputStream(8192);

      while(true) {
         int count = in.read(buf, 0, 8192);
         if (count < 0) {
            return out.toByteArray();
         }

         out.write(buf, 0, count);
      }
   }

   public static void setOwnerOnlyPermissions(File file) {
      boolean readable = true;
      boolean writable = true;
      boolean executable = false;
      if (file.isDirectory()) {
         executable = true;
      } else {
         String name = file.getName();
         if (name.endsWith(".py") || name.endsWith(".pl") || name.endsWith(".sh") || name.endsWith(".jar") || name.endsWith(".a") || name.endsWith(".so") || name.endsWith(".war") || name.endsWith(".ear") || name.endsWith(".cmd")) {
            executable = true;
         }
      }

      file.setReadable(false, false);
      file.setWritable(false, false);
      file.setExecutable(false, false);
      file.setReadable(readable, true);
      file.setWritable(writable, true);
      file.setExecutable(executable, true);
   }

   public static boolean makeDirs(File dir) {
      if (dir != null && !dir.exists()) {
         LinkedList dirs = new LinkedList();

         File parent;
         String warn;
         try {
            File canFile = dir.getCanonicalFile();
            dirs.add(canFile);
            parent = canFile.getParentFile();
            if (parent != null) {
               parent = parent.getCanonicalFile();
            }
         } catch (IOException var7) {
            warn = "Exception creating package cache dir, '" + dir.getName() + "'.  Exception: " + var7;
            Py.writeDebug("*file-util*", warn);
            return false;
         }

         while(parent != null && !parent.exists()) {
            dirs.add(parent);

            try {
               parent = parent.getParentFile();
               if (parent != null) {
                  parent = parent.getCanonicalFile();
               }
            } catch (IOException var6) {
               warn = "Exception creating package cache dir, '" + dir.getName() + "'.  Exception: " + var6;
               Py.writeDebug("*file-util*", warn);
               return false;
            }
         }

         Iterator iterator = dirs.descendingIterator();

         while(iterator.hasNext()) {
            File descendingDir = (File)iterator.next();
            if (!descendingDir.exists()) {
               if (!descendingDir.mkdirs()) {
                  String warn = "Can't create package dir: '" + descendingDir.getName() + "'";
                  Py.writeDebug("*file-util*", warn);
                  return false;
               }

               setOwnerOnlyPermissions(descendingDir);
            }
         }

         return true;
      } else {
         return true;
      }
   }
}
