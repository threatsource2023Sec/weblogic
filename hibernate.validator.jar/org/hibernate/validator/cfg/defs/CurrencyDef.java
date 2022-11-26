package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.Currency;

public class CurrencyDef extends ConstraintDef {
   public CurrencyDef() {
      super(Currency.class);
   }

   public CurrencyDef value(String... value) {
      this.addParameter("value", value);
      return this;
   }
}
