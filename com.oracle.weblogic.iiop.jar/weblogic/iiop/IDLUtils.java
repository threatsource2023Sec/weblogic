package weblogic.iiop;

import java.io.Externalizable;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.omg.CORBA.portable.IDLEntity;
import weblogic.corba.rmic.IDLAttribute;
import weblogic.corba.rmic.IDLField;
import weblogic.corba.rmic.IDLKeywords;
import weblogic.corba.rmic.IDLMangler;
import weblogic.corba.rmic.IDLOptions;
import weblogic.corba.utils.CorbaUtils;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.Debug;
import weblogic.utils.compiler.CodeGenerationException;

public final class IDLUtils implements IDLKeywords {
   private static String SEQ = "seq";
   public static final String GET = "get";
   public static final String SET = "set";
   public static final String IS = "is";
   public static final String DOT = ".";
   public static final char DOT_C = '.';
   public static final String RAISES = " raises (";
   public static final String DOUBLEUNDERSCORE = "__";
   public static final String UNDERSCORE = "_";
   public static final String EXCEPTION = "Exception";
   public static final String EX = "Ex";
   private static Hashtable m_treatedClasses = null;
   public static final String BOXED_IDL_COLON_COLON = "::org::omg::boxedIDL::";

   public static String javaTypeToPath(Class c) {
      return javaTypeToPath((String)null, c);
   }

   public static String getTypeID(Class c) {
      return ValueHandlerImpl.getRepositoryID(c);
   }

   public static String getPragmaID(Class c) {
      String result = "";
      String typeID = getTypeID(c);
      String type = stripPackage(getIDLType(c, "."));
      result = "#pragma ID " + type + " \"" + typeID + "\"\n";
      return result;
   }

   public static Class[] getInheritedInterfaces(Class c) {
      Hashtable hResult = new Hashtable();
      getInheritedInterfaces(c, hResult);
      Class[] result = new Class[hResult.size()];
      Enumeration e = hResult.elements();

      for(int i = 0; e.hasMoreElements(); result[i++] = (Class)e.nextElement()) {
      }

      return result;
   }

   static void getInheritedInterfaces(Class c, Hashtable h) {
      Class sup = c.getSuperclass();
      Class[] cl = null;
      if (sup != null && !sup.isInterface()) {
         Class[] cls = c.getInterfaces();
         cl = new Class[cls.length + 1];
         System.arraycopy(cls, 0, cl, 0, cls.length);
         cl[cls.length] = sup;
      } else {
         cl = c.getInterfaces();
      }

      Vector added = new Vector();

      Class next;
      for(int i = 0; i < cl.length; ++i) {
         next = (Class)h.get(cl[i]);
         if (null == next) {
            h.put(cl[i], cl[i]);
            added.addElement(cl[i]);
         }
      }

      Enumeration e = added.elements();

      while(e.hasMoreElements()) {
         next = (Class)e.nextElement();
         getInheritedInterfaces(next, h);
      }

   }

   public static Class[] getInheritedRemoteInterfaces(Class c) {
      Class[] cl = getInheritedInterfaces(c);
      Vector hResult = new Vector();

      for(int i = 0; i < cl.length; ++i) {
         if (Utilities.isARemote(cl[i])) {
            hResult.addElement(cl[i]);
         }
      }

      Class[] result = new Class[hResult.size()];
      int i = 0;

      for(Enumeration e = hResult.elements(); e.hasMoreElements(); result[i++] = (Class)e.nextElement()) {
      }

      return result;
   }

   static boolean isCORBAObject(Class c) {
      return "org.omg.CORBA.Object".equals(c.getName());
   }

   static String convertLeadingUnderscore(String name) {
      String result = name;
      if (name.startsWith("_")) {
         result = "J" + name;
      }

      return result;
   }

   public static String javaTypeToPath(String path, Class c) {
      String result = new String();
      if (null != path) {
         result = result + path;
      }

      String sep = (new Character(File.separatorChar)).toString();
      if (isCORBAObject(c)) {
         return "org" + sep + "omg" + sep + "CORBA" + sep + "_Object.idl";
      } else {
         String name = getIDLType(c, sep);
         result = result + name + ".idl";
         if (result.startsWith(sep)) {
            result = result.substring(1);
         }

         return result;
      }
   }

   public static final String exceptionToEx(String ex) {
      String result = ex;
      if (ex.endsWith("Exception")) {
         result = ex.substring(0, ex.length() - "Exception".length());
      }

      return result + "Ex";
   }

