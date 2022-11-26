package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Function;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resource.Resource;
import net.shibboleth.utilities.java.support.scripting.EvaluableScript;
import org.opensaml.core.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptedTrustedNamesFunction implements Function {
   @Nonnull
   @NotEmpty
   public static final String DEFAULT_ENGINE = "JavaScript";
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ScriptedTrustedNamesFunction.class);
   @Nonnull
   private final EvaluableScript script;
   @Nullable
   private Object customObject;
   @Nullable
   private final String logPrefix;

   protected ScriptedTrustedNamesFunction(@Nonnull EvaluableScript theScript, @Nullable String extraInfo) {
      this.script = (EvaluableScript)Constraint.isNotNull(theScript, "Supplied script cannot be null");
      this.logPrefix = "Scripted Function from " + extraInfo + " :";
   }

   protected ScriptedTrustedNamesFunction(@Nonnull EvaluableScript theScript) {
      this.script = (EvaluableScript)Constraint.isNotNull(theScript, "Supplied script should not be null");
      this.logPrefix = "Anonymous Scripted Function :";
   }

   @Nullable
   public Object getCustomObject() {
      return this.customObject;
   }

   @Nullable
   public void setCustomObject(Object object) {
      this.customObject = object;
   }

   public Set apply(@Nullable XMLObject context) {
      SimpleScriptContext scriptContext = new SimpleScriptContext();
      scriptContext.setAttribute("custom", this.getCustomObject(), 100);
      scriptContext.setAttribute("profileContext", context, 100);

      try {
         Object output = this.script.eval(scriptContext);
         return (Set)output;
      } catch (ScriptException var4) {
         this.log.error("{} Error while executing Function script", this.logPrefix, var4);
         return null;
      }
   }

   @Nonnull
   static ScriptedTrustedNamesFunction resourceScript(@Nonnull @NotEmpty String engineName, @Nonnull Resource resource) throws ScriptException, IOException {
      InputStream is = resource.getInputStream();
      Throwable var3 = null;

      ScriptedTrustedNamesFunction var5;
      try {
         EvaluableScript script = new EvaluableScript(engineName, is);
         var5 = new ScriptedTrustedNamesFunction(script, resource.getDescription());
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

   @Nonnull
   static ScriptedTrustedNamesFunction resourceScript(@Nonnull Resource resource) throws ScriptException, IOException {
      return resourceScript("JavaScript", resource);
   }

   @Nonnull
   static ScriptedTrustedNamesFunction inlineScript(@Nonnull @NotEmpty String engineName, @Nonnull @NotEmpty String scriptSource) throws ScriptException {
      EvaluableScript script = new EvaluableScript(engineName, scriptSource);
      return new ScriptedTrustedNamesFunction(script, "Inline");
   }

   @Nonnull
   static ScriptedTrustedNamesFunction inlineScript(@Nonnull @NotEmpty String scriptSource) throws ScriptException {
      return inlineScript("JavaScript", scriptSource);
   }
}
