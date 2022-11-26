package kodo.persistence;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.PersistenceExceptions;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.UserException;

/** @deprecated */
public class KodoPersistence extends Persistence {
   private static final Localizer _loc = Localizer.forPackage(KodoPersistence.class);

   public static KodoEntityManagerFactory toEntityManagerFactory(BrokerFactory factory) {
      return cast((EntityManagerFactory)JPAFacadeHelper.toEntityManagerFactory(factory));
   }

   public static KodoEntityManager toEntityManager(Broker broker) {
      return cast((EntityManager)JPAFacadeHelper.toEntityManager(broker));
   }

   public static BrokerFactory toBrokerFactory(EntityManagerFactory emf) {
      return JPAFacadeHelper.toBrokerFactory(emf);
   }

   public static Broker toBroker(EntityManager em) {
      return JPAFacadeHelper.toBroker(em);
   }

   public static KodoEntityManagerFactory cast(EntityManagerFactory emf) {
      if (emf instanceof KodoEntityManagerFactory) {
         return (KodoEntityManagerFactory)emf;
      } else {
         throw new UserException(_loc.get("emf-cast", emf.getClass()));
      }
   }

   public static KodoEntityManager cast(EntityManager em) {
      if (em instanceof KodoEntityManager) {
         return (KodoEntityManager)em;
      } else {
         throw new UserException(_loc.get("em-cast", em.getClass()));
      }
   }

   public static KodoQuery cast(Query q) {
      if (q instanceof KodoQuery) {
         return (KodoQuery)q;
      } else {
         throw new UserException(_loc.get("query-cast", q.getClass()));
      }
   }

   public static KodoEntityManagerFactory getEntityManagerFactory() {
      return getEntityManagerFactory((Map)null);
   }

   public static KodoEntityManagerFactory getEntityManagerFactory(Map map) {
      return cast((EntityManagerFactory)OpenJPAPersistence.getEntityManagerFactory(ensureDeprecatedProvider(map)));
   }

   public static KodoEntityManagerFactory createEntityManagerFactory() {
      return createEntityManagerFactory((String)null, (String)null, (Map)null);
   }

   public static KodoEntityManagerFactory createEntityManagerFactory(String unit, Map properties) {
      return createEntityManagerFactory(unit, (String)null, properties);
   }

   public static KodoEntityManagerFactory createEntityManagerFactory(String unit) {
      return createEntityManagerFactory(unit, (String)null, (Map)null);
   }

   public static KodoEntityManagerFactory createEntityManagerFactory(String name, String resource) {
      return createEntityManagerFactory(name, resource, (Map)null);
   }

   public static KodoEntityManagerFactory createEntityManagerFactory(String name, String resource, Map map) {
      return (KodoEntityManagerFactory)OpenJPAPersistence.createEntityManagerFactory(name, resource, ensureDeprecatedProvider(map));
   }

   public static KodoEntityManagerFactory createEntityManagerFactory(String jndiLocation, Context context) {
      return (KodoEntityManagerFactory)OpenJPAPersistence.createEntityManagerFactory(jndiLocation, ensureDeprecatedProvider(context));
   }

   public static KodoEntityManager getEntityManager(Object o) {
      return cast((EntityManager)OpenJPAPersistence.getEntityManager(o));
   }

   public static ClassMetaData getMetaData(Object o) {
      return JPAFacadeHelper.getMetaData(o);
   }

   public static ClassMetaData getMetaData(EntityManager em, Class cls) {
      return JPAFacadeHelper.getMetaData(em, cls);
   }

   public static ClassMetaData getMetaData(EntityManagerFactory emf, Class cls) {
      return JPAFacadeHelper.getMetaData(emf, cls);
   }

   public static void close(Object o) {
      OpenJPAPersistence.close(o);
   }

   public static boolean isManagedType(EntityManager em, Class cls) {
      return OpenJPAPersistence.isManagedType(em, cls);
   }

   public static boolean isManagedType(EntityManagerFactory emf, Class cls) {
      try {
         return ImplHelper.isManagedType(JPAFacadeHelper.toBrokerFactory(emf).getConfiguration(), cls);
      } catch (Exception var3) {
         throw PersistenceExceptions.toPersistenceException(var3);
      }
   }

   public static Object fromKodoObjectId(Object oid) {
      return JPAFacadeHelper.fromOpenJPAObjectId(oid);
   }

   public static Object toKodoObjectId(ClassMetaData meta, Object oid) {
      return JPAFacadeHelper.toOpenJPAObjectId(meta, oid);
   }

   public static Object[] toKodoObjectIds(ClassMetaData meta, Object... oids) {
      return JPAFacadeHelper.toOpenJPAObjectIds(meta, oids);
   }

   public static Collection toKodoObjectIds(ClassMetaData meta, Collection oids) {
      return JPAFacadeHelper.toOpenJPAObjectIds(meta, oids);
   }

   public static Class fromKodoObjectIdClass(Class oidClass) {
      return JPAFacadeHelper.fromOpenJPAObjectIdClass(oidClass);
   }

   private static Map ensureDeprecatedProvider(Map map) {
      String key = "javax.persistence.provider";
      String provider = PersistenceProviderImpl.class.getName();
      if (map == null) {
         map = new Properties();
      }

      Object existing = ((Map)map).get(key);
      if (existing == null) {
         ((Map)map).put(key, provider);
      } else if (!provider.equals(existing.toString())) {
         throw new UserException(_loc.get("wrong-provider", key, existing, provider));
      }

      return (Map)map;
   }

   private static Context ensureDeprecatedProvider(Context context) {
      String key = "javax.persistence.provider";
      String provider = PersistenceProviderImpl.class.getName();

      try {
         if (context == null) {
            context = new InitialContext();
         }

         Object existing = ((Context)context).lookup(key);
         if (existing == null) {
            ((Context)context).bind(key, provider);
         } else if (!provider.equals(existing.toString())) {
            throw new UserException(_loc.get("wrong-provider", key, existing, provider));
         }

         return (Context)context;
      } catch (NamingException var4) {
         throw new UserException(_loc.get("bind-error", key, provider), var4);
      }
   }
}
