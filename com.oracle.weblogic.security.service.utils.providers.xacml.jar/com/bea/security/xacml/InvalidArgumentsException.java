package com.bea.security.xacml;

import com.bea.common.security.xacml.Type;
import java.util.List;

public class InvalidArgumentsException extends Exception {
   private static final long serialVersionUID = 4051325656333891122L;
   private Type returnType;
   private List argumentTypes;

   public InvalidArgumentsException(Type returnType, List argumentTypes) {
      this.returnType = returnType;
      this.argumentTypes = argumentTypes;
   }

   public Type getExpectedReturnType() {
      return this.returnType;
   }

   public List getExpectedArguments() {
      return this.argumentTypes;
   }
}
