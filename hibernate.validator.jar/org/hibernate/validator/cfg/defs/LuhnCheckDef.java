package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.LuhnCheck;

public class LuhnCheckDef extends ConstraintDef {
   public LuhnCheckDef() {
      super(LuhnCheck.class);
   }

   public LuhnCheckDef startIndex(int index) {
      this.addParameter("startIndex", index);
      return this;
   }

   public LuhnCheckDef endIndex(int index) {
      this.addParameter("endIndex", index);
      return this;
   }

   public LuhnCheckDef checkDigitIndex(int index) {
      this.addParameter("checkDigitIndex", index);
      return this;
   }

   public LuhnCheckDef ignoreNonDigitCharacters(boolean ignore) {
      this.addParameter("ignoreNonDigitCharacters", ignore);
      return this;
   }
}