   public static String getIDLType(Class c, String sep, String leading_sep) {
      String name = IDLMangler.normalizeClassToIDLName(c);
      StringBuffer sb = new StringBuffer();
      int i = 0;
      if (IDLOptions.getVisibroker() && name.equals(".javax.rmi.CORBA.ClassDesc")) {
         return leading_sep + "javax" + sep + "rmi" + sep + "CORBA_" + sep + "ClassDesc";
      } else {
         if (name.charAt(0) == '.') {
            sb.append(leading_sep);
            ++i;
         }

         if (sep.equals(".")) {
            return sb.append(name.substring(i).replace(' ', '_')).toString();
         } else {
            for(; i < name.length(); ++i) {
               switch (name.charAt(i)) {
                  case ' ':
                     if (sep.equals("_")) {
                        sb.append("_");
                     } else {
                        sb.append(" ");
                     }
                     break;
                  case '.':
                     sb.append(sep);
                     break;
                  default:
                     sb.append(name.charAt(i));
               }
            }

            return sb.toString();
         }
      }
   }

   public static String getIDLType(Class c, String sep) {
      return getIDLType(c, sep, "");
   }

   public static String getIDLType(Class c) {
      return getIDLType(c, "::", "::");
   }

   public static boolean isException(Class c) {
      return Exception.class.isAssignableFrom(c) && !RemoteException.class.equals(c);
   }

   public static String stripPackage(String pkgName) {
      return stripPackage(pkgName, ".");
   }

   public static String stripPackage(String pkgName, String sep) {
      String result = pkgName;
      int ind = pkgName.lastIndexOf(sep);
      if (-1 != ind) {
         result = pkgName.substring(ind + sep.length());
      }

      return result;
   }

   public static Class[] getClasses(Class theInterface, boolean recurse, boolean include_methods) {
      Hashtable h = new Hashtable();
      getAllTypes(theInterface, h, recurse, include_methods);
      Class[] result = new Class[h.size()];
      Enumeration e = h.elements();

      for(int i = 0; e.hasMoreElements(); result[i++] = (Class)e.nextElement()) {
      }

      return result;
   }

   static void getAllTypes(Class theInterface, Hashtable hResult, boolean recurse, boolean include_methods) {
      m_treatedClasses = new Hashtable();
      getAllTypesInternal(theInterface, hResult, recurse, include_methods);
   }

   public static boolean isValidField(Field f) {
      return !attributeMustBeIgnored(f.getName()) && !Modifier.isStatic(f.getModifiers()) && !Modifier.isTransient(f.getModifiers());
   }

   public static boolean isValid(Method m) {
      boolean result = true;
      int mod = m.getModifiers();
      if (Modifier.isNative(mod) || !Modifier.isPublic(mod)) {
         result = false;
      }

      return result;
   }

   static void addType(Class c, Hashtable hResult, Class theInterface, boolean recurse) {
      if (!c.equals(theInterface) && CorbaUtils.isValid(c)) {
         hResult.put(c, c);
         if (recurse) {
            for(c = c.getSuperclass(); null != c; c = c.getSuperclass()) {
               if (!c.equals(theInterface) && CorbaUtils.isValid(c)) {
                  hResult.put(c, c);
               }
            }
         }
      }

   }

