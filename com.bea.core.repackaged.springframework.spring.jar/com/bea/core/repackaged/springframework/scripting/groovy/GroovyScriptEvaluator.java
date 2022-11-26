package com.bea.core.repackaged.springframework.scripting.groovy;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptCompilationException;
import com.bea.core.repackaged.springframework.scripting.ScriptEvaluator;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.scripting.support.ResourceScriptSource;
import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import java.io.IOException;
import java.util.Map;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

public class GroovyScriptEvaluator implements ScriptEvaluator, BeanClassLoaderAware {
   @Nullable
   private ClassLoader classLoader;
   private CompilerConfiguration compilerConfiguration = new CompilerConfiguration();

   public GroovyScriptEvaluator() {
   }

   public GroovyScriptEvaluator(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public void setCompilerConfiguration(@Nullable CompilerConfiguration compilerConfiguration) {
      this.compilerConfiguration = compilerConfiguration != null ? compilerConfiguration : new CompilerConfiguration();
   }

   public CompilerConfiguration getCompilerConfiguration() {
      return this.compilerConfiguration;
   }

   public void setCompilationCustomizers(CompilationCustomizer... compilationCustomizers) {
      this.compilerConfiguration.addCompilationCustomizers(compilationCustomizers);
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
      GroovyShell groovyShell = new GroovyShell(this.classLoader, new Binding(arguments), this.compilerConfiguration);

      try {
         String filename = script instanceof ResourceScriptSource ? ((ResourceScriptSource)script).getResource().getFilename() : null;
         return filename != null ? groovyShell.evaluate(script.getScriptAsString(), filename) : groovyShell.evaluate(script.getScriptAsString());
      } catch (IOException var5) {
         throw new ScriptCompilationException(script, "Cannot access Groovy script", var5);
      } catch (GroovyRuntimeException var6) {
         throw new ScriptCompilationException(script, var6);
      }
   }
}
