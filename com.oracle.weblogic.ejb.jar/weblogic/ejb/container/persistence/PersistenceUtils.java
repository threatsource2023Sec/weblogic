package weblogic.ejb.container.persistence;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.dd.xml.DDUtils;

public final class PersistenceUtils {
   public static final String PERSISTENCE_VERBOSE_PROP = "weblogic.ejb.container.persistence.verbose";
   public static final String PERSISTENCE_DEBUG_PROP = "weblogic.ejb.container.persistence.debug";
   protected static final String[] validPersistencePublicIds = new String[]{"-//BEA Systems, Inc.//DTD WebLogic 6.0.0 Persistence Vendor//EN"};
   public static final String RDBMS_CMP_RESOURCE_NAME = "WebLogic_CMP_RDBMS.xml";

   public static Map getAccessorMethodMap(Class beanClass) {
      Class cur = beanClass;

      HashMap accessors;
      for(accessors = new HashMap(); cur != null && cur != Object.class; cur = cur.getSuperclass()) {
         Method[] methods = cur.getMethods();
         Method[] var4 = methods;
         int var5 = methods.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method m = var4[var6];
            String name = m.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
               int modifiers = m.getModifiers();
               if (Modifier.isAbstract(modifiers) && (cur == beanClass || Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers))) {
                  accessors.put(m.getName(), m);
               }
            }
         }
      }

      return accessors;
   }

   public static Collection getAbstractMethodCollection(Class beanClass) {
      Class cur = beanClass;
      Collection abstracts = new ArrayList();
      Set abstractSigs = new HashSet();
      Set concreteSigs = new HashSet();
      Set methodObjects = new HashSet();

      while(cur != null && cur != Object.class) {
         methodObjects.addAll(Arrays.asList(cur.getDeclaredMethods()));
         if (cur == beanClass) {
            methodObjects.addAll(Arrays.asList(cur.getMethods()));
         }

         Iterator var6 = methodObjects.iterator();

         while(true) {
            while(var6.hasNext()) {
               Method m = (Method)var6.next();
               int modifiers = m.getModifiers();
               if (Modifier.isAbstract(modifiers) && (cur == beanClass || Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers))) {
                  String methodSig = DDUtils.getMethodSignature(m);
                  if (!concreteSigs.contains(methodSig) && !abstractSigs.contains(methodSig)) {
                     abstracts.add(m);
                     abstractSigs.add(methodSig);
                  }
               } else {
                  concreteSigs.add(DDUtils.getMethodSignature(m));
               }
            }

            cur = cur.getSuperclass();
            methodObjects.clear();
            break;
         }
      }

      return abstracts;
   }

   public static Method getMethodIncludeSuper(Class cls, String name, Class[] params) {
      Class cur = cls;
      boolean found = false;

      Method m;
      for(m = null; cur != null && !found; cur = cur.getSuperclass()) {
         try {
            m = cur.getMethod(name, params);
            int modifiers = m.getModifiers();
            if (cur == cls || Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
               found = true;
            }
         } catch (NoSuchMethodException var7) {
         }
      }

      return found ? m : null;
   }
}
