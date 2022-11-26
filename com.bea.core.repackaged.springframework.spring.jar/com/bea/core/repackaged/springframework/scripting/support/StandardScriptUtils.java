package com.bea.core.repackaged.springframework.scripting.support;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

public abstract class StandardScriptUtils {
   public static ScriptEngine retrieveEngineByName(ScriptEngineManager scriptEngineManager, String engineName) {
      ScriptEngine engine = scriptEngineManager.getEngineByName(engineName);
      if (engine == null) {
         Set engineNames = new LinkedHashSet();

         List factoryNames;
         for(Iterator var4 = scriptEngineManager.getEngineFactories().iterator(); var4.hasNext(); engineNames.addAll(factoryNames)) {
            ScriptEngineFactory engineFactory = (ScriptEngineFactory)var4.next();
            factoryNames = engineFactory.getNames();
            if (factoryNames.contains(engineName)) {
               try {
                  engine = engineFactory.getScriptEngine();
                  engine.setBindings(scriptEngineManager.getBindings(), 200);
               } catch (Throwable var8) {
                  throw new IllegalStateException("Script engine with name '" + engineName + "' failed to initialize", var8);
               }
            }
         }

         throw new IllegalArgumentException("Script engine with name '" + engineName + "' not found; registered engine names: " + engineNames);
      } else {
         return engine;
      }
   }

   static Bindings getBindings(Map bindings) {
      return (Bindings)(bindings instanceof Bindings ? (Bindings)bindings : new SimpleBindings(bindings));
   }
}
