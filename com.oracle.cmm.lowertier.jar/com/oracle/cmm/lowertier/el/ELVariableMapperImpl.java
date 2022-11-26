package com.oracle.cmm.lowertier.el;

import java.util.HashMap;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

public class ELVariableMapperImpl extends VariableMapper {
   private HashMap expressionMap = null;

   public ELVariableMapperImpl() {
      this.expressionMap = new HashMap();
   }

   public ValueExpression resolveVariable(String variable) {
      return variable == null ? null : (ValueExpression)this.expressionMap.get(variable);
   }

   public ValueExpression setVariable(String variable, ValueExpression expression) {
      return variable == null ? null : (ValueExpression)this.expressionMap.put(variable, expression);
   }
}
