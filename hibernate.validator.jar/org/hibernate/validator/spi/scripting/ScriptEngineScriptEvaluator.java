package org.hibernate.validator.spi.scripting;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.SimpleBindings;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

@Incubating
public class ScriptEngineScriptEvaluator implements ScriptEvaluator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final ScriptEngine engine;

   public ScriptEngineScriptEvaluator(ScriptEngine engine) {
      this.engine = engine;
   }

   public Object evaluate(String script, Map bindings) throws ScriptEvaluationException {
      if (this.engineAllowsParallelAccessFromMultipleThreads()) {
         return this.doEvaluate(script, bindings);
      } else {
         synchronized(this.engine) {
            return this.doEvaluate(script, bindings);
         }
      }
   }

   private Object doEvaluate(String script, Map bindings) throws ScriptEvaluationException {
      try {
         return this.engine.eval(script, new SimpleBindings(bindings));
      } catch (Exception var4) {
         throw LOG.getErrorExecutingScriptException(script, var4);
      }
   }

   private boolean engineAllowsParallelAccessFromMultipleThreads() {
      String threadingType = (String)this.engine.getFactory().getParameter("THREADING");
      return "THREAD-ISOLATED".equals(threadingType) || "STATELESS".equals(threadingType);
   }
}
