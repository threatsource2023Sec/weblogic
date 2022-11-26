package org.apache.openjpa.kernel;

import java.util.Properties;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingBrokerFactory implements BrokerFactory {
   private final BrokerFactory _factory;
   private final DelegatingBrokerFactory _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingBrokerFactory(BrokerFactory factory) {
      this(factory, (RuntimeExceptionTranslator)null);
   }

   public DelegatingBrokerFactory(BrokerFactory factory, RuntimeExceptionTranslator trans) {
      this._factory = factory;
      if (factory instanceof DelegatingBrokerFactory) {
         this._del = (DelegatingBrokerFactory)factory;
      } else {
         this._del = null;
      }

      this._trans = trans;
   }

   public BrokerFactory getDelegate() {
      return this._factory;
   }

   public BrokerFactory getInnermostDelegate() {
      return this._del == null ? this._factory : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingBrokerFactory) {
            other = ((DelegatingBrokerFactory)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public OpenJPAConfiguration getConfiguration() {
      try {
         return this._factory.getConfiguration();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Properties getProperties() {
      try {
         return this._factory.getProperties();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object putUserObject(Object key, Object val) {
      try {
         return this._factory.putUserObject(key, val);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object getUserObject(Object key) {
      try {
         return this._factory.getUserObject(key);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Broker newBroker() {
      try {
         return this._factory.newBroker();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Broker newBroker(String user, String pass, boolean managed, int connRetainMode, boolean findExisting) {
      try {
         return this._factory.newBroker(user, pass, managed, connRetainMode, findExisting);
      } catch (RuntimeException var7) {
         throw this.translate(var7);
      }
   }

   public void addLifecycleListener(Object listener, Class[] classes) {
      try {
         this._factory.addLifecycleListener(listener, classes);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void removeLifecycleListener(Object listener) {
      try {
         this._factory.removeLifecycleListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void addTransactionListener(Object listener) {
      try {
         this._factory.addTransactionListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void removeTransactionListener(Object listener) {
      try {
         this._factory.removeTransactionListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void close() {
      try {
         this._factory.close();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isClosed() {
      try {
         return this._factory.isClosed();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void lock() {
      try {
         this._factory.lock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void unlock() {
      try {
         this._factory.unlock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }
}
