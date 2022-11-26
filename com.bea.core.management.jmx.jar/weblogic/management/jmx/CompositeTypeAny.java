package weblogic.management.jmx;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class CompositeTypeAny {
   public static final CompositeType ANY;
   public static final String ANY_TYPE_NAME = CompositeType.class.getName() + ".ANY";
   public static final String VALUE_KEY = "ValueAsString";
   public static final String TYPE_KEY = "OpenTypeName";
   public static final String VALUE_ARRAY_KEY = "ValueAsStringArray";
   private static final String[] KEY_NAME_ARRAY = new String[]{"ValueAsString", "OpenTypeName", "ValueAsStringArray"};
   private static final String[] NULL_STRING_ARRAY = new String[]{"null"};
   private static final Class[] ARGS_STRING;

   public static final CompositeData newAnyInstance(Object value) throws OpenDataException {
      String valueString = null;
      String typeString = null;
      String[] valuesAsStrings = NULL_STRING_ARRAY;
      if (value != null) {
         Class valueClass;
         if (value.getClass().isArray()) {
            valueClass = value.getClass();
            valueString = valueClass.getName();
            typeString = ArrayType.class.getName();
            int i;
            if (value.getClass().getComponentType().isPrimitive()) {
               int entryCount = Array.getLength(value);
               valuesAsStrings = new String[entryCount];

               for(i = 0; i < entryCount; ++i) {
                  valuesAsStrings[i] = convertPrimitiveEntry(i, value);
               }
            } else {
               verifyReconstitute(value.getClass().getComponentType());
               Object[] values = (Object[])((Object[])value);
               valuesAsStrings = new String[values.length];

               for(i = 0; i < values.length; ++i) {
                  valuesAsStrings[i] = values[i].toString();
               }
            }
         } else {
            valueClass = value.getClass();
            if (!valueClass.isPrimitive()) {
               verifyReconstitute(valueClass);
            }

            typeString = valueClass.getName();
            valueString = value.toString();
         }
      } else {
         valueString = "null";
         typeString = "null";
      }

      return new CompositeDataSupport(ANY, KEY_NAME_ARRAY, new Object[]{valueString, typeString, valuesAsStrings});
   }

   private static void verifyReconstitute(Class toReconstitute) throws OpenDataException {
      try {
         Constructor var1 = toReconstitute.getConstructor(ARGS_STRING);
      } catch (NoSuchMethodException var2) {
         throw new OpenDataException("An object that is being passed through the CompositeData does not have a constructor which takes a String and thus will not be able to be reconstituted.");
      }
   }

   private static String convertPrimitiveEntry(int i, Object value) {
      Class componentType = value.getClass().getComponentType();
      if (componentType.equals(Byte.TYPE)) {
         return Byte.toString(Array.getByte(value, i));
      } else if (componentType.equals(Boolean.TYPE)) {
         return Boolean.toString(Array.getBoolean(value, i));
      } else if (componentType.equals(Character.TYPE)) {
         return Character.toString(Array.getChar(value, i));
      } else if (componentType.equals(Short.TYPE)) {
         return Short.toString(Array.getShort(value, i));
      } else if (componentType.equals(Integer.TYPE)) {
         return Integer.toString(Array.getInt(value, i));
      } else if (componentType.equals(Long.TYPE)) {
         return Long.toString(Array.getLong(value, i));
      } else if (componentType.equals(Float.TYPE)) {
         return Float.toString(Array.getFloat(value, i));
      } else if (componentType.equals(Double.TYPE)) {
         return Double.toString(Array.getDouble(value, i));
      } else {
         throw new AssertionError("Unable to convert entry at " + i + ":" + value);
      }
   }

   static {
      try {
         ArrayType stringArrayType = new ArrayType(1, SimpleType.STRING);
         ANY = new CompositeType(ANY_TYPE_NAME, "Represents a value which can be any of the SimpleTypes", KEY_NAME_ARRAY, new String[]{"The toString value of the Object or the type of the element if this is an array", "The String reprentation of an of the SimpleTypes", "If the type is an Array this contains an array of the toString value of each element"}, new OpenType[]{SimpleType.STRING, SimpleType.STRING, stringArrayType});
      } catch (OpenDataException var1) {
         throw new RuntimeException(var1);
      }

      ARGS_STRING = new Class[]{String.class};
   }
}
