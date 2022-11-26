package com.oracle.pitchfork.spi;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.oracle.pitchfork.interfaces.TargetWrapper;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TargetWrapperImpl implements Serializable, TargetWrapper, TargetSource {
   private static final long serialVersionUID = 2338309290453139983L;
   private Object beanTarget;
   private Map interceptionInstances;

   public TargetWrapperImpl(Object beanTarget) {
      this.beanTarget = beanTarget;
   }

   public Object getBeanTarget() {
      return this.beanTarget;
   }

   public Class getTargetClass() {
      return this.beanTarget.getClass();
   }

   public boolean isStatic() {
      return false;
   }

   public Object getTarget() {
      return this.getBeanTarget();
   }

   public void releaseTarget(Object object) {
   }

   public void resetTarget(Object object) {
      this.beanTarget = object;
   }

   public void removeTarget() {
      this.beanTarget = null;
   }

   public Map getInterceptionInstances() {
      if (this.interceptionInstances == null) {
         this.interceptionInstances = new HashMap();
      }

      return this.interceptionInstances;
   }

   public void setInterceptionInstances(Map interceptors) {
      this.interceptionInstances = interceptors;
   }
}
