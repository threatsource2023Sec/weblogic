package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleContext;
import com.oracle.weblogic.lifecycle.LifecycleOperationType;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import java.util.Map;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;

@Service
public class LifecycleContextImpl implements LifecycleContext {
   @Inject
   private RuntimeManagerImpl runtimeManager;
   private Map properties;
   private LifecycleOperationType lifecycleOperationType;
   private String runtimeType;
   private Object obj;

   public LifecycleContextImpl(Map properties) {
      this.properties = properties;
   }

   public LifecycleContextImpl(Map properties, String runtimeType, LifecycleOperationType lifecycleOperationType) {
      this.properties = properties;
      this.lifecycleOperationType = lifecycleOperationType;
      this.runtimeType = runtimeType;
   }

   public Map getProperties() {
      return this.properties;
   }

   public LifecycleOperationType getOperationType() {
      return this.lifecycleOperationType;
   }

   public LifecycleRuntime getRuntime() {
      return this.runtimeManager.getRuntime(this.runtimeType);
   }
}
