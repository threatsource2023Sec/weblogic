package org.apache.openjpa.persistence;

import java.util.Collection;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.rmi.PortableRemoteObject;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.ImplHelper;

public class OpenJPAPersistence {
   private static final Localizer _loc = Localizer.forPackage(OpenJPAPersistence.class);
   /** @deprecated */
   public static final String EM_KEY = "org.apache.openjpa.persistence.EntityManager";
   /** @deprecated */
   public static final String EMF_KEY = "org.apache.openjpa.persistence.EntityManagerFactory";

   public static OpenJPAEntityManagerFactory cast(EntityManagerFactory emf) {
      return (OpenJPAEntityManagerFactory)emf;
   }

   public static OpenJPAEntityManager cast(EntityManager em) {
      return em instanceof OpenJPAEntityManager ? (OpenJPAEntityManager)em : (OpenJPAEntityManager)em.getDelegate();
   }

   public static OpenJPAQuery cast(Query q) {
      return (OpenJPAQuery)q;
   }

   public static OpenJPAEntityManagerFactory getEntityManagerFactory() {
      return getEntityManagerFactory((Map)null);
   }

   public static OpenJPAEntityManagerFactory getEntityManagerFactory(Map map) {
      ConfigurationProvider cp = new PersistenceProductDerivation.ConfigurationProviderImpl(map);

      try {
         return JPAFacadeHelper.toEntityManagerFactory(Bootstrap.getBrokerFactory(cp, (ClassLoader)null));
      } catch (Exception var3) {
         throw PersistenceExceptions.toPersistenceException(var3);
      }
   }

   public static OpenJPAEntityManagerFactory createEntityManagerFactory(String name, String resource) {
      return createEntityManagerFactory(name, resource, (Map)null);
   }

   public static OpenJPAEntityManagerFactory createEntityManagerFactory(String name, String resource, Map map) {
      return (new PersistenceProviderImpl()).createEntityManagerFactory(name, resource, map);
   }

   public static OpenJPAEntityManagerFactory createEntityManagerFactory(String jndiLocation, Context context) {
      if (jndiLocation == null) {
         throw new NullPointerException("jndiLocation == null");
      } else {
         try {
            if (context == null) {
               context = new InitialContext();
            }

            Object o = ((Context)context).lookup(jndiLocation);
            return (OpenJPAEntityManagerFactory)PortableRemoteObject.narrow(o, OpenJPAEntityManagerFactory.class);
         } catch (NamingException var3) {
            throw new ArgumentException(_loc.get("naming-exception", (Object)jndiLocation), new Throwable[]{var3}, (Object)null, true);
         }
      }
   }

   public static OpenJPAEntityManager getEntityManager(Object o) {
      try {
         if (ImplHelper.isManageable(o)) {
            PersistenceCapable pc = ImplHelper.toPersistenceCapable(o, (Object)null);
            if (pc != null) {
               return JPAFacadeHelper.toEntityManager((Broker)pc.pcGetGenericContext());
            }
         }

         return null;
      } catch (Exception var2) {
         throw PersistenceExceptions.toPersistenceException(var2);
      }
   }

   public static void close(Object o) {
      try {
         ImplHelper.close(o);
      } catch (Exception var2) {
         throw PersistenceExceptions.toPersistenceException(var2);
      }
   }

   public static boolean isManagedType(EntityManager em, Class cls) {
      try {
         return ImplHelper.isManagedType(JPAFacadeHelper.toBroker(em).getConfiguration(), cls);
      } catch (Exception var3) {
         throw PersistenceExceptions.toPersistenceException(var3);
      }
   }

   /** @deprecated */
   public static OpenJPAEntityManagerFactory toEntityManagerFactory(BrokerFactory factory) {
      return JPAFacadeHelper.toEntityManagerFactory(factory);
   }

   /** @deprecated */
   public static BrokerFactory toBrokerFactory(EntityManagerFactory factory) {
      return JPAFacadeHelper.toBrokerFactory(factory);
   }

   /** @deprecated */
   public static OpenJPAEntityManager toEntityManager(Broker broker) {
      return JPAFacadeHelper.toEntityManager(broker);
   }

   /** @deprecated */
   public static Broker toBroker(EntityManager em) {
      return JPAFacadeHelper.toBroker(em);
   }

   /** @deprecated */
   public static ClassMetaData getMetaData(Object o) {
      return JPAFacadeHelper.getMetaData(o);
   }

   /** @deprecated */
   public static ClassMetaData getMetaData(EntityManager em, Class cls) {
      return JPAFacadeHelper.getMetaData(em, cls);
   }

   /** @deprecated */
   public static ClassMetaData getMetaData(EntityManagerFactory factory, Class cls) {
      return JPAFacadeHelper.getMetaData(factory, cls);
   }

   /** @deprecated */
   public static Object fromOpenJPAObjectId(Object oid) {
      return JPAFacadeHelper.fromOpenJPAObjectId(oid);
   }

   /** @deprecated */
   public static Object toOpenJPAObjectId(ClassMetaData meta, Object oid) {
      return JPAFacadeHelper.toOpenJPAObjectId(meta, oid);
   }

   /** @deprecated */
   public static Object[] toOpenJPAObjectIds(ClassMetaData meta, Object... oids) {
      return JPAFacadeHelper.toOpenJPAObjectIds(meta, oids);
   }

   /** @deprecated */
   public static Collection toOpenJPAObjectIds(ClassMetaData meta, Collection oids) {
      return JPAFacadeHelper.toOpenJPAObjectIds(meta, oids);
   }

   /** @deprecated */
   public static Class fromOpenJPAObjectIdClass(Class oidClass) {
      return JPAFacadeHelper.fromOpenJPAObjectIdClass(oidClass);
   }
}
