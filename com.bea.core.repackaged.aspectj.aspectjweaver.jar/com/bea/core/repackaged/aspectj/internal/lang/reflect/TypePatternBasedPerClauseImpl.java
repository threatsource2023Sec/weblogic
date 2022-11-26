package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;
import com.bea.core.repackaged.aspectj.lang.reflect.TypePattern;
import com.bea.core.repackaged.aspectj.lang.reflect.TypePatternBasedPerClause;

public class TypePatternBasedPerClauseImpl extends PerClauseImpl implements TypePatternBasedPerClause {
   private TypePattern typePattern;

   public TypePatternBasedPerClauseImpl(PerClauseKind kind, String pattern) {
      super(kind);
      this.typePattern = new TypePatternImpl(pattern);
   }

   public TypePattern getTypePattern() {
      return this.typePattern;
   }

   public String toString() {
      return "pertypewithin(" + this.typePattern.asString() + ")";
   }
}
