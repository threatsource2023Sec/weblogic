package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Future;
import org.hibernate.validator.cfg.ConstraintDef;

public class FutureDef extends ConstraintDef {
   public FutureDef() {
      super(Future.class);
   }
}
