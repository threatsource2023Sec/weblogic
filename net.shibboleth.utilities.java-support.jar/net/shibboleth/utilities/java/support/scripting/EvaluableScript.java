package net.shibboleth.utilities.java.support.scripting;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class EvaluableScript {
   private final String scriptLanguage;
   private final String script;
   private ScriptEngine scriptEngine;
   private CompiledScript compiledScript;

   public EvaluableScript(@Nonnull @NotEmpty String engineName, @Nonnull @NotEmpty String scriptSource) throws ScriptException {
      this.scriptLanguage = (String)Constraint.isNotNull(StringSupport.trimOrNull(engineName), "Scripting language can not be null or empty");
      this.script = (String)Constraint.isNotNull(StringSupport.trimOrNull(scriptSource), "Script source can not be null or empty");
      this.initialize();
   }

   public EvaluableScript(@Nonnull @NotEmpty String scriptSource) throws ScriptException {
      this("javascript", scriptSource);
   }

   public EvaluableScript(@Nonnull @NotEmpty String engineName, @Nonnull InputStream scriptSource) throws ScriptException {
      this.scriptLanguage = (String)Constraint.isNotNull(StringSupport.trimOrNull(engineName), "Scripting language can not be null or empty");

      try {
         this.script = StringSupport.inputStreamToString((InputStream)Constraint.isNotNull(scriptSource, "Script source can not be null or empty"), (CharsetDecoder)null);
      } catch (IOException var4) {
         throw new ScriptException(var4);
      }

      this.initialize();
   }

   public EvaluableScript(@Nonnull @NotEmpty String engineName, @Nonnull File scriptSource) throws ScriptException {
      this.scriptLanguage = (String)Constraint.isNotNull(StringSupport.trimOrNull(engineName), "Scripting language can not be null or empty");
      Constraint.isNotNull(scriptSource, "Script source file can not be null");
      if (!scriptSource.exists()) {
         throw new ScriptException("Script source file " + scriptSource.getAbsolutePath() + " does not exist");
      } else if (!scriptSource.canRead()) {
         throw new ScriptException("Script source file " + scriptSource.getAbsolutePath() + " exists but is not readable");
      } else {
         try {
            this.script = (String)Constraint.isNotNull(StringSupport.trimOrNull(Files.toString(scriptSource, Charset.defaultCharset())), "Script source can not be empty");
         } catch (IOException var4) {
            throw new ScriptException("Unable to read data from source file " + scriptSource.getAbsolutePath());
         }

         this.initialize();
      }
   }

   @Nonnull
   public String getScript() {
      return this.script;
   }

   @Nonnull
   public String getScriptLanguage() {
      return this.scriptLanguage;
   }

   @Nullable
   public Object eval(Bindings scriptBindings) throws ScriptException {
      return this.compiledScript != null ? this.compiledScript.eval(scriptBindings) : this.scriptEngine.eval(this.script, scriptBindings);
   }

   @Nullable
   public Object eval(ScriptContext scriptContext) throws ScriptException {
      return this.compiledScript != null ? this.compiledScript.eval(scriptContext) : this.scriptEngine.eval(this.script, scriptContext);
   }

   private void initialize() throws ScriptException {
      ScriptEngineManager engineManager = new ScriptEngineManager();
      this.scriptEngine = engineManager.getEngineByName(this.scriptLanguage);
      Constraint.isNotNull(this.scriptEngine, "No scripting engine associated with scripting language " + this.scriptLanguage);
      if (this.scriptEngine instanceof Compilable) {
         this.compiledScript = ((Compilable)this.scriptEngine).compile(this.script);
      } else {
         this.compiledScript = null;
      }

   }
}
