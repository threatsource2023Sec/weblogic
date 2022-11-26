package org.apache.openjpa.persistence;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.kernel.DelegatingBrokerFactory;
import org.apache.openjpa.kernel.DelegatingFetchConfiguration;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.conf.Value;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.OpenJPAException;
import serp.util.Strings;

public class EntityManagerFactoryImpl implements OpenJPAEntityManagerFactory, OpenJPAEntityManagerFactorySPI, Closeable {
   private static final Localizer _loc = Localizer.forPackage(EntityManagerFactoryImpl.class);
   private DelegatingBrokerFactory _factory = null;
   private transient Constructor _plan = null;
   private transient StoreCache _cache = null;
   private transient QueryResultCache _queryCache = null;

   public EntityManagerFactoryImpl() {
   }

   public EntityManagerFactoryImpl(BrokerFactory factory) {
      this.setBrokerFactory(factory);
   }

   public BrokerFactory getBrokerFactory() {
      return this._factory.getDelegate();
   }

   public void setBrokerFactory(BrokerFactory factory) {
      this._factory = new DelegatingBrokerFactory(factory, PersistenceExceptions.TRANSLATOR);
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._factory.getConfiguration();
   }

   public Properties getPropertiesAsProperties() {
      return this._factory.getProperties();
   }

   public Map getProperties() {
      HashMap retv = new HashMap(this.getPropertiesAsProperties());
      return retv;
   }

   public Object putUserObject(Object key, Object val) {
      return this._factory.putUserObject(key, val);
   }

   public Object getUserObject(Object key) {
      return this._factory.getUserObject(key);
   }

   public StoreCache getStoreCache() {
      this._factory.lock();

      StoreCache var5;
      try {
         if (this._cache == null) {
            OpenJPAConfiguration conf = this._factory.getConfiguration();
            this._cache = new StoreCacheImpl(this, conf.getDataCacheManagerInstance().getSystemDataCache());
         }

         var5 = this._cache;
      } finally {
         this._factory.unlock();
      }

      return var5;
   }

   public StoreCache getStoreCache(String cacheName) {
      return new StoreCacheImpl(this, this._factory.getConfiguration().getDataCacheManagerInstance().getDataCache(cacheName, true));
   }

   public QueryResultCache getQueryResultCache() {
      this._factory.lock();

      QueryResultCache var1;
      try {
         if (this._queryCache == null) {
            this._queryCache = new QueryResultCacheImpl(this._factory.getConfiguration().getDataCacheManagerInstance().getSystemQueryCache());
         }

         var1 = this._queryCache;
      } finally {
         this._factory.unlock();
      }

      return var1;
   }

   public OpenJPAEntityManagerSPI createEntityManager() {
      return this.createEntityManager((Map)null);
   }

