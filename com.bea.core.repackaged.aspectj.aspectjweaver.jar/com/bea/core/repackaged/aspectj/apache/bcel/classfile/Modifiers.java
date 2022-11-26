package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

public abstract class Modifiers {
   protected int modifiers;

   public Modifiers() {
   }

   public Modifiers(int a) {
      this.modifiers = a;
   }

   public final int getModifiers() {
      return this.modifiers;
   }

   public final void setModifiers(int modifiers) {
      this.modifiers = modifiers;
   }

   public final boolean isPublic() {
      return (this.modifiers & 1) != 0;
   }

   public final boolean isPrivate() {
      return (this.modifiers & 2) != 0;
   }

   public final boolean isProtected() {
      return (this.modifiers & 4) != 0;
   }

   public final boolean isStatic() {
      return (this.modifiers & 8) != 0;
   }

   public final boolean isFinal() {
      return (this.modifiers & 16) != 0;
   }

   public final boolean isSynchronized() {
      return (this.modifiers & 32) != 0;
   }

   public final boolean isVolatile() {
      return (this.modifiers & 64) != 0;
   }

   public final boolean isTransient() {
      return (this.modifiers & 128) != 0;
   }

   public final boolean isNative() {
      return (this.modifiers & 256) != 0;
   }

   public final boolean isInterface() {
      return (this.modifiers & 512) != 0;
   }

   public final boolean isAbstract() {
      return (this.modifiers & 1024) != 0;
   }

   public final boolean isStrictfp() {
      return (this.modifiers & 2048) != 0;
   }

   public final boolean isVarargs() {
      return (this.modifiers & 128) != 0;
   }

   public final boolean isBridge() {
      return (this.modifiers & 64) != 0;
   }
}
