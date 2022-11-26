package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.PerClause;
import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;

public class PerClauseImpl implements PerClause {
   private final PerClauseKind kind;

   protected PerClauseImpl(PerClauseKind kind) {
      this.kind = kind;
   }

   public PerClauseKind getKind() {
      return this.kind;
   }

   public String toString() {
      return "issingleton()";
   }
}
