package org.jboss.weld.util.bytecode;

import org.jboss.classfilewriter.code.CodeAttribute;

public class BytecodeUtils {
   public static final String VOID_CLASS_DESCRIPTOR = "V";
   public static final String BYTE_CLASS_DESCRIPTOR = "B";
   public static final String CHAR_CLASS_DESCRIPTOR = "C";
   public static final String DOUBLE_CLASS_DESCRIPTOR = "D";
   public static final String FLOAT_CLASS_DESCRIPTOR = "F";
   public static final String INT_CLASS_DESCRIPTOR = "I";
   public static final String LONG_CLASS_DESCRIPTOR = "J";
   public static final String SHORT_CLASS_DESCRIPTOR = "S";
   public static final String BOOLEAN_CLASS_DESCRIPTOR = "Z";
   private static final String TYPE = "TYPE";
   private static final String LJAVA_LANG_CLASS = "Ljava/lang/Class;";
   public static final int ENUM = 16384;
   public static final int ANNOTATION = 8192;

   private BytecodeUtils() {
   }

   public static void addLoadInstruction(CodeAttribute code, String type, int variable) {
      char tp = type.charAt(0);
      if (tp != 'L' && tp != '[') {
         switch (tp) {
            case 'D':
               code.dload(variable);
               break;
            case 'F':
               code.fload(variable);
               break;
            case 'J':
               code.lload(variable);
               break;
            default:
               code.iload(variable);
         }
      } else {
         code.aload(variable);
      }

   }

   public static void pushClassType(CodeAttribute b, String classType) {
      if (classType.length() != 1) {
         if (classType.startsWith("L") && classType.endsWith(";")) {
            classType = classType.substring(1, classType.length() - 1);
         }

         b.loadClass(classType);
      } else {
         char type = classType.charAt(0);
         switch (type) {
            case 'B':
               b.getstatic(Byte.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'C':
               b.getstatic(Character.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'D':
               b.getstatic(Double.class.getName(), "TYPE", "Ljava/lang/Class;");
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
               throw new RuntimeException("Cannot handle primitive type: " + type);
            case 'F':
               b.getstatic(Float.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'I':
               b.getstatic(Integer.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'J':
               b.getstatic(Long.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'S':
               b.getstatic(Short.class.getName(), "TYPE", "Ljava/lang/Class;");
               break;
            case 'Z':
               b.getstatic(Boolean.class.getName(), "TYPE", "Ljava/lang/Class;");
         }
      }

   }

   public static String getName(String descriptor) {
      return !descriptor.startsWith("[") ? descriptor.substring(1).substring(0, descriptor.length() - 2) : descriptor;
   }
}
