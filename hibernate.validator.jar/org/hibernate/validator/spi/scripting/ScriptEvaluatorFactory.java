package org.hibernate.validator.spi.scripting;

import org.hibernate.validator.Incubating;

@Incubating
public interface ScriptEvaluatorFactory {
   ScriptEvaluator getScriptEvaluatorByLanguageName(String var1);

   void clear();
}
