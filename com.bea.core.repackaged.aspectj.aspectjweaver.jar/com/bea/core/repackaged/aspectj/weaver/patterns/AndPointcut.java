package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.Map;

public class AndPointcut extends Pointcut {
   Pointcut left;
   Pointcut right;
   private int couldMatchKinds;

   public AndPointcut(Pointcut left, Pointcut right) {
      this.left = left;
      this.right = right;
      this.pointcutKind = 5;
      this.setLocation(left.getSourceContext(), left.getStart(), right.getEnd());
      this.couldMatchKinds = left.couldMatchKinds() & right.couldMatchKinds();
   }

   public int couldMatchKinds() {
      return this.couldMatchKinds;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      FuzzyBoolean leftMatch = this.left.fastMatch(type);
      return leftMatch.alwaysFalse() ? leftMatch : leftMatch.and(this.right.fastMatch(type));
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      FuzzyBoolean leftMatch = this.left.match(shadow);
      return leftMatch.alwaysFalse() ? leftMatch : leftMatch.and(this.right.match(shadow));
   }

   public String toString() {
      return "(" + this.left.toString() + " && " + this.right.toString() + ")";
   }

   public boolean equals(Object other) {
      if (!(other instanceof AndPointcut)) {
         return false;
      } else {
         AndPointcut o = (AndPointcut)other;
         return o.left.equals(this.left) && o.right.equals(this.right);
      }
   }

   public int hashCode() {
      int result = 19;
      result = 37 * result + this.left.hashCode();
      result = 37 * result + this.right.hashCode();
      return result;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.left.resolveBindings(scope, bindings);
      this.right.resolveBindings(scope, bindings);
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(5);
      this.left.write(s);
      this.right.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AndPointcut ret = new AndPointcut(Pointcut.read(s, context), Pointcut.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      return Test.makeAnd(this.left.findResidue(shadow, state), this.right.findResidue(shadow, state));
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      AndPointcut ret = new AndPointcut(this.left.concretize(inAspect, declaringType, bindings), this.right.concretize(inAspect, declaringType, bindings));
      ret.copyLocationFrom(this);
      ret.m_ignoreUnboundBindingForNames = this.m_ignoreUnboundBindingForNames;
      return ret;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      AndPointcut ret = new AndPointcut(this.left.parameterizeWith(typeVariableMap, w), this.right.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      ret.m_ignoreUnboundBindingForNames = this.m_ignoreUnboundBindingForNames;
      return ret;
   }

   public Pointcut getLeft() {
      return this.left;
   }

   public Pointcut getRight() {
      return this.right;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object traverse(PatternNodeVisitor visitor, Object data) {
      Object ret = this.accept(visitor, data);
      this.left.traverse(visitor, ret);
      this.right.traverse(visitor, ret);
      return ret;
   }
}
