package org.apache.openjpa.persistence;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.BigDecimalId;
import org.apache.openjpa.util.BigIntegerId;
import org.apache.openjpa.util.ByteId;
import org.apache.openjpa.util.CharId;
import org.apache.openjpa.util.DoubleId;
import org.apache.openjpa.util.FloatId;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.IntId;
import org.apache.openjpa.util.LongId;
import org.apache.openjpa.util.ObjectId;
import org.apache.openjpa.util.OpenJPAId;
import org.apache.openjpa.util.ShortId;
import org.apache.openjpa.util.StringId;

public class JPAFacadeHelper {
   public static final String EM_KEY = "org.apache.openjpa.persistence.EntityManager";
   public static final String EMF_KEY = "org.apache.openjpa.persistence.EntityManagerFactory";
   private static final Localizer _loc = Localizer.forPackage(JPAFacadeHelper.class);

   public static OpenJPAEntityManagerFactory toEntityManagerFactory(BrokerFactory factory) {
      if (factory == null) {
         return null;
      } else {
         factory.lock();

         OpenJPAEntityManagerFactory var2;
         try {
            OpenJPAEntityManagerFactory emf = (OpenJPAEntityManagerFactory)factory.getUserObject("org.apache.openjpa.persistence.EntityManagerFactory");
            if (emf == null) {
               emf = EntityManagerFactoryValue.newFactory(factory);
               factory.putUserObject("org.apache.openjpa.persistence.EntityManagerFactory", emf);
            }

            var2 = emf;
         } catch (Exception var6) {
            throw PersistenceExceptions.toPersistenceException(var6);
         } finally {
            factory.unlock();
         }

         return var2;
      }
   }

   public static BrokerFactory toBrokerFactory(EntityManagerFactory emf) {
      if (emf == null) {
         return null;
      } else {
         if (!(emf instanceof EntityManagerFactoryImpl)) {
            Class c = emf.getClass();

            try {
               emf = (EntityManagerFactoryImpl)((OpenJPAEntityManagerFactory)emf).getUserObject("org.apache.openjpa.persistence.EntityManagerFactory");
            } catch (ClassCastException var3) {
               throw new ArgumentException(_loc.get("cant-convert-brokerfactory", (Object)c), (Throwable[])null, (Object)null, false);
            }
         }

         return ((EntityManagerFactoryImpl)emf).getBrokerFactory();
      }
   }

   public static OpenJPAEntityManager toEntityManager(Broker broker) {
      if (broker == null) {
         return null;
      } else {
         broker.lock();

         Object var8;
         try {
            OpenJPAEntityManager em = (OpenJPAEntityManager)broker.getUserObject("org.apache.openjpa.persistence.EntityManager");
            if (em == null) {
               EntityManagerFactoryImpl emf = (EntityManagerFactoryImpl)toEntityManagerFactory(broker.getBrokerFactory());
               em = emf.newEntityManagerImpl(broker);
               broker.putUserObject("org.apache.openjpa.persistence.EntityManager", em);
            }

            var8 = em;
         } catch (Exception var6) {
            throw PersistenceExceptions.toPersistenceException(var6);
         } finally {
            broker.unlock();
         }

         return (OpenJPAEntityManager)var8;
      }
   }

   public static Broker toBroker(EntityManager em) {
      if (em == null) {
         return null;
      } else {
         if (!(em instanceof EntityManagerImpl)) {
            Class c = em.getClass();

            try {
               em = (EntityManagerImpl)((OpenJPAEntityManager)em).getUserObject("org.apache.openjpa.persistence.EntityManager");
            } catch (ClassCastException var3) {
               throw new ArgumentException(_loc.get("cant-convert-broker", (Object)c), new Throwable[]{var3}, (Object)null, false);
            }
         }

         return ((EntityManagerImpl)em).getBroker();
      }
   }

   public static ClassMetaData getMetaData(Object o) {
      if (o == null) {
         return null;
      } else {
         EntityManager em = OpenJPAPersistence.getEntityManager(o);
         return em == null ? null : getMetaData((EntityManager)em, ImplHelper.getManagedInstance(o).getClass());
      }
   }

