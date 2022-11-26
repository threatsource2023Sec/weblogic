package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.CodePointLength;

public class CodePointLengthDef extends ConstraintDef {
   public CodePointLengthDef() {
      super(CodePointLength.class);
   }

   public CodePointLengthDef min(int min) {
      this.addParameter("min", min);
      return this;
   }

   public CodePointLengthDef max(int max) {
      this.addParameter("max", max);
      return this;
   }

   public CodePointLengthDef normalizationStrategy(CodePointLength.NormalizationStrategy strategy) {
      this.addParameter("normalizationStrategy", strategy);
      return this;
   }
}
