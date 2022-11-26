package weblogic.wtc.gwt;

import java.util.HashMap;
import weblogic.wtc.jatmi.Decimal;

public class TuxedoPrimitives {
   public static final int TYPE_BOOLEAN = 0;
   public static final int TYPE_BYTE = 1;
   public static final int TYPE_SHORT = 2;
   public static final int TYPE_INT = 3;
   public static final int TYPE_LONG = 4;
   public static final int TYPE_CHAR = 5;
   public static final int TYPE_FLOAT = 6;
   public static final int TYPE_DOUBLE = 7;
   public static final int TYPE_STRING = 8;
   public static final int TYPE_DECIMAL = 9;
   public static final int TYPE_NON_PRIMITIVE = 10;
   private static final HashMap wellKnownSignaturesMap = new HashMap(32);
   private static final HashMap wellKnownClassesMap = new HashMap(32);

   public static String getName(int typeCode) {
      switch (typeCode) {
         case 0:
            return "Boolean";
         case 1:
            return "Byte";
         case 2:
            return "Short";
         case 3:
            return "Integer";
         case 4:
            return "Long";
         case 5:
            return "Character";
         case 6:
            return "Float";
         case 7:
            return "Double";
         case 8:
            return "String";
         case 9:
            return "Decimal";
         default:
            return "Object";
      }
   }

   private static final void addWKT(Class c, String sig, int val) {
      wellKnownSignaturesMap.put(sig, new Integer(val));
      wellKnownClassesMap.put(c, new Integer(val));
   }

   public static int getPrimitiveTypeCode(String signature) {
      Integer i = (Integer)wellKnownSignaturesMap.get(signature);
      return i == null ? 10 : i;
   }

   public static int getPrimitiveTypeCode(Object obj) {
      return getPrimitiveTypeCode(obj.getClass());
   }

   public static int getPrimitiveTypeCode(Class cls) {
      Integer i = (Integer)wellKnownClassesMap.get(cls);
      return i == null ? 10 : i;
   }

   public static String typeNameToSignature(String typeName) {
      if (typeName.equals("char")) {
         return "C";
      } else if (typeName.equals("boolean")) {
         return "Z";
      } else if (typeName.equals("byte")) {
         return "B";
      } else if (typeName.equals("short")) {
         return "S";
      } else if (typeName.equals("int")) {
         return "I";
      } else if (typeName.equals("long")) {
         return "J";
      } else if (typeName.equals("float")) {
         return "F";
      } else if (typeName.equals("double")) {
         return "D";
      } else if (typeName.equals("java.lang.Character")) {
         return "Ljava/lang/Character;";
      } else if (typeName.equals("java.lang.Boolean")) {
         return "Ljava/lang/Boolean;";
      } else if (typeName.equals("java.lang.Byte")) {
         return "Ljava/lang/Byte;";
      } else if (typeName.equals("java.lang.Short")) {
         return "Ljava/lang/Short;";
      } else if (typeName.equals("java.lang.Integer")) {
         return "Ljava/lang/Integer;";
      } else if (typeName.equals("java.lang.Long")) {
         return "Ljava/lang/Long;";
      } else if (typeName.equals("java.lang.Float")) {
         return "Ljava/lang/Float;";
      } else if (typeName.equals("java.lang.Double")) {
         return "Ljava/lang/Double;";
      } else if (typeName.equals("java.lang.String")) {
         return "Ljava/lang/String;";
      } else {
         return typeName.equals("weblogic.wtc.jatmi.Decimal") ? "Lweblogic/wtc/jatmi/Decimal;" : "";
      }
   }

   static {
      addWKT(Character.TYPE, "C", 5);
      addWKT(Boolean.TYPE, "Z", 0);
      addWKT(Byte.TYPE, "B", 1);
      addWKT(Short.TYPE, "S", 2);
      addWKT(Integer.TYPE, "I", 3);
      addWKT(Long.TYPE, "J", 4);
      addWKT(Float.TYPE, "F", 6);
      addWKT(Double.TYPE, "D", 7);
      addWKT(Character.class, "Ljava/lang/Character;", 5);
      addWKT(Boolean.class, "Ljava/lang/Boolean;", 0);
      addWKT(Byte.class, "Ljava/lang/Byte;", 1);
      addWKT(Short.class, "Ljava/lang/Short;", 2);
      addWKT(Integer.class, "Ljava/lang/Integer;", 3);
      addWKT(Long.class, "Ljava/lang/Long;", 4);
      addWKT(Float.class, "Ljava/lang/Float;", 6);
      addWKT(Double.class, "Ljava/lang/Double;", 7);
      addWKT(String.class, "Ljava/lang/String;", 8);
      addWKT(Decimal.class, "Lweblogic/wtc/jatmi/Decimal;", 9);
   }
}
