package com.bea.core.repackaged.aspectj.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {
   public static final File DEFAULT_PARENT = new File(".");
   public static final List SOURCE_SUFFIXES = Collections.unmodifiableList(Arrays.asList(".java", ".aj"));
   public static final FileFilter ZIP_FILTER = new FileFilter() {
      public boolean accept(File file) {
         return FileUtil.isZipFile(file);
      }

      public String toString() {
         return "ZIP_FILTER";
      }
   };
   static final int[] INT_RA = new int[0];
   public static final FileFilter ALL = new FileFilter() {
      public boolean accept(File f) {
         return true;
      }
   };
   public static final FileFilter DIRS_AND_WRITABLE_CLASSES = new FileFilter() {
      public boolean accept(File file) {
         return null != file && (file.isDirectory() || file.canWrite() && file.getName().toLowerCase().endsWith(".class"));
      }
   };
   private static final boolean PERMIT_CVS;
   public static final FileFilter aspectjSourceFileFilter;
   static final String FILECHARS = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

   public static boolean isZipFile(File file) {
      try {
         return null != file && new ZipFile(file) != null;
      } catch (IOException var2) {
         return false;
      }
   }

   public static int zipSuffixLength(File file) {
      return null == file ? 0 : zipSuffixLength(file.getPath());
   }

   public static int zipSuffixLength(String path) {
      if (null != path && 4 < path.length()) {
         String test = path.substring(path.length() - 4).toLowerCase();
         if (".zip".equals(test) || ".jar".equals(test)) {
            return 4;
         }
      }

      return 0;
   }

   public static boolean hasSourceSuffix(File file) {
      return null != file && hasSourceSuffix(file.getPath());
   }

   public static boolean hasSourceSuffix(String path) {
      return null != path && 0 != sourceSuffixLength(path);
   }

   public static int sourceSuffixLength(File file) {
      return null == file ? 0 : sourceSuffixLength(file.getPath());
   }

   public static int sourceSuffixLength(String path) {
      if (LangUtil.isEmpty(path)) {
         return 0;
      } else {
         Iterator iter = SOURCE_SUFFIXES.iterator();

         String suffix;
         do {
            if (!iter.hasNext()) {
               return 0;
            }

            suffix = (String)iter.next();
         } while(!path.endsWith(suffix) && !path.toLowerCase().endsWith(suffix));

         return suffix.length();
      }
   }

   public static boolean canReadDir(File dir) {
      return null != dir && dir.canRead() && dir.isDirectory();
   }

   public static boolean canReadFile(File file) {
      return null != file && file.canRead() && file.isFile();
   }

   public static boolean canWriteDir(File dir) {
      return null != dir && dir.canWrite() && dir.isDirectory();
   }

   public static boolean canWriteFile(File file) {
      return null != file && file.canWrite() && file.isFile();
   }

   public static void throwIaxUnlessCanReadDir(File dir, String label) {
      if (!canReadDir(dir)) {
         throw new IllegalArgumentException(label + " not readable dir: " + dir);
      }
   }

   public static void throwIaxUnlessCanWriteFile(File file, String label) {
      if (!canWriteFile(file)) {
         throw new IllegalArgumentException(label + " not writable file: " + file);
      }
   }

   public static void throwIaxUnlessCanWriteDir(File dir, String label) {
      if (!canWriteDir(dir)) {
         throw new IllegalArgumentException(label + " not writable dir: " + dir);
      }
   }

   public static String[] getPaths(File[] files) {
      if (null != files && 0 != files.length) {
         String[] result = new String[files.length];

         for(int i = 0; i < result.length; ++i) {
            if (null != files[i]) {
               result[i] = files[i].getPath();
            }
         }

         return result;
      } else {
         return new String[0];
      }
   }

   public static String[] getPaths(List files) {
      int size = null == files ? 0 : files.size();
      if (0 == size) {
         return new String[0];
      } else {
         String[] result = new String[size];

         for(int i = 0; i < size; ++i) {
            File file = (File)files.get(i);
            if (null != file) {
               result[i] = file.getPath();
            }
         }

         return result;
      }
   }

   public static String fileToClassName(File basedir, File classFile) {
      LangUtil.throwIaxIfNull(classFile, "classFile");
      String classFilePath = normalizedPath(classFile);
      String basePath;
      if (!classFilePath.endsWith(".class")) {
         basePath = classFile + " does not end with .class";
         throw new IllegalArgumentException(basePath);
      } else {
         classFilePath = classFilePath.substring(0, classFilePath.length() - 6);
         if (null != basedir) {
            basePath = normalizedPath(basedir);
            if (!classFilePath.startsWith(basePath)) {
               String m = classFile + " does not start with " + basedir;
               throw new IllegalArgumentException(m);
            }

            classFilePath = classFilePath.substring(basePath.length() + 1);
         } else {
            String[] suffixes = new String[]{"com", "org", "java", "javax"};
            boolean found = false;

            int loc;
            for(loc = 0; !found && loc < suffixes.length; ++loc) {
               int loc = classFilePath.indexOf(suffixes[loc] + "/");
               if (0 == loc || -1 != loc && '/' == classFilePath.charAt(loc - 1)) {
                  classFilePath = classFilePath.substring(loc);
                  found = true;
               }
            }

            if (!found) {
               loc = classFilePath.lastIndexOf("/");
               if (-1 != loc) {
                  classFilePath = classFilePath.substring(loc + 1);
               }
            }
         }

         return classFilePath.replace('/', '.');
      }
   }

   public static String normalizedPath(File file, File basedir) {
      String filePath = normalizedPath(file);
      if (null != basedir) {
         String basePath = normalizedPath(basedir);
         if (filePath.startsWith(basePath)) {
            filePath = filePath.substring(basePath.length());
            if (filePath.startsWith("/")) {
               filePath = filePath.substring(1);
            }
         }
      }

      return filePath;
   }

   public static String flatten(File[] files, String infix) {
      return LangUtil.isEmpty((Object[])files) ? "" : flatten(getPaths(files), infix);
   }

   public static String flatten(String[] paths, String infix) {
      if (null == infix) {
         infix = File.pathSeparator;
      }

      StringBuffer result = new StringBuffer();
      boolean first = true;

      for(int i = 0; i < paths.length; ++i) {
         String path = paths[i];
         if (null != path) {
            if (first) {
               first = false;
            } else {
               result.append(infix);
            }

            result.append(path);
         }
      }

      return result.toString();
   }

   public static String normalizedPath(File file) {
      return null == file ? "" : weakNormalize(file.getAbsolutePath());
   }

   public static String weakNormalize(String path) {
      if (null != path) {
         path = path.replace('\\', '/').trim();
      }

      return path;
   }

   public static File getBestFile(String[] paths) {
      if (null == paths) {
         return null;
      } else {
         File result = null;

         for(int i = 0; null == result && i < paths.length; ++i) {
            String path = paths[i];
            if (null != path) {
               if (path.startsWith("sp:")) {
                  try {
                     path = System.getProperty(path.substring(3));
                  } catch (Throwable var6) {
                     path = null;
                  }

                  if (null == path) {
                     continue;
                  }
               }

               try {
                  File f = new File(path);
                  if (f.exists() && f.canRead()) {
                     result = getBestFile(f);
                  }
               } catch (Throwable var5) {
               }
            }
         }

         return result;
      }
   }

   public static File getBestFile(File file) {
      LangUtil.throwIaxIfNull(file, "file");
      if (file.exists()) {
         try {
            return file.getCanonicalFile();
         } catch (IOException var2) {
            return file.getAbsoluteFile();
         }
      } else {
         return file;
      }
   }

   public static String getBestPath(File file) {
      LangUtil.throwIaxIfNull(file, "file");
      if (file.exists()) {
         try {
            return file.getCanonicalPath();
         } catch (IOException var2) {
            return file.getAbsolutePath();
         }
      } else {
         return file.getPath();
      }
   }

   public static String[] getAbsolutePaths(File[] files) {
      if (null != files && 0 != files.length) {
         String[] result = new String[files.length];

         for(int i = 0; i < result.length; ++i) {
            if (null != files[i]) {
               result[i] = files[i].getAbsolutePath();
            }
         }

         return result;
      } else {
         return new String[0];
      }
   }

   public static int deleteContents(File dir) {
      return deleteContents(dir, ALL);
   }

   public static int deleteContents(File dir, FileFilter filter) {
      return deleteContents(dir, filter, true);
   }

   public static int deleteContents(File dir, FileFilter filter, boolean deleteEmptyDirs) {
      if (null == dir) {
         throw new IllegalArgumentException("null dir");
      } else if (dir.exists() && dir.canWrite()) {
         if (!dir.isDirectory()) {
            dir.delete();
            return 1;
         } else {
            String[] fromFiles = dir.list();
            if (fromFiles == null) {
               return 0;
            } else {
               int result = 0;

               for(int i = 0; i < fromFiles.length; ++i) {
                  String string = fromFiles[i];
                  File file = new File(dir, string);
                  if (null == filter || filter.accept(file)) {
                     if (file.isDirectory()) {
                        result += deleteContents(file, filter, deleteEmptyDirs);
                        String[] fileContent = file.list();
                        if (deleteEmptyDirs && fileContent != null && 0 == fileContent.length) {
                           file.delete();
                        }
                     } else {
                        file.delete();
                        ++result;
                     }
                  }
               }

               return result;
            }
         }
      } else {
         return 0;
      }
   }

   public static int copyDir(File fromDir, File toDir) throws IOException {
      return copyDir(fromDir, toDir, (String)null, (String)null);
   }

   public static int copyDir(File fromDir, File toDir, String fromSuffix, String toSuffix) throws IOException {
      return copyDir(fromDir, toDir, fromSuffix, toSuffix, (FileFilter)null);
   }

   public static int copyDir(File fromDir, File toDir, final String fromSuffix, String toSuffix, FileFilter delegate) throws IOException {
      if (null != fromDir && fromDir.canRead()) {
         boolean haveSuffix = null != fromSuffix && 0 < fromSuffix.length();
         int slen = !haveSuffix ? 0 : fromSuffix.length();
         if (!toDir.exists()) {
            toDir.mkdirs();
         }

         String[] fromFiles;
         if (!haveSuffix) {
            fromFiles = fromDir.list();
         } else {
            FilenameFilter filter = new FilenameFilter() {
               public boolean accept(File dir, String name) {
                  return (new File(dir, name)).isDirectory() || name.endsWith(fromSuffix);
               }
            };
            fromFiles = fromDir.list(filter);
         }

         int result = 0;
         int MAX = null == fromFiles ? 0 : fromFiles.length;

         for(int i = 0; i < MAX; ++i) {
            String filename = fromFiles[i];
            File fromFile = new File(fromDir, filename);
            if (fromFile.canRead()) {
               if (fromFile.isDirectory()) {
                  result += copyDir(fromFile, new File(toDir, filename), fromSuffix, toSuffix, delegate);
               } else if (fromFile.isFile()) {
                  if (haveSuffix) {
                     filename = filename.substring(0, filename.length() - slen);
                  }

                  if (null != toSuffix) {
                     filename = filename + toSuffix;
                  }

                  File targetFile = new File(toDir, filename);
                  if (null == delegate || delegate.accept(targetFile)) {
                     copyFile(fromFile, targetFile);
                  }

                  ++result;
               }
            }
         }

         return result;
      } else {
         return 0;
      }
   }

   public static String[] listFiles(File srcDir) {
      ArrayList result = new ArrayList();
      if (null != srcDir && srcDir.canRead()) {
         listFiles(srcDir, (String)null, (ArrayList)result);
      }

      return (String[])result.toArray(new String[0]);
   }

   public static File[] listFiles(File srcDir, FileFilter fileFilter) {
      ArrayList result = new ArrayList();
      if (null != srcDir && srcDir.canRead()) {
         listFiles(srcDir, result, fileFilter);
      }

      return (File[])result.toArray(new File[result.size()]);
   }

   public static List listClassFiles(File dir) {
      ArrayList result = new ArrayList();
      if (null != dir && dir.canRead()) {
         listClassFiles(dir, result);
      }

      return result;
   }

   public static File[] getBaseDirFiles(File basedir, String[] paths) {
      return getBaseDirFiles(basedir, paths, (String[])null);
   }

   public static File[] getBaseDirFiles(File basedir, String[] paths, String[] suffixes) {
      LangUtil.throwIaxIfNull(basedir, "basedir");
      LangUtil.throwIaxIfNull(paths, "paths");
      File[] result = null;
      if (!LangUtil.isEmpty((Object[])suffixes)) {
         ArrayList list = new ArrayList();

         for(int i = 0; i < paths.length; ++i) {
            String path = paths[i];

            for(int j = 0; j < suffixes.length; ++j) {
               if (path.endsWith(suffixes[j])) {
                  list.add(new File(basedir, paths[i]));
                  break;
               }
            }
         }

         result = (File[])list.toArray(new File[0]);
      } else {
         result = new File[paths.length];

         for(int i = 0; i < result.length; ++i) {
            result[i] = newFile(basedir, paths[i]);
         }
      }

      return result;
   }

   private static File newFile(File dir, String path) {
      if (".".equals(path)) {
         return dir;
      } else if ("..".equals(path)) {
         File parentDir = dir.getParentFile();
         return null != parentDir ? parentDir : new File(dir, "..");
      } else {
         return new File(dir, path);
      }
   }

   public static File[] copyFiles(File srcDir, String[] relativePaths, File destDir) throws IllegalArgumentException, IOException {
      String[] paths = relativePaths;
      throwIaxUnlessCanReadDir(srcDir, "srcDir");
      throwIaxUnlessCanWriteDir(destDir, "destDir");
      LangUtil.throwIaxIfNull(relativePaths, "relativePaths");
      File[] result = new File[relativePaths.length];

      for(int i = 0; i < paths.length; ++i) {
         String path = paths[i];
         LangUtil.throwIaxIfNull(path, "relativePaths-entry");
         File src = newFile(srcDir, paths[i]);
         File dest = newFile(destDir, path);
         File destParent = dest.getParentFile();
         if (!destParent.exists()) {
            destParent.mkdirs();
         }

         LangUtil.throwIaxIfFalse(canWriteDir(destParent), "dest-entry-parent");
         copyFile(src, dest);
         result[i] = dest;
      }

      return result;
   }

   public static void copyFile(File fromFile, File toFile) throws IOException {
      LangUtil.throwIaxIfNull(fromFile, "fromFile");
      LangUtil.throwIaxIfNull(toFile, "toFile");
      LangUtil.throwIaxIfFalse(!toFile.equals(fromFile), "same file");
      if (toFile.isDirectory()) {
         throwIaxUnlessCanWriteDir(toFile, "toFile");
         if (fromFile.isFile()) {
            File targFile = new File(toFile, fromFile.getName());
            copyValidFiles(fromFile, targFile);
         } else if (fromFile.isDirectory()) {
            copyDir(fromFile, toFile);
         } else {
            LangUtil.throwIaxIfFalse(false, "not dir or file: " + fromFile);
         }
      } else if (toFile.isFile()) {
         if (fromFile.isDirectory()) {
            LangUtil.throwIaxIfFalse(false, "can't copy to file dir: " + fromFile);
         }

         copyValidFiles(fromFile, toFile);
      } else {
         ensureParentWritable(toFile);
         if (fromFile.isFile()) {
            copyValidFiles(fromFile, toFile);
         } else if (fromFile.isDirectory()) {
            toFile.mkdirs();
            throwIaxUnlessCanWriteDir(toFile, "toFile");
            copyDir(fromFile, toFile);
         } else {
            LangUtil.throwIaxIfFalse(false, "not dir or file: " + fromFile);
         }
      }

   }

   public static File ensureParentWritable(File path) {
      LangUtil.throwIaxIfNull(path, "path");
      File pathParent = path.getParentFile();
      if (null == pathParent) {
         pathParent = DEFAULT_PARENT;
      }

      if (!pathParent.canWrite()) {
         pathParent.mkdirs();
      }

      throwIaxUnlessCanWriteDir(pathParent, "pathParent");
      return pathParent;
   }

   public static void copyValidFiles(File fromFile, File toFile) throws IOException {
      FileInputStream in = null;
      FileOutputStream out = null;

      try {
         in = new FileInputStream(fromFile);
         out = new FileOutputStream(toFile);
         copyStream((InputStream)in, (OutputStream)out);
      } finally {
         if (out != null) {
            out.close();
         }

         if (in != null) {
            in.close();
         }

      }

   }

   public static void copyStream(DataInputStream in, PrintStream out) throws IOException {
      LangUtil.throwIaxIfNull(in, "in");
      LangUtil.throwIaxIfNull(in, "out");

      String s;
      while(null != (s = in.readLine())) {
         out.println(s);
      }

   }

   public static void copyStream(InputStream in, OutputStream out) throws IOException {
      int MAX = true;
      byte[] buf = new byte[4096];

      for(int bytesRead = in.read(buf, 0, 4096); bytesRead != -1; bytesRead = in.read(buf, 0, 4096)) {
         out.write(buf, 0, bytesRead);
      }

   }

   public static void copyStream(Reader in, Writer out) throws IOException {
      int MAX = true;
      char[] buf = new char[4096];

      for(int bytesRead = in.read(buf, 0, 4096); bytesRead != -1; bytesRead = in.read(buf, 0, 4096)) {
         out.write(buf, 0, bytesRead);
      }

   }

   public static File makeNewChildDir(File parent, String child) {
      if (null != parent && parent.canWrite() && parent.isDirectory()) {
         if (null == child) {
            child = "makeNewChildDir";
         } else if (!isValidFileName(child)) {
            throw new IllegalArgumentException("bad child: " + child);
         }

         File result = new File(parent, child);
         int safety = 1000;
         String suffix = randomFileString();

         while(true) {
            --safety;
            if (0 >= safety || !result.exists()) {
               if (result.exists()) {
                  System.err.println("exhausted files for child dir in " + parent);
                  return null;
               } else {
                  return result.mkdirs() && result.exists() ? result : null;
               }
            }

            result = new File(parent, child + suffix);
            suffix = randomFileString();
         }
      } else {
         throw new IllegalArgumentException("bad parent: " + parent);
      }
   }

   public static File getTempDir(String name) {
      if (null == name) {
         name = "FileUtil_getTempDir";
      } else if (!isValidFileName(name)) {
         throw new IllegalArgumentException(" invalid: " + name);
      }

      File result = null;
      File tempFile = null;

      try {
         tempFile = File.createTempFile("ignoreMe", ".txt");
         File tempParent = tempFile.getParentFile();
         result = makeNewChildDir(tempParent, name);
      } catch (IOException var7) {
         result = makeNewChildDir(new File("."), name);
      } finally {
         if (null != tempFile) {
            tempFile.delete();
         }

      }

      return result;
   }

   public static URL[] getFileURLs(File[] files) {
      if (null != files && 0 != files.length) {
         URL[] result = new URL[files.length];

         for(int i = 0; i < result.length; ++i) {
            result[i] = getFileURL(files[i]);
         }

         return result;
      } else {
         return new URL[0];
      }
   }

   public static URL getFileURL(File file) {
      LangUtil.throwIaxIfNull(file, "file");
      URL result = null;

      try {
         result = file.toURL();
         if (null != result) {
            return result;
         }

         String url = "file:" + file.getAbsolutePath().replace('\\', '/');
         result = new URL(url + (file.isDirectory() ? "/" : ""));
      } catch (MalformedURLException var4) {
         String m = "Util.makeURL(\"" + file.getPath() + "\" MUE " + var4.getMessage();
         System.err.println(m);
      }

      return result;
   }

   public static String writeAsString(File file, String contents) {
      LangUtil.throwIaxIfNull(file, "file");
      if (null == contents) {
         contents = "";
      }

      Writer out = null;

      String var4;
      try {
         File parentDir = file.getParentFile();
         if (parentDir.exists() || parentDir.mkdirs()) {
            Reader in = new StringReader(contents);
            out = new FileWriter(file);
            copyStream((Reader)in, (Writer)out);
            Object var5 = null;
            return (String)var5;
         }

         var4 = "unable to make parent dir for " + file;
      } catch (IOException var16) {
         var4 = LangUtil.unqualifiedClassName((Object)var16) + " writing " + file + ": " + var16.getMessage();
         return var4;
      } finally {
         if (null != out) {
            try {
               out.close();
            } catch (IOException var15) {
            }
         }

      }

      return var4;
   }

   public static boolean[] readBooleanArray(DataInputStream s) throws IOException {
      int len = s.readInt();
      boolean[] ret = new boolean[len];

      for(int i = 0; i < len; ++i) {
         ret[i] = s.readBoolean();
      }

      return ret;
   }

   public static void writeBooleanArray(boolean[] a, DataOutputStream s) throws IOException {
      int len = a.length;
      s.writeInt(len);

      for(int i = 0; i < len; ++i) {
         s.writeBoolean(a[i]);
      }

   }

   public static int[] readIntArray(DataInputStream s) throws IOException {
      int len = s.readInt();
      int[] ret = new int[len];

      for(int i = 0; i < len; ++i) {
         ret[i] = s.readInt();
      }

      return ret;
   }

   public static void writeIntArray(int[] a, DataOutputStream s) throws IOException {
      int len = a.length;
      s.writeInt(len);

      for(int i = 0; i < len; ++i) {
         s.writeInt(a[i]);
      }

   }

   public static String[] readStringArray(DataInputStream s) throws IOException {
      int len = s.readInt();
      String[] ret = new String[len];

      for(int i = 0; i < len; ++i) {
         ret[i] = s.readUTF();
      }

      return ret;
   }

   public static void writeStringArray(String[] a, DataOutputStream s) throws IOException {
      if (a == null) {
         s.writeInt(0);
      } else {
         int len = a.length;
         s.writeInt(len);

         for(int i = 0; i < len; ++i) {
            s.writeUTF(a[i]);
         }

      }
   }

   public static String readAsString(File file) throws IOException {
      BufferedReader r = new BufferedReader(new FileReader(file));
      StringBuffer b = new StringBuffer();

      while(true) {
         int ch = r.read();
         if (ch == -1) {
            r.close();
            return b.toString();
         }

         b.append((char)ch);
      }
   }

   public static byte[] readAsByteArray(File file) throws IOException {
      FileInputStream in = new FileInputStream(file);
      byte[] ret = readAsByteArray((InputStream)in);
      in.close();
      return ret;
   }

   public static byte[] readAsByteArray(InputStream inStream) throws IOException {
      int size = 1024;
      byte[] ba = new byte[size];
      int readSoFar = 0;

      while(true) {
         int nRead = inStream.read(ba, readSoFar, size - readSoFar);
         if (nRead == -1) {
            byte[] newBa = new byte[readSoFar];
            System.arraycopy(ba, 0, newBa, 0, readSoFar);
            return newBa;
         }

         readSoFar += nRead;
         if (readSoFar == size) {
            int newSize = size * 2;
            byte[] newBa = new byte[newSize];
            System.arraycopy(ba, 0, newBa, 0, size);
            ba = newBa;
            size = newSize;
         }
      }
   }

   static String randomFileString() {
      double FILECHARS_length = (double)"abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length();
      int LEN = true;
      char[] result = new char[6];
      int index = (int)(Math.random() * 6.0);

      for(int i = 0; i < 6; ++i) {
         if (index >= 6) {
            index = 0;
         }

         result[index++] = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt((int)(Math.random() * FILECHARS_length));
      }

      return new String(result);
   }

   public static InputStream getStreamFromZip(String zipFile, String name) {
      try {
         ZipFile zf = new ZipFile(zipFile);

         try {
            ZipEntry entry = zf.getEntry(name);
            InputStream var4 = zf.getInputStream(entry);
            return var4;
         } finally {
            ;
         }
      } catch (IOException var9) {
         return null;
      }
   }

   public static List lineSeek(String sought, List sources, boolean listAll, PrintStream errorSink) {
      if (!LangUtil.isEmpty(sought) && !LangUtil.isEmpty((Collection)sources)) {
         ArrayList result = new ArrayList();
         Iterator iter = sources.iterator();

         while(iter.hasNext()) {
            String path = (String)iter.next();
            String error = lineSeek(sought, path, listAll, result);
            if (null != error && null != errorSink) {
               errorSink.println(error);
            }
         }

         return result;
      } else {
         return Collections.emptyList();
      }
   }

   public static String lineSeek(String sought, String sourcePath, boolean listAll, ArrayList sink) {
      if (!LangUtil.isEmpty(sought) && !LangUtil.isEmpty(sourcePath)) {
         if (LangUtil.isEmpty(sourcePath)) {
            return "no sourcePath";
         } else {
            File file = new File(sourcePath);
            if (file.canRead() && file.isFile()) {
               int lineNum = 0;
               FileReader fin = null;

               String line;
               try {
                  fin = new FileReader(file);
                  BufferedReader reader = new BufferedReader(fin);

                  while(null != (line = reader.readLine())) {
                     ++lineNum;
                     int loc = line.indexOf(sought);
                     if (-1 != loc) {
                        sink.add(sourcePath + ":" + lineNum + ":" + loc);
                        if (!listAll) {
                           return null;
                        }
                     }
                  }

                  return null;
               } catch (IOException var18) {
                  line = LangUtil.unqualifiedClassName((Object)var18) + " reading " + sourcePath + ":" + lineNum;
               } finally {
                  try {
                     if (null != fin) {
                        fin.close();
                     }
                  } catch (IOException var17) {
                  }

               }

               return line;
            } else {
               return "sourcePath not a readable file";
            }
         }
      } else {
         return "nothing sought";
      }
   }

   public static BufferedOutputStream makeOutputStream(File file) throws FileNotFoundException {
      File parent = file.getParentFile();
      if (parent != null) {
         parent.mkdirs();
      }

      return new BufferedOutputStream(new FileOutputStream(file));
   }

   public static boolean sleepPastFinalModifiedTime(File[] files) {
      if (null != files && 0 != files.length) {
         long delayUntil = System.currentTimeMillis();

         for(int i = 0; i < files.length; ++i) {
            File file = files[i];
            if (null != file && file.exists()) {
               long nextModTime = file.lastModified();
               if (nextModTime > delayUntil) {
                  delayUntil = nextModTime;
               }
            }
         }

         return LangUtil.sleepUntil(++delayUntil);
      } else {
         return true;
      }
   }

   private static void listClassFiles(File baseDir, ArrayList result) {
      File[] files = baseDir.listFiles();

      for(int i = 0; i < files.length; ++i) {
         File f = files[i];
         if (f.isDirectory()) {
            listClassFiles(f, result);
         } else if (f.getName().endsWith(".class")) {
            result.add(f);
         }
      }

   }

   private static void listFiles(File baseDir, ArrayList result, FileFilter filter) {
      File[] files = baseDir.listFiles();
      boolean skipCVS = !PERMIT_CVS && filter == aspectjSourceFileFilter;

      for(int i = 0; i < files.length; ++i) {
         File f = files[i];
         if (f.isDirectory()) {
            if (skipCVS) {
               String name = f.getName().toLowerCase();
               if ("cvs".equals(name) || "sccs".equals(name)) {
                  continue;
               }
            }

            listFiles(f, result, filter);
         } else if (filter.accept(f)) {
            result.add(f);
         }
      }

   }

   private static boolean isValidFileName(String input) {
      return null != input && -1 == input.indexOf(File.pathSeparator);
   }

   private static void listFiles(File baseDir, String dir, ArrayList result) {
      String dirPrefix = null == dir ? "" : dir + "/";
      File dirFile = null == dir ? baseDir : new File(baseDir.getPath() + "/" + dir);
      String[] files = dirFile.list();

      for(int i = 0; i < files.length; ++i) {
         File f = new File(dirFile, files[i]);
         String path = dirPrefix + files[i];
         if (f.isDirectory()) {
            listFiles(baseDir, path, result);
         } else {
            result.add(path);
         }
      }

   }

   private FileUtil() {
   }

   public static List makeClasspath(URL[] urls) {
      List ret = new LinkedList();
      if (urls != null) {
         for(int i = 0; i < urls.length; ++i) {
            ret.add(toPathString(urls[i]));
         }
      }

      return ret;
   }

   private static String toPathString(URL url) {
      try {
         return url.toURI().getPath();
      } catch (URISyntaxException var2) {
         System.err.println("Warning!! Malformed URL may cause problems: " + url);
         return url.getPath();
      }
   }

   static {
      String name = FileUtil.class.getName() + ".PERMIT_CVS";
      PERMIT_CVS = LangUtil.getBoolean(name, false);
      aspectjSourceFileFilter = new FileFilter() {
         public boolean accept(File pathname) {
            String name = pathname.getName().toLowerCase();
            return name.endsWith(".java") || name.endsWith(".aj");
         }
      };
   }

   public static class Pipe implements Runnable {
      private final InputStream in;
      private final OutputStream out;
      private final long sleep;
      private ByteArrayOutputStream snoop;
      private long totalWritten;
      private Throwable thrown;
      private boolean halt;
      private final boolean closeInput;
      private final boolean closeOutput;
      private boolean finishStream;
      private boolean done;

      Pipe(InputStream in, OutputStream out) {
         this(in, out, 100L, false, false);
      }

      Pipe(InputStream in, OutputStream out, long sleep, boolean closeInput, boolean closeOutput) {
         LangUtil.throwIaxIfNull(in, "in");
         LangUtil.throwIaxIfNull(out, "out");
         this.in = in;
         this.out = out;
         this.closeInput = closeInput;
         this.closeOutput = closeOutput;
         this.sleep = Math.min(0L, Math.max(60000L, sleep));
      }

      public void setSnoop(ByteArrayOutputStream snoop) {
         this.snoop = snoop;
      }

      public void run() {
         this.totalWritten = 0L;
         if (!this.halt) {
            try {
               int MAX = true;
               byte[] buf = new byte[4096];

               for(int count = this.in.read(buf, 0, 4096); this.halt && this.finishStream && 0 < count || !this.halt && -1 != count; count = this.in.read(buf, 0, 4096)) {
                  this.out.write(buf, 0, count);
                  ByteArrayOutputStream mySnoop = this.snoop;
                  if (null != mySnoop) {
                     mySnoop.write(buf, 0, count);
                  }

                  this.totalWritten += (long)count;
                  if (this.halt && !this.finishStream) {
                     break;
                  }

                  if (!this.halt && 0L < this.sleep) {
                     Thread.sleep(this.sleep);
                  }

                  if (this.halt && !this.finishStream) {
                     break;
                  }
               }
            } catch (Throwable var17) {
               this.thrown = var17;
            } finally {
               this.halt = true;
               if (this.closeInput) {
                  try {
                     this.in.close();
                  } catch (IOException var16) {
                  }
               }

               if (this.closeOutput) {
                  try {
                     this.out.close();
                  } catch (IOException var15) {
                  }
               }

               this.done = true;
               this.completing(this.totalWritten, this.thrown);
            }

         }
      }

      public boolean halt(boolean wait, boolean finishStream) {
         if (!this.halt) {
            this.halt = true;
         }

         if (wait) {
            while(!this.done) {
               synchronized(this) {
                  this.notifyAll();
               }

               if (!this.done) {
                  try {
                     Thread.sleep(5L);
                  } catch (InterruptedException var6) {
                     break;
                  }
               }
            }
         }

         return this.halt;
      }

      public long totalWritten() {
         return this.totalWritten;
      }

      public Throwable getThrown() {
         return this.thrown;
      }

      protected void completing(long totalWritten, Throwable thrown) {
      }
   }
}
