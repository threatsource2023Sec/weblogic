package com.sun.faces.facelets.el;

import javax.el.ValueExpression;
import javax.el.VariableMapper;

public final class CompositeVariableMapper extends VariableMapper {
   private final VariableMapper var0;
   private final VariableMapper var1;

   public CompositeVariableMapper(VariableMapper var0, VariableMapper var1) {
      this.var0 = var0;
      this.var1 = var1;
   }

   public ValueExpression resolveVariable(String name) {
      ValueExpression ve = this.var0.resolveVariable(name);
      return ve == null ? this.var1.resolveVariable(name) : ve;
   }

   public ValueExpression setVariable(String name, ValueExpression expression) {
      return this.var0.setVariable(name, expression);
   }
}
