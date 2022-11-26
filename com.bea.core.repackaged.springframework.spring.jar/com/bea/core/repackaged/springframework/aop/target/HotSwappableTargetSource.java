package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class HotSwappableTargetSource implements TargetSource, Serializable {
   private static final long serialVersionUID = 7497929212653839187L;
   private Object target;

   public HotSwappableTargetSource(Object initialTarget) {
      Assert.notNull(initialTarget, "Target object must not be null");
      this.target = initialTarget;
   }

   public synchronized Class getTargetClass() {
      return this.target.getClass();
   }

   public final boolean isStatic() {
      return false;
   }

   public synchronized Object getTarget() {
      return this.target;
   }

   public void releaseTarget(Object target) {
   }

   public synchronized Object swap(Object newTarget) throws IllegalArgumentException {
      Assert.notNull(newTarget, "Target object must not be null");
      Object old = this.target;
      this.target = newTarget;
      return old;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof HotSwappableTargetSource && this.target.equals(((HotSwappableTargetSource)other).target);
   }

   public int hashCode() {
      return HotSwappableTargetSource.class.hashCode();
   }

   public String toString() {
      return "HotSwappableTargetSource for target: " + this.target;
   }
}
