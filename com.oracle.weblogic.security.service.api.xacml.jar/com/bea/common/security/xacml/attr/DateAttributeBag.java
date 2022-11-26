package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class DateAttributeBag extends GenericBag {
   public DateAttributeBag() {
   }

   public DateAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.DATE_BAG;
   }
}
