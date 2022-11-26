package com.sun.faces.facelets.el;

import java.util.HashMap;
import java.util.Map;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

public final class DefaultVariableMapper extends VariableMapper {
   private Map vars;

   public ValueExpression resolveVariable(String name) {
      return this.vars != null ? (ValueExpression)this.vars.get(name) : null;
   }

   public ValueExpression setVariable(String name, ValueExpression expression) {
      if (this.vars == null) {
         this.vars = new HashMap();
      }

      return (ValueExpression)this.vars.put(name, expression);
   }
}
