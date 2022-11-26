package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class NotTypePattern extends TypePattern {
   private TypePattern negatedPattern;
   private boolean isBangVoid = false;
   private boolean checked = false;

   public NotTypePattern(TypePattern pattern) {
      super(false, false);
      this.negatedPattern = pattern;
      this.setLocation(pattern.getSourceContext(), pattern.getStart(), pattern.getEnd());
   }

   public TypePattern getNegatedPattern() {
      return this.negatedPattern;
   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      return true;
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType type) {
      return this.negatedPattern.matchesInstanceof(type).not();
   }

   protected boolean matchesExactly(ResolvedType type) {
      return !this.negatedPattern.matchesExactly(type) && this.annotationPattern.matches(type).alwaysTrue();
   }

   protected boolean matchesExactly(ResolvedType type, ResolvedType annotatedType) {
      return !this.negatedPattern.matchesExactly(type, annotatedType) && this.annotationPattern.matches(annotatedType).alwaysTrue();
   }

   public boolean matchesStatically(ResolvedType type) {
      return !this.negatedPattern.matchesStatically(type);
   }

   public void setAnnotationTypePattern(AnnotationTypePattern annPatt) {
      super.setAnnotationTypePattern(annPatt);
   }

   public void setIsVarArgs(boolean isVarArgs) {
      this.negatedPattern.setIsVarArgs(isVarArgs);
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(6);
      this.negatedPattern.write(s);
      this.annotationPattern.write(s);
      this.writeLocation(s);
   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      TypePattern ret = new NotTypePattern(TypePattern.read(s, context));
      if (s.getMajorVersion() >= 2) {
         ret.annotationPattern = AnnotationTypePattern.read(s, context);
      }

      ret.readLocation(context, s);
      return ret;
   }

   public TypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      if (requireExactType) {
         return this.notExactType(scope);
      } else {
         this.negatedPattern = this.negatedPattern.resolveBindings(scope, bindings, false, false);
         return this;
      }
   }

   public boolean isBangVoid() {
      if (!this.checked) {
         this.isBangVoid = this.negatedPattern.getExactType().isVoid();
         this.checked = true;
      }

      return this.isBangVoid;
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      TypePattern newNegatedPattern = this.negatedPattern.parameterizeWith(typeVariableMap, w);
      NotTypePattern ret = new NotTypePattern(newNegatedPattern);
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

      buff.append('!');
      buff.append(this.negatedPattern);
      if (this.annotationPattern != AnnotationTypePattern.ANY) {
         buff.append(')');
      }

      return buff.toString();
   }

   public boolean equals(Object obj) {
      return !(obj instanceof NotTypePattern) ? false : this.negatedPattern.equals(((NotTypePattern)obj).negatedPattern);
   }

   public int hashCode() {
      return 17 + 37 * this.negatedPattern.hashCode();
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object traverse(PatternNodeVisitor visitor, Object data) {
      Object ret = this.accept(visitor, data);
      this.negatedPattern.traverse(visitor, ret);
      return ret;
   }
}
