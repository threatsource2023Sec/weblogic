package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.UniqueElements;

public class UniqueElementsDef extends ConstraintDef {
   public UniqueElementsDef() {
      super(UniqueElements.class);
   }
}
