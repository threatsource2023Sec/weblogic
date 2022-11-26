package weblogic.management.mbeanservers;

import java.lang.reflect.Method;
import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;

public class ObjectNameTranslator {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");

   public static Hashtable get81Keys(ObjectName objectName90) {
      Hashtable result = new Hashtable();
      String type = calculate81Type(objectName90.getKeyProperty("Type"));
      result.put("Type", type);
      String name = objectName90.getKeyProperty("Name");
      result.put("Name", name);
      if (type.equals("Domain")) {
         result.put(type, name);
      }

      String path = objectName90.getKeyProperty("Path");
      String[] pathComponents = path.split("/");
      Class interfaceClass = null;

      for(int i = 0; i < pathComponents.length - 1; ++i) {
         String pathComponent = pathComponents[i];
         String[] attributeKeyPair = pathComponent.split("\\[|\\]");
         String attribute = attributeKeyPair[0];
         if (interfaceClass == null) {
            try {
               result.put(attribute, attributeKeyPair[1]);
               if (!attribute.equals("DomainRuntime")) {
                  interfaceClass = Class.forName(translateTypeTo90(attribute));
               }
            } catch (ClassNotFoundException var13) {
               throw new AssertionError("Unable to get class for type " + attribute);
            }
         } else {
            try {
               Method method = interfaceClass.getMethod("get" + attribute, (Class[])null);
               interfaceClass = method.getReturnType();
               if (interfaceClass.isArray()) {
                  interfaceClass = interfaceClass.getComponentType();
               }

               String keyFor81 = calculate81Type(interfaceClass.getName());
               result.put(keyFor81, attributeKeyPair[1]);
            } catch (NoSuchMethodException var14) {
               throw new AssertionError("Unable to get method for specified attribute " + attribute);
            }
         }
      }

      if (debug.isDebugEnabled()) {
         debug.debug("get81Keys: for " + objectName90 + " returns " + result);
      }

      return result;
   }

   public static ObjectName translateTo81(ObjectName objectName) {
      Hashtable oldKeys = get81Keys(objectName);
      String domain = (String)oldKeys.get("DomainRuntime");
      if (domain != null) {
         oldKeys.remove("DomainRuntime");
      } else {
         domain = (String)oldKeys.get("Domain");
         oldKeys.remove("Domain");
      }

      try {
         return new ObjectName(domain, oldKeys);
      } catch (MalformedObjectNameException var4) {
         throw new RuntimeException("Unble to translate ObjectName", var4);
      }
   }

   private static Method matchAttributeNameWithClass(Class interfaceClass, String type) {
      Method getter = null;

      try {
         getter = interfaceClass.getMethod("get" + type);
         return getter;
      } catch (NoSuchMethodException var5) {
         try {
            String pluralizedType = pluralize(type);
            getter = interfaceClass.getMethod("get" + pluralizedType);
            return getter;
         } catch (NoSuchMethodException var4) {
            return null;
         }
      }
   }

   private static Method matchAttributeNameWithClass(Class interfaceClass, Class attributeClass, String type) {
      Method result = matchAttributeNameWithClass(interfaceClass, type);
      if (result != null) {
         return result;
      } else {
         if (type.endsWith("Runtime")) {
            String shortenedType = type.substring(0, type.length() - 7);
            result = matchAttributeNameWithClass(interfaceClass, shortenedType);
            if (result != null) {
               return result;
            }
         }

         Method[] methods = interfaceClass.getMethods();

         for(int i = 0; i < methods.length; ++i) {
            Method method = methods[i];
            Class returnType = method.getReturnType();
            if (method.getName().startsWith("get") && method.getParameterTypes().length == 0) {
               if (returnType.isArray()) {
                  Class componentType = returnType.getComponentType();
                  if (componentType.isAssignableFrom(attributeClass)) {
                     if (attributeClass.equals(componentType)) {
                        if (result == null) {
                           result = method;
                        }
                     } else {
                        result = method;
                     }
                  }
               } else if (returnType.isAssignableFrom(attributeClass)) {
                  if (attributeClass.equals(returnType)) {
                     if (result == null) {
                        result = method;
                     }
                  } else {
                     result = method;
                  }
               }
            }
         }

         if (result == null) {
            throw new RuntimeException("Unable to find getter method for " + interfaceClass.getName() + " returning " + attributeClass.getName());
         } else {
            return result;
         }
      }
   }

   public static String translateTypeTo90(String type81) {
      if (type81.endsWith("Runtime")) {
         return "weblogic.management.runtime." + type81 + "MBean";
      } else {
         return type81.endsWith("Config") ? "weblogic.management.configuration." + type81.substring(0, type81.length() - 7) + "MBean" : "weblogic.management.configuration." + type81 + "MBean";
      }
   }

   public static String calculate81Type(String qualifiedName) {
      int packagePosition = qualifiedName.lastIndexOf(46) + 1;
      int truncateAt = qualifiedName.length();
      if (qualifiedName.endsWith("MBean")) {
         truncateAt -= 5;
      } else if (qualifiedName.endsWith("Bean")) {
         truncateAt -= 4;
      }

      return qualifiedName.substring(packagePosition, truncateAt);
   }

   public static String pluralize(String name) {
      String result = null;
      if (!name.endsWith("s") && !name.endsWith("ch") && !name.endsWith("x") && !name.endsWith("sh")) {
         if (name.endsWith("y") && !name.endsWith("ay") && !name.endsWith("ey") && !name.endsWith("iy") && !name.endsWith("oy") && !name.endsWith("uy")) {
            result = name.substring(0, name.length() - 1) + "ies";
         } else {
            result = name + "s";
         }
      } else {
         result = name + "es";
      }

      return result;
   }
}
