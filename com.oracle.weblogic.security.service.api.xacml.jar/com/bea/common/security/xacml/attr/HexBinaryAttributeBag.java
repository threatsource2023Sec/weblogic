package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class HexBinaryAttributeBag extends GenericBag {
   public HexBinaryAttributeBag() {
   }

   public HexBinaryAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.HEX_BINARY_BAG;
   }
}
