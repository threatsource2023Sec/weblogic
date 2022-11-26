package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class YearMonthDurationAttributeBag extends GenericBag {
   public YearMonthDurationAttributeBag() {
   }

   public YearMonthDurationAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.YEAR_MONTH_DURATION_BAG;
   }
}
