package org.hibernate.validator.cfg.defs.br;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.br.CPF;

public class CPFDef extends ConstraintDef {
   public CPFDef() {
      super(CPF.class);
   }
}
