package com.bea.core.repackaged.aspectj.asm.internal;

public class NameConvertor {
   private static final char BOOLEAN = 'Z';
   private static final char BYTE = 'B';
   private static final char CHAR = 'C';
   private static final char DOUBLE = 'D';
   private static final char FLOAT = 'F';
   private static final char INT = 'I';
   private static final char LONG = 'J';
   private static final char SHORT = 'S';
   private static final char ARRAY = '[';
   private static final char RESOLVED = 'L';
   private static final char UNRESOLVED = 'Q';
   public static final char PARAMETERIZED = 'P';
   private static final char[] BOOLEAN_NAME = new char[]{'b', 'o', 'o', 'l', 'e', 'a', 'n'};
   private static final char[] BYTE_NAME = new char[]{'b', 'y', 't', 'e'};
   private static final char[] CHAR_NAME = new char[]{'c', 'h', 'a', 'r'};
   private static final char[] DOUBLE_NAME = new char[]{'d', 'o', 'u', 'b', 'l', 'e'};
   private static final char[] FLOAT_NAME = new char[]{'f', 'l', 'o', 'a', 't'};
   private static final char[] INT_NAME = new char[]{'i', 'n', 't'};
   private static final char[] LONG_NAME = new char[]{'l', 'o', 'n', 'g'};
   private static final char[] SHORT_NAME = new char[]{'s', 'h', 'o', 'r', 't'};
   private static final char[] SQUARE_BRACKETS = new char[]{'[', ']'};
   private static final char[] GREATER_THAN = new char[]{'>'};
   private static final char[] LESS_THAN = new char[]{'<'};
   private static final char[] COMMA = new char[]{','};
   private static final char[] BACKSLASH_LESSTHAN = new char[]{'\\', '<'};
   private static final char[] SEMICOLON = new char[]{';'};

   public static char[] convertFromSignature(char[] c) {
      int lt = CharOperation.indexOf('<', c);
      int sc = CharOperation.indexOf(';', c);
      int gt = CharOperation.indexOf('>', c);
      int smallest = false;
      if (lt == -1 && sc == -1 && gt == -1) {
         return getFullyQualifiedTypeName(c);
      } else {
         int smallest;
         if (lt == -1 || sc != -1 && lt > sc || gt != -1 && lt > gt) {
            if (sc == -1 || lt != -1 && sc > lt || gt != -1 && sc > gt) {
               smallest = gt;
            } else {
               smallest = sc;
            }
         } else {
            smallest = lt;
         }

         char[] first = CharOperation.subarray((char[])c, 0, smallest);
         char[] second = CharOperation.subarray(c, smallest + 1, c.length);
         if (smallest == 0 && first.length == 0 && c[0] == '>') {
            return GREATER_THAN;
         } else if (first.length == 1 && second.length == 0) {
            return first;
         } else if (second.length != 0 && (second.length != 1 || second[0] != ';')) {
            char[] inclComma;
            if (smallest == lt) {
               inclComma = CharOperation.concat(convertFromSignature(first), LESS_THAN);
               return CharOperation.concat(inclComma, convertFromSignature(second));
            } else if (smallest == gt) {
               inclComma = CharOperation.concat(convertFromSignature(first), GREATER_THAN);
               return CharOperation.concat(inclComma, convertFromSignature(second));
            } else if (second.length != 2) {
               inclComma = CharOperation.concat(convertFromSignature(first), COMMA);
               return CharOperation.concat(inclComma, convertFromSignature(second));
            } else {
               return CharOperation.concat(convertFromSignature(first), convertFromSignature(second));
            }
         } else {
            return convertFromSignature(first);
         }
      }
   }

   private static char[] getFullyQualifiedTypeName(char[] c) {
      if (c.length == 0) {
         return c;
      } else if (c[0] == 'Z') {
         return BOOLEAN_NAME;
      } else if (c[0] == 'B') {
         return BYTE_NAME;
      } else if (c[0] == 'C') {
         return CHAR_NAME;
      } else if (c[0] == 'D') {
         return DOUBLE_NAME;
      } else if (c[0] == 'F') {
         return FLOAT_NAME;
      } else if (c[0] == 'I') {
         return INT_NAME;
      } else if (c[0] == 'J') {
         return LONG_NAME;
      } else if (c[0] == 'S') {
         return SHORT_NAME;
      } else if (c[0] == '[') {
         return CharOperation.concat(getFullyQualifiedTypeName(CharOperation.subarray((char[])c, 1, c.length)), SQUARE_BRACKETS);
      } else {
         char[] type = CharOperation.subarray((char[])c, 1, c.length);
         CharOperation.replace(type, '/', '.');
         return type;
      }
   }

