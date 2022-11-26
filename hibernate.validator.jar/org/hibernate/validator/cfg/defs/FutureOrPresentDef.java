package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.FutureOrPresent;
import org.hibernate.validator.cfg.ConstraintDef;

public class FutureOrPresentDef extends ConstraintDef {
   public FutureOrPresentDef() {
      super(FutureOrPresent.class);
   }
}
