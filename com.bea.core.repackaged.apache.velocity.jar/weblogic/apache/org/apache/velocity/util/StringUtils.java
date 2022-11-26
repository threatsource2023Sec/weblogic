package weblogic.apache.org.apache.velocity.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class StringUtils {
   private static final String EOL = System.getProperty("line.separator");
   private static final int EOL_LENGTH;

   public String concat(List list) {
      StringBuffer sb = new StringBuffer();
      int size = list.size();

      for(int i = 0; i < size; ++i) {
         sb.append(list.get(i).toString());
      }

      return sb.toString();
   }

   public static String getPackageAsPath(String pckge) {
      return pckge.replace('.', File.separator.charAt(0)) + File.separator;
   }

   /** @deprecated */
   public static String removeUnderScores(String data) {
      String temp = null;
      StringBuffer out = new StringBuffer();
      StringTokenizer st = new StringTokenizer(data, "_");

      while(st.hasMoreTokens()) {
         String element = (String)st.nextElement();
         out.append(firstLetterCaps(element));
      }

      return out.toString();
   }

   public static String removeAndHump(String data) {
      return removeAndHump(data, "_");
   }

   public static String removeAndHump(String data, String replaceThis) {
      String temp = null;
      StringBuffer out = new StringBuffer();
      StringTokenizer st = new StringTokenizer(data, replaceThis);

      while(st.hasMoreTokens()) {
         String element = (String)st.nextElement();
         out.append(capitalizeFirstLetter(element));
      }

      return out.toString();
   }

   public static String firstLetterCaps(String data) {
      String firstLetter = data.substring(0, 1).toUpperCase();
      String restLetters = data.substring(1).toLowerCase();
      return firstLetter + restLetters;
   }

   public static String capitalizeFirstLetter(String data) {
      String firstLetter = data.substring(0, 1).toUpperCase();
      String restLetters = data.substring(1);
      return firstLetter + restLetters;
   }

   public static String[] split(String line, String delim) {
      List list = new ArrayList();
      StringTokenizer t = new StringTokenizer(line, delim);

      while(t.hasMoreTokens()) {
         list.add(t.nextToken());
      }

      return (String[])list.toArray(new String[list.size()]);
   }

   public static String chop(String s, int i) {
      return chop(s, i, EOL);
   }

   public static String chop(String s, int i, String eol) {
      if (i != 0 && s != null && eol != null) {
         int length = s.length();
         if (eol.length() == 2 && s.endsWith(eol)) {
            length -= 2;
            --i;
         }

         if (i > 0) {
            length -= i;
         }

         if (length < 0) {
            length = 0;
         }

         return s.substring(0, length);
      } else {
         return s;
      }
   }

   public static StringBuffer stringSubstitution(String argStr, Hashtable vars) {
      return stringSubstitution(argStr, (Map)vars);
   }

   public static StringBuffer stringSubstitution(String argStr, Map vars) {
      StringBuffer argBuf = new StringBuffer();
      int cIdx = 0;

      while(true) {
         while(cIdx < argStr.length()) {
            char ch = argStr.charAt(cIdx);
            switch (ch) {
               case '$':
                  StringBuffer nameBuf = new StringBuffer();
                  ++cIdx;

                  while(cIdx < argStr.length()) {
                     ch = argStr.charAt(cIdx);
                     if (ch != '_' && !Character.isLetterOrDigit(ch)) {
                        break;
                     }

                     nameBuf.append(ch);
                     ++cIdx;
                  }

                  if (nameBuf.length() > 0) {
                     String value = (String)vars.get(nameBuf.toString());
                     if (value != null) {
                        argBuf.append(value);
                     }
                  }
                  break;
               default:
                  argBuf.append(ch);
                  ++cIdx;
            }
         }

         return argBuf;
      }
   }

   public static String fileContentsToString(String file) {
      String contents = "";
      File f = new File(file);
      if (f.exists()) {
         try {
            FileReader fr = new FileReader(f);
            char[] template = new char[(int)f.length()];
            fr.read(template);
            contents = new String(template);
         } catch (Exception var5) {
            System.out.println(var5);
            var5.printStackTrace();
         }
      }

      return contents;
   }

   public static String collapseNewlines(String argStr) {
      char last = argStr.charAt(0);
      StringBuffer argBuf = new StringBuffer();

      for(int cIdx = 0; cIdx < argStr.length(); ++cIdx) {
         char ch = argStr.charAt(cIdx);
         if (ch != '\n' || last != '\n') {
            argBuf.append(ch);
            last = ch;
         }
      }

      return argBuf.toString();
   }

   public static String collapseSpaces(String argStr) {
      char last = argStr.charAt(0);
      StringBuffer argBuf = new StringBuffer();

      for(int cIdx = 0; cIdx < argStr.length(); ++cIdx) {
         char ch = argStr.charAt(cIdx);
         if (ch != ' ' || last != ' ') {
            argBuf.append(ch);
            last = ch;
         }
      }

      return argBuf.toString();
   }

   public static final String sub(String line, String oldString, String newString) {
      int i = 0;
      if ((i = line.indexOf(oldString, i)) < 0) {
         return line;
      } else {
         char[] line2 = line.toCharArray();
         char[] newString2 = newString.toCharArray();
         int oLength = oldString.length();
         StringBuffer buf = new StringBuffer(line2.length);
         buf.append(line2, 0, i).append(newString2);
         i += oLength;

         int j;
         for(j = i; (i = line.indexOf(oldString, i)) > 0; j = i) {
            buf.append(line2, j, i - j).append(newString2);
            i += oLength;
         }

         buf.append(line2, j, line2.length - j);
         return buf.toString();
      }
   }

   public static final String stackTrace(Throwable e) {
      String foo = null;

      try {
         ByteArrayOutputStream ostr = new ByteArrayOutputStream();
         e.printStackTrace(new PrintWriter(ostr, true));
         foo = ostr.toString();
      } catch (Exception var3) {
      }

      return foo;
   }

   public static final String normalizePath(String path) {
      String normalized = path;
      if (path.indexOf(92) >= 0) {
         normalized = path.replace('\\', '/');
      }

      if (!normalized.startsWith("/")) {
         normalized = "/" + normalized;
      }

      while(true) {
         int index = normalized.indexOf("//");
         if (index < 0) {
            while(true) {
               index = normalized.indexOf("%20");
               if (index < 0) {
                  while(true) {
                     index = normalized.indexOf("/./");
                     if (index < 0) {
                        while(true) {
                           index = normalized.indexOf("/../");
                           if (index < 0) {
                              return normalized;
                           }

                           if (index == 0) {
                              return null;
                           }

                           int index2 = normalized.lastIndexOf(47, index - 1);
                           normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
                        }
                     }

                     normalized = normalized.substring(0, index) + normalized.substring(index + 2);
                  }
               }

               normalized = normalized.substring(0, index) + " " + normalized.substring(index + 3);
            }
         }

         normalized = normalized.substring(0, index) + normalized.substring(index + 1);
      }
   }

   public String select(boolean state, String trueString, String falseString) {
      return state ? trueString : falseString;
   }

   public boolean allEmpty(List list) {
      int size = list.size();

      for(int i = 0; i < size; ++i) {
         if (list.get(i) != null && list.get(i).toString().length() > 0) {
            return false;
         }
      }

      return true;
   }

   static {
      EOL_LENGTH = EOL.length();
   }
}
