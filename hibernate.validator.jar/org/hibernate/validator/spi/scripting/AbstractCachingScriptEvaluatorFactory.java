package org.hibernate.validator.spi.scripting;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.hibernate.validator.Incubating;

@Incubating
public abstract class AbstractCachingScriptEvaluatorFactory implements ScriptEvaluatorFactory {
   private final ConcurrentMap scriptEvaluatorCache = new ConcurrentHashMap();

   public ScriptEvaluator getScriptEvaluatorByLanguageName(String languageName) {
      return (ScriptEvaluator)this.scriptEvaluatorCache.computeIfAbsent(languageName, this::createNewScriptEvaluator);
   }

   public void clear() {
      this.scriptEvaluatorCache.clear();
   }

   protected abstract ScriptEvaluator createNewScriptEvaluator(String var1) throws ScriptEvaluatorNotFoundException;
}
