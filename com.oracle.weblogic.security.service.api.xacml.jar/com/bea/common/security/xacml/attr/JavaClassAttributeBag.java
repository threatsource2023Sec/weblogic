package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class JavaClassAttributeBag extends GenericBag {
   public JavaClassAttributeBag() {
   }

   public JavaClassAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.CLASS_BAG;
   }
}
