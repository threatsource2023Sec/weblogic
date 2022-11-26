package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.Length;

public class LengthDef extends ConstraintDef {
   public LengthDef() {
      super(Length.class);
   }

   public LengthDef min(int min) {
      this.addParameter("min", min);
      return this;
   }

   public LengthDef max(int max) {
      this.addParameter("max", max);
      return this;
   }
}
