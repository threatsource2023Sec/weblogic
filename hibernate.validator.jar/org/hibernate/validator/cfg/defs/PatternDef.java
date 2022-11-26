package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.cfg.ConstraintDef;

public class PatternDef extends ConstraintDef {
   public PatternDef() {
      super(Pattern.class);
   }

   public PatternDef flags(Pattern.Flag[] flags) {
      this.addParameter("flags", flags);
      return this;
   }

   public PatternDef regexp(String regexp) {
      this.addParameter("regexp", regexp);
      return this;
   }
}
