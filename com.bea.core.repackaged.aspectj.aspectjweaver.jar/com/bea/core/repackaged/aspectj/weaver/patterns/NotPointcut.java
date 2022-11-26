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

public class NotPointcut extends Pointcut {
   private Pointcut body;

   public NotPointcut(Pointcut negated) {
      this.body = negated;
      this.pointcutKind = 7;
      this.setLocation(negated.getSourceContext(), negated.getStart(), negated.getEnd());
   }

   public NotPointcut(Pointcut pointcut, int startPos) {
      this(pointcut);
      this.setLocation(pointcut.getSourceContext(), startPos, pointcut.getEnd());
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public Pointcut getNegatedPointcut() {
      return this.body;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return this.body.fastMatch(type).not();
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      return this.body.match(shadow).not();
   }

   public String toString() {
      return "!" + this.body.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof NotPointcut)) {
         return false;
      } else {
         NotPointcut o = (NotPointcut)other;
         return o.body.equals(this.body);
      }
   }

   public int hashCode() {
      return 851 + this.body.hashCode();
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.body.resolveBindings(scope, (Bindings)null);
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(7);
      this.body.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      NotPointcut ret = new NotPointcut(Pointcut.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      return Test.makeNot(this.body.findResidue(shadow, state));
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      Pointcut ret = new NotPointcut(this.body.concretize(inAspect, declaringType, bindings));
      ret.copyLocationFrom(this);
      return ret;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      Pointcut ret = new NotPointcut(this.body.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object traverse(PatternNodeVisitor visitor, Object data) {
      Object ret = this.accept(visitor, data);
      this.body.traverse(visitor, ret);
      return ret;
   }
}
