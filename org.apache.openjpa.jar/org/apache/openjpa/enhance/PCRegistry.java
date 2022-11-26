package org.apache.openjpa.enhance;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap;
import org.apache.openjpa.util.UserException;

public class PCRegistry {
   private static final Localizer _loc = Localizer.forPackage(PCRegistry.class);
   private static final Map _metas = new ConcurrentReferenceHashMap(1, 0);
   private static final Collection _listeners = new LinkedList();

   public static void addRegisterClassListener(RegisterClassListener rcl) {
      if (rcl != null) {
         synchronized(_listeners) {
            _listeners.add(rcl);
         }

         synchronized(_metas) {
            Iterator itr = _metas.keySet().iterator();

            while(itr.hasNext()) {
               rcl.register((Class)itr.next());
            }

         }
      }
   }

   public static void removeRegisterClassListener(RegisterClassListener rcl) {
      synchronized(_listeners) {
         _listeners.remove(rcl);
      }
   }

   public static String[] getFieldNames(Class pcClass) {
      Meta meta = getMeta(pcClass);
      return meta.fieldNames;
   }

   public static Class[] getFieldTypes(Class pcClass) {
      Meta meta = getMeta(pcClass);
      return meta.fieldTypes;
   }

   public static Class getPersistentSuperclass(Class pcClass) {
      Meta meta = getMeta(pcClass);
      return meta.pcSuper;
   }

   public static PersistenceCapable newInstance(Class pcClass, StateManager sm, boolean clear) {
      Meta meta = getMeta(pcClass);
      return meta.pc == null ? null : meta.pc.pcNewInstance(sm, clear);
   }

   public static PersistenceCapable newInstance(Class pcClass, StateManager sm, Object oid, boolean clear) {
      Meta meta = getMeta(pcClass);
      return meta.pc == null ? null : meta.pc.pcNewInstance(sm, oid, clear);
   }

   public static Class getPCType(Class type) {
      Meta meta = getMeta(type);
      return meta.pc == null ? null : meta.pc.getClass();
   }

   public static Object newObjectId(Class pcClass) {
      Meta meta = getMeta(pcClass);
      return meta.pc == null ? null : meta.pc.pcNewObjectIdInstance();
   }

   public static Object newObjectId(Class pcClass, String str) {
      Meta meta = getMeta(pcClass);
      return meta.pc == null ? null : meta.pc.pcNewObjectIdInstance(str);
   }

   public static String getTypeAlias(Class pcClass) {
      return getMeta(pcClass).alias;
   }

   public static void copyKeyFieldsToObjectId(Class pcClass, FieldSupplier fm, Object oid) {
      Meta meta = getMeta(pcClass);
      if (meta.pc == null) {
         throw new UserException(_loc.get("copy-no-id", (Object)pcClass));
      } else {
         meta.pc.pcCopyKeyFieldsToObjectId(fm, oid);
      }
   }

   public static void copyKeyFieldsFromObjectId(Class pcClass, FieldConsumer fm, Object oid) {
      Meta meta = getMeta(pcClass);
      if (meta.pc == null) {
         throw new UserException(_loc.get("copy-no-id", (Object)pcClass));
      } else {
         meta.pc.pcCopyKeyFieldsFromObjectId(fm, oid);
      }
   }

   public static void register(Class pcClass, String[] fieldNames, Class[] fieldTypes, byte[] fieldFlags, Class sup, String alias, PersistenceCapable pc) {
      if (pcClass == null) {
         throw new NullPointerException();
      } else {
         Meta meta = new Meta(pc, fieldNames, fieldTypes, sup, alias);
         synchronized(_metas) {
            _metas.put(pcClass, meta);
         }

         synchronized(_listeners) {
            Iterator i = _listeners.iterator();

            while(i.hasNext()) {
               ((RegisterClassListener)i.next()).register(pcClass);
            }

         }
      }
   }

   public static void deRegister(ClassLoader cl) {
      synchronized(_metas) {
         Iterator i = _metas.keySet().iterator();

         while(i.hasNext()) {
            Class pcClass = (Class)i.next();
            if (pcClass.getClassLoader() == cl) {
               _metas.remove(pcClass);
            }
         }

      }
   }

   public static Collection getRegisteredTypes() {
      return Collections.unmodifiableCollection(_metas.keySet());
   }

   public static boolean isRegistered(Class cls) {
      return _metas.containsKey(cls);
   }

   private static Meta getMeta(Class pcClass) {
      Meta ret = (Meta)_metas.get(pcClass);
      if (ret == null) {
         throw new IllegalStateException(_loc.get("no-meta", (Object)pcClass).getMessage());
      } else {
         return ret;
      }
   }

   private static class Meta {
      public final PersistenceCapable pc;
      public final String[] fieldNames;
      public final Class[] fieldTypes;
      public final Class pcSuper;
      public final String alias;

      public Meta(PersistenceCapable pc, String[] fieldNames, Class[] fieldTypes, Class pcSuper, String alias) {
         this.pc = pc;
         this.fieldNames = fieldNames;
         this.fieldTypes = fieldTypes;
         this.pcSuper = pcSuper;
         this.alias = alias;
      }
   }

   public interface RegisterClassListener {
      void register(Class var1);
   }
}
