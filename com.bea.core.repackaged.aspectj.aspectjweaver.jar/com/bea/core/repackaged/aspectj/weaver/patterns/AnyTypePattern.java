package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class AnyTypePattern extends TypePattern {
   public AnyTypePattern() {
      super(false, false, new TypePatternList());
   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      return true;
   }

   protected boolean matchesExactly(ResolvedType type) {
      return true;
   }

   protected boolean matchesExactly(ResolvedType type, ResolvedType annotatedType) {
      return true;
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType type) {
      return FuzzyBoolean.YES;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(5);
   }

   protected boolean matchesSubtypes(ResolvedType type) {
      return true;
   }

   public boolean isStar() {
      return true;
   }

   public String toString() {
      return "*";
   }

   public boolean equals(Object obj) {
      return obj instanceof AnyTypePattern;
   }

   public int hashCode() {
      return 37;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public TypePattern parameterizeWith(Map arg0, World w) {
      return this;
   }
}
