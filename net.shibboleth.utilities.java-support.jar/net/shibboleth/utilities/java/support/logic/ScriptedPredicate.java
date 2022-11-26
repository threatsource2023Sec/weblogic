package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Predicate;
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

public class ScriptedPredicate extends AbstractScriptEvaluator implements Predicate {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ScriptedPredicate.class);

   protected ScriptedPredicate(@Nonnull @ParameterName(name = "theScript") @NotEmpty EvaluableScript theScript, @Nullable @ParameterName(name = "extraInfo") @NotEmpty String extraInfo) {
      super(theScript);
      this.setOutputType(Boolean.class);
      this.setReturnOnError(false);
      this.setLogPrefix("Scripted Predicate from " + extraInfo + ":");
   }

   protected ScriptedPredicate(@Nonnull @ParameterName(name = "theScript") @NotEmpty EvaluableScript theScript) {
      super(theScript);
      this.setLogPrefix("Anonymous Scripted Predicate:");
      this.setOutputType(Boolean.class);
      this.setReturnOnError(false);
   }

   public void setReturnOnError(boolean flag) {
      this.setReturnOnError(flag);
   }

   public boolean apply(@Nullable Object input) {
      Object result = this.evaluate(new Object[]{input});
      return (Boolean)(result != null ? result : this.getReturnOnError());
   }

   protected void prepareContext(@Nonnull ScriptContext scriptContext, @Nullable Object... input) {
      scriptContext.setAttribute("input", input[0], 100);
   }

   static ScriptedPredicate resourceScript(@Nonnull @NotEmpty String engineName, @Nonnull Resource resource) throws ScriptException, IOException {
      InputStream is = resource.getInputStream();
      Throwable var3 = null;

      ScriptedPredicate var5;
      try {
         EvaluableScript script = new EvaluableScript(engineName, is);
         var5 = new ScriptedPredicate(script, resource.getDescription());
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

   static ScriptedPredicate resourceScript(Resource resource) throws ScriptException, IOException {
      return resourceScript("JavaScript", resource);
   }

   static ScriptedPredicate inlineScript(@Nonnull @NotEmpty String engineName, @Nonnull @NotEmpty String scriptSource) throws ScriptException {
      EvaluableScript script = new EvaluableScript(engineName, scriptSource);
      return new ScriptedPredicate(script, "Inline");
   }

   static ScriptedPredicate inlineScript(@Nonnull @NotEmpty String scriptSource) throws ScriptException {
      EvaluableScript script = new EvaluableScript("JavaScript", scriptSource);
      return new ScriptedPredicate(script, "Inline");
   }
}
