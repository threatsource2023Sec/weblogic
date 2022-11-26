package org.hibernate.validator.cfg.defs;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.constraints.ParameterScriptAssert;

public class ParameterScriptAssertDef extends ConstraintDef {
   public ParameterScriptAssertDef() {
      super(ParameterScriptAssert.class);
   }

   public ParameterScriptAssertDef lang(String lang) {
      this.addParameter("lang", lang);
      return this;
   }

   public ParameterScriptAssertDef script(String script) {
      this.addParameter("script", script);
      return this;
   }
}
