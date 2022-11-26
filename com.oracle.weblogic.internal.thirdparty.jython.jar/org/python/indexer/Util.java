package org.python.indexer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Util {
   private static final String UTF_8 = "UTF-8";
   private static final char SEPCHAR;
   private static final String SEP;
   private static final String INIT_PY = "__init__.py";
   private static final String SEP_INIT_PY;
   private static int gensymCount;

   public static String gensym(String base) {
      ++gensymCount;
      return base + gensymCount;
   }

   public static String getSystemTempDir() {
      String tmp = System.getProperty("java.io.tmpdir");
      return tmp.endsWith(SEP) ? tmp : tmp + SEP;
   }

   public static String getQnameParent(String qname) {
      if (qname != null && qname.length() != 0) {
         int index = qname.lastIndexOf(".");
         return index == -1 ? "" : qname.substring(0, index);
      } else {
         return "";
      }
   }

   public static String moduleQname(String file) {
      boolean initpy = file.endsWith(SEP_INIT_PY);
      if (initpy) {
         file = file.substring(0, file.length() - SEP_INIT_PY.length());
      } else if (file.endsWith(".py")) {
         file = file.substring(0, file.length() - ".py".length());
      }

      Iterator var2 = Indexer.idx.getLoadPath().iterator();

      String root;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         root = (String)var2.next();
      } while(!file.startsWith(root));

      return file.substring(root.length()).replace(SEPCHAR, '.');
   }

   public static String arrayToString(Collection strings) {
      StringBuffer sb = new StringBuffer();
      Iterator var2 = strings.iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         sb.append(s).append("\n");
      }

      return sb.toString();
   }

   public static String arrayToSortedStringSet(Collection strings) {
      Set sorter = new TreeSet();
      sorter.addAll(strings);
      return arrayToString(sorter);
   }

   public static String moduleNameFor(String path) {
      File f = new File(path);
      if (f.isDirectory()) {
         throw new IllegalStateException("failed assertion: " + path);
      } else {
         String fname = f.getName();
         return fname.equals("__init__.py") ? f.getParentFile().getName() : fname.substring(0, fname.lastIndexOf(46));
      }
   }

   public static File joinPath(File dir, String file) {
      return joinPath(dir.getAbsolutePath(), file);
   }

   public static File joinPath(String dir, String file) {
      return dir.endsWith(SEP) ? new File(dir + file) : new File(dir + SEP + file);
   }

   public static void writeFile(String path, String contents) throws Exception {
      PrintWriter out = null;

      try {
         out = new PrintWriter(new BufferedWriter(new FileWriter(path)));
         out.print(contents);
         out.flush();
      } finally {
         if (out != null) {
            out.close();
         }

      }

   }

   public static String readFile(String filename) throws Exception {
      return readFile(new File(filename));
   }

   public static String readFile(File path) throws Exception {
      return new String(getBytesFromFile(path), "UTF-8");
   }

   public static byte[] getBytesFromFile(File file) throws IOException {
      InputStream is = null;

      byte[] var7;
      try {
         is = new FileInputStream(file);
         long length = file.length();
         if (length > 2147483647L) {
            throw new IOException("file too large: " + file);
         }

         byte[] bytes = new byte[(int)length];
         int offset = 0;

         int numRead;
         for(int numRead = false; offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0; offset += numRead) {
         }

         if (offset < bytes.length) {
            throw new IOException("Failed to read whole file " + file);
         }

         var7 = bytes;
      } finally {
         if (is != null) {
            is.close();
         }

      }

      return var7;
   }

   public static String getMD5(File path) throws Exception {
      byte[] bytes = getBytesFromFile(path);
      return getMD5(bytes);
   }

   public static String getMD5(byte[] fileContents) throws Exception {
      MessageDigest algorithm = MessageDigest.getInstance("MD5");
      algorithm.reset();
      algorithm.update(fileContents);
      byte[] messageDigest = algorithm.digest();
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < messageDigest.length; ++i) {
         sb.append(String.format("%02x", 255 & messageDigest[i]));
      }

      return sb.toString();
   }

   public static String canonicalize(String path) {
      File f = new File(path);
      path = f.getAbsolutePath();
      return f.isDirectory() && !path.endsWith(SEP) ? path + SEP : path;
   }

   static boolean isReadableFile(String path) {
      File f = new File(path);
      return f.canRead() && f.isFile();
   }

   static {
      SEPCHAR = File.separatorChar;
      SEP = File.separator;
      SEP_INIT_PY = SEP + "__init__.py";
      gensymCount = -1;
   }
}
