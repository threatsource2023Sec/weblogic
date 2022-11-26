package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Max;
import org.hibernate.validator.cfg.ConstraintDef;

public class MaxDef extends ConstraintDef {
   public MaxDef() {
      super(Max.class);
   }

   public MaxDef value(long max) {
      this.addParameter("value", max);
      return this;
   }
}
