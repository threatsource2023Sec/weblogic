package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class DoubleAttributeBag extends GenericBag {
   public DoubleAttributeBag() {
   }

   public DoubleAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.DOUBLE_BAG;
   }
}
