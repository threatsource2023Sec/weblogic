package org.hibernate.validator.cfg.defs;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.cfg.ConstraintDef;

public class EmailDef extends ConstraintDef {
   public EmailDef() {
      super(Email.class);
   }

   public EmailDef regexp(String regexp) {
      this.addParameter("regexp", regexp);
      return this;
   }

   public EmailDef flags(Pattern.Flag... flags) {
      this.addParameter("flags", flags);
      return this;
   }
}
