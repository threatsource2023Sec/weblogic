package org.glassfish.hk2.configuration.hub.api;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface BeanDatabaseUpdateListener {
   void prepareDatabaseChange(BeanDatabase var1, BeanDatabase var2, Object var3, List var4);

   void commitDatabaseChange(BeanDatabase var1, BeanDatabase var2, Object var3, List var4);

   void rollbackDatabaseChange(BeanDatabase var1, BeanDatabase var2, Object var3, List var4);
}
