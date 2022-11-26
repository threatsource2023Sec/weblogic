package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.CrosscuttingMembers;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Expr;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PerCflow extends PerClause {
   private final boolean isBelow;
   private final Pointcut entry;

   public PerCflow(Pointcut entry, boolean isBelow) {
      this.entry = entry;
      this.isBelow = isBelow;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      return FuzzyBoolean.YES;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.entry.resolve(scope);
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      PerCflow ret = new PerCflow(this.entry.parameterizeWith(typeVariableMap, w), this.isBelow);
      ret.copyLocationFrom(this);
      return ret;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      Expr myInstance = Expr.makeCallExpr(AjcMemberMaker.perCflowAspectOfMethod(this.inAspect), Expr.NONE, this.inAspect);
      state.setAspectInstance(myInstance);
      return Test.makeCall(AjcMemberMaker.perCflowHasAspectMethod(this.inAspect), Expr.NONE);
   }

   public PerClause concretize(ResolvedType inAspect) {
      PerCflow ret = new PerCflow(this.entry, this.isBelow);
      ret.inAspect = inAspect;
      if (inAspect.isAbstract()) {
         return ret;
      } else {
         Member cflowStackField = new ResolvedMemberImpl(Member.FIELD, inAspect, 25, UnresolvedType.forName("com.bea.core.repackaged.aspectj.runtime.internal.CFlowStack"), "ajc$perCflowStack", UnresolvedType.NONE);
         World world = inAspect.getWorld();
         CrosscuttingMembers xcut = inAspect.crosscuttingMembers;
         Collection previousCflowEntries = xcut.getCflowEntries();
         Pointcut concreteEntry = this.entry.concretize(inAspect, inAspect, 0, (ShadowMunger)null);
         List innerCflowEntries = new ArrayList(xcut.getCflowEntries());
         innerCflowEntries.removeAll(previousCflowEntries);
         xcut.addConcreteShadowMunger(Advice.makePerCflowEntry(world, concreteEntry, this.isBelow, cflowStackField, inAspect, innerCflowEntries));
         if (inAspect.isAnnotationStyleAspect() && !inAspect.isAbstract()) {
            inAspect.crosscuttingMembers.addLateTypeMunger(inAspect.getWorld().getWeavingSupport().makePerClauseAspect(inAspect, this.getKind()));
         }

         if (inAspect.isAnnotationStyleAspect() && !inAspect.getWorld().isXnoInline()) {
            inAspect.crosscuttingMembers.addTypeMunger(inAspect.getWorld().getWeavingSupport().createAccessForInlineMunger(inAspect));
         }

         return ret;
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      PERCFLOW.write(s);
      this.entry.write(s);
      s.writeBoolean(this.isBelow);
      this.writeLocation(s);
   }

   public static PerClause readPerClause(VersionedDataInputStream s, ISourceContext context) throws IOException {
      PerCflow ret = new PerCflow(Pointcut.read(s, context), s.readBoolean());
      ret.readLocation(context, s);
      return ret;
   }

   public PerClause.Kind getKind() {
      return PERCFLOW;
   }

   public Pointcut getEntry() {
      return this.entry;
   }

   public String toString() {
      return "percflow(" + this.inAspect + " on " + this.entry + ")";
   }

   public String toDeclarationString() {
      return this.isBelow ? "percflowbelow(" + this.entry + ")" : "percflow(" + this.entry + ")";
   }

   public boolean equals(Object other) {
      if (!(other instanceof PerCflow)) {
         return false;
      } else {
         boolean var10000;
         label48: {
            PerCflow pc = (PerCflow)other;
            if (pc.isBelow && this.isBelow) {
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
      result = 37 * result + (this.isBelow ? 0 : 1);
      result = 37 * result + (this.inAspect == null ? 0 : this.inAspect.hashCode());
      result = 37 * result + (this.entry == null ? 0 : this.entry.hashCode());
      return result;
   }
}
