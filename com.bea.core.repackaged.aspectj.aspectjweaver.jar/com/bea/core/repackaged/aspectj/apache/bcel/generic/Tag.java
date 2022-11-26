package com.bea.core.repackaged.aspectj.apache.bcel.generic;

public abstract class Tag implements InstructionTargeter, Cloneable {
   public boolean containsTarget(InstructionHandle ih) {
      return false;
   }

   public void updateTarget(InstructionHandle oldHandle, InstructionHandle newHandle) {
      oldHandle.removeTargeter(this);
      if (newHandle != null) {
         newHandle.addTargeter(this);
      }

   }

   public Tag copy() {
      try {
         return (Tag)this.clone();
      } catch (CloneNotSupportedException var2) {
         throw new RuntimeException("Sanity check, can't clone me");
      }
   }
}
