package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;

public final class EmptyTargetSource implements TargetSource, Serializable {
   private static final long serialVersionUID = 3680494563553489691L;
   public static final EmptyTargetSource INSTANCE = new EmptyTargetSource((Class)null, true);
   private final Class targetClass;
   private final boolean isStatic;

   public static EmptyTargetSource forClass(@Nullable Class targetClass) {
      return forClass(targetClass, true);
   }

   public static EmptyTargetSource forClass(@Nullable Class targetClass, boolean isStatic) {
      return targetClass == null && isStatic ? INSTANCE : new EmptyTargetSource(targetClass, isStatic);
   }

   private EmptyTargetSource(@Nullable Class targetClass, boolean isStatic) {
      this.targetClass = targetClass;
      this.isStatic = isStatic;
   }

   @Nullable
   public Class getTargetClass() {
      return this.targetClass;
   }

   public boolean isStatic() {
      return this.isStatic;
   }

   @Nullable
   public Object getTarget() {
      return null;
   }

   public void releaseTarget(Object target) {
   }

   private Object readResolve() {
      return this.targetClass == null && this.isStatic ? INSTANCE : this;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof EmptyTargetSource)) {
         return false;
      } else {
         EmptyTargetSource otherTs = (EmptyTargetSource)other;
         return ObjectUtils.nullSafeEquals(this.targetClass, otherTs.targetClass) && this.isStatic == otherTs.isStatic;
      }
   }

   public int hashCode() {
      return EmptyTargetSource.class.hashCode() * 13 + ObjectUtils.nullSafeHashCode((Object)this.targetClass);
   }

   public String toString() {
      return "EmptyTargetSource: " + (this.targetClass != null ? "target class [" + this.targetClass.getName() + "]" : "no target class") + ", " + (this.isStatic ? "static" : "dynamic");
   }
}
