package kodo.jdo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.datastore.DataStoreCache;
import javax.jdo.datastore.Sequence;
import javax.jdo.identity.ByteIdentity;
import javax.jdo.identity.CharIdentity;
import javax.jdo.identity.IntIdentity;
import javax.jdo.identity.LongIdentity;
import javax.jdo.identity.ObjectIdentity;
import javax.jdo.identity.ShortIdentity;
import javax.jdo.identity.SingleFieldIdentity;
import javax.jdo.identity.StringIdentity;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.kernel.LockLevels;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.ByteId;
import org.apache.openjpa.util.CharId;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.IntId;
import org.apache.openjpa.util.LongId;
import org.apache.openjpa.util.ObjectId;
import org.apache.openjpa.util.OpenJPAId;
import org.apache.openjpa.util.ShortId;
import org.apache.openjpa.util.StringId;

public class KodoJDOHelper extends JDOHelper implements LockLevels {
   public static final String PMF_KEY = "kodo.jdo.PersistenceManagerFactory";
   public static final String PM_KEY = "kodo.jdo.PersistenceManager";

   public static KodoPersistenceManagerFactory toPersistenceManagerFactory(BrokerFactory factory) {
      if (factory == null) {
         return null;
      } else {
         factory.lock();

         Object var2;
         try {
            KodoPersistenceManagerFactory pmf = (KodoPersistenceManagerFactory)factory.getUserObject("kodo.jdo.PersistenceManagerFactory");
            if (pmf == null) {
               pmf = new PersistenceManagerFactoryImpl(factory);
               factory.putUserObject("kodo.jdo.PersistenceManagerFactory", pmf);
            }

            var2 = pmf;
         } catch (Exception var6) {
            throw JDOExceptions.toJDOException(var6);
         } finally {
            factory.unlock();
         }

         return (KodoPersistenceManagerFactory)var2;
      }
   }

   public static BrokerFactory toBrokerFactory(PersistenceManagerFactory pmf) {
      return pmf == null ? null : ((PersistenceManagerFactoryImpl)pmf).getDelegate();
   }

   public static KodoPersistenceManager toPersistenceManager(Broker broker) {
      if (broker == null) {
         return null;
      } else if (broker.isClosed()) {
         return null;
      } else {
         broker.lock();

         Object var8;
         try {
            KodoPersistenceManager pm = (KodoPersistenceManager)broker.getUserObject("kodo.jdo.PersistenceManager");
            if (pm == null) {
               PersistenceManagerFactoryImpl pmf = (PersistenceManagerFactoryImpl)toPersistenceManagerFactory(broker.getBrokerFactory());
               pm = new PersistenceManagerImpl(pmf, broker);
               broker.putUserObject("kodo.jdo.PersistenceManager", pm);
            }

            var8 = pm;
         } catch (Exception var6) {
            throw JDOExceptions.toJDOException(var6);
         } finally {
            broker.unlock();
         }

         return (KodoPersistenceManager)var8;
      }
   }

   public static Broker toBroker(PersistenceManager pm) {
      return pm == null ? null : ((PersistenceManagerImpl)pm).getDelegate();
   }

   public static KodoPersistenceManagerFactory cast(PersistenceManagerFactory pmf) {
      return (KodoPersistenceManagerFactory)pmf;
   }

   public static KodoPersistenceManager cast(PersistenceManager pm) {
      return (KodoPersistenceManager)pm;
   }

   public static KodoQuery cast(Query q) {
      return (KodoQuery)q;
   }

   public static KodoExtent cast(Extent e) {
      return (KodoExtent)e;
   }

   public static KodoFetchPlan cast(FetchPlan fetch) {
      return (KodoFetchPlan)fetch;
   }

