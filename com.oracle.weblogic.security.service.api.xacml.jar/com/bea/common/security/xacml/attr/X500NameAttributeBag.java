package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class X500NameAttributeBag extends GenericBag {
   public X500NameAttributeBag() {
   }

   public X500NameAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.X500_NAME_BAG;
   }
}
