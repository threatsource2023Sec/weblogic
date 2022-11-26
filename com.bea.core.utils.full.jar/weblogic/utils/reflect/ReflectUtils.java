package weblogic.utils.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import weblogic.utils.Debug;
import weblogic.utils.enumerations.PeekingEnumerator;

public class ReflectUtils {
   private static final String SET_PFX = "set";
   private static final boolean debug = true;
   private static final boolean verbose = false;
   private static final Map primMap = new HashMap();
   private static final HashMap classTranslation = new HashMap();
   private static final HashMap primitiveToLang = new HashMap();

   public static boolean isPrimitiveClass(String className) {
      return primMap.containsKey(className);
   }

   public static Class loadPrimitiveClass(String className) {
      return (Class)primMap.get(className);
   }

   public static Enumeration allInterfaces(Class c) {
      LinkedHashMap h = new LinkedHashMap();
      addInterfaces(h, c);
      return new EnumerationImpl(h.keySet());
   }

   public static List everyInterface(Class c) {
      LinkedHashSet retVal;
      for(retVal = new LinkedHashSet(); c != null; c = c.getSuperclass()) {
         Enumeration immediateInterfaces = allInterfaces(c);

         while(immediateInterfaces.hasMoreElements()) {
            retVal.add(immediateInterfaces.nextElement());
         }
      }

      return new LinkedList(retVal);
   }

   public static Enumeration allSuperclasses(Class c) {
      return new SuperclassEnumerator(c);
   }

   public static Enumeration distinctInterfaceMethods(Class c) {
      Debug.assertion(c.isInterface());
      LinkedHashMap h = new LinkedHashMap();
      addDistinctInterfaceMethods(h, c);
      Enumeration ifcs = allInterfaces(c);

      while(ifcs.hasMoreElements()) {
         addDistinctInterfaceMethods(h, (Class)ifcs.nextElement());
      }

      return new EnumerationImpl(h.values());
   }

   public static Enumeration distinctMethods(Class c) {
      LinkedHashMap h = new LinkedHashMap();
      addDistinctInterfaceMethods(h, c);
      Enumeration ifcs = allInterfaces(c);

      while(ifcs.hasMoreElements()) {
         addDistinctInterfaceMethods(h, (Class)ifcs.nextElement());
      }

      Enumeration superClasses = allSuperclasses(c);

      while(superClasses.hasMoreElements()) {
         addDistinctInterfaceMethods(h, (Class)superClasses.nextElement());
      }

      return new EnumerationImpl(h.values());
   }

   public static boolean haveCommonInterface(Class c1, Class c2) {
      return haveCommonElement(allInterfaces(c1), allInterfaces(c2));
   }

   private static boolean haveCommonElement(Enumeration e1, Enumeration e2) {
      LinkedHashMap map = new LinkedHashMap();

      while(e1.hasMoreElements()) {
         Class o = (Class)e1.nextElement();
         map.put(o, o);
      }

      do {
         if (!e2.hasMoreElements()) {
            return false;
         }
      } while(!map.containsKey(e2.nextElement()));

      return true;
   }

   private static void addInterfaces(LinkedHashMap h, Class c) {
      Class[] ifcs = c.getInterfaces();

      for(int i = 0; i < ifcs.length; ++i) {
         Class ifc = ifcs[i];
         h.put(ifc, ifc);
         addInterfaces(h, ifc);
      }

   }

   private static void addDistinctInterfaceMethods(LinkedHashMap h, Class c) {
      Method[] methods = c.getMethods();
      int i = 0;

      for(int len = methods.length; i < len; ++i) {
         Method m = methods[i];
         if (!isStaticInitializer(m)) {
            MKey mkey = new MKey(m);
            Method m2 = (Method)h.get(mkey);
            if (m2 == null) {
               h.put(mkey, m);
            } else {
               Class c1 = m.getDeclaringClass();
               Class c2 = m2.getDeclaringClass();
               if (c2.isAssignableFrom(c1)) {
                  h.put(mkey, m);
               }
            }
         }
      }

   }

   private static boolean isStaticInitializer(Method m) {
      return m.getName().equals("<clinit>");
   }

   public static String getJavaLangType(String type) {
      String retVal = (String)primitiveToLang.get(type);
      return retVal == null ? type : retVal;
   }

   public static Class getTypeOfSetter(AccessibleObject setter) {
      if (setter instanceof Method) {
         Method method = (Method)setter;
         Class[] parameters = method.getParameterTypes();
         if (parameters != null && parameters.length == 1) {
            return parameters[0];
         } else {
            throw new IllegalArgumentException("Invalid setter method " + setter);
         }
      } else if (setter instanceof Field) {
         Field field = (Field)setter;
         return field.getType();
      } else {
         throw new IllegalArgumentException("Invalid setter: " + setter);
      }
   }

   public static AccessibleObject getMethodOrFieldForSetter(Class targetClass, String attribute, Class preferredType) throws IllegalArgumentException {
      if (targetClass != null && attribute != null) {
         AccessibleObject retVal = getMethodForSetter(targetClass, attribute, preferredType);
         if (retVal != null) {
            return retVal;
         } else {
            AccessibleObject retVal = getFieldForSetter(targetClass, attribute);
            if (retVal == null) {
               throw new IllegalArgumentException("Could not find method or setter for attribute " + attribute + " on class " + targetClass.getName() + " with preferred type " + (preferredType == null ? "<null>" : preferredType.getName()));
            } else {
               return retVal;
            }
         }
      } else {
         throw new IllegalArgumentException("targetClass is " + targetClass + " attribute is " + attribute);
      }
   }

