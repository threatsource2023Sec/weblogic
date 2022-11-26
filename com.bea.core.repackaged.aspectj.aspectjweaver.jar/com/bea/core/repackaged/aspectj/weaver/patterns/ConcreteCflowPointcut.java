package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.MemberImpl;
import com.bea.core.repackaged.aspectj.weaver.NameMangler;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Expr;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConcreteCflowPointcut extends Pointcut {
   private final Member cflowField;
   List slots;
   boolean usesCounter;
   ResolvedType aspect;
   private static final Member cflowStackIsValidMethod;
   private static final Member cflowCounterIsValidMethod;

   public ConcreteCflowPointcut(ResolvedType aspect, Member cflowField, List slots, boolean usesCounter) {
      this.aspect = aspect;
      this.cflowField = cflowField;
      this.slots = slots;
      this.usesCounter = usesCounter;
      this.pointcutKind = 10;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      if (this.slots != null) {
         Iterator i$ = this.slots.iterator();

         while(i$.hasNext()) {
            Slot slot = (Slot)i$.next();
            ResolvedType rt = slot.formalType;
            if (rt.isMissing()) {
               ISourceLocation[] locs = new ISourceLocation[]{this.getSourceLocation()};
               Message m = new Message(WeaverMessages.format("missingTypePreventsMatch", rt.getName()), "", Message.WARNING, shadow.getSourceLocation(), (Throwable)null, locs);
               rt.getWorld().getMessageHandler().handleMessage(m);
               return FuzzyBoolean.NO;
            }
         }
      }

      return FuzzyBoolean.MAYBE;
   }

   public int[] getUsedFormalSlots() {
      if (this.slots == null) {
         return new int[0];
      } else {
         int[] indices = new int[this.slots.size()];

         for(int i = 0; i < indices.length; ++i) {
            indices[i] = ((Slot)this.slots.get(i)).formalIndex;
         }

         return indices;
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      throw new RuntimeException("unimplemented");
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      throw new RuntimeException("unimplemented");
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      throw new RuntimeException("unimplemented");
   }

   public boolean equals(Object other) {
      if (!(other instanceof ConcreteCflowPointcut)) {
         return false;
      } else {
         ConcreteCflowPointcut o = (ConcreteCflowPointcut)other;
         return o.cflowField.equals(this.cflowField);
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.cflowField.hashCode();
      return result;
   }

   public String toString() {
      return "concretecflow(" + this.cflowField + ")";
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (this.usesCounter) {
         return Test.makeFieldGetCall(this.cflowField, cflowCounterIsValidMethod, Expr.NONE);
      } else {
         if (this.slots != null) {
            Iterator i$ = this.slots.iterator();

            while(i$.hasNext()) {
               Slot slot = (Slot)i$.next();
               state.set(slot.formalIndex, this.aspect.getWorld().getWeavingSupport().makeCflowAccessVar(slot.formalType, this.cflowField, slot.arrayIndex));
            }
         }

         return Test.makeFieldGetCall(this.cflowField, cflowStackIsValidMethod, Expr.NONE);
      }
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      throw new RuntimeException("unimplemented");
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   static {
      cflowStackIsValidMethod = MemberImpl.method(NameMangler.CFLOW_STACK_UNRESOLVEDTYPE, 0, UnresolvedType.BOOLEAN, "isValid", UnresolvedType.NONE);
      cflowCounterIsValidMethod = MemberImpl.method(NameMangler.CFLOW_COUNTER_UNRESOLVEDTYPE, 0, UnresolvedType.BOOLEAN, "isValid", UnresolvedType.NONE);
   }

   public static class Slot {
      int formalIndex;
      ResolvedType formalType;
      int arrayIndex;

      public Slot(int formalIndex, ResolvedType formalType, int arrayIndex) {
         this.formalIndex = formalIndex;
         this.formalType = formalType;
         this.arrayIndex = arrayIndex;
      }

      public boolean equals(Object other) {
         if (!(other instanceof Slot)) {
            return false;
         } else {
            Slot o = (Slot)other;
            return o.formalIndex == this.formalIndex && o.arrayIndex == this.arrayIndex && o.formalType.equals(this.formalType);
         }
      }

      public int hashCode() {
         int result = 19;
         result = 37 * result + this.formalIndex;
         result = 37 * result + this.arrayIndex;
         result = 37 * result + this.formalType.hashCode();
         return result;
      }

      public String toString() {
         return "Slot(" + this.formalIndex + ", " + this.formalType + ", " + this.arrayIndex + ")";
      }
   }
}
