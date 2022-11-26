package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.policy.Apply;
import java.util.ArrayList;
import java.util.List;

public class Expression implements Parameter {
   private Function function;
   private Parameter[] parameters;
   private Apply apply;

   public Expression(Apply apply) {
      this.apply = apply;
   }

   Expression(Function function, Parameter[] parameters) {
      this.function = function;
      this.parameters = parameters;
   }

   public com.bea.common.security.xacml.policy.Expression toXACML() throws InvalidParameterException {
      if (this.apply != null) {
         return this.apply;
      } else {
         List childs = new ArrayList();
         this.apply = new Apply(this.function.getFunctionId(), childs);
         if (this.parameters != null) {
            if (this.function.isHigherOrderBagFunction()) {
               if (!(this.parameters[0] instanceof Expression)) {
                  throw new InvalidParameterException("the higer order bag functions should take an expression as parameter.");
               }

               Expression exp = (Expression)this.parameters[0];
               if (exp.function == null || exp.parameters == null) {
                  throw new InvalidParameterException("the inside function and parameters for higher order bag functions should not be null.");
               }

               childs.add(new com.bea.common.security.xacml.policy.Function(exp.function.getFunctionId()));

               for(int i = 0; i < exp.parameters.length && exp.parameters[i] != null; ++i) {
                  childs.add(exp.parameters[i].toXACML());
               }
            } else {
               for(int i = 0; i < this.parameters.length && this.parameters[i] != null; ++i) {
                  childs.add(this.parameters[i].toXACML());
               }
            }
         }

         return this.apply;
      }
   }

   public String toString() {
      try {
         return this.toXACML().toString();
      } catch (InvalidParameterException var2) {
         throw new RuntimeException(var2);
      }
   }
}
