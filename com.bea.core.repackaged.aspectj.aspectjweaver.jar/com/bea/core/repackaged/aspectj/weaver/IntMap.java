package com.bea.core.repackaged.aspectj.weaver;

import java.util.ArrayList;
import java.util.List;

public class IntMap {
   private ResolvedType concreteAspect;
   private ShadowMunger enclosingAdvice;
   private List enclosingDefinition = new ArrayList();
   private static final int MISSING = -1;
   private int[] map;

   public void pushEnclosingDefinition(ResolvedPointcutDefinition def) {
      this.enclosingDefinition.add(def);
   }

   public void popEnclosingDefinitition() {
      this.enclosingDefinition.remove(this.enclosingDefinition.size() - 1);
   }

   public ResolvedPointcutDefinition peekEnclosingDefinition() {
      return this.enclosingDefinition.size() == 0 ? null : (ResolvedPointcutDefinition)this.enclosingDefinition.get(this.enclosingDefinition.size() - 1);
   }

   public boolean directlyInAdvice() {
      return this.enclosingDefinition.isEmpty();
   }

   public ShadowMunger getEnclosingAdvice() {
      return this.enclosingAdvice;
   }

   public void setEnclosingAdvice(ShadowMunger advice) {
      this.enclosingAdvice = advice;
   }

   public Member getAdviceSignature() {
      return this.enclosingAdvice instanceof Advice ? ((Advice)this.enclosingAdvice).getSignature() : null;
   }

   public ResolvedType getConcreteAspect() {
      return this.concreteAspect;
   }

   public void setConcreteAspect(ResolvedType concreteAspect) {
      this.concreteAspect = concreteAspect;
   }

   public void copyContext(IntMap bindings) {
      this.enclosingAdvice = bindings.enclosingAdvice;
      this.enclosingDefinition = bindings.enclosingDefinition;
      this.concreteAspect = bindings.concreteAspect;
   }

   private IntMap(int[] map) {
      this.map = map;
   }

   public IntMap() {
      this.map = new int[0];
   }

   public IntMap(int initialCapacity) {
      this.map = new int[initialCapacity];

      for(int i = 0; i < initialCapacity; ++i) {
         this.map[i] = -1;
      }

   }

   public void put(int key, int val) {
      if (key >= this.map.length) {
         int[] tmp = new int[key * 2 + 1];
         System.arraycopy(this.map, 0, tmp, 0, this.map.length);
         int i = this.map.length;

         for(int len = tmp.length; i < len; ++i) {
            tmp[i] = -1;
         }

         this.map = tmp;
      }

      this.map[key] = val;
   }

   public int get(int key) {
      return this.map[key];
   }

   public boolean hasKey(int key) {
      return key < this.map.length && this.map[key] != -1;
   }

   public static IntMap idMap(int size) {
      int[] map = new int[size];

      for(int i = 0; i < size; map[i] = i++) {
      }

      return new IntMap(map);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("[");
      boolean seenFirst = false;
      int i = 0;

      for(int len = this.map.length; i < len; ++i) {
         if (this.map[i] != -1) {
            if (seenFirst) {
               buf.append(", ");
            }

            seenFirst = true;
            buf.append(i);
            buf.append(" -> ");
            buf.append(this.map[i]);
         }
      }

      buf.append("]");
      return buf.toString();
   }
}
