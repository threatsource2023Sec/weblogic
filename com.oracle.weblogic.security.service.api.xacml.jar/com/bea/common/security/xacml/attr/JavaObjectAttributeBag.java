package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class JavaObjectAttributeBag extends GenericBag {
   public JavaObjectAttributeBag() {
   }

   public JavaObjectAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.OBJECT_BAG;
   }
}
