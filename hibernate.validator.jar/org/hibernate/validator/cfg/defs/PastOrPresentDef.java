package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.PastOrPresent;
import org.hibernate.validator.cfg.ConstraintDef;

public class PastOrPresentDef extends ConstraintDef {
   public PastOrPresentDef() {
      super(PastOrPresent.class);
   }
}
