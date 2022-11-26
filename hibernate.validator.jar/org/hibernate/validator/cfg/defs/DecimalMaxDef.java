package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.DecimalMax;
import org.hibernate.validator.cfg.ConstraintDef;

public class DecimalMaxDef extends ConstraintDef {
   public DecimalMaxDef() {
      super(DecimalMax.class);
   }

   public DecimalMaxDef value(String max) {
      this.addParameter("value", max);
      return this;
   }

   public DecimalMaxDef inclusive(boolean inclusive) {
      this.addParameter("inclusive", inclusive);
      return this;
   }
}
