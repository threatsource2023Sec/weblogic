package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class AnyURIAttributeBag extends GenericBag {
   public AnyURIAttributeBag() {
   }

   public AnyURIAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.ANY_URI_BAG;
   }
}
