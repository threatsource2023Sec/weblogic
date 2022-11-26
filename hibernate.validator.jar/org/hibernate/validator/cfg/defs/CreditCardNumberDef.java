package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.CreditCardNumber;

public class CreditCardNumberDef extends ConstraintDef {
   public CreditCardNumberDef() {
      super(CreditCardNumber.class);
   }
}
