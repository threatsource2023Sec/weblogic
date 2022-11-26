package org.apache.taglibs.standard.functions;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.Util;

public class Functions {
   public static String toUpperCase(String input) {
      return input.toUpperCase();
   }

   public static String toLowerCase(String input) {
      return input.toLowerCase();
   }

   public static int indexOf(String input, String substring) {
      if (input == null) {
         input = "";
      }

      if (substring == null) {
         substring = "";
      }

      return input.indexOf(substring);
   }

   public static boolean contains(String input, String substring) {
      return indexOf(input, substring) != -1;
   }

   public static boolean containsIgnoreCase(String input, String substring) {
      if (input == null) {
         input = "";
      }

      if (substring == null) {
         substring = "";
      }

      String inputUC = input.toUpperCase();
      String substringUC = substring.toUpperCase();
      return indexOf(inputUC, substringUC) != -1;
   }

   public static boolean startsWith(String input, String substring) {
      if (input == null) {
         input = "";
      }

      if (substring == null) {
         substring = "";
      }

      return input.startsWith(substring);
   }

   public static boolean endsWith(String input, String substring) {
      if (input == null) {
         input = "";
      }

      if (substring == null) {
         substring = "";
      }

      return input.endsWith(substring);
   }

   public static String substring(String input, int beginIndex, int endIndex) {
      if (input == null) {
         input = "";
      }

      if (beginIndex >= input.length()) {
         return "";
      } else {
         if (beginIndex < 0) {
            beginIndex = 0;
         }

         if (endIndex < 0 || endIndex > input.length()) {
            endIndex = input.length();
         }

         return endIndex < beginIndex ? "" : input.substring(beginIndex, endIndex);
      }
   }

   public static String substringAfter(String input, String substring) {
      if (input == null) {
         input = "";
      }

      if (input.length() == 0) {
         return "";
      } else {
         if (substring == null) {
            substring = "";
         }

         if (substring.length() == 0) {
            return input;
         } else {
            int index = input.indexOf(substring);
            return index == -1 ? "" : input.substring(index + substring.length());
         }
      }
   }

   public static String substringBefore(String input, String substring) {
      if (input == null) {
         input = "";
      }

      if (input.length() == 0) {
         return "";
      } else {
         if (substring == null) {
            substring = "";
         }

         if (substring.length() == 0) {
            return "";
         } else {
            int index = input.indexOf(substring);
            return index == -1 ? "" : input.substring(0, index);
         }
      }
   }

   public static String escapeXml(String input) {
      return input == null ? "" : Util.escapeXml(input);
   }

   public static String trim(String input) {
      return input == null ? "" : input.trim();
   }

   public static String replace(String input, String substringBefore, String substringAfter) {
      if (input == null) {
         input = "";
      }

      if (input.length() == 0) {
         return "";
      } else {
         if (substringBefore == null) {
            substringBefore = "";
         }

         if (substringBefore.length() == 0) {
            return input;
         } else {
            StringBuffer buf = new StringBuffer(input.length());

            int startIndex;
            int index;
            for(startIndex = 0; (index = input.indexOf(substringBefore, startIndex)) != -1; startIndex = index + substringBefore.length()) {
               buf.append(input.substring(startIndex, index)).append(substringAfter);
            }

            return buf.append(input.substring(startIndex)).toString();
         }
      }
   }

   public static String[] split(String input, String delimiters) {
      if (input == null) {
         input = "";
      }

      String[] array;
      if (input.length() == 0) {
         array = new String[]{""};
         return array;
      } else {
         if (delimiters == null) {
            delimiters = "";
         }

         StringTokenizer tok = new StringTokenizer(input, delimiters);
         int count = tok.countTokens();
         array = new String[count];

         for(int i = 0; tok.hasMoreTokens(); array[i++] = tok.nextToken()) {
         }

         return array;
      }
   }

   public static int length(Object obj) throws JspTagException {
      if (obj == null) {
         return 0;
      } else if (obj instanceof String) {
         return ((String)obj).length();
      } else if (obj instanceof Collection) {
         return ((Collection)obj).size();
      } else if (obj instanceof Map) {
         return ((Map)obj).size();
      } else {
         int count = false;
         int count;
         if (obj instanceof Iterator) {
            Iterator iter = (Iterator)obj;
            count = 0;

            while(iter.hasNext()) {
               ++count;
               iter.next();
            }

            return count;
         } else if (!(obj instanceof Enumeration)) {
            try {
               count = Array.getLength(obj);
               return count;
            } catch (IllegalArgumentException var3) {
               throw new JspTagException(Resources.getMessage("FOREACH_BAD_ITEMS"));
            }
         } else {
            Enumeration enum_ = (Enumeration)obj;
            count = 0;

            while(enum_.hasMoreElements()) {
               ++count;
               enum_.nextElement();
            }

            return count;
         }
      }
   }

   public static String join(String[] array, String separator) {
      if (array == null) {
         return "";
      } else {
         if (separator == null) {
            separator = "";
         }

         StringBuffer buf = new StringBuffer();

         for(int i = 0; i < array.length; ++i) {
            buf.append(array[i]);
            if (i < array.length - 1) {
               buf.append(separator);
            }
         }

         return buf.toString();
      }
   }
}
