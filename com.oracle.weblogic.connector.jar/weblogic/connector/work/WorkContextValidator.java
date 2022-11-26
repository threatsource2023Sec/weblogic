package weblogic.connector.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.resource.spi.work.HintsContext;
import javax.resource.spi.work.SecurityContext;
import javax.resource.spi.work.TransactionContext;
import javax.resource.spi.work.WorkContext;
import weblogic.connector.common.Debug;
import weblogic.connector.security.layer.WorkContextWrapper;

public class WorkContextValidator {
   final List supportedContextClasses = new ArrayList(3);
   static final Class[] buildinContextClasses = new Class[]{TransactionContext.class, HintsContext.class, SecurityContext.class};

   public void registerSupportedContext(Class wc) {
      if (this.supportedContextClasses.contains(wc)) {
         throw new RuntimeException("cannot register duplicate validator for same WorkContext:" + wc);
      } else {
         this.supportedContextClasses.add(wc);
      }
   }

   public void unregisterSupportedContext(Class wc) {
      if (!this.supportedContextClasses.contains(wc)) {
         throw new RuntimeException("cannot unregister unknown validator for WorkContext:" + wc);
      } else {
         this.supportedContextClasses.remove(wc);
      }
   }

   public List getSupportedContextClasses() {
      return this.supportedContextClasses;
   }

   public List removeNullElements(List contexts) {
      List newList = new ArrayList();
      Iterator var3 = contexts.iterator();

      while(var3.hasNext()) {
         WorkContextWrapper wc = (WorkContextWrapper)var3.next();
         if (wc != null) {
            newList.add(wc);
         }
      }

      return newList;
   }

   public static List checkRequiredWorkContexts(Class[] contextClasses) {
      List unsupported = new ArrayList();

      assert contextClasses != null;

      Class[] var2 = contextClasses;
      int var3 = contextClasses.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class wcClass = var2[var4];
         boolean support = false;
         Class[] var7 = buildinContextClasses;
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Class supportedClass = var7[var9];
            if (supportedClass.equals(wcClass)) {
               support = true;
            }
         }

         if (!support) {
            if (Debug.isWorkEnabled()) {
               Debug.work("checkRequiredContextClasses: find unsupported context: " + wcClass);
            }

            unsupported.add(wcClass);
         }
      }

      return unsupported;
   }

   public Map checkRequiredContextClasses(String[] contextClassNames, ClassLoader cl) {
      Map map = new HashMap();
      if (contextClassNames == null) {
         return map;
      } else {
         String[] var4 = contextClassNames;
         int var5 = contextClassNames.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String className = var4[var6];
            if (className != null && !"".equals(className.trim())) {
               Class cls = null;

               Object list;
               try {
                  cls = cl.loadClass(className);
               } catch (Throwable var11) {
                  if (Debug.isWorkEnabled()) {
                     Debug.work("checkRequiredContextClasses: Error_ClassNotFound-->" + className + ": " + var11);
                  }

                  list = (Map)map.get(WorkContextValidator.CheckRequiredResult.Error_ClassNotFound);
                  if (list == null) {
                     list = new HashMap();
                     map.put(WorkContextValidator.CheckRequiredResult.Error_ClassNotFound, list);
                  }

                  ((Map)list).put(className, var11);
                  continue;
               }

               if (!WorkContext.class.isAssignableFrom(cls)) {
                  if (Debug.isWorkEnabled()) {
                     Debug.work("checkRequiredContextClasses: Error_NotImplementInflowContext-->" + className);
                  }

                  List list = (List)map.get(WorkContextValidator.CheckRequiredResult.Error_NotImplementInflowContext);
                  if (list == null) {
                     list = new ArrayList();
                     map.put(WorkContextValidator.CheckRequiredResult.Error_NotImplementInflowContext, list);
                  }

                  ((List)list).add(className);
               } else if (!this.isContextSupported(cls)) {
                  if (Debug.isWorkEnabled()) {
                     Debug.work("checkRequiredContextClasses: Error_UnsupportedContextClass-->" + className);
                  }

                  list = (List)map.get(WorkContextValidator.CheckRequiredResult.Error_UnsupportedContextClass);
                  if (list == null) {
                     list = new ArrayList();
                     map.put(WorkContextValidator.CheckRequiredResult.Error_UnsupportedContextClass, list);
                  }

                  ((List)list).add(className);
               } else {
                  list = (List)map.get(WorkContextValidator.CheckRequiredResult.OK_Supported);
                  if (list == null) {
                     list = new ArrayList();
                     map.put(WorkContextValidator.CheckRequiredResult.OK_Supported, list);
                  }

                  ((List)list).add(cls);
                  if (Debug.isWorkEnabled()) {
                     Debug.work("checkRequiredContextClasses: OK_Supported-->" + className);
                  }
               }
            }
         }

         return map;
      }
   }

   public boolean isContextSupported(Class contextClass) {
      assert contextClass != null;

      boolean support = this.supportedContextClasses.contains(contextClass);
      return support;
   }

   public Class getSupportedClass(WorkContextWrapper context) {
      return this.getSupportedClass(context.getOriginalClass());
   }

   public Class getSupportedClass(Class originalClass) {
      Class supportedCls;
      for(supportedCls = null; !Object.class.equals(originalClass) && WorkContext.class.isAssignableFrom(originalClass); originalClass = originalClass.getSuperclass()) {
         if (this.supportedContextClasses.contains(originalClass)) {
            supportedCls = originalClass;
            break;
         }
      }

      return supportedCls;
   }

   public List checkUnSupportedContexts(List contexts) {
      assert contexts != null;

      List unsupported = new ArrayList();
      Iterator var3 = contexts.iterator();

      while(var3.hasNext()) {
         WorkContextWrapper wc = (WorkContextWrapper)var3.next();
         if (this.getSupportedClass(wc) == null) {
            if (Debug.isWorkEnabled()) {
               Debug.work("checkUnSupportedContexts: find unsupported context " + wc + ":" + wc.getOriginalClass());
            }

            unsupported.add(wc);
         }
      }

      return unsupported;
   }

   public Map checkDuplicatedContexts(List contexts) {
      assert contexts != null;

      Map dupMap = new HashMap();
      Object[] wcArray = contexts.toArray();
      if (wcArray.length == 0) {
         return dupMap;
      } else {
         Class[] wcClassArray = new Class[wcArray.length];

         for(int i = 0; i < wcArray.length; ++i) {
            WorkContextWrapper wc = (WorkContextWrapper)wcArray[i];
            Class cls = this.getSupportedClass(wc);

            assert cls != null;

            wcClassArray[i] = cls;

            for(int j = 0; j < i; ++j) {
               if (wcClassArray[j] == cls) {
                  Set dupSet = (Set)dupMap.get(cls);
                  if (dupSet == null) {
                     dupSet = new HashSet();
                     dupMap.put(cls, dupSet);
                  }

                  ((Set)dupSet).add((WorkContextWrapper)wcArray[j]);
                  ((Set)dupSet).add((WorkContextWrapper)wcArray[i]);
                  if (Debug.isWorkEnabled()) {
                     Debug.work("checkDuplicatedContexts: find duplicate contexts: " + wc + " dup with " + wcArray[j]);
                  }
               }
            }
         }

         return dupMap;
      }
   }

   public static enum CheckRequiredResult {
      Error_ClassNotFound,
      Error_NotImplementInflowContext,
      Error_UnsupportedContextClass,
      OK_Supported;
   }
}
