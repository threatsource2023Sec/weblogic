package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.cfg.ConstraintDef;

public class PositiveOrZeroDef extends ConstraintDef {
   public PositiveOrZeroDef() {
      super(PositiveOrZero.class);
   }
}
