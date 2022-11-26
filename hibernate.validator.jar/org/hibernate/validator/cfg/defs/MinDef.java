package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Min;
import org.hibernate.validator.cfg.ConstraintDef;

public class MinDef extends ConstraintDef {
   public MinDef() {
      super(Min.class);
   }

   public MinDef value(long min) {
      this.addParameter("value", min);
      return this;
   }
}
