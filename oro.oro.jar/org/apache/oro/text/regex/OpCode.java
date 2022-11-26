package org.apache.oro.text.regex;

final class OpCode {
   static final char _END = '\u0000';
   static final char _BOL = '\u0001';
   static final char _MBOL = '\u0002';
   static final char _SBOL = '\u0003';
   static final char _EOL = '\u0004';
   static final char _MEOL = '\u0005';
   static final char _SEOL = '\u0006';
   static final char _ANY = '\u0007';
   static final char _SANY = '\b';
   static final char _ANYOF = '\t';
   static final char _CURLY = '\n';
   static final char _CURLYX = '\u000b';
   static final char _BRANCH = '\f';
   static final char _BACK = '\r';
   static final char _EXACTLY = '\u000e';
   static final char _NOTHING = '\u000f';
   static final char _STAR = '\u0010';
   static final char _PLUS = '\u0011';
   static final char _ALNUM = '\u0012';
   static final char _NALNUM = '\u0013';
   static final char _BOUND = '\u0014';
   static final char _NBOUND = '\u0015';
   static final char _SPACE = '\u0016';
   static final char _NSPACE = '\u0017';
   static final char _DIGIT = '\u0018';
   static final char _NDIGIT = '\u0019';
   static final char _REF = '\u001a';
   static final char _OPEN = '\u001b';
   static final char _CLOSE = '\u001c';
   static final char _MINMOD = '\u001d';
   static final char _GBOL = '\u001e';
   static final char _IFMATCH = '\u001f';
   static final char _UNLESSM = ' ';
   static final char _SUCCEED = '!';
   static final char _WHILEM = '"';
   static final char _ANYOFUN = '#';
   static final char _NANYOFUN = '$';
   static final char _RANGE = '%';
   static final char _ALPHA = '&';
   static final char _BLANK = '\'';
   static final char _CNTRL = '(';
   static final char _GRAPH = ')';
   static final char _LOWER = '*';
   static final char _PRINT = '+';
   static final char _PUNCT = ',';
   static final char _UPPER = '-';
   static final char _XDIGIT = '.';
   static final char _OPCODE = '/';
   static final char _NOPCODE = '0';
   static final char _ONECHAR = '1';
   static final char _ALNUMC = '2';
   static final char _ASCII = '3';
   static final int[] _operandLength = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   static final char[] _opType = new char[]{'\u0000', '\u0001', '\u0001', '\u0001', '\u0004', '\u0004', '\u0004', '\u0007', '\u0007', '\t', '\n', '\n', '\f', '\r', '\u000e', '\u000f', '\u0010', '\u0011', '\u0012', '\u0013', '\u0014', '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001a', '\u001b', '\u001c', '\u001d', '\u0001', '\f', '\f', '\u0000', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3'};
   static final char[] _opLengthVaries = new char[]{'\f', '\r', '\u0010', '\u0011', '\n', '\u000b', '\u001a', '"'};
   static final char[] _opLengthOne = new char[]{'\u0007', '\b', '\t', '\u0012', '\u0013', '\u0016', '\u0017', '\u0018', '\u0019', '#', '$', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3'};
   static final int _NULL_OFFSET = -1;
   static final char _NULL_POINTER = '\u0000';

   private OpCode() {
   }

   static final int _getNextOffset(char[] var0, int var1) {
      return var0[var1 + 1];
   }

   static final char _getArg1(char[] var0, int var1) {
      return var0[var1 + 2];
   }

   static final char _getArg2(char[] var0, int var1) {
      return var0[var1 + 3];
   }

   static final int _getOperand(int var0) {
      return var0 + 2;
   }

   static final boolean _isInArray(char var0, char[] var1, int var2) {
      while(true) {
         if (var2 < var1.length) {
            if (var0 != var1[var2++]) {
               continue;
            }

            return true;
         }

         return false;
      }
   }

   static final int _getNextOperator(int var0) {
      return var0 + 2;
   }

   static final int _getPrevOperator(int var0) {
      return var0 - 2;
   }

   static final int _getNext(char[] var0, int var1) {
      if (var0 == null) {
         return -1;
      } else {
         int var2 = _getNextOffset(var0, var1);
         if (var2 == 0) {
            return -1;
         } else {
            return var0[var1] == '\r' ? var1 - var2 : var1 + var2;
         }
      }
   }

   static final boolean _isWordCharacter(char var0) {
      return Character.isLetterOrDigit(var0) || var0 == '_';
   }
}
