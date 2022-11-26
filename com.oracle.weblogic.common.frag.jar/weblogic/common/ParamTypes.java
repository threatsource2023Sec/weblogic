package weblogic.common;

public final class ParamTypes {
   public static final int UNKNOWN_TYPE = 0;
   public static final int BOOLEAN = 1;
   public static final int BYTE = 2;
   public static final int INT = 3;
   public static final int LONG = 4;
   public static final int DOUBLE = 5;
   public static final int FLOAT = 6;
   public static final int CHAR = 7;
   public static final int STRING = 8;
   public static final int DATE = 9;
   public static final int DURATION = 10;
   public static final int BLOB = 11;
   public static final int NOTYPE = 15;
   public static final int OBJECT = 16;
   public static final int ABBREVSTRING = 17;
   public static final int RECORDS = 18;
   public static final int SHORT = 19;
   public static final int IN = 40;
   public static final int OUT = 41;
   public static final int INOUT = 42;
   public static final int UNKNOWNPARAMTYPE = 43;
   public static final int UNKNOWN = 43;
   public static final int SCALAR = 50;
   public static final int VECTOR = 51;

   public static String toString(int type) {
      switch (type) {
         case 1:
            return "BOOLEAN";
         case 2:
            return "BYTE";
         case 3:
            return "INT";
         case 4:
            return "LONG";
         case 5:
            return "DOUBLE";
         case 6:
            return "FLOAT";
         case 7:
            return "CHAR";
         case 8:
            return "STRING";
         case 9:
            return "DATE";
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         default:
            return "(unrecognized)";
         case 16:
            return "OBJECT";
         case 17:
            return "STRING";
         case 18:
            return "RECORDS";
         case 19:
            return "SHORT";
         case 40:
            return "IN";
         case 41:
            return "OUT";
         case 42:
            return "INOUT";
         case 43:
            return "UNKNOWN";
         case 51:
            return "VECTOR";
      }
   }
}
