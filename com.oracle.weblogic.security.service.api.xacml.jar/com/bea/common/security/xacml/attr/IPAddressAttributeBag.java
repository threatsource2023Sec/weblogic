package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class IPAddressAttributeBag extends GenericBag {
   public IPAddressAttributeBag() {
   }

   public IPAddressAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.IP_ADDRESS_BAG;
   }
}
