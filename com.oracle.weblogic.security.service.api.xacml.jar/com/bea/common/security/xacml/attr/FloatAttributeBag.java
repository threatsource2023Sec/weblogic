package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class FloatAttributeBag extends GenericBag {
   public FloatAttributeBag() {
   }

   public FloatAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.FLOAT_BAG;
   }
}
