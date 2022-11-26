package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class FunctionAttributeBag extends GenericBag {
   public FunctionAttributeBag() {
   }

   public FunctionAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.FUNCTION_BAG;
   }
}