   private static Field getFieldForSetter(Class targetClass, String attribute) {
      try {
         return targetClass.getDeclaredField(attribute);
      } catch (NoSuchFieldException var3) {
         return null;
      }
   }

   private static Method getMethodForSetter(Class targetClass, String attribute, Class preferredType) throws IllegalArgumentException {
      String methodName = constructBeanMethodName("set", attribute);
      if (preferredType != null) {
         try {
            return targetClass.getDeclaredMethod(methodName, preferredType);
         } catch (NoSuchMethodException var7) {
            if (classTranslation.containsKey(preferredType)) {
               try {
                  return targetClass.getDeclaredMethod(methodName, (Class)classTranslation.get(preferredType));
               } catch (NoSuchMethodException var6) {
               }
            }

            Method matchByName = getMatchByName(targetClass, methodName);
            return matchByName != null && matchByName.getParameterTypes()[0].isAssignableFrom(preferredType) ? matchByName : null;
         }
      } else {
         return getMatchByName(targetClass, methodName);
      }
   }

   private static Method getMatchByName(Class targetClass, String methodName) {
      Method retVal = null;
      Method[] var3 = targetClass.getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         if (methodName.equals(method.getName())) {
            Class[] parameters = method.getParameterTypes();
            if (parameters != null && parameters.length == 1) {
               if (retVal != null) {
                  throw new IllegalArgumentException("There are two methods named " + methodName + " on class " + targetClass.getName() + " that could be used");
               }

               retVal = method;
            }
         }
      }

      return retVal;
   }

   public static String constructBeanMethodName(String prefix, String attribute) {
      String firstChar = attribute.substring(0, 1);
      String otherChars = attribute.substring(1);
      firstChar = firstChar.toUpperCase();
      return prefix + firstChar + otherChars;
   }

   public static List getDeclaredMethodsOfAGivenName(Class clazz, String name) {
      List retVal = new LinkedList();
      if (clazz != null && name != null) {
         Method[] allDeclaredMethods = clazz.getDeclaredMethods();
         Method[] var4 = allDeclaredMethods;
         int var5 = allDeclaredMethods.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (name.equals(method.getName())) {
               retVal.add(method);
            }
         }

         return retVal;
      } else {
         return retVal;
      }
   }

   static {
      primMap.put(Boolean.TYPE.getName(), Boolean.TYPE);
      primMap.put(Character.TYPE.getName(), Character.TYPE);
      primMap.put(Byte.TYPE.getName(), Byte.TYPE);
      primMap.put(Short.TYPE.getName(), Short.TYPE);
      primMap.put(Integer.TYPE.getName(), Integer.TYPE);
      primMap.put(Long.TYPE.getName(), Long.TYPE);
      primMap.put(Float.TYPE.getName(), Float.TYPE);
      primMap.put(Double.TYPE.getName(), Double.TYPE);
      classTranslation.put(Short.class, Short.TYPE);
      classTranslation.put(Integer.class, Integer.TYPE);
      classTranslation.put(Long.class, Long.TYPE);
      classTranslation.put(Float.class, Float.TYPE);
      classTranslation.put(Double.class, Double.TYPE);
      classTranslation.put(Boolean.class, Boolean.TYPE);
      classTranslation.put(Character.class, Character.TYPE);
      classTranslation.put(Byte.class, Byte.TYPE);
      primitiveToLang.put("short", Short.class.getName());
      primitiveToLang.put("int", Integer.class.getName());
      primitiveToLang.put("long", Long.class.getName());
      primitiveToLang.put("float", Float.class.getName());
      primitiveToLang.put("double", Double.class.getName());
      primitiveToLang.put("boolean", Boolean.class.getName());
      primitiveToLang.put("char", Character.class.getName());
      primitiveToLang.put("byte", Byte.class.getName());
   }

   private static class EnumerationImpl implements Enumeration {
      private Iterator iterator;

      private EnumerationImpl(Collection bar) {
         this.iterator = bar.iterator();
      }

      public boolean hasMoreElements() {
         return this.iterator.hasNext();
      }

      public Object nextElement() {
         return this.iterator.next();
      }

      // $FF: synthetic method
      EnumerationImpl(Collection x0, Object x1) {
         this(x0);
      }
   }

   private static class MKey {
      private Method method;

      MKey(Method m) {
         this.method = m;
      }

      public boolean equals(Object o) {
         try {
            Method m2 = ((MKey)o).method;
            if (!this.method.getName().equals(m2.getName())) {
               return false;
            } else {
               Class[] params = this.method.getParameterTypes();
               Class[] params2 = m2.getParameterTypes();
               if (params.length != params2.length) {
                  return false;
               } else {
                  for(int i = 0; i < params.length; ++i) {
                     if (params[i] != params2[i]) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         } catch (ClassCastException var6) {
            return false;
         }
      }

      public int hashCode() {
         int h = this.method.getName().hashCode();
         Class[] params = this.method.getParameterTypes();

         for(int i = 0; i < params.length; ++i) {
            h ^= params[i].hashCode();
         }

         return h;
      }
   }

   private static class SuperclassEnumerator extends PeekingEnumerator {
      private Class c;

      SuperclassEnumerator(Class c) {
         this.c = c.getSuperclass();
      }

      protected Object nextObject() {
         if (this.c == null) {
            return END;
         } else {
            Class tmp = this.c;
            this.c = this.c.getSuperclass();
            return tmp;
         }
      }
   }
}
