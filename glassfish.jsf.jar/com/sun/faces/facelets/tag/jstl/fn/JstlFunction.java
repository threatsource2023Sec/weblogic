package com.sun.faces.facelets.tag.jstl.fn;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class JstlFunction {
   private JstlFunction() {
   }

   public static boolean contains(String name, String searchString) {
      if (name == null) {
         name = "";
      }

      if (searchString == null) {
         searchString = "";
      }

      return name.contains(searchString);
   }

   public static boolean containsIgnoreCase(String name, String searchString) {
      if (name == null) {
         name = "";
      }

      if (searchString == null) {
         searchString = "";
      }

      return name.toLowerCase().contains(searchString.toLowerCase());
   }

   public static boolean endsWith(String name, String searchString) {
      if (name == null) {
         name = "";
      }

      if (searchString == null) {
         searchString = "";
      }

      return name.endsWith(searchString);
   }

   public static String escapeXml(String value) {
      if (value == null || value.length() == 0) {
         value = "";
      }

      StringBuilder b = new StringBuilder(value.length());
      int i = 0;

      for(int len = value.length(); i < len; ++i) {
         char c = value.charAt(i);
         if (c == '<') {
            b.append("&lt;");
         } else if (c == '>') {
            b.append("&gt;");
         } else if (c == '\'') {
            b.append("&#039;");
         } else if (c == '"') {
            b.append("&#034;");
         } else if (c == '&') {
            b.append("&amp;");
         } else {
            b.append(c);
         }
      }

      return b.toString();
   }

   public static int indexOf(String name, String searchString) {
      if (name == null) {
         name = "";
      }

      if (searchString == null) {
         searchString = "";
      }

      return name.indexOf(searchString);
   }

   public static String join(String[] a, String delim) {
      if (a != null && a.length != 0) {
         boolean skipDelim = false;
         if (delim == null || delim.length() == 0) {
            skipDelim = true;
         }

         StringBuilder sb = new StringBuilder();
         int i = 0;
         int len = a.length;

         for(int delimCount = len - 1; i < len; ++i) {
            sb.append(a[i]);
            if (!skipDelim && i < delimCount) {
               sb.append(delim);
            }
         }

         return sb.toString();
      } else {
         return "";
      }
   }

   public static int length(Object obj) {
      if (obj == null) {
         return 0;
      } else if (obj instanceof Collection) {
         return ((Collection)obj).size();
      } else if (obj.getClass().isArray()) {
         return Array.getLength(obj);
      } else if (obj instanceof String) {
         return ((String)obj).length();
      } else if (obj instanceof Map) {
         return ((Map)obj).size();
      } else {
         int count;
         if (obj instanceof Enumeration) {
            Enumeration e = (Enumeration)obj;

            for(count = 0; e.hasMoreElements(); ++count) {
               e.nextElement();
            }

            return count;
         } else if (!(obj instanceof Iterator)) {
            throw new IllegalArgumentException("Object type not supported: " + obj.getClass().getName());
         } else {
            Iterator i = (Iterator)obj;

            for(count = 0; i.hasNext(); ++count) {
               i.next();
            }

            return count;
         }
      }
   }

   public static String replace(String value, String before, String after) {
      if (value == null) {
         value = "";
      }

      if (before == null) {
         before = "";
      }

      if (before.length() == 0) {
         return value;
      } else if (value.length() == 0) {
         return "";
      } else {
         if (after == null) {
            after = "";
         }

         return value.replaceAll(before, after);
      }
   }

   public static String[] split(String value, String d) {
      if (value == null) {
         value = "";
      }

      if (value.length() == 0) {
         return new String[]{""};
      } else {
         if (d == null) {
            d = "";
         }

         if (d.length() == 0) {
            return new String[]{value};
         } else {
            List tokens = new ArrayList();
            StringTokenizer st = new StringTokenizer(value, d);

            while(st.hasMoreTokens()) {
               tokens.add(st.nextToken());
            }

            return (String[])tokens.toArray(new String[tokens.size()]);
         }
      }
   }

   public static boolean startsWith(String value, String p) {
      if (value == null) {
         value = "";
      }

      if (p == null) {
         p = "";
      }

      return value.startsWith(p);
   }

   public static String substring(String v, int s, int e) {
      if (v == null) {
         v = "";
      }

      if (s >= v.length()) {
         return "";
      } else {
         if (s < 0) {
            s = 0;
         }

         if (e < 0 || e >= v.length()) {
            e = v.length();
         }

         return e < s ? "" : v.substring(s, e);
      }
   }

   public static String substringAfter(String v, String p) {
      if (v == null) {
         v = "";
      }

      if (v.length() == 0) {
         return "";
      } else {
         if (p == null) {
            p = "";
         }

         int i = v.indexOf(p);
         return i == -1 ? "" : v.substring(i + p.length());
      }
   }

   public static String substringBefore(String v, String s) {
      if (v == null) {
         v = "";
      }

      if (v.length() == 0) {
         return "";
      } else {
         if (s == null) {
            s = "";
         }

         int i = v.indexOf(s);
         return i == -1 ? "" : v.substring(0, i);
      }
   }

   public static String toLowerCase(String v) {
      return v != null && v.length() != 0 ? v.toLowerCase() : "";
   }

   public static String toUpperCase(String v) {
      return v != null && v.length() != 0 ? v.toUpperCase() : "";
   }

   public static String trim(String v) {
      return v != null && v.length() != 0 ? v.trim() : "";
   }
}
