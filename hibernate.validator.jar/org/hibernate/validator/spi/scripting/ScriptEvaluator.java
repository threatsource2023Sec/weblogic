package org.hibernate.validator.spi.scripting;

import java.util.Map;
import org.hibernate.validator.Incubating;

@Incubating
public interface ScriptEvaluator {
   Object evaluate(String var1, Map var2) throws ScriptEvaluationException;
}
