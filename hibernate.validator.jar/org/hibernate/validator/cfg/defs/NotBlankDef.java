package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.cfg.ConstraintDef;

public class NotBlankDef extends ConstraintDef {
   public NotBlankDef() {
      super(NotBlank.class);
   }
}
