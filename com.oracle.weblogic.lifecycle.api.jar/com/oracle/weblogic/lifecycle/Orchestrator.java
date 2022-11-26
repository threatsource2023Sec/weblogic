package com.oracle.weblogic.lifecycle;

import javax.json.JsonObject;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Orchestrator {
   Environment orchestrate(String var1, JsonObject var2) throws LifecycleException;

   Environment orchestrate(String var1, String var2, JsonObject var3) throws LifecycleException;

   boolean deleteOrchestration(String var1, String var2) throws LifecycleException;

   boolean deleteOrchestration(String var1, JsonObject var2) throws LifecycleException;

   boolean deleteOrchestration(String var1, String var2, String var3) throws LifecycleException;

   boolean deleteOrchestration(String var1, String var2, String var3, String var4) throws LifecycleException;

   public interface Operation {
      String DELETE_ALL = "deleteAll";
   }
}
