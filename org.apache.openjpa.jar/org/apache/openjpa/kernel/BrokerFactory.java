package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.Properties;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.util.Closeable;

public interface BrokerFactory extends Serializable, Closeable {
   OpenJPAConfiguration getConfiguration();

   Properties getProperties();

   Object putUserObject(Object var1, Object var2);

   Object getUserObject(Object var1);

   Broker newBroker();

   Broker newBroker(String var1, String var2, boolean var3, int var4, boolean var5);

   void addLifecycleListener(Object var1, Class[] var2);

   void removeLifecycleListener(Object var1);

   void addTransactionListener(Object var1);

   void removeTransactionListener(Object var1);

   void close();

   boolean isClosed();

   void lock();

   void unlock();
}
