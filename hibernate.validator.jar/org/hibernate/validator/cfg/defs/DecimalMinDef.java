package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.cfg.ConstraintDef;

public class DecimalMinDef extends ConstraintDef {
   public DecimalMinDef() {
      super(DecimalMin.class);
   }

   public DecimalMinDef value(String min) {
      this.addParameter("value", min);
      return this;
   }

   public DecimalMinDef inclusive(boolean inclusive) {
      this.addParameter("inclusive", inclusive);
      return this;
   }
}
