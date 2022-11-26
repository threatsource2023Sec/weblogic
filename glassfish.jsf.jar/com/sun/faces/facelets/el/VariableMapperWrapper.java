package com.sun.faces.facelets.el;

import java.util.HashMap;
import java.util.Map;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

public class VariableMapperWrapper extends VariableMapper {
   private final VariableMapper target;
   private Map vars;

   public VariableMapperWrapper(VariableMapper orig) {
      this.target = orig;
   }

   public ValueExpression resolveVariable(String variable) {
      ValueExpression ve = null;

      try {
         if (this.vars != null) {
            ve = (ValueExpression)this.vars.get(variable);
         }

         return ve == null ? this.target.resolveVariable(variable) : ve;
      } catch (StackOverflowError var4) {
         throw new ELException("Could not Resolve Variable [Overflow]: " + variable, var4);
      }
   }

   public ValueExpression setVariable(String variable, ValueExpression expression) {
      if (this.vars == null) {
         this.vars = new HashMap();
      }

      return (ValueExpression)this.vars.put(variable, expression);
   }
}
