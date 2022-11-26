package org.glassfish.admin.rest.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.codehaus.jettison.json.JSONArray;

public class StringUtil {
   public static boolean compareStringLists(List list1, List list2) {
      return list1.equals(list2);
   }

   public static boolean compareStrings(String str1, String str2) {
      if (str1 == null) {
         return str2 == null;
      } else {
         return str1.equals(str2);
      }
   }

   public static boolean compareStringsIgnoreCase(String str1, String str2) {
      if (str1 == null) {
         return str2 == null;
      } else {
         return str1.equalsIgnoreCase(str2);
      }
   }

   public static String getCommaSeparatedStringList(List strings) {
      StringBuilder sb = new StringBuilder();
      if (strings != null) {
         boolean first = true;

         for(Iterator var3 = strings.iterator(); var3.hasNext(); first = false) {
            String str = (String)var3.next();
            if (!first) {
               sb.append(",");
            }

            sb.append(str);
         }
      }

      return sb.toString();
   }

   public static List parseCommaSeparatedStringList(String stringList) {
      List list = new ArrayList();
      if (stringList != null) {
         StringTokenizer st = new StringTokenizer(stringList, ",");

         while(st.hasMoreTokens()) {
            list.add(st.nextToken().trim());
         }
      }

      return list;
   }

   public static boolean notEmpty(String string) {
      return !isEmpty(string);
   }

   public static boolean isEmpty(String string) {
      return string == null || string.isEmpty();
   }

   public static String nonEmpty(String string) {
      return notEmpty(string) ? string : null;
   }

   public static String nonNull(String string) {
      return notEmpty(string) ? string : "";
   }

   public static String[] toArray(List list) {
      return list != null ? (String[])((String[])list.toArray(new String[list.size()])) : null;
   }

   public static String[] toArray(JSONArray json) throws Exception {
      if (json == null) {
         return null;
      } else {
         String[] array = new String[json.length()];

         for(int i = 0; i < json.length(); ++i) {
            array[i] = json.getString(i);
         }

         return array;
      }
   }

   public static List toList(String[] array) {
      if (array == null) {
         return null;
      } else {
         List list = new ArrayList();
         String[] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            list.add(s);
         }

         return list;
      }
   }

   public static List toList(JSONArray json) throws Exception {
      if (json == null) {
         return null;
      } else {
         List list = new ArrayList();

         for(int i = 0; i < json.length(); ++i) {
            list.add(json.getString(i));
         }

         return list;
      }
   }

   public static JSONArray toJson(String[] array) {
      if (array == null) {
         return null;
      } else {
         JSONArray json = new JSONArray();
         String[] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            json.put(s);
         }

         return json;
      }
   }

   public static JSONArray toJson(List list) {
      if (list == null) {
         return null;
      } else {
         JSONArray json = new JSONArray();
         Iterator var2 = list.iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();
            json.put(s);
         }

         return json;
      }
   }

   public static String getSingular(String plural) {
      String singular = replaceEnding(plural, "sses", "ss");
      if (singular == null) {
         singular = replaceEnding(plural, "ies", "y");
      }

      if (singular == null) {
         singular = replaceEnding(plural, "s", "");
      }

      if (singular == null) {
         singular = plural;
      }

      return singular;
   }

   public static String getPlural(String singular) {
      String plural = replaceEnding(singular, "ss", "sses");
      if (plural == null) {
         plural = replaceEnding(singular, "y", "ies");
      }

      if (plural == null) {
         plural = singular + "s";
      }

      return plural;
   }

   public static String camelCaseToUpperCaseWords(String camelCase) {
      return lowerCaseWordsToUpperCaseWords(camelCaseToLowerCaseWords(camelCase));
   }

   public static String lowerCaseWordsToUpperCaseWords(String lowerCaseWords) {
      if (lowerCaseWords == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         boolean inWord = false;

         for(int i = 0; i < lowerCaseWords.length(); ++i) {
            char c = lowerCaseWords.charAt(i);
            if (c == ' ') {
               inWord = false;
            } else {
               if (!inWord) {
                  c = Character.toUpperCase(c);
               }

               inWord = true;
            }

            sb.append(c);
         }

         return sb.toString();
      }
   }

   public static String camelCaseToLowerCaseWords(String camelCase) {
      if (camelCase == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         int start = 0;
         boolean lastCharIsUpperCase = false;
         boolean emitWord = false;

         for(int i = 0; i < camelCase.length(); ++i) {
            char c = camelCase.charAt(i);
            boolean charIsUpperCase = Character.isUpperCase(c);
            if (i != 0) {
               if (!charIsUpperCase || !lastCharIsUpperCase) {
                  if (charIsUpperCase && !lastCharIsUpperCase) {
                     emitWord(sb, camelCase, start, i);
                     start = i;
                  } else if ((charIsUpperCase || lastCharIsUpperCase) && !charIsUpperCase && lastCharIsUpperCase) {
                     if (i == start) {
                        throw new AssertionError("Only the first word can start with a lower case letter: " + camelCase + " " + start);
                     }

                     if (i != start + 1 && i != start + 2) {
                        emitWord(sb, camelCase, start, i - 1);
                        start = i - 1;
                     }
                  }
               }
            }

            lastCharIsUpperCase = charIsUpperCase;
         }

         emitWord(sb, camelCase, start, camelCase.length());
         return sb.toString();
      }
   }

   private static void emitWord(StringBuilder sb, String s, int start, int end) {
      if (start > end) {
         throw new AssertionError("Start greater than end: " + s + " " + start + " " + end);
      } else if (start != end) {
         String word = s.substring(start, end);
         boolean isAcronym = false;
         if (word.length() > 1) {
            isAcronym = Character.isUpperCase(word.charAt(1));
         } else {
            isAcronym = false;
         }

         if (!isAcronym) {
            word = word.toLowerCase();
         }

         boolean firstWord = start == 0;
         if (!firstWord) {
            sb.append(" ");
         }

         sb.append(word);
      }
   }

   private static String replaceEnding(String plural, String pluralEnding, String singularEnding) {
      return plural.endsWith(pluralEnding) ? plural.substring(0, plural.length() - pluralEnding.length()) + singularEnding : null;
   }
}
