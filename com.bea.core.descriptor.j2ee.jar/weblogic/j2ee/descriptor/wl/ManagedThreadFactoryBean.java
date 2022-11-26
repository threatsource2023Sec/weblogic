package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface ManagedThreadFactoryBean extends SettableBean {
   String getName();

   void setName(String var1);

   int getMaxConcurrentNewThreads();

   void setMaxConcurrentNewThreads(int var1);

   int getPriority();

   void setPriority(int var1);

   String getId();

   void setId(String var1);
}
