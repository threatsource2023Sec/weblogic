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

public class OrPointcut extends Pointcut {
   Pointcut left;
   Pointcut right;
   private int couldMatchKinds;

   public OrPointcut(Pointcut left, Pointcut right) {
      this.left = left;
      this.right = right;
      this.setLocation(left.getSourceContext(), left.getStart(), right.getEnd());
      this.pointcutKind = 6;
      this.couldMatchKinds = left.couldMatchKinds() | right.couldMatchKinds();
   }

   public int couldMatchKinds() {
      return this.couldMatchKinds;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      FuzzyBoolean leftMatch = this.left.fastMatch(type);
      return leftMatch.alwaysTrue() ? leftMatch : leftMatch.or(this.right.fastMatch(type));
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      FuzzyBoolean leftMatch = this.left.match(shadow);
      return leftMatch.alwaysTrue() ? leftMatch : leftMatch.or(this.right.match(shadow));
   }

   public String toString() {
      return "(" + this.left.toString() + " || " + this.right.toString() + ")";
   }

   public boolean equals(Object other) {
      if (!(other instanceof OrPointcut)) {
         return false;
      } else {
         OrPointcut o = (OrPointcut)other;
         return o.left.equals(this.left) && o.right.equals(this.right);
      }
   }

   public int hashCode() {
      int result = 31;
      result = 37 * result + this.left.hashCode();
      result = 37 * result + this.right.hashCode();
      return result;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      Bindings old = bindings == null ? null : bindings.copy();
      this.left.resolveBindings(scope, bindings);
      this.right.resolveBindings(scope, old);
      if (bindings != null) {
         bindings.checkEquals(old, scope);
      }

   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(6);
      this.left.write(s);
      this.right.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      OrPointcut ret = new OrPointcut(Pointcut.read(s, context), Pointcut.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      return Test.makeOr(this.left.findResidue(shadow, state), this.right.findResidue(shadow, state));
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      Pointcut ret = new OrPointcut(this.left.concretize(inAspect, declaringType, bindings), this.right.concretize(inAspect, declaringType, bindings));
      ret.copyLocationFrom(this);
      return ret;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      Pointcut ret = new OrPointcut(this.left.parameterizeWith(typeVariableMap, w), this.right.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
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
