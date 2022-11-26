package org.apache.openjpa.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.ManagedInstanceProvider;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.ReflectingPersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.LockManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.UUIDGenerator;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.meta.SequenceMetaData;

public class ImplHelper {
   private static final Map _assignableTypes = new ConcurrentReferenceHashMap(1, 0);
   public static final Map _unenhancedInstanceMap = new ConcurrentReferenceHashMap(1, 0) {
      protected boolean eq(Object x, Object y) {
         if (x instanceof Map.Entry) {
            return super.eq(x, y);
         } else {
            return x == y;
         }
      }

      protected int hc(Object o) {
         return o instanceof Map.Entry ? super.hc(o) : System.identityHashCode(o);
      }
   };

   public static Collection loadAll(Collection sms, StoreManager store, PCState state, int load, FetchConfiguration fetch, Object context) {
      Collection failed = null;
      Iterator itr = sms.iterator();

      while(true) {
         while(itr.hasNext()) {
            OpenJPAStateManager sm = (OpenJPAStateManager)itr.next();
            if (sm.getManagedInstance() == null) {
               if (!store.initialize(sm, state, fetch, context)) {
                  failed = addFailedId(sm, failed);
               }
            } else if (load == 0 && sm.getPCState() != PCState.HOLLOW) {
               if (!store.exists(sm, context)) {
                  failed = addFailedId(sm, failed);
               }
            } else {
               LockManager lm = sm.getContext().getLockManager();
               if (!store.load(sm, sm.getUnloaded(fetch), fetch, lm.getLockLevel(sm), context)) {
                  failed = addFailedId(sm, failed);
               }
            }
         }

         return (Collection)(failed == null ? Collections.EMPTY_LIST : failed);
      }
   }

   private static Collection addFailedId(OpenJPAStateManager sm, Collection failed) {
      if (failed == null) {
         failed = new ArrayList();
      }

      ((Collection)failed).add(sm.getId());
      return (Collection)failed;
   }

   public static Object generateIdentityValue(StoreContext ctx, ClassMetaData meta, int typeCode) {
      return generateValue(ctx, meta, (FieldMetaData)null, typeCode);
   }

   public static Object generateFieldValue(StoreContext ctx, FieldMetaData fmd) {
      return generateValue(ctx, fmd.getDefiningMetaData(), fmd, fmd.getDeclaredTypeCode());
   }

   private static Object generateValue(StoreContext ctx, ClassMetaData meta, FieldMetaData fmd, int typeCode) {
      int strategy = fmd == null ? meta.getIdentityStrategy() : fmd.getValueStrategy();
      switch (strategy) {
         case 2:
            SequenceMetaData smd = fmd == null ? meta.getIdentitySequenceMetaData() : fmd.getValueSequenceMetaData();
            return JavaTypes.convert(smd.getInstance(ctx.getClassLoader()).next(ctx, meta), typeCode);
         case 3:
         case 4:
         default:
            return null;
         case 5:
            return UUIDGenerator.nextString();
         case 6:
            return UUIDGenerator.nextHex();
      }
   }

   public static BitSet getUpdateFields(OpenJPAStateManager sm) {
      if (sm.getPCState() == PCState.PDIRTY && (!sm.isFlushed() || sm.isFlushedDirty()) || sm.getPCState() == PCState.PNEW && sm.isFlushedDirty()) {
         BitSet dirty = sm.getDirty();
         if (sm.isFlushed()) {
            dirty = (BitSet)dirty.clone();
            dirty.andNot(sm.getFlushed());
         }

         if (dirty.length() > 0) {
            return dirty;
         }
      }

      return null;
   }

   public static void close(Object o) {
      try {
         if (o instanceof Closeable) {
            ((Closeable)o).close();
         }

      } catch (RuntimeException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new GeneralException(var3);
      }
   }

   public static boolean isManagedType(OpenJPAConfiguration conf, Class type) {
      return PersistenceCapable.class.isAssignableFrom(type) || type != null && (conf == null || conf.getRuntimeUnenhancedClassesConstant() == 0) && PCRegistry.isRegistered(type);
   }

   public static boolean isManageable(Object instance) {
      return instance instanceof PersistenceCapable || instance != null && PCRegistry.isRegistered(instance.getClass());
   }

   public static boolean isAssignable(Class from, Class to) {
      if (from != null && to != null) {
         Boolean isAssignable = null;
         Map assignableTo = (Map)_assignableTypes.get(from);
         if (assignableTo == null) {
            assignableTo = new ConcurrentReferenceHashMap(1, 0);
            _assignableTypes.put(from, assignableTo);
         } else {
            isAssignable = (Boolean)((Map)assignableTo).get(to);
         }

         if (isAssignable == null) {
            isAssignable = from.isAssignableFrom(to);
            ((Map)assignableTo).put(to, isAssignable);
         }

         return isAssignable;
      } else {
         return false;
      }
   }

   public static PersistenceCapable toPersistenceCapable(Object o, Object ctx) {
      if (o instanceof PersistenceCapable) {
         return (PersistenceCapable)o;
      } else {
         OpenJPAConfiguration conf = null;
         if (ctx instanceof OpenJPAConfiguration) {
            conf = (OpenJPAConfiguration)ctx;
         } else if (ctx instanceof StateManager && ((StateManager)ctx).getGenericContext() instanceof StoreContext) {
            conf = ((StoreContext)((StateManager)ctx).getGenericContext()).getConfiguration();
         }

         if (!isManageable(o)) {
            return null;
         } else {
            synchronized(o) {
               PersistenceCapable pc = (PersistenceCapable)_unenhancedInstanceMap.get(o);
               if (pc != null) {
                  return pc;
               } else if (conf == null) {
                  return null;
               } else {
                  PersistenceCapable pc = new ReflectingPersistenceCapable(o, conf);
                  _unenhancedInstanceMap.put(o, pc);
                  return pc;
               }
            }
         }
      }
   }

   public static void registerPersistenceCapable(ReflectingPersistenceCapable pc) {
      _unenhancedInstanceMap.put(pc.getManagedInstance(), pc);
   }

   public static Object getManagedInstance(Object o) {
      return o instanceof ManagedInstanceProvider ? ((ManagedInstanceProvider)o).getManagedInstance() : o;
   }
}
