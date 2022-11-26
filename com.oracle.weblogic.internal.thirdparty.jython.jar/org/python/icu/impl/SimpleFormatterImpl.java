package org.python.icu.impl;

public final class SimpleFormatterImpl {
   private static final int ARG_NUM_LIMIT = 256;
   private static final char LEN1_CHAR = 'ā';
   private static final char LEN2_CHAR = 'Ă';
   private static final char LEN3_CHAR = 'ă';
   private static final char SEGMENT_LENGTH_ARGUMENT_CHAR = '\uffff';
   private static final int MAX_SEGMENT_LENGTH = 65279;
   private static final String[][] COMMON_PATTERNS = new String[][]{{"{0} {1}", "\u0002\u0000ā \u0001"}, {"{0} ({1})", "\u0002\u0000Ă (\u0001ā)"}, {"{0}, {1}", "\u0002\u0000Ă, \u0001"}, {"{0} – {1}", "\u0002\u0000ă – \u0001"}};

   private SimpleFormatterImpl() {
   }

   public static String compileToStringMinMaxArguments(CharSequence pattern, StringBuilder sb, int min, int max) {
      int textLength;
      int maxArg;
      if (min <= 2 && 2 <= max) {
         String[][] var4 = COMMON_PATTERNS;
         textLength = var4.length;

         for(maxArg = 0; maxArg < textLength; ++maxArg) {
            String[] pair = var4[maxArg];
            if (pair[0].contentEquals(pattern)) {
               assert pair[1].charAt(0) == 2;

               return pair[1];
            }
         }
      }

      int patternLength = pattern.length();
      sb.ensureCapacity(patternLength);
      sb.setLength(1);
      textLength = 0;
      maxArg = -1;
      boolean inQuote = false;
      int i = 0;

      while(true) {
         while(i < patternLength) {
            char c = pattern.charAt(i++);
            if (c == '\'') {
               if (i < patternLength && (c = pattern.charAt(i)) == '\'') {
                  ++i;
               } else {
                  if (inQuote) {
                     inQuote = false;
                     continue;
                  }

                  if (c != '{' && c != '}') {
                     c = '\'';
                  } else {
                     ++i;
                     inQuote = true;
                  }
               }
            } else if (!inQuote && c == '{') {
               if (textLength > 0) {
                  sb.setCharAt(sb.length() - textLength - 1, (char)(256 + textLength));
                  textLength = 0;
               }

               int argNumber;
               if (i + 1 < patternLength && 0 <= (argNumber = pattern.charAt(i) - 48) && argNumber <= 9 && pattern.charAt(i + 1) == '}') {
                  i += 2;
               } else {
                  int argStart = i - 1;
                  argNumber = -1;
                  if (i < patternLength && '1' <= (c = pattern.charAt(i++)) && c <= '9') {
                     argNumber = c - 48;

                     while(i < patternLength && '0' <= (c = pattern.charAt(i++)) && c <= '9') {
                        argNumber = argNumber * 10 + (c - 48);
                        if (argNumber >= 256) {
                           break;
                        }
                     }
                  }

                  if (argNumber < 0 || c != '}') {
                     throw new IllegalArgumentException("Argument syntax error in pattern \"" + pattern + "\" at index " + argStart + ": " + pattern.subSequence(argStart, i));
                  }
               }

               if (argNumber > maxArg) {
                  maxArg = argNumber;
               }

               sb.append((char)argNumber);
               continue;
            }

            if (textLength == 0) {
               sb.append('\uffff');
            }

            sb.append(c);
            ++textLength;
            if (textLength == 65279) {
               textLength = 0;
            }
         }

         if (textLength > 0) {
            sb.setCharAt(sb.length() - textLength - 1, (char)(256 + textLength));
         }

         i = maxArg + 1;
         if (i < min) {
            throw new IllegalArgumentException("Fewer than minimum " + min + " arguments in pattern \"" + pattern + "\"");
         }

         if (i > max) {
            throw new IllegalArgumentException("More than maximum " + max + " arguments in pattern \"" + pattern + "\"");
         }

         sb.setCharAt(0, (char)i);
         return sb.toString();
      }
   }

