package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class DayTimeDurationAttributeBag extends GenericBag {
   public DayTimeDurationAttributeBag() {
   }

   public DayTimeDurationAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.DAY_TIME_DURATION_BAG;
   }
}
