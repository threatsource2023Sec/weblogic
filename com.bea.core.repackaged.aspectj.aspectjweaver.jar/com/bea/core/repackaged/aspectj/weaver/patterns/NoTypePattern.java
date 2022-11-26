package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class NoTypePattern extends TypePattern {
   public NoTypePattern() {
      super(false, false, new TypePatternList());
   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      return false;
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
      s.writeByte(9);
   }

   protected boolean matchesSubtypes(ResolvedType type) {
      return false;
   }

   public boolean isStar() {
      return false;
   }

   public String toString() {
      return "<nothing>";
   }

   public boolean equals(Object obj) {
      return obj instanceof NoTypePattern;
   }

   public int hashCode() {
      return 23273;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public TypePattern parameterizeWith(Map arg0, World w) {
      return this;
   }
}
