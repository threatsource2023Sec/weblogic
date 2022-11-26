package org.jboss.classfilewriter.util;

import org.jboss.classfilewriter.code.CodeAttribute;

public class Boxing {
   public static void boxIfNessesary(CodeAttribute ca, String desc) {
      if (desc.length() == 1) {
         char type = desc.charAt(0);
         switch (type) {
            case 'B':
               boxByte(ca);
               break;
            case 'C':
               boxChar(ca);
               break;
            case 'D':
               boxDouble(ca);
               break;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            default:
               throw new RuntimeException("Cannot box unkown primitive type: " + type);
            case 'F':
               boxFloat(ca);
               break;
            case 'I':
               boxInt(ca);
               break;
            case 'J':
               boxLong(ca);
               break;
            case 'S':
               boxShort(ca);
               break;
            case 'Z':
               boxBoolean(ca);
         }
      }

   }

   public static CodeAttribute unbox(CodeAttribute ca, String desc) {
      char type = desc.charAt(0);
      switch (type) {
         case 'B':
            return unboxByte(ca);
         case 'C':
            return unboxChar(ca);
         case 'D':
            return unboxDouble(ca);
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new RuntimeException("Cannot unbox unkown primitive type: " + type);
         case 'F':
            return unboxFloat(ca);
         case 'I':
            return unboxInt(ca);
         case 'J':
            return unboxLong(ca);
         case 'S':
            return unboxShort(ca);
         case 'Z':
            return unboxBoolean(ca);
      }
   }

   public static void boxInt(CodeAttribute bc) {
      bc.invokestatic("java.lang.Integer", "valueOf", "(I)Ljava/lang/Integer;");
   }

   public static void boxLong(CodeAttribute bc) {
      bc.invokestatic("java.lang.Long", "valueOf", "(J)Ljava/lang/Long;");
   }

   public static void boxShort(CodeAttribute bc) {
      bc.invokestatic("java.lang.Short", "valueOf", "(S)Ljava/lang/Short;");
   }

   public static void boxByte(CodeAttribute bc) {
      bc.invokestatic("java.lang.Byte", "valueOf", "(B)Ljava/lang/Byte;");
   }

   public static void boxFloat(CodeAttribute bc) {
      bc.invokestatic("java.lang.Float", "valueOf", "(F)Ljava/lang/Float;");
   }

   public static void boxDouble(CodeAttribute bc) {
      bc.invokestatic("java.lang.Double", "valueOf", "(D)Ljava/lang/Double;");
   }

   public static void boxChar(CodeAttribute bc) {
      bc.invokestatic("java.lang.Character", "valueOf", "(C)Ljava/lang/Character;");
   }

   public static void boxBoolean(CodeAttribute bc) {
      bc.invokestatic("java.lang.Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
   }

   public static CodeAttribute unboxInt(CodeAttribute bc) {
      bc.checkcast("java.lang.Number");
      bc.invokevirtual("java.lang.Number", "intValue", "()I");
      return bc;
   }

   public static CodeAttribute unboxLong(CodeAttribute bc) {
      bc.checkcast("java.lang.Number");
      bc.invokevirtual("java.lang.Number", "longValue", "()J");
      return bc;
   }

   public static CodeAttribute unboxShort(CodeAttribute bc) {
      bc.checkcast("java.lang.Number");
      bc.invokevirtual("java.lang.Number", "shortValue", "()S");
      return bc;
   }

   public static CodeAttribute unboxByte(CodeAttribute bc) {
      bc.checkcast("java.lang.Number");
      bc.invokevirtual("java.lang.Number", "byteValue", "()B");
      return bc;
   }

   public static CodeAttribute unboxFloat(CodeAttribute bc) {
      bc.checkcast("java.lang.Number");
      bc.invokevirtual("java.lang.Number", "floatValue", "()F");
      return bc;
   }

   public static CodeAttribute unboxDouble(CodeAttribute bc) {
      bc.checkcast("java.lang.Number");
      bc.invokevirtual("java.lang.Number", "doubleValue", "()D");
      return bc;
   }

   public static CodeAttribute unboxChar(CodeAttribute bc) {
      bc.checkcast("java.lang.Character");
      bc.invokevirtual("java.lang.Character", "charValue", "()C");
      return bc;
   }

   public static CodeAttribute unboxBoolean(CodeAttribute bc) {
      bc.checkcast("java.lang.Boolean");
      bc.invokevirtual("java.lang.Boolean", "booleanValue", "()Z");
      return bc;
   }
}
