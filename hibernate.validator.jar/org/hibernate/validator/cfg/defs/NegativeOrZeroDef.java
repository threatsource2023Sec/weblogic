package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.NegativeOrZero;
import org.hibernate.validator.cfg.ConstraintDef;

public class NegativeOrZeroDef extends ConstraintDef {
   public NegativeOrZeroDef() {
      super(NegativeOrZero.class);
   }
}
