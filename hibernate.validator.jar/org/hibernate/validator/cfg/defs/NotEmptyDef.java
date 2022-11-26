package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.cfg.ConstraintDef;

public class NotEmptyDef extends ConstraintDef {
   public NotEmptyDef() {
      super(NotEmpty.class);
   }
}
