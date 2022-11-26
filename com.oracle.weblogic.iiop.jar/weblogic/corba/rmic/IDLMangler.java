package weblogic.corba.rmic;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import org.omg.CORBA.portable.IDLEntity;

public class IDLMangler implements IDLKeywords {
   private static final String SEQ = "seq";
   public static final String GET = "get";
   public static final String SET = "set";
   public static final String IS = "is";
   public static final String GETTER = "_get_";
   public static final String SETTER = "_set_";
   public static final String DOT = ".";
   public static final String DOUBLEUNDERSCORE = "__";
   public static final String UNDERSCORE = "_";
   public static final String BOXED_IDL = ".org.omg.boxedIDL.";
   public static final String BOXED_RMI = ".org.omg.boxedRMI";
   private static final Object[][] typeMapping;
   private static final String[] ILLEGAL_CHARS;

   public static boolean isIDLEntity(Class c) {
      return !IDLEntity.class.equals(c) && IDLEntity.class.isAssignableFrom(c);
   }

   public static String normalizeClassToIDLName(Class c) {
      Object[][] var1 = typeMapping;
      int var2 = var1.length;

      int dotPosition;
      for(dotPosition = 0; dotPosition < var2; ++dotPosition) {
         Object[] aTypeMapping = var1[dotPosition];
         if (c.equals(aTypeMapping[0])) {
            return (String)aTypeMapping[1];
         }
      }

      String name = c.getName();
      if (c.getComponentType() != null) {
         return getSequenceTypeName(c);
      } else if (isIDLEntity(c)) {
         return ".org.omg.boxedIDL." + normalizeJavaName(name);
      } else {
         StringBuilder sb = new StringBuilder(".");
         dotPosition = name.lastIndexOf(".");
         if (dotPosition >= 0) {
            ++dotPosition;
            sb.append(name.substring(0, dotPosition));
         } else {
            dotPosition = 0;
         }

         StringBuilder normalizedName;
         int dollarPosition;
         for(normalizedName = new StringBuilder(); (dollarPosition = name.indexOf(36, dotPosition)) >= 0; dotPosition = dollarPosition + 1) {
            normalizedName.append(name.substring(dotPosition, dollarPosition)).append("__");
         }

         normalizedName.append(name.substring(dotPosition));
         sb.append(normalizeJavaName(normalizedName.toString()));
         return sb.toString();
      }
   }

   public static String normalizeJavaName(String name) {
      String result = name;
      if (name.startsWith("_")) {
         result = "J" + name;
      } else {
         String[] var2 = KEYWORDS;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String keyword = var2[var4];
            if (name.equalsIgnoreCase(keyword)) {
               result = "_" + result;
            }
         }
      }

