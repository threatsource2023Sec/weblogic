package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.AssertTrue;
import org.hibernate.validator.cfg.ConstraintDef;

public class AssertTrueDef extends ConstraintDef {
   public AssertTrueDef() {
      super(AssertTrue.class);
   }
}
