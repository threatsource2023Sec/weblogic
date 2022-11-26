package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.resource.Resource;
import net.shibboleth.utilities.java.support.scripting.AbstractScriptEvaluator;
import net.shibboleth.utilities.java.support.scripting.EvaluableScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptedFunction extends AbstractScriptEvaluator implements Function {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ScriptedFunction.class);
   @Nullable
   private Class inputTypeClass;

   protected ScriptedFunction(@Nonnull @ParameterName(name = "theScript") @NotEmpty EvaluableScript theScript, @Nullable @ParameterName(name = "extraInfo") @NotEmpty String extraInfo) {
      super(theScript);
      this.setLogPrefix("Scripted Function from " + extraInfo + ":");
   }

   protected ScriptedFunction(@Nonnull @ParameterName(name = "theScript") @NotEmpty EvaluableScript theScript) {
      super(theScript);
      this.setLogPrefix("Anonymous Function:");
   }

   public void setOutputType(@Nullable Class type) {
      super.setOutputType(type);
   }

   @Nullable
   public Class getInputType() {
      return this.inputTypeClass;
   }

   public void setInputType(@Nullable Class type) {
      this.inputTypeClass = type;
   }

   public void setReturnOnError(@Nullable Object value) {
      super.setReturnOnError(value);
   }

   public Object apply(@Nullable Object input) {
      if (null != this.getInputType() && null != input && !this.getInputType().isInstance(input)) {
         this.log.error("{} Input of type {} was not of type {}", new Object[]{this.getLogPrefix(), input.getClass(), this.getInputType()});
         return this.getReturnOnError();
      } else {
         return this.evaluate(new Object[]{input});
      }
   }

   protected void prepareContext(@Nonnull ScriptContext scriptContext, @Nullable Object... input) {
      scriptContext.setAttribute("input", input[0], 100);
   }

   static ScriptedFunction resourceScript(@Nonnull @NotEmpty String engineName, @Nonnull Resource resource) throws ScriptException, IOException {
      InputStream is = resource.getInputStream();
      Throwable var3 = null;

      ScriptedFunction var5;
      try {
         EvaluableScript script = new EvaluableScript(engineName, is);
         var5 = new ScriptedFunction(script, resource.getDescription());
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (is != null) {
            if (var3 != null) {
               try {
                  is.close();
               } catch (Throwable var13) {
                  var3.addSuppressed(var13);
               }
            } else {
               is.close();
            }
         }

      }

      return var5;
   }

   static ScriptedFunction resourceScript(Resource resource) throws ScriptException, IOException {
      return resourceScript("JavaScript", resource);
   }

   static ScriptedFunction inlineScript(@Nonnull @NotEmpty String engineName, @Nonnull @NotEmpty String scriptSource) throws ScriptException {
      EvaluableScript script = new EvaluableScript(engineName, scriptSource);
      return new ScriptedFunction(script, "Inline");
   }

   static ScriptedFunction inlineScript(@Nonnull @NotEmpty String scriptSource) throws ScriptException {
      EvaluableScript script = new EvaluableScript("JavaScript", scriptSource);
      return new ScriptedFunction(script, "Inline");
   }
}
