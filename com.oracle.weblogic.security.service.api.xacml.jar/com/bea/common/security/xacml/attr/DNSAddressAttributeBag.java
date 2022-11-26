package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class DNSAddressAttributeBag extends GenericBag {
   public DNSAddressAttributeBag() {
   }

   public DNSAddressAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.DNS_ADDRESS_BAG;
   }
}
