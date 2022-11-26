package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Positive;
import org.hibernate.validator.cfg.ConstraintDef;

public class PositiveDef extends ConstraintDef {
   public PositiveDef() {
      super(Positive.class);
   }
}
