package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public class Bindings {
   public static final Bindings NONE = new Bindings(0);
   private BindingPattern[] bindings;

   public Bindings(BindingPattern[] bindings) {
      this.bindings = bindings;
   }

   public Bindings(int count) {
      this(new BindingPattern[count]);
   }

   public void register(BindingPattern binding, IScope scope) {
      int index = binding.getFormalIndex();
      BindingPattern existingBinding = this.bindings[index];
      if (existingBinding != null) {
         scope.message(IMessage.ERROR, existingBinding, binding, "multiple bindings" + index + ", " + binding);
      }

      this.bindings[index] = binding;
   }

   public void mergeIn(Bindings other, IScope scope) {
      int i = 0;

      for(int len = other.bindings.length; i < len; ++i) {
         if (other.bindings[i] != null) {
            this.register(other.bindings[i], scope);
         }
      }

   }

   public void checkEquals(Bindings other, IScope scope) {
      BindingPattern[] b1 = this.bindings;
      BindingPattern[] b2 = other.bindings;
      int len = b1.length;
      if (len != b2.length) {
         throw new BCException("INSANE");
      } else {
         for(int i = 0; i < len; ++i) {
            if (b1[i] == null && b2[i] != null) {
               scope.message(IMessage.ERROR, b2[i], "inconsistent binding");
               b1[i] = b2[i];
            } else if (b2[i] == null && b1[i] != null) {
               scope.message(IMessage.ERROR, b1[i], "inconsistent binding");
               b2[i] = b1[i];
            }
         }

      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("Bindings(");
      int i = 0;

      for(int len = this.bindings.length; i < len; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(this.bindings[i]);
      }

      buf.append(")");
      return buf.toString();
   }

   public int[] getUsedFormals() {
      int[] ret = new int[this.bindings.length];
      int index = 0;
      int i = 0;

      for(int len = this.bindings.length; i < len; ++i) {
         if (this.bindings[i] != null) {
            ret[index++] = i;
         }
      }

      int[] newRet = new int[index];
      System.arraycopy(ret, 0, newRet, 0, index);
      return newRet;
   }

   public UnresolvedType[] getUsedFormalTypes() {
      UnresolvedType[] ret = new UnresolvedType[this.bindings.length];
      int index = 0;
      int i = 0;

      for(int len = this.bindings.length; i < len; ++i) {
         if (this.bindings[i] != null) {
            ret[index++] = ((BindingTypePattern)this.bindings[i]).getExactType();
         }
      }

      UnresolvedType[] newRet = new UnresolvedType[index];
      System.arraycopy(ret, 0, newRet, 0, index);
      return newRet;
   }

   public Bindings copy() {
      return new Bindings((BindingPattern[])((BindingPattern[])this.bindings.clone()));
   }

   public void checkAllBound(IScope scope) {
      int i = 0;

      for(int len = this.bindings.length; i < len; ++i) {
         if (this.bindings[i] == null) {
            if (scope.getFormal(i) instanceof FormalBinding.ImplicitFormalBinding) {
               this.bindings[i] = new BindingTypePattern(scope.getFormal(i), false);
            } else {
               scope.message(IMessage.ERROR, scope.getFormal(i), "formal unbound in pointcut ");
            }
         }
      }

   }

   public int size() {
      return this.bindings.length;
   }
}
