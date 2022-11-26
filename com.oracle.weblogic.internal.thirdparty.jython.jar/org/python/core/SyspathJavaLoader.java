package org.python.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import org.python.core.util.RelativeFile;

public class SyspathJavaLoader extends ClassLoader {
   private static final char SLASH_CHAR = '/';

   public SyspathJavaLoader(ClassLoader parent) {
      super(parent);
   }

   private byte[] getBytesFromInputStream(InputStream input, int size) {
      Object var4;
      try {
         byte[] buffer = new byte[size];

         for(int nread = 0; nread < size; nread += input.read(buffer, nread, size - nread)) {
         }

         byte[] var5 = buffer;
         return var5;
      } catch (IOException var15) {
         var4 = null;
      } finally {
         try {
            input.close();
         } catch (IOException var14) {
         }

      }

      return (byte[])var4;
   }

   private byte[] getBytesFromDir(String dir, String name) {
      try {
         File file = this.getFile(dir, name);
         return file == null ? null : this.getBytesFromInputStream(new FileInputStream(file), (int)file.length());
      } catch (FileNotFoundException var4) {
         return null;
      } catch (SecurityException var5) {
         return null;
      }
   }

   private byte[] getBytesFromArchive(SyspathArchive archive, String name) {
      String entryname = name.replace('.', '/') + ".class";
      ZipEntry ze = archive.getEntry(entryname);
      if (ze == null) {
         return null;
      } else {
         try {
            return this.getBytesFromInputStream(archive.getInputStream(ze), (int)ze.getSize());
         } catch (IOException var6) {
            return null;
         }
      }
   }

   protected Package definePackageForClass(String name) {
      int lastDotIndex = name.lastIndexOf(46);
      if (lastDotIndex < 0) {
         return null;
      } else {
         String pkgname = name.substring(0, lastDotIndex);
         Package pkg = this.getPackage(pkgname);
         if (pkg == null) {
            pkg = this.definePackage(pkgname, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (URL)null);
         }

         return pkg;
      }
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      PySystemState sys = Py.getSystemState();
      ClassLoader sysClassLoader = sys.getClassLoader();
      if (sysClassLoader != null) {
         return sysClassLoader.loadClass(name);
      } else {
         PyList path = sys.path;

         for(int i = 0; i < path.__len__(); ++i) {
            PyObject entry = replacePathItem(sys, i, path);
            byte[] buffer;
            if (entry instanceof SyspathArchive) {
               SyspathArchive archive = (SyspathArchive)entry;
               buffer = this.getBytesFromArchive(archive, name);
            } else {
               String dir = Py.fileSystemDecode(entry);
               buffer = this.getBytesFromDir(dir, name);
            }

            if (buffer != null) {
               this.definePackageForClass(name);
               return this.defineClass(name, buffer, 0, buffer.length);
            }
         }

         throw new ClassNotFoundException(name);
      }
   }

   protected URL findResource(String res) {
      PySystemState sys = Py.getSystemState();
      res = deslashResource(res);
      String entryRes = res;
      if (File.separatorChar != '/') {
         res = res.replace('/', File.separatorChar);
         entryRes = entryRes.replace(File.separatorChar, '/');
      }

      PyList path = sys.path;

      for(int i = 0; i < path.__len__(); ++i) {
         PyObject entry = replacePathItem(sys, i, path);
         if (entry instanceof SyspathArchive) {
            SyspathArchive archive = (SyspathArchive)entry;
            ZipEntry ze = archive.getEntry(entryRes);
            if (ze != null) {
               try {
                  return new URL("jar:file:" + archive.asUriCompatibleString() + "!/" + entryRes);
               } catch (MalformedURLException var10) {
                  throw new RuntimeException(var10);
               }
            }
         } else {
            String dir = sys.getPath(Py.fileSystemDecode(entry));

            try {
               File resource = new File(dir, res);
               if (resource.exists()) {
                  return resource.toURI().toURL();
               }
            } catch (MalformedURLException var11) {
               throw new RuntimeException(var11);
            }
         }
      }

      return null;
   }

   protected Enumeration findResources(String res) throws IOException {
      List resources = new ArrayList();
      PySystemState sys = Py.getSystemState();
      res = deslashResource(res);
      String entryRes = res;
      if (File.separatorChar != '/') {
         res = res.replace('/', File.separatorChar);
         entryRes = entryRes.replace(File.separatorChar, '/');
      }

      PyList path = sys.path;

      for(int i = 0; i < path.__len__(); ++i) {
         PyObject entry = replacePathItem(sys, i, path);
         if (entry instanceof SyspathArchive) {
            SyspathArchive archive = (SyspathArchive)entry;
            ZipEntry ze = archive.getEntry(entryRes);
            if (ze != null) {
               try {
                  resources.add(new URL("jar:file:" + archive.asUriCompatibleString() + "!/" + entryRes));
               } catch (MalformedURLException var11) {
                  throw new RuntimeException(var11);
               }
            }
         } else {
            String dir = sys.getPath(Py.fileSystemDecode(entry));

            try {
               File resource = new File(dir, res);
               if (resource.exists()) {
                  resources.add(resource.toURI().toURL());
               }
            } catch (MalformedURLException var12) {
               throw new RuntimeException(var12);
            }
         }
      }

      return Collections.enumeration(resources);
   }

   static PyObject replacePathItem(PySystemState sys, int idx, PyList paths) {
      PyObject path = paths.__getitem__(idx);
      if (path instanceof SyspathArchive) {
         return path;
      } else {
         SyspathArchive path;
         try {
            path = new SyspathArchive(sys.getPath(Py.fileSystemDecode(path)));
         } catch (Exception var5) {
            return path;
         }

         paths.__setitem__(idx, path);
         return path;
      }
   }

   private File getFile(String dir, String name) {
      String accum = "";
      boolean first = true;

      for(StringTokenizer t = new StringTokenizer(name, "."); t.hasMoreTokens(); first = false) {
         String token = t.nextToken();
         if (!first) {
            accum = accum + File.separator;
         }

         accum = accum + token;
      }

      return new RelativeFile(dir, accum + ".class");
   }

   private static String deslashResource(String res) {
      if (!res.isEmpty() && res.charAt(0) == '/') {
         res = res.substring(1);
      }

      return res;
   }
}
