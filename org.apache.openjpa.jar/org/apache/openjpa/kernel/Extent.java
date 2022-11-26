package org.apache.openjpa.kernel;

import java.util.Iterator;
import java.util.List;

public interface Extent {
   FetchConfiguration getFetchConfiguration();

   boolean getIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   List list();

   Iterator iterator();

   Broker getBroker();

   Class getElementType();

   boolean hasSubclasses();

   void closeAll();

   void lock();

   void unlock();
}
