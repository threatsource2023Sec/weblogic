package com.oracle.weblogic.lifecycle;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface LifecycleManager {
   Environment createEnvironment(String var1) throws LifecycleException;

   boolean deleteEnvironment(String var1);

   Environment getEnvironment(String var1);

   Environment getEnvironment(String var1, String var2);

   List getEnvironments();

   void syncEnvironment(String var1) throws LifecycleException;

   void syncEnvironments() throws LifecycleException;

   void configurePeriodicSync(int var1) throws LifecycleException;
}
