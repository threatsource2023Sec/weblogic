package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class EllipsisTypePattern extends TypePattern {
   public EllipsisTypePattern() {
      super(false, false, new TypePatternList());
   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      return true;
   }

   protected boolean matchesExactly(ResolvedType type) {
      return false;
   }

   protected boolean matchesExactly(ResolvedType type, ResolvedType annotatedType) {
      return false;
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType type) {
      return FuzzyBoolean.NO;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(4);
   }

   public boolean isEllipsis() {
      return true;
   }

   public String toString() {
      return "..";
   }

   public boolean equals(Object obj) {
      return obj instanceof EllipsisTypePattern;
   }

   public int hashCode() {
      return 629;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      return this;
   }
}
