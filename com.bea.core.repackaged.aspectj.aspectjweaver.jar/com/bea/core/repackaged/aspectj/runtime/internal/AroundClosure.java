package com.bea.core.repackaged.aspectj.runtime.internal;

import com.bea.core.repackaged.aspectj.lang.ProceedingJoinPoint;

public abstract class AroundClosure {
   protected Object[] state;
   protected int bitflags = 1048576;
   protected Object[] preInitializationState;

   public AroundClosure() {
   }

   public AroundClosure(Object[] state) {
      this.state = state;
   }

   public int getFlags() {
      return this.bitflags;
   }

   public Object[] getState() {
      return this.state;
   }

   public Object[] getPreInitializationState() {
      return this.preInitializationState;
   }

   public abstract Object run(Object[] var1) throws Throwable;

   public ProceedingJoinPoint linkClosureAndJoinPoint() {
      ProceedingJoinPoint jp = (ProceedingJoinPoint)this.state[this.state.length - 1];
      jp.set$AroundClosure(this);
      return jp;
   }

   public ProceedingJoinPoint linkClosureAndJoinPoint(int flags) {
      ProceedingJoinPoint jp = (ProceedingJoinPoint)this.state[this.state.length - 1];
      jp.set$AroundClosure(this);
      this.bitflags = flags;
      return jp;
   }
}
