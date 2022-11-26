package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;

public class SingletonTargetSource implements TargetSource, Serializable {
   private static final long serialVersionUID = 9031246629662423738L;
   private final Object target;

   public SingletonTargetSource(Object target) {
      Assert.notNull(target, "Target object must not be null");
      this.target = target;
   }

   public Class getTargetClass() {
      return this.target.getClass();
   }

   public Object getTarget() {
      return this.target;
   }

   public void releaseTarget(Object target) {
   }

   public boolean isStatic() {
      return true;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof SingletonTargetSource)) {
         return false;
      } else {
         SingletonTargetSource otherTargetSource = (SingletonTargetSource)other;
         return this.target.equals(otherTargetSource.target);
      }
   }

   public int hashCode() {
      return this.target.hashCode();
   }

   public String toString() {
      return "SingletonTargetSource for target object [" + ObjectUtils.identityToString(this.target) + "]";
   }
}
