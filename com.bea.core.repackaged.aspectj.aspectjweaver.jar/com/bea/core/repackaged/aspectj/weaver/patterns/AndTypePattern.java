package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class AndTypePattern extends TypePattern {
   private TypePattern left;
   private TypePattern right;

   public AndTypePattern(TypePattern left, TypePattern right) {
      super(false, false);
      this.left = left;
      this.right = right;
      this.setLocation(left.getSourceContext(), left.getStart(), right.getEnd());
   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      return true;
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType type) {
      return this.left.matchesInstanceof(type).and(this.right.matchesInstanceof(type));
   }

   protected boolean matchesExactly(ResolvedType type) {
      return this.left.matchesExactly(type) && this.right.matchesExactly(type);
   }

   protected boolean matchesExactly(ResolvedType type, ResolvedType annotatedType) {
      return this.left.matchesExactly(type, annotatedType) && this.right.matchesExactly(type, annotatedType);
   }

   public boolean matchesStatically(ResolvedType type) {
      return this.left.matchesStatically(type) && this.right.matchesStatically(type);
   }

   public void setIsVarArgs(boolean isVarArgs) {
      this.isVarArgs = isVarArgs;
      this.left.setIsVarArgs(isVarArgs);
      this.right.setIsVarArgs(isVarArgs);
   }

   public void setAnnotationTypePattern(AnnotationTypePattern annPatt) {
      if (annPatt != AnnotationTypePattern.ANY) {
         if (this.left.annotationPattern == AnnotationTypePattern.ANY) {
            this.left.setAnnotationTypePattern(annPatt);
         } else {
            this.left.setAnnotationTypePattern(new AndAnnotationTypePattern(this.left.annotationPattern, annPatt));
         }

         if (this.right.annotationPattern == AnnotationTypePattern.ANY) {
            this.right.setAnnotationTypePattern(annPatt);
         } else {
            this.right.setAnnotationTypePattern(new AndAnnotationTypePattern(this.right.annotationPattern, annPatt));
         }

      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(8);
      this.left.write(s);
      this.right.write(s);
      this.writeLocation(s);
   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AndTypePattern ret = new AndTypePattern(TypePattern.read(s, context), TypePattern.read(s, context));
      ret.readLocation(context, s);
      if (ret.left.isVarArgs && ret.right.isVarArgs) {
         ret.isVarArgs = true;
      }

      return ret;
   }

   public TypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      if (requireExactType) {
         return this.notExactType(scope);
      } else {
         this.left = this.left.resolveBindings(scope, bindings, false, false);
         this.right = this.right.resolveBindings(scope, bindings, false, false);
         return this;
      }
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      TypePattern newLeft = this.left.parameterizeWith(typeVariableMap, w);
      TypePattern newRight = this.right.parameterizeWith(typeVariableMap, w);
      AndTypePattern ret = new AndTypePattern(newLeft, newRight);
      ret.copyLocationFrom(this);
      return ret;
   }

   public String toString() {
      StringBuffer buff = new StringBuffer();
      if (this.annotationPattern != AnnotationTypePattern.ANY) {
         buff.append('(');
         buff.append(this.annotationPattern.toString());
         buff.append(' ');
      }

      buff.append('(');
      buff.append(this.left.toString());
      buff.append(" && ");
      buff.append(this.right.toString());
      buff.append(')');
      if (this.annotationPattern != AnnotationTypePattern.ANY) {
         buff.append(')');
      }

      return buff.toString();
   }

   public TypePattern getLeft() {
      return this.left;
   }

   public TypePattern getRight() {
      return this.right;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof AndTypePattern)) {
         return false;
      } else {
         AndTypePattern atp = (AndTypePattern)obj;
         return this.left.equals(atp.left) && this.right.equals(atp.right);
      }
   }

   public boolean isStarAnnotation() {
      return this.left.isStarAnnotation() && this.right.isStarAnnotation();
   }

   public int hashCode() {
      int ret = 17;
      ret += 37 * this.left.hashCode();
      ret += 37 * this.right.hashCode();
      return ret;
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
