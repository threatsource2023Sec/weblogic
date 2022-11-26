package com.bea.core.repackaged.springframework.scripting.bsh;

import bsh.EvalError;
import bsh.Interpreter;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptCompilationException;
import com.bea.core.repackaged.springframework.scripting.ScriptEvaluator;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

public class BshScriptEvaluator implements ScriptEvaluator, BeanClassLoaderAware {
   @Nullable
   private ClassLoader classLoader;

   public BshScriptEvaluator() {
   }

   public BshScriptEvaluator(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   @Nullable
   public Object evaluate(ScriptSource script) {
      return this.evaluate(script, (Map)null);
   }

   @Nullable
   public Object evaluate(ScriptSource script, @Nullable Map arguments) {
      try {
         Interpreter interpreter = new Interpreter();
         interpreter.setClassLoader(this.classLoader);
         if (arguments != null) {
            Iterator var4 = arguments.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               interpreter.set((String)entry.getKey(), entry.getValue());
            }
         }

         return interpreter.eval(new StringReader(script.getScriptAsString()));
      } catch (IOException var6) {
         throw new ScriptCompilationException(script, "Cannot access BeanShell script", var6);
      } catch (EvalError var7) {
         throw new ScriptCompilationException(script, var7);
      }
   }
}
