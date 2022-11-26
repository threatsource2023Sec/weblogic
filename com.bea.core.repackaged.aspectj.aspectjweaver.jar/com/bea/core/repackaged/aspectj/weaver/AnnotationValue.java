package com.bea.core.repackaged.aspectj.weaver;

public abstract class AnnotationValue {
   protected int valueKind;
   public static final int STRING = 115;
   public static final int ENUM_CONSTANT = 101;
   public static final int CLASS = 99;
   public static final int ANNOTATION = 64;
   public static final int ARRAY = 91;
   public static final int PRIMITIVE_INT = 73;
   public static final int PRIMITIVE_BYTE = 66;
   public static final int PRIMITIVE_CHAR = 67;
   public static final int PRIMITIVE_DOUBLE = 68;
   public static final int PRIMITIVE_FLOAT = 70;
   public static final int PRIMITIVE_LONG = 74;
   public static final int PRIMITIVE_SHORT = 83;
   public static final int PRIMITIVE_BOOLEAN = 90;

   public abstract String stringify();

   public AnnotationValue(int kind) {
      this.valueKind = kind;
   }

   public static String whatKindIsThis(int kind) {
      switch (kind) {
         case 64:
            return "annotation";
         case 65:
         case 69:
         case 71:
         case 72:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 100:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         default:
            throw new RuntimeException("Dont know what this is : " + kind);
         case 66:
            return "byte";
         case 67:
            return "char";
         case 68:
            return "double";
         case 70:
            return "float";
         case 73:
            return "int";
         case 74:
            return "long";
         case 83:
            return "short";
         case 90:
            return "boolean";
         case 91:
            return "array";
         case 99:
            return "class";
         case 101:
            return "enum";
         case 115:
            return "string";
      }
   }
}