   public OpenJPAEntityManagerSPI createEntityManager(Map props) {
      if (props == null) {
         props = Collections.EMPTY_MAP;
      } else if (!((Map)props).isEmpty()) {
         props = new HashMap((Map)props);
      }

      OpenJPAConfiguration conf = this.getConfiguration();
      String user = (String)Configurations.removeProperty("ConnectionUserName", (Map)props);
      if (user == null) {
         user = conf.getConnectionUserName();
      }

      String pass = (String)Configurations.removeProperty("ConnectionPassword", (Map)props);
      if (pass == null) {
         pass = conf.getConnectionPassword();
      }

      String str = (String)Configurations.removeProperty("TransactionMode", (Map)props);
      boolean managed;
      if (str == null) {
         managed = conf.isTransactionModeManaged();
      } else {
         Value val = conf.getValue("TransactionMode");
         managed = Boolean.parseBoolean(val.unalias(str));
      }

      Object obj = Configurations.removeProperty("ConnectionRetainMode", (Map)props);
      int retainMode;
      if (obj instanceof Number) {
         retainMode = ((Number)obj).intValue();
      } else if (obj == null) {
         retainMode = conf.getConnectionRetainModeConstant();
      } else {
         Value val = conf.getValue("ConnectionRetainMode");

         try {
            retainMode = Integer.parseInt(val.unalias((String)obj));
         } catch (Exception var21) {
            throw new ArgumentException(_loc.get("bad-em-prop", "openjpa.ConnectionRetainMode", obj), new Throwable[]{var21}, obj, true);
         }
      }

      Broker broker = this._factory.newBroker(user, pass, managed, retainMode, false);
      broker.setAutoDetach(2, true);
      broker.setAutoDetach(16, true);
      broker.setDetachedNew(false);
      OpenJPAEntityManagerSPI em = this.newEntityManagerImpl(broker);
      String[] prefixes = ProductDerivations.getConfigurationPrefixes();
      List errs = null;
      Iterator var17 = ((Map)props).entrySet().iterator();

      while(true) {
         Method setter;
         String prop;
         Map.Entry entry;
         while(true) {
            String prefix;
            do {
               if (!var17.hasNext()) {
                  if (errs != null) {
                     em.close();
                     if (errs.size() == 1) {
                        throw (RuntimeException)errs.get(0);
                     }

                     throw new ArgumentException(_loc.get("bad-em-props"), (Throwable[])errs.toArray(new Throwable[errs.size()]), (Object)null, true);
                  }

                  return em;
               }

               entry = (Map.Entry)var17.next();
               prop = (String)entry.getKey();
               prefix = null;

               for(int i = 0; i < prefixes.length; ++i) {
                  prefix = prefixes[i] + ".";
                  if (prop.startsWith(prefix)) {
                     break;
                  }

                  prefix = null;
               }
            } while(prefix == null);

            prop = prop.substring(prefix.length());

            try {
               setter = Reflection.findSetter(em.getClass(), prop, true);
               break;
            } catch (OpenJPAException var22) {
               if (errs == null) {
                  errs = new LinkedList();
               }

               errs.add(PersistenceExceptions.toPersistenceException(var22));
            }
         }

         Object val = entry.getValue();

         try {
            if (val instanceof String) {
               if ("null".equals(val)) {
                  val = null;
               } else {
                  val = Strings.parse((String)val, setter.getParameterTypes()[0]);
               }
            }

            Reflection.set(em, (Method)setter, (Object)val);
         } catch (Throwable var23) {
            Throwable t;
            for(t = var23; t.getCause() != null; t = t.getCause()) {
            }

            ArgumentException err = new ArgumentException(_loc.get("bad-em-prop", prop, entry.getValue()), new Throwable[]{t}, (Object)null, true);
            if (errs == null) {
               errs = new LinkedList();
            }

            errs.add(err);
         }
      }
   }

   protected EntityManagerImpl newEntityManagerImpl(Broker broker) {
      return new EntityManagerImpl(this, broker);
   }

   public void addLifecycleListener(Object listener, Class... classes) {
      this._factory.addLifecycleListener(listener, classes);
   }

   public void removeLifecycleListener(Object listener) {
      this._factory.removeLifecycleListener(listener);
   }

   public void addTransactionListener(Object listener) {
      this._factory.addTransactionListener(listener);
   }

   public void removeTransactionListener(Object listener) {
      this._factory.removeTransactionListener(listener);
   }

   public void close() {
      this._factory.close();
   }

   public boolean isOpen() {
      return !this._factory.isClosed();
   }

   public int hashCode() {
      return this._factory.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof EntityManagerFactoryImpl) ? false : this._factory.equals(((EntityManagerFactoryImpl)other)._factory);
      }
   }

   FetchPlan toFetchPlan(Broker broker, FetchConfiguration fetch) {
      if (fetch == null) {
         return null;
      } else {
         if (fetch instanceof DelegatingFetchConfiguration) {
            fetch = ((DelegatingFetchConfiguration)fetch).getInnermostDelegate();
         }

         try {
            if (this._plan == null) {
               Class storeType = broker == null ? null : broker.getStoreManager().getInnermostDelegate().getClass();
               Class cls = this._factory.getConfiguration().getStoreFacadeTypeRegistry().getImplementation(FetchPlan.class, storeType, FetchPlanImpl.class);
               this._plan = cls.getConstructor(FetchConfiguration.class);
            }

            return (FetchPlan)this._plan.newInstance(fetch);
         } catch (InvocationTargetException var5) {
            throw PersistenceExceptions.toPersistenceException(var5.getTargetException());
         } catch (Exception var6) {
            throw PersistenceExceptions.toPersistenceException(var6);
         }
      }
   }
}
