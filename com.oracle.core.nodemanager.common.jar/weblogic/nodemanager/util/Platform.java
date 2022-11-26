package weblogic.nodemanager.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class Platform {
   private static final String[] unixNames = new String[]{"PATH", "LD_LIBRARY_PATH"};
   private static final String[] windowsNames = new String[]{"PATH", "SystemRoot"};
   private static final String windowsClasspathVariableName = "%CLASSPATH%";
   private static final String unixClasspathVariableName = "$CLASSPATH";
   private static final boolean isUnix;
   private static final boolean isWindows;

   public static boolean isUnix() {
      return isUnix;
   }

   public static boolean isWindows() {
      return isWindows;
   }

   public static Map environment() {
      HashMap env = new HashMap();
      String[] names;
      if (isUnix) {
         names = unixNames;
      } else {
         if (!isWindows) {
            throw new UnsupportedOperationException("Unsupported platform type");
         }

         names = windowsNames;
      }

      for(int i = 0; i < names.length; ++i) {
         String s = System.getenv(names[i]);
         if (s != null) {
            env.put(names[i], s);
         }
      }

      return env;
   }

   public static String[] toEnvironmentArray(Map envMap) {
      String[] env = new String[envMap.size()];
      Iterator it = envMap.entrySet().iterator();

      for(int i = 0; i < env.length; ++i) {
         Map.Entry me = (Map.Entry)it.next();
         env[i] = (String)me.getKey() + "=" + (String)me.getValue();
      }

      return env;
   }

   public static ConcurrentFile getConcurrentFile(String path) {
      return (ConcurrentFile)(isUnix ? new ConcurrentUnixFile(path) : new ConcurrentLockedFile(path));
   }

   public static String parseClassPath(String classpath, String systemClasspath) {
      String separatorChar = String.valueOf(File.pathSeparatorChar);
      String classpathVariable = "$CLASSPATH";
      if (isWindows()) {
         classpathVariable = "%CLASSPATH%";
      }

      StringTokenizer strTok = new StringTokenizer(classpath, separatorChar);

      StringBuffer parsedClassPath;
      String value;
      for(parsedClassPath = new StringBuffer(); strTok.hasMoreTokens(); parsedClassPath.append(value)) {
         if (parsedClassPath.length() > 0) {
            parsedClassPath.append(separatorChar);
         }

         String token = strTok.nextToken();
         value = token;
         if (token.equalsIgnoreCase(classpathVariable)) {
            value = systemClasspath;
         }
      }

      return parsedClassPath.toString();
   }

   public static String preparePathForCommand(String path) {
      path = path.trim();
      char escapeChar = isWindows() ? 94 : 92;
      StringBuffer buf = new StringBuffer(path.length());

      for(int i = 0; i < path.length(); ++i) {
         char c;
         if ((c = path.charAt(i)) == '"') {
            buf.append((char)escapeChar);
         }

         buf.append(c);
      }

      path = buf.toString();
      if (isWindows() && path.endsWith(File.separator) && (path.length() <= 1 || path.charAt(path.length() - 2) != File.separatorChar)) {
         path = path.substring(0, path.length() - 1);
      }

      return path;
   }

   static {
      isUnix = File.separatorChar == '/';
      isWindows = File.separatorChar == '\\';
   }
}
