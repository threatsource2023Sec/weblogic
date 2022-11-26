package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Negative;
import org.hibernate.validator.cfg.ConstraintDef;

public class NegativeDef extends ConstraintDef {
   public NegativeDef() {
      super(Negative.class);
   }
}
