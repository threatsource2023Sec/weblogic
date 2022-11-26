package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.Mod10Check;

public class Mod10CheckDef extends ConstraintDef {
   public Mod10CheckDef() {
      super(Mod10Check.class);
   }

   public Mod10CheckDef multiplier(int multiplier) {
      this.addParameter("multiplier", multiplier);
      return this;
   }

   public Mod10CheckDef weight(int weight) {
      this.addParameter("weight", weight);
      return this;
   }

   public Mod10CheckDef startIndex(int startIndex) {
      this.addParameter("startIndex", startIndex);
      return this;
   }

   public Mod10CheckDef endIndex(int endIndex) {
      this.addParameter("endIndex", endIndex);
      return this;
   }

   public Mod10CheckDef checkDigitIndex(int checkDigitIndex) {
      this.addParameter("checkDigitIndex", checkDigitIndex);
      return this;
   }

   public Mod10CheckDef ignoreNonDigitCharacters(boolean ignoreNonDigitCharacters) {
      this.addParameter("ignoreNonDigitCharacters", ignoreNonDigitCharacters);
      return this;
   }
}
