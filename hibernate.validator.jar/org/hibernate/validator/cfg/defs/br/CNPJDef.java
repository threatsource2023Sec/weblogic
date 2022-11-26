package org.hibernate.validator.cfg.defs.br;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.br.CNPJ;

public class CNPJDef extends ConstraintDef {
   public CNPJDef() {
      super(CNPJ.class);
   }
}
