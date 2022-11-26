package weblogic.management.provider.internal;

import java.lang.reflect.Method;
import weblogic.common.internal.VersionInfo;

public class BeanInfoHelper {
   public static final Class[] CONSTRUCTORPARAMS;
   public static final String BEAN_INFO_SUFFIX = "BeanInfo";

   public static boolean isVersionCompliant(String sinceVersionString, String obsoleteVersionString, String targetVersionString) {
      if (targetVersionString == null) {
         return true;
      } else {
         VersionInfo targetVersion = new VersionInfo(targetVersionString);
         VersionInfo obsoleteVersion;
         if (sinceVersionString != null) {
            obsoleteVersion = new VersionInfo(sinceVersionString);
            if (obsoleteVersion.laterThan(targetVersion)) {
               return false;
            }
         }

         if (obsoleteVersionString != null) {
            obsoleteVersion = new VersionInfo(obsoleteVersionString);
            return targetVersion.earlierThan(obsoleteVersion);
         } else {
            return true;
         }
      }
   }

   public static String[] stringArray(String s) {
      return new String[]{s};
   }

   public static String[] stringArray(String[] s) {
      return s;
   }

   public static String buildMethodKey(Method m) {
      StringBuffer result = new StringBuffer(m.getName());
      Class[] params = m.getParameterTypes();
      result.append('(');
      int i = 0;

      while(i < params.length) {
         Class param = params[i];
         result.append(param.getName());
         ++i;
         if (i < params.length) {
            result.append(',');
         }
      }

      result.append(')');
      return result.toString();
   }

   public static String encodeEntities(String str) {
      StringBuffer sb = new StringBuffer();
      char[] chars = str.toCharArray();

      for(int i = 0; i < chars.length; ++i) {
         char c = chars[i];
         switch (c) {
            case '"':
               sb.append("&#34;");
               break;
            case '#':
               sb.append("&#35;");
               break;
            case '&':
               sb.append("&#38;");
               break;
            case '\'':
               sb.append("&#39;");
               break;
            case '<':
               sb.append("&#60;");
               break;
            case '>':
               sb.append("&#62;");
               break;
            default:
               sb.append(c);
         }
      }

      return sb.toString().intern();
   }

   static {
      CONSTRUCTORPARAMS = new Class[]{Boolean.TYPE, String.class};
   }
}
