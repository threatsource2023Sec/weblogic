package com.oracle.weblogic.lifecycle.config.database;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface LifecyclePersistencePlugin {
   void persist(List var1) throws Exception;

   void load() throws Exception;
}
