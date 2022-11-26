package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class StringAttributeBag extends GenericBag {
   public StringAttributeBag() {
   }

   public StringAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.STRING_BAG;
   }
}