   public static char[] createShortName(char[] c, boolean haveFullyQualifiedAtLeastOneThing, boolean needsFullyQualifiedFirstEntry) {
      char[] ret;
      if (c[0] == '[') {
         ret = CharOperation.concat(new char[]{'\\', '['}, createShortName(CharOperation.subarray((char[])c, 1, c.length), haveFullyQualifiedAtLeastOneThing, needsFullyQualifiedFirstEntry));
         return ret;
      } else if (c[0] == '+') {
         ret = CharOperation.concat(new char[]{'+'}, createShortName(CharOperation.subarray((char[])c, 1, c.length), haveFullyQualifiedAtLeastOneThing, needsFullyQualifiedFirstEntry));
         return ret;
      } else if (c[0] == '*') {
         return c;
      } else {
         int lt = CharOperation.indexOf('<', c);
         int sc = CharOperation.indexOf(';', c);
         int gt = CharOperation.indexOf('>', c);
         int smallest = false;
         if (lt == -1 && sc == -1 && gt == -1) {
            return !needsFullyQualifiedFirstEntry ? getTypeName(c, true) : getTypeName(c, haveFullyQualifiedAtLeastOneThing);
         } else {
            int smallest;
            if (lt != -1 && (sc == -1 || lt <= sc) && (gt == -1 || lt <= gt)) {
               smallest = lt;
            } else if (sc == -1 || lt != -1 && sc > lt || gt != -1 && sc > gt) {
               smallest = gt;
            } else {
               smallest = sc;
            }

            char[] first = CharOperation.subarray((char[])c, 0, smallest);
            char[] second = CharOperation.subarray(c, smallest + 1, c.length);
            if (smallest == 0 && first.length == 0 && c[0] == '>') {
               return c;
            } else if (first.length == 1 && second.length == 0) {
               return first;
            } else if (second.length == 0 || second.length == 1 && second[0] == ';') {
               return CharOperation.concat(createShortName(first, haveFullyQualifiedAtLeastOneThing, needsFullyQualifiedFirstEntry), new char[]{';'});
            } else {
               char[] firstTypeParam;
               if (smallest == lt) {
                  firstTypeParam = CharOperation.concat(createShortName(first, haveFullyQualifiedAtLeastOneThing, true), BACKSLASH_LESSTHAN);
                  return CharOperation.concat(firstTypeParam, createShortName(second, true, false));
               } else if (smallest == gt) {
                  firstTypeParam = CharOperation.concat(createShortName(first, haveFullyQualifiedAtLeastOneThing, needsFullyQualifiedFirstEntry), GREATER_THAN);
                  return CharOperation.concat(firstTypeParam, createShortName(second, true, false));
               } else {
                  firstTypeParam = CharOperation.concat(createShortName(first, haveFullyQualifiedAtLeastOneThing, false), SEMICOLON);
                  return CharOperation.concat(firstTypeParam, createShortName(second, true, false));
               }
            }
         }
      }
   }

   public static char[] getTypeName(char[] name, boolean haveFullyQualifiedAtLeastOneThing) {
      if (!haveFullyQualifiedAtLeastOneThing) {
         char[] sub;
         if (name[0] != 'L' && name[0] != 'P') {
            sub = CharOperation.subarray((char[])name, 1, name.length);
            CharOperation.replace(sub, '/', '.');
            return CharOperation.concat(new char[]{name[0]}, sub);
         } else {
            sub = CharOperation.subarray((char[])name, 1, name.length);
            CharOperation.replace(sub, '/', '.');
            return CharOperation.concat(new char[]{'Q'}, sub);
         }
      } else {
         int i = CharOperation.lastIndexOf('/', name);
         if (i != -1) {
            return name[0] != 'L' && name[0] != 'P' ? CharOperation.concat(new char[]{name[0]}, CharOperation.subarray(name, i + 1, name.length)) : CharOperation.concat(new char[]{'Q'}, CharOperation.subarray(name, i + 1, name.length));
         } else {
            return name;
         }
      }
   }
}
