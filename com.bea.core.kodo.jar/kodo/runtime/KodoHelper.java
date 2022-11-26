package kodo.runtime;

import java.io.File;
import java.util.Properties;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.naming.Context;
import kodo.jdo.JDOExceptions;
import kodo.jdo.KodoJDOHelper;
import kodo.jdo.PersistenceManagerFactoryImpl;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.meta.ClassMetaData;

/** @deprecated */
public class KodoHelper {
   public static KodoPersistenceManagerFactory getPersistenceManagerFactory() {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory());
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory(Properties props) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory(props));
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory(Properties props, ClassLoader loader) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory(props, loader));
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory(String propsResource) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory(propsResource));
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory(String propsResource, ClassLoader loader) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory(propsResource, loader));
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory(File propsFile) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory(propsFile));
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory(File propsFile, ClassLoader loader) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory(propsFile, loader));
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory(String jndiLocation, Context context) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory(jndiLocation, context));
   }

   public static KodoPersistenceManagerFactory getPersistenceManagerFactory(String jndiLocation, Context context, ClassLoader loader) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)KodoJDOHelper.getPersistenceManagerFactory(jndiLocation, context, loader));
   }

   public static ClassMetaData getMetaData(Object o) {
      return KodoJDOHelper.getMetaData(o);
   }

   public static ClassMetaData getMetaData(PersistenceManager pm, Class cls) {
      return KodoJDOHelper.getMetaData(pm, cls);
   }

   public static ClassMetaData getMetaData(PersistenceManagerFactory factory, Class cls) {
      return KodoJDOHelper.getMetaData(factory, cls);
   }

   public static Object getVersion(Object o) {
      return KodoJDOHelper.getVersion(o);
   }

   public static SequenceGenerator getSequenceGenerator(Object o) {
      return o != null && o instanceof PersistenceCapable ? getSequenceGenerator((Broker)((PersistenceCapable)o).pcGetGenericContext(), o.getClass()) : null;
   }

   public static SequenceGenerator getSequenceGenerator(PersistenceManager pm, Class cls) {
      return pm == null ? null : getSequenceGenerator(KodoJDOHelper.toBroker(pm), cls);
   }

   private static SequenceGenerator getSequenceGenerator(Broker broker, Class cls) {
      try {
         ClassMetaData meta = broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(cls, broker.getClassLoader(), true);
         Seq seq = broker.getIdentitySequence(meta);
         return seq == null ? null : new SequenceGenerator(seq, broker, meta);
      } catch (Exception var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public static DataCache getDataCache(Object o) {
      ClassMetaData meta = getMetaData(o);
      return meta == null ? null : meta.getDataCache();
   }

   public static DataCache getDataCache(PersistenceManager pm, Class cls) {
      ClassMetaData meta = getMetaData(pm, cls);
      return meta == null ? null : meta.getDataCache();
   }

   public static DataCache getDataCache(PersistenceManagerFactory pmf, Class cls) {
      ClassMetaData meta = getMetaData(pmf, cls);
      return meta == null ? null : meta.getDataCache();
   }

   public static int getLockLevel(Object o) {
      return KodoJDOHelper.getLockLevel(o);
   }

   public static void close(Object o) {
      KodoJDOHelper.close(o);
   }

   public static KodoPersistenceManager getPersistenceManager(Object o) {
      return (KodoPersistenceManager)KodoJDOHelper.getPersistenceManager(o);
   }

   public static void makeDirty(Object o, String field) {
      KodoJDOHelper.makeDirty(o, field);
   }

   public static Object getObjectId(Object o) {
      return KodoJDOHelper.getObjectId(o);
   }

   public static Object getTransactionalObjectId(Object o) {
      return KodoJDOHelper.getTransactionalObjectId(o);
   }

   public static boolean isDirty(Object o) {
      return KodoJDOHelper.isDirty(o);
   }

   public static boolean isTransactional(Object o) {
      return KodoJDOHelper.isTransactional(o);
   }

   public static boolean isPersistent(Object o) {
      return KodoJDOHelper.isPersistent(o);
   }

   public static boolean isNew(Object o) {
      return KodoJDOHelper.isNew(o);
   }

   public static boolean isDeleted(Object o) {
      return KodoJDOHelper.isDeleted(o);
   }

   public static boolean isDetached(Object pc) {
      return KodoJDOHelper.isDetached(pc);
   }

   public static void setDetachedState(Object pc, Object state) {
      KodoJDOHelper.setDetachedState(pc, state);
   }

   public static Object getDetachedState(Object pc) {
      return KodoJDOHelper.getDetachedState(pc);
   }

   public static void setDetachedObjectId(Object pc, String id) {
      Object state = getDetachedState(pc);
      if (state instanceof Object[]) {
         ((Object[])((Object[])state))[0] = id;
      }

   }

   public static String getDetachedObjectId(Object pc) {
      Object state = getDetachedState(pc);
      return state instanceof Object[] ? ((Object[])((Object[])state))[0].toString() : null;
   }
}
