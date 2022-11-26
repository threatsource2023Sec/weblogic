package org.hibernate.validator.cfg.defs.pl;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.pl.NIP;

public class NIPDef extends ConstraintDef {
   public NIPDef() {
      super(NIP.class);
   }
}
