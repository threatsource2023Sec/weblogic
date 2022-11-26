package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AnnotatedElement;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class AnyAnnotationTypePattern extends AnnotationTypePattern {
   public FuzzyBoolean fastMatches(AnnotatedElement annotated) {
      return FuzzyBoolean.YES;
   }

   public FuzzyBoolean matches(AnnotatedElement annotated) {
      return FuzzyBoolean.YES;
   }

   public FuzzyBoolean matches(AnnotatedElement annotated, ResolvedType[] parameterAnnotations) {
      return FuzzyBoolean.YES;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(7);
   }

   public void resolve(World world) {
   }

   public String toString() {
      return "@ANY";
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean isAny() {
      return true;
   }

   public AnnotationTypePattern parameterizeWith(Map arg0, World w) {
      return this;
   }

   public void setForParameterAnnotationMatch() {
   }
}
