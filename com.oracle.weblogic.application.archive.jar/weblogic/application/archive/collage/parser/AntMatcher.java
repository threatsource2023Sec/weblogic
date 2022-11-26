package weblogic.application.archive.collage.parser;

public class AntMatcher {
   public static boolean match(String pattern, String str, boolean isCaseSensitive) {
      char[] patArr = pattern.toCharArray();
      char[] strArr = str.toCharArray();
      int patIdxStart = 0;
      int patIdxEnd = patArr.length - 1;
      int strIdxStart = 0;
      int strIdxEnd = strArr.length - 1;
      boolean containsStar = false;

      int patIdxTmp;
      for(patIdxTmp = 0; patIdxTmp < patArr.length; ++patIdxTmp) {
         if (patArr[patIdxTmp] == '*') {
            containsStar = true;
            break;
         }
      }

      char ch;
      if (!containsStar) {
         if (patIdxEnd != strIdxEnd) {
            return false;
         } else {
            for(patIdxTmp = 0; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
               ch = patArr[patIdxTmp];
               if (ch != '?') {
                  if (isCaseSensitive && ch != strArr[patIdxTmp]) {
                     return false;
                  }

                  if (!isCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[patIdxTmp])) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else if (patIdxEnd == 0) {
         return true;
      } else {
         while((ch = patArr[patIdxStart]) != '*' && strIdxStart <= strIdxEnd) {
            if (ch != '?') {
               if (isCaseSensitive && ch != strArr[strIdxStart]) {
                  return false;
               }

               if (!isCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxStart])) {
                  return false;
               }
            }

            ++patIdxStart;
            ++strIdxStart;
         }

         if (strIdxStart > strIdxEnd) {
            for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
               if (patArr[patIdxTmp] != '*') {
                  return false;
               }
            }

            return true;
         } else {
            while((ch = patArr[patIdxEnd]) != '*' && strIdxStart <= strIdxEnd) {
               if (ch != '?') {
                  if (isCaseSensitive && ch != strArr[strIdxEnd]) {
                     return false;
                  }

                  if (!isCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxEnd])) {
                     return false;
                  }
               }

               --patIdxEnd;
               --strIdxEnd;
            }

            if (strIdxStart > strIdxEnd) {
               for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                  if (patArr[patIdxTmp] != '*') {
                     return false;
                  }
               }

               return true;
            } else {
               while(patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                  patIdxTmp = -1;

                  int patLength;
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
                     int strLength = strIdxEnd - strIdxStart + 1;
                     int foundIdx = -1;

                     label166:
                     for(int i = 0; i <= strLength - patLength; ++i) {
                        for(int j = 0; j < patLength; ++j) {
                           ch = patArr[patIdxStart + j + 1];
                           if (ch != '?' && (isCaseSensitive && ch != strArr[strIdxStart + i + j] || !isCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxStart + i + j]))) {
                              continue label166;
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
                  if (patArr[patIdxTmp] != '*') {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }
}
