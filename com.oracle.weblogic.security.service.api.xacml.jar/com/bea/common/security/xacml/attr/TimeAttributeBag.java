package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class TimeAttributeBag extends GenericBag {
   public TimeAttributeBag() {
   }

   public TimeAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.TIME_BAG;
   }
}
