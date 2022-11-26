package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.Mod11Check;

public class Mod11CheckDef extends ConstraintDef {
   public Mod11CheckDef() {
      super(Mod11Check.class);
   }

   public Mod11CheckDef threshold(int threshold) {
      this.addParameter("threshold", threshold);
      return this;
   }

   public Mod11CheckDef startIndex(int startIndex) {
      this.addParameter("startIndex", startIndex);
      return this;
   }

   public Mod11CheckDef endIndex(int endIndex) {
      this.addParameter("endIndex", endIndex);
      return this;
   }

   public Mod11CheckDef checkDigitIndex(int checkDigitIndex) {
      this.addParameter("checkDigitIndex", checkDigitIndex);
      return this;
   }

   public Mod11CheckDef ignoreNonDigitCharacters(boolean ignoreNonDigitCharacters) {
      this.addParameter("ignoreNonDigitCharacters", ignoreNonDigitCharacters);
      return this;
   }

   public Mod11CheckDef treatCheck10As(char treatCheck10As) {
      this.addParameter("treatCheck10As", treatCheck10As);
      return this;
   }

   public Mod11CheckDef treatCheck11As(char treatCheck11As) {
      this.addParameter("treatCheck11As", treatCheck11As);
      return this;
   }

   public Mod11CheckDef processingDirection(Mod11Check.ProcessingDirection processingDirection) {
      this.addParameter("processingDirection", processingDirection);
      return this;
   }
}
