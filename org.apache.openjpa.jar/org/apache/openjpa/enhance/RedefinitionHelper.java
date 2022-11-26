package org.apache.openjpa.enhance;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.openjpa.kernel.StateManagerImpl;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ImplHelper;

public class RedefinitionHelper {
   public static void dirtyCheck(StateManager sm) {
      if (sm instanceof StateManagerImpl) {
         ((StateManagerImpl)sm).dirtyCheck();
      }

   }

   public static void accessingField(Object o, int absoluteIndex) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.accessingField(absoluteIndex);
         }

      }
   }

   public static void settingField(Object o, int idx, boolean cur, boolean next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingBooleanField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, char cur, char next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingCharField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, byte cur, byte next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingByteField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, short cur, short next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingShortField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, int cur, int next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingIntField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, long cur, long next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingLongField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, float cur, float next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingFloatField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, double cur, double next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingDoubleField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, String cur, String next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingStringField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void settingField(Object o, int idx, Object cur, Object next) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
      if (pc != null) {
         StateManager sm = pc.pcGetStateManager();
         if (sm != null) {
            sm.settingObjectField(pc, idx, cur, next, 0);
         }

      }
   }

   public static void assignLazyLoadProxies(StateManagerImpl sm) {
      FieldMetaData[] fmds = sm.getMetaData().getFields();
      int i = 0;

      while(i < fmds.length) {
         switch (fmds[i].getTypeCode()) {
            case 12:
            case 13:
               PersistenceCapable pc = sm.getPersistenceCapable();
               Field field = (Field)fmds[i].getBackingMember();
               Reflection.set(pc, (Field)field, (Object)newLazyLoadingProxy(fmds[i].getDeclaredType(), i, sm));
            default:
               ++i;
         }
      }

   }

   private static Object newLazyLoadingProxy(Class type, final int idx, final StateManagerImpl sm) {
      InvocationHandler handler = new InvocationHandler() {
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object delegate = sm.fetch(idx);
            return method.invoke(delegate, args);
         }
      };
      return Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, handler);
   }
}
