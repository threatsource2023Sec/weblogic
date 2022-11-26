package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.AssertFalse;
import org.hibernate.validator.cfg.ConstraintDef;

public class AssertFalseDef extends ConstraintDef {
   public AssertFalseDef() {
      super(AssertFalse.class);
   }
}
