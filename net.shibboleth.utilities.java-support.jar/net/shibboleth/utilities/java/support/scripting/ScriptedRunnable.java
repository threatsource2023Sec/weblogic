package net.shibboleth.utilities.java.support.scripting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.ScriptContext;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.UnmodifiableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptedRunnable extends AbstractIdentifiableInitializableComponent implements Runnable, UnmodifiableComponent {
   @NonnullAfterInit
   private EvaluableScript script;
   @NonnullAfterInit
   private RunnableScriptEvaluator scriptEvaluator;
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ScriptedRunnable.class);
   @Nullable
   private Object customObject;

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (null == this.script) {
         throw new ComponentInitializationException("No script has been provided");
      } else {
         this.scriptEvaluator = new RunnableScriptEvaluator(this.script);
         this.scriptEvaluator.setCustomObject(this.customObject);
         StringBuilder builder = (new StringBuilder("ScriptedRunnable '")).append(this.getId()).append("':");
         this.scriptEvaluator.setLogPrefix(builder.toString());
      }
   }

   @Nullable
   public Object getCustomObject() {
      return this.customObject;
   }

   public void setCustomObject(@Nullable Object object) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.customObject = object;
   }

   @NonnullAfterInit
   public EvaluableScript getScript() {
      return this.script;
   }

   public void setScript(@Nonnull EvaluableScript matcherScript) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.script = (EvaluableScript)Constraint.isNotNull(matcherScript, "Attribute value matching script cannot be null");
   }

   public void run() {
      this.scriptEvaluator.execute();
   }

   private class RunnableScriptEvaluator extends AbstractScriptEvaluator {
      public RunnableScriptEvaluator(@Nonnull EvaluableScript theScript) {
         super(theScript);
      }

      protected void prepareContext(ScriptContext scriptContext, Object... input) {
      }

      public void execute() {
         ScriptedRunnable.this.log.debug("{}: running script", this.getLogPrefix());
         this.evaluate((Object[])null);
      }
   }
}