   public static ClassMetaData getMetaData(EntityManager em, Class cls) {
      if (em == null) {
         throw new NullPointerException("em == null");
      } else {
         OpenJPAEntityManagerSPI kem = (OpenJPAEntityManagerSPI)OpenJPAPersistence.cast(em);

         try {
            return kem.getConfiguration().getMetaDataRepositoryInstance().getMetaData(cls, kem.getClassLoader(), false);
         } catch (Exception var4) {
            throw PersistenceExceptions.toPersistenceException(var4);
         }
      }
   }

   public static ClassMetaData getMetaData(EntityManagerFactory emf, Class cls) {
      if (emf == null) {
         throw new NullPointerException("emf == null");
      } else {
         OpenJPAEntityManagerFactorySPI emfSPI = (OpenJPAEntityManagerFactorySPI)OpenJPAPersistence.cast(emf);

         try {
            return emfSPI.getConfiguration().getMetaDataRepositoryInstance().getMetaData((Class)cls, (ClassLoader)null, false);
         } catch (Exception var4) {
            throw PersistenceExceptions.toPersistenceException(var4);
         }
      }
   }

   public static Object fromOpenJPAObjectId(Object oid) {
      return oid instanceof OpenJPAId ? ((OpenJPAId)oid).getIdObject() : oid;
   }

   public static Object toOpenJPAObjectId(ClassMetaData meta, Object oid) {
      if (oid != null && meta != null) {
         Class cls = meta.getDescribedType();
         if (meta.getIdentityType() == 1) {
            return new Id(cls, ((Number)oid).longValue());
         } else if (oid instanceof Byte) {
            return new ByteId(cls, (Byte)oid);
         } else if (oid instanceof Character) {
            return new CharId(cls, (Character)oid);
         } else if (oid instanceof Double) {
            return new DoubleId(cls, (Double)oid);
         } else if (oid instanceof Float) {
            return new FloatId(cls, (Float)oid);
         } else if (oid instanceof Integer) {
            return new IntId(cls, (Integer)oid);
         } else if (oid instanceof Long) {
            return new LongId(cls, (Long)oid);
         } else if (oid instanceof Short) {
            return new ShortId(cls, (Short)oid);
         } else if (oid instanceof String) {
            return new StringId(cls, (String)oid);
         } else if (oid instanceof BigDecimal) {
            return new BigDecimalId(cls, (BigDecimal)oid);
         } else {
            return oid instanceof BigInteger ? new BigIntegerId(cls, (BigInteger)oid) : new ObjectId(cls, oid);
         }
      } else {
         return null;
      }
   }

   public static Object[] toOpenJPAObjectIds(ClassMetaData meta, Object... oids) {
      if (oids != null && oids.length != 0) {
         Object oid = toOpenJPAObjectId(meta, oids[0]);
         if (oid == oids[0]) {
            return oids;
         } else {
            Object[] copy = new Object[oids.length];
            copy[0] = oid;

            for(int i = 1; i < oids.length; ++i) {
               copy[i] = toOpenJPAObjectId(meta, oids[i]);
            }

            return copy;
         }
      } else {
         return oids;
      }
   }

   public static Collection toOpenJPAObjectIds(ClassMetaData meta, Collection oids) {
      if (oids != null && !oids.isEmpty()) {
         Iterator itr = oids.iterator();
         Object orig = itr.next();
         Object oid = toOpenJPAObjectId(meta, orig);
         if (oid == orig) {
            return oids;
         } else {
            Collection copy = new ArrayList(oids.size());
            copy.add(oid);

            while(itr.hasNext()) {
               copy.add(toOpenJPAObjectId(meta, itr.next()));
            }

            return copy;
         }
      } else {
         return oids;
      }
   }

   public static Class fromOpenJPAObjectIdClass(Class oidClass) {
      if (oidClass == null) {
         return null;
      } else if (oidClass == Id.class) {
         return Long.class;
      } else if (oidClass == ByteId.class) {
         return Byte.class;
      } else if (oidClass == CharId.class) {
         return Character.class;
      } else if (oidClass == DoubleId.class) {
         return Double.class;
      } else if (oidClass == FloatId.class) {
         return Float.class;
      } else if (oidClass == IntId.class) {
         return Integer.class;
      } else if (oidClass == LongId.class) {
         return Long.class;
      } else if (oidClass == ShortId.class) {
         return Short.class;
      } else if (oidClass == StringId.class) {
         return String.class;
      } else if (oidClass == BigDecimalId.class) {
         return BigDecimal.class;
      } else {
         return oidClass == BigIntegerId.class ? BigInteger.class : oidClass;
      }
   }
}
