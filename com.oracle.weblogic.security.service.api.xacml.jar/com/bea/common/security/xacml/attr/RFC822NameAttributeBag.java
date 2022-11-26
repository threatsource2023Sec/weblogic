package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class RFC822NameAttributeBag extends GenericBag {
   public RFC822NameAttributeBag() {
   }

   public RFC822NameAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.RFC822_NAME_BAG;
   }
}