   public static int getArgumentLimit(String compiledPattern) {
      return compiledPattern.charAt(0);
   }

   public static String formatCompiledPattern(String compiledPattern, CharSequence... values) {
      return formatAndAppend(compiledPattern, new StringBuilder(), (int[])null, values).toString();
   }

   public static String formatRawPattern(String pattern, int min, int max, CharSequence... values) {
      StringBuilder sb = new StringBuilder();
      String compiledPattern = compileToStringMinMaxArguments(pattern, sb, min, max);
      sb.setLength(0);
      return formatAndAppend(compiledPattern, sb, (int[])null, values).toString();
   }

   public static StringBuilder formatAndAppend(String compiledPattern, StringBuilder appendTo, int[] offsets, CharSequence... values) {
      int valuesLength = values != null ? values.length : 0;
      if (valuesLength < getArgumentLimit(compiledPattern)) {
         throw new IllegalArgumentException("Too few values.");
      } else {
         return format(compiledPattern, values, appendTo, (String)null, true, offsets);
      }
   }

   public static StringBuilder formatAndReplace(String compiledPattern, StringBuilder result, int[] offsets, CharSequence... values) {
      int valuesLength = values != null ? values.length : 0;
      if (valuesLength < getArgumentLimit(compiledPattern)) {
         throw new IllegalArgumentException("Too few values.");
      } else {
         int firstArg = -1;
         String resultCopy = null;
         if (getArgumentLimit(compiledPattern) > 0) {
            int i = 1;

            while(i < compiledPattern.length()) {
               int n = compiledPattern.charAt(i++);
               if (n < 256) {
                  if (values[n] == result) {
                     if (i == 2) {
                        firstArg = n;
                     } else if (resultCopy == null) {
                        resultCopy = result.toString();
                     }
                  }
               } else {
                  i += n - 256;
               }
            }
         }

         if (firstArg < 0) {
            result.setLength(0);
         }

         return format(compiledPattern, values, result, resultCopy, false, offsets);
      }
   }

   public static String getTextWithNoArguments(String compiledPattern) {
      int capacity = compiledPattern.length() - 1 - getArgumentLimit(compiledPattern);
      StringBuilder sb = new StringBuilder(capacity);
      int i = 1;

      while(i < compiledPattern.length()) {
         int segmentLength = compiledPattern.charAt(i++) - 256;
         if (segmentLength > 0) {
            int limit = i + segmentLength;
            sb.append(compiledPattern, i, limit);
            i = limit;
         }
      }

      return sb.toString();
   }

   private static StringBuilder format(String compiledPattern, CharSequence[] values, StringBuilder result, String resultCopy, boolean forbidResultAsValue, int[] offsets) {
      int offsetsLength;
      int i;
      if (offsets == null) {
         offsetsLength = 0;
      } else {
         offsetsLength = offsets.length;

         for(i = 0; i < offsetsLength; ++i) {
            offsets[i] = -1;
         }
      }

      i = 1;

      while(i < compiledPattern.length()) {
         int n = compiledPattern.charAt(i++);
         if (n < 256) {
            CharSequence value = values[n];
            if (value == result) {
               if (forbidResultAsValue) {
                  throw new IllegalArgumentException("Value must not be same object as result");
               }

               if (i == 2) {
                  if (n < offsetsLength) {
                     offsets[n] = 0;
                  }
               } else {
                  if (n < offsetsLength) {
                     offsets[n] = result.length();
                  }

                  result.append(resultCopy);
               }
            } else {
               if (n < offsetsLength) {
                  offsets[n] = result.length();
               }

               result.append(value);
            }
         } else {
            int limit = i + (n - 256);
            result.append(compiledPattern, i, limit);
            i = limit;
         }
      }

      return result;
   }
}
