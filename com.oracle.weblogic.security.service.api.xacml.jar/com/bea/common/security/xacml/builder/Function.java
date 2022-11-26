package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.FunctionException;
import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.URI;
import java.net.URISyntaxException;

public class Function {
   private URI functionId;
   private int paraNum;
   private boolean isHigherOrderBagFunction;

   public Function(String functionId, int paraNum) throws InvalidParameterException {
      this(functionId, paraNum, false);
   }

   public Function(String functionId, int paraNum, boolean isHigherOrderBagFunction) throws InvalidParameterException {
      this.paraNum = 0;

      try {
         this.functionId = new URI(functionId);
      } catch (URISyntaxException var5) {
         throw new InvalidParameterException(var5);
      }

      if (paraNum < 0) {
         throw new InvalidParameterException("The parameter number should not be negative.");
      } else {
         this.paraNum = paraNum;
         this.isHigherOrderBagFunction = isHigherOrderBagFunction;
      }
   }

   URI getFunctionId() {
      return this.functionId;
   }

   public String toString() {
      return "Function: [" + this.functionId + "], parameter number: [" + this.paraNum + "]";
   }

   public Expression apply(Parameter... parameters) throws FunctionException {
      if (parameters != null && parameters.length != 0) {
         if (this.paraNum != 0 && this.paraNum != parameters.length) {
            throw new FunctionException("the parameter number for the function " + this.functionId.toString() + "should be [" + this.paraNum + "]");
         } else {
            return new Expression(this, parameters);
         }
      } else {
         throw new FunctionException("The parameters should be provided.");
      }
   }

   boolean isHigherOrderBagFunction() {
      return this.isHigherOrderBagFunction;
   }
}
