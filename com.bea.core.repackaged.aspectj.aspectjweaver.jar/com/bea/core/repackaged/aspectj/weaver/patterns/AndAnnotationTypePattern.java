package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AnnotatedElement;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class AndAnnotationTypePattern extends AnnotationTypePattern {
   private AnnotationTypePattern left;
   private AnnotationTypePattern right;

   public AndAnnotationTypePattern(AnnotationTypePattern left, AnnotationTypePattern right) {
      this.left = left;
      this.right = right;
      this.setLocation(left.getSourceContext(), left.getStart(), right.getEnd());
   }

   public FuzzyBoolean matches(AnnotatedElement annotated) {
      return this.left.matches(annotated).and(this.right.matches(annotated));
   }

   public FuzzyBoolean matches(AnnotatedElement annotated, ResolvedType[] parameterAnnotations) {
      return this.left.matches(annotated, parameterAnnotations).and(this.right.matches(annotated, parameterAnnotations));
   }

   public void resolve(World world) {
      this.left.resolve(world);
      this.right.resolve(world);
   }

   public AnnotationTypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding) {
      this.left = this.left.resolveBindings(scope, bindings, allowBinding);
      this.right = this.right.resolveBindings(scope, bindings, allowBinding);
      return this;
   }

   public AnnotationTypePattern parameterizeWith(Map typeVariableMap, World w) {
      AnnotationTypePattern newLeft = this.left.parameterizeWith(typeVariableMap, w);
      AnnotationTypePattern newRight = this.right.parameterizeWith(typeVariableMap, w);
      AndAnnotationTypePattern ret = new AndAnnotationTypePattern(newLeft, newRight);
      ret.copyLocationFrom(this);
      if (this.isForParameterAnnotationMatch()) {
         ret.setForParameterAnnotationMatch();
      }

      return ret;
   }

   public static AnnotationTypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AnnotationTypePattern p = new AndAnnotationTypePattern(AnnotationTypePattern.read(s, context), AnnotationTypePattern.read(s, context));
      p.readLocation(context, s);
      if (s.getMajorVersion() >= 4 && s.readBoolean()) {
         p.setForParameterAnnotationMatch();
      }

      return p;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(5);
      this.left.write(s);
      this.right.write(s);
      this.writeLocation(s);
      s.writeBoolean(this.isForParameterAnnotationMatch());
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof AndAnnotationTypePattern)) {
         return false;
      } else {
         AndAnnotationTypePattern other = (AndAnnotationTypePattern)obj;
         return this.left.equals(other.left) && this.right.equals(other.right) && this.left.isForParameterAnnotationMatch() == this.right.isForParameterAnnotationMatch();
      }
   }

   public int hashCode() {
      int result = 17;
      result = result * 37 + this.left.hashCode();
      result = result * 37 + this.right.hashCode();
      result = result * 37 + (this.isForParameterAnnotationMatch() ? 0 : 1);
      return result;
   }

   public String toString() {
      return this.left.toString() + " " + this.right.toString();
   }

   public AnnotationTypePattern getLeft() {
      return this.left;
   }

   public AnnotationTypePattern getRight() {
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

   public void setForParameterAnnotationMatch() {
      this.left.setForParameterAnnotationMatch();
      this.right.setForParameterAnnotationMatch();
   }
}
