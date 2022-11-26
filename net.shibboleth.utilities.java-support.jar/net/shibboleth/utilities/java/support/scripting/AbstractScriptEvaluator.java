package net.shibboleth.utilities.java.support.scripting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractScriptEvaluator {
   @Nonnull
   @NotEmpty
   public static final String DEFAULT_ENGINE = "JavaScript";
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractScriptEvaluator.class);
   @Nonnull
   private final EvaluableScript script;
   @Nullable
   private String logPrefix;
   @Nullable
   private Class outputType;
   @Nullable
   private Object customObject;
   private boolean hideExceptions;
   @Nullable
   private Object returnOnError;

   public AbstractScriptEvaluator(@Nonnull @ParameterName(name = "theScript") EvaluableScript theScript) {
      this.script = (EvaluableScript)Constraint.isNotNull(theScript, "Supplied script cannot be null");
   }

   @Nullable
   protected String getLogPrefix() {
      return this.logPrefix;
   }

   public void setLogPrefix(@Nullable String prefix) {
      this.logPrefix = prefix;
   }

   @Nullable
   protected Class getOutputType() {
      return this.outputType;
   }

   protected void setOutputType(@Nullable Class type) {
      this.outputType = type;
   }

   @Nullable
   protected Object getCustomObject() {
      return this.customObject;
   }

   public void setCustomObject(@Nullable Object object) {
      this.customObject = object;
   }

   protected boolean getHideExceptions() {
      return this.hideExceptions;
   }

   public void setHideExceptions(boolean flag) {
      this.hideExceptions = flag;
   }

   @Nullable
   protected Object getReturnOnError() {
      return this.returnOnError;
   }

   protected void setReturnOnError(@Nullable Object value) {
      this.returnOnError = value;
   }

   @Nullable
   protected Object evaluate(@Nullable Object... input) {
      SimpleScriptContext scriptContext = new SimpleScriptContext();
      scriptContext.setAttribute("custom", this.getCustomObject(), 100);
      this.prepareContext(scriptContext, input);

      try {
         Object result = this.script.eval((ScriptContext)scriptContext);
         if (null != this.getOutputType() && null != result && !this.getOutputType().isInstance(result)) {
            this.log.error("{} Output of type {} was not of type {}", new Object[]{this.getLogPrefix(), result.getClass(), this.getOutputType()});
            return this.getReturnOnError();
         } else {
            return this.finalizeContext(scriptContext, result);
         }
      } catch (ScriptException var4) {
         if (this.getHideExceptions()) {
            this.log.warn("{} Suppressing exception thrown by script", this.getLogPrefix(), var4);
            return this.getReturnOnError();
         } else {
            throw new RuntimeException(var4);
         }
      }
   }

   protected abstract void prepareContext(@Nonnull ScriptContext var1, @Nullable Object... var2);

   @Nullable
   protected Object finalizeContext(@Nonnull ScriptContext scriptContext, @Nullable Object scriptResult) throws ScriptException {
      return scriptResult;
   }
}
