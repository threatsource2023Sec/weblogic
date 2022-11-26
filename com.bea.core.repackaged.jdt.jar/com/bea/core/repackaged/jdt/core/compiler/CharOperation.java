package com.bea.core.repackaged.jdt.core.compiler;

import com.bea.core.repackaged.jdt.internal.compiler.parser.ScannerHelper;
import java.util.Arrays;
import java.util.List;

public final class CharOperation {
   public static final char[] NO_CHAR = new char[0];
   public static final char[][] NO_CHAR_CHAR = new char[0][];
   public static final String[] NO_STRINGS = new String[0];
   public static final char[] ALL_PREFIX = new char[]{'*'};
   public static final char[] COMMA_SEPARATOR = new char[]{','};

   public static final char[] append(char[] array, char suffix) {
      if (array == null) {
         return new char[]{suffix};
      } else {
         int length = array.length;
         System.arraycopy(array, 0, array = new char[length + 1], 0, length);
         array[length] = suffix;
         return array;
      }
   }

   public static final char[] append(char[] target, char[] suffix) {
      if (suffix != null && suffix.length != 0) {
         int targetLength = target.length;
         int subLength = suffix.length;
         int newTargetLength = targetLength + subLength;
         if (newTargetLength > targetLength) {
            System.arraycopy(target, 0, target = new char[newTargetLength], 0, targetLength);
         }

         System.arraycopy(suffix, 0, target, targetLength, subLength);
         return target;
      } else {
         return target;
      }
   }

   public static final char[] append(char[] target, int index, char[] array, int start, int end) {
      int targetLength = target.length;
      int subLength = end - start;
      int newTargetLength = subLength + index;
      if (newTargetLength > targetLength) {
         System.arraycopy(target, 0, target = new char[newTargetLength * 2], 0, index);
      }

      System.arraycopy(array, start, target, index, subLength);
      return target;
   }

   public static final char[] prepend(char prefix, char[] array) {
      if (array == null) {
         return new char[]{prefix};
      } else {
         int length = array.length;
         System.arraycopy(array, 0, array = new char[length + 1], 1, length);
         array[0] = prefix;
         return array;
      }
   }

   public static final char[][] arrayConcat(char[][] first, char[][] second) {
      if (first == null) {
         return second;
      } else if (second == null) {
         return first;
      } else {
         int length1 = first.length;
         int length2 = second.length;
         char[][] result = new char[length1 + length2][];
         System.arraycopy(first, 0, result, 0, length1);
         System.arraycopy(second, 0, result, length1, length2);
         return result;
      }
   }

   public static final boolean camelCaseMatch(char[] pattern, char[] name) {
      if (pattern == null) {
         return true;
      } else {
         return name == null ? false : camelCaseMatch(pattern, 0, pattern.length, name, 0, name.length, false);
      }
   }

   public static final boolean camelCaseMatch(char[] pattern, char[] name, boolean samePartCount) {
      if (pattern == null) {
         return true;
      } else {
         return name == null ? false : camelCaseMatch(pattern, 0, pattern.length, name, 0, name.length, samePartCount);
      }
   }

   public static final boolean camelCaseMatch(char[] pattern, int patternStart, int patternEnd, char[] name, int nameStart, int nameEnd) {
      return camelCaseMatch(pattern, patternStart, patternEnd, name, nameStart, nameEnd, false);
   }

