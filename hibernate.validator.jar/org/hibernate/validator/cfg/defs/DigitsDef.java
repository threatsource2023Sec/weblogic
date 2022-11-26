package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Digits;
import org.hibernate.validator.cfg.ConstraintDef;

public class DigitsDef extends ConstraintDef {
   public DigitsDef() {
      super(Digits.class);
   }

   public DigitsDef integer(int integer) {
      this.addParameter("integer", integer);
      return this;
   }

   public DigitsDef fraction(int fraction) {
      this.addParameter("fraction", fraction);
      return this;
   }
}
