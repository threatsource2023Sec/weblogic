package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.core.compiler.InvalidInputException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScannerHelper {
   public static final long[] Bits = new long[]{1L, 2L, 4L, 8L, 16L, 32L, 64L, 128L, 256L, 512L, 1024L, 2048L, 4096L, 8192L, 16384L, 32768L, 65536L, 131072L, 262144L, 524288L, 1048576L, 2097152L, 4194304L, 8388608L, 16777216L, 33554432L, 67108864L, 134217728L, 268435456L, 536870912L, 1073741824L, 2147483648L, 4294967296L, 8589934592L, 17179869184L, 34359738368L, 68719476736L, 137438953472L, 274877906944L, 549755813888L, 1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, 17592186044416L, 35184372088832L, 70368744177664L, 140737488355328L, 281474976710656L, 562949953421312L, 1125899906842624L, 2251799813685248L, 4503599627370496L, 9007199254740992L, 18014398509481984L, 36028797018963968L, 72057594037927936L, 144115188075855872L, 288230376151711744L, 576460752303423488L, 1152921504606846976L, 2305843009213693952L, 4611686018427387904L, Long.MIN_VALUE};
   private static final int START_INDEX = 0;
   private static final int PART_INDEX = 1;
   private static long[][][] Tables;
   private static long[][][] Tables7;
   private static long[][][] Tables8;
   private static long[][][] Tables9;
   private static long[][][] Tables11;
   private static long[][][] Tables12;
   public static final int MAX_OBVIOUS = 128;
   public static final int[] OBVIOUS_IDENT_CHAR_NATURES = new int[128];
   public static final int C_JLS_SPACE = 256;
   public static final int C_SPECIAL = 128;
   public static final int C_IDENT_START = 64;
   public static final int C_UPPER_LETTER = 32;
   public static final int C_LOWER_LETTER = 16;
   public static final int C_IDENT_PART = 8;
   public static final int C_DIGIT = 4;
   public static final int C_SEPARATOR = 2;
   public static final int C_SPACE = 1;

   static {
      OBVIOUS_IDENT_CHAR_NATURES[0] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[1] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[2] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[3] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[4] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[5] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[6] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[7] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[8] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[14] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[15] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[16] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[17] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[18] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[19] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[20] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[21] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[22] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[23] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[24] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[25] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[26] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[27] = 8;
      OBVIOUS_IDENT_CHAR_NATURES[127] = 8;

      int i;
      for(i = 48; i <= 57; ++i) {
         OBVIOUS_IDENT_CHAR_NATURES[i] = 12;
      }

      for(i = 97; i <= 122; ++i) {
         OBVIOUS_IDENT_CHAR_NATURES[i] = 88;
      }

      for(i = 65; i <= 90; ++i) {
         OBVIOUS_IDENT_CHAR_NATURES[i] = 104;
      }

      OBVIOUS_IDENT_CHAR_NATURES[95] = 200;
      OBVIOUS_IDENT_CHAR_NATURES[36] = 200;
      OBVIOUS_IDENT_CHAR_NATURES[9] = 257;
      OBVIOUS_IDENT_CHAR_NATURES[10] = 257;
      OBVIOUS_IDENT_CHAR_NATURES[11] = 1;
      OBVIOUS_IDENT_CHAR_NATURES[12] = 257;
      OBVIOUS_IDENT_CHAR_NATURES[13] = 257;
      OBVIOUS_IDENT_CHAR_NATURES[28] = 1;
      OBVIOUS_IDENT_CHAR_NATURES[29] = 1;
      OBVIOUS_IDENT_CHAR_NATURES[30] = 1;
      OBVIOUS_IDENT_CHAR_NATURES[31] = 1;
      OBVIOUS_IDENT_CHAR_NATURES[32] = 257;
      OBVIOUS_IDENT_CHAR_NATURES[46] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[58] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[59] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[44] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[91] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[93] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[40] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[41] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[123] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[125] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[43] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[45] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[42] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[47] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[61] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[38] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[124] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[63] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[60] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[62] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[33] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[37] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[94] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[126] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[34] = 2;
      OBVIOUS_IDENT_CHAR_NATURES[39] = 2;
   }

   static void initializeTable() {
      Tables = initializeTables("unicode");
   }

   static void initializeTable17() {
      Tables7 = initializeTables("unicode6");
   }

   static void initializeTable18() {
      Tables8 = initializeTables("unicode6_2");
   }

   static void initializeTable19() {
      Tables9 = initializeTables("unicode8");
   }

   static void initializeTableJava11() {
      Tables11 = initializeTables("unicode10");
   }

   static void initializeTableJava12() {
      Tables12 = initializeTables("unicode11");
   }

   static long[][][] initializeTables(String unicode_path) {
      long[][][] tempTable = new long[][][]{new long[3][], new long[4][]};

      Throwable var2;
      Object var3;
      DataInputStream inputStream;
      long[] readValues;
      int i;
      try {
         var2 = null;
         var3 = null;

         try {
            inputStream = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(unicode_path + "/start0.rsc")));

            try {
               readValues = new long[1024];

               for(i = 0; i < 1024; ++i) {
                  readValues[i] = inputStream.readLong();
               }

               tempTable[0][0] = readValues;
            } finally {
               if (inputStream != null) {
                  inputStream.close();
               }

            }
         } catch (Throwable var424) {
            if (var2 == null) {
               var2 = var424;
            } else if (var2 != var424) {
               var2.addSuppressed(var424);
            }

            throw var2;
         }
      } catch (FileNotFoundException var425) {
         var425.printStackTrace();
      } catch (IOException var426) {
         var426.printStackTrace();
      }

      try {
         var2 = null;
         var3 = null;

         try {
            inputStream = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(unicode_path + "/start1.rsc")));

            try {
               readValues = new long[1024];

               for(i = 0; i < 1024; ++i) {
                  readValues[i] = inputStream.readLong();
               }

               tempTable[0][1] = readValues;
            } finally {
               if (inputStream != null) {
                  inputStream.close();
               }

            }
         } catch (Throwable var420) {
            if (var2 == null) {
               var2 = var420;
            } else if (var2 != var420) {
               var2.addSuppressed(var420);
            }

            throw var2;
         }
      } catch (FileNotFoundException var421) {
         var421.printStackTrace();
      } catch (IOException var422) {
         var422.printStackTrace();
      }

      try {
         var2 = null;
         var3 = null;

         try {
            inputStream = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(unicode_path + "/start2.rsc")));

            try {
               readValues = new long[1024];

               for(i = 0; i < 1024; ++i) {
                  readValues[i] = inputStream.readLong();
               }

               tempTable[0][2] = readValues;
            } finally {
               if (inputStream != null) {
                  inputStream.close();
               }

            }
         } catch (Throwable var416) {
            if (var2 == null) {
               var2 = var416;
            } else if (var2 != var416) {
               var2.addSuppressed(var416);
            }

            throw var2;
         }
      } catch (FileNotFoundException var417) {
         var417.printStackTrace();
      } catch (IOException var418) {
         var418.printStackTrace();
      }

      try {
         var2 = null;
         var3 = null;

         try {
            inputStream = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(unicode_path + "/part0.rsc")));

            try {
               readValues = new long[1024];

               for(i = 0; i < 1024; ++i) {
                  readValues[i] = inputStream.readLong();
               }

               tempTable[1][0] = readValues;
            } finally {
               if (inputStream != null) {
                  inputStream.close();
               }

            }
         } catch (Throwable var412) {
            if (var2 == null) {
               var2 = var412;
            } else if (var2 != var412) {
               var2.addSuppressed(var412);
            }

            throw var2;
         }
      } catch (FileNotFoundException var413) {
         var413.printStackTrace();
      } catch (IOException var414) {
         var414.printStackTrace();
      }

      try {
         var2 = null;
         var3 = null;

         try {
            inputStream = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(unicode_path + "/part1.rsc")));

            try {
               readValues = new long[1024];

               for(i = 0; i < 1024; ++i) {
                  readValues[i] = inputStream.readLong();
               }

               tempTable[1][1] = readValues;
            } finally {
               if (inputStream != null) {
                  inputStream.close();
               }

            }
         } catch (Throwable var408) {
            if (var2 == null) {
               var2 = var408;
            } else if (var2 != var408) {
               var2.addSuppressed(var408);
            }

            throw var2;
         }
      } catch (FileNotFoundException var409) {
         var409.printStackTrace();
      } catch (IOException var410) {
         var410.printStackTrace();
      }

      try {
         var2 = null;
         var3 = null;

         try {
            inputStream = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(unicode_path + "/part2.rsc")));

            try {
               readValues = new long[1024];

               for(i = 0; i < 1024; ++i) {
                  readValues[i] = inputStream.readLong();
               }

               tempTable[1][2] = readValues;
            } finally {
               if (inputStream != null) {
                  inputStream.close();
               }

            }
         } catch (Throwable var404) {
            if (var2 == null) {
               var2 = var404;
            } else if (var2 != var404) {
               var2.addSuppressed(var404);
            }

            throw var2;
         }
      } catch (FileNotFoundException var405) {
         var405.printStackTrace();
      } catch (IOException var406) {
         var406.printStackTrace();
      }

      try {
         var2 = null;
         var3 = null;

         try {
            inputStream = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(unicode_path + "/part14.rsc")));

            try {
               readValues = new long[1024];

               for(i = 0; i < 1024; ++i) {
                  readValues[i] = inputStream.readLong();
               }

               tempTable[1][3] = readValues;
            } finally {
               if (inputStream != null) {
                  inputStream.close();
               }

            }
         } catch (Throwable var400) {
            if (var2 == null) {
               var2 = var400;
            } else if (var2 != var400) {
               var2.addSuppressed(var400);
            }

            throw var2;
         }
      } catch (FileNotFoundException var401) {
         var401.printStackTrace();
      } catch (IOException var402) {
         var402.printStackTrace();
      }

      return tempTable;
   }

   private static final boolean isBitSet(long[] values, int i) {
      try {
         return (values[i / 64] & Bits[i % 64]) != 0L;
      } catch (NullPointerException var2) {
         return false;
      }
   }

   public static boolean isJavaIdentifierPart(char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 8) != 0;
      } else {
         return Character.isJavaIdentifierPart(c);
      }
   }

   public static boolean isJavaIdentifierPart(long complianceLevel, char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 8) != 0;
      } else {
         return isJavaIdentifierPart(complianceLevel, (int)c);
      }
   }

   private static boolean isJavaIdentifierPart0(int codePoint, long[][][] tables) {
      switch ((codePoint & 2031616) >> 16) {
         case 0:
            return isBitSet(tables[1][0], codePoint & '\uffff');
         case 1:
            return isBitSet(tables[1][1], codePoint & '\uffff');
         case 2:
            return isBitSet(tables[1][2], codePoint & '\uffff');
         case 14:
            return isBitSet(tables[1][3], codePoint & '\uffff');
         default:
            return false;
      }
   }

   public static boolean isJavaIdentifierPart(long complianceLevel, int codePoint) {
      if (complianceLevel <= 3276800L) {
         if (Tables == null) {
            initializeTable();
         }

         return isJavaIdentifierPart0(codePoint, Tables);
      } else if (complianceLevel <= 3342336L) {
         if (Tables7 == null) {
            initializeTable17();
         }

         return isJavaIdentifierPart0(codePoint, Tables7);
      } else if (complianceLevel <= 3407872L) {
         if (Tables8 == null) {
            initializeTable18();
         }

         return isJavaIdentifierPart0(codePoint, Tables8);
      } else if (complianceLevel <= 3538944L) {
         if (Tables9 == null) {
            initializeTable19();
         }

         return isJavaIdentifierPart0(codePoint, Tables9);
      } else if (complianceLevel <= 3604480L) {
         if (Tables11 == null) {
            initializeTableJava11();
         }

         return isJavaIdentifierPart0(codePoint, Tables11);
      } else {
         if (Tables12 == null) {
            initializeTableJava12();
         }

         return isJavaIdentifierPart0(codePoint, Tables12);
      }
   }

   public static boolean isJavaIdentifierPart(long complianceLevel, char high, char low) {
      return isJavaIdentifierPart(complianceLevel, toCodePoint(high, low));
   }

   public static boolean isJavaIdentifierStart(char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 64) != 0;
      } else {
         return Character.isJavaIdentifierStart(c);
      }
   }

   public static boolean isJavaIdentifierStart(long complianceLevel, char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 64) != 0;
      } else {
         return isJavaIdentifierStart(complianceLevel, (int)c);
      }
   }

   public static boolean isJavaIdentifierStart(long complianceLevel, char high, char low) {
      return isJavaIdentifierStart(complianceLevel, toCodePoint(high, low));
   }

   private static boolean isJavaIdentifierStart0(int codePoint, long[][][] tables) {
      switch ((codePoint & 2031616) >> 16) {
         case 0:
            return isBitSet(tables[0][0], codePoint & '\uffff');
         case 1:
            return isBitSet(tables[0][1], codePoint & '\uffff');
         case 2:
            return isBitSet(tables[0][2], codePoint & '\uffff');
         default:
            return false;
      }
   }

   public static boolean isJavaIdentifierStart(long complianceLevel, int codePoint) {
      if (complianceLevel <= 3276800L) {
         if (Tables == null) {
            initializeTable();
         }

         return isJavaIdentifierStart0(codePoint, Tables);
      } else if (complianceLevel <= 3342336L) {
         if (Tables7 == null) {
            initializeTable17();
         }

         return isJavaIdentifierStart0(codePoint, Tables7);
      } else if (complianceLevel <= 3407872L) {
         if (Tables8 == null) {
            initializeTable18();
         }

         return isJavaIdentifierStart0(codePoint, Tables8);
      } else if (complianceLevel <= 3538944L) {
         if (Tables9 == null) {
            initializeTable19();
         }

         return isJavaIdentifierStart0(codePoint, Tables9);
      } else if (complianceLevel <= 3604480L) {
         if (Tables11 == null) {
            initializeTableJava11();
         }

         return isJavaIdentifierStart0(codePoint, Tables11);
      } else {
         if (Tables12 == null) {
            initializeTableJava12();
         }

         return isJavaIdentifierStart0(codePoint, Tables12);
      }
   }

   private static int toCodePoint(char high, char low) {
      return (high - '\ud800') * 1024 + (low - '\udc00') + 65536;
   }

   public static boolean isDigit(char c) throws InvalidInputException {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 4) != 0;
      } else if (Character.isDigit(c)) {
         throw new InvalidInputException("Invalid_Digit");
      } else {
         return false;
      }
   }

   public static int digit(char c, int radix) {
      if (c < 128) {
         switch (radix) {
            case 8:
               if (c >= '0' && c <= '7') {
                  return c - 48;
               }

               return -1;
            case 10:
               if (c >= '0' && c <= '9') {
                  return c - 48;
               }

               return -1;
            case 16:
               if (c >= '0' && c <= '9') {
                  return c - 48;
               }

               if (c >= 'A' && c <= 'F') {
                  return c - 65 + 10;
               }

               if (c >= 'a' && c <= 'f') {
                  return c - 97 + 10;
               }

               return -1;
         }
      }

      return Character.digit(c, radix);
   }

   public static int getNumericValue(char c) {
      if (c < 128) {
         switch (OBVIOUS_IDENT_CHAR_NATURES[c]) {
            case 4:
               return c - 48;
            case 16:
               return 10 + c - 97;
            case 32:
               return 10 + c - 65;
         }
      }

      return Character.getNumericValue(c);
   }

   public static int getHexadecimalValue(char c) {
      switch (c) {
         case '0':
            return 0;
         case '1':
            return 1;
         case '2':
            return 2;
         case '3':
            return 3;
         case '4':
            return 4;
         case '5':
            return 5;
         case '6':
            return 6;
         case '7':
            return 7;
         case '8':
            return 8;
         case '9':
            return 9;
         case ':':
         case ';':
         case '<':
         case '=':
         case '>':
         case '?':
         case '@':
         case 'G':
         case 'H':
         case 'I':
         case 'J':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'S':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         case 'Z':
         case '[':
         case '\\':
         case ']':
         case '^':
         case '_':
         case '`':
         default:
            return -1;
         case 'A':
         case 'a':
            return 10;
         case 'B':
         case 'b':
            return 11;
         case 'C':
         case 'c':
            return 12;
         case 'D':
         case 'd':
            return 13;
         case 'E':
         case 'e':
            return 14;
         case 'F':
         case 'f':
            return 15;
      }
   }

   public static char toUpperCase(char c) {
      if (c < 128) {
         if ((OBVIOUS_IDENT_CHAR_NATURES[c] & 32) != 0) {
            return c;
         }

         if ((OBVIOUS_IDENT_CHAR_NATURES[c] & 16) != 0) {
            return (char)(c - 32);
         }
      }

      return Character.toUpperCase(c);
   }

   public static char toLowerCase(char c) {
      if (c < 128) {
         if ((OBVIOUS_IDENT_CHAR_NATURES[c] & 16) != 0) {
            return c;
         }

         if ((OBVIOUS_IDENT_CHAR_NATURES[c] & 32) != 0) {
            return (char)(32 + c);
         }
      }

      return Character.toLowerCase(c);
   }

   public static boolean isLowerCase(char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 16) != 0;
      } else {
         return Character.isLowerCase(c);
      }
   }

   public static boolean isUpperCase(char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 32) != 0;
      } else {
         return Character.isUpperCase(c);
      }
   }

   public static boolean isWhitespace(char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 1) != 0;
      } else {
         return Character.isWhitespace(c);
      }
   }

   public static boolean isLetter(char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 48) != 0;
      } else {
         return Character.isLetter(c);
      }
   }

   public static boolean isLetterOrDigit(char c) {
      if (c < 128) {
         return (OBVIOUS_IDENT_CHAR_NATURES[c] & 52) != 0;
      } else {
         return Character.isLetterOrDigit(c);
      }
   }
}
