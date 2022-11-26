package weblogic.persistence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.kernel.ResettableThreadLocal;
import weblogic.kernel.ThreadLocalInitialValue;

public final class ExtendedPersistenceContextManager {
   private static final ResettableThreadLocal THREAD_LOCAL = new ResettableThreadLocal(new ThreadLocalInitialValue() {
      protected Object initialValue() {
         return new HashMap();
      }
   });

   private ExtendedPersistenceContextManager() {
   }

   public static ExtendedPersistenceContextWrapper getExtendedPersistenceContext(String name) {
      return (ExtendedPersistenceContextWrapper)getMap().get(name);
   }

   public static void setExtendedPersistenceContext(String name, ExtendedPersistenceContextWrapper epcWrapper) {
      getMap().put(name, epcWrapper);
   }

   public static void removeExtendedPersistenceContext(String name) {
      getMap().remove(name);
   }

   public static Set pushMissingWrappers(Set epcs) {
      Map map = getMap();
      Set pushedPCs = null;
      Iterator var3 = epcs.iterator();

      while(var3.hasNext()) {
         ExtendedPersistenceContextWrapper wrap = (ExtendedPersistenceContextWrapper)var3.next();
         if (map.get(wrap.getPersistenceUnitName()) == null) {
            map.put(wrap.getPersistenceUnitName(), wrap);
            if (pushedPCs == null) {
               pushedPCs = new HashSet();
            }

            pushedPCs.add(wrap.getPersistenceUnitName());
         }
      }

      return pushedPCs;
   }

   public static void popWrappers(Set names) {
      Map map = getMap();
      Iterator var2 = names.iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         map.remove(name);
      }

   }

   private static Map getMap() {
      return (Map)THREAD_LOCAL.get();
   }
}
