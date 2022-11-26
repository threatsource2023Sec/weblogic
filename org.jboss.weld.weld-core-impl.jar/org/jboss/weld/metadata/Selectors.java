package org.jboss.weld.metadata;

public class Selectors {
   public static final String DEEP_TREE_MATCH = "**";

   private Selectors() {
   }

   public static boolean matchPath(String pattern, String str) {
      String[] patDirs = tokenize(pattern);
      return matchPath(patDirs, tokenize(str), true);
   }

   public static boolean matchPath(String pattern, String str, boolean isCaseSensitive) {
      String[] patDirs = tokenize(pattern);
      return matchPath(patDirs, tokenize(str), isCaseSensitive);
   }

   static boolean matchPath(String[] tokenizedPattern, String[] strDirs, boolean isCaseSensitive) {
      int patIdxStart = 0;
      int patIdxEnd = tokenizedPattern.length - 1;
      int strIdxStart = 0;

      int strIdxEnd;
      String patDir;
      for(strIdxEnd = strDirs.length - 1; patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd; ++strIdxStart) {
         patDir = tokenizedPattern[patIdxStart];
         if (patDir.equals("**")) {
            break;
         }

         if (!match(patDir, strDirs[strIdxStart], isCaseSensitive)) {
            return false;
         }

         ++patIdxStart;
      }

      int patIdxTmp;
      if (strIdxStart > strIdxEnd) {
         for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
            if (!tokenizedPattern[patIdxTmp].equals("**")) {
               return false;
            }
         }

         return true;
      } else if (patIdxStart > patIdxEnd) {
         return false;
      } else {
         while(patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd) {
            patDir = tokenizedPattern[patIdxEnd];
            if (patDir.equals("**")) {
               break;
            }

            if (!match(patDir, strDirs[strIdxEnd], isCaseSensitive)) {
               return false;
            }

            --patIdxEnd;
            --strIdxEnd;
         }

         if (strIdxStart > strIdxEnd) {
            for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
               if (!tokenizedPattern[patIdxTmp].equals("**")) {
                  return false;
               }
            }

            return true;
         } else {
            while(patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
               patIdxTmp = -1;

               int patLength;
               for(patLength = patIdxStart + 1; patLength <= patIdxEnd; ++patLength) {
                  if (tokenizedPattern[patLength].equals("**")) {
                     patIdxTmp = patLength;
                     break;
                  }
               }

               if (patIdxTmp == patIdxStart + 1) {
                  ++patIdxStart;
               } else {
                  patLength = patIdxTmp - patIdxStart - 1;
                  int strLength = strIdxEnd - strIdxStart + 1;
                  int foundIdx = -1;
                  int i = 0;

                  label106:
                  while(i <= strLength - patLength) {
                     for(int j = 0; j < patLength; ++j) {
                        String subPat = tokenizedPattern[patIdxStart + j + 1];
                        String subStr = strDirs[strIdxStart + i + j];
                        if (!match(subPat, subStr, isCaseSensitive)) {
                           ++i;
                           continue label106;
                        }
                     }

                     foundIdx = strIdxStart + i;
                     break;
                  }

                  if (foundIdx == -1) {
                     return false;
                  }

                  patIdxStart = patIdxTmp;
                  strIdxStart = foundIdx + patLength;
               }
            }

            for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
               if (!tokenizedPattern[patIdxTmp].equals("**")) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean different(boolean caseSensitive, char ch, char other) {
      return caseSensitive ? ch != other : Character.toUpperCase(ch) != Character.toUpperCase(other);
   }

   public static boolean match(String pattern, String str, boolean caseSensitive) {
      char[] patArr = pattern.toCharArray();
      char[] strArr = str.toCharArray();
      int patIdxStart = 0;
      int patIdxEnd = patArr.length - 1;
      int strIdxStart = 0;
      int strIdxEnd = strArr.length - 1;
      boolean containsStar = false;
      char[] var11 = patArr;
      int patLength = patArr.length;

      int strLength;
      int foundIdx;
      for(strLength = 0; strLength < patLength; ++strLength) {
         foundIdx = var11[strLength];
         if (foundIdx == 42) {
            containsStar = true;
            break;
         }
      }

      char ch;
      int patIdxTmp;
      if (!containsStar) {
         if (patIdxEnd != strIdxEnd) {
            return false;
         } else {
            for(patIdxTmp = 0; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
               ch = patArr[patIdxTmp];
               if (ch != '?' && different(caseSensitive, ch, strArr[patIdxTmp])) {
                  return false;
               }
            }

            return true;
         }
      } else if (patIdxEnd == 0) {
         return true;
      } else {
         while(true) {
            ch = patArr[patIdxStart];
            if (ch == '*' || strIdxStart > strIdxEnd) {
               if (strIdxStart > strIdxEnd) {
                  return allStars(patArr, patIdxStart, patIdxEnd);
               } else {
                  while(true) {
                     ch = patArr[patIdxEnd];
                     if (ch == '*' || strIdxStart > strIdxEnd) {
                        if (strIdxStart > strIdxEnd) {
                           return allStars(patArr, patIdxStart, patIdxEnd);
                        } else {
                           while(patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                              patIdxTmp = -1;

                              for(patLength = patIdxStart + 1; patLength <= patIdxEnd; ++patLength) {
                                 if (patArr[patLength] == '*') {
                                    patIdxTmp = patLength;
                                    break;
                                 }
                              }

                              if (patIdxTmp == patIdxStart + 1) {
                                 ++patIdxStart;
                              } else {
                                 patLength = patIdxTmp - patIdxStart - 1;
                                 strLength = strIdxEnd - strIdxStart + 1;
                                 foundIdx = -1;
                                 int i = 0;

                                 label101:
                                 while(i <= strLength - patLength) {
                                    for(int j = 0; j < patLength; ++j) {
                                       ch = patArr[patIdxStart + j + 1];
                                       if (ch != '?' && different(caseSensitive, ch, strArr[strIdxStart + i + j])) {
                                          ++i;
                                          continue label101;
                                       }
                                    }

                                    foundIdx = strIdxStart + i;
                                    break;
                                 }

                                 if (foundIdx == -1) {
                                    return false;
                                 }

                                 patIdxStart = patIdxTmp;
                                 strIdxStart = foundIdx + patLength;
                              }
                           }

                           return allStars(patArr, patIdxStart, patIdxEnd);
                        }
                     }

                     if (ch != '?' && different(caseSensitive, ch, strArr[strIdxEnd])) {
                        return false;
                     }

                     --patIdxEnd;
                     --strIdxEnd;
                  }
               }
            }

            if (ch != '?' && different(caseSensitive, ch, strArr[strIdxStart])) {
               return false;
            }

            ++patIdxStart;
            ++strIdxStart;
         }
      }
   }

   private static boolean allStars(char[] chars, int start, int end) {
      for(int i = start; i <= end; ++i) {
         if (chars[i] != '*') {
            return false;
         }
      }

      return true;
   }

   static String[] tokenize(String str) {
      char sep = 46;
      int start = 0;
      int len = str.length();
      int count = 0;

      for(int pos = 0; pos < len; ++pos) {
         if (str.charAt(pos) == sep) {
            if (pos != start) {
               ++count;
            }

            start = pos + 1;
         }
      }

      if (len != start) {
         ++count;
      }

      String[] l = new String[count];
      count = 0;
      start = 0;

      for(int pos = 0; pos < len; ++pos) {
         if (str.charAt(pos) == sep) {
            if (pos != start) {
               String tok = str.substring(start, pos);
               l[count++] = tok;
            }

            start = pos + 1;
         }
      }

      if (len != start) {
         String tok = str.substring(start);
         l[count] = tok;
      }

      return l;
   }
}
