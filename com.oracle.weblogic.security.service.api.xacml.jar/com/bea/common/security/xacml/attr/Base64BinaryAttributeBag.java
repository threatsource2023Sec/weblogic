package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class Base64BinaryAttributeBag extends GenericBag {
   public Base64BinaryAttributeBag() {
   }

   public Base64BinaryAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.BASE64_BINARY_BAG;
   }
}
