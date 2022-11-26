package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class ColognePhonetic implements StringEncoder {
   private static final char[] AEIJOUY = new char[]{'A', 'E', 'I', 'J', 'O', 'U', 'Y'};
   private static final char[] SCZ = new char[]{'S', 'C', 'Z'};
   private static final char[] WFPV = new char[]{'W', 'F', 'P', 'V'};
   private static final char[] GKQ = new char[]{'G', 'K', 'Q'};
   private static final char[] CKQ = new char[]{'C', 'K', 'Q'};
   private static final char[] AHKLOQRUX = new char[]{'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X'};
   private static final char[] SZ = new char[]{'S', 'Z'};
   private static final char[] AHOUKQX = new char[]{'A', 'H', 'O', 'U', 'K', 'Q', 'X'};
   private static final char[] TDX = new char[]{'T', 'D', 'X'};

   private static boolean arrayContains(char[] arr, char key) {
      char[] var2 = arr;
      int var3 = arr.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char element = var2[var4];
         if (element == key) {
            return true;
         }
      }

      return false;
   }

   public String colognePhonetic(String text) {
      if (text == null) {
         return null;
      } else {
         CologneInputBuffer input = new CologneInputBuffer(this.preprocess(text));
         CologneOutputBuffer output = new CologneOutputBuffer(input.length() * 2);
         char CHAR_FIRST_POS = true;
         char CHAR_IGNORE = true;
         char lastChar = '-';
         char lastCode = '/';

         while(true) {
            char nextChar;
            char chr;
            do {
               do {
                  do {
                     if (input.length() <= 0) {
                        return output.toString();
                     }

                     chr = input.removeNext();
                     if (input.length() > 0) {
                        nextChar = input.getNextChar();
                     } else {
                        nextChar = '-';
                     }
                  } while(chr == 'H');
               } while(chr < 'A');
            } while(chr > 'Z');

            char code;
            if (arrayContains(AEIJOUY, chr)) {
               code = '0';
            } else if (chr != 'B' && (chr != 'P' || nextChar == 'H')) {
               if ((chr == 'D' || chr == 'T') && !arrayContains(SCZ, nextChar)) {
                  code = '2';
               } else if (arrayContains(WFPV, chr)) {
                  code = '3';
               } else if (arrayContains(GKQ, chr)) {
                  code = '4';
               } else if (chr == 'X' && !arrayContains(CKQ, lastChar)) {
                  code = '4';
                  input.addLeft('S');
               } else if (chr != 'S' && chr != 'Z') {
                  if (chr == 'C') {
                     if (lastCode == '/') {
                        if (arrayContains(AHKLOQRUX, nextChar)) {
                           code = '4';
                        } else {
                           code = '8';
                        }
                     } else if (!arrayContains(SZ, lastChar) && arrayContains(AHOUKQX, nextChar)) {
                        code = '4';
                     } else {
                        code = '8';
                     }
                  } else if (arrayContains(TDX, chr)) {
                     code = '8';
                  } else if (chr == 'R') {
                     code = '7';
                  } else if (chr == 'L') {
                     code = '5';
                  } else if (chr != 'M' && chr != 'N') {
                     code = chr;
                  } else {
                     code = '6';
                  }
               } else {
                  code = '8';
               }
            } else {
               code = '1';
            }

            if (code != '-' && (lastCode != code && (code != '0' || lastCode == '/') || code < '0' || code > '8')) {
               output.addRight(code);
            }

            lastChar = chr;
            lastCode = code;
         }
      }
   }

   public Object encode(Object object) throws EncoderException {
      if (!(object instanceof String)) {
         throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + object.getClass().getName() + ".");
      } else {
         return this.encode((String)object);
      }
   }

   public String encode(String text) {
      return this.colognePhonetic(text);
   }

   public boolean isEncodeEqual(String text1, String text2) {
      return this.colognePhonetic(text1).equals(this.colognePhonetic(text2));
   }

   private char[] preprocess(String text) {
      char[] chrs = text.toUpperCase(Locale.GERMAN).toCharArray();

      for(int index = 0; index < chrs.length; ++index) {
         switch (chrs[index]) {
            case 'Ä':
               chrs[index] = 'A';
               break;
            case 'Ö':
               chrs[index] = 'O';
               break;
            case 'Ü':
               chrs[index] = 'U';
         }
      }

      return chrs;
   }

   private class CologneInputBuffer extends CologneBuffer {
      public CologneInputBuffer(char[] data) {
         super(data);
      }

      public void addLeft(char ch) {
         ++this.length;
         this.data[this.getNextPos()] = ch;
      }

      protected char[] copyData(int start, int length) {
         char[] newData = new char[length];
         System.arraycopy(this.data, this.data.length - this.length + start, newData, 0, length);
         return newData;
      }

      public char getNextChar() {
         return this.data[this.getNextPos()];
      }

      protected int getNextPos() {
         return this.data.length - this.length;
      }

      public char removeNext() {
         char ch = this.getNextChar();
         --this.length;
         return ch;
      }
   }

   private class CologneOutputBuffer extends CologneBuffer {
      public CologneOutputBuffer(int buffSize) {
         super(buffSize);
      }

      public void addRight(char chr) {
         this.data[this.length] = chr;
         ++this.length;
      }

      protected char[] copyData(int start, int length) {
         char[] newData = new char[length];
         System.arraycopy(this.data, start, newData, 0, length);
         return newData;
      }
   }

   private abstract class CologneBuffer {
      protected final char[] data;
      protected int length = 0;

      public CologneBuffer(char[] data) {
         this.data = data;
         this.length = data.length;
      }

      public CologneBuffer(int buffSize) {
         this.data = new char[buffSize];
         this.length = 0;
      }

      protected abstract char[] copyData(int var1, int var2);

      public int length() {
         return this.length;
      }

      public String toString() {
         return new String(this.copyData(0, this.length));
      }
   }
}
