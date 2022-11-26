package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class BooleanAttributeBag extends GenericBag {
   public BooleanAttributeBag() {
   }

   public BooleanAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.BOOLEAN_BAG;
   }
}
