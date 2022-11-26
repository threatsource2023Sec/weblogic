package weblogic.connector.utils;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TypeUtils {
   private static final String[] PRIMITIVE_TYPES = new String[]{"boolean", "byte", "char", "double", "float", "int", "long", "short"};
   private static final String[] PRIMITIVE_OBJ_CLASSES = new String[]{"java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short"};

   private TypeUtils() {
   }

   public static String primitiveToObject(String className) {
      int pos = Arrays.binarySearch(PRIMITIVE_TYPES, className);
      return pos >= 0 ? PRIMITIVE_OBJ_CLASSES[pos] : null;
   }

   public static String objectToPrimitive(String className) {
      int pos = Arrays.binarySearch(PRIMITIVE_OBJ_CLASSES, className);
      return pos >= 0 ? PRIMITIVE_TYPES[pos] : null;
   }

   public static boolean isPrimitive(String typeName) {
      return Arrays.binarySearch(PRIMITIVE_TYPES, typeName) >= 0;
   }

   public static boolean isObjectTypeOfPrimitive(String typeName) {
      return Arrays.binarySearch(PRIMITIVE_OBJ_CLASSES, typeName) >= 0;
   }

   public static boolean isSupportedType(String type) {
      if (type == null) {
         return false;
      } else {
         return "java.lang.String".equals(type) || isPrimitive(type) || isObjectTypeOfPrimitive(type);
      }
   }

   public static Object getValueByType(String configValue, String configType) throws NumberFormatException {
      Object valueObject = null;
      if (configType.equals("java.lang.String")) {
         valueObject = configValue;
      } else if (!configType.equals("java.lang.Character") && !configType.equals("char")) {
         if (!configType.equals("java.lang.Boolean") && !configType.equals("boolean")) {
            if (!configType.equals("java.lang.Integer") && !configType.equals("int")) {
               if (!configType.equals("java.lang.Double") && !configType.equals("double")) {
                  if (!configType.equals("java.lang.Byte") && !configType.equals("byte")) {
                     if (!configType.equals("java.lang.Short") && !configType.equals("short")) {
                        if (!configType.equals("java.lang.Long") && !configType.equals("long")) {
                           if (!configType.equals("java.lang.Float") && !configType.equals("float")) {
                              assert false : "User should ensure property has right type";
                           } else {
                              valueObject = Float.valueOf(configValue);
                           }
                        } else {
                           valueObject = Long.valueOf(configValue);
                        }
                     } else {
                        valueObject = Short.valueOf(configValue);
                     }
                  } else {
                     valueObject = Byte.valueOf(configValue);
                  }
               } else {
                  valueObject = Double.valueOf(configValue);
               }
            } else {
               valueObject = Integer.valueOf(configValue);
            }
         } else {
            valueObject = Boolean.valueOf(configValue);
         }
      } else {
         valueObject = configValue.charAt(0);
      }

      return valueObject;
   }

   public static boolean nameMatch(String s1, String s2) {
      return Introspector.decapitalize(s1).equals(Introspector.decapitalize(s2));
   }

   public static Set getInterfaces(Class cls) {
      Set all = new HashSet();
      Queue queue = new LinkedList();
      queue.add(cls);

      while(!queue.isEmpty()) {
         Class head = (Class)queue.poll();
         Class[] var4 = head.getInterfaces();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class intf = var4[var6];
            all.add(intf);
            queue.add(intf);
         }

         Class superClass = head.getSuperclass();
         if (superClass != null && superClass != Object.class) {
            queue.add(superClass);
         }
      }

      return all;
   }

   public static Set getInterfacesNameSet(Class cls) {
      Set all = getInterfaces(cls);
      Set result = new HashSet();
      Iterator var3 = all.iterator();

      while(var3.hasNext()) {
         Class class1 = (Class)var3.next();
         result.add(class1.getName());
      }

      return result;
   }

   public static final boolean checkOverridesHashCodeEquals(Class someClass, boolean mustOverride) {
      boolean equalsMethodFound = false;
      boolean hashCodeMethodFound = false;
      Method[] methods = someClass.getMethods();

      for(int i = 0; i < methods.length && (!equalsMethodFound || !hashCodeMethodFound); ++i) {
         Method m = methods[i];
         if (!equalsMethodFound) {
            equalsMethodFound = !m.getDeclaringClass().equals(Object.class) && m.getName().equals("equals") && m.getReturnType().equals(Boolean.TYPE) && m.getParameterTypes().length == 1 && m.getParameterTypes()[0].equals(Object.class);
         }

         if (!hashCodeMethodFound) {
            hashCodeMethodFound = !m.getDeclaringClass().equals(Object.class) && m.getName().equals("hashCode") && m.getReturnType().equals(Integer.TYPE) && m.getParameterTypes().length == 0;
         }
      }

      return mustOverride && equalsMethodFound && hashCodeMethodFound || !mustOverride && !equalsMethodFound && !hashCodeMethodFound;
   }
}
