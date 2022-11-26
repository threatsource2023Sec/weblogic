package org.python.google.common.base;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible(
   emulated = true
)
public final class Utf8 {
   public static int encodedLength(CharSequence sequence) {
      int utf16Length = sequence.length();
      int utf8Length = utf16Length;

      int i;
      for(i = 0; i < utf16Length && sequence.charAt(i) < 128; ++i) {
      }

      while(i < utf16Length) {
         char c = sequence.charAt(i);
         if (c >= 2048) {
            utf8Length += encodedLengthGeneral(sequence, i);
            break;
         }

         utf8Length += 127 - c >>> 31;
         ++i;
      }

      if (utf8Length < utf16Length) {
         throw new IllegalArgumentException("UTF-8 length does not fit in int: " + ((long)utf8Length + 4294967296L));
      } else {
         return utf8Length;
      }
   }

   private static int encodedLengthGeneral(CharSequence sequence, int start) {
      int utf16Length = sequence.length();
      int utf8Length = 0;

      for(int i = start; i < utf16Length; ++i) {
         char c = sequence.charAt(i);
         if (c < 2048) {
            utf8Length += 127 - c >>> 31;
         } else {
            utf8Length += 2;
            if ('\ud800' <= c && c <= '\udfff') {
               if (Character.codePointAt(sequence, i) == c) {
                  throw new IllegalArgumentException(unpairedSurrogateMsg(i));
               }

               ++i;
            }
         }
      }

      return utf8Length;
   }

   public static boolean isWellFormed(byte[] bytes) {
      return isWellFormed(bytes, 0, bytes.length);
   }

   public static boolean isWellFormed(byte[] bytes, int off, int len) {
      int end = off + len;
      Preconditions.checkPositionIndexes(off, end, bytes.length);

      for(int i = off; i < end; ++i) {
         if (bytes[i] < 0) {
            return isWellFormedSlowPath(bytes, i, end);
         }
      }

      return true;
   }

   private static boolean isWellFormedSlowPath(byte[] bytes, int off, int end) {
      int index = off;

      while(true) {
         byte byte1;
         do {
            if (index >= end) {
               return true;
            }
         } while((byte1 = bytes[index++]) >= 0);

         if (byte1 < -32) {
            if (index == end) {
               return false;
            }

            if (byte1 < -62 || bytes[index++] > -65) {
               return false;
            }
         } else {
            byte byte2;
            if (byte1 < -16) {
               if (index + 1 >= end) {
                  return false;
               }

               byte2 = bytes[index++];
               if (byte2 > -65 || byte1 == -32 && byte2 < -96 || byte1 == -19 && -96 <= byte2 || bytes[index++] > -65) {
                  return false;
               }
            } else {
               if (index + 2 >= end) {
                  return false;
               }

               byte2 = bytes[index++];
               if (byte2 > -65 || (byte1 << 28) + (byte2 - -112) >> 30 != 0 || bytes[index++] > -65 || bytes[index++] > -65) {
                  return false;
               }
            }
         }
      }
   }

   private static String unpairedSurrogateMsg(int i) {
      return "Unpaired surrogate at index " + i;
   }

   private Utf8() {
   }
}
