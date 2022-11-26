package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Past;
import org.hibernate.validator.cfg.ConstraintDef;

public class PastDef extends ConstraintDef {
   public PastDef() {
      super(Past.class);
   }
}
