package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Size;
import org.hibernate.validator.cfg.ConstraintDef;

public class SizeDef extends ConstraintDef {
   public SizeDef() {
      super(Size.class);
   }

   public SizeDef min(int min) {
      this.addParameter("min", min);
      return this;
   }

   public SizeDef max(int max) {
      this.addParameter("max", max);
      return this;
   }
}