   public static final boolean camelCaseMatch(char[] pattern, int patternStart, int patternEnd, char[] name, int nameStart, int nameEnd, boolean samePartCount) {
      if (name == null) {
         return false;
      } else if (pattern == null) {
         return true;
      } else {
         if (patternEnd < 0) {
            patternEnd = pattern.length;
         }

         if (nameEnd < 0) {
            nameEnd = name.length;
         }

         if (patternEnd <= patternStart) {
            return nameEnd <= nameStart;
         } else if (nameEnd <= nameStart) {
            return false;
         } else if (name[nameStart] != pattern[patternStart]) {
            return false;
         } else {
            int iPattern = patternStart;
            int iName = nameStart;

            label130:
            while(true) {
               ++iPattern;
               ++iName;
               char nameChar;
               if (iPattern == patternEnd) {
                  if (samePartCount && iName != nameEnd) {
                     for(; iName != nameEnd; ++iName) {
                        nameChar = name[iName];
                        if (nameChar < 128) {
                           if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[nameChar] & 32) != 0) {
                              return false;
                           }
                        } else if (!Character.isJavaIdentifierPart(nameChar) || Character.isUpperCase(nameChar)) {
                           return false;
                        }
                     }

                     return true;
                  } else {
                     return true;
                  }
               }

               if (iName == nameEnd) {
                  return false;
               }

               char patternChar;
               if ((patternChar = pattern[iPattern]) != name[iName]) {
                  if (patternChar < 128) {
                     if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[patternChar] & 36) == 0) {
                        return false;
                     }
                  } else if (Character.isJavaIdentifierPart(patternChar) && !Character.isUpperCase(patternChar) && !Character.isDigit(patternChar)) {
                     return false;
                  }

                  while(true) {
                     while(iName != nameEnd) {
                        nameChar = name[iName];
                        if (nameChar < 128) {
                           int charNature = ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[nameChar];
                           if ((charNature & 144) != 0) {
                              ++iName;
                           } else {
                              if ((charNature & 4) == 0) {
                                 if (patternChar != nameChar) {
                                    return false;
                                 }
                                 continue label130;
                              }

                              if (patternChar == nameChar) {
                                 continue label130;
                              }

                              ++iName;
                           }
                        } else if (Character.isJavaIdentifierPart(nameChar) && !Character.isUpperCase(nameChar)) {
                           ++iName;
                        } else {
                           if (!Character.isDigit(nameChar)) {
                              if (patternChar != nameChar) {
                                 return false;
                              }
                              continue label130;
                           }

                           if (patternChar == nameChar) {
                              continue label130;
                           }

                           ++iName;
                        }
                     }

                     return false;
                  }
               }
            }
         }
      }
   }

   public static final boolean substringMatch(String pattern, String name) {
      if (pattern != null && pattern.length() != 0) {
         return name == null ? false : checkSubstringMatch(pattern.toCharArray(), name.toCharArray());
      } else {
         return true;
      }
   }

   public static final boolean substringMatch(char[] pattern, char[] name) {
      if (pattern != null && pattern.length != 0) {
         return name == null ? false : checkSubstringMatch(pattern, name);
      } else {
         return true;
      }
   }

   private static final boolean checkSubstringMatch(char[] pattern, char[] name) {
      for(int nidx = 0; nidx < name.length - pattern.length + 1; ++nidx) {
         for(int pidx = 0; pidx < pattern.length; ++pidx) {
            if (Character.toLowerCase(name[nidx + pidx]) != Character.toLowerCase(pattern[pidx])) {
               if (name[nidx + pidx] == '(' || name[nidx + pidx] == ':') {
                  return false;
               }
               break;
            }

            if (pidx == pattern.length - 1) {
               return true;
            }
         }
      }

      return false;
   }

   public static String[] charArrayToStringArray(char[][] charArrays) {
      if (charArrays == null) {
         return null;
      } else {
         int length = charArrays.length;
         if (length == 0) {
            return NO_STRINGS;
         } else {
            String[] strings = new String[length];

            for(int i = 0; i < length; ++i) {
               strings[i] = new String(charArrays[i]);
            }

            return strings;
         }
      }
   }

   public static String charToString(char[] charArray) {
      return charArray == null ? null : new String(charArray);
   }

   public static char[][] toCharArrays(List stringList) {
      if (stringList == null) {
         return null;
      } else {
         char[][] result = new char[stringList.size()][];

         for(int i = 0; i < result.length; ++i) {
            result[i] = ((String)stringList.get(i)).toCharArray();
         }

         return result;
      }
   }

   public static final char[][] arrayConcat(char[][] first, char[] second) {
      if (second == null) {
         return first;
      } else if (first == null) {
         return new char[][]{second};
      } else {
         int length = first.length;
         char[][] result = new char[length + 1][];
         System.arraycopy(first, 0, result, 0, length);
         result[length] = second;
         return result;
      }
   }

   public static final int compareTo(char[] array1, char[] array2) {
      int length1 = array1.length;
      int length2 = array2.length;
      int min = Math.min(length1, length2);

      for(int i = 0; i < min; ++i) {
         if (array1[i] != array2[i]) {
            return array1[i] - array2[i];
         }
      }

      return length1 - length2;
   }

   public static final int compareTo(char[] array1, char[] array2, int start, int end) {
      int length1 = array1.length;
      int length2 = array2.length;
      int min = Math.min(length1, length2);
      min = Math.min(min, end);

      for(int i = start; i < min; ++i) {
         if (array1[i] != array2[i]) {
            return array1[i] - array2[i];
         }
      }

      return length1 - length2;
   }

   public static final int compareWith(char[] array, char[] prefix) {
      int arrayLength = array.length;
      int prefixLength = prefix.length;
      int min = Math.min(arrayLength, prefixLength);
      int i = 0;

      while(min-- != 0) {
         char c1 = array[i];
         char c2 = prefix[i++];
         if (c1 != c2) {
            return c1 - c2;
         }
      }

      return prefixLength == i ? 0 : -1;
   }

   public static final char[] concat(char[] first, char[] second) {
      if (first == null) {
         return second;
      } else if (second == null) {
         return first;
      } else {
         int length1 = first.length;
         int length2 = second.length;
         char[] result = new char[length1 + length2];
         System.arraycopy(first, 0, result, 0, length1);
         System.arraycopy(second, 0, result, length1, length2);
         return result;
      }
   }

   public static final char[] concat(char[] first, char[] second, char[] third) {
      if (first == null) {
         return concat(second, third);
      } else if (second == null) {
         return concat(first, third);
      } else if (third == null) {
         return concat(first, second);
      } else {
         int length1 = first.length;
         int length2 = second.length;
         int length3 = third.length;
         char[] result = new char[length1 + length2 + length3];
         System.arraycopy(first, 0, result, 0, length1);
         System.arraycopy(second, 0, result, length1, length2);
         System.arraycopy(third, 0, result, length1 + length2, length3);
         return result;
      }
   }

   public static final char[] concat(char[] first, char[] second, char separator) {
      if (first == null) {
         return second;
      } else if (second == null) {
         return first;
      } else {
         int length1 = first.length;
         if (length1 == 0) {
            return second;
         } else {
            int length2 = second.length;
            if (length2 == 0) {
               return first;
            } else {
               char[] result = new char[length1 + length2 + 1];
               System.arraycopy(first, 0, result, 0, length1);
               result[length1] = separator;
               System.arraycopy(second, 0, result, length1 + 1, length2);
               return result;
            }
         }
      }
   }

   public static final char[] concatAll(char[] first, char[] second, char separator) {
      if (first == null) {
         return second;
      } else if (second == null) {
         return first;
      } else {
         int length1 = first.length;
         if (length1 == 0) {
            return second;
         } else {
            int length2 = second.length;
            char[] result = new char[length1 + length2 + 1];
            System.arraycopy(first, 0, result, 0, length1);
            result[length1] = separator;
            if (length2 > 0) {
               System.arraycopy(second, 0, result, length1 + 1, length2);
            }

            return result;
         }
      }
   }

   public static final char[] concat(char[] first, char sep1, char[] second, char sep2, char[] third) {
      if (first == null) {
         return concat(second, third, sep2);
      } else if (second == null) {
         return concat(first, third, sep1);
      } else if (third == null) {
         return concat(first, second, sep1);
      } else {
         int length1 = first.length;
         int length2 = second.length;
         int length3 = third.length;
         char[] result = new char[length1 + length2 + length3 + 2];
         System.arraycopy(first, 0, result, 0, length1);
         result[length1] = sep1;
         System.arraycopy(second, 0, result, length1 + 1, length2);
         result[length1 + length2 + 1] = sep2;
         System.arraycopy(third, 0, result, length1 + length2 + 2, length3);
         return result;
      }
   }

   public static final char[] concatNonEmpty(char[] first, char[] second, char separator) {
      if (first != null && first.length != 0) {
         return second != null && second.length != 0 ? concat(first, second, separator) : first;
      } else {
         return second;
      }
   }

   public static final char[] concatNonEmpty(char[] first, char sep1, char[] second, char sep2, char[] third) {
      if (first != null && first.length != 0) {
         if (second != null && second.length != 0) {
            return third != null && third.length != 0 ? concat(first, sep1, second, sep2, third) : concatNonEmpty(first, second, sep1);
         } else {
            return concatNonEmpty(first, third, sep1);
         }
      } else {
         return concatNonEmpty(second, third, sep2);
      }
   }

   public static final char[] concat(char prefix, char[] array, char suffix) {
      if (array == null) {
         return new char[]{prefix, suffix};
      } else {
         int length = array.length;
         char[] result = new char[length + 2];
         result[0] = prefix;
         System.arraycopy(array, 0, result, 1, length);
         result[length + 1] = suffix;
         return result;
      }
   }

   public static final char[] concatWith(char[] name, char[][] array, char separator) {
      int nameLength = name == null ? 0 : name.length;
      if (nameLength == 0) {
         return concatWith(array, separator);
      } else {
         int length = array == null ? 0 : array.length;
         if (length == 0) {
            return name;
         } else {
            int size = nameLength;
            int index = length;

            while(true) {
               --index;
               if (index < 0) {
                  char[] result = new char[size];
                  index = size;

                  for(int i = length - 1; i >= 0; --i) {
                     int subLength = array[i].length;
                     if (subLength > 0) {
                        index -= subLength;
                        System.arraycopy(array[i], 0, result, index, subLength);
                        --index;
                        result[index] = separator;
                     }
                  }

                  System.arraycopy(name, 0, result, 0, nameLength);
                  return result;
               }

               if (array[index].length > 0) {
                  size += array[index].length + 1;
               }
            }
         }
      }
   }

   public static final char[] concatWith(char[][] array, char[] name, char separator) {
      int nameLength = name == null ? 0 : name.length;
      if (nameLength == 0) {
         return concatWith(array, separator);
      } else {
         int length = array == null ? 0 : array.length;
         if (length == 0) {
            return name;
         } else {
            int size = nameLength;
            int index = length;

            while(true) {
               --index;
               if (index < 0) {
                  char[] result = new char[size];
                  index = 0;

                  for(int i = 0; i < length; ++i) {
                     int subLength = array[i].length;
                     if (subLength > 0) {
                        System.arraycopy(array[i], 0, result, index, subLength);
                        index += subLength;
                        result[index++] = separator;
                     }
                  }

                  System.arraycopy(name, 0, result, index, nameLength);
                  return result;
               }

               if (array[index].length > 0) {
                  size += array[index].length + 1;
               }
            }
         }
      }
   }

   public static final char[] concatWith(char[][] array, char separator) {
      int length = array == null ? 0 : array.length;
      if (length == 0) {
         return NO_CHAR;
      } else {
         int size = length - 1;
         int index = length;

         while(true) {
            --index;
            if (index < 0) {
               if (size <= 0) {
                  return NO_CHAR;
               }

               char[] result = new char[size];
               index = length;

               while(true) {
                  --index;
                  if (index < 0) {
                     return result;
                  }

                  length = array[index].length;
                  if (length > 0) {
                     System.arraycopy(array[index], 0, result, size -= length, length);
                     --size;
                     if (size >= 0) {
                        result[size] = separator;
                     }
                  }
               }
            }

            if (array[index].length == 0) {
               --size;
            } else {
               size += array[index].length;
            }
         }
      }
   }

   public static final char[] concatWithAll(char[][] array, char separator) {
      int length = array == null ? 0 : array.length;
      if (length == 0) {
         return NO_CHAR;
      } else {
         int size = length - 1;
         int index = length;

         while(true) {
            --index;
            if (index < 0) {
               char[] result = new char[size];
               index = length;

               while(true) {
                  --index;
                  if (index < 0) {
                     return result;
                  }

                  length = array[index].length;
                  if (length > 0) {
                     System.arraycopy(array[index], 0, result, size -= length, length);
                  }

                  --size;
                  if (size >= 0) {
                     result[size] = separator;
                  }
               }
            }

            size += array[index].length;
         }
      }
   }

   public static final boolean contains(char character, char[][] array) {
      int i = array.length;

      label19:
      while(true) {
         --i;
         if (i < 0) {
            return false;
         }

         char[] subarray = array[i];
         int j = subarray.length;

         do {
            --j;
            if (j < 0) {
               continue label19;
            }
         } while(subarray[j] != character);

         return true;
      }
   }

   public static final boolean contains(char character, char[] array) {
      int i = array.length;

      do {
         --i;
         if (i < 0) {
            return false;
         }
      } while(array[i] != character);

      return true;
   }

   public static final boolean contains(char[] characters, char[] array) {
      int i = array.length;

      label19:
      while(true) {
         --i;
         if (i < 0) {
            return false;
         }

         int j = characters.length;

         do {
            --j;
            if (j < 0) {
               continue label19;
            }
         } while(array[i] != characters[j]);

         return true;
      }
   }

   public static boolean containsEqual(char[][] array, char[] sequence) {
      for(int i = 0; i < array.length; ++i) {
         if (equals(array[i], sequence)) {
            return true;
         }
      }

      return false;
   }

   public static final char[][] deepCopy(char[][] toCopy) {
      int toCopyLength = toCopy.length;
      char[][] result = new char[toCopyLength][];

      for(int i = 0; i < toCopyLength; ++i) {
         char[] toElement = toCopy[i];
         int toElementLength = toElement.length;
         char[] resultElement = new char[toElementLength];
         System.arraycopy(toElement, 0, resultElement, 0, toElementLength);
         result[i] = resultElement;
      }

      return result;
   }

   public static final boolean endsWith(char[] array, char[] toBeFound) {
      int i = toBeFound.length;
      int j = array.length - i;
      if (j < 0) {
         return false;
      } else {
         do {
            --i;
            if (i < 0) {
               return true;
            }
         } while(toBeFound[i] == array[i + j]);

         return false;
      }
   }

   public static final boolean equals(char[][] first, char[][] second) {
      if (first == second) {
         return true;
      } else if (first != null && second != null) {
         if (first.length != second.length) {
            return false;
         } else {
            int i = first.length;

            do {
               --i;
               if (i < 0) {
                  return true;
               }
            } while(equals(first[i], second[i]));

            return false;
         }
      } else {
         return false;
      }
   }

   public static final boolean equals(char[][] first, char[][] second, boolean isCaseSensitive) {
      if (isCaseSensitive) {
         return equals(first, second);
      } else if (first == second) {
         return true;
      } else if (first != null && second != null) {
         if (first.length != second.length) {
            return false;
         } else {
            int i = first.length;

            do {
               --i;
               if (i < 0) {
                  return true;
               }
            } while(equals(first[i], second[i], false));

            return false;
         }
      } else {
         return false;
      }
   }

   public static final boolean equals(char[] first, char[] second) {
      if (first == second) {
         return true;
      } else if (first != null && second != null) {
         if (first.length != second.length) {
            return false;
         } else {
            int i = first.length;

            do {
               --i;
               if (i < 0) {
                  return true;
               }
            } while(first[i] == second[i]);

            return false;
         }
      } else {
         return false;
      }
   }

   public static final boolean equals(char[] first, char[] second, int secondStart, int secondEnd) {
      return equals(first, second, secondStart, secondEnd, true);
   }

   public static final boolean equals(char[] first, char[] second, int secondStart, int secondEnd, boolean isCaseSensitive) {
      if (first == second) {
         return true;
      } else if (first != null && second != null) {
         if (first.length != secondEnd - secondStart) {
            return false;
         } else {
            int i;
            if (isCaseSensitive) {
               i = first.length;

               while(true) {
                  --i;
                  if (i < 0) {
                     break;
                  }

                  if (first[i] != second[i + secondStart]) {
                     return false;
                  }
               }
            } else {
               i = first.length;

               while(true) {
                  --i;
                  if (i < 0) {
                     break;
                  }

                  if (ScannerHelper.toLowerCase(first[i]) != ScannerHelper.toLowerCase(second[i + secondStart])) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static final boolean equals(char[] first, char[] second, boolean isCaseSensitive) {
      if (isCaseSensitive) {
         return equals(first, second);
      } else if (first == second) {
         return true;
      } else if (first != null && second != null) {
         if (first.length != second.length) {
            return false;
         } else {
            int i = first.length;

            do {
               --i;
               if (i < 0) {
                  return true;
               }
            } while(ScannerHelper.toLowerCase(first[i]) == ScannerHelper.toLowerCase(second[i]));

            return false;
         }
      } else {
         return false;
      }
   }

   public static final boolean fragmentEquals(char[] fragment, char[] name, int startIndex, boolean isCaseSensitive) {
      int max = fragment.length;
      if (name.length < max + startIndex) {
         return false;
      } else {
         int i;
         if (isCaseSensitive) {
            i = max;

            do {
               --i;
               if (i < 0) {
                  return true;
               }
            } while(fragment[i] == name[i + startIndex]);

            return false;
         } else {
            i = max;

            do {
               --i;
               if (i < 0) {
                  return true;
               }
            } while(ScannerHelper.toLowerCase(fragment[i]) == ScannerHelper.toLowerCase(name[i + startIndex]));

            return false;
         }
      }
   }

   public static final int hashCode(char[] array) {
      int hash = Arrays.hashCode(array);
      return hash & Integer.MAX_VALUE;
   }

   public static boolean isWhitespace(char c) {
      return c < 128 && (ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 256) != 0;
   }

   public static final int indexOf(char toBeFound, char[] array) {
      return indexOf(toBeFound, array, 0);
   }

   public static final int indexOf(char[] toBeFound, char[] array, boolean isCaseSensitive) {
      return indexOf(toBeFound, array, isCaseSensitive, 0);
   }

   public static final int indexOf(char[] toBeFound, char[] array, boolean isCaseSensitive, int start) {
      return indexOf(toBeFound, array, isCaseSensitive, start, array.length);
   }

   public static final int indexOf(char[] toBeFound, char[] array, boolean isCaseSensitive, int start, int end) {
      int arrayLength = end;
      int toBeFoundLength = toBeFound.length;
      if (toBeFoundLength <= end && start >= 0) {
         if (toBeFoundLength == 0) {
            return 0;
         } else {
            int i;
            if (toBeFoundLength == end) {
               if (isCaseSensitive) {
                  for(i = start; i < arrayLength; ++i) {
                     if (array[i] != toBeFound[i]) {
                        return -1;
                     }
                  }

                  return 0;
               } else {
                  for(i = start; i < arrayLength; ++i) {
                     if (ScannerHelper.toLowerCase(array[i]) != ScannerHelper.toLowerCase(toBeFound[i])) {
                        return -1;
                     }
                  }

                  return 0;
               }
            } else {
               int max;
               int j;
               if (isCaseSensitive) {
                  i = start;

                  label95:
                  for(max = end - toBeFoundLength + 1; i < max; ++i) {
                     if (array[i] == toBeFound[0]) {
                        for(j = 1; j < toBeFoundLength; ++j) {
                           if (array[i + j] != toBeFound[j]) {
                              continue label95;
                           }
                        }

                        return i;
                     }
                  }
               } else {
                  i = start;

                  label81:
                  for(max = end - toBeFoundLength + 1; i < max; ++i) {
                     if (ScannerHelper.toLowerCase(array[i]) == ScannerHelper.toLowerCase(toBeFound[0])) {
                        for(j = 1; j < toBeFoundLength; ++j) {
                           if (ScannerHelper.toLowerCase(array[i + j]) != ScannerHelper.toLowerCase(toBeFound[j])) {
                              continue label81;
                           }
                        }

                        return i;
                     }
                  }
               }

               return -1;
            }
         }
      } else {
         return -1;
      }
   }

   public static final int indexOf(char toBeFound, char[] array, int start) {
      for(int i = start; i < array.length; ++i) {
         if (toBeFound == array[i]) {
            return i;
         }
      }

      return -1;
   }

   public static final int indexOf(char toBeFound, char[] array, int start, int end) {
      for(int i = start; i < end; ++i) {
         if (toBeFound == array[i]) {
            return i;
         }
      }

      return -1;
   }

   public static final int lastIndexOf(char toBeFound, char[] array) {
      int i = array.length;

      do {
         --i;
         if (i < 0) {
            return -1;
         }
      } while(toBeFound != array[i]);

      return i;
   }

   public static final int lastIndexOf(char toBeFound, char[] array, int startIndex) {
      int i = array.length;

      do {
         --i;
         if (i < startIndex) {
            return -1;
         }
      } while(toBeFound != array[i]);

      return i;
   }

   public static final int lastIndexOf(char toBeFound, char[] array, int startIndex, int endIndex) {
      int i = endIndex;

      do {
         --i;
         if (i < startIndex) {
            return -1;
         }
      } while(toBeFound != array[i]);

      return i;
   }

   public static final char[] lastSegment(char[] array, char separator) {
      int pos = lastIndexOf(separator, array);
      return pos < 0 ? array : subarray(array, pos + 1, array.length);
   }

   public static final boolean match(char[] pattern, char[] name, boolean isCaseSensitive) {
      if (name == null) {
         return false;
      } else {
         return pattern == null ? true : match(pattern, 0, pattern.length, name, 0, name.length, isCaseSensitive);
      }
   }

   public static final boolean match(char[] pattern, int patternStart, int patternEnd, char[] name, int nameStart, int nameEnd, boolean isCaseSensitive) {
      if (name == null) {
         return false;
      } else if (pattern == null) {
         return true;
      } else {
         int iPattern = patternStart;
         int iName = nameStart;
         if (patternEnd < 0) {
            patternEnd = pattern.length;
         }

         if (nameEnd < 0) {
            nameEnd = name.length;
         }

         for(char patternChar = false; iPattern != patternEnd; ++iPattern) {
            char patternChar;
            if ((patternChar = pattern[iPattern]) == '*') {
               int segmentStart;
               if (patternChar == '*') {
                  ++iPattern;
                  segmentStart = iPattern;
               } else {
                  segmentStart = 0;
               }

               int prefixStart = iName;

               while(true) {
                  while(iName < nameEnd) {
                     if (iPattern == patternEnd) {
                        iPattern = segmentStart;
                        ++prefixStart;
                        iName = prefixStart;
                     } else if ((patternChar = pattern[iPattern]) == '*') {
                        ++iPattern;
                        segmentStart = iPattern;
                        if (iPattern == patternEnd) {
                           return true;
                        }

                        prefixStart = iName;
                     } else if ((isCaseSensitive ? name[iName] : ScannerHelper.toLowerCase(name[iName])) != patternChar && patternChar != '?') {
                        iPattern = segmentStart;
                        ++prefixStart;
                        iName = prefixStart;
                     } else {
                        ++iName;
                        ++iPattern;
                     }
                  }

                  if (segmentStart == patternEnd || iName == nameEnd && iPattern == patternEnd || iPattern == patternEnd - 1 && pattern[iPattern] == '*') {
                     return true;
                  }

                  return false;
               }
            }

            if (iName == nameEnd) {
               return false;
            }

            if (patternChar != (isCaseSensitive ? name[iName] : ScannerHelper.toLowerCase(name[iName])) && patternChar != '?') {
               return false;
            }

            ++iName;
         }

         if (iName == nameEnd) {
            return true;
         } else {
            return false;
         }
      }
   }

   public static final boolean pathMatch(char[] pattern, char[] filepath, boolean isCaseSensitive, char pathSeparator) {
      if (filepath == null) {
         return false;
      } else if (pattern == null) {
         return true;
      } else {
         int pSegmentStart = pattern[0] == pathSeparator ? 1 : 0;
         int pLength = pattern.length;
         int pSegmentEnd = indexOf(pathSeparator, pattern, pSegmentStart + 1);
         if (pSegmentEnd < 0) {
            pSegmentEnd = pLength;
         }

         boolean freeTrailingDoubleStar = pattern[pLength - 1] == pathSeparator;
         int fLength = filepath.length;
         int fSegmentStart;
         if (filepath[0] != pathSeparator) {
            fSegmentStart = 0;
         } else {
            fSegmentStart = 1;
         }

         if (fSegmentStart != pSegmentStart) {
            return false;
         } else {
            int fSegmentEnd = indexOf(pathSeparator, filepath, fSegmentStart + 1);
            if (fSegmentEnd < 0) {
               fSegmentEnd = fLength;
            }

            while(pSegmentStart < pLength && (pSegmentEnd != pLength || !freeTrailingDoubleStar) && (pSegmentEnd != pSegmentStart + 2 || pattern[pSegmentStart] != '*' || pattern[pSegmentStart + 1] != '*')) {
               if (fSegmentStart >= fLength) {
                  return false;
               }

               if (!match(pattern, pSegmentStart, pSegmentEnd, filepath, fSegmentStart, fSegmentEnd, isCaseSensitive)) {
                  return false;
               }

               pSegmentEnd = indexOf(pathSeparator, pattern, pSegmentStart = pSegmentEnd + 1);
               if (pSegmentEnd < 0) {
                  pSegmentEnd = pLength;
               }

               fSegmentEnd = indexOf(pathSeparator, filepath, fSegmentStart = fSegmentEnd + 1);
               if (fSegmentEnd < 0) {
                  fSegmentEnd = fLength;
               }
            }

            int pSegmentRestart;
            if (pSegmentStart >= pLength && freeTrailingDoubleStar || pSegmentEnd == pSegmentStart + 2 && pattern[pSegmentStart] == '*' && pattern[pSegmentStart + 1] == '*') {
               pSegmentEnd = indexOf(pathSeparator, pattern, pSegmentStart = pSegmentEnd + 1);
               if (pSegmentEnd < 0) {
                  pSegmentEnd = pLength;
               }

               pSegmentRestart = pSegmentStart;
            } else {
               if (pSegmentStart >= pLength) {
                  if (fSegmentStart >= fLength) {
                     return true;
                  }

                  return false;
               }

               pSegmentRestart = 0;
            }

            int fSegmentRestart = fSegmentStart;

            while(true) {
               while(fSegmentStart < fLength) {
                  if (pSegmentStart >= pLength) {
                     if (freeTrailingDoubleStar) {
                        return true;
                     }

                     pSegmentStart = pSegmentRestart;
                     pSegmentEnd = indexOf(pathSeparator, pattern, pSegmentRestart);
                     if (pSegmentEnd < 0) {
                        pSegmentEnd = pLength;
                     }

                     fSegmentRestart = indexOf(pathSeparator, filepath, fSegmentRestart + 1);
                     if (fSegmentRestart < 0) {
                        fSegmentRestart = fLength;
                     } else {
                        ++fSegmentRestart;
                     }

                     fSegmentStart = fSegmentRestart;
                     fSegmentEnd = indexOf(pathSeparator, filepath, fSegmentRestart);
                     if (fSegmentEnd < 0) {
                        fSegmentEnd = fLength;
                     }
                  } else if (pSegmentEnd == pSegmentStart + 2 && pattern[pSegmentStart] == '*' && pattern[pSegmentStart + 1] == '*') {
                     pSegmentEnd = indexOf(pathSeparator, pattern, pSegmentStart = pSegmentEnd + 1);
                     if (pSegmentEnd < 0) {
                        pSegmentEnd = pLength;
                     }

                     pSegmentRestart = pSegmentStart;
                     fSegmentRestart = fSegmentStart;
                     if (pSegmentStart >= pLength) {
                        return true;
                     }
                  } else if (!match(pattern, pSegmentStart, pSegmentEnd, filepath, fSegmentStart, fSegmentEnd, isCaseSensitive)) {
                     pSegmentStart = pSegmentRestart;
                     pSegmentEnd = indexOf(pathSeparator, pattern, pSegmentRestart);
                     if (pSegmentEnd < 0) {
                        pSegmentEnd = pLength;
                     }

                     fSegmentRestart = indexOf(pathSeparator, filepath, fSegmentRestart + 1);
                     if (fSegmentRestart < 0) {
                        fSegmentRestart = fLength;
                     } else {
                        ++fSegmentRestart;
                     }

                     fSegmentStart = fSegmentRestart;
                     fSegmentEnd = indexOf(pathSeparator, filepath, fSegmentRestart);
                     if (fSegmentEnd < 0) {
                        fSegmentEnd = fLength;
                     }
                  } else {
                     pSegmentEnd = indexOf(pathSeparator, pattern, pSegmentStart = pSegmentEnd + 1);
                     if (pSegmentEnd < 0) {
                        pSegmentEnd = pLength;
                     }

                     fSegmentEnd = indexOf(pathSeparator, filepath, fSegmentStart = fSegmentEnd + 1);
                     if (fSegmentEnd < 0) {
                        fSegmentEnd = fLength;
                     }
                  }
               }

               if (pSegmentRestart >= pSegmentEnd || fSegmentStart >= fLength && pSegmentStart >= pLength || pSegmentStart == pLength - 2 && pattern[pSegmentStart] == '*' && pattern[pSegmentStart + 1] == '*' || pSegmentStart == pLength && freeTrailingDoubleStar) {
                  return true;
               }

               return false;
            }
         }
      }
   }

   public static final int occurencesOf(char toBeFound, char[] array) {
      int count = 0;

      for(int i = 0; i < array.length; ++i) {
         if (toBeFound == array[i]) {
            ++count;
         }
      }

      return count;
   }

   public static final int occurencesOf(char toBeFound, char[] array, int start) {
      int count = 0;

      for(int i = start; i < array.length; ++i) {
         if (toBeFound == array[i]) {
            ++count;
         }
      }

      return count;
   }

   public static final int parseInt(char[] array, int start, int length) throws NumberFormatException {
      if (length == 1) {
         int result = array[start] - 48;
         if (result >= 0 && result <= 9) {
            return result;
         } else {
            throw new NumberFormatException("invalid digit");
         }
      } else {
         return Integer.parseInt(new String(array, start, length));
      }
   }

   public static final boolean prefixEquals(char[] prefix, char[] name) {
      int max = prefix.length;
      if (name.length < max) {
         return false;
      } else {
         int i = max;

         do {
            --i;
            if (i < 0) {
               return true;
            }
         } while(prefix[i] == name[i]);

         return false;
      }
   }

   public static final boolean prefixEquals(char[] prefix, char[] name, boolean isCaseSensitive) {
      return prefixEquals(prefix, name, isCaseSensitive, 0);
   }

   public static final boolean prefixEquals(char[] prefix, char[] name, boolean isCaseSensitive, int startIndex) {
      int max = prefix.length;
      if (name.length - startIndex < max) {
         return false;
      } else {
         int i;
         if (isCaseSensitive) {
            i = max;

            do {
               --i;
               if (i < 0) {
                  return true;
               }
            } while(prefix[i] == name[startIndex + i]);

            return false;
         } else {
            i = max;

            do {
               --i;
               if (i < 0) {
                  return true;
               }
            } while(ScannerHelper.toLowerCase(prefix[i]) == ScannerHelper.toLowerCase(name[startIndex + i]));

            return false;
         }
      }
   }

   public static final char[] remove(char[] array, char toBeRemoved) {
      if (array == null) {
         return null;
      } else {
         int length = array.length;
         if (length == 0) {
            return array;
         } else {
            char[] result = null;
            int count = 0;

            for(int i = 0; i < length; ++i) {
               char c = array[i];
               if (c == toBeRemoved) {
                  if (result == null) {
                     result = new char[length];
                     System.arraycopy(array, 0, result, 0, i);
                     count = i;
                  }
               } else if (result != null) {
                  result[count++] = c;
               }
            }

            if (result == null) {
               return array;
            } else {
               System.arraycopy(result, 0, result = new char[count], 0, count);
               return result;
            }
         }
      }
   }

   public static final void replace(char[] array, char toBeReplaced, char replacementChar) {
      if (toBeReplaced != replacementChar) {
         int i = 0;

         for(int max = array.length; i < max; ++i) {
            if (array[i] == toBeReplaced) {
               array[i] = replacementChar;
            }
         }
      }

   }

   public static final void replace(char[] array, char[] toBeReplaced, char replacementChar) {
      replace(array, toBeReplaced, replacementChar, 0, array.length);
   }

   public static final void replace(char[] array, char[] toBeReplaced, char replacementChar, int start, int end) {
      int i = end;

      while(true) {
         --i;
         if (i < start) {
            return;
         }

         int j = toBeReplaced.length;

         while(true) {
            --j;
            if (j < 0) {
               break;
            }

            if (array[i] == toBeReplaced[j]) {
               array[i] = replacementChar;
            }
         }
      }
   }

   public static final char[] replace(char[] array, char[] toBeReplaced, char[] replacementChars) {
      int max = array.length;
      int replacedLength = toBeReplaced.length;
      int replacementLength = replacementChars.length;
      int[] starts = new int[5];
      int occurrenceCount = 0;
      int index;
      if (!equals(toBeReplaced, replacementChars)) {
         int i = 0;

         while(i < max) {
            index = indexOf(toBeReplaced, array, true, i);
            if (index == -1) {
               ++i;
            } else {
               if (occurrenceCount == starts.length) {
                  System.arraycopy(starts, 0, starts = new int[occurrenceCount * 2], 0, occurrenceCount);
               }

               starts[occurrenceCount++] = index;
               i = index + replacedLength;
            }
         }
      }

      if (occurrenceCount == 0) {
         return array;
      } else {
         char[] result = new char[max + occurrenceCount * (replacementLength - replacedLength)];
         index = 0;
         int outStart = 0;

         for(int i = 0; i < occurrenceCount; ++i) {
            int offset = starts[i] - index;
            System.arraycopy(array, index, result, outStart, offset);
            index += offset;
            outStart += offset;
            System.arraycopy(replacementChars, 0, result, outStart, replacementLength);
            index += replacedLength;
            outStart += replacementLength;
         }

         System.arraycopy(array, index, result, outStart, max - index);
         return result;
      }
   }

   public static final char[] replaceOnCopy(char[] array, char toBeReplaced, char replacementChar) {
      char[] result = null;
      int i = 0;

      for(int length = array.length; i < length; ++i) {
         char c = array[i];
         if (c == toBeReplaced) {
            if (result == null) {
               result = new char[length];
               System.arraycopy(array, 0, result, 0, i);
            }

            result[i] = replacementChar;
         } else if (result != null) {
            result[i] = c;
         }
      }

      if (result == null) {
         return array;
      } else {
         return result;
      }
   }

   public static final char[][] splitAndTrimOn(char divider, char[] array) {
      int length = array == null ? 0 : array.length;
      if (length == 0) {
         return NO_CHAR_CHAR;
      } else {
         int wordCount = 1;

         for(int i = 0; i < length; ++i) {
            if (array[i] == divider) {
               ++wordCount;
            }
         }

         char[][] split = new char[wordCount][];
         int last = 0;
         int currentWord = 0;

         int i;
         int start;
         for(i = 0; i < length; ++i) {
            if (array[i] == divider) {
               start = last;

               int end;
               for(end = i - 1; start < i && array[start] == ' '; ++start) {
               }

               while(end > start && array[end] == ' ') {
                  --end;
               }

               split[currentWord] = new char[end - start + 1];
               System.arraycopy(array, start, split[currentWord++], 0, end - start + 1);
               last = i + 1;
            }
         }

         i = last;

         for(start = length - 1; i < length && array[i] == ' '; ++i) {
         }

         while(start > i && array[start] == ' ') {
            --start;
         }

         split[currentWord] = new char[start - i + 1];
         System.arraycopy(array, i, split[currentWord++], 0, start - i + 1);
         return split;
      }
   }

   public static final char[][] splitOn(char divider, char[] array) {
      int length = array == null ? 0 : array.length;
      if (length == 0) {
         return NO_CHAR_CHAR;
      } else {
         int wordCount = 1;

         for(int i = 0; i < length; ++i) {
            if (array[i] == divider) {
               ++wordCount;
            }
         }

         char[][] split = new char[wordCount][];
         int last = 0;
         int currentWord = 0;

         for(int i = 0; i < length; ++i) {
            if (array[i] == divider) {
               split[currentWord] = new char[i - last];
               System.arraycopy(array, last, split[currentWord++], 0, i - last);
               last = i + 1;
            }
         }

         split[currentWord] = new char[length - last];
         System.arraycopy(array, last, split[currentWord], 0, length - last);
         return split;
      }
   }

   public static final char[][] splitOn(char divider, char[] array, int start, int end) {
      int length = array == null ? 0 : array.length;
      if (length != 0 && start <= end) {
         int wordCount = 1;

         for(int i = start; i < end; ++i) {
            if (array[i] == divider) {
               ++wordCount;
            }
         }

         char[][] split = new char[wordCount][];
         int last = start;
         int currentWord = 0;

         for(int i = start; i < end; ++i) {
            if (array[i] == divider) {
               split[currentWord] = new char[i - last];
               System.arraycopy(array, last, split[currentWord++], 0, i - last);
               last = i + 1;
            }
         }

         split[currentWord] = new char[end - last];
         System.arraycopy(array, last, split[currentWord], 0, end - last);
         return split;
      } else {
         return NO_CHAR_CHAR;
      }
   }

   public static final char[][] splitOnWithEnclosures(char divider, char openEncl, char closeEncl, char[] array, int start, int end) {
      int length = array == null ? 0 : array.length;
      if (length != 0 && start <= end) {
         int wordCount = 1;
         int enclCount = 0;

         int nesting;
         for(nesting = start; nesting < end; ++nesting) {
            if (array[nesting] == openEncl) {
               ++enclCount;
            } else if (array[nesting] == divider) {
               ++wordCount;
            }
         }

         if (enclCount == 0) {
            return splitOn(divider, array, start, end);
         } else {
            nesting = 0;
            if (openEncl != divider && closeEncl != divider) {
               int[][] splitOffsets = new int[wordCount][2];
               int last = start;
               int currentWord = 0;
               int prevOffset = start;

               for(int i = start; i < end; ++i) {
                  if (array[i] == openEncl) {
                     ++nesting;
                  } else if (array[i] == closeEncl) {
                     if (nesting > 0) {
                        --nesting;
                     }
                  } else if (array[i] == divider && nesting == 0) {
                     splitOffsets[currentWord][0] = prevOffset;
                     last = splitOffsets[currentWord++][1] = i;
                     prevOffset = last + 1;
                  }
               }

               if (last < end - 1) {
                  splitOffsets[currentWord][0] = prevOffset;
                  splitOffsets[currentWord++][1] = end;
               }

               char[][] split = new char[currentWord][];

               for(int i = 0; i < currentWord; ++i) {
                  int sStart = splitOffsets[i][0];
                  int sEnd = splitOffsets[i][1];
                  int size = sEnd - sStart;
                  split[i] = new char[size];
                  System.arraycopy(array, sStart, split[i], 0, size);
               }

               return split;
            } else {
               return NO_CHAR_CHAR;
            }
         }
      } else {
         return NO_CHAR_CHAR;
      }
   }

   public static final char[][] subarray(char[][] array, int start, int end) {
      if (end == -1) {
         end = array.length;
      }

      if (start > end) {
         return null;
      } else if (start < 0) {
         return null;
      } else if (end > array.length) {
         return null;
      } else {
         char[][] result = new char[end - start][];
         System.arraycopy(array, start, result, 0, end - start);
         return result;
      }
   }

   public static final char[] subarray(char[] array, int start, int end) {
      if (end == -1) {
         end = array.length;
      }

      if (start > end) {
         return null;
      } else if (start < 0) {
         return null;
      } else if (end > array.length) {
         return null;
      } else {
         char[] result = new char[end - start];
         System.arraycopy(array, start, result, 0, end - start);
         return result;
      }
   }

   public static final char[] toLowerCase(char[] chars) {
      if (chars == null) {
         return null;
      } else {
         int length = chars.length;
         char[] lowerChars = null;

         for(int i = 0; i < length; ++i) {
            char c = chars[i];
            char lc = ScannerHelper.toLowerCase(c);
            if (c != lc || lowerChars != null) {
               if (lowerChars == null) {
                  System.arraycopy(chars, 0, lowerChars = new char[length], 0, i);
               }

               lowerChars[i] = lc;
            }
         }

         return lowerChars == null ? chars : lowerChars;
      }
   }

   public static final char[] toUpperCase(char[] chars) {
      if (chars == null) {
         return null;
      } else {
         int length = chars.length;
         char[] upperChars = null;

         for(int i = 0; i < length; ++i) {
            char c = chars[i];
            char lc = ScannerHelper.toUpperCase(c);
            if (c != lc || upperChars != null) {
               if (upperChars == null) {
                  System.arraycopy(chars, 0, upperChars = new char[length], 0, i);
               }

               upperChars[i] = lc;
            }
         }

         return upperChars == null ? chars : upperChars;
      }
   }

   public static final char[] trim(char[] chars) {
      if (chars == null) {
         return null;
      } else {
         int start = 0;
         int length = chars.length;

         int end;
         for(end = length - 1; start < length && chars[start] == ' '; ++start) {
         }

         while(end > start && chars[end] == ' ') {
            --end;
         }

         return start == 0 && end == length - 1 ? chars : subarray(chars, start, end + 1);
      }
   }

   public static final String toString(char[][] array) {
      char[] result = concatWith(array, '.');
      return new String(result);
   }

   public static final String[] toStrings(char[][] array) {
      if (array == null) {
         return NO_STRINGS;
      } else {
         int length = array.length;
         if (length == 0) {
            return NO_STRINGS;
         } else {
            String[] result = new String[length];

            for(int i = 0; i < length; ++i) {
               result[i] = new String(array[i]);
            }

            return result;
         }
      }
   }
}
