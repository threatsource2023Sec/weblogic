package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class DecimalAttributeBag extends GenericBag {
   public DecimalAttributeBag() {
   }

   public DecimalAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.DECIMAL_BAG;
   }
}
