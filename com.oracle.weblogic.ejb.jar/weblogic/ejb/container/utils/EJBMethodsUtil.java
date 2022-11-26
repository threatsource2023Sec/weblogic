package weblogic.ejb.container.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.j2ee.dd.xml.WseeAnnotationProcessor;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.annotation.BeaSynthetic.Helper;
import weblogic.utils.collections.EnumerationIterator;
import weblogic.utils.reflect.MethodText;
import weblogic.utils.reflect.ReflectUtils;

public final class EJBMethodsUtil {
   public static final short STANDARD_METHOD = 0;
   public static final short HOME_METHOD = 1;
   private static final Set EO_METHS = initWith(EJBObject.class);
   private static final Set EH_METHS = initWith(EJBHome.class);
   private static final Set ELO_METHS = initWith(EJBLocalObject.class);
   private static final Set ELH_METHS = initWith(EJBLocalHome.class);

   private static Set initWith(Class clazz) {
      Method[] methods = clazz.getMethods();
      Set set = new HashSet(methods.length);
      Method[] var3 = methods;
      int var4 = methods.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         set.add(new weblogic.utils.reflect.MethodKey(m));
      }

      return set;
   }

   private EJBMethodsUtil() {
   }

   public static Map getMethodSigs(Method[] methods) {
      return methodSigsInternal(methods, false);
   }

   public static Map getHomeMethodSigs(Method[] methods) {
      return methodSigsInternal(methods, true);
   }

   private static Map methodSigsInternal(Method[] methods, boolean isHome) {
      if (methods == null) {
         return Collections.emptyMap();
      } else {
         Map map = new HashMap(methods.length);
         MethodText methodText = new MethodText();
         Method[] var4 = methods;
         int var5 = methods.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method m = var4[var6];
            methodText.setMethod(m);
            methodText.setOptions(128);
            if (isHome) {
               map.put(m, homeClassMethodNameMapper(methodText.toString()));
            } else {
               map.put(m, methodText.toString());
            }
         }

         return map;
      }
   }

   public static String homeClassMethodNameMapper(String sig) {
      if (sig.startsWith("create")) {
         return "ejbC" + sig.substring(1);
      } else {
         return sig.startsWith("find") ? "ejbF" + sig.substring(1) : "ejbHome" + sig;
      }
   }

   public static Method[] getRemoteMethods(Class clazz, boolean filterDups) {
      final Set methods = EO_METHS;
      return getMethodsInternal(clazz, new MethodFilter() {
         public boolean accept(Method method) {
            return !methods.contains(new weblogic.utils.reflect.MethodKey(method));
         }
      }, filterDups);
   }

   public static Method[] getLocalMethods(Class clazz, boolean filterDups) {
      final Set methods = ELO_METHS;
      return getMethodsInternal(clazz, new MethodFilter() {
         public boolean accept(Method method) {
            return !methods.contains(new weblogic.utils.reflect.MethodKey(method));
         }
      }, filterDups);
   }

   public static Method[] getMethods(Class clazz, boolean filterDups) {
      return getMethodsInternal(clazz, (MethodFilter)null, filterDups);
   }

   private static Method[] getMethodsInternal(Class clazz, MethodFilter mf, boolean filterDups) {
      List toReturn = new ArrayList();
      if (filterDups) {
         Enumeration e = ReflectUtils.distinctInterfaceMethods(clazz);

         while(true) {
            Method method;
            do {
               do {
                  if (!e.hasMoreElements()) {
                     return (Method[])toReturn.toArray(new Method[toReturn.size()]);
                  }

                  method = (Method)e.nextElement();
               } while(Helper.isBeaSyntheticMethod(method));
            } while(mf != null && !mf.accept(method));

            toReturn.add(method);
         }
      } else {
         Method[] var8 = clazz.getMethods();
         int var9 = var8.length;

         for(int var6 = 0; var6 < var9; ++var6) {
            Method m = var8[var6];
            if (!Helper.isBeaSyntheticMethod(m) && (mf == null || mf.accept(m))) {
               toReturn.add(m);
            }
         }

         return (Method[])toReturn.toArray(new Method[toReturn.size()]);
      }
   }

   public static Method[] getBeanHomeClassMethods(Class clazz) {
      List toReturn = new ArrayList();
      Method[] var2 = clazz.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         String methodName = m.getName();
         if (methodName.startsWith("ejbHome") || methodName.startsWith("ejbCreate") || methodName.startsWith("ejbFind")) {
            toReturn.add(m);
         }
      }

      return (Method[])toReturn.toArray(new Method[toReturn.size()]);
   }

   public static Method[] getLocalCreateMethods(Class localHomeIntf) {
      return getMethodsInternal(localHomeIntf, ELH_METHS, "create");
   }

   public static Method[] getCreateMethods(Class homeIntf) {
      return getMethodsInternal(homeIntf, EH_METHS, "create");
   }

   public static Method[] getLocalFindMethods(Class localHomeIntf) {
      return getMethodsInternal(localHomeIntf, ELH_METHS, "find");
   }

   public static Method[] getFindMethods(Class homeIntf) {
      return getMethodsInternal(homeIntf, EH_METHS, "find");
   }

   private static Method[] getMethodsInternal(Class intf, Set excludes, String startString) {
      List matchedMethods = new ArrayList();
      Method[] var4 = intf.getMethods();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method m = var4[var6];
         if (!excludes.contains(new weblogic.utils.reflect.MethodKey(m)) && m.getName().startsWith(startString)) {
            matchedMethods.add(m);
         }
      }

      if (matchedMethods.isEmpty()) {
         return null;
      } else {
         return (Method[])matchedMethods.toArray(new Method[matchedMethods.size()]);
      }
   }

   public static Method[] getWebserviceMethods(SessionBeanInfo sbi) {
      if (sbi.isEJB30()) {
         WseeAnnotationProcessor wseeAP = (WseeAnnotationProcessor)GlobalServiceLocator.getServiceLocator().getService(WseeAnnotationProcessor.class, new Annotation[0]);
         return wseeAP == null ? new Method[0] : (Method[])wseeAP.getWebServiceMethods(sbi.getBeanClass(), sbi.getServiceEndpointClass()).toArray(new Method[0]);
      } else {
         Enumeration e = ReflectUtils.distinctInterfaceMethods(sbi.getServiceEndpointClass());
         List result = new ArrayList();
         Iterator methods = new EnumerationIterator(e);

         while(methods.hasNext()) {
            Method m = (Method)methods.next();
            if (!Modifier.isVolatile(m.getModifiers()) && !ELO_METHS.contains(new weblogic.utils.reflect.MethodKey(m))) {
               String name = m.getName();
               if (!name.equals("remove") && !name.equals("create") && !name.equals("getEJBHome") && !name.equals("getPrimaryKey") && !name.equals("getHandle") && !name.equals("isIdentical")) {
                  result.add(m);
               }
            }
         }

         return (Method[])result.toArray(new Method[result.size()]);
      }
   }

   public static String methodDescriptorPrefix(short type) {
      switch (type) {
         case 0:
         default:
            return "md_eo_";
         case 1:
            return "md_";
      }
   }

   public static Map categorizeNoInterfaceMethods(Class beanClass) {
      Set allMethods = new HashSet();
      List publicMethods = new ArrayList();
      List protectedAndPackageMethods = new ArrayList();

      for(Class c = beanClass; c != Object.class; c = c.getSuperclass()) {
         Method[] var5 = c.getDeclaredMethods();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method m = var5[var7];
            String methodDesc = stringify(m.getName(), m.getReturnType(), m.getParameterTypes());
            if (!allMethods.contains(methodDesc) && !Helper.isBeaSyntheticMethod(m)) {
               allMethods.add(methodDesc);
               int mods = m.getModifiers();
               if (!Modifier.isStatic(mods)) {
                  if (Modifier.isPublic(mods)) {
                     publicMethods.add(m);
                  } else if (!Modifier.isPrivate(mods)) {
                     protectedAndPackageMethods.add(m);
                  }
               }
            }
         }
      }

      Map categorized = new HashMap();
      categorized.put(1, publicMethods.toArray(new Method[publicMethods.size()]));
      categorized.put(-4, protectedAndPackageMethods.toArray(new Method[protectedAndPackageMethods.size()]));
      return categorized;
   }

   private static String stringify(String name, Class retType, Class... params) {
      StringBuilder sb = new StringBuilder(name);
      sb.append(":(");
      if (params != null) {
         Class[] var4 = params;
         int var5 = params.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class p = var4[var6];
            sb.append(p.getName());
         }
      }

      sb.append(")");
      sb.append(retType.getName());
      return sb.toString();
   }

   public static Method[] getNoInterfaceViewBusinessMethods(Class beanClass) {
      return (Method[])categorizeNoInterfaceMethods(beanClass).get(1);
   }

   public static Method getInvokableMethod(Class clazz, String name, Class... params) {
      Method m = findBeanMethod(clazz, name, params);
      if (Modifier.isPrivate(m.getModifiers())) {
         m.setAccessible(true);
      }

      return m;
   }

   public static Method findBeanMethod(Class clazz, String name, Class... params) {
      Class c = clazz;

      while(c != Object.class && c != null) {
         try {
            return c.getDeclaredMethod(name, params);
         } catch (NoSuchMethodException var5) {
            c = c.getSuperclass();
         }
      }

      throw new AssertionError("Bean class " + clazz.getName() + " missing method " + name + " params : " + Arrays.toString(params));
   }

   private interface MethodFilter {
      boolean accept(Method var1);
   }
}
