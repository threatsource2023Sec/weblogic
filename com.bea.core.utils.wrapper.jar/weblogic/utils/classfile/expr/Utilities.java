package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Type;

class Utilities {
   public static Type getReturnType(String descriptor) {
      char c = descriptor.charAt(descriptor.indexOf(41) + 1);
      switch (c) {
         case 'B':
            return Type.BYTE;
         case 'C':
            return Type.CHARACTER;
         case 'D':
            return Type.DOUBLE;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            return Type.INVALID;
         case 'F':
            return Type.FLOAT;
         case 'I':
            return Type.INT;
         case 'J':
            return Type.LONG;
         case 'L':
            return Type.OBJECT;
         case 'S':
            return Type.SHORT;
         case 'V':
            return Type.VOID;
         case 'Z':
            return Type.BOOLEAN;
         case '[':
            return Type.ARRAY;
      }
   }
}
