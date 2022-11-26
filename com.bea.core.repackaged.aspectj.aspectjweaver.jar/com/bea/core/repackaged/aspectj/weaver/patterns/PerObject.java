package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.PerObjectInterfaceTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Expr;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import java.io.IOException;
import java.util.Map;

public class PerObject extends PerClause {
   private final boolean isThis;
   private final Pointcut entry;
   private static final int thisKindSet;
   private static final int targetKindSet;

   public PerObject(Pointcut entry, boolean isThis) {
      this.entry = entry;
      this.isThis = isThis;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public int couldMatchKinds() {
      return this.isThis ? thisKindSet : targetKindSet;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      return this.isThis ? FuzzyBoolean.fromBoolean(shadow.hasThis()) : FuzzyBoolean.fromBoolean(shadow.hasTarget());
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.entry.resolve(scope);
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      PerObject ret = new PerObject(this.entry.parameterizeWith(typeVariableMap, w), this.isThis);
      ret.copyLocationFrom(this);
      return ret;
   }

   private Var getVar(Shadow shadow) {
      return this.isThis ? shadow.getThisVar() : shadow.getTargetVar();
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      Expr myInstance = Expr.makeCallExpr(AjcMemberMaker.perObjectAspectOfMethod(this.inAspect), new Expr[]{this.getVar(shadow)}, this.inAspect);
      state.setAspectInstance(myInstance);
      return Test.makeCall(AjcMemberMaker.perObjectHasAspectMethod(this.inAspect), new Expr[]{this.getVar(shadow)});
   }

   public PerClause concretize(ResolvedType inAspect) {
      PerObject ret = new PerObject(this.entry, this.isThis);
      ret.inAspect = inAspect;
      if (inAspect.isAbstract()) {
         return ret;
      } else {
         World world = inAspect.getWorld();
         Pointcut concreteEntry = this.entry.concretize(inAspect, inAspect, 0, (ShadowMunger)null);
         inAspect.crosscuttingMembers.addConcreteShadowMunger(Advice.makePerObjectEntry(world, concreteEntry, this.isThis, inAspect));
         ResolvedTypeMunger munger = new PerObjectInterfaceTypeMunger(inAspect, concreteEntry);
         inAspect.crosscuttingMembers.addLateTypeMunger(world.getWeavingSupport().concreteTypeMunger(munger, inAspect));
         if (inAspect.isAnnotationStyleAspect() && !inAspect.isAbstract()) {
            inAspect.crosscuttingMembers.addLateTypeMunger(inAspect.getWorld().getWeavingSupport().makePerClauseAspect(inAspect, this.getKind()));
         }

         if (inAspect.isAnnotationStyleAspect() && !inAspect.getWorld().isXnoInline()) {
            inAspect.crosscuttingMembers.addTypeMunger(world.getWeavingSupport().createAccessForInlineMunger(inAspect));
         }

         return ret;
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      PEROBJECT.write(s);
      this.entry.write(s);
      s.writeBoolean(this.isThis);
      this.writeLocation(s);
   }

   public static PerClause readPerClause(VersionedDataInputStream s, ISourceContext context) throws IOException {
      PerClause ret = new PerObject(Pointcut.read(s, context), s.readBoolean());
      ret.readLocation(context, s);
      return ret;
   }

   public PerClause.Kind getKind() {
      return PEROBJECT;
   }

   public boolean isThis() {
      return this.isThis;
   }

   public String toString() {
      return "per" + (this.isThis ? "this" : "target") + "(" + this.entry + ")";
   }

   public String toDeclarationString() {
      return this.toString();
   }

   public Pointcut getEntry() {
      return this.entry;
   }

   public boolean equals(Object other) {
      if (!(other instanceof PerObject)) {
         return false;
      } else {
         boolean var10000;
         label48: {
            PerObject pc = (PerObject)other;
            if (pc.isThis && this.isThis) {
               label42: {
                  if (pc.inAspect == null) {
                     if (this.inAspect != null) {
                        break label42;
                     }
                  } else if (!pc.inAspect.equals(this.inAspect)) {
                     break label42;
                  }

                  if (pc.entry == null) {
                     if (this.entry == null) {
                        break label48;
                     }
                  } else if (pc.entry.equals(this.entry)) {
                     break label48;
                  }
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (this.isThis ? 0 : 1);
      result = 37 * result + (this.inAspect == null ? 0 : this.inAspect.hashCode());
      result = 37 * result + (this.entry == null ? 0 : this.entry.hashCode());
      return result;
   }

   static {
      int thisFlags = Shadow.ALL_SHADOW_KINDS_BITS;
      int targFlags = Shadow.ALL_SHADOW_KINDS_BITS;

      for(int i = 0; i < Shadow.SHADOW_KINDS.length; ++i) {
         Shadow.Kind kind = Shadow.SHADOW_KINDS[i];
         if (kind.neverHasThis()) {
            thisFlags -= kind.bit;
         }

         if (kind.neverHasTarget()) {
            targFlags -= kind.bit;
         }
      }

      thisKindSet = thisFlags;
      targetKindSet = targFlags;
   }
}
