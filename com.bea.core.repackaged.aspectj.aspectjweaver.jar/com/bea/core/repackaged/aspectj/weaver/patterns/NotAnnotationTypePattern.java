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

public class NotAnnotationTypePattern extends AnnotationTypePattern {
   AnnotationTypePattern negatedPattern;

   public NotAnnotationTypePattern(AnnotationTypePattern pattern) {
      this.negatedPattern = pattern;
      this.setLocation(pattern.getSourceContext(), pattern.getStart(), pattern.getEnd());
   }

   public FuzzyBoolean matches(AnnotatedElement annotated) {
      return this.negatedPattern.matches(annotated).not();
   }

   public FuzzyBoolean matches(AnnotatedElement annotated, ResolvedType[] parameterAnnotations) {
      return this.negatedPattern.matches(annotated, parameterAnnotations).not();
   }

   public void resolve(World world) {
      this.negatedPattern.resolve(world);
   }

   public AnnotationTypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding) {
      this.negatedPattern = this.negatedPattern.resolveBindings(scope, bindings, allowBinding);
      return this;
   }

   public AnnotationTypePattern parameterizeWith(Map typeVariableMap, World w) {
      AnnotationTypePattern newNegatedPattern = this.negatedPattern.parameterizeWith(typeVariableMap, w);
      NotAnnotationTypePattern ret = new NotAnnotationTypePattern(newNegatedPattern);
      ret.copyLocationFrom(this);
      if (this.isForParameterAnnotationMatch()) {
         ret.setForParameterAnnotationMatch();
      }

      return ret;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(3);
      this.negatedPattern.write(s);
      this.writeLocation(s);
      s.writeBoolean(this.isForParameterAnnotationMatch());
   }

   public static AnnotationTypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AnnotationTypePattern ret = new NotAnnotationTypePattern(AnnotationTypePattern.read(s, context));
      ret.readLocation(context, s);
      if (s.getMajorVersion() >= 4 && s.readBoolean()) {
         ret.setForParameterAnnotationMatch();
      }

      return ret;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof NotAnnotationTypePattern)) {
         return false;
      } else {
         NotAnnotationTypePattern other = (NotAnnotationTypePattern)obj;
         return other.negatedPattern.equals(this.negatedPattern) && other.isForParameterAnnotationMatch() == this.isForParameterAnnotationMatch();
      }
   }

   public int hashCode() {
      int result = 17 + 37 * this.negatedPattern.hashCode();
      result = 37 * result + (this.isForParameterAnnotationMatch() ? 0 : 1);
      return result;
   }

   public String toString() {
      return "!" + this.negatedPattern.toString();
   }

   public AnnotationTypePattern getNegatedPattern() {
      return this.negatedPattern;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object traverse(PatternNodeVisitor visitor, Object data) {
      Object ret = this.accept(visitor, data);
      this.negatedPattern.traverse(visitor, ret);
      return ret;
   }

   public void setForParameterAnnotationMatch() {
      this.negatedPattern.setForParameterAnnotationMatch();
   }
}
