package weblogic.xml.dtdc;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.StringTokenizer;

public class NameMangler {
   public static String file2URL(String s) throws MalformedURLException {
      s = s.replace(File.separatorChar, '/');
      String s1 = System.getProperty("user.dir").replace(File.separatorChar, '/') + "/";
      if (s1.charAt(0) != '/') {
         s1 = "/" + s1;
      }

      URL url1 = new URL("file", "", s1);
      if (s.length() >= 2 && s.charAt(1) == ':') {
         char c = Character.toUpperCase(s.charAt(0));
         if (c >= 'A' && c <= 'Z') {
            s = "file:///" + s;
         }
      }

      return s.equals("") ? url1.toString() : (new URL(url1, s)).toString();
   }

   public static String upcase(String word) {
      return word.substring(0, 1).toUpperCase(Locale.ENGLISH) + (word.length() > 1 ? word.substring(1) : "").replace('-', '_').replace('.', '_');
   }

   public static String getpackage(String name) {
      String packageName = "";

      String part;
      for(StringTokenizer st = new StringTokenizer(name, ":"); st.hasMoreTokens(); packageName = packageName + "." + part) {
         part = st.nextToken();
         if (!st.hasMoreTokens()) {
            break;
         }
      }

      return packageName;
   }

   public static String depackage(String name) {
      StringTokenizer st = new StringTokenizer(name, ":");

      while(st.hasMoreTokens()) {
         String part = st.nextToken();
         if (!st.hasMoreTokens()) {
            name = part;
            break;
         }
      }

      return deunderbar(name);
   }

   public static String deunderbar(String name) {
      StringBuffer sb = new StringBuffer();
      StringTokenizer st = new StringTokenizer(name, "-_.");
      int num = 0;

      while(st.hasMoreTokens()) {
         String token = st.nextToken();
         if (num++ != 0) {
            token = upcase(token);
         }

         if (!st.hasMoreTokens()) {
            name = token;
            break;
         }

         sb.append(token);
      }

      sb.append(name);
      return sb.toString();
   }
}
