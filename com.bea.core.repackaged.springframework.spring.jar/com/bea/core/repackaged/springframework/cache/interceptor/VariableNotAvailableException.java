package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.expression.EvaluationException;

class VariableNotAvailableException extends EvaluationException {
   private final String name;

   public VariableNotAvailableException(String name) {
      super("Variable not available");
      this.name = name;
   }

   public final String getName() {
      return this.name;
   }
}
