package org.stringtemplate.v4.misc;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.Iterator;

public class Misc {
   public static final String newline = System.getProperty("line.separator");

   public static boolean referenceEquals(Object x, Object y) {
      return x == y;
   }

   public static String join(Iterator iter, String separator) {
      StringBuilder buf = new StringBuilder();

      while(iter.hasNext()) {
         buf.append(iter.next());
         if (iter.hasNext()) {
            buf.append(separator);
         }
      }

      return buf.toString();
   }

   public static String strip(String s, int n) {
      return s.substring(n, s.length() - n);
   }

   public static String trimOneStartingNewline(String s) {
      if (s.startsWith("\r\n")) {
         s = s.substring(2);
      } else if (s.startsWith("\n")) {
         s = s.substring(1);
      }

      return s;
   }

   public static String trimOneTrailingNewline(String s) {
      if (s.endsWith("\r\n")) {
         s = s.substring(0, s.length() - 2);
      } else if (s.endsWith("\n")) {
         s = s.substring(0, s.length() - 1);
      }

      return s;
   }

   public static String stripLastPathElement(String f) {
      int slash = f.lastIndexOf(47);
      return slash < 0 ? f : f.substring(0, slash);
   }

   public static String getFileNameNoSuffix(String f) {
      if (f == null) {
         return null;
      } else {
         f = getFileName(f);
         return f.substring(0, f.lastIndexOf(46));
      }
   }

   public static String getFileName(String fullFileName) {
      if (fullFileName == null) {
         return null;
      } else {
         File f = new File(fullFileName);
         return f.getName();
      }
   }

   public static String getParent(String name) {
      if (name == null) {
         return null;
      } else {
         int lastSlash = name.lastIndexOf(47);
         if (lastSlash > 0) {
            return name.substring(0, lastSlash);
         } else {
            return lastSlash == 0 ? "/" : "";
         }
      }
   }

   public static String getPrefix(String name) {
      if (name == null) {
         return "/";
      } else {
         String parent = getParent(name);
         String prefix = parent;
         if (!parent.endsWith("/")) {
            prefix = parent + '/';
         }

         return prefix;
      }
   }

   public static String replaceEscapes(String s) {
      s = s.replaceAll("\n", "\\\\n");
      s = s.replaceAll("\r", "\\\\r");
      s = s.replaceAll("\t", "\\\\t");
      return s;
   }

   public static String replaceEscapedRightAngle(String s) {
      StringBuilder buf = new StringBuilder();
      int i = 0;

      while(true) {
         while(i < s.length()) {
            char c = s.charAt(i);
            if (c == '<' && s.substring(i).startsWith("<\\\\>")) {
               buf.append("<\\\\>");
               i += "<\\\\>".length();
            } else if (c == '>' && s.substring(i).startsWith(">\\>")) {
               buf.append(">>");
               i += ">\\>".length();
            } else if (c == '\\' && s.substring(i).startsWith("\\>>") && !s.substring(i).startsWith("\\>>>")) {
               buf.append(">>");
               i += "\\>>".length();
            } else {
               buf.append(c);
               ++i;
            }
         }

         return buf.toString();
      }
   }

   public static boolean urlExists(URL url) {
      try {
         URLConnection connection = url.openConnection();
         if (connection instanceof JarURLConnection) {
            JarURLConnection jarURLConnection = (JarURLConnection)connection;
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarURLConnection.getJarFileURL()});

            boolean var4;
            try {
               var4 = urlClassLoader.findResource(jarURLConnection.getEntryName()) != null;
            } finally {
               if (urlClassLoader instanceof Closeable) {
                  ((Closeable)urlClassLoader).close();
               }

            }

            return var4;
         } else {
            InputStream is = null;

            try {
               is = url.openStream();
            } finally {
               if (is != null) {
                  is.close();
               }

            }

            return is != null;
         }
      } catch (IOException var15) {
         return false;
      }
   }

   public static Coordinate getLineCharPosition(String s, int index) {
      int line = 1;
      int charPos = 0;

      for(int p = 0; p < index; ++p) {
         if (s.charAt(p) == '\n') {
            ++line;
            charPos = 0;
         } else {
            ++charPos;
         }
      }

      return new Coordinate(line, charPos);
   }
}
