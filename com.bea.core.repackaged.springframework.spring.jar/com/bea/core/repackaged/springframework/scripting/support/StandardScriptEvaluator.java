package com.bea.core.repackaged.springframework.scripting.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptCompilationException;
import com.bea.core.repackaged.springframework.scripting.ScriptEvaluator;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class StandardScriptEvaluator implements ScriptEvaluator, BeanClassLoaderAware {
   @Nullable
   private String engineName;
   @Nullable
   private volatile Bindings globalBindings;
   @Nullable
   private volatile ScriptEngineManager scriptEngineManager;

   public StandardScriptEvaluator() {
   }

   public StandardScriptEvaluator(ClassLoader classLoader) {
      this.scriptEngineManager = new ScriptEngineManager(classLoader);
   }

   public StandardScriptEvaluator(ScriptEngineManager scriptEngineManager) {
      this.scriptEngineManager = scriptEngineManager;
   }

   public void setLanguage(String language) {
      this.engineName = language;
   }

   public void setEngineName(String engineName) {
      this.engineName = engineName;
   }

   public void setGlobalBindings(Map globalBindings) {
      Bindings bindings = StandardScriptUtils.getBindings(globalBindings);
      this.globalBindings = bindings;
      ScriptEngineManager scriptEngineManager = this.scriptEngineManager;
      if (scriptEngineManager != null) {
         scriptEngineManager.setBindings(bindings);
      }

   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      ScriptEngineManager scriptEngineManager = this.scriptEngineManager;
      if (scriptEngineManager == null) {
         scriptEngineManager = new ScriptEngineManager(classLoader);
         this.scriptEngineManager = scriptEngineManager;
         Bindings bindings = this.globalBindings;
         if (bindings != null) {
            scriptEngineManager.setBindings(bindings);
         }
      }

   }

   @Nullable
   public Object evaluate(ScriptSource script) {
      return this.evaluate(script, (Map)null);
   }

   @Nullable
   public Object evaluate(ScriptSource script, @Nullable Map argumentBindings) {
      ScriptEngine engine = this.getScriptEngine(script);

      try {
         if (CollectionUtils.isEmpty(argumentBindings)) {
            return engine.eval(script.getScriptAsString());
         } else {
            Bindings bindings = StandardScriptUtils.getBindings(argumentBindings);
            return engine.eval(script.getScriptAsString(), bindings);
         }
      } catch (IOException var5) {
         throw new ScriptCompilationException(script, "Cannot access script for ScriptEngine", var5);
      } catch (ScriptException var6) {
         throw new ScriptCompilationException(script, new StandardScriptEvalException(var6));
      }
   }

   protected ScriptEngine getScriptEngine(ScriptSource script) {
      ScriptEngineManager scriptEngineManager = this.scriptEngineManager;
      if (scriptEngineManager == null) {
         scriptEngineManager = new ScriptEngineManager();
         this.scriptEngineManager = scriptEngineManager;
      }

      if (StringUtils.hasText(this.engineName)) {
         return StandardScriptUtils.retrieveEngineByName(scriptEngineManager, this.engineName);
      } else if (script instanceof ResourceScriptSource) {
         Resource resource = ((ResourceScriptSource)script).getResource();
         String extension = StringUtils.getFilenameExtension(resource.getFilename());
         if (extension == null) {
            throw new IllegalStateException("No script language defined, and no file extension defined for resource: " + resource);
         } else {
            ScriptEngine engine = scriptEngineManager.getEngineByExtension(extension);
            if (engine == null) {
               throw new IllegalStateException("No matching engine found for file extension '" + extension + "'");
            } else {
               return engine;
            }
         }
      } else {
         throw new IllegalStateException("No script language defined, and no resource associated with script: " + script);
      }
   }
}