   static void getAllTypesInternal(Class theInterface, Hashtable hResult, boolean recurse, boolean include_methods) {
      Class ct = theInterface.getComponentType();
      if (null != ct) {
         addType(ct, hResult, theInterface, recurse);
      } else {
         Class sc = theInterface.getSuperclass();
         if (null != sc && !Object.class.equals(sc)) {
            addType(theInterface.getSuperclass(), hResult, theInterface, recurse);
         }

         Class[] ca = theInterface.getInterfaces();
         Class[] var8 = ca;
         int var9 = ca.length;

         Class cl;
         for(int var10 = 0; var10 < var9; ++var10) {
            cl = var8[var10];
            addType(cl, hResult, theInterface, recurse);
         }

         Hashtable hr = new Hashtable();
         getAllFieldTypes(theInterface, hr);
         Enumeration e = hr.elements();

         while(e.hasMoreElements()) {
            Class cl = (Class)e.nextElement();
            addType(cl, hResult, theInterface, recurse);
         }

         if (include_methods) {
            Method[] methods = theInterface.getDeclaredMethods();
            Method[] var26 = methods;
            int var12 = methods.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               Method m = var26[var13];
               if (isValid(m)) {
                  Class returnType = m.getReturnType();
                  if (null != returnType) {
                     addType(returnType, hResult, theInterface, recurse);
                  }

                  Class[] paramTypes = m.getParameterTypes();
                  Class[] exTypes = paramTypes;
                  int var18 = paramTypes.length;

                  int var19;
                  for(var19 = 0; var19 < var18; ++var19) {
                     Class paramType = exTypes[var19];
                     addType(paramType, hResult, theInterface, recurse);
                  }

                  exTypes = m.getExceptionTypes();
                  Class[] var28 = exTypes;
                  var19 = exTypes.length;

                  for(int var29 = 0; var29 < var19; ++var29) {
                     Class exType = var28[var29];
                     addType(exType, hResult, theInterface, recurse);
                  }
               }
            }
         }

         if (recurse) {
            Enumeration en = hResult.elements();

            while(en.hasMoreElements()) {
               cl = (Class)en.nextElement();
               if (!isException(cl) && null == m_treatedClasses.get(cl.getName())) {
                  m_treatedClasses.put(cl.getName(), cl);
                  getAllTypesInternal(cl, hResult, recurse, include_methods);
               }
            }
         }

      }
   }

   public static void getAllFieldTypes(Class theInterface, Hashtable hResult) {
      Field[] fields = theInterface.getDeclaredFields();

      for(int i = 0; i < fields.length; ++i) {
         if (isValidField(fields[i])) {
            addType(fields[i].getType(), hResult, theInterface, false);
         }
      }

   }

   public static Class getNonConformantType(Class c) {
      Class result = null;
      if (CorbaUtils.isValid(c) && !CorbaUtils.isARemote(c) && !isConcreteValueType(c) && !CorbaUtils.isAbstractInterface(c)) {
         result = c;
      }

      return result;
   }

   public static boolean isValueType(Class c) {
      Debug.assertion(null != c);
      if (!c.isPrimitive() && !Serializable.class.equals(c) && !Externalizable.class.equals(c) && !RemoteException.class.equals(c) && !IDLEntity.class.equals(c) && !Class.class.equals(c) && !Void.TYPE.equals(c) && !CorbaUtils.isRemote(c) && !CorbaUtils.isARemote(c)) {
         if (c.isInterface() && !CorbaUtils.isAbstractInterface(c)) {
            return true;
         } else if (c.getComponentType() != null) {
            return false;
         } else {
            return Serializable.class.isAssignableFrom(c);
         }
      } else {
         return false;
      }
   }

   public static boolean isConcreteValueType(Class c) {
      return isValueType(c) && !c.isInterface();
   }

   public static boolean isIDLInterface(Class c) {
      return org.omg.CORBA.Object.class.isAssignableFrom(c) && IDLEntity.class.isAssignableFrom(c);
   }

   public static Class getRemoteInterface(Class c) {
      Class result = null;
      Class[] itf = c.getInterfaces();

      for(int i = 0; i < itf.length; ++i) {
         if (CorbaUtils.isARemote(itf[i])) {
            result = itf[i];
            break;
         }
      }

      return result;
   }

   public static boolean isACheckedException(Class c) {
      return !RemoteException.class.isAssignableFrom(c) && !RuntimeException.class.isAssignableFrom(c) && !Error.class.isAssignableFrom(c);
   }

   public static String openModule(Class c) {
      StringBuffer result = new StringBuffer();
      String name = getIDLType(c, ".");

      for(int ind = name.indexOf("."); ind != -1; ind = name.indexOf(".")) {
         String subName = name.substring(0, ind);
         result.append("module " + subName + " {\n");
         name = name.substring(ind + 1);
      }

      return result.toString();
   }

   public static String closeModule(Class c) {
      StringBuffer result = new StringBuffer();
      String name = getIDLType(c, ".").substring(1);

      for(int ind = name.indexOf("."); ind != -1; ind = name.indexOf(".")) {
         result.append("};\n");
         name = name.substring(ind + 1);
      }

      return result.toString();
   }

   public static String generateGuard(Class c, String prefix) {
      String idlType = getIDLType(c, "_");
      return generateGuard(idlType, prefix);
   }

   public static String generateGuard(String idlType, String prefix) {
      String result = prefix + " __" + idlType + "__\n";
      return result;
   }

   static String removeChars(String s, char c) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < s.length(); ++i) {
         char c2 = s.charAt(i);
         if (c2 != c) {
            sb.append(c2);
         }
      }

      return sb.toString();
   }

   public static String generateInclude(String dir, Class cl) {
      StringBuffer result = new StringBuffer();
      String fileName = javaTypeToPath((String)null, cl);
      result.append("#include \"" + fileName + "\"\n");
      return result.toString();
   }

   public static void getAttributesFromMethods(Class itf, Hashtable outResult) throws CodeGenerationException {
      Hashtable h = new Hashtable();
      findIDLAttributes(itf, h);
      Enumeration e = h.elements();

      while(e.hasMoreElements()) {
         IDLField a = (IDLField)e.nextElement();
         outResult.put(a.getMangledName(), a);
      }

   }

   public static void findIDLAttributes(Class itf, Hashtable outAttributes) {
      Method[] methods = itf.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         if (isValid(m)) {
            boolean get = IDLMangler.isGetter(m) || IDLMangler.isIsser(m);
            boolean set = IDLMangler.isSetter(m);
            if (get || set) {
               Class type = null;
               if (get) {
                  type = m.getReturnType();
               } else {
                  type = m.getParameterTypes()[0];
               }

               int attModifier = set ? 2 : 1;
               String accName = IDLMangler.accessorToAttribute(m.getName());
               IDLAttribute a = (IDLAttribute)outAttributes.get(accName);
               if (null == a) {
                  a = new IDLAttribute(accName, attModifier, (Object)null, type);
                  outAttributes.put(accName, a);
               } else if (set) {
                  a.setModifier(2);
               }
            }
         }
      }

   }

   public static boolean isASetterFor(Field f, Method m) {
      return isAnAccessorFor(f, m, "set");
   }

   public static boolean isAGetterFor(Field f, Method m) {
      return isAnAccessorFor(f, m, "get") && isAnAccessorFor(f, m, "is");
   }

   public static boolean isAnAccessorFor(Field f, Method m, String op) {
      boolean result = false;
      String methodName = m.getName();
      int mod = m.getModifiers();
      if (!Modifier.isNative(mod) && (!op.equals("set") || IDLMangler.isSetter(m)) && (!op.equals("get") || IDLMangler.isGetter(m)) && (!op.equals("is") || IDLMangler.isIsser(m)) && (f == null || IDLMangler.accessorToAttribute(methodName).equals(IDLMangler.normalizeJavaName(f.getName())))) {
         if ("set".equals(op)) {
            if (null == f || m.getParameterTypes()[0].equals(f.getType())) {
               result = true;
            }
         } else if (null == f || m.getReturnType().equals(f.getType())) {
            result = true;
         }

         return result;
      } else {
         return false;
      }
   }

   public static void getAttributesFromFields(Class itf, Hashtable outResult) throws CodeGenerationException {
      Field[] fields = itf.getDeclaredFields();

      for(int i = 0; i < fields.length; ++i) {
         Field f = fields[i];
         if (isValidField(f)) {
            IDLField a = new IDLField(itf, f);

            while(outResult.get(a.getMangledName()) != null) {
               a.setMangledName(a.getMangledName() + "_");
            }

            outResult.put(a.getMangledName(), a);
         }
      }

   }

   public static boolean attributeMustBeIgnored(String attName) {
      return "serialVersionUID".equals(attName);
   }

   public static String mangleAttributeName(Class c, Field f) {
      return IDLMangler.convertIllegalCharacters(f.getName());
   }

   public static boolean isThrown(Class c, Class ex) {
      Debug.assertion(Exception.class.isAssignableFrom(ex));
      Method[] methods = c.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         if (isValid(m)) {
            Class[] exs = m.getExceptionTypes();

            for(int j = 0; j < exs.length; ++j) {
               if (exs[j].equals(ex)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean isInheritable(Class c) {
      boolean result = true;
      if (c.equals(Object.class) || CorbaUtils.isRemote(c) || Serializable.class.equals(c)) {
         result = false;
      }

      return result;
   }

   public static boolean isMethodForAnAttribute(Class c, Method m) {
      return IDLMangler.isIsser(m) || IDLMangler.isGetter(m) || IDLMangler.isSetter(m);
   }

   public static String mangleExceptionName(String e) {
      int i = e.lastIndexOf("Exception");
      if (i != -1) {
         e = e.substring(0, i) + "Ex";
      } else {
         e = e + "Ex";
      }

      return e;
   }

   public static boolean mustSkipClass(Class c) {
      return mustSkipClass(c, isValueType(c));
   }

   public static boolean mustSkipClass(Class c, String idlString) {
      boolean isValueType = -1 != idlString.indexOf("valuetype");
      return mustSkipClass(c, isValueType);
   }

   private static boolean mustSkipClass(Class c, boolean isValueType) {
      boolean result = false;
      boolean isSequence = CorbaUtils.isValid(c) && null != c.getComponentType();
      result = isValueType || isSequence;
      return result;
   }

   public static String getMangledMethodName(Method current, Method[] methods) throws CodeGenerationException {
      return IDLMangler.getMangledMethodName(current);
   }

   private static void p(String s) {
      System.out.println("***<IDLUtils> " + s);
   }
}