   public static KodoDataStoreCache cast(DataStoreCache cache) {
      return (KodoDataStoreCache)cache;
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory() {
      try {
         return toPersistenceManagerFactory(Bootstrap.getBrokerFactory());
      } catch (Exception var1) {
         throw JDOExceptions.toJDOException(var1);
      }
   }

   public static ClassMetaData getMetaData(Object o) {
      if (o == null) {
         return null;
      } else {
         PersistenceManager pm = getPersistenceManager(o);
         return pm == null ? null : getMetaData(pm, o.getClass());
      }
   }

   public static ClassMetaData getMetaData(PersistenceManager pm, Class cls) {
      if (pm == null) {
         throw new NullPointerException("pm == null");
      } else {
         KodoPersistenceManager kpm = cast(pm);

         try {
            return kpm.getConfiguration().getMetaDataRepositoryInstance().getMetaData(cls, kpm.getClassLoader(), false);
         } catch (Exception var4) {
            throw JDOExceptions.toJDOException(var4);
         }
      }
   }

   public static ClassMetaData getMetaData(PersistenceManagerFactory pmf, Class cls) {
      if (pmf == null) {
         throw new NullPointerException("pmf == null");
      } else {
         KodoPersistenceManagerFactory kpmf = cast(pmf);

         try {
            return kpmf.getConfiguration().getMetaDataRepositoryInstance().getMetaData(cls, (ClassLoader)null, false);
         } catch (Exception var4) {
            throw JDOExceptions.toJDOException(var4);
         }
      }
   }

   public static Sequence getIdentitySequence(Object o) {
      KodoPersistenceManager pm = cast(getPersistenceManager(o));
      return pm == null ? null : pm.getIdentitySequence(o.getClass());
   }

   public static Sequence getFieldSequence(Object o, String fieldName) {
      KodoPersistenceManager pm = cast(getPersistenceManager(o));
      return pm == null ? null : pm.getFieldSequence(o.getClass(), fieldName);
   }

   public static Object getDetachedState(Object o) {
      if (o != null && o instanceof PersistenceCapable) {
         Object state = ((PersistenceCapable)o).pcGetDetachedState();
         return state == PersistenceCapable.DESERIALIZED ? null : state;
      } else {
         return null;
      }
   }

   public static void setDetachedState(Object o, Object state) {
      if (o != null && o instanceof PersistenceCapable) {
         ((PersistenceCapable)o).pcSetDetachedState(state);
      }

   }

   public static int getLockLevel(Object o) {
      PersistenceManager pm = getPersistenceManager(o);
      if (pm == null) {
         return 0;
      } else {
         try {
            return toBroker(pm).getLockLevel(o);
         } catch (Exception var3) {
            throw JDOExceptions.toJDOException(var3);
         }
      }
   }

   public static void close(Object o) {
      try {
         ImplHelper.close(o);
      } catch (Exception var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public static Object fromKodoObjectId(Object oid) {
      if (oid instanceof OpenJPAId && !(oid instanceof Id)) {
         switch (oid.getClass().getName().charAt(24)) {
            case 'B':
               ByteId boid = (ByteId)oid;
               return new ByteIdentity(boid.getType(), boid.getId());
            case 'C':
               CharId coid = (CharId)oid;
               return new CharIdentity(coid.getType(), coid.getId());
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'J':
            case 'K':
            case 'M':
            case 'N':
            case 'P':
            case 'Q':
            case 'R':
            default:
               throw new FatalInternalException();
            case 'I':
               IntId ioid = (IntId)oid;
               return new IntIdentity(ioid.getType(), ioid.getId());
            case 'L':
               LongId loid = (LongId)oid;
               return new LongIdentity(loid.getType(), loid.getId());
            case 'O':
               ObjectId ooid = (ObjectId)oid;
               return new ObjectIdentity(ooid.getType(), ooid.getId());
            case 'S':
               if (oid instanceof ShortId) {
                  ShortId soid = (ShortId)oid;
                  return new ShortIdentity(soid.getType(), soid.getId());
               } else {
                  StringId stoid = (StringId)oid;
                  return new StringIdentity(stoid.getType(), stoid.getId());
               }
         }
      } else {
         return oid;
      }
   }

   public static Object toKodoObjectId(Object oid, PersistenceManager pm) {
      if (!(oid instanceof SingleFieldIdentity)) {
         return oid;
      } else {
         switch (oid.getClass().getName().charAt(19)) {
            case 'B':
               ByteIdentity boid = (ByteIdentity)oid;
               return new ByteId(getTargetClass(boid, pm), boid.getKey());
            case 'C':
               CharIdentity coid = (CharIdentity)oid;
               return new CharId(getTargetClass(coid, pm), coid.getKey());
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'J':
            case 'K':
            case 'M':
            case 'N':
            case 'P':
            case 'Q':
            case 'R':
            default:
               throw new FatalInternalException();
            case 'I':
               IntIdentity ioid = (IntIdentity)oid;
               return new IntId(getTargetClass(ioid, pm), ioid.getKey());
            case 'L':
               LongIdentity loid = (LongIdentity)oid;
               return new LongId(getTargetClass(loid, pm), loid.getKey());
            case 'O':
               ObjectIdentity ooid = (ObjectIdentity)oid;
               return new ObjectId(getTargetClass(ooid, pm), ooid.getKey());
            case 'S':
               if (oid instanceof ShortIdentity) {
                  ShortIdentity soid = (ShortIdentity)oid;
                  return new ShortId(getTargetClass(soid, pm), soid.getKey());
               } else {
                  StringIdentity stoid = (StringIdentity)oid;
                  return new StringId(getTargetClass(stoid, pm), stoid.getKey());
               }
         }
      }
   }

   public static Object[] toKodoObjectIds(Object[] oids, PersistenceManager pm) {
      if (oids != null && oids.length != 0) {
         Object[] copy = null;

         for(int i = 0; i < oids.length; ++i) {
            Object oid = toKodoObjectId(oids[i], pm);
            if (oid != oids[i]) {
               if (copy == null) {
                  copy = new Object[oids.length];
                  System.arraycopy(oids, 0, copy, 0, oids.length);
               }

               copy[i] = oid;
            }
         }

         return copy == null ? oids : copy;
      } else {
         return oids;
      }
   }

   public static Collection toKodoObjectIds(Collection oids, PersistenceManager pm) {
      if (oids != null && !oids.isEmpty()) {
         Collection copy = new ArrayList(oids.size());
         Iterator itr = oids.iterator();

         while(itr.hasNext()) {
            copy.add(toKodoObjectId(itr.next(), pm));
         }

         return copy;
      } else {
         return oids;
      }
   }

   private static Class getTargetClass(SingleFieldIdentity oid, PersistenceManager pm) {
      if (oid.getTargetClass() != null) {
         return oid.getTargetClass();
      } else {
         ClassLoader loader = null;
         if (pm != null) {
            KodoPersistenceManager kpm = (KodoPersistenceManager)pm;
            loader = kpm.getConfiguration().getClassResolverInstance().getClassLoader(oid.getClass(), kpm.getClassLoader());
         } else {
            loader = Thread.currentThread().getContextClassLoader();
         }

         try {
            return Class.forName(oid.getTargetClassName(), true, loader);
         } catch (Throwable var4) {
            throw new UserException(KodoJDOHelper.Loc._loc.get("bad-id-cls-name", oid.getTargetClassName(), oid), (Throwable[])null, (Object)null);
         }
      }
   }

   public static Class fromKodoObjectIdClass(Class cls) {
      if (cls == null) {
         return null;
      } else if (!OpenJPAId.class.isAssignableFrom(cls) && cls != Id.class) {
         return cls;
      } else {
         switch (cls.getName().charAt(24)) {
            case 'B':
               return ByteIdentity.class;
            case 'C':
               return CharIdentity.class;
            case 'I':
               return IntIdentity.class;
            case 'L':
               return LongIdentity.class;
            case 'S':
               return cls == ShortId.class ? ShortIdentity.class : StringIdentity.class;
            default:
               throw new FatalInternalException();
         }
      }
   }

   private static class Loc {
      private static final Localizer _loc = Localizer.forPackage(KodoJDOHelper.class);
   }
}
