package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class DateTimeAttributeBag extends GenericBag {
   public DateTimeAttributeBag() {
   }

   public DateTimeAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.DATE_TIME_BAG;
   }
}
