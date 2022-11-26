package com.bea.core.repackaged.aspectj.asm.internal;

public class CharOperation {
   public static final char[][] NO_CHAR_CHAR = new char[0][];
   public static final char[] NO_CHAR = new char[0];

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

   public static final char[][] subarray(char[][] array, int start, int end) {
      if (end == -1) {
         end = array.length;
      }

      if (start > end) {
         return (char[][])null;
      } else if (start < 0) {
         return (char[][])null;
      } else if (end > array.length) {
         return (char[][])null;
      } else {
         char[][] result = new char[end - start][];
         System.arraycopy(array, start, result, 0, end - start);
         return result;
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

   public static final int indexOf(char toBeFound, char[] array) {
      for(int i = 0; i < array.length; ++i) {
         if (toBeFound == array[i]) {
            return i;
         }
      }

      return -1;
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

   public static final String toString(char[][] array) {
      char[] result = concatWith(array, '.');
      return new String(result);
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

   public static final int hashCode(char[] array) {
      int length = array.length;
      int hash = length == 0 ? 31 : array[0];
      int i;
      if (length < 8) {
         i = length;

         while(true) {
            --i;
            if (i <= 0) {
               break;
            }

            hash = hash * 31 + array[i];
         }
      } else {
         i = length - 1;

         for(int last = i > 16 ? i - 16 : 0; i > last; i -= 2) {
            hash = hash * 31 + array[i];
         }
      }

      return hash & Integer.MAX_VALUE;
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
}
