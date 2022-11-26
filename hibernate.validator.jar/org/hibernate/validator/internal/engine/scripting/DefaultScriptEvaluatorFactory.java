package org.hibernate.validator.internal.engine.scripting;

import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.spi.scripting.AbstractCachingScriptEvaluatorFactory;
import org.hibernate.validator.spi.scripting.ScriptEngineScriptEvaluator;
import org.hibernate.validator.spi.scripting.ScriptEvaluationException;
import org.hibernate.validator.spi.scripting.ScriptEvaluator;

public class DefaultScriptEvaluatorFactory extends AbstractCachingScriptEvaluatorFactory {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private ClassLoader classLoader;
   private volatile ScriptEngineManager scriptEngineManager;
   private volatile ScriptEngineManager threadContextClassLoaderScriptEngineManager;

   public DefaultScriptEvaluatorFactory(ClassLoader externalClassLoader) {
      this.classLoader = externalClassLoader == null ? DefaultScriptEvaluatorFactory.class.getClassLoader() : externalClassLoader;
   }

   public void clear() {
      super.clear();
      this.classLoader = null;
      this.scriptEngineManager = null;
      this.threadContextClassLoaderScriptEngineManager = null;
   }

   protected ScriptEvaluator createNewScriptEvaluator(String languageName) throws ScriptEvaluationException {
      ScriptEngine engine = this.getScriptEngineManager().getEngineByName(languageName);
      if (engine == null) {
         engine = this.getThreadContextClassLoaderScriptEngineManager().getEngineByName(languageName);
      }

      if (engine == null) {
         throw LOG.getUnableToFindScriptEngineException(languageName);
      } else {
         return new ScriptEngineScriptEvaluator(engine);
      }
   }

   private ScriptEngineManager getScriptEngineManager() {
      if (this.scriptEngineManager == null) {
         synchronized(this) {
            if (this.scriptEngineManager == null) {
               this.scriptEngineManager = new ScriptEngineManager(this.classLoader);
            }
         }
      }

      return this.scriptEngineManager;
   }

   private ScriptEngineManager getThreadContextClassLoaderScriptEngineManager() {
      if (this.threadContextClassLoaderScriptEngineManager == null) {
         synchronized(this) {
            if (this.threadContextClassLoaderScriptEngineManager == null) {
               this.threadContextClassLoaderScriptEngineManager = new ScriptEngineManager((ClassLoader)run(GetClassLoader.fromContext()));
            }
         }
      }

      return this.threadContextClassLoaderScriptEngineManager;
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
