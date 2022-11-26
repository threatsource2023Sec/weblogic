package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class IntegerAttributeBag extends GenericBag {
   public IntegerAttributeBag() {
   }

   public IntegerAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.INTEGER_BAG;
   }
}