      return convertIllegalCharacters(result);
   }

   public static String accessorToAttribute(String methodName) {
      int ind = 3;
      if (methodName.startsWith("is")) {
         ind = 2;
      }

      String r = methodName.substring(ind);
      if (r.length() == 1 || r.length() >= 2 && (!Character.isUpperCase(r.charAt(0)) || !Character.isUpperCase(r.charAt(1)))) {
         r = Character.toLowerCase(r.charAt(0)) + r.substring(1);
      }

      return normalizeJavaName(r);
   }

   public static String convertOverloadedName(String name, Class[] args) {
      StringBuilder sb = new StringBuilder(normalizeJavaName(name));
      Class[] var3 = args;
      int var4 = args.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class arg = var3[var5];
         sb.append("__");

         String type;
         int dot;
         for(type = normalizeClassToIDLName(arg); (dot = type.lastIndexOf(".")) >= 0 && type.length() > dot + 1 && type.charAt(dot + 1) == '_'; type = type.substring(0, dot) + type.substring(dot + 1)) {
         }

         while(type.startsWith("_") || type.startsWith(".")) {
            type = type.substring(1);
         }

         type = type.replace('.', '_');
         type = type.replace(' ', '_');
         sb.append(type);
      }

      if (args.length == 0) {
         sb.append("__");
      }

      return sb.toString();
   }

   public static String convertOverloadedName(Method m) {
      return convertOverloadedName(m.getName(), m.getParameterTypes());
   }

   public static int isOverloaded(Class c, Method m) {
      if (c != null && !c.equals(Object.class)) {
         Method[] methods = c.getDeclaredMethods();
         Class[] params = m.getParameterTypes();
         Method[] var4 = methods;
         int ret = methods.length;

         for(int var6 = 0; var6 < ret; ++var6) {
            Method method = var4[var6];
            if (!m.getName().equals(method.getName())) {
               if (m.getName().equalsIgnoreCase(method.getName())) {
                  return 2;
               }
            } else {
               boolean identical = true;
               Class[] newparams = method.getParameterTypes();
               if (newparams.length != params.length) {
                  identical = false;
               } else {
                  for(int j = 0; j < newparams.length; ++j) {
                     if (newparams[j] != params[j]) {
                        identical = false;
                     }
                  }
               }

               if (!identical) {
                  return 1;
               }
            }
         }

         if (m.getName().equalsIgnoreCase(c.getName()) && !m.getName().equals(c.getName())) {
            return 2;
         } else {
            Class sc = c.getSuperclass();
            ret = isOverloaded(sc, m);
            if (ret > 0) {
               return ret;
            } else {
               Class[] inf = c.getInterfaces();
               Class[] var13 = inf;
               int var14 = inf.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  Class anInf = var13[var15];
                  ret = isOverloaded(anInf, m);
                  if (ret > 0) {
                     return ret;
                  }
               }

               return 0;
            }
         }
      } else {
         return 0;
      }
   }

   public static boolean methodThrowsCheckedException(Method m) {
      Class[] ex = m.getExceptionTypes();
      Class[] var2 = ex;
      int var3 = ex.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class anEx = var2[var4];
         if (Exception.class.isAssignableFrom(anEx) && !RemoteException.class.isAssignableFrom(anEx) && !RuntimeException.class.isAssignableFrom(anEx)) {
            return true;
         }
      }

      return false;
   }

   public static String getMangledMethodName(Method m) {
      return getMangledMethodName(m, (Class)null);
   }

   public static String getMangledMethodName(Method m, Class context) {
      Class c = m.getDeclaringClass();
      String name = m.getName();
      if (context == null) {
         context = c;
      }

      if (!org.omg.CORBA.Object.class.isAssignableFrom(context) && !org.omg.CORBA.Object.class.isAssignableFrom(m.getDeclaringClass())) {
         int overload = isOverloaded(c, m);
         if (overload == 1) {
            name = convertOverloadedName(m);
         } else if (overload == 2) {
            name = convertCaseSensitiveName(m);
         } else {
            name = normalizeJavaName(name);
         }

         if (!methodThrowsCheckedException(m)) {
            if (isIsser(m)) {
               name = "_get_" + unescape(accessorToAttribute(name));
            } else if (isGetter(m)) {
               name = "_get_" + unescape(accessorToAttribute(name));
            } else if (isSetter(m)) {
               name = "_set_" + unescape(accessorToAttribute(name));
            }
         }

         return name;
      } else {
         return name;
      }
   }

   private static String unescape(String name) {
      while(name.startsWith("_")) {
         name = name.substring(1, name.length());
      }

      return name;
   }

   public static boolean isIsser(Method m) {
      String name = m.getName();
      return name.startsWith("is") && m.getReturnType() == Boolean.TYPE && m.getParameterTypes().length == 0 && !methodThrowsCheckedException(m);
   }

   public static boolean isGetter(Method m) {
      String name = m.getName();
      if (name.startsWith("get") && name.length() > "get".length() && m.getReturnType() != Void.TYPE && m.getParameterTypes().length == 0 && !methodThrowsCheckedException(m)) {
         StringBuilder sb = new StringBuilder("is");
         sb.append(name.substring("get".length()));

         try {
            Method isser = m.getDeclaringClass().getDeclaredMethod(sb.toString(), Void.TYPE);
            if (isser.getReturnType() != m.getReturnType()) {
               return true;
            }
         } catch (NoSuchMethodException var4) {
            return true;
         }
      }

      return false;
   }

   public static boolean isSetter(Method m) {
      String name = m.getName();
      Class[] params = m.getParameterTypes();
      if (name.startsWith("set") && m.getReturnType() == Void.TYPE && params.length == 1 && !methodThrowsCheckedException(m)) {
         String iname = "is" + name.substring("set".length());

         Method isser;
         try {
            isser = m.getDeclaringClass().getDeclaredMethod(iname, (Class[])null);
            if (isser.getReturnType() == params[0]) {
               return true;
            }
         } catch (NoSuchMethodException var6) {
         }

         try {
            iname = "get" + name.substring("set".length());
            isser = m.getDeclaringClass().getDeclaredMethod(iname, (Class[])null);
            if (isser.getReturnType() == params[0]) {
               return true;
            }
         } catch (NoSuchMethodException var5) {
         }

         return false;
      } else {
         return false;
      }
   }

   private static String convertCaseSensitiveName(Method m) {
      String name = m.getName();
      StringBuilder sb = new StringBuilder(normalizeJavaName(name));
      boolean appendUnderscode = true;

      for(int i = 0; i < sb.length(); ++i) {
         if (Character.isUpperCase(sb.charAt(i))) {
            sb.append("_");
            sb.append(i);
            appendUnderscode = false;
         }
      }

      if (appendUnderscode) {
         sb.append("_");
      }

      return sb.toString();
   }

   private static String getSequenceTypeName(Class array) {
      StringBuilder sb = new StringBuilder(".org.omg.boxedRMI");

      Class compType;
      for(compType = array.getComponentType(); compType.getComponentType() != null; compType = compType.getComponentType()) {
      }

      String typeName = normalizeClassToIDLName(compType);
      String s = array.toString();
      int arrayDimension = s.lastIndexOf(91) - s.indexOf(91) + 1;
      int dotPosition = typeName.lastIndexOf(".");
      if (dotPosition >= 0) {
         sb.append(typeName.substring(0, dotPosition + 1));
      } else {
         sb.append(".");
      }

      sb.append("seq").append(Integer.toString(arrayDimension)).append("_").append(typeName.substring(dotPosition + 1));
      return sb.toString().replace(' ', '_');
   }

   public static String convertIllegalCharacters(String name) {
      StringBuilder result = new StringBuilder();

      for(int i = 0; i < name.length(); ++i) {
         char c = name.charAt(i);
         boolean illegal = false;

         for(int j = 0; j < ILLEGAL_CHARS.length; j += 2) {
            if (c != '\\') {
               char ill = ILLEGAL_CHARS[j].charAt(0);
               if (c == ill) {
                  result.append(ILLEGAL_CHARS[j + 1]);
                  illegal = true;
                  break;
               }
            } else {
               if ('u' == name.charAt(i + 1)) {
                  result.append("U");

                  for(int k = i + 2; k < i + 5; ++k) {
                     result.append(Character.toUpperCase(name.charAt(k)));
                  }
               }

               i += 4;
               illegal = true;
            }
         }

         if (!illegal) {
            result.append(c);
         }
      }

      return result.toString();
   }

   public static void main(String[] argv) throws Exception {
      if (argv.length >= 1 && argv.length <= 2) {
         Class c = Class.forName(argv[0]);
         Method[] var2 = c.getDeclaredMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method method = var2[var4];
            if (argv.length == 1 || method.getName().equals(argv[1])) {
               System.out.println(getMangledMethodName(method));
            }
         }
      } else {
         System.out.println("weblogic.corba.rmic.IDLMangler <class> <method>");
      }

   }

   static {
      typeMapping = new Object[][]{{null, ""}, {Void.TYPE, "void"}, {Boolean.TYPE, "boolean"}, {Character.TYPE, "wchar"}, {Byte.TYPE, "octet"}, {Short.TYPE, "short"}, {Integer.TYPE, "long"}, {Long.TYPE, "long long"}, {Float.TYPE, "float"}, {Double.TYPE, "double"}, {String.class, ".CORBA.WStringValue"}, {Object.class, ".java.lang._Object"}, {Class.class, ".javax.rmi.CORBA.ClassDesc"}, {Serializable.class, ".java.io.Serializable"}, {Externalizable.class, ".java.io.Externalizable"}, {org.omg.CORBA.Object.class, "Object"}};
      ILLEGAL_CHARS = new String[]{"$", "U0024"};
   }

   public interface Overloaded {
      int FALSE = 0;
      int TRUE = 1;
      int IGNORING_CASE = 2;
   }
}
