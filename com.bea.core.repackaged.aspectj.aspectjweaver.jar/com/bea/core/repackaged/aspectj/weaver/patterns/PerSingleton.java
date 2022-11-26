package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Expr;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.Map;

public class PerSingleton extends PerClause {
   private ResolvedMember perSingletonAspectOfMethod;

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.YES;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      return FuzzyBoolean.YES;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      return this;
   }

   public Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (this.perSingletonAspectOfMethod == null) {
         this.perSingletonAspectOfMethod = AjcMemberMaker.perSingletonAspectOfMethod(this.inAspect);
      }

      Expr myInstance = Expr.makeCallExpr(this.perSingletonAspectOfMethod, Expr.NONE, this.inAspect);
      state.setAspectInstance(myInstance);
      return Literal.TRUE;
   }

   public PerClause concretize(ResolvedType inAspect) {
      PerSingleton ret = new PerSingleton();
      ret.copyLocationFrom(this);
      World world = inAspect.getWorld();
      ret.inAspect = inAspect;
      if (inAspect.isAnnotationStyleAspect() && !inAspect.isAbstract()) {
         if (this.getKind() == SINGLETON) {
            inAspect.crosscuttingMembers.addTypeMunger(world.getWeavingSupport().makePerClauseAspect(inAspect, this.getKind()));
         } else {
            inAspect.crosscuttingMembers.addLateTypeMunger(world.getWeavingSupport().makePerClauseAspect(inAspect, this.getKind()));
         }
      }

      if (inAspect.isAnnotationStyleAspect() && !inAspect.getWorld().isXnoInline()) {
         inAspect.crosscuttingMembers.addTypeMunger(world.getWeavingSupport().createAccessForInlineMunger(inAspect));
      }

      return ret;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      SINGLETON.write(s);
      this.writeLocation(s);
   }

   public static PerClause readPerClause(VersionedDataInputStream s, ISourceContext context) throws IOException {
      PerSingleton ret = new PerSingleton();
      ret.readLocation(context, s);
      return ret;
   }

   public PerClause.Kind getKind() {
      return SINGLETON;
   }

   public String toString() {
      return "persingleton(" + this.inAspect + ")";
   }

   public String toDeclarationString() {
      return "";
   }

   public boolean equals(Object other) {
      if (!(other instanceof PerSingleton)) {
         return false;
      } else {
         PerSingleton pc = (PerSingleton)other;
         return pc.inAspect == null ? this.inAspect == null : pc.inAspect.equals(this.inAspect);
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (this.inAspect == null ? 0 : this.inAspect.hashCode());
      return result;
   }
}
