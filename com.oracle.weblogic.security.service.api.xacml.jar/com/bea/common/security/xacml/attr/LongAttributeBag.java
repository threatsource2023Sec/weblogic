package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class LongAttributeBag extends GenericBag {
   public LongAttributeBag() {
   }

   public LongAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.LONG_BAG;
   }
}
